/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.deliverable;

/**
 * The DeliverableChecker interface is responsible for deciding if a deliverable is complete. If so,
 * it sets the completion date of the deliverable.
 *
 * <p>Implementations of this interface are not required to be thread safe.
 *
 * @author aubergineanode, singlewood
 * @version 1.0.2
 */
public interface DeliverableChecker {
  /**
   * Checks the given deliverable to see if it is complete.
   *
   * @param deliverable The deliverable to check
   * @throws IllegalArgumentException If deliverable is null
   * @throws DeliverableCheckingException If there is an error when determining whether a
   *     Deliverable has been completed or not
   */
  void check(Deliverable deliverable) throws DeliverableCheckingException;
}
