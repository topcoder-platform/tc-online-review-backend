/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.search.filter;

import com.topcoder.onlinereview.component.datavalidator.ObjectValidator;
import com.topcoder.onlinereview.component.search.ValidationResult;

import java.util.Map;

/**
 * This class extends AbstractSimpeFilter class. It is used to construct between search criterion.
 *
 * <p>The class is thread-safe since it is immutable
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.3
 */
public class BetweenFilter extends AbstractSimpleFilter {
  /**
   * Create a new instance,by setting the name, upper, lower.
   *
   * @param name the filed name of the search criterion
   * @param upper a Comparable object representing the upperThreshold
   * @param lower a Comparable object representing the lowerThreshold
   * @throws IllegalArgumentException if any parameter is Null
   * @throws IllegalArgumentException if <code>name</code> is empty
   */
  public BetweenFilter(String name, Comparable upper, Comparable lower) {
    super(name, upper, lower);
  }

  /**
   * Test to see if the filter is valid according to the rules presented in the validators, if the
   * field is searchable,return fail.
   *
   * @param validators a map containing <code>ObjectValidator</code> Objects
   * @return the <code>ValidationResult</code> object
   * @param alias a Map containing mapping between real names and alias names
   * @throws IllegalArgumentException if any parameter is Null
   */
  public ValidationResult isValid(Map validators, Map alias) {
    if (validators == null) {
      throw new IllegalArgumentException("The validators should not be null.");
    }
    if (alias == null) {
      throw new IllegalArgumentException("The alias should not be null.");
    }

    if (validators.size() == 0) {
      throw new IllegalArgumentException("The validators map should not be empty");
    }

    FilterHelper.checkMap(validators, "validators");
    FilterHelper.checkMap(alias, "alias");
    ObjectValidator rule = null;
    if (validators.get(fieldName) != null && validators.get(fieldName) instanceof ObjectValidator) {
      rule = (ObjectValidator) validators.get(fieldName);
    }

    // if null,then with alias name
    if (rule == null) {
      String aliasName = (String) alias.get(fieldName);

      if (aliasName != null) {
        if (validators.get(aliasName) != null
            && validators.get(aliasName) instanceof ObjectValidator) {
          rule = (ObjectValidator) validators.get(aliasName);
        }
      }
    }

    // if not searchable,for this is no rule with the name given, return invalid
    if (rule == null) {
      throw new IllegalArgumentException("The map validators is invalid to get the check rule.");
    }
    // check upperThreshold
    if (rule.getMessage(upperThreshold) != null) {
      return ValidationResult.createInvalidResult(
          "Between filter fails for " + rule.getMessage(upperThreshold) + " .", (Filter) this);
    }

    // check lowerThreshold
    if (rule.getMessage(lowerThreshold) != null) {
      return ValidationResult.createInvalidResult(
          "Between filter fails for " + rule.getMessage(upperThreshold) + " .", (Filter) this);
    }

    // return valid object
    return ValidationResult.createValidResult();
  }

  /**
   * Get the type of the Filter.
   *
   * @return a integer representing the type of the Filter
   * @deprecated This method has been deprecated.
   */
  public int getFilterType() {
    return Filter.BETWEEN_FILTER;
  }

  /**
   * return a clone of the object.
   *
   * @return a clone of the object
   */
  public Object clone() {
    return (new BetweenFilter(this.fieldName, this.upperThreshold, this.lowerThreshold));
  }
}
