/*
 * Copyright (C) 2006-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.login.authenticator;

import com.topcoder.onlinereview.component.authenticationfactory.Principal;
import com.topcoder.onlinereview.component.authenticationfactory.Response;
import com.topcoder.onlinereview.component.login.AuthResponseParser;
import com.topcoder.onlinereview.component.login.AuthResponseParsingException;
import com.topcoder.onlinereview.component.login.Util;
import com.topcoder.onlinereview.component.security.TCSubject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * This class is used to manage the log in state in request for the <code>SecurityManagerAuthenticator</code>, so it's
 * expected that this parser should stay with <code>SecurityManagerAuthenticator</code> within the configuration file
 * for <code>LoginActions</code>.
 * <p>
 * In the <code>setLoginState</code> method, if authentication succeeded, it will store in the user identifier in the
 * session attributes. In the <code>unsetLoginState</code> method, it will remove the user identifier from the session
 * and invalidate the session.
 * </p>
 * <p>
 * This class is thread safe since it does not contain any mutable state.
 * </p>
 *
 * @author woodjohn, maone, TCSASSEMBLER
 * @version 2.0
 */
public class SecurityManagerAuthResponseParser implements AuthResponseParser {

    /**
     * The key name of the user identifier. The value of this key is the user id retrieved from TCSubject. The key-value
     * pair will be stored in the request session in the setLogin method if authentication succeeded.
     * <p>
     * This variable is set in the constructor, non-null and non-empty.
     * </p>
     */
    private String userIdentifierKey;

    public void setUserIdentifierKey(String userIdentifierKey) {
        this.userIdentifierKey = userIdentifierKey;
    }

    /**
     * Set the login information(retrieved from given <code>authResponse</code>) on the request.
     * <p>
     * If the given <code>authResponse</code> is successful, a new session will be retrieved from the
     * <code>request</code>, and the user id will be stored as an attribute. Otherwise, nothing would happen.
     * </p>
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
     *             if <code>authResponse#getDetails()</code> is not of type <code>com.topcoder.security.TCSubject</code>
     *             , or any other error occurred
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

        // retrieve TCSubject from given authResponse
        Object subjectObj = authResponse.getDetails();

        if (!TCSubject.class.isInstance(subjectObj)) {
            throw new AuthResponseParsingException("The details of response is not instanceof " + TCSubject.class);
        }

        // invalidate old session
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        // store user id in the session
        session = request.getSession();

        long userId = ((TCSubject) subjectObj).getUserId();

        session.setAttribute(this.userIdentifierKey, new Long(userId));
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
