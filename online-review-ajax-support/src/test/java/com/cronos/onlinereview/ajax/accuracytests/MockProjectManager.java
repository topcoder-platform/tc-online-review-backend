/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 */
package com.cronos.onlinereview.ajax.accuracytests;

import com.topcoder.management.project.*;
import com.topcoder.search.builder.filter.Filter;

import java.util.List;


/**
 * Mock class.
 * 
 * @author assistant
 * @version 1.0
 */
public class MockProjectManager implements ProjectManager {


    public void createProject(Project project, String operator) {
    }

    public void updateProject(Project project, String operator) {
    }

    public Project getProject(long id) {
        if (id == -1) {
            return null;
        } else {
            return new MockProject(new ProjectCategory(1, "name", new ProjectType(1, "Java")),
                    new ProjectStatus(1, "Open"));
        }
        
    }

    public Project[] searchProjects(Filter filter) {
        return null;
    }

    public Project[] getUserProjects(long user) {
        return null;
    }

    public ProjectCategory[] getAllProjectCategories() {
        return null;
    }

    public ProjectStatus[] getAllProjectStatuses() {
        return null;
    }

    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        // TODO Auto-generated method stub
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

    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project[] getProjects(long[] arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateProject(Project arg0, String arg1, String arg2) throws PersistenceException, ValidationException {
        // TODO Auto-generated method stub
        
    }
}
