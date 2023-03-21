/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.termsofuse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topcoder.onlinereview.component.grpcclient.termsofuse.TermsOfUseServiceRpc;
import com.topcoder.onlinereview.grpc.termsofuse.proto.ProjectRoleTermsOfUseProto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This class is the default implementation of ProjectTermsOfUseDao. It utilizes the DB Connection Factory to get
 * access to the database. The configuration is done by the Configuration API.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;CMConfig&gt;
 *     &lt;Config name=&quot;projectTermsOfUseDao&quot;&gt;
 *      &lt;Property name=&quot;dbConnectionFactoryConfig&quot;&gt;
 *        &lt;Property name=&quot;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&quot;&gt;
 *          &lt;Property name=&quot;connections&quot;&gt;
 *                 &lt;Property name=&quot;default&quot;&gt;
 *                     &lt;Value&gt;InformixJDBCConnection&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *              &lt;Property name=&quot;InformixJDBCConnection&quot;&gt;
 *                  &lt;Property name=&quot;producer&quot;&gt;
 *                      &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *                  &lt;/Property&gt;
 *                  &lt;Property name=&quot;parameters&quot;&gt;
 *                      &lt;Property name=&quot;jdbc_driver&quot;&gt;
 *                      &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name=&quot;jdbc_url&quot;&gt;
 *                              &lt;Value&gt;
 *                                  jdbc:informix-sqli://localhost:1526/common_oltp:informixserver=ol_topcoder
 *                              &lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name=&quot;user&quot;&gt;
 *                          &lt;Value&gt;informix&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name=&quot;password&quot;&gt;
 *                          &lt;Value&gt;123456&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                  &lt;/Property&gt;
 *              &lt;/Property&gt;
 *          &lt;/Property&gt;
 *        &lt;/Property&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;loggerName&quot;&gt;
 *          &lt;Value&gt;loggerName&lt;/Value&gt;
 *      &lt;/Property&gt;
 *     &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Create the configuration object
 * ConfigurationObject configurationObject = TestsHelper.getConfig(TestsHelper.CONFIG_PROJECT_TERMS);
 * // Instantiate the dao implementation from configuration defined above
 * ProjectTermsOfUseDao projectTermsOfUseDao = new ProjectTermsOfUseDaoImpl(configurationObject);
 *
 * // Create user terms of use to project link
 * projectTermsOfUseDao.createProjectRoleTermsOfUse(2, 2, 3, 2, 0);
 *
 * // Remove user terms of use to project link
 * projectTermsOfUseDao.removeProjectRoleTermsOfUse(2, 2, 3, 0);
 *
 * // Get terms of use with non-member-agreeable terms
 * // Will return two lists:
 * // 1st with ids: 1,2,3
 * // 2nd with ids: 1
 * Map&lt;Integer, List&lt;TermsOfUse&gt;&gt; termsGroupMap =
 *     projectTermsOfUseDao.getTermsOfUse(1, new int[] {1, 2}, null);
 *
 * // Get terms of use without non-member-agreeable terms
 * // Will return one list:
 * // 1st with ids: 1
 * termsGroupMap = projectTermsOfUseDao.getTermsOfUse(1, new int[] {1, 2}, new int[] {2, 3});
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Changes in 1.1:</em>
 * <ol>
 * <li>getTermsOfUse() method was updated to support filtering of terms of use groups by custom agreeability types
 * instead of member agreeable flag.</li>
 * </ol>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The class is immutable and thread safe.
 * </p>
 *
 * @author faeton, sparemax, saarixx
 * @version 1.1
 */
@Slf4j
@Component
public class ProjectTermsOfUseDao {
    @Autowired
    TermsOfUseServiceRpc termsOfUseServiceRpc;
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = ProjectTermsOfUseDao.class.getName();

    /**
     * This method will create a project role terms of use association.
     *
     * @param groupIndex
     *            the group index to associate.
     * @param resourceRoleId
     *            the role id to associate.
     * @param sortOrder
     *            the association sort order.
     * @param termsOfUseId
     *            the terms of use id to associate.
     * @param projectId
     *            the project id to associate.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public void createProjectRoleTermsOfUse(int projectId, int resourceRoleId, long termsOfUseId, int sortOrder,
        int groupIndex) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".createProjectRoleTermsOfUse(int projectId, int resourceRoleId,"
            + " long termsOfUseId, int sortOrder, int groupIndex)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"projectId", "resourceRoleId", "termsOfUseId", "sortOrder", "groupIndex"},
            new Object[] {projectId, resourceRoleId, termsOfUseId, sortOrder, groupIndex});

        try {
            termsOfUseServiceRpc.createProjectRoleTermsOfUse(projectId, resourceRoleId, termsOfUseId, sortOrder, groupIndex);

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * This method will remove a project role terms of use association.
     *
     * @param groupIndex
     *            the group index to associate.
     * @param resourceRoleId
     *            the role id to associate.
     * @param termsOfUseId
     *            the terms of use id to associate group index to associate.
     * @param projectId
     *            the project id to associate.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     */
    public void removeProjectRoleTermsOfUse(int projectId, int resourceRoleId, long termsOfUseId, int groupIndex)
        throws EntityNotFoundException, TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".removeProjectRoleTermsOfUse(int projectId, int resourceRoleId,"
            + " long termsOfUseId, int groupIndex)";

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"projectId", "resourceRoleId", "termsOfUseId", "groupIndex"},
            new Object[] {projectId, resourceRoleId, termsOfUseId, groupIndex});

        try {
            int num = termsOfUseServiceRpc.deleteProjectRoleTermsOfUse(projectId, resourceRoleId, termsOfUseId,
                    resourceRoleId, groupIndex);
            if (num != 1) {
                throw new EntityNotFoundException("The entity was not found for id ("
                        + new StringBuilder().append(projectId).append(", ").append(resourceRoleId).append(", ")
                                .append(termsOfUseId).append(", ").append(groupIndex).toString()
                        + ").");
            }

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * <p>
     * This method retrieves terms of use for specific pair of user and resource roles and groups it by terms of use
     * groups. Additionally groups can be filtered by agreeability types.
     * </p>
     *
     * <p>
     * <em>Changes in 1.1:</em>
     * <ol>
     * <li>Replaced includeNonMemberAgreeable:boolean parameter with agreeabilityTypeIds:int[].</li>
     * <li>Throws IllegalArgumentException if agreeabilityTypeIds is empty.</li>
     * </ol>
     * </p>
     *
     * <p>
     * <em>Changes in 1.1.1</em>
     * This method should return a map
     * where the key is the role id and the value is the TOU groups linked to that role.
     * </p>
     *
     * @param projectId
     *            the project id to associate.
     * @param agreeabilityTypeIds
     *            the IDs of the agreeability types for terms of use to be retrieved (null if filtering by
     *            agreeability type is not required; if at least one terms of use in the group has agreeability type
     *            with not specified ID, the whole group is ignored)
     *
     * @return Map of lists of terms of use entities. The key of the map is the role id, the value is the
     *         TOU groups for this role.
     *
     * @throws IllegalArgumentException
     *             if agreeabilityTypeIds is empty.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public Map<Integer, List<TermsOfUse>> getTermsOfUse(int projectId, int resourceRoleId,
        int[] agreeabilityTypeIds) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getTermsOfUse(int projectId, int[] resourceRoleIds,"
            + " int[] agreeabilityTypeIds)";

        List<Integer> agreeabilityTypeIdsList = toList(agreeabilityTypeIds);

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"projectId", "resourceRoleId", "agreeabilityTypeIds"},
            new Object[] {projectId, resourceRoleId, agreeabilityTypeIdsList});

        try {
            if ((agreeabilityTypeIds != null) && (agreeabilityTypeIds.length == 0)) {
                throw new IllegalArgumentException("'agreeabilityTypeIds' should not be empty.");
            }
            List<ProjectRoleTermsOfUseProto> projectRoleTermsOfUseProtos = termsOfUseServiceRpc.getProjectRoleTermsOfUse(projectId, resourceRoleId);

            Map<Integer, List<TermsOfUse>> result = getTermsOfUse(projectRoleTermsOfUseProtos, agreeabilityTypeIdsList);

            // Log method exit
            Helper.logExit(log, signature, new Object[] {result});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * This method will remove all project role terms of use association for a given project.
     *
     * @param projectId
     *            the project id to remove.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public void removeAllProjectRoleTermsOfUse(int projectId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".removeAllProjectRoleTermsOfUse(int projectId)";
        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"projectId"},
            new Object[] {projectId});

        try {
            termsOfUseServiceRpc.deleteAllProjectRoleTermsOfUse(projectId);
            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * <p>
     * Retrieves terms of use for specific pair of user and resource roles and groups it by terms of use groups.
     * Additionally groups can be filtered by agreeability types.
     * </p>
     *
     * <p>
     * <em>Changes in 1.1:</em>
     * <ol>
     * <li>Replaced includeNonMemberAgreeable:boolean parameter with agreeabilityTypeIds:List&lt;Integer&gt;.</li>
     * <li>Updated implementation notes to use agreeabilityTypeIds instead of includeNonMemberAgreeable.</li>
     * </ol>
     * </p>
     *
     * @param rs
     *            the result set.
     * @param agreeabilityTypeIds
     *            the IDs of the agreeability types for terms of use to be retrieved (null if filtering by
     *            agreeability type is not required; if at least one terms of use in the group has agreeability type
     *            with not specified ID, the whole group is ignored).
     *
     * @return Map of lists of terms of use entities. The key of the map is the group index, the value is the list of
     *         terms for this group.
     *
     * @throws SQLException
     *             if any persistence error occurs.
     */
    private static Map<Integer, List<TermsOfUse>> getTermsOfUse(List<ProjectRoleTermsOfUseProto> rs, List<Integer> agreeabilityTypeIds) {

        Map<Integer, List<TermsOfUse>> result = new HashMap<Integer, List<TermsOfUse>>();

        // Flag, specifying whether the groupList should not be added to result list
        // This is done when one of user terms of the list has agreeability type ID
        // that is not in agreeabilityTypeIds and agreeabilityTypeIds != null:
        Integer previousGroup = null;

        // The single list of terms ids, which contains ids from the same group.
        List<TermsOfUse> groupList = new ArrayList<TermsOfUse>();
        Integer group = 0;

        // Flag, specifying whether the groupList should not be added to result list
        // This is done when one of user terms of the list is not memberAgreeable
        // and includeNonMemberAgreeable flag == false
        boolean ignoreGroupList = false;

        // Iterate over all results
        for (ProjectRoleTermsOfUseProto r: rs) {
            // Get the group
            group = r.getGroupInd();

            // if moved to next group, add current list to result and clear.
            if (!group.equals(previousGroup)) {
                if (!groupList.isEmpty() && !ignoreGroupList) {
                    result.put(previousGroup, new ArrayList<>(groupList));
                }
                groupList.clear();

                previousGroup = group;
                ignoreGroupList = false;
            }
            if (ignoreGroupList) {
                // Skip the group
                continue;
            }

            TermsOfUse terms = Helper.getTermsOfUse(r);
            groupList.add(terms);

            if (agreeabilityTypeIds != null) {
                TermsOfUseAgreeabilityType agreeabilityType = terms.getAgreeabilityType();
                int termsOfUseAgreeabilityTypeId = agreeabilityType.getTermsOfUseAgreeabilityTypeId();

                if (!agreeabilityTypeIds.contains(termsOfUseAgreeabilityTypeId)) {
                    ignoreGroupList = true;
                }
            }
        }
        // If some data present, add to result
        if (!groupList.isEmpty() && !ignoreGroupList) {
            result.put(group, groupList);
        }

        return result;
    }

    /**
     * Converts the array to a list.
     *
     * @param array
     *            the array.
     *
     * @return the list or <code>null</code> if array is <code>null</code>.
     */
    private static List<Integer> toList(int[] array) {
        if (array == null) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }

        return list;
    }
}
