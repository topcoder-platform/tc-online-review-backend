/** Copyright (C) 2005 TopCoder Inc., All Rights Reserved. */
package com.topcoder.onlinereview.component.id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeUpdateSql;

/** The DB helper class centralizes db operations for generating ids. */
@Component
public class DBHelper {
  /** The default select sql sentence used for retrieving data from table. */
  private static final String DEFAULT_SELECT_NEXT_BLOCK =
      "SELECT next_block_start, block_size, exhausted FROM " + "id_sequences WHERE name = ?";

  /** The default sql sentence to update the next_block_start of the table. */
  private static final String DEFAULT_UPDATE_NEXT_BLOCK_START =
      "UPDATE id_sequences SET next_block_start = ? " + "WHERE name = ?";

  /** The default sql sentence to set the exausted to 1. */
  private static final String DEFAULT_UPDATE_EXHAUSTED =
      "UPDATE id_sequences SET exhausted = 1 WHERE name = ?";

  /**
   * The key to the select sql sentence or the corresponding statement used for retrieving data from
   * table.
   */
  public static final String SELECT_NEXT_BLOCK_KEY = "select_next_block";

  /**
   * The key to the sql sentence or the corresponding statement to update the next_block_start of
   * the table.
   */
  public static final String UPDATE_NEXT_BLOCK_START_KEY = "update_next_block_start";

  /** The key to sql sentence or the corresponding statement to set the exausted to 1. */
  public static final String UPDATE_EXHAUSTED_KEY = "update_exhausted";

  /** the sql sentences support for generating ids */
  private Map<String, String> sqlSentences = new HashMap();

  @Autowired
  @Qualifier("oltpJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @PostConstruct
  public void postRun() {
    sqlSentences.put(SELECT_NEXT_BLOCK_KEY, DEFAULT_SELECT_NEXT_BLOCK);
    sqlSentences.put(UPDATE_NEXT_BLOCK_START_KEY, DEFAULT_UPDATE_NEXT_BLOCK_START);
    sqlSentences.put(UPDATE_EXHAUSTED_KEY, DEFAULT_UPDATE_EXHAUSTED);
  }

  /**
   * Executes a sql statement. If the connection is to be reused, it first tries to execute the
   * prepared statement according to the key. If failed the first time, it will recreate an database
   * connection and prepare the statements against the newly created database connection and then
   * try again. If the connection is not to be reused, it just executes the corresponding sql
   * clause.
   *
   * @param key the key to the sql statement
   * @param parameters the parameters of the sql statement
   * @return the result set for a select statement, or an integer value for an update statement.
   * @throws IDGenerationException if the connection to the database cannot be created.
   */
  public List<Map<String, Object>> executeQuery(String key, Object[] parameters)
      throws IDGenerationException {
    if ((key == null) || (key.trim().length() == 0)) {
      throw new IllegalArgumentException("The key should not be null or empty!");
    }
    if (!sqlSentences.containsKey(key)) {
      throw new IllegalArgumentException(
          "The key is not for a required sql statement supporting id generation.");
    }
    return executeSqlWithParam(jdbcTemplate, sqlSentences.get(key), newArrayList(parameters));
  }

  public int executeUpdate(String key, Object[] parameters) throws IDGenerationException {
    if ((key == null) || (key.trim().length() == 0)) {
      throw new IllegalArgumentException("The key should not be null or empty!");
    }
    if (!sqlSentences.containsKey(key)) {
      throw new IllegalArgumentException(
          "The key is not for a required sql statement supporting id generation.");
    }
    return executeUpdateSql(jdbcTemplate, sqlSentences.get(key), newArrayList(parameters));
  }

  /** Commits all the changes to the database. */
  public void commit() {}

  /** Rollbacks all the changes made to the database. */
  public void rollback() {}

  /**
   * Releases all the database resources.
   *
   * @param force whether to release the resources even reuseConnection is true.
   */
  public void releaseDatabaseResources(boolean force) {}
}
