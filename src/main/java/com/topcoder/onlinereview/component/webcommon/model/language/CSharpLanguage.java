// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.onlinereview.component.webcommon.model.language;

public class CSharpLanguage extends CStyleLanguage
{
    public static final int ID = 4;
    public static final String DESCRIPTION = "C#";
    public static final CSharpLanguage CSHARP_LANGUAGE;
    
    public CSharpLanguage() {
        super(4, "C#");
    }
    
    public String getDefaultExtension() {
        return "cs";
    }
    
    static {
        CSHARP_LANGUAGE = new CSharpLanguage();
    }
}
