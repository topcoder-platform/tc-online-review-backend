/*
 * Copyright (c) 2006-2013, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.onlinereview.component.deliverable.checker;

import com.topcoder.onlinereview.component.deliverable.Deliverable;
import com.topcoder.onlinereview.component.deliverable.DeliverableChecker;
import com.topcoder.onlinereview.component.deliverable.DeliverableCheckingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.util.CommonUtils.getDate;

/**
 * The CommittedReviewDeliverableChecker class subclasses the SingleQuerySqlDeliverableChecker
 * class. The SQL query that it executes checks whether a given resource (in this case a reviewer)
 * has completed a review for the project and submission. If so, it marks the deliverable as
 * complete, using the date the review was last changed.
 *
 * <p>This class is immutable.
 *
 * @author aubergineanode, isv
 * @author kr00tki
 * @version 1.1.1
 */
@Component
public class CommittedReviewDeliverableChecker implements DeliverableChecker {

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
              getSqlQuery(deliverable.isPerSubmission()),
              getQueryParameters(deliverable));
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
   * Given a PreparedStatement representation of the SQL query returned by the getSqlQuery method,
   * this method extracts resource_id and submission_id values from the deliverable and sets them as
   * parameters of the PreparedStatement.
   *
   * @param deliverable The deliverable from which to get any needed parameters to set on the
   *     PreparedStatement.
   * @throws SQLException if any error occurs while setting the values to statement.
   * @throws DeliverableCheckingException if the deliverable is not per submission.
   */
  protected List<Object> getQueryParameters(Deliverable deliverable) {
    if (deliverable.isPerSubmission()) {
      return newArrayList(
          deliverable.getResource(), deliverable.getPhase(), deliverable.getSubmission());
    } else {
      return newArrayList(deliverable.getResource(), deliverable.getPhase());
    }
  }

  /**
   * Gets the SQL query string to select the last modification date for the review scorecard for the
   * resource(reviewer)/submission. Returned query will have 2 placeholders for the resource_id and
   * submission_id values.
   *
   * @return The SQL query string to execute.
   */
  protected String getSqlQuery(boolean isPerSubmission) {
    if (isPerSubmission) {
      return "SELECT modify_date FROM review WHERE committed = 1 AND resource_id = ? AND project_phase_id = ? "
          + "AND submission_id = ?";
    } else {
      return "SELECT modify_date FROM review WHERE committed = 1 AND resource_id = ? AND project_phase_id = ? ";
    }
  }
}
