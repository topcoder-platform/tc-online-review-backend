/*
 * Copyright (C) 2007-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.management;

/**
 * <p>
 * Helper class for this component.
 * </p>
 * <p>
 * <b>Version 1.1 change:</b> Add the method <code>createConnection(DBConnectionFactory, String)</code> to create the
 * <code>Connection</code> by the given connection factory and name.
 * </p>
 * <p>
 * Version 1.1 (End Of Project Analysis Release Assembly v1.0)
 * <ul>
 * <li>Added new data type for <code>Boolean</code> column types.</li>
 * </ul>
 * </p>
 *
 * @author urtks, fuyun, TCSDEVELOPER
 * @version 1.2.4
 * @since 1.0
 */
class Helper {

    /**
     * This constant provides the <code>DataType</code> instance that can be used in the query methods to specify that a
     * <code>ResultSet</code> column of a query result should be returned as value of type <code>String</code> or as
     * <code>null</code> in case the <code>ResultSet</code> value was <tt>null</tt>.
     */
    static final DataType STRING_TYPE = new StringType();

    /**
     * This constant provides the <code>DataType</code> instance that can be used in the query methods to specify that a
     * <code>ResultSet</code> column of a query result should be returned as value of type <code>Long</code> or as
     * <code>null</code> in case the <code>ResultSet</code> value was <tt>null</tt>.
     */
    static final DataType LONG_TYPE = new LongType();

    /**
     * This constant provides the <code>DataType</code> instance that can be used in the query methods to specify that a
     * <code>ResultSet</code> column of a query result should be returned as value of type <code>Double</code> or as
     * <code>null</code> in case the <code>ResultSet</code> value was <tt>null</tt>.
     *
     * @since 1.2
     */
    static final DataType Double_TYPE = new DoubleType();

    /**
     * This constant provides the <code>DataType</code> instance that can be used in the query methods to specify that a
     * <code>ResultSet</code> column of a query result should be returned as value of type <code>Date</code> or as
     * <code>null</code> in case the <code>ResultSet</code> value was <tt>null</tt>.
     */
    static final DataType DATE_TYPE = new DateType();

    /**
     * <p>
     * This constant provides the <code>DataType</code> instance that can be used in the query methods to specify that a
     * <code>ResultSet</code> column of a query result should be returned as value of type <code>Boolean</code> or as
     * <code>null</code> in case the <code>ResultSet</code> value was <tt>null</tt>.
     *
     * @since 1.2
     */
    static final DataType BOOLEAN_TYPE = new BooleanType();

    /**
     * This class is a wrapper for type safe retrieval of values from a <code>ResultSet</code>. This class has been
     * introduced to consist the behaviors of different databases and JDBC drivers so that always the expected type is
     * returned (<code>getObject(int)</code> does not sufficiently do this job as the type of the value is highly
     * database-dependent (e.g. for a BLOB column the MySQL driver returns a <code>byte[]</code> and the Oracle driver
     * returns a <code>Blob</code>)).
     * <p/>
     * This class contains a private constructor to make sure all implementations of this class are declared inside
     * <code>Helper</code>. Instances are provided to users via constants declared in <code>Helper</code> - so this
     * class defines some kind of 'pseudo-enum' which cannot be instantiated externally.
     *
     * @author urtks
     * @version 1.0
     */
    abstract static class DataType {
        /**
         * Empty private constructor. By using this concept, it is assured that only {@link Helper} can contain
         * subclasses of this class and the implementation classes cannot be instantiated externally.
         */
        private DataType() {
        }
    }

    /**
     * This class is a wrapper for type safe retrieval of values from a <code>ResultSet</code>. The values retrieved
     * by the <code>getValue(java.sql.ResultSet, int)</code> implementation of this <code>DataType</code> are assured
     * to be of type <code>String</code> or to be <tt>null</tt> in case the <code>ResultSet</code> value was
     * <tt>null</tt>.
     *
     * @author urtks
     * @version 1.0
     */
    private static class StringType extends DataType {
    }

    /**
     * This class is a wrapper for type safe retrieval of values from a <code>ResultSet</code>. The values retrieved by
     * the <code>getValue(java.sql.ResultSet, int)</code> implementation of this <code>DataType</code> are assured to be
     * of type <code>Double</code> or to be <tt>null</tt> in case the <code>ResultSet</code> value was <tt>null</tt>.
     *
     * @author TCSDEVELOPER
     * @version 1.2
     * @since 1.2
     */
    private static class DoubleType extends DataType {
    }

    /**
     * This class is a wrapper for type safe retrieval of values from a <code>ResultSet</code>. The values retrieved
     * by the <code>getValue(java.sql.ResultSet, int)</code> implementation of this <code>DataType</code> are assured
     * to be of type <code>Long</code> or to be <tt>null</tt> in case the <code>ResultSet</code> value was
     * <tt>null</tt>.
     *
     * @author urtks
     * @version 1.0
     */
    private static class LongType extends DataType {
    }

    /**
     * This class is a wrapper for type safe retrieval of values from a <code>ResultSet</code>. The values retrieved
     * by the <code>getValue(java.sql.ResultSet, int)</code> implementation of this <code>DataType</code> are assured
     * to be of type <code>Date</code> or to be <tt>null</tt> in case the <code>ResultSet</code> value was
     * <tt>null</tt>.
     *
     * @author urtks
     * @version 1.0
     */
    private static class DateType extends DataType {
    }

    /**
     * <p>
     * This class is a wrapper for type safe retrieval of values from a <code>ResultSet</code>. The values retrieved
     * by the <code>getValue(java.sql.ResultSet, int)</code> implementation of this <code>DataType</code> are assured
     * to be of type <code>Long</code> or to be <tt>null</tt> in case the <code>ResultSet</code> value was
     * <tt>null</tt>.
     *
     * @author TCSDEVEOPER
     * @version 1.0
     * @since 1.2
     */
    private static class BooleanType extends DataType {
    }

    /**
     * Private constructor to prevent this class be instantiated.
     */
    private Helper() {
    }

    /**
     * Check if the given object is null.
     *
     * @param obj the given object to check
     * @param name the name to identify the object.
     * @throws IllegalArgumentException if the given object is null
     */
    static void assertObjectNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * Check if the given array is null or contains null.
     *
     * @param array the given array to check
     * @param name the name to identify the array.
     * @throws IllegalArgumentException if the given array is null or contains null.
     */
    static void assertArrayNotNullNorHasNull(Object[] array, String name) {
        assertObjectNotNull(array, name);

        for (Object item : array) {
            if (item == null) {
                throw new IllegalArgumentException(name + " should not contain null.");
            }
        }
    }

    /**
     * Check if the given string is null or empty (trimmed).
     *
     * @param str the given string to check
     * @param name the name to identify the string.
     * @throws IllegalArgumentException if the given string is null or empty (trimmed).
     */
    static void assertStringNotNullNorEmpty(String str, String name) {
        assertObjectNotNull(str, name);

        if (str.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty (trimmed).");
        }
    }

    /**
     * Check if the given long value is positive.
     *
     * @param val the given long value to check.
     * @param name the name to identify the long value.
     * @throws IllegalArgumentException if the given long value is negative or zero.
     */
    static void assertLongPositive(long val, String name) {
        if (val <= 0) {
            throw new IllegalArgumentException(name + " [" + val + "] should be positive.");
        }
    }

    /**
     * Check if the number is positive.
     *
     * @param number
     *            the number to check.
     * @param name
     *            the number name
     * @throws IllegalArgumentException
     *             if the number is not positive.
     */
    public static void checkNumberPositive(long number, String name) {
        if (number <= 0) {
            throw new IllegalArgumentException("the number " + name + " should be positive.");
        }
    }

    /**
     * Check if the object is null.
     *
     * @param obj
     *            the object to check.
     * @param name
     *            the object name
     * @throws IllegalArgumentException
     *             if the object is null.
     */
    public static void checkObjectNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException("the object " + name + " should not be null.");
        }
    }

    /**
     * Check if the string is null or empty.
     *
     * @param str
     *            the string to check.
     * @param name
     *            the parameter name.
     * @throws IllegalArgumentException
     *             if the string is null or empty.
     */
    public static void checkStringNotNullOrEmpty(String str, String name) {
        checkObjectNotNull(str, name);
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("the string " + name + " should not be empty.");
        }
    }
}
