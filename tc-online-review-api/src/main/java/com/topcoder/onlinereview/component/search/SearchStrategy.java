/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.search;

import com.topcoder.onlinereview.component.search.builder.SearchBuilderHelper;
import com.topcoder.onlinereview.component.search.builder.SearchFragmentBuilder;
import com.topcoder.onlinereview.component.search.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.util.CommonUtils.executeSqlWithParam;

/**
 * This is a Search Strategy that is tuned for searching a database. It is responsible for building
 * the necessary SQL search string appropriate to the filters provided and executing the SQL against
 * the database. This is done with the help of the SearchFragmentBuilder implementations that are
 * provided in this package. Each SearchFragmentBuilder is responsible for building the SQL for a
 * specific filter, and a ClassAssociator is used to associate the FragmentBuilders with the filters
 * (making the filter - Fragment mapping easier).
 *
 * <p>A PreparedStatement is used, and the search parameters are bound to the PreparedStatement
 * after the entire JDBC SQL String has been generated.
 *
 * <p>Thread Safety: This is thread safe. The state is maintained in a separate SearchContext class,
 * allowing concurrent calls to be supported.
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.3
 */
@Component
public class SearchStrategy {

  @Value("${search.persistence.entity-manager-name}")
  private String entityManagerName;

  @Autowired private Map<String, EntityManager> entityManagerMap;
  private EntityManager entityManager;

  @PostConstruct
  public void postRun() {
    entityManager = entityManagerMap.get(entityManagerName);
  }
  /**
   * This is a protected method that can be used to build the SearchContext for the search. It may
   * be overridden by subclasses to provide additional or different methods of building up the
   * search context.
   *
   * <p>In this Strategy, the SearchContext is used to hold the state of processing the Filters. The
   * search String is built separately from the binding parameters. The SQL SearchFragmentBuilders
   * are expected to do the following: 1. Update the searchString by appending the necessary String
   * fragment relevant to the Filter. Usually this is a String fragment that contains a
   * PreparedStatement-bindable parameter. For example: "a > ?" 2. If a bindable parameter(s) were
   * added, append them to the Bindable parameters of the SearchContext.
   *
   * <p>This method may be overridden by subclasses to add additional behavior (for example,
   * processing the SQL String to selectively choose the table joins for increased query
   * performance). For such a method, all it would do is call super.buildSearchContext, then modify
   * the searchString buffer of the built context befrore returning itself.
   *
   * @return A SearchContext object that is used to hold the state of building the search.
   * @param context This is the context under which the search is performed.
   * @param filter The search filter which will constrain the search results.
   * @param returnFields A list of return fields to further constrain the search an empty List means
   *     that all fields in the context should be returned by the search.
   * @param aliasMap a map of strings, holding the alternate names of fields as keys and their
   *     actual values in the datastore as the respective values.
   * @throws UnrecognizedFilterException propagated from SearchFragmentBuilder
   */
  private SearchContext buildSearchContext(
      String context, Filter filter, List returnFields, Map aliasMap)
      throws UnrecognizedFilterException {
    SearchContext searchContext = new SearchContext(aliasMap);

    // append the context firstly
    searchContext
        .getSearchString()
        .append(includeReturnFieldsInSearchString(context, returnFields));
    SearchFragmentBuilder builder = searchContext.getFragmentBuilder(filter);

    // if no SearchFragmentBuilder can be looked up, throw
    // UnrecognizedFilterException
    if (builder == null) {
      throw new UnrecognizedFilterException("No SearchFragmentBuilder can be retrieved.", filter);
    }

    // builder the search
    builder.buildSearch(filter, searchContext);

    return searchContext;
  }

  /**
   * Searches the database using the provided context, filter and constraints the returnFields.
   *
   * @return A CustomResultSet object containing the results of the search.
   * @param context The search context. This would be an SQL statement.
   * @param filter The filter to use.
   * @param returnFields The set of fields to return.
   * @param aliasMap a map of strings, holding the alternate names of fields as keys and their
   *     actual values in the datastore as the respective values.
   * @throws UnrecognizedFilterException propagated from SearchFragmentBuilder
   * @throws IllegalArgumentException if context or filter is null or if context is an empty String
   *     or if returnFields contains null/non-String contents or aliasMap contains null/empty String
   *     arguments.
   * @throws PersistenceOperationException to wrap any exception that occurs while searching (except
   *     UnrecognizedFilterException and IAE).
   */
  public List<Map<String, Object>> search(
      String context, Filter filter, List returnFields, Map aliasMap)
      throws PersistenceOperationException, UnrecognizedFilterException {
    if (context == null) {
      throw new IllegalArgumentException("The context should not be null.");
    }
    if (context.trim().length() == 0) {
      throw new IllegalArgumentException("The context should not be empty.");
    }
    if (filter == null) {
      throw new IllegalArgumentException("The context should not be null.");
    }
    SearchBuilderHelper.checkList(returnFields, "returnFields", String.class);
    SearchBuilderHelper.checkaliasMap(aliasMap, "aliasMap");
    SearchContext searchContext = buildSearchContext(context, filter, returnFields, aliasMap);
    try {
      return executeSqlWithParam(
          entityManager,
          searchContext.getSearchString().toString(),
          searchContext.getBindableParameters());
    } catch (Exception e) {
      e.printStackTrace();
      throw new PersistenceOperationException("SQLException occurs.", e);
    }
  }

  /**
   * Build a complete search string via including return fields into the search string at last,the
   * return fields will include both of those in the list and the searchString. The mothed do not
   * need synchronized.
   *
   * @param context a search string used to conduct the search
   * @param fields names of fileds should be returned
   * @return constructed search string
   * @throws IllegalArgumentException is the context is invalid
   */
  private String includeReturnFieldsInSearchString(String context, List fields) {
    if (fields.size() == 0) {
      return context + " ";
    }
    StringBuffer buffer = new StringBuffer();
    if (fields.size() > 0) {
      Iterator it = fields.iterator();
      buffer.append((String) it.next());
      while (it.hasNext()) {
        buffer.append(",").append((String) it.next());
      }
    }
    String returnFields = buffer.toString();

    // find the 'select' token
    int indexSelect = context.toLowerCase().indexOf("select");

    // find the 'from' token
    int indexFrom = context.toLowerCase().indexOf("from");

    // if not find the 'select token'
    if ((indexSelect < 0) || (indexFrom < 0)) {
      throw new IllegalArgumentException(
          "The searchString should contain the 'select' token to includeReturnFieldsInSearchString.");
    }

    String filedNames = context.substring(indexSelect + "select".length(), indexFrom).trim();

    // if select all then return the searchString
    if (filedNames.equals("*")) {
      // select all the fields
      return context + " ";
    }

    // makeup the string and return
    buffer = new StringBuffer();
    buffer.append("select ").append(filedNames);
    // fields already exist in context
    if (filedNames.length() != 0) {
      buffer.append(", ");
    }
    buffer.append(returnFields).append(" ").append(context.substring(indexFrom)).append(" ");
    return buffer.toString();
  }
}
