/*
 * Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.deliverable.late;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.topcoder.onlinereview.component.grpcclient.deliverable.DeliverableServiceRpc;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This class is an implementation of LateDeliverablePersistence that updates late deliverables and retrieves all late
 * deliverable types in/from database persistence using JDBC and DB Connection Factory component. This class uses
 * Logging Wrapper component to log errors and debug information.
 * </p>
 *
 * <p>
 * <em>Changes in version 1.0.6:</em>
 * <ol>
 * <li>Added getLateDeliverableTypes() method.</li>
 * <li>Updated throws documentation of update() method.</li>
 * <li>Updated class documentation.</li>
 * </ol>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable, but thread safe when configure() method is called just once
 * right after instantiation and entities passed to it are used by the caller in thread safe manner. It uses thread
 * safe DBConnectionFactory and Log instances. This class uses transactions when updating data in the database.
 * </p>
 *
 * @author saarixx, sparemax
 * @version 1.0.6
 */
@Slf4j
@Component
public class LateDeliverablePersistence {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = LateDeliverablePersistence.class.getName();

    @Autowired
    private DeliverableServiceRpc deliverableServiceRpc;

    /**
     * <p>
     * Updates the given late deliverable in persistence.
     * </p>
     *
     * <p>
     * <em>Changes in version 1.0.6:</em>
     * <ol>
     * <li>Updated throws documentation for IllegalArgumentException.</li>
     * <li>Added/updated steps to update the late deliverable type id.</li>
     * <li>Added logging for IllegalStateException.</li>
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
     * @throws IllegalStateException
     *             if persistence was not configured properly (dbConnectionFactory is null).
     * @throws LateDeliverableNotFoundException
     *             if late deliverable with ID equal to lateDeliverable.getId() doesn't exist in persistence.
     * @throws LateDeliverablePersistenceException
     *             if some other error occurred when accessing the persistence.
     */
    @Transactional
    public void update(LateDeliverable lateDeliverable) throws LateDeliverablePersistenceException {
        Date enterTimestamp = new Date();
        String signature = getSignature("update(LateDeliverable lateDeliverable)");

        try {
            // Log method entry
            Helper.logEntrance(log, signature,
                new String[] {"lateDeliverable"},
                new Object[] {lateDeliverable});

            Helper.checkNull(lateDeliverable, "lateDeliverable");
            Helper.checkPositive(lateDeliverable.getId(), "lateDeliverable.getId()");
            Helper.checkNull(lateDeliverable.getCreateDate(), "lateDeliverable.getCreateDate()");
            LateDeliverableType type = lateDeliverable.getType();
            Helper.checkNull(type, "lateDeliverable.getType()");
            Helper.checkPositive(type.getId(), "lateDeliverable.getType().getId()");

            // Update the given late deliverable in persistence
            updateLateDeliverable(lateDeliverable);

            // Log method exit
            Helper.logExit(log, signature, null, enterTimestamp);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalArgumentException is thrown.");
        } catch (IllegalStateException e) {
            // Log exception
            throw Helper.logException(log, signature, e, "IllegalStateException is thrown.");
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
     * Retrieves all existing late deliverable types from persistence.
     * </p>
     *
     * @return the retrieved late deliverable types (not null, doesn't contain null).
     *
     * @throws IllegalStateException
     *             if persistence was not configured properly (dbConnectionFactory is null).
     * @throws LateDeliverablePersistenceException
     *             if some error occurred when accessing the persistence.
     *
     * @since 1.0.6
     */
    public List<LateDeliverableType> getLateDeliverableTypes() throws LateDeliverablePersistenceException {
        Date enterTimestamp = new Date();
        String signature = getSignature("getLateDeliverableTypes()");

        // Log method entry
        Helper.logEntrance(log, signature, null, null);
        try {
            List<LateDeliverableType> result = deliverableServiceRpc.getLateDeliverableTypes();
            // Log method exit
            Helper.logExit(log, signature, new Object[] {result}, enterTimestamp);
            return result;
        } catch (IllegalStateException e) {
            throw Helper.logException(log, signature, e, "IllegalStateException is thrown.");
        }
    }

    /**
     * <p>
     * Updates the given late deliverable in persistence.
     * </p>
     *
     * <p>
     * <em>Changes in version 1.0.6:</em>
     * <ol>
     * <li>Added/updated steps to update the late deliverable type id.</li>
     * </ol>
     * </p>
     *
     * @param lateDeliverable
     *            the late deliverable with updated data.
     * @throws LateDeliverableNotFoundException
     *             if late deliverable with ID equal to lateDeliverable.getId() doesn't exist in persistence.
     * @throws SQLException
     *             if a database access errors.
     */
    private void updateLateDeliverable(LateDeliverable lateDeliverable) throws LateDeliverableNotFoundException {
        if (deliverableServiceRpc.updateLateDeliverable(lateDeliverable) == 0) {
            throw new LateDeliverableNotFoundException(Helper.concat("The late deliverable with ID '",
                    lateDeliverable.getId(), "' doesn't exist in persistence."), lateDeliverable.getId());
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
