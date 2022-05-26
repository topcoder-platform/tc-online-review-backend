/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.resource;

import com.topcoder.onlinereview.component.search.filter.EqualToFilter;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.component.search.filter.InFilter;
import com.topcoder.onlinereview.component.search.filter.NullFilter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The ResourceFilterBuilder class supports building filters for searching for Resources.
 *
 * <p>This class consists of 2 parts. The first part consists of static Strings that name the fields
 * that are available for searching. All ResourceManager implementations should use SearchBundles
 * that are configured to use these names. The second part of this class consists of convenience
 * methods to create common filters based on these field names.
 *
 * <p>This class has only final static fields/methods, so mutability is not an issue. This class is
 * thread-safe.
 *
 * <p>Changes in version 1.1: Added a new filter factory method createAnySubmissionIdFilter(long[])
 * based on the fact that resource has been changed to be associated with one or many submission
 * ids. And it also updated those deprecated methods in Search Builder component of latest version.
 *
 * @author aubergineanode, kinfkong, Rica, Xuchen
 * @version 1.1
 * @since 1.0
 */
public class ResourceFilterBuilder {

  /**
   * The name of the field under which SearchBuilder Filters can be built in order to filter on the
   * id of the Resource.
   *
   * <p>Note, according to the forum:
   *
   * <p>The name is changed to : resource.resource_id
   *
   * <p>This field is final, static, and public, and is used in the createResourceIdFilter method.
   */
  public static final String RESOURCE_ID_FIELD_NAME = "resource.resource_id";

  /**
   * The name of the field under which SearchBuilder Filters can be built in order to filter on the
   * id of the project the Resource belongs to.
   *
   * <p>This field is final, static, and public, and is used in the createProjectIdFilter and
   * createNullProjectFilter method.
   */
  public static final String PROJECT_ID_FIELD_NAME = "project_id";

  /**
   * The name of the field under which SearchBuilder Filters can be built in order to filter on the
   * id of the phase the Resource belongs to.
   *
   * <p>This field is final, static, and public, and is used in the createPhaseIdFilter method.
   */
  public static final String PHASE_ID_FIELD_NAME = "project_phase_id";

  /**
   * The name of the field under which SearchBuilder Filters can be built in order to filter on the
   * id of the user the Resource belongs to.
   *
   * <p>This field is final, static, and public, and is used in the createUserIdFilter method.
   */
  public static final String USER_ID_FIELD_NAME = "user_id";

  /**
   * The name of the field under which SearchBuilder Filters can be built in order to filter on the
   * submission ids associated with the Resource.
   *
   * <p>This field is final, static, and public, and is used in the createSubmissionIdFilter and
   * createNoSubmissionFilter methods.
   */
  public static final String SUBMISSION_ID_FIELD_NAME = "submission_id";

  /**
   * The name of the field under which SearchBuilder Filters can be built in order to filter on the
   * resource role id with which the Resource is associated.
   *
   * <p>This field is final, static, and public, and is used in the createResourceRoleIdFilter
   * method.
   */
  public static final String RESOURCE_ROLE_ID_FIELD_NAME = "resource_role_id";

  /**
   * The name of the field under which SearchBuilder Filters can be built in order to filter based
   * on the name of an extension property.
   *
   * <p>This field is public, static, and final.
   */
  public static final String EXTENSION_PROPERTY_NAME_FIELD_NAME = "name";

  /**
   * The name of the field under which SearchBuilder Filters can be built in order to filter based
   * on the value of an extension property.
   *
   * <p>This field is public, static, and final.
   */
  public static final String EXTENSION_PROPERTY_VALUE_FIELD_NAME = "value";

  /** The constructor of ResourceFilterBuilder. It is set to be private to prevent instantiation. */
  private ResourceFilterBuilder() {
    // do nothing
  }

  /**
   * Creates a filter that selects resources with the given id.
   *
   * @param resourceId The resource id to filter on
   * @return A filter for selecting Resources with the given id
   * @throws IllegalArgumentException If resourceId is &lt;= 0
   */
  public static Filter createResourceIdFilter(long resourceId) {
    Helper.checkLongPositive(resourceId, "resourceId");
    return new EqualToFilter(RESOURCE_ID_FIELD_NAME, resourceId);
  }

  /**
   * Creates a filter that selects resources which have the given project id.
   *
   * @param projectId The project id to filter on
   * @return A filter for selecting Resources which are associated with the given project
   * @throws IllegalArgumentException If projectId is &lt;= 0.
   */
  public static Filter createProjectIdFilter(long projectId) {
    Helper.checkLongPositive(projectId, "projectId");
    return new EqualToFilter(PROJECT_ID_FIELD_NAME, projectId);
  }

  /**
   * Creates a filter that selects resources which are related to the given phase.
   *
   * @param phaseId The phase id to create the filter with
   * @return A filter for selecting Resources associated with the given phase
   * @throws IllegalArgumentException If phaseId is &lt;= 0
   */
  public static Filter createPhaseIdFilter(long phaseId) {
    Helper.checkLongPositive(phaseId, "phaseId");
    return new EqualToFilter(PHASE_ID_FIELD_NAME, phaseId);
  }

  /**
   * Creates a filter that selects resources which are related to the given user.
   *
   * @param userId The user id to create the filter with
   * @return A filter for selecting Resources associated with the given user
   * @throws IllegalArgumentException If userId is &lt;= 0
   */
  public static Filter createUserIdFilter(long userId) {
    Helper.checkLongPositive(userId, "userId");
    return new EqualToFilter(USER_ID_FIELD_NAME, userId);
  }

  /**
   * Creates a filter that selects resources which have associated the given submission id with
   * their submission Set.
   *
   * @param submissionId The submission id to create the filter with
   * @return A filter to selecting Resources which have associated the given submission id with
   *     their submission Set.
   * @throws IllegalArgumentException If submissionId is &lt;= 0
   */
  public static Filter createSubmissionIdFilter(long submissionId) {
    Helper.checkLongPositive(submissionId, "submissionId");
    return new EqualToFilter(SUBMISSION_ID_FIELD_NAME, submissionId);
  }

  /**
   * Creates a filter that selects resources which have associated any one in the given submission
   * ids with their submission Set.
   *
   * @param submissionsId the array of submission ids to build the filter with
   * @return A filter for selecting Resources associated with the any of the given submissionId; or
   *     NullFilter if given array is empty
   * @throws IllegalArgumentException if given argument is null or any of the array elements is
   *     &lt;=0
   * @since 1.1
   */
  public static Filter createAnySubmissionIdFilter(long[] submissionsId) {
    Helper.checkNull(submissionsId, "submissionsId");

    if (submissionsId.length == 0) {
      return new NullFilter(SUBMISSION_ID_FIELD_NAME);
    }

    // Validate each id value, and remove duplicated ids by putting them into set which will improve
    // efficiency
    // of InFilter created later.
    Set submissionIdSet = new HashSet();
    for (int i = 0; i < submissionsId.length; i++) {
      Helper.checkLongPositive(submissionsId[i], "element of submissionsId array");

      submissionIdSet.add(submissionsId[i]);
    }

    return new InFilter(SUBMISSION_ID_FIELD_NAME, new ArrayList(submissionIdSet));
  }

  /**
   * Creates a filter that selects resources which are associated with the given resource role.
   *
   * @param resourceRoleId The resource role id to create the filter with
   * @return A filter to selecting Resources with the given role
   * @throws IllegalArgumentException If resourceRoleId is &lt;= 0
   */
  public static Filter createResourceRoleIdFilter(long resourceRoleId) {
    Helper.checkLongPositive(resourceRoleId, "resourceRoleId");
    return new EqualToFilter(RESOURCE_ROLE_ID_FIELD_NAME, resourceRoleId);
  }

  /**
   * Creates a filter that selects resources which do not have an associated project.
   *
   * @return A filter for selecting Resources with no associated project
   */
  public static Filter createNoProjectFilter() {
    return new NullFilter(PROJECT_ID_FIELD_NAME);
  }

  /**
   * Creates a filter that selects resources which do not have an associated submission.
   *
   * @return A filter for selecting Resources with no associated submission
   */
  public static Filter createNoSubmissionFilter() {
    return new NullFilter(SUBMISSION_ID_FIELD_NAME);
  }

  /**
   * Creates a filter that selects resources which do not have an associated phase.
   *
   * @return A filter for selecting Resources that are not associated with a phase
   */
  public static Filter createNoPhaseFilter() {
    return new NullFilter(PHASE_ID_FIELD_NAME);
  }

  /**
   * Creates a filter that selects resources which have an extension property with the given value.
   *
   * @param value The value to search for
   * @return A filter for selecting Resources that have an extension property with the given value
   * @throws IllegalArgumentException If value is null
   */
  public static Filter createExtensionPropertyValueFilter(String value) {
    Helper.checkNull(value, "value");
    return new EqualToFilter(EXTENSION_PROPERTY_VALUE_FIELD_NAME, value);
  }

  /**
   * Creates a filter that selects resources which have an extension property with the given name.
   *
   * @param name The extension property name to search for
   * @return A filter for selecting Resources that have an extension property with the given name
   * @throws IllegalArgumentException If name is null
   */
  public static Filter createExtensionPropertyNameFilter(String name) {
    Helper.checkNull(name, "name");
    return new EqualToFilter(EXTENSION_PROPERTY_NAME_FIELD_NAME, name);
  }
}
