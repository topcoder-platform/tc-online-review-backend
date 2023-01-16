/*
 * Copyright (c) 2006-2012, TopCoder, Inc. All rights reserved
 */
package com.topcoder.onlinereview.component.scorecard;

import java.util.List;

/**
 * Helper class that maintain common database methods used in this component.
 *
 * @author kr00tki
 * @version 1.0.3
 */
final class DBUtils {
  /** Empty constructor. */
  private DBUtils() {}

  /**
   * Checks if the given array is not <code>null</code> or empty and if the values are positive.
   *
   * @param array the array to be checked.
   * @param name the name of the argument. Used for the exception message.
   * @throws IllegalArgumentException if array is <code>null</code> or empty or contains negatize or
   *     zero value.
   */
  public static void checkIdsArray(Long[] array, String name) {
    if (array == null) {
      throw new IllegalArgumentException(name + " cannot be null.");
    }

    if (array.length == 0) {
      throw new IllegalArgumentException("The ids array cannot be empty.");
    }

    for (int i = 0; i < array.length; i++) {
      if (array[i] <= 0) {
        throw new IllegalArgumentException("The ids array contains non-positive value at: " + i);
      }
    }
  }

  /**
   * Converts the give list of Long objects to array.
   *
   * @param ids the ids list.
   * @return the ids array.
   */
  static Long[] listToArray(List<Long> ids) {
    Long[] result = new Long[ids.size()];
    for (int i = 0; i < result.length; i++) {
      result[i] = ((Long) ids.get(i)).longValue();
    }

    return result;
  }
}
