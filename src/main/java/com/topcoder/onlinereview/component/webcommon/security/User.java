// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.onlinereview.component.webcommon.security;

import java.io.Serializable;

public interface User extends Serializable
{
    long getId();
    
    String getUserName();
    
    String getPassword();
    
    boolean isAnonymous();
}
