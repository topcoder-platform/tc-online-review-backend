package com.topcoder.util.commandline.functionaltests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all functional test cases.</p>
 *
 * @author TopCoder Software
 * @version 1.0
 */
public class FunctionalTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(new TestSuite(SingleValueTest.class));
        suite.addTest(new TestSuite(MultipleValueTest.class));
        suite.addTest(new TestSuite(ParameterTest.class));
        suite.addTest(new TestSuite(ProgrammerTest.class));
        suite.addTest(new TestSuite(DelimiterTest.class));
        suite.addTest(new TestSuite(IntegerTest.class));
        suite.addTest(new TestSuite(EnumTest.class));
        suite.addTest(new TestSuite(ErrorHandlingTest.class));
        return suite;
    }

}
