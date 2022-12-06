/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.deliverable.checker;

import com.topcoder.onlinereview.component.deliverable.Deliverable;
import com.topcoder.onlinereview.component.deliverable.DeliverableChecker;
import com.topcoder.onlinereview.component.deliverable.DeliverableCheckingException;
import com.topcoder.onlinereview.component.grpcclient.deliverable.DeliverableServiceRpc;

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
 * The SpecificationSubmissionDeliverableChecker class subclasses the
 * SingleQuerySqlDeliverableChecker class. The SQL query used checks to see whether the
 * specification is already submitted for phase. If so, it marks the deliverable as completed, using
 * the date of the specification submission.
 *
 * <p>This class is immutable.
 *
 * @author isv
 * @version 1.0.4
 * @since 1.0.2
 */
@Component
public class SpecificationSubmissionDeliverableChecker implements DeliverableChecker {
  @Autowired
  @Qualifier("tcsJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private DeliverableServiceRpc deliverableServiceRpc;

  public void check(Deliverable deliverable) throws DeliverableCheckingException {
    if (deliverable == null) {
      throw new IllegalArgumentException("deliverable cannot be null.");
    }
    try {
      deliverableServiceRpc.specificationSubmissionDeliverableCheck(deliverable);
      /* TODO GRPC
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
      */
    } catch (Exception ex) {
      throw new DeliverableCheckingException("Error occurs while database check operation.", ex);
    }
  }

  /**
   * Gets the SQL query string to select the last modification date for the specification submission
   * for the project phase. Returned query will have 2 placeholders for the resource_id and
   * project_phase_id values.
   *
   * @return The SQL query string to execute.
   */
  private String getSqlQuery() {
    return "SELECT s.modify_date "
        + "FROM submission s "
        + "INNER JOIN upload u ON s.upload_id = u.upload_id "
        + "WHERE s.submission_status_id <> 5 "
        + "AND s.submission_type_id = 2 "
        + "AND u.resource_id = ? "
        + "AND u.project_phase_id = ?";
  }
}
