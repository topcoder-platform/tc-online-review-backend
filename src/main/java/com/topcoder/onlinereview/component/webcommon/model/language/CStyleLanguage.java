// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.onlinereview.component.webcommon.model.language;

public abstract class CStyleLanguage extends BaseLanguage
{
    public CStyleLanguage() {
    }
    
    public CStyleLanguage(final int id, final String description) {
        super(id, description);
    }
    
    public String getMethodSignature(final String methodName, final DataType returnType, final DataType[] paramTypes, final String[] paramNames) {
        if (paramTypes.length != paramNames.length) {
            throw new IllegalArgumentException("CStyleLanguage.getMethodSignature: paramTypes.length != paramNames.length (" + paramTypes.length + " + " + paramNames.length + ")");
        }
        final String returns = returnType.getDescriptor(this);
        final String[] params = new String[paramTypes.length];
        int len = returns.length() + methodName.length() + 3;
        for (int i = 0; i < params.length; ++i) {
            final String type = paramTypes[i].getDescriptor(this);
            params[i] = type + ' ' + paramNames[i];
            len += params[i].length();
        }
        len += 2 * (params.length - 1);
        final StringBuffer buf = new StringBuffer(len);
        buf.append(returns);
        buf.append(' ');
        buf.append(methodName);
        buf.append('(');
        for (int j = 0; j < params.length; ++j) {
            if (j > 0) {
                buf.append(", ");
            }
            buf.append(params[j]);
        }
        buf.append(')');
        return buf.toString();
    }
    
    public String exampleExposedCall(final String className, final String methodName, final String[] paramNames) {
        final StringBuffer buf = new StringBuffer();
        buf.append("val = ");
        buf.append(className);
        buf.append(".");
        buf.append(methodName);
        buf.append("(");
        for (int i = 0; i < paramNames.length; ++i) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(paramNames[i]);
        }
        buf.append(");");
        return buf.toString();
    }
}
