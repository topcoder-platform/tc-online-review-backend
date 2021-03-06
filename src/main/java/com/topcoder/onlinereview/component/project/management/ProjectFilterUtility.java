/*
 * Copyright (C) 2006-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.management;

import com.topcoder.onlinereview.component.search.SearchBundle;
import com.topcoder.onlinereview.component.search.filter.Filter;

import java.util.List;

/**
 * This class contains methods to build filter instances to use in project searching. It can build
 * filter to search for project based on various criteria such as:
 *
 * <ul>
 *   <li>Project type id
 *   <li>Project type name
 *   <li>Project category id
 *   <li>Project category name
 *   <li>Project status id
 *   <li>Project status name
 *   <li>Project property name
 *   <li>Project property value
 *   <li>Project resource property name
 *   <li>Project resource property value
 * </ul>
 *
 * Besides, this class also provides method to combine any of the above filter to make complex
 * filters. This class is used be the client to create search filter. The created filter is used in
 * SearchProjects() method of ProjectManager.
 *
 * <p>Thread safety: This class is immutable and thread safe.
 *
 * @author tuenm, iamajia, VolodymyrK
 * @version 1.2.2
 */
public class ProjectFilterUtility {

  /**
   * Represents the alias for project type id column. It is used to build search filter for this
   * column. This constant also defined in configuration settings for search builder.
   */
  public static final String PROJECT_TYPE_ID = "ProjectTypeID";

  /**
   * Represents the alias for project type name column. It is used to build search filter for this
   * column. This constant also defined in configuration settings for search builder.
   */
  public static final String PROJECT_TYPE_NAME = "ProjectTypeName";

  /**
   * Represents the alias for project category id column. It is used to build search filter for this
   * column. This constant also defined in configuration settings for search builder.
   */
  public static final String PROJECT_CATEGORY_ID = "ProjectCategoryID";

  /**
   * Represents the alias for project category name column. It is used to build search filter for
   * this column. This constant also defined in configuration settings for search builder.
   */
  public static final String PROJECT_CATEGORY_NAME = "ProjectCategoryName";

  /**
   * Represents the alias for project status id column. It is used to build search filter for this
   * column. This constant also defined in configuration settings for search builder.
   */
  public static final String PROJECT_STATUS_ID = "ProjectStatusID";

  /**
   * Represents the alias for project status name column. It is used to build search filter for this
   * column. This constant also defined in configuration settings for search builder.
   */
  public static final String PROJECT_STATUS_NAME = "ProjectStatusName";

  /**
   * Represents the alias for project property name column. It is used to build search filter for
   * this column. This constant also defined in configuration settings for search builder.
   */
  public static final String PROJECT_PROPERTY_NAME = "ProjectPropertyName";

  /**
   * Represents the alias for project property value column. It is used to build search filter for
   * this column. This constant also defined in configuration settings for search builder.
   */
  public static final String PROJECT_PROPERTY_VALUE = "ProjectPropertyValue";

  /**
   * Represents the alias for TC Direct project ID value. It is used to build search filter for this
   * column. This constant also defined in configuration settings for search builder.
   *
   * @since 1.2.1
   */
  public static final String PROJECT_TC_DIRECT_PROJECT_ID = "TCDirectProjectID";

  /** Private constructor to prevent initialization of class instance. */
  private ProjectFilterUtility() {}

  /**
   * Build the filter to search for project with the given type id. Type id must be greater than
   * zero.
   *
   * @return the filter to search for project.
   * @param typeId The type id to build filter.
   * @throws IllegalArgumentException if input is less than or equals to zero.
   */
  public static Filter buildTypeIdEqualFilter(long typeId) {
    Helper.checkNumberPositive(typeId, "typeId");
    return SearchBundle.buildEqualToFilter(PROJECT_TYPE_ID, typeId);
  }

  /**
   * Build the filter to search for project with type id in the given type id list. Type ids must be
   * greater than zero. The content of the input list must be of Long type.
   *
   * @return the filter to search for project.
   * @param typeIds The type id list to build filter.
   * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the
   *     list is null or empty or the content of the list is not of Long type.
   */
  @SuppressWarnings("deprecation")
  public static Filter buildTypeIdInFilter(List typeIds) {
    assertLongListValid(typeIds, "typeIds");
    return SearchBundle.buildInFilter(PROJECT_TYPE_ID, typeIds);
  }

  /**
   * Build the filter to search for project with the given type name. Type name must not be null or
   * empty string.
   *
   * @return the filter to search for project.
   * @param name The type name to build filter.
   * @throws IllegalArgumentException if input is null or empty.
   */
  @SuppressWarnings("deprecation")
  public static Filter buildTypeNameEqualFilter(String name) {
    Helper.checkStringNotNullOrEmpty(name, "name");
    return SearchBundle.buildEqualToFilter(PROJECT_TYPE_NAME, name);
  }

  /**
   * Build the filter to search for project with type name in the given type name list. Type names
   * must not be null or empty. The content of the input list must be of String type.
   *
   * @return the filter to search for project.
   * @param names The type names list to build filter.
   * @throws IllegalArgumentException if one name in the list is empty string or the list is null or
   *     empty or the content of the list is not of String type.
   */
  @SuppressWarnings("deprecation")
  public static Filter buildTypeNameInFilter(List names) {
    assertStringListValid(names, "names");
    return SearchBundle.buildInFilter(PROJECT_TYPE_NAME, names);
  }

  /**
   * Build the filter to search for project with the given category id. Category id must be greater
   * than zero.
   *
   * @return the filter to search for project.
   * @param categoryId The category id to build filter.
   * @throws IllegalArgumentException if input is less than or equals to zero.
   */
  @SuppressWarnings("deprecation")
  public static Filter buildCategoryIdEqualFilter(long categoryId) {
    Helper.checkNumberPositive(categoryId, "categoryId");
    return SearchBundle.buildEqualToFilter(PROJECT_CATEGORY_ID, categoryId);
  }

  /**
   * Build the filter to search for project with category id in the given category id list. Category
   * ids must be greater than zero. The content of the input list must be of Long type.
   *
   * @return the filter to search for project.
   * @param categoryIds The category id list to build filter.
   * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the
   *     list is null or empty or the content of the list is not of Long type.
   */
  @SuppressWarnings("deprecation")
  public static Filter buildCategoryIdInFilter(List categoryIds) {
    assertLongListValid(categoryIds, "categoryIds");
    return SearchBundle.buildInFilter(PROJECT_CATEGORY_ID, categoryIds);
  }

  /**
   * Build the filter to search for project with the given category name. Category name must not be
   * null or empty string.
   *
   * @return the filter to search for project.
   * @param name The category name to build filter.
   * @throws IllegalArgumentException if input is null or empty.
   */
  @SuppressWarnings("deprecation")
  public static Filter buildCategoryNameEqualFilter(String name) {
    Helper.checkStringNotNullOrEmpty(name, "name");
    return SearchBundle.buildEqualToFilter(PROJECT_CATEGORY_NAME, name);
  }

  /**
   * Build the filter to search for project with category name in the given category name list.
   * Category names must not be null or empty. The content of the input list must be of String type.
   *
   * @return the filter to search for project.
   * @param names The category names list to build filter.
   * @throws IllegalArgumentException if one name in the list is empty string or the list is null or
   *     empty or the content of the list is not of String type.
   */
  @SuppressWarnings("deprecation")
  public static Filter buildCategoryNameInFilter(List names) {
    assertStringListValid(names, "names");
    return SearchBundle.buildInFilter(PROJECT_CATEGORY_NAME, names);
  }

  /**
   * Build the filter to search for project with the given status id. Status id must be greater than
   * zero.
   *
   * @return the filter to search for project.
   * @param statusId The status id to build filter.
   * @throws IllegalArgumentException if input is less than or equals to zero.
   */
  public static Filter buildStatusIdEqualFilter(long statusId) {
    Helper.checkNumberPositive(statusId, "statusId");
    return SearchBundle.buildEqualToFilter(PROJECT_STATUS_ID, statusId);
  }

  /**
   * Build the filter to search for project with status id in the given status id list. Status ids
   * must be greater than zero. The content of the input list must be of Long type.
   *
   * @return the filter to search for project.
   * @param statusIds The status id list to build filter.
   * @throws IllegalArgumentException if one id in the list is less than or equals to zero or the
   *     list is null or empty or the content of the list is not of Long type.
   */
  public static Filter buildStatusIdInFilter(List statusIds) {
    assertLongListValid(statusIds, "statusIds");
    return SearchBundle.buildInFilter(PROJECT_STATUS_ID, statusIds);
  }

  /**
   * Build the filter to search for project with the given status name. Status name must not be null
   * or empty string.
   *
   * @return the filter to search for project.
   * @param name The status name to build filter.
   * @throws IllegalArgumentException if input is null or empty.
   */
  public static Filter buildStatusNameEqualFilter(String name) {
    Helper.checkStringNotNullOrEmpty(name, "name");
    return SearchBundle.buildEqualToFilter(PROJECT_STATUS_NAME, name);
  }

  /**
   * Build the filter to search for project with status name in the given status name list. Status
   * names must not be null or empty. The content of the input list must be of String type.
   *
   * @return the filter to search for project.
   * @param names The status names list to build filter.
   * @throws IllegalArgumentException if one name in the list is empty string or the list is null or
   *     empty or the content of the list is not of String type.
   */
  public static Filter buildStatusNameInFilter(List names) {
    assertStringListValid(names, "names");
    return SearchBundle.buildInFilter(PROJECT_STATUS_NAME, names);
  }

  /**
   * Build the filter to search for project with the given property name. Name must not be null or
   * empty string.
   *
   * @return the filter to search for project.
   * @param name The property name to build filter.
   * @throws IllegalArgumentException if input is null or empty string.
   */
  public static Filter buildProjectPropertyNameEqualFilter(String name) {
    Helper.checkStringNotNullOrEmpty(name, "name");
    return SearchBundle.buildEqualToFilter(PROJECT_PROPERTY_NAME, name);
  }

  /**
   * Build the filter to search for project with the given list of property names. Names must not be
   * null or empty string. The content of the input list must be of String type.
   *
   * @return the filter to search for project.
   * @param names The property name list to build filter.
   * @throws IllegalArgumentException if one name in the list is empty string or the list is null or
   *     empty or the content of the list is not of String type.
   */
  public static Filter buildProjectPropertyNameInFilter(List names) {
    assertStringListValid(names, "names");
    return SearchBundle.buildInFilter(PROJECT_PROPERTY_NAME, names);
  }

  /**
   * Build the filter to search for project with the given property value. Value must not be null or
   * empty string.
   *
   * @return the filter to search for project.
   * @param value The property value to build filter.
   * @throws IllegalArgumentException if input is null or empty string.
   */
  public static Filter buildProjectPropertyValueEqualFilter(String value) {
    Helper.checkStringNotNullOrEmpty(value, "value");
    return SearchBundle.buildEqualToFilter(PROJECT_PROPERTY_VALUE, value);
  }

  /**
   * Build the filter to search for project with the given list of property values. Values must not
   * be null or empty string. The content of the input list must be of String type.
   *
   * @return the filter to search for project.
   * @param values The property name list to build filter.
   * @throws IllegalArgumentException if one value in the list is empty string or the list is null
   *     or empty or the content of the list is not of String type.
   */
  public static Filter buildProjectPropertyValueInFilter(List values) {
    assertStringListValid(values, "values");
    return SearchBundle.buildInFilter(PROJECT_PROPERTY_VALUE, values);
  }

  /**
   * Build the filter to search for project with the given property name/value pair. Name and value
   * must not be null or empty string.
   *
   * @param name he property name to build filter.
   * @param value he property value to build filter.
   * @return he filter to search for project.
   * @throws IllegalArgumentException if any input is null or empty string.
   */
  public static Filter buildProjectPropertyEqualFilter(String name, String value) {
    return buildAndFilter(
        buildProjectPropertyNameEqualFilter(name), buildProjectPropertyValueEqualFilter(value));
  }

  /**
   * Build the filter to search for project with the given TC Direct project id. ID must be greater
   * than zero.
   *
   * @param tcDirectProjectID The TC Direct project ID.
   * @return the filter to search for project.
   * @throws IllegalArgumentException if input is less than or equals to zero.
   * @since 1.2.1
   */
  public static Filter buildTCDirectProjectIDEqualFilter(long tcDirectProjectID) {
    Helper.checkNumberPositive(tcDirectProjectID, "tcDirectProjectID");
    return SearchBundle.buildEqualToFilter(PROJECT_TC_DIRECT_PROJECT_ID, tcDirectProjectID);
  }

  /**
   * Build the AND filter that combine the two input filters.
   *
   * @return the combined filter.
   * @param f1 the first filter.
   * @param f2 the second filter.
   * @throws IllegalArgumentException if any input is null.
   */
  public static Filter buildAndFilter(Filter f1, Filter f2) {
    Helper.checkObjectNotNull(f1, "f1");
    Helper.checkObjectNotNull(f2, "f2");
    return SearchBundle.buildAndFilter(f1, f2);
  }

  /**
   * Build the OR filter that combine the two input filters.
   *
   * @return the combined filter.
   * @param f1 the first filter.
   * @param f2 the second filter.
   * @throws IllegalArgumentException if any input is null.
   */
  public static Filter buildOrFilter(Filter f1, Filter f2) {
    Helper.checkObjectNotNull(f1, "f1");
    Helper.checkObjectNotNull(f2, "f2");
    return SearchBundle.buildOrFilter(f1, f2);
  }

  /**
   * Build the NOT filter that negate input filter.
   *
   * @return the negated filter.
   * @param f1 the filter.
   * @throws IllegalArgumentException if any input is null.
   */
  public static Filter buildNotFilter(Filter f1) {
    Helper.checkObjectNotNull(f1, "f1");
    return SearchBundle.buildNotFilter(f1);
  }

  /**
   * this private method is used to check if long list valid.
   *
   * @param list the list to validate
   * @param name the name of list
   * @throws IllegalArgumentException if one element in the list is less than or equals to zero or
   *     the list is null or empty or the content of the list is not of Long type.
   */
  private static void assertLongListValid(List list, String name) {
    Helper.checkObjectNotNull(list, name);
    Helper.checkNumberPositive(list.size(), name + "'s size");
    // validate each element
    for (Object element : list) {
      if (!(element instanceof Long)) {
        throw new IllegalArgumentException(name + "has a element that is not Long");
      }
      Helper.checkNumberPositive((Long) element, name + "'s element");
    }
  }

  /**
   * this private method is used to check if given string list valid.
   *
   * @param list the list to validate
   * @param name the name of list
   * @throws IllegalArgumentException if one element in the list is empty string or the list is null
   *     or empty or the content of the list is not of String type.
   */
  private static void assertStringListValid(List list, String name) {
    Helper.checkObjectNotNull(list, name);
    Helper.checkNumberPositive(list.size(), name + "'s size");
    // validate each element
    for (Object element : list) {
      if (!(element instanceof String)) {
        throw new IllegalArgumentException(name + "has a element that is not String.");
      }
      Helper.checkStringNotNullOrEmpty((String) element, name + "'s element");
    }
  }
}
