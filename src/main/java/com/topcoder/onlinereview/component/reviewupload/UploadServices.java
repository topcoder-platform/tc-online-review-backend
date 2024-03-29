/*
 * Copyright (C) 2007-2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.reviewupload;

import com.topcoder.onlinereview.component.deliverable.Submission;
import com.topcoder.onlinereview.component.deliverable.SubmissionFilterBuilder;
import com.topcoder.onlinereview.component.deliverable.SubmissionStatus;
import com.topcoder.onlinereview.component.deliverable.SubmissionType;
import com.topcoder.onlinereview.component.deliverable.Upload;
import com.topcoder.onlinereview.component.deliverable.UploadFilterBuilder;
import com.topcoder.onlinereview.component.deliverable.UploadManager;
import com.topcoder.onlinereview.component.deliverable.UploadPersistenceException;
import com.topcoder.onlinereview.component.deliverable.UploadStatus;
import com.topcoder.onlinereview.component.deliverable.UploadType;
import com.topcoder.onlinereview.component.external.ExternalUser;
import com.topcoder.onlinereview.component.external.RetrievalException;
import com.topcoder.onlinereview.component.external.UserRetrieval;
import com.topcoder.onlinereview.component.grpcclient.reviewupload.ReviewUploadServiceRpc;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.phase.Phase;
import com.topcoder.onlinereview.component.project.phase.PhaseManagementException;
import com.topcoder.onlinereview.component.project.phase.PhaseStatus;
import com.topcoder.onlinereview.component.resource.NotificationType;
import com.topcoder.onlinereview.component.resource.Resource;
import com.topcoder.onlinereview.component.resource.ResourceFilterBuilder;
import com.topcoder.onlinereview.component.resource.ResourceManager;
import com.topcoder.onlinereview.component.resource.ResourcePersistenceException;
import com.topcoder.onlinereview.component.resource.ResourceRole;
import com.topcoder.onlinereview.component.search.SearchBuilderException;
import com.topcoder.onlinereview.component.search.filter.AndFilter;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.component.search.filter.OrFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

/**
 * <p>
 * This is the default implementation of <code>UploadServices</code> interface. It manages different type of
 * upload. It used all managers from <code>ManagerProvider</code> to perform several operations. All the methods
 * are logged. It's possible to construct the instance through configuration and <code>ObjectFactory</code> and
 * set via constructor.
 * </p>
 * <p>
 * A sample configuration file that can be used is given below.
 *
 * <pre>
 *  &lt;Config name=&quot;com.cronos.onlinereview.services.uploads.impl.DefaultUploadServices&quot;&gt;
 *      &lt;Property name=&quot;objectFactoryNamespace&quot;&gt;
 *          &lt;Value&gt;myObjectFactory&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;managersProviderIdentifier&quot;&gt;
 *          &lt;Value&gt;managersProvider&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Config&gt;
 * </pre>
 * </p>
 *
 * <p>
 * Changes in version 1.1: Added
 * </p>
 *
 * <p>
 * Version 1.2 (Online Review Build From Sources) Change notes:
 *   <ol>
 *     <li>Removed dependency on Auto Screening.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Thread safety: the thread safety is completely relied to the managers implementations because it's impossible to
 * change the other variables.
 * </p>
 *
 * @author fabrizyo, saarixx, cyberjag, lmmortal
 * @version 1.1.3
 */
@Slf4j
@Component
public class UploadServices {

    @Autowired
    ReviewUploadServiceRpc reviewUploadServiceRpc;

    /**
     * This member variable is a string constant that defines the name of the configuration namespace which the
     * parameters for database connection factory are stored under.
     */
    public static final String DB_CONNECTION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * It contains all the managers used in this class. When you meet a Manager you must use the related getter
     * methods of this <code>ManagersProvider</code>. It is defined in constructor and cannot be
     * <code>null</code>.
     * </p>
     */
    @Autowired
    private ManagersProvider managersProvider;

    @Autowired
    private UserRetrieval userRetrieval;

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     * <p>
     * If the project allows multiple submissions for users, it will add the new submission and return. If multiple
     * submission are not allowed for the project firstly, it will add the new submission, secondly mark previous
     * submissions as deleted and then return.
     * </p>
     *
     * <p>
     * Changes in 1.1: Added a step for setting submissionType of the created submission.
     * </p>
     *
     * @param projectId the project's id
     * @param userId    the user's id
     * @param filename  the file name to use
     *
     * @return the id of the new submission
     *
     * @throws PersistenceException         if some error occurs in persistence layer
     * @throws UploadServicesException      if some other exception occurs in the process (wrap it)
     * @throws InvalidProjectException      if the project does not exist
     * @throws InvalidProjectPhaseException if neither Submission or Screening phase are opened
     * @throws InvalidUserException         if the user does not exist or has not the submitter role
     * @throws IllegalArgumentException     if any id is &lt; 0, if filename is <code>null</code> or trim to empty
     * @since 1.0
     */
    public long uploadSubmission(long projectId, long userId, String filename) throws UploadServicesException {
        Helper.logFormat(log, "Entered DefaultUploadServices#uploadSubmission(long, long, String)");
        Helper.checkId(projectId, "projectId", log);
        Helper.checkId(userId, "userId", log);
        Helper.checkString(filename, "filename", log);

        // check if the project exists
        Project project = getProject(projectId);

        // check that the user exists and has the submitter role
        Resource resource = null;
        try {
            resource = getResource(projectId, userId, new String[]{"Submitter"});
        } catch (InvalidUserException e) {
            Helper.logFormat(log, "Creating submitter role for user: {0} in project {1}",
                    new Object[]{userId, projectId});
            try {
                resource = managersProvider.getResourceManager().getResource(addSubmitter(projectId, userId));
            } catch (ResourcePersistenceException e1) {
                Helper.logFormat(log, e, "Failed to create submitter for user: {0} and project {1}.",
                        new Object[]{userId, projectId});
                throw new UploadServicesException("Failed to create submitter for user: " + userId + " and project "
                        + projectId + ".", e);
            }
        }

        try {
            com.topcoder.onlinereview.component.project.phase.Project projectPhases = managersProvider.getPhaseManager().getPhases(
                    projectId);
            Phase[] phases = projectPhases.getAllPhases();
            // iterate over the phases to find if the type is "Submission" or "Screening"
            for (Phase phase : phases) {
                if (phase.getPhaseType() != null) {
                    Helper.logFormat(log, "Looping through phase {0} of the project.",
                            new Object[]{phase.getPhaseType().getName()});
                }
                if (phase.getPhaseType() != null
                        && ("Submission".equals(phase.getPhaseType().getName()) || "Screening".equals(phase
                        .getPhaseType().getName()))) {
                    Helper.logFormat(log, "Current status for the phase {0} is {1} of the project.",
                            new Object[]{phase.getPhaseType().getName(), phase.getPhaseStatus().getName()});
                    // check if submission or screening phase are open checking its the status
                    if (PhaseStatus.OPEN.getName().equals(phase.getPhaseStatus().getName())) {
                        // create a new Submission
                        Submission submission = new Submission();

                        Helper.logFormat(log,
                                "Current status for the phase {0} is {1} of the project.", new Object[]{
                                        phase.getPhaseType().getName(), phase.getPhaseStatus().getName()});

                        // gets the SubmissionStatus with name "Active" and set to submission
                        submission.setSubmissionStatus(getSubmissionStatusByName("Active"));

                        // Added in version 1.1
                        // gets the SubmissionType with name "Contest Submission" and sets it to created submission
                        submission.setSubmissionType(getSubmissionTypeByName("Contest Submission"));

                        Upload upload = createUpload(projectId, resource.getId(), filename, "Submission");
                        Helper.logFormat(log,
                                "Upload created for the  projectId {0}, userId {1} with filename {2}.",
                                new Object[]{projectId, userId, filename});

                        String operator = String.valueOf(userId);
                        // persist the upload
                        managersProvider.getUploadManager().createUpload(upload, operator);

                        Helper.logFormat(log,
                                "Created submission Upload for project {0}, user {1} with file name {2}.",
                                new Object[]{projectId, userId, filename});

                        // set the upload.
                        submission.setUpload(upload);

                        // persist the submission with uploadManager.createSubmission with the useId as
                        // operator
                        managersProvider.getUploadManager().createSubmission(submission, operator);

                        Helper.logFormat(log, "Created submission for project {0}, user {1}.",
                                new Object[]{projectId, userId});

                        // associate the submission with the submitter resource
                        resource.addSubmission(submission.getId());

                        Helper.logFormat(log, "Added submission {0} to resource.",
                                new Object[]{submission.getId()});

                        // Persist the resource using ResourceManager#updateResource
                        managersProvider.getResourceManager().updateResource(resource, operator);

                        Helper.logFormat(log, "Updated resource using the operator {0}.",
                                new Object[]{operator});

                        // If the project DOESN'T allow multiple submissions hence its property "Allow
                        // multiple submissions" will be false
                        Boolean allow = Boolean.parseBoolean((String) project
                                .getProperty("Allow multiple submissions"));

                        if (!allow) {
                            deletePreviousSubmissions(userId, resource);
                            Helper.logFormat(log,
                                    "Marked previous submissions for deletion for the user {0}.",
                                    new Object[]{userId});
                        }

                        return submission.getId();
                    }

                } // end of Submission or Screening
            } // end of for loop
            Helper.logFormat(log, "Failed to upload submission for the projectId {0}, userId {1}",
                    new Object[]{project.getId(), userId});
            throw new InvalidProjectException("Failed to upload submission for the project", project.getId());
        } catch (PhaseManagementException e) {
            Helper.logFormat(log, e, "Failed to upload submission for user {0} and project {1}.",
                    new Object[]{userId, projectId});
            throw new UploadServicesException("Failed to upload submission for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (UploadPersistenceException e) {
            Helper.logFormat(log, e, "Failed to upload submission for user {0} and project {1}.",
                    new Object[]{userId, projectId});
            throw new PersistenceException("Failed to upload submission for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (ResourcePersistenceException e) {
            Helper.logFormat(log, e, "Failed to upload submission for user {0} and project {1}.",
                    new Object[]{userId, projectId});
            throw new UploadServicesException("Failed to upload submission for user " + userId + " and project "
                    + projectId + ".", e);
        } finally {
            Helper
                    .logFormat(log,
                            "Exited DefaultUploadServices#uploadSubmission(long, long, String)");
        }
    }

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     *
     * @param projectId the project's id
     * @param userId    the user's id
     * @param filename  the file name to use
     *
     * @return the id of the created final fix submission.
     *
     * @throws InvalidProjectException      if the project does not exist
     * @throws InvalidProjectPhaseException if Final Fix phase is not opened
     * @throws InvalidUserException         if the user does not exist or she/he is not winner submitter
     * @throws PersistenceException         if some error occurs in persistence layer
     * @throws UploadServicesException      if some other exception occurs in the process (wrap it)
     * @throws IllegalArgumentException     if any id is &lt; 0, if filename is <code>null</code> or trim to empty
     * @since 1.0
     */
    public long uploadFinalFix(long projectId, long userId, String filename) throws UploadServicesException {
        Helper.logFormat(log, "Entered DefaultUploadServices#uploadFinalFix(long, long, String)");
        Helper.checkId(projectId, "projectId", log);
        Helper.checkId(userId, "userId", log);
        Helper.checkString(filename, "filename", log);

        // check if the project exists
        Project project = getProject(projectId);

        // check that the user exists and has the submitter role
        Resource resource = getResource(projectId, userId, new String[]{"Submitter"});

        // Check that the user is the winner
        // modified in version 1.1
        Long winnerId;
        try {
            winnerId = getProjectLongValue(project, "Winner External Reference ID");

            if (winnerId != userId) {
                throw new InvalidUserException("The given user is not the winner", userId);
            }
        } catch (NumberFormatException exc) {
            Helper.logFormat(log, exc, "Can not parse property 'Winner External Reference ID'" +
                    " for user {0} and project {1}.",
                    new Object[]{userId, projectId});

            throw new UploadServicesException("Can not parse property 'Winner External Reference ID' for user "
                    + userId + " and project " + projectId + ".", exc);
        }

        try {
            com.topcoder.onlinereview.component.project.phase.Project projectPhases = managersProvider.getPhaseManager().getPhases(
                    projectId);
            Phase[] phases = projectPhases.getAllPhases();
            // iterate over the phases to find if the type is "Final Fix"
            for (Phase phase : phases) {
                if (phase.getPhaseType() != null && ("Final Fix".equals(phase.getPhaseType().getName()))) {
                    // check if final fix is open checking its the status
                    if (PhaseStatus.OPEN.getName().equals(phase.getPhaseStatus().getName())) {

                        // create a new Upload
                        Upload upload = createUpload(projectId, resource.getId(), filename, "Final Fix");

                        String operator = String.valueOf(userId);

                        UploadManager uploadManager = managersProvider.getUploadManager();

                        // persist the upload
                        uploadManager.createUpload(upload, operator);

                        Helper.logFormat(log,
                                "Created final fix Upload for project {0}, user {1} with file name {2}.",
                                new Object[]{projectId, userId, filename});

                        // delete previous final fixes
                        Filter filterProject = UploadFilterBuilder.createProjectIdFilter(project.getId());
                        Filter filterResource = UploadFilterBuilder.createResourceIdFilter(resource.getId());
                        Filter filterType = UploadFilterBuilder.createUploadTypeIdFilter(upload.getUploadType()
                                .getId());

                        Filter filter = new AndFilter(Arrays.asList(new Filter[]{filterProject, filterResource,
                                filterType}));

                        Upload[] uploads = uploadManager.searchUploads(filter);
                        Upload oldUpload = (uploads.length != 0) ? uploads[uploads.length - 1] : null;
                        if (oldUpload != null) {
                            oldUpload.setUploadStatus(getUploadStatusByName("Deleted"));
                            uploadManager.updateUpload(oldUpload, operator);
                        }

                        Helper.logFormat(log,
                                "Marked previous final fixes for deletion for the user {0}.",
                                new Object[]{userId});

                        return upload.getId();

                    }
                    Helper.logFormat(log,
                            "The 'Final Fix' phase is not OPEN for phaseId {0}, userId {1}", new Object[]{
                                    phase.getId(), userId});
                    throw new InvalidProjectPhaseException("The 'Final Fix' phase is not OPEN", phase.getId());
                } // end of if Final Fix

            } // end of for loop
            Helper.logFormat(log, "Failed to upload final fix for the projectId {0}",
                    new Object[]{project.getId()});
            throw new InvalidProjectException("Failed to upload final fix for the project", project.getId());
        } catch (PhaseManagementException e) {
            Helper.logFormat(log, e, "Failed to upload final fix for user {0} and project {1}.",
                    new Object[]{userId, projectId});
            throw new UploadServicesException("Failed to upload final fix for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (UploadPersistenceException e) {
            Helper.logFormat(log, e, "Failed to upload final fix for user {0} and project {1}.",
                    new Object[]{userId, projectId});
            throw new PersistenceException("Failed to upload final fix for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (SearchBuilderException e) {
            Helper.logFormat(log, e, "Failed to upload final fix for user {0} and project {1}.",
                    new Object[]{userId, projectId});
            throw new PersistenceException("Failed to upload final fix for user " + userId + " and project "
                    + projectId + ".", e);
        } finally {
            Helper.logFormat(log, "Exited DefaultUploadServices#uploadFinalFix(long, long, String)");
        }
    }

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     *
     * @param projectId the project's id
     * @param userId    the user's id
     * @param filename  the file name to use
     *
     * @return the id of the created test cases submission
     *
     * @throws InvalidProjectException  if the project does not exist
     * @throws InvalidUserException     if the user does not exist or has not the reviewer role
     * @throws PersistenceException     if some error occurs in persistence layer
     * @throws UploadServicesException  if some other exception occurs in the process (wrap it)
     * @throws IllegalArgumentException if any id is &lt; 0, if filename is <code>null</code> or trim to empty
     * @since 1.0
     */
    public long uploadTestCases(long projectId, long userId, String filename) throws UploadServicesException {
        Helper.logFormat(log, "Entered DefaultUploadServices#uploadTestCases(long, long, String)");
        Helper.checkId(projectId, "projectId", log);
        Helper.checkId(userId, "userId", log);
        Helper.checkString(filename, "filename", log);

        // check if the project exists
        Project project = getProject(projectId);

        // check that the user exists and has the reviewer role
        Resource resource = getResource(projectId, userId, new String[]{"Accuracy Reviewer", "Failure Reviewer",
                "Stress Reviewer"});

        try {
            // check that the Review phase is open for the project
            com.topcoder.onlinereview.component.project.phase.Project projectPhases = managersProvider.getPhaseManager().getPhases(
                    projectId);

            Phase[] phases = projectPhases.getAllPhases();
            // iterate over the phases to find if the type is "Review"
            for (Phase phase : phases) {
                if (phase.getPhaseType() != null && ("Review".equals(phase.getPhaseType().getName()))) {
                    // check if final fix is open checking its the status
                    if (PhaseStatus.OPEN.getName().equals(phase.getPhaseStatus().getName())) {
                        // create a new Upload
                        Upload upload = createUpload(projectId, resource.getId(), filename, "Test Case");

                        String operator = String.valueOf(userId);
                        // persist the upload
                        managersProvider.getUploadManager().createUpload(upload, operator);
                        Helper.logFormat(log,
                                "Created test cases Upload for project {0}, user {1} with file name {2}.",
                                new Object[]{projectId, userId, filename});

                        // delete the previous submissions
                        Filter filterProject = UploadFilterBuilder.createProjectIdFilter(project.getId());
                        Filter filterResource = UploadFilterBuilder.createResourceIdFilter(resource.getId());
                        Filter filterStatus = UploadFilterBuilder
                                .createUploadStatusIdFilter(getUploadStatusByName("Active").getId());
                        Filter filterType = UploadFilterBuilder.createUploadTypeIdFilter(upload.getUploadType()
                                .getId());

                        Filter filter = new AndFilter(Arrays.asList(new Filter[]{filterProject, filterResource,
                                filterStatus, filterType}));

                        Upload[] uploads = managersProvider.getUploadManager().searchUploads(filter);
                        Upload oldUpload = (uploads.length != 0) ? uploads[0] : null;

                        if (oldUpload != null) {
                            oldUpload.setUploadStatus(getUploadStatusByName("Deleted"));
                            managersProvider.getUploadManager().updateUpload(oldUpload, operator);
                        }

                        Helper
                                .logFormat(log,
                                        "Marked previous test cases for deletion for the user {0}.",
                                        new Object[]{userId});

                        return upload.getId();
                    }

                    Helper.logFormat(log, "The 'Review' phase is not OPEN for phaseId {0}",
                            new Object[]{phase.getId()});
                    throw new InvalidProjectPhaseException("The 'Review' phase is not OPEN", phase.getId());
                } // end of if Review

            } // end of for loop
            Helper.logFormat(log, "Failed to upload test case for the projectId {0}",
                    new Object[]{project.getId()});
            throw new InvalidProjectException("Failed to upload test case for the project", project.getId());
        } catch (PhaseManagementException e) {
            Helper.logFormat(log, e, "Failed to upload testcases for user {0} and project {1}.",
                    new Object[]{userId, projectId});
            throw new UploadServicesException("Failed to upload testcases for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (UploadPersistenceException e) {
            Helper.logFormat(log, e, "Failed to upload testcases for user {0} and project {1}.",
                    new Object[]{userId, projectId});
            throw new PersistenceException("Failed to upload testcases for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (SearchBuilderException e) {
            Helper.logFormat(log, e, "Failed to upload testcases for user {0} and project {1}.",
                    new Object[]{userId, projectId});
            throw new PersistenceException("Failed to upload testcases for user " + userId + " and project "
                    + projectId + ".", e);
        } finally {
            Helper.logFormat(log, "Exited DefaultUploadServices#uploadTestCases(long, long, String)");
        }
    }

    /**
     * <p>Adds a specification submission for a user in a particular project.</p>
     *
     * @param projectId the project's id
     * @param userId    the user's id.
     * @param filename  the file name to use.
     *
     * @return the id of the new submission.
     *
     * @throws IllegalArgumentException     if any id is < 0, if filename is null or trim to empty
     * @throws InvalidProjectException      if the project doesn't exist.
     * @throws InvalidProjectPhaseException if Specification Submission phase is not opened.
     * @throws InvalidUserException         if the user doesn't exist or hasn't the Specification Submitter role.
     * @throws PersistenceException         if some error occurs in persistence layer
     * @throws UploadServicesException      if some other exception occurs in the process (wrap it)
     * @since 1.1
     */
    public long uploadSpecification(long projectId, long userId, String filename) throws UploadServicesException {
        // log method entry
        Helper.logFormat(log, "Entered DefaultUploadServices#uploadSpecification(long, long, String)");

        // validate input
        Helper.checkId(projectId, "projectId", log);
        Helper.checkId(userId, "userId", log);
        Helper.checkString(filename, "filename", log);

        // get the project by it's id
        Project project = getProject(projectId);

        // check that the user exists and has the Specification submitter role
        // it may throw InvalidUserException
        Resource resource = getResource(projectId, userId, new String[]{"Specification Submitter"});

        try {
            // get all project phases
            com.topcoder.onlinereview.component.project.phase.Project projectPhases = managersProvider.getPhaseManager().getPhases(
                    projectId);
            Phase[] phases = projectPhases.getAllPhases();

            // iterate over the phases to find if the "Specification Submission" phase
            for (Phase phase : phases) {
                if (phase.getPhaseType() != null
                        && ("Specification Submission".equals(phase.getPhaseType().getName()))) {

                    Helper.logFormat(log, "Current status for the phase {0} is {1} of the project.",
                            new Object[]{phase.getPhaseType().getName(), phase.getPhaseStatus().getName()});

                    // check if specification submission phase is open
                    if (PhaseStatus.OPEN.getName().equals(phase.getPhaseStatus().getName())) {
                        // create a new Submission
                        Submission submission = new Submission();

                        Helper.logFormat(log,
                                "Current status for the phase {0} is {1} of the project.", new Object[]{
                                        phase.getPhaseType().getName(), phase.getPhaseStatus().getName()});

                        // gets the SubmissionStatus with name "Active" and sets it to submission
                        submission.setSubmissionStatus(getSubmissionStatusByName("Active"));

                        // gets the SubmissionType with name "Specification Submission" and sets it to
                        // submission
                        submission.setSubmissionType(getSubmissionTypeByName("Specification Submission"));

                        Upload upload = createUpload(projectId, resource.getId(), filename, "Submission");
                        Helper.logFormat(log,
                                "Upload created for the  projectId {0}, userId {1} with filename {2}.",
                                new Object[]{projectId, userId, filename});

                        String operator = String.valueOf(userId);
                        // persist the upload
                        managersProvider.getUploadManager().createUpload(upload, operator);

                        Helper.logFormat(log,
                                "Created specification Upload for project {0}, user {1} with file name {2}.",
                                new Object[]{projectId, userId, filename});

                        // set the upload.
                        submission.setUpload(upload);

                        // persist the submission with uploadManager.createSubmission with the useId as
                        // operator
                        managersProvider.getUploadManager().createSubmission(submission, operator);

                        Helper.logFormat(log, "Created specification for project {0}, user {1}.",
                                new Object[]{projectId, userId});

                        // associate the submission with the submitter resource
                        resource.addSubmission(submission.getId());

                        Helper.logFormat(log, "Added specification {0} to resource.",
                                new Object[]{submission.getId()});

                        // persist the resource using ResourceManager#updateResource
                        managersProvider.getResourceManager().updateResource(resource, operator);

                        Helper.logFormat(log, "Updated resource using the operator {0}.",
                                new Object[]{operator});

                        return submission.getId();
                    }
                    // throw exception if phase 'Specification Submission' is not opened
                    Helper.logFormat(log,
                            "The 'Specification Submission' phase is not OPEN for phaseId {0}, userId {1}",
                            new Object[]{phase.getId(), userId});
                    throw new InvalidProjectPhaseException("The 'Specification Submission' phase is not OPEN",
                            phase.getId());
                }
                // end of Specification Submission
            } // end of for loop
            Helper.logFormat(log, "Failed to upload specification for the projectId {0}, userId {1}",
                    new Object[]{project.getId(), userId});
            throw new InvalidProjectException("Failed to upload specification for the project", project.getId());
        } catch (PhaseManagementException e) {
            Helper.logFormat(log, e, "Failed to upload specification for user {0} and project {1}.",
                    new Object[]{userId, projectId});
            throw new UploadServicesException("Failed to upload specification for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (UploadPersistenceException e) {
            Helper.logFormat(log, e, "Failed to upload specification for user {0} and project {1}.",
                    new Object[]{userId, projectId});
            throw new PersistenceException("Failed to upload specification for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (ResourcePersistenceException e) {
            Helper.logFormat(log, e, "Failed to upload specification for user {0} and project {1}.",
                    new Object[]{userId, projectId});
            throw new UploadServicesException("Failed to upload specification for user " + userId + " and project "
                    + projectId + ".", e);
        } finally {
            Helper.logFormat(log,
                    "Exited DefaultUploadServices#uploadSpecification(long, long, String)");
        }
    }

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     *
     * @param submissionId       the submission's id
     * @param submissionStatusId the submission status id
     * @param operator           the operator which execute the operation
     *
     * @throws InvalidSubmissionException if the submission does not exist
     * @throws InvalidSubmissionStatusException
     *                                    if the submission status does not exist
     * @throws PersistenceException       if some error occurs in persistence layer
     * @throws IllegalArgumentException   if any id is &lt; 0 or if operator is null or trim to empty
     * @since 1.0
     */
    public void setSubmissionStatus(long submissionId, long submissionStatusId, String operator)
            throws InvalidSubmissionException, InvalidSubmissionStatusException, PersistenceException {
        Helper.logFormat(log, "Entered DefaultUploadServices#"
                + "setSubmissionStatus(long, long, String)");
        Helper.checkId(submissionId, "submissionId", log);
        Helper.checkId(submissionStatusId, "submissionStatusId", log);
        Helper.checkString(operator, "operator", log);

        try {
            Submission submission = managersProvider.getUploadManager().getSubmission(submissionId);
            if (submission == null) {
                Helper.logFormat(log, "Failed to get submission with the given Id {0}",
                        new Object[]{submissionId});
                throw new InvalidSubmissionException("Failed to get submission with the given Id", submissionId);
            }

            SubmissionStatus[] submissionStatus = managersProvider.getUploadManager().getAllSubmissionStatuses();
            // iterate over statuses and check which status has the submissionStatusId defined
            for (SubmissionStatus status : submissionStatus) {
                if (status.getId() == submissionStatusId) {
                    submission.setSubmissionStatus(status);
                    managersProvider.getUploadManager().updateSubmission(submission, operator);
                    Helper.logFormat(log, "Updated submission {0} using operator {1}.", new Object[]{
                            submission.getId(), operator});
                    return;
                }
            }

            Helper.logFormat(log, "Failed to get submission status with the given id {0}",
                    new Object[]{submissionStatusId});
            throw new InvalidSubmissionStatusException("Failed to get submission status with the given id",
                    submissionStatusId);
        } catch (UploadPersistenceException e) {
            Helper.logFormat(log, e,
                    "Failed to get the submission from persistence, submissionId - {0}, submissionStatusId - {1}",
                    new Object[]{submissionId, submissionStatusId});
            throw new PersistenceException("Failed to get the submission from the persistence", e);
        } finally {
            Helper.logFormat(log,
                    "Exited DefaultUploadServices#setSubmissionStatus(long, long, String)");
        }
    }

    /**
     * Gets the <code>Project</code> from the persistence.
     *
     * @param projectId the project id to use
     *
     * @return the <code>Project</code>
     *
     * @throws PersistenceException    if some error occurs in persistence layer
     * @throws InvalidProjectException if the project does not exist
     * @since 1.0
     */
    private Project getProject(long projectId) throws PersistenceException, InvalidProjectException {
        Project project;
        try {
            project = managersProvider.getProjectManager().getProject(projectId);
        } catch (PersistenceException e) {
            Helper.logFormat(log, e, "Failed to get the project with Id {0}",
                    new Object[]{projectId});
            throw new PersistenceException("Failed to get the project with Id " + projectId + ".", e);
        }
        if (project == null) {
            Helper.logFormat(log, "Project does not exist - {0}", new Object[]{projectId});
            throw new InvalidProjectException("Project does not exist", projectId);
        }
        return project;
    }

    /**
     * Gets the resource role id for the given role.
     *
     * @param roles the roles to use
     *
     * @return the resource role id or zero
     *
     * @throws UploadServicesException if any while executing.
     * @since 1.0
     */
    private Long[] getSubmitterRoleId(String[] roles) throws UploadServicesException {
        ResourceManager manager = managersProvider.getResourceManager();
        ResourceRole[] resourceRoles;
        try {
            resourceRoles = manager.getAllResourceRoles();
        } catch (ResourcePersistenceException e) {
            throw new UploadServicesException(e.getMessage(), e);
        }
        List<Long> resourceRolesIds = new ArrayList<Long>();
        for (ResourceRole resourceRole : resourceRoles) {
            for (String role : roles) {
                if (role.equals(resourceRole.getName())) {
                    // if matched return the resourceRoleId
                    resourceRolesIds.add(resourceRole.getId());
                }
            }
        }
        return resourceRolesIds.toArray(new Long[resourceRolesIds.size()]);
    }

    /**
     * Gets the <code>Resource</code> associated with the project and user with the given role.
     *
     * @param projectId the project id to use
     * @param userId    the user's id
     * @param roles     the roles for filtering
     *
     * @return the <code>Resource</code>
     *
     * @throws UploadServicesException if any error occurs
     * @since 1.0
     */
    private Resource getResource(long projectId, long userId, String[] roles) throws UploadServicesException {

        ResourceManager manager = managersProvider.getResourceManager();
        Long[] submitterRoleIds = getSubmitterRoleId(roles);
        if (submitterRoleIds.length == 0) {
            Helper.logFormat(log, "There is no submitterRoleId for the given userId {0}",
                    new Object[]{userId});
            throw new InvalidUserException("There is no submitterRoleId for the given user", userId);
        }

        Filter[] filters = new Filter[submitterRoleIds.length];

        for (int i = 0; i < filters.length; i++) {
            filters[i] = ResourceFilterBuilder.createResourceRoleIdFilter(submitterRoleIds[i]);
        }

        Filter submitterRoleIdFilter = new OrFilter(Arrays.asList(filters));

        // create the filter for searching resources
        AndFilter filter = new AndFilter(Arrays.asList(new Filter[]{
                submitterRoleIdFilter,
                ResourceFilterBuilder.createProjectIdFilter(projectId),
                ResourceFilterBuilder.createUserIdFilter(userId)}));

        Resource[] resources;
        try {
            resources = manager.searchResources(filter);
        } catch (SearchBuilderException e) {
            Helper.logFormat(log, e,
                    "SearchBuilderException : There is no resource for the given userId {0}", userId);
            throw new InvalidUserException("SearchBuilderException : There is no resource for the given user", userId);
        } catch (ResourcePersistenceException e) {
            Helper.logFormat(log, e,
                    "ResourcePersistenceException : Problem retrieving the resource for the given userId {0}",
                    userId);
            throw new InvalidUserException("Problem retrieving the resource for the given user", userId);
        }

        if (resources.length != 1) {
            Helper.logFormat(log, "There is no resource for the given userId {0}, resources: {1}",
                    new Object[]{userId, Arrays.asList(resources)});
            throw new InvalidUserException("There is no resource for the given user", userId);
        }

        return resources[0];
    }

    /**
     * Gets the upload status by name.
     *
     * @param name the name of the status.
     *
     * @return the UploadStatus for the given name
     *
     * @throws UploadPersistenceException if any error occurs.
     * @since 1.0
     */
    private UploadStatus getUploadStatusByName(String name) throws UploadPersistenceException {
        UploadStatus[] uploadStatus = managersProvider.getUploadManager().getAllUploadStatuses();
        for (UploadStatus status : uploadStatus) {
            if (name.equals(status.getName())) {
                return status;
            }
        }
        return null;
    }

    /**
     * <p>Gets submission status by it's name.</p>
     *
     * @param submissionStatus name of the submission status to get
     *
     * @return the SubmissionStatus with given name
     *
     * @throws UploadPersistenceException if any error occurs
     * @since 1.1
     */
    private SubmissionStatus getSubmissionStatusByName(String submissionStatus) throws
            UploadPersistenceException {
        SubmissionStatus[] submissionStatuses = managersProvider.getUploadManager()
                .getAllSubmissionStatuses();

        for (SubmissionStatus status : submissionStatuses) {
            if (submissionStatus.equals(status.getName())) {
                return status;
            }
        }

        // return null if there were no matching submission status
        return null;
    }

    /**
     * <p>Gets submission type by it's name.</p>
     *
     * @param submissionType name of the submission type to get
     *
     * @return the SubmissionType with given name
     *
     * @throws UploadPersistenceException if any error occurs
     * @since 1.1
     */
    private SubmissionType getSubmissionTypeByName(String submissionType) throws UploadPersistenceException {
        SubmissionType[] submissionTypes = managersProvider.getUploadManager()
                .getAllSubmissionTypes();

        for (SubmissionType type : submissionTypes) {
            if (submissionType.equals(type.getName())) {
                return type;
            }
        }

        // return null if there were no matching submission type
        return null;
    }

    /**
     * Creates the <code>Upload</code> and set the required attributes.
     *
     * @param projectId  the project id to use
     * @param resourceId the user's id
     * @param filename   the filename to set as parameter
     * @param uploadType the type of upload
     *
     * @return the created <code>Upload</code> instance
     *
     * @throws PersistenceException if some error occurs in persistence layer
     * @since 1.0
     */
    private Upload createUpload(long projectId, long resourceId, String filename, String uploadType)
            throws PersistenceException {
        // create a new Upload
        Upload upload = new Upload();

        // iterate over all UploadStatuses, get the UploadStatus with name "Active" and set to Upload
        try {
            UploadStatus[] uploadStatus = managersProvider.getUploadManager().getAllUploadStatuses();
            for (UploadStatus status : uploadStatus) {
                if ("Active".equals(status.getName())) {
                    upload.setUploadStatus(status);
                    break;
                }
            }

            // iterate over all UploadTypes, get the UploadType with name uploadType and set to Upload
            UploadType[] uploadTypes = managersProvider.getUploadManager().getAllUploadTypes();
            for (UploadType type : uploadTypes) {
                if (uploadType.equals(type.getName())) {
                    upload.setUploadType(type);
                    break;
                }
            }

            // set the owner as userId
            upload.setOwner(resourceId);

            // set the projectId
            upload.setProject(projectId);

            // file name have to be passed
            upload.setParameter(filename);

            return upload;

        } catch (UploadPersistenceException e) {
            Helper.logFormat(log, e,
                    "Failed to create Upload properly with projectId {0} and userId {1}", new Object[]{projectId,
                            resourceId});
            throw new PersistenceException("Failed to create upload properly.", e);
        }

    }

    /**
     * Deletes the previous submissions for the given user.
     *
     * @param userId           the user's id
     * @param resource         the resource to create the filter
     *
     * @throws PersistenceException       if some error occurs in persistence layer
     * @throws InvalidSubmissionException if the submission does not exist
     * @throws InvalidSubmissionStatusException
     *                                    if the submission status does not exist
     * @throws UploadServicesException    if some other exception occurs in the process (wrap it)
     *
     * @version 1.1
     * @since 1.0
     */
    private void deletePreviousSubmissions(long userId, Resource resource)
            throws UploadServicesException {
        try {
            // Change previous submissions status to "Deleted"
            Filter prevSubFilter = SubmissionFilterBuilder.createResourceIdFilter(resource.getId());

            Submission[] prevSubmissions = managersProvider.getUploadManager().searchSubmissions(prevSubFilter);

            // set the statuses of these submission to "Deleted"
            long delSubStatusId = 0;

            SubmissionStatus submissionStatus = getSubmissionStatusByName("Deleted");
            if(submissionStatus != null) {
                delSubStatusId = submissionStatus.getId();
            }

            String operator = String.valueOf(userId);
            for (Submission prevSubmission : prevSubmissions) {
                // set delSubStatusId to submissions using the method in this class
                setSubmissionStatus(prevSubmission.getId(), delSubStatusId, operator);

                // persist the submissions
                managersProvider.getUploadManager().updateSubmission(prevSubmission, operator);
            }
        } catch (UploadPersistenceException e) {
            Helper.logFormat(log, e, "Failed to delete previous submissions for userId {0}",
                    new Object[]{userId});
            throw new PersistenceException("Failed to delete previous submissions.", e);
        } catch (SearchBuilderException e) {
            Helper.logFormat(log, e, "Failed to delete previous submissions for userId {0}",
                    new Object[]{userId});
            throw new UploadServicesException("Failed to delete previous submissions.", e);
        }

    }

    /**
     * Adds the given user as a new submitter to the given project id. If the user is already added returns the the
     * id.
     *
     * @param projectId the project to which the user needs to be added
     * @param userId    the user to be added
     *
     * @return the added resource id
     *
     * @throws InvalidProjectException      if the project id is unknown
     * @throws InvalidUserException         if the user id is unknown
     * @throws InvalidProjectPhaseException if the phase of the project is not Registration.
     * @throws UploadServicesException      if any error occurs from UploadServices
     * @throws IllegalArgumentException     if any id is &lt; 0
     * @since 1.0
     */
    public long addSubmitter(long projectId, long userId) throws InvalidProjectException, UploadServicesException,
            InvalidUserException, InvalidProjectPhaseException {
        Helper.logFormat(log, "Entered DefaultUploadServices#addSubmitter(long, long)");
        Helper.checkId(projectId, "projectId", log);
        Helper.checkId(userId, "userId", log);
        Resource resource = null;
        try {
            resource = getResource(projectId, userId, new String[]{"Submitter"});
        } catch (InvalidUserException e) {
            // ignore
        }
        if (resource != null) {
            return resource.getId();
        }
        try {
            Project project = managersProvider.getProjectManager().getProject(projectId);
            log.debug("Project successfully retrieved for the project id : " + projectId);
            // Obtain the instance of the Resource Manager
            ResourceManager resourceManager = managersProvider.getResourceManager();

            // Get info about user with the specified userId
            ExternalUser user = userRetrieval.retrieveUser(userId);
            log.debug("User information successfully retrieved for the user id : " + userId);
            // If there is no user with such handle, indicate an error
            if (user == null) {
                Helper.logFormat(log, "Failed to get the user details for the userId {0}",
                        new Object[]{userId});
                throw new InvalidUserException("The user id  is not found.", userId);
            }
            // Get all types of resource roles
            ResourceRole[] resourceRoles = resourceManager.getAllResourceRoles();

            ResourceRole submitterRole = null;
            for (int i = 0; i < resourceRoles.length && submitterRole == null; i++) {
                ResourceRole role = resourceRoles[i];
                if ("Submitter".equals(role.getName())) {
                    submitterRole = role;
                }
            }

            // Get all types of notifications
            NotificationType[] types = resourceManager.getAllNotificationTypes();
            long timelineNotificationId = Long.MIN_VALUE;

            // get the id for the timelineNotification
            for (int i = 0; i < types.length; ++i) {
                if (types[i].getName().equals("Timeline Notification")) {
                    timelineNotificationId = types[i].getId();
                    break;
                }
            }

            // HashSet used to identify resource of new user
            Set<Long> newSubmitters = new HashSet<Long>();
            resource = new Resource();

            // Set resource properties
            resource.setProject(new Long(project.getId()));
            resource.setResourceRole(submitterRole);
            resource.setUserId(userId);
            resource.setProperty("Handle", user.getHandle());
            resource.setProperty("Payment", null);
            resource.setProperty("Payment Status", "No");
            // Set resource properties copied from external user
            resource.setProperty("External Reference ID", Long.toString(userId));

            resource.setProperty("Registration Date", new Date().toString());

            // Save the resource in the persistence level
            resourceManager.updateResource(resource, Long.toString(userId));
            log.debug("Resource successfully persisted into" + " to the DB with the id : "
                    + resource.getId());
            newSubmitters.add(new Long(user.getId()));

            // Populate project_result and component_inquiry for new submitters
            populateProjectResult(project, newSubmitters);

            // Update all the time line notifications if the timelineNotificationId is retrieved properly
            if ("On".equals(project.getProperty("Timeline Notification"))
                    && timelineNotificationId != Long.MIN_VALUE) {
                resourceManager.addNotifications(new Long[]{userId}, project.getId(), timelineNotificationId,
                        Long.toString(userId));
            }
            return resource.getId();
        } catch (PersistenceException e) {
            Helper.logFormat(log, e, "Failed to get the project for the projectId {0}",
                    new Object[]{projectId});
            throw new InvalidProjectException("Exception occurred while getting the project.", e, projectId);
        } catch (ResourcePersistenceException e) {
            Helper.logFormat(log, e, "ResourcePersistenceException occurred while "
                    + "creating the resource for userId : {0} and projectId : {1}.", new Object[]{userId,
                    projectId});
            throw new UploadServicesException(
                    "ResourcePersistenceException occurred while creating the resource.", e);
        } catch (RetrievalException e) {
            Helper.logFormat(log, e, "Failed to get the user information for the userId {0}",
                    new Object[]{userId});
            throw new InvalidUserException("Exception occurred while getting the user information.", e, userId);
        } finally {
            Helper.logFormat(log, "Exited DefaultUploadServices#addSubmitter(long, long)");
        }
    }

    /**
     * Populate project_result and component_inquiry for new submitters.
     *
     * @param project       the project
     * @param newSubmitters new submitters external ids.
     *
     * @throws UploadServicesException if error occurs
     * @since 1.0
     */
    private void populateProjectResult(Project project, Collection newSubmitters) throws UploadServicesException {
        long categoryId = project.getProjectCategory().getId();
        // Only design/development/assembly will modify the project result table.
        if (categoryId != 1 && categoryId != 2 && categoryId != 14) {
            // design/development/assembly project need project_result
            return;
        }
        log.debug("Populating the project result table.");
        try {
            long projectId = project.getId();
            // retrieve and update component_inquiry_id
            long componentInquiryId = getNextComponentInquiryId(newSubmitters.size());
            long componentId = getProjectLongValue(project, "Component ID");
            long phaseId = 111 + project.getProjectCategory().getId();
            log.debug("calculated phaseId for Project: " + projectId + " phaseId: " + phaseId);
            long version = getProjectLongValue(project, "Version ID");

            List<List<Object>> projectResultParam = new ArrayList<>();
            List<List<Object>> componentInquiryParam = new ArrayList<>();
            for (Iterator iter = newSubmitters.iterator(); iter.hasNext();) {
                String userId = iter.next().toString();
                boolean existPR = reviewUploadServiceRpc.isProjectResultExists(projectId, userId);

                boolean existCI = reviewUploadServiceRpc.isComponentInquiryExists(projectId, userId);

                // Retrieve oldRating
                double oldRating = 0;
                if (!existPR || !existCI) {
                    Long result = reviewUploadServiceRpc.getUserRating(projectId, userId);
                    if (result != null) {
                        oldRating = result.doubleValue();
                    }
                }

                if (!existPR) {
                    // add project_result
                    if (oldRating == 0) {
                        projectResultParam.add(newArrayList(projectId, userId, 0, 0, null));
                    } else {
                        projectResultParam.add(newArrayList(projectId, userId, 0, 0, oldRating));
                    }
                }

                // add component_inquiry
                // only design, development and assembly contests needs a component_inquiry entry
                if (!existCI && componentId > 0) {
                    log.debug("adding component_inquiry for projectId: " + projectId + " userId: "
                            + userId);
                    // assembly contest must have phaseId set to null
                    if (categoryId == 14) {
                        componentInquiryParam.add(newArrayList(componentInquiryId++, componentId, userId, projectId, null, userId, oldRating, version));
                    } else {
                        componentInquiryParam.add(newArrayList(componentInquiryId++, componentId, userId, projectId, phaseId, userId, oldRating, version));
                    }
                }
            }
            for (List<Object> proj : projectResultParam) {
                reviewUploadServiceRpc.createProjectResult((long) proj.get(0), (String) proj.get(1), (long) proj.get(2),
                        (long) proj.get(3), (Double) proj.get(4));
            }
            for (List<Object> comp : componentInquiryParam) {
                reviewUploadServiceRpc.createComponentInquiry((long) comp.get(0), (long) comp.get(1),
                        (String) comp.get(2), (long) comp.get(3), (Long) comp.get(4), (String) comp.get(5), 1,
                        (double) comp.get(6), (long) comp.get(7));
            }
        } catch (NumberFormatException e) {
            throw new UploadServicesException("Failed to parse long from project property", e);
        }
    }

    /**
     * Retrieve and update next ComponentInquiryId.
     *
     * @param count the count of new submitters
     *
     * @return next component_inquiry_id
     *
     * @throws UploadServicesException if any exception while getting the values
     * @since 1.0
     */
    private long getNextComponentInquiryId(int count) throws UploadServicesException {
        log.debug("Getting the next component inquiry id.");
        try {
            while (true) {
                long currentValue = reviewUploadServiceRpc.getNextId();
                if (reviewUploadServiceRpc.updateNextId(currentValue + count, currentValue) > 0) {
                    return currentValue;
                }
            }
        } catch (Exception e) {
            throw new UploadServicesException("Failed to retrieve next component_inquiry_id", e);
        }
    }

    /**
     * Return project property long value.
     *
     * @param project the project object
     * @param name    the property name
     *
     * @return the long value, 0 if it does not exist
     *
     * @throws NumberFormatException if property can not be parsed into long number
     * @since 1.0
     */
    private static long getProjectLongValue(Project project, String name) {
        Object obj = project.getProperty(name);
        if (obj == null) {
            return 0;
        }
        return Long.parseLong(obj.toString());
    }
}
