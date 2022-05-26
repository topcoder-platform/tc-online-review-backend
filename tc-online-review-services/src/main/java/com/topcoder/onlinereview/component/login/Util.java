/*
 * Copyright (C) 2006-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.login;

/**
 * Utility class for Online Review Login Component.
 * <p>
 * It defines some common routines used by this component. It includes methods to get property value from
 * <code>ConfigManager</code>, as well as methods to validate parameters.
 * </p>
 *
 * @author woodjohn, maone, TCSASSEMBLER
 * @version 2.0
 */
public final class Util {

    /**
     * Represents the key of forwardUrl value.
     * <p>
     * It can be used to retrieve forward url value from <code>ActionForm</code> and <code>Principal</code>.
     * </p>
     */
    public static final String FOWARD_URL = "forwardUrl";

    /**
     * Represents the key of password value.
     * <p>
     * It can be used to retrieve password value from <code>ActionForm</code> and <code>Principal</code>.
     * </p>
     */
    public static final String PASSWORD = "password";

    /**
     * Represents the key of user name value.
     * <p>
     * It can be used to retrieve user name value from <code>ActionForm</code> and <code>Principal</code>.
     * </p>
     */
    public static final String USERNAME = "userName";

    /**
     * Private constructor.
     */
    private Util() {

        // emtpy
    }

    /**
     * Validate whether the given value is null.
     *
     * @param value
     *            the value to be validated
     * @param name
     *            the name associated with the value (should be non-null)
     * @return the validated value
     * @throws IllegalArgumentException
     *             is the given value is null
     */
    public static Object validateNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " is null.");
        }

        return value;
    }
}
