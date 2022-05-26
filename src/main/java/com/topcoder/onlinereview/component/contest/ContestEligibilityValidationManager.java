/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.contest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ContestEligibilityValidationManager {

  /**
   * Represents the log name.Default value is 'contest_eligibility_logger'.You also could change the
   * default value via deploy descriptor.Can not be null but can be empty if really needed.
   */
  private String logName = "contest_eligibility_logger";

  /**
   * Represents the validation map.It is not changed after setting in initialize method. The key is
   * concrete class name of contest eligibility entity,the value is its corresponding contest
   * eligibility validator instance. It will be used in validate method.Can be empty,but not null
   * and contains null item.
   */
  @Autowired private GroupEligibilityValidator groupEligibilityValidator;

  /**
   * Validate the user against a list of ContestEligibility entities.
   *
   * <p>It will for each ContestEligibility, invoke the corresponding ContestEligibilityValidator to
   * check, for now if any one of the eligibility check is true (OR), then it will return true
   * (eligible). If the given list is empty,then return true because empty list means there is no
   * eligibility assign.
   *
   * @param userId the id of user
   * @param eligibilities a list of ContestEligibility entities
   * @return true if any one of the eligibility check is true or the given list is empty,otherwise
   *     false
   * @throws IllegalArgumentException if eligibilities is null or eligibilities contains null item
   */
  public boolean validate(long userId, List<ContestEligibility> eligibilities)
      throws ContestEligibilityValidationManagerException {
    logEntrance(
        "ContestEligibilityValidationManagerBean#validate",
        new String[] {"userId", "eligibilities"},
        new Object[] {userId, eligibilities});
    checkNull(eligibilities, "eligibilities");
    boolean result = false;
    if (eligibilities.size() == 0) {
      result = true;
    } else {
      for (ContestEligibility contestEligibility : eligibilities) {
        try {
          if (contestEligibility instanceof GroupContestEligibility
              && groupEligibilityValidator.validate(userId, contestEligibility)) {
            result = true;
            break;
          }
        } catch (Exception e) {
          throw logError(
              new ContestEligibilityValidationManagerException(
                  "Any errors occurred when validating the contestEligibility.", e));
        }
      }
    }
    logExit("ContestEligibilityValidationManagerBean#validate");
    return result;
  }

  /**
   * Logs the error.
   *
   * @param <T> the generic class type of error
   * @param error the error needs to be logged.
   * @return the error
   */
  private <T extends Exception> T logError(T error) {
    log.error("Error recognized: " + error.getMessage(), error);
    return error;
  }

  /**
   * Log the entrance of a method and all the input arguments.
   *
   * @param methodName the name of the method
   * @param paramNames the name of the parameters
   * @param params the parameters
   */
  @SuppressWarnings("unchecked")
  private void logEntrance(String methodName, String[] paramNames, Object[] params) {
    log.debug("Enter into Method: " + methodName + " At " + new Date());
    if (paramNames != null) {
      StringBuilder logInfo = new StringBuilder("Parameters:");
      for (int i = 0; i < paramNames.length; i++) {
        if (params[i] instanceof List && ((List<ContestEligibility>) params[i]).size() != 0) {
          List<ContestEligibility> list = (List<ContestEligibility>) params[i];
          StringBuilder paramLog = new StringBuilder();
          for (ContestEligibility contestEligibility : list) {
            if (contestEligibility == null) {
              paramLog.append("  null");
              continue;
            }
            paramLog.append(
                contestEligibility.getClass().getName()
                    + " with id="
                    + contestEligibility.getId()
                    + "  ");
          }
          logInfo.append(" [ " + paramNames[i] + " = {" + paramLog.toString() + "} ]");
          continue;
        }
        logInfo.append(" [ " + paramNames[i] + " = " + params[i] + " ]");
      }
      log.debug(logInfo.toString());
    }
  }

  /**
   * Log the exit of a method.
   *
   * @param methodName the name of the method
   */
  private void logExit(String methodName) {
    log.debug("Exit out Method: " + methodName + " At " + new Date());
  }

  /**
   * Checks whether the given Object is null.
   *
   * @param arg the argument to check
   * @param name the name of the argument to check
   * @throws IllegalArgumentException if the given Object is null
   */
  private void checkNull(Object arg, String name) {
    if (arg == null) {
      IllegalArgumentException e =
          new IllegalArgumentException("The argument " + name + " cannot be null.");
      throw logError(e);
    }
  }
}
