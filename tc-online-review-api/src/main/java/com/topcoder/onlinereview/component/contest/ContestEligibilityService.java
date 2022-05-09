/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.contest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class ContestEligibilityService {

  /**
   * A <code>ContestEligibilityValidationManager</code> providing access to available <code>
   * Contest Eligibility Validation EJB</code>.
   */
  @Autowired private ContestEligibilityValidationManager contestEligibilityValidationManager;

  /**
   * A <code>ContestEligibilityManager</code> providing access to available <code>
   * Contest Eligibility Persistence EJB</code>.
   */
  @Autowired private ContestEligibilityManager contestEligibilityManager;

  /**
   * Returns whether a user is eligible for a particular contest.
   *
   * @param userId The user id
   * @param contestId The contest id
   * @param isStudio true if the contest is a studio contest, false otherwise.
   * @return true if the user is eligible for the specified contest, false otherwise.
   * @throws ContestEligibilityValidatorException if any other error occurs
   * @since 1.2.2
   */
  public boolean isEligible(long userId, long contestId, boolean isStudio)
      throws ContestEligibilityValidatorException {
    String methodName = "isEligible";
    log.debug("Enter: " + methodName);

    boolean eligible;

    try {
      List<ContestEligibility> eligibilities =
          contestEligibilityManager.getContestEligibility(contestId, isStudio);
      eligible = contestEligibilityValidationManager.validate(userId, eligibilities);
    } catch (ContestEligibilityPersistenceException | ContestEligibilityValidationManagerException e) {
      log.error(e.getMessage(), e);
      throw new ContestEligibilityValidatorException(e.getMessage(), e);
    }
    log.debug("Exit: " + methodName);
    return eligible;
  }

  /**
   * Returns whether a contest has any eligibility
   *
   * @param contestId The contest id
   * @param isStudio true if the contest is a studio contest, false otherwise.
   * @return true if the user is eligible for the specified contest, false otherwise.
   * @throws ContestEligibilityValidatorException if any other error occurs
   * @since 1.2
   */
  public boolean hasEligibility(long contestId, boolean isStudio)
      throws ContestEligibilityValidatorException {

    String methodName = "hasEligibility";
    log.debug("Enter: " + methodName);

    List<ContestEligibility> eligibilities;

    try {
      eligibilities = contestEligibilityManager.getContestEligibility(contestId, isStudio);
    } catch (ContestEligibilityPersistenceException e) {
      log.error(e.getMessage(), e);
      throw new ContestEligibilityValidatorException(e.getMessage(), e);
    }

    if (eligibilities == null || eligibilities.size() == 0) {
      return false;
    }

    return true;
  }

  /**
   * Return a list of contest ids that has eligibility.
   *
   * @param contestIds the contest id list
   * @param isStudio the flag used to indicate whether it is studio
   * @return a list of contest ids
   * @throws IllegalArgumentException if contestId is not positive
   */
  public Set<Long> haveEligibility(Long[] contestIds, boolean isStudio)
      throws ContestEligibilityValidatorException {
    try {
      return contestEligibilityManager.haveEligibility(contestIds, isStudio);
    } catch (ContestEligibilityPersistenceException e) {
      log.error(e.getMessage(), e);
      throw new ContestEligibilityValidatorException(e.getMessage(), e);
    }
  }
}
