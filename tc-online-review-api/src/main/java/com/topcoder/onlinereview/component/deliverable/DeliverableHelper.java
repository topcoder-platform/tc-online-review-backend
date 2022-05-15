/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.deliverable;

import com.topcoder.onlinereview.component.datavalidator.IntegerValidator;
import com.topcoder.onlinereview.component.datavalidator.ObjectValidator;
import com.topcoder.onlinereview.component.datavalidator.StringValidator;
import com.topcoder.onlinereview.component.search.SearchBundle;

import java.util.HashMap;
import java.util.Map;

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
     * Identifier of deliverable search bundle.
     */
    public static final int DELIVERABLE_SEARCH_BUNDLE = 1;

    /**
     * Identifier of deliverable with submission search bundle.
     */
    public static final int DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE = 2;

    /**
     * Identifier of upload search bundle.
     */
    public static final int UPLOAD_SEARCH_BUNDLE = 3;

    /**
     * Identifier of submission search bundle.
     */
    public static final int SUBMISSION_SEARCH_BUNDLE = 4;

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

    /**
     * Set the searchable fields of given SearchBundle.
     * <p>
     * <em>Changes in 1.1:</em>
     * <ul>
     * <li>Add the searchable field 'submission_type_id' of submission search bundle.</li>
     * </ul>
     * </p>
     * <p>
     * Changes in version 1.2:
     * <ul>
     * <li>Changes to generic type support.</li>
     * </ul>
     * </p>
     *
     * @param searchBundle
     *            the SearchBundle to set
     * @param key
     *            the identifier of SearchBundle
     */
    static void setSearchableFields(SearchBundle searchBundle, int key) {
        Map<String, ObjectValidator> fields = new HashMap<>();

        // Set up an IntegerValidator for latter use.
        IntegerValidator greaterThanZeroValidator = IntegerValidator.greaterThan(0);

        // Set the fields with different validator.
        switch (key) {
        case DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE:
            fields.put("submission_id", greaterThanZeroValidator);
            // Falls through.

        case DELIVERABLE_SEARCH_BUNDLE:
            fields.put("deliverable_id", greaterThanZeroValidator);
            fields.put("phase_id", greaterThanZeroValidator);
            fields.put("name", StringValidator.hasLength(greaterThanZeroValidator));
            fields.put("required", IntegerValidator.inRange(Integer.MIN_VALUE, Integer.MAX_VALUE));
            break;

        case UPLOAD_SEARCH_BUNDLE:
            fields.put("upload_id", greaterThanZeroValidator);
            fields.put("upload_type_id", greaterThanZeroValidator);
            fields.put("upload_status_id", greaterThanZeroValidator);
            fields.put("project_phase_id", greaterThanZeroValidator);
            break;

        case SUBMISSION_SEARCH_BUNDLE:
            fields.put("upload_id", greaterThanZeroValidator);
            fields.put("submission_id", greaterThanZeroValidator);
            fields.put("submission_status_id", greaterThanZeroValidator);
            fields.put("submission_type_id", greaterThanZeroValidator);
            fields.put("project_phase_id", greaterThanZeroValidator);
            break;

        default:
            break;
        }

        // Set common searchable fields for those search bundle.
        fields.put("project_id", greaterThanZeroValidator);
        fields.put("resource_id", greaterThanZeroValidator);

        searchBundle.setSearchableFields(fields);
    }
}
