package com.topcoder.onlinereview.util;

import org.hibernate.query.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.BeanUtils;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CommonUtils {
  public static <T, R> R copyProperties(T t, R r) {
    BeanUtils.copyProperties(t, r);
    return r;
  }

  public static Long getLong(Map<String, Object> map, String key) {
    return Long.parseLong(map.get(key).toString());
  }

  public static Integer getInt(Map<String, Object> map, String key) {
    return Integer.parseInt(map.get(key).toString());
  }

  public static Double getDouble(Map<String, Object> map, String key) {
    return Double.parseDouble(map.get(key).toString());
  }

  public static Float getFloat(Map<String, Object> map, String key) {
    return Float.parseFloat(map.get(key).toString());
  }

  public static Boolean getBoolean(Map<String, Object> map, String key) {
    return (Boolean) map.get(key);
  }

  public static String getString(Map<String, Object> map, String key) {
    return map.get(key).toString();
  }

  public static Date getDate(Map<String, Object> map, String key) {
    return (Date) map.get(key);
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
}
