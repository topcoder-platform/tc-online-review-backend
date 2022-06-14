package com.topcoder.onlinereview.component.project.payment;

import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class Helper {
  private static final String DEFAULT_LOGDATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static final String MESSAGE_ENTRANCE = "%1$s INFO The method [%2$s] began. [%3$s]";
  private static final String MESSAGE_EXIT = "%1$s INFO The method [%2$s] ended. [%3$s]";
  private static final String MESSAGE_ERROR = "%1$s ERROR Error in method [%2$s], details: %3$s";

  public static void logEntrance(
      Logger log, String signature, String[] paramNames, Object[] paramValues) {
    DateFormat format = new SimpleDateFormat(DEFAULT_LOGDATE_FORMAT);

    StringBuilder sb = new StringBuilder();
    // Log parameters
    sb.append("Input {");
    for (int i = 0; i < paramNames.length; i++) {
      sb.append(paramNames[i]).append(":").append(paramValues[i].toString());
    }
    sb.append("}");

    log.debug(String.format(MESSAGE_ENTRANCE, format.format(new Date()), signature, sb.toString()));
  }

  public static void logExit(Logger log, String signature, Object[] value) {
    DateFormat format = new SimpleDateFormat(DEFAULT_LOGDATE_FORMAT);

    // Log return value
    String output = "Output: " + value == null || value.length == 0 ? "" : value[0].toString();

    log.debug(String.format(MESSAGE_EXIT, format.format(new Date()), signature, output));
  }

  public static <T extends Throwable> T logException(Logger log, String signature, T e) {
    // Log exception at ERROR level
    DateFormat format = new SimpleDateFormat(DEFAULT_LOGDATE_FORMAT);
    log.error(String.format(MESSAGE_ERROR, format.format(new Date()), signature, getStackTrace(e)));

    return e;
  }

  private static String getStackTrace(Throwable cause) {
    OutputStream out = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(out);

    // Print a new line
    ps.println();
    cause.printStackTrace(ps);

    return out.toString();
  }

  public static void checkPositive(long value, String name) {
    if (value <= 0) {
      throw new IllegalArgumentException(getParamValueMessage(name, " should be positive."));
    }
  }

  public static void checkNotNull(Object value, String name) {
    if (value == null) {
      throw new IllegalArgumentException(getParamValueMessage(name, " should not be null."));
    }
  }

  public static void checkNotNegative(double value, String name) {
    if (value < 0) {
      throw new IllegalArgumentException(getParamValueMessage(name, " should be not negative."));
    }
  }

  private static String getParamValueMessage(String paramName, Object... values) {
    StringBuilder sb = new StringBuilder();

    sb.append("The '").append(paramName).append("' parameter");

    for (Object value : values) {
      sb.append(value);
    }
    return sb.toString();
  }

  public static void checkNotNullNorEmpty(String value, String name) {
    checkNotNull(value, name);
    checkNotEmptyString(value, name);
  }

  private static void checkNotEmptyString(String value, String name) {
    if (value.length() == 0) {
      throw new IllegalArgumentException(getParamValueMessage(name, " should not be empty (without trimming)."));
    }
  }

  public static void checkNotNullElements(Collection<?> collection, String name) {
    if ((collection != null) && containNull(collection)) {
      throw new IllegalArgumentException(getParamValueMessage(name, " should not contain null."));
    }
  }

  public static void checkNotNullNorEmpty(Collection<?> collection, String name) {
    checkNotNull(collection, name);
    checkNotEmptyCollection(collection, name);
  }

  private static void checkNotEmptyCollection(Collection<?> collection, String name) {
    if (collection.isEmpty()) {
      throw new IllegalArgumentException(getParamValueMessage(name, " should not be empty."));
    }
  }

  private static boolean containNull(Collection<?> collection) {
    for (Object obj: collection) {
      if (obj == null) {
        return true;
      }
    }
    return false;
  }
}
