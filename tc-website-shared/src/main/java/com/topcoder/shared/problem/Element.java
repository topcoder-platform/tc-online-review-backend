// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;
import com.topcoder.shared.netCommon.CustomSerializable;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public interface Element extends Serializable, Cloneable, CustomSerializable
{
    void customWriteObject(final CSWriter p0) throws IOException;
    
    void customReadObject(final CSReader p0) throws IOException, ObjectStreamException;
    
    String toXML();
    
    void setRenderer(final ElementRenderer p0);
    
    ElementRenderer getRenderer();
}
