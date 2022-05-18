/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.search.filter;

import com.topcoder.onlinereview.component.search.ValidationResult;
import com.topcoder.onlinereview.component.search.builder.SearchFragmentBuilder;

import java.io.Serializable;
import java.util.Map;

/**
 * This interface defines APIs that every implementation must adhere to. It is part of the composite
 * pattern. It is a common interface for both individual and composite components so that both the
 * individual components and composite components can be viewed uniformly. It also defines a set of
 * static final integer constants representing Filter types.
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.3
 */
public interface Filter extends Cloneable, Serializable {
  /**
   * Test to see if this filter is valid.
   *
   * @param validators a map containing <code>ObjectValidator</code> Objects
   * @return the <code>ValidationResult</code> object
   * @param alias a Map containing mapping between real names and alias names
   * @throws IllegalArgumentException if any parameter is Null
   */
  ValidationResult isValid(Map validators, Map alias);

  SearchFragmentBuilder getSearchFragmentBuilder();

  /**
   * return the clone of the object.
   *
   * @return a clone of the filter.
   */
  Object clone();
}
