/*
 * Copyright (c) 2006-2013, TopCoder, Inc. All rights reserved.
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

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.component.util.CommonUtils.getDate;

/**
 * The AppealResponsesDeliverableChecker class subclasses the SqlDeliverableChecker class and checks
 * that every <tt>Appeal</tt> comment has been responded to with an <tt>Appeal Response</tt>. If
 * this is the case, the deliverable is marked as complete.
 *
 * <p>This class is immutable.
 *
 * @author aubergineanode
 * @author kr00tki
 * @version 1.1.1s
 */
@Component
public class AppealResponsesDeliverableChecker implements DeliverableChecker {

  /**
   * This SQL query returns all the modify dates for all responses with type 'Appeal Response' for
   * given resource id.
   */
  private static final String QUERY =
      "SELECT appeal_response_comment.modify_date FROM review_item_comment "
          + "appeal_comment "
          + "INNER JOIN comment_type_lu appeal_comment_type ON appeal_comment.comment_type_id = "
          + "appeal_comment_type.comment_type_id AND appeal_comment_type.name = 'Appeal' "
          + "INNER JOIN review_item ON appeal_comment.review_item_id = review_item.review_item_id "
          + "INNER JOIN review ON review_item.review_id = review.review_id AND review.resource_id = ? AND review.submission_id = ? "
          + "LEFT OUTER JOIN (review_item_comment appeal_response_comment "
          + "INNER JOIN comment_type_lu appeal_response_comment_type "
          + "ON appeal_response_comment.comment_type_id = appeal_response_comment_type.comment_type_id "
          + "AND appeal_response_comment_type.name = 'Appeal Response') "
          + "ON appeal_response_comment.review_item_id = appeal_comment.review_item_id";

  @Autowired
  @Qualifier("tcsJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private DeliverableServiceRpc deliverableServiceRpc;

  /**
   * Checks the given deliverable to see if it is complete. This method queries the database to
   * select, for each "Appeal" comment, the "Appeal Response" comment.
   *
   * @param deliverable The deliverable to check
   * @throws IllegalArgumentException If deliverable is null
   * @throws DeliverableCheckingException If there is an error checking the deliverable due to an
   *     underlying database error.
   */
  public void check(Deliverable deliverable) throws DeliverableCheckingException {
    if (deliverable == null) {
      throw new IllegalArgumentException("deliverable cannot be null.");
    }
    try {
      deliverableServiceRpc.appealResponsesDeliverableCheck(deliverable);
      /* TODO GRPC
      List<Map<String, Object>> rs =
          executeSqlWithParam(
              jdbcTemplate,
              QUERY,
              newArrayList(deliverable.getResource(), deliverable.getSubmission()));
      // if there was a completion date - set it
      if (rs.isEmpty()) {
        deliverable.setCompletionDate(new Date());
      } else {
        rs.stream()
            .map(m -> getDate(m, "modify_date"))
            .max(Comparator.comparing(d -> d))
            .ifPresent(d -> deliverable.setCompletionDate(d));
      }
      */
    } catch (Exception ex) {
      throw new DeliverableCheckingException("Error occurs while checking in database.", ex);
    }
  }
}
