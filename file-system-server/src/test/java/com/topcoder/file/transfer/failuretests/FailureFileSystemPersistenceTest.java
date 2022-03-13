/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.file.transfer.failuretests;

import com.topcoder.file.transfer.persistence.FilePersistenceException;
import com.topcoder.file.transfer.persistence.FileSystemPersistence;

import junit.framework.TestCase;


/**
 * Test <code>FileSystemPersistence</code> class for failure.
 *
 * @author fairytale
 * @version 1.0
 */
public class FailureFileSystemPersistenceTest extends TestCase {
    /** The invalid file name used for testing, represents a directory. */
    private static final String INV_FILENAME = "dir";

    /** The location of the persistence file. */
    private static final String LOCATION = "test_files/failure";

    /** The main <code>FileSystemPersistence</code> instance used to test. */
    private FileSystemPersistence persistence;

    /**
     * Initialization for all tests here.
     */
    protected void setUp() {
        persistence = new FileSystemPersistence();
    }

    /**
     * Test <code>FileSystemPersistence(int)</code> method for failure. <code>IllegalArgumentException</code> should be
     * thrown if the int argument is not positive.
     */
    public void testFileSystemPersistenceForIllegalArgumentException() {
        try {
            new FileSystemPersistence(0);
            fail("the IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test <code>createFile(String, String)</code> method for failure. <code>NullPointerException</code> should be
     * thrown if argument is null.
     *
     * @throws Exception to Junit.
     */
    public void testCreateFileForNullPointerException_Loc()
        throws Exception {
        try {
            persistence.createFile(null, INV_FILENAME);
            fail("the NullPointerException should be thrown!");
        } catch (NullPointerException e) {
            // Good
        }
    }

    /**
     * Test <code>createFile(String, String)</code> method for failure. <code>NullPointerException</code> should be
     * thrown if argument is null.
     *
     * @throws Exception to Junit.
     */
    public void testCreateFileForNullPointerException_Filename()
        throws Exception {
        try {
            persistence.createFile(LOCATION, null);
            fail("the NullPointerException should be thrown!");
        } catch (NullPointerException e) {
            // Good
        }
    }

    /**
     * Test <code>createFile(String, String)</code> method for failure. <code>IllegalArgumentException</code> should be
     * thrown if location is empty.
     *
     * @throws Exception to Junit.
     */
    public void testCreateFileForIllegalArgumentException_Loc()
        throws Exception {
        try {
            persistence.createFile("  ", INV_FILENAME);
            fail("the IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test <code>createFile(String, String)</code> method for failure. <code>IllegalArgumentException</code> should be
     * thrown if persistenceFile is empty.
     *
     * @throws Exception to Junit.
     */
    public void testCreateFileForIllegalArgumentException_Filename()
        throws Exception {
        try {
            persistence.createFile(LOCATION, "  ");
            fail("the IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test <code>createFile(String, String)</code> method for failure. <code>FilePersistenceException</code> should be
     * thrown if the file is a directory.
     */
    public void testCreateFileForFilePersistenceException() {
        try {
            persistence.createFile(LOCATION, INV_FILENAME);
            fail("the FilePersistenceException should be thrown!");
        } catch (FilePersistenceException e) {
            // Good
        }
    }

    /**
     * Test <code>appendBytes(String, byte[])</code> method for failure. <code>NullPointerException</code> should be
     * thrown if fileCreationId is null.
     *
     * @throws Exception to Junit.
     */
    public void testAppendBytesForNullPointerException_Id()
        throws Exception {
        try {
            String fileCreationId = null;
            byte[] bytes = new byte[2];
            persistence.appendBytes(fileCreationId, bytes);
            fail("the NullPointerException should be thrown!");
        } catch (NullPointerException e) {
            // Good
        }
    }

    /**
     * Test <code>appendBytes(String, byte[])</code> method for failure. <code>NullPointerException</code> should be
     * thrown if bytes is null.
     *
     * @throws Exception to Junit.
     */
    public void testAppendBytesForNullPointerException_Byte()
        throws Exception {
        try {
            String fileCreationId = "12345";
            byte[] bytes = null;
            persistence.appendBytes(fileCreationId, bytes);
            fail("the NullPointerException should be thrown!");
        } catch (NullPointerException e) {
            // Good
        }
    }

    /**
     * Test <code>appendBytes(String, byte[])</code> method for failure. <code>IllegalArgumentException</code> should
     * be thrown if fileCreationId is empty.
     *
     * @throws Exception to Junit.
     */
    public void testAppendBytesForIllegalArgumentException()
        throws Exception {
        try {
            String fileCreationId = "  ";
            byte[] bytes = new byte[2];
            persistence.appendBytes(fileCreationId, bytes);
            fail("the IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test <code>appendBytes(String, byte[])</code> method for failure. <code>FilePersistenceException</code> should
     * be thrown if file creation id does not exist in map.
     */
    public void testAppendBytesForFilePersistenceException() {
        try {
            String fileCreationId = "12345";
            byte[] bytes = new byte[2];
            persistence.appendBytes(fileCreationId, bytes);
            fail("the FilePersistenceException should be thrown!");
        } catch (FilePersistenceException e) {
            // Good
        }
    }

    /**
     * Test <code>closeFile(String)</code> method for failure. <code>NullPointerException</code> should be thrown if
     * argument is null.
     *
     * @throws Exception to Junit.
     */
    public void testCloseFileForNullPointerException()
        throws Exception {
        try {
            persistence.closeFile(null);
            fail("the NullPointerException should be thrown!");
        } catch (NullPointerException e) {
            // Good
        }
    }

    /**
     * Test <code>closeFile(String)</code> method for failure. <code>IllegalArgumentException</code> should be thrown
     * if string is empty.
     *
     * @throws Exception to Junit.
     */
    public void testCloseFileForIllegalArgumentException()
        throws Exception {
        try {
            persistence.closeFile(" ");
            fail("the IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test <code>closeFile(String)</code> method for failure. <code>FilePersistenceException</code> should be thrown
     * if file creation id does not exist in map..
     */
    public void testCloseFileForFilePersistenceException() {
        try {
            persistence.closeFile("1234");
            fail("the FilePersistenceException should be thrown!");
        } catch (FilePersistenceException e) {
            // Good
        }
    }

    /**
     * Test <code>deleteFile(String, String)</code> method for failure. <code>NullPointerException</code> should be
     * thrown if location is null.
     *
     * @throws Exception to Junit.
     */
    public void testDeleteFileForNullPointerException_Loc()
        throws Exception {
        try {
            persistence.deleteFile(null, INV_FILENAME);
            fail("the NullPointerException should be thrown!");
        } catch (NullPointerException e) {
            // Good
        }
    }

    /**
     * Test <code>deleteFile(String, String)</code> method for failure. <code>NullPointerException</code> should be
     * thrown if filename is null.
     *
     * @throws Exception to Junit.
     */
    public void testDeleteFileForNullPointerException_Filename()
        throws Exception {
        try {
            persistence.deleteFile(LOCATION, null);
            fail("the NullPointerException should be thrown!");
        } catch (NullPointerException e) {
            // Good
        }
    }

    /**
     * Test <code>deleteFile(String, String)</code> method for failure. <code>IllegalArgumentException</code> should be
     * thrown if location is empty.
     *
     * @throws Exception to Junit.
     */
    public void testDeleteFileForIllegalArgumentException_Loc()
        throws Exception {
        try {
            persistence.deleteFile(" ", INV_FILENAME);
            fail("the IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test <code>deleteFile(String, String)</code> method for failure. <code>IllegalArgumentException</code> should be
     * thrown if filename is empty.
     *
     * @throws Exception to Junit.
     */
    public void testDeleteFileForIllegalArgumentException_Filename()
        throws Exception {
        try {
            persistence.deleteFile(LOCATION, " ");
            fail("the IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test <code>deleteFile(String, String)</code> method for failure. <code>FilePersistenceException</code> should be
     * thrown if the file is a directory.
     */
    public void testDeleteFileForFilePersistenceException() {
        try {
            persistence.deleteFile(LOCATION, INV_FILENAME);
            fail("the FilePersistenceException should be thrown!");
        } catch (FilePersistenceException e) {
            // Good
        }
    }

    /**
     * Test <code>getFileBytesIterator(String, String)</code> method for failure. <code>NullPointerException</code>
     * should be thrown if location is null.
     *
     * @throws Exception to Junit.
     */
    public void testGetFileBytesIteratorForNullPointerException_Loc()
        throws Exception {
        try {
            persistence.getFileBytesIterator(null, INV_FILENAME);
            fail("the NullPointerException should be thrown!");
        } catch (NullPointerException e) {
            // Good
        }
    }

    /**
     * Test <code>getFileBytesIterator(String, String)</code> method for failure. <code>NullPointerException</code>
     * should be thrown if filename is null.
     *
     * @throws Exception to Junit.
     */
    public void testGetFileBytesIteratorForNullPointerException_Filename()
        throws Exception {
        try {
            persistence.getFileBytesIterator(LOCATION, null);
            fail("the NullPointerException should be thrown!");
        } catch (NullPointerException e) {
            // Good
        }
    }

    /**
     * Test <code>getFileBytesIterator(String, String)</code> method for failure. <code>IllegalArgumentException</code>
     * should be thrown if location is empty.
     *
     * @throws Exception to Junit.
     */
    public void testGetFileBytesIteratorForIllegalArgumentException_Loc()
        throws Exception {
        try {
            persistence.getFileBytesIterator(" ", INV_FILENAME);
            fail("the IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test <code>getFileBytesIterator(String, String)</code> method for failure. <code>IllegalArgumentException</code>
     * should be thrown if filename is empty.
     *
     * @throws Exception to Junit.
     */
    public void testGetFileBytesIteratorForIllegalArgumentException_Filename()
        throws Exception {
        try {
            persistence.getFileBytesIterator(LOCATION, " ");
            fail("the IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test <code>getFileBytesIterator(String, String)</code> method for failure. <code>FilePersistenceException</code>
     * should be thrown if the file is a directory.
     */
    public void testGetFileBytesIteratorForFilePersistenceException() {
        try {
            persistence.getFileBytesIterator(LOCATION, INV_FILENAME);
            fail("the FilePersistenceException should be thrown!");
        } catch (FilePersistenceException e) {
            // Good
        }
    }

    /**
     * Test <code>getFileSize(String, String)</code> method for failure. <code>NullPointerException</code> should be
     * thrown if location is null.
     *
     * @throws Exception to Junit.
     */
    public void testGetFileSizeForNullPointerException_Loc()
        throws Exception {
        try {
            persistence.getFileSize(null, INV_FILENAME);
            fail("the NullPointerException should be thrown!");
        } catch (NullPointerException e) {
            // Good
        }
    }

    /**
     * Test <code>getFileSize(String, String)</code> method for failure. <code>NullPointerException</code> should be
     * thrown if filename is null.
     *
     * @throws Exception to Junit.
     */
    public void testGetFileSizeForNullPointerException_Filename()
        throws Exception {
        try {
            persistence.getFileSize(LOCATION, null);
            fail("the NullPointerException should be thrown!");
        } catch (NullPointerException e) {
            // Good
        }
    }

    /**
     * Test <code>getFileSize(String, String)</code> method for failure. <code>IllegalArgumentException</code> should
     * be thrown if location is empty.
     *
     * @throws Exception to Junit.
     */
    public void testGetFileSizeForIllegalArgumentException_Loc()
        throws Exception {
        try {
            persistence.getFileSize(" ", INV_FILENAME);
            fail("the IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * Test <code>getFileSize(String, String)</code> method for failure. <code>IllegalArgumentException</code> should
     * be thrown if filename is empty.
     *
     * @throws Exception to Junit.
     */
    public void testGetFileSizeForIllegalArgumentException_Filename()
        throws Exception {
        try {
            persistence.getFileSize(LOCATION, " ");
            fail("the IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }
}
