/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.workday;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * This is the default implementation of WorkdaysFactory. It creates instances of DefaultWorkdays
 * type. It loads the configuration file under the constant namespace. If no error occurs and the
 * namespace contains 2 properties: file_name and file_format, it will create a DefaultWorkdays
 * using the constructor with 2 string arguments. If the file_format is missing, the
 * DefaultWorkdays.XML_FILE_FORMAT is used.
 *
 * <p><strong>Thread Safety:</strong> This class is immutable and thread safe.
 *
 * <p><strong> Change log:</strong> Added support of additional configuration strategy with use of
 * Configuration API and Configuration Persistence components.Usage of Configuration Manager for
 * reading configuration is now deprecated.
 *
 * <p>The sample configuration file.
 *
 * <p><em>file_name=test_files/workdays_unittest.properties</em>
 *
 * <p><em>ile_name=test_files/workdays_unittest.properties</em>
 *
 * <p>Sample usage.
 *
 * <p>WorkdaysFactory factory = new DefaultWorkdaysFactory(true); Workdays w1 =
 * factory.createWorkdaysInstance();
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.1
 */
@Component
@PropertySource("classpath:applicationConfig.properties")
public class WorkdaysFactory {
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
  private SortedSet<Date> nonWorkSundayDays = new TreeSet<Date>();

  /** This boolean field tells whether Saturdays are to be considered as a normal workday or not. */
  @Value("${workday.is_saturday_workday:false}")
  private boolean isSaturdayWorkday;

  /** This boolean field tells whether Sundays are to be considered as a normal workday or not. */
  @Value("${workday.is_sunday_workday:false}")
  private boolean isSundayWorkday;

  /** Represents the workday start time hours. */
  @Value("${workday.start_time_hours:9}")
  private int startTimeHours;

  /** Represents the workday start time minutes. */
  @Value("${workday.start_time_minutes:0}")
  private int startTimeMinutes;

  /** Represents the workday end time hours. */
  @Value("${workday.end_time_hours:17}")
  private int endTimeHours;

  @Value("${workday.end_time_minutes:0}")
  private int endTimeMinutes;

  @Value("${workday.locale.language:#{null}}")
  private String localeLanguage;

  @Value("${workday.locale.country:#{null}}")
  private String localeCountry;

  @Value("${workday.locale.variant:#{null}}")
  private String localeVariant;

  @Value("${workday.date_style:#{null}}")
  private String dateStyle;

  @Value("${workday.non_workdays:#{null}}")
  private String nonWorkdays;

  private Locale locale = Locale.getDefault();
  private DateFormat dateFormat = DateFormat.getDateInstance();

  @PostConstruct
  public void postRun() {
    // initialize all the properties by configManager
    if (localeLanguage != null && localeCountry != null && localeVariant != null) {
      this.locale = new Locale(localeLanguage, localeCountry, localeVariant);
    } else if (localeLanguage != null && localeCountry != null) {
      // only language and country are specified
      this.locale = new Locale(localeLanguage, localeCountry);
    }
    if (dateStyle != null) {
      if (dateStyle.equalsIgnoreCase("SHORT")) {
        this.dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, this.locale);
      } else if (dateStyle.equalsIgnoreCase("MEDIUM")) {
        this.dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, this.locale);
      } else if (dateStyle.equalsIgnoreCase("LONG")) {
        this.dateFormat = DateFormat.getDateInstance(DateFormat.LONG, this.locale);
      } else if (dateStyle.equalsIgnoreCase("FULL")) {
        this.dateFormat = DateFormat.getDateInstance(DateFormat.FULL, this.locale);
      } else {
        this.dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, this.locale);
      }
    } else {
      this.dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, this.locale);
    }
    // load all non workdays
    if (nonWorkdays != null) {
      Stream.of(nonWorkdays.split(";"))
          .forEach(
              d -> {
                try {
                  addNonWorkday(dateFormat.parse(d.trim()));
                } catch (ParseException e) {
                }
              });
    }
  }

  private void addNonWorkday(Date nonWorkday) {
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
   * Creates a DefaultWorkdays. It loads the configuration file under the constant namespace. If no
   * error occurs and the namespace contains 2 properties: file_name and file_format, it will create
   * a DefaultWorkdays using the constructor with 2 string arguments. If the file_format is missing,
   * the DefaultWorkdays.XML_FILE_FORMAT is used.
   *
   * <p>If an error loading the file occurs, or the namespace doesn't contain a file name, or the
   * DefaultWorkdays throw an exception, a DefaultWorkdays constructed with the constructor with no
   * arguments is returned.
   *
   * @return a DefaultWorkdays instance.
   */
  public Workdays createWorkdaysInstance() {
    return new Workdays(
        nonWorkDays,
        nonWorkSaturdayDays,
        nonWorkSundayDays,
        isSaturdayWorkday,
        isSundayWorkday,
        startTimeHours,
        startTimeMinutes,
        endTimeHours,
        endTimeMinutes,
        locale,
        dateFormat);
  }
}
