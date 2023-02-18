/*
 * Copyright (C) 2005 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.phase.handler.or;

import com.topcoder.onlinereview.component.deliverable.Submission;
import com.topcoder.onlinereview.component.deliverable.SubmissionFilterBuilder;
import com.topcoder.onlinereview.component.deliverable.UploadManager;
import com.topcoder.onlinereview.component.exception.BaseException;
import com.topcoder.onlinereview.component.grpcclient.phasehandler.PhaseHandlerServiceRpc;
import com.topcoder.onlinereview.component.project.management.LogMessage;
import com.topcoder.onlinereview.component.project.management.PersistenceException;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectManager;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import com.topcoder.onlinereview.component.project.management.ValidationException;
import com.topcoder.onlinereview.component.project.phase.ManagerHelper;
import com.topcoder.onlinereview.component.project.phase.Phase;
import com.topcoder.onlinereview.component.project.phase.PhaseHandlingException;
import com.topcoder.onlinereview.component.project.phase.handler.Constants;
import com.topcoder.onlinereview.component.project.phase.handler.LookupHelper;
import com.topcoder.onlinereview.component.project.phase.handler.PhasesHelper;
import com.topcoder.onlinereview.component.review.Review;
import com.topcoder.onlinereview.component.search.SearchBuilderException;
import com.topcoder.onlinereview.component.search.filter.AndFilter;
import com.topcoder.onlinereview.component.search.filter.EqualToFilter;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.component.search.filter.NotFilter;
import com.topcoder.onlinereview.component.search.filter.OrFilter;
import com.topcoder.onlinereview.grpc.phasehandler.proto.AppealResponseProto;
import com.topcoder.onlinereview.grpc.phasehandler.proto.ReviewProto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * The PRHelper which is used to provide helper method for Phase Handler.
 *
 * <p>
 * Thread-safety: This class is thread-safe as it does not contain any mutable inner state.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
public class PRHelper {

    private static final Logger logger = LoggerFactory.getLogger(PRHelper.class.getName());

    /**
     * This member variable holds the formatting string used to format dates.
     */
    private static final String dateFormat = "MM.dd.yyyy HH:mm z";

    /**
     * Pull data to project_result.
     */
    void processRegistrationPR(long projectId, boolean toStart) {
        if (toStart) {
            logger.info(
                new LogMessage(projectId, null, "start registration process.").toString());
        }
    }

    /**
     * Pull data to project_result.
     *
     * @param projectId
     *            the projectId
     * @throws PhaseHandlingException
     *             if error occurs
     */
    void processSubmissionPR(PhaseHandlerServiceRpc phaseHandlerServiceRpc, long projectId, boolean toStart) throws PhaseHandlingException {
        if (!toStart) {
            logger.info(
                new LogMessage(projectId, null, "process submission phase.").toString());
            phaseHandlerServiceRpc.updateProjectResult(projectId);
        }
    }

    /**
     * Pull data to project_result.
     *
     * @param projectId
     *            the projectId
     * @param toStart
     *            whether the phase is to start or not.
     * @param operator
     *            the operator.
     * @throws PhaseHandlingException
     *             if error occurs
     */
    void processScreeningPR(PhaseHandlerServiceRpc phaseHandlerServiceRpc, long projectId, boolean toStart, String operator) throws PhaseHandlingException {
        if (!toStart) {
            logger.info(
                new LogMessage(projectId, null, "process screening phase.").toString());
            // Update all users who failed to pass screen, set valid_submission_ind = 0
            phaseHandlerServiceRpc.updateFailedPassScreening(projectId);

            // Update all users who pass screen, set valid_submission_ind = 1
            phaseHandlerServiceRpc.updatePassScreening(projectId);

            PaymentsHelper.processAutomaticPayments(projectId, operator);
        }
    }

    /**
     * Pull data to project_result for software competitions; update submitter's payments and complete project
     * for Studio competitions.
     *
     * @param managerHelper
     *            the <code>ManagerHelper</code> instance.
     * @param phase
     *            the corresponding review phase.
     * @param operator
     *            the operator.
     * @param toStart
     *            whether the phase is to start or not.
     * @throws PhaseHandlingException
     *             if error occurs
     */
    void processReviewPR(PhaseHandlerServiceRpc phaseHandlerServiceRpc, ManagerHelper managerHelper, Phase phase, String operator, boolean toStart) throws PhaseHandlingException {
        long projectId = phase.getProject().getId();
        boolean paymentsProcessed = false;
        try {
            if (!toStart) {
                logger.info(
                        new LogMessage(projectId, null, "process review phase.").toString());

                // if review phase is last one and there is at least one active submission complete the project.
                if (isLastPhase(phase)) {
                    Submission[] activeSubs = PhasesHelper.getActiveProjectSubmissions(managerHelper.getUploadManager(),
                        projectId, Constants.SUBMISSION_TYPE_CONTEST_SUBMISSION);
                    if (activeSubs.length > 0) {
                        completeProject(managerHelper, phase, operator);
                    }
                }

                if (!isStudioProject(managerHelper.getProjectManager(), projectId)) {
                    // Retrieve all
                    List<ReviewProto> result = phaseHandlerServiceRpc.getReviews(projectId);
                    
                    for (ReviewProto r: result) {
                        // Update all raw score
                        Double rawScore = r.hasRawScore() ? r.getRawScore() : null;
                        long userId = r.getUserId();
                        phaseHandlerServiceRpc.updateProjectResultReview(projectId, rawScore, userId);
                    }

                    Phase appealsResponsePhase = PhasesHelper.locatePhase(phase, "Appeals Response", true, false);
                    if (appealsResponsePhase == null) {
                        // populate project result
                        populateProjectResult(phaseHandlerServiceRpc, projectId, operator);
                        paymentsProcessed = true;
                    }
                }

                if (!paymentsProcessed) {
                    PaymentsHelper.processAutomaticPayments(projectId, operator);
                }
            }
        } catch(SQLException e) {
            throw new PhaseHandlingException("Failed to push data to project_result", e);
        }
    }

    /**
     * Pull data to project_result for while appeal response phase closed.
     *
     * @param managerHelper
     *            the <code>ManagerHelper</code> instance.
     * @param phase
     *            the corresponding review phase.
     * @param toStart
     *            whether the phase is to start or not.
     * @param operator
     *            the operator.
     * @throws PhaseHandlingException
     *             if error occurs
     */
    void processAppealResponsePR(PhaseHandlerServiceRpc phaseHandlerServiceRpc, ManagerHelper managerHelper, Phase phase, boolean toStart, String operator) throws PhaseHandlingException {
        long projectId = phase.getProject().getId();

        try {
            if (!toStart) {
                logger.info(
                    new LogMessage(projectId, null, "process Appeal Response phase.").toString());

                // if appeals response phase is last one and there is at least one active submission complete the project.
                if (isLastPhase(phase)) {
                    Submission [] activeSubs = PhasesHelper.getActiveProjectSubmissions(managerHelper.getUploadManager(),
                            projectId, Constants.SUBMISSION_TYPE_CONTEST_SUBMISSION);
                    if (activeSubs.length > 0) {
                        completeProject(managerHelper, phase, operator);
                    }
                }

                populateProjectResult(phaseHandlerServiceRpc, projectId, operator);
            }
        } catch(SQLException e) {
            throw new PhaseHandlingException("Failed to push data to project_result", e);
        }
    }

    /**
     * Pull data to project_result.
     *
     * @param projectId
     *            the projectId
     * @param toStart
     *            whether the phase is to start or not.
     * @param operator
     *            the operator.
     * @throws PhaseHandlingException
     *             if error occurs
     */
    void processAggregationPR(PhaseHandlerServiceRpc phaseHandlerServiceRpc, long projectId,  boolean toStart, String operator) throws PhaseHandlingException {
        try {
            if (!toStart) {
                logger.info(
                        new LogMessage(projectId, null, "process Aggregation phase.").toString());
                populateProjectResult(phaseHandlerServiceRpc, projectId, operator);
            }
        } catch(SQLException e) {
            throw new PhaseHandlingException("Failed to push data to project_result", e);
        }
    }

    /**
     * Pull data to project_result.
     *
     * @param projectId
     *            the projectId
     * @param toStart
     *            whether the phase is to start or not.
     * @param operator
     *            the operator.
     * @throws PhaseHandlingException
     *             if error occurs
     */
    void processFinalFixPR(PhaseHandlerServiceRpc phaseHandlerServiceRpc, long projectId, boolean toStart, String operator) throws PhaseHandlingException {
        try {
            if (!toStart) {
                logger.info(
                        new LogMessage(projectId, null, "Process final fix phase.").toString());
                populateProjectResult(phaseHandlerServiceRpc, projectId, operator);
            }
        } catch(SQLException e) {
            throw new PhaseHandlingException("Failed to push data to project_result", e);
        }
    }

    /**
     * Pull data to project_result.
     *
     * @param projectId
     *            the projectId
     * @param toStart
     *            whether the phase is to start or not.
     * @param operator
     *            the operator.
     * @throws PhaseHandlingException
     *             if error occurs
     */
    void processFinalReviewPR(PhaseHandlerServiceRpc phaseHandlerServiceRpc, long projectId, boolean toStart, String operator) throws PhaseHandlingException {
        try {
            if (!toStart) {
                logger.info(
                        new LogMessage(projectId, null, "Process final review phase.").toString());
                populateProjectResult(phaseHandlerServiceRpc, projectId, operator);
            } else {
                logger.info(
                        new LogMessage(projectId, null, "start final review phase.").toString());
            }
        } catch(SQLException e) {
            throw new PhaseHandlingException("Failed to push data to project_result", e);
        }
    }

    /**
     * Pull data to project_result, populate reviewer payments and clear copilot payments.
     *
     * @param projectId
     *            the projectId
     * @param toStart
     *            whether the phase is to start or not.
     * @param operator
     *            the operator.
     * @throws PhaseHandlingException
     *             if error occurs
     */
    void processPostMortemPR(PhaseHandlerServiceRpc phaseHandlerServiceRpc, long projectId, boolean toStart, String operator) throws PhaseHandlingException {
        try {
            if (!toStart) {
                logger.info(
                        new LogMessage(projectId, null, "Process post mortem phase.").toString());
                populateProjectResult(phaseHandlerServiceRpc, projectId, operator);
            } else {
                logger.info(
                        new LogMessage(projectId, null, "start post mortem phase.").toString());
            }
        } catch(SQLException e) {
            throw new PhaseHandlingException("Failed to push data to project_result", e);
        }
    }

    /**
     * Populate final_score, placed and passed_review_ind.
     *
     * @param projectId
     *            project id
     * @param operator
     *            the operator.
     * @throws SQLException
     *             if error occurs
     */
    public static void populateProjectResult(PhaseHandlerServiceRpc phaseHandlerServiceRpc, long projectId, String operator)
            throws SQLException, PhaseHandlingException {
        // Payment should be set before populate to project_result table
        PaymentsHelper.processAutomaticPayments(projectId, operator);
        PaymentsHelper.updateProjectResultPayments(phaseHandlerServiceRpc, projectId);

            // Retrieve all
            List<AppealResponseProto> result = phaseHandlerServiceRpc.getAppealResponses(projectId);

            logger.debug(new LogMessage(projectId, null,
                    "update project_result with final scores, placed and passed_review_ind.").toString());
            for (AppealResponseProto r: result) {
                int status = r.getSubmissionStatusId();
                // Update final score, placed and passed_review_ind
                Double finalScore = r.hasFinalScore() ? r.getFinalScore() : null;
                Integer placed = r.hasPlacement() ? r.getPlacement() : null;
                // 1 is active, 4 is Completed Without Win
                int passedReviewInd = status == 1 || status == 4 ? 1 : 0;
                Long userId = r.hasUserId() ? r.getUserId() : null;
                phaseHandlerServiceRpc.updateProjectResultAppealResponse(projectId, finalScore, userId, placed, passedReviewInd);
            }

    }

    /**
     * Close the jdbc resource.
     *
     * @param obj
     *            the jdbc resource object
     */
    static void close(Object obj) {
        if (obj instanceof Connection) {
            try {
                ((Connection) obj).close();
                logger.debug("close the connection");
            } catch (Exception e) {
                // Just ignore
            }
        } else if (obj instanceof PreparedStatement) {
            try {
                ((PreparedStatement) obj).close();
            } catch (Exception e) {
                // Just ignore
            }
        }
    }

    /**
     * Finds whether the project id belongs to a studio project or not.
     *
     * @param projectId
     *            the project d.
     * @return true if it is a studio project.
     */
    static boolean isStudioProject(ProjectManager projectManager, long projectId) {
        try {
            return "Studio".equals(projectManager.getProject(projectId).getProjectCategory().getProjectType().getName());
        } catch (BaseException e) {
            return false;
        }
    }

    /**
     * Checks if the given phase is the last phase in the project. Note that if multiple
     * phases end at the same date/time at the end of the project, all they are
     * considered to be last phases of the project.
     * @param phase
     *            the phase to be checked.
     * @return true if phase is the last phase in the project, false otherwise.
     */
    static boolean isLastPhase(Phase phase) {
        // Get all phases for the project
        Phase[] phases = phase.getProject().getAllPhases();

        // Get index of the input phase in phases array
        int phaseIndex = 0;

        for (int i = 0; i < phases.length; i++) {
            if (phases[i].getId() == phase.getId()) {
                phaseIndex = i;
            }
        }

        Date endDate = phases[phaseIndex].calcEndDate();
        for (int i=0; i < phases.length; i++) {
            // Dirty fix for Studio F2F contests - Review phase should be considered as "last" even if the
            // Registration and Submission phases are still open and scheduled to close after Review.
            String phaseType = phases[i].getPhaseType().getName();
            if (Constants.PHASE_REGISTRATION.equals(phaseType) || Constants.PHASE_SUBMISSION.equals(phaseType)) {
                continue;
            }
            
            if (i != phaseIndex && phases[i].calcEndDate().after(endDate)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Finds the project status with the given name.
     *
     * @param projectManager
     *            The ProjectManager instance
     * @param statusName
     *            the status name
     * @return the matched project status
     * @throws PhaseHandlingException
     *             If any problem to find the project status
     */
    static ProjectStatus findProjectStatusByName(ProjectManager projectManager, String statusName)
        throws PhaseHandlingException {
        try {
            ProjectStatus[] statuses = projectManager.getAllProjectStatuses();

            ProjectStatus result = null;
            for (ProjectStatus status : statuses) {
                if (statusName.equals(status.getName())) {
                    result = status;
                }
            }

            if (result == null) {
                throw new PhaseHandlingException(
                        "There is no corresponding record in persistence for status with name - " + statusName);
            }

            return result;
        } catch (PersistenceException e) {
            throw new PhaseHandlingException("Fail to retrieve all project statuses", e);
        }
    }

    /**
     * Updates the project status to "Completed".
     *
     * @param managerHelper
     *            the ManagerHelper instance.
     * @param phase
     *            the phase
     * @param operator
     *            the operator
     * @throws PhaseHandlingException
     *             If any error occurs while updating the project status.
     */
    static void completeProject(ManagerHelper managerHelper, Phase phase, String operator)
        throws PhaseHandlingException {
        try {
            ProjectManager projectManager = managerHelper.getProjectManager();
            Project project = projectManager.getProject(phase.getProject().getId());

            Format format = new SimpleDateFormat(dateFormat);
            project.setProperty("Completion Timestamp", format.format(new Date()));

            ProjectStatus completedStatus = PRHelper.findProjectStatusByName(projectManager, "Completed");
            project.setProjectStatus(completedStatus);
            projectManager.updateProject(project, "Setting the project status to Completed automatically", operator);
        } catch (PersistenceException e) {
            throw new PhaseHandlingException("Problem when updating project", e);
        } catch (ValidationException e) {
            throw new PhaseHandlingException("Problem when updating project", e);
        }
    }

    /**
     * Gets all the reviews for a given project.
     *
     * @param managerHelper the <code>ManagerHelper</code> instance.
     * @param projectId the id of the given project.
     * @param complete true if to retrieve complete review data structure, false otherwise.
     * @return all the reviews of the given project.
     * @throws PhaseHandlingException if there was an error during retrieval.
     */
    static Review[] searchReviewsForProject(ManagerHelper managerHelper, long projectId, boolean complete)
            throws PhaseHandlingException {
        Filter filter = new EqualToFilter("project", projectId);
        return managerHelper.getReviewManager().searchReviews(filter, complete);
    }

    /**
     * Retrieves all non-deleted submitters' submissions for the given project id.
     * @param uploadManager
     *            UploadManager instance to use for searching.
     * @param projectId
     *            project id.
     * @return all non-deleted submissions for the given project id.
     * @throws PhaseHandlingException
     *             if an error occurs during retrieval.
     */
    static Submission[] getNonDeletedProjectSubmitterSubmissions(UploadManager uploadManager, long projectId)
            throws PhaseHandlingException {
        try {
            Filter projectIdFilter = SubmissionFilterBuilder.createProjectIdFilter(projectId);
            Filter nonDeletedFilter = new NotFilter(SubmissionFilterBuilder.createSubmissionStatusIdFilter(
                    LookupHelper.getSubmissionStatus(uploadManager, Constants.SUBMISSION_STATUS_DELETED).getId()));
            Filter typeFilter = new OrFilter(
                    SubmissionFilterBuilder.createSubmissionTypeIdFilter(LookupHelper.getSubmissionType(
                            uploadManager, Constants.SUBMISSION_TYPE_CONTEST_SUBMISSION).getId()),
                    SubmissionFilterBuilder.createSubmissionTypeIdFilter(LookupHelper.getSubmissionType(
                            uploadManager, Constants.SUBMISSION_TYPE_CHECKPOINT_SUBMISSION).getId())
            );
            Filter fullFilter = new AndFilter(Arrays.asList(projectIdFilter, nonDeletedFilter, typeFilter));
            return uploadManager.searchSubmissions(fullFilter);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("There was a search builder error", e);
        }
    }
}
