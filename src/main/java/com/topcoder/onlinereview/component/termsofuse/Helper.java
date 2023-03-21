/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.termsofuse;

import org.slf4j.Logger;

import com.topcoder.onlinereview.grpc.termsofuse.proto.GetTermsOfUseResponse;
import com.topcoder.onlinereview.grpc.termsofuse.proto.ProjectRoleTermsOfUseProto;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * Helper class for the component. It provides useful common methods for all the classes in this component.
 * </p>
 *
 * <p>
 * <em>Changes in 1.1:</em>
 * <ol>
 * <li>Updated getTermsOfUse() to support removed and new properties.</li>
 * <li>Added a method executeUpdate(Connection, String, Object[]).</li>
 * </ol>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author sparemax, saarixx
 * @version 1.1
 */
final class Helper {
    /**
     * <p>
     * Represents the entrance message.
     * </p>
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>the method signature.</li>
     * </ol>
     * </p>
     */
    private static final String MESSAGE_ENTRANCE = "Entering method [%1$s].";

    /**
     * <p>
     * Represents the exit message.
     * </p>
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>the method signature.</li>
     * </ol>
     * </p>
     */
    private static final String MESSAGE_EXIT = "Exiting method [%1$s].";

    /**
     * <p>
     * Represents the error message.
     * </p>
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>the method signature.</li>
     * </ol>
     * </p>
     */
    private static final String MESSAGE_ERROR = "Error in method [%1$s].";

    /**
     * <p>
     * Prevents to create a new instance.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be <code>null</code>.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     *
     * @throws IllegalArgumentException
     *             if the value of the variable is <code>null</code>.
     */
    static void checkNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException("'" + name + "' should not be null.");
        }
    }

    /**
     * Retrieves the terms of use entity.
     *
     * <p>
     * <em>Changes in 1.1:</em>
     * <ol>
     * <li>Support removal of memberAgreeable and electronicallySignable properties.</li>
     * <li>Support adding of agreeabilityType property.</li>
     * </ol>
     * </p>
     *
     * @param rs
     *            the result set.
     * @param termsOfUseId
     *            the terms of use id (used if not <code>null</code>).
     * @param termsOfUseTypeId
     *            the terms of use type id (used if not <code>null</code>).
     *
     * @return the terms of use entity.
     *
     */
    static TermsOfUse getTermsOfUse(ProjectRoleTermsOfUseProto rs) {
        TermsOfUse terms = new TermsOfUse();
        terms.setTermsOfUseId(rs.getTermsOfUseId());
        terms.setTermsOfUseTypeId(rs.getTermsOfUseTypeId());
        if (rs.hasTitle()) {
            terms.setTitle(rs.getTitle());
        }
        if (rs.hasUrl()) {
            terms.setUrl(rs.getUrl());
        }

        TermsOfUseAgreeabilityType termsOfUseAgreeabilityType = new TermsOfUseAgreeabilityType();
        termsOfUseAgreeabilityType.setTermsOfUseAgreeabilityTypeId(rs.getTermsOfUseAgreeabilityTypeId());
        if (rs.hasTermsOfUseAgreeabilityTypeName()) {
            termsOfUseAgreeabilityType.setName(rs.getTermsOfUseAgreeabilityTypeName());
        }
        if (rs.hasTermsOfUseAgreeabilityTypeDescription()) {
            termsOfUseAgreeabilityType.setDescription(rs.getTermsOfUseAgreeabilityTypeDescription());
        }
        terms.setAgreeabilityType(termsOfUseAgreeabilityType);

        return terms;
    }

    static TermsOfUse getTermsOfUse(GetTermsOfUseResponse rs) {
        TermsOfUse terms = new TermsOfUse();
        terms.setTermsOfUseId(rs.getTermsOfUseId());
        terms.setTermsOfUseTypeId(rs.getTermsOfUseTypeId());
        if (rs.hasTitle()) {
            terms.setTitle(rs.getTitle());
        }
        if (rs.hasUrl()) {
            terms.setUrl(rs.getUrl());
        }

        TermsOfUseAgreeabilityType termsOfUseAgreeabilityType = new TermsOfUseAgreeabilityType();
        termsOfUseAgreeabilityType.setTermsOfUseAgreeabilityTypeId(rs.getTermsOfUseAgreeabilityTypeId());
        if (rs.hasTermsOfUseAgreeabilityTypeName()) {
            termsOfUseAgreeabilityType.setName(rs.getTermsOfUseAgreeabilityTypeName());
        }
        if (rs.hasTermsOfUseAgreeabilityTypeDescription()) {
            termsOfUseAgreeabilityType.setDescription(rs.getTermsOfUseAgreeabilityTypeDescription());
        }
        terms.setAgreeabilityType(termsOfUseAgreeabilityType);

        return terms;
    }

    /**
     * <p>
     * Logs for entrance into public method and parameters at <code>DEBUG</code> level.
     * </p>
     *
     * @param log
     *            the logger (not <code>null</code>).
     * @param signature
     *            the signature of the method to log.
     * @param paramNames
     *            the names of parameters to log.
     * @param paramValues
     *            the values of parameters to log.
     */
    static void logEntrance(Logger log, String signature, String[] paramNames, Object[] paramValues) {
        log.debug(String.format(MESSAGE_ENTRANCE, signature));

        if (paramNames != null) {
            // Log parameters
            StringBuilder sb = new StringBuilder("Input parameters[");
            int paramNamesLen = paramNames.length;
            for (int i = 0; i < paramNamesLen; i++) {
                if (i != 0) {
                    // Append a comma
                    sb.append(", ");
                }
                Object paramValue = paramValues[i];
                sb.append(paramNames[i]).append(":").append(toString(paramValue));
            }
            sb.append("]");

            log.debug(sb.toString());
        }
    }

    /**
     * <p>
     * Logs for exit from public method and return value at <code>DEBUG</code> level.
     * </p>
     *
     * @param log
     *            the logger (not <code>null</code>).
     * @param signature
     *            the signature of the method to log.
     * @param value
     *            the return value to log.
     */
    static void logExit(Logger log, String signature, Object[] value) {
        log.debug(String.format(MESSAGE_EXIT, signature));

        if (value != null) {
            // Log return value
            log.debug("Output parameter: " + toString(value[0]));
        }
    }

    /**
     * <p>
     * Logs the given exception and message at <code>ERROR</code> level.
     * </p>
     *
     * @param <T>
     *            the exception type.
     * @param log
     *            the logger (not <code>null</code>).
     * @param signature
     *            the signature of the method to log.
     * @param e
     *            the exception to log.
     *
     * @return the passed in exception.
     */
    static <T extends Throwable> T logException(Logger log, String signature, T e) {
        // Log exception at ERROR level
        log.error(String.format(MESSAGE_ERROR, signature) + getStackTrace(e));
        return e;
    }

    /**
     * Converts the object to a string.
     *
     * <p>
     * <em>Changes in 1.1:</em>
     * <ol>
     * <li>Support the TermsOfUseAgreeabilityType object.</li>
     * </ol>
     * </p>
     *
     * @param obj
     *            the object.
     *
     * @return the string.
     */
    @SuppressWarnings("unchecked")
    private static String toString(Object obj) {
        if (obj instanceof TermsOfUse) {
            return toString((TermsOfUse) obj);
        } else if (obj instanceof TermsOfUseAgreeabilityType) {
            return toString((TermsOfUseAgreeabilityType) obj);
        } else if (obj instanceof TermsOfUseType) {
            return toString((TermsOfUseType) obj);
        } else if (obj instanceof List) {
            return toString((List<?>) obj);
        } else if (obj instanceof Map) {
            return toString((Map<Integer, List<TermsOfUse>>) obj);
        }

        return String.valueOf(obj);
    }

    /**
     * Converts the Map instance to a string.
     *
     * @param obj
     *            the Map instance (not <code>null</code>).
     *
     * @return the string.
     */
    private static String toString(Map<Integer, List<TermsOfUse>> obj) {
        StringBuilder sb = new StringBuilder("{");

        boolean first = true;
        for (Entry<Integer, List<TermsOfUse>> entry : obj.entrySet()) {
            if (!first) {
                // Append a comma
                sb.append(", ");
            }
            first = false;

            sb.append(entry.getKey()).append('=').append(toString(entry.getValue()));
        }

        sb.append("}");

        return sb.toString();
    }


    /**
     * Converts the List to a string.
     *
     * @param obj
     *            the List (not <code>null</code>).
     *
     * @return the string.
     */
    private static String toString(List<?> obj) {
        StringBuilder sb = new StringBuilder("[");

        boolean first = true;
        for (Object element : obj) {
            if (!first) {
                // Append a comma
                sb.append(", ");
            }
            first = false;

            sb.append(toString(element));
        }

        sb.append("]");

        return sb.toString();
    }

    /**
     * Converts the TermsOfUse instance to a string.
     *
     * <p>
     * <em>Changes in 1.1:</em>
     * <ol>
     * <li>Support removal of memberAgreeable and electronicallySignable properties.</li>
     * <li>Support adding of agreeabilityType property.</li>
     * </ol>
     * </p>
     *
     * @param obj
     *            the TermsOfUse instance (not <code>null</code>).
     *
     * @return the string.
     */
    private static String toString(TermsOfUse obj) {
        return toString(TermsOfUse.class.getName(),
            new String[] {"termsOfUseId", "termsOfUseTypeId",
                "title", "url", "agreeabilityType"},
            new String[] {Long.toString(obj.getTermsOfUseId()), Integer.toString(obj.getTermsOfUseTypeId()),
                obj.getTitle(), obj.getUrl(), toString((Object) obj.getAgreeabilityType())});
    }

    /**
     * Converts the TermsOfUseAgreeabilityType instance to a string.
     *
     * @param obj
     *            the TermsOfUseAgreeabilityType instance (not <code>null</code>).
     *
     * @return the string.
     *
     * @since 1.1
     */
    private static String toString(TermsOfUseAgreeabilityType obj) {
        return toString(TermsOfUseAgreeabilityType.class.getName(),
            new String[] {"termsOfUseAgreeabilityTypeId", "name",
                "description"},
            new String[] {Integer.toString(obj.getTermsOfUseAgreeabilityTypeId()), obj.getName(),
                obj.getDescription()});
    }

    /**
     * Converts the TermsOfUseType instance to a string.
     *
     * @param obj
     *            the TermsOfUseType instance (not <code>null</code>).
     *
     * @return the string.
     */
    private static String toString(TermsOfUseType obj) {
        return toString(TermsOfUseType.class.getName(),
            new String[] {"termsOfUseTypeId", "description"},
            new String[] {Integer.toString(obj.getTermsOfUseTypeId()), obj.getDescription()});
    }

    /**
     * Converts the fields to a string.
     *
     * @param className
     *            the class name.
     * @param fields
     *            the fields.
     * @param values
     *            the values of fields.
     *
     * @return the string.
     */
    private static String toString(String className, String[] fields, String[] values) {
        StringBuilder sb = new StringBuilder();

        sb.append(className).append("{");

        int num = fields.length;
        for (int i = 0; i < num; i++) {
            if (i != 0) {
                // Append a comma
                sb.append(", ");
            }

            sb.append(fields[i]).append(":").append(values[i]);
        }

        sb.append("}");
        return sb.toString();
    }

    /**
     * <p>
     * Returns the exception stack trace string.
     * </p>
     *
     * @param cause
     *            the exception to be recorded.
     *
     * @return the exception stack trace string.
     */
    private static String getStackTrace(Throwable cause) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);

        // Print a new line
        ps.println();
        cause.printStackTrace(ps);

        return out.toString();
    }
}
