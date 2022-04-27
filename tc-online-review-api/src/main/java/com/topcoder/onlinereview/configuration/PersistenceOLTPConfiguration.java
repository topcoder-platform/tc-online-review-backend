package com.topcoder.onlinereview.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.topcoder.onlinereview.repository.oltp",
    entityManagerFactoryRef = "oltpEntityManager",
    transactionManagerRef = "oltpTransactionManager")
public class PersistenceOLTPConfiguration {
  @Primary
  @Bean
  @ConfigurationProperties(prefix = "spring.oltp-datasource")
  public DataSource oltpDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean
  public LocalContainerEntityManagerFactoryBean oltpEntityManager() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(oltpDataSource());
    em.setPackagesToScan(new String[] {"com.topcoder.onlinereview.entity.oltp"});
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    return em;
  }

  @Primary
  @Bean
  public PlatformTransactionManager oltpTransactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(oltpEntityManager().getObject());
    return transactionManager;
  }
}
