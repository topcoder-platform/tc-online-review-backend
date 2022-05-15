/*
 * Copyright (C) 2006 - 2015 TopCoder Inc.  All Rights Reserved.
 */
package com.topcoder.onlinereview.component.or.util;

import com.topcoder.onlinereview.component.deliverable.Submission;
import com.topcoder.onlinereview.component.deliverable.SubmissionFilterBuilder;
import com.topcoder.onlinereview.component.deliverable.SubmissionType;
import com.topcoder.onlinereview.component.deliverable.Upload;
import com.topcoder.onlinereview.component.deliverable.UploadFilterBuilder;
import com.topcoder.onlinereview.component.deliverable.UploadManager;
import com.topcoder.onlinereview.component.exception.BaseException;
import com.topcoder.onlinereview.component.external.ExternalUser;
import com.topcoder.onlinereview.component.external.RetrievalException;
import com.topcoder.onlinereview.component.external.UserRetrieval;
import com.topcoder.onlinereview.component.or.model.PhaseGroup;
import com.topcoder.onlinereview.component.or.model.PhasesDetails;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.phase.Phase;
import com.topcoder.onlinereview.component.resource.Resource;
import com.topcoder.onlinereview.component.resource.ResourceManager;
import com.topcoder.onlinereview.component.review.Item;
import com.topcoder.onlinereview.component.review.Review;
import com.topcoder.onlinereview.component.review.ReviewManager;
import com.topcoder.onlinereview.component.search.filter.AndFilter;
import com.topcoder.onlinereview.component.search.filter.EqualToFilter;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.component.search.filter.InFilter;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.component.or.util.ActionsHelper.hasUserPermission;
import static com.topcoder.onlinereview.component.or.util.ActionsHelper.hasUserRole;
import static com.topcoder.onlinereview.util.CommonUtils.getMessageText;

/**
 * An utility class for getting the details for particular project phase.
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
public class PhasesDetailsServices {

  /** This constructor is declared private to prevent class instantiation. */
  private PhasesDetailsServices() {
    // nothing
  }

  /**
   * Gets the phases details.
   *
   * @param textProvider the text provider
   * @param project the project
   * @param phases the phases
   * @param allProjectResources the all project resources
   * @param allProjectExternalUsers the all project external users
   * @return the phases details
   * @throws BaseException the base exception
   */
  public static PhasesDetails getPhasesDetails(
      UserRetrieval userRetrieval,
      LookupHelper lookupHelper,
      ResourceManager resourceManager,
      ReviewManager reviewManager,
      UploadManager uploadManager,
      String roles,
      MessageSource textProvider,
      Project project,
      Phase[] phases,
      Resource[] allProjectResources,
      Resource[] myResources,
      ExternalUser[] allProjectExternalUsers)
      throws BaseException {

    // Validate parameters first
    ActionsHelper.validateParameterNotNull(textProvider, "textProvider");
    ActionsHelper.validateParameterNotNull(phases, "phases");

    // Move Post-Mortem phase to appropriate place in the list in order to prevent
    // splitting phase group tabs into
    // separate same-named tabs
    Phase[] originalPhases = phases;
    Phase[] phasesCopy = new Phase[phases.length];
    System.arraycopy(phases, 0, phasesCopy, 0, phases.length);
    phases = phasesCopy;

    // Determine the index of Post-Mortem phase in array and if it is present get
    // the phase which Post-Mortem
    // phase depends on
    Phase postMortemPhasePredecessor = null;
    Phase postMortemPhase = null;
    for (int i = 0; i < phases.length; i++) {
      Phase phase = phases[i];
      if (phase.getPhaseType().getName().equals(ORConstants.POST_MORTEM_PHASE_NAME)) {
        postMortemPhase = phase;
        System.arraycopy(phases, i + 1, phases, i, phases.length - i - 1);
      } else {
        if (phase.getPhaseStatus().getId() == 3) { // Closed
          postMortemPhasePredecessor = phase;
        }
      }
    }

    // If Post-Mortem phase exists and depends on some other phase then
    if (postMortemPhase != null) {
      if (postMortemPhasePredecessor != null) {
        for (int i = 0; i < phases.length; i++) {
          Phase phase = phases[i];
          if (phase.getId() == postMortemPhasePredecessor.getId()) {
            int phaseGroupIndex =
                ConfigHelper.findPhaseGroupForPhaseName(
                    postMortemPhasePredecessor.getPhaseType().getName());
            for (int j = i + 1; j < phases.length; j++) {
              Phase nextPhase = phases[j];
              int phaseGroupIndexNext =
                  ConfigHelper.findPhaseGroupForPhaseName(nextPhase.getPhaseType().getName());
              if (phaseGroupIndexNext != phaseGroupIndex) {
                System.arraycopy(phases, j, phases, j + 1, phases.length - j - 1);
                phases[j] = postMortemPhase;
                break;
              }
            }
            break;
          }
        }
      }
    }

    List<PhaseGroup> phaseGroups = new ArrayList<>();
    Map<String, Integer> similarPhaseGroupIndexes = new HashMap<String, Integer>();
    PhaseGroup phaseGroup = null;
    int[] phaseGroupIndexes = new int[phases.length];
    int phaseGroupIdx = -1;
    int activeTabIdx = -1;
    Resource[] submitters = null;

    Submission[] mostRecentContestSubmissions =
        ActionsHelper.getProjectSubmissions(
            lookupHelper,
            uploadManager,
            project.getId(),
            ORConstants.CONTEST_SUBMISSION_TYPE_NAME,
            null,
            false);

    for (int phaseIdx = 0; phaseIdx < phases.length; ++phaseIdx) {
      // Get a phase for the current iteration
      Phase phase = phases[phaseIdx];
      String phaseName = phase.getPhaseType().getName();
      // Take next phase (if the current one is not the last)
      Phase nextPhase = (phaseIdx + 1 != phases.length) ? phases[phaseIdx + 1] : null;

      if (phaseGroup == null
          || !ConfigHelper.isPhaseGroupContainsPhase(phaseGroupIdx, phaseName)
          || phaseGroup.isPhaseInThisGroup(phase)) {
        // Get an index of this potential phase group in the configuration
        phaseGroupIdx = ConfigHelper.findPhaseGroupForPhaseName(phaseName);

        String appFuncName = ConfigHelper.getPhaseGroupAppFunction(phaseGroupIdx);

        if (!appFuncName.equals(ORConstants.VIEW_REGISTRANTS_APP_FUNC)
            || hasUserPermission(roles, ORConstants.VIEW_REGISTRATIONS_PERM_NAME)) {
          phaseGroup = new PhaseGroup();
          phaseGroups.add(phaseGroup);

          Integer groupIndexObj = similarPhaseGroupIndexes.get(appFuncName);
          int groupIndex = (groupIndexObj != null) ? groupIndexObj : 0;

          similarPhaseGroupIndexes.put(appFuncName, groupIndex + 1);

          String groupIndexStr = (groupIndex != 0) ? ("&#160;" + (groupIndex + 1)) : "";

          if (appFuncName.equals(ORConstants.VIEW_REVIEWS_APP_FUNC)
              && ActionsHelper.isStudioProject(project)) {
            phaseGroup.setName(
                getMessageText(
                    textProvider, "ProjectPhaseGroup.Review", new String[] {groupIndexStr}));
          } else {
            phaseGroup.setName(
                getMessageText(
                    textProvider,
                    ConfigHelper.getPhaseGroupNameKey(phaseGroupIdx),
                    new String[] {groupIndexStr}));
          }
          phaseGroup.setTableName(
              getMessageText(
                  textProvider,
                  ConfigHelper.getPhaseGroupTableNameKey(phaseGroupIdx),
                  new String[] {groupIndexStr}));
          phaseGroup.setGroupIndex(groupIndexStr);
          phaseGroup.setAppFunc(appFuncName);
        } else {
          phaseGroup = null;
        }
      }

      if (phaseGroup == null) {
        phaseGroupIndexes[phaseIdx] = -1;
        continue;
      }

      phaseGroup.addPhase(phase);

      // Hide Review/Appeals tab for peer review projects
      if (phaseGroup.getAppFunc().equalsIgnoreCase(ORConstants.VIEW_REVIEWS_APP_FUNC)) {
        Object reviewType = project.getProperty("Review Type");
        if (reviewType != null && ORConstants.PEER_REVIEW_TYPE.equals(reviewType.toString())) {
          phaseGroup.setVisible(false);
        }
      }

      String phaseStatus = phase.getPhaseStatus().getName();

      if (phaseStatus.equalsIgnoreCase("Closed") || phaseStatus.equalsIgnoreCase("Open")) {
        if (phaseStatus.equalsIgnoreCase("Open") && phaseGroupIdx != -1) {
          // Consider only visible phase groups
          if (phaseGroups.get(phaseGroups.size() - 1).isVisible()) {
            // If there are multiple open phases, only the last one's tab will be "active",
            // i.e. open by default
            activeTabIdx = phaseGroups.size() - 1;
          }
        }
        phaseGroup.setPhaseOpen(true);
      }

      boolean isAfterAppealsResponse = ActionsHelper.isAfterAppealsResponse(phases, phaseIdx);
      boolean isStudioScreening = false;
      Phase[] activePhases = ActionsHelper.getActivePhases(phases);
      if (ActionsHelper.isStudioProject(project)) {
        for (Phase p : activePhases) {
          if (p.getPhaseType().getName().equals(ORConstants.SCREENING_PHASE_NAME)
              || p.getPhaseType().getName().equals(ORConstants.CHECKPOINT_SCREENING_PHASE_NAME)) {
            isStudioScreening = true;
          }
        }
      }

      submitters =
          getSubmitters(
              roles, allProjectResources, isAfterAppealsResponse, isStudioScreening, submitters);
      phaseGroup.setSubmitters(submitters);

      // Determine an index of the current phase group (needed for timeline phases
      // list)
      for (int i = 0; i < originalPhases.length; i++) {
        Phase originalPhase = originalPhases[i];
        if (originalPhase.getId() == phase.getId()) {
          int index = phaseGroups.size() - 1;
          phaseGroupIndexes[i] = phaseGroups.get(index).isVisible() ? index : -1;
        }
      }

      if (!phaseGroup.isPhaseOpen()) {
        continue;
      }

      if (phaseGroup.getAppFunc().equals(ORConstants.VIEW_REGISTRANTS_APP_FUNC)) {
        serviceRegistrantsAppFunc(userRetrieval, phaseGroup, submitters, allProjectExternalUsers);
      } else if (phaseGroup.getAppFunc().equals(ORConstants.VIEW_SUBMISSIONS_APP_FUNC)) {
        serviceSubmissionsAppFunc(
            uploadManager,
            reviewManager,
            lookupHelper,
            roles,
            phaseGroup,
            project,
            phases,
            phaseIdx,
            allProjectResources,
            submitters,
            mostRecentContestSubmissions,
            isAfterAppealsResponse,
            myResources);
      } else if (phaseGroup.getAppFunc().equalsIgnoreCase(ORConstants.VIEW_REVIEWS_APP_FUNC)) {
        serviceReviewsAppFunc(
            uploadManager,
            lookupHelper,
            roles,
            phaseGroup,
            project,
            phase,
            nextPhase,
            allProjectResources,
            submitters,
            mostRecentContestSubmissions,
            isAfterAppealsResponse,
            myResources);
      } else if (phaseGroup.getAppFunc().equalsIgnoreCase(ORConstants.AGGREGATION_APP_FUNC)) {
        serviceAggregationAppFunc(
            uploadManager,
            reviewManager,
            lookupHelper,
            roles,
            phaseGroup,
            project,
            phase,
            allProjectResources,
            mostRecentContestSubmissions,
            isAfterAppealsResponse);
      } else if (phaseGroup.getAppFunc().equalsIgnoreCase(ORConstants.FINAL_FIX_APP_FUNC)) {
        serviceFinalFixAppFunc(
            uploadManager,
            reviewManager,
            lookupHelper,
            roles,
            phaseGroup,
            project,
            phase,
            allProjectResources,
            mostRecentContestSubmissions,
            isAfterAppealsResponse,
            myResources);
      } else if (phaseGroup.getAppFunc().equalsIgnoreCase(ORConstants.APPROVAL_APP_FUNC)) {
        serviceApprovalAppFunc(
            reviewManager,
            uploadManager,
            lookupHelper,
            roles,
            phaseGroup,
            project,
            phase,
            allProjectResources,
            isAfterAppealsResponse,
            mostRecentContestSubmissions);
      } else if (phaseGroup.getAppFunc().equalsIgnoreCase(ORConstants.POST_MORTEM_APP_FUNC)) {
        servicePostMortemAppFunc(reviewManager, phaseGroup, project, phase, allProjectResources);
      } else if (phaseGroup.getAppFunc().equalsIgnoreCase(ORConstants.SPEC_REVIEW_APP_FUNC)) {
        serviceSpecReviewAppFunc(
            lookupHelper,
            reviewManager,
            resourceManager,
            phaseGroup,
            project,
            phase,
            allProjectResources);
      } else if (phaseGroup.getAppFunc().equalsIgnoreCase(ORConstants.CHECKPOINT_APP_FUNC)) {
        serviceCheckpointAppFunc(
            lookupHelper,
            reviewManager,
            uploadManager,
            roles,
            phaseGroup,
            project,
            phase,
            allProjectResources,
            myResources,
            phases,
            phaseIdx,
            submitters);
      } else if (phaseGroup.getAppFunc().equalsIgnoreCase(ORConstants.ITERATIVEREVIEW_APP_FUNC)) {
        serviceIterativeReviewsAppFunc(
            uploadManager,
            lookupHelper,
            reviewManager,
            roles,
            phaseGroup,
            project,
            phase,
            allProjectResources,
            submitters,
            mostRecentContestSubmissions,
            myResources);
      }
    }

    PhasesDetails details = new PhasesDetails();

    details.setPhaseGroup(phaseGroups.toArray(new PhaseGroup[phaseGroups.size()]));
    details.setPhaseGroupIndexes(phaseGroupIndexes);
    details.setActiveTabIndex(activeTabIdx);

    return details;
  }

  /**
   * Processes the current phase from <code>Checkpoint</code> group of phases.
   *
   * @param phaseGroup a <code>PhaseGroup</code> providing the collected data for groups of phases.
   * @param project a <code>Project</code> providing details for current project.
   * @param phase a <code>Phase</code> providing details for current phase.
   * @param allProjectResources a <code>Resource</code> listing all project resources.
   * @param phases a <code>Phase</code> array listing all project phases.
   * @param phaseIdx an <code>int</code> specifying the index of current phase.
   * @param submitters a <code>Resource</code> array listing the submitters for project.
   * @throws BaseException if an unexpected error occurs.
   */
  private static void serviceCheckpointAppFunc(
      LookupHelper lookupHelper,
      ReviewManager reviewManager,
      UploadManager uploadManager,
      String roles,
      PhaseGroup phaseGroup,
      Project project,
      Phase phase,
      Resource[] allProjectResources,
      Resource[] myResources,
      Phase[] phases,
      int phaseIdx,
      Resource[] submitters)
      throws BaseException {
    Phase checkpointReviewPhase =
        ActionsHelper.getPhase(phases, false, ORConstants.CHECKPOINT_REVIEW_PHASE_NAME);
    if (checkpointReviewPhase != null) {
      phaseGroup.setCheckpointReviewFinished(checkpointReviewPhase.getPhaseStatus().getId() == 3);
    }

    Phase reviewPhase =
        ActionsHelper.findPhaseByTypeName(phases, ORConstants.APPEALS_RESPONSE_PHASE_NAME);
    if (reviewPhase == null) {
      reviewPhase = ActionsHelper.getPhase(phases, false, ORConstants.REVIEW_PHASE_NAME);
    }
    boolean isReviewFinished = (reviewPhase != null) && (reviewPhase.getPhaseStatus().getId() == 3);

    String phaseName = phase.getPhaseType().getName();

    boolean mayViewMostRecentAfterReview =
        hasUserPermission(
            roles, ORConstants.VIEW_RECENT_CHECKPOINT_SUBMISSIONS_AFTER_REVIEW_PERM_NAME);

    // Checkpoint Submission phase
    if (phaseName.equalsIgnoreCase(ORConstants.CHECKPOINT_SUBMISSION_PHASE_NAME)) {
      Submission[] submissions = null;

      if (mayViewMostRecentAfterReview && isReviewFinished
          || hasUserPermission(roles, ORConstants.VIEW_ALL_CHECKPOINT_SUBMISSIONS_PERM_NAME)
          || (hasUserPermission(roles, ORConstants.VIEW_RECENT_CHECKPOINT_SUBMISSIONS_PERM_NAME)
              && !hasUserRole(roles, ORConstants.CHECKPOINT_REVIEWER_ROLE_NAME)
              && !hasUserRole(roles, ORConstants.REVIEWER_ROLE_NAMES))
          || (hasUserPermission(roles, ORConstants.VIEW_RECENT_CHECKPOINT_SUBMISSIONS_PERM_NAME)
              && hasUserRole(roles, ORConstants.CHECKPOINT_REVIEWER_ROLE_NAME)
              && ActionsHelper.isInOrAfterPhase(
                  phases, phaseIdx, ORConstants.CHECKPOINT_REVIEW_PHASE_NAME))
          || (hasUserPermission(roles, ORConstants.VIEW_RECENT_CHECKPOINT_SUBMISSIONS_PERM_NAME)
              && hasUserRole(roles, ORConstants.REVIEWER_ROLE_NAMES)
              && ActionsHelper.isInOrAfterPhase(phases, phaseIdx, ORConstants.REVIEW_PHASE_NAME))
          || (hasUserPermission(roles, ORConstants.VIEW_SCREENER_CHECKPOINT_SUBMISSION_PERM_NAME)
              && ActionsHelper.isInOrAfterPhase(
                  phases, phaseIdx, ORConstants.CHECKPOINT_SCREENING_PHASE_NAME))) {
        submissions =
            ActionsHelper.getProjectSubmissions(
                lookupHelper,
                uploadManager,
                project.getId(),
                ORConstants.CHECKPOINT_SUBMISSION_TYPE_NAME,
                null,
                false);
      }

      if (submissions == null
          && hasUserPermission(roles, ORConstants.VIEW_MY_CHECKPOINT_SUBMISSIONS_PERM_NAME)) {
        // Get "my" (submitter's) resource
        Resource myResource = null;
        Resource[] myPhaseResources = ActionsHelper.getResourcesForPhase(myResources, null);
        for (Resource resource : myPhaseResources) {
          if (resource.getResourceRole().getName().equals("Submitter")) {
            myResource = resource;
            break;
          }
        }
        if (myResource == null) {
          throw new BaseException(
              "Unable to find the Submitter resource "
                  + "associated with the current user for project "
                  + project.getId());
        }

        submissions =
            ActionsHelper.getResourceSubmissions(
                uploadManager,
                lookupHelper,
                myResource.getId(),
                ORConstants.CHECKPOINT_SUBMISSION_TYPE_NAME,
                null,
                false);
      }

      if (submissions == null) {
        submissions = new Submission[0];
      }
      // Use comparator to sort submissions either by placement
      // or by the time when they were uploaded
      Comparators.CheckpointSubmissionComparator comparator =
          new Comparators.CheckpointSubmissionComparator();

      comparator.assignSubmitters(submitters);
      Arrays.sort(submissions, comparator);

      phaseGroup.setPastCheckpointSubmissions(
          getPreviousUploadsForSubmissions(
              lookupHelper,
              uploadManager,
              roles,
              project,
              submissions,
              ORConstants.VIEW_ALL_CHECKPOINT_SUBMISSIONS_PERM_NAME));
      phaseGroup.setCheckpointSubmissions(submissions);
    }

    // Checkpoint Screening phase
    if (phaseName.equalsIgnoreCase(ORConstants.CHECKPOINT_SCREENING_PHASE_NAME)
        && phaseGroup.getCheckpointSubmissions() != null) {

      Resource[] screeners =
          ActionsHelper.getResourcesForPhase(allProjectResources, phases[phaseIdx]);
      if (screeners != null && screeners.length > 0) {
        phaseGroup.setCheckpointScreener(screeners[0]);
      }

      phaseGroup.setCheckpointScreeningPhaseStatus(phase.getPhaseStatus().getId());
      phaseGroup.setCheckpointScreeningReviews(
          ActionsHelper.searchReviews(reviewManager, phase.getId(), null, false));
    }

    // Checkpoint Review phase
    if (phaseName.equalsIgnoreCase(ORConstants.CHECKPOINT_REVIEW_PHASE_NAME)
        && phaseGroup.getCheckpointSubmissions() != null) {

      Resource[] reviewers = ActionsHelper.getResourcesForPhase(allProjectResources, phase);
      if (reviewers != null && reviewers.length > 0) {
        phaseGroup.setCheckpointReviewer(reviewers[0]);
      }

      // Obtain an instance of Review Manager
      phaseGroup.setCheckpointReviews(
          ActionsHelper.searchReviews(reviewManager, phase.getId(), null, false));
    }
  }

  /**
   * Service registrants app func.
   *
   * @param userRetrieval the request
   * @param phaseGroup the phase group
   * @param submitters the submitters
   * @param allProjectExternalUsers the all project external users
   * @throws RetrievalException the retrieval exception
   */
  private static void serviceRegistrantsAppFunc(
      UserRetrieval userRetrieval,
      PhaseGroup phaseGroup,
      Resource[] submitters,
      ExternalUser[] allProjectExternalUsers)
      throws RetrievalException {
    if (submitters == null || submitters.length == 0) {
      return;
    }

    // Get corresponding external users for the array of submitters
    ExternalUser[] extUsers =
        (allProjectExternalUsers != null)
            ? allProjectExternalUsers
            : ActionsHelper.getExternalUsersForResources(userRetrieval, submitters);
    String[] userEmails = new String[submitters.length];

    for (int j = 0; j < submitters.length; ++j) {
      // Get external ID for the current submitter's resource
      long extUserId = submitters[j].getUserId();
      for (ExternalUser extUser : extUsers) {
        if (extUserId == extUser.getId()) {
          userEmails[j] = extUser.getEmail();
          break;
        }
      }
    }

    phaseGroup.setRegistrantsEmails(userEmails);
  }

  /**
   * Gets the previous uploads for specified submissions.
   *
   * @param project a <code>Project</code> providing details for project.
   * @param submissions a <code>Submission</code> array listing the current submissions to get
   *     previous uploads for.
   * @param viewAllSubmissionsPermission a <code>String</code> providing the permission for viewing
   *     all submissions of desired type.
   * @return an <code>Upload</code> array listing the uploads for previous submissions for specified
   *     submissions.
   * @throws BaseException if an unexpected error occurs.
   */
  private static Upload[][] getPreviousUploadsForSubmissions(
      LookupHelper lookupHelper,
      UploadManager uploadManager,
      String roles,
      Project project,
      Submission[] submissions,
      String viewAllSubmissionsPermission)
      throws BaseException {
    Upload[][] pastSubmissions = null;
    if (submissions.length > 0 && hasUserPermission(roles, viewAllSubmissionsPermission)) {

      // Find all deleted submissions for specified project
      Submission[] allDeletedSubmissions =
          ActionsHelper.getProjectSubmissions(
              lookupHelper, uploadManager, project.getId(), null, "Deleted", true);

      pastSubmissions = new Upload[submissions.length][];

      for (int j = 0; j < pastSubmissions.length; ++j) {
        List<Upload> temp = new ArrayList<>();
        long currentUploadOwnerId = submissions[j].getUpload().getOwner();

        for (Submission deletedSubmission : allDeletedSubmissions) {
          Upload deletedSubmissionUpload = deletedSubmission.getUpload();
          if (deletedSubmission.getSubmissionType().getId()
                  == submissions[j].getSubmissionType().getId()
              && deletedSubmissionUpload.getOwner() == currentUploadOwnerId) {
            temp.add(deletedSubmissionUpload);
          }
        }

        if (!temp.isEmpty()) {
          pastSubmissions[j] = temp.toArray(new Upload[temp.size()]);
        }
      }
    }
    return pastSubmissions;
  }

  /**
   * Service submissions app func.
   *
   * @param phaseGroup the phase group
   * @param project the project
   * @param phases the phases
   * @param phaseIdx the phase idx
   * @param allProjectResources the all project resources
   * @param submitters the submitters
   * @param mostRecentContestSubmissions the most recent contest submissions
   * @param isAfterAppealsResponse the is after appeals response
   * @throws BaseException the base exception
   */
  private static void serviceSubmissionsAppFunc(
      UploadManager uploadManager,
      ReviewManager reviewManager,
      LookupHelper lookupHelper,
      String roles,
      PhaseGroup phaseGroup,
      Project project,
      Phase[] phases,
      int phaseIdx,
      Resource[] allProjectResources,
      Resource[] submitters,
      Submission[] mostRecentContestSubmissions,
      boolean isAfterAppealsResponse,
      Resource[] myResources)
      throws BaseException {
    String phaseName = phases[phaseIdx].getPhaseType().getName();

    if (phaseName.equalsIgnoreCase(ORConstants.SUBMISSION_PHASE_NAME)) {

      Submission[] submissions = null;

      if (hasUserPermission(roles, ORConstants.VIEW_ALL_SUBM_PERM_NAME)) {
        submissions = mostRecentContestSubmissions;
      }

      boolean mayViewMostRecentAfterAppealsResponse =
          hasUserPermission(roles, ORConstants.VIEW_RECENT_SUBM_AAR_PERM_NAME);

      if (submissions == null
          && ((mayViewMostRecentAfterAppealsResponse && isAfterAppealsResponse))
          && project.getProjectCategory().getId() != 37) {
        submissions = mostRecentContestSubmissions;
      }
      if (submissions == null
          && hasUserPermission(roles, ORConstants.VIEW_RECENT_SUBM_PERM_NAME)
          && !hasUserRole(roles, ORConstants.REVIEWER_ROLE_NAMES)) {
        submissions = mostRecentContestSubmissions;
      }
      if (submissions == null
          && hasUserPermission(roles, ORConstants.VIEW_RECENT_SUBM_PERM_NAME)
          && hasUserRole(roles, ORConstants.REVIEWER_ROLE_NAMES)
          && ActionsHelper.isInOrAfterPhase(phases, phaseIdx, ORConstants.REVIEW_PHASE_NAME)) {
        submissions = mostRecentContestSubmissions;
      }
      if (submissions == null
          && hasUserPermission(roles, ORConstants.VIEW_SCREENER_SUBM_PERM_NAME)
          && ActionsHelper.isInOrAfterPhase(phases, phaseIdx, ORConstants.SCREENING_PHASE_NAME)) {
        submissions = mostRecentContestSubmissions;
      }
      if (submissions == null && hasUserPermission(roles, ORConstants.VIEW_MY_SUBM_PERM_NAME)) {
        // Get "my" (submitter's) resource
        Resource myResource = null;
        for (Resource resource : myResources) {
          if (resource.getResourceRole().getName().equals("Submitter")) {
            myResource = resource;
            break;
          }
        }

        if (myResource == null) {
          throw new BaseException(
              "Unable to find the Submitter resource "
                  + "associated with the current user for project "
                  + project.getId());
        }

        submissions =
            ActionsHelper.getResourceSubmissions(
                uploadManager,
                lookupHelper,
                myResource.getId(),
                ORConstants.CONTEST_SUBMISSION_TYPE_NAME,
                null,
                false);
      }
      if (submissions == null
          && hasUserPermission(roles, ORConstants.VIEW_CURRENT_ITERATIVE_REVIEW_SUBMISSION)) {
        submissions = mostRecentContestSubmissions;
      }

      if (submissions == null) {
        submissions = new Submission[0];
      }
      // Use comparator to sort submissions either by placement
      // or by the time when they were uploaded
      Comparators.SubmissionComparer comparator = new Comparators.SubmissionComparer();

      comparator.assignSubmitters(submitters);
      Arrays.sort(submissions, comparator);

      phaseGroup.setPastSubmissions(
          getPreviousUploadsForSubmissions(
              lookupHelper,
              uploadManager,
              roles,
              project,
              submissions,
              ORConstants.VIEW_ALL_SUBM_PERM_NAME));

      phaseGroup.setSubmissions(submissions);
    }

    if (phaseName.equalsIgnoreCase(ORConstants.SCREENING_PHASE_NAME)
        && phaseGroup.getSubmissions() != null) {
      Submission[] submissions = phaseGroup.getSubmissions();

      if (hasUserPermission(roles, ORConstants.VIEW_SCREENER_SUBM_PERM_NAME)
          && !hasUserRole(roles, ORConstants.PRIMARY_SCREENER_ROLE_NAME)) {
        Resource[] myPhaseResources =
            ActionsHelper.getResourcesForPhase(myResources, phases[phaseIdx]);
        List<Submission> tempSubs = new ArrayList<>();

        for (Submission submission : submissions) {
          for (Resource resource : myPhaseResources) {
            if (resource.containsSubmission(submission.getId())) {
              tempSubs.add(submission);
            }
          }
        }
        submissions = tempSubs.toArray(new Submission[tempSubs.size()]);
        phaseGroup.setSubmissions(submissions);
      }

      Resource[] screeners =
          ActionsHelper.getResourcesForPhase(allProjectResources, phases[phaseIdx]);

      phaseGroup.setReviewers(screeners);
      phaseGroup.setScreeningPhaseStatus(phases[phaseIdx].getPhaseStatus().getId());

      // No need to fetch screening results if there are no submissions
      if (submissions.length == 0) {
        return;
      }

      phaseGroup.setScreenings(
          ActionsHelper.searchReviews(reviewManager, phases[phaseIdx].getId(), null, false));
    }
  }

  /**
   * Service reviews app func.
   *
   * @param phaseGroup the phase group
   * @param project the project
   * @param phase the phase
   * @param nextPhase the next phase
   * @param allProjectResources the all project resources
   * @param submitters the submitters
   * @param mostRecentContestSubmissions the most recent contest submissions
   * @param isAfterAppealsResponse the is after appeals response
   * @throws BaseException the base exception
   */
  private static void serviceReviewsAppFunc(
      UploadManager uploadManager,
      LookupHelper lookupHelper,
      String roles,
      PhaseGroup phaseGroup,
      Project project,
      Phase phase,
      Phase nextPhase,
      Resource[] allProjectResources,
      Resource[] submitters,
      Submission[] mostRecentContestSubmissions,
      boolean isAfterAppealsResponse,
      Resource[] myResources)
      throws BaseException {
    String phaseName = phase.getPhaseType().getName();

    if (phaseName.equalsIgnoreCase(ORConstants.REVIEW_PHASE_NAME)) {
      // If the project is not in the after appeals response state, allow uploading of
      // testcases
      phaseGroup.setUploadingTestcasesAllowed(!isAfterAppealsResponse);

      Submission[] submissions = null;

      if (hasUserPermission(roles, ORConstants.VIEW_ALL_SUBM_PERM_NAME)) {
        submissions = mostRecentContestSubmissions;
      }

      boolean mayViewMostRecentAfterAppealsResponse =
          hasUserPermission(roles, ORConstants.VIEW_RECENT_SUBM_AAR_PERM_NAME);

      if (submissions == null
          && ((mayViewMostRecentAfterAppealsResponse && isAfterAppealsResponse)
              || hasUserPermission(roles, ORConstants.VIEW_RECENT_SUBM_PERM_NAME))) {
        submissions = mostRecentContestSubmissions;
      }
      if (submissions == null && hasUserPermission(roles, ORConstants.VIEW_MY_SUBM_PERM_NAME)) {
        // Get "my" (submitter's) resource
        Resource myResource = null;
        for (Resource resource : myResources) {
          if (resource.getResourceRole().getName().equals("Submitter")) {
            myResource = resource;
            break;
          }
        }

        if (myResource == null) {
          throw new BaseException(
              "Unable to find the Submitter resource "
                  + "associated with the current user for project "
                  + project.getId());
        }

        submissions =
            ActionsHelper.getResourceSubmissions(
                uploadManager,
                lookupHelper,
                myResource.getId(),
                ORConstants.CONTEST_SUBMISSION_TYPE_NAME,
                null,
                false);
      }
      // No submissions -- nothing to review, but the list of submissions must not be
      // null in this case
      if (submissions == null) {
        submissions = new Submission[0];
      }

      // Use comparator to sort submissions either by placement or by the time when
      // they were uploaded
      Comparators.SubmissionComparer comparator = new Comparators.SubmissionComparer();

      comparator.assignSubmitters(submitters);
      Arrays.sort(submissions, comparator);

      phaseGroup.setSubmissions(submissions);

      // Some resource roles can always see links to reviews (if there are any).
      // There is no corresponding permission, so the list of roles is hard-coded
      boolean allowedToSeeReviewLink =
          hasUserRole(
              roles,
              new String[] {
                ORConstants.MANAGER_ROLE_NAME,
                ORConstants.GLOBAL_MANAGER_ROLE_NAME,
                ORConstants.COCKPIT_PROJECT_USER_ROLE_NAME,
                ORConstants.REVIEWER_ROLE_NAME,
                ORConstants.ACCURACY_REVIEWER_ROLE_NAME,
                ORConstants.FAILURE_REVIEWER_ROLE_NAME,
                ORConstants.STRESS_REVIEWER_ROLE_NAME,
                ORConstants.CLIENT_MANAGER_ROLE_NAME,
                ORConstants.COPILOT_ROLE_NAME,
                ORConstants.OBSERVER_ROLE_NAME
              });
      // Determine if the Review phase is closed
      boolean isReviewClosed =
          phase.getPhaseStatus().getName().equalsIgnoreCase(ORConstants.CLOSED_PH_STATUS_NAME);
      // Determine if the Appeals phase is open
      boolean isAppealsOpen =
          (nextPhase != null
              && nextPhase.getPhaseType().getName().equalsIgnoreCase(ORConstants.APPEALS_PHASE_NAME)
              && nextPhase
                  .getPhaseStatus()
                  .getName()
                  .equalsIgnoreCase(ORConstants.OPEN_PH_STATUS_NAME));

      if (!allowedToSeeReviewLink) {
        boolean canViewReviewsDuringReview =
            "Yes".equals(phase.getAttribute("View Reviews During Review"));

        // Determine if the user is allowed to place appeals and Appeals phase is open
        if (isReviewClosed
            || canViewReviewsDuringReview
            || (isAppealsOpen && hasUserPermission(roles, ORConstants.PERFORM_APPEAL_PERM_NAME))) {
          allowedToSeeReviewLink = true;
        }
      }

      phaseGroup.setDisplayReviewLinks(allowedToSeeReviewLink);

      Resource[] reviewers = null;

      if (!isAfterAppealsResponse
          && hasUserPermission(roles, ORConstants.VIEW_REVIEWER_REVIEWS_PERM_NAME)
          && hasUserRole(roles, ORConstants.REVIEWER_ROLE_NAMES)) {
        // Get "my" (reviewer's) resource
        reviewers = ActionsHelper.getResourcesForPhase(myResources, phase);
      }

      if (reviewers == null) {
        reviewers = ActionsHelper.getResourcesForPhase(allProjectResources, phase);
      }

      // Put collected reviewers into the phase group
      phaseGroup.setReviewers(reviewers);
      // A safety check: create an empty array in case reviewers is null
      if (reviewers == null) {
        reviewers = new Resource[0];
      }

      List<Long> submissionIds = new ArrayList<Long>();
      for (Submission submission : submissions) {
        submissionIds.add(submission.getId());
      }

      List<Long> reviewerIds = new ArrayList<Long>();
      for (Resource reviewer : reviewers) {
        reviewerIds.add(reviewer.getId());
      }

      Review[] ungroupedReviews = null;

      if (!(submissionIds.isEmpty() || reviewerIds.isEmpty())) {
        Filter filterSubmissions = new InFilter("submission", submissionIds);
        Filter filterReviewers = new InFilter("reviewer", reviewerIds);
        Filter filterScorecard =
            new EqualToFilter("scorecardType", lookupHelper.getScorecardType("Review").getId());

        List<Filter> reviewFilters = new ArrayList<Filter>();
        reviewFilters.add(filterReviewers);
        reviewFilters.add(filterScorecard);
        reviewFilters.add(filterSubmissions);

        // Create final filter
        Filter filterForReviews = new AndFilter(reviewFilters);

        // Obtain an instance of Review Manager
        ReviewManager revMgr = ActionsHelper.createReviewManager();
        // Get the reviews from every individual reviewer
        ungroupedReviews = revMgr.searchReviews(filterForReviews, false);
      }
      if (ungroupedReviews == null) {
        ungroupedReviews = new Review[0];
      }

      boolean canDownloadTestCases =
          (isReviewClosed && hasUserPermission(roles, ORConstants.DOWNLOAD_TEST_CASES_PERM_NAME))
              || (!isReviewClosed
                  && hasUserPermission(roles, ORConstants.DOWNLOAD_TC_DUR_REVIEW_PERM_NAME))
              || (!isReviewClosed
                  && isAppealsOpen
                  && hasUserPermission(roles, ORConstants.PERFORM_APPEAL_PERM_NAME)
                  && hasUserPermission(roles, ORConstants.DOWNLOAD_TEST_CASES_PERM_NAME));

      if (!reviewerIds.isEmpty() && canDownloadTestCases) {
        Filter filterResource = new InFilter("resource_id", reviewerIds);
        Filter filterStatus =
            UploadFilterBuilder.createUploadStatusIdFilter(
                lookupHelper.getUploadStatus("Active").getId());
        Filter filterType =
            UploadFilterBuilder.createUploadTypeIdFilter(
                lookupHelper.getUploadType("Test Case").getId());

        Filter filterForUploads =
            new AndFilter(Arrays.asList(filterResource, filterStatus, filterType));
        Upload[] testCases = ActionsHelper.createUploadManager().searchUploads(filterForUploads);

        phaseGroup.setTestCases(testCases);
      }

      Review[][] reviews = new Review[submissions.length][];
      Date[] reviewDates = new Date[submissions.length];

      for (int j = 0; j < submissions.length; ++j) {
        Date latestDate = null;
        Review[] innerReviews = new Review[reviewers.length];
        Arrays.fill(innerReviews, null);

        for (int k = 0; k < reviewers.length; ++k) {
          for (Review ungrouped : ungroupedReviews) {
            if (ungrouped.getAuthor() == reviewers[k].getId()
                && ungrouped.getSubmission() == submissions[j].getId()) {
              innerReviews[k] = ungrouped;
              if (!ungrouped.isCommitted()) {
                continue;
              }
              if (latestDate == null || latestDate.before(ungrouped.getModificationTimestamp())) {
                latestDate = ungrouped.getModificationTimestamp();
              }
            }
          }
        }

        reviews[j] = innerReviews;
        reviewDates[j] = latestDate;
      }
      phaseGroup.setReviews(reviews);
      phaseGroup.setReviewDates(reviewDates);
    }

    if (phaseName.equalsIgnoreCase(ORConstants.APPEALS_PHASE_NAME)
        && phaseGroup.getReviews() != null) {
      // set the Appeals phase status to indicate
      // if the appeals information should be available
      if (phase.getPhaseStatus().getName().equalsIgnoreCase("Scheduled")) {
        return;
      }

      phaseGroup.setAppealsPhaseOpened(true);

      Review[][] reviews = phaseGroup.getReviews();
      int[][] totalAppeals = new int[reviews.length][];
      int[][] unresolvedAppeals = new int[reviews.length][];

      countAppeals(reviews, totalAppeals, unresolvedAppeals);

      phaseGroup.setTotalAppealsCounts(totalAppeals);
      phaseGroup.setUnresolvedAppealsCounts(unresolvedAppeals);
    }
  }

  /**
   * Service aggregation app func.
   *
   * @param phaseGroup the phase group
   * @param project the project
   * @param phase the phase
   * @param allProjectResources the all project resources
   * @param mostRecentContestSubmissions the most recent contest submissions
   * @param isAfterAppealsResponse the is after appeals response
   * @throws BaseException the base exception
   */
  private static void serviceAggregationAppFunc(
      UploadManager uploadManager,
      ReviewManager reviewManager,
      LookupHelper lookupHelper,
      String roles,
      PhaseGroup phaseGroup,
      Project project,
      Phase phase,
      Resource[] allProjectResources,
      Submission[] mostRecentContestSubmissions,
      boolean isAfterAppealsResponse)
      throws BaseException {
    retrieveSubmissions(
        uploadManager,
        lookupHelper,
        roles,
        null,
        phaseGroup,
        project,
        mostRecentContestSubmissions,
        isAfterAppealsResponse);
    String phaseName = phase.getPhaseType().getName();

    if (phaseName.equalsIgnoreCase(ORConstants.AGGREGATION_PHASE_NAME)
        && phaseGroup.getSubmitters() != null) {
      Resource winner = ActionsHelper.getWinner(phase.getProject().getId());
      phaseGroup.setWinner(winner);

      Resource[] aggregator = ActionsHelper.getResourcesForPhase(allProjectResources, phase);

      if (aggregator == null || aggregator.length == 0) {
        return;
      }

      Review[] reviews =
          ActionsHelper.searchReviews(reviewManager, phase.getId(), aggregator[0].getId(), false);
      if (reviews.length != 0) {
        phaseGroup.setAggregation(reviews[0]);
      }
    }
  }

  /**
   * Service final fix app func.
   *
   * @param phaseGroup the phase group
   * @param project the project
   * @param phase the phase
   * @param allProjectResources the all project resources
   * @param mostRecentContestSubmissions the most recent contest submissions
   * @param isAfterAppealsResponse the is after appeals response
   * @throws BaseException the base exception
   */
  private static void serviceFinalFixAppFunc(
      UploadManager uploadManager,
      ReviewManager reviewManager,
      LookupHelper lookupHelper,
      String roles,
      PhaseGroup phaseGroup,
      Project project,
      Phase phase,
      Resource[] allProjectResources,
      Submission[] mostRecentContestSubmissions,
      boolean isAfterAppealsResponse,
      Resource[] myResources)
      throws BaseException {
    retrieveSubmissions(
        uploadManager,
        lookupHelper,
        roles,
        myResources,
        phaseGroup,
        project,
        mostRecentContestSubmissions,
        isAfterAppealsResponse);
    String phaseName = phase.getPhaseType().getName();

    if (phaseGroup.getSubmitters() != null) {
      Resource winner = phaseGroup.getWinner();
      if (winner == null) {
        winner = ActionsHelper.getWinner(phase.getProject().getId());
        phaseGroup.setWinner(winner);
      }
      if (winner == null) {
        return;
      }
    }

    if (phaseName.equalsIgnoreCase(ORConstants.FINAL_FIX_PHASE_NAME)) {
      Upload[] uploads =
          ActionsHelper.getPhaseUploads(uploadManager, lookupHelper, phase.getId(), "Final Fix");

      if (uploads.length > 0) {
        phaseGroup.setFinalFix(uploads[0]);
      }
    }

    if (phaseName.equalsIgnoreCase(ORConstants.FINAL_REVIEW_PHASE_NAME)
        && phaseGroup.getSubmitters() != null) {
      Resource[] reviewers = ActionsHelper.getResourcesForPhase(allProjectResources, phase);

      if (reviewers == null || reviewers.length == 0) {
        return;
      }

      Review[] reviews =
          ActionsHelper.searchReviews(reviewManager, phase.getId(), reviewers[0].getId(), false);
      if (reviews.length != 0) {
        phaseGroup.setFinalReview(reviews[0]);
      }
    }
  }

  /**
   * Sets the specified phase group with details for <code>Specification Submission/Review</code>
   * phases.
   *
   * @param phaseGroup a <code>PhaseGroup</code> providing the details for group of phase the
   *     current phase belongs to.
   * @param project a <code>Project</code> providing the details for current project.
   * @param phase a <code>Phase</code> providing the details for <code>Post-Mortem</code> phase.
   * @param allProjectResources a <code>Resource</code> array listing all existing resources for
   *     specified project.
   * @throws BaseException if an unexpected error occurs.
   */
  private static void serviceSpecReviewAppFunc(
      LookupHelper lookupHelper,
      ReviewManager reviewManager,
      ResourceManager resourceManager,
      PhaseGroup phaseGroup,
      Project project,
      Phase phase,
      Resource[] allProjectResources)
      throws BaseException {
    String phaseName = phase.getPhaseType().getName();

    if (phaseName.equalsIgnoreCase(ORConstants.SPECIFICATION_SUBMISSION_PHASE_NAME)) {
      SubmissionType specSubmissionType =
          lookupHelper.getSubmissionType(ORConstants.SPECIFICATION_SUBMISSION_TYPE_NAME);

      Filter submissionTypeFilter =
          SubmissionFilterBuilder.createSubmissionTypeIdFilter(specSubmissionType.getId());
      Filter phaseFilter = SubmissionFilterBuilder.createProjectPhaseIdFilter(phase.getId());

      UploadManager upMgr = ActionsHelper.createUploadManager();
      Submission[] submissions =
          upMgr.searchSubmissions(new AndFilter(phaseFilter, submissionTypeFilter));
      if (submissions.length > 0) {
        phaseGroup.setSpecificationSubmission(submissions[0]);
        phaseGroup.setSpecificationSubmitter(
            resourceManager.getResource(submissions[0].getUpload().getOwner()));
      }
    }

    if (phaseName.equalsIgnoreCase(ORConstants.SPECIFICATION_REVIEW_PHASE_NAME)) {
      Resource[] reviewers = ActionsHelper.getResourcesForPhase(allProjectResources, phase);
      if ((reviewers == null) || (reviewers.length == 0)) {
        return;
      }
      Review[] reviews =
          ActionsHelper.searchReviews(reviewManager, phase.getId(), reviewers[0].getId(), false);
      if (reviews.length != 0) {
        phaseGroup.setSpecificationReview(reviews[0]);
      }
    }
  }

  /**
   * Sets the specified phase group with details for <code>Approval</code> phase.
   *
   * @param phaseGroup a <code>PhaseGroup</code> providing the details for group of phase the
   *     current phase belongs to.
   * @param project a <code>Project</code> providing the details for current project.
   * @param thisPhase a <code>Phase</code> providing the details for <code>Post-Mortem</code> phase.
   * @param allProjectResources a <code>Resource</code> array listing all existing resources for
   *     specified project.
   * @param isAfterAppealsResponse <code>true</code> if current phase is after appeals response;
   *     <code>false</code> otherwise.
   * @throws BaseException if an unexpected error occurs.
   */
  private static void serviceApprovalAppFunc(
      ReviewManager reviewManager,
      UploadManager uploadManager,
      LookupHelper lookupHelper,
      String roles,
      PhaseGroup phaseGroup,
      Project project,
      Phase thisPhase,
      Resource[] allProjectResources,
      boolean isAfterAppealsResponse,
      Submission[] mostRecentContestSubmissions)
      throws BaseException {
    if (phaseGroup.getSubmitters() == null) {
      return;
    }
    retrieveSubmissions(
        uploadManager,
        lookupHelper,
        roles,
        allProjectResources,
        phaseGroup,
        project,
        mostRecentContestSubmissions,
        isAfterAppealsResponse);
    Resource winner = ActionsHelper.getWinner(project.getId());
    phaseGroup.setWinner(winner);

    Resource[] approvers = ActionsHelper.getResourcesForPhase(allProjectResources, thisPhase);
    if (approvers == null || approvers.length == 0) {
      return;
    }
    phaseGroup.setApprovalReviewers(approvers);

    phaseGroup.setApproval(
        ActionsHelper.searchReviews(reviewManager, thisPhase.getId(), null, false));
    phaseGroup.setApprovalPhaseStatus(thisPhase.getPhaseStatus().getId());
    phaseGroup.setFinalFix(
        ActionsHelper.getFinalFixForApprovalPhase(uploadManager, lookupHelper, thisPhase));
  }

  /**
   * Sets the specified phase group with details for <code>Post-Mortem</code> phase.
   *
   * @param phaseGroup a <code>PhaseGroup</code> providing the details for group of phase the
   *     current phase belongs to.
   * @param project a <code>Project</code> providing the details for current project.
   * @param thisPhase a <code>Phase</code> providing the details for <code>Post-Mortem</code> phase.
   * @param allProjectResources a <code>Resource</code> array listing all existing resources for
   *     specified project.
   * @throws BaseException if an unexpected error occurs.
   */
  private static void servicePostMortemAppFunc(
      ReviewManager reviewManager,
      PhaseGroup phaseGroup,
      Project project,
      Phase thisPhase,
      Resource[] allProjectResources)
      throws BaseException {
    // Get the list of Post-Mortem reviewers assigned to project
    Resource[] postMortemReviewers =
        ActionsHelper.getResourcesForPhase(allProjectResources, thisPhase);
    if (postMortemReviewers == null || postMortemReviewers.length == 0) {
      return;
    }
    phaseGroup.setPostMortemReviewers(postMortemReviewers);

    phaseGroup.setPostMortemReviews(
        ActionsHelper.searchReviews(reviewManager, thisPhase.getId(), null, false));
    phaseGroup.setPostMortemPhaseStatus(thisPhase.getPhaseStatus().getId());
  }

  /**
   * Sets the specified phase group with details for <code>Iterative Review</code> phase.
   *
   * @param phaseGroup a <code>PhaseGroup</code> providing the details for group of phase the
   *     current phase belongs to.
   * @param project a <code>Project</code> providing the details for current project.
   * @param phase a <code>Phase</code> providing the details for <code>Iterative Review</code>
   *     phase.
   * @param allProjectResources a <code>Resource</code> array listing all existing resources for
   *     specified project.
   * @param submitters a <code>Resource</code> array listing all submitters for specified project.
   * @param mostRecentContestSubmissions a <code>Submission</code> array listing all active contest
   *     submissions.
   * @throws BaseException if an unexpected error occurs.
   */
  private static void serviceIterativeReviewsAppFunc(
      UploadManager uploadManager,
      LookupHelper lookupHelper,
      ReviewManager revMgr,
      String roles,
      PhaseGroup phaseGroup,
      Project project,
      Phase phase,
      Resource[] allProjectResources,
      Resource[] submitters,
      Submission[] mostRecentContestSubmissions,
      Resource[] myResources)
      throws BaseException {
    String phaseName = phase.getPhaseType().getName();

    Submission associatedSubmission = null;

    Review[] ungroupedReviews = null;
    Filter filterPhase = new EqualToFilter("projectPhase", phase.getId());
    Filter filterScorecard =
        new EqualToFilter(
            "scorecardType", lookupHelper.getScorecardType("Iterative Review").getId());
    List<Filter> reviewFilters = new ArrayList<Filter>();
    reviewFilters.add(filterScorecard);
    reviewFilters.add(filterPhase);
    // Create final filter
    Filter filterForReviews = new AndFilter(reviewFilters);
    // Obtain an instance of Review Manager
    // Get the reviews from every individual reviewer
    ungroupedReviews = revMgr.searchReviews(filterForReviews, false);

    if (mostRecentContestSubmissions != null) {
      if (ungroupedReviews != null && ungroupedReviews.length > 0) {
        long submissionId = ungroupedReviews[0].getSubmission();
        for (Submission submission : mostRecentContestSubmissions) {
          if (submissionId == submission.getId()) {
            associatedSubmission = submission;
          }
        }
      } else {
        Date earliestDate = null;
        for (Submission submission : mostRecentContestSubmissions) {
          if (submission.getSubmissionStatus().getName().equalsIgnoreCase("Active")
              && (earliestDate == null
                  || earliestDate.compareTo(submission.getCreationTimestamp()) > 0)) {
            earliestDate = submission.getCreationTimestamp();
            associatedSubmission = submission;
          }
        }
      }
    }

    if (phaseName.equalsIgnoreCase(ORConstants.ITERATIVE_REVIEW_PHASE_NAME)) {
      if (hasUserPermission(roles, ORConstants.VIEW_ALL_SUBM_PERM_NAME)) {
        phaseGroup.setIterativeReviewSubmission(associatedSubmission);
      }

      if (hasUserPermission(roles, ORConstants.PERFORM_ITERATIVE_REVIEW_PERM_NAME)) {
        phaseGroup.setIterativeReviewSubmission(associatedSubmission);
      }

      if (phaseGroup.getIterativeReviewSubmission() == null
          && hasUserPermission(roles, ORConstants.VIEW_MY_SUBM_PERM_NAME)) {
        // Get "my" (submitter's) resource
        Resource myResource = null;
        for (Resource resource : myResources) {
          if (resource.getResourceRole().getName().equals("Submitter")) {
            myResource = resource;
            break;
          }
        }

        if (myResource == null) {
          throw new BaseException(
              "Unable to find the Submitter resource "
                  + "associated with the current user for project "
                  + project.getId());
        }

        Submission[] mySubmissions =
            ActionsHelper.getResourceSubmissions(
                uploadManager,
                lookupHelper,
                myResource.getId(),
                ORConstants.CONTEST_SUBMISSION_TYPE_NAME,
                null,
                false);
        if (mySubmissions != null) {
          for (Submission submission : mySubmissions) {
            if (submission.getId() == associatedSubmission.getId()) {
              phaseGroup.setIterativeReviewSubmission(associatedSubmission);
            }
          }
        }
      }

      for (Resource resource : allProjectResources) {
        if (resource.getId() == associatedSubmission.getUpload().getOwner()) {
          phaseGroup.setIterativeReviewSubmitter(resource);
          break;
        }
      }

      // Some resource roles can always see links to reviews (if there are any).
      // There is no corresponding permission, so the list of roles is hard-coded
      boolean allowedToSeeReviewLink =
          hasUserRole(
              roles,
              new String[] {
                ORConstants.MANAGER_ROLE_NAME,
                ORConstants.GLOBAL_MANAGER_ROLE_NAME,
                ORConstants.COCKPIT_PROJECT_USER_ROLE_NAME,
                ORConstants.ITERATIVE_REVIEWER_ROLE_NAME,
                ORConstants.CLIENT_MANAGER_ROLE_NAME,
                ORConstants.COPILOT_ROLE_NAME,
                ORConstants.OBSERVER_ROLE_NAME
              });
      // Determine if the Iterative Review phase is closed
      boolean isReviewClosed =
          phase.getPhaseStatus().getName().equalsIgnoreCase(ORConstants.CLOSED_PH_STATUS_NAME);

      if (isReviewClosed) {
        allowedToSeeReviewLink = true;
      }

      phaseGroup.setDisplayReviewLinks(allowedToSeeReviewLink);

      Resource[] reviewers = null;

      if (phase.getPhaseStatus().getName().equalsIgnoreCase("Open")
          && hasUserPermission(roles, ORConstants.VIEW_ITERATIVE_REVIEWER_REVIEWS_PERM_NAME)
          && hasUserRole(roles, ORConstants.ITERATIVE_REVIEWER_ROLE_NAME)) {
        // Get "my" (reviewer's) resource
        reviewers = new Resource[1];
        reviewers[0] =
            ActionsHelper.getMyResourceForRole(
                myResources, ORConstants.ITERATIVE_REVIEWER_ROLE_NAME);
      }

      if (reviewers == null) {
        List<Resource> resources = new ArrayList<Resource>();
        for (Resource resource : allProjectResources) {
          if (resource
              .getResourceRole()
              .getName()
              .equals(ORConstants.ITERATIVE_REVIEWER_ROLE_NAME)) {
            resources.add(resource);
          }
        }
        reviewers = resources.toArray(new Resource[resources.size()]);
      }

      // Put collected reviewers into the phase group
      phaseGroup.setIterativeReviewers(reviewers);
      // A safety check: create an empty array in case reviewers is null
      if (reviewers == null) {
        phaseGroup.setIterativeReviewers(new Resource[0]);
      }

      List<Long> reviewerIds = new ArrayList<Long>();
      for (Resource reviewer : reviewers) {
        reviewerIds.add(reviewer.getId());
      }

      if (ungroupedReviews == null) {
        ungroupedReviews = new Review[0];
      }

      Review[] reviews = new Review[reviewerIds.size()];
      Date[] reviewDates = new Date[1];

      Date latestDate = null;
      for (int k = 0; k < reviewerIds.size(); ++k) {
        for (Review ungrouped : ungroupedReviews) {
          if (ungrouped.getAuthor() == reviewers[k].getId()) {
            reviews[k] = ungrouped;
            if (!ungrouped.isCommitted()) {
              continue;
            }
            if (latestDate == null || latestDate.before(ungrouped.getModificationTimestamp())) {
              latestDate = ungrouped.getModificationTimestamp();
            }
          }
        }
      }

      reviewDates[0] = latestDate;
      phaseGroup.setIterativeReviewReviews(reviews);
      phaseGroup.setReviewDates(reviewDates);
      phaseGroup.setIterativeReviewPhase(phase);
    }
  }

  /**
   * Retrieve submissions.
   *
   * @param myResources the request
   * @param phaseGroup the phase group
   * @param project the project
   * @param mostRecentContestSubmissions the most recent contest submissions
   * @param isAfterAppealsResponse the is after appeals response
   * @throws BaseException the base exception
   */
  private static void retrieveSubmissions(
      UploadManager uploadManager,
      LookupHelper lookupHelper,
      String roles,
      Resource[] myResources,
      PhaseGroup phaseGroup,
      Project project,
      Submission[] mostRecentContestSubmissions,
      boolean isAfterAppealsResponse)
      throws BaseException {
    if (phaseGroup.getSubmitters() == null || phaseGroup.getSubmissions() != null) {
      return;
    }

    Submission[] submissions = null;

    boolean mayViewMostRecentAfterAppealsResponse =
        hasUserPermission(roles, ORConstants.VIEW_RECENT_SUBM_AAR_PERM_NAME);

    if ((mayViewMostRecentAfterAppealsResponse && isAfterAppealsResponse)
        || hasUserPermission(roles, ORConstants.VIEW_ALL_SUBM_PERM_NAME)
        || hasUserPermission(roles, ORConstants.VIEW_RECENT_SUBM_PERM_NAME)
        || hasUserPermission(roles, ORConstants.VIEW_WINNING_SUBM_PERM_NAME)) {
      submissions = mostRecentContestSubmissions;
    }
    if (submissions == null && hasUserPermission(roles, ORConstants.VIEW_MY_SUBM_PERM_NAME)) {

      // Get "my" (submitter's) resource
      Resource myResource = null;
      for (Resource resource : myResources) {
        if (resource.getResourceRole().getName().equals("Submitter")) {
          myResource = resource;
          break;
        }
      }

      if (myResource == null) {
        throw new BaseException(
            "Unable to find the Submitter resource "
                + "associated with the current user for project "
                + project.getId());
      }

      submissions =
          ActionsHelper.getResourceSubmissions(
              uploadManager,
              lookupHelper,
              myResource.getId(),
              ORConstants.CONTEST_SUBMISSION_TYPE_NAME,
              ORConstants.ACTIVE_SUBMISSION_STATUS_NAME,
              false);
    }
    if (submissions != null) {
      phaseGroup.setSubmissions(submissions);
    }
  }

  /**
   * Gets the submitters.
   *
   * @param allProjectResources the all project resources
   * @param isAfterAppealsResponse the is after appeals response
   * @param isStudioScreening the is studio screening
   * @param prevSubmitters the prev submitters
   * @return the submitters
   */
  private static Resource[] getSubmitters(
      String roles,
      Resource[] allProjectResources,
      boolean isAfterAppealsResponse,
      boolean isStudioScreening,
      Resource[] prevSubmitters) {
    final boolean canSeeSubmitters =
        (isAfterAppealsResponse
            || hasUserPermission(roles, ORConstants.VIEW_ALL_SUBM_PERM_NAME)
            || (isStudioScreening
                && (hasUserRole(roles, ORConstants.SCREENER_ROLE_NAME)
                    || hasUserRole(roles, ORConstants.CHECKPOINT_SCREENER_ROLE_NAME))));

    if (!canSeeSubmitters) {
      return null;
    }

    return (prevSubmitters != null)
        ? prevSubmitters
        : ActionsHelper.getAllSubmitters(allProjectResources);
  }

  /**
   * This static method counts the number of total and unresolved appeals for
   * every review in the provided array of reviews. The other two arrays
   * (specified by parameters <code>totalAppeals</code> and
   * <code>unresolvedAppeals</code>) must be of the same length as the array
   * specified by <code>reviews<code> parameter.
   *
   * &#64;param reviews
   *            a two-dimensional array of reviews to count appeals counts in.
   * &#64;param totalAppeals
   *            specifies an array that on output will receive the amount of total appeals per
   *            every review in <code>reviews</code> input array.
   *
   * @param unresolvedAppeals specifies an array that on output will receive the
   *                          amount of unresolved appeals per every review in
   *                          <code>reviews</code> input array.
   * @throws IllegalArgumentException if any of the parameters are
   *                                  <code>null</code>, or if the number of items
   *                                  in <code>totalAppeals</code> or
   *                                  <code>unresolvedAppeals</code> arrays does
   *                                  not match the number of items in the
   *                                  <code>reviews</code> array.
   */
  private static void countAppeals(
      Review[][] reviews, int[][] totalAppeals, int[][] unresolvedAppeals) {
    // Validate parameters
    ActionsHelper.validateParameterNotNull(reviews, "reviews");
    ActionsHelper.validateParameterNotNull(totalAppeals, "totalAppeals");
    ActionsHelper.validateParameterNotNull(unresolvedAppeals, "unresolvedAppeals");

    // Validate array lengths
    if (reviews.length != totalAppeals.length) {
      throw new IllegalArgumentException(
          "The number of items in 'reviews' array ("
              + reviews.length
              + ")"
              + " does not match the number of items in 'totalAppeals' array ("
              + totalAppeals.length
              + ").");
    }
    if (reviews.length != unresolvedAppeals.length) {
      throw new IllegalArgumentException(
          "The number of items in 'reviews' array ("
              + reviews.length
              + ")"
              + " does not match the number of items in 'unresolvedAppeals' array ("
              + unresolvedAppeals.length
              + ").");
    }

    for (int i = 0; i < reviews.length; ++i) {
      Review[] innerReviews = reviews[i];
      int[] innerTotalAppeals = new int[innerReviews.length];
      int[] innerUnresolvedAppeals = new int[innerReviews.length];

      for (int j = 0; j < innerReviews.length; ++j) {
        Review review = innerReviews[j];

        if (review == null) {
          continue;
        }

        for (int itemIdx = 0; itemIdx < review.getNumberOfItems(); ++itemIdx) {
          Item item = review.getItem(itemIdx);
          boolean appealFound = false;
          boolean appealResolved = false;

          for (int commentIdx = 0;
              commentIdx < item.getNumberOfComments() && !(appealFound && appealResolved);
              ++commentIdx) {
            String commentType = item.getComment(commentIdx).getCommentType().getName();

            if (!appealFound && commentType.equalsIgnoreCase("Appeal")) {
              appealFound = true;
              continue;
            }
            if (!appealResolved && commentType.equalsIgnoreCase("Appeal Response")) {
              appealResolved = true;
            }
          }

          if (appealFound) {
            ++innerTotalAppeals[j];
            if (!appealResolved) {
              ++innerUnresolvedAppeals[j];
            }
          }
        }
      }

      totalAppeals[i] = innerTotalAppeals;
      unresolvedAppeals[i] = innerUnresolvedAppeals;
    }
  }
}
