/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.termsofuse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topcoder.onlinereview.component.grpcclient.termsofuse.TermsOfUseServiceRpc;
import com.topcoder.onlinereview.grpc.termsofuse.proto.GetTermsOfUseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class is the default implementation of UserTermsOfUseDao. It utilizes the DB Connection Factory to get access
 * to the database. The configuration is done by the Configuration API.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;CMConfig&gt;
 *     &lt;Config name=&quot;userTermsOfUseDao&quot;&gt;
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
 *     &lt;/Config&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Create the configuration object
 * ConfigurationObject configurationObject = TestsHelper.getConfig(TestsHelper.CONFIG_USER_TERMS);
 * // Instantiate the dao implementation from configuration defined above
 * UserTermsOfUseDao userTermsOfUseDao = new UserTermsOfUseDaoImpl(configurationObject);
 *
 * // Create user terms of use to user link
 * userTermsOfUseDao.createUserTermsOfUse(3, 2);
 *
 * // Remove user terms of use to user link
 * userTermsOfUseDao.removeUserTermsOfUse(3, 3);
 *
 * // Retrieve terms of use. This will return user terms with ids 1 and 2.
 * List&lt;TermsOfUse&gt; termsList = userTermsOfUseDao.getTermsOfUseByUserId(1);
 *
 * // Retrieve users by terms of use. This will return ids 1 and 3.
 * List&lt;Long&gt; userIdsList = userTermsOfUseDao.getUsersByTermsOfUseId(2);
 *
 * // Check whether user has terms of use. Will return false
 * boolean hasTerms = userTermsOfUseDao.hasTermsOfUse(3, 3);
 *
 * // Check whether user has terms of use ban. Will return true
 * boolean hasTermsBan = userTermsOfUseDao.hasTermsOfUseBan(1, 3);
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Changes in 1.1:</em>
 * <ol>
 * <li>Updated queries to support removal of memberAgreeable and electronicallySignable properties of TermsOfUse.</li>
 * <li>Updated queries to support adding of TermsOfUse#agreeabilityType property.</li>
 * </ol>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The class is immutable and thread safe.
 * </p>
 *
 * @author faeton, sparemax, saarixx
 * @version 1.1
 */
@Slf4j
@Component
public class UserTermsOfUseDao {
    @Autowired
    TermsOfUseServiceRpc termsOfUseServiceRpc;
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = UserTermsOfUseDao.class.getName();

    /**
     * Records the fact of acceptance of specified terms of use by specified user.
     *
     * @param userId
     *            a long providing the user ID.
     * @param termsOfUseId
     *            a long providing the terms of use ID.
     *
     * @throws UserBannedException
     *             if the user is banned to create terms of use.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public void createUserTermsOfUse(long userId, long termsOfUseId) throws UserBannedException,
        TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".createUserTermsOfUse(long userId, long termsOfUseId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"userId", "termsOfUseId"},
            new Object[] {userId, termsOfUseId});

        if (hasTermsOfUseBan(userId, termsOfUseId)) {
            // Log exception
            throw Helper.logException(log, signature, new UserBannedException("The user with id " + userId
                + " has a ban for following terms of use with id " + termsOfUseId + "."));
        }

        try {
            termsOfUseServiceRpc.createUserTermsOfUse(userId, termsOfUseId);

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Removes the fact of acceptance of specified terms of use by specified user.
     *
     * @param userId
     *            a long providing the user ID.
     * @param termsOfUseId
     *            a long providing the terms of use ID.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     */
    public void removeUserTermsOfUse(long userId, long termsOfUseId) throws TermsOfUsePersistenceException,
        EntityNotFoundException {
        String signature = CLASS_NAME + ".removeUserTermsOfUse(long userId, long termsOfUseId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"userId", "termsOfUseId"},
            new Object[] {userId, termsOfUseId});

        try {
            int num = termsOfUseServiceRpc.deleteUserTermsOfUse(userId, termsOfUseId);
            if (num != 1) {
                throw new EntityNotFoundException("The entity was not found for id (" + Long.toString(userId) + ", " + termsOfUseId + ").");
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
     * Retrieves terms of use entities associated with the given user from the database.
     *
     * @param userId
     *            a long containing the user id to retrieve terms of use.
     *
     * @return a TermsOfUse list with the requested terms of use or empty list if not found.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public List<TermsOfUse> getTermsOfUseByUserId(long userId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getTermsOfUseByUserId(long userId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"userId"},
            new Object[] {userId});

        try {
            List<GetTermsOfUseResponse> response = termsOfUseServiceRpc.getTermsOfUseByUserId(userId);
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
     * Retrieves user ids associated with the given terms of use from the database.
     *
     * @param termsOfUseId
     *            a long containing the terms of use id to retrieve the user ids.
     *
     * @return a list of user ids or empty list if not found.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public List<Long> getUsersByTermsOfUseId(long termsOfUseId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getUsersByTermsOfUseId(long termsOfUseId)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseId"},
            new Object[] {termsOfUseId});
        try {
            List<Long> result = termsOfUseServiceRpc.getUsersByTermsOfUseId(termsOfUseId);
            // Log method exit
            Helper.logExit(log, signature, new Object[] {result});
            return result;
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Checks if there is a record on the fact of acceptance of specified terms of use by specified user.
     *
     * @param userId
     *            a long providing the user ID.
     * @param termsOfUseId
     *            a long providing the terms of use ID.
     *
     * @return <code>true</code> if specified user accepted the specified terms of use; <code>false</code>
     *         otherwise.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public boolean hasTermsOfUse(long userId, long termsOfUseId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".hasTermsOfUse(long userId, long termsOfUseId)";
        Helper.logEntrance(log, signature,
                new String[] { "userId", "termsOfUseId" },
                new Object[] { userId, termsOfUseId });

        try {
            boolean result = termsOfUseServiceRpc.isTermsOfUseExists(userId, termsOfUseId);
            // Log method exit
            Helper.logExit(log, signature, new Object[] { result });
            return result;
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Checks if there is a record on the fact of banning the specified user from accepting the specified terms of
     * use.
     *
     * @param userId
     *            a long providing the user ID.
     * @param termsOfUseId
     *            a long providing the terms of use ID.
     *
     * @return <code>true</code> if specified user has ban for the specified terms of use; <code>false</code>
     *         otherwise.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public boolean hasTermsOfUseBan(long userId, long termsOfUseId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".hasTermsOfUseBan(long userId, long termsOfUseId)";

        Helper.logEntrance(log, signature,
                new String[] { "userId", "termsOfUseId" },
                new Object[] { userId, termsOfUseId });

        try {
            boolean result = termsOfUseServiceRpc.isTermsOfUseBanExists(userId, termsOfUseId);
            // Log method exit
            Helper.logExit(log, signature, new Object[] { result });
            return result;
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }
}
