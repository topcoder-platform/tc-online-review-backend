/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.or.dataaccess;

import com.topcoder.onlinereview.component.or.model.ClientProject;
import com.topcoder.onlinereview.component.or.model.CockpitProject;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.management.ProjectPropertyType;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.component.or.dataaccess.BaseDataAccess.PROJECT_STATUS_ACTIVE_ID;
import static com.topcoder.onlinereview.component.or.dataaccess.BaseDataAccess.PROJECT_STATUS_DRAFT_ID;
import static com.topcoder.onlinereview.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.util.CommonUtils.getString;

/**
 * A simple DAO for projects backed up by Query Tool. Changes in version 2.1 Topcoder - Add Group
 * Permission Check For Adding Resources v1.0 - add the checkUserChallengeEligibility method to
 * check user group permission
 *
 * @author TCSASSEMBLER
 * @version 2.1
 */
@Slf4j
@Component
public class ProjectDataAccess {
  @Autowired private BaseDataAccess baseDataAccess;

  @Autowired
  @Qualifier("idsEntityManager")
  private EntityManager tcsEntityManager;

  @Autowired
  @Qualifier("oltpEntityManager")
  private EntityManager oltpEntityManager;

  /**
   * Gets all active projects.
   *
   * @param projectStatuses a <code>ProjectStatus</code> array listing the available project
   *     statuses.
   * @param projectCategories a <code>ProjectCategory</code> array listing available project
   *     categories.
   * @param projectInfoTypes a <code>ProjectPropertyType</code> listing available project info
   *     types.
   * @return a <code>Project</code> array listing the projects of specified status.
   * @throws DataAccessException if an unexpected error occurs while running the query via Query
   *     Tool.
   */
  public Project[] searchActiveProjects(
      ProjectStatus[] projectStatuses,
      ProjectCategory[] projectCategories,
      ProjectPropertyType[] projectInfoTypes) {
    return searchProjectsByQueryTool(
        "tcs_projects_by_status",
        "tcs_project_infos_by_status",
        "stid",
        String.valueOf(PROJECT_STATUS_ACTIVE_ID),
        projectStatuses,
        projectCategories,
        projectInfoTypes);
  }

  /**
   * Gets all draft projects.
   *
   * @param projectStatuses a <code>ProjectStatus</code> array listing the available project
   *     statuses.
   * @param projectCategories a <code>ProjectCategory</code> array listing available project
   *     categories.
   * @param projectInfoTypes a <code>ProjectPropertyType</code> listing available project info
   *     types.
   * @return a <code>Project</code> array listing the projects of specified status.
   * @throws DataAccessException if an unexpected error occurs while running the query via Query
   *     Tool.
   */
  public Project[] searchDraftProjects(
      ProjectStatus[] projectStatuses,
      ProjectCategory[] projectCategories,
      ProjectPropertyType[] projectInfoTypes) {
    return searchProjectsByQueryTool(
        "tcs_projects_by_status",
        "tcs_project_infos_by_status",
        "stid",
        String.valueOf(PROJECT_STATUS_DRAFT_ID),
        projectStatuses,
        projectCategories,
        projectInfoTypes);
  }

  /**
   * Gets all active projects assigned to specified user.
   *
   * @param userId a <code>long</code> providing the user ID.
   * @param projectStatuses a <code>ProjectStatus</code> array listing the available project
   *     statuses.
   * @param projectCategories a <code>ProjectCategory</code> array listing available project
   *     categories.
   * @param projectInfoTypes a <code>ProjectPropertyType</code> listing available project info
   *     types.
   * @return a <code>Project</code> array listing the projects of specified status.
   * @throws DataAccessException if an unexpected error occurs while running the query via Query
   *     Tool.
   */
  public Project[] searchUserActiveProjects(
      long userId,
      ProjectStatus[] projectStatuses,
      ProjectCategory[] projectCategories,
      ProjectPropertyType[] projectInfoTypes) {
    return searchProjectsByQueryTool(
        "tcs_projects_by_user",
        "tcs_project_infos_by_user",
        "uid",
        String.valueOf(userId),
        projectStatuses,
        projectCategories,
        projectInfoTypes);
  }

  /**
   * Checks if the specified user has access to the specified cockpit project.
   *
   * @param projectId a <code>long</code> providing the ID of a project.
   * @param userId a <code>long</code> providing the ID of a user.
   * @return <code>true</code> if specified user is granted <code>Cockpit Project User</code> role
   *     for specified project; <code>false</code> otherwise.
   */
  public boolean isCockpitProjectUser(long projectId, long userId) {
    var results =
        baseDataAccess.runQuery(
            "cockpit_project_user",
            new String[] {"pj", "uid"},
            new String[] {String.valueOf(projectId), String.valueOf(userId)});
    var result = results.get("cockpit_project_user");
    return !result.isEmpty();
  }

  /**
   * Gets the <code>CockpitProject</code> by the ID.
   *
   * @param cockpitProjectId a <code>long</code> providing the ID of the cockpit project.
   * @return a <code>CockpitProject</code> or null if not found.
   */
  public CockpitProject getCockpitProject(long cockpitProjectId) {
    var results =
        baseDataAccess.runQuery("cockpit_project_by_id", "pj", String.valueOf(cockpitProjectId));
    var result = results.get("cockpit_project_by_id");
    if (!result.isEmpty()) {
      CockpitProject project = new CockpitProject();
      project.setId(getLong(result.get(0), "tc_direct_project_id"));
      project.setName(getString(result.get(0), "tc_direct_project_name"));
      return project;
    } else {
      return null;
    }
  }

  /**
   * Gets the list of all existing <code>CockpitProject</code> projects.
   *
   * @return a <code>List</code> of all existing <code>CockpitProject</code> projects.
   */
  public List<CockpitProject> getAllCockpitProjects() {
    var results = baseDataAccess.runQuery("cockpit_projects", (String) null, (String) null);
    var projectsResultContainer = results.get("cockpit_projects");

    List<CockpitProject> result = new ArrayList<CockpitProject>();
    for (var row : projectsResultContainer) {
      CockpitProject project = new CockpitProject();
      project.setId(getLong(row, "tc_direct_project_id"));
      project.setName(getString(row, "tc_direct_project_name"));
      result.add(project);
    }
    return result;
  }

  /**
   * Gets the list of all existing <code>CockpitProject</code> projects which the specified user is
   * granted access permission for.
   *
   * @param userId a <code>long</code> providing the ID of a user to get the list of accessible
   *     <code>CockpitProject</code> projects for.
   * @return a <code>List</code> of all existing <code>CockpitProject</code> projects accessible by
   *     the specified user.
   */
  public List<CockpitProject> getCockpitProjectsForUser(long userId) {
    var results = baseDataAccess.runQuery("direct_my_projects", "uid", String.valueOf(userId));
    var projectsResultContainer = results.get("direct_my_projects");

    List<CockpitProject> result = new ArrayList<>();
    for (var row : projectsResultContainer) {
      CockpitProject project = new CockpitProject();
      project.setId(getLong(row, "tc_direct_project_id"));
      project.setName(getString(row, "tc_direct_project_name"));
      result.add(project);
    }
    return result;
  }

  /**
   * Gets the <code>ClientProject</code> by the ID.
   *
   * @param clientProjectId a <code>long</code> providing the ID of the client project.
   * @return a <code>ClientProject</code> or null if not found.
   */
  public ClientProject getClientProject(long clientProjectId) {
    var results =
        baseDataAccess.runQuery("client_project_by_id", "pj", String.valueOf(clientProjectId));
    var result = results.get("client_project_by_id");
    if (!result.isEmpty()) {
      ClientProject project = new ClientProject();
      project.setId(getLong(result.get(0), "project_id"));
      project.setName(getString(result.get(0), "project_name"));
      return project;
    } else {
      return null;
    }
  }

  /**
   * Gets the list of all existing <code>ClientProject</code> projects.
   *
   * @return a <code>List</code> of all existing <code>ClientProject</code> projects.
   */
  public List<ClientProject> getAllClientProjects() {
    var results = baseDataAccess.runQuery("client_projects", (String) null, (String) null);
    var projectsResultContainer = results.get("client_projects");

    List<ClientProject> result = new ArrayList<ClientProject>();
    for (var row : projectsResultContainer) {
      ClientProject project = new ClientProject();
      project.setId(getLong(row, "project_id"));
      project.setName(getString(row, "project_name"));
      result.add(project);
    }
    return result;
  }

  /**
   * Gets the list of all existing <code>ClientProject</code> projects which the specified user is
   * granted access permission for.
   *
   * @param userId a <code>long</code> providing the ID of a user to get the list of accessible
   *     <code>ClientProject</code> projects for.
   * @return a <code>List</code> of all existing <code>ClientProject</code> projects accessible by
   *     the specified user.
   */
  public List<ClientProject> getClientProjectsForUser(long userId) {
    var results = baseDataAccess.runQuery("client_projects_by_user", "uid", String.valueOf(userId));
    var projectsResultContainer = results.get("client_projects_by_user");

    List<ClientProject> result = new ArrayList<>();
    for (var row : projectsResultContainer) {
      ClientProject project = new ClientProject();
      project.setId(getLong(row, "project_id"));
      project.setName(getString(row, "project_name"));
      result.add(project);
    }
    return result;
  }

  /**
   * Gets the list of projects of specified status.
   *
   * @param projectQuery a <code>String</code> providing the name of the query to be run for getting
   *     the project details.
   * @param projectInfoQuery a <code>String</code> providing the name of the query to be run for
   *     getting the project info details.
   * @param paramName a <code>String</code> providing the name of the query parameter for
   *     customization.
   * @param paramValue a <code>String</code> providing the value of the query parameter for
   *     customization.
   * @param projectStatuses a <code>ProjectStatus</code> array listing the available project
   *     statuses.
   * @param projectCategories a <code>ProjectCategory</code> array listing available project
   *     categories.
   * @param projectInfoTypes a <code>ProjectPropertyType</code> listing available project info
   *     types.
   * @return a <code>Project</code> array listing the projects of specified status.
   * @throws DataAccessException if an unexpected error occurs while running the query via Query
   *     Tool.
   */
  private Project[] searchProjectsByQueryTool(
      String projectQuery,
      String projectInfoQuery,
      String paramName,
      String paramValue,
      ProjectStatus[] projectStatuses,
      ProjectCategory[] projectCategories,
      ProjectPropertyType[] projectInfoTypes) {

    // Build the cache of project categories and project statuses for faster lookup by ID
    Map<Long, ProjectCategory> categoriesMap =
        baseDataAccess.buildProjectCategoriesLookupMap(projectCategories);
    Map<Long, ProjectStatus> statusesMap =
        baseDataAccess.buildProjectStatusesLookupMap(projectStatuses);

    // Get project details by status using Query Tool
    var results = baseDataAccess.runQuery(projectQuery, paramName, paramValue);

    // Convert returned data into Project objects
    var projectsData = results.get(projectQuery);
    Map<Long, Project> projects = new HashMap<Long, Project>(projectsData.size());
    int recordNum = projectsData.size();
    Project[] resultingProjects = new Project[recordNum];
    for (var i = 0; i < projectsData.size(); i++) {
      var row = projectsData.get(i);
      long projectId = getLong(row, "project_id");
      long projectCategoryId = getLong(row, "project_category_id");
      long projectStatusId = getLong(row, "project_status_id");
      String createUser = getString(row, "create_user");
      Date createDate = getDate(row, "create_date");
      String modifyUser = getString(row, "modify_user");
      Date modifyDate = getDate(row, "modify_date");

      Project project =
          new Project(
              projectId, categoriesMap.get(projectCategoryId), statusesMap.get(projectStatusId));
      project.setCreationUser(createUser);
      project.setCreationTimestamp(createDate);
      project.setModificationUser(modifyUser);
      project.setModificationTimestamp(modifyDate);

      resultingProjects[i] = project;
      projects.put(projectId, project);
    }

    // Build the cache of project info types for faster lookup by ID
    Map<Long, ProjectPropertyType> infoTypesMap =
        baseDataAccess.buildProjectPropertyTypesLookupMap(projectInfoTypes);

    var projectInfoData = results.get(projectInfoQuery);
    for (var row : projectInfoData) {
      long projectId = getLong(row, "project_id");
      long projectInfoTypeId = getLong(row, "project_info_type_id");
      String value = getString(row, "value");
      Project project = projects.get(projectId);
      project.setProperty(infoTypesMap.get(projectInfoTypeId).getName(), value);
    }

    return resultingProjects;
  }

  /**
   * Get client id for specify tc direct project.
   *
   * @param directProjectId the id of tc direct project
   * @return the client id
   * @throws Exception if any exception occurs
   */
  public long getProjectClient(long directProjectId) {
    log.info("Will run getProjectClient with id=" + directProjectId);
    String queryName = "non_admin_client_billing_accounts";
    var resultContainer =
        baseDataAccess
            .runQueryInDB(
                tcsEntityManager,
                queryName,
                new String[] {"tdpis"},
                new String[] {String.valueOf(directProjectId)})
            .get(queryName);

    if (resultContainer != null) {
      if (resultContainer.size() > 0) {
        return getLong(resultContainer.get(0), "client_id");
      }
    }
    return 0;
  }

  /**
   * Check user challenge eligibility
   *
   * @param userId the userId to use
   * @param challengeId the challengeId to use
   * @return the Map<String,Long> result contains the group and challenge info
   */
  public Map<String, Long> checkUserChallengeEligibility(long userId, long challengeId) {
    String queryName = "get_challenge_accessibility_and_groups";
    var resultContainer =
        baseDataAccess
            .runQueryInDB(
                oltpEntityManager,
                queryName,
                new String[] {"userId", "challengeId"},
                new String[] {userId + "", challengeId + ""})
            .get(queryName);

    if (resultContainer != null && resultContainer.size() > 0) {
      Map<String, Long> map = new HashMap<>();
      var row = resultContainer.get(0);
      if (row.get("user_group_xref_found") != null) {
        map.put("user_group_xref_found", getLong(row, "user_group_xref_found"));
      }
      if (row.get("challenge_group_ind") != null) {
        map.put("challenge_group_ind", getLong(row, "user_group_xref_found"));
      }
      if (row.get("group_id") != null) {
        map.put("group_id", getLong(row, "group_id"));
      }
      return map;
    }
    return null;
  }
}
