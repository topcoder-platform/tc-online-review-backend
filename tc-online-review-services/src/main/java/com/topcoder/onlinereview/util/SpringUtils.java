package com.topcoder.onlinereview.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {
  /** A <code>ApplicationContext</code> providing the current application context. */
  private static ApplicationContext applicationContext;

  /** Constructs new <code>SpringContextProvider</code> instance. */
  public SpringUtils() {}

  /**
   * Gets the current application context.
   *
   * @return a <code>ApplicationContext</code> providing the current application context.
   */
  public static ApplicationContext getApplicationContext() {
    return SpringUtils.applicationContext;
  }

  /**
   * Sets the current application context.
   *
   * @param applicationContext a <code>ApplicationContext</code> providing the current application
   *     context.
   * @throws ApplicationContextException if an unexpected error occurs while accessing the JNDI
   *     context or reading configuration for JNDI utility.
   */
  public void setApplicationContext(ApplicationContext applicationContext) {
    SpringUtils.applicationContext = applicationContext;
  }

  public static String getPropertyValue(String propertyName) {
    return applicationContext.getEnvironment().getProperty(propertyName);
  }

  public static JdbcTemplate getTcsJdbcTemplate() {
    return applicationContext.getBean("tcsJdbcTemplate", JdbcTemplate.class);
  }

  public static <T> T getBean(Class<T> clazz) {
    return applicationContext.getBean(clazz);
  }

  public static <T> T getBean(String beanName, Class<T> clazz) {
    return applicationContext.getBean(beanName, clazz);
  }
}
