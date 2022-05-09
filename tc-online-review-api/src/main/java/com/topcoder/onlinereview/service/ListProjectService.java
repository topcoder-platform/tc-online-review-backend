package com.topcoder.onlinereview.service;

import com.topcoder.onlinereview.component.contest.ContestEligibilityService;
import com.topcoder.onlinereview.component.deliverable.Deliverable;
import com.topcoder.onlinereview.component.deliverable.DeliverableCheckingException;
import com.topcoder.onlinereview.component.deliverable.DeliverableManager;
import com.topcoder.onlinereview.component.deliverable.DeliverablePersistenceException;
import com.topcoder.onlinereview.component.exception.BaseException;
import com.topcoder.onlinereview.component.or.dataaccess.DeliverableDataAccess;
import com.topcoder.onlinereview.component.or.dataaccess.ProjectDataAccess;
import com.topcoder.onlinereview.component.or.dataaccess.ProjectPhaseDataAccess;
import com.topcoder.onlinereview.component.or.util.ActionsHelper;
import com.topcoder.onlinereview.component.or.util.Comparators;
import com.topcoder.onlinereview.component.or.util.LookupHelper;
import com.topcoder.onlinereview.component.or.util.ORConstants;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.management.ProjectManager;
import com.topcoder.onlinereview.component.project.management.ProjectPropertyType;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import com.topcoder.onlinereview.component.project.management.ProjectType;
import com.topcoder.onlinereview.component.project.phase.Phase;
import com.topcoder.onlinereview.component.project.phase.PhaseManager;
import com.topcoder.onlinereview.component.project.phase.PhaseStatus;
import com.topcoder.onlinereview.component.project.phase.PhaseType;
import com.topcoder.onlinereview.component.resource.Resource;
import com.topcoder.onlinereview.component.search.SearchBuilderException;
import com.topcoder.onlinereview.component.search.filter.AndFilter;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.component.search.filter.InFilter;
import com.topcoder.onlinereview.dto.ListProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ListProjectService {
  @Autowired private ProjectManager projectManager;
  @Autowired private DeliverableManager deliverableManager;
  @Autowired private PhaseManager phaseManager;
  @Autowired private ContestEligibilityService contestEligibilityService;
  @Autowired private LookupHelper lookupHelper;
  @Autowired private ProjectDataAccess projectDataAccess;
  @Autowired private ProjectPhaseDataAccess phasesDataAccess;
  @Autowired private DeliverableDataAccess deliverableDataAccess;

  public ListProjectResponse listProjects(Integer activeTab, String scope, long userId, String role)
      throws BaseException {
    var result = new ListProjectResponse();
    // Get all project types defined in the database (e.g. Assembly, Component, etc.)
    ProjectType[] projectTypes = projectManager.getAllProjectTypes();

    // Sort project types by their names in ascending order
    Arrays.sort(projectTypes, new Comparators.ProjectTypeComparer());

    // Get all project categories defined in the database (e.g. Design, Security, etc.)
    ProjectCategory[] projectCategories = projectManager.getAllProjectCategories();

    result.setProjectTypes(projectTypes);
    result.setProjectCategories(projectCategories);

    ProjectPropertyType[] projectInfoTypes = projectManager.getAllProjectPropertyTypes();

    int[] typeCounts = new int[projectTypes.length];
    int[] categoryCounts = new int[projectCategories.length];
    String[] categoryIconNames = new String[projectCategories.length];

    // This is to signify whether "My" Projects list is displayed, or any other
    // type of Projects List. Some columns are present only in "My" Projects List
    boolean myProjects = scope.equalsIgnoreCase("my");

    Project[][] projects = new Project[projectCategories.length][];
    Phase[][][] phases = new Phase[projectCategories.length][][];
    Date[][] phaseEndDates = new Date[projectCategories.length][];
    Date[][] projectEndDates = new Date[projectCategories.length][];

    // The following will only be non-null for the list of "My" Projects
    Resource[][][] myResources = (myProjects) ? new Resource[projectCategories.length][][] : null;
    String[][] myRoles = (myProjects) ? new String[projectCategories.length][] : null;

    // Fetch projects from the database. These projects will require further grouping
    ProjectStatus draftStatus = lookupHelper.getProjectStatus("Draft");
    ProjectStatus activeStatus = lookupHelper.getProjectStatus("Active");
    Project[] ungroupedProjects;
    ProjectStatus[] projectStatuses = projectManager.getAllProjectStatuses();

    if (activeTab != 1) {
      if (activeTab == 4) {
        ungroupedProjects =
            projectDataAccess.searchDraftProjects(
                projectStatuses, projectCategories, projectInfoTypes);
      } else {
        ungroupedProjects =
            projectDataAccess.searchActiveProjects(
                projectStatuses, projectCategories, projectInfoTypes);
      }
    } else {
      // user projects
      ungroupedProjects =
          projectDataAccess.searchUserActiveProjects(
              userId, projectStatuses, projectCategories, projectInfoTypes);
    }

    // Sort fetched projects. Currently sorting is done by projects' names only, in ascending order
    Arrays.sort(ungroupedProjects, new Comparators.ProjectNameComparer());

    List<Long> projectFilters = new ArrayList<Long>();

    for (Project ungroupedProject : ungroupedProjects) {
      projectFilters.add(ungroupedProject.getId());
    }

    Resource[] allMyResources = null;
    if (ungroupedProjects.length != 0 && userId > 0) {
      if (activeTab == 1) { // My projects
        allMyResources = ActionsHelper.searchUserResources(userId, activeStatus);
      } else if (activeTab == 2) { // Active projects
        allMyResources = ActionsHelper.searchUserResources(userId, activeStatus);
      } else if (activeTab == 4) { // Draft projects
        allMyResources = ActionsHelper.searchUserResources(userId, draftStatus);
      }
    }

    // new eligibility constraints
    // if the user is not a global manager and is seeing all projects eligibility checks need to be
    // performed
    if (!role.contains(ORConstants.GLOBAL_MANAGER_ROLE_NAME)
        && (scope.equalsIgnoreCase("all") || scope.equalsIgnoreCase("draft"))
        && projectFilters.size() > 0) {

      // remove those projects that the user can't see
      ungroupedProjects =
          filterUsingEligibilityConstraints(ungroupedProjects, projectFilters, allMyResources);
    }

    PhaseStatus[] phaseStatuses = phaseManager.getAllPhaseStatuses();

    PhaseType[] phaseTypes = phaseManager.getAllPhaseTypes();

    Map<Long, com.topcoder.onlinereview.component.project.phase.Project> phProjects;

    if (activeTab != 1) {
      if (activeTab == 4) {
        phProjects = phasesDataAccess.searchDraftProjectPhases(phaseStatuses, phaseTypes);
      } else {
        phProjects = phasesDataAccess.searchActiveProjectPhases(phaseStatuses, phaseTypes);
      }
    } else {
      // user projects
      phProjects = phasesDataAccess.searchUserProjectPhases(userId, phaseStatuses, phaseTypes);
    }

    for (int i = 0; i < projectCategories.length; ++i) {
      // Count number of projects in this category
      for (Project ungroupedProject : ungroupedProjects) {
        if (ungroupedProject.getProjectCategory().getId() == projectCategories[i].getId()) {
          ++categoryCounts[i];
        }
      }

      /*
       * Now, as the exact count of projects in this category is known,
       * it is possible to initialize arrays
       */
      Project[] projs = new Project[categoryCounts[i]]; // This Category's Projects
      Phase[][] phass = new Phase[categoryCounts[i]][]; // Projects' active Phases
      Date[] pheds = new Date[categoryCounts[i]]; // End date of every first active phase
      Date[] preds = new Date[categoryCounts[i]]; // Projects' end dates

      // No need to collect any Resources or Roles if
      // the list of projects is not just "My" Projects
      Resource[][] myRss = (myProjects) ? new Resource[categoryCounts[i]][] : null;
      String[] rols = (myProjects) ? new String[categoryCounts[i]] : null;

      if (categoryCounts[i] != 0) {
        // Counter of projects currently added to this category
        int counter = 0;

        // Copy ungrouped projects into group of this category
        for (Project ungroupedProject : ungroupedProjects) {
          // Skip projects that are not in this category
          // (they'll be processed later, or have already been processed)
          if (ungroupedProject.getProjectCategory().getId() != projectCategories[i].getId()) {
            continue;
          }

          // Get this project's Root Catalog ID
          String rootCatalogId = (String) ungroupedProject.getProperty("Root Catalog ID");

          Phase[] activePhases = null;

          // Calculate end date of the project and get all active phases (if any)
          if (phProjects.containsKey(ungroupedProject.getId())) {
            com.topcoder.onlinereview.component.project.phase.Project phProject =
                phProjects.get(ungroupedProject.getId());
            preds[counter] = phProject.calcEndDate();
            activePhases = ActionsHelper.getActivePhases(phProject.getAllPhases());
            pheds[counter] = null;
          }

          // Get currently open phase end calculate its end date
          if (activePhases != null && activePhases.length != 0) {
            phass[counter] = activePhases;
            pheds[counter] = activePhases[0].getScheduledEndDate();
          }

          // Store project in a group and increment counter
          projs[counter] = ungroupedProject;
          ++counter;
        }
      }

      // Save collected data in main arrays
      projects[i] = projs;
      phases[i] = phass;
      phaseEndDates[i] = pheds;
      projectEndDates[i] = preds;

      // Resources and roles must not always be saved
      if (myProjects) {
        myResources[i] = myRss;
        myRoles[i] = rols;
      }
    }

    if (ungroupedProjects.length != 0 && myProjects) {
      Deliverable[] allMyDeliverables = getDeliverables(projects, phases, myResources);
      result.setAllMyDeliverables(allMyDeliverables);
    }

    int totalProjectsCount = 0;

    // Count projects in every type group now
    for (int i = 0; i < projectTypes.length; ++i) {
      for (int j = 0; j < projectCategories.length; ++j) {
        if (projectCategories[j].getProjectType().getId() == projectTypes[i].getId()) {
          typeCounts[i] += categoryCounts[j];
        }
      }

      totalProjectsCount += typeCounts[i];
    }

    // Place all collected data into the request as attributes
    result.setProjects(projects);
    result.setPhases(phases);
    result.setPhaseEndDates(phaseEndDates);
    result.setProjectEndDates(projectEndDates);
    result.setTypeCounts(typeCounts);
    result.setCategoryCounts(categoryCounts);
    result.setTotalProjectsCount(totalProjectsCount);
    // If the currently displayed list is a list of "My" Projects, add some more attributes
    if (myProjects) {
      result.setMyRoles(myRoles);
    }
    return result;
  }

  /**
   * This method will return an array of <code>Project</code> with those projects the user can see
   * taking into consideration eligibility constraints.
   *
   * <p>The user can see all those "public" (no eligibility constraints) projects plus those
   * non-public projects where he is assigned as a resource.
   *
   * @param ungroupedProjects all project to be displayed
   * @param projectFilters all project ids to be displayed
   * @param allMyResources all resources the user has for the projects to be displayed
   * @return a <code>Project[]</code> with those projects that the user can see.
   * @throws BaseException if any error occurs during eligibility services call
   */
  private Project[] filterUsingEligibilityConstraints(
      Project[] ungroupedProjects, List<Long> projectFilters, Resource[] allMyResources)
      throws BaseException {
    // check which projects have eligibility constraints
    Set<Long> projectsWithEligibilityConstraints;

    try {
      projectsWithEligibilityConstraints =
          contestEligibilityService.haveEligibility(
              projectFilters.toArray(new Long[projectFilters.size()]), false);
    } catch (Exception e) {
      throw new BaseException("It was not possible to retrieve eligibility constraints", e);
    }

    // create a set of projects where the user is a resource
    Set<Long> resourceProjects = new HashSet<Long>();

    if (allMyResources != null) {
      for (Resource r : allMyResources) {
        resourceProjects.add(r.getProject());
      }
    }

    // user can see those projects with eligibility constraints where he is a resource, so remove
    // these
    // from the projectsWithEligibilityConstraints set
    projectsWithEligibilityConstraints.removeAll(resourceProjects);

    // finally remove those projects left in projectsWithEligibilityConstraints from
    // ungroupedProjects
    List<Project> visibleProjects = new ArrayList<Project>();

    for (Project p : ungroupedProjects) {
      if (!projectsWithEligibilityConstraints.contains(p.getId())) {
        visibleProjects.add(p);
      }
    }

    ungroupedProjects = visibleProjects.toArray(new Project[visibleProjects.size()]);

    return ungroupedProjects;
  }

  /**
   * This static method performs a search for all outstanding deliverables. The list of these
   * deliverables is returned as is, i.e. as one-dimensional array, and will require further
   * grouping.
   *
   * @return an array of outstanding (incomplete) deliverables.
   * @param projects an array of the projects to search the deliverables for.
   * @param phases an array of active phases for the projects specified by <code>projects</code>
   *     parameter. The deliverables found will only be related to these phases.
   * @param resources an array of resources to search the deliverables for. Each of the deliverables
   *     found will have to be completed by one of the resources from this array.
   * @throws IllegalArgumentException if any of the parameters are <code>null</code>.
   * @throws DeliverablePersistenceException if there is an error reading the persistence store.
   * @throws SearchBuilderException if there is an error executing the filter.
   * @throws DeliverableCheckingException if there is an error determining whether some Deliverable
   *     has been completed or not.
   */
  private Deliverable[] getDeliverables(
      Project[][] projects, Phase[][][] phases, Resource[][][] resources)
      throws DeliverablePersistenceException, DeliverableCheckingException, SearchBuilderException {

    Map<Long, Map<Long, Long>> deliverableTypes = deliverableDataAccess.getDeliverablesList();

    // Validate parameters
    ActionsHelper.validateParameterNotNull(projects, "projects");
    ActionsHelper.validateParameterNotNull(phases, "phases");
    ActionsHelper.validateParameterNotNull(resources, "resources");

    List<Long> projectIds = new ArrayList<Long>();
    List<Long> phaseTypeIds = new ArrayList<Long>();
    List<Long> resourceIds = new ArrayList<Long>();

    for (int i = 0; i < projects.length; ++i) {
      for (int j = 0; j < projects[i].length; ++j) {
        projectIds.add(projects[i][j].getId());

        // Get an array of active phases for the project
        Phase[] activePhases = phases[i][j];

        // If there are no active phases, no need to select deliverables for this project
        if (activePhases == null) {
          continue;
        }

        for (Phase activePhase : activePhases) {
          phaseTypeIds.add(activePhase.getId());
        }

        // Get an array of "my" resources for the active phases
        Resource[] myResources = resources[i][j];

        // If there are no "my" resources, skip the rest of the loop
        if (myResources == null) {
          continue;
        }

        // Filter out those resources which do not correspond to active phases. If resource has
        // phase set
        // explicitly (but is not one of the reviewer roles) then check if it's phase is in list of
        // active
        // phases; otherwise check if it's role has a deliverable for one of the active phases
        for (Resource myResource : myResources) {
          boolean toAdd = false;
          long resourceRoleId = myResource.getResourceRole().getId();
          boolean isReviewer =
              (resourceRoleId == 4)
                  || (resourceRoleId == 5)
                  || (resourceRoleId == 6)
                  || (resourceRoleId == 7);
          for (int m = 0; !toAdd && (m < activePhases.length); m++) {
            Phase activePhase = activePhases[m];
            if (myResource.getPhase() != null && !isReviewer) {
              toAdd = (activePhase.getId() == myResource.getPhase());
            } else {
              Map<Long, Long> roleDeliverables =
                  deliverableTypes.get(myResource.getResourceRole().getId());
              if (roleDeliverables != null) {
                if (roleDeliverables.containsKey(activePhase.getPhaseType().getId())) {
                  toAdd = true;
                }
              }
            }
          }

          if (toAdd) {
            resourceIds.add(myResource.getId());
          }
        }
      }
    }

    // If any of the sets is empty, there cannot be any deliverables
    if (projectIds.isEmpty() || phaseTypeIds.isEmpty() || resourceIds.isEmpty()) {
      return new Deliverable[0]; // No deliverables
    }

    // Build filters to select deliverables
    Filter filterProjects = new InFilter("project_id", projectIds);
    Filter filterPhases = new InFilter("phase_id", phaseTypeIds);
    Filter filterResources = new InFilter("resource_id", resourceIds);
    // Build final combined filter
    Filter filter = new AndFilter(Arrays.asList(filterProjects, filterPhases, filterResources));

    // Get and return an array of my incomplete deliverables for all active phases.
    // These deliverables will require further grouping
    return deliverableManager.searchDeliverables(filter, Boolean.FALSE);
  }
}
