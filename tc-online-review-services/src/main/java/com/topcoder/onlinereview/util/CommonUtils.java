package com.topcoder.onlinereview.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;
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
    return getT(map, key, v -> v.toString());
  }

  public static Date getDate(Map<String, Object> map, String key) {
    return getT(map, key, v -> (Date) v);
  }

  public static List<Map<String, Object>> executeSql(JdbcTemplate jdbcTemplate, String sql) {
    return jdbcTemplate.queryForList(sql);
  }

  public static List<Map<String, Object>> executeSqlWithParam(
      JdbcTemplate jdbcTemplate, String sql, List<Object> parameters) {
    return jdbcTemplate.queryForList(sql, parameters.toArray());
  }

  public static int executeUpdateSql(
      JdbcTemplate jdbcTemplate, String sql, List<Object> parameters) {
    return jdbcTemplate.update(sql, parameters.toArray());
  }
}
