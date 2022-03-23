/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.calculator.impl;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.payment.calculator.ProjectPaymentCalculatorConfigurationException;


/**
 * <p>
 * Unit test case of {@link Helper}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperTest {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(HelperTest.class);
    }

    /**
     * <p>
     * Accuracy test method for <code>Helper#getStringProperty(ConfigurationObject, String, boolean)</code> when
     * property exists and has a String value.
     * </p>
     * <p>
     * Expects that the returned value is correct.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testGetStringProperty() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("myConfig");
        config.setPropertyValue("key", "value");

        String value = Helper.getStringProperty(config, "key", true);
        Assert.assertEquals("Incorrect property value", "value", value);
    }

    /**
     * <p>
     * Accuracy test method for <code>Helper#getStringProperty(ConfigurationObject, String, boolean)</code> when
     * optional property has an empty value.
     * </p>
     * <p>
     * Expects that returned value is null.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testGetStringProperty_EmptyOptional() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("myConfig");
        config.setPropertyValue("key", "  ");

        String value = Helper.getStringProperty(config, "key", false);
        Assert.assertNull("Shall be null", value);
    }

    /**
     * <p>
     * Failure test method for <code>Helper#getStringProperty(ConfigurationObject, String, boolean)</code> when
     * property exists but it does not have a String value.
     * </p>
     * <p>
     * Expects that <code>ProjectPaymentCalculatorConfigurationException</code> is thrown.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test(expected = ProjectPaymentCalculatorConfigurationException.class)
    public void testGetStringProperty_InvalidType() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("myConfig");
        config.setPropertyValue("key", 2);

        Helper.getStringProperty(config, "key", true);
    }

    /**
     * <p>
     * Failure test method for <code>Helper#getStringProperty(ConfigurationObject, String, boolean)</code> when
     * required property does not exist.
     * </p>
     * <p>
     * Expects that <code>ProjectPaymentCalculatorConfigurationException</code> is thrown.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test(expected = ProjectPaymentCalculatorConfigurationException.class)
    public void testGetStringProperty_MissingRequired() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("myConfig");

        Helper.getStringProperty(config, "key", true);
    }

    /**
     * <p>
     * Failure test method for <code>Helper#getStringProperty(ConfigurationObject, String, boolean)</code> when
     * required property exists but it has an empty String value.
     * </p>
     * <p>
     * Expects that <code>ProjectPaymentCalculatorConfigurationException</code> is thrown.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test(expected = ProjectPaymentCalculatorConfigurationException.class)
    public void testGetStringProperty_EmptyRequired() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("myConfig");
        config.setPropertyValue("key", "  ");

        Helper.getStringProperty(config, "key", true);
    }

    /**
     * <p>
     * Accuracy test method for
     * {@link Helper#getChildConfiguration(com.topcoder.configuration.ConfigurationObject, java.lang.String)}.
     * </p>
     * <p>
     * Expects the returned value is correct.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test
    public void testGetChildConfiguration() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("myConfig");
        ConfigurationObject child = new DefaultConfigurationObject("child");
        config.addChild(child);

        ConfigurationObject result = Helper.getChildConfiguration(config, "child");
        Assert.assertSame("Incorrect child", child, result);
    }

    /**
     * <p>
     * Failure test method for
     * {@link Helper#getChildConfiguration(com.topcoder.configuration.ConfigurationObject, java.lang.String)} when
     * child does not exist.
     * </p>
     * <p>
     * Expects <code>ProjectPaymentCalculatorConfigurationException</code> is thrown.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    @Test(expected = ProjectPaymentCalculatorConfigurationException.class)
    public void testGetChildConfiguration_Missing() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("myConfig");
        ConfigurationObject child = new DefaultConfigurationObject("child");
        config.addChild(child);

        Helper.getChildConfiguration(config, "child1");
    }
}
