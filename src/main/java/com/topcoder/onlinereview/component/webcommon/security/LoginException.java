// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.onlinereview.component.webcommon.security;

public class LoginException extends AuthenticationException
{
    public LoginException(final String s) {
        super(s);
    }
    
    public LoginException(final Exception e) {
        super(e.getMessage());
    }
}
