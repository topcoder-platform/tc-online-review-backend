/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.dataaccess;

import com.topcoder.onlinereview.component.grpcclient.dataaccess.DataAccessServiceRpc;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.management.ProjectPropertyType;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.component.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.component.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.component.util.CommonUtils.getString;

/**
 * A simple DAO for projects backed up by Query Tool. Changes in version 2.1 Topcoder - Add Group
 * Permission Check For Adding Resources v1.0 - add the checkUserChallengeEligibility method to
 * check user group permission
 *
 * @author TCSASSEMBLER
 * @version 2.1
 */
@Component
public class ProjectDataAccess extends BaseDataAccess {
  @Autowired
  DataAccessServiceRpc dataAccessServiceRpc;

  @Autowired
  @Qualifier("oltpJdbcTemplate")
  private JdbcTemplate oltpJdbcTemplate;

  @Autowired
  @Qualifier("tcsDwJdbcTemplate")
  private JdbcTemplate tcsDwJdbcTemplate;

  public void setOltpJdbcTemplate(JdbcTemplate oltpJdbcTemplate) {
    this.oltpJdbcTemplate = oltpJdbcTemplate;
  }

  public void setTcsDwJdbcTemplate(JdbcTemplate tcsDwJdbcTemplate) {
    this.tcsDwJdbcTemplate = tcsDwJdbcTemplate;
  }

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
    return dataAccessServiceRpc.isCockpitProjectUser(projectId, userId);
  }

  /**
   * Gets the <code>CockpitProject</code> by the ID.
   *
   * @param cockpitProjectId a <code>long</code> providing the ID of the cockpit project.
   * @return a <code>CockpitProject</code> or null if not found.
   */
  public CockpitProject getCockpitProject(long cockpitProjectId) {
    return dataAccessServiceRpc.getCockpitProject(cockpitProjectId);
  }

  /**
   * Gets the list of all existing <code>CockpitProject</code> projects.
   *
   * @return a <code>List</code> of all existing <code>CockpitProject</code> projects.
   */
  public List<CockpitProject> getAllCockpitProjects() {
    return dataAccessServiceRpc.getAllCockpitProjects();
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
    return dataAccessServiceRpc.getCockpitProjectsForUser(userId);
  }

  /**
   * Gets the <code>ClientProject</code> by the ID.
   *
   * @param clientProjectId a <code>long</code> providing the ID of the client project.
   * @return a <code>ClientProject</code> or null if not found.
   */
  public ClientProject getClientProject(long clientProjectId) {
    return dataAccessServiceRpc.getClientProject(clientProjectId);
  }

  /**
   * Gets the list of all existing <code>ClientProject</code> projects.
   *
   * @return a <code>List</code> of all existing <code>ClientProject</code> projects.
   */
  public List<ClientProject> getAllClientProjects() {
    return dataAccessServiceRpc.getAllClientProjects();
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
    return dataAccessServiceRpc.getClientProjectsForUser(userId);
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
    Map<Long, ProjectCategory> categoriesMap = buildProjectCategoriesLookupMap(projectCategories);
    Map<Long, ProjectStatus> statusesMap = buildProjectStatusesLookupMap(projectStatuses);

    // Get project details by status using Query Tool
    Map<String, List<Map<String, Object>>> results = runQuery(projectQuery, paramName, paramValue);

    // Convert returned data into Project objects
    List<Map<String, Object>> projectsData = results.get(projectQuery);
    Map<Long, Project> projects = new HashMap<Long, Project>(projectsData.size());
    int recordNum = projectsData.size();
    Project[] resultingProjects = new Project[recordNum];
    for (int i = 0; i < recordNum; i++) {
      long projectId = getLong(projectsData.get(i), "project_id");
      long projectCategoryId = getLong(projectsData.get(i), "project_category_id");
      long projectStatusId = getLong(projectsData.get(i), "project_status_id");
      String createUser = getString(projectsData.get(i), "create_user");
      Date createDate = getDate(projectsData.get(i), "create_date");
      String modifyUser = getString(projectsData.get(i), "modify_user");
      Date modifyDate = getDate(projectsData.get(i), "modify_date");

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
        buildProjectPropertyTypesLookupMap(projectInfoTypes);

    List<Map<String, Object>> projectInfosData = results.get(projectInfoQuery);
    recordNum = projectInfosData.size();
    for (int i = 0; i < recordNum; i++) {
      long projectId = getLong(projectInfosData.get(i), "project_id");
      long projectInfoTypeId = getLong(projectInfosData.get(i), "project_info_type_id");
      String value = getString(projectInfosData.get(i), "value");
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
    System.out.println("Will run getProjectClient with id=" + directProjectId);

    String queryName = "non_admin_client_billing_accounts";

    List<Map<String, Object>> resultContainer =
        runQueryInDB(
                tcsDwJdbcTemplate,
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
    List<Map<String, Object>> resultContainer =
        runQueryInDB(
                oltpJdbcTemplate,
                queryName,
                new String[] {"userId", "challengeId"},
                new String[] {userId + "", challengeId + ""})
            .get(queryName);

    if (resultContainer != null && resultContainer.size() > 0) {
      Map<String, Long> map = new HashMap<String, Long>();
      Map<String, Object> row = resultContainer.get(0);

      Object obj = row.get("user_group_xref_found");
      if (obj != null) {
        map.put("user_group_xref_found", Long.parseLong(obj.toString()));
      }
      obj = row.get("challenge_group_ind");
      if (obj != null) {
        map.put("challenge_group_ind", Long.parseLong(obj.toString()));
      }
      obj = row.get("group_id");
      if (obj != null) {
        map.put("group_id", Long.parseLong(obj.toString()));
      }
      return map;
    }

    return null;
  }
}
