/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.workday;

/**
 * Instances of this class represent units of time. It has a private constructor, so only the three
 * public static final instances can be used: MINUTES, HOURS and DAYS.
 *
 * <p><strong>Thread Safety:</strong> This class is immutable and thread safe.
 *
 * <p><strong> Change log:</strong> Trivial code fixes: Made name and value attributes final.
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.1
 */
public enum WorkdaysUnitOfTime {
  /**
   * This represents minutes as the unit of time to add to a given date. Is initialized during class
   * loading and never changed after that. Cannot be null.
   */
  MINUTES("MINUTES", 0),

  /**
   * This represents hours as the unit of time to add to a given date. Is initialized during class
   * loading and never changed after that. Cannot be null.
   */
  HOURS("HOURS", 1),

  /**
   * This represents days as the unit of time to add to a given date. Is initialized during class
   * loading and never changed after that. Cannot be null.
   */
  DAYS("DAYS", 2);

  /**
   * Name of this unit of time. It is used by toString() for a descriptive display of an instance of
   * this class. Is initialized in the constructor and never changed after that. Cannot be
   * null/empty.
   */
  private final String name;

  /**
   * Value of this unit of time. It is used for ordering the unit of time. Is initialized in the
   * constructor and never changed after that. Cannot be negative. Has a getter.
   */
  private final int value;

  /**
   * Private constructor to create a WorkdaysUnitOfTime with the given name and the given value.
   *
   * @param name name for the unit of time
   * @param value value for the unit of time
   * @throws NullPointerException when parameter name is null
   * @throws IllegalArgumentException when parameter name is empty
   */
  WorkdaysUnitOfTime(String name, int value) {
    if (name == null) {
      throw new NullPointerException("Parameter name is null");
    }

    if (name.trim().length() == 0) {
      throw new IllegalArgumentException("parameter name is empty");
    }

    this.name = name;
    this.value = value;
  }

  /**
   * Getter for the value of a WorkdaysUnitOfTime instance. The value is used for ordering.
   *
   * @return value for the unit of time
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Returns a descriptive string of an instance of WorkdaysUnitOfTime class.
   *
   * @return name of this unit of time
   */
  public String toString() {
    return this.name;
  }
}
