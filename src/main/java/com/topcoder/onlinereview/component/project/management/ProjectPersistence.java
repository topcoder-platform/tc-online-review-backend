/*
 * Copyright (C) 2007 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.management;

import com.topcoder.onlinereview.component.grpcclient.project.ProjectServiceRpc;
import com.topcoder.onlinereview.component.id.DBHelper;
import com.topcoder.onlinereview.component.id.IDGenerationException;
import com.topcoder.onlinereview.component.id.IDGenerator;
import com.topcoder.onlinereview.grpc.project.proto.ProjectPropertyProto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSql;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.component.util.CommonUtils.getBoolean;
import static com.topcoder.onlinereview.component.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.component.util.CommonUtils.getInt;
import static com.topcoder.onlinereview.component.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.component.util.CommonUtils.getString;

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

  /** Represents the sql statement to query all project types. */
  private static final String QUERY_ALL_PROJECT_TYPES_SQL =
      "SELECT " + "project_type_id, name, description, is_generic FROM project_type_lu";

  /** Represents the sql statement to query all project categories. */
  private static final String QUERY_ALL_PROJECT_CATEGORIES_SQL =
      "SELECT "
          + "category.project_category_id, category.name as category_name,"
          + "category.description as category_description, "
          + "type.project_type_id, type.name as type_name, type.description as type_description, type.is_generic "
          + "FROM project_category_lu AS category "
          + "JOIN project_type_lu AS type "
          + "ON category.project_type_id = type.project_type_id";

  /** Represents the sql statement to query all project statuses. */
  private static final String QUERY_ALL_PROJECT_STATUSES_SQL =
      "SELECT " + "project_status_id, name, description FROM project_status_lu";

  /** Represents the sql statement to query all project property types. */
  private static final String QUERY_ALL_PROJECT_PROPERTY_TYPES_SQL =
      "SELECT " + "project_info_type_id, name, description FROM project_info_type_lu";

  /** Represents the sql statement to query project properties. */
  private static final String QUERY_ONE_PROJECT_PROPERTIES_SQL =
      "SELECT "
          + "info.project_id, info_type.name, info.value "
          + "FROM project_info AS info "
          + "JOIN project_info_type_lu AS info_type "
          + "ON info.project_info_type_id=info_type.project_info_type_id "
          + "WHERE info.project_id = ?";

  /** Represents the sql statement to query projects count */
  private static final String QUERY_LIST_PROJECTS_COUNT =
      "SELECT count(p.project_id) as p_count, p.project_category_id, cat.name as cat_name, typ.project_type_id, typ.name as type_name"
          + " FROM project p"
          + " LEFT JOIN project_category_lu cat ON cat.project_category_id = p.project_category_id"
          + " LEFT JOIN project_type_lu typ ON typ.project_type_id = cat.project_type_id"
          + " WHERE p.project_status_id = ?";

  /** Represents the sql condition statement to list all projects for not logged in user */
  private static final String QUERY_LIST_PROJECTS_FOR_GUEST =
      " and not EXISTS (SELECT 1 FROM contest_eligibility WHERE is_studio = 0 and contest_id=p.project_id)";

  /** Represents the sql statement to query projects ordering */
  private static final String QUERY_LIST_PROJECTS_ORDER = " ORDER BY p.project_id DESC";

  /**
   * Represents the audit creation type.
   *
   * @since 1.1.2
   */
  private static final int AUDIT_CREATE_TYPE = 1;

  /**
   * Represents the audit deletion type.
   *
   * @since 1.1.2
   */
  private static final int AUDIT_DELETE_TYPE = 2;

  /**
   * Represents the audit update type.
   *
   * @since 1.1.2
   */
  private static final int AUDIT_UPDATE_TYPE = 3;

  /**
   * Represents the sql statement to query prize types from the prize_type_lu table.
   *
   * @since 1.2
   */
  private static final String QUERY_ALL_PRIZE_TYPES_SQL =
      "SELECT prize_type_id, prize_type_desc FROM prize_type_lu";

  /**
   * Represents the sql statement to query file types from the file_type_lu table.
   *
   * @since 1.2
   */
  private static final String QUERY_ALL_FILE_TYPES_SQL =
      "SELECT file_type_id, description, sort, image_file, "
          + "extension, bundled_file FROM file_type_lu";

  /**
   * Represents the sql statement to delete the project file types reference by the specified file
   * type id.
   *
   * @since 1.2
   */
  private static final String DELETE_PROJECT_FILE_TYPE_XREF_SQL =
      "DELETE FROM project_file_type_xref " + "WHERE file_type_id=?";
  /**
   * Represents the sql statement to delete the file types by the specified file type id.
   *
   * @since 1.2
   */
  private static final String DELETE_FILE_TYPE_SQL =
      "DELETE FROM file_type_lu WHERE file_type_id=?";

  /**
   * Represents the sql statement to set studio specification id for project table with the
   * specified project id.
   *
   * @since 1.2
   */
  private static final String SET_PROJECT_STUDIO_SPEC_WITH_PROJECT_SQL =
      "UPDATE project SET project_studio_spec_id=" + "? WHERE project.project_id=";

  /**
   * Represents the sql statement to query project id for project table with the specified file type
   * id.
   *
   * @since 1.2
   */
  private static final String QUERY_PROJECT_IDS_WITH_FILE_TYPE_SQL =
      "SELECT DISTINCT project_id FROM project_file_type_xref WHERE file_type_id=";

  /** Represents the sql condition statement to list user's projects */
  private static final String QUERY_LIST_PROJECTS_FOR_USER =
      " and EXISTS (SELECT 1 FROM resource r WHERE r.project_id=p.project_id and r.user_id = ?)";

  /** Represents the sql condition statement to list all projects for logged in user */
  private static final String QUERY_LIST_PROJECTS_ALL_FOR_USER =
      " and (EXISTS (SELECT 1 FROM resource r WHERE r.project_id=p.project_id and r.user_id = ?)"
          + " or not EXISTS (SELECT 1 FROM contest_eligibility WHERE is_studio = 0 and contest_id=p.project_id))";

  /** Represents the sql statement to query projects count grouping */
  private static final String QUERY_LIST_PROJECTS_COUNT_GROUP =
      " GROUP BY p.project_category_id, cat.name, typ.project_type_id, typ.name"
          + " ORDER BY typ.name, cat.name";

  /** Represents the sql statement to query projects */
  private static final String QUERY_LIST_PROJECTS =
      "SELECT SKIP ? FIRST ? p.project_id,"
          + "(SELECT pi.value FROM project_info pi WHERE pi.project_id=p.project_id and pi.project_info_type_id=6) as project_name,"
          + "(SELECT pi.value FROM project_info pi WHERE pi.project_id=p.project_id and pi.project_info_type_id=7) as project_version,"
          + "(SELECT pi.value FROM project_info pi WHERE pi.project_id=p.project_id and pi.project_info_type_id=5) as root_catalog_id,"
          + "(SELECT pi.value FROM project_info pi WHERE pi.project_id=p.project_id and pi.project_info_type_id=23) as winner_reference_id"
          + " FROM project p"
          + " WHERE p.project_status_id = ? and p.project_category_id = ?";

  @Value("${project.persistence.project-id-sequence-name:project_id_seq}")
  private String projectIdSeqName;

  @Value("${project.persistence.project-audit-id-sequence-name:project_audit_id_seq}")
  private String projectAuditIdSeqName;

  @Value("${project.persistence.file-type-id-sequence-name:file_type_id_seq}")
  private String fileTypeIdSeqName;

  @Value("${project.persistence.prize-id-sequence-name:prize_id_seq}")
  private String prizeIdSeqName;

  @Value("${project.persistence.studio-spec-id-sequence-name:studio_spec_id_seq}")
  private String studioSpecIdSeqName;

  @Autowired private DBHelper dbHelper;

  /**
   * Represents the IDGenerator for project table. This variable is initialized in the constructor
   * and never change after that. It will be used in createProject() method to generate new Id for
   * project..
   */
  private IDGenerator projectIdGenerator;

  /**
   * Represents the IDGenerator for project audit table. This variable is initialized in the
   * constructor and never change after that. It will be used in updateProject() method to store
   * update reason.
   */
  private IDGenerator projectAuditIdGenerator;

  /**
   * Represents the IDGenerator for file_type table. This variable is initialized in the constructor
   * and never change after that. It will be used in createFileType() method to generate new Id for
   * project. It could not be null after initialized.
   *
   * @since 1.2
   */
  private IDGenerator fileTypeIdGenerator;
  /**
   * Represents the IDGenerator for prize table. This variable is initialized in the constructor and
   * never change after that. It will be used in createPrize() method to generate new Id for
   * project. It could not be null after initialized.
   *
   * @since 1.2
   */
  private IDGenerator prizeIdGenerator;
  /**
   * Represents the IDGenerator for project_studio_specification table. This variable is initialized
   * in the constructor and never change after that. It will be used in
   * createProjectStudioSpecification() method to generate new Id for project. It could not be null
   * after initialized.
   *
   * @since 1.2
   */
  private IDGenerator studioSpecIdGenerator;

  @Autowired
  @Qualifier("tcsJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @PostConstruct
  public void postRun() throws IDGenerationException {
    projectIdGenerator = new IDGenerator(projectIdSeqName, dbHelper);
    projectAuditIdGenerator = new IDGenerator(projectAuditIdSeqName, dbHelper);
    fileTypeIdGenerator = new IDGenerator(fileTypeIdSeqName, dbHelper);
    prizeIdGenerator = new IDGenerator(prizeIdSeqName, dbHelper);
    studioSpecIdGenerator = new IDGenerator(studioSpecIdSeqName, dbHelper);
  }

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
      // check whether the project id is already in the database
      if (project.getId() > 0) {
        if (projectServiceRpc.isProjectExists(project.getId())) {
          throw new PersistenceException(
              "The project with the same id [" + project.getId() + "] already exists.");
        }
      }

      // generate id for the project
      Long newId = projectIdGenerator.getNextID();
      log.debug(new LogMessage(newId, operator, "generate id for new project").toString());

      // create the project
      createProject(newId, project, operator);

      // set the file types
      createOrUpdateProjectFileTypes(newId, project.getProjectFileTypes(), operator, false);

      // set the prizes
      createOrUpdateProjectPrizes(newId, project.getPrizes(), operator, false);
      // set the project studio specification
      createOrUpdateProjectStudioSpecification(
          newId, project.getProjectStudioSpecification(), operator);
      // set the newId when no exception occurred
      project.setId(newId);
    } catch (IDGenerationException e) {
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
      // check whether the project id is already in the database
      if (!projectServiceRpc.isProjectExists(project.getId())) {
        throw new PersistenceException(
            "The project id [" + project.getId() + "] does not exist in the database.");
      }
      Date modifyDate = new Date();
      // update the project
      updateProject(project, reason, operator, modifyDate);
      // set the file types
      createOrUpdateProjectFileTypes(
          project.getId(), project.getProjectFileTypes(), operator, true);

      // set the prizes
      createOrUpdateProjectPrizes(project.getId(), project.getPrizes(), operator, true);

      // set the project studio specification
      createOrUpdateProjectStudioSpecification(
          project.getId(), project.getProjectStudioSpecification(), operator);
      // set the modification user and date when no exception
      // occurred.
      project.setModificationUser(operator);
      project.setModificationTimestamp(modifyDate);
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
      // check whether the project id is already in the database
      if (!projectServiceRpc.isProjectExists(projectId)) {
        throw new PersistenceException(
            "The project with id " + projectId + " does not exist in the database.");
      }
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
      // check whether the project id is already in the database
      if (!Helper.checkEntityExists("project", "project_id", projectId, jdbcTemplate)) {
        throw new PersistenceException(
            "The project with id " + projectId + " does not exist in the database.");
      }

      createOrUpdateProjectFileTypes(projectId, fileTypes, operator, true);

      // create project audit record into project_audit table
      createProjectAudit(projectId, "Updates the project file types", operator);

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
   * Creates or updates project file types.
   *
   * @param projectId the project id
   * @param fileTypes the file types for the project
   * @param operator the given audit user.
   * @param update true to update the project file types reference; otherwise, just create the
   *     project file types reference
   * @throws PersistenceException if any error occurs
   * @since 1.2
   */
  private void createOrUpdateProjectFileTypes(
      long projectId, List<FileType> fileTypes, String operator, boolean update)
      throws PersistenceException {
    if (fileTypes == null) {
      return;
    }

    if (update) {

      log.debug(
          "delete the project file typs reference from database with the specified project id: "
              + projectId);
      // delete the project file types reference from database with the specified project id
      projectServiceRpc.deleteProjectFileType(projectId);
    }

    for (FileType fileType : fileTypes) {
      // the file type with the specified file type id exists, just update it
      if (fileType.getId() > 0 && projectServiceRpc.isFileTypeExists(fileType.getId())) {
        updateFileType(fileType, operator);
      } else { // the file type with the specified file types id does not exist, insert it to the
        // database
        createFileType(fileType, operator);
      }

      // insert projectId and file type id into project_file_type_xref table
      log.debug(
          "insert projectId: "
              + projectId
              + " and file type id: "
              + fileType.getId()
              + " into project_file_type_xref table");

      projectServiceRpc.createProjectFileType(projectId, fileType.getId());
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
      // check whether the project id is already in the database
      if (!projectServiceRpc.isProjectExists(projectId)) {
        throw new PersistenceException(
            "The project with id " + projectId + " does not exist in the database.");
      }
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
      // check whether the project id is already in the database
      if (!Helper.checkEntityExists("project", "project_id", projectId, jdbcTemplate)) {
        throw new PersistenceException(
            "The project with id " + projectId + " does not exist in the database.");
      }
      createOrUpdateProjectPrizes(projectId, prizes, operator, true);
      // create project audit record into project_audit table
      createProjectAudit(projectId, "Updates the project prizes", operator);
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
   * Creates or updates project prizes.
   *
   * @param projectId the project id
   * @param prizes the prizes for the project
   * @param operator the given audit user.
   * @param update true to update the project prizes reference; otherwise, just create the project
   *     prizes reference
   * @throws PersistenceException if any error occurs
   * @since 1.2
   */
  private void createOrUpdateProjectPrizes(
      long projectId, List<Prize> prizes, String operator, boolean update)
      throws PersistenceException {
    if (prizes == null) {
      return;
    }

    for (Prize prize : prizes) {
      // the prize with the specified prize id exists, just update it
      if (prize.getId() > 0 && projectServiceRpc.isPrizeExists(prize.getId())) {
        updatePrize(prize, operator);
      } else { // the prize with the specified prize id does not exist, insert it to the database
        createPrize(prize, operator);
      }
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
      // check whether the file type id is already in the database
      if (fileType.getId() > 0) {
        if (projectServiceRpc.isFileTypeExists(fileType.getId())) {
          throw new PersistenceException(
              "The file type with the same id [" + fileType.getId() + "] already exists.");
        }
      }
      // generate id for the file type
      Long newId = fileTypeIdGenerator.getNextID();
      log.debug(new LogMessage(newId, operator, "generate id for new file type").toString());
      // create the file type
      log.debug("insert record into file type with id:" + newId);
      Date createDate = new Date();
      fileType.setCreationUser(operator);
      fileType.setModificationUser(operator);
      fileType.setCreationTimestamp(createDate);
      fileType.setModificationTimestamp(createDate);
      fileType.setId(newId);
      // insert the file type into database
      projectServiceRpc.createFileType(fileType);      
    } catch (IDGenerationException e) {
      throw new PersistenceException("Unable to generate id for the file type.", e);
    } catch (PersistenceException e) {
      log.error(new LogMessage(null, operator, "Fails to create file type ", e).toString());
      throw e;
    }
    return fileType;
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
    // modifyDate will contain the modify_date retrieved from database.
    Date modifyDate = new Date();
    try {
      // check whether the file type id is already in the database
      if (!projectServiceRpc.isFileTypeExists(fileType.getId())) {
        throw new PersistenceException(
            "The file type id [" + fileType.getId() + "] does not exist in the database.");
      }

      fileType.setModificationUser(operator);
      fileType.setModificationTimestamp(modifyDate);
      projectServiceRpc.updateFileType(fileType);
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
      // check whether the file type id is already in the database
      if (!Helper.checkEntityExists(
          "file_type_lu", "file_type_id", fileType.getId(), jdbcTemplate)) {
        throw new PersistenceException(
            "The file type id [" + fileType.getId() + "] does not exist in the database.");
      }
      List<Long> projectIds =
          executeSql(jdbcTemplate, QUERY_PROJECT_IDS_WITH_FILE_TYPE_SQL + fileType.getId()).stream()
              .map(m -> getLong(m, "project_id"))
              .collect(Collectors.toList());
      // delete the project file type reference from database
      Object[] queryArgs = new Object[] {fileType.getId()};
      // create project audit record into project_audit table
      auditProjects(projectIds, "Removes the project file type", operator);
      Helper.doDMLQuery(jdbcTemplate, DELETE_PROJECT_FILE_TYPE_XREF_SQL, queryArgs);
      // delete the file type from database
      Helper.doDMLQuery(jdbcTemplate, DELETE_FILE_TYPE_SQL, queryArgs);
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
      List<Map<String, Object>> result = executeSql(jdbcTemplate, QUERY_ALL_FILE_TYPES_SQL);
      // create the FileType array.
      FileType[] fileTypes = new FileType[result.size()];
      for (int i = 0; i < result.size(); ++i) {
        Map<String, Object> row = result.get(i);
        // create a new instance of FileType class
        fileTypes[i] = new FileType();
        fileTypes[i].setId(getLong(row, "file_type_id"));
        fileTypes[i].setDescription(getString(row, "description"));
        fileTypes[i].setSort(getInt(row, "sort"));
        fileTypes[i].setImageFile(getBoolean(row, "image_file"));
        fileTypes[i].setExtension(getString(row, "extension"));
        fileTypes[i].setBundledFile(getBoolean(row, "bundled_file"));
      }
      return fileTypes;
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
      List<Map<String, Object>> result = executeSql(jdbcTemplate, QUERY_ALL_PRIZE_TYPES_SQL);
      // create the PrizeType array.
      PrizeType[] prizeTypes = new PrizeType[result.size()];

      for (int i = 0; i < result.size(); ++i) {
        Map<String, Object> row = result.get(i);

        // create a new instance of PrizeType class
        prizeTypes[i] = new PrizeType();
        prizeTypes[i].setId(getLong(row, "prize_type_id"));
        prizeTypes[i].setDescription(getString(row, "prize_type_desc"));
      }
      return prizeTypes;
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
      // check whether the prize id is already in the database
      if (prize.getId() > 0) {
        if (projectServiceRpc.isPrizeExists(prize.getId())) {
          throw new PersistenceException(
              "The prize with the same id [" + prize.getId() + "] already exists.");
        }
      }

      // generate id for the prize
      Long newId = prizeIdGenerator.getNextID();
      log.debug(new LogMessage(newId, operator, "generate id for new prize").toString());
      // create the prize
      log.debug("insert record into prize with id:" + newId);
      Date createDate = new Date();
      prize.setCreationUser(operator);
      prize.setCreationTimestamp(createDate);
      prize.setModificationUser(operator);
      prize.setModificationTimestamp(createDate);
      prize.setId(newId);
      // insert the prize into database
      projectServiceRpc.createPrize(prize);
    } catch (IDGenerationException e) {
      throw new PersistenceException("Unable to generate id for the prize.", e);
    } catch (PersistenceException e) {
      log.error(new LogMessage(null, operator, "Fails to create prize", e).toString());
      throw e;
    }

    return prize;
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
    Date modifyDate = new Date();
    try {
      // check whether the prize id is already in the database
      if (!projectServiceRpc.isPrizeExists(prize.getId())) {
        throw new PersistenceException(
            "The prize id [" + prize.getId() + "] does not exist in the database.");
      }
      prize.setModificationUser(operator);
      prize.setModificationTimestamp(modifyDate);
      // insert the prize into database
      projectServiceRpc.updatePrize(prize);
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
      // check whether the prize id is already in the database
      if (!projectServiceRpc.isPrizeExists(prize.getId())) {
        throw new PersistenceException(
            "The prize id [" + prize.getId() + "] does not exist in the database.");
      }
      // delete the project prize reference from database
      List<Long> projectIds = projectServiceRpc.getPrizeProjectIds(prize.getId());
      // create project audit record into project_audit table
      auditProjects(projectIds, "Removes the project prize", operator);
      projectServiceRpc.deletePrize(prize.getId());
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
      // check whether the project studio specification id is already in the database
      if (spec.getId() > 0) {
        if (projectServiceRpc.isStudioSpecExists(spec.getId())) {
          throw new PersistenceException(
              "The project studio specification with the same id ["
                  + spec.getId()
                  + "] already exists.");
        }
      }
      // generate id for the project studio specification
      Long newId = studioSpecIdGenerator.getNextID();
      log.debug(
          new LogMessage(newId, operator, "generate id for new project studio specification")
              .toString());
      // create the project studio specification
      log.debug("insert record into project studio specification with id:" + newId);

      Date createDate = new Date();

      // insert the project studio specification into database
      projectServiceRpc.createStudioSpec(newId, spec, operator, createDate);
      spec.setCreationUser(operator);
      spec.setCreationTimestamp(createDate);
      spec.setModificationUser(operator);
      spec.setModificationTimestamp(createDate);
      // set the newId when no exception occurred
      spec.setId(newId);
      return spec;
    } catch (IDGenerationException e) {
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

    // modifyDate will contain the modify_date retrieved from database.
    Date modifyDate = new Date();
    try {
      // check whether the project studio specification id is already in the database
      if (!projectServiceRpc.isStudioSpecExists(spec.getId())) {
        throw new PersistenceException(
            "The project studio specification id ["
                + spec.getId()
                + "] does not exist in the database.");
      }
      // insert the project studio specification into database
      projectServiceRpc.updateStudioSpec(spec, operator, modifyDate);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(
                  null, operator, "Fails to update project studio specification " + spec.getId(), e)
              .toString());
      throw e;
    }

    spec.setModificationUser(operator);
    spec.setModificationTimestamp(modifyDate);
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
      // check whether the project studio specification id is already in the database
      if (!projectServiceRpc.isStudioSpecExists(spec.getId())) {
        throw new PersistenceException(
            "The project studio specification id ["
                + spec.getId()
                + "] does not exist in the database.");
      }

      // delete the project project studio specification reference from database
      List<Long> projectIds = projectServiceRpc.getStudioSpecProjectIds(spec.getId());
      // create project audit record into project_audit table
      auditProjects(projectIds, "Removes the project studio specification", operator);
      projectServiceRpc.removeStudioSpecFromProjects(spec.getId());
      // delete the project studio specification from database
      projectServiceRpc.deleteStudioSpec(spec.getId());
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(
                  null, operator, "Fails to delete project studio specification " + spec.getId(), e)
              .toString());
      throw e;
    }
  }

  /**
   * Audits the projects.
   *
   * @param rows the rows containing the projects ids
   * @param reason the reason to audit
   * @param operator the audit user
   * @throws PersistenceException if any error occurs
   * @since 1.2
   */
  private void auditProjects(List<Long> rows, String reason, String operator)
      throws PersistenceException {
    if (!rows.isEmpty()) {
      for (Long id : rows) {
        createProjectAudit(id, reason, operator);
      }
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
      // check whether the project id is already in the database
      if (!projectServiceRpc.isProjectExists(projectId)) {
        throw new PersistenceException(
            "The project with id " + projectId + " does not exist in the database.");
      }
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
      // check whether the project id is already in the database
      if (!Helper.checkEntityExists("project", "project_id", projectId, jdbcTemplate)) {
        throw new PersistenceException(
            "The project with id " + projectId + " does not exist in the database.");
      }

      createOrUpdateProjectStudioSpecification(projectId, spec, operator);

      // create project audit record into project_audit table
      createProjectAudit(projectId, "Updates the project studion specification", operator);

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
   * Creates or updates project studio specification.
   *
   * @param projectId the project id
   * @param spec the studio specification for the project
   * @param operator the given audit user
   * @throws PersistenceException if any error occurs
   * @since 1.2
   */
  private void createOrUpdateProjectStudioSpecification(
      long projectId, ProjectStudioSpecification spec, String operator)
      throws PersistenceException {
    if (spec == null) {
      return;
    }

    // the studio specification with the specified id exists, just update it
    if (spec.getId() > 0 && projectServiceRpc.isStudioSpecExists(spec.getId())) {
      updateProjectStudioSpecification(spec, operator);
    } else { // the studio specification with the specified id does not exist, insert it to the
      // database
      createProjectStudioSpecification(spec, operator);
    }

    // update the project studio specification reference for the project table
    Object[] queryArgs = new Object[] {spec.getId()};
    Helper.doDMLQuery(
        jdbcTemplate, SET_PROJECT_STUDIO_SPEC_WITH_PROJECT_SQL + projectId, queryArgs);
  }

  /**
   * Creates the project in the database using the given project instance.
   *
   * @param projectId The new generated project id
   * @param project The project instance to be created in the database.
   * @param operator The creation user of this project.
   * @throws PersistenceException if error occurred while accessing the database.
   * @since 1.0
   */
  @SuppressWarnings("rawtypes")
  private void createProject(Long projectId, Project project, String operator)
      throws PersistenceException {
    log.debug("insert record into project with id:" + projectId);
    // insert the project into database
    projectServiceRpc.createProject(projectId, project, operator);
    // get the creation date.
    Date createDate = projectServiceRpc.getProjectCreateDate(projectId);
    // set the creation/modification user and date when no exception
    // occurred
    project.setCreationUser(operator);
    project.setCreationTimestamp(createDate);
    project.setModificationUser(operator);
    project.setModificationTimestamp(createDate);
    // get the property id - property value map from the project.
    Map idValueMap = makePropertyIdPropertyValueMap(project.getAllProperties());
    // create the project properties
    createProjectProperties(projectId, project, idValueMap, operator);
  }

  /**
   * Update the given project instance into the database.
   *
   * @param project The project instance to be updated into the database.
   * @param reason The update reason. It will be stored in the persistence for future references.
   * @param operator The modification user of this project.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  @SuppressWarnings("rawtypes")
  private void updateProject(Project project, String reason, String operator, Date modifyDate)
      throws PersistenceException {
    Long projectId = project.getId();
    log.debug(
        new LogMessage(projectId, operator, "update project with projectId:" + projectId)
            .toString());
    // update the project type and project category
    projectServiceRpc.updateProject(project, operator, modifyDate);
    // update the project object so this data's correct for audit purposes
    project.setModificationUser(operator);
    project.setModificationTimestamp(modifyDate);

    // get the property id - property value map from the new project object.
    Map idValueMap = makePropertyIdPropertyValueMap(project.getAllProperties());

    // update the project properties
    updateProjectProperties(project, idValueMap, operator);

    // create project audit record into project_audit table
    createProjectAudit(projectId, reason, operator);
  }

  /**
   * Makes a property id-property value(String) map from property name-property value map.
   *
   * @param nameValueMap the property name-property value map
   * @return a property id-property value map
   * @throws PersistenceException if error occurred while accessing the database.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private Map makePropertyIdPropertyValueMap(Map nameValueMap) throws PersistenceException {
    Map idValueMap = new HashMap();
    // get the property name-property id map
    Map nameIdMap = makePropertyNamePropertyIdMap(getAllProjectPropertyTypes());
    // enumerate each property
    for (Object item : nameValueMap.entrySet()) {
      Entry entry = (Entry) item;
      // find the property id from property name
      Object propertyId = nameIdMap.get(entry.getKey());
      // check if the property id can be found
      if (propertyId == null) {
        throw new PersistenceException(
            "Unable to find ProjectPropertyType name ["
                + entry.getKey()
                + "] in project_info_type_lu table.");
      }
      // put the property id-property value(String) pair into the map
      idValueMap.put(propertyId, entry.getValue().toString());
    }
    return idValueMap;
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
    // find all project property types in the table.
    List<Map<String, Object>> result =
        executeSql(jdbcTemplate, QUERY_ALL_PROJECT_PROPERTY_TYPES_SQL);
    // create the ProjectPropertyType array.
    ProjectPropertyType[] propertyTypes = new ProjectPropertyType[result.size()];
    for (int i = 0; i < result.size(); ++i) {
      Map<String, Object> row = result.get(i);
      // create a new instance of ProjectPropertyType class
      propertyTypes[i] =
          new ProjectPropertyType(
              getLong(row, "project_info_type_id"),
              getString(row, "name"),
              getString(row, "description"));
    }
    return propertyTypes;
  }

  /**
   * Get projects from sql result.
   *
   * @param result result set from sql query
   * @return An array with project found in persistence.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public Project[] getProjects(List<Map<String, Object>> result) throws PersistenceException {
    try {
      int size = result.size();
      Project[] projects = new Project[size];
      for (int i = 0; i < size; i++) {
        Map<String, Object> row = result.get(i);
        // create the ProjectStatus object
        ProjectStatus status = new ProjectStatus(getLong(row, "project_status_id"), getString(row, "project_status_name"));
        // create the ProjectType object
        ProjectType type = new ProjectType(getLong(row, "project_type_id"), getString(row, "project_type_name"));
        // create the ProjectCategory object
        ProjectCategory category =
            new ProjectCategory(getLong(row, "project_category_id"), getString(row, "project_category_name"), type);
        // create a new instance of ProjectType class
        projects[i] = new Project(getLong(row, "project_id"), category, status);
        // assign the audit information
        projects[i].setCreationUser(getString(row, "create_user"));
        projects[i].setCreationTimestamp(getDate(row, "create_date"));
        projects[i].setModificationUser(getString(row, "modify_user"));
        projects[i].setModificationTimestamp(getDate(row, "modify_date"));
        List<Map<String, Object>> pp =
            executeSqlWithParam(
                jdbcTemplate, QUERY_ONE_PROJECT_PROPERTIES_SQL, newArrayList(getLong(row, "project_id")));
        for (Map<String, Object> pRow : pp) {
          projects[i].setProperty(getString(pRow, "name"), getString(pRow, "value"));
        }
      }
      return projects;
    } catch (Exception e) {
      throw new PersistenceException(e.getMessage(), e);
    }
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
    // create the Project array.
    Project[] projects = projectServiceRpc.getProjects(ids);
    for (int i = 0; i < projects.length; i++) {
        projects[i].setProjectFileTypes(Arrays.asList(getProjectFileTypes(projects[i].getId())));
        projects[i].setPrizes(Arrays.asList(getProjectPrizes(projects[i].getId())));
        projects[i].setProjectStudioSpecification(getProjectStudioSpecification(projects[i].getId()));
    }

    // get the Id-Project map
    Map<Long, Project> projectMap = makeIdProjectMap(projects);
    List<ProjectPropertyProto> ppResult = projectServiceRpc.getProjectsProperties(ids);

    // enumerate each row
    for (ProjectPropertyProto pp : ppResult) {
      // get the corresponding Project object
      Project project = projectMap.get(pp.getProjectId());

      // set the property to project
      project.setProperty(pp.getName(), pp.hasValue() ? pp.getValue() : null);
    }
    return projects;
  }

  /**
   * Gets an array of all project types in the persistence. The project types are stored in
   * 'project_type_lu' table.
   *
   * @return An array of all project types in the persistence.
   */
  public ProjectType[] getAllProjectTypes() {
    log.debug(new LogMessage(null, null, "Enter getAllProjectTypes method.").toString());
    // find all project types in the table.
    List<Map<String, Object>> result = executeSql(jdbcTemplate, QUERY_ALL_PROJECT_TYPES_SQL);
    // create the ProjectType array.
    ProjectType[] projectTypes = new ProjectType[result.size()];
    for (int i = 0; i < result.size(); ++i) {
      Map<String, Object> row = result.get(i);
      // create a new instance of ProjectType class
      projectTypes[i] =
          new ProjectType(
              getLong(row, "project_type_id"),
              getString(row, "name"),
              getString(row, "description"),
              getBoolean(row, "is_generic"));
    }
    return projectTypes;
  }

  /**
   * Create the project properties in the database.
   *
   * @param projectId the project's Id
   * @param project The new generated project
   * @param idValueMap The property id - property value map
   * @param operator The creation user of this project
   * @throws PersistenceException if error occurred while accessing the database.
   */
  @SuppressWarnings("rawtypes")
  private void createProjectProperties(
      Long projectId, Project project, Map idValueMap, String operator)
      throws PersistenceException {

    log.debug(
        new LogMessage(
                projectId, operator, "insert record into project_info with project id" + projectId)
            .toString());
    try {
      // enumerator each property in the idValueMap
      for (Object item : idValueMap.entrySet()) {
        Entry entry = (Entry) item;
        // insert the project property into database
        projectServiceRpc.createProjectProperty(projectId, (Long) entry.getKey(), entry.getValue().toString(), operator);
        auditProjectInfo(
            projectId,
            project,
            AUDIT_CREATE_TYPE,
            (Long) entry.getKey(),
            (String) entry.getValue());
      }
    } catch (Exception e) {
      throw new PersistenceException(
          "Unable to create prepared statement [create project property].", e);
    }
  }

  /**
   * Make an Id-Project map from Project[].
   *
   * @param projects the Id-Project array
   * @return an Id-Project map
   */
  private Map<Long, Project> makeIdProjectMap(Project[] projects) {
    Map<Long, Project> map = new HashMap<>();

    for (Project project : projects) {
      map.put(project.getId(), project);
    }
    return map;
  }

  /**
   * Update the project properties into the database.
   *
   * @param project the project object
   * @param idValueMap the property id - property value map
   * @param operator the modification user of this project
   * @throws PersistenceException if error occurred while accessing the database.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private void updateProjectProperties(Project project, Map idValueMap, String operator)
      throws PersistenceException {

    Long projectId = project.getId();

    // get old property ids and values from database
    Map<Long, String> propertyMap = getProjectPropertyIdsAndValues(projectId);

    // create a property id-property value map that contains the properties
    // to insert
    Map createIdValueMap = new HashMap();
    try {
      log.debug(
          new LogMessage(
                  projectId,
                  operator,
                  "update project, update project_info with projectId:" + projectId)
              .toString());
      // enumerator each property id in the project object
      for (Object item : idValueMap.entrySet()) {
        Entry entry = (Entry) item;

        Long propertyId = (Long) entry.getKey();

        // check if the property in the project object already exists in
        // the database
        if (propertyMap.containsKey(propertyId)) {
          // if the value hasn't been changed, we don't need to update anything
          if (!propertyMap.get(propertyId).equals((String) entry.getValue())) {
            // update the project property
            projectServiceRpc.updateProjectProperty(projectId, propertyId, entry.getValue().toString(), operator);

            auditProjectInfo(project, AUDIT_UPDATE_TYPE, propertyId, (String) entry.getValue());
          }
          propertyMap.remove(propertyId);
        } else {
          // add the entry to the createIdValueMap
          createIdValueMap.put(propertyId, entry.getValue());
        }
      }
    } catch (Exception e) {
      throw new PersistenceException(
          "Unable to create prepared statement [update project property].", e);
    }

    // create the new properties
    createProjectProperties(project.getId(), project, createIdValueMap, operator);

    // delete the remaining property ids that are not in the project object
    // any longer
    deleteProjectProperties(project, propertyMap.keySet());
  }

  /**
   * Gets all the property ids and values associated to this project.
   *
   * @param projectId The id of this project
   * @return A map that contains the property values, keyed by id
   * @throws PersistenceException if error occurred while accessing the database.
   */
  private Map<Long, String> getProjectPropertyIdsAndValues(Long projectId) {
    return projectServiceRpc.getProjectPropertyIdValue(projectId);
  }

  /**
   * Delete the project properties from the database.
   *
   * @param project the project object
   * @param propertyIdSet the Set that contains the property ids to delete
   * @throws PersistenceException if error occurred while accessing the database.
   */
  private void deleteProjectProperties(Project project, Set<Long> propertyIdSet)
      throws PersistenceException {

    Long projectId = project.getId();

    // check if the property id set is empty
    // do nothing if property id set is empty
    if (!propertyIdSet.isEmpty()) {
      log.debug(
          new LogMessage(
                  projectId, null, "delete records from project_info with projectId:" + projectId)
              .toString());

      // delete the properties whose id is in the set
      projectServiceRpc.deleteProjectProperty(projectId, propertyIdSet);

      for (Long id : propertyIdSet) {
        auditProjectInfo(project, AUDIT_DELETE_TYPE, id, null);
      }
    }
  }

  /**
   * Create a project audit record into the database.
   *
   * @param projectId The id of the project
   * @param reason The update reason
   * @param operator the modification user of this project
   * @throws PersistenceException if error occurred while accessing the database.
   */
  private void createProjectAudit(Long projectId, String reason, String operator)
      throws PersistenceException {

    Long auditId;
    try {
      // generate a new audit id
      auditId = projectAuditIdGenerator.getNextID();
      log.debug(
          new LogMessage(projectId, operator, "generate new id for the project audit.").toString());
    } catch (IDGenerationException e) {
      throw new PersistenceException("Unable to generate id for project.", e);
    }
    log.debug("insert record into project_audit with projectId:" + projectId);
    // insert the update reason information to project_audit table
    projectServiceRpc.auditProject(auditId, projectId, reason, operator);
  }

  /**
   * Make a property name - property id map from ProjectPropertyType[].
   *
   * @param propertyTypes the ProjectPropertyType array
   * @return a property name - property id map
   * @throws PersistenceException if duplicate property type names are found
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private Map makePropertyNamePropertyIdMap(ProjectPropertyType[] propertyTypes)
      throws PersistenceException {
    Map map = new HashMap();

    // enumerate each property type
    for (ProjectPropertyType propertyType : propertyTypes) {

      // check if the property name already exists
      if (map.containsKey(propertyType.getName())) {
        throw new PersistenceException(
            "Duplicate project property type names ["
                + propertyType.getName()
                + "] found in project_info_type_lu table.");
      }

      // put the name-id pair to the map
      map.put(propertyType.getName(), propertyType.getId());
    }
    return map;
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
    // find all project categories in the table.
    List<Map<String, Object>> result = executeSql(jdbcTemplate, QUERY_ALL_PROJECT_CATEGORIES_SQL);
    // create the ProjectCategory array.
    ProjectCategory[] projectCategories = new ProjectCategory[result.size()];
    for (int i = 0; i < projectCategories.length; ++i) {
      Map<String, Object> row = result.get(i);
      // create the ProjectType object
      ProjectType type =
          new ProjectType(
              getLong(row, "project_type_id"),
              getString(row, "type_name"),
              getString(row, "type_description"),
              getBoolean(row, "is_generic"));
      // create a new instance of ProjectCategory class
      projectCategories[i] =
          new ProjectCategory(
              getLong(row, "project_category_id"),
              getString(row, "category_name"),
              getString(row, "category_description"),
              type);
    }
    return projectCategories;
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
    // find all project statuses in the table.
    List<Map<String, Object>> result = executeSql(jdbcTemplate, QUERY_ALL_PROJECT_STATUSES_SQL);
    // create the ProjectStatus array.
    ProjectStatus[] projectStatuses = new ProjectStatus[result.size()];
    for (int i = 0; i < result.size(); ++i) {
      Map<String, Object> row = result.get(i);
      // create a new instance of ProjectStatus class
      projectStatuses[i] =
          new ProjectStatus(
              getLong(row, "project_status_id"),
              getString(row, "name"),
              getString(row, "description"));
    }
    return projectStatuses;
  }

  /**
   * This method will audit project information. This information is generated when most project
   * properties are inserted, deleted, or edited.
   *
   * @param projectId the id of the project being audited
   * @param project the project being audited
   * @param auditType the audit type. Can be AUDIT_CREATE_TYPE, AUDIT_DELETE_TYPE, or
   *     AUDIT_UPDATE_TYPE
   * @param projectInfoTypeId the project info type id
   * @param value the project info value that we're changing to
   * @throws PersistenceException if validation error occurs or any error occurs in the underlying
   *     layer
   * @since 1.1.2
   */
  private void auditProjectInfo(
      Long projectId, Project project, int auditType, long projectInfoTypeId, String value)
      throws PersistenceException {
    try {
        projectServiceRpc.auditProjectInfo(projectId, auditType, projectInfoTypeId, value,
                project.getModificationUser());
    } catch (Exception e) {
      throw new PersistenceException("Unable to insert project_info_audit record.", e);
    }
  }

  /**
   * This method will audit project information. This information is generated when most project
   * properties are inserted, deleted, or edited.
   *
   * @param project the project being audited
   * @param auditType the audit type. Can be AUDIT_CREATE_TYPE, AUDIT_DELETE_TYPE, or
   *     AUDIT_UPDATE_TYPE
   * @param projectInfoTypeId the project info type id
   * @param value the project info value that we're changing to
   * @throws PersistenceException if validation error occurs or any error occurs in the underlying
   *     layer
   * @since 1.1.2
   */
  private void auditProjectInfo(
      Project project, int auditType, long projectInfoTypeId, String value)
      throws PersistenceException {
    auditProjectInfo(project.getId(), project, auditType, projectInfoTypeId, value);
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
    String query = QUERY_LIST_PROJECTS_COUNT;

    List<Object> argsList = new ArrayList<>();
    argsList.add(status.getId());

    if (userId == null) {
      query = query + QUERY_LIST_PROJECTS_FOR_GUEST;
      log.debug("get all" + status.getName() + " projects count");
    } else if (my) {
      query = query + QUERY_LIST_PROJECTS_FOR_USER;
      argsList.add(userId);
      log.debug("get my projects count for user: " + userId);
    } else {
      if (!hasManagerRole) {
        query = query + QUERY_LIST_PROJECTS_ALL_FOR_USER;
        argsList.add(userId);
      }
      log.debug("get all" + status.getName() + " projects count for user: " + userId);
    }
    query = query + QUERY_LIST_PROJECTS_COUNT_GROUP;

    // get the project objects
    List<UserProjectType> projects = countUserProjects(query, argsList);
    return projects;
  }

  private List<UserProjectType> countUserProjects(String query, List<Object> args)
      throws PersistenceException {
    // find projects in the table.
    List<Map<String, Object>> rows = executeSqlWithParam(jdbcTemplate, query, args);

    // create the UserProjectType array.
    List<UserProjectType> projectTypes = new ArrayList<>();

    for (int i = 0; i < rows.size(); ++i) {
      Map<String, Object> row = rows.get(i);
      UserProjectType projectType;
      Optional<UserProjectType> projectTypeOpt =
          projectTypes.stream()
              .filter(x -> x.getId() == getLong(row, "project_type_id"))
              .findFirst();
      if (!projectTypeOpt.isPresent()) {
        projectType =
            new UserProjectType(getLong(row, "project_type_id"), getString(row, "type_name"));
        projectTypes.add(projectType);
      } else {
        projectType = projectTypeOpt.get();
      }
      projectType.addCategory(
          new UserProjectCategory(
              getLong(row, "project_category_id"),
              getString(row, "cat_name"),
              getInt(row, "p_count")));
      projectType.setCount(projectType.getCount() + getInt(row, "p_count"));
    }
    return projectTypes;
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
    String query = QUERY_LIST_PROJECTS;

    List<Object> argsList = new ArrayList<>();
    argsList.add((page - 1) * perPage);
    argsList.add(perPage);
    argsList.add(status.getId());
    argsList.add(categoryId);

    if (userId == null) {
      query = query + QUERY_LIST_PROJECTS_FOR_GUEST;
      log.debug("get all" + status.getName() + " projects");
    } else if (my) {
      query = query + QUERY_LIST_PROJECTS_FOR_USER;
      argsList.add(userId);
      log.debug("get my projects for user: " + userId);
    } else {
      if (!hasManagerRole) {
        query = query + QUERY_LIST_PROJECTS_ALL_FOR_USER;
        argsList.add(userId);
      }
      log.debug("get all" + status.getName() + " projects for user: " + userId);
    }
    query = query + QUERY_LIST_PROJECTS_ORDER;

    // get the project objects
    Project[] projects = getProjectsList(query, argsList, status);
    return projects;
  }

  /**
   * Gets projects ids.
   *
   * @return the id array
   */
  public long[] searchProjectsForIds(String query) {
    try {
      List<Map<String, Object>> result = executeSql(jdbcTemplate, query);
      // create the PrizeType array.
      long[] projectIds = new long[result.size()];

      for (int i = 0; i < result.size(); ++i) {
        Map<String, Object> row = result.get(i);
        projectIds[i] = getLong(row, "project_id");
      }
      return projectIds;
    } catch (Exception e) {
      throw new PersistenceException("error occurs when trying to get ids.");
    }
  }

  private Project[] getProjectsList(String query, List<Object> args, ProjectStatus status)
      throws PersistenceException {
    // find projects in the table.
    List<Map<String, Object>> rows = executeSqlWithParam(jdbcTemplate, query, args);
    // create the Project array.
    Project[] projects = new Project[rows.size()];
    for (int i = 0; i < rows.size(); ++i) {
      Map<String, Object> row = rows.get(i);
      // create a new instance of Project class
      Project project = new Project(getLong(row, "project_id"), status);
      project.setProperty("Project Name", row.get("project_name"));
      project.setProperty("Project Version", row.get("project_version"));
      project.setProperty("Root Catalog ID", row.get("root_catalog_id"));
      project.setProperty("Winner External Reference ID", row.get("winner_reference_id"));
      projects[i] = project;
    }
    return projects;
  }
}
