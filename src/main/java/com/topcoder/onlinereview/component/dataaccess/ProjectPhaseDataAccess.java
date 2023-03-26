/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.dataaccess;

import com.topcoder.onlinereview.component.grpcclient.dataaccess.DataAccessServiceRpc;
import com.topcoder.onlinereview.component.project.phase.Dependency;
import com.topcoder.onlinereview.component.project.phase.Phase;
import com.topcoder.onlinereview.component.project.phase.PhaseStatus;
import com.topcoder.onlinereview.component.project.phase.PhaseType;
import com.topcoder.onlinereview.component.project.phase.Project;
import com.topcoder.onlinereview.component.workday.Workdays;
import com.topcoder.onlinereview.component.workday.WorkdaysFactory;
import com.topcoder.onlinereview.grpc.dataaccess.proto.ProjectPhaseProto;
import com.topcoder.onlinereview.grpc.dataaccess.proto.SearchProjectPhasesResponse;
import com.topcoder.onlinereview.grpc.dataaccess.proto.SearchProjectsParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A simple DAO for project phases backed up by Query Tool.</p>
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
@Component
public class ProjectPhaseDataAccess extends BaseDataAccess {
    @Autowired
    DataAccessServiceRpc dataAccessServiceRpc;
    @Autowired
    private WorkdaysFactory workdaysFactory;

    /**
     * <p>Gets the phases for projects of <code>Active</code> status.</p>
     *
     * @param phaseStatuses a <code>PhaseStatus</code> array listing the available phase statuses.
     * @param phaseTypes a <code>PhaseType</code> array listing the available phase types.
     * @return a <code>Map</code> mapping project IDs to the project phases.
     */
    public Map<Long, Project> searchActiveProjectPhases(PhaseStatus[] phaseStatuses, PhaseType[] phaseTypes) {
        return searchProjectPhasesByQueryTool(SearchProjectsParameter.STATUS_ID,
                                              String.valueOf(PROJECT_STATUS_ACTIVE_ID), phaseStatuses, phaseTypes);
    }

    /**
     * <p>Gets the phases for projects of <code>Draft</code> status.</p>
     *
     * @param phaseStatuses a <code>PhaseStatus</code> array listing the available phase statuses.
     * @param phaseTypes a <code>PhaseType</code> array listing the available phase types.
     * @return a <code>Map</code> mapping project IDs to the project phases.
     */
    public Map<Long, Project> searchDraftProjectPhases(PhaseStatus[] phaseStatuses, PhaseType[] phaseTypes) {
        return searchProjectPhasesByQueryTool(SearchProjectsParameter.STATUS_ID,
                                              String.valueOf(PROJECT_STATUS_DRAFT_ID), phaseStatuses, phaseTypes);
    }

    /**
     * <p>Gets the phases for projects assigned to specified user.</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     * @param phaseStatuses a <code>PhaseStatus</code> array listing the available phase statuses.
     * @param phaseTypes a <code>PhaseType</code> array listing the available phase types.
     * @return a <code>Map</code> mapping project IDs to the project phases.
     */
    public Map<Long, Project> searchUserProjectPhases(long userId, PhaseStatus[] phaseStatuses,
                                                      PhaseType[] phaseTypes) {
        return searchProjectPhasesByQueryTool(SearchProjectsParameter.USER_ID,
                                              String.valueOf(userId), phaseStatuses, phaseTypes);
    }

    /**
     * <p>Gets the phases for projects of specified status.</p>
     *
     * @param queryName a <code>String</code> providing the name of the query to be run.
     * @param paramName a <code>String</code> providing the name of the query parameter for customization.
     * @param paramValue a <code>String</code> providing the value of the query parameter for customization.
     * @param phaseStatuses a <code>PhaseStatus</code> array listing the available phase statuses.
     * @param phaseTypes a <code>PhaseType</code> array listing the available phase types.
     * @return a <code>Project</code> array listing the project phases.
     * @throws DataAccessException if an unexpected error occurs while running the query via Query Tool.
     */
    private Map<Long, Project> searchProjectPhasesByQueryTool(SearchProjectsParameter paramName, String paramValue,
                                                              PhaseStatus[] phaseStatuses, PhaseType[] phaseTypes) {

        // Build the cache of phase statuses for faster lookup by ID
        Map<Long, PhaseStatus> statusesMap = buildPhaseStatusesLookupMap(phaseStatuses);

        // Build the cache of phase types for faster lookup by ID
        Map<Long, PhaseType> typesMap = buildPhaseTypesLookupMap(phaseTypes);

        // Get project details by status using Query Tool
        SearchProjectPhasesResponse results = dataAccessServiceRpc.searchProjectPhases(paramName, paramValue);

        // Convert returned data into Project objects
        Map<Long, Phase> cachedPhases = new HashMap<Long, Phase>();
        Map<Long, List<Object[]>> deferredDependencies = new HashMap<Long, List<Object[]>>();
        Workdays workdays = workdaysFactory.createWorkdaysInstance();
        Map<Long, Project> phProjects = new HashMap<>();
        Project currentPhProject = null;
        Phase currentPhase = null;
        List<ProjectPhaseProto> phasesData = results.getPhasesList();
        int recordNum = phasesData.size();
        for (int i = 0; i < recordNum; i++) {
            ProjectPhaseProto phaseData = phasesData.get(i);
            long projectId = phaseData.getProjectId();
            if ((currentPhProject == null) || (currentPhProject.getId() != projectId)) {
                currentPhProject = new Project(new Date(Long.MAX_VALUE), workdays);
                currentPhProject.setId(projectId);
                phProjects.put(projectId, currentPhProject);
            }

            long phaseId = phaseData.getProjectPhaseId();
            if ((currentPhase == null) || (currentPhase.getId() != phaseId)) {
                currentPhase = new Phase(currentPhProject, phaseData.getDuration());
                currentPhase.setId(phaseId);
                if (phaseData.hasActualEndTime()) {
                    currentPhase.setActualEndDate(new Date(phaseData.getActualEndTime().getSeconds() * 1000));
                }
                if (phaseData.hasActualStartTime()) {
                    currentPhase.setActualStartDate(new Date(phaseData.getActualStartTime().getSeconds() * 1000));
                }
                if (phaseData.hasFixedStartTime()) {
                    currentPhase.setFixedStartDate(new Date(phaseData.getFixedStartTime().getSeconds() * 1000));
                }
                if (phaseData.hasScheduledEndTime()) {
                    currentPhase.setScheduledEndDate(new Date(phaseData.getScheduledEndTime().getSeconds() * 1000));
                }
                if (phaseData.hasScheduledStartTime()) {
                    currentPhase.setScheduledStartDate(new Date(phaseData.getScheduledStartTime().getSeconds() * 1000));
                }
                if (phaseData.hasPhaseStatusId()) {
                    currentPhase.setPhaseStatus(statusesMap.get(phaseData.getPhaseStatusId()));
                }
                if (phaseData.hasPhaseTypeId()) {
                    currentPhase.setPhaseType(typesMap.get(phaseData.getPhaseTypeId()));
                }
                cachedPhases.put(phaseId, currentPhase);
                Date currentPhaseStartDate = currentPhase.getScheduledStartDate();
                Date currentProjectStartDate = currentPhProject.getStartDate();
                if (currentProjectStartDate.compareTo(currentPhaseStartDate) > 0) {
                    currentPhProject.setStartDate(currentPhaseStartDate);
                }
            }

            if (phaseData.hasDependentPhaseId()) {
                long dependencyId = phaseData.getDependencyPhaseId();
                long dependentId = phaseData.getDependentPhaseId();
                long lagTime = phaseData.getLagTime();
                boolean dependencyStart = phaseData.getDependencyStart();
                boolean dependentStart = phaseData.getDependentStart();
                if (cachedPhases.containsKey(dependencyId)) {
                    Dependency dependency = new Dependency(cachedPhases.get(dependencyId), currentPhase,
                                                           dependencyStart, dependentStart, lagTime);
                    currentPhase.addDependency(dependency);
                } else {
                    if (!deferredDependencies.containsKey(dependencyId)) {
                        deferredDependencies.put(dependencyId, new ArrayList<Object[]>());
                    }
                    deferredDependencies.get(dependencyId).add(
                        new Object[] {dependentId, lagTime, dependencyStart, dependentStart});
                }
            }
        }

        // Resolve deferred dependencies
        for (Long dependencyId : deferredDependencies.keySet()) {
            List<Object[]> dependencies = deferredDependencies.get(dependencyId);
            for (Object[] dependency : dependencies) {
                long dependentId = (Long) dependency[0];
                long lagTime = (Long) dependency[1];
                boolean dependencyStart = (Boolean) dependency[2];
                boolean dependentStart = (Boolean) dependency[3];
                Phase dependentPhase = cachedPhases.get(dependentId);
                Dependency dep = new Dependency(cachedPhases.get(dependencyId), dependentPhase,
                        dependencyStart, dependentStart, lagTime);
                dependentPhase.addDependency(dep);
            }
        }

        deferredDependencies.clear();
        cachedPhases.clear();
        statusesMap.clear();
        typesMap.clear();

        return phProjects;
    }
}
