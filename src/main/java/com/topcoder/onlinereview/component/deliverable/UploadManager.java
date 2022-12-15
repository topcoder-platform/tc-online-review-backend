/*
 * Copyright (C) 2006-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.deliverable;

import com.topcoder.onlinereview.component.grpcclient.upload.UploadServiceRpc;
import com.topcoder.onlinereview.component.id.DBHelper;
import com.topcoder.onlinereview.component.id.IDGenerationException;
import com.topcoder.onlinereview.component.id.IDGenerator;
import com.topcoder.onlinereview.component.project.management.LogMessage;
import com.topcoder.onlinereview.component.search.SearchBuilderException;
import com.topcoder.onlinereview.component.search.filter.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * The PersistenceUploadManager class implements the UploadManager interface. It ties together a
 * persistence mechanism, several Search Builder instances (for searching for various types of
 * data), and several id generators (for generating ids for the various types). This class consists
 * of several methods styles. The first method style just calls directly to a corresponding method
 * of the persistence. The second method style first assigns values to some data fields of the
 * object before calling a persistence method. The third type of method uses a SearchBundle to
 * execute a search and then uses the persistence to load an object for each of the ids found from
 * the search.
 *
 * <p>Here is the sample code for using this component.
 *
 * <pre>
 * // Set up the SearchBundleManager.
 * SearchBundleManager searchBundleManager = new SearchBundleManager(SEARCH_BUILDER_NAMESPACE);
 *
 * // 4.3.1 Create Upload Manager, according to Component Specification.
 *
 * SqlUploadPersistence uploadPersistence =
 *     new SqlUploadPersistence(new DBConnectionFactoryImpl(DB_CONNECTION_NAMESPACE));
 *
 * UploadManager manager = new PersistenceUploadManager(uploadPersistence,
 *     searchBundleManager.getSearchBundle(&quot;Upload Search Bundle&quot;),
 *     searchBundleManager.getSearchBundle(&quot;Submission Search Bundle&quot;),
 *     IDGeneratorFactory.getIDGenerator(&quot;upload_id_seq&quot;),
 *     IDGeneratorFactory.getIDGenerator(&quot;upload_type_id_seq&quot;),
 *     IDGeneratorFactory.getIDGenerator(&quot;upload_status_id_seq&quot;),
 *     IDGeneratorFactory.getIDGenerator(&quot;submission_id_seq&quot;),
 *     IDGeneratorFactory.getIDGenerator(&quot;submission_status_id_seq&quot;),
 *     IDGeneratorFactory.getIDGenerator(&quot;submission_type_id_seq&quot;));
 *
 * // 4.3.2 Create an Upload and Submission (with supporting classes).
 *
 * // Load tagging instances (also demonstrates manager interactions)
 *
 * UploadType uploadType = manager.getAllUploadTypes()[0];
 * SubmissionStatus submissionStatus = manager.getAllSubmissionStatuses()[0];
 * UploadStatus uploadStatus = manager.getAllUploadStatuses()[0];
 * SubmissionType submissionType = manager.getAllSubmissionTypes()[0];
 *
 * // Create upload
 * Upload upload = new Upload(1234);
 * upload.setProject(24);
 * upload.setUploadType(uploadType);
 * upload.setUploadStatus(uploadStatus);
 * upload.setOwner(553);
 * upload.setParameter(&quot;The upload is somewhere&quot;);
 * upload.setDescription(&quot;This is a sample upload&quot;);
 *
 * // Create Submission
 * Submission submission = new Submission(823);
 * List&lt;Upload&gt; uploads = new ArrayList&lt;Upload&gt;();
 * uploads.add(upload);
 * submission.setUploads(uploads);
 * submission.setSubmissionStatus(submissionStatus);
 * submission.setSubmissionType(submissionType);
 * submission.setUserRank(2);
 * submission.setExtra(true);
 *
 * // 4.3.4 Save the created Upload and Submission.
 *
 * // Create manager, upload and submission. (see 4.3.1, 4.3.2)
 * upload = new Upload();
 * upload.setProject(24);
 * upload.setUploadType(uploadType);
 * upload.setUploadStatus(uploadStatus);
 * upload.setOwner(553);
 * upload.setParameter(&quot;The upload is somewhere&quot;);
 * submission = new Submission();
 *
 * manager.createUpload(upload, &quot;Operator #1&quot;);
 *
 * assertEquals(&quot;upload is not created properly.&quot;, &quot;Operator #1&quot;, upload.getCreationUser());
 *
 * submission.setUploads(Arrays.asList(upload));
 * submission.setSubmissionStatus(submissionStatus);
 * submission.setSubmissionType(submissionType);
 *
 * manager.createSubmission(submission, &quot;Operator #1&quot;);
 * // New instances of the tagging classes can be created through
 * // similar methods.
 *
 * // Change a property of the Upload
 * upload.setProject(14424);
 *
 * // And update it in the persistence
 * manager.updateUpload(upload, &quot;Operator #2&quot;);
 *
 * // Remove it from the persistence
 * manager.removeUpload(upload, &quot;Operator #3&quot;);
 *
 * // Submissions can be changed and removed similarly
 *
 * // 4.3.5 Retrieve and search for uploads
 *
 * // Get an upload for a given id
 * Upload upload2 = manager.getUpload(14402);
 *
 * // The properties of the upload can then be queried and used by the client of this
 * // component. Submissions can be retrieved similarly.
 *
 * // Search for uploads Build the uploads - this example shows searching for
 * // all uploads related to a given project and having a given upload type
 * Filter projectFilter = UploadFilterBuilder.createProjectIdFilter(953);
 * Filter uploadTypeFilter = UploadFilterBuilder.createUploadTypeIdFilter(4);
 *
 * Filter fullFilter = new AndFilter(projectFilter, uploadTypeFilter);
 *
 * // Search for the Uploads
 * Upload[] matchingUploads = manager.searchUploads(fullFilter);
 *
 * // Submissions and Deliverables can be searched similarly by
 * // using the other FilterBuilder classes and the corresponding
 * // UploadManager or DeliverableManager methods.
 *
 * // Get all the lookup table values.
 * UploadType[] uploadTypes = manager.getAllUploadTypes();
 * UploadStatus[] uploadStatuses = manager.getAllUploadStatuses();
 * SubmissionStatus[] submissionStatuses = manager.getAllSubmissionStatuses();
 * SubmissionType[] submissionTypes = manager.getAllSubmissionTypes();
 *
 * // Alter a lookup table entry and update the persistence
 * uploadTypes[0].setName(&quot;Changed name&quot;);
 * manager.updateUploadType(uploadTypes[0], &quot;Operator #1&quot;);
 *
 * // Lookup table entries can be created/removed through parallel
 * // methods to those shown in section 4.3.4
 *
 * // 4.3.6 Search for submissions with specific submission type
 * long specificationSubmissionTypeId = 1;
 * Filter specificationSubmissionFilter = SubmissionFilterBuilder
 *         .createSubmissionTypeIdFilter(specificationSubmissionTypeId);
 *
 * Submission[] specificationSubmissions = manager.searchSubmissions(specificationSubmissionFilter);
 *
 * // 4.3.7 new demo for addition in version 1.2
 * // Create submission image
 * SubmissionImage submissionImage = new SubmissionImage();
 * submissionImage.setSubmissionId(submission.getId());
 * submissionImage.setImageId(1);
 * submissionImage.setSortOrder(1);
 * manager.createSubmissionImage(submissionImage, &quot;admin&quot;);
 *
 * // Update the submission image
 * submissionImage.setSortOrder(0);
 * manager.updateSubmissionImage(submissionImage, &quot;admin&quot;);
 *
 * // Remove the submission image
 * manager.removeSubmissionImage(submissionImage, &quot;admin&quot;);
 *
 * // Retrieve the MIME type with ID=1
 * MimeType mimeType = manager.getMimeType(1);
 *
 * // Retrieve all MIME types
 * MimeType[] mimeTypes = manager.getAllMimeTypes();
 *
 * // Retrieve the submissions for project with ID=1 and
 * // user with ID=1
 * Submission[] submissions = manager.getUserSubmissionsForProject(1, 1);
 *
 * // Retrieve all submissions for project with ID=1
 * submissions = manager.getProjectSubmissions(1);
 *
 * // Retrieve the images for submission with ID=1
 * SubmissionImage[] submissionImages = manager.getImagesForSubmission(1);
 * </pre>
 *
 * <p><em>Changes in 1.1: </em>
 *
 * <ul>
 *   <li>Added methods for managing submission types.
 *   <li>Changed <code>logger</code> attribute name to upper case to meet Java and TopCoder
 *       standards.
 * </ul>
 *
 * <p>Changes in 1.2:
 *
 * <ul>
 *   <li>Added methods for managing SubmissionImage entities.
 *   <li>Added methods for retrieving MimeType entities.
 *   <li>Added methods for retrieving project/user submissions.
 *   <li>Added method for retrieving images associated with submission.
 *   <li>Retrieving id field of entity is refactored into getId method, to reduce code redundancy.
 *   <li>Update {@link #searchSubmissions(Filter)} method to remove <code>submission.setUploads
 *       </code> statement because one submission can only have a upload now.
 * </ul>
 *
 * <p><strong>Thread Safety: </strong> This class is immutable and hence thread-safe.
 *
 * @author aubergineanode, singlewood, George1
 * @author saarixx, sparemax
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.2.3
 */
@Slf4j
@Component
public class UploadManager {
  /**
   * The name under which the upload search bundle should appear in the SearchBundleManager, if the
   * SearchBundleManager constructor is used.
   */
  public static final String UPLOAD_SEARCH_BUNDLE_NAME = "Upload Search Bundle";

  /**
   * The name under which the submission search bundle should appear in the SearchBundleManager, if
   * the SearchBundleManager constructor is used.
   */
  public static final String SUBMISSION_SEARCH_BUNDLE_NAME = "Submission Search Bundle";

  /**
   * The name under which the id generator for uploads should appear in the IDGeneratorFactory if
   * the IDGeneratorFactory constructor is used.
   */
  public static final String UPLOAD_ID_GENERATOR_NAME = "upload_id_seq";

  /**
   * The name under which the id generator for upload types should appear in the IDGeneratorFactory
   * if the IDGeneratorFactory constructor is used. This field is public, static, and final.
   */
  public static final String UPLOAD_TYPE_ID_GENERATOR_NAME = "upload_type_id_seq";

  /**
   * The name under which the id generator for upload statuses should appear in the
   * IDGeneratorFactory if the IDGeneratorFactory constructor is used. This field is public, static,
   * and final.
   */
  public static final String UPLOAD_STATUS_ID_GENERATOR_NAME = "upload_status_id_seq";

  /**
   * The name under which the id generator for submissions should appear in the IDGeneratorFactory
   * if the IDGeneratorFactory constructor is used.
   */
  public static final String SUBMISSION_ID_GENERATOR_NAME = "submission_id_seq";

  /**
   * The name under which the id generator for submission statuses should appear in the
   * IDGeneratorFactory if the IDGeneratorFactory constructor is used.
   */
  public static final String SUBMISSION_STATUS_ID_GENERATOR_NAME = "submission_status_id_seq";

  /**
   * The name under which the id generator for submission types should appear in the
   * IDGeneratorFactory if the IDGeneratorFactory constructor is used.
   *
   * <p>This field is public, static, and final.
   *
   * @since 1.1
   */
  public static final String SUBMISSION_TYPE_ID_GENERATOR_NAME = "submission_type_id_seq";

  /** The flag which indicate the caller of persistenceUploadManagerHelper. */
  private static final int CREATE_FLAG = 0;

  /** The flag which indicate the caller of persistenceUploadManagerHelper. */
  private static final int UPDATE_FLAG = 1;

  /** The flag which indicate the caller of persistenceUploadManagerHelper. */
  private static final int REMOVE_FLAG = 2;

  /**
   * The persistence store for Uploads, Submission, and related objects. This field is set in the
   * constructor, is immutable, and can never be null.
   */
  //  @Autowired private UploadPersistence persistence;

  @Autowired private SqlUploadPersistence persistence;

  /**
   * The generator used to create ids for new Uploads. This field is set in the constructor, is
   * immutable, and can never be null.
   */
  private IDGenerator uploadIdGenerator;

  /**
   * The generator used to create ids for new Submissions. This field is set in the constructor, is
   * immutable, and can never be null.
   */
  private IDGenerator submissionIdGenerator;

  /**
   * The generator used to create ids for new UploadTypes. This field is set in the constructor, is
   * immutable, and can never be null.
   */
  private IDGenerator uploadTypeIdGenerator;

  /**
   * The generator used to create ids for new UploadStatuses. This field is set in the constructor,
   * is immutable, and can never be null.
   */
  private IDGenerator uploadStatusIdGenerator;

  /**
   * The generator used to create ids for new SubmissionStatuses. This field is set in the
   * constructor, is immutable, and can never be null.
   */
  private IDGenerator submissionStatusIdGenerator;

  /**
   * The generator used to create ids for new SubmissionTypes.
   *
   * <p>This field is set in the constructor, is immutable, and can never be null. This field is
   * used when an id is needed for a new SubmissionType, which occurs in the createSubmissionType
   * method.
   *
   * @since 1.1
   */
  private IDGenerator submissionTypeIdGenerator;

  @Autowired private DBHelper dbHelper;

  @Autowired
  private UploadServiceRpc uploadServiceRpc;

  @PostConstruct
  public void postRun() throws IDGenerationException {
    uploadIdGenerator = new IDGenerator(UPLOAD_ID_GENERATOR_NAME, dbHelper);
    uploadTypeIdGenerator = new IDGenerator(UPLOAD_TYPE_ID_GENERATOR_NAME, dbHelper);
    uploadStatusIdGenerator = new IDGenerator(UPLOAD_STATUS_ID_GENERATOR_NAME, dbHelper);
    submissionIdGenerator = new IDGenerator(SUBMISSION_ID_GENERATOR_NAME, dbHelper);
    submissionStatusIdGenerator = new IDGenerator(SUBMISSION_STATUS_ID_GENERATOR_NAME, dbHelper);
    submissionTypeIdGenerator = new IDGenerator(SUBMISSION_TYPE_ID_GENERATOR_NAME, dbHelper);
  }

  /**
   * Creates a new upload in the persistence store. The id of the upload must be UNSET_ID. The
   * manager will assign an id to the upload.
   *
   * @param upload The upload to add
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If upload or operator is null
   * @throws IllegalArgumentException If the id is not UNSET_ID
   * @throws IllegalArgumentException If the upload (once an id and creation/modification user/date
   *     are assigned) is not in a state to be persisted (i.e. if isValidToPersist returns false)
   * @throws UploadPersistenceException If there is an error persisting the upload
   */
  public void createUpload(Upload upload, String operator) throws UploadPersistenceException {
    log.debug(new LogMessage(null, operator, "Create new Upload.").toString());
    persistenceUploadManagerHelper(upload, "upload", operator, CREATE_FLAG, uploadIdGenerator);
  }

  /**
   * Updates the upload in the persistence store. The id of the upload can not be UNSET_ID and all
   * necessary fields must have already been assigned.
   *
   * @param upload The upload to update
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If upload or operator is null
   * @throws IllegalArgumentException The id of the upload is UNSET_ID
   * @throws IllegalArgumentException If the upload (once the modification user/date is set) is not
   *     in a state to be persisted (i.e. if isValidToPersist returns false)
   * @throws UploadPersistenceException If there is an error persisting the upload changes
   */
  public void updateUpload(Upload upload, String operator) throws UploadPersistenceException {
    log.debug(new LogMessage(getId(upload), operator, "Update Upload.").toString());

    persistenceUploadManagerHelper(upload, "upload", operator, UPDATE_FLAG, null);
  }

  /**
   * Removes the upload (by id) from the persistence store. The id of the upload can not be
   * UNSET_ID.
   *
   * @param upload The upload to remove
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If upload or operator is null
   * @throws IllegalArgumentException If the id of the upload is UNSET_ID
   * @throws IllegalArgumentException If the upload (once the modification user/date is set) is not
   *     in a state to be persisted (i.e. if isValidToPersist returns false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void removeUpload(Upload upload, String operator) throws UploadPersistenceException {
    log.debug(new LogMessage(getId(upload), operator, "Remove Upload.").toString());

    persistenceUploadManagerHelper(upload, "upload", operator, REMOVE_FLAG, null);
  }

  /**
   * Gets the upload in the persistence store for the given id. Returns null if there is no upload
   * for the given id.
   *
   * @return The retrieved Upload, or null
   * @param id The id of the upload to retrieve
   * @throws IllegalArgumentException If id is <= 0
   * @throws UploadPersistenceException If there is an error retrieving the Upload from persistence
   */
  public Upload getUpload(long id) throws UploadPersistenceException {
    log.debug(new LogMessage(id, null, "Retrieve Upload, delegate to persistence.").toString());
    // Simply call the corresponding persistence method.
    return persistence.loadUpload(id);
  }

  /**
   * Searches the persistence for uploads meeting the filter. The filter can be easily built using
   * the UploadFilterBuilder.
   *
   * @return The Uploads meeting the filter
   * @param filter The filter to use
   * @throws IllegalArgumentException If filter is null
   * @throws UploadPersistenceException If there is an error reading the upload from persistence
   * @throws SearchBuilderException If there is an error executing the filter
   */
  public Upload[] searchUploads(Filter filter)
      throws UploadPersistenceException, SearchBuilderException {
    DeliverableHelper.checkObjectNotNull(filter, "filter");
    return uploadServiceRpc.searchUploads(filter);
  }

  /**
   * Creates a new UploadType in the persistence store. The id of the upload type must be UNSET_ID.
   * The manager will assign an id before persisting the change.
   *
   * @param uploadType The upload type to add to the persistence store
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If uploadType or operator is null
   * @throws IllegalArgumentException If the id is not UNSET_ID
   * @throws IllegalArgumentException If the upload type (once an id and creation/modification
   *     user/date are assigned) is not in a state to be persisted (i.e. if isValidToPersist returns
   *     false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void createUploadType(UploadType uploadType, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(null, operator, "Create new UploadType.").toString());
    persistenceUploadManagerHelper(
        uploadType, "uploadType", operator, CREATE_FLAG, uploadTypeIdGenerator);
  }

  /**
   * Updates the info for an UploadType in the persistence store. All fields of the upload type must
   * have values assigned when this method is called.
   *
   * @param uploadType The upload type to update
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If uploadType or operator is null
   * @throws IllegalArgumentException The id is UNSET_ID
   * @throws IllegalArgumentException If the upload type (once the modification user/date is set) is
   *     not in a state to be persisted (i.e. if isValidToPersist returns false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void updateUploadType(UploadType uploadType, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(getId(uploadType), operator, "update UploadType.").toString());

    persistenceUploadManagerHelper(uploadType, "uploadType", operator, UPDATE_FLAG, null);
  }

  /**
   * Removes the given upload type (by id) from the persistence. The id of the uploadType can not be
   * UNSET_ID.
   *
   * @param uploadType The upload type to remove
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If uploadType or operator is null
   * @throws IllegalArgumentException If the id of the upload type is UNSET_ID
   * @throws IllegalArgumentException If the uploadType (once the modification user/date is set) is
   *     not in a state to be persisted (i.e. if isValidToPersist returns false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void removeUploadType(UploadType uploadType, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(getId(uploadType), operator, "remove UploadType.").toString());

    persistenceUploadManagerHelper(uploadType, "uploadType", operator, REMOVE_FLAG, null);
  }

  /**
   * Gets all the upload types in the persistence store.
   *
   * @return All UploadTypes in the persistence store.
   * @throws UploadPersistenceException If there is an error accessing the persistence store
   */
  public UploadType[] getAllUploadTypes() throws UploadPersistenceException {
    log.debug("get All upload types, delegate to persistence.");
    return persistence.loadUploadTypes(persistence.getAllUploadTypeIds());
  }

  /**
   * Creates a new UploadStatus in the persistence store. The id of the upload status must be
   * UNSET_ID. The manager will assign an id before persisting the change.
   *
   * @param uploadStatus The upload status to add to the persistence store
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If uploadStatus or operator is null
   * @throws IllegalArgumentException If the id is not UNSET_ID
   * @throws IllegalArgumentException If the upload status (once an id and creation/modification
   *     user/date are assigned) is not in a state to be persisted (i.e. if isValidToPersist returns
   *     false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void createUploadStatus(UploadStatus uploadStatus, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(null, operator, "create new UploadStatus.").toString());
    persistenceUploadManagerHelper(
        uploadStatus, "uploadStatus", operator, CREATE_FLAG, uploadStatusIdGenerator);
  }

  /**
   * Updates the info for an UploadStatus in the persistence store. All fields of the upload status
   * must have values assigned when this method is called.
   *
   * @param uploadStatus The upload status to update
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If uploadStatus or operator is null
   * @throws IllegalArgumentException The id is UNSET_ID
   * @throws IllegalArgumentException If the upload status (once the modification user/date is set)
   *     is not in a state to be persisted (i.e. if isValidToPersist returns false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void updateUploadStatus(UploadStatus uploadStatus, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(getId(uploadStatus), operator, "create new UploadStatus.").toString());

    persistenceUploadManagerHelper(uploadStatus, "uploadStatus", operator, UPDATE_FLAG, null);
  }

  /**
   * Removes the given upload status (by id) from the persistence. The id of the uploadStatus can
   * not be UNSET_ID.
   *
   * @param uploadStatus The upload status to remove
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If uploadStatus or operator is null
   * @throws IllegalArgumentException If the id of the upload status is UNSET_ID
   * @throws IllegalArgumentException If the uploadStatus (once the modification user/date is set)
   *     is not in a state to be persisted (i.e. if isValidToPersist returns false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void removeUploadStatus(UploadStatus uploadStatus, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(getId(uploadStatus), operator, "remove UploadStatus.").toString());

    persistenceUploadManagerHelper(uploadStatus, "uploadStatus", operator, REMOVE_FLAG, null);
  }

  /**
   * Gets all the upload statuses in the persistence store.
   *
   * @return All UploadStatuses in the persistence store.
   * @throws UploadPersistenceException If there is an error accessing the persistence store
   */
  public UploadStatus[] getAllUploadStatuses() throws UploadPersistenceException {
    log.debug("get All upload status, delegate to persistence.");
    return persistence.loadUploadStatuses(persistence.getAllUploadStatusIds());
  }

  /**
   * Creates a new Submission in the persistence store. The id of the submission must be UNSET_ID.
   * The manager will assign an id before persisting the change.
   *
   * @param submission The submission to add to the persistence store
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If submission or operator is null
   * @throws IllegalArgumentException If the id is not UNSET_ID
   * @throws IllegalArgumentException If the submission (once an id and creation/modification
   *     user/date are assigned) is not in a state to be persisted (i.e. if isValidToPersist returns
   *     false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void createSubmission(Submission submission, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(null, operator, "create new Submission.").toString());
    persistenceUploadManagerHelper(
        submission, "submission", operator, CREATE_FLAG, submissionIdGenerator);
  }

  /**
   * Updates the info for a Submission in the persistence store. All fields of the submission must
   * have values assigned when this method is called.
   *
   * @param submission The submission to update
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If submission or operator is null
   * @throws IllegalArgumentException The id is UNSET_ID
   * @throws IllegalArgumentException If the submission (once the modification user/date is set) is
   *     not in a state to be persisted (i.e. if isValidToPersist returns false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void updateSubmission(Submission submission, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(getId(submission), operator, "update Submission.").toString());

    persistenceUploadManagerHelper(submission, "submission", operator, UPDATE_FLAG, null);
  }

  /**
   * Removes the given submission (by id) from the persistence. The id of the submission can not be
   * UNSET_ID.
   *
   * @param submission The submission to remove
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If submission or operator is null
   * @throws IllegalArgumentException If the id of the submission is UNSET_ID
   * @throws IllegalArgumentException If the submission (once the modification user/date is set) is
   *     not in a state to be persisted (i.e. if isValidToPersist returns false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void removeSubmission(Submission submission, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(getId(submission), operator, "remove Submission.").toString());

    persistenceUploadManagerHelper(submission, "submission", operator, REMOVE_FLAG, null);
  }

  /**
   * Gets the Submission in the persistence store for the given id. Returns null if there is no
   * submission for the given id.
   *
   * @return The retrieved Submission, or null
   * @param id The id of the submission to retrieve
   * @throws IllegalArgumentException If id is <= 0
   * @throws UploadPersistenceException If there is an error retrieving the Submission from
   *     persistence
   */
  public Submission getSubmission(long id) throws UploadPersistenceException {
    log.debug(new LogMessage(id, null, "Retrieve Submission, delegate to persistence").toString());
    return persistence.loadSubmission(id);
  }

  /**
   * Searches the persistence for submissions meeting the filter The filter can be easily built
   * using the SubmissionFilterBuilder.
   *
   * <p>Changes in 1.2:
   *
   * <ul>
   *   <li>Now, uploads and submission images related to the given submission, are retrieved
   *       separately by using getUploadsForSubmission() and getImagesForSubmission().
   * </ul>
   *
   * @return The submissions meeting the filter
   * @param filter The filter to use
   * @throws IllegalArgumentException If filter is null
   * @throws UploadPersistenceException If there is an error reading the submissions from
   *     persistence
   * @throws SearchBuilderException If there is an error executing the filter
   */
  public Submission[] searchSubmissions(Filter filter)
      throws UploadPersistenceException, SearchBuilderException {
    DeliverableHelper.checkObjectNotNull(filter, "filter");
    Submission[] submissions = uploadServiceRpc.searchSubmissions(filter);
    for (Submission submission : submissions) {
      submission.setImages(Arrays.asList(persistence.getImagesForSubmission(submission.getId())));
    }

    return submissions;
  }

  /**
   * Creates a new SubmissionStatus in the persistence store. The id of the submission status must
   * be UNSET_ID. The manager will assign an id before persisting the change.
   *
   * @param submissionStatus The submission status to add to the persistence store
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If submissionStatus or operator is null
   * @throws IllegalArgumentException If the id is not UNSET_ID
   * @throws IllegalArgumentException If the submission status (once an id and creation/modification
   *     user/date are assigned) is not in a state to be persisted (i.e. if isValidToPersist returns
   *     false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void createSubmissionStatus(SubmissionStatus submissionStatus, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(null, operator, "create new SubmissionStatus.").toString());
    persistenceUploadManagerHelper(
        submissionStatus, "submissionStatus", operator, CREATE_FLAG, submissionStatusIdGenerator);
  }

  /**
   * Updates the info for submission status in the persistence store. All fields of the submission
   * status must have values assigned when this method is called.
   *
   * @param submissionStatus The submission status to update
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If submissionStatus or operator is null
   * @throws IllegalArgumentException The id is UNSET_ID
   * @throws IllegalArgumentException If the submission status (once the modification user/date is
   *     set) is not in a state to be persisted (i.e. if isValidToPersist returns false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void updateSubmissionStatus(SubmissionStatus submissionStatus, String operator)
      throws UploadPersistenceException {
    log.debug(
        new LogMessage(getId(submissionStatus), operator, "update SubmissionStatus.").toString());

    persistenceUploadManagerHelper(
        submissionStatus, "submissionStatus", operator, UPDATE_FLAG, null);
  }

  /**
   * Removes the given submission status (by id) from the persistence. The id of the
   * submissionStatus can not be UNSET_ID.
   *
   * @param submissionStatus The submission status to remove
   * @param operator The name of the operator making the change to the persistence store
   * @throws IllegalArgumentException If submissionStatus or operator is null
   * @throws IllegalArgumentException If the id of the submission status is UNSET_ID
   * @throws IllegalArgumentException If the submissionStatus (once the modification user/date is
   *     set) is not in a state to be persisted (i.e. if isValidToPersist returns false)
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  public void removeSubmissionStatus(SubmissionStatus submissionStatus, String operator)
      throws UploadPersistenceException {
    log.debug(
        new LogMessage(getId(submissionStatus), operator, "remove SubmissionStatus.").toString());

    persistenceUploadManagerHelper(
        submissionStatus, "submissionStatus", operator, REMOVE_FLAG, null);
  }

  /**
   * Gets all the submission statuses in the persistence store.
   *
   * @return All SubmissionStatuses in the persistence store.
   * @throws UploadPersistenceException If there is an error accessing the persistence store
   */
  public SubmissionStatus[] getAllSubmissionStatuses() throws UploadPersistenceException {
    log.debug("get All submission statuses, delegate to persistence.");

    return persistence.loadSubmissionStatuses(persistence.getAllSubmissionStatusIds());
  }

  /**
   * Creates a new SubmissionType in the persistence store. The id of the submission type must be
   * UNSET_ID. The manager will assign an id before persisting the change.
   *
   * @param submissionType the submission type to add to the persistence store.
   * @param operator the name of the operator making the change to the persistence store.
   * @throws IllegalArgumentException if submissionType or operator is <code>null</code>, id is not
   *     UNSET_ID, or the submission type (once an id and creation/modification user/date are
   *     assigned) is not in a state to be persisted (i.e. if isValidToPersist returns false).
   * @throws UploadPersistenceException if there is an error making the change in the persistence.
   * @since 1.1
   */
  public void createSubmissionType(SubmissionType submissionType, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(null, operator, "Create new SubmissionType.").toString());

    persistenceUploadManagerHelper(
        submissionType, "submissionType", operator, CREATE_FLAG, submissionTypeIdGenerator);
  }

  /**
   * Updates the info for submission type in the persistence store. All fields of the submission
   * type must have values assigned when this method is called.
   *
   * @param submissionType the submission type to update
   * @param operator the name of the operator making the change to the persistence store
   * @throws IllegalArgumentException if submissionType or operator is <code>null</code>, or the
   *     submission type (once the modification user/date is set) is not in a state to be persisted
   *     (i.e. if isValidToPersist returns false)
   * @throws UploadPersistenceException if there is an error making the change in the persistence.
   * @since 1.1
   */
  public void updateSubmissionType(SubmissionType submissionType, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(getId(submissionType), operator, "Update SubmissionType.").toString());

    persistenceUploadManagerHelper(submissionType, "submissionType", operator, UPDATE_FLAG, null);
  }

  /**
   * Removes the given submission type (by id) from the persistence. The id of the submissionType
   * can not be UNSET_ID.
   *
   * @param submissionType the submission type to remove.
   * @param operator the name of the operator making the change to the persistence store.
   * @throws IllegalArgumentException if submissionType or operator is <code>null</code> or the id
   *     of the submission type is UNSET_ID.
   * @throws UploadPersistenceException if there is an error making the change in the persistence.
   * @since 1.1
   */
  public void removeSubmissionType(SubmissionType submissionType, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(getId(submissionType), operator, "Remove SubmissionType.").toString());

    persistenceUploadManagerHelper(submissionType, "submissionType", operator, REMOVE_FLAG, null);
  }

  /**
   * Gets all the submission types in the persistence store.
   *
   * @return All SubmissionTypes in the persistence store.
   * @throws UploadPersistenceException if there is an error accessing the persistence store.
   * @since 1.1
   */
  public SubmissionType[] getAllSubmissionTypes() throws UploadPersistenceException {
    log.debug("Get all submission types, delegate to persistence.");

    return persistence.loadSubmissionTypes(persistence.getAllSubmissionTypeIds());
  }

  /**
   * Creates the given submission image in persistence.
   *
   * @param submissionImage the submission image to be created in persistence
   * @param operator the user that performs the operation
   * @throws IllegalArgumentException if submissionImage or operator is null, or
   *     submissionImage.isValidToPersist() returns false
   * @throws UploadPersistenceException if some error occurred when accessing the persistence
   * @since 1.2
   */
  public void createSubmissionImage(SubmissionImage submissionImage, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(null, operator, "Create new SubmissionImage.").toString());
    DeliverableHelper.checkObjectNotNull(submissionImage, "submissionImage");
    DeliverableHelper.checkObjectNotNull(submissionImage, "submissionImage");
    DeliverableHelper.checkObjectNotNull(operator, "operator");
    if (!submissionImage.isValidToPersist()) {
      log.error("The submission image is invalid to persist.");
      throw new IllegalArgumentException("The submission image is invalid to persist.");
    }

    // set create date and modify date
    Date now = new Date();
    submissionImage.setCreateDate(now);
    submissionImage.setModifyDate(now);

    persistence.addSubmissionImage(submissionImage);
  }

  /**
   * Updates the given submission image in persistence.
   *
   * @param submissionImage the submission image to be updated in persistence
   * @param operator the user that performs the operation
   * @throws IllegalArgumentException if submissionImage or operator is null, or
   *     submissionImage.isValidToPersist() returns false
   * @throws UploadPersistenceException if some error occurred when accessing the persistence
   * @since 1.2
   */
  public void updateSubmissionImage(SubmissionImage submissionImage, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(null, operator, "Update SubmissionImage.").toString());
    DeliverableHelper.checkObjectNotNull(submissionImage, "submissionImage");
    DeliverableHelper.checkObjectNotNull(operator, "operator");
    if (!submissionImage.isValidToPersist()) {
      log.error("The submission image is invalid to persist.");
      throw new IllegalArgumentException("The submission image is invalid to persist.");
    }

    // set modify date
    submissionImage.setModifyDate(new Date());

    persistence.updateSubmissionImage(submissionImage);
  }

  /**
   * Removes the submission image in persistence.
   *
   * @param submissionImage the submission image to be removed in persistence
   * @param operator the user that performs the operation
   * @throws IllegalArgumentException if submissionImage/operator is null,
   *     submissionImage.getSubmissionId() <= 0 or submissionImage.getImageId() <= 0
   * @throws UploadPersistenceException if some error occurred when accessing the persistence
   * @since 1.2
   */
  public void removeSubmissionImage(SubmissionImage submissionImage, String operator)
      throws UploadPersistenceException {
    log.debug(new LogMessage(null, operator, "Remove SubmissionImage.").toString());
    DeliverableHelper.checkObjectNotNull(submissionImage, "submissionImage");
    DeliverableHelper.checkObjectNotNull(operator, "operator");
    DeliverableHelper.checkGreaterThanZero(
        submissionImage.getSubmissionId(), "The submission id of the submission image");
    DeliverableHelper.checkGreaterThanZero(
        submissionImage.getImageId(), "The image id of the submission image");

    persistence.removeSubmissionImage(submissionImage);
  }

  /**
   * Retrieves the MIME type with the given ID from persistence.
   *
   * @param id the ID of the MIME type to be retrieved
   * @return the retrieved MIME type or null if MIME type with the given ID doesn't exist
   * @throws IllegalArgumentException if id <= 0
   * @throws UploadPersistenceException if some error occurred when retrieving the MimeType from
   *     persistence
   * @since 1.2
   */
  public MimeType getMimeType(long id) throws UploadPersistenceException {
    log.debug(new LogMessage(id, null, "Retrieve MimeType, delegate to persistence.").toString());
    DeliverableHelper.checkGreaterThanZero(id, "id");
    return persistence.loadMimeType(id);
  }

  /**
   * Retrieves all MIME types from persistence. This method returns an empty array if no MIME types
   * were found.
   *
   * @return the retrieved MIME types (not null, doesn't contain null)
   * @throws UploadPersistenceException if some error occurred when accessing the persistence
   * @since 1.2
   */
  public MimeType[] getAllMimeTypes() throws UploadPersistenceException {
    log.debug(
        new LogMessage(null, null, "Retrieve All MimeTypes, delegate to persistence.").toString());
    return persistence.loadMimeTypes(persistence.getAllMimeTypeIds());
  }

  /**
   * Retrieves the images for submission with the given ID. If submissionId is unknown, this method
   * returns an empty array.
   *
   * @param submissionId the ID of the submission
   * @return the retrieved images for submission (not null, doesn't contain null)
   * @throws IllegalArgumentException if submissionId <= 0
   * @throws UploadPersistenceException if some error occurred when accessing the persistence
   * @since 1.2
   */
  public SubmissionImage[] getImagesForSubmission(long submissionId)
      throws UploadPersistenceException {
    log.debug(
        new LogMessage(
                null,
                null,
                MessageFormat.format(
                    "Retrieve SubmissionImages for submission with id [{0}].", submissionId))
            .toString());

    DeliverableHelper.checkGreaterThanZero(submissionId, "submissionId");

    return persistence.getImagesForSubmission(submissionId);
  }

  /**
   * Helper method for PersistenceUploadManager. All the create, update, remove methods delegate to
   * this method. It create (update, remove) AuditedDeliverableStructure object from the
   * persistence.
   *
   * @param obj object to manipulate
   * @param name object name which in manipulate in this method
   * @param operator the operator name
   * @param operation identifier for the caller
   * @param idGenerator the IDGenerator to use
   * @throws IllegalArgumentException if obj or operator is <code>null</code>, id is not UNSET_ID,
   *     or the obj (once an id and creation/modification user/date are assigned) is not in a state
   *     to be persisted (i.e. if isValidToPersist returns false).
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  private void persistenceUploadManagerHelper(
      AuditedDeliverableStructure obj,
      String name,
      String operator,
      int operation,
      IDGenerator idGenerator)
      throws UploadPersistenceException {
    DeliverableHelper.checkObjectNotNull(obj, name);
    DeliverableHelper.checkObjectNotNull(operator, "operator");

    // Get current time.
    Date now = new Date();

    if (operation == CREATE_FLAG) {
      // Branch for CREATE_FLAG operation.

      // id should not been set yet.
      if (obj.getId() != AuditedDeliverableStructure.UNSET_ID) {
        log.error("The id of the " + name + " must be UNSET_ID, " + obj.getId() + " received");
        throw new IllegalArgumentException(
            "The id of the " + name + " must be UNSET_ID, " + obj.getId() + " received");
      }

      // Set the creation user and creation date.
      obj.setCreationUser(operator);
      obj.setCreationTimestamp(now);

      // Create an id using idGenerator.
      try {
        obj.setId(idGenerator.getNextID());
        log.debug("generate next id from the idGenerator:" + idGenerator.getIDName());
      } catch (IDGenerationException ide) {
        log.error("failed to generate an " + name + " id.", ide);
        throw new UploadPersistenceException("Failed to generate an " + name + " id.", ide);
      }
    } else {
      // Branch for UPDATE_FLAG, REMOVE_FLAG operation

      // id field can not equal to UNSET_ID.
      if (obj.getId() == AuditedDeliverableStructure.UNSET_ID) {
        log.error("The id of the " + name + " can't be UNSET_ID.");
        throw new IllegalArgumentException("The id of the " + name + " can't be UNSET_ID.");
      }
    }

    // Set the modification user and modification date.
    obj.setModificationUser(operator);
    obj.setModificationTimestamp(now);

    // Check if the object is ready to persist.
    if (!obj.isValidToPersist()) {
      log.error("The " + name + " is not in a state to be persisted.");
      throw new IllegalArgumentException("The " + name + " is not in a state to be persisted.");
    }

    // Save change to persistence.
    persistApply(obj, operation);
  }

  /**
   * This is a helper method for applying operations on persistence layer.
   *
   * @param obj the object to manipulate
   * @param operation operation identifier for the caller
   * @throws UploadPersistenceException If there is an error making the change in the persistence
   */
  private void persistApply(AuditedDeliverableStructure obj, int operation)
      throws UploadPersistenceException {
    if (obj instanceof Upload) {
      switch (operation) {
        case CREATE_FLAG:
          // Add to persistence
          persistence.addUpload((Upload) obj);
          break;

        case UPDATE_FLAG:
          // Update the persistence
          persistence.updateUpload((Upload) obj);
          break;

        case REMOVE_FLAG:
          // Remove update from persistence
          persistence.removeUpload((Upload) obj);
          break;

        default:
          break;
      }

    } else if (obj instanceof UploadType) {
      switch (operation) {
        case CREATE_FLAG:
          // Add to persistence
          persistence.addUploadType((UploadType) obj);
          break;

        case UPDATE_FLAG:
          // Update the persistence
          persistence.updateUploadType((UploadType) obj);
          break;

        case REMOVE_FLAG:
          // Remove update type from persistence
          persistence.removeUploadType((UploadType) obj);
          break;

        default:
          break;
      }
    } else if (obj instanceof UploadStatus) {
      switch (operation) {
        case CREATE_FLAG:
          // Add to persistence
          persistence.addUploadStatus((UploadStatus) obj);
          break;

        case UPDATE_FLAG:
          // Update the persistence
          persistence.updateUploadStatus((UploadStatus) obj);
          break;

        case REMOVE_FLAG:
          // Remove upload status from persistence
          persistence.removeUploadStatus((UploadStatus) obj);
          break;

        default:
          break;
      }
    } else if (obj instanceof Submission) {
      switch (operation) {
        case CREATE_FLAG:
          // Add to persistence
          persistence.addSubmission((Submission) obj);
          break;

        case UPDATE_FLAG:
          // Update the persistence
          persistence.updateSubmission((Submission) obj);
          break;

        case REMOVE_FLAG:
          // Remove submission from persistence
          persistence.removeSubmission((Submission) obj);
          break;

        default:
          break;
      }
    } else if (obj instanceof SubmissionStatus) {
      switch (operation) {
        case CREATE_FLAG:
          // Add to persistence
          persistence.addSubmissionStatus((SubmissionStatus) obj);
          break;

        case UPDATE_FLAG:
          // Update the persistence
          persistence.updateSubmissionStatus((SubmissionStatus) obj);
          break;

        case REMOVE_FLAG:
          // Remove submission from persistence
          persistence.removeSubmissionStatus((SubmissionStatus) obj);
          break;

        default:
          break;
      }
    } else if (obj instanceof SubmissionType) {
      switch (operation) {
        case CREATE_FLAG:
          // Add to persistence
          persistence.addSubmissionType((SubmissionType) obj);
          break;

        case UPDATE_FLAG:
          // Update the persistence
          persistence.updateSubmissionType((SubmissionType) obj);
          break;

        case REMOVE_FLAG:
          // Remove from persistence
          persistence.removeSubmissionType((SubmissionType) obj);
          break;

        default:
          break;
      }
    }
  }

  /**
   * Gets the id field from the given entity. if the entity is null, should return -1.
   *
   * @param entity the entity to extract the id field.
   * @return the value of id.
   * @since 1.2
   */
  private static long getId(IdentifiableDeliverableStructure entity) {
    if (entity != null) {
      return entity.getId();
    }

    return -1;
  }
}
