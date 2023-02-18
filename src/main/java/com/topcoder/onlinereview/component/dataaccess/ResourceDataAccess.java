/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.dataaccess;

import com.topcoder.onlinereview.component.grpcclient.dataaccess.DataAccessServiceRpc;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import com.topcoder.onlinereview.component.resource.Resource;
import com.topcoder.onlinereview.component.resource.ResourceManager;
import com.topcoder.onlinereview.component.resource.ResourceRole;
import com.topcoder.onlinereview.grpc.dataaccess.proto.ResourceInfoProto;
import com.topcoder.onlinereview.grpc.dataaccess.proto.ResourceProto;
import com.topcoder.onlinereview.grpc.dataaccess.proto.SearchUserResourcesResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A simple DAO for project resources backed up by Query Tool.</p>
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
@Component
public class ResourceDataAccess {
    @Autowired
    DataAccessServiceRpc dataAccessServiceRpc;
    /**
     * <p>Searches the resources for specified user for projects of specified status.
     * If status parameter is null it will search for the 'global' resources with no
     * project associated.</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     * @param status a <code>ProjectStatus</code> specifying the status of the projects.
     * @param resourceManager a <code>ResourceManager</code> to be used for searching.
     * @return a <code>Resource</code> array providing the details for found resources.
     * @throws DataAccessException if an unexpected error occurs.
     */
    public Resource[] searchUserResources(long userId, ProjectStatus status, ResourceManager resourceManager) {
        
        // Get the list of existing resource roles and build a cache
        ResourceRole[] resourceRoles = resourceManager.getAllResourceRoles();
        Map<Long, ResourceRole> cachedRoles = new HashMap<Long, ResourceRole>();
        for (ResourceRole role : resourceRoles) {
            cachedRoles.put(role.getId(), role);
        }

        // Get resources details by user ID using Query Tool
        SearchUserResourcesResponse results;
        if (status == null) {
            results = dataAccessServiceRpc.searchUserResourcesByUserId(userId);
        } else {
            results = dataAccessServiceRpc.searchUserResourcesByUserIdAndStatus(userId, status.getId());
        }

        // Convert returned data into Resource objects
        List<ResourceProto> resourcesData = results.getResourcesList();
        Map<Long, Resource> cachedResources = new HashMap<Long, Resource>();
        int recordNum = resourcesData.size();
        Resource[] resources = new Resource[recordNum];
        for (int i = 0; i < recordNum; i++) {
            ResourceProto resourceData = resourcesData.get(i);
            long resourceId = resourceData.getResourceId();
            long resourceRoleId = resourceData.getResourceRoleId();
            Long projectId = null;
            if (resourceData.hasProjectId()) {
                projectId = resourceData.getProjectId();
            }
            Long phaseId = null;
            if (resourceData.hasPhaseId()) {
                phaseId = resourceData.getPhaseId();
            }
            String createUser = null;
            if (resourceData.hasCreateUser()) {
                createUser = resourceData.getCreateUser();
            }
            Date createDate = null;
            if (resourceData.hasCreateDate()) {
                createDate = new Date(resourceData.getCreateDate().getSeconds() * 1000);
            }
            String modifyUser = null;
            if (resourceData.hasModifyUser()) {
                modifyUser = resourceData.getModifyUser();
            }
            Date modifyDate = null;
            if (resourceData.hasModifyDate()) {
                modifyDate = new Date(resourceData.getModifyDate().getSeconds() * 1000);
            }

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
        List<ResourceInfoProto> resourceInfosData = results.getResourceInfosList();
        recordNum = resourceInfosData.size();

        for (int i = 0; i < recordNum; i++) {
            ResourceInfoProto resourceInfoData = resourceInfosData.get(i);
            long resourceId = resourceInfoData.getResourceId();
            String propName = null;
            if (resourceInfoData.hasResourceInfoTypeName()) {
                propName = resourceInfoData.getResourceInfoTypeName();
            }
            String value = null;
            if (resourceInfoData.hasValue()) {
                value = resourceInfoData.getValue();
            }
            Resource resource = cachedResources.get(resourceId);
            resource.setProperty(propName, value);
        }

        return resources;
    }
}
