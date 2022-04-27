// 
// Decompiled by Procyon v0.5.36
// 

package com.topcoder.shared.problem;

public class ProblemConstants
{
    public static int MAIN_COMPONENT;
    public static int LONG_COMPONENT;
    public static int SECONDARY_COMPONENT;
    public static int TEAM_PROBLEM;
    public static char[] BAD_XML_CHARS;
    public static String TESTER_IO_CLASS;
    public static String WRAPPER_CLASS;
    
    static {
        ProblemConstants.MAIN_COMPONENT = 1;
        ProblemConstants.LONG_COMPONENT = 2;
        ProblemConstants.SECONDARY_COMPONENT = 0;
        ProblemConstants.TEAM_PROBLEM = 2;
        ProblemConstants.BAD_XML_CHARS = new char[] { '<', '>', '&', ':', ';', '\'', '\"' };
        ProblemConstants.TESTER_IO_CLASS = "LongTest";
        ProblemConstants.WRAPPER_CLASS = "Wrapper";
    }
}
