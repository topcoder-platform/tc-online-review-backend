/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.onlinereview.component.deliverable.checker;

import com.topcoder.onlinereview.component.deliverable.Deliverable;
import com.topcoder.onlinereview.component.deliverable.DeliverableChecker;
import com.topcoder.onlinereview.component.deliverable.DeliverableCheckingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.util.CommonUtils.getDate;

/**
 * The TestCasesDeliverableChecker class subclasses the SingleQuerySqlDeliverableChecker class. The
 * SQL query that it executes checks whether a given resource (in this case a reviewer) has uploaded
 * a test suite. If so, it marks the deliverable as completed, using the date of the last test suite
 * upload.
 *
 * <p>This class is immutable.
 *
 * @author aubergineanode
 * @author kr00tki
 * @version 1.0
 */
@Component
public class TestCasesDeliverableChecker implements DeliverableChecker {
  @Value("{deliverable.persistence.entity-manager-name}")
  private String entityManagerName;

  @Autowired private Map<String, EntityManager> entityManagerMap;
  private EntityManager entityManager;

  @PostConstruct
  public void postRun() {
    entityManager = entityManagerMap.get(entityManagerName);
  }

  public void check(Deliverable deliverable) throws DeliverableCheckingException {
    if (deliverable == null) {
      throw new IllegalArgumentException("deliverable cannot be null.");
    }
    try {
      // execute it
      var rs =
          executeSqlWithParam(
              entityManager,
              getSqlQuery(),
              newArrayList(deliverable.getPhase(), deliverable.getResource()));
      if (!rs.isEmpty()) {
        if (rs.get(0).get("modify_date") != null) {
          deliverable.setCompletionDate(getDate(rs.get(0), "modify_date"));
        }
      }
    } catch (Exception ex) {
      throw new DeliverableCheckingException("Error occurs while database check operation.", ex);
    }
  }

  /**
   * Gets the SQL query string to select the last modification date for a test case upload. Returned
   * query will have two placeholders for project_id and resource_id values.
   *
   * @return The SQL query string to execute.
   */
  protected String getSqlQuery() {
    return "SELECT MAX(upload.modify_date) as modify_date FROM upload "
        + "INNER JOIN upload_type_lu ON upload.upload_type_id = upload_type_lu.upload_type_id "
        + "INNER JOIN upload_status_lu ON upload.upload_status_id = upload_status_lu.upload_status_id "
        + "WHERE upload_type_lu.name = 'Test Case' AND upload_status_lu.name = 'Active' "
        + "AND upload.resource_id = ?";
  }
}
