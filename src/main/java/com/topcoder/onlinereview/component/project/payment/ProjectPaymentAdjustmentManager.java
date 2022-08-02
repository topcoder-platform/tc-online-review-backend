/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * This class is an implementation of ProjectPaymentAdjustmentManager that uses pluggable
 * ProjectPaymentAdjustmentPersistence instance for all operations in persistence. This class uses Logging Wrapper
 * component to log errors and debug information.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot;?&gt;
 * &lt;CMConfig&gt;
 *   &lt;Config name=&quot;com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl&quot;&gt;
 *     &lt;Property name=&quot;loggerName&quot;&gt;
 *       &lt;Value&gt;myLogger&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;objectFactoryConfig&quot;&gt;
 *       &lt;Property name=&quot;DatabaseProjectPaymentAdjustmentPersistence&quot;&gt;
 *         &lt;Property name=&quot;type&quot;&gt;
 *           &lt;Value&gt;
 *               com.topcoder.management.payment.impl.persistence.DatabaseProjectPaymentAdjustmentPersistence
 *           &lt;/Value&gt;
 *         &lt;/Property&gt;
 *       &lt;/Property&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;projectPaymentAdjustmentPersistenceKey&quot;&gt;
 *       &lt;Value&gt;DatabaseProjectPaymentAdjustmentPersistence&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;projectPaymentAdjustmentPersistenceConfig&quot;&gt;
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
 *                   &lt;Value&gt;jdbc:informix-sqli://localhost:1526/test:informixserver=ol_topcoder&lt;/Value&gt;
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
 * <pre>
 * // Create an instance of ProjectPaymentAdjustmentManagerImpl using configuration
 * ConfigurationObject configuration = getConfig(ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
 * ProjectPaymentAdjustmentManagerImpl projectPaymentAdjustmentManager = new ProjectPaymentAdjustmentManagerImpl(
 *     configuration);
 *
 * // Retrieve the project payment adjustments with project ID=1001
 * List&lt;ProjectPaymentAdjustment&gt; projectPaymentAdjustments =
 *     projectPaymentAdjustmentManager.retrieveByProjectId(1001);
 * // projectPaymentAdjustmentss.size() must be 1
 * // project id must be 1001
 * // resource role id must be 4
 * // fixed amount must be 50
 * // etc.
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe when entities passed to it are used by the
 * caller in thread safe manner. It uses thread safe SearchBundle, ProjectPaymentPersistence and Log instances.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0
 */
@Slf4j
@Component
public class ProjectPaymentAdjustmentManager {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = ProjectPaymentAdjustmentManager.class.getName();


    /**
     * The project payment adjustment persistence instance used by this class for all operations. Initialized in
     * the constructor and never changed after that. Cannot be null. Used in all methods.
     */
    @Autowired
    private ProjectPaymentAdjustmentPersistence persistence;

    /**
     * Creates or updates the given project payment adjustment in persistence.
     *
     * @param projectPaymentAdjustment
     *            the project payment adjustment to create or update
     *
     * @throws IllegalArgumentException
     *             if projectPaymentAdjustment is null
     * @throws ProjectPaymentAdjustmentValidationException
     *             if projectPaymentAdjustment's project id is null, projectPaymentAdjustment's resource role id is
     *             null, projectPaymentAdjustment's fixed amount is negative, projectPaymentAdjustment's multiplier is
     *             negative, if both fixed amount and multiplier are non-null at the same time.
     * @throws ProjectPaymentManagementPersistenceException
     *             if some other error occurred when accessing the persistence for example.
     */
    public void save(ProjectPaymentAdjustment projectPaymentAdjustment)
        throws ProjectPaymentAdjustmentValidationException, ProjectPaymentManagementPersistenceException {
        String signature = CLASS_NAME + ".save(ProjectPaymentAdjustment projectPaymentAdjustment)";

        // Log Entrance
        Helper.logEntrance(log, signature,
            new String[] {"projectPaymentAdjustment"},
            new Object[] {projectPaymentAdjustment.toString()});

        try {
            persistence.save(projectPaymentAdjustment);
            // Log Exit
            Helper.logExit(log, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (ProjectPaymentAdjustmentValidationException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (ProjectPaymentManagementPersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Retrieves the given project payment adjustments from persistence by project id.
     *
     * @param projectId
     *            the project id of the project payment adjustment
     *
     * @return the list or project payment adjustments matching the criteria, empty list if nothing was found.
     *
     * @throws ProjectPaymentManagementPersistenceException
     *             if some error occurred when accessing the persistence for example.
     */
    public List<ProjectPaymentAdjustment> retrieveByProjectId(long projectId)
        throws ProjectPaymentManagementPersistenceException {
        String signature = CLASS_NAME + ".retrieveByProjectId(long projectId)";

        // Log Entrance
        Helper.logEntrance(log, signature,
            new String[] {"projectId"},
            new Object[] {projectId});

        try {
            List<ProjectPaymentAdjustment> result = persistence.retrieveByProjectId(projectId);

            // Log Exit
            Helper.logExit(log, signature, new Object[] {result.toString()});
            return result;
        } catch (ProjectPaymentManagementPersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }
}
