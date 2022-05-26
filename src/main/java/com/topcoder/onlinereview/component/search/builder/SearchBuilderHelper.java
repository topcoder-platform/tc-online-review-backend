/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.search.builder;

import com.topcoder.onlinereview.component.search.SearchContext;
import com.topcoder.onlinereview.component.search.UnrecognizedFilterException;
import com.topcoder.onlinereview.component.search.filter.AbstractAssociativeFilter;
import com.topcoder.onlinereview.component.search.filter.AbstractSimpleFilter;
import com.topcoder.onlinereview.component.search.filter.Filter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This helperClass of the package com.topcoder.search.builder. The class offers some check mothed
 * use to check some param valid in some motheds.
 *
 * @author TCSDEVELOPER
 * @version 1.3
 */
public final class SearchBuilderHelper {
  /** The private construct. */
  private SearchBuilderHelper() {}

  /**
   * Get the real Name with the alias the context.
   *
   * @param alias the alias, maybe it is already the real name
   * @param searchContext the context used when build search
   * @return the real name
   */
  public static final String getRealName(String alias, SearchContext searchContext) {
    String realName = searchContext.getFieldName(alias);
    if (realName == null) {
      realName = alias;
    }
    return realName;
  }

  /**
   * Build the search String for Simple Filter, include NullFilter, EqualsToFilter, and
   * RangeFragmentBuilder. The filter is for database.
   *
   * <p>This method is added in version 1.3.
   *
   * @param filter the Simple Filter to be built on.
   * @param operateKey the operate key
   * @param searchContext the context of the building
   * @param value the value of the filter, can be null
   * @throws UnrecognizedFilterException if can not get all the inner builder successfully
   */
  public static void buildDBSimpleFilter(
      AbstractSimpleFilter filter, String operateKey, SearchContext searchContext, Object value)
      throws UnrecognizedFilterException {
    // retrieve the search String
    StringBuffer buffer = searchContext.getSearchString();

    // get the real name and append to buffer
    buffer.append(getRealName(filter.getName(), searchContext));

    buffer.append(" ").append(operateKey).append(" ?");

    searchContext.getBindableParameters().add(value);
  }

  /**
   * Build the search String for AbstractAssociativeFilter, the filter is for database.
   *
   * <p>This method is added in version 1.3.
   *
   * @param filter the AbstractAssociativeFilter to be built on.
   * @param connectKey the connect key, 'AND' for AndFilter, 'OR' for OrFilter
   * @param searchContext the context of the building
   * @throws UnrecognizedFilterException if can not get all the inner builder successfully
   */
  public static void buildDBAbstractAssociativeFilter(
      AbstractAssociativeFilter filter, String connectKey, SearchContext searchContext)
      throws UnrecognizedFilterException {
    // retrieve the search String
    StringBuffer buffer = searchContext.getSearchString();

    buffer.append("(");

    boolean isFirst = true;
    // get all the filters in the andFilter, and build their search
    for (Iterator it = filter.getFilters().iterator(); it.hasNext(); ) {
      Filter innerFilter = (Filter) it.next();

      if (isFirst) {
        isFirst = false;
      } else {
        buffer.append(" " + connectKey + " ");
      }
      // look up the SearchFragmentBuilder in the context with the innerFilter
      SearchFragmentBuilder builder = searchContext.getFragmentBuilder(innerFilter);
      if (builder == null) {
        throw new UnrecognizedFilterException(
            "No SearchFragmentBuilder is looked up.", innerFilter);
      }

      // build the inner Filter firstly
      builder.buildSearch(innerFilter, searchContext);
    }
    buffer.append(")");
  }

  /**
   * Check the list valid.The list should not be null also should not be empty. The mothed also
   * check the items in the list which also should be with the expected class type.
   *
   * <p>This method is added in version 1.3.
   *
   * @param list The list to be checked
   * @param listName the Name of the list, which denote the usage of the list
   * @param expectedClass the expected class the items should be
   * @throws IllegalArgumentException if the list is null, the items in it is null, or empty String
   */
  public static void checkList(List list, String listName, Class expectedClass) {
    if (list == null) {
      throw new IllegalArgumentException("The list " + listName + " should not be null.");
    }

    // all the items in the list should not be null
    for (Iterator it = list.iterator(); it.hasNext(); ) {
      Object obj = it.next();
      if (obj == null) {
        throw new IllegalArgumentException(
            "The items in the list " + listName + " should not be null.");
      }
      if (!(expectedClass.isAssignableFrom(obj.getClass()))) {
        throw new IllegalArgumentException("The items in the list " + listName + " is invalid.");
      }

      if ((obj instanceof String) && String.valueOf(obj).trim().length() == 0) {
        throw new IllegalArgumentException(
            "The String in the list " + listName + " should not be empty.");
      }
    }
  }

  /**
   * Check the aliasMap valid.The map should not be null also should not be empty. The mothed also
   * check the key and value in the map which also should not be null. More, both the key and value
   * should String type.
   *
   * <p>This method is updated in version 1.3.
   *
   * @param map The map to be checked
   * @param mapName the Name of the map, which denote the usage of the map
   * @throws IllegalArgumentException if the map is null, if the keys and values illegal
   */
  public static void checkaliasMap(Map map, String mapName) {
    if (map == null) {
      throw new IllegalArgumentException("The " + mapName + " should not be null.");
    }
    for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
      Map.Entry entry = (Map.Entry) it.next();
      Object key = entry.getKey();
      // the keys in the map should not be null
      if (!(key instanceof String)) {
        throw new IllegalArgumentException("The key in the list " + mapName + " illegal.");
      }
      Object value = entry.getValue();
      // the values in the map should not be null
      if (!(value instanceof String)) {
        throw new IllegalArgumentException("The value in the list " + mapName + " illegal.");
      }

      if (key.toString().trim().length() == 0 || value.toString().trim().length() == 0) {
        throw new IllegalArgumentException(
            "Both the key and value in the list " + mapName + " should not be" + " empty.");
      }
    }
  }
}
