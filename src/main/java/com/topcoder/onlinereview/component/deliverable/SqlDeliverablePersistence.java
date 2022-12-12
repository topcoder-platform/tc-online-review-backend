/*
 * Copyright (C) 2006-2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.deliverable;

import com.topcoder.onlinereview.component.grpcclient.deliverable.DeliverableServiceRpc;
import com.topcoder.onlinereview.component.project.management.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SqlDeliverablePersistence {

  @Autowired
  private DeliverableServiceRpc deliverableServiceRpc;

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

    try {
      // since two queries may be needed, auto-commit mode is
      // disabled to ensure that the database snapshot does not change during the two queries.
      if (submissionIds != null) {
        return deliverableServiceRpc.loadDeliverablesWithSubmission(deliverableIds, resourceIds, phaseIds, submissionIds);
      }
      return deliverableServiceRpc.loadDeliverablesWithoutSubmission(deliverableIds, resourceIds, phaseIds);

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
}
