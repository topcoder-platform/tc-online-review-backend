// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.onlinereview.component.webcommon.security;

public interface Authentication
{
    void login(final User p0) throws LoginException;
    
    void logout();
    
    User getUser();
}
