/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import com.topcoder.management.project.*;
import com.topcoder.search.builder.filter.Filter;

import java.util.List;

/**
 * A mock implementation of ProjectManager for testing.
 *
 * @author kshatriyan
 * @version 1.0
 */
public class MockProjectManager implements ProjectManager {

    /**
     * Not used.
     *
     * @param arg0
     *            project
     * @param arg1
     *            desc
     * @throws PersistenceException
     *             not thrown
     * @throws ValidationException
     *             not thrown
     */
    public void createProject(Project arg0, String arg1) throws PersistenceException, ValidationException {
    }

    /**
     * Not used.
     *
     * @param arg0
     *            project
     * @param arg1
     *            desc
     * @param arg2
     *            desc
     * @throws PersistenceException
     *             not thrown
     * @throws ValidationException
     *             not thrown
     */
    public void updateProject(Project arg0, String arg1, String arg2) throws PersistenceException,
            ValidationException {

    }

    /**
     * A mock implementation.
     *
     * @param arg0
     *            project id
     * @return project
     * @throws PersistenceException
     *             if the flag is set
     */
    public Project getProject(long arg0) throws PersistenceException {
        ProjectType type = new ProjectType(1, "java");
        ProjectCategory category = new ProjectCategory(1, "accuracy", type);
        ProjectStatus status = new ProjectStatus(1, "accuracy");
        Project project = new Project(arg0, category, status);
        project.setProperty("Winner External Reference ID", AccuracyHelper.USER_ID);
        project.setProperty("Allow multiple submissions", "true");
        return project;
    }

    /**
     * Not used.
     *
     * @param arg0
     *            project id
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public Project[] getProjects(long[] arg0) throws PersistenceException {
        return null;
    }

    public Project[] getAllProjects(Long userId, ProjectStatus status, ProjectCategory[] categories, boolean my, boolean hasManagerRole) throws PersistenceException {
        return null;
    }

    /**
     * Not used.
     *
     * @param arg0
     *            filter
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public Project[] searchProjects(Filter arg0) throws PersistenceException {
        return null;
    }

    /**
     * Not used.
     *
     * @param arg0
     *            project id
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public Project[] getUserProjects(long arg0) throws PersistenceException {
        return null;
    }

    /**
     * Not used.
     *
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        return null;
    }

    /**
     * Not used.
     *
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        return null;
    }

    /**
     * Not used.
     *
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        return null;
    }

    /**
     * Not used.
     *
     * @return always null
     * @throws PersistenceException
     *             not thrown
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        return null;
    }

    @Override
    public Project[] getProjectsByDirectProjectId(long directProjectId) throws PersistenceException {
        return new Project[0];
    }

    @Override
    public FileType[] getProjectFileTypes(long projectId) throws PersistenceException {
        return new FileType[0];
    }

    @Override
    public void updateProjectFileTypes(long projectId, List<FileType> fileTypes, String operator) throws PersistenceException {

    }

    @Override
    public Prize[] getProjectPrizes(long projectId) throws PersistenceException {
        return new Prize[0];
    }

    @Override
    public void updateProjectPrizes(long projectId, List<Prize> prizes, String operator) throws PersistenceException {

    }

    @Override
    public FileType createFileType(FileType fileType, String operator) throws PersistenceException {
        return null;
    }

    @Override
    public void updateFileType(FileType fileType, String operator) throws PersistenceException {

    }

    @Override
    public void removeFileType(FileType fileType, String operator) throws PersistenceException {

    }

    @Override
    public FileType[] getAllFileTypes() throws PersistenceException {
        return new FileType[0];
    }

    @Override
    public PrizeType[] getPrizeTypes() throws PersistenceException {
        return new PrizeType[0];
    }

    @Override
    public Prize createPrize(Prize prize, String operator) throws PersistenceException {
        return null;
    }

    @Override
    public void updatePrize(Prize prize, String operator) throws PersistenceException {

    }

    @Override
    public void removePrize(Prize prize, String operator) throws PersistenceException {

    }

    @Override
    public ProjectStudioSpecification createProjectStudioSpecification(ProjectStudioSpecification spec, String operator) throws PersistenceException {
        return null;
    }

    @Override
    public void updateProjectStudioSpecification(ProjectStudioSpecification spec, String operator) throws PersistenceException {

    }

    @Override
    public void removeProjectStudioSpecification(ProjectStudioSpecification spec, String operator) throws PersistenceException {

    }

    @Override
    public ProjectStudioSpecification getProjectStudioSpecification(long projectId) throws PersistenceException {
        return null;
    }

    @Override
    public void updateStudioSpecificationForProject(ProjectStudioSpecification spec, long projectId, String operator) throws PersistenceException {

    }

    public Project[] getProjectsByCreateDate(int arg0)
			throws PersistenceException {
		return null;
	}
}
