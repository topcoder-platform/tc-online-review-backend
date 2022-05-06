/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.or.dataaccess;

import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.management.ProjectPropertyType;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import com.topcoder.onlinereview.component.project.phase.PhaseStatus;
import com.topcoder.onlinereview.component.project.phase.PhaseType;
import com.topcoder.onlinereview.component.resource.ResourceRole;
import com.topcoder.onlinereview.component.shared.dataaccess.DataAccess;
import com.topcoder.onlinereview.component.shared.dataaccess.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A base class for DAO implementations backed up by Query Tool.
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
@Component
public class BaseDataAccess {

  /** A <code>long</code> providing the ID of project status <code>Active</code>. */
  protected static final long PROJECT_STATUS_ACTIVE_ID = 1;

  /** A <code>long</code> providing the ID of project status <code>Draft</code>. */
  protected static final long PROJECT_STATUS_DRAFT_ID = 2;

  @Value("{data-access.entity-manager-name}")
  private String entityManagerName;

  @Autowired private Map<String, EntityManager> entityManagerMap;
  private EntityManager entityManager;

  @PostConstruct
  public void postRun() {
    entityManager = entityManagerMap.get(entityManagerName);
  }

  /**
   * Builds the map to be used for looking up the project categories by IDs.
   *
   * @param categories a <code>ProjectCategory</code> array listing existing project categories.
   * @return a <code>Map</code> mapping the category IDs to categories.
   */
  public Map<Long, ProjectCategory> buildProjectCategoriesLookupMap(ProjectCategory[] categories) {
    Map<Long, ProjectCategory> categoriesMap =
        new HashMap<Long, ProjectCategory>(categories.length);
    for (ProjectCategory category : categories) {
      categoriesMap.put(category.getId(), category);
    }
    return categoriesMap;
  }

  /**
   * Builds the map to be used for looking up the phase types by IDs.
   *
   * @param phaseTypes a <code>PhaseType</code> array listing existing phase types.
   * @return a <code>Map</code> mapping the type IDs to phase types.
   */
  public Map<Long, PhaseType> buildPhaseTypesLookupMap(PhaseType[] phaseTypes) {
    Map<Long, PhaseType> typesMap = new HashMap<Long, PhaseType>(phaseTypes.length);
    for (PhaseType type : phaseTypes) {
      typesMap.put(type.getId(), type);
    }
    return typesMap;
  }

  /**
   * Builds the map to be used for looking up the phase statuses by IDs.
   *
   * @param phaseStatuses a <code>PhaseType</code> array listing existing phase statuses.
   * @return a <code>Map</code> mapping the status IDs to phase statuses.
   */
  public Map<Long, PhaseStatus> buildPhaseStatusesLookupMap(PhaseStatus[] phaseStatuses) {
    Map<Long, PhaseStatus> statusesMap = new HashMap<Long, PhaseStatus>(phaseStatuses.length);
    for (PhaseStatus phaseStatus : phaseStatuses) {
      statusesMap.put(phaseStatus.getId(), phaseStatus);
    }
    return statusesMap;
  }

  /**
   * Builds the map to be used for looking up the project statuses by IDs.
   *
   * @param projectStatuses a <code>PhaseType</code> array listing existing project statuses.
   * @return a <code>Map</code> mapping the status IDs to project statuses.
   */
  public Map<Long, ProjectStatus> buildProjectStatusesLookupMap(ProjectStatus[] projectStatuses) {
    Map<Long, ProjectStatus> statusesMap = new HashMap<Long, ProjectStatus>(projectStatuses.length);
    for (ProjectStatus status : projectStatuses) {
      statusesMap.put(status.getId(), status);
    }
    return statusesMap;
  }

  /**
   * Builds the map to be used for looking up the project info types by IDs.
   *
   * @param projectInfoTypes a <code>PhaseType</code> array listing existing project info types.
   * @return a <code>Map</code> mapping the info type IDs to project info types.
   */
  public Map<Long, ProjectPropertyType> buildProjectPropertyTypesLookupMap(
      ProjectPropertyType[] projectInfoTypes) {
    Map<Long, ProjectPropertyType> typesMap =
        new HashMap<Long, ProjectPropertyType>(projectInfoTypes.length);
    for (ProjectPropertyType infoType : projectInfoTypes) {
      typesMap.put(infoType.getId(), infoType);
    }
    return typesMap;
  }

  /**
   * Builds the map to be used for looking up the resource roles by IDs.
   *
   * @param resourceRoles a <code>ResourceRole</code> array listing existing resource roles.
   * @return a <code>Map</code> mapping the info type IDs to project info types.
   */
  public Map<Long, ResourceRole> buildResourceRolesLookupMap(ResourceRole[] resourceRoles) {
    Map<Long, ResourceRole> rolesMap = new HashMap<Long, ResourceRole>(resourceRoles.length);
    for (ResourceRole role : resourceRoles) {
      rolesMap.put(role.getId(), role);
    }
    return rolesMap;
  }

  /**
   * Executes the specified query using Query Tool. The query is customized with provided value of
   * specified parameter.
   *
   * @param queryName a <code>String</code> providing the name of the query to be run.
   * @param paramName a <code>String</code> providing the name of the query parameter for
   *     customization.
   * @param paramValue a <code>String</code> providing the value of the query parameter for
   *     customization.
   * @return a <code>Map</code> providing the query results. Maps query names to query results.
   * @throws DataAccessException if an unexpected error occurs while running the query.
   */
  public Map<String, List<Map<String, Object>>> runQuery(
      String queryName, String paramName, String paramValue) {
    return runQuery(queryName, new String[] {paramName}, new String[] {paramValue});
  }

  /**
   * Executes the specified query using Query Tool. The query is customized with provided value of
   * specified parameter.
   *
   * @param queryName a <code>String</code> providing the name of the query to be run.
   * @param paramNames a <code>String</code> array providing the names of the query parameters for
   *     customization.
   * @param paramValues a <code>String</code> array providing the values of the query parameters for
   *     customization.
   * @return a <code>Map</code> providing the query results. Maps query names to query results.
   * @throws DataAccessException if an unexpected error occurs while running the query.
   */
  public Map<String, List<Map<String, Object>>> runQuery(
      String queryName, String[] paramNames, String[] paramValues) {
    return runQueryInDB(entityManager, queryName, paramNames, paramValues);
  }

  /**
   * Executes the specified query using Query Tool. The query is customized with provided value of
   * specified parameter.
   *
   * @param entityManager the entityManager.
   * @param queryName a <code>String</code> providing the name of the query to be run.
   * @param paramNames a <code>String</code> array providing the names of the query parameters for
   *     customization.
   * @param paramValues a <code>String</code> array providing the values of the query parameters for
   *     customization.
   * @return a <code>Map</code> providing the query results. Maps query names to query results.
   * @throws DataAccessException if an unexpected error occurs while running the query.
   */
  protected Map<String, List<Map<String, Object>>> runQueryInDB(
      EntityManager entityManager, String queryName, String[] paramNames, String[] paramValues) {
    DataAccess dataAccess = new DataAccess(entityManager);
    Request request = new Request();
    request.setContentHandle(queryName);
    for (int i = 0; i < paramNames.length; i++) {
      if (paramNames[i] != null) {
        request.setProperty(paramNames[i], paramValues[i]);
      }
    }
    try {
      return dataAccess.getData(request);
    } catch (Exception e) {
      throw new DataAccessException("Failed to run " + queryName + " query via Query Tool", e);
    }
  }
}
