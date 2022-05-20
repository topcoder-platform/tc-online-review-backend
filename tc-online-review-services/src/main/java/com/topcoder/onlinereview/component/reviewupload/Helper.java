/*
 * Copyright (C) 2007-2012 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.onlinereview.component.reviewupload;

import org.slf4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

/**
 * <p>
 * This class provides static utility methods which are used to facilitate the coding or reduce the redundancies.
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread safe since it is immutable.
 * </p>
 *
 * @author cyberjag
 * @version 1.1.2
 */
final class Helper {

    /**
     * <p>
     * Private constructor to prevent instantiation of this class.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Checks the given <code>obj</code> for <code>null</code>.
     * </p>
     *
     * @param obj       the object to check.
     * @param paramName the name of the argument.
     * @param log       the logger
     *
     * @throws IllegalArgumentException if obj is <code>null</code>.
     */
    static void checkNull(Object obj, String paramName, Logger log) {
        if (obj == null) {
            log.error("Parameter argument: {0} cannot be null.", new Object[]{paramName});
            throw new IllegalArgumentException("Parameter argument: '" + paramName + "' cannot be null!");
        }
    }

    /**
     * <p>
     * Checks the given <code>String</code> for <code>null</code> and empty.
     * </p>
     * <p>
     * Note that the empty string is checked after trimming.
     * </p>
     *
     * @param str       string to check.
     * @param paramName the name of the argument.
     * @param log       the logger.
     *
     * @throws IllegalArgumentException if str is <code>null</code> or empty string.
     */
    static void checkString(String str, String paramName, Logger log) {
        // check null
        checkNull(str, paramName, log);

        // check empty
        if (str.trim().length() == 0) {
            log.error("Parameter argument: {0} cannot be empty string.", new Object[]{paramName});
            throw new IllegalArgumentException("Parameter argument: '" + paramName + "' cannot be empty string!");
        }
    }

    /**
     * <p>
     * Checks the given <code>id</code> is less than 0.
     * </p>
     *
     * @param id        the id to check.
     * @param paramName the name of the argument.
     * @param log       the logger for logging purpose.
     *
     * @throws IllegalArgumentException if the id is &lt; 0
     */
    static void checkId(long id, String paramName, Logger log) {
        if (id < 0) {
            log.error("Parameter argument: {0} cannot be negative.", new Object[]{paramName});
            throw new IllegalArgumentException("Parameter argument: '" + paramName + "' cannot be negative!");
        }
    }

    /**
     * <p>Logs message with specified logging level.</p>
     *
     * @param log logger to use
     * @param message message to log
     */
    public static void logFormat(Logger log, String message) {
        log.debug(message);
    }

    /**
     * <p>Formats and logs message with specified logging level.</p>
     *
     * @param log logger to use
     * @param message message pattern
     * @param params message arguments
     */
    public static void logFormat(Logger log, String message, Object... params) {
        log.debug(MessageFormat.format(message, params));
    }

    /**
     * <p>Formats and logs message with specified logging level.</p>
     *
     * @param log logger to use
     * @param message message pattern
     * @param params message arguments
     */
    public static void logFormat(Logger log, Throwable error, String message, Object... params) {
        if (error != null) {
            // 'convert' the exception stack trace into string
            // note: closing the stringWriter has no effect
            StringWriter buffer = new StringWriter();
            buffer.append("\n");
            error.printStackTrace(new PrintWriter(buffer));
            log.error(MessageFormat.format(message, params) + buffer.toString());
        } else {
            log.error(MessageFormat.format(message, params));
        }

    }
}
