package com.topcoder.onlinereview.service;

import com.topcoder.onlinereview.component.deliverable.Submission;
import com.topcoder.onlinereview.component.exception.BaseException;
import com.topcoder.onlinereview.component.external.ExternalUser;
import com.topcoder.onlinereview.component.external.UserRetrieval;
import com.topcoder.onlinereview.component.or.model.ClientProject;
import com.topcoder.onlinereview.component.or.model.CockpitProject;
import com.topcoder.onlinereview.component.or.util.ActionsHelper;
import com.topcoder.onlinereview.component.or.util.Comparators;
import com.topcoder.onlinereview.component.or.util.ConfigHelper;
import com.topcoder.onlinereview.component.or.util.CorrectnessCheckResult;
import com.topcoder.onlinereview.component.or.util.LookupHelper;
import com.topcoder.onlinereview.component.or.util.ORConstants;
import com.topcoder.onlinereview.component.or.util.PhasesDetailsServices;
import com.topcoder.onlinereview.component.project.management.Prize;
import com.topcoder.onlinereview.component.project.management.PrizeType;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.management.ProjectManager;
import com.topcoder.onlinereview.component.project.management.ProjectType;
import com.topcoder.onlinereview.component.project.payment.ProjectPayment;
import com.topcoder.onlinereview.component.project.phase.Dependency;
import com.topcoder.onlinereview.component.project.phase.Phase;
import com.topcoder.onlinereview.component.project.phase.PhaseManager;
import com.topcoder.onlinereview.component.project.phase.PhaseStatus;
import com.topcoder.onlinereview.component.project.phase.PhaseType;
import com.topcoder.onlinereview.component.resource.Resource;
import com.topcoder.onlinereview.component.resource.ResourceFilterBuilder;
import com.topcoder.onlinereview.component.resource.ResourceManager;
import com.topcoder.onlinereview.component.resource.ResourceRole;
import com.topcoder.onlinereview.component.scorecard.Scorecard;
import com.topcoder.onlinereview.component.scorecard.ScorecardManager;
import com.topcoder.onlinereview.component.scorecard.ScorecardSearchBundle;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.dto.EditProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Service
public class EditProjectService {
  @Autowired private ProjectManager projectManager;
  @Autowired private ResourceManager resourceManager;
  @Autowired private PhaseManager phaseManager;
  @Autowired private LookupHelper lookupHelper;
  @Autowired private ScorecardManager scorecardManager;
  @Autowired private MessageSource messageSource;
  @Autowired private UserRetrieval userRetrieval;

  public EditProjectResponse editProject() throws BaseException {

    EditProjectResponse response = new EditProjectResponse();
    // Verify that certain requirements are met before processing with the Action
    CorrectnessCheckResult verification =
        ActionsHelper.checkForCorrectProjectId(
            messageSource, request, ORConstants.EDIT_PROJECT_DETAILS_PERM_NAME, false);

    // If any error has occurred, return action forward contained in the result bean
    if (!verification.isSuccessful()) {
      //      return verification.getResult();
    }

    setEditProjectFormData(response, verification);

    // Populate the form with project properties
    populateProjectForm(response, verification.getProject());

    return response;
  }

  /**
   * Sets the basic form data for <code>Edit Project</code> screen.
   *
   * @param response an <code>HttpServletRequest</code> representing incoming request from the
   *     client.
   * @param verification a <code>CorrectnessCheckResult</code> providing the verification result.
   * @throws BaseException if an unexpected error occurs.
   */
  private void setEditProjectFormData(
      EditProjectResponse response, CorrectnessCheckResult verification) throws BaseException {
    // Load the lookup data
    loadProjectEditLookups(response);
    String phaseDependenciesEditable = null;
    if (verification != null) {
      phaseDependenciesEditable =
          (String) verification.getProject().getProperty("Phase Dependencies Editable");
    }
    response.setArePhaseDependenciesEditable("true".equalsIgnoreCase(phaseDependenciesEditable));
    // Place the flag, indicating that we are editing the existing project, into request
    response.setNewProject(Boolean.FALSE);
    response.setProjectStatuses(projectManager.getAllProjectStatuses());
    response.setProject(verification.getProject());

    populateProjectFormDefaults(response);
  }

  protected void populateProjectFormDefaults(EditProjectResponse response) {
    // Set the JS id to start generation from
    response.set("js_current_id", 0);

    // Populate form with some data so that resources row template
    // is rendered properly by the appropriate JSP
    response.set("resources_role", 0, -1);
    response.set("resources_id", 0, -1);
    response.set("resources_action", 0, "add");

    // Populate form with some data so that phases row template
    // is rendered properly by the appropriate JSP
    response.set("phase_id", 0, -1);
    response.set("phase_action", 0, "add");
    response.set("phase_can_open", 0, Boolean.TRUE);
    response.set("phase_can_close", 0, Boolean.FALSE);
    response.set("phase_use_duration", 0, Boolean.TRUE);
    // Populate default phase duration
    response.set("addphase_duration", 168);
  }

  /**
   * This method populates the specified LazyValidatorForm with the values taken from the specified
   * Project.
   *
   * @param response the request to be processed
   * @param project the project to take the data from
   * @throws BaseException if any error
   */
  @SuppressWarnings("unchecked")
  private void populateProjectForm(EditProjectResponse response, Project project)
      throws BaseException {
    // Populate project id
    response.set("pid", project.getId());

    // Populate project name
    populateProjectFormProperty(response, "project_name", project, "Project Name");

    // Populate project type
    Long projectTypeId = project.getProjectCategory().getProjectType().getId();
    response.set("project_type", projectTypeId);

    // Populate project category
    Long projectCategoryId = project.getProjectCategory().getId();
    response.set("project_category", projectCategoryId);

    // Populate project category
    Long projectStatusId = project.getProjectStatus().getId();
    response.set("status", projectStatusId);

    // Populate project forum id
    populateProjectFormProperty(response, "forum_id", project, "Developer Forum ID");

    // Populate project component id
    populateProjectFormProperty(response, "component_id", project, "Component ID");
    // Populate project external reference id
    populateProjectFormProperty(
        response, "external_reference_id", project, "External Reference ID");
    // Populate project dr points
    populateProjectFormProperty(response, "dr_points", project, "DR points");

    // since Online Review Update - Add Project Dropdown v1.0
    // Populate project billing project
    populateProjectFormProperty(response, "billing_project", project, "Billing Project");
    response.set("cockpit_project", project.getTcDirectProjectId());

    // Populate project autopilot option
    response.set("autopilot", "On".equals(project.getProperty("Autopilot Option")));
    // Populate project status notification option
    response.set("email_notifications", "On".equals(project.getProperty("Status Notification")));
    // Populate project timeline notification option
    response.set(
        "timeline_notifications", "On".equals(project.getProperty("Timeline Notification")));
    // Populate project Digital Run option
    response.set("digital_run_flag", "On".equals(project.getProperty("Digital Run Flag")));
    // Populate project's 'do not rate this project' option
    // Note, this property is inverse by its meaning in project and form
    response.set("no_rate_project", !("Yes".equals(project.getProperty("Rated"))));

    // Populate project SVN module
    populateProjectFormProperty(response, "SVN_module", project, "SVN Module");
    // Populate project notes
    populateProjectFormProperty(response, "notes", project, "Notes");
    // Retrieve the list of the resources associated with the project
    Resource[] resources =
        resourceManager.searchResources(
            ResourceFilterBuilder.createProjectIdFilter(project.getId()));
    // Get an array of external users for the corresponding resources
    ExternalUser[] externalUsers =
        ActionsHelper.getExternalUsersForResources(userRetrieval, resources);

    // Populate form with resources data
    for (int i = 0; i < resources.length; ++i) {
      response.set("resources_id", i + 1, resources[i].getId());
      response.set("resources_action", i + 1, "update");

      response.set("resources_role", i + 1, resources[i].getResourceRole().getId());
      response.set("resources_phase", i + 1, "loaded_" + resources[i].getPhase());
      response.set("resources_name", i + 1, externalUsers[i].getHandle());
    }

    // Populate project prizes to form
    List<Prize> prizes = project.getPrizes();

    if (prizes != null) {
      PrizeType contestPrize = lookupHelper.getPrizeType(ORConstants.CONTEST_PRIZE_TYPE_NAME);
      PrizeType checkpointPrize = lookupHelper.getPrizeType(ORConstants.CHECKPOINT_PRIZE_TYPE_NAME);

      for (Prize prize : prizes) {
        if (prize.getPrizeType().getId() == contestPrize.getId()) {
          response.set(
              "contest_prizes_amount",
              prize.getPlace() - 1,
              String.valueOf(prize.getPrizeAmount()));
          response.set("contest_prizes_num", prize.getPlace() - 1, prize.getNumberOfSubmissions());
        } else if (prize.getPrizeType().getId() == checkpointPrize.getId()) {
          response.set(
              "checkpoint_prizes_amount",
              prize.getPlace() - 1,
              String.valueOf(prize.getPrizeAmount()));
          response.set(
              "checkpoint_prizes_num", prize.getPlace() - 1, prize.getNumberOfSubmissions());
        }
      }
    }
    // Retrieve project phases
    Phase[] phases = ActionsHelper.getPhasesForProject(phaseManager, project);
    // Sort project phases
    Arrays.sort(phases, new Comparators.ProjectPhaseComparer());

    setEditProjectPhasesData(response, phases, false);
    setEditProjectRequestAttributes(request, project, resources, externalUsers, phases);

    // Populate last modification timestamp
    response.set(
        "last_modification_time", ActionsHelper.getLastModificationTime(project, phases).getTime());

    boolean isAdmin =
        AuthorizationHelper.hasUserRole(request, ORConstants.MANAGER_ROLE_NAME)
            || AuthorizationHelper.hasUserRole(request, ORConstants.COCKPIT_PROJECT_USER_ROLE_NAME)
            || AuthorizationHelper.hasUserRole(request, ORConstants.GLOBAL_MANAGER_ROLE_NAME);
    request.setAttribute("isAdmin", isAdmin);

    // start BUGR 4039 - Check whether the billing project id is in the user's allowed billing
    // projects list
    List<ClientProject> availableClientProjects = ActionsHelper.getClientProjects(request);
    Long currentClientProjectId = (Long) getModel().get("billing_project");
    boolean inList = false;

    if (currentClientProjectId == null) {
      // no billing project yet, allow set
      inList = true;
    } else {
      for (ClientProject cp : availableClientProjects) {
        if (cp.getId() == currentClientProjectId) {
          inList = true;

          break;
        }
      }
    }

    request.setAttribute("allowBillingEdit", isAdmin && inList);

    // end BUG-4039
    List<CockpitProject> availableCockpitProjects = ActionsHelper.getCockpitProjects(request);
    Long currentCockpitProjectId = (Long) getModel().get("cockpit_project");
    inList = false;

    if (currentCockpitProjectId == null) {
      inList = true;
    } else {
      for (CockpitProject cockpitProject : availableCockpitProjects) {
        if (cockpitProject.getId() == currentCockpitProjectId) {
          inList = true;

          break;
        }
      }
    }

    request.setAttribute("allowCockpitProjectEdit", isAdmin && inList);
  }

  /**
   * This method populates as single property of the project form by the value taken from the
   * specified Project instance.
   *
   * @param response the type of form property to be populated
   * @param formProperty the name of form property to be populated
   * @param project the project to take the value of property of
   * @param projectProperty the name of project property to take the value of
   */
  private void populateProjectFormProperty(
      EditProjectResponse response, String formProperty, Project project, String projectProperty) {
    String value = (String) project.getProperty(projectProperty);
    if (value != null) {
      response.set(formProperty, value);
    }
  }

  private void setEditProjectPhasesData(
      EditProjectResponse response, Phase[] phases, boolean closedPhasesOnly) {
    Map<Long, Integer> phaseNumberMap = new HashMap<Long, Integer>();

    // Populate form with phases data
    for (int i = 0; i < phases.length; ++i) {
      if (closedPhasesOnly && phases[i].getPhaseStatus().getId() != 3) {
        continue;
      }

      response.set("phase_id", i + 1, phases[i].getId());

      response.set(
          "phase_can_open",
          i + 1,
          phases[i].getPhaseStatus().getName().equals(PhaseStatus.SCHEDULED.getName()));
      response.set(
          "phase_can_close",
          i + 1,
          phases[i].getPhaseStatus().getName().equals(PhaseStatus.OPEN.getName()));

      Long phaseTypeId = phases[i].getPhaseType().getId();
      response.set("phase_type", i + 1, phaseTypeId);

      Integer phaseNumber = phaseNumberMap.get(phaseTypeId);

      if (phaseNumber == null) {
        phaseNumber = 1;
      } else {
        phaseNumber = phaseNumber + 1;
      }

      phaseNumberMap.put(phaseTypeId, phaseNumber);
      response.set("phase_number", i + 1, phaseNumber);

      response.set("phase_name", i + 1, phases[i].getPhaseType().getName());
      response.set("phase_action", i + 1, "update");
      response.set("phase_js_id", i + 1, "loaded_" + phases[i].getId());

      if (phases[i].getAllDependencies().length > 0) {
        response.set("phase_start_by_phase", i + 1, Boolean.TRUE);

        Dependency dependency = phases[i].getAllDependencies()[0];
        response.set("phase_start_phase", i + 1, "loaded_" + dependency.getDependency().getId());
        response.set("phase_start_when", i + 1, dependency.isDependencyStart() ? "starts" : "ends");

        // lagTime may be negative
        long lagTime = dependency.getLagTime();

        if (lagTime < 0) {
          // negative lagTime
          response.set("phase_start_plusminus", i + 1, "minus");
          lagTime *= -1;
        }

        if (lagTime % (24 * 3600 * 1000L) == 0) {
          response.set("phase_start_dayshrs", i + 1, "days");
          response.set("phase_start_amount", i + 1, (int) (lagTime / (24 * 3600 * 1000L)));
        } else if (lagTime % (3600 * 1000L) == 0) {
          response.set("phase_start_dayshrs", i + 1, "hrs");
          response.set("phase_start_amount", i + 1, (int) (lagTime / (3600 * 1000L)));
        } else {
          response.set("phase_start_dayshrs", i + 1, "mins");
          response.set("phase_start_amount", i + 1, (int) (lagTime / (60 * 1000L)));
        }
      }

      if (phases[i].getFixedStartDate() != null) {
        response.set("phase_start_by_fixed_time", i + 1, Boolean.TRUE);
        populateDatetimeFormProperties(
            response, "phase_start_date", "phase_start_time", i + 1, phases[i].getFixedStartDate());
      }

      populateDatetimeFormProperties(
          response, "phase_end_date", "phase_end_time", i + 1, phases[i].calcEndDate());
      // always use duration
      response.set("phase_use_duration", i + 1, Boolean.TRUE);

      // populate the phase duration
      long phaseLength = phases[i].getLength();
      String phaseDuration;
      if (phaseLength % (3600 * 1000) == 0) {
        phaseDuration = "" + phaseLength / (3600 * 1000);
      } else {
        long hour = phaseLength / 3600 / 1000;
        long min = (phaseLength % (3600 * 1000)) / 1000 / 60;
        phaseDuration = hour + ":" + (min >= 10 ? "" + min : "0" + min);
      }

      response.set("phase_duration", i + 1, phaseDuration);

      // Populate phase criteria
      if (phases[i].getAttribute("Scorecard ID") != null) {
        response.set(
            "phase_scorecard",
            i + 1,
            Long.valueOf((String) phases[i].getAttribute("Scorecard ID")));
      }

      if (phases[i].getAttribute("Registration Number") != null) {
        response.set(
            "phase_required_registrations",
            i + 1,
            Integer.valueOf((String) phases[i].getAttribute("Registration Number")));
      }

      if (phases[i].getAttribute("Reviewer Number") != null) {
        response.set(
            "phase_required_reviewers",
            i + 1,
            Integer.valueOf((String) phases[i].getAttribute("Reviewer Number")));
      }

      if (phases[i].getAttribute("View Response During Appeals") != null) {
        response.set(
            "phase_view_appeal_responses",
            i + 1,
            Boolean.valueOf("Yes".equals(phases[i].getAttribute("View Response During Appeals"))));
      }
    }
  }

  private void populateDatetimeFormProperties(
      EditProjectResponse response,
      String dateProperty,
      String timeProperty,
      int index,
      Date date) {
    DateFormat dateFormat = new SimpleDateFormat("MM.dd.yy HH:mm", Locale.US);
    String[] parts = dateFormat.format(date).split("[ ]");
    response.set(dateProperty, index, parts[0]);
    response.set(timeProperty, index, parts[1]);
  }

  protected void loadProjectEditLookups(EditProjectResponse response) throws BaseException {
    // Retrieve project types and categories
    ProjectType[] projectTypes = projectManager.getAllProjectTypes();
    ProjectCategory[] projectCategories = projectManager.getAllProjectCategories();

    // Store the retrieved types and categories in request
    request.setAttribute("projectTypes", projectTypes);
    request.setAttribute("projectCategories", projectCategories);
    request.setAttribute(
        "projectCategoriesMap", buildProjectCategoriesLookupMap(projectCategories));

    // Obtain an instance of Resource Manager
    ResourceManager resourceManager = ActionsHelper.createResourceManager();
    // Get all types of resource roles and filter out those which are not allowed for selection
    // Place resource roles into the request as attribute
    ResourceRole[] resourceRoles = resourceManager.getAllResourceRoles();
    Set<String> disabledResourceRoles =
        new HashSet<String>(Arrays.asList(ConfigHelper.getDisabledResourceRoles()));
    List<ResourceRole> allowedResourceRoles = new ArrayList<ResourceRole>();
    for (ResourceRole resourceRole : resourceRoles) {
      if (!disabledResourceRoles.contains(String.valueOf(resourceRole.getId()))) {
        allowedResourceRoles.add(resourceRole);
      }
    }
    request.setAttribute("allowedResourceRoles", allowedResourceRoles);
    request.setAttribute("resourceRoles", resourceRoles);
    request.setAttribute("disabledResourceRoles", disabledResourceRoles);

    // Obtain an instance of Phase Manager
    PhaseManager phaseManager = ActionsHelper.createPhaseManager(false);
    // Get all phase types
    PhaseType[] phaseTypes = phaseManager.getAllPhaseTypes();
    // Place them into request as an attribute
    request.setAttribute("phaseTypes", phaseTypes);
    request.setAttribute("arePhaseDependenciesEditable", true);

    // Retrieve the scorecard lists
    Scorecard[] screeningScorecards = searchActiveScorecards(scorecardManager, "Screening");
    Scorecard[] reviewScorecards = searchActiveScorecards(scorecardManager, "Review");
    Scorecard[] approvalScorecards = searchActiveScorecards(scorecardManager, "Approval");
    Scorecard[] postMortemScorecards = searchActiveScorecards(scorecardManager, "Post-Mortem");
    Scorecard[] specificationReviewScorecards =
        searchActiveScorecards(scorecardManager, "Specification Review");
    Scorecard[] checkpointScreeningScorecards =
        searchActiveScorecards(scorecardManager, "Checkpoint Screening");
    Scorecard[] checkpointReviewScorecards =
        searchActiveScorecards(scorecardManager, "Checkpoint Review");
    Scorecard[] iterativeReviewScorecards =
        searchActiveScorecards(scorecardManager, "Iterative Review");

    // Store them in the request
    request.setAttribute("screeningScorecards", screeningScorecards);
    request.setAttribute("reviewScorecards", reviewScorecards);
    request.setAttribute("approvalScorecards", approvalScorecards);
    request.setAttribute("postMortemScorecards", postMortemScorecards);
    request.setAttribute("specificationReviewScorecards", specificationReviewScorecards);
    request.setAttribute("checkpointScreeningScorecards", checkpointScreeningScorecards);
    request.setAttribute("checkpointReviewScorecards", checkpointReviewScorecards);
    request.setAttribute("iterativeReviewScorecards", iterativeReviewScorecards);
    request.setAttribute("defaultScorecards", ActionsHelper.getDefaultScorecards());

    // Load phase template names
    String[] phaseTemplateNames = ActionsHelper.createPhaseTemplate().getAllTemplateNames();
    request.setAttribute("phaseTemplateNames", phaseTemplateNames);

    // since Online Review Update - Add Project Dropdown v1.0
    // Retrieve the list of all client projects and store it in the request
    // this need to be retrieved only for admin user.
    if (AuthorizationHelper.hasUserRole(request, ORConstants.MANAGER_ROLE_NAME)
        || AuthorizationHelper.hasUserRole(request, ORConstants.GLOBAL_MANAGER_ROLE_NAME)
        || AuthorizationHelper.hasUserRole(request, ORConstants.COCKPIT_PROJECT_USER_ROLE_NAME)) {
      request.setAttribute("billingProjects", ActionsHelper.getClientProjects(request));
      request.setAttribute("cockpitProjects", ActionsHelper.getCockpitProjects(request));
    }
  }

  private Scorecard[] searchActiveScorecards(
      ScorecardManager scorecardManager, String scorecardTypeName) throws BaseException {
    Filter filter =
        ScorecardSearchBundle.buildAndFilter(
            ScorecardSearchBundle.buildTypeNameEqualFilter(scorecardTypeName),
            ScorecardSearchBundle.buildStatusNameEqualFilter("Active"));
    return scorecardManager.searchScorecards(filter, false);
  }

  private static Map<Long, ProjectCategory> buildProjectCategoriesLookupMap(
      ProjectCategory[] categories) {
    Map<Long, ProjectCategory> map = new HashMap<>(categories.length);

    for (ProjectCategory category : categories) {
      map.put(category.getId(), category);
    }

    return map;
  }

  protected void setEditProjectRequestAttributes(HttpServletRequest request,
                                                 Project project, Resource[] resources, ExternalUser[] externalUsers,
                                                 Phase[] phases) throws BaseException {
    PhasesDetails phasesDetails = PhasesDetailsServices.getPhasesDetails(request,
            this, project, phases, resources, externalUsers);

    request.setAttribute("phaseGroupIndexes", phasesDetails.getPhaseGroupIndexes());
    request.setAttribute("phaseGroups", phasesDetails.getPhaseGroups());
    request.setAttribute("activeTabIdx", phasesDetails.getActiveTabIndex());

    request.setAttribute("isManager",
            AuthorizationHelper.hasUserRole(request, ORConstants.MANAGER_ROLE_NAMES));
    request.setAttribute("isAllowedToPerformScreening",
            AuthorizationHelper.hasUserPermission(request, ORConstants.PERFORM_SCREENING_PERM_NAME) &&
                    ActionsHelper.getPhase(phases, true, ORConstants.SCREENING_PHASE_NAME) != null);
    request.setAttribute("isAllowedToPerformCheckpointScreening",
            AuthorizationHelper.hasUserPermission(request, ORConstants.PERFORM_CHECKPOINT_SCREENING_PERM_NAME) &&
                    ActionsHelper.getPhase(phases, true, ORConstants.CHECKPOINT_SCREENING_PHASE_NAME) != null);
    request.setAttribute("isAllowedToPerformCheckpointReview",
            AuthorizationHelper.hasUserPermission(request, ORConstants.PERFORM_CHECKPOINT_REVIEW_PERM_NAME) &&
                    ActionsHelper.getPhase(phases, true, ORConstants.CHECKPOINT_REVIEW_PHASE_NAME) != null);
    request.setAttribute("isAllowedToViewScreening",
            AuthorizationHelper.hasUserPermission(request, ORConstants.VIEW_SCREENING_PERM_NAME));
    request.setAttribute("isAllowedToViewCheckpointScreening",
            AuthorizationHelper.hasUserPermission(request, ORConstants.VIEW_CHECKPOINT_SCREENING_PERM_NAME));
    request.setAttribute("isAllowedToViewCheckpointReview",
            AuthorizationHelper.hasUserPermission(request, ORConstants.VIEW_CHECKPOINT_REVIEW_PERM_NAME));
    request.setAttribute("isAllowedToUploadTC",
            AuthorizationHelper.hasUserPermission(request, ORConstants.UPLOAD_TEST_CASES_PERM_NAME));
    request.setAttribute("isAllowedToPerformAggregation",
            AuthorizationHelper.hasUserPermission(request, ORConstants.PERFORM_AGGREGATION_PERM_NAME));
    request.setAttribute("isAllowedToPerformAggregationReview",
            AuthorizationHelper.hasUserPermission(request, ORConstants.PERFORM_AGGREG_REVIEW_PERM_NAME) &&
                    !AuthorizationHelper.hasUserPermission(request, ORConstants.PERFORM_AGGREGATION_PERM_NAME));
    request.setAttribute("isAllowedToUploadFF",
            AuthorizationHelper.hasUserPermission(request, ORConstants.PERFORM_FINAL_FIX_PERM_NAME));
    request.setAttribute("isAllowedToPerformFinalReview",
            ActionsHelper.getPhase(phases, true, ORConstants.FINAL_REVIEW_PHASE_NAME) != null &&
                    AuthorizationHelper.hasUserPermission(request, ORConstants.PERFORM_FINAL_REVIEW_PERM_NAME));
    request.setAttribute("isAllowedToPerformApproval",
            ActionsHelper.getPhase(phases, true, ORConstants.APPROVAL_PHASE_NAME) != null &&
                    AuthorizationHelper.hasUserPermission(request, ORConstants.PERFORM_APPROVAL_PERM_NAME));
    request.setAttribute("isAllowedToPerformPortMortemReview",
            ActionsHelper.getPhase(phases, true, ORConstants.POST_MORTEM_PHASE_NAME) != null &&
                    AuthorizationHelper.hasUserPermission(request, ORConstants.PERFORM_POST_MORTEM_REVIEW_PERM_NAME));

    collectTrueSubmittersAndReviewers(project, resources, ActionsHelper.createReviewManager(), request);

    boolean[] canEditPrizes = canEditPrize(project.getId());

    request.setAttribute("canEditContestPrize", canEditPrizes[0]);
    request.setAttribute("canEditCheckpointPrize", canEditPrizes[1]);

    setResourcePaidRequestAttribute(project, request);
  }

  protected boolean[] canEditPrize(long projectId) throws BaseException {
    boolean[] ret = {true, true};
    if (projectId <= 0) {
      // new project
      return ret;
    }
    Submission[] contestSubmissions = ActionsHelper.getProjectSubmissions(projectId,
            ORConstants.CONTEST_SUBMISSION_TYPE_NAME, null, false);
    Submission[] checkpointSubmissions = ActionsHelper.getProjectSubmissions(projectId,
            ORConstants.CHECKPOINT_SUBMISSION_TYPE_NAME, null, false);
    for (Submission sub : contestSubmissions) {
      if (sub.getPrize() != null) {
        ret[0] = false;
        break;
      }
    }
    for (Submission sub : checkpointSubmissions) {
      if (sub.getPrize() != null) {
        ret[1] = false;
        break;
      }
    }
    return ret;
  }

  protected void setResourcePaidRequestAttribute(Project project, HttpServletRequest request)
          throws BaseException {
    if (request.getAttribute("resourcePaid") != null) {
      return;
    }

    Map<Long, Boolean> resourcePaid = new HashMap<Long, Boolean>();

    if (project.getId() > 0) {
      List<ProjectPayment> allPayments = ActionsHelper.createProjectPaymentManager().search(
              ProjectPaymentFilterBuilder.createProjectIdFilter(project.getId()));
      request.setAttribute("allPayments", allPayments);

      for (ProjectPayment payment : allPayments) {
        if (payment.getPactsPaymentId() != null) {
          resourcePaid.put(payment.getResourceId(), Boolean.TRUE);
        }
      }
    }

    request.setAttribute("resourcePaid", resourcePaid);
  }
}
