/*
 * Copyright (C) 2007-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.reviewupload;

import com.topcoder.onlinereview.component.id.UUID;
import com.topcoder.onlinereview.component.id.UUIDType;
import com.topcoder.onlinereview.component.id.UUIDUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.activation.DataHandler;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.MessageFormat;

/**
 * <p>
 * This is the default implementation of <code>UploadExternalServices</code> interface. For all upload* methods
 * it saves the file into fileStorageLocation and then delegates to UploadServices implementation. The
 * setSubmission delegate directly to <code>UploadServices</code> instance.
 * </p>
 * <p>
 * A sample configuration file that can be used is given below.
 *
 * <pre>
 *  &lt;Config name=&quot;com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices&quot;&gt;
 *      &lt;Property name=&quot;objectFactoryNamespace&quot;&gt;
 *          &lt;Value&gt;myObjectFactory&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;uploadServicesIdentifier&quot;&gt;
 *          &lt;Value&gt;uploadServices&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;filenamePattern&quot;&gt;
 *          &lt;Value&gt;submission-{0}-{1}&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;fileStorageLocation&quot;&gt;
 *          &lt;Value&gt;test_files/upload&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Config&gt;
 * </pre>
 * </p>
 *
 * <p>
 * Changes in version 1.1: Added
 * </p>
 *
 * <p>
 * Thread safe: The thread safety is completely relied to the uploadServices implementation because it's impossible
 * to change the other variables
 * </p>
 *
 * @author fabrizyo, saarixx, cyberjag, TCSDEVELOPER
 * @version 1.1.2
 * @since 1.0
 */
@Slf4j
@Component
public class UploadExternalServices {

    /**
     * <p>
     * Represents the default namespace for this class used to load the configuration with
     * <code>ConfigManager</code>.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = UploadExternalServices.class.getName();

    /**
     * <p>
     * Represents the default pattern to construct the entire filename to store the file. {0} represents the UUID
     * generated, {1} represents the filename as argument.
     * </p>
     */
    public static final String DEFAULT_FILENAME_PATTERN = "{0}_{1}";

    /**
     * <p>
     * Represents the buffer size used for input stream reading.
     * </p>
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * <p>
     * Represents the internal <code>UploadServices</code> to delegate the calls. In upload* methods the filename
     * is generated before passing to this services. It is defined in constructor and cannot be <code>null</code>.
     * </p>
     */
    @Autowired
    private UploadServices uploadServices;

    /**
     * <p>
     * Represents the pattern to construct the filename. It's used with
     * <code>MessageFormat.format(filenamePattern, uuidGenerated, filename)</code>. It is defined in constructor
     * and cannot be <code>null</code> or empty.
     * </p>
     */
    @Value("${filenamePattern:{0}_{1}}")
    private String filenamePattern;

    /**
     * <p>
     * Represents the path where the files will be stored. It is defined in constructor and cannot be
     * <code>null</code> or empty.
     * </p>
     */
    @Value("${fileStorageLocation:#{null}}")
    private String fileStorageLocation;

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     * <p>
     * If the project allows multiple submissions for users, it will add the new submission and return. If multiple
     * submission are not allowed for the project, firstly it will add the new submission, secondly mark previous
     * submissions as deleted and then return.
     * </p>
     *
     * @param projectId  the project's id
     * @param userId     the user's id
     * @param filename   the file name to use
     * @param submission the submission file data
     *
     * @return the id of the new submission
     *
     * @throws RemoteException          if an internal exception occurs (wrap it)
     * @throws UploadServicesException  if any error related to UploadService occurs
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @since 1.0
     */
    public long uploadSubmission(long projectId, long userId, String filename, DataHandler submission)
        throws RemoteException, UploadServicesException {
        log.debug("Entered DefaultUploadExternalServices#uploadSubmission(long, long, String, DataHandler)");
        Helper.checkId(projectId, "projectId", log);
        Helper.checkId(userId, "userId", log);
        Helper.checkString(filename, "filename", log);
        Helper.checkNull(submission, "submission", log);

        File newFile = createNewFile(filename, submission);
        String filenameGenerated = newFile.getName();
        Helper.logFormat(log, "Submission file created {0}", new Object[]{newFile.getAbsolutePath()});
        try {
            // delegate to the similar method in uploadServices without DataHandler passing only
            // filenameGenerated
            return uploadServices.uploadSubmission(projectId, userId, filenameGenerated);
            // if there is an exception during this processing then the file previous written must be deleted
            // delete the file temp generated with Axis
        } catch (UploadServicesException e) {
            newFile.delete();
            throw e;
        } finally {
            if (submission.getName() != null) {
                new File(submission.getName()).delete();
            }
            Helper.logFormat(log,
                    "Exited DefaultUploadExternalServices#uploadSubmission(long, long, String, DataHandler)");
        }
    }

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     *
     * @param projectId the project's id
     * @param userId    the user's id
     * @param filename  the file name to use
     * @param finalFix  the final fix file data
     *
     * @return the id of the created final fix submission
     *
     * @throws UploadServicesException  if any error related to UploadService occurs
     * @throws RemoteException          if an internal exception occurs (wrap it)
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @since 1.0
     */
    public long uploadFinalFix(long projectId, long userId, String filename, DataHandler finalFix)
        throws RemoteException, UploadServicesException {
        Helper.logFormat(log,
                "Entered DefaultUploadExternalServices#uploadFinalFix(long, long, String, DataHandler)");
        Helper.checkId(projectId, "projectId", log);
        Helper.checkId(userId, "userId", log);
        Helper.checkString(filename, "filename", log);
        Helper.checkNull(finalFix, "finalFix", log);

        File newFile = createNewFile(filename, finalFix);
        String filenameGenerated = newFile.getName();
        Helper.logFormat(log, "Final fix file created {0}", new Object[]{filenameGenerated});
        try {
            // delegate to the similar method in uploadServices without DataHandler passing only
            // filenameGenerated
            return uploadServices.uploadFinalFix(projectId, userId, filenameGenerated);
            // if there is an exception during this processing then the file previous written must be deleted
            // delete the file temp generated with Axis
        } catch (UploadServicesException e) {
            newFile.delete();
            throw e;
        } finally {
            if (finalFix.getName() != null) {
                new File(finalFix.getName()).delete();
            }
            Helper.logFormat(log,
                    "Exited DefaultUploadExternalServices#uploadFinalFix(long, long, String, DataHandler)");
        }
    }

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     *
     * @param projectId the project's id
     * @param userId    the user's id
     * @param filename  the file name to use
     * @param testCases the test cases data
     *
     * @return the id of the created test cases submission
     *
     * @throws UploadServicesException  if any error related to UploadService occurs
     * @throws RemoteException          if an internal exception occurs (wrap it)
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @since 1.0
     */
    public long uploadTestCases(long projectId, long userId, String filename, DataHandler testCases)
        throws RemoteException, UploadServicesException {
        Helper.logFormat(log,
                "Entered DefaultUploadExternalServices#uploadTestCases(long, long, String, DataHandler)");
        Helper.checkId(projectId, "projectId", log);
        Helper.checkId(userId, "userId", log);
        Helper.checkString(filename, "filename", log);
        Helper.checkNull(testCases, "testCases", log);

        File newFile = createNewFile(filename, testCases);
        String filenameGenerated = newFile.getName();
        Helper.logFormat(log, "Test case file created {0}", new Object[]{filenameGenerated});
        try {
            // delegate to the similar method in uploadServices without DataHandler passing only
            // filenameGenerated
            return uploadServices.uploadTestCases(projectId, userId, filenameGenerated);
            // if there is an exception during this processing then the file previous written must be deleted
            // delete the file temp generated with Axis
        } catch (UploadServicesException e) {
            newFile.delete();
            throw e;
        } finally {
            if (testCases.getName() != null) {
                new File(testCases.getName()).delete();
            }
            Helper.logFormat(log,
                    "Exited DefaultUploadExternalServices#uploadTestCases(long, long, String, DataHandler)");
        }
    }

    /**
     * <p>Adds a new specification upload for a user in a particular project. This
     * submission always overwrite the previous ones.</p>
     *
     * @param projectId     the project's id
     * @param userId        the user's id.
     * @param filename      the file name to use.
     * @param specification the data handler to be used
     *
     * @return the id of the created specification submission.
     *
     * @throws IllegalArgumentException if any id is < 0 or any argument is null or trim to empty
     * @throws RemoteException          if an internal exception occurs (wrap it)
     * @throws UploadServicesException  if any error related to UploadServices occurs
     * @since 1.1
     */
    public long uploadSpecification(long projectId, long userId, String filename, DataHandler specification)
        throws RemoteException, UploadServicesException {

        // log method entry
        log.debug("Entered DefaultUploadExternalServices#uploadSpecification(long, long, String, DataHandler)");

        // validate input parameters
        Helper.checkId(projectId, "projectId", log);
        Helper.checkId(userId, "userId", log);
        Helper.checkString(filename, "filename", log);
        Helper.checkNull(specification, "specification", log);

        // generate unique tmp file and write the content of the specification into it
        File newFile = createNewFile(filename, specification);
        String filenameGenerated = newFile.getName();
        Helper.logFormat(log, "Specification file created {0}", new Object[]{newFile.getAbsolutePath()});
        try {
            // delegates to the similar method in uploadServices without DataHandler passing only
            // generated filename
            return uploadServices.uploadSpecification(projectId, userId, filenameGenerated);
        } catch (UploadServicesException e) {
            // if there is an exception during this processing then the file previous written must be deleted
            // delete the file temp generated with Axis
            newFile.delete();
            throw e;
        } finally {
            // finally if the data handler has associated file then delete it
            if (specification.getName() != null) {
                new File(specification.getName()).delete();
            }
            // log method exit
            Helper.logFormat(log,
                    "Exited DefaultUploadExternalServices#uploadSpecification(long, long, String, DataHandler)");
        }
    }

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     *
     * @param submissionId       the submission's id
     * @param submissionStatusId the submission status id
     * @param operator           the operator which execute the operation
     *
     * @throws RemoteException            if an internal exception occurs
     * @throws InvalidSubmissionException if the submission does not exist
     * @throws InvalidSubmissionStatusException
     *                                    if the submission status does not exist
     * @throws PersistenceException       if some error occurs in persistence layer
     * @throws RemoteException            if an internal exception occurs (wrap it)
     * @throws IllegalArgumentException   if any id is &lt; 0 or if operator is null or trim to empty
     * @since 1.0
     */
    public void setSubmissionStatus(long submissionId, long submissionStatusId, String operator)
        throws RemoteException, InvalidSubmissionException, InvalidSubmissionStatusException,
        PersistenceException {
        Helper.logFormat(log,
                "Entered DefaultUploadExternalServices#setSubmissionStatus(long, long, String)");
        try {
            uploadServices.setSubmissionStatus(submissionId, submissionStatusId, operator);
        } finally {
            Helper.logFormat(log,
                    "Exited DefaultUploadExternalServices#setSubmissionStatus(long, long, String)");
        }
    }

    /**
     * Creates a new file with a unique name and copies the stream from the <code>DataHandler</code> to the new
     * file created.
     *
     * @param filename    the filename to use
     * @param dataHandler the <code>DataHandler</code> to get the <code>InputStream</code>
     *
     * @return the new file
     *
     * @throws RemoteException if an internal exception occurs
     * @since 1.0
     */
    private File createNewFile(String filename, DataHandler dataHandler) throws RemoteException {
        // generate the filename to storage the file
        String filenameGenerated;
        File newFile;
        do {
            UUID uuidGenerated = UUIDUtility.getNextUUID(UUIDType.TYPEINT32);
            filenameGenerated = MessageFormat.format(filenamePattern, uuidGenerated, filename);
            filenameGenerated = fileStorageLocation + File.separator + filenameGenerated;
            newFile = new File(filenameGenerated);
        } while (newFile.exists());

        // write the content of submission into file
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {

            inputStream = dataHandler.getDataSource().getInputStream();
            outputStream = new FileOutputStream(newFile);
            // write all bytes from input to output
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            log.error("Failed to read/write from the submission data source stream to new file", e);
            throw new RemoteException("Failed to read/write from the submission data source stream to new file", e);
        } finally {

            close(inputStream);
            close(outputStream);
        }
        return newFile;
    }

    /**
     * Adds the given user as a new submitter to the given project id. If the user is already added returns the the
     * id.
     *
     * @param projectId the project to which the user needs to be added
     * @param userId    the user to be added
     *
     * @return the added resource id
     *
     * @throws InvalidProjectException      if the project id is unknown
     * @throws InvalidUserException         if the user id is unknown
     * @throws InvalidProjectPhaseException if the phase of the project is not Registration.
     * @throws UploadServicesException      if any error occurs from UploadServices
     * @throws IllegalArgumentException     if any id is &lt; 0
     * @since 1.0
     */
    public long addSubmitter(long projectId, long userId) throws RemoteException, UploadServicesException {
        Helper.logFormat(log,
                "Entered DefaultUploadExternalServices#addSubmitter(long, long)");
        try {
            return uploadServices.addSubmitter(projectId, userId);
        } finally {
            Helper.logFormat(log,
                    "Exited DefaultUploadExternalServices#addSubmitter(long, long)");
        }
    }

    /**
     * <p>Closes passed stream ignoring any exception that may occur during this operation.</p>
     *
     * @param closeable instance of {@link Closeable} object
     */
    private static void close(Closeable closeable) {

        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            // ignores exception
        }
    }
}
