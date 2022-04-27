// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;

public abstract class SizeConstraint extends Constraint
{
    protected int size;
    protected int dimension;
    protected String paramName;
    
    public SizeConstraint(final int size, final int dimension, final String paramName) {
        this.size = size;
        this.dimension = dimension;
        this.paramName = paramName;
    }
    
    public SizeConstraint() {
    }
    
    public int getSize() {
        return this.size;
    }
    
    public int getDimension() {
        return this.dimension;
    }
    
    public String getParamName() {
        return this.paramName;
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeInt(this.size);
        writer.writeInt(this.dimension);
        writer.writeString(this.paramName);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.size = reader.readInt();
        this.dimension = reader.readInt();
        this.paramName = reader.readString();
    }
}
