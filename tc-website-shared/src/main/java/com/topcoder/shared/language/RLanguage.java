// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.language;

public class RLanguage extends CStyleLanguage
{
    public static final int ID = 7;
    public static String DESCRIPTION;
    public static RLanguage R_LANGUAGE;
    
    public RLanguage() {
        super(7, RLanguage.DESCRIPTION);
    }
    
    public String getDefaultExtension() {
        return RLanguage.DESCRIPTION;
    }
    
    static {
        RLanguage.DESCRIPTION = "R";
        RLanguage.R_LANGUAGE = new RLanguage();
    }
}
