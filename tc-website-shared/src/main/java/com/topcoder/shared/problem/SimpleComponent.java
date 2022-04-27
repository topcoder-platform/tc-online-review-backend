// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

import com.topcoder.shared.netCommon.CSReader;
import com.topcoder.shared.netCommon.CSWriter;
import com.topcoder.shared.netCommon.CustomSerializable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public class SimpleComponent implements Serializable, CustomSerializable
{
    int problemID;
    int componentID;
    int componentTypeID;
    int roundType;
    String className;
    String methodName;
    DataType[] paramTypes;
    DataType returnType;
    Long[] webServiceDependencies;
    
    public SimpleComponent() {
        this.webServiceDependencies = null;
    }
    
    public void customWriteObject(final CSWriter writer) throws IOException {
        writer.writeString(this.className);
        writer.writeString(this.methodName);
        writer.writeObject(this.returnType);
        writer.writeObjectArray(this.paramTypes);
        writer.writeInt(this.componentID);
        writer.writeInt(this.componentTypeID);
        writer.writeInt(this.problemID);
        writer.writeInt(this.roundType);
        writer.writeObjectArray(this.webServiceDependencies);
    }
    
    public void customReadObject(final CSReader reader) throws IOException {
        this.className = reader.readString();
        this.methodName = reader.readString();
        this.returnType = (DataType)reader.readObject();
        Object[] o_paramTypes = reader.readObjectArray();
        this.componentID = reader.readInt();
        this.componentTypeID = reader.readInt();
        this.problemID = reader.readInt();
        this.roundType = reader.readInt();
        if (o_paramTypes == null) {
            o_paramTypes = new Object[0];
        }
        this.paramTypes = new DataType[o_paramTypes.length];
        for (int i = 0; i < o_paramTypes.length; ++i) {
            this.paramTypes[i] = (DataType)o_paramTypes[i];
        }
        this.webServiceDependencies = (Long[])reader.readObjectArray(Long.class);
    }
    
    public int getProblemID() {
        return this.problemID;
    }
    
    public void setProblemID(final int problemID) {
        this.problemID = problemID;
    }
    
    public int getComponentID() {
        return this.componentID;
    }
    
    public void setComponentID(final int componentID) {
        this.componentID = componentID;
    }
    
    public int getComponentTypeID() {
        return this.componentTypeID;
    }
    
    public void setComponentTypeID(final int componentTypeID) {
        this.componentTypeID = componentTypeID;
    }
    
    public String getClassName() {
        return this.className;
    }
    
    public void setClassName(final String className) {
        this.className = className;
    }
    
    public String getMethodName() {
        return this.methodName;
    }
    
    public void setMethodName(final String methodName) {
        this.methodName = methodName;
    }
    
    public DataType[] getParamTypes() {
        return this.paramTypes;
    }
    
    public void setParamTypes(final DataType[] paramTypes) {
        this.paramTypes = paramTypes;
    }
    
    public DataType getReturnType() {
        return this.returnType;
    }
    
    public void setReturnType(final DataType returnType) {
        this.returnType = returnType;
    }
    
    public static String getCacheKey(final int componentID) {
        return "SimpleProblemComponent." + componentID;
    }
    
    public String getCacheKey() {
        return getCacheKey(this.componentID);
    }
    
    public String getReturnType(final int language) {
        return this.returnType.getDescriptor(language);
    }
    
    public boolean hasWebServiceDependencies() {
        return this.webServiceDependencies != null && this.webServiceDependencies.length > 0;
    }
    
    public Long[] getWebServiceDependencies() {
        return this.webServiceDependencies;
    }
    
    public void setWebServiceDependencies(final Long[] webServiceIDs) {
        this.webServiceDependencies = webServiceIDs;
    }
    
    public int getRoundType() {
        return this.roundType;
    }
    
    public void setRoundType(final int roundType) {
        this.roundType = roundType;
    }
    
    public String toString() {
        final StringBuffer ret = new StringBuffer(1000);
        ret.append("(com.topcoder.shared.problem.SimpleComponent) [");
        ret.append("problemID = ");
        ret.append(this.problemID);
        ret.append(", ");
        ret.append("componentID = ");
        ret.append(this.componentID);
        ret.append(", ");
        ret.append("componentTypeID = ");
        ret.append(this.componentTypeID);
        ret.append(", ");
        ret.append("roundType = ");
        ret.append(this.roundType);
        ret.append(", ");
        ret.append("className = ");
        if (this.className == null) {
            ret.append("null");
        }
        else {
            ret.append(this.className.toString());
        }
        ret.append(", ");
        ret.append("methodName = ");
        if (this.methodName == null) {
            ret.append("null");
        }
        else {
            ret.append(this.methodName.toString());
        }
        ret.append(", ");
        ret.append("paramTypes = ");
        if (this.paramTypes == null) {
            ret.append("null");
        }
        else {
            ret.append("{");
            for (int i = 0; i < this.paramTypes.length; ++i) {
                ret.append(this.paramTypes[i].toString() + ",");
            }
            ret.append("}");
        }
        ret.append(", ");
        ret.append("returnType = ");
        if (this.returnType == null) {
            ret.append("null");
        }
        else {
            ret.append(this.returnType.toString());
        }
        ret.append(", ");
        ret.append("webServiceDependencies = ");
        if (this.webServiceDependencies == null) {
            ret.append("null");
        }
        else {
            ret.append(Arrays.asList(this.webServiceDependencies));
        }
        ret.append(", ");
        ret.append("]");
        return ret.toString();
    }
}
