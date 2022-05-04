/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.onlinereview.component.deliverable;

import java.util.Date;

/**
 * Helper class for the package com.topcoder.management.deliverable.persistence.sql., including the
 * methods to validate arguments and the methods to access database.
 *
 * @author urtks
 * @version 1.0
 */
class Helper {
  /** Private constructor to prevent this class be instantiated. */
  private Helper() {}

  /**
   * Load data items from the data row and fill the fields of an AuditedDeliverableStructure
   * instance.
   *
   * @param entity the AuditedDeliverableStructure instance whose fields will be filled
   * @param row the data row
   * @param startIndex the start index to read from
   * @return the start index of the left data items that haven't been read
   */
  static int loadEntityFieldsSequentially(
      AuditedDeliverableStructure entity, Object[] row, int startIndex) {
    entity.setId(((Long) row[startIndex++]).longValue());
    entity.setCreationUser((String) row[startIndex++]);
    entity.setCreationTimestamp((Date) row[startIndex++]);
    entity.setModificationUser((String) row[startIndex++]);
    entity.setModificationTimestamp((Date) row[startIndex++]);

    return startIndex;
  }

  /**
   * Load data items from the data row and fill the fields of an NamedDeliverableStructure instance.
   *
   * @param namedEntity the NamedDeliverableStructure instance whose fields will be filled
   * @param row the data row
   * @param startIndex the start index to read from
   * @return the start index of the left data items that haven't been read
   */
  static int loadNamedEntityFieldsSequentially(
      NamedDeliverableStructure namedEntity, Object[] row, int startIndex) {
    startIndex = loadEntityFieldsSequentially(namedEntity, row, startIndex);

    namedEntity.setName((String) row[startIndex++]);
    namedEntity.setDescription((String) row[startIndex++]);

    return startIndex;
  }

  /**
   * Check if the given object is null.
   *
   * @param obj the given object to check
   * @param name the name to identify the object.
   * @throws IllegalArgumentException if the given object is null
   */
  static void assertObjectNotNull(Object obj, String name) {
    if (obj == null) {
      throw new IllegalArgumentException(name + " should not be null.");
    }
  }

  /**
   * Check if the given array is null or contains null.
   *
   * @param array the given array to check
   * @param name the name to identify the array.
   * @throws IllegalArgumentException if the given array is null or contains null.
   */
  static void assertArrayNotNullNorHasNull(Object[] array, String name) {
    assertObjectNotNull(array, name);

    for (int i = 0; i < array.length; ++i) {
      if (array[i] == null) {
        throw new IllegalArgumentException(name + " should not contain null.");
      }
    }
  }

  /**
   * Check if the lengths of the two arrays are equal.
   *
   * @param array the given array to check
   * @param array1 the given array to check
   * @param name the name to identify array.
   * @param name1 the name to identify array1.
   * @throws IllegalArgumentException if length of array is different from that of array1.
   */
  static void assertArrayLengthEqual(Object[] array, String name, Object[] array1, String name1) {
    if (array.length != array1.length) {
      throw new IllegalArgumentException(
          "The length of " + name + " should be the same as that of " + name1);
    }
  }

  /**
   * Check if the given string is empty (trimmed).
   *
   * @param str the given string to check
   * @param name the name to identify the string.
   * @throws IllegalArgumentException if the given string is not null and empty (trimmed).
   */
  static void assertStringNotEmpty(String str, String name) {
    if ((str != null) && (str.trim().length() == 0)) {
      throw new IllegalArgumentException(name + " should not be empty (trimmed).");
    }
  }

  /**
   * Check if the given string is null or empty (trimmed).
   *
   * @param str the given string to check
   * @param name the name to identify the string.
   * @throws IllegalArgumentException if the given string is null or empty (trimmed).
   */
  static void assertStringNotNullNorEmpty(String str, String name) {
    assertObjectNotNull(str, name);
    assertStringNotEmpty(str, name);
  }

  /**
   * Check if the given long value is UNSET_ID.
   *
   * @param value the given long value to check.
   * @param name the name to identify the long value.
   * @throws IllegalArgumentException if the given long value is UNSET_ID.
   */
  static void assertIdNotUnset(long value, String name) {
    if (value == AuditedDeliverableStructure.UNSET_ID) {
      throw new IllegalArgumentException(name + " [" + value + "] should not be UNSET_ID.");
    }
  }

  /**
   * Check if the given long array is null or contains non positive values.
   *
   * @param values the long array to check
   * @param name the name to identify the long array.
   * @throws IllegalArgumentException if the given long array is null or contains negative or zero
   *     values.
   */
  static void assertLongArrayNotNullAndOnlyHasPositive(long[] values, String name) {
    Helper.assertObjectNotNull(values, name);

    for (int i = 0; i < values.length; ++i) {
      if (values[i] <= 0) {
        throw new IllegalArgumentException(name + " should only contain positive values.");
      }
    }
  }

  /**
   * Check if the given object is an instance of the expected class.
   *
   * @param obj the given object to check.
   * @param expectedType the expected class.
   * @param name the name to identify the object.
   * @throws IllegalArgumentException if the given object is null or is not an instance of the
   *     expected class.
   */
  static void assertObjectNullOrIsInstance(Object obj, Class<?> expectedType, String name) {
    if ((obj != null) && !expectedType.isInstance(obj)) {
      throw new IllegalArgumentException(
          name
              + " of type ["
              + obj.getClass().getName()
              + "] should be an instance of "
              + expectedType.getName());
    }
  }

  /**
   * Check if the given AuditedDeliverableStructure instance is valid to persist.
   *
   * @param entity the given AuditedDeliverableStructure instance to check.
   * @param name the name to identify the instance.
   * @throws IllegalArgumentException if the given instance is null or isValidToPersist returns
   *     false.
   */
  static void assertEntityNotNullAndValidToPersist(
      AuditedDeliverableStructure entity, String name) {
    Helper.assertObjectNotNull(entity, name);

    if (!entity.isValidToPersist()) {
      throw new IllegalArgumentException("The entity [" + name + "] is not valid to persist.");
    }
  }
}
