package com.topcoder.onlinereview.component.shared.dataaccess;

/**
 * A class to store the constants used for data access.
 *
 * @author Greg Paul
 * @version $Revision$ $Date$
 */
public class DataAccessConstants {
  public static String QUERY_KEY = "query";
  public static String COMMAND = "c";
  public static String NUMBER_RECORDS = "nr";
  public static String NUMBER_PAGE = "np";
  public static String START_RANK = "sr";
  public static String END_RANK = "er";
  public static String SORT_COLUMN = "sc";
  public static String SORT_QUERY = "sq";
  public static String SORT_DIRECTION = "sd";
  public static int INTEGER_INPUT = 1001;
  public static int DECIMAL_INPUT = 1002;
  public static int DATE_INPUT = 1003;
  public static int SORT_DIRECTION_INPUT = 1004;
  public static int STRING_INPUT = 1005;
  public static String INPUT_DELIMITER = "@";
  public static String SPECIAL_DEFAULT_MARKER = "$";
  public static String DATE_FORMAT = "yyyy-MM-dd";
  public static int DEFAULT_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 3;
}
