// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.onlinereview.component.webcommon.model.language;

public class VBLanguage extends BaseLanguage
{
    public static final int ID = 5;
    public static final String DESCRIPTION = "VB";
    public static final VBLanguage VB_LANGUAGE;
    
    public VBLanguage() {
        super(5, "VB");
    }
    
    public String getMethodSignature(final String methodName, final DataType returnType, final DataType[] paramTypes, final String[] paramNames) {
        if (paramTypes.length != paramNames.length) {
            throw new IllegalArgumentException("VBLanguage.getMethodSignature: paramTypes.length != paramNames.length (" + paramTypes.length + " + " + paramNames.length + ")");
        }
        final String returns = returnType.getDescriptor(this);
        final String[] params = new String[paramTypes.length];
        int len = returns.length() + methodName.length() + 3;
        for (int i = 0; i < params.length; ++i) {
            final String type = paramTypes[i].getDescriptor(this);
            params[i] = paramNames[i] + " As " + type;
            len += params[i].length();
        }
        len += 2 * (params.length - 1);
        final StringBuffer buf = new StringBuffer(len);
        buf.append("Public Function ");
        buf.append(methodName);
        buf.append('(');
        for (int j = 0; j < params.length; ++j) {
            if (j > 0) {
                buf.append(", ");
            }
            buf.append(params[j]);
        }
        buf.append(')');
        buf.append(" As ");
        buf.append(returns);
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
        buf.append(")");
        return buf.toString();
    }
    
    public String getDefaultExtension() {
        return "vb";
    }
    
    static {
        VB_LANGUAGE = new VBLanguage();
    }
}
