package com.topcoder.onlinereview.component.project.management;

import com.topcoder.onlinereview.component.datavalidator.IntegerValidator;
import com.topcoder.onlinereview.component.datavalidator.LongValidator;
import com.topcoder.onlinereview.component.datavalidator.StringValidator;
import com.topcoder.onlinereview.component.search.SearchBuilderException;
import com.topcoder.onlinereview.component.search.SearchBundle;
import com.topcoder.onlinereview.component.search.SearchBundleManager;
import com.topcoder.onlinereview.component.search.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProjectManager {
  private static final String PROJECT_SEARCH_BUNDLE = "ProjectSearchBundle";
  @Autowired private ProjectPersistence persistence;
  @Autowired private SearchBundleManager searchBundleManager;
  private SearchBundle searchBundle;
  @Autowired private ProjectValidator validator;

  @PostConstruct
  public void postRun() {
    searchBundle = searchBundleManager.getSearchBundle(PROJECT_SEARCH_BUNDLE);
    this.setValidationMap();
  }

  public void createProject(Project project, String operator)
      throws PersistenceException, ValidationException {
    this.validator.validateProject(project);
    this.persistence.createProject(project, operator);
  }

  public void updateProject(Project project, String reason, String operator)
      throws PersistenceException, ValidationException {
    this.validator.validateProject(project);
    this.persistence.updateProject(project, reason, operator);
  }

  public Project getProject(long id) throws PersistenceException {
    return this.persistence.getProject(id);
  }

  public Project[] getProjects(long[] ids) throws PersistenceException {
    return this.persistence.getProjects(ids);
  }

  public Project[] searchProjects(Filter filter) throws PersistenceException {
    Helper.checkObjectNotNull(filter, "filter");
    try {
      var result = this.searchBundle.search(filter);
      return this.persistence.getProjects(result);
    } catch (ClassCastException | SearchBuilderException var4) {
      throw new PersistenceException("error occurs when trying to get ids.", var4);
    }
  }

  public ProjectType[] getAllProjectTypes() throws PersistenceException {
    return this.persistence.getAllProjectTypes();
  }

  public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
    return this.persistence.getAllProjectCategories();
  }

  public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
    return this.persistence.getAllProjectStatuses();
  }

  public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
    return this.persistence.getAllProjectPropertyTypes();
  }

  public Project[] getProjectsByDirectProjectId(long directProjectId) throws PersistenceException {
    return this.persistence.getProjectsByDirectProjectId(directProjectId);
  }

  public FileType[] getProjectFileTypes(long projectId) throws PersistenceException {
    return this.persistence.getProjectFileTypes(projectId);
  }

  public void updateProjectFileTypes(long projectId, List<FileType> fileTypes, String operator)
      throws PersistenceException {
    this.persistence.updateProjectFileTypes(projectId, fileTypes, operator);
  }

  public Prize[] getProjectPrizes(long projectId) throws PersistenceException {
    return this.persistence.getProjectPrizes(projectId);
  }

  public void updateProjectPrizes(long projectId, List<Prize> prizes, String operator)
      throws PersistenceException {
    this.persistence.updateProjectPrizes(projectId, prizes, operator);
  }

  public FileType createFileType(FileType fileType, String operator) throws PersistenceException {
    return this.persistence.createFileType(fileType, operator);
  }

  public void updateFileType(FileType fileType, String operator) throws PersistenceException {
    this.persistence.updateFileType(fileType, operator);
  }

  public void removeFileType(FileType fileType, String operator) throws PersistenceException {
    this.persistence.removeFileType(fileType, operator);
  }

  public FileType[] getAllFileTypes() throws PersistenceException {
    return this.persistence.getAllFileTypes();
  }

  public PrizeType[] getPrizeTypes() throws PersistenceException {
    return this.persistence.getPrizeTypes();
  }

  public Prize createPrize(Prize prize, String operator) throws PersistenceException {
    return this.persistence.createPrize(prize, operator);
  }

  public void updatePrize(Prize prize, String operator) throws PersistenceException {
    this.persistence.updatePrize(prize, operator);
  }

  public void removePrize(Prize prize, String operator) throws PersistenceException {
    this.persistence.removePrize(prize, operator);
  }

  public ProjectStudioSpecification createProjectStudioSpecification(
      ProjectStudioSpecification spec, String operator) throws PersistenceException {
    return this.persistence.createProjectStudioSpecification(spec, operator);
  }

  public void updateProjectStudioSpecification(ProjectStudioSpecification spec, String operator)
      throws PersistenceException {
    this.persistence.updateProjectStudioSpecification(spec, operator);
  }

  public void removeProjectStudioSpecification(ProjectStudioSpecification spec, String operator)
      throws PersistenceException {
    this.persistence.removeProjectStudioSpecification(spec, operator);
  }

  public ProjectStudioSpecification getProjectStudioSpecification(long projectId)
      throws PersistenceException {
    return this.persistence.getProjectStudioSpecification(projectId);
  }

  public void updateStudioSpecificationForProject(
      ProjectStudioSpecification spec, long projectId, String operator)
      throws PersistenceException {
    this.persistence.updateStudioSpecificationForProject(spec, projectId, operator);
  }

  private void setValidationMap() {
    Map validationMap = new HashMap();
    validationMap.put("ProjectTypeID", LongValidator.isPositive());
    validationMap.put("ProjectCategoryID", LongValidator.isPositive());
    validationMap.put("ProjectStatusID", LongValidator.isPositive());
    validationMap.put(
        "ProjectTypeName", StringValidator.hasLength(IntegerValidator.lessThanOrEqualTo(64)));
    validationMap.put(
        "ProjectCategoryName", StringValidator.hasLength(IntegerValidator.lessThanOrEqualTo(64)));
    validationMap.put(
        "ProjectStatusName", StringValidator.hasLength(IntegerValidator.lessThanOrEqualTo(64)));
    validationMap.put(
        "ProjectPropertyName", StringValidator.hasLength(IntegerValidator.lessThanOrEqualTo(64)));
    validationMap.put(
        "ProjectPropertyValue",
        StringValidator.hasLength(IntegerValidator.lessThanOrEqualTo(4096)));
    validationMap.put("TCDirectProjectID", LongValidator.isPositive());
    this.searchBundle.setSearchableFields(validationMap);
  }
}
