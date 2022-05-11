/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.or.dataaccess;

import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import com.topcoder.onlinereview.component.resource.Resource;
import com.topcoder.onlinereview.component.resource.ResourceManager;
import com.topcoder.onlinereview.component.resource.ResourcePersistenceException;
import com.topcoder.onlinereview.component.resource.ResourceRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.util.CommonUtils.getString;

/**
 * A simple DAO for project resources backed up by Query Tool.
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
@Slf4j
@Component
public class ResourceDataAccess {
  @Autowired private BaseDataAccess baseDataAccess;
  @Autowired private ResourceManager resourceManager;

  /**
   * Searches the resources for specified user for projects of specified status. If status parameter
   * is null it will search for the 'global' resources with no project associated.
   *
   * @param userId a <code>long</code> providing the user ID.
   * @param status a <code>ProjectStatus</code> specifying the status of the projects.
   * @return a <code>Resource</code> array providing the details for found resources.
   * @throws ResourcePersistenceException if an error occurs while retrieving resource roles.
   * @throws DataAccessException if an unexpected error occurs.
   */
  public Resource[] searchUserResources(long userId, ProjectStatus status)
      throws ResourcePersistenceException {
    // Get the list of existing resource roles and build a cache
    ResourceRole[] resourceRoles = resourceManager.getAllResourceRoles();
    Map<Long, ResourceRole> cachedRoles = new HashMap<>();
    for (ResourceRole role : resourceRoles) {
      cachedRoles.put(role.getId(), role);
    }

    // Get resources details by user ID using Query Tool
    Map<String, List<Map<String, Object>>> results;
    if (status == null) {
      results =
          baseDataAccess.runQuery("tcs_global_resources_by_user", "uid", String.valueOf(userId));
    } else {
      results =
          baseDataAccess.runQuery(
              "tcs_resources_by_user_and_status",
              new String[] {"uid", "stid"},
              new String[] {String.valueOf(userId), String.valueOf(status.getId())});
    }

    // Convert returned data into Resource objects
    List<Map<String, Object>> resourcesData;
    if (status == null) {
      resourcesData = results.get("tcs_global_resources_by_user");
    } else {
      resourcesData = results.get("tcs_resources_by_user_and_status");
    }
    Map<Long, Resource> cachedResources = new HashMap<>();
    int recordNum = resourcesData.size();
    Resource[] resources = new Resource[recordNum];
    for (int i = 0; i < recordNum; i++) {
      long resourceId = getLong(resourcesData.get(i), "resource_id");
      long resourceRoleId = getLong(resourcesData.get(i), "resource_role_id");
      Long projectId = null;
      if (resourcesData.get(i).get("project_id") != null) {
        projectId = getLong(resourcesData.get(i), "project_id");
      }
      Long phaseId = null;
      if (resourcesData.get(i).get("phase_id") != null) {
        phaseId = getLong(resourcesData.get(i), "phase_id");
      }
      String createUser = getString(resourcesData.get(i), "create_user");
      Date createDate = getDate(resourcesData.get(i), "create_date");
      String modifyUser = getString(resourcesData.get(i), "modify_user");
      Date modifyDate = getDate(resourcesData.get(i), "modify_date");

      Resource resource = new Resource(resourceId, cachedRoles.get(resourceRoleId));
      resource.setProject(projectId);
      resource.setPhase(phaseId);
      resource.setUserId(userId);
      resource.setCreationUser(createUser);
      resource.setCreationTimestamp(createDate);
      resource.setModificationUser(modifyUser);
      resource.setModificationTimestamp(modifyDate);

      resources[i] = resource;
      cachedResources.put(resourceId, resource);
    }

    // Fill resources with resource info records
    List<Map<String, Object>> resourceInfosData;
    if (status == null) {
      resourceInfosData = results.get("tcs_global_resource_infos_by_user");
    } else {
      resourceInfosData = results.get("tcs_resource_infos_by_user_and_status");
    }
    recordNum = resourceInfosData.size();

    for (int i = 0; i < recordNum; i++) {
      long resourceId = getLong(resourceInfosData.get(i), "resource_id");
      String propName = getString(resourceInfosData.get(i), "resource_info_type_name");
      String value = getString(resourceInfosData.get(i), "value");
      Resource resource = cachedResources.get(resourceId);
      resource.setProperty(propName, value);
    }

    return resources;
  }
}
