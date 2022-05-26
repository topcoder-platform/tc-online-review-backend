/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.onlinereview.component.deliverable.checker;

import com.topcoder.onlinereview.component.deliverable.Deliverable;
import com.topcoder.onlinereview.component.deliverable.DeliverableChecker;
import com.topcoder.onlinereview.component.deliverable.DeliverableCheckingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.component.util.CommonUtils.getDate;

/**
 * The FinalFixesDeliverableChecker class subclasses the SingleQuerySqlDeliverableChecker class. The
 * SQL query that it executes checks whether a submitter has uploaded the final fixes for the
 * component. If so, it marks the deliverable as completed, using the date of the last final fixes
 * upload.
 *
 * <p>This class is immutable.
 *
 * @author aubergineanode
 * @author kr00tki
 * @version 1.0.4
 */
@Component
public class FinalFixesDeliverableChecker implements DeliverableChecker {
  @Autowired
  @Qualifier("tcsJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  public void check(Deliverable deliverable) throws DeliverableCheckingException {
    if (deliverable == null) {
      throw new IllegalArgumentException("deliverable cannot be null.");
    }
    try {
      // execute it
      List<Map<String, Object>> rs =
          executeSqlWithParam(
              jdbcTemplate,
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
   * Gets the SQL query string to select the last final fixes upload date. The returned query will
   * have 2 placeholders for the project_phase_id and resource_id values.
   *
   * @return The SQL query string to execute.
   */
  protected String getSqlQuery() {
    return "SELECT MAX(upload.modify_date) as modify_date FROM upload "
        + "INNER JOIN upload_type_lu ON upload.upload_type_id = upload_type_lu.upload_type_id "
        + "WHERE upload_type_lu.name = 'Final Fix' "
        + "AND upload.project_phase_id = ? AND upload.resource_id = ?";
  }
}
