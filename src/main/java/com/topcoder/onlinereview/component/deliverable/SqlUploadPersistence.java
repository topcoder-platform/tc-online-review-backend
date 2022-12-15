/*
 * Copyright (C) 2006-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.deliverable;

import com.topcoder.onlinereview.component.grpcclient.upload.UploadServiceRpc;
import com.topcoder.onlinereview.component.project.management.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

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

  @Autowired
  private UploadServiceRpc uploadServiceRpc;

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
    uploadServiceRpc.addUploadType(uploadType);
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
    uploadServiceRpc.addUploadStatus(uploadStatus);
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
    uploadServiceRpc.addSubmissionStatus(submissionStatus);
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
    uploadServiceRpc.removeUploadType(uploadType.getId());
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
    uploadServiceRpc.removeUploadStatus(uploadStatus.getId());
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
    uploadServiceRpc.removeSubmissionStatus(submissionStatus.getId());
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
    uploadServiceRpc.removeUpload(upload.getId());
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
    uploadServiceRpc.removeSubmission(submission.getId());
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
    uploadServiceRpc.updateUploadType(uploadType);
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
    uploadServiceRpc.updateUploadStatus(uploadStatus);
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
    uploadServiceRpc.updateSubmissionStatus(submissionStatus);
  }

  /**
   * Gets the ids of all upload types in the persistence. The individual upload types can then be
   * loaded with the loadUploadType method.
   *
   * @return The ids of all upload types
   * @throws UploadPersistenceException If there is an error when reading the persistence store
   */
  public Long[] getAllUploadTypeIds() throws UploadPersistenceException {
    return uploadServiceRpc.getAllUploadTypeIds();
  }

  /**
   * Gets the ids of all upload statuses in the persistence. The individual upload statuses can then
   * be loaded with the loadUploadStatus method.
   *
   * @return The ids of all upload statuses
   * @throws UploadPersistenceException If there is an error when reading the persistence store
   */
  public Long[] getAllUploadStatusIds() throws UploadPersistenceException {
    return uploadServiceRpc.getAllUploadStatusIds();
  }

  /**
   * Gets the ids of all submission statuses in the persistence. The individual submission statuses
   * can then be loaded with the loadSubmissionStatus method.
   *
   * @return The ids of all submission statuses
   * @throws UploadPersistenceException If there is an error when reading the persistence store
   */
  public Long[] getAllSubmissionStatusIds() throws UploadPersistenceException {
    return uploadServiceRpc.getAllSubmissionStatusIds();
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
    uploadServiceRpc.addSubmissionType(submissionType);
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
    uploadServiceRpc.removeSubmissionType(submissionType.getId());
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
    uploadServiceRpc.updateSubmissionType(submissionType);
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
    return uploadServiceRpc.loadSubmissionTypes(submissionTypeIds);
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
    return uploadServiceRpc.getAllSubmissionTypeIds();
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
    return uploadServiceRpc.loadUploadTypes(uploadTypeIds);
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
    return uploadServiceRpc.loadUploadStatuses(uploadStatusIds);
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

    return uploadServiceRpc.loadSubmissionStatuses(submissionStatusIds);
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
    uploadServiceRpc.addUpload(upload);
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
    uploadServiceRpc.updateUpload(upload);
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
    uploadServiceRpc.addSubmission(submission);
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
    uploadServiceRpc.updateSubmission(submission);
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
    return uploadServiceRpc.loadUploads(uploadIds);
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
    return uploadServiceRpc.loadSubmissions(submissionIds);
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
    uploadServiceRpc.addSubmissionImage(submissionImage);
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
    uploadServiceRpc.updateSubmissionImage(submissionImage);
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
    uploadServiceRpc.removeSubmissionImage(submissionImage);
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
    return uploadServiceRpc.getAllMimeTypeIds();
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
    return uploadServiceRpc.loadMimeTypes(mimeTypeIds);
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
    return uploadServiceRpc.getImagesForSubmission(submissionId);
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
}
