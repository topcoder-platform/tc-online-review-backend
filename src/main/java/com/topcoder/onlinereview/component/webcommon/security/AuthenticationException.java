// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.onlinereview.component.webcommon.security;

public class AuthenticationException extends Exception
{
    public AuthenticationException(final String s) {
        super(s);
    }
    
    public AuthenticationException(final Exception e) {
        super(e.getMessage());
    }
}
