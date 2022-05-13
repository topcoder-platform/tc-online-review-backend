/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.onlinereview.component.deliverable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Map;

import static com.topcoder.onlinereview.util.CommonUtils.executeSql;
import static com.topcoder.onlinereview.util.CommonUtils.getBoolean;
import static com.topcoder.onlinereview.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.util.CommonUtils.getString;

/**
 * The SqlDeliverablePersistence class implements the DeliverablePersistence interface, in order to
 * persist to the database structure given in the deliverable_management.sql script.
 *
 * <p>This class does not cache a Connection to the database. Instead a new Connection is used on
 * every method call. PreparedStatements should be used to execute the SQL statements.
 *
 * <p>Thread Safety: This class is immutable and thread-safe in the sense that multiple threads can
 * not corrupt its internal data structures. However, the results if used from multiple threads can
 * be unpredictable as the database is changed from different threads. This can equally well occur
 * when the component is used on multiple machines or multiple instances are used, so this is not a
 * thread-safety concern.
 *
 * @author aubergineanode, urtks
 * @version 1.0
 */
@Component
public class DeliverablePersistence {
  /** Represents the sql statement to load deliverables without submission. */
  private static final String LOAD_DELIVERABLES_WITHOUT_SUBMISSION_SQL =
      "SELECT "
          + "resource.project_id, project_phase.project_phase_id, "
          + "resource.resource_id, deliverable_lu.required, "
          + "deliverable_lu.deliverable_id, deliverable_lu.create_user, deliverable_lu.create_date, "
          + "deliverable_lu.modify_user, deliverable_lu.modify_date, "
          + "deliverable_lu.name, deliverable_lu.description "
          + "FROM deliverable_lu "
          + "INNER JOIN resource ON resource.resource_role_id = deliverable_lu.resource_role_id "
          + "INNER JOIN project_phase ON project_phase.project_id = resource.project_id AND project_phase.phase_type_id = deliverable_lu.phase_type_id "
          + "WHERE deliverable_lu.per_submission = 0 AND ";

  /** Represents the sql statement to load deliverables with submission. */
  private static final String LOAD_DELIVERABLES_WITH_SUBMISSION_SQL =
      "SELECT "
          + "upload.project_id, project_phase.project_phase_id, resource.resource_id, "
          + "submission.submission_id, deliverable_lu.required, "
          + "deliverable_lu.deliverable_id, deliverable_lu.create_user, deliverable_lu.create_date, "
          + "deliverable_lu.modify_user, deliverable_lu.modify_date, "
          + "deliverable_lu.name, deliverable_lu.description "
          + "FROM deliverable_lu "
          + "INNER JOIN resource ON resource.resource_role_id = deliverable_lu.resource_role_id "
          + "INNER JOIN project_phase ON project_phase.project_id = resource.project_id AND project_phase.phase_type_id = deliverable_lu.phase_type_id "
          + "INNER JOIN upload ON upload.project_id = resource.project_id "
          + "INNER JOIN submission ON submission.upload_id = upload.upload_id "
          + "INNER JOIN submission_status_lu ON submission.submission_status_id = submission_status_lu.submission_status_id "
          + "WHERE deliverable_lu.per_submission = 1 AND submission_status_lu.name = 'Active' AND ";

  @Value("${deliverable.persistence.entity-manager-name}")
  private String entityManagerName;

  @Autowired @Qualifier("entityManagerMap")private Map<String, EntityManager> entityManagerMap;
  private EntityManager entityManager;

  @PostConstruct
  public void postRun() {
    entityManager = entityManagerMap.get(entityManagerName);
  }

  /**
   * Loads the deliverables associated with the given deliverable id and resource id. There may be
   * more than one deliverable returned. If there is no matching deliverable in the persistence, an
   * empty array should be returned.
   *
   * @return The matching deliverables (possibly expanded by matching with each active submission,
   *     if it is a "per submission" deliverable), or an empty array.
   * @param deliverableId The id of the deliverable
   * @param resourceId The resource id of deliverable
   * @param phaseId The phase id of deliverable
   * @throws IllegalArgumentException If deliverableId is <= 0 or resourceId <=0
   * @throws DeliverablePersistenceException If there is an error when reading the persistence
   */
  public Deliverable[] loadDeliverables(Long deliverableId, Long resourceId, Long phaseId)
      throws DeliverablePersistenceException {
    Helper.assertIdNotUnset(deliverableId, "deliverableId");
    Helper.assertIdNotUnset(resourceId, "resourceId");
    Helper.assertIdNotUnset(phaseId, "phaseId");

    return loadDeliverables(
        new Long[] {deliverableId}, new Long[] {resourceId}, new Long[] {phaseId});
  }

  /**
   * Loads the deliverable associated with the given submission and resource. The deliverable must
   * be a "per submission" deliverable and the given submission must be "Active". If this is not the
   * case, null is returned.
   *
   * @return The deliverable, or null if there is no "per submission" deliverable for the given id,
   *     or submission is not an 'Active' submission
   * @param deliverableId The id of the deliverable
   * @param resourceId The resource id of deliverable
   * @param phaseId The phase id of deliverable
   * @param submissionId The id of the submission the deliverable should be associated with
   * @throws IllegalArgumentException If deliverableId, resourceId or submissionId is <= 0
   * @throws DeliverablePersistenceException If there is an error when reading the persistence data
   */
  public Deliverable loadDeliverable(
      long deliverableId, long resourceId, long phaseId, long submissionId)
      throws DeliverablePersistenceException {
    Helper.assertIdNotUnset(deliverableId, "deliverableId");
    Helper.assertIdNotUnset(resourceId, "resourceId");
    Helper.assertIdNotUnset(phaseId, "phaseId");
    Helper.assertIdNotUnset(submissionId, "submissionId");

    Deliverable[] deliverables =
        loadDeliverables(
            new Long[] {deliverableId},
            new Long[] {resourceId},
            new Long[] {phaseId},
            new Long[] {submissionId});

    return (deliverables.length == 0) ? null : deliverables[0];
  }

  /**
   * Loads all Deliverables with the given ids and resource ids from persistence. May return a
   * 0-length array.
   *
   * @param deliverableIds The ids of deliverables to load
   * @param resourceIds The resource ids of deliverables to load
   * @param phaseIds The phase ids of deliverables to load
   * @return The loaded deliverables
   * @throws IllegalArgumentException if deliverableIds or resourceIds is null or any id is <= 0
   * @throws IllegalArgumentException If the two arguments do not have the same number of elements
   * @throws DeliverablePersistenceException if there is an error when reading the persistence data
   */
  public Deliverable[] loadDeliverables(Long[] deliverableIds, Long[] resourceIds, Long[] phaseIds)
      throws DeliverablePersistenceException {
    Helper.assertLongArrayNotNullAndOnlyHasPositive(deliverableIds, "deliverableIds");
    Helper.assertLongArrayNotNullAndOnlyHasPositive(resourceIds, "resourceIds");
    Helper.assertLongArrayNotNullAndOnlyHasPositive(phaseIds, "phaseIds");

    if ((deliverableIds.length != resourceIds.length)
        || (deliverableIds.length != phaseIds.length)) {
      throw new IllegalArgumentException(
          "deliverableIds, resourceIds and phaseIds should have" + " the same number of elements.");
    }

    // simply return an empty Deliverable array if deliverableIds is empty
    if (deliverableIds.length == 0) {
      return new Deliverable[0];
    }

    // build the match condition string.
    StringBuffer stringBuffer = new StringBuffer();

    stringBuffer.append('(');

    for (int i = 0; i < deliverableIds.length; ++i) {
      if (i != 0) {
        stringBuffer.append(" OR ");
      }

      stringBuffer.append('(');
      stringBuffer.append("deliverable_lu.deliverable_id=");
      stringBuffer.append(deliverableIds[i]);
      stringBuffer.append(" AND ");
      stringBuffer.append("resource.resource_id=");
      stringBuffer.append(resourceIds[i]);
      stringBuffer.append(" AND ");
      stringBuffer.append("project_phase.project_phase_id=");
      stringBuffer.append(phaseIds[i]);
      stringBuffer.append(")");
    }

    stringBuffer.append(')');
    String matchCondition = stringBuffer.toString();
    try {
      // get the non-"per submission" deliverables
      Deliverable[] deliverablesWithoutSubmission =
          loadDeliverablesWithoutSubmission(matchCondition);

      // get the "per submission" deliverables
      Deliverable[] deliverablesWithSubmission = loadDeliverablesWithSubmission(matchCondition);

      // merge the two arrays into one array
      Deliverable[] deliverables =
          new Deliverable[deliverablesWithoutSubmission.length + deliverablesWithSubmission.length];

      System.arraycopy(
          deliverablesWithoutSubmission, 0, deliverables, 0, deliverablesWithoutSubmission.length);
      System.arraycopy(
          deliverablesWithSubmission,
          0,
          deliverables,
          deliverablesWithoutSubmission.length,
          deliverablesWithSubmission.length);

      return deliverables;

      // since the connection is read-only, no commit or rollback
      // operations are needed.
    } catch (PersistenceException e) {
      // wrap PersistenceException
      throw new DeliverablePersistenceException(
          "Unable to load deliverables without submission from the database.", e);
    }
  }

  /**
   * Loads the deliverables associated with the given submissions and resource. The deliverables
   * must be "per submission" deliverables and the given submissions must be "Active". Pairs of ids
   * not meeting this requirement will not be returned.
   *
   * @param deliverableIds The ids of deliverables to load
   * @param resourceIds The resource ids of deliverables to load
   * @param phaseIds The phase ids of deliverables to load
   * @param submissionIds The ids of the submission for each deliverable
   * @return The loaded deliverables
   * @throws IllegalArgumentException if any array is null or any id (in either array) is <= 0
   * @throws IllegalArgumentException If the three arguments do not have the same number of elements
   * @throws DeliverablePersistenceException if there is an error when reading the persistence data
   */
  public Deliverable[] loadDeliverables(
      Long[] deliverableIds, Long[] resourceIds, Long[] phaseIds, Long[] submissionIds)
      throws DeliverablePersistenceException {
    Helper.assertLongArrayNotNullAndOnlyHasPositive(deliverableIds, "deliverableIds");
    Helper.assertLongArrayNotNullAndOnlyHasPositive(resourceIds, "resourceIds");
    Helper.assertLongArrayNotNullAndOnlyHasPositive(phaseIds, "phaseIds");
    Helper.assertLongArrayNotNullAndOnlyHasPositive(submissionIds, "submissionIds");

    if ((deliverableIds.length != submissionIds.length)
        || (deliverableIds.length != phaseIds.length)
        || (deliverableIds.length != resourceIds.length)) {
      throw new IllegalArgumentException(
          "deliverableIds, resourceIds, phaseIds and submissionIds should have the same number of elements");
    }

    // simply return an empty Deliverable array if deliverableIds is empty
    if (deliverableIds.length == 0) {
      return new Deliverable[0];
    }

    // build the match condition string.
    StringBuffer stringBuffer = new StringBuffer();

    stringBuffer.append('(');

    for (int i = 0; i < deliverableIds.length; ++i) {
      if (i != 0) {
        stringBuffer.append(" OR ");
      }

      stringBuffer.append('(');
      stringBuffer.append("deliverable_lu.deliverable_id=");
      stringBuffer.append(deliverableIds[i]);
      stringBuffer.append(" AND ");
      stringBuffer.append("submission.submission_id=");
      stringBuffer.append(submissionIds[i]);
      stringBuffer.append(" AND ");
      stringBuffer.append("resource.resource_id=");
      stringBuffer.append(resourceIds[i]);
      stringBuffer.append(" AND ");
      stringBuffer.append("project_phase.project_phase_id=");
      stringBuffer.append(phaseIds[i]);
      stringBuffer.append(")");
    }

    stringBuffer.append(')');
    String matchCondition = stringBuffer.toString();
    try {
      return loadDeliverablesWithSubmission(matchCondition);
    } catch (PersistenceException e) {
      // wrap PersistenceException
      throw new DeliverablePersistenceException(
          "Unable to load deliverables with submission from the database.", e);
    }
  }

  /**
   * Loads the deliverables which are NOT associated with submissions. The deliverables must NOT be
   * "per submission" deliverables. An additional match condition that much be meet is added to the
   * tail of the sql string.
   *
   * @param matchCondition the addition condition
   * @return the loaded deliverables
   * @throws PersistenceException if there is an error when reading the persistence data
   */
  private Deliverable[] loadDeliverablesWithoutSubmission(String matchCondition)
      throws PersistenceException {
    var result =
        executeSql(entityManager, LOAD_DELIVERABLES_WITHOUT_SUBMISSION_SQL + matchCondition);

    // create a new Deliverable array
    Deliverable[] deliverables = new Deliverable[result.size()];

    // enumerate each data row
    for (int i = 0; i < result.size(); ++i) {
      // reference the current data row
      var row = result.get(i);
      // get the parameters of the Deliverable constructor
      long projectId = getLong(row, "project_id");
      long phaseId = getLong(row, "project_phase_id");
      long resourceId = getLong(row, "resource_id");
      boolean required = getBoolean(row, "required");

      // create a new Deliverable object
      Deliverable deliverable = new Deliverable(projectId, phaseId, resourceId, null, required);

      // then fill the NamedDeliverableStructure fields from startIndex
      Helper.loadNamedEntityFieldsSequentially(
          deliverable,
          new Object[] {
            getLong(row, "deliverable_id"),
            getString(row, "create_user"),
            getDate(row, "create_date"),
            getString(row, "modify_user"),
            getDate(row, "modify_date"),
            getString(row, "name"),
            getString(row, "description")
          },
          0);

      // assign it to the array
      deliverables[i] = deliverable;
    }

    return deliverables;
  }

  /**
   * Loads the deliverables associated with the given submissions. The deliverables must be "per
   * submission" deliverables and the given submissions must be "Active". An additional match
   * condition that much be meet is added to the tail of the sql string.
   *
   * @param matchCondition the addition condition
   * @return the loaded deliverables
   * @throws PersistenceException if there is an error when reading the persistence data
   */
  private Deliverable[] loadDeliverablesWithSubmission(String matchCondition)
      throws PersistenceException {
    var result = executeSql(entityManager, LOAD_DELIVERABLES_WITH_SUBMISSION_SQL + matchCondition);

    // create a new Deliverable array
    Deliverable[] deliverables = new Deliverable[result.size()];

    // enumerate each data row
    for (int i = 0; i < result.size(); ++i) {
      // reference the current data row
      var row = result.get(i);
      // get the parameters of the Deliverable's constructor
      long projectId = getLong(row, "project_id");
      long phaseId = getLong(row, "project_phase_id");
      long resourceId = getLong(row, "resource_id");
      Long submissionId = getLong(row, "submission_id");
      boolean required = getBoolean(row, "required");

      // create a new Deliverable object
      Deliverable deliverable =
          new Deliverable(projectId, phaseId, resourceId, submissionId, required);

      // then fill the NamedDeliverableStructure fields from startIndex
      Helper.loadNamedEntityFieldsSequentially(
          deliverable,
          new Object[] {
            getLong(row, "deliverable_id"),
            getString(row, "create_user"),
            getDate(row, "create_date"),
            getString(row, "modify_user"),
            getDate(row, "modify_date"),
            getString(row, "name"),
            getString(row, "description")
          },
          0);

      // assign it to the array
      deliverables[i] = deliverable;
    }

    return deliverables;
  }
}
