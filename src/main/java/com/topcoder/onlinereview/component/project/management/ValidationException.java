/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.management;

import com.topcoder.onlinereview.component.exception.BaseException;

/**
 * <p>
 * Represents an exception related to validating projects. When a project validation failed due to some reason, this
 * exception will be thrown including the validation message.Inner exception should be provided whenever possible to
 * give more details about the error. It is used in classes of the validation package.
 * </p>
 * <p>
 * Thread safety: This class is immutable and thread safe.
 * </p>
 *
 * @author tuenm, iamajia
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ValidationException extends BaseException {

    /**
     * Create a new ValidationException instance with the specified error message.
     *
     * @param message
     *            the error message of the exception
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Create a new ValidationException instance with the specified error message and inner exception.
     *
     * @param message
     *            the error message of the exception
     * @param cause
     *            the inner exception
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
