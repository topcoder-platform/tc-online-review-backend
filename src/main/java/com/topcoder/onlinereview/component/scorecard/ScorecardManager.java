/*
 * Copyright (C) 2006-2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.scorecard;

import com.topcoder.onlinereview.component.datavalidator.IntegerValidator;
import com.topcoder.onlinereview.component.datavalidator.LongValidator;
import com.topcoder.onlinereview.component.datavalidator.StringValidator;
import com.topcoder.onlinereview.component.search.SearchBuilderException;
import com.topcoder.onlinereview.component.search.SearchBundle;
import com.topcoder.onlinereview.component.search.SearchBundleManager;
import com.topcoder.onlinereview.component.search.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the manager class of this component. It loads persistence implementation using settings
 * in the configuration namespace. Then using the persistence implementation to
 * create/update/retrieve/search scorecards. This is the main class of the component, which is used
 * by client to perform the above scorecards operations. The default configuration namespace for
 * this class is: &quot;com.topcoder.management.scorecard&quot;. It can accept a custom namespace as
 * well. Apart from the persistence settings, it also initialize a SearchBundle instance to use in
 * scorecards searching and a ScorecardValidator instance to validate scorecards.<br>
 * Thread Safety: The implementation is not thread safe in that two threads running the same method
 * will use the same statement and could overwrite each other's work.
 *
 * <p>In version 1.0.2, a new method named getDefaultScorecardsIDInfo is added to retrieve the
 * scorecard type ids and scorecard ids for a specific category from default scorecards table.
 *
 * @author tuenm
 * @author zhuzeyuan
 * @author George1
 * @author Angen
 * @version 1.0.2
 */
@Component
public class ScorecardManager {
  public static final String SCORECARD_SEARCH_BUNDLE_NAME = "ScorecardSearchBundle";

  /** The maximal length for ScorecardStatusName. */
  private static final int SCORECARD_STATUS_NAME_MAXLENGTH = 64;

  /** The maximal length for ScorecardTypeName. */
  private static final int SCORECARD_TYPE_NAME_MAXLENGTH = 64;

  /** The maximal length for ScorecardName. */
  private static final int SCORECARD_NAME_MAXLENGTH = 64;

  /** The maximal length for ScorecardVersion. */
  private static final int SCORECARD_VERSION_MAXLENGTH = 16;

  /**
   * The persistence instance. It is initialized in the constructor and never changed after that. It
   * is used in the create/update/retrieve/search scorecard methods. It can never be null.
   */
  @Autowired private ScorecardPersistence persistence;

  @Autowired private SearchBundleManager searchBundleManager;

  /**
   * The search bundle instance. It is initialized in the constructor and never changed after that.
   * It is used in the search scorecard method. It can never be null.
   */
  private SearchBundle searchBundle;

  /**
   * The scorecard validator instance. It is initialized in the constructor and never changed after
   * that. It is used to validate scorecards before create/update them. It can never be null.
   */
  @Autowired private ScorecardValidator validator;

  /**
   * Create a new instance of ScorecardManagerImpl using the given configuration namespace. First it
   * load the 'PersistenceClass' and 'PersistenceNamespace' properties to initialize the persistence
   * plug-in implementation. The 'PersistenceNamespace' is optional and if it does not present,
   * value of 'PersistenceClass' property will be used. Then it load the 'SearchBuilderNamespace'
   * property to initialize SearchBuilder component.
   */
  @PostConstruct
  public void postRun() {
    searchBundle = searchBundleManager.getSearchBundle(SCORECARD_SEARCH_BUNDLE_NAME);
    // Create a validationMap, and map the items to be checked with desired validator.
    // Please refer to Component Specification 1.3.8 for the details.
    Map validationMap = new HashMap();
    validationMap.put(ScorecardSearchBundle.SCORECARD_STATUS_ID, LongValidator.isPositive());
    validationMap.put(ScorecardSearchBundle.SCORECARD_TYPE_ID, LongValidator.isPositive());
    validationMap.put(ScorecardSearchBundle.PROJECT_CATEGORY_ID, LongValidator.isPositive());
    validationMap.put(ScorecardSearchBundle.PROJECT_ID, LongValidator.isPositive());
    validationMap.put(
        ScorecardSearchBundle.SCORECARD_STATUS_NAME,
        StringValidator.hasLength(
            IntegerValidator.lessThanOrEqualTo(SCORECARD_STATUS_NAME_MAXLENGTH)));
    validationMap.put(
        ScorecardSearchBundle.SCORECARD_TYPE_NAME,
        StringValidator.hasLength(
            IntegerValidator.lessThanOrEqualTo(SCORECARD_TYPE_NAME_MAXLENGTH)));
    validationMap.put(
        ScorecardSearchBundle.SCORECARD_NAME,
        StringValidator.hasLength(IntegerValidator.lessThanOrEqualTo(SCORECARD_NAME_MAXLENGTH)));
    validationMap.put(
        ScorecardSearchBundle.SCORECARD_VERSION,
        StringValidator.hasLength(IntegerValidator.lessThanOrEqualTo(SCORECARD_VERSION_MAXLENGTH)));

    // Set the SearchableFields with this validationMap.
    searchBundle.setSearchableFields(validationMap);
  }

  /**
   * Create the scorecard in the database using the given scorecard instance. The scorecard instance
   * can include sub items such as groups, sections and questions. Those sub items will be persisted
   * as well. The operator parameter is used as the creation/modification user of the scorecard and
   * its subitems. The creation date and modification date will be the current date time when the
   * scorecard is created. The given scorecard instance will be validated before persisting.
   *
   * @param scorecard The scorecard instance to be created in the database.
   * @param operator The creation user of this scorecard.
   * @throws IllegalArgumentException if any input is null or the operator is empty string.
   * @throws PersistenceException if error occurred while accessing the database.
   * @throws ValidationException if error occurred while validating the scorecard instance.
   */
  public void createScorecard(Scorecard scorecard, String operator)
      throws PersistenceException, ValidationException {
    // Validate the scorecard first, and then pass it to the persistence.
    validator.validateScorecard(scorecard);
    persistence.createScorecard(scorecard, operator);
  }

  /**
   * Update the given scorecard instance into the database. The scorecard instance can include sub
   * items such as groups, sections and questions. Those sub items will be updated as well. If sub
   * items are removed from the scorecard, they will be deleted from the persistence. Likewise, if
   * new sub items are added, they will be created in the persistence. The operator parameter is
   * used as the modification user of the scorecard and its subitems. The modification date will be
   * the current date time when the scorecard is updated. The given scorecard instance will be
   * validated before persisting.
   *
   * @param scorecard The scorecard instance to be updated into the database.
   * @param operator The modification user of this scorecard.
   * @throws IllegalArgumentException if any input is null or the operator is empty string.
   * @throws PersistenceException if error occurred while accessing the database.
   * @throws ValidationException if error occurred while validating the scorecard instance.
   */
  public void updateScorecard(Scorecard scorecard, String operator)
      throws PersistenceException, ValidationException {
    // Validate the scorecard first, and then pass it to the persistence.
    validator.validateScorecard(scorecard);
    persistence.updateScorecard(scorecard, operator);
  }

  /**
   * Retrieves the scorecard instance from the persistence given its id. The scorecard instance is
   * retrieved with its sub items, such as group, section and questions.
   *
   * @return The scorecard instance.
   * @param id The id of the scorecard to be retrieved.
   * @throws IllegalArgumentException if the input id is less than or equal to zero.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public Scorecard getScorecard(long id) throws PersistenceException {
    Helper.assertIntegerGreaterThanZero(id, "id");

    return persistence.getScorecard(id, true);
  }

  /**
   * Search scorecard instances using the given filter parameter. The filter parameter decides the
   * condition of searching. This method use the Search Builder component to perform searching. The
   * search condition can be the combination of any of the followings:<br>
   * - Scorecard name<br>
   * - Scorecard version<br>
   * - Scorecard type id<br>
   * - Scorecard type name<br>
   * - Scorecard status id<br>
   * - Scorecard status name<br>
   * - Project category id that the scorecard linked to.<br>
   * - Project id that the scorecard linked to<br>
   * The filter is created using the ScorecardSearchBundle class. This class provide method to
   * create filter of the above condition and any combination of them.
   *
   * @return An array of scorecard instance as the search result.
   * @param filter The filter to search for scorecards.
   * @param complete Indicates whether to retrieve the scorecard including its sub items.
   * @throws IllegalArgumentException if the filter is null.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public Scorecard[] searchScorecards(Filter filter, boolean complete) throws PersistenceException {
    Helper.assertObjectNotNull(filter, "filter");

    try {
      // Use the SearchBundle instance to search for ids
      List<Map<String, Object>> result = searchBundle.search(filter);

      // If no search result found
      if (result.isEmpty()) {
        return new Scorecard[0];
      }
      // Use the ScorecardPersistence instance to get Scorecard instance array
      return persistence.getScorecards(result, complete);
    } catch (SearchBuilderException sbe) {
      throw new PersistenceException("Error exist while searching with searchBundle", sbe);
    } catch (ClassCastException cce) {
      throw new PersistenceException("Error exist while class casting", cce);
    }
  }

  /**
   * Retrieves all the scorecard types from the persistence.
   *
   * @return An array of scorecard type instances.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public ScorecardType[] getAllScorecardTypes() throws PersistenceException {
    return persistence.getAllScorecardTypes();
  }

  /**
   * Retrieves all the question types from the persistence.
   *
   * @return An array of question type instances.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public QuestionType[] getAllQuestionTypes() throws PersistenceException {
    return persistence.getAllQuestionTypes();
  }

  /**
   * Retrieves all the scorecard statuses from the persistence.
   *
   * @return An array of scorecard status instances.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public ScorecardStatus[] getAllScorecardStatuses() throws PersistenceException {
    return persistence.getAllScorecardStatuses();
  }

  /**
   * Retrieves an array of the scorecard instances from the persistence given their ids. The
   * scorecard instances can be retrieved with or without its sub items, depends on the 'complete'
   * parameter.
   *
   * @param ids The array of ids of the scorecards to be retrieved.
   * @param complete Indicates whether to retrieve the scorecard including its sub items.
   * @return An array of scorecard instances.
   * @throws PersistenceException if error occurred while accessing the database.
   * @throws IllegalArgumentException if the ids is less than or equal to zero. Or the input array
   *     is null or empty.
   */
  public Scorecard[] getScorecards(Long[] ids, boolean complete) throws PersistenceException {
    return persistence.getScorecards(ids, complete);
  }

  /**
   * Retrieves the scorecard type ids and scorecard ids for a specific category from default
   * scorecards table. This method takes as a parameter projectCategoryId, gets the id information
   * of scorecards for it, and returns an array of ScorecardIDInfo instances, each one containing
   * the scorecard type id and the scorecard id.
   *
   * @param projectCategoryId the id of the project category.
   * @return the ScorecardIDInfo instances array, each one containing the scorecard type id and the
   *     scorecard id.
   * @throws PersistenceException if error occurred while accessing the persistence.
   * @throws IllegalArgumentException if the projectCategoryId is less than or equal to zero.
   */
  public ScorecardIDInfo[] getDefaultScorecardsIDInfo(long projectCategoryId)
      throws PersistenceException {
    return persistence.getDefaultScorecardsIDInfo(projectCategoryId);
  }
}
