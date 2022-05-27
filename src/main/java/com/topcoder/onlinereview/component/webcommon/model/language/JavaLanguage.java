// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.onlinereview.component.webcommon.model.language;

public class JavaLanguage extends CStyleLanguage
{
    public static final int ID = 1;
    public static String DESCRIPTION;
    public static JavaLanguage JAVA_LANGUAGE;
    
    public JavaLanguage() {
        super(1, JavaLanguage.DESCRIPTION);
    }
    
    public String getDefaultExtension() {
        return "java";
    }
    
    static {
        JavaLanguage.DESCRIPTION = "Java";
        JavaLanguage.JAVA_LANGUAGE = new JavaLanguage();
    }
}
