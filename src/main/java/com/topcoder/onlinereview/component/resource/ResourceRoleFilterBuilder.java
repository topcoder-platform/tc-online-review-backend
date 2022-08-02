/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.resource;

import com.topcoder.onlinereview.component.search.filter.EqualToFilter;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.component.search.filter.NullFilter;

/**
 * The ResourceRoleFilterBuilder class supports building filters for searching for ResourceRoles.
 *
 * <p>This class consists of 2 parts. The first part consists of static Strings that name the fields
 * that are available for searching. All ResourceManager implementations should use SearchBundles
 * that are configured to use these names. The second part of this class consists of convenience
 * methods to create common filters based on these field names.
 *
 * <p>This class has only final static fields/methods, so mutability is not an issue. This class is
 * thread-safe.
 *
 * @author aubergineanode, kinfkong
 * @version 1.0
 */
public class ResourceRoleFilterBuilder {

  /**
   * The name of the field under which SearchBuilder Filters can be built in order to filter on the
   * resource role id.
   *
   * <p>This field is final, static, and public, and is used in the createResourceRoleIdFilter
   * method.
   */
  public static final String RESOURCE_ROLE_ID_FIELD_NAME = "resource_role_id";

  /**
   * The name of the field under which SearchBuilder Filters can be built in order to filter on the
   * resource role name.
   *
   * <p>This field is final, static, and public, and is used in the createNameFilter method.
   */
  public static final String NAME_FIELD_NAME = "name";

  /**
   * The name of the field under which SearchBuilder Filters can be built in order to filter on the
   * phase type id of the resource role.
   *
   * <p>This field is final, static, and public, and is used in the createPhaseTypeIdFilter and
   * createNoPhaseTypeFilter methods.
   */
  public static final String PHASE_TYPE_ID_FIELD_NAME = "phase_type_id";

  /** Costructor of ResourceRoleFilterBuilder. It is set to be private prevent instantiation. */
  private ResourceRoleFilterBuilder() {
    // do nothing
  }

  /**
   * Creates a filter that selects resource roles which have the given id.
   *
   * @param resourceRoleId The resource role id to filter on
   * @return A filter for selecting resource roles which have the given id
   * @throws IllegalArgumentException If resourceRoleId is &lt;= 0
   */
  public static Filter createResourceRoleIdFilter(long resourceRoleId) {
    Helper.checkLongPositive(resourceRoleId, "resourceRoleId");
    return new EqualToFilter(RESOURCE_ROLE_ID_FIELD_NAME, new Long(resourceRoleId));
  }

  /**
   * Creates a filter that selects resource roles which have the given name.
   *
   * @param name The resource role name to filter on
   * @return A filter for selecting resource roles which have the given name.
   * @throws IllegalArgumentException If name is null
   */
  public static Filter createNameFilter(String name) {
    Helper.checkNull(name, "name");
    return new EqualToFilter(NAME_FIELD_NAME, name);
  }

  /**
   * Creates a filter that selects resource roles which are related to the given phase type.
   *
   * @param phaseTypeId The phase type id to create the filter with
   * @return A filter for selecting resource roles associated with the given phase type id
   * @throws IllegalArgumentException If phaseTypeId is &lt;= 0
   */
  public static Filter createPhaseTypeIdFilter(long phaseTypeId) {
    Helper.checkLongPositive(phaseTypeId, "phaseTypeId");
    return new EqualToFilter(PHASE_TYPE_ID_FIELD_NAME, new Long(phaseTypeId));
  }

  /**
   * Creates a filter that selects resources which do not have an associated phase.
   *
   * @return A filter for selecting ResourceRoles that are not associated with a phase type
   */
  public static Filter createNoPhaseTypeFilter() {
    return new NullFilter(PHASE_TYPE_ID_FIELD_NAME);
  }
}
