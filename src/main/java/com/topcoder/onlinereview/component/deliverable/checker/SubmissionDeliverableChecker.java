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
  private DeliverableServiceRpc deliverableServiceRpc;

  public void check(Deliverable deliverable) throws DeliverableCheckingException {
    if (deliverable == null) {
      throw new IllegalArgumentException("deliverable cannot be null.");
    }
    try {deliverableServiceRpc.submissionDeliverableCheck(deliverable);
    } catch (Exception ex) {
      throw new DeliverableCheckingException("Error occurs while database check operation.", ex);}}
}
