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
import static com.topcoder.onlinereview.util.CommonUtils.getLong;

/**
 * The SubmitterCommentDeliverableChecker class subclasses the SingleQuerySqlDeliverableChecker
 * class. The SQL query that it executes checks whether the resource (i.e. the submitter) has
 * attached an approved or rejected comment to the review. If so, it marks the deliverable as
 * completed, using the date that the comment was updated.
 *
 * <p>This class is immutable.
 *
 * @author aubergineanode
 * @author kr00tki
 * @version 1.0
 */
@Component
public class SubmitterCommentDeliverableChecker implements DeliverableChecker {

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
              entityManager, getSqlQuery(), newArrayList(deliverable.getResource()));
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
   * Gets the SQL query string to check that a submitter comment has been made. The returned query
   * will have two placeholders for the submission_id and resource_id values.
   *
   * @return The SQL query string to execute.
   */
  protected String getSqlQuery() {
    return "SELECT MAX(review_comment.modify_date) as modify_date, review.submission_id FROM review_comment "
        + "INNER JOIN comment_type_lu ON review_comment.comment_type_id = comment_type_lu.comment_type_id "
        + "INNER JOIN review ON review.review_id = review_comment.review_id "
        + "INNER JOIN resource ON review.resource_id = resource.resource_id "
        + "INNER JOIN phase_dependency ON resource.project_phase_id = phase_dependency.dependency_phase_id "
        + "WHERE review_comment.resource_id = ? "
        + "AND phase_dependency.dependent_phase_id = ? "
        + "AND comment_type_lu.name = 'Submitter Comment' "
        + "AND (review_comment.extra_info = 'Approved' OR review_comment.extra_info = 'Rejected') "
        + "GROUP BY review.submission_id";
  }
}
