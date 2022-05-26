/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.reviewfeedback;

import com.topcoder.onlinereview.component.exception.ExceptionData;

/**
 * <p>
 * This exception indicates error caused by fact that specified entity is not found in persistence.
 * </p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class inherits from not thread-safe class, so it's not thread-safe.
 * </p>
 *
 * @author gevak, amazingpig
 * @version 1.0
 */
public class ReviewFeedbackManagementEntityNotFoundException extends ReviewFeedbackManagementException {
    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = 1388475950632024899L;

    /**
     * Creates instance with specified error message. Simply delegate to base class constructor with same signature.
     *
     * @param message Error message.
     */
    public ReviewFeedbackManagementEntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates instance with specified error message and inner cause. Simply delegate to base class constructor with
     * same signature.
     *
     * @param message Error message.
     * @param cause Inner cause.
     */
    public ReviewFeedbackManagementEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates instance with specified error message and exception data. Simply delegate to base class constructor
     * with same signature.
     *
     * @param message Error message.
     * @param data Exception data.
     */
    public ReviewFeedbackManagementEntityNotFoundException(String message, ExceptionData data) {
        super(message, data);
    }

    /**
     * Creates instance with specified error message, inner cause and exception data. Simply delegate to base class
     * constructor with same signature.
     *
     * @param message Error message.
     * @param cause Inner cause.
     * @param data Exception data.
     */
    public ReviewFeedbackManagementEntityNotFoundException(String message, Throwable cause, ExceptionData data) {
        super(message, cause, data);
    }
}
