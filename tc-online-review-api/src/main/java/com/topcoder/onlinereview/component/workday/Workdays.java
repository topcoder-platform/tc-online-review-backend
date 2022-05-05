/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.workday;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The DefaultrWorkdays class is the default implementation of the Workdays interface. It has a
 * parameterless constructor to create an empty DefaultWorkdays instance, with default settings, and
 * a constructor with a fileName:String and a fileFormat:String. The second constructor creates a
 * DefaultWorkdays that loads its information from the file with the given fileName, of the given
 * fileFormat. It provides methods for refreshing the configuration from the configuration file and
 * for saving the modified configuration to the configuration file.
 *
 * <p><strong>Thread Safety:</strong> This class is highly mutable and not thread safe.
 *
 * <p><strong>Change log:</strong> Added support of additional configuration strategy with use of
 * Configuration API and Configuration Persistence components.Usage of Configuration Manager for
 * reading configuration is now deprecated. Added generic parameters for Java collection types as a
 * part of code upgrade from Java 1.4 to Java 1.5. Added support of time subtraction (adding of
 * negative amount) in add() method.
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.1
 */
public class Workdays {
  /** Represent the total millisecond of one day. */
  private static final int DAY_TIME = 24 * 60 * 60 * 1000;

  /** Represent 60000 millisecond. */
  private static final int SIX_TEN_THOUSAND = 60000;

  /** Represent total days of one week. */
  private static final int DAYS_WEEK = 7;

  /** Represent the total millisecond of one minute. */
  private static final int MINUTE_TIME = 60 * 1000;

  /** Represent the total hours of one day. */
  private static final int HOURS_DAY = 24;

  /** Represent total minutes of one hour or total second of one minute. */
  private static final int SIXTY = 60;

  /**
   * Represents a sorted set of non-work days, that are not Saturdays or Sundays (these two kind of
   * non-work days are stored in nonWorkSaturdayDays and nonWorkSundayDays sorted sets). Collection
   * instance is initialized during construction and never changed after that. Cannot be null,
   * cannot contain null. Is used in addNonWorkday(), removeNonWorkday(), getNonWorkdays(),
   * clearNonWorkdays(), add(), refresh(), save(), isNonWorkday() and getWorkdaysCount().
   */
  private SortedSet<Date> nonWorkDays = new TreeSet<>();

  /**
   * Represents a sorted set of non-work Saturday days. Collection instance is initialized during
   * construction and never changed after that. Cannot be null, cannot contain null. Is used in
   * addNonWorkday(), removeNonWorkday(), getNonWorkdays(), clearNonWorkdays(), add(), refresh(),
   * save(), isNonWorkday() and getWorkdaysCount().
   */
  private SortedSet<Date> nonWorkSaturdayDays = new TreeSet<>();

  /**
   * Represents a sorted set of non-work Sunday days. Collection instance is initialized during
   * construction and never changed after that. Cannot be null, cannot contain null. Is used in
   * addNonWorkday(), removeNonWorkday(), getNonWorkdays(), clearNonWorkdays(), add(), refresh(),
   * save(), isNonWorkday() and getWorkdaysCount().
   */
  private SortedSet<Date> nonWorkSundayDays = new TreeSet<>();

  /** This boolean field tells whether Saturdays are to be considered as a normal workday or not. */
  private boolean isSaturdayWorkday;

  /** This boolean field tells whether Sundays are to be considered as a normal workday or not. */
  private boolean isSundayWorkday;

  /** Represents the workday start time hours. */
  private int startTimeHours;

  /** Represents the workday start time minutes. */
  private int startTimeMinutes;

  /** Represents the workday end time hours. */
  private int endTimeHours;

  private int endTimeMinutes;

  private Locale locale;
  private DateFormat dateFormat;

  public Workdays(
      SortedSet<Date> nonWorkDays,
      SortedSet<Date> nonWorkSaturdayDays,
      SortedSet<Date> nonWorkSundayDays,
      boolean isSaturdayWorkday,
      boolean isSundayWorkday,
      int startTimeHours,
      int startTimeMinutes,
      int endTimeHours,
      int endTimeMinutes,
      Locale locale,
      DateFormat dateFormat) {
    this.nonWorkDays.addAll(nonWorkDays);
    this.nonWorkSaturdayDays.addAll(nonWorkSaturdayDays);
    this.nonWorkSundayDays.addAll(nonWorkSundayDays);
    this.isSaturdayWorkday = isSaturdayWorkday;
    this.isSundayWorkday = isSundayWorkday;
    this.startTimeHours = startTimeHours;
    this.startTimeMinutes = startTimeMinutes;
    this.endTimeHours = endTimeHours;
    this.endTimeMinutes = endTimeMinutes;
    this.locale = new Locale(locale.getLanguage(), locale.getCountry(), locale.getVariant());
    this.dateFormat = (DateFormat) dateFormat.clone();
  }

  public void save() {}

  /**
   * Adds a non-workday to the list of non-work days.
   *
   * <p>If the date is a Saturday it is added to nonWorkSaturdayDays set, if it is a Sunday it is
   * added to nonWorkSundayDays set and otherwise it is added to nonWorkDays set.
   *
   * <p>This difference is made to ease the calculations for the add(x,x,x) method: there will be no
   * need to check if a non workday is Saturday, if isSaturdayWorkday is false; this check had to be
   * made so a Saturday wouldn't be considered twice as a non-work day.
   *
   * @param nonWorkday the date to add as a non work day
   * @throws NullPointerException if nonWorkDay is null
   */
  public void addNonWorkday(Date nonWorkday) {
    if (nonWorkday == null) {
      throw new NullPointerException("Parameter nonWorkday is null");
    }

    Calendar nonWorkdayCal = Calendar.getInstance(this.locale);
    nonWorkdayCal.setTime(nonWorkday);

    // cut the part of nonWorkday's hour, minute , second and millisecond
    nonWorkdayCal.set(Calendar.HOUR_OF_DAY, 0);
    nonWorkdayCal.set(Calendar.MINUTE, 0);
    nonWorkdayCal.set(Calendar.SECOND, 0);
    nonWorkdayCal.set(Calendar.MILLISECOND, 0);

    if (nonWorkdayCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
      // this nonWorkday is saturday
      this.nonWorkSaturdayDays.add(nonWorkdayCal.getTime());
    } else if (nonWorkdayCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
      // this nonWorkday is sunday
      this.nonWorkSundayDays.add(nonWorkdayCal.getTime());
    } else {
      this.nonWorkDays.add(nonWorkdayCal.getTime());
    }
  }

  /**
   * Removes a non-workday from the list of non-work days.
   *
   * <p>If the date is a Saturday it is removed from nonWorkSaturdayDays set, if it is a Sunday it
   * is removed from nonWorkSundayDays set and otherwise it is removed from nonWorkDays set.
   *
   * @param nonWorkday the date to remove from the list
   * @throws NullPointerException is thrown if nonWorkDay is null
   * @throws IllegalArgumentException is thrown if nonWorkDay does not exist
   */
  public void removeNonWorkday(Date nonWorkday) {
    if (nonWorkday == null) {
      throw new NullPointerException("Parameter nonWorkday is null");
    }

    // cut the part of nonWorkday's hour, minute , second and millisecond
    Calendar nonWorkdayCal = Calendar.getInstance(this.locale);
    nonWorkdayCal.setTime(nonWorkday);
    nonWorkdayCal.set(Calendar.HOUR_OF_DAY, 0);
    nonWorkdayCal.set(Calendar.MINUTE, 0);
    nonWorkdayCal.set(Calendar.SECOND, 0);
    nonWorkdayCal.set(Calendar.MILLISECOND, 0);

    boolean exist = this.nonWorkDays.remove(nonWorkday);

    if (!exist) {
      // not in nonWorkDays set
      exist = this.nonWorkSaturdayDays.remove(nonWorkday);
    }

    if (!exist) {
      // not in nonWorkDays and nonWorkSaturdayDays set
      exist = this.nonWorkSundayDays.remove(nonWorkday);
    }

    if (!exist) {
      // not exist
      throw new IllegalArgumentException("nonWorkday does not exist");
    }
  }

  /**
   * Returns a Set with all non-workdays. The resulting Set is a reunion of the 3 sorted sets for
   * non workdays
   *
   * @return a Set with all non-workdays
   */
  public Set<Date> getNonWorkdays() {
    Set<Date> allNonWorkDays = new TreeSet<Date>();
    allNonWorkDays.addAll(this.nonWorkDays);
    allNonWorkDays.addAll(this.nonWorkSaturdayDays);
    allNonWorkDays.addAll(this.nonWorkSundayDays);
    return allNonWorkDays;
  }

  /** Clears the non-workdays. Emptyies the sorted sets. */
  public void clearNonWorkdays() {
    this.nonWorkDays.clear();
    this.nonWorkSaturdayDays.clear();
    this.nonWorkSundayDays.clear();
  }

  /**
   * Sets whether or not Saturday is to be considered a work day.
   *
   * @param isSaturdayWorkday <code>true</code> if Saturday is to be considered a workday
   */
  public void setSaturdayWorkday(boolean isSaturdayWorkday) {
    this.isSaturdayWorkday = isSaturdayWorkday;
  }

  /**
   * Returns whether or not Saturday is considered a workday.
   *
   * @return <code>true</code> if Saturday is considered a workday.
   */
  public boolean isSaturdayWorkday() {
    return this.isSaturdayWorkday;
  }

  /**
   * Sets whether or not Sunday is to be considered a work day.
   *
   * @param isSundayWorkday <code>true</code> if Sunday is to be considered a workday
   */
  public void setSundayWorkday(boolean isSundayWorkday) {
    this.isSundayWorkday = isSundayWorkday;
  }

  /**
   * Returns whether or not Sunday is considered a workday.
   *
   * @return <code>true</code> if Sunday is considered a workday.
   */
  public boolean isSundayWorkday() {
    return this.isSundayWorkday;
  }

  /**
   * Sets the hours of the workday start time.&nbsp; This is to be in 24 hour mode.
   *
   * @param startTimeHours the hours of the workday start time (24 hour mode)
   * @throws IllegalArgumentException if startTimeHours is not a valid hour
   */
  public void setWorkdayStartTimeHours(int startTimeHours) {
    if ((startTimeHours < 0) || (startTimeHours > HOURS_DAY)) {
      throw new IllegalArgumentException("Parameter startTimeHours is not a valid hour");
    }
    this.startTimeHours = startTimeHours;
    this.timeStateValidation();
  }

  /**
   * Returns the hours of the workday start time, in 24 hour mode.
   *
   * @return the hours of the workday start time
   */
  public int getWorkdayStartTimeHours() {
    return this.startTimeHours;
  }

  /**
   * Sets the minutes of the workday start time.
   *
   * @param startTimeMinutes the minutes of the workday start time
   * @throws IllegalArgumentException if startTimeMinutes is not a valid minute
   */
  public void setWorkdayStartTimeMinutes(int startTimeMinutes) {
    if ((startTimeMinutes < 0) || (startTimeMinutes >= SIXTY)) {
      throw new IllegalArgumentException("Parameter startTimeMinutes is not a valid hour");
    }

    this.startTimeMinutes = startTimeMinutes;
    this.timeStateValidation();
  }

  /**
   * Returns the minutes of the workday start time.
   *
   * @return the minutes of the workday start time
   */
  public int getWorkdayStartTimeMinutes() {
    return this.startTimeMinutes;
  }

  /**
   * Sets the hours of the workday end time.&nbsp; This is to be in 24 hour mode.
   *
   * @param endTimeHours the hours of the workday end time (24 hour mode).
   * @throws IllegalArgumentException if endTimeHours is not a valid hour
   */
  public void setWorkdayEndTimeHours(int endTimeHours) {
    if ((endTimeHours < 0) || (endTimeHours > HOURS_DAY)) {
      throw new IllegalArgumentException("Parameter endTimeHours is not a valid hour");
    }

    this.endTimeHours = endTimeHours;
    this.timeStateValidation();
  }

  /**
   * Returns the hours of the workday end time, in 24 hour mode.
   *
   * @return the hours of the workday end time
   */
  public int getWorkdayEndTimeHours() {
    return this.endTimeHours;
  }

  /**
   * Sets the minutes of the workday end time.
   *
   * @param endTimeMinutes the minutes of the workday end time
   * @throws IllegalArgumentException if endTimeMinutes is not a valid minute
   */
  public void setWorkdayEndTimeMinutes(int endTimeMinutes) {
    if ((endTimeMinutes < 0) || (endTimeMinutes >= SIXTY)) {
      throw new IllegalArgumentException("Parameter endTimeMinutes is not a valid hour");
    }

    this.endTimeMinutes = endTimeMinutes;
    this.timeStateValidation();
  }

  /**
   * Returns the minutes of the workday end time.
   *
   * @return the minutes of the workday end time
   */
  public int getWorkdayEndTimeMinutes() {
    return this.endTimeMinutes;
  }

  /**
   * Method to add a certain amount of time to a Date to calculate end date that it would take to
   * complete.
   *
   * <p>There are there types of unit time, minutes, hours, and days. To implement this method,
   * binary search algorithm can be used.
   *
   * @param startDate the date to perform the addition to
   * @param unitOfTime the unit of time to add (minutes, hours, days)
   * @param amount the amount of time to add
   * @return the Date result of adding the amount of time to the startDate taking into consideration
   *     the workdays definition.
   * @throws NullPointerException if startDate or unitOfTime is null
   * @throws IllegalArgumentException if the start/end time set incorrectly
   */
  public Date add(Date startDate, WorkdaysUnitOfTime unitOfTime, int amount) {
    if (startDate == null) {
      throw new NullPointerException("Parameter startDate is null");
    }

    if (unitOfTime == null) {
      throw new NullPointerException("Parameter unitOfTime is null");
    }

    // validate the start/end time.
    this.timeStateValidation();

    if (amount == 0) {
      return startDate;
    }

    boolean amountSubtracted = amount < 0;
    if (amountSubtracted) {
      amount = -amount;
    }

    Calendar startCal = Calendar.getInstance(this.locale);
    startCal.setTime(startDate);

    // ignore second and millisecond field of the startDate, otherwise, maybe the answer
    // is "2005.01.04 09:00:00 100" insteed of "2005.01.03 17:00:00"
    startCal.set(Calendar.SECOND, 0);
    startCal.set(Calendar.MILLISECOND, 0);

    // get the begin and end time of the startDate
    Calendar dayBegin = (Calendar) startCal.clone();

    // get the begin and end time of the startDate
    Calendar dayEnd = (Calendar) startCal.clone();
    dayBegin.set(Calendar.HOUR_OF_DAY, this.startTimeHours);
    dayBegin.set(Calendar.MINUTE, this.startTimeMinutes);

    dayEnd.set(Calendar.HOUR_OF_DAY, this.endTimeHours);
    dayEnd.set(Calendar.MINUTE, this.endTimeMinutes);

    // time in detail as milliseconds just for convenience
    long workdayInMilliSeconds = (long) this.getWorkdayDurationInMinutes() * MINUTE_TIME;

    long timeInMilliSeconds = (long) this.getAmountInMinutes(unitOfTime, amount) * MINUTE_TIME;

    if (!this.isNonWorkday(startCal)) {
      // the start date is a workday
      if (amountSubtracted) {
        if (startCal.before(dayBegin)) {
          timeInMilliSeconds += workdayInMilliSeconds;
        } else if (startCal.before(dayEnd)) {
          timeInMilliSeconds += (dayEnd.getTime().getTime() - startCal.getTime().getTime());
        }
      } else {
        if (startCal.after(dayEnd)) {
          timeInMilliSeconds += workdayInMilliSeconds;
        } else if (startCal.after(dayBegin)) {
          timeInMilliSeconds += (startCal.getTime().getTime() - dayBegin.getTime().getTime());
        }
      }
    }
    return helpAdd(
        timeInMilliSeconds, workdayInMilliSeconds, startCal, dayBegin, dayEnd, amountSubtracted);
  }

  /**
   * Calculates the amount of time from the unit of time specified by the unitOfTime, to minutes.
   * This method is used by add(x,x,x) method.
   *
   * @param unitOfTime the unit of time the amount is expressed in (minutes, hours, days)
   * @param amount the amount of time
   * @return the amount of time calculated in minutes
   */
  private int getAmountInMinutes(WorkdaysUnitOfTime unitOfTime, int amount) {
    if (unitOfTime.equals(WorkdaysUnitOfTime.MINUTES)) {
      return amount;
    }

    if (unitOfTime.equals(WorkdaysUnitOfTime.HOURS)) {
      return amount * SIXTY;
    }

    return this.getWorkdayDurationInMinutes() * amount;
  }

  /**
   * Calculates the duration of a normal workday in minutes. This method is used by add(x,x,x)
   * method.
   *
   * @return the duration of a normal workday in minutes
   */
  private int getWorkdayDurationInMinutes() {
    return ((this.endTimeHours * SIXTY) + this.endTimeMinutes)
        - (this.startTimeHours * SIXTY)
        - this.startTimeMinutes;
  }

  /**
   * Calculates the number of workdays between the date represented by startCal (inclusive) and the
   * date represented by endCal (exclusive).
   *
   * <p>it makes use of the fact that the non-workdays sets are sorted; for example: int
   * count=nonWorkDays.subSet(startDate,endDate).size().
   *
   * <p>This method uses the nonWorkDays, nonWorkSaturdayDays and nonWorkSundayDays sorted sets and
   * the boolean fields isSaturdayWorkday and isSundayWorkday to perform its calculations.
   *
   * <p>This method is used by the add(x,x,x) method.
   *
   * @param startDay the start date (inclusive)
   * @param endDay the end date (exclusive)
   * @return the number of workdays between [ startDay , endDay )
   */
  private int getWorkdaysCount(Date startDay, Date endDay) {
    // initialize startCal with startDay, used to calcutlate the day of week
    Calendar startCal = Calendar.getInstance();
    startCal.setTime(startDay);

    // total days between startDay and endDay
    int total = (int) ((endDay.getTime() - startDay.getTime() + SIX_TEN_THOUSAND) / (DAY_TIME));

    // non-workdays in nonWorkdays set, exclude Saturdays and Sundays
    int count = this.nonWorkDays.subSet(startDay, endDay).size();

    if (this.isSaturdayWorkday()) {
      // Saturday is workday, add nonWordays of Saturday to the count
      count += this.nonWorkSaturdayDays.subSet(startDay, endDay).size();
    } else {
      // Saturday isn't workday
      // in disToSaturday days, it is Saturday
      int disToSaturday =
          (Calendar.SATURDAY - startCal.get(Calendar.DAY_OF_WEEK) + DAYS_WEEK) % DAYS_WEEK;

      // add Saturdays between startDay and endDay to count
      count += ((total - disToSaturday + (DAYS_WEEK - 1)) / DAYS_WEEK);
    }

    if (this.isSundayWorkday()) {
      // Sunday is workday, add nonWorkdays of Sunday to count
      count += this.nonWorkSundayDays.subSet(startDay, endDay).size();
    } else {
      // sunday isn't workday
      // in disToSunday days, it is Sunday
      int disToSunday =
          (Calendar.SUNDAY - startCal.get(Calendar.DAY_OF_WEEK) + DAYS_WEEK) % DAYS_WEEK;

      // add Sundays between startDay and endDay to count
      count += ((total - disToSunday + DAYS_WEEK - 1) / DAYS_WEEK);
    }

    // workdays is the total days minus the non-workdays
    return total - count;
  }

  /**
   * Checks if the date represented by cal is a non-work day.
   *
   * @param cal the date to be checked
   * @return <code>true</code> if cal represents a non-work day
   */
  private boolean isNonWorkday(Calendar cal) {
    if (!this.isSaturdayWorkday() && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
      return true;
    }

    if (!this.isSundayWorkday() && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
      return true;
    }

    if (this.nonWorkDays.contains(cal.getTime())) {
      return true;
    }

    if (this.isSaturdayWorkday() && this.nonWorkSaturdayDays.contains(cal.getTime())) {
      return true;
    }

    if (this.isSundayWorkday() && this.nonWorkSundayDays.contains(cal.getTime())) {
      return true;
    }

    return false;
  }

  /**
   * Validation for the start/end time. if start time is after end time or start/end hours is 24 but
   * start/end minutes is bigger than 0, IllegalArgumentException throws.
   *
   * @throws IllegalArgumentException if start time is after end time or start/end hours is 24 but
   *     start/end minutes is bigger than 0
   */
  private void timeStateValidation() {
    if (startTimeHours == HOURS_DAY && startTimeMinutes > 0) {
      throw new IllegalArgumentException("The workday start time is after 24:00");
    }
    if (endTimeHours == HOURS_DAY && endTimeMinutes > 0) {
      throw new IllegalArgumentException("The workday end time is after 24:00");
    }
    if (startTimeHours > endTimeHours
        || startTimeHours == endTimeHours && startTimeMinutes >= endTimeMinutes) {
      throw new IllegalArgumentException("The workday start time is after the end time");
    }
  }

  /**
   * Binary searc and return the result. It is used in <code>add()</code>.
   *
   * @param timeInMilliSeconds the timeInMilliSeconds.
   * @param workdayInMilliSeconds the workdayInMilliSeconds.
   * @param startCal the startCal.
   * @param dayBegin the dayBegin.
   * @param dayEnd the dayEnd.
   * @param amountSubtracted true if amount is negative otherwise false.
   * @return the startDay if amountSubtracted is true ohterwise the endDay.
   */
  private Date helpAdd(
      long timeInMilliSeconds,
      long workdayInMilliSeconds,
      Calendar startCal,
      Calendar dayBegin,
      Calendar dayEnd,
      boolean amountSubtracted) {
    // Get total days to add or subtract.
    long daysToAddOrSubtract =
        ((timeInMilliSeconds + workdayInMilliSeconds) - 1) / workdayInMilliSeconds;

    // Get minimal days to add or subtract.
    long min = daysToAddOrSubtract;

    // Get maximal days to add or subtract
    long max =
        (daysToAddOrSubtract * DAYS_WEEK) / (DAYS_WEEK - 2)
            + nonWorkDays.size()
            + nonWorkSaturdayDays.size()
            + nonWorkSundayDays.size()
            + DAYS_WEEK;

    // cut the part of startCal's hour, minute and second
    startCal.set(Calendar.HOUR_OF_DAY, 0);
    startCal.set(Calendar.MINUTE, 0);
    startCal.set(Calendar.SECOND, 0);

    // Create binary search start day
    Date startDay = new Date();
    // Create binary search end day
    Date endDay = new Date();

    if (amountSubtracted) {
      endDay.setTime(startCal.getTime().getTime() + DAY_TIME - SIX_TEN_THOUSAND);
    } else {
      startDay = startCal.getTime();
    }
    // Use binary search.
    for (long mid = (min + max) / 2; min <= max; mid = (min + max) / 2) {
      if (amountSubtracted) {
        startDay.setTime((endDay.getTime() - (mid * DAY_TIME)) + SIX_TEN_THOUSAND);
      } else {
        endDay.setTime((startDay.getTime() + (mid * DAY_TIME)) - SIX_TEN_THOUSAND);
      }

      int workdaysCount = getWorkdaysCount(startDay, endDay);

      if (workdaysCount >= daysToAddOrSubtract) {
        max = mid - 1;
      } else {
        min = mid + 1;
      }
    }

    if (amountSubtracted) {
      long disFromEnd =
          (((timeInMilliSeconds + workdayInMilliSeconds) - 1) % workdayInMilliSeconds) + 1;
      startDay.setTime(dayEnd.getTime().getTime() - (max * DAY_TIME) - disFromEnd);
      return startDay;
    } else {
      long disToBegin =
          (((timeInMilliSeconds + workdayInMilliSeconds) - 1) % workdayInMilliSeconds) + 1;
      endDay.setTime(dayBegin.getTime().getTime() + (max * DAY_TIME) + disToBegin);
      return endDay;
    }
  }
}
