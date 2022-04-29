package com.topcoder.onlinereview.component.project.management;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class ProjectPersistence {
    public static final String PROJECT_ID_SEQUENCE_NAME = "project_id_seq";
    public static final String PROJECT_AUDIT_ID_SEQUENCE_NAME = "project_audit_id_seq";
    private static final String FILE_TYPE_ID_SEQUENCE_NAME = "file_type_id_seq";
    private static final String PRIZE_ID_SEQUENCE_NAME = "prize_id_seq";
    private static final String STUDIO_SPEC_ID_SEQUENCE_NAME = "studio_spec_id_seq";
    private static final String CONNECTION_NAME_PARAMETER = "ConnectionName";
    private static final String CONNECTION_FACTORY_NAMESPACE_PARAMETER = "ConnectionFactoryNS";
    private static final String PROJECT_ID_SEQUENCE_NAME_PARAMETER = "ProjectIdSequenceName";
    private static final String PROJECT_AUDIT_ID_SEQUENCE_NAME_PARAMETER = "ProjectAuditIdSequenceName";
    private static final String FILE_TYPE_ID_SEQUENCE_NAME_PARAMETER = "FileTypeIdGeneratorSequenceName";
    private static final String PRIZE_ID_SEQUENCE_NAME_PARAMETER = "PrizeIdGeneratorSequenceName";
    private static final String STUDIO_SPEC_ID_SEQUENCE_NAME_PARAMETER = "StudioSpecIdGeneratorSequenceName";
    private static final String QUERY_ALL_PROJECT_TYPES_SQL = "SELECT project_type_id, name, description, is_generic FROM project_type_lu";
    private static final DataType[] QUERY_ALL_PROJECT_TYPES_COLUMN_TYPES;
    private static final String QUERY_ALL_PROJECT_CATEGORIES_SQL = "SELECT category.project_category_id, category.name, category.description, type.project_type_id, type.name, type.description, type.is_generic FROM project_category_lu AS category JOIN project_type_lu AS type ON category.project_type_id = type.project_type_id";
    private static final DataType[] QUERY_ALL_PROJECT_CATEGORIES_COLUMN_TYPES;
    private static final String QUERY_ALL_PROJECT_STATUSES_SQL = "SELECT project_status_id, name, description FROM project_status_lu";
    private static final DataType[] QUERY_ALL_PROJECT_STATUSES_COLUMN_TYPES;
    private static final String QUERY_ALL_PROJECT_PROPERTY_TYPES_SQL = "SELECT project_info_type_id, name, description FROM project_info_type_lu";
    private static final DataType[] QUERY_ALL_PROJECT_PROPERTY_TYPES_COLUMN_TYPES;
    private static final String QUERY_PROJECTS_SQL = "SELECT project.project_id, status.project_status_id, status.name, category.project_category_id, category.name, type.project_type_id, type.name, project.create_user, project.create_date, project.modify_user, project.modify_date, category.description, project.tc_direct_project_id, tcdp.name as tc_direct_project_name FROM project JOIN project_status_lu AS status ON project.project_status_id=status.project_status_id JOIN project_category_lu AS category ON project.project_category_id=category.project_category_id JOIN project_type_lu AS type ON category.project_type_id=type.project_type_id LEFT OUTER JOIN tc_direct_project AS tcdp ON tcdp.project_id=project.tc_direct_project_id WHERE project.project_id IN ";
    private static final DataType[] QUERY_PROJECTS_COLUMN_TYPES;
    private static final String QUERY_PROJECT_PROPERTIES_SQL = "SELECT info.project_id, info_type.name, info.value FROM project_info AS info JOIN project_info_type_lu AS info_type ON info.project_info_type_id=info_type.project_info_type_id WHERE info.project_id IN ";
    private static final String QUERY_ONE_PROJECT_PROPERTIES_SQL = "SELECT info.project_id, info_type.name, info.value FROM project_info AS info JOIN project_info_type_lu AS info_type ON info.project_info_type_id=info_type.project_info_type_id WHERE info.project_id = ?";
    private static final DataType[] QUERY_PROJECT_PROPERTIES_COLUMN_TYPES;
    private static final String QUERY_PROJECT_PROPERTY_IDS_SQL = "SELECT project_info_type_id FROM project_info WHERE project_id=?";
    private static final String QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_SQL = "SELECT project_info_type_id, value FROM project_info WHERE project_id=?";
    private static final DataType[] QUERY_PROJECT_PROPERTY_IDS_COLUMN_TYPES;
    private static final DataType[] QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_COLUMN_TYPES;
    private static final String CREATE_PROJECT_SQL = "INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date, tc_direct_project_id) VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT, ?)";
    private static final String CREATE_PROJECT_PROPERTY_SQL = "INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";
    private static final String CREATE_PROJECT_AUDIT_SQL = "INSERT INTO project_audit (project_audit_id, project_id, update_reason, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";
    private static final String UPDATE_PROJECT_SQL = "UPDATE project SET project_status_id=?, project_category_id=?, modify_user=?, modify_date=?, tc_direct_project_id=? WHERE project_id=?";
    private static final String UPDATE_PROJECT_PROPERTY_SQL = "UPDATE project_info SET value=?, modify_user=?, modify_date=CURRENT WHERE project_id=? AND project_info_type_id=?";
    private static final String DELETE_PROJECT_PROPERTIES_SQL = "DELETE FROM project_info WHERE project_id=? AND project_info_type_id IN ";
    private static final int AUDIT_CREATE_TYPE = 1;
    private static final int AUDIT_DELETE_TYPE = 2;
    private static final int AUDIT_UPDATE_TYPE = 3;
    private static final String PROJECT_INFO_AUDIT_INSERT_SQL = "INSERT INTO project_info_audit (project_id, project_info_type_id, value, audit_action_type_id, action_date, action_user_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String QUERY_FILE_TYPES_SQL = "SELECT type.file_type_id, type.description, type.sort, type.image_file, type.extension, type.bundled_file FROM file_type_lu AS type JOIN project_file_type_xref AS xref ON type.file_type_id=xref.file_type_id WHERE xref.project_id=";
    private static final DataType[] QUERY_FILE_TYPES_COLUMN_TYPES;
    private static final String QUERY_PRIZES_SQL = "SELECT prize.prize_id, prize.place, prize.prize_amount, prize.number_of_submissions, prize_type.prize_type_id, prize_type.prize_type_desc FROM prize AS prize JOIN prize_type_lu AS prize_type ON prize.prize_type_id=prize_type.prize_type_id WHERE prize.project_id=";
    private static final DataType[] QUERY_PRIZES_COLUMN_TYPES;
    private static final String CREATE_PRIZE_SQL = "INSERT INTO prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PRIZE_SQL = "UPDATE prize SET place=?, prize_amount=?, prize_type_id=?, number_of_submissions=?, modify_user=?, modify_date=?, project_id=? WHERE prize_id=";
    private static final String DELETE_PRIZE_SQL = "DELETE FROM prize WHERE prize_id=?";
    private static final String QUERY_ALL_PRIZE_TYPES_SQL = "SELECT prize_type_id, prize_type_desc FROM prize_type_lu";
    private static final DataType[] QUERY_ALL_PRIZE_TYPES_COLUMN_TYPES;
    private static final String QUERY_ALL_FILE_TYPES_SQL = "SELECT file_type_id, description, sort, image_file, extension, bundled_file FROM file_type_lu";
    private static final DataType[] QUERY_ALL_FILE_TYPES_COLUMN_TYPES;
    private static final String DELETE_PROJECT_FILE_TYPE_XREF_SQL = "DELETE FROM project_file_type_xref WHERE file_type_id=?";
    private static final String DELETE_FILE_TYPE_SQL = "DELETE FROM file_type_lu WHERE file_type_id=?";
    private static final String UPDATE_FILE_TYPE_SQL = "UPDATE file_type_lu SET description=?, sort=?, image_file=?, extension=?, bundled_file=?, modify_user=?, modify_date=? WHERE file_type_id=";
    private static final String CREATE_FILE_TYPE_SQL = "INSERT INTO file_type_lu (file_type_id, description, sort, image_file, extension, bundled_file, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_PROJECT_FILE_TYPES_XREF__WITH_PROJECT_ID_SQL = "DELETE FROM project_file_type_xref WHERE project_id=?";
    private static final String INSERT_PROJECT_FILE_TYPES_XREF_SQL = "INSERT INTO project_file_type_xref (project_id, file_type_id) VALUES (?, ?)";
    private static final String CREATE_STUDIO_SPEC_SQL = "INSERT INTO project_studio_specification (project_studio_spec_id, goals, target_audience, branding_guidelines, disliked_design_websites, other_instructions, winning_criteria, submitters_locked_between_rounds, round_one_introduction, round_two_introduction, colors, fonts, layout_and_size, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_STUDIO_SPEC_SQL = "UPDATE project_studio_specification SET goals=?, target_audience=?, branding_guidelines=?, disliked_design_websites=?, other_instructions=?, winning_criteria=?, submitters_locked_between_rounds=?, round_one_introduction=?, round_two_introduction=?, colors=?, fonts=?, layout_and_size=?, modify_user=?, modify_date=? WHERE project_studio_spec_id=";
    private static final String DELETE_STUDIO_SPEC_SQL = "DELETE FROM project_studio_specification WHERE project_studio_spec_id=?";
    private static final String SET_PROJECT_STUDIO_SPEC_SQL = "UPDATE project SET project_studio_spec_id=NULL WHERE project_studio_spec_id=?";
    private static final String QUERY_STUDIO_SPEC_SQL = "SELECT spec.project_studio_spec_id, spec.goals, spec.target_audience, spec.branding_guidelines, spec.disliked_design_websites, spec.other_instructions, spec.winning_criteria, spec.submitters_locked_between_rounds, spec.round_one_introduction, spec.round_two_introduction, spec.colors, spec.fonts, spec.layout_and_size FROM project_studio_specification AS spec JOIN project AS project ON project.project_studio_spec_id=spec.project_studio_spec_id WHERE project.project_id=";
    private static final DataType[] QUERY_STUDIO_SPEC_COLUMN_TYPES;
    private static final String SET_PROJECT_STUDIO_SPEC_WITH_PROJECT_SQL = "UPDATE project SET project_studio_spec_id=? WHERE project.project_id=";
    private static final String QUERY_PROJECT_IDS_SQL = "SELECT DISTINCT project_id FROM project WHERE tc_direct_project_id=";
    private static final DataType[] QUERY_PROJECT_IDS__COLUMN_TYPES;
    private static final String QUERY_PROJECT_IDS_WITH_STUDIO_SPEC_SQL = "SELECT DISTINCT project_id FROM project WHERE project_studio_spec_id=";
    private static final String QUERY_PROJECT_IDS_WITH_FILE_TYPE_SQL = "SELECT DISTINCT project_id FROM project_file_type_xref WHERE file_type_id=";
    private static final String QUERY_PROJECT_IDS_WITH_PRIZE_SQL = "SELECT project_id FROM prize WHERE prize_id=";
    private final DBConnectionFactory factory;
    private final String connectionName;
    private final IDGenerator projectIdGenerator;
    private final IDGenerator projectAuditIdGenerator;
    private final IDGenerator fileTypeIdGenerator;
    private final IDGenerator prizeIdGenerator;
    private final IDGenerator studioSpecIdGenerator;

    protected AbstractInformixProjectPersistence(String namespace) throws ConfigurationException, PersistenceException {
        Helper.assertStringNotNullNorEmpty(namespace, "namespace");
        ConfigManager cm = ConfigManager.getInstance();
        String factoryNamespace = Helper.getConfigurationParameterValue(cm, namespace, "ConnectionFactoryNS", true, this.getLogger());

        try {
            this.factory = new DBConnectionFactoryImpl(factoryNamespace);
        } catch (Exception var15) {
            throw new ConfigurationException("Unable to create a new instance of DBConnectionFactoryImpl class from namespace [" + factoryNamespace + "].", var15);
        }

        this.connectionName = Helper.getConfigurationParameterValue(cm, namespace, "ConnectionName", false, this.getLogger());
        String projectIdSequenceName = Helper.getConfigurationParameterValue(cm, namespace, "ProjectIdSequenceName", false, this.getLogger());
        if (projectIdSequenceName == null) {
            projectIdSequenceName = "project_id_seq";
        }

        String projectAuditIdSequenceName = Helper.getConfigurationParameterValue(cm, namespace, "ProjectAuditIdSequenceName", false, this.getLogger());
        if (projectAuditIdSequenceName == null) {
            projectAuditIdSequenceName = "project_audit_id_seq";
        }

        String fileTypeIdGeneratorSequenceName = Helper.getConfigurationParameterValue(cm, namespace, "FileTypeIdGeneratorSequenceName", false, this.getLogger());
        if (fileTypeIdGeneratorSequenceName == null) {
            fileTypeIdGeneratorSequenceName = "file_type_id_seq";
        }

        String prizeIdGeneratorSequenceName = Helper.getConfigurationParameterValue(cm, namespace, "PrizeIdGeneratorSequenceName", false, this.getLogger());
        if (prizeIdGeneratorSequenceName == null) {
            prizeIdGeneratorSequenceName = "prize_id_seq";
        }

        String studioSpecIdGeneratorSequenceName = Helper.getConfigurationParameterValue(cm, namespace, "StudioSpecIdGeneratorSequenceName", false, this.getLogger());
        if (studioSpecIdGeneratorSequenceName == null) {
            studioSpecIdGeneratorSequenceName = "studio_spec_id_seq";
        }

        try {
            this.projectIdGenerator = IDGeneratorFactory.getIDGenerator(projectIdSequenceName);
        } catch (IDGenerationException var14) {
            this.getLogger().log(Level.ERROR, "The projectIdSequence [" + projectIdSequenceName + "] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '" + projectIdSequenceName + "'.", var14);
        }

        try {
            this.projectAuditIdGenerator = IDGeneratorFactory.getIDGenerator(projectAuditIdSequenceName);
        } catch (IDGenerationException var13) {
            this.getLogger().log(Level.ERROR, "The projectAuditIdSequence [" + projectAuditIdSequenceName + "] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '" + projectAuditIdSequenceName + "'.", var13);
        }

        try {
            this.fileTypeIdGenerator = IDGeneratorFactory.getIDGenerator(fileTypeIdGeneratorSequenceName);
        } catch (IDGenerationException var12) {
            this.getLogger().log(Level.ERROR, "The fileTypeIdGeneratorSequence [" + fileTypeIdGeneratorSequenceName + "] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '" + fileTypeIdGeneratorSequenceName + "'.", var12);
        }

        try {
            this.prizeIdGenerator = IDGeneratorFactory.getIDGenerator(prizeIdGeneratorSequenceName);
        } catch (IDGenerationException var11) {
            this.getLogger().log(Level.ERROR, "The prizeIdGeneratorSequence [" + prizeIdGeneratorSequenceName + "] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '" + prizeIdGeneratorSequenceName + "'.", var11);
        }

        try {
            this.studioSpecIdGenerator = IDGeneratorFactory.getIDGenerator(studioSpecIdGeneratorSequenceName);
        } catch (IDGenerationException var10) {
            this.getLogger().log(Level.ERROR, "The studioSpecIdGeneratorSequence [" + studioSpecIdGeneratorSequenceName + "] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '" + studioSpecIdGeneratorSequenceName + "'.", var10);
        }
    }

    public void createProject(Project project, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(project, "project");
        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "creating new project: " + project.getAllProperties()));

        Long newId;
        try {
            conn = this.openConnection();
            if (project.getId() > 0L && Helper.checkEntityExists("project", "project_id", project.getId(), conn)) {
                throw new PersistenceException("The project with the same id [" + project.getId() + "] already exists.");
            }

            try {
                newId = this.projectIdGenerator.getNextID();
                this.getLogger().log(Level.DEBUG, new LogMessage(newId, operator, "generate id for new project"));
            } catch (IDGenerationException var6) {
                throw new PersistenceException("Unable to generate id for the project.", var6);
            }

            this.createProject(newId, project, operator, conn);
            this.createOrUpdateProjectFileTypes(newId, project.getProjectFileTypes(), conn, operator, false);
            this.createOrUpdateProjectPrizes(newId, project.getPrizes(), conn, operator, false);
            this.createOrUpdateProjectStudioSpecification(newId, project.getProjectStudioSpecification(), conn, operator);
            this.closeConnection(conn);
        } catch (PersistenceException var7) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to create project " + project.getAllProperties(), var7));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var7;
        }

        project.setId(newId);
    }

    public void updateProject(Project project, String reason, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(project, "project");
        Helper.assertLongPositive(project.getId(), "project id");
        Helper.assertObjectNotNull(reason, "reason");
        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage(project.getId(), operator, "updating project: " + project.getAllProperties()));

        Date modifyDate;
        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("project", "project_id", project.getId(), conn)) {
                throw new PersistenceException("The project id [" + project.getId() + "] does not exist in the database.");
            }

            this.updateProject(project, reason, operator, conn);
            this.getLogger().log(Level.DEBUG, new LogMessage(project.getId(), operator, "execute sql:SELECT modify_date FROM project WHERE project_id=?"));
            modifyDate = (Date)Helper.doSingleValueQuery(conn, "SELECT modify_date FROM project WHERE project_id=?", new Object[]{project.getId()}, Helper.DATE_TYPE);
            this.createOrUpdateProjectFileTypes(project.getId(), project.getProjectFileTypes(), conn, operator, true);
            this.createOrUpdateProjectPrizes(project.getId(), project.getPrizes(), conn, operator, true);
            this.createOrUpdateProjectStudioSpecification(project.getId(), project.getProjectStudioSpecification(), conn, operator);
            this.closeConnection(conn);
        } catch (PersistenceException var7) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to update project " + project.getAllProperties(), var7));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var7;
        }

        project.setModificationUser(operator);
        project.setModificationTimestamp(modifyDate);
    }

    public Project getProject(long id) throws PersistenceException {
        Helper.assertLongPositive(id, "id");
        Project[] projects = this.getProjects(new long[]{id});
        return projects.length == 0 ? null : projects[0];
    }

    public Project[] getProjects(long[] ids) throws PersistenceException {
        Helper.assertObjectNotNull(ids, "ids");
        if (ids.length == 0) {
            throw new IllegalArgumentException("Array 'ids' should not be empty.");
        } else {
            String idstring = "";
            long[] var3 = ids;
            int var4 = ids.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                long id = var3[var5];
                idstring = idstring + id + ",";
                if (id <= 0L) {
                    throw new IllegalArgumentException("Array 'ids' contains an id that is <= 0.");
                }
            }

            Connection conn = null;
            this.getLogger().log(Level.DEBUG, "get projects with the ids: " + idstring.substring(0, idstring.length() - 1));

            try {
                conn = this.openConnection();
                Project[] projects = this.getProjects(ids, conn);
                this.closeConnection(conn);
                return projects;
            } catch (PersistenceException var8) {
                this.getLogger().log(Level.ERROR, new LogMessage((Long)null, (String)null, "Fails to retrieving projects with ids: " + idstring.substring(0, idstring.length() - 1), var8));
                if (conn != null) {
                    this.closeConnectionOnError(conn);
                }

                throw var8;
            }
        }
    }

    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, (String)null, "Enter getAllProjectTypes method."));

        try {
            conn = this.openConnection();
            ProjectType[] projectTypes = this.getAllProjectTypes(conn);
            this.closeConnection(conn);
            return projectTypes;
        } catch (PersistenceException var3) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, (String)null, "Fail to getAllProjectTypes.", var3));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var3;
        }
    }

    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, (String)null, "Enter getAllProjectCategories method."));

        try {
            conn = this.openConnection();
            ProjectCategory[] projectCategories = this.getAllProjectCategories(conn);
            this.closeConnection(conn);
            return projectCategories;
        } catch (PersistenceException var3) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, (String)null, "Fail to getAllProjectCategories.", var3));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var3;
        }
    }

    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, (String)null, "Enter getAllProjectStatuses method."));

        try {
            conn = this.openConnection();
            ProjectStatus[] projectStatuses = this.getAllProjectStatuses(conn);
            this.closeConnection(conn);
            return projectStatuses;
        } catch (PersistenceException var3) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, (String)null, "Fail to getAllProjectStatuses.", var3));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var3;
        }
    }

    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, (String)null, "Enter getAllProjectPropertyTypes method."));
        Connection conn = null;

        try {
            conn = this.openConnection();
            ProjectPropertyType[] propertyTypes = this.getAllProjectPropertyTypes(conn);
            this.closeConnection(conn);
            return propertyTypes;
        } catch (PersistenceException var3) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, (String)null, "Fail to getAllProjectPropertyTypes.", var3));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var3;
        }
    }

    public Project[] getProjectsByDirectProjectId(long directProjectId) throws PersistenceException {
        Helper.assertLongPositive(directProjectId, "directProjectId");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, "get projects with the direct project id: " + directProjectId);

        try {
            conn = this.openConnection();
            Object[][] rows = Helper.doQuery(conn, "SELECT DISTINCT project_id FROM project WHERE tc_direct_project_id=" + directProjectId, new Object[0], QUERY_PROJECT_IDS__COLUMN_TYPES);
            if (0 == rows.length) {
                return new Project[0];
            } else {
                long[] ids = new long[rows.length];

                for(int i = 0; i < rows.length; ++i) {
                    ids[i] = (Long)rows[i][0];
                }

                Project[] projects = this.getProjects(ids, conn);
                this.closeConnection(conn);
                return projects;
            }
        } catch (PersistenceException var7) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, (String)null, "Fails to retrieving projects with the direct project id: " + directProjectId, var7));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var7;
        }
    }

    public FileType[] getProjectFileTypes(long projectId) throws PersistenceException {
        Helper.assertLongPositive(projectId, "projectId");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, "get Project file types with the project id: " + projectId);

        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            } else {
                Object[][] rows = Helper.doQuery(conn, "SELECT type.file_type_id, type.description, type.sort, type.image_file, type.extension, type.bundled_file FROM file_type_lu AS type JOIN project_file_type_xref AS xref ON type.file_type_id=xref.file_type_id WHERE xref.project_id=" + projectId, new Object[0], QUERY_FILE_TYPES_COLUMN_TYPES);
                FileType[] fileTypes = new FileType[rows.length];

                for(int i = 0; i < rows.length; ++i) {
                    Object[] row = rows[i];
                    FileType fileType = new FileType();
                    fileType.setId((Long)row[0]);
                    fileType.setDescription((String)row[1]);
                    fileType.setSort(((Long)row[2]).intValue());
                    fileType.setImageFile((Boolean)row[3]);
                    fileType.setExtension((String)row[4]);
                    fileType.setBundledFile((Boolean)row[5]);
                    fileTypes[i] = fileType;
                }

                this.closeConnection(conn);
                return fileTypes;
            }
        } catch (PersistenceException var9) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, (String)null, "Fails to retrieving project file types with project id: " + projectId, var9));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var9;
        }
    }

    public void updateProjectFileTypes(long projectId, List<FileType> fileTypes, String operator) throws PersistenceException {
        Helper.assertLongPositive(projectId, "projectId");
        Helper.assertObjectNotNull(fileTypes, "fileTypes");

        for(int i = 0; i < fileTypes.size(); ++i) {
            Helper.assertObjectNotNull(fileTypes.get(i), "fileTypes[" + i + "]");
        }

        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "updating the file types for the project with id: " + projectId));

        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            } else {
                this.createOrUpdateProjectFileTypes(projectId, fileTypes, conn, operator, true);
                this.createProjectAudit(projectId, "Updates the project file types", operator, conn);
                this.closeConnection(conn);
            }
        } catch (PersistenceException var7) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to update the file types for the project with id " + projectId, var7));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var7;
        }
    }

    private void createOrUpdateProjectFileTypes(long projectId, List<FileType> fileTypes, Connection conn, String operator, boolean update) throws PersistenceException {
        if (fileTypes != null) {
            Object[] queryArgs;
            if (update) {
                this.getLogger().log(Level.DEBUG, "delete the project file typs reference from database with the specified project id: " + projectId);
                queryArgs = new Object[]{projectId};
                Helper.doDMLQuery(conn, "DELETE FROM project_file_type_xref WHERE project_id=?", queryArgs);
            }

            Iterator var8 = fileTypes.iterator();

            while(var8.hasNext()) {
                FileType fileType = (FileType)var8.next();
                if (fileType.getId() > 0L && Helper.checkEntityExists("file_type_lu", "file_type_id", fileType.getId(), conn)) {
                    this.updateFileType(fileType, operator);
                } else {
                    this.createFileType(fileType, operator);
                }

                this.getLogger().log(Level.DEBUG, "insert projectId: " + projectId + " and file type id: " + fileType.getId() + " into project_file_type_xref table");
                queryArgs = new Object[]{projectId, fileType.getId()};
                Helper.doDMLQuery(conn, "INSERT INTO project_file_type_xref (project_id, file_type_id) VALUES (?, ?)", queryArgs);
            }

        }
    }

    public Prize[] getProjectPrizes(long projectId) throws PersistenceException {
        Helper.assertLongPositive(projectId, "projectId");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, "get project prizes with the project id: " + projectId);

        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            } else {
                Object[][] rows = Helper.doQuery(conn, "SELECT prize.prize_id, prize.place, prize.prize_amount, prize.number_of_submissions, prize_type.prize_type_id, prize_type.prize_type_desc FROM prize AS prize JOIN prize_type_lu AS prize_type ON prize.prize_type_id=prize_type.prize_type_id WHERE prize.project_id=" + projectId, new Object[0], QUERY_PRIZES_COLUMN_TYPES);
                Prize[] prizes = new Prize[rows.length];

                for(int i = 0; i < rows.length; ++i) {
                    Object[] row = rows[i];
                    Prize prize = new Prize();
                    prize.setId((Long)row[0]);
                    prize.setProjectId(projectId);
                    prize.setPlace(((Long)row[1]).intValue());
                    prize.setPrizeAmount((Double)row[2]);
                    prize.setNumberOfSubmissions(((Long)row[3]).intValue());
                    PrizeType prizeType = new PrizeType();
                    prizeType.setId((Long)row[4]);
                    prizeType.setDescription((String)row[5]);
                    prize.setPrizeType(prizeType);
                    prizes[i] = prize;
                }

                this.closeConnection(conn);
                return prizes;
            }
        } catch (PersistenceException var10) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, (String)null, "Fails to retrieving project prizes with project id: " + projectId, var10));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var10;
        }
    }

    public void updateProjectPrizes(long projectId, List<Prize> prizes, String operator) throws PersistenceException {
        Helper.assertLongPositive(projectId, "projectId");
        Helper.assertObjectNotNull(prizes, "prizes");

        for(int i = 0; i < prizes.size(); ++i) {
            Helper.assertObjectNotNull(prizes.get(i), "prizes[" + i + "]");
        }

        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "updating the prizes for the project with id: " + projectId));

        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            } else {
                this.createOrUpdateProjectPrizes(projectId, prizes, conn, operator, true);
                this.createProjectAudit(projectId, "Updates the project prizes", operator, conn);
                this.closeConnection(conn);
            }
        } catch (PersistenceException var7) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to update the prizes for the project with id " + projectId, var7));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var7;
        }
    }

    private void createOrUpdateProjectPrizes(long projectId, List<Prize> prizes, Connection conn, String operator, boolean update) throws PersistenceException {
        if (prizes != null) {
            Iterator var7 = prizes.iterator();

            while(true) {
                while(var7.hasNext()) {
                    Prize prize = (Prize)var7.next();
                    if (prize.getId() > 0L && Helper.checkEntityExists("prize", "prize_id", prize.getId(), conn)) {
                        this.updatePrize(prize, operator);
                    } else {
                        this.createPrize(prize, operator);
                    }
                }

                return;
            }
        }
    }

    public FileType createFileType(FileType fileType, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(fileType, "fileType");
        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        Long newId = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "creating new file type: " + fileType));

        try {
            conn = this.openConnection();
            if (fileType.getId() > 0L && Helper.checkEntityExists("file_type_lu", "file_type_id", fileType.getId(), conn)) {
                throw new PersistenceException("The file type with the same id [" + fileType.getId() + "] already exists.");
            } else {
                try {
                    newId = this.fileTypeIdGenerator.getNextID();
                    this.getLogger().log(Level.DEBUG, new LogMessage(newId, operator, "generate id for new file type"));
                } catch (IDGenerationException var7) {
                    throw new PersistenceException("Unable to generate id for the file type.", var7);
                }

                this.getLogger().log(Level.DEBUG, "insert record into file type with id:" + newId);
                Timestamp createDate = new Timestamp(System.currentTimeMillis());
                Object[] queryArgs = new Object[]{newId, fileType.getDescription(), fileType.getSort(), this.convertBooleanToString(fileType.isImageFile()), fileType.getExtension(), this.convertBooleanToString(fileType.isBundledFile()), operator, createDate, operator, createDate};
                Helper.doDMLQuery(conn, "INSERT INTO file_type_lu (file_type_id, description, sort, image_file, extension, bundled_file, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", queryArgs);
                this.closeConnection(conn);
                fileType.setCreationUser(operator);
                fileType.setCreationTimestamp(createDate);
                fileType.setModificationUser(operator);
                fileType.setModificationTimestamp(createDate);
                fileType.setId(newId);
                return fileType;
            }
        } catch (PersistenceException var8) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to create file type " + newId, var8));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var8;
        }
    }

    private Object convertBooleanToString(boolean booleanVal) {
        return booleanVal ? "t" : "f";
    }

    public void updateFileType(FileType fileType, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(fileType, "fileType");
        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "updating the file type with id: " + fileType.getId()));
        Timestamp modifyDate = new Timestamp(System.currentTimeMillis());

        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("file_type_lu", "file_type_id", fileType.getId(), conn)) {
                throw new PersistenceException("The file type id [" + fileType.getId() + "] does not exist in the database.");
            }

            Object[] queryArgs = new Object[]{fileType.getDescription(), fileType.getSort(), this.convertBooleanToString(fileType.isImageFile()), fileType.getExtension(), this.convertBooleanToString(fileType.isBundledFile()), operator, modifyDate};
            Helper.doDMLQuery(conn, "UPDATE file_type_lu SET description=?, sort=?, image_file=?, extension=?, bundled_file=?, modify_user=?, modify_date=? WHERE file_type_id=" + fileType.getId(), queryArgs);
            this.closeConnection(conn);
        } catch (PersistenceException var6) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to update file type " + fileType, var6));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var6;
        }

        fileType.setModificationUser(operator);
        fileType.setModificationTimestamp(modifyDate);
    }

    public void removeFileType(FileType fileType, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(fileType, "fileType");
        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "deleting the file type with id: " + fileType.getId()));

        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("file_type_lu", "file_type_id", fileType.getId(), conn)) {
                throw new PersistenceException("The file type id [" + fileType.getId() + "] does not exist in the database.");
            } else {
                Object[] queryArgs = new Object[]{fileType.getId()};
                Object[][] rows = Helper.doQuery(conn, "SELECT DISTINCT project_id FROM project_file_type_xref WHERE file_type_id=" + fileType.getId(), new Object[0], QUERY_PROJECT_IDS__COLUMN_TYPES);
                this.auditProjects(rows, conn, "Removes the project file type", operator);
                Helper.doDMLQuery(conn, "DELETE FROM project_file_type_xref WHERE file_type_id=?", queryArgs);
                Helper.doDMLQuery(conn, "DELETE FROM file_type_lu WHERE file_type_id=?", queryArgs);
                this.closeConnection(conn);
            }
        } catch (PersistenceException var6) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to delete file type " + fileType, var6));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var6;
        }
    }

    public FileType[] getAllFileTypes() throws PersistenceException {
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, (String)null, "Enter getAllFileTypes method."));

        try {
            conn = this.openConnection();
            Object[][] rows = Helper.doQuery(conn, "SELECT file_type_id, description, sort, image_file, extension, bundled_file FROM file_type_lu", new Object[0], QUERY_ALL_FILE_TYPES_COLUMN_TYPES);
            this.closeConnection(conn);
            FileType[] fileTypes = new FileType[rows.length];

            for(int i = 0; i < rows.length; ++i) {
                Object[] row = rows[i];
                fileTypes[i] = new FileType();
                fileTypes[i].setId((Long)row[0]);
                fileTypes[i].setDescription((String)row[1]);
                fileTypes[i].setSort(((Long)row[2]).intValue());
                fileTypes[i].setImageFile((Boolean)row[3]);
                fileTypes[i].setExtension((String)row[4]);
                fileTypes[i].setBundledFile((Boolean)row[5]);
            }

            return fileTypes;
        } catch (PersistenceException var6) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, (String)null, "Fail to getAllFileTypes.", var6));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var6;
        }
    }

    public PrizeType[] getPrizeTypes() throws PersistenceException {
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, (String)null, "Enter getPrizeTypes method."));

        try {
            conn = this.openConnection();
            Object[][] rows = Helper.doQuery(conn, "SELECT prize_type_id, prize_type_desc FROM prize_type_lu", new Object[0], QUERY_ALL_PRIZE_TYPES_COLUMN_TYPES);
            PrizeType[] prizeTypes = new PrizeType[rows.length];

            for(int i = 0; i < rows.length; ++i) {
                Object[] row = rows[i];
                prizeTypes[i] = new PrizeType();
                prizeTypes[i].setId((Long)row[0]);
                prizeTypes[i].setDescription((String)row[1]);
            }

            this.closeConnection(conn);
            return prizeTypes;
        } catch (PersistenceException var6) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, (String)null, "Fail to getPrizeTypes.", var6));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var6;
        }
    }

    public Prize createPrize(Prize prize, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(prize, "prize");
        Helper.assertObjectNotNull(prize.getPrizeType(), "prize.prizeType");
        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        Long newId = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "creating new prize: " + prize));

        try {
            conn = this.openConnection();
            if (prize.getId() > 0L && Helper.checkEntityExists("prize", "prize_id", prize.getId(), conn)) {
                throw new PersistenceException("The prize with the same id [" + prize.getId() + "] already exists.");
            } else {
                try {
                    newId = this.prizeIdGenerator.getNextID();
                    this.getLogger().log(Level.DEBUG, new LogMessage(newId, operator, "generate id for new prize"));
                } catch (IDGenerationException var7) {
                    throw new PersistenceException("Unable to generate id for the prize.", var7);
                }

                this.getLogger().log(Level.DEBUG, "insert record into prize with id:" + newId);
                Timestamp createDate = new Timestamp(System.currentTimeMillis());
                Object[] queryArgs = new Object[]{newId, prize.getProjectId(), (long)prize.getPlace(), prize.getPrizeAmount(), prize.getPrizeType().getId(), prize.getNumberOfSubmissions(), operator, createDate, operator, createDate};
                Helper.doDMLQuery(conn, "INSERT INTO prize (prize_id, project_id, place, prize_amount, prize_type_id, number_of_submissions, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", queryArgs);
                this.closeConnection(conn);
                prize.setCreationUser(operator);
                prize.setCreationTimestamp(createDate);
                prize.setModificationUser(operator);
                prize.setModificationTimestamp(createDate);
                prize.setId(newId);
                return prize;
            }
        } catch (PersistenceException var8) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to create prize " + newId, var8));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var8;
        }
    }

    public void updatePrize(Prize prize, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(prize, "prize");
        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "updating the prize with id: " + prize.getId()));
        Timestamp modifyDate = new Timestamp(System.currentTimeMillis());

        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("prize", "prize_id", prize.getId(), conn)) {
                throw new PersistenceException("The prize id [" + prize.getId() + "] does not exist in the database.");
            }

            Object[] queryArgs = new Object[]{(long)prize.getPlace(), prize.getPrizeAmount(), prize.getPrizeType().getId(), prize.getNumberOfSubmissions(), operator, modifyDate, prize.getProjectId()};
            Helper.doDMLQuery(conn, "UPDATE prize SET place=?, prize_amount=?, prize_type_id=?, number_of_submissions=?, modify_user=?, modify_date=?, project_id=? WHERE prize_id=" + prize.getId(), queryArgs);
            this.closeConnection(conn);
        } catch (PersistenceException var6) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to update prize " + prize, var6));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var6;
        }

        prize.setModificationUser(operator);
        prize.setModificationTimestamp(modifyDate);
    }

    public void removePrize(Prize prize, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(prize, "prize");
        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "deleting the prize with id: " + prize.getId()));

        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("prize", "prize_id", prize.getId(), conn)) {
                throw new PersistenceException("The prize id [" + prize.getId() + "] does not exist in the database.");
            } else {
                Object[] queryArgs = new Object[]{prize.getId()};
                Object[][] rows = Helper.doQuery(conn, "SELECT project_id FROM prize WHERE prize_id=" + prize.getId(), new Object[0], QUERY_PROJECT_IDS__COLUMN_TYPES);
                this.auditProjects(rows, conn, "Removes the project prize", operator);
                Helper.doDMLQuery(conn, "DELETE FROM prize WHERE prize_id=?", queryArgs);
                this.closeConnection(conn);
            }
        } catch (PersistenceException var6) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to delete prize " + prize, var6));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var6;
        }
    }

    public ProjectStudioSpecification createProjectStudioSpecification(ProjectStudioSpecification spec, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(spec, "spec");
        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        Long newId = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "creating new project studio specification: " + spec));

        try {
            conn = this.openConnection();
            if (spec.getId() > 0L && Helper.checkEntityExists("project_studio_specification", "project_studio_spec_id", spec.getId(), conn)) {
                throw new PersistenceException("The project studio specification with the same id [" + spec.getId() + "] already exists.");
            } else {
                try {
                    newId = this.studioSpecIdGenerator.getNextID();
                    this.getLogger().log(Level.DEBUG, new LogMessage(newId, operator, "generate id for new project studio specification"));
                } catch (IDGenerationException var7) {
                    throw new PersistenceException("Unable to generate id for the project studio specification.", var7);
                }

                this.getLogger().log(Level.DEBUG, "insert record into project studio specification with id:" + newId);
                Timestamp createDate = new Timestamp(System.currentTimeMillis());
                Object[] queryArgs = new Object[]{newId, spec.getGoals(), spec.getTargetAudience(), spec.getBrandingGuidelines(), spec.getDislikedDesignWebSites(), spec.getOtherInstructions(), spec.getWinningCriteria(), spec.isSubmittersLockedBetweenRounds(), spec.getRoundOneIntroduction(), spec.getRoundTwoIntroduction(), spec.getColors(), spec.getFonts(), spec.getLayoutAndSize(), operator, createDate, operator, createDate};
                Helper.doDMLQuery(conn, "INSERT INTO project_studio_specification (project_studio_spec_id, goals, target_audience, branding_guidelines, disliked_design_websites, other_instructions, winning_criteria, submitters_locked_between_rounds, round_one_introduction, round_two_introduction, colors, fonts, layout_and_size, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", queryArgs);
                this.closeConnection(conn);
                spec.setCreationUser(operator);
                spec.setCreationTimestamp(createDate);
                spec.setModificationUser(operator);
                spec.setModificationTimestamp(createDate);
                spec.setId(newId);
                return spec;
            }
        } catch (PersistenceException var8) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to create project studio specification " + newId, var8));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var8;
        }
    }

    public void updateProjectStudioSpecification(ProjectStudioSpecification spec, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(spec, "spec");
        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "updating the project studio specification with id: " + spec.getId()));
        Timestamp modifyDate = new Timestamp(System.currentTimeMillis());

        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("project_studio_specification", "project_studio_spec_id", spec.getId(), conn)) {
                throw new PersistenceException("The project studio specification id [" + spec.getId() + "] does not exist in the database.");
            }

            Object[] queryArgs = new Object[]{spec.getGoals(), spec.getTargetAudience(), spec.getBrandingGuidelines(), spec.getDislikedDesignWebSites(), spec.getOtherInstructions(), spec.getWinningCriteria(), spec.isSubmittersLockedBetweenRounds(), spec.getRoundOneIntroduction(), spec.getRoundTwoIntroduction(), spec.getColors(), spec.getFonts(), spec.getLayoutAndSize(), operator, modifyDate};
            Helper.doDMLQuery(conn, "UPDATE project_studio_specification SET goals=?, target_audience=?, branding_guidelines=?, disliked_design_websites=?, other_instructions=?, winning_criteria=?, submitters_locked_between_rounds=?, round_one_introduction=?, round_two_introduction=?, colors=?, fonts=?, layout_and_size=?, modify_user=?, modify_date=? WHERE project_studio_spec_id=" + spec.getId(), queryArgs);
            this.closeConnection(conn);
        } catch (PersistenceException var6) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to update project studio specification " + spec.getId(), var6));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var6;
        }

        spec.setModificationUser(operator);
        spec.setModificationTimestamp(modifyDate);
    }

    public void removeProjectStudioSpecification(ProjectStudioSpecification spec, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(spec, "spec");
        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "deleting the project studio specification with id: " + spec.getId()));

        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("project_studio_specification", "project_studio_spec_id", spec.getId(), conn)) {
                throw new PersistenceException("The project studio specification id [" + spec.getId() + "] does not exist in the database.");
            } else {
                Object[] queryArgs = new Object[]{spec.getId()};
                Object[][] rows = Helper.doQuery(conn, "SELECT DISTINCT project_id FROM project WHERE project_studio_spec_id=" + spec.getId(), new Object[0], QUERY_PROJECT_IDS__COLUMN_TYPES);
                this.auditProjects(rows, conn, "Removes the project studio specification", operator);
                Helper.doDMLQuery(conn, "UPDATE project SET project_studio_spec_id=NULL WHERE project_studio_spec_id=?", queryArgs);
                Helper.doDMLQuery(conn, "DELETE FROM project_studio_specification WHERE project_studio_spec_id=?", queryArgs);
                this.closeConnection(conn);
            }
        } catch (PersistenceException var6) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to delete project studio specification " + spec.getId(), var6));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var6;
        }
    }

    private void auditProjects(Object[][] rows, Connection conn, String reason, String operator) throws PersistenceException {
        if (0 != rows.length) {
            Object[][] var5 = rows;
            int var6 = rows.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Object[] row = var5[var7];
                this.createProjectAudit((Long)row[0], reason, operator, conn);
            }
        }

    }

    public ProjectStudioSpecification getProjectStudioSpecification(long projectId) throws PersistenceException {
        Helper.assertLongPositive(projectId, "projectId");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, "get project studio specification with the project id: " + projectId);

        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            } else {
                Object[][] rows = Helper.doQuery(conn, "SELECT spec.project_studio_spec_id, spec.goals, spec.target_audience, spec.branding_guidelines, spec.disliked_design_websites, spec.other_instructions, spec.winning_criteria, spec.submitters_locked_between_rounds, spec.round_one_introduction, spec.round_two_introduction, spec.colors, spec.fonts, spec.layout_and_size FROM project_studio_specification AS spec JOIN project AS project ON project.project_studio_spec_id=spec.project_studio_spec_id WHERE project.project_id=" + projectId, new Object[0], QUERY_STUDIO_SPEC_COLUMN_TYPES);
                if (rows.length == 0) {
                    this.closeConnection(conn);
                    return null;
                } else {
                    ProjectStudioSpecification studioSpec = new ProjectStudioSpecification();
                    studioSpec.setId((Long)rows[0][0]);
                    studioSpec.setGoals((String)rows[0][1]);
                    studioSpec.setTargetAudience((String)rows[0][2]);
                    studioSpec.setBrandingGuidelines((String)rows[0][3]);
                    studioSpec.setDislikedDesignWebSites((String)rows[0][4]);
                    studioSpec.setOtherInstructions((String)rows[0][5]);
                    studioSpec.setWinningCriteria((String)rows[0][6]);
                    studioSpec.setSubmittersLockedBetweenRounds((Boolean)rows[0][7]);
                    studioSpec.setRoundOneIntroduction((String)rows[0][8]);
                    studioSpec.setRoundTwoIntroduction((String)rows[0][9]);
                    studioSpec.setColors((String)rows[0][10]);
                    studioSpec.setFonts((String)rows[0][11]);
                    studioSpec.setLayoutAndSize((String)rows[0][12]);
                    this.closeConnection(conn);
                    return studioSpec;
                }
            }
        } catch (PersistenceException var6) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, (String)null, "Fails to retrieving project studio specification with project id: " + projectId, var6));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var6;
        }
    }

    public void updateStudioSpecificationForProject(ProjectStudioSpecification spec, long projectId, String operator) throws PersistenceException {
        Helper.assertObjectNotNull(spec, "spec");
        Helper.assertLongPositive(projectId, "projectId");
        Helper.assertStringNotNullNorEmpty(operator, "operator");
        Connection conn = null;
        this.getLogger().log(Level.DEBUG, new LogMessage((Long)null, operator, "updating the studio specification for the project with id: " + projectId));

        try {
            conn = this.openConnection();
            if (!Helper.checkEntityExists("project", "project_id", projectId, conn)) {
                throw new PersistenceException("The project with id " + projectId + " does not exist in the database.");
            } else {
                this.createOrUpdateProjectStudioSpecification(projectId, spec, conn, operator);
                this.createProjectAudit(projectId, "Updates the project studion specification", operator, conn);
                this.closeConnection(conn);
            }
        } catch (PersistenceException var7) {
            this.getLogger().log(Level.ERROR, new LogMessage((Long)null, operator, "Fails to update the studio specification for the project with id " + projectId, var7));
            if (conn != null) {
                this.closeConnectionOnError(conn);
            }

            throw var7;
        }
    }

    private void createOrUpdateProjectStudioSpecification(long projectId, ProjectStudioSpecification spec, Connection conn, String operator) throws PersistenceException {
        if (spec != null) {
            if (spec.getId() > 0L && Helper.checkEntityExists("project_studio_specification", "project_studio_spec_id", spec.getId(), conn)) {
                this.updateProjectStudioSpecification(spec, operator);
            } else {
                this.createProjectStudioSpecification(spec, operator);
            }

            Object[] queryArgs = new Object[]{spec.getId()};
            Helper.doDMLQuery(conn, "UPDATE project SET project_studio_spec_id=? WHERE project.project_id=" + projectId, queryArgs);
        }
    }

    protected String getConnectionName() {
        return this.connectionName;
    }

    protected DBConnectionFactory getConnectionFactory() {
        return this.factory;
    }

    protected abstract Log getLogger();

    protected abstract Connection openConnection() throws PersistenceException;

    protected abstract void closeConnection(Connection var1) throws PersistenceException;

    protected abstract void closeConnectionOnError(Connection var1) throws PersistenceException;

    private void createProject(Long projectId, Project project, String operator, Connection conn) throws PersistenceException {
        this.getLogger().log(Level.DEBUG, "insert record into project with id:" + projectId);
        Object[] queryArgs = new Object[]{projectId, project.getProjectStatus().getId(), project.getProjectCategory().getId(), operator, operator, project.getTcDirectProjectId()};
        Helper.doDMLQuery(conn, "INSERT INTO project (project_id, project_status_id, project_category_id, create_user, create_date, modify_user, modify_date, tc_direct_project_id) VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT, ?)", queryArgs);
        Date createDate = (Date)Helper.doSingleValueQuery(conn, "SELECT create_date FROM project WHERE project_id=?", new Object[]{projectId}, Helper.DATE_TYPE);
        project.setCreationUser(operator);
        project.setCreationTimestamp(createDate);
        project.setModificationUser(operator);
        project.setModificationTimestamp(createDate);
        Map idValueMap = this.makePropertyIdPropertyValueMap(project.getAllProperties(), conn);
        this.createProjectProperties(projectId, project, idValueMap, operator, conn);
    }

    private void updateProject(Project project, String reason, String operator, Connection conn) throws PersistenceException {
        Long projectId = project.getId();
        this.getLogger().log(Level.DEBUG, new LogMessage(projectId, operator, "update project with projectId:" + projectId));
        Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
        Object[] queryArgs = new Object[]{project.getProjectStatus().getId(), project.getProjectCategory().getId(), operator, modifyDate, project.getTcDirectProjectId() == 0L ? null : project.getTcDirectProjectId(), projectId};
        Helper.doDMLQuery(conn, "UPDATE project SET project_status_id=?, project_category_id=?, modify_user=?, modify_date=?, tc_direct_project_id=? WHERE project_id=?", queryArgs);
        project.setModificationUser(operator);
        project.setModificationTimestamp(modifyDate);
        Map idValueMap = this.makePropertyIdPropertyValueMap(project.getAllProperties(), conn);
        this.updateProjectProperties(project, idValueMap, operator, conn);
        this.createProjectAudit(projectId, reason, operator, conn);
    }

    private Map makePropertyIdPropertyValueMap(Map nameValueMap, Connection conn) throws PersistenceException {
        Map idValueMap = new HashMap();
        Map nameIdMap = this.makePropertyNamePropertyIdMap(this.getAllProjectPropertyTypes(conn));
        Iterator var5 = nameValueMap.entrySet().iterator();

        while(var5.hasNext()) {
            Object item = var5.next();
            Map.Entry entry = (Map.Entry)item;
            Object propertyId = nameIdMap.get(entry.getKey());
            if (propertyId == null) {
                throw new PersistenceException("Unable to find ProjectPropertyType name [" + entry.getKey() + "] in project_info_type_lu table.");
            }

            idValueMap.put(propertyId, entry.getValue().toString());
        }

        return idValueMap;
    }

    private ProjectPropertyType[] getAllProjectPropertyTypes(Connection conn) throws PersistenceException {
        Object[][] rows = Helper.doQuery(conn, "SELECT project_info_type_id, name, description FROM project_info_type_lu", new Object[0], QUERY_ALL_PROJECT_PROPERTY_TYPES_COLUMN_TYPES);
        ProjectPropertyType[] propertyTypes = new ProjectPropertyType[rows.length];

        for(int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];
            propertyTypes[i] = new ProjectPropertyType((Long)row[0], (String)row[1], (String)row[2]);
        }

        return propertyTypes;
    }

    public Project[] getProjects(CustomResultSet result) throws PersistenceException {
        Connection conn = null;

        try {
            conn = this.openConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT info.project_id, info_type.name, info.value FROM project_info AS info JOIN project_info_type_lu AS info_type ON info.project_info_type_id=info_type.project_info_type_id WHERE info.project_id = ?");
            int size = result.getRecordCount();
            Project[] projects = new Project[size];

            for(int i = 0; i < size; ++i) {
                result.absolute(i + 1);
                ProjectStatus status = new ProjectStatus(result.getLong(2), result.getString(3));
                ProjectType type = new ProjectType(result.getLong(6), result.getString(7));
                ProjectCategory category = new ProjectCategory(result.getLong(4), result.getString(5), type);
                projects[i] = new Project(result.getLong(1), category, status);
                projects[i].setCreationUser(result.getString(8));
                projects[i].setCreationTimestamp(result.getDate(9));
                projects[i].setModificationUser(result.getString(10));
                projects[i].setModificationTimestamp(result.getDate(11));
                ps.setLong(1, projects[i].getId());
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    projects[i].setProperty(rs.getString(2), rs.getString(3));
                }
            }

            Project[] var20 = projects;
            return var20;
        } catch (NullColumnValueException var16) {
            throw new PersistenceException("Null value retrieved.", var16);
        } catch (InvalidCursorStateException var17) {
            throw new PersistenceException("Cursor state is invalid.", var17);
        } catch (SQLException var18) {
            throw new PersistenceException(var18.getMessage(), var18);
        } finally {
            if (conn != null) {
                this.closeConnection(conn);
            }

        }
    }

    private Project[] getProjects(long[] ids, Connection conn) throws PersistenceException {
        StringBuilder idListBuffer = new StringBuilder();
        idListBuffer.append('(');

        for(int i = 0; i < ids.length; ++i) {
            if (i != 0) {
                idListBuffer.append(',');
            }

            idListBuffer.append(ids[i]);
        }

        idListBuffer.append(')');
        String idList = idListBuffer.toString();
        Object[][] rows = Helper.doQuery(conn, "SELECT project.project_id, status.project_status_id, status.name, category.project_category_id, category.name, type.project_type_id, type.name, project.create_user, project.create_date, project.modify_user, project.modify_date, category.description, project.tc_direct_project_id, tcdp.name as tc_direct_project_name FROM project JOIN project_status_lu AS status ON project.project_status_id=status.project_status_id JOIN project_category_lu AS category ON project.project_category_id=category.project_category_id JOIN project_type_lu AS type ON category.project_type_id=type.project_type_id LEFT OUTER JOIN tc_direct_project AS tcdp ON tcdp.project_id=project.tc_direct_project_id WHERE project.project_id IN " + idList, new Object[0], QUERY_PROJECTS_COLUMN_TYPES);
        Project[] projects = new Project[rows.length];

        for(int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];
            ProjectStatus status = new ProjectStatus((Long)row[1], (String)row[2]);
            ProjectType type = new ProjectType((Long)row[5], (String)row[6]);
            ProjectCategory category = new ProjectCategory((Long)row[3], (String)row[4], type);
            category.setDescription((String)row[11]);
            long projectId = (Long)row[0];
            projects[i] = new Project(projectId, category, status);
            projects[i].setCreationUser((String)row[7]);
            projects[i].setCreationTimestamp((Date)row[8]);
            projects[i].setModificationUser((String)row[9]);
            projects[i].setModificationTimestamp((Date)row[10]);
            projects[i].setTcDirectProjectId(row[12] == null ? 0L : (long)((Long)row[12]).intValue());
            projects[i].setTcDirectProjectName((String)row[13]);
            projects[i].setProjectFileTypes(Arrays.asList(this.getProjectFileTypes(projectId)));
            projects[i].setPrizes(Arrays.asList(this.getProjectPrizes(projectId)));
            projects[i].setProjectStudioSpecification(this.getProjectStudioSpecification(projectId));
        }

        Map projectMap = this.makeIdProjectMap(projects);
        rows = Helper.doQuery(conn, "SELECT info.project_id, info_type.name, info.value FROM project_info AS info JOIN project_info_type_lu AS info_type ON info.project_info_type_id=info_type.project_info_type_id WHERE info.project_id IN " + idList, new Object[0], QUERY_PROJECT_PROPERTIES_COLUMN_TYPES);
        Object[][] var16 = rows;
        int var17 = rows.length;

        for(int var18 = 0; var18 < var17; ++var18) {
            Object[] row = var16[var18];
            Project project = (Project)projectMap.get(row[0]);
            project.setProperty((String)row[1], row[2]);
        }

        return projects;
    }

    private ProjectType[] getAllProjectTypes(Connection conn) throws PersistenceException {
        Object[][] rows = Helper.doQuery(conn, "SELECT project_type_id, name, description, is_generic FROM project_type_lu", new Object[0], QUERY_ALL_PROJECT_TYPES_COLUMN_TYPES);
        ProjectType[] projectTypes = new ProjectType[rows.length];

        for(int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];
            projectTypes[i] = new ProjectType((Long)row[0], (String)row[1], (String)row[2], (Boolean)row[3]);
        }

        return projectTypes;
    }

    private void createProjectProperties(Long projectId, Project project, Map idValueMap, String operator, Connection conn) throws PersistenceException {
        this.getLogger().log(Level.DEBUG, new LogMessage(projectId, operator, "insert record into project_info with project id" + projectId));
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = conn.prepareStatement("INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)");
            Iterator var7 = idValueMap.entrySet().iterator();

            while(var7.hasNext()) {
                Object item = var7.next();
                Map.Entry entry = (Map.Entry)item;
                Object[] queryArgs = new Object[]{projectId, entry.getKey(), entry.getValue(), operator, operator};
                Helper.doDMLQuery(preparedStatement, queryArgs);
                this.auditProjectInfo(conn, projectId, project, 1, (Long)entry.getKey(), (String)entry.getValue());
            }
        } catch (SQLException var14) {
            throw new PersistenceException("Unable to create prepared statement [INSERT INTO project_info (project_id, project_info_type_id, value, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)].", var14);
        } finally {
            Helper.closeStatement(preparedStatement);
        }

    }

    private Map makeIdProjectMap(Project[] projects) {
        Map map = new HashMap();
        Project[] var3 = projects;
        int var4 = projects.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Project project = var3[var5];
            map.put(project.getId(), project);
        }

        return map;
    }

    private void updateProjectProperties(Project project, Map idValueMap, String operator, Connection conn) throws PersistenceException {
        Long projectId = project.getId();
        Map<Long, String> propertyMap = this.getProjectPropertyIdsAndValues(projectId, conn);
        Map createIdValueMap = new HashMap();
        PreparedStatement preparedStatement = null;

        try {
            this.getLogger().log(Level.DEBUG, new LogMessage(projectId, operator, "update project, update project_info with projectId:" + projectId));
            preparedStatement = conn.prepareStatement("UPDATE project_info SET value=?, modify_user=?, modify_date=CURRENT WHERE project_id=? AND project_info_type_id=?");
            Iterator var9 = idValueMap.entrySet().iterator();

            while(var9.hasNext()) {
                Object item = var9.next();
                Map.Entry entry = (Map.Entry)item;
                Long propertyId = (Long)entry.getKey();
                if (propertyMap.containsKey(propertyId)) {
                    if (!((String)propertyMap.get(propertyId)).equals((String)entry.getValue())) {
                        Object[] queryArgs = new Object[]{entry.getValue(), operator, projectId, propertyId};
                        Helper.doDMLQuery(preparedStatement, queryArgs);
                        this.auditProjectInfo(conn, project, 3, propertyId, (String)entry.getValue());
                    }

                    propertyMap.remove(propertyId);
                } else {
                    createIdValueMap.put(propertyId, entry.getValue());
                }
            }
        } catch (SQLException var17) {
            throw new PersistenceException("Unable to create prepared statement [UPDATE project_info SET value=?, modify_user=?, modify_date=CURRENT WHERE project_id=? AND project_info_type_id=?].", var17);
        } finally {
            Helper.closeStatement(preparedStatement);
        }

        this.createProjectProperties(project.getId(), project, createIdValueMap, operator, conn);
        this.deleteProjectProperties(project, propertyMap.keySet(), conn);
    }

    private Set getProjectPropertyIds(Long projectId, Connection conn) throws PersistenceException {
        Set idSet = new HashSet();
        Object[][] rows = Helper.doQuery(conn, "SELECT project_info_type_id FROM project_info WHERE project_id=?", new Object[]{projectId}, QUERY_PROJECT_PROPERTY_IDS_COLUMN_TYPES);
        Object[][] var5 = rows;
        int var6 = rows.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Object[] row = var5[var7];
            idSet.add(row[0]);
        }

        return idSet;
    }

    private Map<Long, String> getProjectPropertyIdsAndValues(Long projectId, Connection conn) throws PersistenceException {
        Map<Long, String> idMap = new HashMap();
        Object[][] rows = Helper.doQuery(conn, "SELECT project_info_type_id, value FROM project_info WHERE project_id=?", new Object[]{projectId}, QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_COLUMN_TYPES);
        Object[][] var5 = rows;
        int var6 = rows.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Object[] row = var5[var7];
            idMap.put((Long)row[0], (String)row[1]);
        }

        return idMap;
    }

    private void deleteProjectProperties(Project project, Set<Long> propertyIdSet, Connection conn) throws PersistenceException {
        Long projectId = project.getId();
        if (!propertyIdSet.isEmpty()) {
            StringBuilder idListBuffer = new StringBuilder();
            idListBuffer.append('(');
            int idx = 0;

            Iterator var7;
            Long id;
            for(var7 = propertyIdSet.iterator(); var7.hasNext(); idListBuffer.append(id)) {
                id = (Long)var7.next();
                if (idx++ != 0) {
                    idListBuffer.append(',');
                }
            }

            idListBuffer.append(')');
            this.getLogger().log(Level.DEBUG, new LogMessage(projectId, (String)null, "delete records from project_info with projectId:" + projectId));
            Helper.doDMLQuery(conn, "DELETE FROM project_info WHERE project_id=? AND project_info_type_id IN " + idListBuffer.toString(), new Object[]{projectId});
            var7 = propertyIdSet.iterator();

            while(var7.hasNext()) {
                id = (Long)var7.next();
                this.auditProjectInfo(conn, project, 2, id, (String)null);
            }
        }

    }

    private void createProjectAudit(Long projectId, String reason, String operator, Connection conn) throws PersistenceException {
        Long auditId;
        try {
            auditId = this.projectAuditIdGenerator.getNextID();
            this.getLogger().log(Level.DEBUG, new LogMessage(projectId, operator, "generate new id for the project audit."));
        } catch (IDGenerationException var7) {
            throw new PersistenceException("Unable to generate id for project.", var7);
        }

        this.getLogger().log(Level.DEBUG, "insert record into project_audit with projectId:" + projectId);
        Object[] queryArgs = new Object[]{auditId, projectId, reason, operator, operator};
        Helper.doDMLQuery(conn, "INSERT INTO project_audit (project_audit_id, project_id, update_reason, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)", queryArgs);
    }

    private Map makePropertyNamePropertyIdMap(ProjectPropertyType[] propertyTypes) throws PersistenceException {
        Map map = new HashMap();
        ProjectPropertyType[] var3 = propertyTypes;
        int var4 = propertyTypes.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            ProjectPropertyType propertyType = var3[var5];
            if (map.containsKey(propertyType.getName())) {
                throw new PersistenceException("Duplicate project property type names [" + propertyType.getName() + "] found in project_info_type_lu table.");
            }

            map.put(propertyType.getName(), propertyType.getId());
        }

        return map;
    }

    private ProjectCategory[] getAllProjectCategories(Connection conn) throws PersistenceException {
        Object[][] rows = Helper.doQuery(conn, "SELECT category.project_category_id, category.name, category.description, type.project_type_id, type.name, type.description, type.is_generic FROM project_category_lu AS category JOIN project_type_lu AS type ON category.project_type_id = type.project_type_id", new Object[0], QUERY_ALL_PROJECT_CATEGORIES_COLUMN_TYPES);
        ProjectCategory[] projectCategories = new ProjectCategory[rows.length];

        for(int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];
            ProjectType type = new ProjectType((Long)row[3], (String)row[4], (String)row[5], (Boolean)row[6]);
            projectCategories[i] = new ProjectCategory((Long)row[0], (String)row[1], (String)row[2], type);
        }

        return projectCategories;
    }

    private ProjectStatus[] getAllProjectStatuses(Connection conn) throws PersistenceException {
        Object[][] rows = Helper.doQuery(conn, "SELECT project_status_id, name, description FROM project_status_lu", new Object[0], QUERY_ALL_PROJECT_STATUSES_COLUMN_TYPES);
        ProjectStatus[] projectStatuses = new ProjectStatus[rows.length];

        for(int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];
            projectStatuses[i] = new ProjectStatus((Long)row[0], (String)row[1], (String)row[2]);
        }

        return projectStatuses;
    }

    private void auditProjectInfo(Connection connection, Long projectId, Project project, int auditType, long projectInfoTypeId, String value) throws PersistenceException {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO project_info_audit (project_id, project_info_type_id, value, audit_action_type_id, action_date, action_user_id) VALUES (?, ?, ?, ?, ?, ?)");
            int index = 1;
            int var15 = index + 1;
            statement.setLong(index, projectId);
            statement.setLong(var15++, projectInfoTypeId);
            statement.setString(var15++, value);
            statement.setInt(var15++, auditType);
            statement.setTimestamp(var15++, new Timestamp(project.getModificationTimestamp().getTime()));
            statement.setString(var15++, project.getModificationUser());
            if (statement.executeUpdate() != 1) {
                throw new PersistenceException("Audit information was not successfully saved.");
            }
        } catch (SQLException var13) {
            this.closeConnectionOnError(connection);
            throw new PersistenceException("Unable to insert project_info_audit record.", var13);
        } finally {
            Helper.closeStatement(statement);
        }

    }

    private void auditProjectInfo(Connection connection, Project project, int auditType, long projectInfoTypeId, String value) throws PersistenceException {
        this.auditProjectInfo(connection, project.getId(), project, auditType, projectInfoTypeId, value);
    }

    static {
        QUERY_ALL_PROJECT_TYPES_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.BOOLEAN_TYPE};
        QUERY_ALL_PROJECT_CATEGORIES_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.BOOLEAN_TYPE};
        QUERY_ALL_PROJECT_STATUSES_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};
        QUERY_ALL_PROJECT_PROPERTY_TYPES_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};
        QUERY_PROJECTS_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE};
        QUERY_PROJECT_PROPERTIES_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};
        QUERY_PROJECT_PROPERTY_IDS_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE};
        QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.STRING_TYPE};
        QUERY_FILE_TYPES_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.BOOLEAN_TYPE, Helper.STRING_TYPE, Helper.BOOLEAN_TYPE};
        QUERY_PRIZES_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.Double_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE};
        QUERY_ALL_PRIZE_TYPES_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.STRING_TYPE};
        QUERY_ALL_FILE_TYPES_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.BOOLEAN_TYPE, Helper.STRING_TYPE, Helper.BOOLEAN_TYPE};
        QUERY_STUDIO_SPEC_COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.BOOLEAN_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};
        QUERY_PROJECT_IDS__COLUMN_TYPES = new DataType[]{Helper.LONG_TYPE};
    }
    protected Connection openConnection() throws PersistenceException {
        String connectionName = this.getConnectionName();
        if (connectionName == null) {
            LOGGER.log(Level.DEBUG, new LogMessage((Long)null, (String)null, "creating db connection using default connection"));
        } else {
            LOGGER.log(Level.DEBUG, new LogMessage((Long)null, (String)null, "creating db connection using connection name: " + connectionName));
        }

        Connection conn = Helper.createConnection(this.getConnectionFactory(), connectionName);

        try {
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException var4) {
            throw new PersistenceException("Error occurs when setting " + (connectionName == null ? "the default connection" : "the connection '" + connectionName + "'") + " to support transaction.", var4);
        }
    }

    protected void closeConnection(Connection connection) throws PersistenceException {
        Helper.assertObjectNotNull(connection, "connection");

        try {
            LOGGER.log(Level.DEBUG, "committing transaction");
            Helper.commitTransaction(connection);
        } finally {
            Helper.closeConnection(connection);
        }

    }

    protected void closeConnectionOnError(Connection connection) throws PersistenceException {
        Helper.assertObjectNotNull(connection, "connection");

        try {
            LOGGER.log(Level.DEBUG, "rollback transaction");
            Helper.rollBackTransaction(connection);
        } finally {
            Helper.closeConnection(connection);
        }

    }

//    protected Log getLogger() {
//        return LOGGER;
//    }
}
