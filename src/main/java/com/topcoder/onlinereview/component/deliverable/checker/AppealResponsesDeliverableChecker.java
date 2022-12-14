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
    } catch (Exception ex) {
      throw new DeliverableCheckingException("Error occurs while checking in database.", ex);
    }
  }
}
