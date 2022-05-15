/*
 * Copyright (C) 2006-2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.deliverable;

import com.topcoder.onlinereview.component.project.management.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.topcoder.onlinereview.util.CommonUtils.executeSql;
import static com.topcoder.onlinereview.util.CommonUtils.getBoolean;
import static com.topcoder.onlinereview.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.util.CommonUtils.getString;

@Slf4j
@Component
public class SqlDeliverablePersistence {

  /**
   * Represents the sql statement to load deliverables with submission.
   *
   * <p>Changes in 1.1: added join with submission_type_lu - to ensure that submission has assigned
   * submission type.
   */
  private static final String LOAD_DELIVERABLES_WITH_SUBMISSION_SQL =
      "SELECT "
          + "u.project_id, p.project_phase_id, r.resource_id, s.submission_id, d.required, d.deliverable_id, "
          + "d.create_user, d.create_date, d.modify_user, d.modify_date, d.name, d.description FROM deliverable_lu d "
          + "INNER JOIN resource r ON r.resource_role_id = d.resource_role_id "
          + "INNER JOIN project_phase p ON p.project_id = r.project_id AND p.phase_type_id = d.phase_type_id "
          + "INNER JOIN upload u ON u.project_id = r.project_id and u.upload_status_id=1 and u.upload_type_id=1 "
          + "INNER JOIN submission s ON s.submission_type_id = d.submission_type_id AND s.submission_status_id = 1 and s.upload_id = u.upload_id "
          + "WHERE d.submission_type_id IS NOT NULL "
          + "AND u.create_date = ( "
          + "    CASE "
          + "        WHEN p.phase_type_id = 18 "
          + "        THEN "
          + "                (SELECT MIN(u1.create_date) "
          + "                 FROM upload u1 "
          + "                 INNER JOIN submission s1 ON s1.upload_id = u1.upload_id AND s1.submission_status_id = 1"
          + "                 WHERE s1.submission_type_id = d.submission_type_id "
          + "                 AND   u1.upload_status_id = 1 AND u1.upload_type_id = 1 "
          + "                 AND   u1.project_id = p.project_id) "
          + "        ELSE u.create_date "
          + "    END "
          + ")  "
          + "AND ";

  /** Represents the sql statement to load deliverables without submission. */
  private static final String LOAD_DELIVERABLES_WITHOUT_SUBMISSION_SQL =
      "SELECT "
          + "r.project_id, p.project_phase_id, r.resource_id, d.required, "
          + "d.deliverable_id, d.create_user, d.create_date, d.modify_user, d.modify_date, "
          + "d.name, d.description FROM deliverable_lu d "
          + "INNER JOIN resource r ON r.resource_role_id = d.resource_role_id "
          + "INNER JOIN project_phase p ON p.project_id = r.project_id AND p.phase_type_id = d.phase_type_id "
          + "WHERE d.submission_type_id IS NULL AND ";

  @Value("${deliverable.persistence.entity-manager-name}")
  private String entityManagerName;

  @Autowired
  @Qualifier("entityManagerMap")
  private Map<String, EntityManager> entityManagerMap;

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
   * @param deliverableId The id of the deliverable
   * @param resourceId The resource id of deliverable
   * @param phaseId The phase id of deliverable
   * @return The matching deliverables (possibly expanded by matching with each active submission,
   *     if it is a "per submission" deliverable), or an empty array.
   * @throws IllegalArgumentException If deliverableId is <= 0 or resourceId <=0
   * @throws DeliverablePersistenceException If there is an error when reading the persistence
   */
  public Deliverable[] loadDeliverables(long deliverableId, long resourceId, long phaseId)
      throws DeliverablePersistenceException {
    Helper.assertIdNotUnset(deliverableId, "deliverableId");
    Helper.assertIdNotUnset(resourceId, "resourceId");
    Helper.assertIdNotUnset(phaseId, "phaseId");

    log.debug(
        new LogMessage(
                deliverableId,
                null,
                "load Deliverables with resourceId:"
                    + resourceId
                    + " phaseId:"
                    + phaseId
                    + ", delegate to loadDeliverables(long[] deliverableIds, long[] resourceIds, long[] phaseIds).")
            .toString());
    return loadDeliverables(
        new Long[] {deliverableId}, new Long[] {resourceId}, new Long[] {phaseId});
  }

  /**
   * Loads the deliverable associated with the given submission and resource. The deliverable must
   * be a "per submission" deliverable and the given submission must be "Active". If this is not the
   * case, null is returned.
   *
   * @param deliverableId The id of the deliverable
   * @param resourceId The resource id of deliverable
   * @param phaseId The phase id of deliverable
   * @param submissionId The id of the submission the deliverable should be associated with
   * @return The deliverable, or null if there is no "per submission" deliverable for the given id,
   *     or submission is not an 'Active' submission
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

    log.debug(
        new LogMessage(
                deliverableId,
                null,
                "load Deliverable with resourceId:"
                    + resourceId
                    + " phaseId:"
                    + phaseId
                    + " and submissionId:"
                    + submissionId
                    + ", delegate to loadDeliverables(long[] deliverableIds,"
                    + " long[] resourceIds, long[] phaseIds, long[] submissionIds).")
            .toString());

    Deliverable[] deliverables =
        loadDeliverables(
            new Long[] {deliverableId},
            new Long[] {resourceId},
            new Long[] {phaseId},
            new Long[] {submissionId});
    return deliverables.length == 0 ? null : deliverables[0];
  }

  /**
   * Loads all Deliverables with the given ids and resource ids from persistence. May return a
   * 0-length array.
   *
   * @param deliverableIds The ids of deliverables to load
   * @param resourceIds The resource ids of deliverables to load
   * @param phaseIds The phase ids of deliverables to load
   * @return The loaded deliverables
   * @throws IllegalArgumentException if deliverableIds or resourceIds is null or any id is <= 0 If
   *     the two arguments do not have the same number of elements
   * @throws DeliverablePersistenceException if there is an error when reading the persistence data
   */
  public Deliverable[] loadDeliverables(Long[] deliverableIds, Long[] resourceIds, Long[] phaseIds)
      throws DeliverablePersistenceException {
    return loadDeliverablesHelper(deliverableIds, resourceIds, phaseIds, null);
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
   * @throws IllegalArgumentException if any array is null or any id (in either array) is <= 0 If
   *     the three arguments do not have the same number of elements
   * @throws DeliverablePersistenceException if there is an error when reading the persistence data
   */
  public Deliverable[] loadDeliverables(
      Long[] deliverableIds, Long[] resourceIds, Long[] phaseIds, Long[] submissionIds)
      throws DeliverablePersistenceException {
    return loadDeliverablesHelper(deliverableIds, resourceIds, phaseIds, submissionIds);
  }

  /**
   * Constructs WHERE clause of the SQL statement for retrieving deliverables.
   *
   * @param deliverableIds The ids of deliverables to load, should not be null
   * @param resourceIds The resource ids of deliverables to load, should not be null
   * @param phaseIds The phase ids of deliverables to load, should not be null
   * @param submissionIds The ids of the submission for each deliverable, can be null
   * @return SQL WHERE clause
   */
  private String constructSQLCondition(
      Long[] deliverableIds, Long[] resourceIds, Long[] phaseIds, Long[] submissionIds) {
    Set<Long> distinctDeliverableIds = new HashSet<>();
    for (long deliverableId : deliverableIds) {
      distinctDeliverableIds.add(deliverableId);
    }

    // build the match condition string.
    StringBuilder stringBuffer = new StringBuilder();
    stringBuffer.append('(');

    // To reduce size of the SQL we move the equality check for deliverable_id out of the braces.
    // We do that by several linear traversal through the arrays each time picking up the only items
    // with
    // a certain deliverable ID.
    boolean firstDeliverable = true;
    for (Long deliverableId : distinctDeliverableIds) {
      if (!firstDeliverable) {
        stringBuffer.append(" OR ");
      }
      firstDeliverable = false;
      stringBuffer.append("(d.deliverable_id=").append(deliverableId).append(" AND (");

      // To reduce size of the SQL even further, we now group by phase ID.
      Map<Long, List<Long>> submissionsByPhase = new HashMap<Long, List<Long>>();
      Map<Long, List<Long>> resourcesByPhase = new HashMap<Long, List<Long>>();
      for (int i = 0; i < deliverableIds.length; ++i) {
        if (deliverableIds[i] == deliverableId) {
          List<Long> resources = resourcesByPhase.get(phaseIds[i]);
          if (resources == null) {
            resources = new ArrayList<Long>();
            resourcesByPhase.put(phaseIds[i], resources);
          }
          resources.add(resourceIds[i]);

          if (submissionIds != null) {
            List<Long> submissions = submissionsByPhase.get(phaseIds[i]);
            if (submissions == null) {
              submissions = new ArrayList<Long>();
              submissionsByPhase.put(phaseIds[i], submissions);
            }
            submissions.add(submissionIds[i]);
          }
        }
      }

      // Now loop through all phases and for each phase construct a separate block of conditions
      // separated by OR.
      boolean firstPhase = true;
      for (Long phaseId : resourcesByPhase.keySet()) {
        if (!firstPhase) {
          stringBuffer.append(" OR ");
        }
        firstPhase = false;
        stringBuffer.append("(p.project_phase_id=").append(phaseId).append(" AND (");

        List<Long> resources = resourcesByPhase.get(phaseId);
        List<Long> submissions = submissionsByPhase.get(phaseId);
        for (int i = 0; i < resources.size(); ++i) {
          if (i > 0) {
            stringBuffer.append(" OR ");
          }

          stringBuffer.append("(");
          if (submissions != null) {
            stringBuffer.append("s.submission_id=").append(submissions.get(i)).append(" AND ");
          }
          stringBuffer.append("r.resource_id=").append(resources.get(i)).append(")");
        }

        stringBuffer.append("))");
      }

      stringBuffer.append("))");
    }

    stringBuffer.append(')');
    return stringBuffer.toString();
  }

  /**
   * Loads the deliverables associated with the given IDs, resource and (optionally) submissions.
   *
   * @param deliverableIds The ids of deliverables to load, should not be null
   * @param resourceIds The resource ids of deliverables to load, should not be null
   * @param phaseIds The phase ids of deliverables to load, should not be null
   * @param submissionIds The ids of the submission for each deliverable, can be null
   * @return The loaded deliverables
   * @throws IllegalArgumentException If any array is null or any id (in either array) is <= 0
   *     (submissionIds is allowed to be null) If the three arguments do not have the same number of
   *     elements
   * @throws DeliverablePersistenceException if there is an error when reading the persistence data
   */
  private Deliverable[] loadDeliverablesHelper(
      Long[] deliverableIds, Long[] resourceIds, Long[] phaseIds, Long[] submissionIds)
      throws DeliverablePersistenceException {
    Helper.assertLongArrayNotNullAndOnlyHasPositive(deliverableIds, "deliverableIds");
    Helper.assertLongArrayNotNullAndOnlyHasPositive(resourceIds, "resourceIds");
    Helper.assertLongArrayNotNullAndOnlyHasPositive(phaseIds, "phaseIds");

    if (deliverableIds.length != resourceIds.length || deliverableIds.length != phaseIds.length) {
      throw new IllegalArgumentException(
          "deliverableIds, resourceIds and phaseIds should have" + " the same number of elements.");
    }
    if (submissionIds != null) {
      Helper.assertLongArrayNotNullAndOnlyHasPositive(submissionIds, "submissionIds");
      if (deliverableIds.length != submissionIds.length) {
        throw new IllegalArgumentException(
            "deliverableIds and submissionIds should have the same number of elements");
      }
    }

    // simply return an empty Deliverable array if deliverableIds is empty
    if (deliverableIds.length == 0) {
      return new Deliverable[0];
    }

    log.debug(
        new LogMessage(
                null,
                null,
                "load Deliverables with deliverableIds:"
                    + deliverableIds
                    + ", resourceId:"
                    + resourceIds
                    + ",phaseId:"
                    + phaseIds
                    + " and submissionIds:"
                    + submissionIds)
            .toString());

    String matchCondition =
        constructSQLCondition(deliverableIds, resourceIds, phaseIds, submissionIds);
    try {
      // since two queries may be needed, auto-commit mode is
      // disabled to ensure that the database snapshot does not change during the two queries.
      if (submissionIds != null) {
        return loadDeliverablesWithSubmission(matchCondition);
      }

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

    } catch (PersistenceException e) {
      log.error(
          new LogMessage(
                  null,
                  null,
                  "Failed to load Deliverables with deliverableIds:"
                      + deliverableIds
                      + ", resourceId:"
                      + resourceIds
                      + ",phaseId:"
                      + phaseIds
                      + " and submissionIds:"
                      + submissionIds)
              .toString(),
          e);
      // wrap PersistenceException
      throw new DeliverablePersistenceException(
          "Unable to load deliverables from the database.", e);
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

    // load deliverables without submission
    var rows = executeSql(entityManager, LOAD_DELIVERABLES_WITHOUT_SUBMISSION_SQL + matchCondition);
    // create a new Deliverable array
    Deliverable[] deliverables = new Deliverable[rows.size()];

    // enumerate each data row
    for (int i = 0; i < rows.size(); ++i) {
      // reference the current data row
      var row = rows.get(i);
      // get the parameters of the Deliverable's constructor
      long projectId = getLong(row, "project_id");
      long phaseId = getLong(row, "project_phase_id");
      long resourceId = getLong(row, "resource_id");
      boolean required = getBoolean(row, "required");

      // create a new Deliverable object
      Deliverable deliverable = new Deliverable(projectId, phaseId, resourceId, null, required);

      // then fill the NamedDeliverableStructure fields
      deliverable.setId(getLong(row, "deliverable_id"));
      deliverable.setCreationUser(getString(row, "create_user"));
      deliverable.setCreationTimestamp(getDate(row, "create_date"));
      deliverable.setModificationUser(getString(row, "modify_user"));
      deliverable.setModificationTimestamp(getDate(row, "modify_date"));
      deliverable.setName(getString(row, "name"));
      deliverable.setDescription(getString(row, "description"));

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

    // load deliverables
    var rows = executeSql(entityManager, LOAD_DELIVERABLES_WITH_SUBMISSION_SQL + matchCondition);

    // create a new Deliverable array
    Deliverable[] deliverables = new Deliverable[rows.size()];

    // enumerate each data row
    for (int i = 0; i < rows.size(); ++i) {
      // reference the current data row
      var row = rows.get(i);

      // get the parameters of the Deliverable's constructor
      long projectId = getLong(row, "project_id");
      long phaseId = getLong(row, "project_phase_id");
      long resourceId = getLong(row, "resource_id");
      Long submissionId = getLong(row, "submission_id");
      boolean required = getBoolean(row, "required");

      // create a new Deliverable object
      Deliverable deliverable =
          new Deliverable(projectId, phaseId, resourceId, submissionId, required);

      // then fill the NamedDeliverableStructure fields
      deliverable.setId(getLong(row, "deliverable_id"));
      deliverable.setCreationUser(getString(row, "create_user"));
      deliverable.setCreationTimestamp(getDate(row, "create_date"));
      deliverable.setModificationUser(getString(row, "modify_user"));
      deliverable.setModificationTimestamp(getDate(row, "modify_date"));
      deliverable.setName(getString(row, "name"));
      deliverable.setDescription(getString(row, "description"));

      // assign it to the array
      deliverables[i] = deliverable;
    }
    return deliverables;
  }
}
