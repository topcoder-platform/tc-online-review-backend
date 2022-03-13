/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.distribution;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit tests for <code>DistributionToolException</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DistributionToolExceptionTest extends TestCase {
    /**
     * <p>
     * The error message used for testing.
     * </p>
     */
    private static final String MESSAGE = "the error message";

    /**
     * <p>
     * The ExceptionData used for testing.
     * </p>
     */
    private static final ExceptionData DATA = new ExceptionData();

    /**
     * <p>
     * The inner exception for testing.
     * </p>
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * <p>
     * Returns the test suite of this class.
     * </p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(DistributionToolExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DistributionToolException(String)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStr() {
        DistributionToolException exception = new DistributionToolException(MESSAGE);
        assertNotNull("Unable to create DistributionToolException instance.", exception);
        assertTrue("Should be instance of DistributionToolException.", exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DistributionToolException(String)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully with null message.
     * </p>
     */
    public void testCtorStr_Null() {
        DistributionToolException exception = new DistributionToolException((String) null);
        assertNotNull("Unable to create DistributionToolException instance.", exception);
        assertTrue("Should be instance of DistributionToolException.", exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DistributionToolException(String, ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrExp() {
        DistributionToolException exception = new DistributionToolException(MESSAGE, DATA);
        assertNotNull("Unable to create DistributionToolException instance.", exception);
        assertTrue("Should be instance of DistributionToolException.", exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DistributionToolException(String, ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully with null message and exception data.
     * </p>
     */
    public void testCtorStrExp_Null() {
        DistributionToolException exception = new DistributionToolException(null, (ExceptionData) null);
        assertNotNull("Unable to create DistributionToolException instance.", exception);
        assertTrue("Should be instance of DistributionToolException.", exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DistributionToolException(String, Throwable)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowable() {
        DistributionToolException exception = new DistributionToolException(MESSAGE, CAUSE);
        assertNotNull("Unable to create DistributionToolException instance.", exception);
        assertTrue("Should be instance of DistributionToolException.", exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DistributionToolException(String, Throwable)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully with null message and cause.
     * </p>
     */
    public void testCtorStrThrowable_Null() {
        DistributionToolException exception = new DistributionToolException(null, (Throwable) null);
        assertNotNull("Unable to create DistributionToolException instance.", exception);
        assertTrue("Should be instance of DistributionToolException.", exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DistributionToolException(String, Throwable,
     * ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully.
     * </p>
     */
    public void testCtorStrThrowableExp() {
        DistributionToolException exception = new DistributionToolException(MESSAGE, CAUSE, DATA);
        assertNotNull("Unable to create DistributionToolException instance.", exception);
        assertTrue("Should be instance of DistributionToolException.", exception instanceof BaseCriticalException);
        assertEquals("The error message should match.", MESSAGE, exception.getMessage());
        assertEquals("The inner cause should match.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DistributionToolException(String, Throwable,
     * ExceptionData)</code>.
     * </p>
     * <p>
     * Verify that the exception can be created successfully with null arguments.
     * </p>
     */
    public void testCtorStrThrowableExp_Null() {
        DistributionToolException exception = new DistributionToolException(null, null, null);
        assertNotNull("Unable to create DistributionToolException instance.", exception);
        assertTrue("Should be instance of DistributionToolException.", exception instanceof BaseCriticalException);
        assertNull("The error message should match.", exception.getMessage());
        assertNull("The inner cause should match.", exception.getCause());
    }
}
