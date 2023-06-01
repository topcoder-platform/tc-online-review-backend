/*
 * Copyright (C) 2006-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.deliverable;

import com.topcoder.onlinereview.component.deliverable.checker.*;
import com.topcoder.onlinereview.component.grpcclient.deliverable.DeliverableServiceRpc;
import com.topcoder.onlinereview.component.search.SearchBuilderException;
import com.topcoder.onlinereview.component.search.filter.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import static com.google.common.collect.Lists.newArrayList;

/**
 * The PersistenceDeliverableManager class implements the DeliverableManager interface. It ties
 * together a persistence mechanism and two Search Builder instances (for searching for various
 * types of data). The methods in this class use a SearchBundle to execute a search and then use the
 * persistence to load an object for each of the ids found from the search. *
 *
 * <p>Here is the sample code for using this component.
 *
 * <pre>
 * // Set up the SearchBundleManager.
 * SearchBundleManager searchBundleManager = new SearchBundleManager(SEARCH_BUILDER_NAMESPACE);
 *
 * // 4.3.3 Create deliverable persistence and manager.
 *
 * DeliverablePersistence deliverablePersistence = new SqlDeliverablePersistence(new DBConnectionFactoryImpl(
 *         DB_CONNECTION_NAMESPACE));
 *
 * // The checker is used when deliverable instances
 * // are retrieved
 * Map&lt;String, DeliverableChecker&gt; checker = new HashMap&lt;String, DeliverableChecker&gt;();
 * checker.put(&quot;name1&quot;, new MockDeliverableChecker());
 * checker.put(&quot;name2&quot;, new MockDeliverableChecker());
 *
 * DeliverableManager deliverableManager = new PersistenceDeliverableManager(deliverablePersistence, checker,
 *         searchBundleManager.getSearchBundle(&quot;Deliverable Search Bundle&quot;), searchBundleManager
 *                 .getSearchBundle(&quot;Deliverable With Submission Search Bundle&quot;));
 *
 * // Search for deliverables (see 4.3.5)
 * </pre>
 *
 * <p><em>Changes in 1.1:</em>
 *
 * <ul>
 *   <li>Changed <code>logger</code> attribute name to upper case to meet Java and TopCoder
 *       standards.
 * </ul>
 *
 * <p><strong>Thread Safety: </strong> This class is immutable and hence thread-safe.
 *
 * <p>Version 1.1.1 (Specification Review Part 1 Assembly 1.0) Change notes:
 *
 * <p><em>Changes in 1.2:</em>
 *
 * <ul>
 *   <li>generic types are used to eliminate manually type check.
 *   <li>checkAndGetCustomResultSetValidDeliverable and checkDeliverableCheckers helper methods are
 *       moved here.
 * </ul>
 *
 * @author aubergineanode, singlewood, saarixx, sparemax, isv
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.2.3
 */
@Slf4j
@Component
public class DeliverableManager {
  /**
   * The persistence store for Deliverables. This field is set in the constructor, is immutable, and
   * can never be null.
   */
  @Autowired private SqlDeliverablePersistence persistence;

  /**
   * The checkers that are used to determine whether a deliverable is complete or not. This field is
   * set in the constructor and can never be null.
   *
   * <p>Change in 1.2: Added generic parameters.
   */
  // @Autowired private AggregationDeliverableChecker aggregationDeliverableChecker;
  @Autowired private AppealResponsesDeliverableChecker appealResponsesDeliverableChecker;
  @Autowired private CommittedReviewDeliverableChecker CommittedReviewDeliverableChecker;
  // @Autowired private FinalFixesDeliverableChecker finalFixesDeliverableChecker;
  // @Autowired private FinalReviewDeliverableChecker finalReviewDeliverableChecker;
  // @Autowired private IndividualReviewDeliverableChecker individualReviewDeliverableChecker;
  // @Autowired private SpecificationSubmissionDeliverableChecker specificationSubmissionDeliverableChecker;
  @Autowired private SubmissionDeliverableChecker submissionDeliverableChecker;
  // @Autowired private SubmitterCommentDeliverableChecker submitterCommentDeliverableChecker;
  // @Autowired private TestCasesDeliverableChecker testCasesDeliverableChecker;

  private Map<String, DeliverableChecker> deliverableCheckers;

  @Autowired
  private DeliverableServiceRpc deliverableServiceRpc;

  @PostConstruct
  public void postRun() {
    deliverableCheckers = new HashMap<String, DeliverableChecker>();
    // deliverableCheckers.put("Aggregation", aggregationDeliverableChecker);
    deliverableCheckers.put("Appeal Responses", appealResponsesDeliverableChecker);
    deliverableCheckers.put("Checkpoint Screening Scorecard", CommittedReviewDeliverableChecker);
    deliverableCheckers.put("Checkpoint Review Scorecard", CommittedReviewDeliverableChecker);
    deliverableCheckers.put("Primary Screening Scorecard", CommittedReviewDeliverableChecker);
    deliverableCheckers.put("Review Scorecard", CommittedReviewDeliverableChecker);
    deliverableCheckers.put("Iterative Review Scorecard", CommittedReviewDeliverableChecker);
    // deliverableCheckers.put("Specification Review", CommittedReviewDeliverableChecker);
    // deliverableCheckers.put("Final Fix", finalFixesDeliverableChecker);
    // deliverableCheckers.put("Final Review", finalReviewDeliverableChecker);
    // deliverableCheckers.put(null, individualReviewDeliverableChecker);
    // deliverableCheckers.put(null, specificationSubmissionDeliverableChecker);
    deliverableCheckers.put("Submission", submissionDeliverableChecker);
    deliverableCheckers.put("Checkpoint Submission", submissionDeliverableChecker);
    // deliverableCheckers.put(null, submitterCommentDeliverableChecker);
    // deliverableCheckers.put("Stress Test Cases", testCasesDeliverableChecker);
  }

  /**
   * Searches the deliverables in the persistence store using the given filter. The filter can be
   * formed using the field names and utility methods in DeliverableFilterBuilder. The return will
   * always be a non-null (possibly 0 item) array. This method is designed to be used with
   * submission id filters, and returns only deliverables that are "per submission".
   *
   * @return The Deliverables meeting the filter and complete conditions
   * @param filter The filter to use
   * @param complete True to include only those deliverables that have been completed, false to
   *     include only those deliverables that are not complete, null to include both complete and
   *     incomplete deliverables
   * @throws IllegalArgumentException If filter is null
   * @throws DeliverablePersistenceException If there is an error reading the persistence
   * @throws SearchBuilderException If there is an error executing the filter
   * @throws DeliverableCheckingException If there is an error when determining whether a
   *     Deliverable has been completed or not
   */
  public Deliverable[] searchDeliverablesWithSubmissionFilter(Filter filter, Boolean complete)
      throws DeliverablePersistenceException, SearchBuilderException, DeliverableCheckingException {
    // Delegate to searchDeliverablesHelper. 4 indicates that there are four columns in the
    // CustomResultSet.
    log.debug("search deliverables with submission filter.");
    return searchDeliverablesHelper(
        filter,
        complete,
        newArrayList("deliverable_id", "resource_id", "project_phase_id", "error"));
  }

  /**
   * searchDeliverables: Searches the deliverables in the persistence store using the given filter.
   * The filter can be formed using the field names and utility methods in DeliverableFilterBuilder.
   * The return will always be a non-null (possibly 0 item) array. This method should not be used
   * with submission id filters.
   *
   * @return The Deliverables meeting the Filter and complete conditions
   * @param filter The filter to use
   * @param complete True to include only those deliverables that have been completed, false to
   *     include only those deliverables that are not complete, null to include both complete and
   *     incomplete deliverables
   * @throws IllegalArgumentException If filter is null
   * @throws DeliverablePersistenceException If there is an error reading the persistence store
   * @throws SearchBuilderException If there is an error executing the filter
   * @throws DeliverableCheckingException If there is an error when determining whether a
   *     Deliverable has been completed or not
   */
  public Deliverable[] searchDeliverables(Filter filter, Boolean complete)
      throws DeliverablePersistenceException, SearchBuilderException, DeliverableCheckingException {
    // Delegate to searchDeliverablesHelper. 3 indicates that there are three columns in the
    // CustomResultSet.
    log.debug("Search deliverables filter.");
    return searchDeliverablesHelper(
        filter, complete, newArrayList("deliverable_id", "resource_id", "project_phase_id"));
  }

  /**
   * Help method for methods: searchDeliverables and searchDeliverablesWithSubmissionFilter.
   * Searches the deliverables in the persistence store using the given filter. The filter can be
   * formed using the field names and utility methods in DeliverableFilterBuilder. The return will
   * always be a non-null (possibly 0 item) array. The String operation is used for distinguish call
   * from two methods.
   *
   * <p>Changes in version 1.2:
   *
   * <ul>
   *   <li>add generic type support.
   *   <li>checkAndGetCustomResultSetValidDeliverable method is moved in this class.
   * </ul>
   *
   * @param filter The filter to use
   * @param complete True to include only those deliverables that have been completed, false to
   *     include only those deliverables that are not complete, null to include both complete and
   *     incomplete deliverables
   * @param cols the identifier for the caller, 3 is searchDeliverables, 4 is
   *     searchDeliverablesWithSubmissionFilter
   * @return The Deliverables meeting the Filter and complete conditions
   * @throws DeliverablePersistenceException If there is an error reading the persistence store
   * @throws SearchBuilderException If there is an error executing the filter
   * @throws DeliverableCheckingException If there is an error when determining whether a
   *     Deliverable has been completed or not
   */
  private Deliverable[] searchDeliverablesHelper(Filter filter, Boolean complete, List<String> cols)
      throws SearchBuilderException, DeliverablePersistenceException, DeliverableCheckingException {
    DeliverableHelper.checkObjectNotNull(filter, "filter");

    Long[][] array;
    if (cols.size() == 3) {
        array = deliverableServiceRpc.searchDeliverables(filter);
    } else {
        array = deliverableServiceRpc.searchDeliverablesWithSubmission(filter);
    }
    List<Deliverable> list = new ArrayList<>();

    log.debug("Deliverable Id Array: " + array.length + ", " + array[0].length);

    // Get Deliverable[] from persistence layer.
    Deliverable[] deliverableArray;
    if (cols.size() == 3) {
      deliverableArray = persistence.loadDeliverables(array[0], array[1], array[2]);
    } else {
      deliverableArray = persistence.loadDeliverables(array[0], array[1], array[2], array[3]);
    }

    // For each deliverable in deliverableArray
    for (int i = 0, n = deliverableArray.length; i < n; i++) {

      log.debug("Loading Deliverable Array");

      Deliverable deliverable = deliverableArray[i];

      // Get the name of deliverable. This can not be null, if it is, throw a
      // DeliverablePersistenceException.
      String name = deliverable.getName();
      DeliverableHelper.checkObjectNotNullFullDesp(name, "name in the deliverable can't be null.");

      // Look up the deliverable checker with the name.
      DeliverableChecker deliverableChecker = deliverableCheckers.get(name);

      // If DeliverableChecker is found for the name, run the retrieved DeliverableChecker
      // on the deliverable. Otherwise do not check the deliverable.
      if (deliverableChecker != null) {
        deliverableChecker.check(deliverable);
      }

      // If the isCompleted property of the deliverable matches the complete argument
      // (when complete is not null), then add the deliverable to the return array.
      // When complete is null, the deliverable is always added to the return array.
      if (complete == null || (complete == deliverable.isComplete())) {

        log.debug("Adding Deliverable To List");
        list.add(deliverable);
      }
    }

    // Convert List to Deliverable[]
    return list.toArray(new Deliverable[list.size()]);
  }
}
