/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.calculator.impl;

import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.PropertyNotFoundException;
import com.topcoder.configuration.PropertyTypeMismatchException;
import com.topcoder.management.payment.calculator.ProjectPaymentCalculatorConfigurationException;


/**
 * <p>
 * This class provide various helper methods for other classes in this component.
 * </p>
 * <p>
 * <b>Thread-Safety:</b> This class is immutable as it is final and can not be instantiated thus thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class Helper {
    /**
     * <p>
     * Private constructor to prevent instantiation.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Gets the property value for the given key as String. If property value is empty (trimmed), then exception is
     * thrown.
     * </p>
     *
     * @param config
     *            the configuration to read the property value from
     * @param key
     *            the key of property
     * @param required
     *            whether the property is required, or not
     * @return the property value of given key
     * @throws ProjectPaymentCalculatorConfigurationException
     *             if property value is empty, or if required property is missing, or if property value is not a
     *             String, or if any error occurs while accessing configuration
     */
    static String getStringProperty(ConfigurationObject config, String key, boolean required) {
        try {
            // Get property value from the configuration
            String value = config.getPropertyValue(key, String.class, required);

            if (value != null && value.trim().length() == 0) {
                if (required) {
                    throw new ProjectPaymentCalculatorConfigurationException("The configured '" + key
                        + "' property value should not be empty");
                }
                value = null;
            }

            return value;
        } catch (PropertyTypeMismatchException e) {
            throw new ProjectPaymentCalculatorConfigurationException("The configured '" + key
                + "' property value is not a String", e);
        } catch (PropertyNotFoundException e) {
            throw new ProjectPaymentCalculatorConfigurationException("The '" + key
                + "' property does not exist in configuration", e);
        } catch (ConfigurationAccessException e) {
            throw new ProjectPaymentCalculatorConfigurationException(
                "An error occurs while accessing configuration", e);
        }
    }

    /**
     * <p>
     * Gets a child ConfigurationObject with given name. If the child does not exist, then exception is thrown.
     * </p>
     *
     * @param config
     *            the configuration to get its child from
     * @param name
     *            the child name
     * @return a child configuration
     */
    static ConfigurationObject getChildConfiguration(ConfigurationObject config, String name) {
        try {
            // Get child configuration
            ConfigurationObject child = config.getChild(name);

            // check if it is missing
            if (child == null) {
                throw new ProjectPaymentCalculatorConfigurationException("The '" + name
                    + "' configuration child does not exist in configuration");
            }

            return child;
        } catch (ConfigurationAccessException e) {
            throw new ProjectPaymentCalculatorConfigurationException(
                "An error occurs while accessing configuration", e);
        }
    }
}
