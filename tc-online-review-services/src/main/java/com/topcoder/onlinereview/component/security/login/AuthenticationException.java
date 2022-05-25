package com.topcoder.onlinereview.component.security.login;

import com.topcoder.onlinereview.component.security.GeneralSecurityException;

/**
 * @author Heather Van Aelst
 * @version 0.1
 */
public class AuthenticationException extends GeneralSecurityException {

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }

}
