/*
 * Copyright (C) 2007 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.management;

import com.topcoder.onlinereview.component.grpcclient.project.ProjectServiceRpc;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class contains operations to create/update/retrieve project instances from the Informix
 * database. It also provides methods to retrieve database look up table values. It implements the
 * ProjectPersistence interface to provide a plug-in persistence for Informix database. It is used
 * by the ProjectManagerImpl class. The constructor takes a namespace parameter to initialize
 * database connection.
 *
 * <p>Note that in this class, delete operation is not supported. To delete a project, its status is
 * set to 'Deleted'. Create and update operations here work on the project and its related items as
 * well. It means creating/updating a project would involve creating/updating its properties.
 *
 * <p>This abstract class does not manage the connection itself. It contains three abstract methods
 * which should be implemented by concrete subclass to manage the connection:
 *
 * <ul>
 *   <li><code>openConnection()</code> is used to acquire and open the connection.
 *   <li><code>closeConnection()</code> is used to dispose the connection if the queries are
 *       executed successfully.
 *   <li><code>closeConnectionOnError()</code> is used to handle the error if the SQL operation
 *       fails.
 * </ul>
 *
 * The transaction handling logic should be implemented in subclasses while the <code>Statement
 * </code>s and <code>ResultSet</code>s are handled in this abstract class.
 *
 * <p>Version 1.1.3 (End Of Project Analysis Release Assembly v1.0)
 *
 * <ul>
 *   <li>Updated {@link #getAllProjectTypes()} method and relevant constant strings to populate
 *       project type entities with new <code>generic</code> property.
 *   <li>Updated {@link #getAllProjectCategories()} method and relevant constant strings to populate
 *       project type entities with new <code>generic</code> property.
 * </ul>
 *
 * <p>Updated in version 1.2. Add CRUD methods for following entities, it also maintain the
 * relationships between project and following entities.
 *
 * <ul>
 *   <li>FileType
 *   <li>Prize
 *   <li>ProjectStudioSpecification
 * </ul>
 *
 * It also adds a method to get projects by direct project id.
 *
 * <p>Sample Usage: Firstly, you need to set up the configuration according to the CS. Then, you can
 * use the component as the following codes shown:
 *
 * <pre>
 * // get the sample project object
 * Project project = TestHelper.getSampleProject1();
 *
 * persistence.createProject(project, &quot;user&quot;);
 *
 * long projectId = project.getId();
 *
 * // get projects by directProjectId
 * Project[] projects = persistence.getProjectsByDirectProjectId(1);
 *
 * FileType fileType = TestHelper.createFileType(&quot;description 1&quot;, &quot;extension 1&quot;, true, false, 1);
 *
 * // create FileType entity
 * fileType = persistence.createFileType(fileType, &quot;admin&quot;);
 *
 * // update FileType entity
 * fileType.setBundledFile(true);
 * fileType.setSort(2);
 * fileType.setExtension(&quot;extension3&quot;);
 * persistence.updateFileType(fileType, &quot;admin&quot;);
 *
 * // remove FileType entity
 * persistence.removeFileType(fileType, &quot;admin&quot;);
 *
 * fileType = TestHelper.createFileType(&quot;description 2&quot;, &quot;extension 2&quot;, true, false, 1);
 * persistence.createFileType(fileType, &quot;admin&quot;);
 *
 * // get all FileTypes
 * FileType[] fileTypes = persistence.getAllFileTypes();
 *
 * // get all FileTypes by project id
 * fileTypes = persistence.getProjectFileTypes(projectId);
 *
 * // update relationship for project and FileTypes
 * persistence.updateProjectFileTypes(1, Arrays.asList(fileTypes), &quot;admin&quot;);
 *
 * // get all PrizeTypes
 * PrizeType[] prizeTypes = persistence.getPrizeTypes();
 *
 * Prize prize = TestHelper.createPrize(1, 600.00, 5);
 *
 * // create Prize entity
 * prize = persistence.createPrize(prize, &quot;admin&quot;);
 *
 * // update Prize entity
 * prize.setPlace(2);
 * prize.setNumberOfSubmissions(10);
 * persistence.updatePrize(prize, &quot;admin&quot;);
 *
 * // remove Prize entity
 * persistence.removePrize(prize, &quot;admin&quot;);
 *
 * // get all Prizes by project id
 * Prize[] prizes = persistence.getProjectPrizes(projectId);
 *
 * // update relationship for project and Prizes
 * persistence.updateProjectPrizes(1, Arrays.asList(prizes), &quot;admin&quot;);
 *
 * ProjectStudioSpecification spec = TestHelper.
 * createProjectStudioSpecification(&quot;goal1&quot;, &quot;targetAudience&quot;,
 *     &quot;roundOneIntroduction1&quot;);
 * // create ProjectStudioSpecification entity
 * spec = persistence.createProjectStudioSpecification(spec, &quot;admin&quot;);
 *
 * // update ProjectStudioSpecification entity
 * spec.setSubmittersLockedBetweenRounds(false);
 * spec.setGoals(&quot;goal&quot;);
 * persistence.updateProjectStudioSpecification(spec, &quot;admin&quot;);
 *
 * // remove ProjectStudioSpecification entity
 * persistence.removeProjectStudioSpecification(spec, &quot;admin&quot;);
 *
 * // get all ProjectStudioSpecification by project id
 * spec = persistence.getProjectStudioSpecification(1);
 *
 * // update relationship for project and ProjectStudioSpecification
 * persistence.updateStudioSpecificationForProject(spec, 1, &quot;admin&quot;);
 * </pre>
 *
 * <p>Thread Safety: This class is thread safe because it is immutable.
 *
 * <p>Version 1.2.1 (Milestone Support Assembly 1.0) Change notes:
 *
 * <ol>
 *   <li>Replaced references to <code>prize_type_lu.description</code> column with reference to
 *       <code>prize_type_lu.prize_type_desc</code> column to match actual DB schema.
 *   <li>Updated the logic to support optional file types, prizes and spec for project.
 *   <li>Fixed the issue with connection leaking in {@link #getProjectStudioSpecification(long)}
 *       method.
 * </ol>
 *
 * <p>Version 1.2.2 (Online Review Replatforming Release 2) Change notes:
 *
 * @author tuenm, urtks, bendlund, fuyun, flytoj2ee, VolodymyrK
 * @version 1.2.4
 * @since 1.0
 */
@Slf4j
@Component
public class ProjectPersistence {

  @Autowired
  private ProjectServiceRpc projectServiceRpc;

  /**
   * Creates the project in the database using the given project instance.
   *
   * <p>The project information is stored to 'project' table, while its properties are stored in
   * 'project_info' table. The project's associating scorecards are stored in 'project_scorecard'
   * table. For the project, its properties and associating scorecards, the operator parameter is
   * used as the creation/modification user and the creation date and modification date will be the
   * current date time when the project is created.
   *
   * <p>Updated in version 1.2 (TC - Studio Replatforming Project): get sequence name and create
   * following IdGenerators.
   *
   * <ul>
   *   <li>fileTypeIdGenerator
   *   <li>prizeIdGenerator
   *   <li>studioSpecIdGenerator
   * </ul>
   *
   * @param project The project instance to be created in the database.
   * @param operator The creation user of this project.
   * @throws IllegalArgumentException if any input is null or the operator is empty string.
   * @throws PersistenceException if error occurred while accessing the database.
   * @since 1.0
   */
  public void createProject(Project project, String operator) throws PersistenceException {
    Helper.assertObjectNotNull(project, "project");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(
        new LogMessage(null, operator, "creating new project: " + project.getAllProperties())
            .toString());
    try {
      projectServiceRpc.createProject(project, operator);
    } catch (StatusRuntimeException e) {
      log.error(
          new LogMessage(null, operator, "Unable to generate id for the project.", e).toString());
      throw new PersistenceException("Unable to generate id for the project.", e);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(null, operator, "Fails to create project " + project.getAllProperties(), e)
              .toString());
      throw e;
    }
  }

  /**
   * Update the given project instance into the database.
   *
   * <p>The project information is stored to 'project' table, while its properties are stored in
   * 'project_info' table. The project's associating scorecards are stored in 'project_scorecard'
   * table. All related items in these tables will be updated. If items are removed from the
   * project, they will be deleted from the persistence. Likewise, if new items are added, they will
   * be created in the persistence. For the project, its properties and associating scorecards, the
   * operator parameter is used as the modification user and the modification date will be the
   * current date time when the project is updated. An update reason is provided with this method.
   * Update reason will be stored in the persistence for future references.
   *
   * <p>Updated in version 1.2 (TC - Studio Replatforming Project): It needs to maintain the
   * relationships between project and following entities.
   *
   * <ul>
   *   <li>FileType
   *   <li>Prize
   *   <li>ProjectStudioSpecification
   * </ul>
   *
   * @param project The project instance to be updated into the database.
   * @param reason The update reason. It will be stored in the persistence for future references.
   * @param operator The modification user of this project.
   * @throws IllegalArgumentException if any input is null or the operator is empty string. Or
   *     project id is zero.
   * @throws PersistenceException if error occurred while accessing the database.
   * @since 1.0
   */
  public void updateProject(Project project, String reason, String operator)
      throws PersistenceException {
    Helper.assertObjectNotNull(project, "project");
    Helper.assertLongPositive(project.getId(), "project id");
    Helper.assertObjectNotNull(reason, "reason");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(
        new LogMessage(project.getId(), operator, "updating project: " + project.getAllProperties())
            .toString());
    try {
      projectServiceRpc.updateProject(project, operator, reason);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(null, operator, "Fails to update project " + project.getAllProperties(), e)
              .toString());
      throw e;
    }
  }

  /**
   * Retrieves the project instance from the persistence given its id. The project instance is
   * retrieved with its related items, such as properties and scorecards.
   *
   * @return The project instance.
   * @param id The id of the project to be retrieved.
   * @throws IllegalArgumentException if the input id is less than or equal to zero.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public Project getProject(Long id) throws PersistenceException {
    Helper.assertLongPositive(id, "id");

    Project[] projects = getProjects(new Long[] {id});
    return projects.length == 0 ? null : projects[0];
  }

  /**
   * Gets Project entities by given directProjectId.
   *
   * @param directProjectId the given directProjectId to find the Projects.
   * @return the found Project entities, return empty if cannot find any.
   * @throws IllegalArgumentException if the argument is non-positive
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public Project[] getProjectsByDirectProjectId(long directProjectId) throws PersistenceException {
    Helper.assertLongPositive(directProjectId, "directProjectId");
    log.debug("get projects with the direct project id: " + directProjectId);
    try {
      List<Long> result = projectServiceRpc.getProjectIdsByDirectId(directProjectId);
      if (result.isEmpty()) {
        return new Project[0];
      }
      Long[] ids = result.toArray(new Long[0]);
      // get the project objects
      Project[] projects = getProjects(ids);
      return projects;
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(
                  null,
                  null,
                  "Fails to retrieving projects with the direct project id: " + directProjectId,
                  e)
              .toString());
      throw e;
    }
  }

  /**
   * Gets Project FileType entities by given projectId.
   *
   * @param projectId the given projectId to find the entities.
   * @return the found FileType entities, return empty if cannot find any.
   * @throws IllegalArgumentException if the argument is non-positive
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public FileType[] getProjectFileTypes(long projectId) throws PersistenceException {
    Helper.assertLongPositive(projectId, "projectId");
    log.debug("get Project file types with the project id: " + projectId);
    try {
      return projectServiceRpc.getProjectFileTypes(projectId);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(
                  null,
                  null,
                  "Fails to retrieving project file types with project id: " + projectId,
                  e)
              .toString());
      throw e;
    }
  }

  /**
   * Updates FileType entities by given projectId, it also updates the relationship between project
   * and FileType.
   *
   * @param projectId the given projectId to update the fileTypes entities.
   * @param fileTypes the given fileTypes entities to update.
   * @param operator the given audit user.
   * @throws IllegalArgumentException if projectId is non-positive or fileTypes contains null, or if
   *     operator is null or empty.
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public void updateProjectFileTypes(long projectId, List<FileType> fileTypes, String operator)
      throws PersistenceException {
    Helper.assertLongPositive(projectId, "projectId");
    Helper.assertObjectNotNull(fileTypes, "fileTypes");
    for (int i = 0; i < fileTypes.size(); i++) {
      Helper.assertObjectNotNull(fileTypes.get(i), "fileTypes[" + i + "]");
    }
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(
        new LogMessage(
                null, operator, "updating the file types for the project with id: " + projectId)
            .toString());
    try {
      projectServiceRpc.updateProjectFileTypes(projectId, fileTypes, operator);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(
                  null,
                  operator,
                  "Fails to update the file types for the project with id " + projectId,
                  e)
              .toString());
      throw e;
    }
  }

  /**
   * Gets Project Prize entities by given projectId.
   *
   * @param projectId the given projectId to find the entities.
   * @return the found Prize entities, return empty if cannot find any.
   * @throws IllegalArgumentException if projectId is non-positive
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public Prize[] getProjectPrizes(long projectId) throws PersistenceException {
    Helper.assertLongPositive(projectId, "projectId");
    log.debug("get project prizes with the project id: " + projectId);
    try {
      return projectServiceRpc.getProjectPrizes(projectId);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(
                  null, null, "Fails to retrieving project prizes with project id: " + projectId, e)
              .toString());
      throw e;
    }
  }

  /**
   * Updates Prize entities by given projectId.
   *
   * @param projectId the given projectId to update the prizes entities.
   * @param prizes the given prizes entities to update.
   * @param operator the given audit user.
   * @throws IllegalArgumentException if projectId is non-positive, prizes contains null, or if
   *     operator is null or empty.
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public void updateProjectPrizes(long projectId, List<Prize> prizes, String operator)
      throws PersistenceException {
    Helper.assertLongPositive(projectId, "projectId");
    Helper.assertObjectNotNull(prizes, "prizes");
    for (int i = 0; i < prizes.size(); i++) {
      Helper.assertObjectNotNull(prizes.get(i), "prizes[" + i + "]");
    }
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(
        new LogMessage(null, operator, "updating the prizes for the project with id: " + projectId)
            .toString());
    try {
      projectServiceRpc.updateProjectPrizes(projectId, prizes, operator);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(
                  null,
                  operator,
                  "Fails to update the prizes for the project with id " + projectId,
                  e)
              .toString());
      throw e;
    }
  }

  /**
   * Creates the given FileType entity.
   *
   * @param fileType the given fileType entity to create.
   * @param operator the given audit user.
   * @return the created fileType entity.
   * @throws IllegalArgumentException if fileType is null, or if operator is null or empty.
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public FileType createFileType(FileType fileType, String operator) throws PersistenceException {
    Helper.assertObjectNotNull(fileType, "fileType");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    // newId will contain the new generated Id for the file type
    log.debug(new LogMessage(null, operator, "creating new file type: " + fileType).toString());
    try {
      return projectServiceRpc.createFileType(fileType, operator);
    } catch (StatusRuntimeException e) {
      throw new PersistenceException("Unable to generate id for the file type.", e);
    } catch (PersistenceException e) {
      log.error(new LogMessage(null, operator, "Fails to create file type ", e).toString());
      throw e;
    }
  }

  /**
   * Updates the given FileType entity.
   *
   * @param fileType the given fileType entity to update.
   * @param operator the given audit user.
   * @throws IllegalArgumentException if fileType is null, or if operator is null or empty.
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public void updateFileType(FileType fileType, String operator) throws PersistenceException {
    Helper.assertObjectNotNull(fileType, "fileType");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(
        new LogMessage(null, operator, "updating the file type with id: " + fileType.getId())
            .toString());
    try {
      projectServiceRpc.updateFileType(fileType, operator);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(null, operator, "Fails to update file type " + fileType, e).toString());
      throw e;
    }
  }

  /**
   * Removes the given FileType entity.
   *
   * @param fileType the given fileType entity to update.
   * @param operator the given audit user.
   * @throws IllegalArgumentException if fileType is null, or if operator is null or empty.
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public void removeFileType(FileType fileType, String operator) throws PersistenceException {
    Helper.assertObjectNotNull(fileType, "fileType");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(
        new LogMessage(null, operator, "deleting the file type with id: " + fileType.getId())
            .toString());
    try {
      projectServiceRpc.deleteFileType(fileType.getId(), operator);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(null, operator, "Fails to delete file type " + fileType, e).toString());
      throw e;
    }
  }

  /**
   * Gets all FileType entities.
   *
   * @return the found FileType entities, return empty if cannot find any.
   * @since 1.2
   */
  public FileType[] getAllFileTypes() {
    log.debug(new LogMessage(null, null, "Enter getAllFileTypes method.").toString());
    try {
      return projectServiceRpc.getAllFileTypes();
    } catch (Exception e) {
      log.error(new LogMessage(null, null, "Fail to getAllFileTypes.", e).toString());
      throw e;
    }
  }

  /**
   * Gets all PrizeType entities.
   *
   * @return the found PrizeType entities, return empty if cannot find any.
   * @since 1.2
   */
  public PrizeType[] getPrizeTypes() {
    log.debug(new LogMessage(null, null, "Enter getPrizeTypes method.").toString());
    try {
      return projectServiceRpc.getAllPrizeTypes();
    } catch (Exception e) {
      log.error(new LogMessage(null, null, "Fail to getPrizeTypes.", e).toString());
      throw e;
    }
  }

  /**
   * Creates the given Prize entity.
   *
   * @param prize the given prize entity to create.
   * @param operator the given audit user.
   * @return the created prize entity.
   * @throws IllegalArgumentException if prize is null, or if operator is null or empty.
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public Prize createPrize(Prize prize, String operator) throws PersistenceException {
    Helper.assertObjectNotNull(prize, "prize");
    Helper.assertObjectNotNull(prize.getPrizeType(), "prize.prizeType");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(new LogMessage(null, operator, "creating new prize: " + prize).toString());
    try {
      return projectServiceRpc.createPrize(prize, operator);
    } catch (StatusRuntimeException e) {
      throw new PersistenceException("Unable to generate id for the prize.", e);
    } catch (PersistenceException e) {
      log.error(new LogMessage(null, operator, "Fails to create prize", e).toString());
      throw e;
    }
  }

  /**
   * Updates the given prize entity.
   *
   * @param prize the given prize entity to create.
   * @param operator the given audit user.
   * @throws IllegalArgumentException if prize is null, or if operator is null or empty.
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public void updatePrize(Prize prize, String operator) throws PersistenceException {
    Helper.assertObjectNotNull(prize, "prize");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(
        new LogMessage(null, operator, "updating the prize with id: " + prize.getId()).toString());
    // modifyDate will contain the modify_date retrieved from database.
    // modifyDate will contain the modify_date retrieved from database.
    try {
      projectServiceRpc.updatePrize(prize, operator);
    } catch (PersistenceException e) {
      log.error(new LogMessage(null, operator, "Fails to update prize " + prize, e).toString());
      throw e;
    }
  }

  /**
   * Removes the given prize entity.
   *
   * @param prize the given prize entity to create.
   * @param operator the given audit user.
   * @throws IllegalArgumentException if prize is null, or if operator is null or empty.
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public void removePrize(Prize prize, String operator) throws PersistenceException {
    Helper.assertObjectNotNull(prize, "prize");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(
        new LogMessage(null, operator, "deleting the prize with id: " + prize.getId()).toString());

    try {
      projectServiceRpc.deletePrize(prize.getId(), operator);
    } catch (PersistenceException e) {
      log.error(new LogMessage(null, operator, "Fails to delete prize " + prize, e).toString());
      throw e;
    }
  }

  /**
   * Creates the given ProjectStudioSpecification entity.
   *
   * @param spec the given ProjectStudioSpecification entity to create.
   * @param operator the given audit user.
   * @return the created spec entity
   * @throws IllegalArgumentException if spec is null, or if operator is null or empty.
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public ProjectStudioSpecification createProjectStudioSpecification(
      ProjectStudioSpecification spec, String operator) throws PersistenceException {
    Helper.assertObjectNotNull(spec, "spec");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(
        new LogMessage(null, operator, "creating new project studio specification: " + spec)
            .toString());
    try {
      return projectServiceRpc.createStudioSpec(spec, operator);
    } catch (StatusRuntimeException e) {
      throw new PersistenceException(
          "Unable to generate id for the project studio specification.", e);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(null, operator, "Fails to create project studio specification ", e)
              .toString());
      throw e;
    }
  }

  /**
   * Updates the given ProjectStudioSpecification entity.
   *
   * @param spec the given ProjectStudioSpecification entity to create.
   * @param operator the given audit user.
   * @throws IllegalArgumentException if spec is null, or if operator is null or empty.
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public void updateProjectStudioSpecification(ProjectStudioSpecification spec, String operator)
      throws PersistenceException {
    Helper.assertObjectNotNull(spec, "spec");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(
        new LogMessage(
                null,
                operator,
                "updating the project studio specification with id: " + spec.getId())
            .toString());
    try {
      projectServiceRpc.updateStudioSpec(spec, operator);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(
                  null, operator, "Fails to update project studio specification " + spec.getId(), e)
              .toString());
      throw e;
    }
  }

  /**
   * Removes the given ProjectStudioSpecification entity.
   *
   * @param spec the given ProjectStudioSpecification entity to create.
   * @param operator the given audit user.
   * @throws IllegalArgumentException if spec is null, or if operator is null or empty.
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public void removeProjectStudioSpecification(ProjectStudioSpecification spec, String operator)
      throws PersistenceException {
    Helper.assertObjectNotNull(spec, "spec");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(
        new LogMessage(
                null,
                operator,
                "deleting the project studio specification with id: " + spec.getId())
            .toString());
    try {
      projectServiceRpc.deleteStudioSpec(spec.getId(), operator);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(
                  null, operator, "Fails to delete project studio specification " + spec.getId(), e)
              .toString());
      throw e;
    }
  }

  /**
   * Gets ProjectStudioSpecification entity by given projectId.
   *
   * @param projectId the given projectId to find the entities.
   * @return the found ProjectStudioSpecification entities, return null if cannot find any.
   * @throws IllegalArgumentException if projectId is non-positive
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public ProjectStudioSpecification getProjectStudioSpecification(long projectId)
      throws PersistenceException {
    Helper.assertLongPositive(projectId, "projectId");
    log.debug("get project studio specification with the project id: " + projectId);
    try {
      return projectServiceRpc.getProjectStudioSpec(projectId);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(
                  null,
                  null,
                  "Fails to retrieving project studio specification with project id: " + projectId,
                  e)
              .toString());
      throw e;
    }
  }

  /**
   * Updates the given ProjectStudioSpecification entity for specified project id.
   *
   * @param spec the given ProjectStudioSpecification entity to update.
   * @param projectId the given project id to update.
   * @param operator the given audit user.
   * @throws IllegalArgumentException if spec is null, or projectId is non-positive or if operator
   *     is null or empty.
   * @throws PersistenceException if there are any exceptions.
   * @since 1.2
   */
  public void updateStudioSpecificationForProject(
      ProjectStudioSpecification spec, long projectId, String operator)
      throws PersistenceException {
    Helper.assertObjectNotNull(spec, "spec");
    Helper.assertLongPositive(projectId, "projectId");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(
        new LogMessage(
                null,
                operator,
                "updating the studio specification for the project with id: " + projectId)
            .toString());

    try {
      projectServiceRpc.updateProjectStudioSpec(projectId, spec, operator);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(
                  null,
                  operator,
                  "Fails to update the studio specification for the project with id " + projectId,
                  e)
              .toString());
      throw e;
    }
  }

  /**
   * Gets an array of all project property type in the persistence. The project property types are
   * stored in 'project_info_type_lu' table.
   *
   * @return An array of all scorecard assignments in the persistence.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
    log.debug(new LogMessage(null, null, "Enter getAllProjectPropertyTypes method.").toString());
    return projectServiceRpc.getAllProjectPropertyTypes();
  }

  /**
   * Retrieves an array of project instance from the persistence given their ids. The project
   * instances are retrieved with their properties.
   *
   * @param ids The ids of the projects to be retrieved.
   * @return An array of project instances.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public Project[] getProjects(Long ids[]) throws PersistenceException {
    Helper.assertObjectNotNull(ids, "ids");

    // check if ids is empty
    if (ids.length == 0) {
      throw new IllegalArgumentException("Array 'ids' should not be empty.");
    }

    // check if ids contains an id that is <= 0
    for (long id : ids) {
      if (id <= 0) {
        throw new IllegalArgumentException("Array 'ids' contains an id that is <= 0.");
      }
    }

    return projectServiceRpc.getProjects(ids);
  }

  /**
   * Gets an array of all project types in the persistence. The project types are stored in
   * 'project_type_lu' table.
   *
   * @return An array of all project types in the persistence.
   */
  public ProjectType[] getAllProjectTypes() {
    log.debug(new LogMessage(null, null, "Enter getAllProjectTypes method.").toString());
    return projectServiceRpc.getAllProjectTypes();
  }

  /**
   * Gets an array of all project categories in the persistence. The project categories are stored
   * in 'project_category_lu' table.
   *
   * @return An array of all project categories in the persistence.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
    log.debug(new LogMessage(null, null, "Enter getAllProjectCategories method.").toString());
    return projectServiceRpc.getAllProjectCategories();
  }

  /**
   * Gets an array of all project statuses in the persistence. The project statuses are stored in
   * 'project_status_lu' table.
   *
   * @return An array of all project statuses in the persistence.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
    log.debug(new LogMessage(null, null, "Enter getAllProjectStatuses method.").toString());
    return projectServiceRpc.getAllProjectStatuses();
  }

  /**
   * Retrieves an array of project types instance from the persistence.
   *
   * @param userId the user id.
   * @param status the project status.
   * @param my the my projects flag.
   * @param hasManagerRole the manager role flag.
   * @return An array of project types instances.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public List<UserProjectType> countUserProjects(
      Long userId, ProjectStatus status, boolean my, boolean hasManagerRole)
      throws PersistenceException {
    return projectServiceRpc.countUserProjects(userId, status.getId(), my, hasManagerRole);
  }

  public Project[] getAllProjects(
      Long userId,
      ProjectStatus status,
      int page,
      int perPage,
      long categoryId,
      boolean my,
      boolean hasManagerRole)
      throws PersistenceException {
    return projectServiceRpc.getAllProjects(userId, status, page, perPage, categoryId, my, hasManagerRole);
  }

  /**
   * Gets projects ids.
   *
   * @return the id array
   */
  public long[] searchProjectsForIds(String query) {
    try {
      return projectServiceRpc.searchProjectsForAutopilot();

    } catch (Exception e) {
      throw new PersistenceException("error occurs when trying to get ids.");
    }
  }
}
