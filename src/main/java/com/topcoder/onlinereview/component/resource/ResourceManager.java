/*
 * Copyright (C) 2006-2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.resource;

import com.topcoder.onlinereview.component.grpcclient.resource.ResourceServiceRpc;
import com.topcoder.onlinereview.component.search.SearchBuilderException;
import com.topcoder.onlinereview.component.search.filter.AndFilter;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.component.search.filter.GreaterThanOrEqualToFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The PersistenceResourceManager class implements the ResourceManager interface. It ties together a
 * persistence mechanism, several Search Builder searching instances (for searching for various
 * types of data), and several id generators (for generating ids for the various types).
 *
 * <p>This class consists of a several methods styles. The first method style just calls directly to
 * a corresponding method of the persistence. The second method style first assigns values to some
 * data fields of the object before calling a persistence method. The third type of method uses a
 * SearchBundle to execute a search and then uses the persistence to load an object for each of the
 * ids found from the search.
 *
 * <p>All the update and remove methods in this class is that the modification/creation user/date
 * are set on the various objects and then pass to the persistence.
 *
 * <p>
 *
 * <pre>
 * Sample usage of this component:
 *
 * create Resource Or ResourceRole to Persistence:
 *
 *      // Note that this will assign an id to the resource and
 *      // resource role
 *      manager.updateResourceRole(resourceRole, "Operator #1");
 *      manager.updateResource(resource, "Operator #1");
 *      // The updating of notification types is entirely similar
 *      // to the calls above
 *      // The data can then be changed and the changes
 *      //  persisted
 *      resourceRole.setName("Changed name");
 *      manager.updateResourceRole(resourceRole, "Operator #1");
 *
 * search resources with a filter:
 *
 *      // Search for the Resources
 *      manager.searchResources(fullFilter);
 *
 *      // ResourceRoles, NotificationTypes, and Notifications can be
 *      // searched similarly by using the other FilterBuilder classes
 *      // and the corresponding ResourceManager methods.  They can
 *      // also be retrieved through the getAll methods
 *      manager.getAllResourceRoles();
 *
 *      manager.getAllNotificationTypes();
 *
 * You can refer to the demo or test cases for more details.
 * </pre>
 *
 * <p>This class is immutable and hence thread-safe.
 *
 * @author aubergineanode
 * @author kinfkong
 * @author George1
 * @version 1.1.2
 */
@Component
public class ResourceManager {

  @Autowired
  private ResourceServiceRpc resourceServiceRpc;

  /**
   * Represents the persistence store for Resources, ResourceRoles, Notifications, and
   * NotificationTypes.
   *
   * <p>This field is set in the constructor, and is immutable, and can never be null. This field is
   * used in almost all the methods of this class, as it is used either to make changes to the items
   * in the persistence, or to retrieve the items in the persistence once their ids have been found
   * with the Search Builder component.
   */
  @Autowired private ResourcePersistence persistence;


  /**
   * Updates the given resource in the persistence store.
   *
   * <p>If the resource is new (id is UNSET_ID), then an id should be assigned and the resource
   * added to the persistence store. Otherwise the resource data in the persistence store would be
   * updated.
   *
   * @param resource the resource to update
   * @param operator the operator making the update
   * @throws IllegalArgumentException if a required field of the resource is not set (if
   *     resource.getResourceRole() is null), or if the resource role is associated with a phase
   *     type and the resource is not associated with a phase, or if resource or operator is null
   * @throws ResourcePersistenceException if there is an error updating the resource
   */
  public void updateResource(Resource resource, String operator)
      throws ResourcePersistenceException {
    Helper.checkNull(resource, "resource");
    Helper.checkNull(operator, "operator");
    validateResource(resource, "resource");

    // if the id is unset
    if (resource.getId() == Resource.UNSET_ID) {

      // set the information
      setAudit(resource, operator, true);

      // create the new record using persistence
      persistence.addResource(resource);

    } else {

      // set the information
      setAudit(resource, operator, false);

      // update it via persistence
      persistence.updateResource(resource);
    }
  }

  /**
   * A helper method to set the create user, modification user and create date, and modification
   * date
   *
   * <p>of a AuditableResourceStructure (such as Resource, ResourceRole, Notification,
   * NotificationType, etc).
   *
   * @param audit the AuditableResourceStructure to set
   * @param operator the operator of this processing
   * @param create a flag tells whether it is a new auditable resource structure
   */
  private void setAudit(AuditableResourceStructure audit, String operator, boolean create) {

    // gets the current date
    Date current = new Date();

    // if the audit is new (id not set)
    if (create) {

      // set the creation user and modification user
      audit.setCreationUser(operator);
      audit.setModificationUser(operator);

      // set the creation time and modification time
      audit.setModificationTimestamp(current);
      audit.setCreationTimestamp(current);

    } else {
      // set the modification user
      audit.setModificationUser(operator);

      // set the modification date
      audit.setModificationTimestamp(current);
    }
  }

  /**
   * Removes the given resource in the persistence store (by id). If the id does not exist in the
   * persistence, nothing would be removed.
   *
   * @param resource the resource to remove
   * @param operator the operator making the update
   * @throws IllegalArgumentException if the id of the resource is UNSET_ID, or the resource or
   *     operator is null
   * @throws ResourcePersistenceException if there is an error updating the persistence store
   */
  public void removeResource(Resource resource, String operator)
      throws ResourcePersistenceException {
    Helper.checkNull(resource, "resource");
    Helper.checkNull(operator, "operator");

    // if the id is unset, throws IllegalArgumentException
    if (resource.getId() == Resource.UNSET_ID) {
      throw new IllegalArgumentException("The id of the resource must be set.");
    }

    // set the modification user and time
    setAudit(resource, operator, false);

    // use the persistence to remove the resource
    persistence.deleteResource(resource);
  }

  /**
   * Updates all resources for the given project.
   *
   * <p>Any resources in the array with UNSET_ID are assigned an id and updated in the persistence.
   * Any resources with an id already assigned are updated in the persistence. Any resources
   * associated with the project in the persistence store, but not appearing in the array are
   * removed.
   *
   * @param resources the resources associated with the project
   * @param project the project to update resources for
   * @param operator the operator making the update
   * @throws IllegalArgumentException would be throw for the following cases: 1. if resources is
   *     null or has null entries 2. if project is &lt;= 0 3. if operator is null 4. if a required
   *     field of the resource is not set (if resource.getResourceRole() is null), or if the
   *     resource role is associated with a phase type and the resource role is not associated with
   *     a phase 5. If resources in the array have a project that is not the same as the project
   *     argument
   * @throws ResourcePersistenceException if there is an error updating the persistence store.
   */
  public void updateResources(Resource[] resources, long project, String operator)
      throws ResourcePersistenceException {

    Helper.checkNull(resources, "resources");
    Helper.checkNull(operator, "operator");

    Helper.checkLongPositive(project, "project");

    for (int i = 0; i < resources.length; i++) {
      Helper.checkNull(resources[i], "element in location of resources " + i);
      validateResource(resources[i], "element in location of resources " + i);

      // check if the project in the resource is the same as the project id
      // according to the forum:
      // https://software.topcoder.com/forum/c_forum_message.jsp?f=22404318&r=22884053
      // if the project is not set, IllegalArgumentException should be thrown
      if (resources[i].getProject() == null || resources[i].getProject() != project) {
        throw new IllegalArgumentException(
            "The resource in location "
                + i
                + " contains a project id which is not the same "
                + "as the project argument or the project is null.");
      }
    }

    // a set of ids for checking the existence
    Set<Long> ids = new HashSet<>();

    // load the resources id to a set for existing searching later
    for (int i = 0; i < resources.length; i++) {
      ids.add(Long.valueOf(resources[i].getId()));
    }

    // find all the resources belonging to the project
    Filter filter = ResourceFilterBuilder.createProjectIdFilter(project);

    // use the searchResources to load the resources of the project
    Resource[] res = null;

    try {
      // search with the search bundle
      res = searchResources(filter);

    } catch (SearchBuilderException e) {
      // wrap the search builder exception to ResourcePersistenceException
      throw new ResourcePersistenceException(
          "Error occur while searching the resources of the project.", e);
    }

    // for each resource in the returned result, check its existence,
    // if not exists in the argument resources, simply remove it
    for (int i = 0; i < res.length; i++) {
      if (!ids.contains(res[i].getId())) {
        // if not exists, remove it
        removeResource(res[i], operator);
      }
    }

    // update all the resources in the argument that pass in
    for (int i = 0; i < resources.length; i++) {
      // update it
      updateResource(resources[i], operator);
    }
  }

  /**
   * Gets the resource with the given id from the persistence store. Returns null if there is no
   * resource with the given id.
   *
   * @param id the id of the resource to retrieve
   * @return The loaded Resource
   * @throws IllegalArgumentException if id is &lt;= 0
   * @throws ResourcePersistenceException if there is an error reading the persistence store
   */
  public Resource getResource(long id) throws ResourcePersistenceException {
    // although the persistence.loadResource would validates the id argument
    // but persistence does not belong to this component and still on developing,
    // so developer should not count on it and do the validation here.
    // so the helper check is not code redundancy.
    Helper.checkLongPositive(id, "id");
    return persistence.loadResource(id);
  }

  /**
   * Searches the resources in the persistence store using the given filter. The filter can be
   * formed using the field names and utility methods in ResourceFilterBuilder. The return will
   * always be a non-null (possibly 0 item) array.
   *
   * <p>In order to invoke this method correctly, one should properly set the resourceSearchBundle.
   *
   * <pre>
   * A sample of the context of the search bundle is:
   *                  SELECT resource.resource_id
   *                  FROM resource
   *                  LEFT OUTER JOIN resource_submission
   *                  ON resource.resource_id = resource_submission.resource_id
   *                  LEFT OUTER JOIN resource_info
   *                  ON resource.resource_id = resource_info.resource_id
   *                  LEFT OUTER JOIN resource_info_type_lu
   *                  ON resource_info.resource_info_type_id =
   *                  resource_info_type_lu.resource_info_type_id
   *                  WHERE
   * </pre>
   *
   * Note, make sure the selected column is only one column and of the type: long in the
   * configuration.
   *
   * @param filter the filter to use
   * @return The loaded resources
   * @throws IllegalArgumentException if filter is null
   * @throws ResourcePersistenceException if there is an error reading the persistence store
   * @throws SearchBuilderException if there is an error executing the filter
   */
  public Resource[] searchResources(Filter filter)
      throws ResourcePersistenceException, SearchBuilderException {

    Helper.checkNull(filter, "filter");

    return persistence.loadResources(resourceServiceRpc.searchResources(filter));
  }

  /**
   * Updates the given resource role in the persistence store. If the resource role is new (id is
   * UNSET_ID), then an id should be assigned and the resource role added to the persistence store.
   * Otherwise the resource role data in the persistence store should be updated.
   *
   * @param resourceRole the resource role to update
   * @param operator the operator making the update
   * @throws IllegalArgumentException if a required field of the resource role is missing (i.e. name
   *     or description of the resource role is null), or if resourceRole or operator is null
   * @throws ResourcePersistenceException If there is an error updating the persistence
   */
  public void updateResourceRole(ResourceRole resourceRole, String operator)
      throws ResourcePersistenceException {

    Helper.checkNull(resourceRole, "resourceRole");
    Helper.checkNull(operator, "operator");
    validateResourceRole(resourceRole, "resourceRole");

    // if the id is unset
    if (resourceRole.getId() == ResourceRole.UNSET_ID) {

      // set the information
      setAudit(resourceRole, operator, true);

      // create the new record using persistence
      persistence.addResourceRole(resourceRole);

    } else {
      // set the information
      setAudit(resourceRole, operator, false);

      // update it via persistence
      persistence.updateResourceRole(resourceRole);
    }
  }

  /**
   * Removes a resource role from the persistence (by id).
   *
   * @param resourceRole the resource role to remove
   * @param operator the operator making the update
   * @throws IllegalArgumentException if resourceRole or operator is null, or if the id of the
   *     resource role is UNSET_ID
   * @throws ResourcePersistenceException if there is an error updating the persistence store.
   */
  public void removeResourceRole(ResourceRole resourceRole, String operator)
      throws ResourcePersistenceException {

    Helper.checkNull(resourceRole, "resourceRole");
    Helper.checkNull(operator, "operator");

    // if the id is unset, throws IllegalArgumentException
    if (resourceRole.getId() == ResourceRole.UNSET_ID) {
      throw new IllegalArgumentException("The id of the resourceRole must be set.");
    }

    // set the modification user and time
    setAudit(resourceRole, operator, false);

    // use the persistence to remove the resource Role
    persistence.deleteResourceRole(resourceRole);
  }

  /**
   * Gets all resource roles in the persistence store.
   *
   * @return All resource roles in the persistence store
   * @throws ResourcePersistenceException if there is an error reading the persistence store.
   */
  public ResourceRole[] getAllResourceRoles() throws ResourcePersistenceException {
    // create the filter

    // Important Note:
    // Here we must use GreaterThanOrEqualToFilter(1), instead of GreaterThanFilter(0).
    // Because set have set the field using validator: LongValidator.isPositive().
    // So the parameter of value 0, passing in the filter may cause error
    Filter filter =
        new GreaterThanOrEqualToFilter(ResourceRoleFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, 1L);

    // call the searchResourceRoles method to get the result
    try {

      return searchResourceRoles(filter);

    } catch (SearchBuilderException e) {

      // wrap search builder exception to ResourcePersistenceException
      throw new ResourcePersistenceException("Error occurs while searching the roles.", e);
    }
  }

  /**
   * Searches the resource roles in the persistence store using the given filter. The filter can be
   * formed using the field names and utility methods in ResourceRoleFilterBuilder. The return will
   * always be a non-null (possibly 0 item) array.
   *
   * <p>In order to invoke this method correctly, one should properly set the
   * resourceRoleSearchBundle.
   *
   * <pre>
   * A sample of the context of the search bundle is:
   *                 SELECT resource_role_id FROM resource_role_lu WHERE
   * </pre>
   *
   * Note, make sure the selected column is only one column and of the type: long in the
   * configuration.
   *
   * @param filter the filter to use
   * @return The loaded resource roles
   * @throws IllegalArgumentException if filter is null
   * @throws ResourcePersistenceException if there is an error reading the persistence store
   * @throws SearchBuilderException if there is an error executing the filter.
   */
  public ResourceRole[] searchResourceRoles(Filter filter)
      throws ResourcePersistenceException, SearchBuilderException {

    Helper.checkNull(filter, "filter");

    // load the resource roles from the resultSet
    return persistence.loadResourceRoles(resourceServiceRpc.searchResourceRoles(filter));
  }

  /**
   * Adds a list of notifications for the given user ids to the persistence store. All of the
   * notification are added are for the given project and type.
   *
   * @param users the users to add notifications for
   * @param project the project the notifications apply to
   * @param notificationType the type of notifications to add
   * @param operator the operation making the update
   * @throws IllegalArgumentException if any item of users, project, or notificationType is &lt;= 0,
   *     or if operator or users is null
   * @throws ResourcePersistenceException if there is an error updating the persistence store
   */
  public void addNotifications(Long[] users, Long project, Long notificationType, String operator)
      throws ResourcePersistenceException {

    Helper.checkNull(users, "users");
    Helper.checkNull(operator, "operator");
    Helper.checkLongPositive(project, "project");
    Helper.checkLongPositive(notificationType, "notificationType");

    for (int i = 0; i < users.length; i++) {
      // check if all the user ids are positive
      Helper.checkLongPositive(users[i], "element of users in location " + i);
    }

    // iterator through to add notifications
    for (int i = 0; i < users.length; i++) {
      persistence.addNotification(users[i], project, notificationType, operator);
    }
  }

  /**
   * Removes a list of notifications for the given user ids from the persistence store. The
   * notifications removed are for the given project and type.
   *
   * @param users the users to remove notifications for
   * @param project the project the notifications apply to
   * @param notificationType the type of notifications to remove
   * @param operator the operation making the update
   * @throws IllegalArgumentException If any item of users, project, or notificationType is &lt;= 0,
   *     or if operator or users is null
   * @throws ResourcePersistenceException if there is an error updating the persistence store
   */
  public void removeNotifications(
      Long[] users, Long project, Long notificationType, String operator)
      throws ResourcePersistenceException {

    Helper.checkNull(users, "users");
    Helper.checkNull(operator, "operator");
    Helper.checkLongPositive(project, "project");
    Helper.checkLongPositive(notificationType, "notificationType");

    for (int i = 0; i < users.length; i++) {
      // check if all the user ids are positive
      Helper.checkLongPositive(users[i], "element of users in location " + i);
    }
    for (int i = 0; i < users.length; i++) {
      // iterator through to remove notifications
      persistence.removeNotification(users[i], project, notificationType, operator);
    }
  }

  /**
   * Gets the user id for all notifications for the given project and type. The return will always
   * be a non-null (possibly 0 item) array.
   *
   * @param project the project to get notifications for
   * @param notificationType the type of notifications to retrieve
   * @return the user ids of the notifications for the project and type
   * @throws IllegalArgumentException if project or notificationType is &lt;= 0
   * @throws ResourcePersistenceException if there is an error reading the persistence store
   */
  public Long[] getNotifications(Long project, Long notificationType)
      throws ResourcePersistenceException {

    Helper.checkLongPositive(project, "project");
    Helper.checkLongPositive(notificationType, "notificationType");

    // creates the filters
    Filter filter1 = NotificationFilterBuilder.createProjectIdFilter(project);

    Filter filter2 = NotificationFilterBuilder.createNotificationTypeIdFilter(notificationType);

    // generates the AND filter
    Filter andFilter = new AndFilter(filter1, filter2);

    // call the searchNotifications via the filters to get a list of notifications
    Notification[] notifications = null;

    try {

      notifications = searchNotifications(andFilter);

    } catch (SearchBuilderException e) {
      // wrap the search builder exception to ResourcePersistenceException
      throw new ResourcePersistenceException("Error occurs while searching the notifications.", e);
    }

    // extract the id from the returned array.
    Long[] ids = new Long[notifications.length];

    for (int i = 0; i < ids.length; i++) {
      // extract the user ids
      ids[i] = notifications[i].getExternalId();
    }

    return ids;
  }

  /**
   * searchNotifications: Searches the notifications in the persistence store using the given
   * filter. The filter can be formed using the field names and utility methods in
   * NotificationFilterBuilder. The return will always be a non-null (possibly 0 item) array.
   *
   * <p>Note, the configuration of the NotificaionSearchBundle should with the columns: user id,
   * project id, notification type id (in this order). A sample configuration of the search bundle's
   * context is:
   *
   * <pre>
   * SELECT external_ref_id, project_id, notification_type_id FROM notification WHERE
   * </pre>
   *
   * @param filter the filter to use
   * @return The loaded notifications
   * @throws IllegalArgumentException if filter is null
   * @throws ResourcePersistenceException if there is an error reading the persistence store
   * @throws SearchBuilderException if there is an error executing the filter.
   */
  public Notification[] searchNotifications(Filter filter)
      throws ResourcePersistenceException, SearchBuilderException {

    Helper.checkNull(filter, "filter");

    return persistence.loadNotifications(resourceServiceRpc.searchNotifications(filter));
  }

  /**
   * Updates the given notification type in the persistence store. If the notification type is new
   * (id is UNSET_ID), then an id should be assigned and the notification type added to the
   * persistence store. Otherwise the notification type data in the persistence store should be
   * updated.
   *
   * @param notificationType the notification type to update
   * @param operator the operator making the update
   * @throws IllegalArgumentException If a required field of the notification type is missing (i.e.
   *     name or description of the notification type is null), or if notificationType or operator
   *     is null
   * @throws ResourcePersistenceException if there is an error updating the persistence
   */
  public void updateNotificationType(NotificationType notificationType, String operator)
      throws ResourcePersistenceException {

    Helper.checkNull(notificationType, "notificationType");
    Helper.checkNull(operator, "operator");
    validateNotificationType(notificationType, "notificationType");

    // if the id is unset
    if (notificationType.getId() == NotificationType.UNSET_ID) {

      // set the information
      setAudit(notificationType, operator, true);

      // create the new record using persistence
      persistence.addNotificationType(notificationType);

    } else {
      // set the information
      setAudit(notificationType, operator, false);

      // update it via persistence
      persistence.updateNotificationType(notificationType);
    }
  }

  /**
   * Removes a notification type from the persistence (by id).
   *
   * @param notificationType the notification type to remove
   * @param operator the operator making the update
   * @throws IllegalArgumentException if notificationType or operator is null, or if the id of the
   *     notification type is UNSET_ID
   * @throws ResourcePersistenceException If there is an error updating the persistence store.
   */
  public void removeNotificationType(NotificationType notificationType, String operator)
      throws ResourcePersistenceException {

    Helper.checkNull(notificationType, "resourceRole");
    Helper.checkNull(operator, "operator");

    // if the id is unset, throws IllegalArgumentException
    if (notificationType.getId() == ResourceRole.UNSET_ID) {
      throw new IllegalArgumentException("The id of the notificationType must be set.");
    }

    // set the modification user and time
    setAudit(notificationType, operator, false);

    // use the persistence to remove the resource Role
    persistence.deleteNotificationType(notificationType);
  }

  /**
   * Searches the notification types in the persistence store using the given filter. The filter can
   * be formed using the field names and utility methods in NotificationTypeFilterBuilder. The
   * return will always be a non-null (possibly 0 item) array.
   *
   * <p>In order to invoke this method correctly, one should properly set the
   * notificationTypeSearchBundle.
   *
   * <pre>
   * A sample of the context of the search bundle is:
   *                 SELECT notification_type_id FROM notification_type_lu WHERE
   * </pre>
   *
   * Note, make sure the selected column is only one column and of the type: long in the
   * configuration.
   *
   * @param filter the filter to use
   * @return The loaded notification types
   * @throws IllegalArgumentException if filter is null
   * @throws ResourcePersistenceException if there is an error reading the persistence store
   * @throws SearchBuilderException if there is an error executing the filter.
   */
  public NotificationType[] searchNotificationTypes(Filter filter)
      throws ResourcePersistenceException, SearchBuilderException {

    Helper.checkNull(filter, "filter");

    return persistence.loadNotificationTypes(resourceServiceRpc.searchNotificationTypes(filter));
  }

  /**
   * Gets all notification types in the persistence store.
   *
   * @return All notification types in the persistence store
   * @throws ResourcePersistenceException if there is an error reading the persistence store.
   */
  public NotificationType[] getAllNotificationTypes() throws ResourcePersistenceException {
    // create a GreaterThanFilter
    Filter filter =
        new GreaterThanOrEqualToFilter(
            NotificationTypeFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, 1L);

    NotificationType[] notificationTypes = null;
    try {
      // use the search bundle and filter to get the result
      notificationTypes = searchNotificationTypes(filter);
    } catch (SearchBuilderException e) {
      // wrap it ResourcePersistenceException
      throw new ResourcePersistenceException(
          "Error occurs while searching the notification types.", e);
    }
    return notificationTypes;
  }

  /**
   * <p>
   * Get resources by given project ids
   * </p>
   *
   * @return The resources array
   *
   * @param projectIds The project ids
   *
   * @throws ResourcePersistenceException If there is an error reading the persistence store
   */
  public Resource[] getResourcesByProjects(Long[] projectIds, long userId) throws ResourcePersistenceException {

    Helper.checkNull(projectIds, "projectIds");

    // Get the list of existing resource roles and build a cache
    ResourceRole[] resourceRoles = getAllResourceRoles();
    Map<Long, ResourceRole> cachedRoles = new HashMap<Long, ResourceRole>();
    for (ResourceRole role : resourceRoles) {
      cachedRoles.put(role.getId(), role);
    }

    return persistence.getResourcesByProjects(projectIds, userId, cachedRoles);
  }

  /**
   * Validates a resource, IllegalArgumentException would be thrown if it is invalid.
   *
   * @param resource the resource to check
   * @param argName the variable name of the resource
   * @throws IllegalArgumentException if a required field of the resource is not set (if
   *     resource.getResourceRole() is null), or if the resource role is associated with a phase
   *     type and the resource is not associated with a phase, or if resource is null
   */
  private void validateResource(Resource resource, String argName) {
    // check null
    Helper.checkNull(resource, argName);

    if (resource.getResourceRole() == null) {
      throw new IllegalArgumentException("The resource role must be set.");
    }

    if (resource.getUserId() == null) {
      throw new IllegalArgumentException("The user ID must be set.");
    }

    // if the resource role is associated with a phase type but the resource role is not associated
    // with a phase
    if (resource.getResourceRole().getPhaseType() != null && resource.getPhase() == null) {
      throw new IllegalArgumentException(
          "The phase in the resource and the phase type in the resource role"
              + "are not consistent.");
    }
  }

  /**
   * Validates a instance of ResourceRoles is valid.
   *
   * @param resourceRole the resourceRole to validate
   * @param name the name of the argument
   * @throws IllegalArgumentException If a required field of the resource role is missing (i.e. name
   *     or description of the resource role is null)
   */
  private void validateResourceRole(ResourceRole resourceRole, String name) {
    Helper.checkNull(resourceRole.getName(), "name of " + name);
    Helper.checkNull(resourceRole.getDescription(), "description of " + name);
  }

  /**
   * Validates a instance of NotificationType is valid.
   *
   * @param notificationType the NotificationType to validate
   * @param name the name of the argument
   * @throws IllegalArgumentException If a required field of the notificationType is missing (i.e.
   *     name or description of the notificationType is null)
   */
  private void validateNotificationType(NotificationType notificationType, String name) {
    Helper.checkNull(notificationType.getName(), "name of " + name);
    Helper.checkNull(notificationType.getDescription(), "description of " + name);
  }
}
