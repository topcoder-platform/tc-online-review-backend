/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topcoder.onlinereview.component.grpcclient.payment.PaymentServiceRpc;

import java.sql.SQLException;

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

  @Autowired
  private PaymentServiceRpc paymentServiceRpc;

  public ProjectPayment create(ProjectPayment projectPayment, String operator)
      throws ProjectPaymentValidationException, ProjectPaymentManagementDataIntegrityException,
          ProjectPaymentManagementPersistenceException {
    String signature = CLASS_NAME + ".create(ProjectPayment projectPayment, String operator)";

    // Log Entrance
    log.trace(String.join(" ", signature, operator));

    try {
      // Check project payment integrity
      checkProjectPaymentIntegrity(projectPayment);
      long projectPaymentId = paymentServiceRpc.createPayment(projectPayment, operator);
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
    String signature = CLASS_NAME + ".update(ProjectPayment projectPayment, String operator)";
    // Log Entrance
    log.trace(String.join(" ", signature, operator));
    try {
      // Check project payment integrity
      checkProjectPaymentIntegrity(projectPayment);
      int result = paymentServiceRpc.updatePayment(projectPayment, operator);
      // UPDATE
      if (result == 0) {
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
    String signature = CLASS_NAME + ".retrieve(long projectPaymentId)";
    log.trace(String.join(" ", signature, "retrieve", String.valueOf(projectPaymentId)));

    try {
      ProjectPayment result = paymentServiceRpc.getPayment(projectPaymentId);
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
      paymentServiceRpc.deletePayment(projectPaymentId);
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
    if (!paymentServiceRpc.isProjectPaymentTypeExists(projectPayment.getProjectPaymentType().getProjectPaymentTypeId())) {
      throw new ProjectPaymentManagementDataIntegrityException(
          "'projectPaymentTypeId' doesn't exist.");
    }
    if (!paymentServiceRpc.isResourceExists(projectPayment.getResourceId())) {
      throw new ProjectPaymentManagementDataIntegrityException(
          "'resourceId' doesn't exist.");
    }

    Long submissionId = projectPayment.getSubmissionId();
    if (submissionId != null) {
      if (!paymentServiceRpc.isSubmissionExists(submissionId)) {
        throw new ProjectPaymentManagementDataIntegrityException(
            "'submissionId' doesn't exist.");
      }
    }
  }
}
