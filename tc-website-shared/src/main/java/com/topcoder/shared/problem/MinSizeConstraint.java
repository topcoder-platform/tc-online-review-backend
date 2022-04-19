// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;

public class MinSizeConstraint extends SizeConstraint
{
    public MinSizeConstraint(final int size, final int dimension, final String paramName) {
        super(size, dimension, paramName);
    }
    
    public MinSizeConstraint() {
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        super.customWriteObject(writer);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        super.customReadObject(reader);
    }
    
    public String toXML() {
        return "";
    }
}
