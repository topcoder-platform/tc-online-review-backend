/*
 * Copyright (C) 2005 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.phase.handler.or;

import com.topcoder.onlinereview.component.grpcclient.phasehandler.PhaseHandlerServiceRpc;
import com.topcoder.onlinereview.component.project.management.PersistenceException;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectManager;
import com.topcoder.onlinereview.component.project.phase.ManagerHelper;
import com.topcoder.onlinereview.component.project.phase.Phase;
import com.topcoder.onlinereview.component.project.phase.PhaseHandlingException;
import com.topcoder.onlinereview.component.project.phase.handler.Constants;
import com.topcoder.onlinereview.component.project.phase.handler.EmailOptions;
import com.topcoder.onlinereview.component.project.phase.handler.EmailScheme;
import com.topcoder.onlinereview.component.project.phase.handler.FinalReviewPhaseHandler;
import com.topcoder.onlinereview.component.project.phase.handler.PhasesHelper;
import com.topcoder.onlinereview.component.review.Comment;
import com.topcoder.onlinereview.component.review.Review;

import java.util.List;

/**
 * The extend from FinalReviewPhaseHandler to add on the logic to push data to project_result.
 *
 * <p>
 * Thread-safety: This class is thread-safe as it does not contain any mutable inner state.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
public class PRFinalReviewPhaseHandler extends FinalReviewPhaseHandler {
    
    /**
    * Used for pulling data to project_result table and filling payments.
    */
    private final PRHelper prHelper = new PRHelper();
    
    /**
     * Create a new instance of FinalReviewPhaseHandler using the given namespace for loading configuration settings.
     *
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    public PRFinalReviewPhaseHandler(ManagerHelper managerHelper,
                                     List<EmailScheme> emailSchemes,
                                     EmailScheme reviewFeedbackEmailScheme,
                                     EmailOptions defaultStartEmailOption,
                                     EmailOptions defaultEndEmailOption,
                                     Long finalFixDuration,
                                     Long studioFinalFixDuration,
                                     String approvalPhaseDuration,
                                     String approvalPhaseDefaultReviewerNumber,
                                     String approvalPhaseDefaultScorecardID) {
        super(managerHelper, emailSchemes, reviewFeedbackEmailScheme, defaultStartEmailOption, defaultEndEmailOption,
                finalFixDuration, studioFinalFixDuration, approvalPhaseDuration, approvalPhaseDefaultReviewerNumber, approvalPhaseDefaultScorecardID);
    }

    /**
     * Provides additional logic to execute a phase. this extension will update placed, final_score field of
     * project_result table.</p>
     *
     * @param phase    The input phase to check.
     * @param operator The operator that execute the phase.
     * @throws PhaseHandlingException if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException if the input parameters is null or empty string.
     */
    public void perform(PhaseHandlerServiceRpc phaseHandlerServiceRpc, Phase phase, String operator) throws PhaseHandlingException {
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());
        super.perform(phaseHandlerServiceRpc, phase, operator);

        prHelper.processFinalReviewPR(phaseHandlerServiceRpc, phase.getProject().getId(), toStart, operator);

        // If stopping phase and final fix is approved.
        if (!toStart && !checkFinalReview(phase)) {
            // checks the existence of approval phase
            Phase approvalPhase = PhasesHelper.locatePhase(phase, "Approval", true, false);

            if (approvalPhase == null) {
                try {
                    // check "Approval Required" project property
                    ProjectManager projectManager = getManagerHelper().getProjectManager();
                    Project project = projectManager.getProject(phase.getProject()
                            .getId());

                    if (!"true".equalsIgnoreCase((String) project.getProperty("Approval Required"))) {
                        // update project status to Complete
                        PRHelper.completeProject(getManagerHelper(), phase, operator);
                    }
                } catch (PersistenceException e) {
                    throw new PhaseHandlingException("Problem when retrieving project", e);
                }
            }
        }
    }

    /**
     * This method is called from perform method when the phase is stopping. It
     * checks if the final review is rejected.
     *
     * @param phase phase instance.
     * @return if pass the final review of not
     *
     * @throws PhaseHandlingException if an error occurs when retrieving/saving
     *         data.
     */
    private boolean checkFinalReview(Phase phase) throws PhaseHandlingException {
        ManagerHelper managerHelper = getManagerHelper();
        Review finalWorksheet = PhasesHelper.getWorksheet(managerHelper, phase.getId());

        // check for approved/rejected comments.
        Comment[] comments = finalWorksheet.getAllComments();
        boolean rejected = false;

        for (Comment comment : comments) {
            String value = (String) comment.getExtraInfo();

            if (comment.getCommentType().getName().equals("Final Review Comment")) {
                if (Constants.COMMENT_VALUE_APPROVED.equalsIgnoreCase(value) || Constants.COMMENT_VALUE_ACCEPTED.equalsIgnoreCase(value)) {
                    continue;
                } else if (Constants.COMMENT_VALUE_REJECTED.equalsIgnoreCase(value)) {
                    rejected = true;

                    break;
                } else {
                    throw new PhaseHandlingException("Comment can either be Approved or Rejected.");
                }
            }
        }

        return rejected;
    }
}
