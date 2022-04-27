// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.language;

public class CPPLanguage extends CStyleLanguage
{
    public static final int ID = 3;
    public static final String DESCRIPTION = "C++";
    public static CPPLanguage CPP_LANGUAGE;
    
    public CPPLanguage() {
        super(3, "C++");
    }
    
    public String getDefaultExtension() {
        return "c";
    }
    
    public String exampleExposedCall(final String className, final String methodName, final String[] paramNames) {
        final StringBuffer buf = new StringBuffer();
        buf.append("val = ");
        buf.append(className);
        buf.append("::");
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
    
    static {
        CPPLanguage.CPP_LANGUAGE = new CPPLanguage();
    }
}
