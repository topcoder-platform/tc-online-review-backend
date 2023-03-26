/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.search;

import com.topcoder.onlinereview.component.search.filter.AndFilter;
import com.topcoder.onlinereview.component.search.filter.BetweenFilter;
import com.topcoder.onlinereview.component.search.filter.EqualToFilter;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.component.search.filter.GreaterThanFilter;
import com.topcoder.onlinereview.component.search.filter.GreaterThanOrEqualToFilter;
import com.topcoder.onlinereview.component.search.filter.InFilter;
import com.topcoder.onlinereview.component.search.filter.LessThanFilter;
import com.topcoder.onlinereview.component.search.filter.LessThanOrEqualToFilter;
import com.topcoder.onlinereview.component.search.filter.LikeFilter;
import com.topcoder.onlinereview.component.search.filter.NotFilter;
import com.topcoder.onlinereview.component.search.filter.OrFilter;

import java.util.List;

/**
 * This class represents a search bundle. It is the most important class of the component.It can
 * Build Filter tree. Since the Filter package employs the composite pattern, we have simple Filters
 * and associative filters. Thus all Filters defined for a search can form a tree. The leaves are
 * simple Filters and the parent nodes are associative Filters.And also thoses filters can be
 * created in this class.
 *
 * <p>It also can Validate the constructed Filter according to the rules holding by ObjectValidator
 * objects.Also it do the converting the Filter to search string via the builder.
 *
 * <p>Most import, all the searchFunction is implemented by this class as the API of the component.
 * Both include the Database and LADP.
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.3
 */
public class SearchBundle {
  /**
   * Build a GreaterThanFilter using the real name of the field.
   *
   * @param name name of the field, might be alias, mihgt be real name
   * @param value a Comparable object representing the value constraint of the field
   * @return constructed Filter object
   * @throws IllegalArgumentException if any parameter is null
   * @throws IllegalArgumentException if name is an empty string
   * @deprecated This method is now deprecated.
   */
  public static Filter buildGreaterThanFilter(String name, Comparable value) {
    // Exception will throw in GreaterThanFilter construct if param invalid
    return (Filter) new GreaterThanFilter(name, value);
  }

  /**
   * Build a LessThanFilter using the real name of the field.
   *
   * @param name name of the field, might be alias, mihgt be real name
   * @param value a Comparable object representing the value constraint of the field
   * @return constructed Filter object
   * @throws IllegalArgumentException if any parameter is null
   * @throws IllegalArgumentException if name is an empty string
   * @deprecated This method is now deprecated.
   */
  public static Filter buildLessThanFilter(String name, Comparable value) {
    // Exception will throw in LessThanFilter construct if param invalid
    return (Filter) new LessThanFilter(name, value);
  }

  /**
   * Build a GreaterThanOrEqualToFilter using the real name of the field.
   *
   * @param name name of the field, might be alias, mihgt be real name
   * @param value a Comparable object representing the value constraint of the field
   * @return constructed Filter object
   * @throws IllegalArgumentException if any parameter is null
   * @throws IllegalArgumentException if name is an empty string
   * @deprecated This method is now deprecated.
   */
  public static Filter buildGreaterThanOrEqualToFilter(String name, Comparable value) {
    // Exception will throw in GreaterThanOrEqualToFilter construct if param invalid
    return (Filter) new GreaterThanOrEqualToFilter(name, value);
  }

  /**
   * Build a LessThanOrEqualToFilter using the real name of the field.
   *
   * @param name name of the field, might be alias, mihgt be real name
   * @param value a Comparable object representing the value constraint of the field
   * @return constructed Filter object
   * @throws IllegalArgumentException if any parameter is null
   * @throws IllegalArgumentException if name is an empty string
   * @deprecated This method is now deprecated.
   */
  public static Filter buildLessThanOrEqualToFilter(String name, Comparable value) {
    // Exception will throw in LessThanOrEqualToFilter construct if param invalid
    return (Filter) new LessThanOrEqualToFilter(name, value);
  }

  /**
   * Build an EqualToFilter using the real name of the field.
   *
   * @param name name of the field, might be alias, mihgt be real name
   * @param value a Comparable object representing the value constraint of the field
   * @return constructed Filter object
   * @throws IllegalArgumentException if any parameter is null
   * @throws IllegalArgumentException if name is an empty string
   * @deprecated This method is now deprecated.
   */
  public static Filter buildEqualToFilter(String name, Comparable value) {
    // Exception will throw in EqualToFilter construct if param invalid
    return (Filter) new EqualToFilter(name, value);
  }

  /**
   * Build a BetweenFilter using the real name of the field.
   *
   * @param name name of the field, might be alias, mihgt be real name
   * @param upper a Comparable object representing the upper threshold value of the field
   * @param lower a Comparable object representing the lower threshold value of the field
   * @return constructed Filter object
   * @throws IllegalArgumentException if any parameter is null
   * @throws IllegalArgumentException if name is an empty string
   * @deprecated This method is now deprecated.
   */
  public static Filter buildBetweenFilter(String name, Comparable upper, Comparable lower) {
    // Exception will throw in BetweenFilter construct if param invalid
    return (Filter) new BetweenFilter(name, upper, lower);
  }

  /**
   * Build an InFilter using the real name of the field.
   *
   * @param name name of the field, might be alias, mihgt be real name
   * @param values a Comparable object representing the value constraints of the field
   * @return constructed Filter object
   * @throws IllegalArgumentException if any parameter is null
   * @throws IllegalArgumentException if name is an empty string
   * @deprecated This method is now deprecated.
   */
  public static Filter buildInFilter(String name, List values) {
    // Exception will throw in InFilter construct if param invalid
    return (Filter) new InFilter(name, values);
  }

  /**
   * Build an AndFilter using the real name of the field.
   *
   * @param first a Filter object to be used to build an associative Filter
   * @param second a Filter object to be used to build an associative Filter
   * @return constructed Filter object
   * @throws IllegalArgumentException if any parameter is null
   * @deprecated This method is now deprecated.
   */
  public static Filter buildAndFilter(Filter first, Filter second) {
    // Exception  will throw in AndFilter construct if param invalid
    return (Filter) new AndFilter(first, second);
  }

  /**
   * Build an OrFilter using the real name of the field.
   *
   * @param first a Filter object to be used to build an associative Filter
   * @param second a Filter object to be used to build an associative Filter
   * @return constructed Filter object
   * @throws IllegalArgumentException if any parameter is null
   * @deprecated This method is now deprecated.
   */
  public static Filter buildOrFilter(Filter first, Filter second) {
    // Exception  will throw in OrFilter construct if param invalid
    return (Filter) new OrFilter(first, second);
  }

  /**
   * Build an NotFilter providing the notFilter.
   *
   * @param filter a Filter to be negated
   * @return constructed Filter object
   * @throws IllegalArgumentException if any parameter is null
   * @deprecated This method is now deprecated.
   */
  public static Filter buildNotFilter(Filter filter) {
    // Exception  will throw in NotFilter construct if param invalid
    return (Filter) new NotFilter(filter);
  }

  /**
   * Build a LikeFilter with the given name and value.
   *
   * <p>This method is added in version 1.2.
   *
   * @param name the name of the field, might be alias, might be real name
   * @param value the searching value in the filter
   * @return constructed LikeFilter object
   * @throws IllegalArgumentException if any parameter is null or empty, or value does not begin
   *     with 'SS', 'SW', 'EW', 'WC', or the value without the prefix is zero length
   * @deprecated This method is now deprecated.
   */
  public static Filter buildLikeFilter(String name, String value) {
    return new LikeFilter(name, value);
  }

  /**
   * Build a LikeFilter with the given name, value and escape character.
   *
   * <p>This method is added in version 1.2.
   *
   * @param name the name of the field, might be alias, mihgt be real name
   * @param value the searching value in the filter
   * @param escapeCharacter the character used to escape
   * @return constructed LikeFilter object
   * @throws IllegalArgumentException if any parameter is null or empty, or value does not begin
   *     with 'SS', 'SW', 'EW', 'WC', or the value without the prefix is zero length, or the
   *     escapeCharacter is of some masked chars('*', '%', '_')
   * @deprecated This method is now deprecated.
   */
  public static Filter buildLikeFilter(String name, String value, char escapeCharacter) {
    return new LikeFilter(name, value, escapeCharacter);
  }
}
