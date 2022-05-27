// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.onlinereview.component.webcommon.model.language;

import java.io.Serializable;

public interface Language extends Serializable, Cloneable, CustomSerializable
{
    int getId();
    
    String getName();
    
    boolean equals(final Object p0);
    
    String getMethodSignature(final String p0, final DataType p1, final DataType[] p2, final String[] p3);
    
    String exampleExposedCall(final String p0, final String p1, final String[] p2);
    
    String getDefaultExtension();
}
