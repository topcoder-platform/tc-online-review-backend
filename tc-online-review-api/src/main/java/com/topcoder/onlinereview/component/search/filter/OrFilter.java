/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.search.filter;

import com.topcoder.onlinereview.component.search.ValidationResult;
import com.topcoder.onlinereview.component.search.builder.OrFragmentBuilder;
import com.topcoder.onlinereview.component.search.builder.SearchFragmentBuilder;

import java.util.List;
import java.util.Map;

/**
 * This class extends AbstractAssociativeFilter. It is used to construct OR search criterion. The
 * class is thread safe. The filter attribute is initialized by constructor, and changed by
 * addFilter() method, all other methods use it. Thus it is like a read-write structure.
 *
 * <p>Thread-safety is ensured by using a read-write lock over filters attribute.
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.3
 */
public class OrFilter extends AbstractAssociativeFilter {
  /**
   * Create a new instance,by setting the first and second filter add them to the list of filters.
   *
   * @param first a <code> Filter </code> object
   * @param second a <code> Filter </code> object
   * @throws IllegalArgumentException if any parameter is Null
   */
  public OrFilter(Filter first, Filter second) {
    super(first, second);
  }

  /**
   * Create a new instance,by set the filters.
   *
   * @param filters to construct the filter
   */
  public OrFilter(List filters) {
    super(filters);
  }

  /**
   * Test to see if the filter is valid according to the rules presented in the validators.
   *
   * @param validators a map containing <code>ObjectValidator</code> Objects
   * @return the <code>ValidationResult</code> object
   * @param alias a Map containing mapping between real names and alias names
   * @throws IllegalArgumentException if any parameter is Null
   */
  public synchronized ValidationResult isValid(Map validators, Map alias) {
    return FilterHelper.isValidAssociativeFilter(validators, alias, filters);
  }

  public SearchFragmentBuilder getSearchFragmentBuilder() {
    return new OrFragmentBuilder();
  }
  /**
   * return a clone of the object.
   *
   * @return a clone of the object
   */
  public synchronized Object clone() {
    return FilterHelper.associativeFilterclone(this);
  }
}
