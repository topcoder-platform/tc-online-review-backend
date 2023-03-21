/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.deliverable;

/**
 * Helper class for the whole component.
 * <p>
 * <em>Changes in 1.1:</em>
 * <ul>
 * <li>Removed useless methods checkAndGetCustomResultSetValid and checkObjectNotNull.</li>
 * <li>Add the searchable field 'submission_type_id' of submission search bundle.</li>
 * </ul>
 * </p>
 * <p>
 * <em>Changes in 1.2:</em>
 * <ul>
 * <li>Added generic type support.</li>
 * <li>Simplified checkDeliverableCheckers method since generic type is used</li>
 * <li>logging was added for each parameter checking method.</li>
 * <li>scope of checkObjectNotNullFullDesp method is reduced to package private.</li>
 * <li>checkAndGetCustomResultSetValidDeliverable and checkDeliverableCheckers methods are refactored to
 * PersistenceDeliverableManager class since they are only used there.</li>
 * </ul>
 * </p>
 *
 * @author singlewood, sparemax
 * @author TCSDEVELOPER
 * @version 1.2
 */
public final class DeliverableHelper {

    /**
     * Private empty constructor.
     */
    private DeliverableHelper() {
    }

    /**
     * Check if the object is null.
     *
     * @param obj
     *            the object to check.
     * @param name
     *            the object name
     * @return true if object is not null.
     * @throws IllegalArgumentException
     *             if the object is null.
     */
    public static boolean checkObjectNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("the object " + name + " should not be null.");
        }
        return true;
    }

    /**
     * Check if the object is null.
     *
     * @param obj
     *            the object to check.
     * @param message
     *            the error message
     * @throws IllegalArgumentException
     *             if the object is null.
     */
    static void checkObjectNotNullFullDesp(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Check if the number is greater than zero.
     *
     * @param number
     *            the number to check.
     * @param name
     *            the number name
     * @throws IllegalArgumentException
     *             if the number <= 0.
     */
    public static void checkGreaterThanZero(long number, String name) {
        if (number <= 0) {
            throw new IllegalArgumentException(name + " should be greater than zero.");
        }
    }
}
