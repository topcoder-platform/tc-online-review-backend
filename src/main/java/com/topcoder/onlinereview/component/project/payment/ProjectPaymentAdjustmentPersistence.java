/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.topcoder.onlinereview.component.grpcclient.payment.PaymentServiceRpc;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * This class is an implementation of ProjectPaymentAdjustmentPersistence that uses JDBC and DB Connection Factory
 * component. This class uses Logging Wrapper component to log errors and debug information.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable, but thread safe when configure() method is called just once
 * right after instantiation and entities passed to it are used by the caller in thread safe manner. It uses thread
 * safe DBConnectionFactory and Log instances.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0
 */
@Slf4j
@Component
public class ProjectPaymentAdjustmentPersistence {

    @Autowired
    private PaymentServiceRpc paymentServiceRpc;
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = ProjectPaymentAdjustmentPersistence.class.getName();

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
     *             if some other error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly (dbConnectionFactory is null)
     */
    @Transactional
    public void save(ProjectPaymentAdjustment projectPaymentAdjustment)
        throws ProjectPaymentAdjustmentValidationException, ProjectPaymentManagementPersistenceException {
        String signature = CLASS_NAME + ".save(ProjectPaymentAdjustment projectPaymentAdjustment)";

        // Log Entrance
        Helper.logEntrance(log, signature,
            new String[] {"projectPaymentAdjustment"},
            new Object[] {projectPaymentAdjustment.toString()});

        try {
            // Validate the parameter
            validateProjectPaymentAdjustment(projectPaymentAdjustment);
                // Update
            if (paymentServiceRpc.updatePaymentAdjustment(projectPaymentAdjustment) == 0) {
                // Insert
                paymentServiceRpc.createPaymentAdjustment(projectPaymentAdjustment);
            }
            // Log Exit
            Helper.logExit(log, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (ProjectPaymentAdjustmentValidationException e) {
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
     *             if some error occurred when accessing the persistence
     * @throws IllegalStateException
     *             if persistence was not configured properly (dbConnectionFactory is null)
     */
    public List<ProjectPaymentAdjustment> retrieveByProjectId(long projectId)
        throws ProjectPaymentManagementPersistenceException {
        String signature = CLASS_NAME + ".retrieveByProjectId(long projectId)";
        // Log Entrance
        Helper.logEntrance(log, signature,
            new String[] {"projectId"},
            new Object[] {projectId});

        List<ProjectPaymentAdjustment> result = paymentServiceRpc.getPaymentAdjustments(projectId);
        // Log Exit
        Helper.logExit(log, signature, new Object[] {result.toString()});
        return result;
    }

    /**
     * Validates the project payment adjustment.
     *
     * @param projectPaymentAdjustment
     *            the project payment adjustment.
     *
     * @throws IllegalArgumentException
     *             if projectPaymentAdjustment is null
     * @throws ProjectPaymentAdjustmentValidationException
     *             if projectPaymentAdjustment's project id is null, projectPaymentAdjustment's resource role id is
     *             null, projectPaymentAdjustment's fixed amount is negative, projectPaymentAdjustment's multiplier is
     *             negative, if both fixed amount and multiplier are non-null at the same time.
     */
    private static void validateProjectPaymentAdjustment(ProjectPaymentAdjustment projectPaymentAdjustment)
        throws ProjectPaymentAdjustmentValidationException {
        Helper.checkNotNull(projectPaymentAdjustment, "projectPaymentAdjustment");

        // Validate the input parameter
        Helper.checkNotNull(projectPaymentAdjustment.getProjectId(),
            "projectPaymentAdjustment.getProjectId()");
        Helper.checkNotNull(projectPaymentAdjustment.getResourceRoleId(),
            "projectPaymentAdjustment.getResourceRoleId()");

        BigDecimal fixedAmount = projectPaymentAdjustment.getFixedAmount();
        boolean isFixedAmountNotNull = (fixedAmount != null);
        if (isFixedAmountNotNull) {
            if (fixedAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new ProjectPaymentAdjustmentValidationException(
                    "projectPaymentAdjustment.getFixedAmount() should be not negative.");
            }
        }

        Double multiplier = projectPaymentAdjustment.getMultiplier();
        boolean isMultiplierNotNull = (projectPaymentAdjustment.getMultiplier() != null);
        if (isMultiplierNotNull) {
            Helper.checkNotNegative(multiplier, "projectPaymentAdjustment.getMultiplier()");

            if (isFixedAmountNotNull) {
                throw new ProjectPaymentAdjustmentValidationException(
                    "projectPaymentAdjustment.getFixedAmount() and projectPaymentAdjustment.getMultiplier()"
                        + " should not be non-null at the same time.");
            }
        }
    }
}
