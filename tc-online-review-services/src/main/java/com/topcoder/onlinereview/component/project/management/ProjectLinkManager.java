/*
 * Copyright (C) 2009 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.management;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSql;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeUpdateSql;
import static com.topcoder.onlinereview.component.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.component.util.CommonUtils.getString;


/**
 * <p>
 * Project link manager. It handles persistence operations for project link as well as project link type. It currently
 * relies on the project manager to get <code>Project</code> entities.
 * </p>
 * <p>
 * It is created for "OR Project Linking" assembly.
 * </p>
 *
 * <p>
 * Change log for version 1.1: Updated the code for initializing the retrieved project link types with value of
 * allow_overlap flag; update {@link #updateProjectLinks(long, long[], long[])} method to validate the project links
 * against cycle.
 * </p>
 *
 * @author BeBetter, TCSDEVELOPER, TCSDEVELOPER
 * @version 1.2.4
 * @since 1.0
 */
@Slf4j
@Component
public class ProjectLinkManager {

    /**
     * <p>
     * Represents the name of connection name parameter in configuration.
     * </p>
     */
    private static final String CONNECTION_NAME_PARAMETER = "ConnectionName";

    /**
     * <p>
     * Represents the name of connection factory namespace parameter in configuration.
     * </p>
     */
    private static final String CONNECTION_FACTORY_NAMESPACE_PARAMETER = "ConnectionFactoryNS";

    /**
     * <p>
     * The SQL to get all project link types.
     * </p>
     */
    private static final String QUERY_ALL_PROJECT_LINK_TYPES_SQL = "SELECT link_type_id, link_type_name, allow_overlap"
        + " FROM link_type_lu";

    /**
     * <p>
     * The SQL to get all destination project links given source project id.
     * </p>
     */
    private static final String QUERY_DEST_PROJECT_LINK_SQL = "SELECT dest_project_id, link_type_id "
        + " FROM linked_project_xref WHERE source_project_id = ?";

    /**
     * <p>
     * The SQL to get all source project links for the given destination project id.
     * </p>
     */
    private static final String QUERY_SOURCE_PROJECT_LINK_SQL = "SELECT source_project_id, link_type_id "
        + " FROM linked_project_xref WHERE dest_project_id = ?";

    /**
     * <p>
     * The SQL to delete project links by source project id.
     * </p>
     */
    private static final String DELETE_PROJECT_LINK_BY_SOURCE_RPOJECT_ID = "DELETE FROM linked_project_xref "
        + "WHERE source_project_id = ?";

    /**
     * <p>
     * The SQL to insert project link.
     * </p>
     */
    private static final String INSERT_PROJECT_LINK = "INSERT INTO linked_project_xref"
        + "(source_project_id,dest_project_id,link_type_id) VALUES(?,?,?)";

    /**
     * <p>
     * The project manager. It will delegate some project related queries into project manager.
     * </p>
     */
    @Autowired
    private ProjectManager projectManager;
    @Autowired
    @Qualifier("tcsJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * <p>
     * Gets all project link types.
     * </p>
     *
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    public ProjectLinkType[] getAllProjectLinkTypes() throws PersistenceException {
        Connection conn = null;

        log.debug( new LogMessage(null, null, "Enter getAllProjectLinkTypes method.").toString());
        List<Map<String, Object>> rows = executeSql(jdbcTemplate, QUERY_ALL_PROJECT_LINK_TYPES_SQL);

        // create the ProjectLinkType array.
        ProjectLinkType[] projectLinkTypes = new ProjectLinkType[rows.size()];

        for (int i = 0; i < rows.size(); ++i) {
            Map<String, Object> row = rows.get(i);
            // create a new instance of ProjectLinkType class
            projectLinkTypes[i] = new ProjectLinkType(getLong(row, "link_type_id"), getString(row, "link_type_name"),
                    "1".equals(getString(row, "allow_overlap")));
        }

        return projectLinkTypes;
    }

    /**
     * <p>
     * Gets all project links based on source project id.
     * </p>
     *
     * @param sourceProjectId source project id
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    public ProjectLink[] getDestProjectLinks(long sourceProjectId) throws PersistenceException {
        log.debug(new LogMessage(null, null, "Enter getDestProjectLinks method.").toString());
        List<Map<String, Object>> rows = executeSqlWithParam(jdbcTemplate, QUERY_DEST_PROJECT_LINK_SQL, newArrayList(sourceProjectId));

        ProjectLink[] projectLinks = new ProjectLink[rows.size()];

        long[] ids = new long[rows.size() + 1];
        ids[0] = sourceProjectId;
        for (int i = 0; i < rows.size(); ++i) {
            Map<String, Object> row = rows.get(i);

            // create a new instance of ProjectLink class
            projectLinks[i] = new ProjectLink();
            // we will only provide link type id
            projectLinks[i].setType(new ProjectLinkType());
            projectLinks[i].getType().setId(getLong(row, "link_type_id"));
            ids[i + 1] = getLong(row, "dest_project_id");
        }

        // get Project objects
        Project[] projects = projectManager.getProjects(ids);
        Project sourceProject = projects[0];
        for (int i = 0; i < projectLinks.length; i++) {
            projectLinks[i].setSourceProject(sourceProject);
            projectLinks[i].setDestProject(projects[i + 1]);
        }

        fillLinkTypes(projectLinks);
        return projectLinks;
    }

    /**
     * <p>
     * Gets all project links based on destination project id.
     * </p>
     *
     * @param destProjectId destination project id
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    public ProjectLink[] getSourceProjectLinks(long destProjectId) throws PersistenceException {
        log.debug(new LogMessage(null, null, "Enter getSourceProjectLinks method.").toString());
        List<Map<String, Object>> rows = executeSqlWithParam(jdbcTemplate, QUERY_SOURCE_PROJECT_LINK_SQL, newArrayList(destProjectId));

        ProjectLink[] projectLinks = new ProjectLink[rows.size()];

        long[] ids = new long[rows.size() + 1];
        ids[0] = destProjectId;
        for (int i = 0; i < rows.size(); ++i) {
            Map<String, Object> row = rows.get(i);

            // create a new instance of ProjectLink class
            projectLinks[i] = new ProjectLink();
            // we will only provide link type id
            projectLinks[i].setType(new ProjectLinkType());
            projectLinks[i].getType().setId(getLong(row, "link_type_id"));
            ids[i + 1] = getLong(row, "source_project_id");
        }

        // get Project objects
        Project[] projects = projectManager.getProjects(ids);
        Project destProject = projects[0];
        for (int i = 0; i < projectLinks.length; i++) {
            projectLinks[i].setSourceProject(projects[i + 1]);
            projectLinks[i].setDestProject(destProject);
        }

        fillLinkTypes(projectLinks);
        return projectLinks;
    }

    /**
     * <p>
     * Fills full blown link type information.
     * </p>
     *
     * @param projectLinks project links to be filled
     * @throws PersistenceException if any persistence error occurs
     */
    private void fillLinkTypes(ProjectLink[] projectLinks) throws PersistenceException {
        ProjectLinkType[] allTypes = getAllProjectLinkTypes();

        Map<Long, ProjectLinkType> typeMap = new HashMap<>();
        for (ProjectLinkType type : allTypes) {
            typeMap.put(type.getId(), type);
        }

        for (ProjectLink link : projectLinks) {
            link.setType(typeMap.get(link.getType().getId()));
        }
    }

    /**
     * <p>
     * Updates project links for given source project id. It will delete all old links and use passed in project
     * links. There are 2 arrays passed in, one is for destination project ids and other for link type ids. The id at
     * the same position in each array represents a project link information.
     * </p>
     *
     * @param sourceProjectId the source project id
     * @param destProjectIds the destination project ids
     * @param linkTypeIds the type ids
     * @throws IllegalArgumentException if any array is null or it is not equal in length for dest project id array
     *             and link type array
     * @throws PersistenceException if any persistence error occurs
     */
    public void updateProjectLinks(long sourceProjectId, long[] destProjectIds, long[] linkTypeIds)
        throws PersistenceException {
        log.debug(new LogMessage(null, null, "Enter updateProjectLinks method.").toString());
        Helper.assertObjectNotNull(destProjectIds, "destProjectIds");
        Helper.assertObjectNotNull(linkTypeIds, "linkTypeIds");
        if (destProjectIds.length != linkTypeIds.length) {
            throw new IllegalArgumentException("destProjectIds must have same length as linkTypeIds");
        }
        checkForCycle(sourceProjectId, destProjectIds, linkTypeIds);
        // refresh links
        executeUpdateSql(jdbcTemplate, DELETE_PROJECT_LINK_BY_SOURCE_RPOJECT_ID, newArrayList(sourceProjectId));

        for (int i = 0; i < destProjectIds.length; i++) {
            executeUpdateSql(jdbcTemplate, INSERT_PROJECT_LINK, newArrayList(sourceProjectId, destProjectIds[i], linkTypeIds[i]));
        }
    }

    /**
     * <p>Checks if specified project is a part of cycle in project dependencies.</p>
     *
     * @param sourceProjectId a <code>long</code> providing the ID of a project.
     * @throws PersistenceException if any persistence error occurs.
     */
    public void checkForCycle(long sourceProjectId) throws PersistenceException {
        ProjectLink[] destProjectLinks = getDestProjectLinks(sourceProjectId);
        long[] destProjectIds = new long[destProjectLinks.length];
        long[] linkTypeIds = new long[destProjectLinks.length];

        for (int i = 0; i < destProjectLinks.length; i++) {
            ProjectLink link = destProjectLinks[i];
            destProjectIds[i] = link.getDestProject().getId();
            linkTypeIds[i] = link.getType().getId();
        }

        checkForCycle(sourceProjectId, destProjectIds, linkTypeIds);
    }

    /**
     * <p>Checks if specified project referring to specified projects is a part of a cycle.</p>
     *
     * @param sourceProjectId the source project id.
     * @param destProjectIds the destination project ids.
     * @param linkTypeIds the type ids.
     * @throws PersistenceException if any persistence error occurs.
     */
    private void checkForCycle(long sourceProjectId, long[] destProjectIds, long[] linkTypeIds)
        throws PersistenceException {

        // Build project link types map
        Map<Long, ProjectLinkType> typesMap = new HashMap<Long, ProjectLinkType>();
        ProjectLinkType[] types = getAllProjectLinkTypes();
        for (long linkTypeId : linkTypeIds) {
            for (ProjectLinkType type : types) {
                if (type.getId() == linkTypeId) {
                    typesMap.put(linkTypeId, type);
                    break;
                }
            }
        }

        // Validate for cycles using BFS algorithm

        // Current candidates for cycle
        Project currentProject = this.projectManager.getProject(sourceProjectId);
        Map<Long, LinkedList<Project>> cycles = new HashMap<Long, LinkedList<Project>>();

        // A list of projects to be visited
        LinkedHashMap<Long, Project> open = new LinkedHashMap<Long, Project>();
        for (int i = 0; i < destProjectIds.length; i++) {
            long linkTypeId = linkTypeIds[i];
            ProjectLinkType type = typesMap.get(linkTypeId);
            if (type != null) {
                if (!type.isAllowOverlap()) {
                    open.put(destProjectIds[i], this.projectManager.getProject(destProjectIds[i]));
                    cycles.put(destProjectIds[i],
                               new LinkedList(Arrays.asList(currentProject)));
                }
            }
        }

        // A list of projects already visited
        Map<Long, Project> closed = new HashMap<Long, Project>();
        closed.put(sourceProjectId, currentProject);

        // Do BFS
        while (!open.isEmpty()) {
            Iterator<Map.Entry<Long, Project>> iterator = open.entrySet().iterator();
            Project project = iterator.next().getValue();
            iterator.remove();

            Long projectId = project.getId();

            if (!closed.containsKey(projectId)) {
                closed.put(projectId, project);
                ProjectLink[] links = getDestProjectLinks(projectId);
                for (ProjectLink link : links) {
                    long nextProjectId = link.getDestProject().getId();
                    if (!link.getType().isAllowOverlap()) {
                        if (!closed.containsKey(nextProjectId)) {
                            if (!open.containsKey(nextProjectId)) {
                                open.put(nextProjectId, link.getDestProject());
                                if (cycles.containsKey(nextProjectId)) {
                                    cycles.get(nextProjectId).add(project);
                                } else {
                                    LinkedList<Project> currentCycle = cycles.get(projectId);
                                    LinkedList newCycle = new LinkedList(currentCycle);
                                    newCycle.add(project);
                                    cycles.put(nextProjectId, newCycle);
                                }
                            }
                        } else {
                            LinkedList<Project> cycle = cycles.get(projectId);
                            cycle.addLast(project);
                            throw new ProjectLinkCycleException(cycle);
                        }
                    }
                }
            } else {
                throw new ProjectLinkCycleException(cycles.get(projectId));
            }

            closed.put(project.getId(), project);
        }
    }
}
