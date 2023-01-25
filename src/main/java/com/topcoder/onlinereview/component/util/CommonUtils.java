package com.topcoder.onlinereview.component.util;

import org.apache.commons.lang3.StringUtils;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public class CommonUtils {
  private static <T> T getT(Map<String, Object> map, String key, Function<Object, T> function) {
    if (map.get(key) == null) {
      return null;
    }
    return function.apply(map.get(key));
  }

  public static Long getLong(Map<String, Object> map, String key) {
    return getT(map, key, v -> Long.parseLong(v.toString()));
  }

  public static Integer getInt(Map<String, Object> map, String key) {
    return getT(map, key, v -> Integer.parseInt(v.toString()));
  }

  public static Double getDouble(Map<String, Object> map, String key) {
    return getT(map, key, v -> Double.parseDouble(v.toString()));
  }

  public static Float getFloat(Map<String, Object> map, String key) {
    return getT(map, key, v -> Float.parseFloat(v.toString()));
  }

  public static Boolean getBoolean(Map<String, Object> map, String key) {
    return getT(
        map,
        key,
        v -> {
          if (v instanceof Boolean) {
            return (Boolean) v;
          } else if (StringUtils.isNumeric(v.toString())) {
            return Integer.parseInt(v.toString()) != 0;
          } else {
            return "true".equalsIgnoreCase(v.toString());
          }
        });
  }

  public static String getString(Map<String, Object> map, String key) {
    return getT(
        map,
        key,
        v -> {
          if (v instanceof byte[]) {
            return new String((byte[]) v);
          }
          return v.toString();
        });
  }

  public static Date getDate(Map<String, Object> map, String key) {
    return getT(map, key, v -> (Date) v);
  }
}
