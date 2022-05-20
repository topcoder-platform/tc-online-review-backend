/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.termsofuse;

import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.util.CommonUtils.executeUpdateSql;
import static com.topcoder.onlinereview.util.CommonUtils.getInt;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.util.CommonUtils.getString;

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
     * @throws SQLException
     *             is any error occurs.
     */
    static TermsOfUse getTermsOfUse(Map<String, Object> rs, Long termsOfUseId, Integer termsOfUseTypeId) {
        TermsOfUse terms = new TermsOfUse();
        terms.setTermsOfUseId((termsOfUseId != null) ? termsOfUseId : getLong(rs, "terms_of_use_id"));
        terms.setTermsOfUseTypeId((termsOfUseTypeId != null) ? termsOfUseTypeId : getInt(rs, "terms_of_use_type_id"));
        terms.setTitle(getString(rs, "title"));
        terms.setUrl(getString(rs, "url"));

        TermsOfUseAgreeabilityType termsOfUseAgreeabilityType = new TermsOfUseAgreeabilityType();
        termsOfUseAgreeabilityType.setTermsOfUseAgreeabilityTypeId(getInt(rs, "terms_of_use_agreeability_type_id"));
        termsOfUseAgreeabilityType.setName(getString(rs, "terms_of_use_agreeability_type_name"));
        termsOfUseAgreeabilityType.setDescription(getString(rs, "terms_of_use_agreeability_type_description"));
        terms.setAgreeabilityType(termsOfUseAgreeabilityType);

        return terms;
    }

    /**
     * <p>
     * Retrieves terms of use entities from the database.
     * </p>
     *
     * <p>
     * <em>NOTE: </em> Method exit and exception will be logged.
     * </p>
     *
     * <p>
     * <em>Changes in 1.1:</em>
     * <ol>
     * <li>Renamed "userId" parameter to "queryParam".</li>
     * </ol>
     * </p>
     *
     * @param signature
     *            the signature.
     * @param log
     *            the logger.
     * @param jdbcTemplate
     *            the db connection factory.
     * @param sql
     *            the SQL string.
     * @param queryParam
     *            the long query parameter to be passed to the prepared statement (used if not <code>null</code>).
     * @param termsOfUseTypeId
     *            an int containing the terms of use type id to retrieve (used if not <code>null</code>).
     *
     * @return a list of TermsOfUse entities with the requested terms of use or empty list if not found.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    static List<TermsOfUse> getTermsOfUse(String signature, Logger log, JdbcTemplate jdbcTemplate,
        String sql, Long queryParam, Integer termsOfUseTypeId) throws TermsOfUsePersistenceException {
        try {
            List<Object> param = new ArrayList<>();
            if (queryParam != null) {
                param.add(queryParam);
            } else if (termsOfUseTypeId != null) {
                param.add(termsOfUseTypeId);
            }

            List<Map<String, Object>> rs = executeSqlWithParam(jdbcTemplate, sql, param);
            List<TermsOfUse> result = new ArrayList<TermsOfUse>();
            for (Map<String, Object> r : rs) {
                result.add(getTermsOfUse(r, null, termsOfUseTypeId));
            }

            // Log method exit
            Helper.logExit(log, signature, new Object[] {result});
            return result;
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Executes the sql.
     *
     * @param jdbcTemplate
     *            the db connection.
     * @param sql
     *            the sql string.
     * @param values
     *            the values.
     *
     * @return the row count.
     *
     * @throws TermsOfUsePersistenceException
     *             if any error occurs.
     *
     * @since 1.1
     */
    static int executeUpdate(JdbcTemplate jdbcTemplate, String sql, Object[] values) throws TermsOfUsePersistenceException {
        return executeUpdateSql(jdbcTemplate, sql, newArrayList(values));
    }

    /**
     *
     * Executes the sql.
     *
     * @param jdbcTemplate
     *            the db connection factory.
     * @param sql
     *            the sql string.
     * @param values
     *            the values.
     * @param id
     *            the id.
     *
     * @throws EntityNotFoundException
     *             if the entity was not found.
     * @throws TermsOfUsePersistenceException
     *             if any other error occurs.
     */
    static void executeUpdate(JdbcTemplate jdbcTemplate, String sql, Object[] values,
        String id) throws TermsOfUsePersistenceException, EntityNotFoundException {
        int num = executeUpdate(jdbcTemplate, sql, values);
        if (num != 1) {
            throw new EntityNotFoundException("The entity was not found for id (" + id + ").");
        }
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
     * Sets the parameters.
     *
     * @param ps
     *            the prepared statement.
     * @param values
     *            the values.
     *
     * @throws SQLException
     *             is any error occurs.
     */
    private static void setParameters(PreparedStatement ps, Object[] values) throws SQLException {
        int index = 1;
        for (Object value : values) {
            ps.setObject(index++, value);
        }
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
