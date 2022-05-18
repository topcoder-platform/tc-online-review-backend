/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.util.CommonUtils.getDouble;
import static com.topcoder.onlinereview.util.CommonUtils.getInt;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.util.CommonUtils.getString;

/**
 * This is the Database implementation of the <code>{@link UserRetrieval}</code> interface.
 *
 * <p>All the methods (except <code>retrieveUser(long)</code> and <code>retrieveUser(String)</code>)
 * call <code>super.getConnection</code> to get a connection from the DBConnectionFactory, and then
 * call to <code>super.retrieveObjects</code>, which calls <code>this.createObject</code>.
 *
 * <p>Then, they call <code>updateEmails</code> and <code>updateRatings</code>. Afterwards, the
 * prepared statement, result set and connections are all closed using <code>super.close()</code>.
 *
 * <p>All <code>SQLException</code>s in all methods should be wrapped in <code>RetrievalException
 * </code>.
 *
 * <p><b>Thread Safety</b>: This class is immutable and therefore thread-safe.
 *
 * @author dplass, oodinary
 * @author FireIce
 * @version 1.1
 * @since 1.0
 */
@Component
public class UserRetrieval {

  @Autowired
  @Qualifier("tcsJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  /**
   * Retrieves the external user with the given id.
   *
   * <p>Simply calls retrieveUsers(long[]) and returns the first entry in the array. If the array is
   * empty, return null.
   *
   * @return the external user who has the given id, or null if not found.
   * @param id the id of the user we are interested in.
   * @throws IllegalArgumentException if id is not positive.
   * @throws RetrievalException if any exception occurred during processing; it will wrap the
   *     underlying exception.
   */
  public ExternalUser retrieveUser(Long id) throws RetrievalException {
    // Gets Users by calling retrieveUsers(long[]).
    ExternalUser[] users = retrieveUsers(new Long[] {id});

    // If the array is empty, return null; else, return this first one.
    return retFirstObject(users);
  }

  private ExternalUser retFirstObject(ExternalUser[] users) {
    if (users != null && users.length > 0) {
      return users[0];
    }
    return null;
  }

  /**
   * Retrieves the external user with the given handle.
   *
   * <p>Simply calls retrieveUsers(String[]) and returns the first entry in the array. If the array
   * is empty, return null.
   *
   * @return the external user who has the given handle, or null if not found.
   * @param handle the handle of the user we are interested in.
   * @throws IllegalArgumentException if handle is <code>null</code> or empty after trim.
   * @throws RetrievalException if any exception occurred during processing; it will wrap the
   *     underlying exception.
   */
  public ExternalUser retrieveUser(String handle) throws RetrievalException {
    // Gets Users by calling retrieveUsers(String[]).
    ExternalUser[] users = retrieveUsers(new String[] {handle});

    // If the array is empty, return null; else, return this first one.
    return retFirstObject(users);
  }

  /**
   * Retrieves the external users with the given ids. Note that retrieveUsers(ids)[i] will not
   * necessarily correspond to ids[i].
   *
   * <p>If an entry in ids was not found, no entry in the return array will be present. If there are
   * any duplicates in the input array, the output will NOT contain a duplicate External User.
   *
   * @param ids the ids of the users we are interested in.
   * @return an array of external users who have the given ids. If none of the given ids were found,
   *     an empty array will be returned. The index of the entries in the array will not necessarily
   *     directly correspond to the entries in the ids array.
   * @throws IllegalArgumentException if ids is <code>null</code> or any entry is not positive.
   * @throws RetrievalException if any exception occurred during processing; it will wrap the
   *     underlying exception.
   */
  public ExternalUser[] retrieveUsers(Long[] ids) throws RetrievalException {
    if (ids.length == 0) {
      return new ExternalUser[0];
    }

    // Selects users by ids.
    String queryAndClause = " AND u.user_id in ";

    // Delegates to retrieveUsers(String, Object, int, boolean).
    return retrieveUsers(queryAndClause, newArrayList(ids));
  }

  /**
   * Retrieves the external users with the given handles. Note that retrieveUsers(handles)[i] will
   * not necessarily correspond to handles[i].
   *
   * <p>If an entry in handles was not found, no entry in the return array will be present. If there
   * are any duplicates in the input array, the output will NOT contain a duplicate External User.
   *
   * @param handles the handles of the users we are interested in.
   * @return an array of external users who have the given handles. If none of the given handles
   *     were found, an empty array will be returned. The entries in the array will not necessarily
   *     directly correspond to the entries in the handles array.
   * @throws IllegalArgumentException if handles is <code>null</code> or any entry is <code>null
   *     </code>, or empty after trim.
   * @throws RetrievalException if any exception occurred during processing; it will wrap the
   *     underlying exception.
   */
  public ExternalUser[] retrieveUsers(String[] handles) throws RetrievalException {
    if (handles.length == 0) {
      return new ExternalUser[0];
    }

    // Selects users by handles.
    String queryAndClause = " AND u.handle in ";

    // Delegates to retrieveUsers(String, Object, int, boolean).
    return retrieveUsers(queryAndClause, newArrayList(handles));
  }

  /**
   * Retrieves the external users with the given handles, ignoring case when doing the retrieval.
   * Note that retrieveUsers(handles)[i] will not necessarily correspond to handles[i].
   *
   * <p>If an entry in handles was not found, no entry in the return array will be present. If more
   * than one entry was found (e.g., "SMITH" and "smith" were both found for the input "Smith") per
   * input handle, then they will both be in the output array. Conversely, if there are two entries
   * in the input array ("SMITH" and "smith") and there is only a single corresponding user
   * ("Smith") then there would only be a single entry (for "Smith") in the output array.
   *
   * @param handles the handles of the users we are interested in finding by the lower-case version
   *     of their handle.
   * @return an array of external users who have the given handles. If none of the given handles
   *     were found, an empty array will be returned. The entries in the array will not necessarily
   *     directly correspond to the entries in the handles array.
   * @throws IllegalArgumentException if handles is <code>null</code> or any entry is <code>null
   *     </code>, or empty after trim.
   * @throws RetrievalException if any exception occurred during processing; it will wrap the
   *     underlying exception.
   */
  public ExternalUser[] retrieveUsersIgnoreCase(String[] handles) throws RetrievalException {
    if (handles.length == 0) {
      return new ExternalUser[0];
    }

    // Selects users by handles.
    String queryAndClause = " AND u.handle_lower in ";

    // Delegates to retrieveUsers(String, Object, int, boolean).
    return retrieveUsers(
        queryAndClause, Stream.of(handles).map(String::toLowerCase).collect(Collectors.toList()));
  }

  /**
   * Retrieves the external users whose first and last name start with the given first and last
   * name. If either parameter is empty, it will be ignored. (If both parameters are empty, this is
   * an exception.)
   *
   * <p>To search for all users whose last name starts with "Smith", call retrieveUsersByName("",
   * "Smith"). (It is case <b>sensitive</b>.) To search for all users whose first name starts with
   * "Jon" and last name starts with "Smith", call retrieveUsersByname("Jon", "Smith").
   *
   * @param firstName the first name of the user(s) to find, or the empty string to represent "any
   *     first name". Both firstName and lastName cannot be empty.
   * @param lastName the last name of the user(s) to find, or the empty string to represent "any
   *     last name". Both firstName and lastName cannot be empty.
   * @return an array of external users whose first name and last name start with the given first
   *     and last name. If no users match, an empty array will be returned.
   * @throws IllegalArgumentException if firstName or lastName is <code>null</code>, or if BOTH are
   *     empty after trim. (I.e., if one is empty after trim, and the other is not empty after trim,
   *     this is not an exception.)
   * @throws RetrievalException if any exception occurred during processing; it will wrap the
   *     underlying exception.
   */
  public ExternalUser[] retrieveUsersByName(String firstName, String lastName)
      throws RetrievalException {
    if (firstName.trim().equals("") && lastName.trim().equals("")) {
      throw new IllegalArgumentException("The firstName and lastName cannot both be empty.");
    }

    // Creates an ArrayList for storing the names.
    List names = new ArrayList();
    names.add(firstName);
    names.add(lastName);

    // Selects users by names.
    String queryAndClause = " AND u.first_name like ? and u.last_name like ?";

    // Delegates to retrieveUsers(String, Object, int, boolean).
    return retrieveUsers(queryAndClause, names);
  }

  /**
   * Retrieves the external users with the given ids or handles(ignore case and not) or first name
   * and last name.
   *
   * <p>All the other retrieveUsers method delegate to this one.
   *
   * @param rawClause the query of the prepareStatement, given be the caller.
   * @param parameters the parameter of the query, it can be long[] or String[], due to the
   *     different caller.
   * @return an array of external users whose first name and last name start with the given first
   *     and last name. If no users match, an empty array will be returned.
   * @throws RetrievalException if any exception occurred during processing; it will wrap the
   *     underlying exception.
   */
  private ExternalUser[] retrieveUsers(String rawClause, List<Object> parameters)
      throws RetrievalException {
    String marks = parameters.stream().map(p -> "?").collect(Collectors.joining(","));
    String queryAndClause = rawClause + "(" + marks + ")";
    // Selects Users.
    Map<Long, ExternalUser> userMap = selectUsers(queryAndClause, parameters);

    // Selects and then updates email and rating of the users.
    selectEmail(queryAndClause, parameters, userMap);
    selectRating(queryAndClause, parameters, userMap);

    // Convert the map to an array.
    ExternalUser[] users =
        (ExternalUser[]) new LinkedList(userMap.values()).toArray(new ExternalUser[0]);

    return users;
  }

  /**
   * Selects users by using the userQuery, and then call the super.retrieveObjects to get the Map of
   * the user for returning.
   *
   * @param queryAndClause the query of the prepareStatement, given be the caller.
   * @param queryParameter the parameter of the query, it can be long[] or String[], due to the
   *     different caller.
   * @return the Map of the user.
   * @throws RetrievalException if any exception occurred during processing; it will wrap the
   *     underlying exception.
   */
  private Map<Long, ExternalUser> selectUsers(String queryAndClause, List<Object> queryParameter)
      throws RetrievalException {
    // Constructs the query string.
    String userQuery =
        "SELECT u.user_id id, first_name, last_name, handle, address "
            + "FROM user u, email WHERE u.user_id = email.user_id AND email.primary_ind = 1 ";
    List<Map<String, Object>> result =
        executeSqlWithParam(jdbcTemplate, userQuery + queryAndClause, queryParameter);
    return result.stream()
        .collect(
            Collectors.toMap(
                m -> getLong(m, "id"),
                m ->
                    new ExternalUser(
                        getLong(m, "id"),
                        getString(m, "handle"),
                        getString(m, "first_name"),
                        getString(m, "last_name"),
                        getString(m, "address"))));
  }

  /**
   * Selects emails by using the emailQuery, and then call the private method updateEmails to update
   * the email information of the user.
   *
   * @param queryAndClause the query of the prepareStatement, given be the caller.
   * @param queryParameter the parameter of the query, it can be long[] or String[], due to the
   *     different caller.
   * @param userMap the Map of the user retrieved from the selectUsers method.
   * @return the Map of the user after email updated.
   * @throws RetrievalException if any exception occurred during processing; it will wrap the
   *     underlying exception.
   */
  private Map<Long, ExternalUser> selectEmail(
      String queryAndClause, List<Object> queryParameter, Map<Long, ExternalUser> userMap) {
    // Prepares the email query.
    String emailQuery =
        "SELECT u.user_id id, address FROM user u, email "
            + "WHERE u.user_id = email.user_id AND email.primary_ind = 0 ";
    List<Map<String, Object>> result =
        executeSqlWithParam(jdbcTemplate, emailQuery + queryAndClause, queryParameter);
    for (Map<String, Object> m : result) {
      Optional.ofNullable(userMap.get(getLong(m, "id")))
          .ifPresent(u -> u.addAlternativeEmail(getString(m, "address")));
    }
    return userMap;
  }

  /**
   * Selects ratings by using the ratingsQuery, and then call the private method updateRatings to
   * update the rating information of the user.
   *
   * @param queryAndClause the query of the prepareStatement, given be the caller.
   * @param queryParameter the parameter of the query, it can be long[] or String[], due to the
   *     different caller.
   * @param userMap the Map of the user retrieved from the selectUsers method.
   * @return the Map of the user after rating updated.
   * @throws RetrievalException if any exception occurred during processing; it will wrap the
   *     underlying exception.
   */
  private Map selectRating(
      String queryAndClause, List<Object> queryParameter, Map<Long, ExternalUser> userMap) {
    // Prepares the ratings query.
    String ratingsQuery =
        "SELECT u.user_id id, r.rating rating, r.phase_id phaseId, "
            + "vol volatility, num_ratings numRatings, ur.rating reliability "
            + "FROM user u, user_rating r, OUTER user_reliability ur WHERE u.user_id = r.user_id "
            + "AND u.user_id = ur.user_id AND r.phase_id = ur.phase_id ";
    List<Map<String, Object>> result =
        executeSqlWithParam(jdbcTemplate, ratingsQuery + queryAndClause, queryParameter);
    result.forEach(
        m -> {
          Optional.ofNullable(userMap.get(getLong(m, "id")))
              .ifPresent(
                  u ->
                      u.addRatingInfo(
                          new RatingInfo(
                              RatingType.getRatingType(getInt(m, "phaseId")),
                              getInt(m, "rating"),
                              getInt(m, "numRatings"),
                              getInt(m, "volatility"),
                              getDouble(m, "reliability"))));
        });
    return userMap;
  }
}
