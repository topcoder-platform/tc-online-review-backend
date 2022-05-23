/*
 * Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.deliverable.late;

import com.topcoder.onlinereview.component.search.SearchBundle;
import com.topcoder.onlinereview.component.search.SearchBundleManager;
import com.topcoder.onlinereview.component.search.filter.AndFilter;
import com.topcoder.onlinereview.component.search.filter.EqualToFilter;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.component.search.filter.NotFilter;
import com.topcoder.onlinereview.component.search.filter.NullFilter;
import com.topcoder.onlinereview.component.search.filter.OrFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.util.CommonUtils.getInt;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.util.CommonUtils.getString;

/**
 * <p>
 * This class is an implementation of LateDeliverableManager that uses Search Builder component to retrieve by ID or
 * search for late deliverables in persistence and pluggable LateDeliverablePersistence instance to update late
 * deliverables in persistence. This class uses Logging Wrapper component to log errors and debug information.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 *
 * <pre>
 * &lt;?xml version=&quot;1.0&quot;?&gt;
 * &lt;CMConfig&gt;
 *   &lt;Config name=&quot;com.topcoder.management.deliverable.late.impl.LateDeliverableManagerImpl&quot;&gt;
 *     &lt;Property name=&quot;loggerName&quot;&gt;
 *       &lt;Value&gt;myLogger&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;objectFactoryConfig&quot;&gt;
 *       &lt;Property name=&quot;DatabaseLateDeliverablePersistence&quot;&gt;
 *         &lt;Property name=&quot;type&quot;&gt;
 *           &lt;Value&gt;
 *             com.topcoder.management.deliverable.late.impl.persistence.DatabaseLateDeliverablePersistence
 *           &lt;/Value&gt;
 *         &lt;/Property&gt;
 *       &lt;/Property&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;searchBundleManagerNamespace&quot;&gt;
 *       &lt;Value&gt;LateDeliverableManagerImpl.SearchBuilderManager&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;nonRestrictedSearchBundleName&quot;&gt;
 *       &lt;Value&gt;Non-restricted Late Deliverable Search Bundle&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;restrictedSearchBundleName&quot;&gt;
 *       &lt;Value&gt;Restricted Late Deliverable Search Bundle&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;persistenceKey&quot;&gt;
 *       &lt;Value&gt;DatabaseLateDeliverablePersistence&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;persistenceConfig&quot;&gt;
 *       &lt;Property name=&quot;loggerName&quot;&gt;
 *         &lt;Value&gt;myLogger&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;Property name=&quot;dbConnectionFactoryConfig&quot;&gt;
 *         &lt;Property name=&quot;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&quot;&gt;
 *           &lt;Property name=&quot;connections&quot;&gt;
 *             &lt;Property name=&quot;default&quot;&gt;
 *               &lt;Value&gt;myConnection&lt;/Value&gt;
 *             &lt;/Property&gt;
 *             &lt;Property name=&quot;myConnection&quot;&gt;
 *               &lt;Property name=&quot;producer&quot;&gt;
 *                   &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *               &lt;/Property&gt;
 *               &lt;Property name=&quot;parameters&quot;&gt;
 *                 &lt;Property name=&quot;jdbc_driver&quot;&gt;
 *                   &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name=&quot;jdbc_url&quot;&gt;
 *                   &lt;Value&gt;
 *                     jdbc:informix-sqli://localhost:1526/tcs_catalog:informixserver=ol_topcoder
 *                   &lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name=&quot;SelectMethod&quot;&gt;
 *                   &lt;Value&gt;cursor&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name=&quot;user&quot;&gt;
 *                   &lt;Value&gt;informix&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name=&quot;password&quot;&gt;
 *                   &lt;Value&gt;123456&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *               &lt;/Property&gt;
 *             &lt;/Property&gt;
 *           &lt;/Property&gt;
 *         &lt;/Property&gt;
 *       &lt;/Property&gt;
 *       &lt;Property name=&quot;connectionName&quot;&gt;
 *         &lt;Value&gt;myConnection&lt;/Value&gt;
 *       &lt;/Property&gt;
 *     &lt;/Property&gt;
 *   &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 *
 * <pre>
 * // Create an instance of LateDeliverableManagerImpl using custom configuration
 * ConfigurationObject configuration = TestsHelper.getConfig();
 * LateDeliverableManagerImpl lateDeliverableManager = new LateDeliverableManagerImpl(configuration);
 *
 * // Create an instance of LateDeliverableManagerImpl using custom config file and namespace
 * lateDeliverableManager = new LateDeliverableManagerImpl(LateDeliverableManagerImpl.DEFAULT_CONFIG_FILENAME,
 *     LateDeliverableManagerImpl.DEFAULT_CONFIG_NAMESPACE);
 *
 * // Create an instance of LateDeliverableManagerImpl using default config file
 * lateDeliverableManager = new LateDeliverableManagerImpl();
 *
 * // Retrieve the late deliverable with ID=1
 * LateDeliverable lateDeliverable = lateDeliverableManager.retrieve(1);
 * // lateDeliverable.getId() must be 1
 * // lateDeliverable.getProjectPhaseId() must be 101
 * // lateDeliverable.getResourceId() must be 1001
 * // lateDeliverable.getDeliverableId() must be 4
 * // lateDeliverable.isForgiven() must be false
 * // lateDeliverable.getExplanation() must be null
 * // lateDeliverable.getType().getId() must be 1
 * // lateDeliverable.getType().getName() must be &quot;Missed Deadline&quot;
 * // lateDeliverable.getType().getDescription() must be &quot;Missed Deadline&quot;
 *
 * // Update the late deliverable by changing its forgiven flag and explanation
 * lateDeliverable.setForgiven(true);
 * lateDeliverable.setExplanation(&quot;OR didn't work&quot;);
 * lateDeliverableManager.update(lateDeliverable);
 *
 * // Search for all forgiven late deliverables for project with ID=100000
 * Filter forgivenFilter = LateDeliverableFilterBuilder.createForgivenFilter(true);
 * Filter projectIdFilter = LateDeliverableFilterBuilder.createProjectIdFilter(100000);
 * Filter compositeFilter = new AndFilter(forgivenFilter, projectIdFilter);
 *
 * List&lt;LateDeliverable&gt; lateDeliverables = lateDeliverableManager.searchAllLateDeliverables(compositeFilter);
 * // lateDeliverables.size() must be 1
 * // lateDeliverables.get(0).getId() must be 1
 * // lateDeliverables.get(0).getProjectPhaseId() must be 101
 * // lateDeliverables.get(0).getResourceId() must be 1001
 * // lateDeliverables.get(0).getDeliverableId() must be 4
 * // lateDeliverables.get(0).isForgiven() must be true
 * // lateDeliverables.get(0).getExplanation() must be &quot;OR didn't work&quot;
 * // lateDeliverables.get(0).getType().getId() must be 1
 * // lateDeliverables.get(0).getType().getName() must be &quot;Missed Deadline&quot;
 * // lateDeliverables.get(0).getType().getDescription() must be &quot;Missed Deadline&quot;
 *
 * // Search for all late deliverables from design category for all active projects
 * // to which user with ID=3 has a manager/copilot access
 * Filter categoryFilter = LateDeliverableFilterBuilder.createProjectCategoryIdFilter(1);
 * Filter activeProjectFilter = LateDeliverableFilterBuilder.createProjectStatusIdFilter(1);
 * compositeFilter = new AndFilter(categoryFilter, activeProjectFilter);
 * lateDeliverables = lateDeliverableManager.searchRestrictedLateDeliverables(compositeFilter, 3);
 * // lateDeliverables.size() must be 1
 * // lateDeliverables.get(0).getId() must be 2
 * // lateDeliverables.get(0).getProjectPhaseId() must be 102
 * // lateDeliverables.get(0).getResourceId() must be 1002
 * // lateDeliverables.get(0).getDeliverableId() must be 3
 * // lateDeliverables.get(0).isForgiven() must be false
 * // lateDeliverables.get(0).getExplanation() must be null
 * // lateDeliverables.get(0).getType().getId() must be 1
 * // lateDeliverables.get(0).getType().getName() must be &quot;Missed Deadline&quot;
 * // lateDeliverables.get(0).getType().getDescription() must be &quot;Missed Deadline&quot;
 *
 * // Retrieve all late deliverable types
 * List&lt;LateDeliverableType&gt; lateDeliverableTypes = lateDeliverableManager.getLateDeliverableTypes();
 * // lateDeliverableTypes.size() must be 2
 * // lateDeliverableTypes.get(0).getId() must be 1
 * // lateDeliverableTypes.get(0).getName() must be &quot;Missed Deadline&quot;
 * // lateDeliverableTypes.get(0).getDescription() must be &quot;Missed Deadline&quot;
 * // lateDeliverableTypes.get(1).getId() must be 2
 * // lateDeliverableTypes.get(1).getName() must be &quot;Rejected Final Fix&quot;
 * // lateDeliverableTypes.get(1).getDescription() must be &quot;Rejected Final Fix&quot;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Changes in version 1.0.6:</em>
 * <ol>
 * <li>Added getLateDeliverableTypes() method.</li>
 * <li>Updated throws documentation of update() method.</li>
 * <li>Updated getLateDeliverables() method.</li>
 * <li>Updated class documentation.</li>
 * </ol>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe when entities passed to it are used by the
 * caller in thread safe manner. It uses thread safe SearchBundle, LateDeliverablePersistence and Log instances.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0.6
 */
@Slf4j
@Component
public class LateDeliverableManager {
    /**
     * <p>
     * The default configuration file path for this class. It's an immutable static constant.
     * </p>
     */
    public static final String DEFAULT_CONFIG_FILENAME =
        "com/topcoder/management/deliverable/late/impl/LateDeliverableManagerImpl.properties";

    /**
     * <p>
     * The default configuration namespace for this class. It's an immutable static constant.
     * </p>
     */
    public static final String DEFAULT_CONFIG_NAMESPACE =
        "com.topcoder.management.deliverable.late.impl.LateDeliverableManagerImpl";

    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = LateDeliverableManager.class.getName();

    /**
     * <p>
     * Represents the child key 'objectFactoryConfig'.
     * </p>
     */
    private static final String KEY_OF_CONFIG = "objectFactoryConfig";

    /**
     * <p>
     * Represents the child key 'persistenceConfig'.
     * </p>
     */
    private static final String KEY_PERSISTENCE_CONFIG = "persistenceConfig";

    /**
     * <p>
     * Represents the property key 'searchBundleManagerNamespace'.
     * </p>
     */
    private static final String KEY_SBM_NAMESPACE = "searchBundleManagerNamespace";

    /**
     * <p>
     * Represents the property key 'nonRestrictedSearchBundleName'.
     * </p>
     */
    private static final String KEY_NON_RESTRICTED_SB_NAME = "Non-restricted Late Deliverable Search Bundle";

    /**
     * <p>
     * Represents the property key 'restrictedSearchBundleName'.
     * </p>
     */
    private static final String KEY_RESTRICTED_SB_NAME = "Restricted Late Deliverable Search Bundle";

    /**
     * <p>
     * Represents the property key 'persistenceKey'.
     * </p>
     */
    private static final String KEY_PERSISTENCE_KEY = "persistenceKey";

    /**
     * <p>
     * The search bundle used by this class when searching for late deliverables with no user restriction./p>
     *
     * <p>
     * Is initialized in the constructor and never changed after that. Cannot be null. Is used in
     * searchAllLateDeliverables().
     * </p>
     */
    private SearchBundle nonRestrictedSearchBundle;

    /**
     * <p>
     * The search bundle used by this class when searching for late deliverables with specific user restriction. /p>
     *
     * <p>
     * Is initialized in the constructor and never changed after that. Cannot be null. Is used in
     * searchRestrictedLateDeliverables().
     * </p>
     */
    private SearchBundle restrictedSearchBundle;

    /**
     * <p>
     * The late deliverable persistence instance used by this class for updating late deliverables in persistence./p>
     *
     * <p>
     * Is initialized in the constructor and never changed after that. Cannot be null. Is used in update().
     * </p>
     */
    @Autowired
    private LateDeliverablePersistence persistence;
    @Autowired private SearchBundleManager searchBundleManager;

    @PostConstruct
    public void postRun() {
        nonRestrictedSearchBundle = searchBundleManager.getSearchBundle(KEY_NON_RESTRICTED_SB_NAME);
        restrictedSearchBundle = searchBundleManager.getSearchBundle(KEY_RESTRICTED_SB_NAME);
    }

    /**
     * <p>
     * Updates the given late deliverable in persistence.
     * </p>
     *
     * <p>
     * <em>Changes in version 1.0.6:</em>
     * <ol>
     * <li>Updated throws documentation for IllegalArgumentException.</li>
     * </ol>
     * </p>
     *
     * @param lateDeliverable
     *            the late deliverable with updated data.
     *
     * @throws IllegalArgumentException
     *             if lateDeliverable is null, lateDeliverable.getId() &lt;= 0, lateDeliverable.getDeadline() is null,
     *             lateDeliverable.getCreateDate() is null, lateDeliverable.getType() is null,
     *             lateDeliverable.getType().getId() &lt;= 0.
     * @throws LateDeliverableNotFoundException
     *             if late deliverable with ID equal to lateDeliverable.getId() doesn't exist in persistence.
     * @throws LateDeliverablePersistenceException
     *             if some other error occurred when accessing the persistence.
     */
    public void update(LateDeliverable lateDeliverable) throws LateDeliverablePersistenceException {
        Date enterTimestamp = new Date();
        String signature = getSignature("update(LateDeliverable lateDeliverable)");

        try {
            // Log method entry
            Helper.logEntrance(log, signature,
                new String[] {"lateDeliverable"},
                new Object[] {lateDeliverable});

            // Delegate execution to persistence
            persistence.update(lateDeliverable);

            // Log method exit
            Helper.logExit(log, signature, null, enterTimestamp);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        } catch (LateDeliverableNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "LateDeliverableNotFoundException is thrown"
                + " when updating the given late deliverable in persistence.");
        } catch (LateDeliverablePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "LateDeliverablePersistenceException is thrown"
                + " when updating the given late deliverable in persistence.");
        }
    }

    /**
     * <p>
     * Retrieves the late deliverable with the given ID.
     * </p>
     *
     * <p>
     * <em>Changes in version 1.0.6:</em>
     * <ol>
     * <li>The late deliverable types will be retrieved.</li>
     * </ol>
     * </p>
     *
     * @param lateDeliverableId
     *            the ID of the late deliverable to be retrieved.
     *
     * @return the retrieved late deliverable with the given ID (or null if late deliverable with the given ID doesn't
     *         exist).
     *
     * @throws IllegalArgumentException
     *             if lateDeliverableId &lt;= 0.
     * @throws LateDeliverablePersistenceException
     *             if some other error occurred when accessing the persistence.
     * @throws LateDeliverableManagementException
     *             if some other error occurred.
     */
    public LateDeliverable retrieve(long lateDeliverableId) throws LateDeliverableManagementException {
        Date enterTimestamp = new Date();
        String signature = getSignature("retrieve(long lateDeliverableId)");

        try {
            // Log method entry
            Helper.logEntrance(log, signature,
                new String[] {"lateDeliverableId"},
                new Object[] {lateDeliverableId});

            Helper.checkPositive(lateDeliverableId, "lateDeliverableId");

            // Create a filter for matching late deliverable ID:
            Filter filter = new EqualToFilter("id", lateDeliverableId);
            // Search for late deliverable with the specified ID:
            List<LateDeliverable> lateDeliverables = searchAllLateDeliverables(filter);

            LateDeliverable result = lateDeliverables.isEmpty() ? null : lateDeliverables.get(0);

            // Log method exit
            Helper.logExit(log, signature, new Object[] {result}, enterTimestamp);

            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        } catch (LateDeliverablePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "LateDeliverablePersistenceException is thrown"
                + " when retrieving the late deliverable.");
        } catch (LateDeliverableManagementException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "LateDeliverableManagementException is thrown"
                + " when retrieving the late deliverable.");
        }
    }

    /**
     * <p>
     * Searches for all late deliverables that are matched with the given filter. Returns an empty list if none found.
     * </p>
     *
     * <p>
     * <em>Changes in version 1.0.6:</em>
     * <ol>
     * <li>The late deliverable types will be retrieved.</li>
     * </ol>
     * </p>
     *
     * @param filter
     *            the filter for late deliverables (null if all late deliverables need to be retrieved).
     *
     * @return the list with found late deliverables that are matched with the given filter (not null, doesn't contain
     *         null).
     *
     * @throws LateDeliverablePersistenceException
     *             if some other error occurred when accessing the persistence.
     * @throws LateDeliverableManagementException
     *             if some other error occurred.
     */
    public List<LateDeliverable> searchAllLateDeliverables(Filter filter) throws LateDeliverableManagementException {
        Date enterTimestamp = new Date();
        String signature = getSignature("searchAllLateDeliverables(Filter filter)");

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"filter"},
            new Object[] {filter});

        try {
            if (filter == null) {
                // Create filter that matches all records:
                filter = new NotFilter(new NullFilter("deliverableId"));
            }
            // Get late deliverables using Search Builder:
            List<LateDeliverable> result = getLateDeliverables(nonRestrictedSearchBundle, filter);

            // Log method exit
            Helper.logExit(log, signature, new Object[] {result}, enterTimestamp);

            return result;
        } catch (LateDeliverablePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "LateDeliverablePersistenceException is thrown"
                + " when searching for late deliverables.");
        } catch (LateDeliverableManagementException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "LateDeliverableManagementException is thrown"
                + " when searching for late deliverables.");
        }
    }

    /**
     * <p>
     * Searches for all late deliverables that are matched with the given filter checking whether the user with the
     * specified ID has owner, manager or cockpit project access to the deliverables. Returns an empty list if none
     * found.
     * </p>
     *
     * <p>
     * <em>Changes in version 1.0.6:</em>
     * <ol>
     * <li>The late deliverable types will be retrieved.</li>
     * </ol>
     * </p>
     *
     * @param userId
     *            the ID of the user.
     * @param filter
     *            the filter for late deliverables (null if deliverables must be filtered by user only).
     *
     * @return the list with found late deliverables that are matched with the given filter and accessed by the
     *         specified user (not null, doesn't contain null).
     *
     * @throws IllegalArgumentException
     *             if userId &lt;= 0.
     * @throws LateDeliverablePersistenceException
     *             if some other error occurred when accessing the persistence.
     * @throws LateDeliverableManagementException
     *             if some other error occurred.
     */
    public List<LateDeliverable> searchRestrictedLateDeliverables(Filter filter, long userId)
        throws LateDeliverableManagementException {
        Date enterTimestamp = new Date();
        String signature = getSignature("searchRestrictedLateDeliverables(Filter filter, long userId)");

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"filter", "userId"},
            new Object[] {filter, userId});

        try {
            Helper.checkPositive(userId, "userId");

            // Create filter for matching manager user ID:
            Filter managerUserIdFilter = new EqualToFilter("managerUserId", userId);
            // Create filter for matching late user ID:
            Filter lateUserIdFilter = new EqualToFilter("lateUserId", userId);
            // Create filter for matching ID of user that has access to the project from TC Direct:
            Filter tcDirectUserIdFilter = new EqualToFilter("tcDirectUserId", userId);

            // Create a composite filter for matching user access:
            Filter userIdFilter = new OrFilter(new OrFilter(managerUserIdFilter, lateUserIdFilter),
                tcDirectUserIdFilter);

            Filter compositeFilter;
            if (filter != null) {
                // Create a general composite filter:
                compositeFilter = new AndFilter(userIdFilter, filter);
            } else {
                compositeFilter = userIdFilter;
            }

            // Get late deliverables using Search Builder:
            List<LateDeliverable> result = getLateDeliverables(restrictedSearchBundle, compositeFilter);

            // Log method exit
            Helper.logExit(log, signature, new Object[] {result}, enterTimestamp);

            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        } catch (LateDeliverablePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "LateDeliverablePersistenceException is thrown"
                + " when searching for late deliverables.");
        } catch (LateDeliverableManagementException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "LateDeliverableManagementException is thrown"
                + " when searching for late deliverables.");
        }
    }

    /**
     * <p>
     * Retrieves all existing late deliverable types.
     * </p>
     *
     * @return the retrieved late deliverable types (not null, doesn't contain null).
     *
     * @throws LateDeliverablePersistenceException
     *             if some error occurred when accessing the persistence.
     *
     * @since 1.0.6
     */
    public List<LateDeliverableType> getLateDeliverableTypes() throws LateDeliverablePersistenceException {
        Date enterTimestamp = new Date();
        String signature = getSignature("getLateDeliverableTypes()");

        try {
            // Log method entry
            Helper.logEntrance(log, signature, null, null);

            List<LateDeliverableType> result = persistence.getLateDeliverableTypes();

            // Log method exit
            Helper.logExit(log, signature, new Object[] {result}, enterTimestamp);

            return result;
        } catch (LateDeliverablePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "LateDeliverablePersistenceException is thrown"
                + " when retrieving all existing late deliverable types.");
        }
    }

    /**
     * <p>
     * Retrieves the list of late deliverables with the search bundle and filter.
     * </p>
     *
     * <p>
     * <em>Changes in version 1.0.6:</em>
     * <ol>
     * <li>Added steps to retrieve the late deliverable type.</li>
     * </ol>
     * </p>
     *
     * @param searchBundle
     *            the search bundle.
     * @param filter
     *            the filter.
     *
     * @return the retrieved late deliverables (not null, doesn't contain null).
     *
     * @throws LateDeliverablePersistenceException
     *             if some error occurred when retrieving late deliverables.
     * @throws LateDeliverableManagementException
     *             if some other error occurred.
     */
    private static List<LateDeliverable> getLateDeliverables(SearchBundle searchBundle, Filter filter)
        throws LateDeliverableManagementException {

        // Perform the search using Search Builder:
        List<Map<String, Object>> resultSet = searchBundle.search(filter);

        // Create a list for result
        List<LateDeliverable> result = new ArrayList<LateDeliverable>();
        // Create a map for cached late deliverable types:
        Map<Long, LateDeliverableType> lateDeliverableTypes = new HashMap<Long, LateDeliverableType>();

        for (Map<String, Object> row: resultSet) {
            // Create late deliverable instance:
            LateDeliverable lateDeliverable = new LateDeliverable();

            int index = 1;

            // Copy ID to the late deliverable instance:
            lateDeliverable.setId(getLong(row, "late_deliverable_id"));
            // Copy project ID from the result set to the late deliverable instance:
            lateDeliverable.setProjectId(getLong(row, "project_id"));
            // Copy project phase ID from the result set to the late deliverable instance:
            lateDeliverable.setProjectPhaseId(getLong(row, "project_phase_id"));
            // Copy resource ID from the result set to the late deliverable instance:
            lateDeliverable.setResourceId(getLong(row, "resource_id"));
            // Copy deliverable ID from the result set to the late deliverable instance:
            lateDeliverable.setDeliverableId(getLong(row, "deliverable_id"));
            // Copy deadline from the result set to the late deliverable instance:
            lateDeliverable.setDeadline(getDate(row, "deadline"));
            // Copy compensated deadline from the result set to the late deliverable instance:
            lateDeliverable.setCompensatedDeadline(getDate(row, "compensated_deadline"));
            // Copy creation date from the result set to the late deliverable instance:
            lateDeliverable.setCreateDate(getDate(row, "create_date"));

            int forgiven = getInt(row, "forgive_ind");
            // Copy forgiven flag to the late deliverable instance:
            lateDeliverable.setForgiven((forgiven == 0) ? false : true);

            // Copy last notification date from the result set to the late deliverable instance:
            lateDeliverable.setLastNotified(getDate(row, "last_notified"));
            // Copy delay from the result set to the late deliverable instance:
            lateDeliverable.setDelay(getLong(row, "delay"));
            // Copy explanation from the result set to the late deliverable instance:
            lateDeliverable.setExplanation(getString(row, "explanation"));
            // Copy explanation date from the result set to the late deliverable instance:
            lateDeliverable.setExplanationDate(getDate(row, "explanation_date"));
            // Copy response from the result set to the late deliverable instance:
            lateDeliverable.setResponse(getString(row, "response"));
            // Copy response user from the result set to the late deliverable instance:
            lateDeliverable.setResponseUser(getString(row, "response_user"));
            // Copy response date from the result set to the late deliverable instance:
            lateDeliverable.setResponseDate(getDate(row, "response_date"));

            // Get late deliverable type ID from the result set:
            long lateDeliverableTypeId = getLong(row, "late_deliverable_type_id");
            // Get cached late deliverable type from the map:
            LateDeliverableType lateDeliverableType = lateDeliverableTypes.get(lateDeliverableTypeId);
            if (lateDeliverableType == null) {
                // Create new late deliverable type instance:
                lateDeliverableType = new LateDeliverableType();
                // Set ID to the late deliverable type:
                lateDeliverableType.setId(lateDeliverableTypeId);
                // Copy late deliverable type name from the result set to the late deliverable type instance:
                lateDeliverableType.setName(getString(row, "name"));
                // Copy late deliverable type description from the result set to the late deliverable type
                // instance:
                lateDeliverableType.setDescription(getString(row, "description"));


                lateDeliverableTypes.put(lateDeliverableTypeId, lateDeliverableType);
            }
            // Set late deliverable type to the late deliverable instance:
            lateDeliverable.setType(lateDeliverableType);

            // Add late deliverable to the list:
            result.add(lateDeliverable);
        }

        return result;
    }

    /**
     * <p>
     * Validates the value of a string. The value can not be <code>null</code> or an empty string.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     *
     * @throws IllegalArgumentException
     *             if the given string is <code>null</code> or an empty string.
     */
    private static void checkNullOrEmpty(String value, String name) {
        Helper.checkNull(value, name);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException("'" + name + "' should not be an empty string.");
        }
    }

    /**
     * <p>
     * Gets the signature for given method for logging.
     * </p>
     *
     * @param method
     *            the method name.
     *
     * @return the signature for given method.
     */
    private static String getSignature(String method) {
        return Helper.concat(CLASS_NAME, Helper.DOT, method);
    }
}
