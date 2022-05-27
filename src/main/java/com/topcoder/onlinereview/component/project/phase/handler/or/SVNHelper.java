/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.phase.handler.or;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNErrorMessage;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Random;

import static com.topcoder.onlinereview.component.util.SpringUtils.getPropertyValue;

/**
 * <p>A helper utility class providing various method useful for accessing and managing the SVN repository for project.
 * </p>
 *
 * <p>
 * Thread-safety: This class is thread-safe as it does not contain any mutable inner state.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
public final class SVNHelper {

    /**
     * <p>A <code>Log</code> to be used for logging the events encountered while helper performs it's job.</p>
     */
    private static final Logger log = LoggerFactory.getLogger(SVNHelper.class.getName());

    /**
     * <p>A <code>String</code> array providing the SVN configuration.</p>
     */
    private static final String[] svnConfig;

    /**
     * <p>This static initializer sets up the SVN repository factories for <code>svn://</code> and <code>http://</code>/
     * <code>https://</code> protocols.</p>
     */
    static {
        SVNRepositoryFactoryImpl.setup();
        DAVRepositoryFactory.setup();
        svnConfig = new String[] {getPropertyValue("SVNConfig.Root"),
                getPropertyValue("SVNConfig.AuthUsername"),
                getPropertyValue("SVNConfig.AuthPassword"),
                getPropertyValue("SVNConfig.MkDirCommitMessage"),
                getPropertyValue("SVNConfig.TempFilesBaseDir"),
                getPropertyValue("SVNConfig.PathBasedPermissionsFileURL")};
    }

    /**
     * <p>Constructs new <code>SVNHelper</code> instance. This implementation does nothing.</p>
     */
    private SVNHelper() {
    }

    /**
     * <p>Creates the specified SVN directory in SVN repository.</p>
     *
     * @param path a <code>String</code> providing the path to directory to be created.
     * @return <code>true</code> if requested SVN repository has been created successfully; <code>false</code>
     *         otherwise.
     * @throws SVNException if an unexpected error occurs.
     * @throws IllegalArgumentException if specified <code>path</code> is <code>null</code> or empty.
     */
    static boolean createSVNDirectory(String path) throws SVNException {
        if ((path == null) || (path.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter [path] is not valid. [" + path + "]");
        }
        
        log.debug( "Attempting to create SVN directory " + path);
        SVNClientManager svnClientManager = getSVNClientManager();

        // Create directory in SVN repository
        SVNURL dirURL = SVNURL.parseURIEncoded(path);
        SVNCommitClient commitClient = svnClientManager.getCommitClient();
        try {
            commitClient.doMkDir(new SVNURL[]{dirURL}, getSVNCommitMessage(), null, true);
            log.info( "SVN directory " + path + " has been created successfully");
            return true;
        } catch (SVNException e) {
            SVNErrorMessage svnErrorMessage = e.getErrorMessage();
            if (svnErrorMessage != null) {
                SVNErrorCode errorCode = svnErrorMessage.getErrorCode();
                boolean dirAlreadyExists = SVNErrorCode.RA_DAV_ALREADY_EXISTS.equals(errorCode)
                                           || SVNErrorCode.FS_ALREADY_EXISTS.equals(errorCode);
                if (dirAlreadyExists) {
                    log.debug( "SVN directory " + path + " already exists");
                    return false;
                } else {
                    log.error( "SVN directory " + path + " has not been created. Reason: "
                                         + svnErrorMessage.getFullMessage());
                }
            }
            throw e;
        }
    }

    /**
     * <p>Grants specified users with specified permission for accessing the specified SVN module.</p>
     *
     * @param svnModule a <code>String</code> providing the path to SVN module in repository.
     * @param handles a <code>String</code> array listing the handles for users which are to be granted permission for
     *        accessing the specified SVN module.
     * @param permission a <code>String</code> specifying the permission to be granted.
     * @throws SVNException if an unexpected error occurs.
     * @throws IOException if an I/O error occurs while creating lock-file.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code> or specified
     *         <code>svnModule</code> is empty or it does not map to SVN repository root or path-based permissions file
     *         is not found.
     */
    public synchronized static void grantSVNPermission(String svnModule, String[] handles, String permission)
        throws SVNException, IOException {
        checkAgainstNull(svnModule, "svnModule");
        checkAgainstNull(permission, "permission");
        checkAgainstNull(handles, "handles");
        if (svnModule.trim().length() == 0) {
            throw new IllegalArgumentException("The parameter [svnModule] is not valid. [" + svnModule + "]");
        }
        // Check if requested SVN module corresponds to SVN repository root
        String svnRoot = getSVNRoot();
        if (!svnRoot.endsWith("/")) {
            svnRoot += "/";
        }
        if (!svnModule.startsWith(svnRoot)) {
            throw new IllegalArgumentException("SVN module URL " + svnModule + " does not map to SVN repository root ");
        }
        if (handles.length == 0) {
            return;
        }

        log.debug( "Attempting to grant '" + permission + "' permission for SVN module " + svnModule
                             + " to users " + Arrays.toString(handles));

        Random random = new Random();

        File authzFileLocalCopy = null;
        RandomAccessFile raf = null;
        File tempDir = null;
        try {
            SVNClientManager svnClientManager = getSVNClientManager();
            SVNCommitClient commitClient = svnClientManager.getCommitClient();
            SVNUpdateClient updateClient = svnClientManager.getUpdateClient();

            // Create the temporary directory for checking out the
            File baseTempDir = new File(getSVNTemporaryFilesBaseDir());
            String tempDirName = System.currentTimeMillis() + "_" + random.nextInt();
            tempDir = new File(baseTempDir, tempDirName);
            boolean tempDirCreated = tempDir.mkdirs();
            if (!tempDirCreated) {
                throw new IllegalArgumentException("Could not create temporary directory: "
                                                   + tempDir.getAbsolutePath());
            }
            tempDir.deleteOnExit();

            // Parse the URL for path-based permissions file into directory and file name
            String permissionsFileURL = getSVNPathBasedPermissionsFileURL();
            int pos = permissionsFileURL.lastIndexOf("/");
            String fileName = permissionsFileURL.substring(pos + 1);
            String dirURL = permissionsFileURL.substring(0, pos);

            // Checkout the path-based permissions file to temporary location
            SVNURL permissionsFileDirURL = SVNURL.parseURIEncoded(dirURL);
            updateClient.doCheckout(permissionsFileDirURL, tempDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.FILES,
                                    false);
            authzFileLocalCopy = new File(tempDir, fileName);

            // Add new permissions for specified handlers
            raf = new RandomAccessFile(authzFileLocalCopy, "rw");
            raf.seek(raf.length());
            raf.write(("\n[" + svnModule.substring(svnRoot.length() - 1) + "]\n").getBytes());
            for (String handle : handles) {
                raf.write((handle + " = " + permission + "\n").getBytes());
            }
            raf.close();

            // Commit updated path-based authorization file back to repository
            SVNCommitInfo commitInfo = commitClient
                .doCommit(new File[]{authzFileLocalCopy}, false, getSVNCommitMessage(), null, null,
                          false, false, SVNDepth.EMPTY);
            SVNErrorMessage svnErrorMessage = commitInfo.getErrorMessage();
            if (svnErrorMessage != null) {
                log.warn( "Failed to grant '" + permission + "' permission for SVN module " + svnModule
                                    + " to users " + Arrays.toString(handles) + ". Reason: "
                                    + svnErrorMessage.getMessage());
                throw new SVNException(svnErrorMessage);
            } else {
                log.info( "Granted '" + permission + "' permission for SVN module " + svnModule
                                    + " to users " + Arrays.toString(handles));
            }
        } finally {
            // Close file
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    log.error( "Failed to close path-based permissions file "
                                         + authzFileLocalCopy.getAbsolutePath(), e);
                }
            }

            // Delete temporary directory
            if (tempDir != null) {
                emptyDirectory(tempDir);
                boolean tempDirDeleted = tempDir.delete();
                if (!tempDirDeleted) {
                    log.warn( "Could not delete temporary directory: " + tempDir.getAbsolutePath());
                }
            }
        }
    }

    /**
     * <p>Removes all files and directories from the specified directory.</p>
     *
     * @param dir a <code>File</code> referencing the directory to be emptied.
     */
    private static void emptyDirectory(File dir) {
        File[] items = dir.listFiles();
        for (File item : items) {
            boolean deleted;
            if (item.isDirectory()) {
                emptyDirectory(item);
            }
            deleted = item.delete();
            if (!deleted) {
                log.warn( "Could not delete file/directory: " + item.getAbsolutePath());
            }
        }
    }

    /**
     * <p>Gets the manager for SV repository.</p>
     *
     * @return a <code>SVNClientManager</code> to be used for accessing SVN repository.
     */
    private static SVNClientManager getSVNClientManager() {
        ISVNAuthenticationManager authnManager = new BasicAuthenticationManager(
            getSVNAuthnUsername(), getSVNAuthnPassword());
        SVNClientManager svnClientManager = SVNClientManager.newInstance();
        svnClientManager.setAuthenticationManager(authnManager);
        return svnClientManager;
    }

    /**
     * <p>Verifies that specified value of method argument is not <code>null</code>. If not then an
     * <code>IllegalArgumentException</code> is thrown.</p>
     *
     * @param value an <code>Object</code> providing the value for method argument.
     * @param paramName a <code>String</code> providing the name for respective method argument.
     * @throws IllegalArgumentException if specified <code>value</code> is <code>null</code>.
     */
    private static void checkAgainstNull(Object value, String paramName) {
        if (value == null) {
            throw new IllegalArgumentException("The parameter [" + paramName + "] is NULL");
        }
    }

    /**
     * <p>Gets the URL for SVN repository.</p>
     *
     * @return a <code>String</code> providing the URL for SVN repository.
     */
    public static String getSVNRoot() {
        return svnConfig[0];
    }

    /**
     * <p>Gets the username for authentication to SVN repository.</p>
     *
     * @return a <code>String</code> providing the username to be used for authenticating to SVN repository.
     */
    public static String getSVNAuthnUsername() {
        return svnConfig[1];
    }

    /**
     * <p>Gets the password for authentication to SVN repository.</p>
     *
     * @return a <code>String</code> providing the password to be used for authenticating to SVN repository.
     */
    public static String getSVNAuthnPassword() {
        return svnConfig[2];
    }

    /**
     * <p>Gets the message for committing the new directories to SVN repository.</p>
     *
     * @return a <code>String</code> providing message for committing the new directories to SVN repository.
     */
    public static String getSVNCommitMessage() {
        return svnConfig[3];
    }

    /**
     * <p>Gets the path to local directory where the SVN files can be temporarily checked to.</p>
     *
     * @return a <code>String</code> providing the path to local directory where the SVN files can be temporarily
     *         checked to.
     */
    public static String getSVNTemporaryFilesBaseDir() {
        return svnConfig[4];
    }

    /**
     * <p>Gets the URL for path-based permissions file in SVN repository.</p>
     *
     * @return a <code>String</code> providing the URL for path-based permissions file in SVN repository.
     */
    public static String getSVNPathBasedPermissionsFileURL() {
        return svnConfig[5];
    }
}