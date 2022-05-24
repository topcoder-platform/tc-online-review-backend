/*
 * Copyright (C) 2007 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.management;

import com.topcoder.onlinereview.component.id.DBHelper;
import com.topcoder.onlinereview.component.id.IDGenerationException;
import com.topcoder.onlinereview.component.id.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSql;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeUpdateSql;
import static com.topcoder.onlinereview.component.util.CommonUtils.getBoolean;
import static com.topcoder.onlinereview.component.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.component.util.CommonUtils.getDouble;
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

  /** Represents the sql statement to query projects. */
  private static final String QUERY_PROJECTS_SQL =
      "SELECT "
          + "project.project_id, status.project_status_id, status.name as status_name, "
          + "category.project_category_id, category.name as category_name, type.project_type_id, type.name as type_name, "
          + "project.create_user, project.create_date, project.modify_user, project.modify_date, category.description,"
          + " project.tc_direct_project_id, tcdp.name as tc_direct_project_name "
          + "FROM project JOIN project_status_lu AS status ON project.project_status_id=status.project_status_id "
          + "JOIN project_category_lu AS category ON project.project_category_id=category.project_category_id "
          + "JOIN project_type_lu AS type ON category.project_type_id=type.project_type_id "
          + "LEFT OUTER JOIN tc_direct_project AS tcdp ON tcdp.project_id=project.tc_direct_project_id "
          + "WHERE project.project_id IN ";

  /** Represents the sql statement to query project properties. */
  private static final String QUERY_PROJECT_PROPERTIES_SQL =
      "SELECT "
          + "info.project_id, info_type.name, info.value "
          + "FROM project_info AS info "
          + "JOIN project_info_type_lu AS info_type "
          + "ON info.project_info_type_id=info_type.project_info_type_id "
          + "WHERE info.project_id IN ";

  /** Represents the sql statement to query project properties. */
  private static final String QUERY_ONE_PROJECT_PROPERTIES_SQL =
      "SELECT "
          + "info.project_id, info_type.name, info.value "
          + "FROM project_info AS info "
          + "JOIN project_info_type_lu AS info_type "
          + "ON info.project_info_type_id=info_type.project_info_type_id "
          + "WHERE info.project_id = ?";

  /** Represents the sql statement to query project property ids. */
  private static final String QUERY_PROJECT_PROPERTY_IDS_SQL =
      "SELECT " + "project_info_type_id FROM project_info WHERE project_id=?";

  /** Represents the sql statement to query project property ids and values. */
  private static final String QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_SQL =
      "SELECT " + "project_info_type_id, value FROM project_info WHERE project_id=?";

  /** Represents the sql statement to create project. */
  private static final String CREATE_PROJECT_SQL =
      "INSERT INTO project "
          + "(project_id, project_status_id, project_category_id, "
          + "create_user, create_date, modify_user, modify_date, tc_direct_project_id) "
          + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT, ?)";

  /** Represents the sql statement to create project property. */
  private static final String CREATE_PROJECT_PROPERTY_SQL =
      "INSERT INTO project_info "
          + "(project_id, project_info_type_id, value, "
          + "create_user, create_date, modify_user, modify_date) "
          + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";

  /** Represents the sql statement to create project audit. */
  private static final String CREATE_PROJECT_AUDIT_SQL =
      "INSERT INTO project_audit "
          + "(project_audit_id, project_id, update_reason, "
          + "create_user, create_date, modify_user, modify_date) "
          + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";

  /** Represents the sql statement to update project. */
  private static final String UPDATE_PROJECT_SQL =
      "UPDATE project "
          + "SET project_status_id=?, project_category_id=?, modify_user=?, modify_date=?, tc_direct_project_id=? "
          + "WHERE project_id=?";

  /** Represents the sql statement to update project property. */
  private static final String UPDATE_PROJECT_PROPERTY_SQL =
      "UPDATE project_info "
          + "SET value=?, modify_user=?, modify_date=CURRENT "
          + "WHERE project_id=? AND project_info_type_id=?";

  /** Represents the sql statement to delete project properties. */
  private static final String DELETE_PROJECT_PROPERTIES_SQL =
      "DELETE FROM project_info " + "WHERE project_id=? AND project_info_type_id IN ";

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
   * Represents the SQL statement to audit project info.
   *
   * @since 1.1.2
   */
  private static final String PROJECT_INFO_AUDIT_INSERT_SQL =
      "INSERT INTO project_info_audit "
          + "(project_id, project_info_type_id, value, audit_action_type_id, action_date, action_user_id) "
          + "VALUES (?, ?, ?, ?, ?, ?)";

  /**
   * Represents the sql statement to query file types.
   *
   * @since 1.2
   */
  private static final String QUERY_FILE_TYPES_SQL =
      "SELECT "
          + "type.file_type_id, type.description, type.sort, type.image_file,"
          + " type.extension, type.bundled_file "
          + "FROM file_type_lu AS type "
          + "JOIN project_file_type_xref AS xref "
          + "ON type.file_type_id=xref.file_type_id "
          + "WHERE xref.project_id=";

  /**
   * Represents the sql statement to query prizes.
   *
   * @since 1.2
   */
  private static final String QUERY_PRIZES_SQL =
      "SELECT "
          + "prize.prize_id, prize.place, prize.prize_amount, prize.number_of_submissions, "
          + "prize_type.prize_type_id, prize_type.prize_type_desc "
          + "FROM prize AS prize "
          + "JOIN prize_type_lu AS prize_type ON prize.prize_type_id=prize_type.prize_type_id "
          + "WHERE prize.project_id=";

  /**
   * Represents the sql statement to insert prize to the prize table.
   *
   * @since 1.2
   */
  private static final String CREATE_PRIZE_SQL =
      "INSERT INTO prize "
          + "(prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, "
          + "create_user, create_date, modify_user, modify_date) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  /**
   * Represents the sql statement to update prize to the prize table.
   *
   * @since 1.2
   */
  private static final String UPDATE_PRIZE_SQL =
      "UPDATE prize "
          + "SET place=?, prize_amount=?, prize_type_id=?, number_of_submissions=?, modify_user=?, modify_date=?, "
          + "project_id=? WHERE prize_id=";

  /**
   * Represents the sql statement to delete the prize by the specified prize id.
   *
   * @since 1.2
   */
  private static final String DELETE_PRIZE_SQL = "DELETE FROM prize WHERE prize_id=?";
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
   * Represents the sql statement to update the file types by the specified file type id.
   *
   * @since 1.2
   */
  private static final String UPDATE_FILE_TYPE_SQL =
      "UPDATE file_type_lu "
          + "SET description=?, sort=?, image_file=?, extension=?, bundled_file=?, modify_user=?, modify_date=? "
          + "WHERE file_type_id=";
  /**
   * Represents the sql statement to create the file type.
   *
   * @since 1.2
   */
  private static final String CREATE_FILE_TYPE_SQL =
      "INSERT INTO file_type_lu "
          + "(file_type_id, description, sort, image_file, extension, bundled_file, "
          + "create_user, create_date, modify_user, modify_date) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  /**
   * Represents the sql statement to insert the project file types reference with the provided
   * project id.
   *
   * @since 1.2
   */
  private static final String DELETE_PROJECT_FILE_TYPES_XREF__WITH_PROJECT_ID_SQL =
      "DELETE FROM project_file_type_xref " + "WHERE project_id=?";
  /**
   * Represents the sql statement to insert the project file types reference data.
   *
   * @since 1.2
   */
  private static final String INSERT_PROJECT_FILE_TYPES_XREF_SQL =
      "INSERT INTO project_file_type_xref (project_id, file_type_id) VALUES (?, ?)";

  /**
   * Represents the sql statement to create studio specification data.
   *
   * @since 1.2
   */
  private static final String CREATE_STUDIO_SPEC_SQL =
      "INSERT INTO project_studio_specification (project_studio_spec_id, "
          + "goals, target_audience, branding_guidelines, disliked_design_websites, other_instructions, "
          + "winning_criteria, submitters_locked_between_rounds, round_one_introduction, round_two_introduction, "
          + "colors, fonts, layout_and_size, create_user, create_date, modify_user, modify_date)"
          + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  /**
   * Represents the sql statement to update studio specification data.
   *
   * @since 1.2
   */
  private static final String UPDATE_STUDIO_SPEC_SQL =
      "UPDATE project_studio_specification "
          + "SET goals=?, target_audience=?, branding_guidelines=?, disliked_design_websites=?, "
          + "other_instructions=?, winning_criteria=?, submitters_locked_between_rounds=?, "
          + "round_one_introduction=?, round_two_introduction=?, colors=?, fonts=?, "
          + "layout_and_size=?, modify_user=?, modify_date=? "
          + "WHERE project_studio_spec_id=";

  /**
   * Represents the sql statement to delete studio specification data with the specified project
   * studio specification id.
   *
   * @since 1.2
   */
  private static final String DELETE_STUDIO_SPEC_SQL =
      "DELETE FROM project_studio_specification " + "WHERE project_studio_spec_id=?";

  /**
   * Represents the sql statement to set studio specification id to null for project table with the
   * specified project studio specification id.
   *
   * @since 1.2
   */
  private static final String SET_PROJECT_STUDIO_SPEC_SQL =
      "UPDATE project SET project_studio_spec_id=" + "NULL WHERE project_studio_spec_id=?";
  /**
   * Represents the sql statement to query studio specification data with the specified project id.
   *
   * @since 1.2
   */
  private static final String QUERY_STUDIO_SPEC_SQL =
      "SELECT "
          + "spec.project_studio_spec_id, spec.goals, spec.target_audience, "
          + "spec.branding_guidelines, spec.disliked_design_websites, spec.other_instructions, "
          + "spec.winning_criteria, spec.submitters_locked_between_rounds, "
          + "spec.round_one_introduction, spec.round_two_introduction, spec.colors, "
          + "spec.fonts, spec.layout_and_size "
          + "FROM project_studio_specification AS spec JOIN project AS project "
          + "ON project.project_studio_spec_id=spec.project_studio_spec_id "
          + "WHERE project.project_id=";

  /**
   * Represents the sql statement to set studio specification id for project table with the
   * specified project id.
   *
   * @since 1.2
   */
  private static final String SET_PROJECT_STUDIO_SPEC_WITH_PROJECT_SQL =
      "UPDATE project SET project_studio_spec_id=" + "? WHERE project.project_id=";

  /**
   * Represents the sql statement to query project id for project table with the specified tc direct
   * project id.
   *
   * @since 1.2
   */
  private static final String QUERY_PROJECT_IDS_SQL =
      "SELECT DISTINCT project_id FROM project WHERE tc_direct_project_id=";

  /**
   * Represents the sql statement to query project id for project table with the specified studio
   * specification id.
   *
   * @since 1.2
   */
  private static final String QUERY_PROJECT_IDS_WITH_STUDIO_SPEC_SQL =
      "SELECT DISTINCT project_id FROM project WHERE project_studio_spec_id=";

  /**
   * Represents the sql statement to query project id for project table with the specified file type
   * id.
   *
   * @since 1.2
   */
  private static final String QUERY_PROJECT_IDS_WITH_FILE_TYPE_SQL =
      "SELECT DISTINCT project_id FROM project_file_type_xref WHERE file_type_id=";
  /**
   * Represents the sql statement to query project id for project table with the specified prize id.
   *
   * @since 1.2
   */
  private static final String QUERY_PROJECT_IDS_WITH_PRIZE_SQL =
      "SELECT project_id FROM prize WHERE prize_id=";

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
        if (Helper.checkEntityExists("project", "project_id", project.getId(), jdbcTemplate)) {
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
      if (!Helper.checkEntityExists("project", "project_id", project.getId(), jdbcTemplate)) {
        throw new PersistenceException(
            "The project id [" + project.getId() + "] does not exist in the database.");
      }
      Date modifyDate = new Date();
      // update the project
      updateProject(project, reason, operator, modifyDate);

      log.debug(
          new LogMessage(
                  project.getId(),
                  operator,
                  "execute sql:" + "SELECT modify_date " + "FROM project WHERE project_id=?")
              .toString());
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
      List<Map<String, Object>> result =
          executeSql(jdbcTemplate, QUERY_PROJECT_IDS_SQL + directProjectId);
      if (result.isEmpty()) {
        return new Project[0];
      }
      Long[] ids = new Long[result.size()];
      for (int i = 0; i < result.size(); i++) {
        ids[i] = getLong(result.get(i), "project_id");
      }
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
      if (!Helper.checkEntityExists("project", "project_id", projectId, jdbcTemplate)) {
        throw new PersistenceException(
            "The project with id " + projectId + " does not exist in the database.");
      }
      List<Map<String, Object>> result = executeSql(jdbcTemplate, QUERY_FILE_TYPES_SQL + projectId);
      FileType[] fileTypes = new FileType[result.size()];
      // enumerate each row
      for (int i = 0; i < result.size(); ++i) {
        Map<String, Object> row = result.get(i);
        FileType fileType = new FileType();
        fileType.setId(getLong(row, "file_type_id"));
        fileType.setDescription(getString(row, "description"));
        fileType.setSort(getInt(row, "sort"));
        fileType.setImageFile(getBoolean(row, "image_file"));
        fileType.setExtension(getString(row, "extension"));
        fileType.setBundledFile(getBoolean(row, "bundled_file"));
        fileTypes[i] = fileType;
      }

      return fileTypes;
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
    Object[] queryArgs;

    if (update) {

      log.debug(
          "delete the project file typs reference from database with the specified project id: "
              + projectId);
      // delete the project file types reference from database with the specified project id
      queryArgs = new Object[] {projectId};
      Helper.doDMLQuery(
          jdbcTemplate, DELETE_PROJECT_FILE_TYPES_XREF__WITH_PROJECT_ID_SQL, queryArgs);
    }

    for (FileType fileType : fileTypes) {
      // the file type with the specified file type id exists, just update it
      if (fileType.getId() > 0
          && Helper.checkEntityExists(
              "file_type_lu", "file_type_id", fileType.getId(), jdbcTemplate)) {
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

      queryArgs = new Object[] {projectId, fileType.getId()};
      Helper.doDMLQuery(jdbcTemplate, INSERT_PROJECT_FILE_TYPES_XREF_SQL, queryArgs);
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
      if (!Helper.checkEntityExists("project", "project_id", projectId, jdbcTemplate)) {
        throw new PersistenceException(
            "The project with id " + projectId + " does not exist in the database.");
      }
      List<Map<String, Object>> result = executeSql(jdbcTemplate, QUERY_PRIZES_SQL + projectId);
      Prize[] prizes = new Prize[result.size()];
      // enumerate each row
      for (int i = 0; i < result.size(); ++i) {
        Map<String, Object> row = result.get(i);

        Prize prize = new Prize();
        prize.setId(getLong(row, "prize_id"));
        prize.setProjectId(projectId);
        prize.setPlace(getInt(row, "place"));
        prize.setPrizeAmount(getDouble(row, "prize_amount"));
        prize.setNumberOfSubmissions(getInt(row, "number_of_submissions"));
        PrizeType prizeType = new PrizeType();
        prizeType.setId(getLong(row, "prize_type_id"));
        prizeType.setDescription(getString(row, "prize_type_desc"));
        prize.setPrizeType(prizeType);
        prizes[i] = prize;
      }
      return prizes;
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
      if (prize.getId() > 0
          && Helper.checkEntityExists("prize", "prize_id", prize.getId(), jdbcTemplate)) {
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
        if (Helper.checkEntityExists(
            "file_type_lu", "file_type_id", fileType.getId(), jdbcTemplate)) {
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
      // insert the file type into database
      Object[] queryArgs =
          new Object[] {
            newId,
            fileType.getDescription(),
            fileType.getSort(),
            convertBooleanToString(fileType.isImageFile()),
            fileType.getExtension(),
            convertBooleanToString(fileType.isBundledFile()),
            operator,
            createDate,
            operator,
            createDate
          };
      Helper.doDMLQuery(jdbcTemplate, CREATE_FILE_TYPE_SQL, queryArgs);
      fileType.setCreationUser(operator);
      fileType.setCreationTimestamp(createDate);
      fileType.setModificationUser(operator);
      fileType.setModificationTimestamp(createDate);

      // set the newId when no exception occurred
      fileType.setId(newId);
    } catch (IDGenerationException e) {
      throw new PersistenceException("Unable to generate id for the file type.", e);
    } catch (PersistenceException e) {
      log.error(new LogMessage(null, operator, "Fails to create file type ", e).toString());
      throw e;
    }
    return fileType;
  }

  /**
   * Converts the boolean value to a string representation. For true, we use 't'; For false, we use
   * 'f'.
   *
   * @param booleanVal the boolean value to convert
   * @return 't' if the paramter is true; otherwise, returns 'f'
   * @since 1.2
   */
  private Object convertBooleanToString(boolean booleanVal) {
    return booleanVal ? "t" : "f";
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
      if (!Helper.checkEntityExists(
          "file_type_lu", "file_type_id", fileType.getId(), jdbcTemplate)) {
        throw new PersistenceException(
            "The file type id [" + fileType.getId() + "] does not exist in the database.");
      }

      // update the file type into database
      Object[] queryArgs =
          new Object[] {
            fileType.getDescription(),
            fileType.getSort(),
            convertBooleanToString(fileType.isImageFile()),
            fileType.getExtension(),
            convertBooleanToString(fileType.isBundledFile()),
            operator,
            modifyDate
          };
      Helper.doDMLQuery(jdbcTemplate, UPDATE_FILE_TYPE_SQL + fileType.getId(), queryArgs);
    } catch (PersistenceException e) {
      log.error(
          new LogMessage(null, operator, "Fails to update file type " + fileType, e).toString());
      throw e;
    }
    fileType.setModificationUser(operator);
    fileType.setModificationTimestamp(modifyDate);
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
        if (Helper.checkEntityExists("prize", "prize_id", prize.getId(), jdbcTemplate)) {
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
      // insert the prize into database
      Object[] queryArgs =
          new Object[] {
            newId,
            prize.getProjectId(),
            (long) prize.getPlace(),
            prize.getPrizeAmount(),
            prize.getPrizeType().getId(),
            prize.getNumberOfSubmissions(),
            operator,
            createDate,
            operator,
            createDate
          };
      Helper.doDMLQuery(jdbcTemplate, CREATE_PRIZE_SQL, queryArgs);
      prize.setCreationUser(operator);
      prize.setCreationTimestamp(createDate);
      prize.setModificationUser(operator);
      prize.setModificationTimestamp(createDate);
      // set the newId when no exception occurred
      prize.setId(newId);
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
      if (!Helper.checkEntityExists("prize", "prize_id", prize.getId(), jdbcTemplate)) {
        throw new PersistenceException(
            "The prize id [" + prize.getId() + "] does not exist in the database.");
      }

      // insert the prize into database
      Object[] queryArgs =
          new Object[] {
            (long) prize.getPlace(),
            prize.getPrizeAmount(),
            prize.getPrizeType().getId(),
            prize.getNumberOfSubmissions(),
            operator,
            modifyDate,
            prize.getProjectId()
          };
      Helper.doDMLQuery(jdbcTemplate, UPDATE_PRIZE_SQL + prize.getId(), queryArgs);
      prize.setModificationUser(operator);
      prize.setModificationTimestamp(modifyDate);
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
      if (!Helper.checkEntityExists("prize", "prize_id", prize.getId(), jdbcTemplate)) {
        throw new PersistenceException(
            "The prize id [" + prize.getId() + "] does not exist in the database.");
      }
      // delete the project prize reference from database
      List<Long> projectIds =
          executeSql(jdbcTemplate, QUERY_PROJECT_IDS_WITH_PRIZE_SQL + prize.getId()).stream()
              .map(m -> getLong(m, "project_id"))
              .collect(Collectors.toList());
      // create project audit record into project_audit table
      auditProjects(projectIds, "Removes the project prize", operator);
      Object[] queryArgs = new Object[] {prize.getId()};
      // delete the prize from database
      Helper.doDMLQuery(jdbcTemplate, DELETE_PRIZE_SQL, queryArgs);
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
        if (Helper.checkEntityExists(
            "project_studio_specification", "project_studio_spec_id", spec.getId(), jdbcTemplate)) {
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

      Timestamp createDate = new Timestamp(System.currentTimeMillis());

      // insert the project studio specification into database
      Object[] queryArgs =
          new Object[] {
            newId,
            spec.getGoals(),
            spec.getTargetAudience(),
            spec.getBrandingGuidelines(),
            spec.getDislikedDesignWebSites(),
            spec.getOtherInstructions(),
            spec.getWinningCriteria(),
            spec.isSubmittersLockedBetweenRounds(),
            spec.getRoundOneIntroduction(),
            spec.getRoundTwoIntroduction(),
            spec.getColors(),
            spec.getFonts(),
            spec.getLayoutAndSize(),
            operator,
            createDate,
            operator,
            createDate
          };
      Helper.doDMLQuery(jdbcTemplate, CREATE_STUDIO_SPEC_SQL, queryArgs);
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
      if (!Helper.checkEntityExists(
          "project_studio_specification", "project_studio_spec_id", spec.getId(), jdbcTemplate)) {
        throw new PersistenceException(
            "The project studio specification id ["
                + spec.getId()
                + "] does not exist in the database.");
      }
      // insert the project studio specification into database
      Object[] queryArgs =
          new Object[] {
            spec.getGoals(),
            spec.getTargetAudience(),
            spec.getBrandingGuidelines(),
            spec.getDislikedDesignWebSites(),
            spec.getOtherInstructions(),
            spec.getWinningCriteria(),
            spec.isSubmittersLockedBetweenRounds(),
            spec.getRoundOneIntroduction(),
            spec.getRoundTwoIntroduction(),
            spec.getColors(),
            spec.getFonts(),
            spec.getLayoutAndSize(),
            operator,
            modifyDate
          };
      Helper.doDMLQuery(jdbcTemplate, UPDATE_STUDIO_SPEC_SQL + spec.getId(), queryArgs);
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
      if (!Helper.checkEntityExists(
          "project_studio_specification", "project_studio_spec_id", spec.getId(), jdbcTemplate)) {
        throw new PersistenceException(
            "The project studio specification id ["
                + spec.getId()
                + "] does not exist in the database.");
      }

      // delete the project project studio specification reference from database
      Object[] queryArgs = new Object[] {spec.getId()};
      List<Long> projectIds =
          executeSql(jdbcTemplate, QUERY_PROJECT_IDS_WITH_STUDIO_SPEC_SQL + spec.getId()).stream()
              .map(m -> getLong(m, "project_id"))
              .collect(Collectors.toList());
      // create project audit record into project_audit table
      auditProjects(projectIds, "Removes the project studio specification", operator);
      Helper.doDMLQuery(jdbcTemplate, SET_PROJECT_STUDIO_SPEC_SQL, queryArgs);
      // delete the project studio specification from database
      Helper.doDMLQuery(jdbcTemplate, DELETE_STUDIO_SPEC_SQL, queryArgs);
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
    if (rows.isEmpty()) {
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
      if (!Helper.checkEntityExists("project", "project_id", projectId, jdbcTemplate)) {
        throw new PersistenceException(
            "The project with id " + projectId + " does not exist in the database.");
      }
      List<Map<String, Object>> result =
          executeSql(jdbcTemplate, QUERY_STUDIO_SPEC_SQL + projectId);
      if (result.isEmpty()) { // no project studio specification is found, return null
        return null;
      }
      ProjectStudioSpecification studioSpec = new ProjectStudioSpecification();
      Map<String, Object> rm = result.get(0);
      // sets the properties for the studio specification
      studioSpec.setId(getLong(rm, "project_studio_spec_id"));
      studioSpec.setGoals(getString(rm, "goals"));
      studioSpec.setTargetAudience(getString(rm, "target_audience"));
      studioSpec.setBrandingGuidelines(getString(rm, "branding_guidelines"));
      studioSpec.setDislikedDesignWebSites(getString(rm, "disliked_design_websites"));
      studioSpec.setOtherInstructions(getString(rm, "other_instructions"));
      studioSpec.setWinningCriteria(getString(rm, "winning_criteria"));
      studioSpec.setSubmittersLockedBetweenRounds(
          getBoolean(rm, "submitters_locked_between_rounds"));
      studioSpec.setRoundOneIntroduction(getString(rm, "round_one_introduction"));
      studioSpec.setRoundTwoIntroduction(getString(rm, "round_two_introduction"));
      studioSpec.setColors(getString(rm, "colors"));
      studioSpec.setFonts(getString(rm, "fonts"));
      studioSpec.setLayoutAndSize(getString(rm, "layout_and_size"));
      return studioSpec;
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

    Connection conn = null;

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
    if (spec.getId() > 0
        && Helper.checkEntityExists(
            "project_studio_specification", "project_studio_spec_id", spec.getId(), jdbcTemplate)) {
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
  @SuppressWarnings("unchecked")
  private void createProject(Long projectId, Project project, String operator)
      throws PersistenceException {
    log.debug("insert record into project with id:" + projectId);
    // insert the project into database
    Object[] queryArgs =
        new Object[] {
          projectId,
          project.getProjectStatus().getId(),
          project.getProjectCategory().getId(),
          operator,
          operator,
          project.getTcDirectProjectId()
        };
    Helper.doDMLQuery(jdbcTemplate, CREATE_PROJECT_SQL, queryArgs);
    // get the creation date.
    Date createDate =
        getDate(
            executeSql(
                    jdbcTemplate, "SELECT create_date FROM project WHERE project_id=" + projectId)
                .get(0),
            "create_date");
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
  @SuppressWarnings("unchecked")
  private void updateProject(Project project, String reason, String operator, Date modifyDate)
      throws PersistenceException {
    Long projectId = project.getId();
    log.debug(
        new LogMessage(projectId, operator, "update project with projectId:" + projectId)
            .toString());
    // update the project type and project category
    Object[] queryArgs =
        new Object[] {
          project.getProjectStatus().getId(),
          project.getProjectCategory().getId(),
          operator,
          modifyDate,
          project.getTcDirectProjectId() == 0 ? null : project.getTcDirectProjectId(),
          projectId
        };
    Helper.doDMLQuery(jdbcTemplate, UPDATE_PROJECT_SQL, queryArgs);
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
  @SuppressWarnings("unchecked")
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
        ProjectStatus status = new ProjectStatus(getLong(row, "2"), getString(row, "3"));
        // create the ProjectType object
        ProjectType type = new ProjectType(getLong(row, "6"), getString(row, "7"));
        // create the ProjectCategory object
        ProjectCategory category =
            new ProjectCategory(getLong(row, "4"), getString(row, "5"), type);
        // create a new instance of ProjectType class
        projects[i] = new Project(getLong(row, "1"), category, status);
        // assign the audit information
        projects[i].setCreationUser(getString(row, "8"));
        projects[i].setCreationTimestamp(getDate(row, "9"));
        projects[i].setModificationUser(getString(row, "10"));
        projects[i].setModificationTimestamp(getDate(row, "11"));
        List<Map<String, Object>> pp =
            executeSqlWithParam(
                jdbcTemplate, QUERY_ONE_PROJECT_PROPERTIES_SQL, newArrayList(getLong(row, "1")));
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
  @SuppressWarnings("unchecked")
  public Project[] getProjects(Long ids[]) throws PersistenceException {
    Helper.assertObjectNotNull(ids, "ids");

    // check if ids is empty
    if (ids.length == 0) {
      throw new IllegalArgumentException("Array 'ids' should not be empty.");
    }

    String idstring = "";
    // check if ids contains an id that is <= 0
    for (long id : ids) {
      idstring += id + ",";
      if (id <= 0) {
        throw new IllegalArgumentException("Array 'ids' contains an id that is <= 0.");
      }
    }
    // build the id list string
    StringBuilder idListBuffer = new StringBuilder();
    idListBuffer.append('(');
    for (int i = 0; i < ids.length; ++i) {
      if (i != 0) {
        idListBuffer.append(',');
      }
      idListBuffer.append(ids[i]);
    }
    idListBuffer.append(')');
    // get the id list string
    String idList = idListBuffer.toString();

    List<Map<String, Object>> result = executeSql(jdbcTemplate, QUERY_PROJECTS_SQL + idList);

    // create the Project array.
    Project[] projects = new Project[result.size()];

    for (int i = 0; i < result.size(); ++i) {
      Map<String, Object> row = result.get(i);

      // create the ProjectStatus object
      ProjectStatus status =
          new ProjectStatus(getLong(row, "project_status_id"), getString(row, "status_name"));

      // create the ProjectType object
      ProjectType type =
          new ProjectType(getLong(row, "project_type_id"), getString(row, "type_name"));

      // create the ProjectCategory object
      ProjectCategory category =
          new ProjectCategory(
              getLong(row, "project_category_id"), getString(row, "category_name"), type);
      category.setDescription(getString(row, "description"));

      long projectId = getLong(row, "project_id");
      // create a new instance of Project class
      projects[i] = new Project(projectId, category, status);

      // assign the audit information
      projects[i].setCreationUser(getString(row, "create_user"));
      projects[i].setCreationTimestamp(getDate(row, "create_date"));
      projects[i].setModificationUser(getString(row, "modify_user"));
      projects[i].setModificationTimestamp(getDate(row, "modify_date"));

      // set the tc direct project id and name
      projects[i].setTcDirectProjectId(Optional.ofNullable(getLong(row, "tc_direct_project_id")).orElse(0L));
      projects[i].setTcDirectProjectName(getString(row, "tc_direct_project_name"));

      // set the file types
      projects[i].setProjectFileTypes(Arrays.asList(getProjectFileTypes(projectId)));

      // set the prizes
      projects[i].setPrizes(Arrays.asList(getProjectPrizes(projectId)));

      // set the studio specification
      projects[i].setProjectStudioSpecification(getProjectStudioSpecification(projectId));
    }

    // get the Id-Project map
    Map<Long, Project> projectMap = makeIdProjectMap(projects);
    List<Map<String, Object>> ppResult =
        executeSql(jdbcTemplate, QUERY_PROJECT_PROPERTIES_SQL + idList);

    // enumerate each row
    for (Map<String, Object> row : ppResult) {
      // get the corresponding Project object
      Project project = projectMap.get(getLong(row, "project_id"));

      // set the property to project
      project.setProperty(getString(row, "name"), row.get("value"));
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
  @SuppressWarnings("unchecked")
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
        Object[] queryArgs =
            new Object[] {projectId, entry.getKey(), entry.getValue(), operator, operator};
        Helper.doDMLQuery(jdbcTemplate, CREATE_PROJECT_PROPERTY_SQL, queryArgs);
        auditProjectInfo(
            projectId,
            project,
            AUDIT_CREATE_TYPE,
            (Long) entry.getKey(),
            (String) entry.getValue());
      }
    } catch (Exception e) {
      throw new PersistenceException(
          "Unable to create prepared statement [" + CREATE_PROJECT_PROPERTY_SQL + "].", e);
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
  @SuppressWarnings("unchecked")
  private void updateProjectProperties(Project project, Map idValueMap, String operator)
      throws PersistenceException {

    Long projectId = project.getId();

    // get old property ids and values from database
    Map<Long, String> propertyMap = getProjectPropertyIdsAndValues(projectId);

    // create a property id-property value map that contains the properties
    // to insert
    Map createIdValueMap = new HashMap();

    PreparedStatement preparedStatement = null;
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
            Object[] queryArgs = new Object[] {entry.getValue(), operator, projectId, propertyId};
            Helper.doDMLQuery(jdbcTemplate, UPDATE_PROJECT_PROPERTY_SQL, queryArgs);

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
          "Unable to create prepared statement [" + UPDATE_PROJECT_PROPERTY_SQL + "].", e);
    }

    // create the new properties
    createProjectProperties(project.getId(), project, createIdValueMap, operator);

    // delete the remaining property ids that are not in the project object
    // any longer
    deleteProjectProperties(project, propertyMap.keySet());
  }

  /**
   * Gets all the property ids associated to this project.
   *
   * @param projectId The id of this project
   * @return A set that contains the property ids
   * @throws PersistenceException if error occurred while accessing the database.
   */
  private Set<Long> getProjectPropertyIds(Long projectId) {
    Set<Long> idSet = new HashSet<Long>();
    // find projects in the table.
    List<Map<String, Object>> result =
        executeSqlWithParam(jdbcTemplate, QUERY_PROJECT_PROPERTY_IDS_SQL, newArrayList(projectId));

    // enumerator each row
    for (Map<String, Object> row : result) {
      // add the id to the set
      idSet.add(getLong(row, "project_info_type_id"));
    }
    return idSet;
  }

  /**
   * Gets all the property ids and values associated to this project.
   *
   * @param projectId The id of this project
   * @return A map that contains the property values, keyed by id
   * @throws PersistenceException if error occurred while accessing the database.
   */
  private Map<Long, String> getProjectPropertyIdsAndValues(Long projectId) {
    Map<Long, String> idMap = new HashMap<>();
    // find projects in the table.
    List<Map<String, Object>> result =
        executeSqlWithParam(
            jdbcTemplate, QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_SQL, newArrayList(projectId));
    // enumerator each row
    for (Map<String, Object> row : result) {
      // add the id to the map
      idMap.put(getLong(row, "project_info_type_id"), getString(row, "value"));
    }
    return idMap;
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

      // build the id list string
      StringBuilder idListBuffer = new StringBuilder();
      idListBuffer.append('(');
      int idx = 0;
      for (Long id : propertyIdSet) {
        if (idx++ != 0) {
          idListBuffer.append(',');
        }
        idListBuffer.append(id);
      }
      idListBuffer.append(')');

      log.debug(
          new LogMessage(
                  projectId, null, "delete records from project_info with projectId:" + projectId)
              .toString());

      // delete the properties whose id is in the set
      Helper.doDMLQuery(
          jdbcTemplate, DELETE_PROJECT_PROPERTIES_SQL + idListBuffer, new Object[] {projectId});

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
    Object[] queryArgs = new Object[] {auditId, projectId, reason, operator, operator};
    Helper.doDMLQuery(jdbcTemplate, CREATE_PROJECT_AUDIT_SQL, queryArgs);
  }

  /**
   * Make a property name - property id map from ProjectPropertyType[].
   *
   * @param propertyTypes the ProjectPropertyType array
   * @return a property name - property id map
   * @throws PersistenceException if duplicate property type names are found
   */
  @SuppressWarnings("unchecked")
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
      executeUpdateSql(
          jdbcTemplate,
          PROJECT_INFO_AUDIT_INSERT_SQL,
          newArrayList(
              projectId,
              projectInfoTypeId,
              value,
              auditType,
              new Date(),
              project.getModificationUser()));
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
