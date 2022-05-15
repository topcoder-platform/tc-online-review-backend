/*
 * Copyright (C) 2006-2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.review;

import javax.persistence.EntityManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.util.CommonUtils.executeSql;
import static com.topcoder.onlinereview.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;

/**
 * Helper class for this component.
 *
 * <p>Thread Safety: This class is immutable, and thread safe when entities passed to it are used by
 * the caller in thread safe manner.
 *
 * <p>Changes in 1.2:
 *
 * <ul>
 *   <li>Specified generic parameters for all generic types in the code.
 *   <li>Added deleteEntities() overload for optimized record deletion.
 * </ul>
 *
 * @author urtks, saarixx, TCSDEVELOPER
 * @version 1.2.3
 */
final class Helper {

  /**
   * This constant provides the <code>DataType</code> instance that can be used in the query methods
   * to specify that a <code>ResultSet</code> column of a query result should be returned as value
   * of type <code>String</code> or as <code>null</code> in case the <code>ResultSet</code> value
   * was <tt>null</tt>.
   */
  static final DataType STRING_TYPE = new StringType();

  /**
   * This constant provides the <code>DataType</code> instance that can be used in the query methods
   * to specify that a <code>ResultSet</code> column of a query result should be returned as value
   * of type <code>Long</code> or as <code>null</code> in case the <code>ResultSet</code> value was
   * <tt>null</tt>.
   */
  static final DataType LONG_TYPE = new LongType();

  /**
   * This constant provides the <code>DataType</code> instance that can be used in the query methods
   * to specify that a <code>ResultSet</code> column of a query result should be returned as value
   * of type <code>Date</code> or as <code>null</code> in case the <code>ResultSet</code> value was
   * <tt>null</tt>.
   */
  static final DataType DATE_TYPE = new DateType();

  /**
   * This constant provides the <code>DataType</code> instance that can be used in the query methods
   * to specify that a <code>ResultSet</code> column of a query result should be returned as value
   * of type <code>Double</code> or as <code>null</code> in case the <code>ResultSet</code> value
   * was <tt>null</tt>.
   */
  static final DataType DOUBLE_TYPE = new DoubleType();

  /**
   * This class is a wrapper for type safe retrieval of values from a <code>ResultSet</code>. This
   * class has been introduced to consist the behaviors of different databases and JDBC drivers so
   * that always the expected type is returned (<code>getObject(int)</code> does not sufficiently do
   * this job as the type of the value is highly database-dependent (e.g. for a BLOB column the
   * MySQL driver returns a <code>byte[]</code> and the Oracle driver returns a <code>Blob</code>)).
   *
   * <p>This class contains a private constructor to make sure all implementations of this class are
   * declared inside <code>Helper</code>. Instances are provided to users via constants declared in
   * <code>Helper</code> - so this class defines some kind of 'pseudo-enum' which cannot be
   * instantiated externally.
   *
   * @author urtks
   * @version 1.0
   */
  abstract static class DataType {

    /**
     * Empty private constructor. By using this concept, it is assured that only {@link Helper} can
     * contain subclasses of this class and the implementation classes cannot be instantiated
     * externally.
     */
    private DataType() {}

    /**
     * This method retrieves the value at the given index from the given resultSet as instance of
     * the subclass-dependent type.
     *
     * @param resultSet the result set from which to retrieve the value
     * @param index the index at which to retrieve the value
     * @return the retrieved value
     * @throws IllegalArgumentException if resultSet is <tt>null</tt>
     * @throws SQLException if error occurs while working with the given ResultSet or the index does
     *     not exist in the result set
     */
    protected abstract Object getValue(ResultSet resultSet, int index) throws SQLException;
  }

  /**
   * This class is a wrapper for type safe retrieval of values from a ResultSet. The values
   * retrieved by the getValue(java.sql.ResultSet, int) implementation of this DataType are assured
   * to be of type String or to be null in case the ResultSet value was null.
   *
   * <p>Thread Safety: This class is immutable, and thread safe when result set passed to it is used
   * by the caller in thread safe manner.
   *
   * <p>Changes in 1.2:
   *
   * <ul>
   *   <li>Added empty private constructor.
   * </ul>
   *
   * @author urtks, saarixx, TCSDEVELOPER
   * @version 1.2
   */
  private static final class StringType extends DataType {

    /**
     * Empty private constructor. It can be called by the parent class (Helper) only.
     *
     * @since 1.2
     */
    private StringType() {}

    /**
     * This method retrieves the value at the given index from the given resultSet as instance of
     * the subclass-dependent type.
     *
     * @param resultSet the result set from which to retrieve the value
     * @param index the index at which to retrieve the value
     * @return the retrieved value as <code>String</code> or <code>null</code> if the value in the
     *     <code>ResultSet</code> was <code>null</code>.
     * @throws IllegalArgumentException if resultSet is <code>null</code>
     * @throws SQLException if error occurs while working with the given ResultSet or the index does
     *     not exist in the result set
     */
    protected Object getValue(ResultSet resultSet, int index) throws SQLException {
      Helper.assertObjectNotNull(resultSet, "resultSet");
      return resultSet.getString(index);
    }
  }

  /**
   * This class is a wrapper for type safe retrieval of values from a ResultSet. The values
   * retrieved by the getValue(java.sql.ResultSet, int) implementation of this DataType are assured
   * to be of type Long or to be null in case the ResultSet value was null.
   *
   * <p>Thread Safety: This class is immutable, and thread safe when result set passed to it is used
   * by the caller in thread safe manner.
   *
   * <p>Changes in 1.2:
   *
   * <ul>
   *   <li>Added empty private constructor.
   * </ul>
   *
   * @author urtks, saarixx, TCSDEVELOPER
   * @version 1.2
   */
  private static final class LongType extends DataType {

    /**
     * Empty private constructor. It can be called by the parent class (Helper) only.
     *
     * @since 1.2
     */
    private LongType() {}

    /**
     * This method retrieves the value at the given index from the given resultSet as instance of
     * the subclass-dependent type.
     *
     * @param resultSet the result set from which to retrieve the value
     * @param index the index at which to retrieve the value
     * @return the retrieved value as <code>Long</code> or <code>null</code> if the value in the
     *     <code>ResultSet</code> was <code>null</code>.
     * @throws IllegalArgumentException if resultSet is <code>null</code>
     * @throws SQLException if error occurs while working with the given ResultSet or the index does
     *     not exist in the result set
     */
    protected Object getValue(ResultSet resultSet, int index) throws SQLException {
      Helper.assertObjectNotNull(resultSet, "resultSet");
      long aLong = resultSet.getLong(index);
      return resultSet.wasNull() ? null : aLong;
    }
  }

  /**
   * This class is a wrapper for type safe retrieval of values from a ResultSet. The values
   * retrieved by the getValue(java.sql.ResultSet, int) implementation of this DataType are assured
   * to be of type Date or to be null in case the ResultSet value was null.
   *
   * <p>Thread Safety: This class is immutable, and thread safe when result set passed to it is used
   * by the caller in thread safe manner.
   *
   * <p>Changes in 1.2:
   *
   * <ul>
   *   <li>Added empty private constructor.
   * </ul>
   *
   * @author urtks, saarixx, TCSDEVELOPER
   * @version 1.2
   */
  private static final class DateType extends DataType {

    /**
     * Empty private constructor. It can be called by the parent class (Helper) only.
     *
     * @since 1.2
     */
    private DateType() {}

    /**
     * This method retrieves the value at the given index from the given resultSet as instance of
     * the subclass-dependent type.
     *
     * @param resultSet the result set from which to retrieve the value
     * @param index the index at which to retrieve the value
     * @return the retrieved value as <code>Date</code> or <code>null</code> if the value in the
     *     <code>ResultSet</code> was <code>null</code>.
     * @throws IllegalArgumentException if resultSet is <code>null</code>
     * @throws SQLException if error occurs while working with the given ResultSet or the index does
     *     not exist in the result set
     */
    protected Object getValue(ResultSet resultSet, int index) throws SQLException {
      Helper.assertObjectNotNull(resultSet, "resultSet");
      return resultSet.getTimestamp(index);
    }
  }

  /**
   * This class is a wrapper for type safe retrieval of values from a ResultSet. The values
   * retrieved by the getValue(java.sql.ResultSet, int) implementation of this DataType are assured
   * to be of type Double or to be null in case the ResultSet value was null.
   *
   * <p>Thread Safety: This class is immutable, and thread safe when result set passed to it is used
   * by the caller in thread safe manner.
   *
   * <p>Changes in 1.2:
   *
   * <ul>
   *   <li>Added empty private constructor.
   * </ul>
   *
   * @author urtks, saarixx, TCSDEVELOPER
   * @version 1.2
   */
  private static final class DoubleType extends DataType {

    /**
     * Empty private constructor. It can be called by the parent class (Helper) only.
     *
     * @since 1.2
     */
    private DoubleType() {}

    /**
     * This method retrieves the value at the given index from the given resultSet as instance of
     * the subclass-dependent type.
     *
     * @param resultSet the result set from which to retrieve the value
     * @param index the index at which to retrieve the value
     * @return the retrieved value as <code>Double</code> or <code>null</code> if the value in the
     *     <code>ResultSet</code> was <code>null</code>.
     * @throws IllegalArgumentException if resultSet is <code>null</code>
     * @throws SQLException if error occurs while working with the given ResultSet or the index does
     *     not exist in the result set
     */
    protected Object getValue(ResultSet resultSet, int index) throws SQLException {
      Helper.assertObjectNotNull(resultSet, "resultSet");
      double aDouble = resultSet.getDouble(index);
      return resultSet.wasNull() ? null : aDouble;
    }
  }

  /** Private constructor to prevent this class be instantiated. */
  private Helper() {}

  /**
   * This method performs the given retrieval (i.e. non-DML) query on the given connection using the
   * given query arguments. The <code>ResultSet</code> returned from the query is expected to
   * contain ONE row containing ONE value, which is then returned. This approach assured that all
   * resources (<code>PreparedStatement</code> and the <code>ResultSet</code>) allocated in this
   * method are also de-allocated in this method. <b>Note:</b> The given connection is not closed or
   * committed in this method.
   *
   * @param entityManager the connection to perform the query on
   * @param queryString the query to be performed
   * @param queryArgs the arguments to be used in the query LONG_TYPE or DOUBLE_TYPE here
   * @return the value returned by the query as value of the type represented by the given
   *     columnType or <code>null</code> if the <code>ResultSet</code> value was <code>null</code>
   * @throws IllegalArgumentException if any parameter is <code>null</code>, or queryString is empty
   *     (trimmed), or the given query did return multiple (or zero) rows or columns
   * @throws ReviewPersistenceException if the query fails
   */
  static <T> T doSingleValueQuery(
      EntityManager entityManager,
      String queryString,
      Object[] queryArgs,
      Function<Map<String, Object>, T> get)
      throws ReviewPersistenceException {
    Helper.assertStringNotNullNorEmpty(queryString, "queryString");
    try {
      // do the query
      var resultSet = executeSqlWithParam(entityManager, queryString, newArrayList(queryArgs));
      if (!resultSet.isEmpty()) {
        return get.apply(resultSet.get(0));
      } else {
        throw new IllegalArgumentException(
            "The given query ["
                + queryString
                + "] did not return ONE result row containing "
                + "ONE value using the query arguments ["
                + Arrays.asList(queryArgs)
                + "].");
      }
    } catch (Exception e) {
      throw new ReviewPersistenceException(
          "Error occurs while executing query ["
              + queryString
              + "] using the query arguments "
              + Arrays.asList(queryArgs).toString()
              + ".",
          e);
    }
  }

  /**
   * This method performs the given DML (query on the given connection using the given query
   * arguments. The update count returned from the query is then returned. <b>Note:</b> The given
   * connection is not closed or committed in this method.
   *
   * @param entityManager the entityManager to perform the query on
   * @param queryString the query to be performed
   * @param queryArgs the arguments to be used in the query
   * @return the number of database rows affected by the query
   * @throws IllegalArgumentException if any parameter is null or queryString is empty (trimmed)
   * @throws ReviewPersistenceException if the query fails
   */
  static int doDMLQuery(EntityManager entityManager, String queryString, Object[] queryArgs)
      throws ReviewPersistenceException {
    Helper.assertStringNotNullNorEmpty(queryString, "queryString");
    Helper.assertObjectNotNull(queryArgs, "queryArgs");
    try {
      executeSqlWithParam(entityManager, queryString, newArrayList(queryArgs));
      return 1;
    } catch (Exception e) {
      throw new ReviewPersistenceException(
          "Error occurs while executing query ["
              + queryString
              + "] using the query arguments "
              + Arrays.asList(queryArgs).toString()
              + ".",
          e);
    }
  }

  /**
   * Close the prepared statement.
   *
   * @param ps the prepared statement to close
   * @throws ReviewPersistenceException error occurs when closing the prepared statement
   */
  static void closeStatement(PreparedStatement ps) throws ReviewPersistenceException {
    if (ps != null) {
      try {
        ps.close();
      } catch (SQLException e) {
        throw new ReviewPersistenceException(
            "Error occurs when closing the prepared statement.", e);
      }
    }
  }

  /**
   * Close the result set.
   *
   * @param rs the result set to close
   * @throws ReviewPersistenceException error occurs when closing the result set.
   */
  static void closeResultSet(ResultSet rs) throws ReviewPersistenceException {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        throw new ReviewPersistenceException("Error occurs when closing the result set.", e);
      }
    }
  }

  /**
   * Delete entities whose given column equals to the given id in the specified table.
   *
   * <p>Changes in 1.2:
   *
   * <ul>
   *   <li>Returns the number of deleted entities (previously return type was "void").
   * </ul>
   *
   * @throws ReviewPersistenceException if error happens during deletion
   * @param id the id to check
   * @param entityManager the db entityManager
   * @param tableName the table name
   * @param columnName the column name
   * @return the number of deleted entities
   */
  static int deleteEntities(
      String tableName, String columnName, long id, EntityManager entityManager)
      throws ReviewPersistenceException {
    return Helper.doDMLQuery(
        entityManager,
        "DELETE FROM " + tableName + " WHERE " + columnName + "=?",
        new Object[] {id});
  }

  /**
   * Delete entities whose given column equals to one of the given IDs in the specified table.
   *
   * @throws ReviewPersistenceException if error happens during deletion
   * @param entityManager the db entityManager
   * @param ids the IDs to check
   * @param tableName the table name
   * @param columnName the column name
   * @return the number of deleted entities
   * @since 1.2
   */
  static int deleteEntities(
      String tableName, String columnName, Collection<Long> ids, EntityManager entityManager)
      throws ReviewPersistenceException {
    if (ids.isEmpty()) {
      return 0;
    }
    StringBuilder sb = new StringBuilder("DELETE FROM ");
    sb.append(tableName).append(" WHERE ").append(columnName).append(" IN (");
    boolean first = true;
    for (Long id : ids) {
      if (first) {
        first = false;
      } else {
        sb.append(",");
      }
      sb.append("?");
    }
    sb.append(")");
    String query = sb.toString();
    return Helper.doDMLQuery(entityManager, query, ids.toArray());
  }

  /**
   * Count entities whose given column equals to the given id in the specified table.
   *
   * @param tableName the table name
   * @param columnName the column name
   * @param id the id to check
   * @param entityManager the db entityManager
   * @return # of entities
   * @throws ReviewPersistenceException if error happens during querying
   */
  static long countEntities(
      String tableName, String columnName, long id, EntityManager entityManager)
      throws ReviewPersistenceException {
    return getLong(
        executeSql(
                entityManager,
                String.format(
                    "SELECT COUNT(*) as count FROM %s WHERE %s=%d", tableName, columnName, id))
            .get(0),
        "count");
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
    for (int i = 0; i < array.length; ++i) {
      if (array[i] == null) {
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
}
