/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.dataaccess;

import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.management.ProjectPropertyType;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import com.topcoder.onlinereview.component.project.phase.PhaseStatus;
import com.topcoder.onlinereview.component.project.phase.PhaseType;
import com.topcoder.onlinereview.component.resource.ResourceRole;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>A base class for DAO implementations backed up by Query Tool.</p>
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
public abstract class BaseDataAccess {

    /**
     * <p>A <code>long</code> providing the ID of project status <code>Active</code>. </p>
     */
    protected static final long PROJECT_STATUS_ACTIVE_ID = 1;

    /**
     * <p>A <code>long</code> providing the ID of project status <code>Draft</code>. </p>
     */
    protected static final long PROJECT_STATUS_DRAFT_ID = 2;

    /**
     * <p>Constructs new <code>BaseDataAccess</code> instance. This implementation does nothing.</p>
     */
    protected BaseDataAccess() {
    }

    /**
     * <p>Builds the map to be used for looking up the project categories by IDs.</p>
     *
     * @param categories a <code>ProjectCategory</code> array listing existing project categories.
     * @return a <code>Map</code> mapping the category IDs to categories.
     */
    protected static Map<Long, ProjectCategory> buildProjectCategoriesLookupMap(ProjectCategory[] categories) {
        Map<Long, ProjectCategory> categoriesMap = new HashMap<Long, ProjectCategory>(categories.length);
        for (ProjectCategory category : categories) {
            categoriesMap.put(category.getId(), category);
        }
        return categoriesMap;
    }

    /**
     * <p>Builds the map to be used for looking up the phase types by IDs.</p>
     *
     * @param phaseTypes a <code>PhaseType</code> array listing existing phase types.
     * @return a <code>Map</code> mapping the type IDs to phase types.
     */
    protected Map<Long, PhaseType> buildPhaseTypesLookupMap(PhaseType[] phaseTypes) {
        Map<Long, PhaseType> typesMap = new HashMap<Long, PhaseType>(phaseTypes.length);
        for (PhaseType type : phaseTypes) {
            typesMap.put(type.getId(), type);
        }
        return typesMap;
    }

    /**
     * <p>Builds the map to be used for looking up the phase statuses by IDs.</p>
     *
     * @param phaseStatuses a <code>PhaseType</code> array listing existing phase statuses.
     * @return a <code>Map</code> mapping the status IDs to phase statuses.
     */
    protected Map<Long, PhaseStatus> buildPhaseStatusesLookupMap(PhaseStatus[] phaseStatuses) {
        Map<Long, PhaseStatus> statusesMap = new HashMap<Long, PhaseStatus>(phaseStatuses.length);
        for (PhaseStatus phaseStatus : phaseStatuses) {
            statusesMap.put(phaseStatus.getId(), phaseStatus);
        }
        return statusesMap;
    }

    /**
     * <p>Builds the map to be used for looking up the project statuses by IDs.</p>
     *
     * @param projectStatuses a <code>PhaseType</code> array listing existing project statuses.
     * @return a <code>Map</code> mapping the status IDs to project statuses.
     */
    protected Map<Long, ProjectStatus> buildProjectStatusesLookupMap(ProjectStatus[] projectStatuses) {
        Map<Long, ProjectStatus> statusesMap = new HashMap<Long, ProjectStatus>(projectStatuses.length);
        for (ProjectStatus status : projectStatuses) {
            statusesMap.put(status.getId(), status);
        }
        return statusesMap;
    }

    /**
     * <p>Builds the map to be used for looking up the project info types by IDs.</p>
     *
     * @param projectInfoTypes a <code>PhaseType</code> array listing existing project info types.
     * @return a <code>Map</code> mapping the info type IDs to project info types.
     */
    protected Map<Long, ProjectPropertyType> buildProjectPropertyTypesLookupMap(ProjectPropertyType[] projectInfoTypes) {
        Map<Long, ProjectPropertyType> typesMap = new HashMap<Long, ProjectPropertyType>(projectInfoTypes.length);
        for (ProjectPropertyType infoType : projectInfoTypes) {
            typesMap.put(infoType.getId(), infoType);
        }
        return typesMap;
    }

    /**
     * <p>Builds the map to be used for looking up the resource roles by IDs.</p>
     *
     * @param resourceRoles a <code>ResourceRole</code> array listing existing resource roles.
     * @return a <code>Map</code> mapping the info type IDs to project info types.
     */
    protected Map<Long, ResourceRole> buildResourceRolesLookupMap(ResourceRole[] resourceRoles) {
        Map<Long, ResourceRole> rolesMap = new HashMap<Long, ResourceRole>(resourceRoles.length);
        for (ResourceRole role : resourceRoles) {
            rolesMap.put(role.getId(), role);
        }
        return rolesMap;
    }
}
