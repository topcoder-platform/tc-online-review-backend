/*
 * Copyright (c) 2006-2012, TopCoder, Inc. All rights reserved
 */
package com.topcoder.onlinereview.component.scorecard;

import com.topcoder.onlinereview.component.id.IDGenerationException;
import com.topcoder.onlinereview.component.id.IDGenerator;

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
   * Generates the array of unique ids using the given IDGenerator.
   *
   * @param length the number of ids to generate.
   * @param idGenerator the IDGenerator to be used.
   * @return the array of ids.
   * @throws PersistenceException if error occurs while generating the ids.
   */
  public static Long[] generateIdsArray(int length, IDGenerator idGenerator)
      throws PersistenceException {
    Long[] result = new Long[length];

    try {
      for (int i = 0; i < result.length; i++) {
        result[i] = idGenerator.getNextID();
      }
    } catch (IDGenerationException ex) {
      throw new PersistenceException("Error occur while generating new id.", ex);
    }

    return result;
  }

  /**
   * Creates the string in the pattern (?,+) where count is the number of question marks. It is used
   * th build prepared statements with IN condition.
   *
   * @param count number of question marks.
   * @return the string of question marks.
   */
  static String createQuestionMarks(int count) {
    StringBuffer buff = new StringBuffer();
    buff.append("(?");
    for (int i = 1; i < count; i++) {
      buff.append(", ?");
    }

    buff.append(")");
    return buff.toString();
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
