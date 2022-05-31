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
import static com.topcoder.onlinereview.component.util.CommonUtils.getLong;

/**
 * The FinalReviewDeliverableChecker class subclasses the SingleQuerySqlDeliverableChecker class.
 * The SQL query used checks to see whether the final review for the submission is complete. If so,
 * it marks the deliverable as completed, using the date of the approval/rejection comment.
 *
 * <p>This class is immutable.
 *
 * @author aubergineanode
 * @author kr00tki
 * @version 1.0
 */
@Component
public class FinalReviewDeliverableChecker implements DeliverableChecker {

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
          executeSqlWithParam(jdbcTemplate, getSqlQuery(), newArrayList(deliverable.getResource()));
      if (!rs.isEmpty()) {
        if (rs.get(0).get("modify_date") != null) {
          deliverable.setCompletionDate(getDate(rs.get(0), "modify_date"));
          deliverable.setSubmission(getLong(rs.get(0), "submission_id"));
        }
      }
    } catch (Exception ex) {
      throw new DeliverableCheckingException("Error occurs while database check operation.", ex);
    }
  }

  /**
   * Gets the SQL query string to select the modification date for the last final review
   * approval/rejection. The returned query will have 2 placeholders for the submission_id and
   * resource_id values.
   *
   * @return The SQL query string to execute.
   */
  protected String getSqlQuery() {
    return "SELECT MAX(review_comment.modify_date) as modify_date, review.submission_id FROM review_comment "
        + "INNER JOIN comment_type_lu ON review_comment.comment_type_id = comment_type_lu.comment_type_id "
        + "INNER JOIN review ON review.review_id = review_comment.review_id "
        + "WHERE review_comment.resource_id = ? "
        + "AND comment_type_lu.name = 'Final Review Comment' "
        + "AND (review_comment.extra_info = 'Approved' OR review_comment.extra_info = 'Rejected') "
        + "GROUP BY review.submission_id";
  }
}
