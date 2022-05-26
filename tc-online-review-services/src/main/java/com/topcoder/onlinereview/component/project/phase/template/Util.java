/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.phase.template;

import com.topcoder.onlinereview.component.project.phase.Dependency;
import com.topcoder.onlinereview.component.project.phase.Phase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Helper class for this component. The user should not use this class directly.
 * </p>
 * <p>
 * Change in 1.2: Adding the new method: 1. checkArrayDuplicateValue(long[]), 2. checkArraySize(int, int),
 *                3. processDependencies(Set<Long>, Map<Long, List<Dependency>>)
 * </p>
 * <p>
 * Thread Safety : This class is immutable and so thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.2
 * @since 1.1
 */
public final class Util {

    /**
     * <p>
     * Represents the tag name "PhaseType".
     * </p>
     */
    public static final String TAG_PHASE_TYPE = "PhaseType";

    /**
     * <p>
     * Represents the tag name "Dependency".
     * </p>
     */
    public static final String TAG_DEPENDENCY = "Dependency";

    /**
     * <p>
     * Represents the tag name "Phase".
     * </p>
     */
    public static final String TAG_PHASE = "Phase";

    /**
     * <p>
     * Represents the attribute name "typeName".
     * </p>
     */
    public static final String ATTRIBUTE_TYPE_NAME = "typeName";

    /**
     * <p>
     * Represents the attribute name "typeId".
     * </p>
     */
    public static final String ATTRIBUTE_TYPE_ID = "typeId";

    /**
     * <p>
     * Represents the attribute name "id".
     * </p>
     */
    public static final String ATTRIBUTE_ID = "id";

    /**
     * <p>
     * Represents the attribute name "type".
     * </p>
     */
    public static final String ATTRIBUTE_TYPE = "type";

    /**
     * <p>
     * Represents the attribute name "length".
     * </p>
     */
    public static final String ATTRIBUTE_LENGTH = "length";

    /**
     * <p>
     * Represents the attribute name "isDependencyStart".
     * </p>
     */
    public static final String ATTRIBUTE_IS_DEPENDENCY_START = "isDependencyStart";

    /**
     * <p>
     * Represents the attribute name "isDependentStart".
     * </p>
     */
    public static final String ATTRIBUTE_IS_DEPENDENT_START = "isDependentStart";

    /**
     * <p>
     * Represents the attribute name "lagTime".
     * </p>
     */
    public static final String ATTRIBUTE_LAG_TIME = "lagTime";

    /**
     * <p>
     * Represents the attribute name "name".
     * </p>
     */
    public static final String ATTRIBUTE_NAME = "name";

    /**
     * <p>
     * Represents the attribute name "category".
     * </p>
     */
    public static final String ATTRIBUTE_CATEGORY = "category";

    /**
     * <p>
     * Represents the attribute name "category".
     * </p>
     */
    public static final String ATTRIBUTE_ISDEFAULT = "isDefault";

    /**
     * <p>
     * Represents the attribute name "creationDate".
     * </p>
     */
    public static final String ATTRIBUTE_CREATIONDATE = "creationDate";

    /**
     * <p>
     * Represents the attribute name "description".
     * </p>
     */
    public static final String ATTRIBUTE_DESCRIPTION = "description";

    /**
     * <p>
     * Represents the attribute name "phaseId".
     * </p>
     */
    public static final String ATTRIBUTE_PHASEID = "phaseId";

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private Util() {

    }

    /**
     * <p>
     * Helper method to check the argument whether is null or not. If it is null,
     * <code>IllegalArgumentException</code> would be thrown.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument
     * @throws IllegalArgumentException
     *             if <code>arg</code> is <code>null</code>
     */
    public static void checkNull(Object arg, String name) {

        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * <p>
     * Helper method to check the string argument whether is null or empty. If it is null or empty,
     * <code>IllegalArgumentException</code> would be thrown.
     * </p>
     *
     * @param arg
     *            the string argument to check
     * @param name
     *            the name of the argument
     * @throws IllegalArgumentException
     *             if <code>arg</code> is <code>null</code> or empty
     */
    public static void checkString(String arg, String name) {

        checkNull(arg, name);
        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

    /**
     * Check array duplicate value.
     *
     * @param leftOutPhaseIds
     *            the left out phase ids
     * @return the set array
     * @throws IllegalArgumentException
     *             if the array contains duplicate id
     * @since 1.2
     */
    public static Set<Long> checkArrayDuplicateValue(long[] leftOutPhaseIds) {

        // check leftOutPhaseIds;
        Set<Long> leftOutPhaseIdsSet = new HashSet<Long>();
        if (leftOutPhaseIds != null) {
            for (long id : leftOutPhaseIds) {
                if (leftOutPhaseIdsSet.contains(id)) {
                    throw new IllegalArgumentException(
                        "The leftOutPhaseIds array contains duplicate phase ID.");
                }
                leftOutPhaseIdsSet.add(id);
            }
        }
        return leftOutPhaseIdsSet;
    }

    /**
     * Check array size.
     *
     * @param oldNum
     *            the old number
     * @param newNum
     *            the new number
     * @throws IllegalArgumentException
     *             if the newNum is not same as oldNum
     * @since 1.2
     */
    public static void checkArraySize(int oldNum, int newNum) {

        if (oldNum != newNum) {
            throw new IllegalArgumentException("at least one of elements in leftOutPhaseIds is not "
                + "a valid phase ID");
        }

    }

    /**
     * Process dependencies.
     *
     * @param leftOutPhaseIdsSet
     *            the left out phase ids set
     * @param dependenciesToLeftOutPhases
     *            the dependencies to left out phases
     * @since 1.2
     */
    public static void processDependencies(Set<Long> leftOutPhaseIdsSet,
        Map<Long, List<Dependency>> dependenciesToLeftOutPhases) {

        // Since 1.2 start
        // process all dependencies for left out phases
        // this algorithm replaces each dependency from not left out phase to left out phase
        // with dependencies from the original not left out phase to not left out phases that
        // are dependencies of the original left out phase
        // Note that since dependency phases of left out phases can be also left out,
        // the breadth-first search approach is used in this algorithm to locate all new dependencies
        // to be used instead of the old dependency
        Map<Long, Set<Long>> processedDependencies = new HashMap<Long, Set<Long>>();
        for (Map.Entry<Long, List<Dependency>> lfphases : dependenciesToLeftOutPhases.entrySet()) {
            for (Dependency dependency : lfphases.getValue()) {
                Phase dependentPhase = dependency.getDependent();
                if (leftOutPhaseIdsSet.contains(dependentPhase.getId())) {
                    continue;
                }
                Set<Long> allProcessedDependencies = processedDependencies.get(dependentPhase.getId());
                if (allProcessedDependencies == null) {
                    allProcessedDependencies = new HashSet<Long>();
                    processedDependencies.put(dependentPhase.getId(), allProcessedDependencies);
                }
                LinkedList<Dependency> dependenciesToBeProcessed = new LinkedList<Dependency>();
                dependenciesToBeProcessed.add(dependency);
                while (!dependenciesToBeProcessed.isEmpty()) {
                    Dependency curDependency = dependenciesToBeProcessed.removeFirst();

                    if (allProcessedDependencies.contains(curDependency.getDependency().getId())) {
                        continue;
                    }

                    allProcessedDependencies.add(curDependency.getDependency().getId());
                    if (leftOutPhaseIdsSet.contains(curDependency.getDependency().getId())) {
                        Dependency[] moreDependencies = curDependency.getDependency().getAllDependencies();
                        for (Dependency dependencyToProcess : moreDependencies) {
                            if (dependencyToProcess.getDependent().getId() == curDependency.getDependency()
                                .getId()) {
                                Dependency combinedDep = new Dependency(dependencyToProcess.getDependency(),
                                    curDependency.getDependent(), dependencyToProcess.isDependencyStart(),
                                    curDependency.isDependentStart(), curDependency.getLagTime()
                                        + dependencyToProcess.getLagTime());
                                dependenciesToBeProcessed.add(combinedDep);
                            }
                        }
                    } else {
                        boolean flag = false;
                        for (Dependency dep : dependentPhase.getAllDependencies()) {
                            if (curDependency.getDependency().getId() == dep.getDependency().getId()) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            continue;
                        }
                        Dependency dependencyCopy = new Dependency(curDependency.getDependency(),
                            curDependency.getDependent(), curDependency.isDependencyStart(),
                            curDependency.isDependentStart(), curDependency.getLagTime());
                        dependencyCopy.setDependent(dependentPhase);
                        dependentPhase.addDependency(dependencyCopy);
                    }
                }
            }
        }
        // Since 1.2 end
    }

}
