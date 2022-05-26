/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


/**
 * <p>
 * Defines utilities for persistence classes.
 * </p>
 *
 * @author PE
 * @version 2.0
 */
final class FileUploadHelper {
    /**
     * Private constructor to prevent this class be instantiated.
     */
    private FileUploadHelper() {
        // empty
    }

    /**
     * <p>
     * Validates the value of a variable with type <code>Object</code>. The value cannot be <code>null</code>.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @return the object to validate.
     *
     * @throws IllegalArgumentException if <code>value</code> is <code>null</code>.
     */
    static Object validateNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " cannot be null.");
        }

        return value;
    }

    /**
     * <p>
     * Validates the value of a string variable. The value cannot be <code>null</code> or empty string.
     * </p>
     *
     * @param value value of the variable.
     * @param name name of the variable.
     *
     * @return the object to validate.
     *
     * @throws IllegalArgumentException if <code>value</code> is <code>null</code> or is empty string.
     */
    static String validateString(String value, String name) {
        validateNotNull(value, name);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException(name + " cannot be empty string.");
        }

        return value;
    }

    /**
     * <p>
     * Close the output stream.
     * </p>
     *
     * @param output the output stream to be closed.
     */
    static void closeOutputStream(OutputStream output) {
        try {
            output.close();
        } catch (IOException e) {
            // ignore
        }
    }
}
