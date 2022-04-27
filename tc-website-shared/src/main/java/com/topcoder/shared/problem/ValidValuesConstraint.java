// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.ArrayList;

public class ValidValuesConstraint extends Constraint
{
    protected ArrayList validValues;
    protected String paramName;
    protected int dimension;
    
    public ValidValuesConstraint(final ArrayList validValues, final int dimension, final String paramName) {
        this.validValues = validValues;
        this.paramName = paramName;
        this.dimension = dimension;
    }
    
    public ValidValuesConstraint() {
    }
    
    public ArrayList getValidValues() {
        return this.validValues;
    }
    
    public int getDimension() {
        return this.dimension;
    }
    
    public String getParamName() {
        return this.paramName;
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeArrayList(this.validValues);
        writer.writeString(this.paramName);
        writer.writeInt(this.dimension);
    }
    
    public void customReadObject(final CSReader reader) throws IOException, ObjectStreamException {
        this.validValues = reader.readArrayList();
        this.paramName = reader.readString();
        this.dimension = reader.readInt();
    }
    
    public String toXML() {
        final StringBuffer sb = new StringBuffer();
        sb.append("<valid-values>");
        for (int i = 0; i < this.validValues.size(); ++i) {
//            sb.append(this.validValues.get(i).toXML());
        }
        sb.append("</valid-values>");
        return sb.toString();
    }
}
