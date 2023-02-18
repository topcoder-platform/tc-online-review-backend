/*
 * Copyright (C) 2009 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.management;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topcoder.onlinereview.component.grpcclient.project.ProjectServiceRpc;
import com.topcoder.onlinereview.grpc.project.proto.ProjectLinkProto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    ProjectServiceRpc projectServiceRpc;

    /**
     * <p>
     * The project manager. It will delegate some project related queries into project manager.
     * </p>
     */
    @Autowired
    private ProjectManager projectManager;

    /**
     * <p>
     * Gets all project link types.
     * </p>
     *
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    public ProjectLinkType[] getAllProjectLinkTypes() throws PersistenceException {
        log.debug( new LogMessage(null, null, "Enter getAllProjectLinkTypes method.").toString());
        return projectServiceRpc.getAllProjectLinkTypes();
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
        List<ProjectLinkProto> pls = projectServiceRpc.getDestProjectLinks(sourceProjectId);

        ProjectLink[] projectLinks = new ProjectLink[pls.size()];

        Long[] ids = new Long[pls.size() + 1];
        ids[0] = sourceProjectId;
        for (int i = 0; i < pls.size(); ++i) {
            ProjectLinkProto pl = pls.get(i);

            // create a new instance of ProjectLink class
            projectLinks[i] = new ProjectLink();
            // we will only provide link type id
            projectLinks[i].setType(new ProjectLinkType());
            projectLinks[i].getType().setId(pl.getLinkTypeId());
            ids[i + 1] = pl.getDestProjectId();
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
        List<ProjectLinkProto> pls = projectServiceRpc.getSourceProjectLinks(destProjectId);

        ProjectLink[] projectLinks = new ProjectLink[pls.size()];

        Long[] ids = new Long[pls.size() + 1];
        ids[0] = destProjectId;
        for (int i = 0; i < pls.size(); ++i) {
            ProjectLinkProto pl = pls.get(i);

            // create a new instance of ProjectLink class
            projectLinks[i] = new ProjectLink();
            // we will only provide link type id
            projectLinks[i].setType(new ProjectLinkType());
            projectLinks[i].getType().setId(pl.getLinkTypeId());
            ids[i + 1] = pl.getSourceProjectId();
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
    public void updateProjectLinks(Long sourceProjectId, Long[] destProjectIds, Long[] linkTypeIds)
        throws PersistenceException {
        log.debug(new LogMessage(null, null, "Enter updateProjectLinks method.").toString());
        Helper.assertObjectNotNull(destProjectIds, "destProjectIds");
        Helper.assertObjectNotNull(linkTypeIds, "linkTypeIds");
        if (destProjectIds.length != linkTypeIds.length) {
            throw new IllegalArgumentException("destProjectIds must have same length as linkTypeIds");
        }
        checkForCycle(sourceProjectId, destProjectIds, linkTypeIds);
        // refresh links
        projectServiceRpc.deleteProjectLinksBySourceId(sourceProjectId);

        for (int i = 0; i < destProjectIds.length; i++) {
            projectServiceRpc.createProjectLink(sourceProjectId, destProjectIds[i], linkTypeIds[i]);
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
        Long[] destProjectIds = new Long[destProjectLinks.length];
        Long[] linkTypeIds = new Long[destProjectLinks.length];

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
    private void checkForCycle(Long sourceProjectId, Long[] destProjectIds, Long[] linkTypeIds)
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
