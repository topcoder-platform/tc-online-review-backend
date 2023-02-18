/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.termsofuse;

import com.topcoder.onlinereview.component.grpcclient.termsofuse.TermsOfUseServiceRpc;
import com.topcoder.onlinereview.grpc.termsofuse.proto.GetTermsOfUseResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * This class is the default implementation of TermsOfUseDao. It utilizes the DB Connection Factory to get access to
 * the database. The configuration is done by the Configuration API.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;CMConfig&gt;
 *     &lt;Config name=&quot;termsOfUseDao&quot;&gt;
 *      &lt;Property name=&quot;dbConnectionFactoryConfig&quot;&gt;
 *        &lt;Property name=&quot;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&quot;&gt;
 *          &lt;Property name=&quot;connections&quot;&gt;
 *                 &lt;Property name=&quot;default&quot;&gt;
 *                     &lt;Value&gt;InformixJDBCConnection&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *              &lt;Property name=&quot;InformixJDBCConnection&quot;&gt;
 *                  &lt;Property name=&quot;producer&quot;&gt;
 *                      &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *                  &lt;/Property&gt;
 *                  &lt;Property name=&quot;parameters&quot;&gt;
 *                      &lt;Property name=&quot;jdbc_driver&quot;&gt;
 *                      &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name=&quot;jdbc_url&quot;&gt;
 *                              &lt;Value&gt;
 *                                  jdbc:informix-sqli://localhost:1526/common_oltp:informixserver=ol_topcoder
 *                              &lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name=&quot;user&quot;&gt;
 *                          &lt;Value&gt;informix&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name=&quot;password&quot;&gt;
 *                          &lt;Value&gt;123456&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                  &lt;/Property&gt;
 *              &lt;/Property&gt;
 *          &lt;/Property&gt;
 *        &lt;/Property&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;loggerName&quot;&gt;
 *          &lt;Value&gt;loggerName&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;idGeneratorName&quot;&gt;
 *          &lt;Value&gt;idGenerator&lt;/Value&gt;
 *      &lt;/Property&gt;
 *     &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Create the configuration object
 * ConfigurationObject configurationObject = TestsHelper.getConfig(TestsHelper.CONFIG_TERMS);
 * // Instantiate the dao implementation from configuration defined above
 * TermsOfUseDao termsOfUseDao = new TermsOfUseDaoImpl(configurationObject);
 *
 * // Create simple TermsOfUse to persist
 * TermsOfUse terms = new TermsOfUse();
 *
 * terms.setTermsOfUseTypeId(3);
 * terms.setTitle(&quot;t5&quot;);
 * terms.setUrl(&quot;url5&quot;);
 * TermsOfUseAgreeabilityType nonAgreeableType = new TermsOfUseAgreeabilityType();
 * nonAgreeableType.setTermsOfUseAgreeabilityTypeId(1);
 * terms.setAgreeabilityType(nonAgreeableType);
 *
 * // Persist the TermsOfUse
 * terms = termsOfUseDao.createTermsOfUse(terms, &quot;&quot;);
 *
 * // Set terms of use text
 * termsOfUseDao.setTermsOfUseText(terms.getTermsOfUseId(), &quot;text5&quot;);
 *
 * // Get terms of use text. This will return &quot;text5&quot;.
 * String termsOfUseText = termsOfUseDao.getTermsOfUseText(terms.getTermsOfUseId());
 *
 * // Update some information for TermsOfUse
 * TermsOfUseAgreeabilityType electronicallyAgreeableType = new TermsOfUseAgreeabilityType();
 * electronicallyAgreeableType.setTermsOfUseAgreeabilityTypeId(3);
 * terms.setAgreeabilityType(electronicallyAgreeableType);
 *
 * // And update the TermsOfUse
 * terms = termsOfUseDao.updateTermsOfUse(terms);
 *
 * // Retrieve some terms of use. The third row will be returned
 * terms = termsOfUseDao.getTermsOfUse(3);
 * // terms.getAgreeabilityType().getTermsOfUseAgreeabilityTypeId() must be 1
 * // terms.getAgreeabilityType().getName() must be &quot;Non-agreeable&quot;
 *
 * // Delete terms of use
 * termsOfUseDao.deleteTermsOfUse(5);
 *
 * // Retrieve all terms of use. All rows will be returned
 * List&lt;TermsOfUse&gt; allTerms = termsOfUseDao.getAllTermsOfUse();
 *
 * // Create the following dependency relationships between terms of use with the specified IDs:
 * // (1) depends on (2)
 * // (2) depends on (3,4)
 * // (3) depends on (4)
 * termsOfUseDao.createDependencyRelationship(1, 2);
 * termsOfUseDao.createDependencyRelationship(2, 3);
 * termsOfUseDao.createDependencyRelationship(2, 4);
 * termsOfUseDao.createDependencyRelationship(3, 4);
 *
 * try {
 *     // Try to make a loop; TermsOfUseCyclicDependencyException must be thrown
 *     termsOfUseDao.createDependencyRelationship(4, 1);
 * } catch (TermsOfUseCyclicDependencyException e) {
 *     // Good
 * }
 *
 * // Retrieve the dependencies of terms of use with ID=2
 * List&lt;TermsOfUse&gt; termsOfUseList = termsOfUseDao.getDependencyTermsOfUse(2);
 * // termsOfUseList.size() must be 2
 * // termsOfUseList.get(0).getTermsOfUseId() must be 3
 * // termsOfUseList.get(0).getTermsOfUseTypeId() must be 1
 * // termsOfUseList.get(0).getTitle() must be &quot;t3&quot;
 * // termsOfUseList.get(0).getUrl() must be &quot;url3&quot;
 * // termsOfUseList.get(0).getAgreeabilityType().getTermsOfUseAgreeabilityTypeId() must be 1
 * // termsOfUseList.get(0).getAgreeabilityType().getName() must be &quot;Non-agreeable&quot;
 * // termsOfUseList.get(0).getAgreeabilityType().getDescription() must be &quot;Non-agreeable&quot;
 * // termsOfUseList.get(1).getTermsOfUseId() must be 4
 * // termsOfUseList.get(1).getTermsOfUseTypeId() must be 2
 * // termsOfUseList.get(1).getTitle() must be &quot;t4&quot;
 * // termsOfUseList.get(1).getUrl() must be &quot;url4&quot;
 * // termsOfUseList.get(1).getAgreeabilityType().getTermsOfUseAgreeabilityTypeId() must be 3
 * // termsOfUseList.get(1).getAgreeabilityType().getName() must be &quot;Electronically-agreeable&quot;
 * // termsOfUseList.get(1).getAgreeabilityType().getDescription() must be &quot;Electronically-agreeable&quot;
 * // Note: the order of elements in termsOfUseList can vary
 *
 * // Retrieve the dependents of terms of use with ID=2
 * termsOfUseList = termsOfUseDao.getDependentTermsOfUse(2);
 * // termsOfUseList.size() must be 1
 * // termsOfUseList.get(0).getTermsOfUseId() must be 1
 * // termsOfUseList.get(0).getTermsOfUseTypeId() must be 1
 * // termsOfUseList.get(0).getTitle() must be &quot;t1&quot;
 * // termsOfUseList.get(0).getUrl() must be &quot;url1&quot;
 * // termsOfUseList.get(0).getAgreeabilityType().getTermsOfUseAgreeabilityTypeId() must be 2
 * // termsOfUseList.get(0).getAgreeabilityType().getName() must be &quot;Non-electronically-agreeable&quot;
 * // termsOfUseList.get(0).getAgreeabilityType().getDescription() must be &quot;Non-electronically-agreeable&quot;
 *
 * // Delete the dependency relationship between terms of use with IDs=2,4
 * termsOfUseDao.deleteDependencyRelationship(2, 4);
 *
 * // Delete all dependency relationships where terms of use with ID=2 is a dependent
 * termsOfUseDao.deleteAllDependencyRelationshipsForDependent(2);
 *
 * // Delete all dependency relationships where terms of use with ID=4 is a dependency
 * termsOfUseDao.deleteAllDependencyRelationshipsForDependency(4);
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Changes in 1.1:</em>
 * <ol>
 * <li>Added methods for managing terms of use dependencies.</li>
 * <li>Updated queries to support removal of memberAgreeable and electronicallySignable properties of TermsOfUse.</li>
 * <li>Updated queries to support adding of TermsOfUse#agreeabilityType property.</li>
 * </ol>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The class is immutable and thread - safe. createDependencyRelationship() is made
 * synchronized to avoid creation of cyclic dependency by calling this method from multiple threads at a time.
 * </p>
 *
 * @author faeton, sparemax, saarixx
 * @version 1.1
 */
@Slf4j
@Component
public class TermsOfUseDao {
    @Autowired
    TermsOfUseServiceRpc termsOfUseServiceRpc;
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = TermsOfUseDao.class.getName();

    /**
     * <p>
     * Creates terms of use entity with the terms text.
     * </p>
     *
     * <p>
     * <em>Changes in 1.1:</em>
     * <ol>
     * <li>Throws IllegalArgumentException when termsOfUse.getAgreeabilityType() is null.</li>
     * <li>Support removal of memberAgreeable and electronicallySignable properties.</li>
     * <li>Support adding of agreeabilityType property.</li>
     * </ol>
     * </p>
     *
     * @param termsOfUse
     *            a TermsOfUse containing required information for creation.
     * @param termsText
     *            the terms text to create.
     *
     * @return a TermsOfUse with created id attribute.
     *
     * @throws IllegalArgumentException
     *             if termsOfUseor or termsOfUse.getAgreeabilityType() is null.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUse createTermsOfUse(TermsOfUse termsOfUse, String termsText) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".createTermsOfUse(TermsOfUse termsOfUse, String termsText)";
        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUse", "termsText"},
            new Object[] {termsOfUse, termsText});

        try {
            Helper.checkNull(termsOfUse, "termsOfUse");

            TermsOfUseAgreeabilityType agreeabilityType = termsOfUse.getAgreeabilityType();
            Helper.checkNull(agreeabilityType, "termsOfUse.getAgreeabilityType()");
            
            long termsOfUseId = termsOfUseServiceRpc.createTermsOfUse(termsOfUse, termsText);
            termsOfUse.setTermsOfUseId(termsOfUseId);

            // Log method exit
            Helper.logExit(log, signature, new Object[] {termsOfUse});
            return termsOfUse;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * <p>
     * Updates terms of use entity.
     * </p>
     *
     * <p>
     * <em>Changes in 1.1:</em>
     * <ol>
     * <li>Throws IllegalArgumentException when termsOfUse.getAgreeabilityType() is null.</li>
     * <li>Support removal of memberAgreeable and electronicallySignable properties.</li>
     * <li>Support adding of agreeabilityType property.</li>
     * </ol>
     * </p>
     *
     * @param termsOfUse
     *            a TermsOfUse containing required information for update.
     *
     * @return a TermsOfUse with updated id attribute.
     *
     * @throws IllegalArgumentException
     *             if termsOfUse or termsOfUse.getAgreeabilityType() is null.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUse updateTermsOfUse(TermsOfUse termsOfUse) throws TermsOfUsePersistenceException,
        EntityNotFoundException {
        String signature = CLASS_NAME + ".updateTermsOfUse(TermsOfUse termsOfUse)";
        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUse"},
            new Object[] {termsOfUse});

        try {
            Helper.checkNull(termsOfUse, "termsOfUse");

            TermsOfUseAgreeabilityType agreeabilityType = termsOfUse.getAgreeabilityType();
            Helper.checkNull(agreeabilityType, "termsOfUse.getAgreeabilityType()");

            int num = termsOfUseServiceRpc.updateTermsOfUse(termsOfUse);
            if (num != 1) {
                throw new EntityNotFoundException("The entity was not found for id (" + Long.toString(termsOfUse.getTermsOfUseId()) + ").");
            }
            // Log method exit
            Helper.logExit(log, signature, new Object[] {termsOfUse});
            return termsOfUse;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Retrieves a terms of use entity from the database.
     *
     * @param termsOfUseId
     *            a long containing the terms of use id to retrieve.
     *
     * @return a TermsOfUse with the requested terms of use or null if not found.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUse getTermsOfUse(long termsOfUseId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getTermsOfUse(long termsOfUseId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseId"},
            new Object[] {termsOfUseId});

        try {
            GetTermsOfUseResponse rs = termsOfUseServiceRpc.getTermsOfUse(termsOfUseId);
            TermsOfUse terms = null;
            if (rs != null) {
                terms = Helper.getTermsOfUse(rs);
            }
            // Log method exit
            Helper.logExit(log, signature, new Object[] {terms});
            return terms;
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Deletes a terms of use entity from the database.
     *
     * @param termsOfUseId
     *            a long containing the terms of use id to delete.
     *
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public void deleteTermsOfUse(long termsOfUseId) throws EntityNotFoundException, TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".deleteTermsOfUse(long termsOfUseId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseId"},
            new Object[] {termsOfUseId});

        try {
            int num = termsOfUseServiceRpc.deleteTermsOfUse(termsOfUseId);
            if (num != 1) {
                throw new EntityNotFoundException("The entity was not found for id (" + Long.toString(termsOfUseId) + ").");
            }
            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Retrieves a terms of use entities by the terms of use type id from the database.
     *
     * @param termsOfUseTypeId
     *            an int containing the terms of use type id to retrieve.
     *
     * @return a list of TermsOfUse entities with the requested terms of use or empty list if not found.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public List<TermsOfUse> getTermsOfUseByTypeId(int termsOfUseTypeId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getTermsOfUseByTypeId(int termsOfUseTypeId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseTypeId"},
            new Object[] {termsOfUseTypeId});
        try {
            List<GetTermsOfUseResponse> response = termsOfUseServiceRpc.getTermsOfUseByTypeId(termsOfUseTypeId);
            List<TermsOfUse> result = new ArrayList<TermsOfUse>();
            for (GetTermsOfUseResponse r : response) {
                result.add(Helper.getTermsOfUse(r));
            }
            // Log method exit
            Helper.logExit(log, signature, new Object[] {result});
            return result;
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Retrieves all terms of use entities from the database.
     *
     * @return a list of all TermsOfUse entities.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public List<TermsOfUse> getAllTermsOfUse() throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getAllTermsOfUse()";

        // Log method entry
        Helper.logEntrance(log, signature, null, null);

        try {
            List<GetTermsOfUseResponse> response = termsOfUseServiceRpc.getAllTermsOfUse();
            List<TermsOfUse> result = new ArrayList<TermsOfUse>();
            for (GetTermsOfUseResponse r : response) {
                result.add(Helper.getTermsOfUse(r));
            }
            // Log method exit
            Helper.logExit(log, signature, new Object[] {result});
            return result;
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Gets terms of use type by id.
     *
     * @param termsOfUseTypeId
     *            terms of use type id.
     *
     * @return terms of use type.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUseType getTermsOfUseType(int termsOfUseTypeId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getTermsOfUseType(int termsOfUseTypeId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseTypeId"},
            new Object[] {termsOfUseTypeId});

        try {
            TermsOfUseType termsType = termsOfUseServiceRpc.getTermsOfUseType(termsOfUseTypeId);
            // Log method exit
            Helper.logExit(log, signature, new Object[] {termsType});
            return termsType;
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Update terms of use type.
     *
     * @param termsType
     *            the terms of use type to be updated.
     *
     * @return updated terms of use type.
     *
     * @throws IllegalArgumentException
     *             if termsType is null.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public TermsOfUseType updateTermsOfUseType(TermsOfUseType termsType) throws TermsOfUsePersistenceException,
        EntityNotFoundException {
        String signature = CLASS_NAME + ".updateTermsOfUseType(TermsOfUseType termsType)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsType"},
            new Object[] {termsType});

        try {
            Helper.checkNull(termsType, "termsType");
            int num = termsOfUseServiceRpc.updateTermsOfUseType(termsType);
            if (num != 1) {
                throw new EntityNotFoundException("The entity was not found for id (" + Long.toString(termsType.getTermsOfUseTypeId()) + ").");
            }
            // Log method exit
            Helper.logExit(log, signature, new Object[] {termsType});
            return termsType;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Gets terms of use text by terms of use id.
     *
     * @param termsOfUseId
     *            terms of use id.
     *
     * @return text of terms of use.
     *
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public String getTermsOfUseText(long termsOfUseId) throws TermsOfUsePersistenceException, EntityNotFoundException {
        String signature = CLASS_NAME + ".getTermsOfUseText(long termsOfUseId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseId"},
            new Object[] {termsOfUseId});

        try {
            String termsText = termsOfUseServiceRpc.getTermsOfUseText(termsOfUseId);
            if (termsText == null) {
                throw new EntityNotFoundException("The entity was not found for id  (" + termsOfUseId + ").");
            }

            // Log method exit
            Helper.logExit(log, signature, new Object[] {termsText});
            return termsText;
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Sets terms of use text.
     *
     * @param termsOfUseId
     *            terms of use id.
     * @param text
     *            text of terms of use.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     */
    public void setTermsOfUseText(long termsOfUseId, String text) throws TermsOfUsePersistenceException,
        EntityNotFoundException {
        String signature = CLASS_NAME + ".setTermsOfUseText(long termsOfUseId, String text)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseId", "text"},
            new Object[] {termsOfUseId, text});

        try {
            int num = termsOfUseServiceRpc.setTermsOfUseText(termsOfUseId, text);
            if (num != 1) {
                throw new EntityNotFoundException("The entity was not found for id (" + Long.toString(termsOfUseId) + ").");
            }
            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }
    /**
     * <p>
     * Creates a dependency relationship between two terms of use. This method ensures that a dependency loop is not
     * created. This method must be synchronized to avoid creation of cyclic dependency by calling this method from
     * multiple threads at a time (e.g. when dependency A->B exists, and two dependencies B->C and C->A are created at
     * the same time).
     * </p>
     *
     * <p>
     * This method throws TermsOfUseCyclicDependencyException when dependencyTermsOfUseId is equal to
     * dependentTermsOfUseId or if any direct/indirect dependency of dependencyTermsOfUseId depends on
     * dependentTermsOfUseId.
     * </p>
     *
     * @param dependencyTermsOfUseId
     *            the ID of the dependency terms of use
     * @param dependentTermsOfUseId
     *            the ID of the dependent terms of use
     *
     * @throws TermsOfUseCyclicDependencyException
     *             if creation of the specified relationship will lead to a dependency loop.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     *
     * @since 1.1
     */
    public synchronized void createDependencyRelationship(long dependentTermsOfUseId, long dependencyTermsOfUseId)
        throws TermsOfUseCyclicDependencyException, TermsOfUsePersistenceException {
        String signature = CLASS_NAME
            + ".createDependencyRelationship(long dependentTermsOfUseId, long dependencyTermsOfUseId)";
        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"dependentTermsOfUseId", "dependencyTermsOfUseId"},
            new Object[] {dependentTermsOfUseId, dependencyTermsOfUseId});

        try {

            if (dependencyTermsOfUseId == dependentTermsOfUseId) {
                throw new TermsOfUseCyclicDependencyException(
                    "Creation of the specified relationship will lead to a dependency loop.");
            }
            // Check dependency loop
            checkDependencyLoop(dependentTermsOfUseId, dependencyTermsOfUseId);

            // Insert the dependency
            termsOfUseServiceRpc.createDependencyRelationship(dependentTermsOfUseId, dependencyTermsOfUseId);

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (TermsOfUseCyclicDependencyException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Retrieves all dependency terms of use for the dependent terms of use with the given ID. Returns an empty list
     * if none found.
     *
     * @param dependentTermsOfUseId
     *            the ID of the dependent terms of use
     * @return the retrieved dependency terms of use (not null; doesn't contain null)
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     *
     * @since 1.1
     */
    public List<TermsOfUse> getDependencyTermsOfUse(long dependentTermsOfUseId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getDependencyTermsOfUse(long dependentTermsOfUseId)";
        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"dependentTermsOfUseId"},
            new Object[] {dependentTermsOfUseId});

        try {
            List<GetTermsOfUseResponse> response = termsOfUseServiceRpc.getDependencyTermsOfUse(dependentTermsOfUseId);
            List<TermsOfUse> result = new ArrayList<TermsOfUse>();
            for (GetTermsOfUseResponse r : response) {
                result.add(Helper.getTermsOfUse(r));
            }
            // Log method exit
            Helper.logExit(log, signature, new Object[] {result});
            return result;
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Retrieves all dependent terms of use for the dependency terms of use with the given ID. Returns an empty list
     * if none found.
     *
     * @param dependencyTermsOfUseId
     *            the ID of the dependency terms of use
     * @return the retrieved dependent terms of use (not null; doesn't contain null)
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     *
     * @since 1.1
     */
    public List<TermsOfUse> getDependentTermsOfUse(long dependencyTermsOfUseId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getDependentTermsOfUse(long dependencyTermsOfUseId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"dependencyTermsOfUseId"},
            new Object[] {dependencyTermsOfUseId});

        try {
            List<GetTermsOfUseResponse> response = termsOfUseServiceRpc.getDependentTermsOfUse(dependencyTermsOfUseId);
            List<TermsOfUse> result = new ArrayList<TermsOfUse>();
            for (GetTermsOfUseResponse r : response) {
                result.add(Helper.getTermsOfUse(r));
            }
            // Log method exit
            Helper.logExit(log, signature, new Object[] {result});
            return result;
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Deletes the dependency relationship between the dependent and dependency terms of use with the specified IDs.
     *
     * @param dependencyTermsOfUseId
     *            the ID of the dependency terms of use
     * @param dependentTermsOfUseId
     *            the ID of the dependent terms of use
     *
     * @throws TermsOfUseDependencyNotFoundException
     *             if dependency relationship between the specified dependency and dependent terms of use doesn't
     *             exist.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     *
     * @since 1.1
     */
    public void deleteDependencyRelationship(long dependentTermsOfUseId, long dependencyTermsOfUseId)
        throws TermsOfUseDependencyNotFoundException, TermsOfUsePersistenceException {
        String signature = CLASS_NAME
            + ".deleteDependencyRelationship(long dependentTermsOfUseId, long dependencyTermsOfUseId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"dependentTermsOfUseId", "dependencyTermsOfUseId"},
            new Object[] {dependentTermsOfUseId, dependencyTermsOfUseId});

        try {
            int deletedNum = termsOfUseServiceRpc.deleteDependencyRelationship(dependentTermsOfUseId, dependencyTermsOfUseId);
            if (deletedNum == 0) {
                // Log exception
                throw Helper.logException(log, signature, new TermsOfUseDependencyNotFoundException(
                    "The dependency relationship between the specified dependency"
                        + " and dependent terms of use doesn't exist."));
            }

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }
    /**
     * Deletes all dependency relationships in which terms of use with the specified ID is a dependent.
     *
     * @param dependentTermsOfUseId
     *            the ID of the dependent terms of use
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     *
     * @since 1.1
     */
    public void deleteAllDependencyRelationshipsForDependent(long dependentTermsOfUseId)
        throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".deleteAllDependencyRelationshipsForDependent(long dependentTermsOfUseId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"dependentTermsOfUseId"},
            new Object[] {dependentTermsOfUseId});

        try {
            termsOfUseServiceRpc.deleteAllDependencyRelationshipsForDependent(dependentTermsOfUseId);

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Deletes all dependent relationships in which terms of use with the specified ID is a dependency.
     *
     * @param dependencyTermsOfUseId
     *            the ID of the dependency terms of use
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     *
     * @since 1.1
     */
    public void deleteAllDependencyRelationshipsForDependency(long dependencyTermsOfUseId)
        throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".deleteAllDependencyRelationshipsForDependency(long dependencyTermsOfUseId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"dependencyTermsOfUseId"},
            new Object[] {dependencyTermsOfUseId});

        try {
            termsOfUseServiceRpc.deleteAllDependencyRelationshipsForDependency(dependencyTermsOfUseId);

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Checks the dependency loop.
     *
     * @param dependencyTermsOfUseId
     *            the ID of the dependency terms of use
     * @param dependentTermsOfUseId
     *            the ID of the dependent terms of use
     *
     * @throws TermsOfUseCyclicDependencyException
     *             if creation of the specified relationship will lead to a dependency loop.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    private void checkDependencyLoop(long dependentTermsOfUseId, long dependencyTermsOfUseId)
        throws TermsOfUseCyclicDependencyException, TermsOfUsePersistenceException {
        // Create a set for deep (direct and indirect) dependencies of dependency terms of use,
        // including the dependency terms of use itself;
        // this set contains all dependencies that are already checked:
        Set<Long> deepDependencyTermsOfUseIds = new HashSet<Long>();
        // Create a set for IDs of dependency terms of use to be checked:
        Set<Long> termsOfUseIdsToCheck = new HashSet<Long>();
        // Add dependencyTermsOfUseId to the set:
        termsOfUseIdsToCheck.add(dependencyTermsOfUseId);

        while (!termsOfUseIdsToCheck.isEmpty()) {
            // Create string builder for dynamic part of the query:
            StringBuilder sb = new StringBuilder();
            // Get the number of terms of use for which dependency IDs must be retrieved:
            int termsOfUseIdsToCheckNum = termsOfUseIdsToCheck.size();
            for (int i = 0; i < termsOfUseIdsToCheckNum; i++) {
                if (i != 0) {
                    // Append a comma:
                    sb.append(", ");
                }
                // Append a parameter placeholder:
                sb.append("?");
            }
            // Add all terms of use IDs to be checked to the set with checked terms of use IDs:
            deepDependencyTermsOfUseIds.addAll(termsOfUseIdsToCheck);
            // Clear the set with terms of use to be checked:
            termsOfUseIdsToCheck.clear();

            // Execute the query:
            List<Long> dependencyIds = termsOfUseServiceRpc.getDependencyTermsOfUseIds(new ArrayList<>(termsOfUseIdsToCheck));
            for (long dependencyId: dependencyIds) {
                if (dependencyId == dependentTermsOfUseId) {
                    throw new TermsOfUseCyclicDependencyException(
                        "Creation of the specified relationship will lead to a dependency loop.");
                }
                if (!deepDependencyTermsOfUseIds.contains(dependencyId)) {
                    // Add ID to the set of terms of use IDs that need to be checked:
                    termsOfUseIdsToCheck.add(dependencyId);
                }
            }
        }
    }
}
