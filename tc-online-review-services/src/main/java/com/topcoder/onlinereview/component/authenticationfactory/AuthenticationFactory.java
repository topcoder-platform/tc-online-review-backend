/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.authenticationfactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * <p>
 * AuthenticationFactory class maintains a collection of Authenticator instances, which are created
 * from the configuration values via the Configuration Manager. Each Authenticator has a unique
 * name to identify it. AuthenticationFactory provides a method to retrieve the Authenticator by
 * its name. If the configuration values are changed, refresh can be called to recreate the
 * Authenticators, so that we don't have to restart the application to be ware of the change.
 * </p>
 *
 * <p>
 * This class is thread-safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 2.0
 */
@Component
public class AuthenticationFactory {
    /**
     * <p>
     * Represents the authenticators created from the configuration file via Configuration Manager.
     * The key is the name of Authenticator, and the value is the created Authenticator instance.
     * It is initialized in the constructor, and re-populated in the refresh method.
     * </p>
     */
    @Autowired
    private Map<String, Authenticator> authenticators;

    /**
     * <p>
     * Return the corresponding Authenticator instance to the specified authenticator's name. Null
     * is returned if there is no corresponding Authenticator.
     * </p>
     *
     * @param authName the name of the Authenticator to return.
     * @return the corresponding Authenticator to the name, or null if not defined.
     *
     * @throws NullPointerException if the given authName is null.
     * @throws IllegalArgumentException if the given authName is empty string.
     */
    public Authenticator getAuthenticator(String authName) {
        if (authName == null) {
            throw new NullPointerException("authName is null");
        }
        if (authName.trim().length() == 0) {
            throw new IllegalArgumentException("authName is empty string");
        }
        return authenticators.get(authName);
    }
}