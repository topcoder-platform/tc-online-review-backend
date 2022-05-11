/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.failuretests;

import com.topcoder.management.project.*;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

import java.util.List;

/**
 * This is a mock class which implements the <code>ProjectPersistence</code> interface.
 *
 * @author vividmxx
 * @version 1.0
 */
public class MockProjectPersistence implements ProjectPersistence {

    /**
     * The flag indicating whether PersistenceException will be thrown when methods in this class are called.
     */
    private static boolean exception = false;

    /**
     * This constructor dose nothing.
     *
     * @param ns
     *            ignored
     */
    public MockProjectPersistence(String ns) {
    }

    /**
     * Setter for the exception field.
     *
     * @param exception
     *            the flag indicating whether PersistenceException will be thrown when methods in this class are called
     */
    public static void setException(boolean exception) {
        MockProjectPersistence.exception = exception;
    }

    /**
     * Create a project.
     *
     * @param project
     *            The project instance to be created in the database.
     * @param operator
     *            The creation user of this project.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public void createProject(Project project, String operator) throws PersistenceException {
        if (project == null) {
            throw new IllegalArgumentException("project should not be null.");
        }
        if (operator == null) {
            throw new IllegalArgumentException("operator should not be null");
        }
        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator should not be empty string");
        }
        throwException();
    }

    /**
     * Update a project.
     *
     * @param project
     *            The project instance to be updated into the database.
     * @param reason
     *            The update reason. It will be stored in the persistence for future references.
     * @param operator
     *            The modification user of this project.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public void updateProject(Project project, String reason, String operator) throws PersistenceException {
        if (project == null) {
            throw new IllegalArgumentException("project should not be null.");
        }
        if (reason == null) {
            throw new IllegalArgumentException("reason should not be null");
        }
        if (operator == null) {
            throw new IllegalArgumentException("operator should not be null");
        }
        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator should not be empty string");
        }
        throwException();
    }

    /**
     * Get a project.
     *
     * @param id
     *            The id of the project to be retrieved.
     * @return The project instance.
     * @throws IllegalArgumentException
     *             if the input id is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project getProject(long id) throws PersistenceException {
        if (id <= 0) {
            throw new IllegalArgumentException("id should be positive.");
        }
        throwException();
        return null;
    }

    /**
     * Get projects.
     *
     * @param ids
     *            The ids of the projects to be retrieved.
     * @return An array of project instances.
     * @throws IllegalArgumentException
     *             if ids empty or contain an id that is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Project[] getProjects(long[] ids) throws PersistenceException {
        if (ids == null) {
            throw new IllegalArgumentException("ids should not be null.");
        }
        if (ids.length == 0) {
            throw new IllegalArgumentException("ids should not be empty.");
        }
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] <= 0) {
                throw new IllegalArgumentException("ids contains non-positive id");
            }
        }
        throwException();
        return null;
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence. The project instances are
     * retrieved with their properties.
     * </p>
     *
     * @param userId the user id.
     * @param status the project status.
     * @param my the my projects flag.
     * @param hasManagerRole the manager role flag.
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project[] getAllProjects(Long userId, ProjectStatus status, ProjectCategory[] categories, boolean my, boolean hasManagerRole) throws PersistenceException {
        if (status == null) {
            throw new IllegalArgumentException("status should not be null.");
        }
        if (categories == null) {
            throw new IllegalArgumentException("categories should not be null.");
        }
        return null;
    }

    /**
     * Get all ProjectType.
     *
     * @return An array of all project types in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        throwException();
        return new ProjectType[0];
    }

    /**
     * Get all ProjectCategory.
     *
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        throwException();
        return new ProjectCategory[0];
    }

    /**
     * Get all ProjectStatus.
     *
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        throwException();
        return new ProjectStatus[0];
    }

    /**
     * Get all ProjectPropertyType.
     *
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        throwException();
        return new ProjectPropertyType[0];
    }

    /**
     * Throw a PersistenceException if exception flag is set to true.
     *
     * @throws PersistenceException
     *             if exception flag is set to true
     */
    private void throwException() throws PersistenceException {
        if (exception) {
            throw new PersistenceException("error occurred");
        }
    }

	public Project[] getProjects(CustomResultSet resultSet) throws PersistenceException {
		throw new IllegalStateException("not implemented");
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

    /**
     * <p>
     * Retrieves an array of project instance from the persistence whose
	 * create date is within current - days 
     * </p>
     * @param days last 'days' 
	 * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
	public Project[] getProjectsByCreateDate(int days) throws PersistenceException {
		return null;
	}
}
