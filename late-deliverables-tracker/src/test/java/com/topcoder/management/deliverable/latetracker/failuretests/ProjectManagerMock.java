/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.failuretests;

import com.topcoder.management.project.*;
import com.topcoder.search.builder.filter.Filter;

import java.util.List;


/**
 * The ProjectManager mock for failure tests.
 * @author gjw99
 * @version 1.0
 */
public class ProjectManagerMock implements ProjectManager {
    public void createProject(Project arg0, String arg1)
        throws PersistenceException, ValidationException {
    }

    public ProjectCategory[] getAllProjectCategories()
        throws PersistenceException {
        return null;
    }

    public ProjectPropertyType[] getAllProjectPropertyTypes()
        throws PersistenceException {
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

    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        return null;
    }

    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        return null;
    }

    public Project getProject(long arg0) throws PersistenceException {
        return null;
    }

    public Project[] getProjects(long[] arg0) throws PersistenceException {
        return null;
    }

    public Project[] getAllProjects(Long userId, ProjectStatus status, ProjectCategory[] categories, boolean my, boolean hasManagerRole) throws PersistenceException {
        return null;
    }

    public Project[] getProjectsByCreateDate(int arg0)
        throws PersistenceException {
        return null;
    }

    public Project[] getUserProjects(long arg0) throws PersistenceException {
        return null;
    }

    public Project[] searchProjects(Filter arg0) throws PersistenceException {
        throw new PersistenceException("failure");
    }

    public void updateProject(Project arg0, String arg1, String arg2)
        throws PersistenceException, ValidationException {
    }
}
