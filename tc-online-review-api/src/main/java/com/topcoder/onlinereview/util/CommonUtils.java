package com.topcoder.onlinereview.util;

import org.hibernate.query.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class CommonUtils {
  public static <T, R> R copyProperties(T t, R r) {
    BeanUtils.copyProperties(t, r);
    return r;
  }

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
    return getT(map, key, v -> (Boolean) v);
  }

  public static String getString(Map<String, Object> map, String key) {
    return getT(map, key, v -> v.toString());
  }

  public static Date getDate(Map<String, Object> map, String key) {
    return getT(map, key, v -> (Date) v);
  }

  public static List<Map<String, Object>> executeSql(EntityManager entityManager, String sql) {
    var nativeQuery = entityManager.createNativeQuery(sql).unwrap(Query.class);
    nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
    return nativeQuery.getResultList();
  }

  public static List<Map<String, Object>> executeSqlWithParam(
      EntityManager entityManager, String sql, List<Object> parameters) {
    var nativeQuery = entityManager.createNativeQuery(sql).unwrap(Query.class);
    for (int i = 0; i < parameters.size(); i++) {
      nativeQuery.setParameter(i + 1, parameters.get(i));
    }
    nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
    return nativeQuery.getResultList();
  }

  public static String getMessageText(MessageSource messages, String code) {
    return messages.getMessage(code, null, Locale.ENGLISH);
  }

  public static String getMessageText(MessageSource messages, String code, Object[] args) {
    return messages.getMessage(code, args, Locale.ENGLISH);
  }
}
