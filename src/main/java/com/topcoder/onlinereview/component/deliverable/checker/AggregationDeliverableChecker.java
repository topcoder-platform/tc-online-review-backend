/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.onlinereview.component.deliverable.checker;

import com.topcoder.onlinereview.component.deliverable.Deliverable;
import com.topcoder.onlinereview.component.deliverable.DeliverableChecker;
import com.topcoder.onlinereview.component.deliverable.DeliverableCheckingException;
import com.topcoder.onlinereview.component.grpcclient.deliverable.DeliverableServiceRpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

  @Autowired
  private DeliverableServiceRpc deliverableServiceRpc;

  public void check(Deliverable deliverable) throws DeliverableCheckingException {
    if (deliverable == null) {
      throw new IllegalArgumentException("deliverable cannot be null.");
    }
    try {
        deliverableServiceRpc.aggregationDeliverableCheck(deliverable);
    } catch (Exception ex) {
      throw new DeliverableCheckingException("Error occurs while database check operation.", ex);
    }
  }
}
