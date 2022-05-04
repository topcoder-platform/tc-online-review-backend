/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <p>
 * TestHelper class for the test.
 * </p>
 * <p>
 * Thread safe: This class has no state, and thus it is thread safe.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public final class TestHelper {

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Gets the file content as string.
     * </p>
     * 
     * @param filePath
     *            The filePath of SQL file
     * @return The content of file
     * @throws Exception
     *             to JUnit
     */
    static String getFileAsString(String filePath) throws Exception {
        StringBuilder buf = new StringBuilder();
        BufferedReader in =
            new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(filePath)));
        try {
            String s;
            while ((s = in.readLine()) != null) {
                buf.append(s);
            }
            return buf.toString();
        } finally {
            in.close();
        }
    }
}