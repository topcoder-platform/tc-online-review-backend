/*
 * Copyright (C) 2009-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.phase.handler;

import com.topcoder.onlinereview.component.project.phase.ManagerHelper;
import com.topcoder.onlinereview.component.project.phase.OperationCheckResult;
import com.topcoder.onlinereview.component.project.phase.Phase;
import com.topcoder.onlinereview.component.project.phase.PhaseHandlingException;
import com.topcoder.onlinereview.component.resource.Resource;
import com.topcoder.onlinereview.component.resource.ResourceFilterBuilder;
import com.topcoder.onlinereview.component.resource.ResourceManager;
import com.topcoder.onlinereview.component.resource.ResourcePersistenceException;
import com.topcoder.onlinereview.component.search.SearchBuilderException;
import com.topcoder.onlinereview.component.search.filter.AndFilter;
import com.topcoder.onlinereview.component.search.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This class implements PhaseHandler interface to provide methods to check if a phase can be executed and to add
 * extra logic to execute a phase. It will be used by Phase Management component. It is configurable using an input
 * namespace. The configurable parameters include database connection, email sending and the required number of
 * registrations. This class handle the registration phase. If the input is of other phase types,
 * PhaseNotSupportedException will be thrown.
 * </p>
 * <p>
 * The registration phase can start whenever the dependencies are met and can stop when:
 * <ul>
 * <li>The dependencies are met</li>
 * <li>The period has passed</li>
 * <li>The number of registrations meets the required number.</li>
 * <li>The parent projects (if any) are completed.</li>
 * </ul>
 * </p>
 * <p>
 * There is no additional logic for executing this phase.
 * </p>
 * <p>
 * <p>
 * Update in version 1.1.: Modify the <code>perform</code> method to add a post-mortem phase where there is no
 * registration at phase end.
 * </p>
 * <p>
 * Version 1.2 changes note:
 * <ul>
 * <li>Added capability to support different email template for different role (e.g. Submitter, Reviewer, Manager,
 * etc).</li>
 * <li>Support for more information in the email generated: for stop, the number of registrant and info about
 * registrant.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.3 (Online Review End Of Project Analysis Assembly 1.0) Change notes:
 * <ol>
 * <li>Updated {@link #perform(Phase, String)} method to use updated PhasesHelper#insertPostMortemPhase(Project ,
 * Phase, ManagerHelper, String) method for creating <code>Post-Mortem</code> phase.</li>
 * </ol>
 * </p>
 * <p>
 * Version 1.4 Change notes:
 * <ol>
 * <li>Dependency projects are checked and project start delayed if required only if this phase is the first phase
 * in the project.</li>
 * </ol>
 * </p>
 * <p>
 * Thread safety: This class is thread safe because it is immutable.
 * </p>
 * <p>
 * Version 1.6.1 changes note:
 * <ul>
 * <li>canPerform() method was updated to return not only true/false value, but additionally an explanation message
 * in case if operation cannot be performed</li>
 * </ul>
 * </p>
 * @author tuenm, bose_java, argolite, waits, saarixx, myxgyy, microsky
 * @version 1.8.1
 */
public class RegistrationPhaseHandler extends AbstractPhaseHandler {
    /**
     * Represents the default namespace of this class. It is used in the default
     * constructor to load configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.RegistrationPhaseHandler";

    /**
     * Represents the logger for this class. Is initialized during class loading and never
     * changed after that.
     * @since 1.4
     */
    private static final Logger log = LoggerFactory.getLogger(RegistrationPhaseHandler.class.getName());

    /**
     * Create a new instance of RegistrationPhaseHandler using the given namespace for
     * loading configuration settings.
     * @throws IllegalArgumentException
     *             if the input is null or empty string.
     */
    public RegistrationPhaseHandler(ManagerHelper managerHelper,
                                    List<EmailScheme> emailSchemes,
                                    EmailScheme reviewFeedbackEmailScheme,
                                    EmailOptions defaultStartEmailOption,
                                    EmailOptions defaultEndEmailOption) {
        super(managerHelper, emailSchemes, reviewFeedbackEmailScheme, defaultStartEmailOption, defaultEndEmailOption);
    }

    /**
     * Check if the input phase can be executed or not. This method will check the phase
     * status to see what will be executed. This method will be called by canStart() and
     * canEnd() methods of PhaseManager implementations in Phase Management component.
     * <p>
     * If the input phase status is Scheduled, then it will check if the phase can be started using the following
     * conditions:
     * <ul>
     * <li>The dependencies are met</li>
     * <li>phase start time is reached</li>
     * <li>Update in version 1.4: the current phase is the NOT first phase in the project OR all parent projects
     * are completed</li>
     * </ul>
     * </p>
     * <p>
     * If the input phase status is Open, then it will check if the phase can be stopped using the following
     * conditions:
     * <ul>
     * <li>The dependencies are met</li>
     * <li>The period has passed</li>
     * <li>The number of registrations meets the required number.</li>
     * </ul>
     * </p>
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be thrown.
     * </p>
     * <p>
     * Version 1.6.1 changes note:
     * <ul>
     * <li>The return changes from boolean to OperationCheckResult.</li>
     * </ul>
     * </p>
     * @param phase
     *            The input phase to check.
     * @return the validation result indicating whether the associated operation can be performed, and if not,
     *         providing a reasoning message (not null)
     * @throws PhaseNotSupportedException
     *             if the input phase type is not &quot;Registration&quot; type.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException
     *             if the input is null.
     */
    public OperationCheckResult canPerform(Phase phase) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkPhaseType(phase, Constants.PHASE_REGISTRATION);

        // will throw exception if phase status is neither "Scheduled" nor  "Open"
        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        OperationCheckResult result;
        if (toStart) {
            result = PhasesHelper.checkPhaseCanStart(phase);
            if (!result.isSuccess()) {
                return result;
            }

            // This is NOT the first phase in the project or all parent projects are completed
            if (!PhasesHelper.isFirstPhase(phase) ||
                PhasesHelper.areParentProjectsCompleted(phase, this.getManagerHelper(), log)) {
                return OperationCheckResult.SUCCESS;
            } else {
                return new OperationCheckResult("Not all parent projects are completed");
            }
        } else {
            result = PhasesHelper.checkPhaseDependenciesMet(phase, false);

            // version 1.1 : can stop if registration is empty
            if (!result.isSuccess()) {
                return result;
            } else if (!PhasesHelper.reachedPhaseEndTime(phase)) {
                return new OperationCheckResult("Phase end time is not yet reached.");
            } else if (!(areRegistrationsEnough(phase) || isRegistrationEmpty(phase, null))) {
                return new OperationCheckResult("Not enough registrants.");
            }
            return OperationCheckResult.SUCCESS;
        }
    }

    /**
     * Provides additional logic to execute a phase. This method will be called by start()
     * and end() methods of PhaseManager implementations in Phase Management component.
     * This method can send email to a group of users associated with timeline
     * notification for the project. The email can be send on start phase or end phase
     * base on configuration settings.
     * <p>
     * If the input phase status is Closed, then PhaseHandlingException will be thrown.
     * </p>
     * <p>
     * Update in version 1.1: Add a new post-mortem phase if there is no registration when phase ends.
     * </p>
     * <p>
     * Update in version 1.2: Support for more information in the email generated, for stop, the number of
     * registrant and info about registrant.
     * </p>
     * @param phase
     *            The input phase to check.
     * @param operator
     *            The operator that execute the phase.
     * @throws PhaseNotSupportedException
     *             if the input phase type is not "Registration" type.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException
     *             if the input parameters is null or empty string.
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkString(operator, "operator");
        PhasesHelper.checkPhaseType(phase, Constants.PHASE_REGISTRATION);

        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());
        Map<String, Object> values = new HashMap<String, Object>();
        if (!toStart && isRegistrationEmpty(phase, values)) {
            // if no registration, insert post-Mortem phase
            PhasesHelper.insertPostMortemPhase(phase.getProject(), phase, getManagerHelper(), operator);
        }

        sendPhaseEmails(phase, values);
    }

    /**
     * This method checks whether the registration phase has empty registrations, if yes,
     * return true.
     * <p>
     * Update in version 1.2: Support for more information in the email generated, for stop, the number of
     * registrant and info about registrant.
     * </p>
     * @param phase
     *            the phase to check.
     * @param values
     *            the values map to hold the information for email generation
     * @return true if there is no registrations, false otherwise.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     */
    private boolean isRegistrationEmpty(Phase phase, Map<String, Object> values)
        throws PhaseHandlingException {
        Resource[] resources = searchResources(phase);

        // support adding more information in the email from version 1.2
        if (values != null) {
            values.put("N_REGISTRANTS", resources.length);
            values.put("REGISTRANT", registrantInfo(resources));
        }

        // there is no registrations, return true
        return resources.length == 0;
    }

    /**
     * <p>
     * Constructs the values map list for email generation. All the registrants information will be listed.
     * </p>
     * @param resources
     *            registrant list, not null
     * @return List of map values, not null, could be empty
     * @since 1.2
     */
    private List<Map<String, Object>> registrantInfo(Resource[] resources) {
        // Sort registrants by their reliability in descending order.
        Arrays.sort(resources, new Comparator<Resource>() {
            public int compare(Resource resource1, Resource resource2) {
                String r1 = (String)resource1.getProperty("Reliability");
                String r2 = (String)resource2.getProperty("Reliability");
                Double reliability1, reliability2;                        
                try {
                    reliability1 = (r1 != null) ? Double.valueOf(r1) : 0.0;
                } catch (NumberFormatException e) {
                    reliability1 = 0.0;
                }
                try {
                    reliability2 = (r2 != null) ? Double.valueOf(r2) : 0.0;
                } catch (NumberFormatException e) {
                    reliability2 = 0.0;
                }
                
                return reliability2.compareTo(reliability1);
            }
        });

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Resource resource : resources) {
            Map<String, Object> values = new HashMap<String, Object>();
            values.put("REGISTRANT_HANDLE", PhasesHelper.notNullValue(resource.getProperty(PhasesHelper.HANDLE)));
            values.put("REGISTRANT_RELIABILITY", PhasesHelper.notNullValue(resource.getProperty("Reliability")));
            values.put("REGISTRANT_RATING", PhasesHelper.notNullValue(resource.getProperty("Rating")));
            result.add(values);
        }

        return result;
    }

    /**
     * This method checks if the number of registrations meets the required number.
     * @param phase
     *            the input phase to check.
     * @return true if registrations are enough, false otherwise.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     */
    private boolean areRegistrationsEnough(Phase phase) throws PhaseHandlingException {
        if (phase.getAttribute(Constants.PHASE_CRITERIA_REGISTRATION_NUMBER) == null) {
            return true;
        }

        int regNumber = PhasesHelper.getIntegerAttribute(phase, Constants.PHASE_CRITERIA_REGISTRATION_NUMBER);
        Resource[] resources = searchResources(phase);
        return (regNumber <= resources.length);
    }

    /**
     * Helper method to search for resources with "Submitter" role and project id filters.
     * @param phase
     *            phase instance.
     * @return Resource[] array.
     * @throws PhaseHandlingException
     *             in case of any error while retrieving.
     */
    private Resource[] searchResources(Phase phase) throws PhaseHandlingException {
        ResourceManager resourceManager = getManagerHelper().getResourceManager();
        try {
            long submitterRoleId = LookupHelper.getResourceRole(resourceManager,
                Constants.ROLE_SUBMITTER).getId();
            Filter roleIdFilter = ResourceFilterBuilder.createResourceRoleIdFilter(submitterRoleId);
            Filter projectIdfilter = ResourceFilterBuilder.createProjectIdFilter(phase.getProject().getId());
            Filter fullFilter = new AndFilter(roleIdFilter, projectIdfilter);
            return resourceManager.searchResources(fullFilter);
        } catch (ResourcePersistenceException e) {
            throw new PhaseHandlingException("resource persistence error", e);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("search builder error", e);
        }
    }
}
