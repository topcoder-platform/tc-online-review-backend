/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.search;


/**
 * <p>
 * It is thrown if connection to data store fails, or any operations over data store fails.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.3
 */
public class PersistenceOperationException extends SearchBuilderException {
    /**
     * <p>
     * Creates a new instance of this custom exception.
     * This constructor has one argument: message-a descriptive message
     * </p>
     *
     *
     * @param message a descriptive message to describe why this exception is generated
     */
    public PersistenceOperationException(String message) {
        super(message);
    }

    /**
     * <p>Creates a new instance of this custom exception. This constructor has two arguments: message-a
     * descriptive message; cause - the exception(or a chain of exceptions) that generated this exception.</p>
     *
     *
     *
     * @param message a descriptive message to describe why this exception is generated
     * @param cause the exception(or a chain of exceptions) that generated this exception.
     */
    public PersistenceOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
