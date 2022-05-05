/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.search.filter;

import com.topcoder.onlinereview.component.search.ValidationResult;

import java.util.Map;

/**
 * Purpose: This class extends AbstractSimpeFilter class. It is used to construct
 * GreaterThanOrEqualTo search criterion. This class also provides concrete isValid(), and
 * getFilterType() methods.
 *
 * <p>The class is thread-safe since it is immutable.
 *
 * @author victor_lxd, TCSDEVELOPER
 * @version 1.1
 */
public class GreaterThanOrEqualToFilter extends AbstractSimpleFilter {
  /**
   * Create a new instance,by setting the name and value.
   *
   * @param name the filed name of the search criterion
   * @param value a Comparable object used to set the upperThreshold and lowerThreshold attributes
   * @throws IllegalArgumentException if any parameter is Null
   * @throws IllegalArgumentException if <code>name</code> is empty
   */
  public GreaterThanOrEqualToFilter(String name, Comparable value) {
    super(name, value);
    lowerThreshold = value;
    isLowerThresholdInclusive = true;
  }

  /**
   * Test to see if the filter is valid according to the rules presented in the validators.
   *
   * @param validators a map containing <code>ObjectValidator</code> Objects
   * @return the <code>ValidationResult</code> object
   * @param alias a Map containing mapping between real names and alias names
   * @throws IllegalArgumentException if any parameter is Null
   */
  public ValidationResult isValid(Map validators, Map alias) {
    return FilterHelper.isValidSimpleFilter(validators, alias, fieldName, value, this);
  }

  /**
   * Get the type of the Filter.
   *
   * @return a integer representing the type of the Filter
   * @deprecated This method has been deprecated.
   */
  public int getFilterType() {
    return Filter.GREATER_THAN_OR_EQUAL_TO_FILTER;
  }

  /**
   * return a clone of the object.
   *
   * @return a clone of the object
   */
  public Object clone() {
    return (new GreaterThanOrEqualToFilter(this.fieldName, this.value));
  }
}
