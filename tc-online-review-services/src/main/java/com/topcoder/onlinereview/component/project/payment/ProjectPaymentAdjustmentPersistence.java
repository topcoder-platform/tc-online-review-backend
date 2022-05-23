/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.util.CommonUtils.executeUpdateSql;
import static com.topcoder.onlinereview.util.CommonUtils.getDouble;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;

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
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = ProjectPaymentAdjustmentPersistence.class.getName();

    /**
     * <p>
     * Represents the SQL string to update payment adjustment.
     * </p>
     */
    private static final String SQL_UPDATE_PAYMENT_ADJUSTMENT = "UPDATE project_payment_adjustment"
        + " SET fixed_amount = ?, multiplier = ? WHERE project_id = ? AND resource_role_id = ?";

    /**
     * <p>
     * Represents the SQL string to insert payment adjustment.
     * </p>
     */
    private static final String SQL_INSERT_PAYMENT_ADJUSTMENT = "INSERT INTO project_payment_adjustment"
        + " (project_id, resource_role_id, fixed_amount, multiplier) VALUES (?, ?, ?, ?)";

    /**
     * <p>
     * Represents the SQL string to query payment adjustment.
     * </p>
     */
    private static final String SQL_QUERY_PAYMENT_ADJUSTMENT = "SELECT project_id, resource_role_id,"
        + " fixed_amount, multiplier FROM project_payment_adjustment where project_id = ?";

    @Autowired
    @Qualifier("tcsJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
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
            if (executeUpdateSql(jdbcTemplate, SQL_UPDATE_PAYMENT_ADJUSTMENT,
                newArrayList(projectPaymentAdjustment.getFixedAmount(), projectPaymentAdjustment.getMultiplier(),
                projectPaymentAdjustment.getProjectId(), projectPaymentAdjustment.getResourceRoleId())) == 0) {
                // Insert
                executeUpdateSql(jdbcTemplate, SQL_INSERT_PAYMENT_ADJUSTMENT,
                        newArrayList(projectPaymentAdjustment.getProjectId(), projectPaymentAdjustment.getResourceRoleId(),
                    projectPaymentAdjustment.getFixedAmount(), projectPaymentAdjustment.getMultiplier()));
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

        // Execute the statement
        List<Map<String, Object>> resultSet = executeSqlWithParam(jdbcTemplate, SQL_QUERY_PAYMENT_ADJUSTMENT, newArrayList(projectId));

        List<ProjectPaymentAdjustment> result = new ArrayList<ProjectPaymentAdjustment>();
        for (Map<String, Object> row: resultSet) {
            ProjectPaymentAdjustment projectPaymentAdjustment = new ProjectPaymentAdjustment();
            projectPaymentAdjustment.setProjectId(getLong(row, "project_id"));
            projectPaymentAdjustment.setResourceRoleId(getLong(row, "resource_role_id"));
            projectPaymentAdjustment.setFixedAmount(new BigDecimal(getDouble(row, "fixed_amount")));
            projectPaymentAdjustment.setMultiplier(getDouble(row, "multiplier"));
            result.add(projectPaymentAdjustment);
        }
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
