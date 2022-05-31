/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.authenticationfactory;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * DefaultKeyConverter class implements the PrincipalKeyConverter interface, it is able to load the
 * key mappings from the configuration file via the Configuration Manager component.
 * </p>
 *
 * <p>
 * This class is thread-safe since it is immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 2.0
 */
@Component("defaultKeyConverter")
public class DefaultKeyConverter implements PrincipalKeyConverter {
    /**
     * <p>
     * Represents the key mappings, the key of the map is authenticator's key, and the value of the
     * map is principal's key.
     * </p>
     */
    private Map<String, String> mappings = new HashMap();

    /**
     * <p>
     * Return the converted principal's key from the given authenticator's key.
     * If no corresponding value found, will return key value.
     * </p>
     *
     * @param key the authenticator's key.
     * @return the converted principal's key.
     *
     * @throws NullPointerException
     *             if the key is null.
     * @throws IllegalArgumentException
     *             if the key is emtpy string.
     */
    public String convert(String key) {
        if (key == null) {
            throw new NullPointerException("key is null.");
        }
        if (key.trim().length() == 0) {
            throw new IllegalArgumentException("key is empty string.");
        }
        String value = mappings.get(key);

        // if given key exist in mappings, return the corresponding value,
        // otherwise, return the key unchanged
        return value != null ? value : key;
    }
}