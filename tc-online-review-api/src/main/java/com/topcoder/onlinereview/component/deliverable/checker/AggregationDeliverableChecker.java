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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.util.CommonUtils.getDate;

/**
 * The AggregationDeliverableChecker class subclasses the SingleQuerySqlDeliverableChecker class.
 * The SQL query that it executes checks whether a given resource (in this case a reviewer) has
 * committed a review. (Note that, unlike the CommittedReviewDeliverableChecker class, the committed
 * review is per project and not per submission). If there is a committed aggregation review, it
 * marks the deliverable as completed, using the date the review was last modified.
 *
 * <p>This class is immutable.
 *
 * @author aubergineanode
 * @author kr00tki
 * @version 1.0
 */
@Component
public class AggregationDeliverableChecker implements DeliverableChecker {

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
              "SELECT modify_date FROM review WHERE committed = 1 AND resource_id = ?",
              newArrayList(deliverable.getResource()));
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
   * this method extracts resource id and project id values from the deliverable and sets them as
   * parameters of the PreparedStatement.
   *
   * @param deliverable The deliverable from which to get any needed parameters to set on the
   *     PreparedStatement.
   * @param statement The PreparedStatement representation of the SQL query returned by getSqlQuery.
   * @throws SQLException if any error occurs while setting the values to statement.
   */
  protected void fillInQueryParameters(Deliverable deliverable, PreparedStatement statement)
      throws SQLException {
    statement.setLong(1, deliverable.getResource());
  }

  /**
   * Gets the SQL query string to select the modification date of the aggregation review scorecard.
   * Returned query will have two placeholders for the resource_id and project_id values.
   *
   * @return The SQL query string to execute.
   */
  protected String getSqlQuery() {
    return "SELECT modify_date FROM review WHERE committed = 1 AND resource_id = ?";
  }
}
