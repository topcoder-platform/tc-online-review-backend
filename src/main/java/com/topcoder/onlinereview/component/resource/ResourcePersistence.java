/*
 * Copyright (C) 2009-2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.resource;

import com.topcoder.onlinereview.component.grpcclient.resource.ResourceServiceRpc;
import com.topcoder.onlinereview.component.project.management.LogMessage;
import com.topcoder.onlinereview.grpc.resource.proto.NotificationProto;
import com.topcoder.onlinereview.grpc.resource.proto.NotificationTypeProto;
import com.topcoder.onlinereview.grpc.resource.proto.ResourceInfoProto;
import com.topcoder.onlinereview.grpc.resource.proto.ResourceProto;
import com.topcoder.onlinereview.grpc.resource.proto.ResourceRoleProto;
import com.topcoder.onlinereview.grpc.resource.proto.ResourceSubmissionProto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

/**
 * The <code>AbstractResourcePersistence</code> class implements the <code>ResourcePersistence
 * </code> interface, in order to persist to the database.
 *
 * <p><i>Changes from 1.0.1 : </i> It contains most of the logic that was in the <code>
 * SqlResourcePersistence</code> class in version 1.0.1. This class does not cache a <code>
 * Connection</code> to the database. Instead, it uses the concrete implementation of the <code>
 * allows the transaction handling logic to be implemented in subclasses while the Statements,
 * queries, and ResultSets are handled in the abstract class.
 *
 * <p>Most methods in this class will just create and execute a single <code>PreparedStatement
 * </code>. However, some of the Resource related methods need to execute several PreparedStatments
 * in order to accomplish the update/insertion/deletion of the resource.
 *
 * <p><i>Version 1.2 Changes:</i> Please note that all the changes in version 1.2 revolve around the
 * changes to the association multiplicity between Resource and Submission which used to be 1 to 1
 * and is not 1 to N (i.e. a resource can be associated with multiple submissions)
 *
 * <p><i>Version 1.2.2 (Configurable Contest Terms Release Assembly v2.0) Changes:</i> Audit
 * information was added to when a resource is added, deleted or changes its user or role.
 *
 * <p><i>Version 1.2.3 Changes:</i> Performance improvements: added caching for the resource roles
 * and refactored the way the submissions are loaded for resources.
 *
 * <p><b>Thread Safety</b> : This class is thread-safe in the sense that multiple threads can not
 * corrupt its internal data structures. However, the results if used from multiple threads can be
 * unpredictable as the database is changed from different threads. This can equally well occur when
 * the component is used on multiple machines or multiple instances are used, so this is not a
 * thread-safety concern.
 *
 * @author aubergineanode
 * @author Chenhong
 * @author bendlund
 * @author mittu
 * @author AleaActaEst
 * @author George1
 * @author pulky
 * @author VolodymyrK
 * @version 1.2.6
 * @since 1.1
 */
@Component
public class ResourcePersistence {
  @Autowired
  private ResourceServiceRpc resourceServiceRpc;

  /** Logger instance using the class name as category */
  private static final Logger LOGGER = LoggerFactory.getLogger(ResourcePersistence.class.getName());

  /**
   * Represents the project user audit creation type
   *
   * @since 1.2.2
   */
  private static final int PROJECT_USER_AUDIT_CREATE_TYPE = 1;

  /**
   * Represents the project user audit deletion type
   *
   * @since 1.2.2
   */
  private static final int PROJECT_USER_AUDIT_DELETE_TYPE = 2;

  /**
   * Stores cached ResourceRoles mapped by their IDs. Used to avoid quering the DB each time for the
   * performance reasons.
   */
  private static Map<Long, ResourceRole> cachedResourceRoles = null;

  /**
   * Adds the given Resource to the persistence store. The resource must not already exist (by id)
   * in the persistence store.
   *
   * <p>Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
   * <code>ResourcePersistenceException</code>.
   *
   * <p>Note: since version 1.2.2, audit information is saved.
   *
   * @param resource The resource to add to the persistence store
   * @throws IllegalArgumentException If resource is <code>null</code> or its id is UNSET_ID or its
   *     <code>ResourceRole</code> is <code>null</code> or its creation/modification user/date is
   *     <code>null</code>
   * @throws ResourcePersistenceException If there is a failure to persist the change or a Resource
   *     with the id is already in the persistence.
   */
  public void addResource(Resource resource) throws ResourcePersistenceException {
    Util.checkResource(resource, false, true);

    LOGGER.debug(
        new LogMessage(
                resource.getId(),
                null,
                "add new resource to the project [id="
                    + (resource.getProject() == null ? "null" : resource.getProject())
                    + "] with role :"
                    + resource.getResourceRole().getName()
                    + " in the [id="
                    + (resource.getPhase() == null ? null : resource.getPhase())
                    + "] phase.")
            .toString());

    try {
      insertResource(resource);

      // if the submissions are not empty, persist the submission.
      Long[] submissions = resource.getSubmissions();
      for (Long submission : submissions) {
        insertSubmission(resource, submission);
      }

      // persist properties.
      Map map = resource.getAllProperties();

      for (Iterator iter = map.entrySet().iterator(); iter.hasNext(); ) {
        Entry entry = (Entry) iter.next();

        Integer resourceInfoTypeId = getResourceInfoTypeId(entry.getKey().toString());

        // if resource_info_type_id is found
        if (resourceInfoTypeId != null) {
          insertResourceInfo(resource, resourceInfoTypeId, entry.getValue().toString());
        }
      }

      auditProjectUser(resource, PROJECT_USER_AUDIT_CREATE_TYPE, null, null); // create

    } catch (Exception e) {
      LOGGER.debug(
          new LogMessage(
                  resource.getId(),
                  null,
                  "Unable to add resource to the project [id="
                      + resource.getProject()
                      + "] with role:"
                      + resource.getResourceRole().getName()
                      + " in [id="
                      + resource.getPhase()
                      + "] phase.",
                  e)
              .toString());
      throw new ResourcePersistenceException("Unable to insert resource.", e);
    }
  }

  /**
   * Inserts the <code>Resource</code> instance into the database.
   *
   * @param resource the <code>Resource</code> instance to be persist.
   */
  private void insertResource(Resource resource) {
    long newId = resourceServiceRpc.createResource(resource);
    resource.setId(newId);
  }

  /**
   * Inserts the submission information into database.
   *
   * @param resource the <code>Resource</code> instance to persist
   * @param submission the submission to insert
   * @throws SQLException if failed to persist submission.
   */
  private void insertSubmission(Resource resource, Long submission) {
    LOGGER.debug(
        "insert a record into the resource_submission with resource_id:"
            + resource.getId()
            + " and submission_id:"
            + getIdString(resource.getSubmissions()));
    resourceServiceRpc.createResourceSubmission(resource, submission);
  }

  /**
   * Inserts the properties of <code>Resource</code> into table resource_info.
   *
   * @param resource the <code>Resource</code> instance to be persisted.
   * @param resourceInfoTypeId the resource_info_type_id
   * @param value the property value to be persisted
   * @throws SQLException if failed to persist resource_info
   */
  private void insertResourceInfo(Resource resource, int resourceInfoTypeId, String value) {
    LOGGER.debug(
        "insert a record into the resource_info table with resource_id = "
            + resource.getId()
            + " and resource_info_type_id ="
            + resourceInfoTypeId
            + " value = "
            + value);
    resourceServiceRpc.createResourceInfo(resource, resourceInfoTypeId, value);
  }

  /**
   * Looks up table resource_info_type_lu for resource_info_type_id.
   *
   * @param name the name to look up in table.
   * @return Integer if exist, <code>null</code> if not
   * @throws SQLException if failed to look up resource_info_type_lu table for resource_info_type_id
   */
  private Integer getResourceInfoTypeId(String name) throws SQLException {
    return resourceServiceRpc.getResourceInfoTypeId(name);
  }

  /**
   * Deletes the given <code>Resource</code> (by id) in the persistence store. The <code>Resource
   * </code> must already be present in the persistence store, otherwise nothing is done.
   *
   * <p>Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
   * <code>ResourcePersistenceException</code>.
   *
   * <p>Note: since version 1.2.2, audit information is saved.
   *
   * @param resource The resource to remove
   * @throws IllegalArgumentException If resource is <code>null</code> or its id is UNSET_ID or its
   *     ResourceRole is <code>null</code>
   * @throws ResourcePersistenceException If there is a failure to persist the change.
   */
  public void deleteResource(Resource resource) throws ResourcePersistenceException {
    Util.checkResource(resource, true, false);

    LOGGER.debug(
        new LogMessage(
                resource.getId(),
                null,
                "delete resource in the project [id="
                    + resource.getProject()
                    + "] with role :"
                    + resource.getResourceRole().getName()
                    + " in the [id="
                    + (resource.getPhase() == null ? "null" : resource.getPhase())
                    + "] phase.")
            .toString());
    try {
      resourceServiceRpc.deleteResource(resource.getId());

      // audit deletion
      auditProjectUser(resource, PROJECT_USER_AUDIT_DELETE_TYPE, null, null); // delete
    } catch (Exception e) {
      LOGGER.debug(
          new LogMessage(
                  resource.getId(),
                  null,
                  "Unable to delete resource to the project [id="
                      + resource.getProject()
                      + "] with role:"
                      + resource.getResourceRole().getName()
                      + " in [id="
                      + resource.getPhase()
                      + "] phase.",
                  e)
              .toString());
      throw new ResourcePersistenceException("Fail to delete resource", e);
    }
  }

  /**
   * Updates the given Resource in the database with its currently set information. The Resource
   * must already be present in the persistence store.
   *
   * <p>The operator information should already have been put in the modification date/modification
   * user properties of the Resource. Any SQLException or DBConnectionException should be wrapped in
   * a ResourcePersistenceException.
   *
   * <p>Note that in version 1.2 we will be possibly updating multiple submission associations
   * (version 1.1 only deals with a single association) which means that the actual SQL will be
   * either modified slightly or it will have to be executed multiple times for each relevant
   * submission entry.
   *
   * <p>Note: since version 1.2.2, audit information is saved.
   *
   * @param resource The resource to update
   * @throws IllegalArgumentException If resource is <code>null</code> or its id is UNSET_ID or its
   *     <code>ResourceRole</code> is <code>null</code> or its modification user/date is <code>null
   *     </code>
   * @throws ResourcePersistenceException If there is a failure to persist the <code>Resource</code>
   *     , or the <code>Resource</code> is not already in the persistence.
   */
  public void updateResource(Resource resource) throws ResourcePersistenceException {
    Util.checkResource(resource, false, false);

    LOGGER.debug(
        new LogMessage(
                resource.getId(),
                null,
                "update resource in the project [id="
                    + (resource.getProject() == null ? "null" : resource.getProject())
                    + "] with role :"
                    + resource.getResourceRole().getName()
                    + " in the [id="
                    + (resource.getPhase() == null ? "null" : resource.getPhase())
                    + "] phase.")
            .toString());

    try {
      // get old resource role and user ID to save audit information in case it changed.
      Resource oldResource = getShallowResource(resource.getId());
      Long oldResourceRoleId = oldResource.getResourceRole().getId();
      Long oldResourceUserId = oldResource.getUserId();

      if (oldResourceRoleId != null
          && oldResourceRoleId.equals(resource.getResourceRole().getId())) {
        // if it's the same role, don't consider it for the audit
        oldResourceRoleId = null;
      }
      if (oldResourceUserId != null && oldResourceUserId.equals(resource.getUserId())) {
        // if it's the same user, don't consider it for the audit
        oldResourceUserId = null;
      }

      // Update the resource table.
      updateResourceTable(resource);

      // Update the resource_submission table accordingly.
      Long[] previousSubmissions = getSubmissionEntry(resource);
      Long[] submissions = resource.getSubmissions();

      // use the Set to check existing of previous submissions
      Set<Long> previousSubmissionsSet = new HashSet<>();

      if (previousSubmissions.length == 0 && submissions.length != 0) {
        // For each submission associated with Resource, insert submission.
        for (int i = 0; i < submissions.length; i++) {
          insertSubmission(resource, submissions[i]);
        }
      } else if (previousSubmissions.length != 0 && submissions.length == 0) {
        LOGGER.debug("delete resource submission with resource id:" + resource.getId());
        // remove previous submission.
        for (int i = 0; i < previousSubmissions.length; i++) {
          deleteResourceSubmission(resource, previousSubmissions[i]);
        }
      } else if (previousSubmissions.length != 0 && submissions.length != 0) {
        // update submission.

        // fill the previousSubmissionsSet
        for (int i = 0; i < previousSubmissions.length; i++) {
          if (!resource.containsSubmission(previousSubmissions[i])) {
            // if it is not in submissions of resource, delete it
            deleteResourceSubmission(resource, previousSubmissions[i]);
          } else {
            // add it to Set
            previousSubmissionsSet.add(previousSubmissions[i]);
          }
        }

        // handle the submissions
        for (int i = 0; i < submissions.length; i++) {
          if (previousSubmissionsSet.contains(submissions[i])) {
            // do update
            updateResourceSubmission(resource, submissions[i]);
          } else {
            // insert the submission
            insertSubmission(resource, submissions[i]);
          }
        }
      }

      // updating the extended properties
      Map previousProperties =
          selectExternalProperties(new Resource(resource.getId())).getAllProperties();

      for (Iterator iter = resource.getAllProperties().entrySet().iterator(); iter.hasNext(); ) {
        Entry entry = (Entry) iter.next();

        String key = entry.getKey().toString();
        String value = entry.getValue().toString();
        Object oldValue = previousProperties.get(key);
        if (value.equals(oldValue)) {
          // there is previous entry, but same current entry, do not hit the db.
          previousProperties.remove(key);

        } else if (oldValue == null) {
          // no previous entry, but current entry has, insert the current entry.
          Integer resourceInfoTypeId = getResourceInfoTypeId(key);

          // if resource_info_type_id is found
          if (resourceInfoTypeId != null) {
            insertResourceInfo(resource, resourceInfoTypeId, value);
          }

        } else if (previousProperties.get(key) != null) {
          // there is previous entry , but different from current entry, do an update.
          Integer resourceInfoTypeId = getResourceInfoTypeId(key);
          if (resourceInfoTypeId != null) {
            updateResourceInfo(resource.getId(), resourceInfoTypeId, value);
          }
          previousProperties.remove(key);
        }
      }

      // Up to now, what are left in the previousProperties are
      // properties which do not exist in the current properties list, we need to remove them from
      // db.
      for (Iterator iter = previousProperties.entrySet().iterator(); iter.hasNext(); ) {
        Entry entry = (Entry) iter.next();

        Integer resourceInfoTypeId = getResourceInfoTypeId(entry.getKey().toString());
        if (resourceInfoTypeId != null) {
          removeResourceInfo(resource.getId(), resourceInfoTypeId);
        }
      }

      // audit update with delete / create audit records
      if (oldResourceUserId != null || oldResourceRoleId != null) {
        auditProjectUser(
            resource,
            PROJECT_USER_AUDIT_DELETE_TYPE,
            oldResourceUserId,
            oldResourceRoleId); // delete
        auditProjectUser(resource, PROJECT_USER_AUDIT_CREATE_TYPE, null, null); // create
      }

    } catch (SQLException e) {
      LOGGER.error(
          new LogMessage(
                  resource.getId(),
                  null,
                  "Unable to update resource to the project [id="
                      + resource.getProject()
                      + "] with role:"
                      + resource.getResourceRole().getName()
                      + " in [id="
                      + resource.getPhase()
                      + "] phase.",
                  e)
              .toString());
      throw new ResourcePersistenceException("Fail to update resource", e);
    }
  }

  /**
   * Deletes the submission information into database.
   *
   * @param resource the <code>Resource</code> instance to persist
   * @param submission the submission to delete
   * @throws SQLException if failed to delete submission.
   */
  private void deleteResourceSubmission(Resource resource, Long submission) throws SQLException {
    resourceServiceRpc.deleteResourceSubmission(resource, submission);
  }

  /**
   * Updates the resource table with <code>Resource</code> instance.
   *
   * @param resource the <code>Resource</code> instance to update
   * @throws SQLException if failed to update <code>Resource</code> instance.
   * @throws ResourcePersistenceException the <code>Resource</code> is not already in the
   *     persistence.
   */
  private void updateResourceTable(Resource resource) {
    LOGGER.debug("update the resource table with resource id : " + resource.getId());
    resourceServiceRpc.updateResource(resource);
  }

  /**
   * Gets the submission entry array for <code>Resource</code> instance.
   *
   * @param resource the Resource instance
   * @return The Long array which contains the submission(s)
   * @throws SQLException if failed to get the submission entry for <code>Resource</code> instance.
   */
  private Long[] getSubmissionEntry(Resource resource) throws SQLException {
    List<Long> submissions = new ArrayList<>();
    List<ResourceSubmissionProto> result = resourceServiceRpc.getResourceSubmissions(new Long[]{resource.getId()});
    for (ResourceSubmissionProto r : result) {
      submissions.add(r.getSubmissionId());
    }
    return submissions.toArray(new Long[submissions.size()]);
  }

  /**
   * Loads all submissions for the specified resources and sets them in the resource's property.
   *
   * @param resources the list of Resource instances
   * @throws SQLException if failed to get the submission entry for <code>Resource</code> instances.
   */
  private void loadResourceSubmissions(List<Resource> resources) throws SQLException {
    Long[] resourceIds = new Long[resources.size()];
    for (int i = 0; i < resources.size(); i++) {
      resourceIds[i] = resources.get(i).getId();
    }
    Map<Long, List<Long>> submissionsMap = new HashMap<>();
    List<ResourceSubmissionProto> result = resourceServiceRpc.getResourceSubmissions(resourceIds);
    for (ResourceSubmissionProto r : result) {
      long resourceId = r.getResourceId();
      long submissionId = r.getSubmissionId();
      List<Long> submissions = submissionsMap.get(resourceId);
      if (submissions == null) {
        submissions = new ArrayList<>();
        submissionsMap.put(resourceId, submissions);
      }
      submissions.add(submissionId);
    }
    for (Resource resource : resources) {
      List<Long> submissions = submissionsMap.get(resource.getId());
      if (submissions != null) {
        resource.setSubmissions(submissions.toArray(new Long[submissions.size()]));
      }
    }
  }

  /**
   * Updates the submission of the <code>Resource</code> instance.
   *
   * @param resource the <code>Resource</code> instance
   * @param submission the submission to update
   * @throws SQLException if failed to update resource submission
   */
  private void updateResourceSubmission(Resource resource, Long submission) throws SQLException {

    LOGGER.debug("update the resource_submission table with the resource_id:" + resource.getId());
    resourceServiceRpc.updateResourceSubmission(resource, submission);
  }

  /**
   * Updates the resource_info table with the given connection, resource_id and
   * resource_info_type_id.
   *
   * @param resourceId the resource id.
   * @param resourceTypeInfoId the resource type info id.
   * @param value the value.
   * @throws SQLException if database operation fails for some reasons.
   */
  private void updateResourceInfo(long resourceId, int resourceTypeInfoId, String value)
      throws SQLException {
    LOGGER.debug(
        "update resource_info table with resource_id = "
            + resourceId
            + " and resource_info_type_id ="
            + resourceTypeInfoId
            + " value = "
            + value);
    resourceServiceRpc.updateResourceInfo(resourceId, resourceTypeInfoId, value);
  }

  /**
   * Removes resource info with the given connection, resource_id and resourceInfo type id.
   *
   * @param resourceId the resource id.
   * @param resourceInfoTypeId the resource info type id.
   * @throws SQLException if database operation fails for some reasons.
   */
  private void removeResourceInfo(long resourceId, int resourceInfoTypeId) throws SQLException {
    LOGGER.debug(
        "delete resource_info with resource_id:"
            + resourceId
            + " and resource_info_type_id:"
            + resourceInfoTypeId);
    resourceServiceRpc.deleteResourceInfo(resourceId, resourceInfoTypeId);
  }

  /**
   * Selects all external properties for <code>Resource</code>, and set them into resource and
   * returned.
   *
   * @param resource the resource instance
   * @return the resource instance with all external properties set.
   * @throws ResourcePersistenceException if failed to select all external properties for this
   *     resource instance.
   */
  private Resource selectExternalProperties(Resource resource) throws ResourcePersistenceException {
    try {
      List<ResourceInfoProto> result = resourceServiceRpc.getResourceInfo(resource.getId());
      for (ResourceInfoProto r : result) {
        resource.setProperty(r.getTypeName(), r.getValue());
      }
      return resource;
    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(null, null, "Failed to get external properties for resource.", e)
              .toString());
      throw new ResourcePersistenceException(
          "Failed to select external properties for resource.", e);
    }
  }

  /**
   * Loads the resource from the persistence with the given id.
   *
   * <p>Returns <code>null</code> if there is no resource for the given id.
   *
   * @return The loaded Resource
   * @param resourceId The id of the Resource to load
   * @throws IllegalArgumentException If resourceId is not greater than 0
   * @throws ResourcePersistenceException If there is an error loading the Resource
   */
  public Resource loadResource(long resourceId) throws ResourcePersistenceException {
    Util.checkPositiveValue(resourceId, "resourceId");

    LOGGER.debug(new LogMessage(resourceId, null, "load resource.").toString());

    try {
      ResourceProto result = resourceServiceRpc.getResource(resourceId);
      if (result != null) {
        Resource resource = constructResource(result);

        // add submissions into resource
        resource.setSubmissions(getSubmissionEntry(resource));

        // select all external properties for the resource instance.
        return selectExternalProperties(resource);
      }
      return null;
    } catch (SQLException e) {
      LOGGER.error(
          new LogMessage(resourceId, null, "Failed to load resource instance.", e).toString());
      throw new ResourcePersistenceException("Failed to load resource instance.", e);
    }
  }

  /**
   * Constructs a <code>Resource</code> instance from given <code>ResultSet</code> instance.
   *
   * @param rs the <code>ResultSet</code> instance
   * @return The Resource instance
   * @throws ResourcePersistenceException if failed to construct the <code>Resource</code> instance.
   */
  private Resource constructResource(ResourceProto r) throws ResourcePersistenceException {
    try {
      Resource resource = new Resource();
      resource.setId(r.getResourceId());
      ResourceRole role = this.loadResourceRole(r.getResourceRoleId());
      resource.setResourceRole(role);
      if (!r.hasProjectId()) {
        resource.setProject(null);
      } else {
        resource.setProject(r.getProjectId());
      }
      if (!r.hasProjectPhaseId()) {
        resource.setPhase(null);
      } else {
        resource.setPhase(r.getProjectPhaseId());
      }
      resource.setUserId(r.getUserId());

      resource.setCreationUser(r.getCreateUser());
      resource.setCreationTimestamp(new Date(r.getCreateDate().getSeconds() * 1000));
      resource.setModificationUser(r.getModifyUser());
      resource.setModificationTimestamp(new Date(r.getModifyDate().getSeconds() * 1000));

      return resource;
    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(null, null, "Failed to load the Resource from ResultSet.", e).toString());
      throw new ResourcePersistenceException("Failed to load the Resource from ResultSet.", e);
    }
  }

  /**
   * Adds a notification to the persistence store. A notification type with the given ID must
   * already exist in the persistence store, as must a project.
   *
   * <p>Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
   * <code>ResourcePersistenceException</code>.
   *
   * @param user The user id to add as a notification
   * @param project The project the notification is related to
   * @param notificationType The id of the notification type
   * @param operator The operator making the change
   * @throws IllegalArgumentException If user, project, or notificationType is &lt;= 0
   * @throws IllegalArgumentException If operator is <code>null</code>
   * @throws ResourcePersistenceException If there is an error making the change in the persistence
   *     store
   */
  public void addNotification(Long user, Long project, Long notificationType, String operator)
      throws ResourcePersistenceException {

    Util.checkPositiveValue(user, "user");
    Util.checkPositiveValue(project, "project");
    Util.checkPositiveValue(notificationType, "notificationType");
    Util.checkNull(operator, "operator");
    LOGGER.debug(
        new LogMessage(
                null,
                operator,
                "add notification(type="
                    + notificationType
                    + " in the project:"
                    + project
                    + " with external_ref user:"
                    + user)
            .toString());
    try {
      Date time = new Date();
      resourceServiceRpc.createNotification(user, project, notificationType, operator, time);
    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(
                  null,
                  operator,
                  "Failed to add notification(type="
                      + notificationType
                      + " in the project:"
                      + project
                      + " with external_ref user:"
                      + user,
                  e)
              .toString());
      throw new ResourcePersistenceException("Failed to insert notification for reason.", e);
    }
  }

  /**
   * Removes a notification from the persistence store. The given notification tuple identifier
   * (user, project, and notificationType) should already exists in the persistence store, otherwise
   * nothing will be done. Note that in this implementation the operator is not used.
   *
   * <p>Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
   * ResourcePersistenceException.
   *
   * @param user The user id of the notification to remove
   * @param project The project id of the notification to remove
   * @param notificationType The notification type id of the notification to remove
   * @param operator The operator making the change
   * @throws IllegalArgumentException If user, project, or notificationType is &lt;= 0 or operator
   *     is null
   * @throws ResourcePersistenceException If there is an error making the change in the persistence
   *     store
   */
  public void removeNotification(long user, long project, long notificationType, String operator)
      throws ResourcePersistenceException {
    Util.checkPositiveValue(user, "user");
    Util.checkPositiveValue(project, "project");
    Util.checkPositiveValue(notificationType, "notificationType");
    Util.checkNull(operator, "operator");
    LOGGER.debug(
        new LogMessage(
                null,
                operator,
                "Remove notification(type="
                    + notificationType
                    + " from the project:"
                    + project
                    + " with external_ref user:"
                    + user)
            .toString());
    try {
      resourceServiceRpc.deleteNotification(user, project, notificationType);
    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(
                  null,
                  operator,
                  "Failed to remove notification(type="
                      + notificationType
                      + " from the project:"
                      + project
                      + " with external_ref user:"
                      + user,
                  e)
              .toString());
      throw new ResourcePersistenceException("Failed to remove notification.", e);
    }
  }

  /**
   * Load the <code>Notification</code> for the given "id" triple from the persistence store.
   * Returns <code>null</code> if no entry in the persistence has the given user, project, and
   * notificationType.
   *
   * <p>Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
   * <code>ResourcePersistenceException</code>.
   *
   * @return The loaded notification
   * @param user The id of the user
   * @param project The id of the project
   * @param notificationType The id of the notificationType
   * @throws IllegalArgumentException If user, project, or notificationType is <= 0
   * @throws ResourcePersistenceException If there is an error making the change in the persistence
   *     store
   */
  public Notification loadNotification(long user, long project, long notificationType)
      throws ResourcePersistenceException {
    Util.checkPositiveValue(user, "user");
    Util.checkPositiveValue(project, "project");
    Util.checkPositiveValue(notificationType, "notificationType");

    LOGGER.debug(
        new LogMessage(
                null,
                null,
                "Load notification(type="
                    + notificationType
                    + " of the project:"
                    + project
                    + " with external_ref user:"
                    + user)
            .toString());
    try {
      NotificationProto result = resourceServiceRpc.getNotification(user, project, notificationType);
      if (result != null) {
        return constructNotification(result);
      }

      return null;
    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(
                  null,
                  null,
                  "Failed to Load notification(type="
                      + notificationType
                      + " of the project:"
                      + project
                      + " with external_ref user:"
                      + user)
              .toString());
      throw new ResourcePersistenceException("Failed to load the notification.", e);
    }
  }

  /**
   * Constructs the <code>Notification</code> instance from the <code>ResultSet</code> instance.
   *
   * @param rs the <code>ResultSet</code> instance
   * @return the constructed <code>Notification</code> instance.
   * @throws ResourcePersistenceException if failed to get the <code>Notification</code> instance
   *     from database.
   */
  private Notification constructNotification(NotificationProto n)
      throws ResourcePersistenceException {
    try {
      NotificationType type = loadNotificationType(n.getNotificationTypeId());
      Notification notification =
          new Notification(n.getProjectId(), type, n.getExternalRefId());
      notification.setCreationUser(n.getCreateUser());
      notification.setCreationTimestamp(new Date(n.getCreateDate().getSeconds() * 1000));
      notification.setModificationUser(n.getModifyUser());
      notification.setModificationTimestamp(new Date(n.getModifyDate().getSeconds() * 1000));
      return notification;
    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(null, null, "Failed to construct Notification instance.", e).toString());
      throw new ResourcePersistenceException("Failed to construct Notification instance.", e);
    }
  }

  /**
   * Adds a notification type to the persistence store. The id of the notification type must already
   * be assigned to the <code>NotificationType</code> object passed to this method, and not already
   * exist in the persistence source.
   *
   * <p>Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
   * <code>ResourcePersistenceException</code>.
   *
   * @param notificationType The notification type to add.
   * @throws IllegalArgumentException If notificationType is null or its id is
   *     NotificationType.UNSET_ID or its name/description is null or its creation/modification
   *     user/date are null
   * @throws ResourcePersistenceException If there is an error updating the persistence
   */
  public void addNotificationType(NotificationType notificationType)
      throws ResourcePersistenceException {
    Util.checkNotificationType(notificationType, false, true);
    LOGGER.debug("add a notification type with id:" + notificationType.getId());
    try {
      long newId = resourceServiceRpc.createNotificationType(notificationType);
      notificationType.setId(newId);
    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(
                  null,
                  null,
                  "Failed add a notification type with id:" + notificationType.getId(),
                  e)
              .toString());
      throw new ResourcePersistenceException("Failed to add the notificationType instance.", e);
    }
  }

  /**
   * Removes a notification type from the persistence (by id) store. If no notification type exists
   * with the id of the notification type, nothing is done.
   *
   * @param notificationType The notification type to delete.
   * @throws IllegalArgumentException If notificationType is <code>null</code> or its id is
   *     UNSET_ID.
   * @throws ResourcePersistenceException If there is an error deleting the notification type in the
   *     persistence
   */
  public void deleteNotificationType(NotificationType notificationType)
      throws ResourcePersistenceException {
    Util.checkNotificationType(notificationType, true, false);
    LOGGER.debug("delete a notification type with id:" + notificationType.getId());
    try {
      resourceServiceRpc.deleteNotificationType(notificationType.getId());
    } catch (Exception ex) {
      LOGGER.error(
          new LogMessage(
                  null,
                  null,
                  "Failed delete a notification type with id:" + notificationType.getId(),
                  ex)
              .toString());
      throw new ResourcePersistenceException("Failed to delete the NotificationType instance.", ex);
    }
  }

  /**
   * Updates the notification type in the persistence store. The notification type (by id) must
   * exist in the persistence store.
   *
   * <p>Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
   * <code>ResourcePersistenceException</code>.
   *
   * @param notificationType The notification type to update
   * @throws IllegalArgumentException If notificationType is <code>null</code> or its id is UNSET_ID
   *     or its name/description is <code>null</code> or its modification user/date is <code>null
   *     </code>
   * @throws ResourcePersistenceException if there is an error updating the notification type in the
   *     persistence
   */
  public void updateNotificationType(NotificationType notificationType)
      throws ResourcePersistenceException {
    Util.checkNotificationType(notificationType, false, false);

    LOGGER.debug("Update a notification type with id:" + notificationType.getId());
    try {
      resourceServiceRpc.updateNotificationType(notificationType);
    } catch (Exception ex) {
      LOGGER.error(
          new LogMessage(
                  null,
                  null,
                  "Failed update a notification type with id:" + notificationType.getId(),
                  ex)
              .toString());
      throw new ResourcePersistenceException("Failed to update the notificationType instance.", ex);
    }
  }

  /**
   * Loads the notification type from the persistence with the given id. Returns <code>null</code>
   * if there is no notification type with the given id.
   *
   * <p>Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
   * <code>ResourcePersistenceException</code>.
   *
   * @return The loaded notification type
   * @param notificationTypeId The id of the notification type to load
   * @throws IllegalArgumentException If notificationTypeId is <= 0
   * @throws ResourcePersistenceException If there is an error loading the notification type
   */
  public NotificationType loadNotificationType(long notificationTypeId)
      throws ResourcePersistenceException {
    Util.checkPositiveValue(notificationTypeId, "notificationTypeId");

    LOGGER.debug("load a notification type with id:" + notificationTypeId);
    try {
      NotificationTypeProto result = resourceServiceRpc.getNotificationType(notificationTypeId);
      if (result != null) {
        NotificationType type = new NotificationType();
        type.setId(result.getNotificationTypeId());
        type.setName(result.getName());
        type.setDescription(result.getDescription());
        type.setCreationUser(result.getCreateUser());
        type.setCreationTimestamp(new Date(result.getCreateDate().getSeconds() * 1000));
        type.setModificationUser(result.getModifyUser());
        type.setModificationTimestamp(new Date(result.getModifyDate().getSeconds() * 1000));
        return type;
      }
      return null;
    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(
                  null, null, "Failed load a notification type with id:" + notificationTypeId, e)
              .toString());
      throw new ResourcePersistenceException("Fail to load notification type.", e);
    }
  }

  /**
   * Adds a resource role to the persistence store. The id of the resource role must already be
   * assigned to the notificationType object passed to this method, and not already exist in the
   * persistence source.
   *
   * @param resourceRole The resource role to add.
   * @throws IllegalArgumentException If resourceRole is <code>null</code> or its id is UNSET_ID or
   *     its name/description is <code>null</code> or its creation/modification date/user is <code>
   *     null</code>
   * @throws ResourcePersistenceException If there is an error updating the persistence
   */
  public void addResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {
    Util.checkResourceRole(resourceRole, false, true);
    LOGGER.debug(
        "add ResourceRole with id:" + resourceRole.getId() + " name:" + resourceRole.getName());
    try {
      long newId = resourceServiceRpc.createResourceRole(resourceRole);
      resourceRole.setId(newId);
      if (cachedResourceRoles != null) {
        cachedResourceRoles.put(resourceRole.getId(), resourceRole);
      }
    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(
                  null, null, "Failed add a resource role with id:" + resourceRole.getId(), e)
              .toString());
      throw new ResourcePersistenceException("Failed to add the ResourceRole instance.", e);
    }
  }

  /**
   * Removes a resource role from the persistence store. If no resource role exists with the given
   * id, nothing is done.
   *
   * @param resourceRole The notification type to delete.
   * @throws IllegalArgumentException If notificationType is <code>null</code> or its id is
   *     UNSET_ID.
   * @throws ResourcePersistenceException If there is an error updating the persistence
   */
  public void deleteResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {
    Util.checkResourceRole(resourceRole, true, false);
    LOGGER.debug("Delete ResourceRole with id:" + resourceRole.getId());
    try {
      resourceServiceRpc.deleteResourceRole(resourceRole.getId());
      if (cachedResourceRoles != null) {
        cachedResourceRoles.remove(resourceRole.getId());
      }

    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(
                  null, null, "Failed Delete a resource role with id:" + resourceRole.getId(), e)
              .toString());
      throw new ResourcePersistenceException("Failed to delete the ResourceRole instance.", e);
    }
  }

  /**
   * Updates the resource role in the persistence store. The resource role (by id) must exist in the
   * persistence store.
   *
   * @param resourceRole The resource role to update
   * @throws ResourcePersistenceException
   * @throws IllegalArgumentException If resourceRole is <code>null</code> or its id is UNSET_ID or
   *     its name/description is <code>null</code> or its modification user/date is <code>null
   *     </code>
   * @throws ResourcePersistenceException if there is an error updating the persistence
   */
  public void updateResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {
    Util.checkResourceRole(resourceRole, false, false);
    LOGGER.debug("update ResourceRole with id:" + resourceRole.getId());
    try {
      resourceServiceRpc.updateResourceRole(resourceRole);
      if (cachedResourceRoles != null) {
        cachedResourceRoles.put(resourceRole.getId(), resourceRole);
      }

    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(
                  null, null, "Failed update a resource role with id:" + resourceRole.getId(), e)
              .toString());
      throw new ResourcePersistenceException("Failed to update the ResourceRole instance.", e);
    }
  }

  /**
   * Loads the resource role from the persistence with the given id. Returns <code>null</code> if
   * there is no resource role with the given id.
   *
   * @return The loaded resource role
   * @param resourceRoleId The id of the resource role to load
   * @throws IllegalArgumentException If resourceRoleId is <= 0
   * @throws ResourcePersistenceException If there is an error loading the resource role
   */
  public ResourceRole loadResourceRole(long resourceRoleId) throws ResourcePersistenceException {
    Util.checkPositiveValue(resourceRoleId, "resourceRoleId");

    LOGGER.debug("load ResourceRole with id:" + resourceRoleId);

    if (cachedResourceRoles == null) {
      cacheAllResourceRoles();
    }
    return cachedResourceRoles.get(resourceRoleId);
  }

  /**
   * Construct the ResourceRole instance from the given ResultSet instance.
   *
   * @param rs the ResultSet instance
   * @return the ResourceRole instance
   */
  private ResourceRole constructResourceRole(ResourceRoleProto r) {
    ResourceRole role = new ResourceRole();

    role.setId(r.getResourceRoleId());

    if (!r.hasPhaseTypeId()) {
      role.setPhaseType(null);
    } else {
      role.setPhaseType(r.getPhaseTypeId());
    }
    role.setName(r.getName());
    role.setDescription(r.getDescription());
    role.setCreationUser(r.getCreateUser());
    role.setCreationTimestamp(new Date(r.getCreateDate().getSeconds() * 1000));
    role.setModificationUser(r.getModifyUser());
    role.setModificationTimestamp(new Date(r.getModifyDate().getSeconds() * 1000));

    return role;
  }

  /**
   * Loads all ResourceRoles from the persistence and caches them.
   *
   * @throws ResourcePersistenceException If there is an error loading from persistence
   */
  private void cacheAllResourceRoles() throws ResourcePersistenceException {
    LOGGER.debug("Load and cache all resource roles.");
    try {
      List<ResourceRoleProto> result = resourceServiceRpc.getResourceRoles();
      Map<Long, ResourceRole> resourceRoles = Collections.synchronizedMap(new HashMap<>());
      for (ResourceRoleProto r : result) {
        ResourceRole role = constructResourceRole(r);
        resourceRoles.put(role.getId(), role);
      }
      cachedResourceRoles = resourceRoles;
    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(null, null, "Failed for getting all ResourceRoles.", e).toString());
      throw new ResourcePersistenceException("Failed to load ResourceRole instances.", e);
    }
  }

  /**
   * Loads the resources from the persistence with the given ids. May return a 0-length array.
   *
   * @param resourceIds The ids of resources to load
   * @return The loaded resources
   * @throws IllegalArgumentException If any id is <= 0 or the array is <code>null</code>
   * @throws ResourcePersistenceException If there is an error loading the Resources
   */
  public Resource[] loadResources(Long[] resourceIds) throws ResourcePersistenceException {
    Util.checkLongArray(resourceIds, "resourceIds");
    if (resourceIds.length == 0) {
      return new Resource[0];
    }
    String idString = getIdString(resourceIds);
    LOGGER.debug("load Resource with ids:" + idString);
    try {
      List<ResourceProto> result = resourceServiceRpc.getAllResources(resourceIds);
      List<Resource> list = new ArrayList<>();
      for (ResourceProto rs : result) {
        list.add(constructResource(rs));
      }
      // load submissions for all resources
      loadResourceSubmissions(list);

      Resource[] resources = list.toArray(new Resource[list.size()]);

      // select all the external properties once and  add the matching properties into resource
      // instances.
      Map<Long, Map<String, String>> propertiesMap = getAllExternalProperties(resourceIds);
      for (int i = 0; i < resources.length; i++) {
        Resource resource = resources[i];
        Map<String, String> properties = propertiesMap.get(resource.getId());

        if (properties == null) {
          continue;
        }
        for (Iterator<Entry<String, String>> iter = properties.entrySet().iterator(); iter.hasNext(); ) {
          Entry<String, String> entry = iter.next();

          resource.setProperty(entry.getKey().toString(), entry.getValue().toString());
        }
      }
      return resources;
    } catch (SQLException e) {
      LOGGER.error(
          new LogMessage(null, null, "Failed to load Resources with ids:" + idString, e)
              .toString());
      throw new ResourcePersistenceException("Failed to load all the resources.", e);
    }
  }

  /**
   * <p>
   * Get resources by given project ids
   * </p>
   *
   * @return The resources array
   *
   * @param projectIds The project ids
   * @param roles The resource prole map
   *
   * @throws ResourcePersistenceException If there is an error reading the persistence store
   */
  public Resource[] getResourcesByProjects(Long[] projectIds, long userId, Map<Long, ResourceRole> roles) throws ResourcePersistenceException {
    LOGGER.debug("Getting resources by project Ids");
    List<Resource> resourceList= new ArrayList<>();
    List<ResourceProto> result = resourceServiceRpc.getResourcesByProjects(projectIds, userId);
    for (ResourceProto r: result) {
      long resourceId = r.getResourceId();
      Long projectId = r.hasProjectId() ? r.getProjectId() : null;
      Long phaseId = r.hasProjectPhaseId() ? r.getProjectPhaseId() : null;
      long resourceRoleId = r.getResourceRoleId();
      Resource resource = new Resource(resourceId, roles.get(resourceRoleId));
      resource.setProject(projectId);
      resource.setPhase(phaseId);
      resourceList.add(resource);
    }
    return resourceList.toArray(new Resource[resourceList.size()]);
  }

  /**
   * Return the id string separated by comma for the given long id array.
   *
   * @param ids the id array
   * @return string separated by comma
   */
  private String getIdString(Object[] ids) {
    if (ids == null || ids.length == 0) {
      return "";
    }
    StringBuilder idString = new StringBuilder();
    for (int i = 0; i < ids.length; i++) {
      idString.append(',').append(ids.toString());
    }
    return idString.substring(1);
  }

  /**
   * Loads the resources from the result of the SELECT operation. May return an empty array.
   *
   * @return The loaded resources
   * @param resultSet The result of the SELECT operation.
   * @throws IllegalArgumentException If any id is <= 0 or the array is null
   * @throws ResourcePersistenceException If there is an error loading the Resources
   */
  public Resource[] loadResources(List<ResourceProto> resultSet)
      throws ResourcePersistenceException {
    Util.checkNull(resultSet, "resultSet");
    if (resultSet.isEmpty()) {
      return new Resource[0];
    }
    try {
      List<Resource> list = new ArrayList<>();

      for (ResourceProto rs : resultSet) {
        list.add(constructResource(rs));
      }

      // load submissions for all resources
      loadResourceSubmissions(list);

      Resource[] resources = list.toArray(new Resource[list.size()]);
      Long[] resourceIds = new Long[resources.length];

      for (int i = 0; i < resources.length; ++i) {
        resourceIds[i] = resources[i].getId();
      }

      // select all the external properties once and add the matching properties into resource
      // instances
      Map<Long, Map<String, String>> propertiesMap = getAllExternalProperties(resourceIds);

      for (int i = 0; i < resources.length; ++i) {
        Resource resource = resources[i];
        Map<String, String> properties = propertiesMap.get(resource.getId());

        if (properties == null) {
          continue;
        }

        for (Iterator<Entry<String, String>> iter = properties.entrySet().iterator(); iter.hasNext(); ) {
          Entry<String, String> entry = iter.next();

          resource.setProperty(entry.getKey().toString(), entry.getValue().toString());
        }
      }

      return resources;
    } catch (SQLException e) {
      throw new ResourcePersistenceException(
          "Failed to load the submissions for the resources.", e);
    } catch (ResourcePersistenceException rpe) {
      throw rpe;
    }
  }

  /**
   * Gets all the external properties with one select sql query. Here a <code>HashMap</code>
   * structure is used. The key is an <code>Integer</code> of resourceId and the value is another
   * map which contains the key/value of external properties.
   *
   * @param resourceIds the resourceIds for retrieving external properties
   * @return a <code>Map</code> contained all external properties.
   * @throws ResourcePersistenceException if failed to select all external properties at once.
   */
  private Map<Long, Map<String, String>> getAllExternalProperties(Long[] resourceIds) throws ResourcePersistenceException {
    // Map from resource id to a Map containing the properties of the resource.
    Map<Long, Map<String, String>> resourcesProperties = new HashMap<Long, Map<String, String>>();
    try {
      List<ResourceInfoProto> result = resourceServiceRpc.getAllResourceInfo(resourceIds);
      for (ResourceInfoProto r : result) {
        Long resourceId = r.getResourceId();

        String key = r.getTypeName();
        String value = r.getValue();

        Map<String, String> properties = resourcesProperties.get(resourceId);
        if (properties == null) {
          properties = new HashMap<String, String>();
          resourcesProperties.put(resourceId, properties);
        }
        properties.put(key, value);
      }
      return resourcesProperties;
    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(
                  null, null, "Failed for getting all external properties for all resourceIds.", e)
              .toString());
      throw new ResourcePersistenceException(
          "Failed for getting all external properties for all resourceIds.", e);
    }
  }

  /**
   * Loads the notification types from the persistence with the given ids. May return a 0-length
   * array.
   *
   * @param notificationTypeIds The ids of notification types to load
   * @return The loaded notification types
   * @throws ResourcePersistenceException
   * @throws IllegalArgumentException If any id is <= 0 or the array is <code>null</code>
   * @throws ResourcePersistenceException If there is an error loading from persistence
   */
  public NotificationType[] loadNotificationTypes(Long[] notificationTypeIds)
      throws ResourcePersistenceException {
    Util.checkLongArray(notificationTypeIds, "notificationTypeIds");
    if (notificationTypeIds.length == 0) {
      return new NotificationType[0];
    }
    String idString = this.getIdString(notificationTypeIds);
    LOGGER.debug("load NotificationType with ids:" + idString);
    try {
      List<NotificationTypeProto> result = resourceServiceRpc.getAllNotificationType(notificationTypeIds);
      List<NotificationType> list = new ArrayList<>();

      for (NotificationTypeProto n : result) {
        list.add(constructNotificationType(n));
      }

      return list.toArray(new NotificationType[list.size()]);

    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(
                  null, null, "Failed for getting all NotificationType with ids:" + idString, e)
              .toString());
      throw new ResourcePersistenceException("Failed to load NotificationTypes instances.", e);
    }
  }

  /**
   * Loads the notification types from the result of the SELECT operation. This method may return a
   * zero-length array. It is designed to keep the amount of SQL queries to a minimum when searching
   * notification types.
   *
   * @param resultSet The result of the SELECT operation. This result should have the following
   *     columns:
   *     <ul>
   *       <li>notification_type_id
   *       <li>name
   *       <li>description
   *       <li>as create_user
   *       <li>as create_date
   *       <li>as modify_user
   *       <li>as modify_date
   *     </ul>
   *
   * @return The loaded notification types
   */
  public NotificationType[] loadNotificationTypes(List<NotificationTypeProto> resultSet) {
    Util.checkNull(resultSet, "resultSet");
    if (resultSet.isEmpty()) {
      return new NotificationType[0];
    }
    List<NotificationType> list = new ArrayList<>();
    for (NotificationTypeProto rs : resultSet) {
      list.add(constructNotificationType(rs));
    }
    return list.toArray(new NotificationType[list.size()]);
  }

  /**
   * Constructs the <code>NotificationType</code> instance from the given <code>CustomResultSet
   * </code> instance.
   *
   * @param rs the <code>CustomResultSet</code> instance
   * @return NotificationType instance.
   */
  private NotificationType constructNotificationType(NotificationTypeProto n) {
    NotificationType type = new NotificationType();
    type.setId(n.getNotificationTypeId());
    type.setName(n.getName());
    type.setDescription(n.getDescription());
    type.setCreationUser(n.getCreateUser());
    type.setCreationTimestamp(new Date(n.getCreateDate().getSeconds() * 1000));
    type.setModificationUser(n.getModifyUser());
    type.setModificationTimestamp(new Date(n.getModifyDate().getSeconds() * 1000));
    return type;
  }

  /**
   * Loads the resource roles from the persistence with the given ids. May return a 0-length array.
   *
   * @param resourceRoleIds The ids of resource roles to load
   * @return The loaded resource roles
   * @throws IllegalArgumentException If any id is <= 0 or the array is <code>null</code>
   * @throws ResourcePersistenceException If there is an error loading from persistence
   */
  public ResourceRole[] loadResourceRoles(Long[] resourceRoleIds)
      throws ResourcePersistenceException {
    Util.checkLongArray(resourceRoleIds, "resourceRoleIds");

    if (resourceRoleIds.length == 0) {
      return new ResourceRole[0];
    }

    String idString = getIdString(resourceRoleIds);
    LOGGER.debug("load Resource role with ids:" + idString);

    if (cachedResourceRoles == null) {
      cacheAllResourceRoles();
    }

    List<ResourceRole> roles = new ArrayList<ResourceRole>();
    for (long roleId : resourceRoleIds) {
      ResourceRole role = cachedResourceRoles.get(roleId);
      if (role != null) {
        roles.add(role);
      }
    }

    return roles.toArray(new ResourceRole[roles.size()]);
  }

  /**
   * Loads the resource roles from the result of the SELECT operation. May return an empty array.
   *
   * @return The loaded resource roles
   * @param resultSet The result of the SELECT operation.
   * @throws IllegalArgumentException If any id is <= 0 or the array is null
   * @throws ResourcePersistenceException If there is an error loading from persistence
   */
  public ResourceRole[] loadResourceRoles(List<ResourceRoleProto> resultSet) {
    Util.checkNull(resultSet, "resultSet");
    if (resultSet.isEmpty()) {
      return new ResourceRole[0];
    }
    List<ResourceRole> roles = new ArrayList<>();
    for (ResourceRoleProto rs : resultSet) {
      roles.add(constructResourceRole(rs));
    }
    return roles.toArray(new ResourceRole[roles.size()]);
  }

  /**
   * Loads the Notifications for the given "id" triples from the persistence store. May return a
   * 0-length array.
   *
   * @param userIds The ids of the users
   * @param projectIds The ids of the projects
   * @param notificationTypes The ids of the notification types
   * @return The loaded notifications
   * @throws IllegalArgumentException If the three arrays don't all have the same number of elements
   *     (or any array is <code>null</code>) or all three arrays do not have the same length, any id
   *     is <= 0
   * @throws ResourcePersistenceException If there is an error loading from the persistence
   */
  public Notification[] loadNotifications(
          Long[] userIds, Long[] projectIds, Long[] notificationTypes)
      throws ResourcePersistenceException {
    Util.checkLongArray(userIds, "userIds");
    Util.checkLongArray(projectIds, "projectIds");
    Util.checkLongArray(notificationTypes, "notificationTypes");

    if (userIds.length != projectIds.length || projectIds.length != notificationTypes.length) {
      throw new IllegalArgumentException("All three arrays do not have the same length.");
    }

    if (userIds.length == 0) {
      return new Notification[0];
    }

    LOGGER.debug("load Notifications with array of userIds/projectIds/notificationTypes.");

    try {
      List<NotificationProto> result = resourceServiceRpc.getAllNotifications(userIds, projectIds, notificationTypes);
      List<Notification> list = new ArrayList<>();

      for (NotificationProto n : result) {
        list.add(constructNotification(n));
      }

      return list.toArray(new Notification[list.size()]);

    } catch (Exception e) {
      LOGGER.error(
          new LogMessage(
                  null,
                  null,
                  "Failed to load Notifications with array of userIds/projectIds/notificationTypes.",
                  e)
              .toString());
      throw new ResourcePersistenceException("Failed to load Notification instances.", e);
    }
  }

  /**
   * Load Notifications from the result of the SELECT operation. May return an empty array.
   *
   * @return The loaded notifications
   * @param resultSet The result of the SELECT operation.
   * @throws IllegalArgumentException If the three arrays don't all have the same number of elements
   *     (or any array is null) or all three arrays do not have the same length, any id is <= 0
   * @throws ResourcePersistenceException If there is an error loading from the persistence
   */
  public Notification[] loadNotifications(List<NotificationProto> resultSet)
      throws ResourcePersistenceException {
    Util.checkNull(resultSet, "resultSet");
    if (resultSet.isEmpty()) {
      return new Notification[0];
    }
    List<Notification> notifications = new ArrayList<>();
    for (NotificationProto rs : resultSet) {
      notifications.add(constructNotification(rs));
    }
    return notifications.toArray(new Notification[notifications.size()]);
  }

  /**
   * This method retrieves the resource without submissions and properties a given resource id.
   *
   * @param resourceId the resource id being queried
   * @return the resource role id if it exists or null otherwise
   * @throws ResourcePersistenceException if an error occurs in the underlying layer.
   */
  private Resource getShallowResource(long resourceId) throws ResourcePersistenceException {
    try {
      ResourceProto result = resourceServiceRpc.getResource(resourceId);
      if (result != null) {
        return constructResource(result);
      }
      return null;
    } catch (Exception e) {
      throw new ResourcePersistenceException("Failed to get resource with id " + resourceId, e);
    }
  }

  /**
   * This method will audit project user information. This information is generated when a resource
   * is added, deleted or changes its user or role.
   *
   * @param resource the resource being audited
   * @param auditType the audit type. Can be PROJECT_USER_AUDIT_CREATE_TYPE or
   *     PROJECT_USER_AUDIT_DELETE_TYPE.
   * @param userId the resource user id. This value overrides the value inside resource if present.
   * @param resourceRoleId the resource role id. This value overrides the value inside resource if
   *     present.
   * @throws ResourcePersistenceException if validation error occurs or any error occurs in the
   *     underlying layer
   * @since 1.2.2
   */
  private void auditProjectUser(Resource resource, int auditType, Long userId, Long resourceRoleId)
      throws ResourcePersistenceException {
    // decide which user id to use
    if (userId == null && resource.getUserId() == null) {
      throw new ResourcePersistenceException(
          "Audit information was not successfully saved " + "since resource doesn't have user id");
    }
    if (resource.getProject() == null) {
      throw new ResourcePersistenceException(
          "Audit information was not successfully saved "
              + "since resource doesn't have project id.");
    }
    long resourceUserId = (userId != null) ? userId : resource.getUserId();
    resourceServiceRpc.auditProjectUser(resource.getProject(), resourceUserId,
        Optional.ofNullable(resourceRoleId).orElse(resource.getResourceRole().getId()), auditType,
        resource.getModificationTimestamp(), Long.parseLong(resource.getModificationUser()));
  }
}
