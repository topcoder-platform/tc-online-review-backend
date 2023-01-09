/*
 * Copyright (C) 2006-2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.review;

/**
 * Helper class for this component.
 *
 * <p>Thread Safety: This class is immutable, and thread safe when entities passed to it are used by
 * the caller in thread safe manner.
 *
 * <p>Changes in 1.2:
 *
 * <ul>
 *   <li>Specified generic parameters for all generic types in the code.
 *   <li>Added deleteEntities() overload for optimized record deletion.
 * </ul>
 *
 * @author urtks, saarixx, TCSDEVELOPER
 * @version 1.2.3
 */
final class Helper {

  /** Private constructor to prevent this class be instantiated. */
  private Helper() {}

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
   * Check if the given string is null or empty (trimmed).
   *
   * @param str the given string to check
   * @param name the name to identify the string.
   * @throws IllegalArgumentException if the given string is null or empty (trimmed).
   */
  static void assertStringNotNullNorEmpty(String str, String name) {
    assertObjectNotNull(str, name);
    if (str.trim().length() == 0) {
      throw new IllegalArgumentException(name + " should not be empty (trimmed).");
    }
  }

  /**
   * Check if the given long value is positive.
   *
   * @param val the given long value to check.
   * @param name the name to identify the long value.
   * @throws IllegalArgumentException if the given long value is negative or zero.
   */
  static void assertLongPositive(long val, String name) {
    if (val <= 0) {
      throw new IllegalArgumentException(name + " [" + val + "] should be positive.");
    }
  }
}
