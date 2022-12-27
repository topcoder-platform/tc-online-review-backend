/*
 * Copyright (c) 2006-2013, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.onlinereview.component.project.phase;

import com.topcoder.onlinereview.component.grpcclient.phase.PhaseServiceRpc;
import com.topcoder.onlinereview.component.workday.Workdays;
import com.topcoder.onlinereview.component.workday.WorkdaysFactory;
import com.topcoder.onlinereview.grpc.phase.proto.PhaseCriteriaProto;
import com.topcoder.onlinereview.grpc.phase.proto.PhaseDependencyProto;
import com.topcoder.onlinereview.grpc.phase.proto.PhaseProto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * This is an actual implementation of database persistence that is geared for Informix database.
 *
 * <p>All the methods uses the transactions to ensure database integrity. If any error will happen
 * in the batch operations, whole transaction will be replaced. Because the batch operation may
 * involve lot of queries, it is important to properly configure the database to avoid too long
 * transaction error.
 *
 * <p>Auditing Operator audit is based on simply filling in the create_user, create_date,
 * modify_user, and modify_date field for each create and update operation on any of the provided
 * tables. When creating an entry the above columns will be fill out with actual data.
 *
 * <p>When updating, only the update user and update timestamp will be set.
 *
 * <p>Thread Safety InformixPhasePersistence acts like a stateless bean with utility-like
 * functionality where function calls retain no state from one call to the next. Separate
 * connections are created each time a call is made and thus (assuming the connections are
 * different) there is no contention for a connection from competing threads. This class is
 * thread-safe.
 *
 * <p>Version 1.0.3 (Online Review Miscellaneous Improvements ) change notes:
 *
 * <ol>
 *   <li>Remove <code>AUDIT_DELETE_TYPEM</code> field.
 *   <li>Updated {@link #deletePhase(Phase)} method to fix the bug of phase audit. Phase audit can't
 *       exist for the phases that has been deleted because of the foreign key constraints.
 * </ol>
 *
 * <p>Version 1.0.4 (Online Review - Project Payments Integration Part 1 v1.0) change notes:
 *
 * @author AleaActaEst
 * @author kr00tki
 * @author flexme
 * @version 1.0.4
 */
@Component
public class PhasePersistence {

  @Autowired
  private PhaseServiceRpc phaseServiceRpc;

  @Autowired private WorkdaysFactory workdaysFactory;

  /**
   * Will return project instance for the given id. If the project can not be found then a null is
   * returned. The project will have all the depedencies filled out. If the project exists in the
   * database, but has no phases, the empty Project instance will be returned.
   *
   * @param projectId project id to find by.
   * @return Project for this id.
   * @throws PhasePersistenceException if any database error happen.
   */
  public Project getProjectPhases(long projectId) throws PhasePersistenceException {
    return getProjectPhases(new Long[] {projectId})[0];
  }

  /**
   * Will return an array of project instances for the given input array of project ids. If the
   * project can not be found then a null is returned in the return array. Returns are positional
   * thus id at index 2 of input is represented at index 2 of output. The project will have all the
   * depedencies filled out. If the project exists in the database, but has no phases, the empty
   * Project instance will be returned.
   *
   * @param projectIds an array of project ids
   * @return and array of Projects for the ids
   * @throws IllegalArgumentException if the input array is null.
   * @throws PhasePersistenceException if an database error occurs.
   */
  public Project[] getProjectPhases(Long[] projectIds) throws PhasePersistenceException {
    // empty array empty result
    if (projectIds.length == 0) {
      return new Project[0];
    }
    return getProjectPhasesImpl(projectIds);
  }

  /**
   * This the current implementation of the {getProjectPhases(long[])} method. It will first check
   * which project for the given ids exists and which have no phases. The it selects all the phases
   * for the projects, create them and return. If any project from the givenlist not exists, null
   * will be returned in that place.
   *
   * @param projectIds the ids of the projects to retrieve.
   * @return the array of projects with one to one mapping between project and it id.
   * @throws SQLException if any database error occurs.
   * @throws PhasePersistenceException if other error happen.
   */
  private Project[] getProjectPhasesImpl(Long[] projectIds) throws PhasePersistenceException {
    // if no ids - return empty array
    if (projectIds.length == 0) {
      return new Project[0];
    }
    // create workdays to be used to create the project
    Workdays workdays = workdaysFactory.createWorkdaysInstance();

    List<Long> result = phaseServiceRpc.getProjectIds(projectIds);
    Map<Long, Project> projectsMap =
        result.stream()
            .collect(
                toMap(
                    m -> m,
                    m -> {
                      Project project = new Project(new Date(Long.MAX_VALUE), workdays);
                      project.setId(m);
                      return project;
                    }));

    List<PhaseProto> phaseResult = phaseServiceRpc.getPhasesByProjectIds(projectIds);
    Map<Long, Phase> phasesMap =
        phaseResult.stream()
            .collect(
                toMap(
                    m -> m.getProjectId(),
                    m -> {
                      Project project = projectsMap.get(m.getProjectId());
                      Phase phase = populatePhase(m, project);
                      return phase;
                    }));

    // fill the phases depedencies and criteria for them
    if (phasesMap.size() > 0) {
      fillDependencies(phasesMap, projectIds);
      fillCriteria(phasesMap, projectIds);
    }

    // this comparator is used to get the lowest start date
    Comparator phasesComparator = new PhaseStartDateComparator();
    // set the correct date for each project
    for (Iterator it = projectsMap.values().iterator(); it.hasNext(); ) {
      Project project = (Project) it.next();
      Phase[] phases = project.getAllPhases(phasesComparator);
      // if project has any phases - get the first one
      if (phases.length > 0) {
        project.setStartDate(phases[0].getScheduledStartDate());
      }
    }

    // create the result array
    Project[] projects = new Project[projectIds.length];
    for (int i = 0; i < projectIds.length; i++) {
      projects[i] = projectsMap.get(projectIds[i]);
    }

    return projects;
  }

  /**
   * Returns all the PhaseTypes from the datastore. The returned array might be empty if no types
   * exists, but it won't be null.
   *
   * @return all available phase types stored in the database.
   * @throws PhasePersistenceException if any error occurs while accessing the database or creating
   *     PhaseTypes.
   */
  public PhaseType[] getAllPhaseTypes() throws PhasePersistenceException {
    // create the types
    List<PhaseType> result = phaseServiceRpc.getAllPhaseTypes();
    // convert list to array and return
    return result.toArray(new PhaseType[result.size()]);
  }

  /**
   * Creates the PhaseType instance using the data from the ResultSet.
   *
   * @param rs the source ResultSet for PhaseType object.
   * @return the PhaseType instance created from the ResultSet row.
   * @throws SQLException if errors occurs while accessing the ResultSet.
   */
  private PhaseType populatePhaseType(PhaseProto p) {
    return new PhaseType(p.getPhaseTypeId(), p.getPhaseTypeName());
  }

  /**
   * Returns all the PhaseStatuses from the datastore. The returned array might be empty if no
   * statuses exists, but it won't be null.
   *
   * @return all available phase statuses stored in the database.
   * @throws PhasePersistenceException if any error occurs while accessing the database or creating
   *     PhaseStatus.
   */
  public PhaseStatus[] getAllPhaseStatuses() throws PhasePersistenceException {
    // create the statuses
    List<PhaseStatus> result = phaseServiceRpc.getAllPhaseStatuses();
    // convert result to array and return
    return result.toArray(new PhaseStatus[result.size()]);
  }

  /**
   * Creates the PhaseStatus instance using the values from ResultSet.
   *
   * @param rs the source result set for the PhaseStatus object.
   * @return the PhaseStatus created from the ResultSet.
   * @throws SQLException if error occurs while accessing the ResultSet.
   */
  private PhaseStatus populatePhaseStatus(PhaseProto p) {
    return new PhaseStatus(p.getPhaseStatusId(), p.getPhaseStatusName());
  }

  /**
   * Creates the provided phase in persistence. All the phase depedencies will be also created. The
   * phase should have the unique id already generated and set.
   *
   * @param phase phase to be created.
   * @param operator the creation operator for audit proposes.
   * @throws IllegalArgumentException if phase or operator is null or if operator is am empty
   *     string.
   * @throws PhasePersistenceException if database relates error occurs.
   */
  public void createPhase(Phase phase, String operator) throws PhasePersistenceException {
    if (phase == null) {
      throw new IllegalArgumentException("phase cannot be null.");
    }

    createPhases(new Phase[] {phase}, operator);
  }

  /**
   * Creates the provided phase in persistence. All the phases depedencies will be also created.
   * Each phase should have the unique id already generated and set.
   *
   * @param phases an array of phases to create in persistence
   * @param operator the creation operator for audit proposes.
   * @throws IllegalArgumentException if phase or operator is null or if operator is an empty string
   *     or the array contains null value.
   * @throws PhasePersistenceException if database relates error occurs.
   */
  public void createPhases(Phase[] phases, String operator) throws PhasePersistenceException {
    checkPhases(phases);
    if (operator == null) {
      throw new IllegalArgumentException("operator cannot be null.");
    }

    if (operator.trim().length() == 0) {
      throw new IllegalArgumentException("operator cannot be empty String.");
    }

    if (phases.length == 0) {
      return;
    }

    Map<String, Long> lookUp = getCriteriaTypes();
    createPhasesImpl(phases, operator, lookUp);
    // if all ok - commit transaction
  }

  /**
   * This is the internal implementation of the {@link #createPhase(Phase, String)} method. It
   * creates the given array of the Phases in the persistence.
   *
   * @param phases the phases array to be created.
   * @param operator the creation audit operator.
   * @param lookUp the lookup map for the criteria name - id.
   * @throws SQLException if any database error occurs.
   * @throws PhasePersistenceException if other error occurs.
   */
  private void createPhasesImpl(Phase[] phases, String operator, Map<String, Long> lookUp)
      throws PhasePersistenceException {
    // the list of phases dependencies
    List<Dependency> dependencies = new ArrayList<>();
    // iterate over the phases array
    for (int i = 0; i < phases.length; i++) {
      Phase phase = phases[i];
      // add all phase dependencies to the list
      dependencies.addAll(Arrays.asList(phase.getAllDependencies()));

      phaseServiceRpc.createPhase(phase, operator);

      // create the criteria for phase
      createPhaseCriteria(phases[i], filterAttributes(phases[i].getAttributes()), operator, lookUp);
    }

    // create the dependencies for phases
    createDependency(dependencies, operator);
  }

  /**
   * Returns all the criteria types for lookup proposes. This will speed up the creation process.
   *
   * @return a map where the key is the criteria name and the value is the criteria type id.
   * @throws SQLException if any database error occurs.
   */
  private Map<String, Long> getCriteriaTypes() {
    return phaseServiceRpc.getAllPhaseCriteriaTypes().stream()
        .collect(toMap(m -> m.getName(), m -> m.getPhaseCriteriaTypeId()));
  }

  /**
   * Creates the phase criteria in the database. It uses helper lookup map to reduce database call
   * for all the criteria types.
   *
   * @param phase the phase to which the criteria belongs.
   * @param attribs the phases criteria and parameters. The key is the name of criteria, the vale is
   *     the parameter.
   * @param operator the operator for audit proposes.
   * @param lookUp the lookup values for the criteria types.
   * @throws SQLException thrown if any database error occurs.
   */
  private void createPhaseCriteria(
      Phase phase, Map attribs, String operator, Map<String, Long> lookUp) {
    // no work to do, return
    if (attribs.size() == 0) {
      return;
    }
    for (Object key : attribs.keySet()) {
      Long id = lookUp.get(key);
      if (id != null) {
        phaseServiceRpc.createPhaseCriteria(phase.getId(), id, attribs.get(key).toString(), operator);
      }
    }
  }

  /**
   * This helper method removes all attributes that have non-String hey.
   *
   * @param attributes the attributes to be filtered.
   * @return the filtered attributes map.
   */
  private static Map filterAttributes(Map attributes) {
    // make the copy because the input attributes are not modifiable
    Map attribs = new HashMap(attributes);
    for (Iterator it = attribs.entrySet().iterator(); it.hasNext(); ) {
      Map.Entry el = (Map.Entry) it.next();
      if (!(el.getKey() instanceof String)) {
        it.remove();
      }
    }
    return attribs;
  }

  /**
   * Creates the given list of dependencies in the database. All the phases must already be
   * persisted.
   *
   * @param dependencies the phases dependencies to.
   * @param operator the creation operator for audit proposes.
   * @throws SQLException if any database error happens.
   */
  private void createDependency(Collection<Dependency> dependencies, String operator) {
    for (Dependency dep : dependencies) {
      phaseServiceRpc.createPhaseDependency(dep, operator);
    }
  }

  /**
   * Reads a specific phase from the data store. If the phase with given id doesn't exist, <code>
   * null</code> value will be returned. The returned phase will have all its depedencies set.
   *
   * @param phaseId id of phase to fetch
   * @return the Phase object with given id, or <code>null</code> if not found.
   * @throws PhasePersistenceException if database related error occurs.
   */
  public Phase getPhase(long phaseId) throws PhasePersistenceException {
    return getPhases(new Long[] {phaseId})[0];
  }

  /**
   * Batch version of the {@link #getPhase(long) getPhase} method. For each entry in the input array
   * at index i of the Phase is not found then we return a null in the corresponding index in the
   * output array. All the phases dependencies will be satisfied.
   *
   * @param phaseIds an array of phase ids to fetch phases with
   * @return a non-null array of Phases.
   * @throws PhasePersistenceException if any database related error occurs.
   * @throws IllegalArgumentException if phaseIds array is null.
   */
  public Phase[] getPhases(Long[] phaseIds) throws PhasePersistenceException {
    if (phaseIds == null) {
      throw new IllegalArgumentException("phaseIds cannot be null.");
    }

    if (phaseIds.length == 0) {
      return new Phase[0];
    }
    return getPhasesImpl(phaseIds);
  }

  /**
   * This is the internal version of the {@link #checkPhases(Phase[])} method.
   *
   * @param phaseIds the phases ids that need to be retrieved.
   * @return the Phase array.
   * @throws SQLException if any database error occurs.
   * @throws PhasePersistenceException if the phase depedencies cannot be met.
   */
  private Phase[] getPhasesImpl(Long[] phaseIds) throws PhasePersistenceException {
    // this map will hold current phases. the key is phase id, value - phase instance
    Map<Long, Long> phaseProjects =
        phaseServiceRpc.getProjectIdsByPhaseIds(phaseIds).stream()
            .collect(toMap(m -> m.getProjectPhaseId(), m -> m.getProjectId()));
    Map<Long, Project> projects =
        Stream.of(getProjectPhasesImpl(phaseProjects.values().stream().distinct().toArray(Long[]::new)))
            .collect(toMap(Project::getId, p -> p));
    Map<Long, Optional<Phase>> phases =
        phaseProjects.entrySet().stream()
            .collect(
                toMap(
                    e -> e.getKey(),
                    e ->
                        Arrays.stream(projects.get(e.getValue()).getAllPhases())
                            .filter(p -> p.getId() == e.getKey())
                            .findFirst()));
    // prepare the result array - if any phase not exists - set the result for it to null
    Phase[] result = new Phase[phaseIds.length];
    for (int i = 0; i < phaseIds.length; i++) {
      result[i] = phases.get(phaseIds[i]).orElse(null);
    }
    return result;
  }

  /**
   * This method set the phase criteria into the phases from the given map.
   *
   * @param phases the Phases to which criteria will ba add. Key should be Long phase id, value -
   *     Phase object.
   * @param projectIds all the project ids.
   * @throws SQLException if any database error occurs.
   */
  private void fillCriteria(Map<Long, Phase> phases, Long[] projectIds) {
    List<PhaseCriteriaProto> result = phaseServiceRpc.getPhaseCriteriasByProjectIds(projectIds);
    for (PhaseCriteriaProto p : result) {
      Phase phase = phases.get(p.getProjectPhaseId());
      phase.setAttribute(p.getCriteriaName(), p.getParameter());
    }
  }

  /**
   * This method selects all the depedencies for phases.
   *
   * @param phases the map of already retrieved phases.
   * @param projectIds all the project ids.
   * @throws SQLException if database error occures.
   * @throws PhasePersistenceException if the phase depedencies cannot be filled.
   */
  private void fillDependencies(Map<Long, Phase> phases, Long[] projectIds)
      throws PhasePersistenceException {
    List<PhaseDependencyProto> result = phaseServiceRpc.getPhaseDependenciesByProjectId(projectIds);
    for (PhaseDependencyProto p : result) {
      // get the depedency
      Long dependentId = p.getDependentPhaseId();
      Long dependencyId = p.getDependencyPhaseId();
      if (phases.containsKey(dependentId) && phases.containsKey(dependencyId)) {
        Phase phase = phases.get(dependentId);
        Dependency dependency = createDependency(p, phases, phase);
        phase.addDependency(dependency);
      } else {
        // because we have retrieved all the phases for project before, this should never happen
        throw new PhasePersistenceException(
            "Missing dependecy: " + dependencyId + " for phase: " + dependentId);
      }
    }
  }

  /**
   * Create the Dependency instance from given result set.
   *
   * @param rs the source result set.
   * @param phases the retrieved phases.
   * @param dependantPhase the dependant phase.
   * @return the Dependency instance.
   * @throws SQLException if database error occures.
   */
  private static Dependency createDependency(
      PhaseDependencyProto p, Map<Long, Phase> phases, Phase dependantPhase) {
    Phase dependencyPhase = phases.get(p.getDependencyPhaseId());

    return new Dependency(
        dependencyPhase,
        dependantPhase,
        p.getDependencyStart(),
        p.getDependentStart(),
        p.getLagTime());
  }

  /**
   * Creates the Phase instance from the given ResultSet.
   *
   * @param rs the source result set.
   * @param project the project for phase.
   * @return the Phase instance.
   * @throws SQLException if database error occurs.
   */
  private Phase populatePhase(PhaseProto p, Project project) {
    Phase phase = new Phase(project, p.getDuration());
    phase.setActualEndDate(p.hasActualEndTime() ? new Date(p.getActualEndTime().getSeconds() * 1000) : null);
    phase.setActualStartDate(p.hasActualStartTime() ? new Date(p.getActualStartTime().getSeconds() * 1000) : null);
    phase.setFixedStartDate(p.hasFixedStartTime() ? new Date(p.getFixedStartTime().getSeconds() * 1000) : null);
    phase.setId(p.getProjectPhaseId());

    phase.setPhaseStatus(populatePhaseStatus(p));
    phase.setPhaseType(populatePhaseType(p));
    phase.setScheduledEndDate(new Date(p.getScheduledEndTime().getSeconds() * 1000));
    phase.setScheduledStartDate(new Date(p.getScheduledStartTime().getSeconds() * 1000));
    phase.setModifyDate(new Date(p.getModifyDate().getSeconds() * 1000));

    return phase;
  }

  /**
   * Update the provided phase in persistence. All the phases depedencies will be updated as well.
   *
   * @param phase update the phase in persistence
   * @param operator operator.
   * @throws PhasePersistenceException if any database related error occurs.
   * @throws IllegalArgumentException if phase or operator is null or the the operator is empty
   *     string.
   */
  public void updatePhase(Phase phase, String operator) throws PhasePersistenceException {
    if (phase == null) {
      throw new IllegalArgumentException("phase cannot be null.");
    }

    updatePhases(new Phase[] {phase}, operator);
  }

  /**
   * Update the provided phases in persistence. All the phases depedencies will be updated as well.
   * If any of the phases is not in the database, it will be create with the new id.
   *
   * @param phases an array of phases to update.
   * @param operator audit operator.
   * @throws PhasePersistenceException if any database related error occurs.
   * @throws IllegalArgumentException if phaseIds array is null or operator is null or the the
   *     operator is empty string.
   */
  public void updatePhases(Phase[] phases, String operator) throws PhasePersistenceException {
    checkPhases(phases);
    if (operator == null) {
      throw new IllegalArgumentException("operator cannot be null.");
    }
    if (operator.trim().length() == 0) {
      throw new IllegalArgumentException("operator cannot be empty String.");
    }
    // if not phases to update - just return
    if (phases.length == 0) {
      return;
    }
    // it will contain the new phases that should be created
    List<Phase> toCreate = new ArrayList<Phase>();
    List<Phase> toUpdate = new ArrayList<Phase>();
    // get the phases criteria lookups
    Map<String, Long> lookUps = getCriteriaTypes();
    // iterate over all phases
    for (Phase phase : phases) {
      // check if is a new phase - if so add it to list
      if (isNewPhase(phase)) {
        toCreate.add(phase);
      } else {
        toUpdate.add(phase);
      }
    }
    phaseServiceRpc.updatePhase(toUpdate, operator);
    for (Phase phase : toUpdate) {
      updatePhaseCriteria(phase, operator, lookUps);
      updateDependencies(phase, operator);
    }

    // create the new phases
    createPhasesImpl(toCreate.toArray(new Phase[toCreate.size()]), operator, lookUps);
  }

  /**
   * This method will update the phase depedencies in the persistence. Because the table has new
   * audit columns, we cannot just remove depedencies and create the again.
   *
   * @param phase the phase with depedencies to update.
   * @param operator the update operator for audit proposes.
   * @throws SQLException if any database error occurs.
   */
  private void updateDependencies(Phase phase, String operator) {
    // put all the dependencies to the map: key: dependency id, value: Dependency object.
    Map<Long, Dependency> dependencies = new HashMap<>();
    Dependency[] deps = phase.getAllDependencies();
    for (int i = 0; i < deps.length; i++) {
      dependencies.put(deps[i].getDependency().getId(), deps[i]);
    }
    // this list will keep dependecies to remove and to update
    List<Long> depsToRemove = new ArrayList<>();
    List<Dependency> depsToUpdate = new ArrayList<>();
    // select dependencies for the phase
    List<PhaseDependencyProto> result = phaseServiceRpc.getPhaseDependenciesByDependentPhaseId(phase.getId());
    for (PhaseDependencyProto p : result) {
      Long dependencyId = p.getDependencyPhaseId();
      if (dependencies.containsKey(dependencyId)) {
        // if yes
        // check to update (we don't need another db call of the row not need to be updated.
        Dependency dep = dependencies.get(dependencyId);
        // check the value
        if ((dep.isDependencyStart() != p.getDependencyStart())
            || (dep.isDependentStart() != p.getDependentStart())
            || (dep.getLagTime() != p.getLagTime())) {

          // if any is different - update the dependency
          depsToUpdate.add(dep);
        }
        // remove the dependency from map
        dependencies.remove(dependencyId);
      } else {
        // if not exists, delete the dependency from database
        depsToRemove.add(dependencyId);
      }
    }
    // if still some dependencies left - this means they are new - create them
    if (dependencies.size() > 0) {
      // create new deps
      createDependency(dependencies.values(), operator);
    }

    // if there is something to remove
    if (depsToRemove.size() > 0) {
      deleteDependencies(depsToRemove, phase.getId());
    }
    // if there is something to update
    if (depsToUpdate.size() > 0) {
      updateDependencies(depsToUpdate, operator);
    }
  }

  /**
   * Updates the dependecies in the database.
   *
   * @param deps the dependencies list.
   * @param operator the update operator for autid proposes.
   * @throws SQLException if any database error occurs.
   */
  private void updateDependencies(List<Dependency> deps, String operator) {
    for (Dependency dep : deps) {
      phaseServiceRpc.updatePhaseDependency(dep, operator);
    }
  }

  /**
   * Deletes the dependecies rom database.
   *
   * @param ids the ids of the dependecies.
   * @param dependantId the dependant phase id.
   * @throws SQLException if any database error occurs.
   */
  private void deleteDependencies(List<Long> ids, long dependantId) {
    phaseServiceRpc.deletePhaseDependencies(dependantId, ids);
  }

  /**
   * Updates the phase criteria for the given phase. It will create new ones, modify existsing and
   * delete the old.
   *
   * @param phase the phase to update.
   * @param operator the modification operator for audit proposes.
   * @param lookUp the lookup value for the criteria type ids.
   * @throws SQLException if any database error occurs.
   */
  private void updatePhaseCriteria(Phase phase, String operator, Map<String, Long> lookUp) {
    // get all the properties that only string key - they are potential criteria
    Map newCriteria = filterAttributes(phase.getAttributes());
    // the map for the old criteria
    Map<String, String> oldCriteria = phaseServiceRpc.getPhaseCriteriasByPhaseId(phase.getId())
        .stream()
        .collect(toMap(m -> m.getCriteriaName(), m -> m.getParameter()));
    // current update time
    List<String> oldKey = new ArrayList<>();
    // iterate over the new attributes taken from the updated phase
    for (Object key : newCriteria.keySet()) {
      // check if the value from new set is in the one from persistence
      String oldValue = oldCriteria.remove(key);
      if (oldValue != null && !oldValue.equals(newCriteria.get(key))) {
        // update if the values are different - update the criteria
        phaseServiceRpc.updatePhaseCriteria(phase.getId(), lookUp.get(key), newCriteria.get(key).toString(), operator);
      }
      if (oldValue != null) {
        // remove the criteria from list
        oldKey.add(key.toString());
      }
    }
    for (String key : oldKey) {
      newCriteria.remove(key);
    }
    // if left any new values - create them
    if (newCriteria.size() > 0) {
      createPhaseCriteria(phase, newCriteria, operator, lookUp);
    }
    // if any value left in old criteria - they need to be removed.
    if (oldCriteria.size() > 0) {
      List<Long> ids =
          oldCriteria.keySet().stream()
              .map(k -> lookUp.get(k))
              .collect(Collectors.toList());
      phaseServiceRpc.deletePhaseCriterias(phase.getId(), ids);
    }
  }

  /**
   * Deletes the provided phase from the persistence. All phase depedencies will be removed as well.
   * If phase is not in the persistence, nothing will happen.
   *
   * @param phase phase to delete.
   * @throws PhasePersistenceException if database error occurs.
   * @throws IllegalArgumentException if phase is null.
   */
  public void deletePhase(Phase phase) throws PhasePersistenceException {
    if (phase == null) {
      throw new IllegalArgumentException("phase cannot be null.");
    }

    deletePhases(new Phase[] {phase});
  }

  /**
   * Deletes the provided phases from the persistence. All phases depedencies and phase audit
   * records will be removed as well. If any phase is not in the persistence, nothing will happen.
   *
   * @param phases an array of phases to delete
   * @throws PhasePersistenceException if database error occurs.
   * @throws IllegalArgumentException if array is null or contains null.
   */
  public void deletePhases(Phase[] phases) throws PhasePersistenceException {
    checkPhases(phases);
    // if no phases to delete - just return
    if (phases.length == 0) {
      return;
    }
    List<Long> ids = Stream.of(phases).map(Phase::getId).collect(Collectors.toList());
    phaseServiceRpc.deletePhases(ids);
  }

  /**
   * Checks the phases array.
   *
   * @param phases the phases array.
   * @throws IllegalArgumentException if phases is null or contains null.
   */
  private static void checkPhases(Phase[] phases) {
    if (phases == null) {
      throw new IllegalArgumentException("phases cannot be null.");
    }

    for (int i = 0; i < phases.length; i++) {
      if (phases[i] == null) {
        throw new IllegalArgumentException("The phases array contains null element at index: " + i);
      }
    }
  }

  /**
   * Tests if the input phase is a new phase (i.e. needs its id to be set).
   *
   * @param phase Phase object to tests if it is new
   * @return true if the applied test woked; true otherwise.
   * @throws IllegalArgumentException if dependency is null.
   */
  public boolean isNewPhase(Phase phase) {
    if (phase == null) {
      throw new IllegalArgumentException("phase cannot be null.");
    }

    return phase.getId() == 0;
  }

  /**
   * Tests if the input dependency is a new depoendency (i.e. needs its id to be set) .
   *
   * <p>Implementation details As per PM comments in the forums. This logic is not currently known
   * and will be supplied later.
   *
   * @param dependency Dependency to check for being new.
   * @return true if new; false otherswise.
   * @throws IllegalArgumentException if dependency is null.
   */
  public boolean isNewDependency(Dependency dependency) {
    if (dependency == null) {
      throw new IllegalArgumentException("dependency cannot be null.");
    }
    return phaseServiceRpc.checkIfDependencyExists(dependency.getDependency().getId(),
        dependency.getDependent().getId());
  }

  /**
   * The private comparator of phases. It compares the phases scheduled start dates.
   *
   * @author kr00tki
   * @version 1.0
   */
  private class PhaseStartDateComparator implements Comparator {

    /**
     * Compares its two arguments for order. Returns a negative integer, zero, or a positive integer
     * as the first start date is less than, equal to, or greater than the second.
     *
     * @param o1 the first phase.
     * @param o2 the second phase.
     * @return a negative integer, zero, or a positive integer as the first argument is less than,
     *     equal to, or greater than the second.
     */
    public int compare(Object o1, Object o2) {
      return ((Phase) o1).getScheduledStartDate().compareTo(((Phase) o2).getScheduledStartDate());
    }
  }
}
