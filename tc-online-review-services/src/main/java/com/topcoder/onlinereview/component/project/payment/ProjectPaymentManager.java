/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.payment;

import com.topcoder.onlinereview.component.search.SearchBuilderException;
import com.topcoder.onlinereview.component.search.SearchBundle;
import com.topcoder.onlinereview.component.search.SearchBundleManager;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.component.search.filter.NotFilter;
import com.topcoder.onlinereview.component.search.filter.NullFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.util.CommonUtils.getBoolean;
import static com.topcoder.onlinereview.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.util.CommonUtils.getDouble;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.util.CommonUtils.getString;

@Slf4j
@Component
public class ProjectPaymentManager {
  /** Represents the class name. */
  private static final String CLASS_NAME = ProjectPaymentManager.class.getName();

  public static final String PAYMENT_SEARCH_BUNDLE_NAME = "ProjectPaymentSearchBundle";
  /**
   * The search bundle used by this class when searching for project payments. Initialized in the
   * constructor and never changed after that. Cannot be null. Used in search().
   */
  private SearchBundle searchBundle;

  /**
   * The project payment persistence instance used by this class for CRUD operations. Initialized in
   * the constructor and never changed after that. Cannot be null. Used in CRUD methods.
   */
  @Autowired private ProjectPaymentPersistence persistence;

  @Autowired private SearchBundleManager searchBundleManager;

  @PostConstruct
  public void postRun() {
    searchBundle = searchBundleManager.getSearchBundle(PAYMENT_SEARCH_BUNDLE_NAME);
  }

  public ProjectPayment create(ProjectPayment projectPayment, String operator)
      throws ProjectPaymentValidationException, ProjectPaymentManagementDataIntegrityException,
          ProjectPaymentManagementPersistenceException {
    String signature = CLASS_NAME + ".create(ProjectPayment projectPayment)";
    log.trace(signature);
    try {
      ProjectPayment result = persistence.create(projectPayment, operator);
      log.trace(String.join(" ", signature, "Exit"));
      return result;
    } catch (ProjectPaymentManagementPersistenceException e) {
      // Log exception
      log.error("create payment error:", e);
      throw e;
    }
  }

  public void update(ProjectPayment projectPayment, String operator)
      throws ProjectPaymentValidationException, ProjectPaymentNotFoundException,
          ProjectPaymentManagementDataIntegrityException,
          ProjectPaymentManagementPersistenceException {
    String signature = CLASS_NAME + ".update(ProjectPayment projectPayment)";
    log.trace(signature);
    try {
      persistence.update(projectPayment, operator);
      log.trace(String.join(" ", signature, "Exit"));
    } catch (ProjectPaymentManagementPersistenceException e) {
      // Log exception
      log.error(signature + " error:", e);
      throw e;
    }
  }

  /**
   * Retrieves the given project payment from persistence by its id.
   *
   * @param projectPaymentId the id of the project payment
   * @return the project payment, null if nothing was found
   * @throws ProjectPaymentManagementPersistenceException if some error occurred when accessing the
   *     persistence for example.
   */
  public ProjectPayment retrieve(long projectPaymentId)
      throws ProjectPaymentManagementPersistenceException {
    String signature = CLASS_NAME + ".retrieve(long projectPaymentId)";

    // Log Entrance
    log.trace(signature);
    try {
      ProjectPayment result = persistence.retrieve(projectPaymentId);
      // Log Exit
      log.trace(String.join(" ", signature, "Exit"));
      return result;
    } catch (ProjectPaymentManagementPersistenceException e) {
      log.error(signature + " error:", e);
      throw e;
    }
  }

  /**
   * Deletes the given project payment from persistence.
   *
   * @param projectPaymentId the id of project payment to delete
   * @return the flag indicating if project payment was deleted
   * @throws ProjectPaymentManagementPersistenceException if some error occurred when accessing the
   *     persistence for example.
   */
  public boolean delete(long projectPaymentId) throws ProjectPaymentManagementPersistenceException {
    String signature = CLASS_NAME + ".delete(long projectPaymentId)";

    // Log Entrance
    log.trace(signature);

    try {
      boolean result = persistence.delete(projectPaymentId);
      // Log Exit
      log.trace(String.join(" ", signature, "Exit"));
      return result;
    } catch (ProjectPaymentManagementPersistenceException e) {
      // Log exception
      log.error(signature + " error:", e);
      throw e;
    }
  }

  /**
   * Searches for all project payments that are matched with the given filter. Returns an empty list
   * if none found.
   *
   * @param filter the filter
   * @return the list with found project payments that are matched with the given filter (not null,
   *     doesn't contain null)
   * @throws ProjectPaymentManagementPersistenceException if some error occurred when accessing the
   *     persistence
   * @throws ProjectPaymentManagementException if some other error occurred
   */
  public List<ProjectPayment> search(Filter filter) throws ProjectPaymentManagementException {
    String signature = CLASS_NAME + ".search(Filter filter)";

    // Log Entrance
    log.trace(signature);

    // Create filter that matches all records if filter is null
    if (filter == null) {
      filter = new NotFilter(new NullFilter("projectPaymentId"));
    }

    try {
      // Perform the search using Search Builder:
      List<Map<String, Object>> resultSet = searchBundle.search(filter);
      // Get results from the result set:
      List<ProjectPayment> result = getProjectPayments(resultSet);

      // Log Exit
      log.trace(String.join(" ", signature, "Exit"));
      return result;
    } catch (SearchBuilderException e) {
      // Log exception
      log.error("Failed to search project payments with the filter.", e);
      throw new ProjectPaymentManagementPersistenceException(
          "Failed to search project payments with the filter.", e);
    } catch (ClassCastException e) {
      // Log exception
      log.error("The result is invalid.", e);
      throw e;
    } catch (ProjectPaymentManagementException e) {
      // Log exception
      log.error(signature + " error:", e);
      throw e;
    }
  }

  /**
   * Retrieves the list of project payments from the provided custom result set.
   *
   * @param resultSet the custom result set from which project payments should be retrieved
   * @return the retrieved project payments (not null, doesn't contain null)
   * @throws ProjectPaymentManagementException if some error occurred when retrieving data
   */
  private List<ProjectPayment> getProjectPayments(List<Map<String, Object>> resultSet)
      throws ProjectPaymentManagementException {
    // Create a list for result:
    List<ProjectPayment> result = new ArrayList<ProjectPayment>();
    try {
      for (Map<String, Object> m : resultSet) {
        ProjectPayment projectPayment = new ProjectPayment();
        projectPayment.setProjectPaymentId(getLong(m, "project_payment_id"));
        projectPayment.setResourceId(getLong(m, "resource_id"));
        if (m.get("submission_id") != null) {
          projectPayment.setSubmissionId(getLong(m, "submission_id"));
        }
        projectPayment.setAmount(new BigDecimal(getDouble(m, "amount")));

        if (m.get("pacts_payment_id") != null) {
          projectPayment.setPactsPaymentId(getLong(m, "pacts_payment_id"));
        }
        projectPayment.setCreateUser(getString(m, "create_user"));
        projectPayment.setCreateDate(getDate(m, "create_date"));
        projectPayment.setModifyUser(getString(m, "modify_user"));
        projectPayment.setModifyDate(getDate(m, "modify_date"));
        ProjectPaymentType projectPaymentType = new ProjectPaymentType();
        projectPaymentType.setProjectPaymentTypeId(getLong(m, "project_payment_type_id"));
        projectPaymentType.setName(getString(m, "name"));
        projectPaymentType.setMergeable(getBoolean(m, "mergeable"));
        projectPaymentType.setPactsPaymentTypeId(getLong(m, "pacts_payment_type_id"));
        projectPayment.setProjectPaymentType(projectPaymentType);
        result.add(projectPayment);
      }
    } catch (Exception e) {
      throw new ProjectPaymentManagementException("The column does not exist.", e);
    }
    return result;
  }
}
