/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeUpdateSql;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeUpdateSqlWithReturn;
import static com.topcoder.onlinereview.component.util.CommonUtils.getBoolean;
import static com.topcoder.onlinereview.component.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.component.util.CommonUtils.getDouble;
import static com.topcoder.onlinereview.component.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.component.util.CommonUtils.getString;

/**
 * This class is an implementation of ProjectPaymentPersistence that uses JDBC and DB Connection
 * Factory component. This class uses Logging Wrapper component to log errors and debug information.
 *
 * <p><strong>Thread Safety: </strong> This class is mutable, but thread safe when configure()
 * method is called just once right after instantiation and entities passed to it are used by the
 * caller in thread safe manner. It uses thread safe DBConnectionFactory and Log instances.
 *
 * @author maksimilian, sparemax
 * @version 1.0.1
 */
@Slf4j
@Component
public class ProjectPaymentPersistence {
  /** Represents the class name. */
  private static final String CLASS_NAME = ProjectPaymentPersistence.class.getName();

  /** Represents the SQL string to insert project payment. */
  private static final String SQL_INSERT_PAYMENT =
      "INSERT INTO project_payment (resource_id, submission_id,"
          + " amount, pacts_payment_id, create_user, create_date, modify_user, modify_date, project_payment_type_id)"
          + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

  /** Represents the SQL string to update project payment. */
  private static final String SQL_UPDATE_PAYMENT =
      "UPDATE project_payment set project_payment_type_id = ?,"
          + " resource_id = ?, submission_id = ?, amount = ?, pacts_payment_id = ?, modify_user = ?, modify_date = ?"
          + " WHERE project_payment_id = ?";

  /** Represents the SQL string to query project payment. */
  private static final String SQL_QUERY_PAYMENT =
      "SELECT pp.resource_id, pp.submission_id, pp.amount,"
          + " pp.pacts_payment_id, pp.create_user, pp.create_date, pp.modify_user, pp.modify_date,"
          + " ppt.project_payment_type_id, ppt.name, ppt.mergeable"
          + " FROM project_payment pp LEFT JOIN project_payment_type_lu ppt"
          + " on pp.project_payment_type_id = ppt.project_payment_type_id WHERE pp.project_payment_id = ?";

  /** Represents the SQL string to delete project payment. */
  private static final String SQL_DELETE_PAYMENT =
      "DELETE FROM project_payment WHERE project_payment_id = ?";

  /** Represents the SQL string to query foreign key. */
  private static final String SQL_QUERY_FK = "SELECT COUNT(*) as count FROM %1$s WHERE %2$s = ?";

  @Autowired
  @Qualifier("tcsJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  public ProjectPayment create(ProjectPayment projectPayment, String operator)
      throws ProjectPaymentValidationException, ProjectPaymentManagementDataIntegrityException,
          ProjectPaymentManagementPersistenceException {
    String signature = CLASS_NAME + ".create(ProjectPayment projectPayment, String operator)";

    // Log Entrance
    log.trace(String.join(" ", signature, operator));

    try {
      // Check project payment integrity
      checkProjectPaymentIntegrity(projectPayment);
      Date date = new Date();

      // INSERT
      long projectPaymentId =
          executeUpdateSqlWithReturn(
              jdbcTemplate,
              SQL_INSERT_PAYMENT,
              newArrayList(
                  projectPayment.getResourceId(),
                  projectPayment.getSubmissionId(),
                  projectPayment.getAmount(),
                  projectPayment.getPactsPaymentId(),
                  operator,
                  date,
                  operator,
                  date,
                  projectPayment.getProjectPaymentType().getProjectPaymentTypeId()));

      projectPayment.setProjectPaymentId(projectPaymentId);

      log.trace(String.join(" ", signature, operator, "Exit"));
      return projectPayment;
    } catch (Exception e) {
      // Log exception
      log.error("Failed to create the project payment.");
      throw new ProjectPaymentManagementPersistenceException(
          "Failed to create the project payment.", e);
    }
  }

  public void update(ProjectPayment projectPayment, String operator)
      throws ProjectPaymentValidationException, ProjectPaymentNotFoundException,
          ProjectPaymentManagementDataIntegrityException,
          ProjectPaymentManagementPersistenceException {
    Date entranceTimestamp = new Date();
    String signature = CLASS_NAME + ".update(ProjectPayment projectPayment, String operator)";
    // Log Entrance
    log.trace(String.join(" ", signature, operator));
    try {
      // Check project payment integrity
      checkProjectPaymentIntegrity(projectPayment);
      List<Map<String, Object>> result =
          executeSqlWithParam(
              jdbcTemplate,
              SQL_UPDATE_PAYMENT,
              newArrayList(
                  projectPayment.getProjectPaymentType().getProjectPaymentTypeId(),
                  projectPayment.getResourceId(),
                  projectPayment.getSubmissionId(),
                  projectPayment.getAmount(),
                  projectPayment.getPactsPaymentId(),
                  operator,
                  new Date(),
                  projectPayment.getProjectPaymentId()));

      // UPDATE
      if (result.isEmpty()) {
        // Log exception
        log.error("The project payment doesn't exist.");
        throw new ProjectPaymentNotFoundException("The project payment doesn't exist.");
      }
      // Log Exit
      log.trace(String.join(" ", signature, operator, "Exit"));
    } catch (Exception e) {
      // Log exception
      log.error("Failed to update the project payment.");
      throw new ProjectPaymentManagementPersistenceException(
          "Failed to update the project payment.", e);
    }
  }

  /**
   * Retrieves the given project payment from persistence by its id.
   *
   * @param projectPaymentId the id of the project payment
   * @return the project payment, null if nothing was found
   * @throws ProjectPaymentManagementPersistenceException if some error occurred when accessing the
   *     persistence
   * @throws IllegalStateException if persistence was not configured properly (dbConnectionFactory
   *     is null)
   */
  public ProjectPayment retrieve(long projectPaymentId)
      throws ProjectPaymentManagementPersistenceException {
    Date entranceTimestamp = new Date();
    String signature = CLASS_NAME + ".retrieve(long projectPaymentId)";
    log.trace(String.join(" ", signature, "retrieve", String.valueOf(projectPaymentId)));

    try {
      // Execute the statement
      List<Map<String, Object>> resultSet =
          executeSqlWithParam(jdbcTemplate, SQL_QUERY_PAYMENT, newArrayList(projectPaymentId));
      ProjectPayment result = null;
      if (!resultSet.isEmpty()) {
        Map<String, Object> f = resultSet.get(0);
        result = new ProjectPayment();
        result.setProjectPaymentId(projectPaymentId);
        result.setResourceId(getLong(f, "resource_id"));

        result.setSubmissionId(getLong(f, "submission_id"));

        result.setAmount(new BigDecimal(getDouble(f, "amount")));

        result.setPactsPaymentId(getLong(f, "pacts_payment_id"));

        result.setCreateUser(getString(f, "create_user"));
        result.setCreateDate(getDate(f, "create_date"));
        result.setModifyUser(getString(f, "modify_user"));
        result.setModifyDate(getDate(f, "modify_date"));

        ProjectPaymentType projectPaymentType = new ProjectPaymentType();
        projectPaymentType.setProjectPaymentTypeId(getLong(f, "project_payment_type_id"));
        projectPaymentType.setName(getString(f, "name"));
        projectPaymentType.setMergeable(getBoolean(f, "mergeable"));
        result.setProjectPaymentType(projectPaymentType);
      }

      // Log Exit
      log.trace(String.join(" ", signature, "retrieve", "Exit"));
      return result;
    } catch (Exception e) {
      // Log exception
      log.error("Failed to retrieve the project payment.");
      throw new ProjectPaymentManagementPersistenceException(
          "Failed to retrieve the project payment.", e);
    }
  }

  /**
   * Deletes the given project payment from persistence.
   *
   * @param projectPaymentId the id of project payment to delete
   * @return the flag indicating if project payment was deleted
   * @throws ProjectPaymentManagementPersistenceException if some error occurred when accessing the
   *     persistence
   * @throws IllegalStateException if persistence was not configured properly (dbConnectionFactory
   *     is null)
   */
  public boolean delete(long projectPaymentId) throws ProjectPaymentManagementPersistenceException {
    String signature = CLASS_NAME + ".delete(long projectPaymentId)";
    log.trace(String.join(" ", signature, "delete", String.valueOf(projectPaymentId)));
    try {
      executeUpdateSql(jdbcTemplate, SQL_DELETE_PAYMENT, newArrayList(projectPaymentId));
      // Log Exit
      log.trace(String.join(" ", signature, "delete", "Exit"));
      return true;
    } catch (Exception e) {
      // Log exception
      log.error("Failed to delete the project payment.");
      throw new ProjectPaymentManagementPersistenceException(
          "Failed to delete the project payment.", e);
    }
  }

  /**
   * Checks the project payment integrity.
   *
   * @param projectPayment the project payment.
   * @throws ProjectPaymentManagementDataIntegrityException if project payment integrity is broken,
   *     for example if project payment is being persisted with payment type that is not present in
   *     DB.
   * @throws SQLException if any other error occurs
   */
  private void checkProjectPaymentIntegrity(ProjectPayment projectPayment)
      throws ProjectPaymentManagementDataIntegrityException, SQLException {
    checkRecord(
        "projectPaymentTypeId",
        "project_payment_type_lu",
        "project_payment_type_id",
        projectPayment.getProjectPaymentType().getProjectPaymentTypeId());
    checkRecord("resourceId", "resource", "resource_id", projectPayment.getResourceId());

    Long submissionId = projectPayment.getSubmissionId();
    if (submissionId != null) {
      checkRecord("submissionId", "submission", "submission_id", submissionId);
    }
  }

  /**
   * Checks if the record exists.
   *
   * @param fieldName the field name.
   * @param table the table.
   * @param key the key.
   * @param value the value.
   * @throws ProjectPaymentManagementDataIntegrityException if the record is not found.
   * @throws SQLException if any other error occurs
   */
  private void checkRecord(String fieldName, String table, String key, Object value)
      throws ProjectPaymentManagementDataIntegrityException {
    String sql = String.format(SQL_QUERY_FK, table, key);
    List<Map<String, Object>> result = executeSqlWithParam(jdbcTemplate, sql, newArrayList(value));
    if (result.isEmpty() || getLong(result.get(0), "count") == 0) {
      throw new ProjectPaymentManagementDataIntegrityException(
          "'" + fieldName + "' doesn't exist.");
    }
  }
}
