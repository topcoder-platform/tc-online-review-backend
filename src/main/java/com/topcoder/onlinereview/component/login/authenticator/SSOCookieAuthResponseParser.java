/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.onlinereview.component.login.authenticator;

import com.topcoder.onlinereview.component.authenticationfactory.Principal;
import com.topcoder.onlinereview.component.authenticationfactory.Response;
import com.topcoder.onlinereview.component.login.AuthResponseParser;
import com.topcoder.onlinereview.component.login.AuthResponseParsingException;
import com.topcoder.onlinereview.component.login.Util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * <p>
 * When using SSO cookie we need no authentication related object in session. The entire session is cleared before login
 * and after logout.
 * </p>
 * <p>
 * This class is thread safe since it does not contain any mutable state.
 * </p>
 *
 * @author ecnu_haozi
 * @version 2.0
 */
public class SSOCookieAuthResponseParser implements AuthResponseParser {
    /**
     * Set the login information(retrieved from given <code>authResponse</code>) on the request.
     *
     * @param principal
     *            the principal (it will be ignored by current implementation)
     * @param authResponse
     *            the authentication response
     * @param request
     *            the http request instance
     * @param response
     *            the http response instance
     * @throws IllegalArgumentException
     *             if any argument is null(except <code>principal</code>)
     * @throws AuthResponseParsingException
     *             if any other error occurred (it is kept for future extension)
     */
    public void setLoginState(Principal principal, Response authResponse, HttpServletRequest request,
                              HttpServletResponse response)
            throws AuthResponseParsingException {
        Util.validateNotNull(authResponse, "authResponse");
        Util.validateNotNull(request, "request");
        Util.validateNotNull(response, "response");

        // unsuccessful login, return directly
        if (!authResponse.isSuccessful()) {
            return;
        }

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * Clear the user identifier from the session and log user out.
     *
     * @param request
     *            the http request instance
     * @param response
     *            the http response instance
     * @throws IllegalArgumentException
     *             if either of argument is null
     * @throws AuthResponseParsingException
     *             if any other error occurred (it is kept for future extension)
     */
    public void unsetLoginState(HttpServletRequest request, HttpServletResponse response)
            throws AuthResponseParsingException {
        Util.validateNotNull(request, "request");
        Util.validateNotNull(response, "response");

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }
}
