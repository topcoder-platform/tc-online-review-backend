package com.topcoder.onlinereview.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class EntityManagerConfiguration {

  @Autowired
  @Qualifier("idsEntityManager")
  private EntityManager idsEntityManager;

  @Autowired
  @Qualifier("dsEntityManager")
  private EntityManager dsEntityManager;

  @Autowired
  @Qualifier("oltpEntityManager")
  private EntityManager oltpEntityManager;

  @Bean
  public Map<String, EntityManager> entityManagerMap() {
    var entityManagerMap = new HashMap<String, EntityManager>();
    entityManagerMap.put("idsEntityManager", idsEntityManager);
    entityManagerMap.put("dsEntityManager", dsEntityManager);
    entityManagerMap.put("oltpEntityManager", oltpEntityManager);
    return entityManagerMap;
  }
}
