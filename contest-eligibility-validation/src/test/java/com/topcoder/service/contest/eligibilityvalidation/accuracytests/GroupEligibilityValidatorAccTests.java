/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation.accuracytests;

import com.topcoder.service.contest.eligibility.GroupContestEligibility;
import com.topcoder.service.contest.eligibilityvalidation.GroupEligibilityValidator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * UnitTest cases of the <code>GroupEligibilityValidator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GroupEligibilityValidatorAccTests extends TestCase {

    /**
     * <p>
     * Represents the <code>GroupEligibilityValidator</code> instance used for test.
     * </p>
     */
    private GroupEligibilityValidator bean;

    /**
     * <p>
     * Creates a test suite for the tests.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(GroupEligibilityValidatorAccTests.class);
        return suite;
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        bean = new GroupEligibilityValidator();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        bean = null;
    }
    /**
     * <p>
     * Accuracy test case for validate.It verifies that validate method return true because the user is
     * eligible.
     * </p>
     * <p>
     * Note that in setup.sql,the user id and group id are 5.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testValidateAccuracy1() throws Exception {
        GroupContestEligibility contestEligibility = new GroupContestEligibility();
        contestEligibility.setGroupId(5);
        assertTrue("The user should be eligible,so return true.", bean.validate(5, contestEligibility));
    }

    /**
     * <p>
     * Accuracy test case for validate.It verifies that validate method return false because the user is not
     * eligible.
     * </p>
     * <p>
     * Note that in setup.sql,the user id and group id are 5.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testValidateAccuracy2() throws Exception {
        GroupContestEligibility contestEligibility = new GroupContestEligibility();
        contestEligibility.setGroupId(1);
        assertFalse("The user should not be eligible,so return false.", bean.validate(5, contestEligibility));
    }
}