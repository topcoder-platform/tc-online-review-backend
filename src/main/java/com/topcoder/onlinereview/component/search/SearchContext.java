/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.search;

import com.topcoder.onlinereview.component.search.builder.SearchBuilderHelper;
import com.topcoder.onlinereview.component.search.builder.SearchFragmentBuilder;
import com.topcoder.onlinereview.component.search.filter.Filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a State object that is used to hold the current status of building a Search. It contains
 * any bindable parameters, as well as the existing search string. It may be used by any
 * SearchStrategies as a convenience class to help with building the search string and relevant
 * parameters.
 *
 * <p>Thread Safety: This class is not required to be thread safe. Only one SearchContext will be
 * used per thread.
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.3
 */
public class SearchContext {

  /**
   * This is the Search String that is currently being built. It is initially empty, but may be
   * built by direct access to the StringBuilder class via <code>getSearchString</code>.
   */
  private final StringBuffer searchString;

  /**
   * This is a list of bindableParameters that have been accumulated during the Search Building
   * process. It is initally empty, but may be modified by direct access to the bindableParameters
   * List via getBindableParameters.
   */
  private final List bindableParameters;

  /**
   * It will hold a Map containing keys as the alias names of the fields and the names of fileds as
   * stored objects. After the initialization, it can not be null, it will be initialized in the
   * constructor. It is immutable. Value will not be changed during the life time of the instance
   */
  private Map aliasMap;

  /**
   * Constructor that builds a SearchContext with the provided SearchFragmentBuilders for lookup.
   *
   * <p>fragmentBuilderAssociations may be null, to indicate that the SearchContext does not need to
   * provide lookups.
   *
   * @param aliasMap a map of strings, holding the alternate names of fields as keys and their
   *     actual values in the datastore as the respective values.
   * @throws IllegalArgumentException if the aliasMap is null, or aliasMap contains null or empty
   *     String elements or non-string elements for both key and value.
   */
  public SearchContext(Map aliasMap) {
    SearchBuilderHelper.checkaliasMap(aliasMap, "aliasMap");

    // init the members
    this.aliasMap = new HashMap(aliasMap);
    this.searchString = new StringBuffer();
    this.bindableParameters = new ArrayList();
  }

  /**
   * Retrieves the Search String that is currently being built.
   *
   * @return the Search String that is currently being built.
   */
  public StringBuffer getSearchString() {
    return searchString;
  }

  /**
   * Retrieves a list of bindableParameters that have been accumulated during the Search Building
   * process.
   *
   * <p>Here can not used shadow copy since it will be used to add the bindable paramters.
   *
   * @return a list of bindableParameters that have been accumulated during the Search Building
   *     process.
   */
  public List getBindableParameters() {
    return bindableParameters;
  }

  /**
   * Retrieves the SearchFragmentBuilder that is associated for the provided filter. If the
   * fragmentBuilders is null, or if no association can be found, then a null is returned.
   *
   * @return the SearchFragmentBuilder that is associated for the provided filter.
   * @param filter The filter to retrieve a SearchFragmentBuidler form.
   * @throws IllegalArgumentException if filter is null.
   */
  public SearchFragmentBuilder getFragmentBuilder(Filter filter) {
    if (filter == null) {
      throw new IllegalArgumentException("The filter should not be null.");
    }
    return filter.getSearchFragmentBuilder();
  }

  /**
   * Retrieves the field name for a provided alias. The field name will be the actual name used to
   * distinguish the field within the datastore.
   *
   * @return The actual field name that was represented by the alias. A null is returned if the
   *     alias could not be recognized.
   * @param alias An alias that is used as a convenient form of representing the actual field name.
   * @throws IllegalArgumentException if the alias is null or an empty String.
   */
  public String getFieldName(String alias) {
    if (alias == null) {
      throw new IllegalArgumentException("The alias String should not be null.");
    }
    if (alias.trim().length() == 0) {
      throw new IllegalArgumentException("The alias String should not be empty.");
    }

    return (String) aliasMap.get(alias);
  }
}
