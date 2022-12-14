/*
 * Copyright (c) 2006-2013, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.onlinereview.component.deliverable.checker;

import com.topcoder.onlinereview.component.deliverable.Deliverable;
import com.topcoder.onlinereview.component.deliverable.DeliverableChecker;
import com.topcoder.onlinereview.component.deliverable.DeliverableCheckingException;
import com.topcoder.onlinereview.component.grpcclient.deliverable.DeliverableServiceRpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
  private DeliverableServiceRpc deliverableServiceRpc;

  public void check(Deliverable deliverable) throws DeliverableCheckingException {
    if (deliverable == null) {
      throw new IllegalArgumentException("deliverable cannot be null.");
    }
    try {
      deliverableServiceRpc.committedReviewDeliverableCheck(deliverable);
    } catch (Exception ex) {
      throw new DeliverableCheckingException("Error occurs while database check operation.", ex);
    }
  }
}
