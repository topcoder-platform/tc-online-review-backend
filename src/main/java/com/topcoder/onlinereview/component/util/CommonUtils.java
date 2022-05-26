package com.topcoder.onlinereview.component.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
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

  public static SqlRowSet queryForRowSet(JdbcTemplate jdbcTemplate, String sql) {
    log.info("execute sql '{}'", sql);
    return jdbcTemplate.queryForRowSet(sql);
  }

  public static List<Map<String, Object>> executeSql(JdbcTemplate jdbcTemplate, String sql) {
    log.info("execute sql '{}'", sql);
    return jdbcTemplate.queryForList(sql);
  }

  public static List<Map<String, Object>> executeSqlWithParam(
      JdbcTemplate jdbcTemplate, String sql, List<Object> parameters) {
    log.info(
        "execute sql '{}', with params '{}'",
        sql,
        parameters.stream()
            .map(o -> o == null ? "null" : o.toString())
            .collect(Collectors.joining(",")));
    return jdbcTemplate.queryForList(sql, parameters.toArray());
  }

  public static int executeUpdateSql(
      JdbcTemplate jdbcTemplate, String sql, List<Object> parameters) {
    log.info(
        "execute update sql '{}', with params '{}'",
        sql,
        parameters.stream()
            .map(o -> o == null ? "null" : o.toString())
            .collect(Collectors.joining(",")));
    return jdbcTemplate.update(sql, parameters.toArray());
  }

  public static long executeUpdateSqlWithReturn(
      JdbcTemplate jdbcTemplate, String sql, List<Object> parameters) {
    log.info(
        "execute insert sql '{}' with params '{}'",
        sql,
        parameters.stream()
            .map(o -> o == null ? "null" : o.toString())
            .collect(Collectors.joining(",")));
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
          for (int i = 0; i < parameters.size(); i++) {
            ps.setObject(i + 1, parameters.get(i));
          }
          return ps;
        },
        keyHolder);
    return keyHolder.getKey().longValue();
  }
}
