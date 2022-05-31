/*
 * Copyright (C) 2006-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.deliverable;

import com.topcoder.onlinereview.component.project.management.FileType;
import com.topcoder.onlinereview.component.project.management.LogMessage;
import com.topcoder.onlinereview.component.project.management.Prize;
import com.topcoder.onlinereview.component.project.management.PrizeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
 * The SqlUploadPersistence class implements the UploadPersistence interface, in order to persist to
 * the database structure given in the deliverable_management.sql script.
 *
 * <p>This class does not cache a Connection to the database. Instead a new Connection is used on
 * every method call. All methods in this class will just create and execute a single
 * PreparedStatement.
 *
 * <p>Sample configuration:
 *
 * <pre>
 * &lt;CMConfig&gt;
 *   &lt;Config name="com.topcoder.db.connectionfactory.DBConnectionFactoryImpl"&gt;
 *       &lt;Property name="connections"&gt;
 *           &lt;Property name="default"&gt;
 *               &lt;Value&gt;informix_connection&lt;/Value&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="informix_connection"&gt;
 *               &lt;Property name="producer"&gt;
 *                   &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *               &lt;/Property&gt;
 *               &lt;Property name="parameters"&gt;
 *                   &lt;Property name="jdbc_driver"&gt;
 *                       &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *                   &lt;Property name="jdbc_url"&gt;
 *                       &lt;Value&gt;jdbc:informix-sqli://localhost:9088/dm:informixserver=informix_db&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *                   &lt;Property name="user"&gt;
 *                       &lt;Value&gt;informix&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *                   &lt;Property name="password"&gt;
 *                       &lt;Value&gt;test&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *               &lt;/Property&gt;
 *           &lt;/Property&gt;
 *       &lt;/Property&gt;
 *   &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 *
 * <p>Sample usage:
 *
 * <pre>
 * // first a DBConnectionFactory instance is created.
 * DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
 * // create the instance of SqlUploadPersistence class, using the default connection name
 * UploadPersistence persistence1 = new SqlUploadPersistence(connectionFactory);
 * // or create the instance of SqlUploadPersistence class, using the given connection name
 * UploadPersistence persistence2 = new SqlUploadPersistence(connectionFactory, &quot;informix_connection&quot;);
 * // create and save submission status
 * SubmissionStatus submissionStatus = new SubmissionStatus();
 * submissionStatus.setId(10);
 * submissionStatus.setName(&quot;Active&quot;);
 * submissionStatus.setDescription(&quot;Active submission&quot;);
 * submissionStatus.setCreationUser(&quot;admin&quot;);
 * submissionStatus.setCreationTimestamp(new Date());
 * submissionStatus.setModificationUser(&quot;admin&quot;);
 * submissionStatus.setModificationTimestamp(new Date());
 * persistence.addSubmissionStatus(submissionStatus);
 * </pre>
 *
 * <pre>
 * // Create submission image
 * SubmissionImage submissionImage = new SubmissionImage();
 * submissionImage.setSubmissionId(submission.getId());
 * submissionImage.setImageId(1);
 * submissionImage.setSortOrder(1);
 * uploadPersistence.addSubmissionImage(submissionImage);
 *
 * // Update the submission image
 * submissionImage.setSortOrder(0);
 * uploadPersistence.updateSubmissionImage(submissionImage);
 *
 * // Remove the submission image
 * uploadPersistence.removeSubmissionImage(submissionImage);
 *
 * // Retrieve the MIME type with ID=1
 * MimeType mimeType = uploadPersistence.loadMimeType(1);
 *
 * // Retrieve IDs of all MIME types
 * long[] mimeTypeIds = uploadPersistence.getAllMimeTypeIds();
 *
 * // Retrieve all MIME types by their IDs
 * MimeType[] mimeTypes = uploadPersistence.loadMimeTypes(mimeTypeIds);
 *
 * // Retrieve the submissions for project with ID=1 and user with ID=1
 * Submission[] submissions = uploadPersistence.getUserSubmissionsForProject(1, 1);
 *
 * // Retrieve all submissions for project with ID=1
 * submissions = uploadPersistence.getProjectSubmissions(1);
 *
 * // Retrieve the images for submission with ID=1
 * SubmissionImage[] submissionImages = uploadPersistence.getImagesForSubmission(1);
 * </pre>
 *
 * <p>Changes in 1.1: Additional methods where added:
 *
 * <ul>
 *   <li>addSubmissionType
 *   <li>removeSubmissionType
 *   <li>updateSubmissionType
 *   <li>loadSubmissionType
 *   <li>loadSubmissionTypes
 *   <li>getAllSubmissionTypeIds
 * </ul>
 *
 * <p>Changes in 1.2:
 *
 * <ul>
 *   <li>Updated existing methods to support new Upload and Submission properties.
 *   <li>Updated the sql statement for new database schema.
 *   <li>Added methods for managing SubmissionImage entities.
 *   <li>Added methods for retrieving MimeType entities.
 *   <li>Added methods for retrieving project/user submissions.
 *   <li>Added method for retrieving images associated with submission.
 *   <li>Added logging for parameter checking.
 *   <li>Refactor logging sequence.
 *   <li>Added generic type support.
 *   <li>Updated existing methods and sql statement the support new Upload and Submission
 *       properties. A Submission can only have one upload now.
 * </ul>
 *
 * <strong>Thread Safety:</strong> This class is immutable and thread-safe in the sense that
 * multiple threads can not corrupt its internal data structures. However, the results if used from
 * multiple threads can be unpredictable as the database is changed from different threads. This can
 * equally well occur when the component is used on multiple machines or multiple instances are
 * used, so this is not a thread-safety concern.
 *
 * @author aubergineanode, saarixx, urtks, George1, gjw99
 * @version 1.2.6
 */
@Slf4j
@Component
public class SqlUploadPersistence {

  /**
   * Represents a place holder for id column in the sql statement which will be replaced by the
   * actual id column name.
   */
  private static final String ID_NAME_PLACEHOLDER = "@id";

  /**
   * Represents a place holder for table name in the sql statement which will be replaced by the
   * actual table name.
   */
  private static final String TABLE_NAME_PLACEHOLDER = "@table";

  /** Represents the sql statement to add named deliverable structure. */
  private static final String ADD_NAMED_ENTITY_SQL =
      "INSERT INTO @table "
          + "(@id, create_user, create_date, modify_user, modify_date, name, description) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?)";

  /** Represents the sql statement to remove audited deliverable structure. */
  private static final String REMOVE_ENTITY_SQL = "DELETE FROM @table WHERE @id=?";

  /** Represents the sql statement to update named deliverable structure. */
  private static final String UPDATE_NAMED_ENTITY_SQL =
      "UPDATE @table " + "SET modify_user=?, modify_date=?, name=?, description=? WHERE @id=?";

  /** Represents the sql statement to load named deliverable structure. */
  private static final String LOAD_NAMED_ENTITIES_SQL =
      "SELECT "
          + "@id, create_user, create_date, modify_user, modify_date, name, description "
          + "FROM @table WHERE @id IN ";

  /** Represents the sql statement to load all audited deliverable structure ids. */
  private static final String GET_ALL_ENTITY_IDS_SQL = "SELECT @id FROM @table";

  /**
   * Represents the sql statement to add upload.
   *
   * <p>Changes in version 1.2: add upload_desc column.
   */
  private static final String ADD_UPLOAD_SQL =
      "INSERT INTO upload "
          + "(upload_id, create_user, create_date, modify_user, modify_date, "
          + "project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, upload_desc) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  /**
   * Represents the sql statement to update upload.
   *
   * <p>Changes in version 1.2: add upload_desc column to update.
   */
  private static final String UPDATE_UPLOAD_SQL =
      "UPDATE upload "
          + "SET modify_user=?, modify_date=?, "
          + "project_id=?, project_phase_id=?, resource_id=?, upload_type_id=?, upload_status_id=?, parameter=?, upload_desc=? "
          + "WHERE upload_id=?";

  /**
   * Represents the sql statement to add submission.
   *
   * <p>Changes in version 1.2: add new columns.
   */
  private static final String ADD_SUBMISSION_SQL =
      "INSERT INTO submission "
          + "(submission_id, create_user, create_date, modify_user, modify_date, "
          + "submission_status_id, submission_type_id, screening_score, "
          + "initial_score, final_score, placement, user_rank, mark_for_purchase, prize_id, upload_id, "
          + "thurgood_job_id)"
          + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  /**
   * Represents the sql statement to add submission image.
   *
   * @since 1.2
   */
  private static final String ADD_SUBMISSION_IMAGE_SQL =
      "INSERT INTO submission_image(submission_id, image_id,"
          + " sort_order, modify_date, create_date) VALUES (?, ?, ?, ?, ?)";

  /**
   * Represents the sql statement to update submission image.
   *
   * @since 1.2
   */
  private static final String UPDATE_SUBMISSION_IMAGE_SQL =
      "UPDATE submission_image SET sort_order = ?, "
          + "modify_date = ?, create_date = ? WHERE submission_id = ? AND image_id = ?";

  /**
   * Represents the sql statement to delete submission image.
   *
   * @since 1.2
   */
  private static final String DELETE_SUBMISSION_IMAGE_SQL =
      "DELETE FROM submission_image" + " WHERE submission_id = ? AND image_id = ?";

  /**
   * Represents the sql statement to update submission.
   *
   * <p>Changes in version 1.2: changes to support new columns.
   */
  private static final String UPDATE_SUBMISSION_SQL =
      "UPDATE submission "
          + "SET modify_user = ?, modify_date = ?, submission_status_id = ?, submission_type_id = ?, "
          + "screening_score = ?, initial_score = ?, final_score = ?, placement = ?, "
          + " user_rank = ?, mark_for_purchase = ?, prize_id = ?, upload_id = ?, thurgood_job_id = ?"
          + " WHERE submission_id = ?";

  /**
   * Represents the sql statement to load uploads.
   *
   * <p>Changes in 1.2: add upload_desc column.
   */
  private static final String LOAD_UPLOADS_SQL =
      "SELECT "
          + "upload.upload_id, upload.create_user as upload_create_user, upload.create_date as upload_create_date, upload.modify_user, upload.modify_date as upload_modify_user, "
          + "upload.project_id, upload.project_phase_id, upload.resource_id, upload.parameter as upload_parameter, upload.upload_desc, "
          + "upload_type_lu.upload_type_id, upload_type_lu.create_user as upload_type_create_user, upload_type_lu.create_date as upload_type_create_date, "
          + "upload_type_lu.modify_user as upload_type_modify_user, upload_type_lu.modify_date as upload_type_modify_date, "
          + "upload_type_lu.name as upload_type_name, upload_type_lu.description as upload_type_description, "
          + "upload_status_lu.upload_status_id, upload_status_lu.create_user as upload_status_create_user, upload_status_lu.create_date as upload_status_create_date, "
          + "upload_status_lu.modify_user as upload_status_modify_user, upload_status_lu.modify_date as upload_status_modify_date, "
          + "upload_status_lu.name as upload_status_name, upload_status_lu.description as upload_status_description, upload.url "
          + "FROM upload INNER JOIN upload_type_lu "
          + "ON upload.upload_type_id=upload_type_lu.upload_type_id "
          + "INNER JOIN upload_status_lu "
          + "ON upload.upload_status_id=upload_status_lu.upload_status_id "
          + "WHERE upload_id IN ";

  /**
   * Represents the sql statement to load mime types.
   *
   * @since 1.2
   */
  private static final String LOAD_MIME_TYPES_SQL =
      "SELECT mime_type_id, file_type_lu.file_type_id,"
          + "file_type_lu.file_type_desc, file_type_lu.sort, file_type_lu.image_file_ind, file_type_lu.extension,"
          + "file_type_lu.bundled_file_ind, mime_type_desc FROM mime_type_lu "
          + "INNER JOIN file_type_lu ON mime_type_lu.file_type_id = file_type_lu.file_type_id "
          + "WHERE mime_type_id IN";

  /**
   * Represents the sql statement to load submissions.
   *
   * <p>Changes in version 1.2: updated according to the new database schema.
   */
  private static final String LOAD_SUBMISSIONS_SQL =
      "SELECT "
          + "submission.submission_id, submission.create_user as submission_create_user, submission.create_date as submission_create_date, "
          + "submission.modify_user as submission_modify_user, submission.modify_date as submission_modify_date, submission_status_lu.submission_status_id, "
          + "submission_status_lu.create_user as submission_status_create_user, submission_status_lu.create_date as submission_status_create_date, "
          + "submission_status_lu.modify_user as submission_status_modify_user, submission_status_lu.modify_date as submission_status_modify_date, "
          + "submission_status_lu.name as submission_status_name, submission_status_lu.description as submission_status_description, "
          + "submission_type_lu.submission_type_id, "
          + "submission_type_lu.create_user as submission_type_create_user, submission_type_lu.create_date as submission_type_create_date, "
          + "submission_type_lu.modify_user as submission_type_modify_user, submission_type_lu.modify_date as submission_type_modify_date, "
          + "submission_type_lu.name as submission_type_name, submission_type_lu.description as submission_type_description, "
          + "submission.screening_score, submission.initial_score, submission.final_score, submission.placement, "
          + "submission.user_rank, submission.mark_for_purchase, submission.thurgood_job_id, "
          + "prize.prize_id, prize.place, prize.prize_amount, prize.prize_type_id, prize.number_of_submissions, "
          + "prize.create_user as prize_create_user, prize.create_date as prize_create_date, prize.modify_user as prize_modify_user, prize.modify_date as prize_modify_date, "
          + "prize_type_lu.prize_type_desc, "
          + "upload.upload_id, upload.create_user as upload_create_user, upload.create_date as upload_create_date, upload.modify_user as upload_modify_user, upload.modify_date as upload_modify_date, "
          + "upload.project_id, upload.project_phase_id, upload.resource_id, upload.parameter as upload_parameter, upload.upload_desc, "
          + "upload_type_lu.upload_type_id, upload_type_lu.create_user as upload_type_create_user, upload_type_lu.create_date as upload_type_create_date, "
          + "upload_type_lu.modify_user as upload_type_modify_user, upload_type_lu.modify_date as upload_type_modify_date, "
          + "upload_type_lu.name as upload_type_name, upload_type_lu.description as upload_type_description, "
          + "upload_status_lu.upload_status_id, upload_status_lu.create_user as upload_status_create_user, upload_status_lu.create_date as upload_status_create_date, "
          + "upload_status_lu.modify_user as upload_status_modify_user, upload_status_lu.modify_date as upload_status_modify_date, "
          + "upload_status_lu.name as upload_status_name, upload_status_lu.description as upload_status_description, upload.url "
          + "FROM submission "
          + "INNER JOIN submission_status_lu ON submission.submission_status_id "
          + "= submission_status_lu.submission_status_id "
          + "INNER JOIN submission_type_lu ON submission.submission_type_id = submission_type_lu.submission_type_id "
          + "LEFT JOIN prize ON submission.prize_id = prize.prize_id "
          + "LEFT JOIN prize_type_lu ON prize.prize_type_id = prize_type_lu.prize_type_id "
          + "INNER JOIN upload ON submission.upload_id = upload.upload_id "
          + "INNER JOIN upload_type_lu ON upload.upload_type_id=upload_type_lu.upload_type_id "
          + "INNER JOIN upload_status_lu ON upload.upload_status_id=upload_status_lu.upload_status_id "
          + "WHERE submission.submission_id IN ";

  /**
   * Represents the sql statement for retrieving the submission images for the given submission.
   *
   * @since 1.2
   */
  private static final String GET_SUBMISSION_IMAGES_FOR_SUBMISSION_SQL =
      "SELECT"
          + " image_id, sort_order, modify_date, create_date FROM submission_image WHERE submission_id = ?";

  @Autowired
  @Qualifier("tcsJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  /**
   * Adds the given uploadType to the persistence. The id of the upload type must already be
   * assigned, as must all the other fields needed for persistence.
   *
   * @param uploadType The upload type to add
   * @throws IllegalArgumentException If uploadType is null If isValidToPersist returns false If
   *     there is an error when making the change in the persistence
   */
  public void addUploadType(UploadType uploadType) throws UploadPersistenceException {

    assertEntityNotNullAndValidToPersist(uploadType, "uploadType");

    addNameEntity(uploadType, "upload_type_lu", "upload_type_id");
  }

  /**
   * Adds the given uploadStatus to the persistence. The id of the upload status must already be
   * assigned, as must all the other fields needed for persistence.
   *
   * @param uploadStatus The upload status to add
   * @throws IllegalArgumentException If uploadStatus is null If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void addUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
    assertEntityNotNullAndValidToPersist(uploadStatus, "uploadStatus");

    addNameEntity(uploadStatus, "upload_status_lu", "upload_status_id");
  }

  /**
   * Adds the given submission status to the persistence. The id of the submission status must
   * already be assigned, as must all the other fields needed for persistence.
   *
   * @param submissionStatus The submission status to add
   * @throws IllegalArgumentException If submissionStatus is null If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void addSubmissionStatus(SubmissionStatus submissionStatus)
      throws UploadPersistenceException {
    assertEntityNotNullAndValidToPersist(submissionStatus, "submissionStatus");

    addNameEntity(submissionStatus, "submission_status_lu", "submission_status_id");
  }

  /**
   * Removes the given upload type (by id) from the persistence. The id of the upload type can not
   * be UNSET_ID, but the other fields do not matter.
   *
   * @param uploadType The upload type to remove
   * @throws IllegalArgumentException If uploadType is null If the id is UNSET_ID
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void removeUploadType(UploadType uploadType) throws UploadPersistenceException {
    Helper.assertObjectNotNull(uploadType, "uploadType");
    Helper.assertIdNotUnset(uploadType.getId(), "uploadType id");

    removeEntity(uploadType, "upload_type_lu", "upload_type_id");
  }

  /**
   * Removes the given upload status (by id) from the persistence. The id of the upload status can
   * not be UNSET_ID, but the other fields do not matter.
   *
   * @param uploadStatus The upload status to remove
   * @throws IllegalArgumentException If uploadStatus is null If the id is UNSET_ID
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void removeUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
    Helper.assertObjectNotNull(uploadStatus, "uploadStatus");
    Helper.assertIdNotUnset(uploadStatus.getId(), "uploadStatus id");

    removeEntity(uploadStatus, "upload_status_lu", "upload_status_id");
  }

  /**
   * Removes the given submission status (by id) from the persistence. The id of the submission
   * status can not be UNSET_ID, but the other fields do not matter.
   *
   * @param submissionStatus The submission status to remove
   * @throws IllegalArgumentException If submissionStatus is null If the id is UNSET_ID
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void removeSubmissionStatus(SubmissionStatus submissionStatus)
      throws UploadPersistenceException {
    Helper.assertObjectNotNull(submissionStatus, "submissionStatus");
    Helper.assertIdNotUnset(submissionStatus.getId(), "submissionStatus id");

    removeEntity(submissionStatus, "submission_status_lu", "submission_status_id");
  }

  /**
   * Removes the given upload (by id) from the persistence. The id of the upload can not be
   * UNSET_ID, but the other fields do not matter.
   *
   * @param upload The upload to remove
   * @throws IllegalArgumentException If upload is null If the id is UNSET_ID
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void removeUpload(Upload upload) throws UploadPersistenceException {
    Helper.assertObjectNotNull(upload, "upload");
    Helper.assertIdNotUnset(upload.getId(), "upload id");

    removeEntity(upload, "upload", "upload_id");
  }

  /**
   * Removes the given submission (by id) from the persistence. The id of the submission can not be
   * UNSET_ID, but the other fields do not matter.
   *
   * @param submission The submission to remove
   * @throws IllegalArgumentException If submission is null If the id is UNSET_ID
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void removeSubmission(Submission submission) throws UploadPersistenceException {
    Helper.assertObjectNotNull(submission, "submission");
    Helper.assertIdNotUnset(submission.getId(), "submission id");

    removeEntity(submission, "submission", "submission_id");
  }

  /**
   * Updates the given upload type in the persistence. The id of the uploadType can not be UNSET_ID,
   * and all other fields needed for persistence must also be assigned.
   *
   * @param uploadType The upload type to update
   * @throws IllegalArgumentException If uploadType is null If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void updateUploadType(UploadType uploadType) throws UploadPersistenceException {
    assertEntityNotNullAndValidToPersist(uploadType, "uploadType");

    updateNamedEntity(uploadType, "upload_type_lu", "upload_type_id");
  }

  /**
   * Updates the given upload status in the persistence. The id of the uploadStatus can not be
   * UNSET_ID, and all other fields needed for persistence must also be assigned.
   *
   * @param uploadStatus The upload status to update
   * @throws IllegalArgumentException If uploadStatus is null If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void updateUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
    assertEntityNotNullAndValidToPersist(uploadStatus, "uploadStatus");

    updateNamedEntity(uploadStatus, "upload_status_lu", "upload_status_id");
  }

  /**
   * Updates the given submission status in the persistence. The id of the submissionStats can not
   * be UNSET_ID, and all other fields needed for persistence must also be assigned.
   *
   * @param submissionStatus The submissionStatus to update
   * @throws IllegalArgumentException If submissionStatus is null If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void updateSubmissionStatus(SubmissionStatus submissionStatus)
      throws UploadPersistenceException {
    assertEntityNotNullAndValidToPersist(submissionStatus, "submissionStatus");

    updateNamedEntity(submissionStatus, "submission_status_lu", "submission_status_id");
  }

  /**
   * Gets the ids of all upload types in the persistence. The individual upload types can then be
   * loaded with the loadUploadType method.
   *
   * @return The ids of all upload types
   * @throws UploadPersistenceException If there is an error when reading the persistence store
   */
  public Long[] getAllUploadTypeIds() throws UploadPersistenceException {
    return getAllEntityIds("upload_type_lu", "upload_type_id");
  }

  /**
   * Gets the ids of all upload statuses in the persistence. The individual upload statuses can then
   * be loaded with the loadUploadStatus method.
   *
   * @return The ids of all upload statuses
   * @throws UploadPersistenceException If there is an error when reading the persistence store
   */
  public Long[] getAllUploadStatusIds() throws UploadPersistenceException {
    return getAllEntityIds("upload_status_lu", "upload_status_id");
  }

  /**
   * Gets the ids of all submission statuses in the persistence. The individual submission statuses
   * can then be loaded with the loadSubmissionStatus method.
   *
   * @return The ids of all submission statuses
   * @throws UploadPersistenceException If there is an error when reading the persistence store
   */
  public Long[] getAllSubmissionStatusIds() throws UploadPersistenceException {
    return getAllEntityIds("submission_status_lu", "submission_status_id");
  }

  /**
   * Adds the given submission type to the persistence. The id of the submission type must be
   * already assigned, as must all the other fields needed for persistence.
   *
   * @param submissionType The submission type to add
   * @throws IllegalArgumentException If submissionType is null or isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   * @since 1.1
   */
  public void addSubmissionType(SubmissionType submissionType) throws UploadPersistenceException {
    assertEntityNotNullAndValidToPersist(submissionType, "submissionType");

    addNameEntity(submissionType, "submission_type_lu", "submission_type_id");
  }

  /**
   * Removes the given submission type (by id) from the persistence. The id of the submission type
   * can not be UNSET_ID, but the other fields do not matter.
   *
   * @param submissionType The submission type to remove
   * @throws IllegalArgumentException If submissionType is null or the id is equal to UNSET_ID
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   * @since 1.1
   */
  public void removeSubmissionType(SubmissionType submissionType)
      throws UploadPersistenceException {
    Helper.assertObjectNotNull(submissionType, "submissionType");
    Helper.assertIdNotUnset(submissionType.getId(), "submissionType id");

    removeEntity(submissionType, "submission_type_lu", "submission_type_id");
  }

  /**
   * Updates the given submission type in the persistence. The id of the submission type can not be
   * UNSET_ID, and all other fields needed for persistence must also be assigned.
   *
   * @param submissionType The submissionType to update
   * @throws IllegalArgumentException If submissionType is null or isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   * @since 1.1
   */
  public void updateSubmissionType(SubmissionType submissionType)
      throws UploadPersistenceException {
    assertEntityNotNullAndValidToPersist(submissionType, "submissionType");

    updateNamedEntity(submissionType, "submission_type_lu", "submission_type_id");
  }

  /**
   * Loads the SubmissionType with the given id from persistence. Returns null if there is no
   * SubmissionType with the given id.
   *
   * @param submissionTypeId The id of the item to retrieve
   * @return The loaded SubmissionType or null
   * @throws IllegalArgumentException if submissionTypeId is <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   * @since 1.1
   */
  public SubmissionType loadSubmissionType(long submissionTypeId)
      throws UploadPersistenceException {
    Helper.assertIdNotUnset(submissionTypeId, "submissionTypeId");

    SubmissionType[] submissionTypes = loadSubmissionTypes(new Long[] {submissionTypeId});
    return submissionTypes.length == 0 ? null : submissionTypes[0];
  }

  /**
   * Loads all SubmissionTypes with the given ids from persistence. May return a 0-length array.
   *
   * @param submissionTypeIds The ids of the objects to load
   * @return The loaded SubmissionTypes
   * @throws IllegalArgumentException if any id is <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   * @since 1.1
   */
  public SubmissionType[] loadSubmissionTypes(Long[] submissionTypeIds)
      throws UploadPersistenceException {
    Helper.assertLongArrayNotNullAndOnlyHasPositive(submissionTypeIds, "submissionTypeIds");

    return (SubmissionType[])
        loadNamedEntities(
            submissionTypeIds, SubmissionType.class, "submission_type_lu", "submission_type_id");
  }

  /**
   * Gets the ids of all submission types in the persistence. The individual submission types can
   * then be loaded with the loadSubmissionType method.
   *
   * @return The ids of all submission types
   * @throws UploadPersistenceException If there is an error reading the persistence store
   * @since 1.1
   */
  public Long[] getAllSubmissionTypeIds() throws UploadPersistenceException {
    return getAllEntityIds("submission_type_lu", "submission_type_id");
  }

  /**
   * Loads the UploadType with the given id from persistence. Returns null if there is no UploadType
   * with the given id.
   *
   * @param uploadTypeId The id of the item to retrieve
   * @return The loaded UploadType or null
   * @throws IllegalArgumentException if uploadTypeId is <= 0
   * @throws UploadPersistenceException if there is an error when reading the persistence data
   */
  public UploadType loadUploadType(long uploadTypeId) throws UploadPersistenceException {
    Helper.assertIdNotUnset(uploadTypeId, "uploadTypeId");

    UploadType[] uploadTypes = loadUploadTypes(new Long[] {uploadTypeId});
    return uploadTypes.length == 0 ? null : uploadTypes[0];
  }

  /**
   * Loads the UploadStatus with the given id from persistence. Returns null if there is no
   * UploadStatus with the given id.
   *
   * @param uploadStatusId The id of the item to retrieve
   * @return The loaded UploadStatus or null
   * @throws IllegalArgumentException if uploadStatusId is <= 0
   * @throws UploadPersistenceException if there is an error when reading the persistence data
   */
  public UploadStatus loadUploadStatus(long uploadStatusId) throws UploadPersistenceException {
    Helper.assertIdNotUnset(uploadStatusId, "uploadStatusId");

    UploadStatus[] uploadStatuses = loadUploadStatuses(new Long[] {uploadStatusId});
    return uploadStatuses.length == 0 ? null : uploadStatuses[0];
  }

  /**
   * Loads the SubmissionStatus with the given id from persistence. Returns null if there is no
   * SubmissionStatus with the given id.
   *
   * @param submissionStatusId The id of the item to retrieve
   * @return The loaded SubmissionStatus or null
   * @throws IllegalArgumentException if submissionStatusId is <= 0
   * @throws UploadPersistenceException if there is an error when reading the persistence data
   */
  public SubmissionStatus loadSubmissionStatus(long submissionStatusId)
      throws UploadPersistenceException {
    Helper.assertIdNotUnset(submissionStatusId, "submissionStatusId");

    SubmissionStatus[] submissionStatuses = loadSubmissionStatuses(new Long[] {submissionStatusId});
    return submissionStatuses.length == 0 ? null : submissionStatuses[0];
  }

  /**
   * Loads all UploadTypes with the given ids from persistence. May return a 0-length array.
   *
   * @param uploadTypeIds The ids of the objects to load
   * @return the loaded UploadTypes
   * @throws IllegalArgumentException if uploadTypeIds is null, or any id is <= 0
   * @throws UploadPersistenceException if there is an error when reading the persistence data
   */
  public UploadType[] loadUploadTypes(Long[] uploadTypeIds) throws UploadPersistenceException {
    Helper.assertLongArrayNotNullAndOnlyHasPositive(uploadTypeIds, "uploadTypeIds");

    return (UploadType[])
        loadNamedEntities(uploadTypeIds, UploadType.class, "upload_type_lu", "upload_type_id");
  }

  /**
   * Loads all UploadStatuses with the given ids from persistence. May return a 0-length array.
   *
   * @param uploadStatusIds The ids of the objects to load
   * @return the loaded UploadStatuses
   * @throws IllegalArgumentException if uploadStatusIds is null, or any id is <= 0
   * @throws UploadPersistenceException if there is an error when reading the persistence data
   */
  public UploadStatus[] loadUploadStatuses(Long[] uploadStatusIds)
      throws UploadPersistenceException {
    Helper.assertLongArrayNotNullAndOnlyHasPositive(uploadStatusIds, "uploadStatusIds");

    return (UploadStatus[])
        loadNamedEntities(
            uploadStatusIds, UploadStatus.class, "upload_status_lu", "upload_status_id");
  }

  /**
   * Loads all SubmissionStatuses with the given ids from persistence. May return a 0-length array.
   *
   * @param submissionStatusIds The ids of the objects to load
   * @return the loaded SubmissionStatuses
   * @throws IllegalArgumentException if submissionStatusIds is null, or any id is <= 0
   * @throws UploadPersistenceException if there is an error when reading the persistence data
   */
  public SubmissionStatus[] loadSubmissionStatuses(Long[] submissionStatusIds)
      throws UploadPersistenceException {
    Helper.assertLongArrayNotNullAndOnlyHasPositive(submissionStatusIds, "submissionStatusIds");

    return (SubmissionStatus[])
        loadNamedEntities(
            submissionStatusIds,
            SubmissionStatus.class,
            "submission_status_lu",
            "submission_status_id");
  }

  /**
   * Adds the given upload to the persistence. The id of the upload must already be assigned, as
   * must all the other fields needed for persistence.
   *
   * <p>Change in 1.2:
   *
   * <ul>
   *   <li>The method is updated to support description property of Upload.
   * </ul>
   *
   * @param upload The upload to add
   * @throws IllegalArgumentException If upload is null If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void addUpload(Upload upload) throws UploadPersistenceException {
    assertEntityNotNullAndValidToPersist(upload, "upload");
    // add upload to database
    executeUpdateSql(
        jdbcTemplate,
        ADD_UPLOAD_SQL,
        newArrayList(
            upload.getId(),
            upload.getCreationUser(),
            upload.getCreationTimestamp(),
            upload.getModificationUser(),
            upload.getModificationTimestamp(),
            upload.getProject(),
            upload.getProjectPhase(),
            upload.getOwner(),
            upload.getUploadType().getId(),
            upload.getUploadStatus().getId(),
            upload.getParameter(),
            upload.getDescription()));
  }

  /**
   * Updates the given upload in the persistence. The id of the upload can not be UNSET_ID, and all
   * other fields needed for persistence must also be assigned.
   *
   * <p>Change in 1.2: The method is updated to support description property of Upload.
   *
   * @param upload The upload to update
   * @throws IllegalArgumentException If upload is null If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void updateUpload(Upload upload) throws UploadPersistenceException {
    assertEntityNotNullAndValidToPersist(upload, "upload");
    // update upload to database
    executeUpdateSql(
        jdbcTemplate,
        UPDATE_UPLOAD_SQL,
        newArrayList(
            upload.getModificationUser(),
            upload.getModificationTimestamp(),
            upload.getProject(),
            upload.getProjectPhase(),
            upload.getOwner(),
            upload.getUploadType().getId(),
            upload.getUploadStatus().getId(),
            upload.getParameter(),
            upload.getDescription(),
            upload.getId()));
  }

  /**
   * Adds the given submission to the persistence. The id of the submission must already be
   * assigned, as must all the other fields needed for persistence.
   *
   * <p>Change in 1.2: The method is updated to support userRank and extra properties of Submission.
   * Additionally removal of upload_id field is handled. Association between submission and the list
   * of uploads is created properly. The foreign key relation to prize table is added if the prize
   * property set.
   *
   * @param submission The submission to add
   * @throws IllegalArgumentException If submission is null If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void addSubmission(Submission submission) throws UploadPersistenceException {
    assertEntityNotNullAndValidToPersist(submission, "submission");

    // add submission to database
    executeUpdateSql(
        jdbcTemplate,
        ADD_SUBMISSION_SQL,
        newArrayList(
            submission.getId(),
            submission.getCreationUser(),
            submission.getCreationTimestamp(),
            submission.getModificationUser(),
            submission.getModificationTimestamp(),
            submission.getSubmissionStatus().getId(),
            submission.getSubmissionType().getId(),
            submission.getScreeningScore(),
            submission.getInitialScore(),
            submission.getFinalScore(),
            submission.getPlacement(),
            submission.getUserRank(),
            submission.isExtra(),
            submission.getPrize() != null ? submission.getPrize().getId() : null,
            submission.getUpload() != null ? submission.getUpload().getId() : null,
            submission.getThurgoodJobId()));
  }

  /**
   * Updates the given submission in the persistence. The id of the submission can not be UNSET_ID,
   * and all other fields needed for persistence must also be assigned.
   *
   * <p>Change in 1.2: The method is updated to support userRank and extra properties of Submission.
   * Additionally removal of upload_id field is handled. Association between submission and the list
   * of uploads is updated properly. The foreign key relation to prize table is updated if the prize
   * property set.
   *
   * @param submission The submission to add
   * @throws IllegalArgumentException If submission is null If isValidToPersist returns false
   * @throws UploadPersistenceException If there is an error when making the change in the
   *     persistence
   */
  public void updateSubmission(Submission submission) throws UploadPersistenceException {
    assertEntityNotNullAndValidToPersist(submission, "submission");
    // update submission to database
    executeUpdateSql(
        jdbcTemplate,
        UPDATE_SUBMISSION_SQL,
        newArrayList(
            submission.getModificationUser(),
            submission.getModificationTimestamp(),
            submission.getSubmissionStatus().getId(),
            submission.getSubmissionType().getId(),
            submission.getScreeningScore(),
            submission.getInitialScore(),
            submission.getFinalScore(),
            submission.getPlacement(),
            submission.getUserRank(),
            submission.isExtra(),
            submission.getPrize() != null ? submission.getPrize().getId() : null,
            submission.getUpload() != null ? submission.getUpload().getId() : null,
            submission.getThurgoodJobId(),
            submission.getId()));
  }

  /**
   * Loads the Upload with the given id from persistence. Returns null if there is no Upload with
   * the given id.
   *
   * @param uploadId The id of the item to retrieve
   * @return The loaded Upload or null
   * @throws IllegalArgumentException if uploadId is <= 0
   * @throws UploadPersistenceException if there is an error when reading the persistence data
   */
  public Upload loadUpload(Long uploadId) throws UploadPersistenceException {
    Helper.assertIdNotUnset(uploadId, "uploadId");

    Upload[] uploads = loadUploads(new Long[] {uploadId});
    return uploads.length == 0 ? null : uploads[0];
  }

  /**
   * Loads all Uploads with the given ids from persistence. May return a 0-length array.
   *
   * <p>Change in 1.2: The method is updated to support description property of Upload.
   *
   * @param uploadIds The ids of uploads to load
   * @return The loaded uploads
   * @throws IllegalArgumentException if uploadIds is null or any id is <= 0
   * @throws UploadPersistenceException if there is an error when reading the persistence data
   */
  public Upload[] loadUploads(Long[] uploadIds) throws UploadPersistenceException {
    Helper.assertLongArrayNotNullAndOnlyHasPositive(uploadIds, "uploadIds");

    // simply return an empty Upload array if uploadIds is empty
    if (uploadIds.length == 0) {
      return new Upload[0];
    }

    List<Map<String, Object>> rows =
        executeSql(jdbcTemplate, LOAD_UPLOADS_SQL + makeIdListString(uploadIds));
    return loadUploads(rows);
  }

  /**
   * Loads uploads from the result of the SELECT operation.
   *
   * <p>Change in 1.2: New description property of Upload is additionally loaded.
   *
   * @param resultSet The result of the SELECT operation.
   * @return an array of loaded uploads.
   * @throws UploadPersistenceException if any error occurs while loading uploads.
   */
  public Upload[] loadUploads(List<Map<String, Object>> resultSet)
      throws UploadPersistenceException {
    Helper.assertObjectNotNull(resultSet, "resultSet");

    if (resultSet.isEmpty()) {
      return new Upload[0];
    }

    List<Upload> uploads = new ArrayList<Upload>();

    for (Map<String, Object> row : resultSet) {
      uploads.add(loadUpload(row));
    }

    return uploads.toArray(new Upload[uploads.size()]);
  }

  /**
   * Loads the Submission with the given id from persistence. Returns null if there is no Submission
   * with the given id.
   *
   * @param submissionId The id of the item to retrieve
   * @return The loaded Submission or null
   * @throws IllegalArgumentException if submissionId is <= 0
   * @throws UploadPersistenceException if there is an error when reading the persistence data
   */
  public Submission loadSubmission(Long submissionId) throws UploadPersistenceException {
    Helper.assertIdNotUnset(submissionId, "submissionId");

    Submission[] submissions = loadSubmissions(new Long[] {submissionId});
    return submissions.length == 0 ? null : submissions[0];
  }

  /**
   * Loads all Submissions with the given ids from persistence. May return a 0-length array.
   *
   * <p>Changes in 1.2: The method is updated to support userRank, extra, and prize properties of
   * Submission. A list of images for each submission is loaded by using getImagesForSubmission()
   * method. A list of uploads for each submission is loaded by using getUploadsForSubmission()
   * method.
   *
   * @param submissionIds The ids of submissions to load
   * @return The loaded submissions
   * @throws IllegalArgumentException if submissionIds is null, or any id is <= 0
   * @throws UploadPersistenceException if there is an error when reading the persistence data
   */
  public Submission[] loadSubmissions(Long[] submissionIds) throws UploadPersistenceException {
    Helper.assertLongArrayNotNullAndOnlyHasPositive(submissionIds, "submissionIds");
    // simply return an empty Submission array if submissionIds is empty
    if (submissionIds.length == 0) {
      return new Submission[0];
    }
    List<Map<String, Object>> rows =
        executeSql(jdbcTemplate, LOAD_SUBMISSIONS_SQL + makeIdListString(submissionIds));
    return loadSubmissions(rows);
  }

  /**
   * Loads submissions from the result of the SELECT operation.
   *
   * <p>Changes in 1.2: Step for loading of Upload from the result set is removed (refer to private
   * loadSubmission(CustomResultSet) method). Additionally userRank, extra and prize properties of
   * Submission are loaded. Notes, images and uploads properties are not loaded, please use the
   * corresponding methods.
   *
   * @param resultSet The result of the SELECT operation.
   * @return an array of loaded submissions.
   * @throws UploadPersistenceException if any error occurs while loading submissions.
   */
  public Submission[] loadSubmissions(List<Map<String, Object>> resultSet)
      throws UploadPersistenceException {
    Helper.assertObjectNotNull(resultSet, "resultSet");

    if (resultSet.isEmpty()) {
      return new Submission[0];
    }

    List<Submission> submissions = new ArrayList<Submission>();

    for (Map<String, Object> row : resultSet) {
      submissions.add(loadSubmission(row));
    }

    return submissions.toArray(new Submission[submissions.size()]);
  }

  /**
   * Adds the given submission image to persistence.
   *
   * @param submissionImage the submission image to be created in persistence
   * @throws IllegalArgumentException If submissionImage is null, or
   *     submissionImage.isValidToPersist() returns false
   * @throws UploadPersistenceException If some error occurred when accessing the persistence
   * @since 1.2
   */
  public void addSubmissionImage(SubmissionImage submissionImage)
      throws UploadPersistenceException {
    Helper.assertObjectNotNull(submissionImage, "submissionImage");
    if (!submissionImage.isValidToPersist()) {
      log.error("The entity [SubmissionImage] is not valid to persist.");
      throw new IllegalArgumentException("The entity [SubmissionImage] is not valid to persist.");
    }
    log.debug(
        "add record into table[submission_image] with submission id: "
            + submissionImage.getSubmissionId()
            + ", and image id:"
            + submissionImage.getImageId());
    executeUpdateSql(
        jdbcTemplate,
        ADD_SUBMISSION_IMAGE_SQL,
        newArrayList(
            submissionImage.getSubmissionId(),
            submissionImage.getImageId(),
            submissionImage.getSortOrder(),
            submissionImage.getModifyDate(),
            submissionImage.getCreateDate()));
  }

  /**
   * Updates the submission image to persistence.
   *
   * @param submissionImage the submission image with updated data
   * @throws IllegalArgumentException If submissionImage is null, or
   *     submissionImage.isValidToPersist() returns false
   * @throws UploadPersistenceException If some error occurred when accessing the persistence
   * @since 1.2
   */
  public void updateSubmissionImage(SubmissionImage submissionImage)
      throws UploadPersistenceException {
    log.debug(new LogMessage(null, null, "update SubmissionImage").toString());

    Helper.assertObjectNotNull(submissionImage, "submissionImage");

    if (!submissionImage.isValidToPersist()) {
      log.error("The entity [SubmissionImage] is not valid to persist.");
      throw new IllegalArgumentException("The entity [SubmissionImage] is not valid to persist.");
    }

    log.debug(
        "update record into table[submission_image] with submission id: "
            + submissionImage.getSubmissionId()
            + ", and image id:"
            + submissionImage.getImageId());

    // build arguments
    Object[] queryArgs =
        new Object[] {
          submissionImage.getSortOrder(),
          submissionImage.getModifyDate(),
          submissionImage.getCreateDate(),
          submissionImage.getSubmissionId(),
          submissionImage.getImageId()
        };

    String errorMessage =
        "Failed to update record into table[submission_image] with submission id: "
            + submissionImage.getSubmissionId()
            + ", and image id:"
            + submissionImage.getImageId();
    executeUpdateSql(
        jdbcTemplate,
        UPDATE_SUBMISSION_IMAGE_SQL,
        newArrayList(
            submissionImage.getSortOrder(),
            submissionImage.getModifyDate(),
            submissionImage.getCreateDate(),
            submissionImage.getSubmissionId(),
            submissionImage.getImageId()));
  }

  /**
   * Removes the submission image to persistence.
   *
   * @param submissionImage the submission image to be removed from persistence
   * @throws IllegalArgumentException If submissionImage is null, submissionImage.getSubmissionId()
   *     <= 0 or submissionImage.getImageId() <= 0
   * @throws UploadPersistenceException If some error occurred when accessing the persistence
   * @since 1.2
   */
  public void removeSubmissionImage(SubmissionImage submissionImage)
      throws UploadPersistenceException {
    log.debug(new LogMessage(null, null, "remove SubmissionImage.").toString());
    Helper.assertObjectNotNull(submissionImage, "submissionImage");
    assertLongBePositive(
        submissionImage.getSubmissionId(), "submission id of the given submission image");
    assertLongBePositive(submissionImage.getImageId(), "image id of the given submission image");
    log.debug(
        "remove record into table[submission_image] with submission id: "
            + submissionImage.getSubmissionId()
            + ", and image id:"
            + submissionImage.getImageId());
    executeUpdateSql(
        jdbcTemplate,
        DELETE_SUBMISSION_IMAGE_SQL,
        newArrayList(submissionImage.getSubmissionId(), submissionImage.getImageId()));
  }

  /**
   * Loads the MimeType with the given id from persistence. Returns null if there is no MimeType
   * with the given id.
   *
   * @param mimeTypeId The id of the item to retrieve
   * @return the loaded MimeType or null
   * @throws IllegalArgumentException if mimeTypeId <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   * @since 1.2
   */
  public MimeType loadMimeType(long mimeTypeId) throws UploadPersistenceException {
    log.debug(
        new LogMessage(mimeTypeId, null, "Load MimeType. Delegate to loadMimeTypes(long[]).")
            .toString());

    assertLongBePositive(mimeTypeId, "mimeTypeId");

    MimeType[] mimeTypes = loadMimeTypes(new Long[] {mimeTypeId});
    return mimeTypes.length == 0 ? null : mimeTypes[0];
  }

  /**
   * Gets the ids of all MIME types in the persistence. The individual MIME types can then be loaded
   * with the loadMimeType method.
   *
   * @return The ids of all MIME types
   * @throws UploadPersistenceException if there is an error reading the persistence store
   * @since 1.2
   */
  public Long[] getAllMimeTypeIds() throws UploadPersistenceException {
    log.debug(new LogMessage(null, null, "Load all MimeType ids in persistence.").toString());

    return getAllEntityIds("mime_type_lu", "mime_type_id");
  }

  /**
   * Loads all MimeTypes with the given ids from persistence. May return an empty array.
   *
   * @param mimeTypeIds The ids of the objects to load
   * @return the loaded MimeTypes
   * @throws IllegalArgumentException if the mimeTypeIds is null or any id <= 0
   * @throws UploadPersistenceException if there is an error reading the persistence data
   * @since 1.2
   */
  public MimeType[] loadMimeTypes(Long[] mimeTypeIds) throws UploadPersistenceException {
    log.debug(new LogMessage(null, null, "Load MimeType with ids:" + mimeTypeIds).toString());

    Helper.assertLongArrayNotNullAndOnlyHasPositive(mimeTypeIds, "mimeTypeIds");

    // simply return an empty array if mimeTypeIds is empty
    if (mimeTypeIds.length == 0) {
      return new MimeType[0];
    }

    List<Map<String, Object>> rows =
        executeSql(jdbcTemplate, LOAD_MIME_TYPES_SQL + makeIdListString(mimeTypeIds));

    MimeType[] mimeTypes = new MimeType[rows.size()];

    for (int i = 0; i < rows.size(); i++) {
      MimeType mimeType = new MimeType();
      loadMimeTypeFieldsSequentially(mimeType, rows.get(i));
      mimeTypes[i] = mimeType;
    }

    return mimeTypes;
  }

  /**
   * Retrieves the images for submission with the given ID. If submissionId is unknown, this method
   * returns an empty array.
   *
   * @param submissionId the ID of the submission
   * @return the retrieved images for submission (not null, doesn't contain null)
   * @throws IllegalArgumentException If submissionId <= 0
   * @throws UploadPersistenceException If some error occurred when accessing the persistence
   * @since 1.2
   */
  public SubmissionImage[] getImagesForSubmission(long submissionId)
      throws UploadPersistenceException {
    log.debug(
        new LogMessage(
                null,
                null,
                MessageFormat.format(
                    "Load SubmissionImages for submission id [{0}].", submissionId))
            .toString());

    assertLongBePositive(submissionId, "submissionId");

    List<Map<String, Object>> rows =
        executeSqlWithParam(
            jdbcTemplate, GET_SUBMISSION_IMAGES_FOR_SUBMISSION_SQL, newArrayList(submissionId));

    // create a new SubmissionImage array
    SubmissionImage[] submissionImages = new SubmissionImage[rows.size()];

    // enumerate each data row
    for (int i = 0; i < rows.size(); ++i) {
      // create a new SubmissionImage object
      SubmissionImage submissionImage = new SubmissionImage();

      // update the submission id.
      submissionImage.setSubmissionId(submissionId);

      loadSubmissionImageFieldsSequentially(submissionImage, rows.get(i));

      // assign it to the array
      submissionImages[i] = submissionImage;
    }

    return submissionImages;
  }

  /**
   * Removes the given AuditedDeliverableStructure instance (by id) from the persistence.
   *
   * @param entity the given AuditedDeliverableStructure instance to remove
   * @param tableName the table name to delete the instance from
   * @param idName the id column name of the table that corresponds to the id field of the instance
   * @throws UploadPersistenceException if there is an error when during the persistence process
   */
  private void removeEntity(AuditedDeliverableStructure entity, String tableName, String idName)
      throws UploadPersistenceException {
    log.debug("delete record from table: " + tableName + " with id:" + entity.getId());
    executeUpdateSql(
        jdbcTemplate,
        REMOVE_ENTITY_SQL
            .replaceAll(TABLE_NAME_PLACEHOLDER, tableName)
            .replaceAll(ID_NAME_PLACEHOLDER, idName),
        newArrayList(entity.getId()));
  }

  /**
   * Adds the given NamedDeliverableStructure instance to the persistence.
   *
   * @param namedEntity the NamedDeliverableStructure instance to add
   * @param tableName the table name to persist the instance into
   * @param idName the id column name of the table that corresponds to the id field of the instance
   * @throws UploadPersistenceException if there is an error during the persistence process
   */
  private void addNameEntity(NamedDeliverableStructure namedEntity, String tableName, String idName)
      throws UploadPersistenceException {
    log.debug("add record into table:" + tableName + " with id:" + namedEntity.getId());
    executeUpdateSql(
        jdbcTemplate,
        ADD_NAMED_ENTITY_SQL
            .replaceAll(TABLE_NAME_PLACEHOLDER, tableName)
            .replaceAll(ID_NAME_PLACEHOLDER, idName),
        newArrayList(
            namedEntity.getId(),
            namedEntity.getCreationUser(),
            namedEntity.getCreationTimestamp(),
            namedEntity.getModificationUser(),
            namedEntity.getModificationTimestamp(),
            namedEntity.getName(),
            namedEntity.getDescription()));
  }

  /**
   * Updates the given NamedDeliverableStructure instance in the persistence.
   *
   * @param namedEntity the given NamedDeliverableStructure instance to update
   * @param tableName the table name to update the instance to
   * @param idName the id column name of the table that corresponds to the id field of the instance
   * @throws UploadPersistenceException if there is an error during the persistence process
   */
  private void updateNamedEntity(
      NamedDeliverableStructure namedEntity, String tableName, String idName)
      throws UploadPersistenceException {
    log.debug("update record in table: " + tableName + " with id:" + namedEntity.getId());
    executeUpdateSql(
        jdbcTemplate,
        UPDATE_NAMED_ENTITY_SQL
            .replaceAll(TABLE_NAME_PLACEHOLDER, tableName)
            .replaceAll(ID_NAME_PLACEHOLDER, idName),
        newArrayList(
            namedEntity.getModificationUser(),
            namedEntity.getModificationTimestamp(),
            namedEntity.getName(),
            namedEntity.getDescription(),
            namedEntity.getId()));
  }

  /**
   * Load data items from the data row and fill the fields of an MimeType instance.
   *
   * @param mimeType the MimeType instance whose fields will be filled
   * @param row the data row
   * @return the start index of the left data items that haven't been read
   * @since 1.2
   */
  private void loadMimeTypeFieldsSequentially(MimeType mimeType, Map<String, Object> row) {
    mimeType.setId(getLong(row, "mime_type_id"));
    FileType fileType = new FileType();
    fileType.setId(getLong(row, "file_type_id"));
    fileType.setDescription(getString(row, "file_type_desc"));
    fileType.setSort(getInt(row, "sort"));
    fileType.setImageFile(getBoolean(row, "image_file_ind"));
    fileType.setExtension(getString(row, "extension"));
    fileType.setBundledFile(getBoolean(row, "bundled_file_ind"));
    mimeType.setFileType(fileType);
    mimeType.setDescription(getString(row, "mime_type_desc"));
  }

  /**
   * Load data items from the data row and fill the fields of an SubmissionImage instance.
   *
   * @param submissionImage the SubmissionImage instance whose fields will be filled
   * @param row the data row
   * @return the start index of the left data items that haven't been read
   * @since 1.2
   */
  private void loadSubmissionImageFieldsSequentially(
      SubmissionImage submissionImage, Map<String, Object> row) {
    submissionImage.setImageId(getInt(row, "image_id"));
    submissionImage.setSortOrder(getInt(row, "sort_order"));
    submissionImage.setModifyDate(getDate(row, "modify_date"));
    submissionImage.setCreateDate(getDate(row, "create_date"));
  }

  /**
   * Loads the prize entity from the data row and fill the fields of an Prize instance.
   *
   * @param prize the Prize instance whose fields will be filled
   * @param row the data row
   * @param startIndex the start index to read from
   * @return the start index of the left data items that haven't been read
   * @since 1.2
   */
  private int loadPrizeEntityFieldsSequentially(Prize prize, Object[] row, int startIndex) {
    Long id = (Long) row[startIndex++];
    if (id != null) {
      prize.setId(id);
      prize.setPlace((Integer) row[startIndex++]);
      prize.setPrizeAmount((Double) row[startIndex++]);
      PrizeType prizeType = new PrizeType();
      prizeType.setId((Long) row[startIndex++]);
      prize.setPrizeType(prizeType);
      prize.setNumberOfSubmissions((Integer) row[startIndex++]);
      prize.setCreationUser((String) row[startIndex++]);
      prize.setCreationTimestamp((Date) row[startIndex++]);
      prize.setModificationUser((String) row[startIndex++]);
      prize.setModificationTimestamp((Date) row[startIndex++]);
      prizeType.setDescription((String) row[startIndex++]);
    } else {
      // increase the index, in case there are some additional fields.
      startIndex += 9;
    }

    return startIndex;
  }

  /**
   * Loads submission from the result of the SELECT operation.
   *
   * @param resultSet Result of the SELECT operation.
   * @return loaded submission.
   * @throws UploadPersistenceException if any error occurs while reading the result.
   */
  private Submission loadSubmission(Map<String, Object> resultSet)
      throws UploadPersistenceException {
    Submission submission = new Submission();
    if (resultSet.get("screening_score") != null) {
      submission.setScreeningScore(getDouble(resultSet, "screening_score"));
    }
    if (resultSet.get("initial_score") != null) {
      submission.setInitialScore(getDouble(resultSet, "initial_score"));
    }
    if (resultSet.get("final_score") != null) {
      submission.setFinalScore(getDouble(resultSet, "final_score"));
    }
    if (resultSet.get("placement") != null) {
      submission.setPlacement(getLong(resultSet, "placement"));
    }

    if (resultSet.get("mark_for_purchase") != null) {
      submission.setExtra(getBoolean(resultSet, "mark_for_purchase"));
    }

    if (resultSet.get("thurgood_job_id") != null) {
      submission.setThurgoodJobId(getString(resultSet, "thurgood_job_id"));
    }

    submission.setId(getLong(resultSet, "submission_id"));
    if (resultSet.get("user_rank") != null) {
      submission.setUserRank(getInt(resultSet, "user_rank"));
    }

    submission.setCreationUser(getString(resultSet, "submission_create_user"));
    submission.setCreationTimestamp(getDate(resultSet, "submission_create_date"));
    submission.setModificationUser(getString(resultSet, "submission_modify_user"));
    submission.setModificationTimestamp(getDate(resultSet, "submission_modify_date"));

    // create a new SubmissionStatus object
    SubmissionStatus submissionStatus = new SubmissionStatus();

    submissionStatus.setId(getLong(resultSet, "submission_status_id"));
    submissionStatus.setCreationUser(getString(resultSet, "submission_status_create_user"));
    submissionStatus.setCreationTimestamp(getDate(resultSet, "submission_status_create_date"));
    submissionStatus.setModificationUser(getString(resultSet, "submission_status_modify_user"));
    submissionStatus.setModificationTimestamp(getDate(resultSet, "submission_status_modify_date"));
    submissionStatus.setName(getString(resultSet, "submission_status_name"));
    submissionStatus.setDescription(getString(resultSet, "submission_status_description"));

    submission.setSubmissionStatus(submissionStatus);

    // create a new SubmissionStatus object
    SubmissionType submissionType = new SubmissionType();

    submissionType.setId(getLong(resultSet, "submission_type_id"));
    submissionType.setCreationUser(getString(resultSet, "submission_type_create_user"));
    submissionType.setCreationTimestamp(getDate(resultSet, "submission_type_create_date"));
    submissionType.setModificationUser(getString(resultSet, "submission_type_modify_user"));
    submissionType.setModificationTimestamp(getDate(resultSet, "submission_type_modify_date"));
    submissionType.setName(getString(resultSet, "submission_type_name"));
    submissionType.setDescription(getString(resultSet, "submission_type_description"));

    submission.setSubmissionType(submissionType);

    // load prize if exist
    if (resultSet.get("prize_id") != null) {
      Prize prize = new Prize();

      prize.setId(getLong(resultSet, "prize_id"));
      prize.setPlace(getInt(resultSet, "place"));
      prize.setPrizeAmount(getDouble(resultSet, "prize_amount"));
      prize.setNumberOfSubmissions(getInt(resultSet, "number_of_submissions"));
      prize.setCreationUser(getString(resultSet, "prize_create_user"));
      prize.setCreationTimestamp(getDate(resultSet, "prize_create_date"));
      prize.setModificationUser(getString(resultSet, "prize_modify_user"));
      prize.setModificationTimestamp(getDate(resultSet, "prize_modify_date"));

      PrizeType prizeType = new PrizeType();
      prizeType.setId(getLong(resultSet, "prize_type_id"));
      prizeType.setDescription(getString(resultSet, "prize_type_desc"));
      prize.setPrizeType(prizeType);

      submission.setPrize(prize);
    }

    submission.setUpload(loadUpload(resultSet));
    return submission;
  }

  /**
   * Loads upload from the result of the SELECT operation.
   *
   * @param resultSet Result of the SELECT operation.
   * @return loaded upload.
   * @throws UploadPersistenceException if any error occurs while reading the result.
   */
  private Upload loadUpload(Map<String, Object> resultSet) throws UploadPersistenceException {
    Upload upload = new Upload();

    upload.setId(getLong(resultSet, "upload_id"));
    upload.setCreationUser(getString(resultSet, "upload_create_user"));
    upload.setCreationTimestamp(getDate(resultSet, "upload_create_date"));
    upload.setModificationUser(getString(resultSet, "upload_modify_user"));
    upload.setModificationTimestamp(getDate(resultSet, "upload_modify_date"));

    upload.setProject(getLong(resultSet, "project_id"));
    upload.setProjectPhase(getLong(resultSet, "project_phase_id"));
    upload.setOwner(getLong(resultSet, "resource_id"));
    upload.setParameter(getString(resultSet, "upload_parameter"));
    upload.setDescription(getString(resultSet, "upload_desc"));
    upload.setUrl(getString(resultSet, "url"));

    // create a new UploadType object
    UploadType uploadType = new UploadType();

    uploadType.setId(getLong(resultSet, "upload_type_id"));
    uploadType.setCreationUser(getString(resultSet, "upload_type_create_user"));
    uploadType.setCreationTimestamp(getDate(resultSet, "upload_type_create_date"));
    uploadType.setModificationUser(getString(resultSet, "upload_type_modify_user"));
    uploadType.setModificationTimestamp(getDate(resultSet, "upload_type_modify_date"));
    uploadType.setName(getString(resultSet, "upload_type_name"));
    uploadType.setDescription(getString(resultSet, "upload_type_description"));

    upload.setUploadType(uploadType);

    // create a new UploadStatus object
    UploadStatus uploadStatus = new UploadStatus();

    uploadStatus.setId(getLong(resultSet, "upload_status_id"));
    uploadStatus.setCreationUser(getString(resultSet, "upload_status_create_user"));
    uploadStatus.setCreationTimestamp(getDate(resultSet, "upload_status_create_date"));
    uploadStatus.setModificationUser(getString(resultSet, "upload_status_modify_user"));
    uploadStatus.setModificationTimestamp(getDate(resultSet, "upload_status_modify_date"));
    uploadStatus.setName(getString(resultSet, "upload_status_name"));
    uploadStatus.setDescription(getString(resultSet, "upload_status_description"));

    upload.setUploadStatus(uploadStatus);

    return upload;
  }

  /**
   * Loads all NamedDeliverableStructure with the given ids from persistence. May return a 0-length
   * array.
   *
   * @param namedEntityIds The ids of the objects to load
   * @param type the concrete class type of the NamedDeliverableStructure
   * @param tableName the name of the table to load from
   * @param idName the id column name of the table that corresponds to the id field of the instance
   * @return an array of all the instances of 'type' type
   * @throws UploadPersistenceException if there is an error during the persistence process
   */
  private NamedDeliverableStructure[] loadNamedEntities(
      Long[] namedEntityIds, Class<?> type, String tableName, String idName)
      throws UploadPersistenceException {

    // check if the given type is of NamedDeliverableStructure kind.
    if (!NamedDeliverableStructure.class.isAssignableFrom(type)) {
      throw new IllegalArgumentException(
          "type ["
              + type.getName()
              + "] should be a NamedDeliverableStructure type or its sub-type.");
    }

    // simply return an empty 'type'[] array if
    // namedEntityIds is empty
    if (namedEntityIds.length == 0) {
      return (NamedDeliverableStructure[]) Array.newInstance(type, 0);
    }

    List<Map<String, Object>> rows =
        executeSql(
            jdbcTemplate,
            LOAD_NAMED_ENTITIES_SQL
                    .replaceAll(TABLE_NAME_PLACEHOLDER, tableName)
                    .replaceAll(ID_NAME_PLACEHOLDER, idName)
                + makeIdListString(namedEntityIds));

    try {
      // create a new array of 'type'[] type
      NamedDeliverableStructure[] namedEntities =
          (NamedDeliverableStructure[]) Array.newInstance(type, rows.size());
      for (int i = 0; i < rows.size(); ++i) {
        // reference the current data row
        Map<String, Object> row = rows.get(i);
        // create a new 'type' object
        NamedDeliverableStructure namedEntity = (NamedDeliverableStructure) type.newInstance();
        namedEntity.setId(getLong(row, idName));
        namedEntity.setCreationUser(getString(row, "create_user"));
        namedEntity.setCreationTimestamp(getDate(row, "create_date"));
        namedEntity.setModificationUser(getString(row, "modify_user"));
        namedEntity.setModificationTimestamp(getDate(row, "modify_date"));
        namedEntity.setName(getString(row, "name"));
        namedEntity.setDescription(getString(row, "description"));
        // assign it to the array
        namedEntities[i] = namedEntity;
      }
      return namedEntities;
    } catch (InstantiationException e) {
      log.error("Unable to create an instance of " + type.getName(), e);
      throw new UploadPersistenceException(
          "Unable to create an instance of " + type.getName() + ".", e);
    } catch (IllegalAccessException e) {
      log.error("Unable to create an instance of " + type.getName(), e);
      throw new UploadPersistenceException(
          "Unable to create an instance of " + type.getName() + ".", e);
    }
  }

  /**
   * Gets the ids from the given table in the persistence.
   *
   * @param tableName the table name to load the ids from
   * @param idName the id column name of the table
   * @return all the ids in the table
   * @throws UploadPersistenceException if there is an error during the persistence process
   */
  private Long[] getAllEntityIds(String tableName, String idName)
      throws UploadPersistenceException {

    List<Map<String, Object>> rows =
        executeSql(
            jdbcTemplate,
            GET_ALL_ENTITY_IDS_SQL
                .replaceAll(TABLE_NAME_PLACEHOLDER, tableName)
                .replaceAll(ID_NAME_PLACEHOLDER, idName));

    // create a long array and set values
    Long[] ids = new Long[rows.size()];

    // enumerate each data row
    for (int i = 0; i < rows.size(); ++i) {
      ids[i] = getLong(rows.get(i), idName);
    }
    return ids;
  }

  /**
   * Check if the given AuditedDeliverableStructure instance is valid to persist.
   *
   * @param entity the given AuditedDeliverableStructure instance to check.
   * @param name the name to identify the instance.
   * @throws IllegalArgumentException if the given instance is null or isValidToPersist returns
   *     false.
   */
  private void assertEntityNotNullAndValidToPersist(
      AuditedDeliverableStructure entity, String name) {
    Helper.assertObjectNotNull(entity, name);

    if (!entity.isValidToPersist()) {
      log.error("The entity [" + name + "] is not valid to persist.");
      throw new IllegalArgumentException("The entity [" + name + "] is not valid to persist.");
    }
  }

  /**
   * Check if the given long value is positive.
   *
   * @param value the long value to check
   * @param name the name to identify the long value.
   * @throws IllegalArgumentException if the given long value is negative or zero
   * @since 1.2
   */
  private void assertLongBePositive(long value, String name) {
    if (value <= 0) {
      log.error(name + " should be positive.");
      throw new IllegalArgumentException(name + " should be positive.");
    }
  }

  /**
   * Create a String object from the ids array in the form of "(id0, id1, id2, ...)", used in a sql
   * statement.
   *
   * @param ids the ids array
   * @return A String that represents the ids array in the form of "(id0, id1, id2, ...)"
   * @throws IllegalArgumentException if ids is null or empty
   */
  private static String makeIdListString(Long[] ids) {
    Helper.assertObjectNotNull(ids, "ids");
    if (ids.length == 0) {
      throw new IllegalArgumentException("ids should not be empty.");
    }

    StringBuilder sb = new StringBuilder();
    sb.append('(');

    // enumerate each id in the array
    for (int i = 0; i < ids.length; ++i) {
      // if it's not the first one, append a comma
      if (i != 0) {
        sb.append(',');
      }
      sb.append(ids[i]);
    }
    sb.append(')');

    return sb.toString();
  }

  /**
   * Gets the id field of the given entity, or null, if the entity is null.
   *
   * @param entity the entity
   * @return the value of id field, or null
   * @since 1.2
   */
  private static Long getIdFromEntity(IdentifiableDeliverableStructure entity) {
    return entity == null ? null : entity.getId();
  }
}
