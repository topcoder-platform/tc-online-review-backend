/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.phase;

import com.topcoder.onlinereview.component.grpcclient.phasehandler.PhaseHandlerServiceRpc;
import com.topcoder.onlinereview.component.project.phase.handler.AbstractPhaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/**
 * Default implementation of the PhaseManager interface. The purpose of this class is to have a
 * facade which will allow a user to manage phase information (backed by a data store). Phases can
 * be started or ended. The logic to check the feasibility of the status change as well as to move
 * the status is pluggable through the PhaseHandler registration API (registerHandler,
 * unregisterHandler, etc.). Applications can provide the plug-ins on a per phase type/operation
 * basis if extra logic needs to be integrated.
 *
 * <p>In addition, a phase validator can be provided that will ensure that all phases that are
 * subject to persistent storage operations are validated before they are persisted. This is a
 * pluggable option.
 *
 * <p>API usage:
 *
 * <pre>
 * // set up the config manager
 * ConfigManager.getInstance().add(&quot;config.xml&quot;);
 * // create a manager using configuration
 * PhaseManager manager = new DefaultPhaseManager(&quot;test.default&quot;);
 * // set up a simple project with a single phase
 * final Project project = new Project(new Date(), new DefaultWorkdaysFactory(false).createWorkdaysInstance());
 * final PhaseType phaseTypeOne = new PhaseType(1, &quot;one&quot;);
 * final Phase phaseOne = new Phase(project, 1);
 * phaseOne.setPhaseType(phaseTypeOne);
 * phaseOne.setFixedStartDate(new Date());
 * phaseOne.setPhaseStatus(PhaseStatus.SCHEDULED);
 * project.addPhase(phaseOne);
 * // create some of the pluggable components
 * DemoIdGenerator idgen = new DemoIdGenerator();
 * DemoPhaseValidator validator = new DemoPhaseValidator();
 * DemoPhaseHandler handler = new DemoPhaseHandler();
 * PhasePersistence persistence = new DemoPhasePersistence() {
 *     public PhaseType[] getAllPhaseTypes() {
 *         return new PhaseType[] {phaseTypeOne};
 *     }
 *     public PhaseStatus[] getAllPhaseStatuses() {
 *         return new PhaseStatus[] {phaseOne.getPhaseStatus()};
 *     }
 *     public Project getProjectPhases(long projectId) {
 *         return project;
 *     }
 * };
 * // create manager programmatically
 * manager = new DefaultPhaseManager(persistence, idgen);
 * // set the validator
 * manager.setPhaseValidator(validator);
 * // register a phase handler for dealing with canStart()
 * manager.registerHandler(handler, phaseTypeOne, PhaseOperationEnum.START);
 * // do some operations
 * // check if phaseOne can be started
 * OperationCheckResult checkResult = manager.canStart(phaseOne);
 * // start
 * if (checkResult.isSuccess()) {
 *     manager.start(phaseOne, &quot;ivern&quot;);
 * } else {
 *     // print out a reason why phase cannot be started
 *     System.out.println(checkResult.getMessage());
 * }
 * // check if phaseOne can be ended
 * checkResult = manager.canEnd(phaseOne);
 * // end
 * if (checkResult.isSuccess()) {
 *     manager.end(phaseOne, &quot;sokol&quot;);
 * } else {
 *     // print out a reason why phase cannot be ended
 *     System.out.println(checkResult.getMessage());
 * }
 * // get all phase types
 * PhaseType[] allTypes = manager.getAllPhaseTypes();
 * // get all phase statuses
 * PhaseStatus[] allStatuses = manager.getAllPhaseStatuses();
 * // update the project
 * manager.updatePhases(project, &quot;ivern&quot;);
 * </pre>
 *
 * <p>Changes in 1.1:
 *
 * <ol>
 *   <li>Removed canCancel() and cancel() methods.
 *   <li>Changed return type of canStart() and canEnd() methods from boolean to
 *       OperationCheckResult.
 * </ol>
 *
 * <p>Thread Safety: DefaultPhaseManager is not thread safe since it is mutable and its state (data
 * such as handlers) can be compromised through race condition issues. To make this thread-safe we
 * would have to ensure that all the methods that use the internal handlers map have their access
 * synchronized.
 *
 * @author AleaActaEst, saarixx, RachaelLCook, sokol
 * @version 1.1
 */
@Component
public class PhaseManager {
  @Autowired
  PhaseHandlerServiceRpc phaseHandlerServiceRpc;
  /**
   * This is an inner class of DefaultPhaseManager. It is a comparator that compares Phase
   * instances.
   *
   * <p>Changes in 1.1:
   *
   * <ol>
   *   <li>Added generic parameter for implemented Comparator&lt;T&gt; interface.
   *   <li>Added default constructor.
   *   <li>Updated compare() method to use Phase instead of Object parameters.
   * </ol>
   *
   * <p>Thread Safety: This class is thread safe by virtue of not having any state.
   *
   * @author sokol
   * @version 1.1
   */
  private class PhaseComparator implements Comparator<Phase> {

    /**
     * Creates an instance of PhaseComparator.
     *
     * @since 1.1
     */
    public PhaseComparator() {}

    /**
     * Returns -1, 0, or 1 if the ID of the first Phase argument is less than, equal to, or greater
     * than the ID of the second Phase, respectively.
     *
     * <p>Changes in 1.1:
     *
     * <ol>
     *   <li>Changed parameter types and names
     * </ol>
     *
     * @param phase1 the first Phase to compare
     * @param phase2 the second Phase to compare
     * @return -1, 0, or 1 if the ID of the first Phase argument is less than, equal to, or greater
     *     than the ID of the second Phase, respectively
     * @throws IllegalArgumentException if phase1 or phase2 is null
     */
    public int compare(Phase phase1, Phase phase2) {
      // check arguments
      Helper.checkState(phase1 == null, "phase1 should not be null.");
      Helper.checkState(phase2 == null, "phase2 should not be null.");
      long p1 = phase1.getId(); // MODIFIED in 1.1
      long p2 = phase2.getId(); // MODIFIED in 1.1
      if (p1 < p2) {
        return -1;
      } else if (p1 > p2) {
        return 1;
      }
      return 0;
    }
  }

  /**
   * Mapping from HandlerRegistryInfo keys to PhaseHandler values. This is used to look up phase
   * handlers when performing operations. Collection instance is initialized during construction and
   * never changed after that. Cannot be null, cannot contain null key or value.
   *
   * <p>Is used in constructor, registerHandler(), unregisterHandler(), getAllHandlers(),
   * getHandlerRegistrationInfo() and getPhaseHandler().
   *
   * <p>Changes in 1.1:
   *
   * <ol>
   *   <li>Added generic parameters to the collection types.
   * </ol>
   */
  private final Map<HandlerRegistryInfo, PhaseHandler> handlers = new HashMap<>();

  /**
   * The phase persistence. This member is initialized by the constructor and does not change over
   * the life of the object.
   */
  @Autowired private PhasePersistence persistence;

  /** The phase validator. */
  @Autowired private PhaseValidator phaseValidator;

  /**
   * Synchronizes the current state of the specified project with persistent storage. This method
   * first validates all of the phases in the project, then generates IDs for any new phases.
   * Finally, the phases of the specified input project are compared to the phases already in the
   * database. If any new phases are encountered, they are added to the persistent store via {@link
   * PhasePersistence#createPhases createPhases}. If any phases are missing from the input, they are
   * deleted using {@link PhasePersistence#deletePhases deletePhases}. All other phases are updated
   * using {@link PhasePersistence#updatePhases updatePhases}.
   *
   * @throws IllegalArgumentException if either argument is <code>null</code> or the empty string
   * @throws PhaseManagementException if a phase fails validation, or if an error occurs while
   *     persisting the updates or generating the IDs
   * @param project project for which to update phases
   * @param operator the operator performing the action
   */
  public void updatePhases(Project project, String operator) throws PhaseManagementException {
    checkUpdatePhasesArguments(project, operator);
    Phase[] phases = project.getAllPhases();
    // first, validate all the phases if a validator exists
    for (int i = 0; i < phases.length; ++i) {
      try {
        phaseValidator.validate(phases[i]);
      } catch (PhaseValidationException ex) {
        throw new PhaseManagementException("validation failure for phase " + phases[i].getId(), ex);
      }
    }
    try {
      // separate the phases into three batches: additions, deletions, and updates
      TreeSet<Phase> delete = new TreeSet<Phase>(new PhaseComparator()); // UPDATED in 1.1
      TreeSet<Phase> add = new TreeSet<Phase>(new PhaseComparator()); // UPDATED in 1.1
      TreeSet<Phase> update = new TreeSet<Phase>(new PhaseComparator()); // UPDATED in 1.1
      // initially, mark all of the currently existing phases for deletion
      Project existingProject = getPhases(project.getId());
      if (existingProject != null) {
        Phase[] existingPhases = existingProject.getAllPhases();
        for (int i = 0; i < existingPhases.length; ++i) {
          delete.add(existingPhases[i]);
        }
      }
      // for each phase in the input project, determine whether it's a new phase or an existing
      // phase
      // if it's an existing phase, remove it from the set of phases to delete
      for (int i = 0; i < phases.length; ++i) {
        if (delete.remove(phases[i])) {
          // the phase already exists, so update it
          update.add(phases[i]);
        } else {
          // the phase doesn't already exist, so add it
          add.add(phases[i]);
        }
      }
      // anything left over in the delete set at this point does not exist in the input project
      // finally, perform the creations, updates, and deletions
      if (add.size() > 0) {
        persistence.createPhases(add.toArray(new Phase[0]), operator);
      }
      if (update.size() > 0) {
        persistence.updatePhases(update.toArray(new Phase[0]), operator);
      }
      if (delete.size() > 0) {
        persistence.deletePhases(delete.toArray(new Phase[0]));
      }
    } catch (PhasePersistenceException ex) {
      throw new PhaseManagementException("phase persistence error", ex);
    }
  }

  /**
   * Validates the arguments to <code>updatePhases</code>. A non-exceptional return indicates
   * success.
   *
   * <p>Changes in 1.1:
   *
   * <ol>
   *   <li>Made static.
   * </ol>
   *
   * @param project project for which to update phases
   * @param operator the operator performing the action
   * @throws IllegalArgumentException if either argument is <code>null</code> or the empty string
   */
  private static void checkUpdatePhasesArguments(Project project, String operator) {
    if (project == null) {
      throw new IllegalArgumentException("project must be non-null");
    }
    if (operator == null) {
      throw new IllegalArgumentException("operator must be non-null");
    }
    if (operator.trim().length() == 0) {
      throw new IllegalArgumentException("operator must be non-empty");
    }
  }

  /**
   * Returns the <code>Project</code> corresponding to the specified ID. If no such project exists,
   * returns <code>null</code>.
   *
   * @throws PhaseManagementException if an error occurred querying the project from the persistent
   *     store
   * @param project id of the project to fetch
   * @return the project corresponding to the specified ID, or <code>null</code> if no such project
   *     exists
   */
  public Project getPhases(long project) throws PhaseManagementException {
    try {
      return persistence.getProjectPhases(project);
    } catch (PhasePersistenceException ex) {
      throw new PhaseManagementException("phase persistence error", ex);
    }
  }

  /**
   * Similar to {@link #getPhases(long) getPhases(long)}, except this method queries multiple
   * projects in one call. Indices in the returned array correspond to indices in the input array.
   * If a specified project cannot be found, a <code>null</code> will be returned in the
   * corresponding array position.
   *
   * @throws PhaseManagementException if an error occurred querying the projects from the persistent
   *     store
   * @throws IllegalArgumentException if <code>projects</code> is <code>null</code>
   * @param projects the project IDs to look up
   * @return the <code>Project</code> instances corresponding to the specified project IDs
   */
  public Project[] getPhases(Long[] projects) throws PhaseManagementException {
    if (projects == null) {
      throw new IllegalArgumentException(
          "arguments to DefaultPhaseManager#getPhases must be non-null");
    }
    try {
      return persistence.getProjectPhases(projects);
    } catch (PhasePersistenceException ex) {
      throw new PhaseManagementException("phase persistence error", ex);
    }
  }

  /**
   * Returns an array of all phase types by calling the {@link PhasePersistence#getAllPhaseStatuses
   * getAllPhaseTypes} method of this manager's configured persistence object.
   *
   * @throws PhaseManagementException if an error occurred retrieving the types from persistent
   *     storage
   * @return an array of all the phase types
   */
  public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
    try {
      return persistence.getAllPhaseTypes();
    } catch (PhasePersistenceException ex) {
      throw new PhaseManagementException("phase persistence error", ex);
    }
  }

  /**
   * Returns an array of all phase statuses by calling the {@link
   * PhasePersistence#getAllPhaseStatuses getAllPhaseStatuses} method of this manager's configured
   * persistence object.
   *
   * @throws PhaseManagementException if an error occurred retrieving the statuses from persistent
   *     storage
   * @return an array of all the phase statuses
   */
  public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
    try {
      return persistence.getAllPhaseStatuses();
    } catch (PhasePersistenceException ex) {
      throw new PhaseManagementException("phase persistence error", ex);
    }
  }

  /**
   * Determines whether it is possible to start the specified phase. If a PhaseHandler phase handler
   * has been registered for the start operation of the given phase type, its canPerform method will
   * be called to determine whether the phase can be started. If no handler is registered, this
   * method returns OperationCheckResult.SUCCESS if the phase's start date is less than or equal to
   * the current date.
   *
   * <p>Changes in 1.1:
   *
   * <ol>
   *   <li>Changed return type from boolean to OperationCheckResult.
   *   <li>Added step for creating OperationCheckResult instance with reasoning message.
   * </ol>
   *
   * @param phase phase to test for starting
   * @return the validation result indicating whether phase can be started, and if not, providing a
   *     reasoning message (not null)
   * @throws IllegalArgumentException if phase is <code>null</code>
   * @throws PhaseHandlingException propagated from the phase handler (if any)
   */
  public OperationCheckResult canStart(Phase phase) throws PhaseHandlingException {
    if (phase == null) {
      throw new IllegalArgumentException("null argument to DefaultPhaseManager#canStart");
    }
    PhaseHandler handler = getPhaseHandler(phase, PhaseOperationEnum.START);
    if (handler != null) {
      return handler.canPerform(phase);
    }
    boolean success = phase.calcStartDate().compareTo(new Date()) <= 0; // UPDATED in 1.1
    return success
        ? OperationCheckResult.SUCCESS
        : new OperationCheckResult("Phase start time is not yet reached"); // NEW in 1.1
  }

  /**
   * Starts the specified phase. If a PhaseHandler phase handler is set for the start operation of
   * the phase's type, the handler's perform method is invoked first. Next, the phase's status is
   * set to OPEN and the phase's actual start date is set to the current date. Finally, the changes
   * are persisted by delegating to the configured phase persistence object.
   *
   * @param phase the phase to start
   * @param operator the operator starting the phase
   * @throws IllegalArgumentException if either argument is null or an empty string
   * @throws PhaseHandlingException if an exception occurs while starting the phase
   * @throws PhaseManagementException if an error occurs while persisting the change
   */
  public void start(Phase phase, String operator) throws PhaseManagementException {
    if (phase == null) {
      throw new IllegalArgumentException("phase must be non-null");
    }
    if (operator == null) {
      throw new IllegalArgumentException("operator must be non-null");
    }
    if (operator.trim().length() == 0) {
      throw new IllegalArgumentException("operator must be non-empty");
    }
    PhaseHandler handler = null;
    if (phase.getPhaseType() != null) {
      handler = getPhaseHandler(phase, PhaseOperationEnum.START);
      if (handler != null) {
        handler.perform(phaseHandlerServiceRpc, phase, operator);
      }
    }
    String oldStatus = phase.getPhaseStatus().getName();
    phase.setPhaseStatus(
        new PhaseStatus(PhaseStatusEnum.OPEN.getId(), PhaseStatusEnum.OPEN.getName()));
    phase.setActualStartDate(new Date());
    try {
      Phase[] allPhases = phase.getProject().getAllPhases();
      recalculateScheduledDates(allPhases);
      persistence.updatePhases(allPhases, operator);
      sendEmail(handler, phase.getId(), oldStatus, true);
    } catch (PhasePersistenceException ex) {
      sendEmail(handler, phase.getId(), oldStatus, false);
      throw new PhaseManagementException("phase persistence error", ex);
    }
  }

  /**
   * Send phase updated email.
   *
   * @param handler
   * @param phaseId
   * @param phaseStatus
   * @param send
   */
  private void sendEmail(PhaseHandler handler, Long phaseId, String phaseStatus, boolean send) {
    if (handler != null && handler instanceof AbstractPhaseHandler) {
      ((AbstractPhaseHandler) handler).sendEmailAfterUpdatePhase(phaseId, phaseStatus, send);
    }
  }

  /**
   * Determines whether it is possible to end the specified phase. If a PhaseHandler phase handler
   * has been registered for the end operation of the given phase type, its canPerform method will
   * be called to determine whether the phase can be ended. If no handler is registered, this method
   * returns OperationCheckResult.SUCCESS if the phase's end date is less than or equal to the
   * current date.
   *
   * <p>Changes in 1.1:
   *
   * <ol>
   *   <li>Changed return type from boolean to OperationCheckResult.
   *   <li>Added step for creating OperationCheckResult instance with reasoning message.
   * </ol>
   *
   * @param phase phase to test for ending
   * @return the validation result indicating whether phase can be ended, and if not, providing a
   *     reasoning message (not null)
   * @throws IllegalArgumentException if phase is <code>null</code>
   * @throws PhaseHandlingException propagated from the phase handler (if any)
   */
  public OperationCheckResult canEnd(Phase phase) throws PhaseHandlingException {
    if (phase == null) {
      throw new IllegalArgumentException("null argument to DefaultPhaseManager#canEnd");
    }
    PhaseHandler handler = getPhaseHandler(phase, PhaseOperationEnum.END);
    if (handler != null) {
      return handler.canPerform(phase);
    }
    boolean success = phase.calcEndDate().compareTo(new Date()) <= 0;
    // NEW in 1.1
    return success
        ? OperationCheckResult.SUCCESS
        : new OperationCheckResult("Phase end time is not yet reached");
  }

  /**
   * Ends the specified phase. If a PhaseHandler phase handler is set for the end operation of the
   * phase's type, the handler's perform method is invoked first. Next, the phase's status is set to
   * CLOSED and the phase's actual end date is set to the current date. Finally, the changes are
   * persisted by delegating to the configured phase persistence object.
   *
   * @param phase the phase to end
   * @param operator the operator ending the phase
   * @throws IllegalArgumentException if either argument is null or an empty string
   * @throws PhaseHandlingException if an exception occurs while starting the phase
   * @throws PhaseManagementException if an error occurs while persisting the change
   */
  public void end(Phase phase, String operator) throws PhaseManagementException {
    if (phase == null) {
      throw new IllegalArgumentException("phase must be non-null");
    }
    if (operator == null) {
      throw new IllegalArgumentException("operator must be non-null");
    }
    if (operator.trim().length() == 0) {
      throw new IllegalArgumentException("operator must be non-empty");
    }
    PhaseHandler handler = null;
    if (phase.getPhaseType() != null) {
      handler = getPhaseHandler(phase, PhaseOperationEnum.END);
      if (handler != null) {
        handler.perform(phaseHandlerServiceRpc, phase, operator);
      }
    }
    String oldStatus = phase.getPhaseStatus().getName();
    phase.setPhaseStatus(
        new PhaseStatus(PhaseStatusEnum.CLOSED.getId(), PhaseStatusEnum.CLOSED.getName()));
    phase.setActualEndDate(new Date());
    try {
      Phase[] allPhases = phase.getProject().getAllPhases();
      recalculateScheduledDates(allPhases);
      persistence.updatePhases(allPhases, operator);
      sendEmail(handler, phase.getId(), oldStatus, true);
    } catch (PhasePersistenceException ex) {
      sendEmail(handler, phase.getId(), oldStatus, false);
      throw new PhaseManagementException("phase persistence error", ex);
    }
  }

  /**
   * Registers a custom handler for the specified phase type and operation. If present, handlers
   * override the default behavior for determining whether a given operation can be performed on a
   * given phase. If a handler already exists for the specified type/operation combination, it will
   * be replaced by the specified handler.
   *
   * <p>Note that <code>type</code> is stored in the registry by reference (rather than copied) so
   * the caller should take care not to subsequently modify the type. Doing so may cause the
   * registry to become inconsistent.
   *
   * @throws IllegalArgumentException if any argument is null
   * @param handler the handler
   * @param type the phase type to associate with the handler
   * @param operation the operation to associate with the handler
   */
  public void registerHandler(PhaseHandler handler, PhaseType type, PhaseOperationEnum operation) {
    if (handler == null) {
      throw new IllegalArgumentException("handler must be non-null");
    }
    if (type == null) {
      throw new IllegalArgumentException("type must be non-null");
    }
    if (operation == null) {
      throw new IllegalArgumentException("operation must be non-null");
    }
    handlers.put(new HandlerRegistryInfo(type, operation), handler);
  }

  /**
   * Unregisters the handler (if any) associated with the specified phase type and operation and
   * returns a reference to the handler. Returns <code>null</code> if no handler is associated with
   * the specified type/operation combination.
   *
   * @throws IllegalArgumentException if either argument is <code>null</code>
   * @param type the phase type associated with the handler to unregister
   * @param operation the operation associated with the handler to unregister
   * @return the previously registered handler, or <code>null</code> if no handler was registered
   */
  public PhaseHandler unregisterHandler(PhaseType type, PhaseOperationEnum operation) {
    if (type == null) {
      throw new IllegalArgumentException("type must be non-null");
    }
    if (operation == null) {
      throw new IllegalArgumentException("operation must be non-null");
    }
    return handlers.remove(new HandlerRegistryInfo(type, operation));
  }

  /**
   * Returns an array of all the currently registered phase handlers. If a handler is registered
   * more than one (for different phase/operation combinations), it will appear only once in the
   * array.
   *
   * <p>Changes in 1.1:
   *
   * <ol>
   *   <li>Just added generic parameters for the collection types in the code.
   * </ol>
   *
   * @return all of the currently registered phase handlers
   */
  public PhaseHandler[] getAllHandlers() {
    // Copying the values into a set will remove duplicate values
    Set<PhaseHandler> allHandlers = new HashSet<PhaseHandler>(handlers.values()); // UPDATED in 1.1
    return allHandlers.toArray(new PhaseHandler[0]);
  }

  /**
   * Returns the phase type(s) and operation(s) associated with the specified handler in the handler
   * registry. Returns an empty array if the handler is not registered.
   *
   * <p>Changes in 1.1:
   *
   * <ol>
   *   <li>Just added generic parameters for the collection types in the code.
   * </ol>
   *
   * @throws IllegalArgumentException if <code>handler</code> is <code>null</code>
   * @param handler handler of interest
   * @return the registration entries associated with the handler
   */
  public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler handler) {
    if (handler == null) {
      throw new IllegalArgumentException("handler must not be null");
    }
    final HashSet<HandlerRegistryInfo> hri = new HashSet<HandlerRegistryInfo>(); // UPDATED in 1.1
    for (Iterator<Entry<HandlerRegistryInfo, PhaseHandler>> it = handlers.entrySet().iterator();
        it.hasNext(); ) {
      Entry<HandlerRegistryInfo, PhaseHandler> entry = it.next();
      if (entry.getValue() == handler) {
        hri.add(entry.getKey());
      }
    }
    return hri.toArray(new HandlerRegistryInfo[0]);
  }

  /**
   * Sets the current phase validator for this manager.
   *
   * @throws IllegalArgumentException if the validator is null
   * @param phaseValidator the validator to use for this manager
   */
  public void setPhaseValidator(PhaseValidator phaseValidator) {
    if (phaseValidator == null) {
      throw new IllegalArgumentException("phase validator cannot be set to null");
    }
    this.phaseValidator = phaseValidator;
  }

  /**
   * Returns the phase handler associated with the specified phase and operation, or <code>null
   * </code> if no such handler exists.
   *
   * @param phase the phase
   * @param operation the phase operation
   * @return the phase handler associated with the specified phase and operation
   */
  private PhaseHandler getPhaseHandler(Phase phase, PhaseOperationEnum operation) {
    HandlerRegistryInfo hri = new HandlerRegistryInfo(phase.getPhaseType(), operation);
    return handlers.get(hri);
  }

  /**
   * Recalculate scheduled start date and end date for all phases when a phase is moved.
   *
   * <p>Changes in 1.1:
   *
   * <ol>
   *   <li>Made static.
   * </ol>
   *
   * @param allPhases all the phases for the project.
   */
  private static void recalculateScheduledDates(Phase[] allPhases) {
    for (int i = 0; i < allPhases.length; ++i) {
      Phase phase = allPhases[i];
      phase.setScheduledStartDate(phase.calcStartDate());
      phase.setScheduledEndDate(phase.calcEndDate());
    }
  }
}
