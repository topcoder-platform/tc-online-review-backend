/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.contest;

/**
 * <p>
 * ContestEligibilityPersistenceException is thrown by ContestEligibilityManager.It is used as the customer
 * exception in this component.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ContestEligibilityPersistenceException extends Exception {

    /**
     * Create the exception, call parent constructor with the same signature.
     */
    public ContestEligibilityPersistenceException() {
        super();
    }

    /**
     * Create the exception, call parent constructor with the same signature.
     *
     * @param message
     *            the error message to set
     */
    public ContestEligibilityPersistenceException(String message) {
        super(message);
    }

    /**
     * Create the exception, call parent constructor with the same signature.
     *
     * @param cause
     *            the inner cause exception to set
     */
    public ContestEligibilityPersistenceException(Throwable cause) {
        super(cause);
    }

    /**
     * Create the exception, call parent constructor with the same signature.
     *
     * @param message
     *            the error message to set
     * @param cause
     *            the cause error to set
     */
    public ContestEligibilityPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
