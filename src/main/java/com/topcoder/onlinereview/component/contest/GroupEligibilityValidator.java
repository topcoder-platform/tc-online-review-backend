/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.contest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topcoder.onlinereview.component.grpcclient.contesteligibility.ContestEligibilityServiceRpc;

/**
 * GroupEligibilityValidator is used to validate whether the user is in the group of
 * contestEligibility.
 *
 * <p><b>Thread Safety:</b> This class is thread safe since it is immutable.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Component
public class GroupEligibilityValidator implements ContestEligibilityValidator {

  @Autowired
  private ContestEligibilityServiceRpc contestEligibilityServiceRpc;

  /** Default constructor. */
  public GroupEligibilityValidator() {
    // does nothing
  }

  /**
   * Validate the given contestEligibility.
   *
   * @param userId the user id
   * @param contestEligibility the contestEligibility to validate
   * @return true if the user is eligible to join,otherwise false
   * @throws IllegalArgumentException if contestEligibility is null or it is not a
   *     GroupContestEligibility instance
   */
  public boolean validate(long userId, ContestEligibility contestEligibility) {
    // might be replaced by real checking web service,so let us make it simple.
    if (!(contestEligibility instanceof GroupContestEligibility)) {
      throw new IllegalArgumentException(
          "The contestEligibility should be a non-null GroupContestEligibility instance.");
    }
    return contestEligibilityServiceRpc.validateUserContestEligibility(userId, ((GroupContestEligibility) contestEligibility).getGroupId());
  }
}
