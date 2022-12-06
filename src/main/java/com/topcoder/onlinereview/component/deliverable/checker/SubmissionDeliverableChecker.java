/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
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
 * The SubmissionDeliverableChecker class subclasses the SingleQuerySqlDeliverableChecker class. The
 * SQL query that it executes checks whether a given resource (in this case a designer/developer)
 * has uploaded a submission for the project. If so, it marks the deliverable complete, using the
 * date of the last upload.
 *
 * <p>This class is immutable.
 *
 * <p>Version 1.0.1 (Milestone Support Assembly 1.0) Change notes:
 *
 * <ol>
 *   <li>Updated the checker to distinguish submission types.
 * </ol>
 *
 * <p>Version 1.0.2 (Online Review Replatforming Release 2 ) Change notes:
 *
 * <ol>
 *   <li>Update sql statement to adapt for the new database schema. The upload_submission table is
 *       dropped.
 * </ol>
 *
 * @author aubergineanode
 * @author kr00tki, TCSDEVELOPER
 * @version 1.0.2
 */
@Component
public class SubmissionDeliverableChecker implements DeliverableChecker {
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
      deliverableServiceRpc.submissionDeliverableCheck(deliverable);
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
   * Gets the SQL query string to select the last date for the submission for the given project /
   * resource (submitter). The returned query will have two placeholders for the project_id and
   * resource_id values.
   *
   * @return The SQL query string to execute.
   */
  protected String getSqlQuery() {
    return "SELECT MAX(upload.modify_date) as modify_date FROM upload "
        + "INNER JOIN upload_type_lu ON upload.upload_type_id = upload_type_lu.upload_type_id "
        + "INNER JOIN upload_status_lu ON upload.upload_status_id = upload_status_lu.upload_status_id "
        + "LEFT JOIN submission ON upload.upload_id = submission.upload_id "
        + "WHERE upload_type_lu.name = 'Submission' AND upload_status_lu.name = 'Active' "
        + "AND upload.resource_id = ? AND submission.submission_type_id = ?";
  }
}
