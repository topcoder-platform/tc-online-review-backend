/*
 * Copyright (c) 2006 - 2010, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.onlinereview.component.deliverable;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.util.CommonUtils.executeSql;
import static com.topcoder.onlinereview.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.util.CommonUtils.getInt;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.util.CommonUtils.getString;

/**
 * A database upload Persistence class for testing.
 *
 * @author Chenhong, morehappiness
 * @version 1.1
 */
@Slf4j
@Component
public class UploadPersistence {
  private static final Logger logger = LoggerFactory.getLogger(UploadPersistence.class.getName());

  @Value("{upload.persistence.entity-manager-name}")
  private String entityManagerName;

  @Autowired private Map<String, EntityManager> entityManagerMap;
  private EntityManager entityManager;

  @PostConstruct
  public void postRun() {
    entityManager = entityManagerMap.get(entityManagerName);
  }

  /**
   * addUploadType: Adds the given uploadType to the persistence. The id of the upload type must
   * already be assigned, as must all the other fields needed for persistence.
   *
   * @param uploadType The upload type to add
   * @throws IllegalArgumentException If uploadType is null
   * @throws IllegalArgumentException If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void addUploadType(UploadType uploadType) throws UploadPersistenceException {
    String query =
        "INSERT INTO upload_type_lu (upload_type_id, name, description, create_user, create_date,"
            + "modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
    List values = new ArrayList();
    values.add(uploadType.getId());
    values.add(uploadType.getName());
    values.add(uploadType.getDescription());
    values.add(uploadType.getCreationUser());
    values.add(uploadType.getCreationTimestamp());
    values.add(uploadType.getModificationUser());
    values.add(uploadType.getModificationTimestamp());
    executeSingleCommand(query, values);
  }

  /**
   * Executes given query with the values. It creates the connection, executes command and closes
   * everything.
   *
   * @param query the query to be used.
   * @param values the query values.
   * @return the number of affected rows.
   * @throws UploadPersistenceException if any error occurs during operation.
   */
  private int executeSingleCommand(String query, List values) throws UploadPersistenceException {
    try {
      return executeUpdate(query, values);
    } catch (SQLException ex) {
      throw new UploadPersistenceException("Error occurs during database operation.", ex);
    }
  }

  /**
   * Executes the update operation using the given connection, query and values.
   *
   * @param query the SQL query.
   * @param values the query values.
   * @return the number of affected rows.
   * @throws SQLException if any database error occurs.
   */
  private int executeUpdate(String query, List values) throws SQLException {
    executeSqlWithParam(entityManager, query, values);
    return 1;
  }

  /**
   * Removes the given upload type (by id) from the persistence. The id of the upload type can not
   * be UNSET_ID, but the other fields do not matter.
   *
   * @param uploadType The upload type to remove
   * @throws IllegalArgumentException If uploadType is null
   * @throws IllegalArgumentException If the id is UNSET_ID
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void removeUploadType(UploadType uploadType) throws UploadPersistenceException {
    String query = "DELETE FROM upload_type_lu WHERE upload_type_id = ?";
    List values = new ArrayList();

    values.add(new Long(uploadType.getId()));
    executeSingleCommand(query, values);
  }

  /**
   * Updates the given upload type in the persistence. The id of the uploadType can not be UNSET_ID,
   * and all other fields needed for persistence must also be assigned.
   *
   * @param uploadType The upload type to update
   * @throws IllegalArgumentException If uploadType is null
   * @throws IllegalArgumentException If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void updateUploadType(UploadType uploadType) throws UploadPersistenceException {
    String query =
        "UPDATE upload_type_lu SET name = ?, description = ?, modify_user = ?, modify_date = ? "
            + "WHERE upload_type_id = ?";
    List values = new ArrayList();

    values.add(uploadType.getName());
    values.add(uploadType.getDescription());
    values.add(uploadType.getModificationUser());
    values.add(uploadType.getModificationTimestamp());
    values.add(new Long(uploadType.getId()));

    executeSingleCommand(query, values);
  }

  /**
   * Loads the UploadType with the given id from persistence. Returns null if there is no UploadType
   * with the given id.
   *
   * @return The loaded UploadType or null
   * @param uploadTypeId The id of the item to retrieve
   * @throws IllegalArgumentException if uploadTypeId is <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   */
  public UploadType loadUploadType(long uploadTypeId) throws UploadPersistenceException {
    UploadType[] types = loadUploadTypes(new long[] {uploadTypeId});

    if (types.length > 0) {
      return types[0];
    }

    return null;
  }

  /**
   * Creates the UploadType instance using the values from result set.
   *
   * @param rs the source result set.
   * @return new UploadType instance.
   */
  private static UploadType populateUploadType(Map<String, Object> rs) {
    UploadType type = new UploadType();
    type.setId(getLong(rs, "type_id"));
    type.setName(getString(rs, "type_name"));
    type.setDescription(getString(rs, "type_description"));
    type.setCreationUser(getString(rs, "type_create_user"));
    type.setCreationTimestamp(getDate(rs, "type_create_date"));
    type.setModificationUser(getString(rs, "type_modify_user"));
    type.setModificationTimestamp(getDate(rs, "type_modify_date"));
    return type;
  }

  /**
   * Gets the ids of all upload types in the persistence. The individual upload types can then be
   * loaded with the loadUploadType method.
   *
   * @return The ids of all upload types
   * @throws UploadPersistenceException If there is an error reading the persistence store
   */
  public long[] getAllUploadTypeIds() throws UploadPersistenceException {
    String query = "SELECT upload_type_id FROM upload_type_lu";
    return selectIds(query, "upload_type_id");
  }

  /**
   * Executes the query that will have only one column in result - the id of something. The reult
   * will be converted to long array.
   *
   * @param query the query to select ids.
   * @return the ids array (may be empty).
   * @throws UploadPersistenceException if any error occurs during operation.
   */
  private long[] selectIds(String query, String idName) throws UploadPersistenceException {
    try {
      var result = executeSql(entityManager, query);
      var ids = new long[0];
      for (var i = 0; i < result.size(); i++) {
        ids[i] = getLong(result.get(i), idName);
      }
      return ids;
    } catch (Exception ex) {
      throw new UploadPersistenceException("Error occurs while retrieving the ids.", ex);
    }
  }

  /**
   * Adds the given uploadStatus to the persistence. The id of the upload status must already be
   * assigned, as must all the other fields needed for persistence.
   *
   * @param uploadStatus The upload status to add
   * @throws IllegalArgumentException If uploadStatus is null
   * @throws IllegalArgumentException If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void addUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
    String query =
        "INSERT INTO upload_status_lu (upload_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

    List values = new ArrayList();

    values.add(new Long(uploadStatus.getId()));
    values.add(uploadStatus.getName());
    values.add(uploadStatus.getDescription());
    values.add(uploadStatus.getCreationUser());
    values.add(uploadStatus.getCreationTimestamp());
    values.add(uploadStatus.getModificationUser());
    values.add(uploadStatus.getModificationTimestamp());

    executeSingleCommand(query, values);
  }

  /**
   * Removes the given upload status (by id) from the persistence. The id of the upload status can
   * not be UNSET_ID, but the other fields do not matter.
   *
   * @param uploadStatus The upload status to remove
   * @throws IllegalArgumentException If uploadStatus is null
   * @throws IllegalArgumentException If the id is UNSET_ID
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void removeUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
    String query = "DELETE FROM upload_status_lu WHERE upload_status_id = ?";
    List values = Collections.singletonList(new Long(uploadStatus.getId()));

    executeSingleCommand(query, values);
  }

  /**
   * Updates the given upload status in the persistence. The id of the uploadStatus can not be
   * UNSET_ID, and all other fields needed for persistence must also be assigned.
   *
   * @param uploadStatus The upload status to update
   * @throws IllegalArgumentException If uploadStatus is null
   * @throws IllegalArgumentException If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void updateUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
    String query =
        "UPDATE upload_status_lu SET name = ?, description = ?, modify_user = ?, "
            + "modify_date = ? WHERE upload_status_id = ?";

    List values = new ArrayList();

    values.add(uploadStatus.getName());
    values.add(uploadStatus.getDescription());
    values.add(uploadStatus.getModificationUser());
    values.add(uploadStatus.getModificationTimestamp());
    values.add(new Long(uploadStatus.getId()));

    executeSingleCommand(query, values);
  }

  /**
   * Loads the UploadStatus with the given id from persistence. Returns null if there is no
   * UploadStatus with the given id.
   *
   * @return The loaded UploadStatus or null
   * @param uploadStatusId The id of the item to retrieve
   * @throws IllegalArgumentException if uploadStatusId is <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   */
  public UploadStatus loadUploadStatus(long uploadStatusId) throws UploadPersistenceException {
    UploadStatus[] statuses = loadUploadStatuses(new long[] {uploadStatusId});

    if (statuses.length > 0) {
      return statuses[0];
    }

    return null;
  }

  /**
   * Creates the UploadStatus instance using the values from result set.
   *
   * @param rs the source result set.
   * @return new UploadStatus instance.
   */
  private static UploadStatus populateUploadStatus(Map<String, Object> rs) {
    UploadStatus status = new UploadStatus();
    status.setId(getLong(rs, "status_id"));
    status.setName(getString(rs, "status_name"));
    status.setDescription(getString(rs, "status_description"));
    status.setCreationUser(getString(rs, "status_create_user"));
    status.setCreationTimestamp(getDate(rs, "status_create_date"));
    status.setModificationUser(getString(rs, "status_modify_user"));
    status.setModificationTimestamp(getDate(rs, "status_modify_date"));
    return status;
  }

  /**
   * Gets the ids of all upload statuses in the persistence. The individual upload statuses can then
   * be loaded with the loadUploadStatus method.
   *
   * @return The ids of all upload statuses
   * @throws UploadPersistenceException If there is an error reading the persistence store
   */
  public long[] getAllUploadStatusIds() throws UploadPersistenceException {
    String query = "SELECT upload_status_id FROM upload_status_lu";
    return selectIds(query, "upload_status_id");
  }

  /**
   * Adds the given submission status to the persistence. The id of the submission status must
   * already be assigned, as must all the other fields needed for persistence.
   *
   * @param submissionStatus The submission status to add
   * @throws IllegalArgumentException If submissionStatus is null
   * @throws IllegalArgumentException If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void addSubmissionStatus(SubmissionStatus submissionStatus)
      throws UploadPersistenceException {
    String query =
        "INSERT INTO submission_status_lu (submission_status_id, name, "
            + "description, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

    List values = new ArrayList();

    values.add(new Long(submissionStatus.getId()));
    values.add(submissionStatus.getName());
    values.add(submissionStatus.getDescription());
    values.add(submissionStatus.getCreationUser());
    values.add(submissionStatus.getCreationTimestamp());
    values.add(submissionStatus.getModificationUser());
    values.add(submissionStatus.getModificationTimestamp());

    executeSingleCommand(query, values);
  }

  /**
   * Removes the given submission status (by id) from the persistence. The id of the submission
   * status can not be UNSET_ID, but the other fields do not matter.
   *
   * @param submissionStatus The submission status to remove
   * @throws IllegalArgumentException If submissionStatus is null
   * @throws IllegalArgumentException If the id is UNSET_ID
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void removeSubmissionStatus(SubmissionStatus submissionStatus)
      throws UploadPersistenceException {
    String query = "DELETE FROM submission_status_lu WHERE submission_status_id = ?";
    List values = Collections.singletonList(new Long(submissionStatus.getId()));

    executeSingleCommand(query, values);
  }

  /**
   * Updates the given submission status in the persistence. The id of the submissionStats can not
   * be UNSET_ID, and all other fields needed for persistence must also be assigned.
   *
   * @param submissionStatus The submissionStatus to update
   * @throws IllegalArgumentException If submissionStatus is null
   * @throws IllegalArgumentException If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void updateSubmissionStatus(SubmissionStatus submissionStatus)
      throws UploadPersistenceException {
    String query =
        "UPDATE submission_status_lu SET name = ?, description = ?, "
            + "modify_user = ?, modify_date = ? WHERE submission_status_id = ?";

    List values = new ArrayList();

    values.add(submissionStatus.getName());
    values.add(submissionStatus.getDescription());
    values.add(submissionStatus.getModificationUser());
    values.add(submissionStatus.getModificationTimestamp());
    values.add(new Long(submissionStatus.getId()));

    executeSingleCommand(query, values);
  }

  /**
   * Loads the SubmissionStatus with the given id from persistence. Returns null if there is no
   * SubmissionStatus with the given id.
   *
   * @return The loaded SubmissionStatus or null
   * @param submissionStatusId The id of the item to retrieve
   * @throws IllegalArgumentException if submissionStatusId is <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   */
  public SubmissionStatus loadSubmissionStatus(long submissionStatusId)
      throws UploadPersistenceException {
    SubmissionStatus[] statuses = loadSubmissionStatuses(new long[] {submissionStatusId});

    if (statuses.length > 0) {
      return statuses[0];
    }

    return null;
  }

  /**
   * Creates the SubmissionStatus instance using the values from result set.
   *
   * @param rs the source result set.
   * @return new SubmissionStatus instance.
   * @throws SQLException if any SQL error occurs.
   */
  private SubmissionStatus populateSubmissionStatus(Map<String, Object> rs) throws SQLException {
    SubmissionStatus status = new SubmissionStatus();
    status.setId(getLong(rs, "status_id"));
    status.setName(getString(rs, "status_name"));
    status.setDescription(getString(rs, "status_description"));
    status.setCreationUser(getString(rs, "status_create_user"));
    status.setCreationTimestamp(getDate(rs, "status_create_date"));
    status.setModificationUser(getString(rs, "status_modify_user"));
    status.setModificationTimestamp(getDate(rs, "status_modify_date"));
    return status;
  }

  /**
   * Gets the ids of all submission statuses in the persistence. The individual submission statuses
   * can then be loaded with the loadSubmissionStatus method.
   *
   * @return The ids of all submission statuses
   * @throws UploadPersistenceException If there is an error reading the persistence store
   */
  public long[] getAllSubmissionStatusIds() throws UploadPersistenceException {
    String query = "SELECT submission_status_id FROM submission_status_lu;";
    return selectIds(query, "submission_status_id");
  }

  /**
   * Adds the given upload to the persistence. The id of the upload must already be assigned, as
   * must all the other fields needed for persistence.
   *
   * @param upload The upload to add
   * @throws IllegalArgumentException If upload is null
   * @throws IllegalArgumentException If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void addUpload(Upload upload) throws UploadPersistenceException {
    String query =
        "INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, "
            + "upload_status_id, parameter, create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    List values = new ArrayList();

    values.add(new Long(upload.getId()));
    values.add(new Long(upload.getProject()));
    values.add(new Long(upload.getOwner()));
    values.add(new Long(upload.getUploadType().getId()));
    values.add(new Long(upload.getUploadStatus().getId()));

    values.add(upload.getParameter());
    values.add(upload.getCreationUser());
    values.add(upload.getCreationTimestamp());
    values.add(upload.getModificationUser());
    values.add(upload.getModificationTimestamp());

    executeSingleCommand(query, values);
  }

  /**
   * Removes the given upload (by id) from the persistence. The id of the upload can not be
   * UNSET_ID, but the other fields do not matter.
   *
   * @param upload The upload to remove
   * @throws IllegalArgumentException If upload is null
   * @throws IllegalArgumentException If the id is UNSET_ID
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void removeUpload(Upload upload) throws UploadPersistenceException {
    String query = "DELETE FROM upload WHERE upload_id = ?";
    List values = Collections.singletonList(new Long(upload.getId()));

    executeSingleCommand(query, values);
  }

  /**
   * Updates the given upload in the persistence. The id of the upload can not be UNSET_ID, and all
   * other fields needed for persistence must also be assigned.
   *
   * @param upload The upload to update
   * @throws IllegalArgumentException If upload is null
   * @throws IllegalArgumentException If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void updateUpload(Upload upload) throws UploadPersistenceException {
    String query =
        "UPDATE upload SET project_id = ?, resource_id = ?, upload_type_id = ?, "
            + "upload_status_id = ?, parameter = ?, modify_user = ?, modify_date = ? WHERE upload_id = ?";

    List values = new ArrayList();

    values.add(new Long(upload.getProject()));
    values.add(new Long(upload.getOwner()));
    values.add(new Long(upload.getUploadType().getId()));
    values.add(new Long(upload.getUploadStatus().getId()));

    values.add(upload.getParameter());
    values.add(upload.getModificationUser());
    values.add(upload.getModificationTimestamp());
    values.add(new Long(upload.getId()));

    executeSingleCommand(query, values);
  }

  /**
   * Loads the Upload with the given id from persistence. Returns null if there is no Upload with
   * the given id.
   *
   * @return The loaded Upload or null
   * @param uploadId The id of the item to retrieve
   * @throws IllegalArgumentException if uploadId is <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   */
  public Upload loadUpload(long uploadId) throws UploadPersistenceException {
    String query =
        "SELECT upload_id, project_id, resource_id, "
            + "parameter, upload.create_user upload_create_user, upload.create_date upload_create_date, "
            + "upload.modify_user upload_modify_user, "
            + "upload.modify_date upload_modify_date, "
            + "upload_type_lu.name type_name, upload_type_lu.upload_type_id type_id, "
            + "upload_status_lu.name status_name, "
            + "upload_status_lu.upload_status_id status_id, upload_type_lu.description type_description, "
            + "upload_status_lu.description status_description, "
            + "upload_type_lu.create_user type_create_user, upload_type_lu.create_date type_create_date, "
            + "upload_type_lu.modify_user type_modify_user, "
            + "upload_type_lu.modify_date type_modify_date, upload_status_lu.create_user status_create_user, "
            + "upload_status_lu.create_date status_create_date, "
            + "upload_status_lu.modify_user status_modify_user, upload_status_lu.modify_date status_modify_date "
            + "FROM upload INNER JOIN upload_type_lu ON upload_type_lu.upload_type_id = upload.upload_type_id "
            + "INNER JOIN upload_status_lu ON upload_status_lu.upload_status_id = upload.upload_status_id "
            + "WHERE upload_id = ?";
    try {
      var rs = executeSqlWithParam(entityManager, query, newArrayList(uploadId));
      if (!rs.isEmpty()) {
        return populateUpload(rs.get(0));
      }

    } catch (Exception ex) {
      throw new UploadPersistenceException("Error occurs while retrieving the upload.", ex);
    }

    return null;
  }

  /**
   * Creates the Upload instance using the values from result set.
   *
   * @return new Upload instance.
   */
  private static Upload populateUpload(Map<String, Object> rs) {
    Upload upload = new Upload();
    upload.setId(getLong(rs, "upload_id"));
    upload.setParameter(getString(rs, "parameter"));
    upload.setProject(getLong(rs, "project_id"));
    upload.setOwner(getLong(rs, "resource_id"));
    upload.setCreationUser(getString(rs, "upload_create_user"));
    upload.setModificationUser(getString(rs, "upload_modify_user"));
    upload.setCreationTimestamp(getDate(rs, "upload_create_date"));
    upload.setModificationTimestamp(getDate(rs, "upload_modify_date"));
    upload.setUploadStatus(populateUploadStatus(rs));
    upload.setUploadType(populateUploadType(rs));
    return upload;
  }

  /**
   * Adds the given submission to the persistence. The id of the submission must already be
   * assigned, as must all the other fields needed for persistence.
   *
   * @param submission The submission to add
   * @throws IllegalArgumentException If submission is null
   * @throws IllegalArgumentException If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void addSubmission(Submission submission) throws UploadPersistenceException {
    String query =
        "INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, "
            + "create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    List values = new ArrayList();

    values.add(submission.getId());
    values.add(submission.getUpload().getId());
    values.add(submission.getSubmissionStatus().getId());
    values.add(submission.getSubmissionType().getId());
    values.add(submission.getCreationUser());
    values.add(submission.getCreationTimestamp());
    values.add(submission.getModificationUser());
    values.add(submission.getModificationTimestamp());

    executeSingleCommand(query, values);
  }

  /**
   * Removes the given submission (by id) from the persistence. The id of the submission can not be
   * UNSET_ID, but the other fields do not matter.
   *
   * @param submission The submission to remove
   * @throws IllegalArgumentException If submission is null
   * @throws IllegalArgumentException If the id is UNSET_ID
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void removeSubmission(Submission submission) throws UploadPersistenceException {
    String query = "DELETE FROM submission WHERE submission_id = ?";
    List values = Collections.singletonList(new Long(submission.getId()));

    executeSingleCommand(query, values);
  }

  /**
   * Updates the given submission in the persistence. The id of the submission can not be UNSET_ID,
   * and all other fields needed for persistence must also be assigned.
   *
   * @param submission The submission to add
   * @throws IllegalArgumentException If submission is null
   * @throws IllegalArgumentException If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void updateSubmission(Submission submission) throws UploadPersistenceException {
    String query =
        "UPDATE submission SET upload_id = ?, submission_status_id = ?, submission_type_id = ?, "
            + "modify_user = ?, modify_date = ? WHERE submission_id = ?";

    List values = new ArrayList();

    values.add(new Long(submission.getUpload().getId()));
    values.add(new Long(submission.getSubmissionStatus().getId()));
    values.add(new Long(submission.getSubmissionType().getId()));
    values.add(submission.getModificationUser());
    values.add(submission.getModificationTimestamp());
    values.add(new Long(submission.getId()));

    executeSingleCommand(query, values);
  }

  /**
   * Loads the Submission with the given id from persistence. Returns null if there is no Submission
   * with the given id.
   *
   * @return The loaded Submission or null
   * @param submissionId The id of the item to retrieve
   * @throws IllegalArgumentException if submissionId is <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   */
  public Submission loadSubmission(long submissionId) throws UploadPersistenceException {
    Submission[] submissions = loadSubmissions(new long[] {submissionId});
    if (submissions.length > 0) {
      return submissions[0];
    }
    return null;
  }

  /**
   * Creates the Submission instance using the values from result set.
   *
   * @param rs the source result set.
   * @return new Submission instance.
   * @throws SQLException if any SQL error occurs.
   * @throws UploadPersistenceException if error occurs when loading Upload instance.
   */
  private Submission populateSubmission(Map<String, Object> rs)
      throws SQLException, UploadPersistenceException {
    Submission submission = new Submission();

    submission.setId(getLong(rs, "submission_id"));
    submission.setSubmissionStatus(populateSubmissionStatus(rs));

    submission.setCreationUser(getString(rs, "submission_create_user"));
    submission.setModificationUser(getString(rs, "submission_modify_user"));
    submission.setCreationTimestamp(getDate(rs, "submission_create_date"));
    submission.setModificationTimestamp(getDate(rs, "submission_modify_date"));

    submission.setUpload(loadUpload(getLong(rs, "upload_id")));

    return submission;
  }

  /**
   * Loads all Submissions with the given ids from persistence. May return a 0-length array.
   *
   * @param submissionIds The ids of submissions to load
   * @return The loaded submissions
   * @throws IllegalArgumentException if any id is <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   */
  public Submission[] loadSubmissions(long[] submissionIds) throws UploadPersistenceException {
    String query =
        "SELECT submission_id, upload_id, "
            + "submission.create_user submission_create_user, submission.create_date submission_create_date, "
            + "submission.modify_user submission_modify_user, submission.modify_date submission_modify_date, "
            + "submission_status_lu.submission_status_id status_id, submission_status_lu.name status_name, "
            + "submission_status_lu.description status_description, "
            + "submission_status_lu.create_user status_create_user, "
            + "submission_status_lu.create_date status_create_date, "
            + "submission_status_lu.modify_user status_modify_user, "
            + "submission_status_lu.modify_date status_modify_date "
            + "FROM submission INNER JOIN submission_status_lu "
            + "ON submission_status_lu.submission_status_id = submission.submission_status_id WHERE "
            + "submission_id IN ";
    try {
      var rs =
          executeSqlWithParam(
              entityManager,
              query + createQuestionMarks(submissionIds.length),
              newArrayList(submissionIds));
      List result = new ArrayList();
      for (var i = 0; i < rs.size(); i++) {
        log.info(this.getClass().getName() + " loadSubmissions - index: " + i);
        result.add(populateSubmission(rs.get(i)));
      }
      return (Submission[]) result.toArray(new Submission[result.size()]);
    } catch (SQLException ex) {
      throw new UploadPersistenceException(
          "Error occurs while retrieving the submission status.", ex);
    }
  }

  /**
   * Loads all Uploads with the given ids from persistence. May return a 0-length array.
   *
   * @param uploadIds The ids of uploads to load
   * @return The loaded uploads
   * @throws IllegalArgumentException if any id is <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   */
  public Upload[] loadUploads(long[] uploadIds) throws UploadPersistenceException {
    String query =
        "SELECT upload_id, project_id, resource_id, "
            + "parameter, upload.create_user upload_create_user, upload.create_date upload_create_date, "
            + "upload.modify_user upload_modify_user, "
            + "upload.modify_date upload_modify_date, "
            + "upload_type_lu.name type_name, upload_type_lu.upload_type_id type_id, "
            + "upload_status_lu.name status_name, "
            + "upload_status_lu.upload_status_id status_id, upload_type_lu.description type_description, "
            + "upload_status_lu.description status_description, "
            + "upload_type_lu.create_user type_create_user, upload_type_lu.create_date type_create_date, "
            + "upload_type_lu.modify_user type_modify_user, "
            + "upload_type_lu.modify_date type_modify_date, upload_status_lu.create_user status_create_user, "
            + "upload_status_lu.create_date status_create_date, "
            + "upload_status_lu.modify_user status_modify_user, upload_status_lu.modify_date status_modify_date "
            + "FROM upload INNER JOIN upload_type_lu ON upload_type_lu.upload_type_id = upload.upload_type_id "
            + "INNER JOIN upload_status_lu ON upload_status_lu.upload_status_id = upload.upload_status_id "
            + "WHERE upload_id IN ";
    try {
      var rs =
          executeSqlWithParam(
              entityManager,
              query + createQuestionMarks(uploadIds.length),
              newArrayList(uploadIds));
      List<Upload> result = new ArrayList();
      for (var row : rs) {
        result.add(populateUpload(row));
      }
      return result.toArray(new Upload[result.size()]);
    } catch (Exception ex) {
      throw new UploadPersistenceException("Error occurs while retrieving the uploads.", ex);
    }
  }

  /**
   * Loads all UploadTypes with the given ids from persistence. May return a 0-length array.
   *
   * @param uploadTypeIds The ids of the objects to load
   * @return the loaded UploadTypes
   * @throws IllegalArgumentException if any id is <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   */
  public UploadType[] loadUploadTypes(long[] uploadTypeIds) throws UploadPersistenceException {
    String query =
        "SELECT upload_type_id type_id, name type_name, description type_description, "
            + "create_user type_create_user, create_date type_create_date, modify_user type_modify_user, "
            + "modify_date type_modify_date FROM upload_type_lu WHERE upload_type_id IN ";
    try {
      var rs =
          executeSqlWithParam(
              entityManager,
              query + createQuestionMarks(uploadTypeIds.length),
              newArrayList(uploadTypeIds));
      List<UploadType> result = new ArrayList();
      for (var row : rs) {
        result.add(populateUploadType(row));
      }

      return result.toArray(new UploadType[result.size()]);
    } catch (Exception ex) {
      throw new UploadPersistenceException("Error occurs while retrieving the uploads.", ex);
    }
  }

  public void addSubmissionImage(SubmissionImage submissionImage)
      throws UploadPersistenceException {}

  public void updateSubmissionImage(SubmissionImage submissionImage)
      throws UploadPersistenceException {}

  public void removeSubmissionImage(SubmissionImage submissionImage)
      throws UploadPersistenceException {}

  public MimeType loadMimeType(long mimeTypeId) throws UploadPersistenceException {
    return null;
  }

  public long[] getAllMimeTypeIds() throws UploadPersistenceException {
    return new long[0];
  }

  public MimeType[] loadMimeTypes(long[] mimeTypeIds) throws UploadPersistenceException {
    return new MimeType[0];
  }

  public SubmissionImage[] getImagesForSubmission(long submissionId)
      throws UploadPersistenceException {
    return new SubmissionImage[0];
  }

  /**
   * Loads all UploadStatuses with the given ids from persistence. May return a 0-length array.
   *
   * @param uploadStatusIds The ids of the objects to load
   * @return the loaded UploadStatuses
   * @throws IllegalArgumentException if any id is <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   */
  public UploadStatus[] loadUploadStatuses(long[] uploadStatusIds)
      throws UploadPersistenceException {
    String query =
        "SELECT upload_status_id status_id, name status_name, description status_description, "
            + "create_user status_create_user, create_date status_create_date, "
            + "modify_user status_modify_user, modify_date status_modify_date FROM upload_status_lu "
            + "WHERE upload_status_id IN ";
    try {
      var rs =
          executeSqlWithParam(
              entityManager,
              query + createQuestionMarks(uploadStatusIds.length),
              newArrayList(uploadStatusIds));
      List<UploadStatus> result = new ArrayList();
      for (var row : rs) {
        result.add(populateUploadStatus(row));
      }
      return result.toArray(new UploadStatus[result.size()]);
    } catch (Exception ex) {
      throw new UploadPersistenceException("Error occurs while retrieving the uploads.", ex);
    }
  }

  /**
   * Loads all SubmissionStatuses with the given ids from persistence. May return a empty array.
   *
   * @param submissionStatusIds The ids of the objects to load
   * @return the loaded SubmissionStatuses
   * @throws IllegalArgumentException if any id is <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   */
  public SubmissionStatus[] loadSubmissionStatuses(long[] submissionStatusIds)
      throws UploadPersistenceException {
    String query =
        "SELECT submission_status_id status_id, name status_name, description status_description, "
            + "create_user status_create_user, create_date status_create_date, "
            + "modify_user status_modify_user, modify_date status_modify_date FROM submission_status_lu "
            + "WHERE submission_status_id IN ";
    try {
      log.info("loadSubmissionStatuses - Creating Query: " + submissionStatusIds.length);
      log.info("loadSubmissionStatuses - Processing Query");
      var rs =
          executeSqlWithParam(
              entityManager,
              query + createQuestionMarks(submissionStatusIds.length),
              newArrayList(submissionStatusIds));
      log.info("loadSubmissionStatuses - Query Processed");
      List<SubmissionStatus> result = new ArrayList();
      for (var row : rs) {
        result.add(populateSubmissionStatus(row));
      }

      return result.toArray(new SubmissionStatus[result.size()]);
    } catch (SQLException ex) {
      throw new UploadPersistenceException(
          "Error occurs while retrieving the submission status.", ex);
    }
  }

  public Submission[] loadSubmissions(List<Map<String, Object>> resultSet)
      throws UploadPersistenceException {
    try {
      Submission[] submissionArray = new Submission[resultSet.size()];
      log.info(" Loading: " + submissionArray.length);
      for (var i = 0; i < resultSet.size(); i++) {
        Submission submission = new Submission();
        submission.setId(getInt(resultSet.get(i), "submission_id"));
        submissionArray[i] = submission;
      }
      return submissionArray;
    } catch (Exception ex) {
      throw new UploadPersistenceException("Error occurs while retrieving submission.", ex);
    }
  }

  public Upload[] loadUploads(List<Map<String, Object>> resultSet)
      throws UploadPersistenceException {
    try {
      Upload[] uploadArray = new Upload[resultSet.size()];
      for (var i = 0; i < resultSet.size(); i++) {
        Upload upload = new Upload();
        upload.setId(getInt(resultSet.get(i), "upload_id"));
        uploadArray[i] = upload;
      }
      return uploadArray;
    } catch (Exception ex) {
      throw new UploadPersistenceException("Error occurs while retrieving Uploads.", ex);
    }
  }

  public void addSubmissionType(SubmissionType submissionType) throws UploadPersistenceException {
    String query =
        "INSERT INTO submission_type_lu (submission_type_id, name, "
            + "description, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

    List values = new ArrayList();

    values.add(submissionType.getId());
    values.add(submissionType.getName());
    values.add(submissionType.getDescription());
    values.add(submissionType.getCreationUser());
    values.add(submissionType.getCreationTimestamp());
    values.add(submissionType.getModificationUser());
    values.add(submissionType.getModificationTimestamp());

    executeSingleCommand(query, values);
  }

  public long[] getAllSubmissionTypeIds() throws UploadPersistenceException {
    String query = "SELECT submission_type_id FROM submission_type_lu;";

    return selectIds(query, "submission_type_id");
  }

  public SubmissionType loadSubmissionType(long submissionTypeId)
      throws UploadPersistenceException {
    SubmissionType[] types = loadSubmissionTypes(new long[] {submissionTypeId});

    if (types.length > 0) {
      return types[0];
    }

    return null;
  }

  public SubmissionType[] loadSubmissionTypes(long[] submissionTypeIds)
      throws UploadPersistenceException {
    String query =
        "SELECT submission_type_id type_id, name type_name, description type_description, "
            + "create_user type_create_user, create_date type_create_date, "
            + "modify_user type_modify_user, modify_date type_modify_date FROM submission_type_lu "
            + "WHERE submission_type_id IN ";
    try {
      log.info("loadSubmissionTypes - Creating Query: " + submissionTypeIds.length);
      log.info("loadSubmissionStatuses - Processing Query");
      var rs =
          executeSqlWithParam(
              entityManager,
              query + createQuestionMarks(submissionTypeIds.length),
              newArrayList(submissionTypeIds));
      log.info("loadSubmissionStatuses - Query Processed");
      List<SubmissionType> result = new ArrayList();

      for (var row : rs) {
        result.add(populateSubmissionType(row));
      }
      return result.toArray(new SubmissionType[result.size()]);
    } catch (SQLException ex) {
      throw new UploadPersistenceException(
          "Error occurs while retrieving the submission type.", ex);
    }
  }

  public void removeSubmissionType(SubmissionType submissionType)
      throws UploadPersistenceException {
    String query = "DELETE FROM submission_type_lu WHERE submission_type_id = ?";
    List values = Collections.singletonList(new Long(submissionType.getId()));

    executeSingleCommand(query, values);
  }

  public void updateSubmissionType(SubmissionType submissionType)
      throws UploadPersistenceException {
    String query =
        "UPDATE submission_type_lu SET name = ?, description = ?, "
            + "modify_user = ?, modify_date = ? WHERE submission_type_id = ?";

    List values = new ArrayList();

    values.add(submissionType.getName());
    values.add(submissionType.getDescription());
    values.add(submissionType.getModificationUser());
    values.add(submissionType.getModificationTimestamp());
    values.add(submissionType.getId());

    executeSingleCommand(query, values);
  }

  /**
   * Creates the SubmissionType instance using the values from result set.
   *
   * @param rs the source result set.
   * @return new SubmissionType instance.
   * @throws SQLException if any SQL error occurs.
   */
  private SubmissionType populateSubmissionType(Map<String, Object> rs) throws SQLException {
    SubmissionType type = new SubmissionType();
    type.setId(getLong(rs, "type_id"));
    type.setName(getString(rs, "type_name"));
    type.setDescription(getString(rs, "type_description"));
    type.setCreationUser(getString(rs, "type_create_user"));
    type.setCreationTimestamp(getDate(rs, "type_create_date"));
    type.setModificationUser(getString(rs, "type_modify_user"));
    type.setModificationTimestamp(getDate(rs, "type_modify_date"));
    return type;
  }

  private static String createQuestionMarks(int count) {
    StringBuffer buff = new StringBuffer();

    buff.append("(?");

    for (int i = 1; i < count; i++) {
      buff.append(", ?");
    }

    buff.append(")");

    return buff.toString();
  }
}
