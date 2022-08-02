/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.authenticationfactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * AbstractAuthenticator abstract class implements Authenticator interface, and provides some
 * common functionalities that can be shared by specific authenticator implementations.
 * </p>
 *
 * <ol>
 * <li>
 * It defines default mappings, which is loaded from the configuration file via Configuration
 * Manager component, so that if certain mapping is missing in the authenticated Principal, the
 * default one can be used instead.
 * </li>
 * <li>
 * It provides a conversion from the specific authenticator's key to principal's key, so that if we
 * switch to another authenticator, we only have to update the conversion configuration.
 * </li>
 * <li>
 * It holds a cache to cache the response of the authenticated principal, so that we can return the
 * cached response to the same principal without do the time-consuming authentication repeatedly.
 * And the cache mechanism can be turned off / on in the configuration file.
 * </li>
 * </ol>
 *
 * <p>
 * Specific authenticator implementations are expected to override the protected doAuthenticate
 * method, and  must provide a constructor taking a namespace argument in order to be created in
 * AuthenticationFactory via Configuration Manager component. defaultMappings variable in this
 * abstract class is designed to be protected for convenience, the subclasses should NEVER modify
 * it outside the constructor to assure thread-safety.  This abstract class is thread-safe due to
 * its immutability, and subclasses should keep the contract.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 2.0
 */
public abstract class AbstractAuthenticator implements Authenticator {

    /**
     * <p>
     * Represents the default mappings for the authenticator, it is initialized in the constructor
     * to empty map. Subclasses are repsonsible to populate it with configuration values defined
     * in the configuration file.  It should NEVER be modified outside the constructor.
     * </p>
     */
    protected Map<String, Object> defaultMappings = new HashMap();

    /**
     * <p>
     * Represents the PrincipalKeyConverter instance to convert from the authenticator's key to
     * principal's key.
     * </p>
     */
    @Autowired
    @Qualifier("defaultKeyConverter")
    private PrincipalKeyConverter converter;

    /**
     * <p>
     * Authenticate the specified Principal, and return the authentication response.
     * </p>
     *
     * @param principal the principal to authenticate.
     *
     * @return the authenticated response of the principal.
     * @throws NullPointerException if the given principal is null.
     * @throws MissingPrincipalKeyException if certain key is missing in the given principal.
     * @throws InvalidPrincipalException if the principal is invalid, e.g. the type of a certain key's
     *         value is invalid.
     * @throws AuthenticateException if error occurs during the authentication.
     */
    public Response authenticate(Principal principal) throws AuthenticateException {
        if (principal == null) {
            throw new NullPointerException("pricipal is null.");
        }
        return doAuthenticate(principal);
    }

    /**
     * <p>
     * Authenticate the given principal really.
     * </p>
     *
     * @param principal the principal to authenticate.
     * @return the authenticated response of the principal.
     *
     * @throws MissingPrincipalKeyException if certain key is missing in the given principal.
     * @throws InvalidPrincipalException if the principal is invalid, e.g. the type of a
     *         certain key's value is invalid.
     * @throws AuthenticateException if error occurs during the authentication
     */
    protected abstract Response doAuthenticate(Principal principal) throws AuthenticateException;

    /**
     * <p>
     * Return the PrincipalKeyConverter instance which is used by the subclasses to convert
     * authenticator's key to principal's key.
     * </p>
     *
     * @return the PrincipalKeyConverter instance.
     */
    protected PrincipalKeyConverter getConverter() {
        return converter;
    }
}
