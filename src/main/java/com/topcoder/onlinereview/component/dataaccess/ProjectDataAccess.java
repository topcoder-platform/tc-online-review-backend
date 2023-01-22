/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.dataaccess;

import com.topcoder.onlinereview.component.grpcclient.dataaccess.DataAccessServiceRpc;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.management.ProjectPropertyType;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import com.topcoder.onlinereview.grpc.dataaccess.proto.CheckUserChallengeEligibilityResponse;
import com.topcoder.onlinereview.grpc.dataaccess.proto.ProjectInfoProto;
import com.topcoder.onlinereview.grpc.dataaccess.proto.ProjectProto;
import com.topcoder.onlinereview.grpc.dataaccess.proto.SearchProjectsParameter;
import com.topcoder.onlinereview.grpc.dataaccess.proto.SearchProjectsResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        SearchProjectsParameter.STATUS_ID,
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
        SearchProjectsParameter.STATUS_ID,
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
        SearchProjectsParameter.USER_ID,
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
      SearchProjectsParameter paramName,
      String paramValue,
      ProjectStatus[] projectStatuses,
      ProjectCategory[] projectCategories,
      ProjectPropertyType[] projectInfoTypes) {

    // Build the cache of project categories and project statuses for faster lookup by ID
    Map<Long, ProjectCategory> categoriesMap = buildProjectCategoriesLookupMap(projectCategories);
    Map<Long, ProjectStatus> statusesMap = buildProjectStatusesLookupMap(projectStatuses);

    // Get project details by status using Query Tool
    SearchProjectsResponse results = dataAccessServiceRpc.searchProjects(paramName, paramValue);

    // Convert returned data into Project objects
    List<ProjectProto> projectsData = results.getProjectsList();;
    Map<Long, Project> projects = new HashMap<Long, Project>(projectsData.size());
    int recordNum = projectsData.size();
    Project[] resultingProjects = new Project[recordNum];
    for (int i = 0; i < recordNum; i++) {
      ProjectProto projectData = projectsData.get(i);
      long projectId = projectData.getProjectId();
      long projectCategoryId = projectData.getProjectCategoryId();
      long projectStatusId = projectData.getProjectStatusId();
      String createUser = null;
      if (projectData.hasCreateUser()) {
        createUser = projectData.getCreateUser();
      }
      Date createDate = null;
      if (projectData.hasCreateDate()) {
        createDate = new Date(projectData.getCreateDate().getSeconds() * 1000);
      }
      String modifyUser = null;
      if (projectData.hasModifyUser()) {
        modifyUser = projectData.getModifyUser();
      }
      Date modifyDate = null;
      if (projectData.hasModifyDate()) {
        modifyDate = new Date(projectData.getModifyDate().getSeconds() * 1000);
      }

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

    List<ProjectInfoProto> projectInfosData = results.getProjectInfosList();;
    recordNum = projectInfosData.size();
    for (int i = 0; i < recordNum; i++) {
      ProjectInfoProto projectInfoData = projectInfosData.get(i);
      long projectId = projectInfoData.getProjectId();
      long projectInfoTypeId = projectInfoData.getProjectInfoTypeId();
      String value = null;
      if (projectInfoData.hasValue()) {
        value = projectInfoData.getValue();
      }
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

    return dataAccessServiceRpc.getProjectClient(directProjectId);
  }

  /**
   * Check user challenge eligibility
   *
   * @param userId the userId to use
   * @param challengeId the challengeId to use
   * @return the Map<String,Long> result contains the group and challenge info
   */
  public Map<String, Long> checkUserChallengeEligibility(long userId, long challengeId) {
    CheckUserChallengeEligibilityResponse result = dataAccessServiceRpc.checkUserChallengeEligibility(userId, challengeId);

    if (result != null) {
      Map<String, Long> map = new HashMap<String, Long>();

      if (result.hasUserGroupXrefFound()) {
        map.put("user_group_xref_found", result.getUserGroupXrefFound());
      }
      if (result.hasChallengeGroupInd()) {
        map.put("challenge_group_ind", result.getChallengeGroupInd());
      }
      if (result.hasGroupId()) {
        map.put("group_id", result.getGroupId());
      }
      return map;
    }

    return null;
  }
}
