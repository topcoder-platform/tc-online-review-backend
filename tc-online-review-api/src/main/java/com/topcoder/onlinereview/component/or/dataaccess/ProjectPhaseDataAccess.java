/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.or.dataaccess;

import com.topcoder.onlinereview.component.project.phase.Dependency;
import com.topcoder.onlinereview.component.project.phase.Phase;
import com.topcoder.onlinereview.component.project.phase.PhaseStatus;
import com.topcoder.onlinereview.component.project.phase.PhaseType;
import com.topcoder.onlinereview.component.project.phase.Project;
import com.topcoder.onlinereview.component.workday.Workdays;
import com.topcoder.onlinereview.component.workday.WorkdaysFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.component.or.dataaccess.BaseDataAccess.PROJECT_STATUS_ACTIVE_ID;
import static com.topcoder.onlinereview.component.or.dataaccess.BaseDataAccess.PROJECT_STATUS_DRAFT_ID;
import static com.topcoder.onlinereview.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.util.CommonUtils.getInt;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;

/**
 * A simple DAO for project phases backed up by Query Tool.
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
@Component
public class ProjectPhaseDataAccess {
  @Autowired private WorkdaysFactory workdaysFactory;
  @Autowired private BaseDataAccess baseDataAccess;

  /**
   * Constructs new <code>ProjectPhaseDataAccess</code> instance. This implementation does nothing.
   */
  public ProjectPhaseDataAccess() {}

  /**
   * Gets the phases for projects of <code>Active</code> status.
   *
   * @param phaseStatuses a <code>PhaseStatus</code> array listing the available phase statuses.
   * @param phaseTypes a <code>PhaseType</code> array listing the available phase types.
   * @return a <code>Map</code> mapping project IDs to the project phases.
   */
  public Map<Long, Project> searchActiveProjectPhases(
      PhaseStatus[] phaseStatuses, PhaseType[] phaseTypes) {
    return searchProjectPhasesByQueryTool(
        "tcs_project_phases_by_status",
        "stid",
        String.valueOf(PROJECT_STATUS_ACTIVE_ID),
        phaseStatuses,
        phaseTypes);
  }

  /**
   * Gets the phases for projects of <code>Draft</code> status.
   *
   * @param phaseStatuses a <code>PhaseStatus</code> array listing the available phase statuses.
   * @param phaseTypes a <code>PhaseType</code> array listing the available phase types.
   * @return a <code>Map</code> mapping project IDs to the project phases.
   */
  public Map<Long, Project> searchDraftProjectPhases(
      PhaseStatus[] phaseStatuses, PhaseType[] phaseTypes) {
    return searchProjectPhasesByQueryTool(
        "tcs_project_phases_by_status",
        "stid",
        String.valueOf(PROJECT_STATUS_DRAFT_ID),
        phaseStatuses,
        phaseTypes);
  }

  /**
   * Gets the phases for projects assigned to specified user.
   *
   * @param userId a <code>long</code> providing the user ID.
   * @param phaseStatuses a <code>PhaseStatus</code> array listing the available phase statuses.
   * @param phaseTypes a <code>PhaseType</code> array listing the available phase types.
   * @return a <code>Map</code> mapping project IDs to the project phases.
   */
  public Map<Long, Project> searchUserProjectPhases(
      long userId, PhaseStatus[] phaseStatuses, PhaseType[] phaseTypes) {
    return searchProjectPhasesByQueryTool(
        "tcs_project_phases_by_user", "uid", String.valueOf(userId), phaseStatuses, phaseTypes);
  }

  /**
   * Gets the phases for projects of specified status.
   *
   * @param queryName a <code>String</code> providing the name of the query to be run.
   * @param paramName a <code>String</code> providing the name of the query parameter for
   *     customization.
   * @param paramValue a <code>String</code> providing the value of the query parameter for
   *     customization.
   * @param phaseStatuses a <code>PhaseStatus</code> array listing the available phase statuses.
   * @param phaseTypes a <code>PhaseType</code> array listing the available phase types.
   * @return a <code>Project</code> array listing the project phases.
   * @throws DataAccessException if an unexpected error occurs while running the query via Query
   *     Tool.
   */
  private Map<Long, Project> searchProjectPhasesByQueryTool(
      String queryName,
      String paramName,
      String paramValue,
      PhaseStatus[] phaseStatuses,
      PhaseType[] phaseTypes) {

    // Build the cache of phase statuses for faster lookup by ID
    Map<Long, PhaseStatus> statusesMap = baseDataAccess.buildPhaseStatusesLookupMap(phaseStatuses);

    // Build the cache of phase types for faster lookup by ID
    Map<Long, PhaseType> typesMap = baseDataAccess.buildPhaseTypesLookupMap(phaseTypes);

    // Get project details by status using Query Tool
    var results = baseDataAccess.runQuery(queryName, paramName, paramValue);

    // Convert returned data into Project objects
    Map<Long, Phase> cachedPhases = new HashMap<Long, Phase>();
    Map<Long, List<Object[]>> deferredDependencies = new HashMap<Long, List<Object[]>>();
    Workdays workdays = workdaysFactory.createWorkdaysInstance();
    Map<Long, Project> phProjects = new HashMap<>();
    Project currentPhProject = null;
    Phase currentPhase = null;
    var phasesData = results.get(queryName);
    int recordNum = phasesData.size();
    for (int i = 0; i < recordNum; i++) {
      long projectId = getLong(phasesData.get(i), "project_id");
      if ((currentPhProject == null) || (currentPhProject.getId() != projectId)) {
        currentPhProject = new Project(new Date(Long.MAX_VALUE), workdays);
        currentPhProject.setId(projectId);
        phProjects.put(projectId, currentPhProject);
      }

      long phaseId = getLong(phasesData.get(i), "project_phase_id");
      if ((currentPhase == null) || (currentPhase.getId() != phaseId)) {
        currentPhase = new Phase(currentPhProject, getLong(phasesData.get(i), "duration"));
        currentPhase.setId(phaseId);
        currentPhase.setActualEndDate(getDate(phasesData.get(i), "actual_end_time"));
        currentPhase.setActualStartDate(getDate(phasesData.get(i), "actual_start_time"));
        currentPhase.setFixedStartDate(getDate(phasesData.get(i), "fixed_start_time"));
        currentPhase.setScheduledEndDate(getDate(phasesData.get(i), "scheduled_end_time"));
        currentPhase.setScheduledStartDate(getDate(phasesData.get(i), "scheduled_start_time"));
        currentPhase.setPhaseStatus(statusesMap.get(getLong(phasesData.get(i), "phase_status_id")));
        currentPhase.setPhaseType(typesMap.get(getLong(phasesData.get(i), "phase_type_id")));
        cachedPhases.put(phaseId, currentPhase);
        Date currentPhaseStartDate = currentPhase.getScheduledStartDate();
        Date currentProjectStartDate = currentPhProject.getStartDate();
        if (currentProjectStartDate.compareTo(currentPhaseStartDate) > 0) {
          currentPhProject.setStartDate(currentPhaseStartDate);
        }
      }

      if (phasesData.get(i).get("dependent_phase_id") != null) {
        long dependencyId = getLong(phasesData.get(i), "dependency_phase_id");
        long dependentId = getLong(phasesData.get(i), "dependent_phase_id");
        long lagTime = getLong(phasesData.get(i), "lag_time");
        boolean dependencyStart = (getInt(phasesData.get(i), "dependency_start") == 1);
        boolean dependentStart = (getInt(phasesData.get(i), "dependent_start") == 1);
        if (cachedPhases.containsKey(dependencyId)) {
          Dependency dependency =
              new Dependency(
                  cachedPhases.get(dependencyId),
                  currentPhase,
                  dependencyStart,
                  dependentStart,
                  lagTime);
          currentPhase.addDependency(dependency);
        } else {
          if (!deferredDependencies.containsKey(dependencyId)) {
            deferredDependencies.put(dependencyId, new ArrayList<Object[]>());
          }
          deferredDependencies
              .get(dependencyId)
              .add(new Object[] {dependentId, lagTime, dependencyStart, dependentStart});
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
        Dependency dep =
            new Dependency(
                cachedPhases.get(dependencyId),
                dependentPhase,
                dependencyStart,
                dependentStart,
                lagTime);
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
