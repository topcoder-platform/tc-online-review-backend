package com.topcoder.onlinereview.util;

import org.springframework.beans.BeanUtils;

import java.util.Map;

public class CommonUtils {
  public static <T, R> R copyProperties(T t, R r) {
    BeanUtils.copyProperties(t, r);
    return r;
  }

  public static Long getLong(Map<String, Object> map, String key) {
    return Long.parseLong(map.get(key).toString());
  }
}
