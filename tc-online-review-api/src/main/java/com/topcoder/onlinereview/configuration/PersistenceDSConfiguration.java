package com.topcoder.onlinereview.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.topcoder.onlinereview.repository.ds",
    entityManagerFactoryRef = "dsEntityManager",
    transactionManagerRef = "dsTransactionManager")
public class PersistenceDSConfiguration {
  @Bean
  @ConfigurationProperties(prefix = "spring.ds-datasource")
  public DataSource idsDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean dsEntityManager() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(idsDataSource());
    em.setPackagesToScan(new String[] {"com.topcoder.onlinereview.entity.ds"});
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    return em;
  }

  @Bean
  public PlatformTransactionManager dsTransactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(dsEntityManager().getObject());
    return transactionManager;
  }
}
