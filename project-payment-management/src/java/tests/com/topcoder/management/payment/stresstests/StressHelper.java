/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.stresstests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;

/**
 * This is the helper class for stress tests.
 *
 * @author itkankan
 * @version 1.0.1
 */
final class StressHelper {

    /**
     * The path of DB configuration file.
     */
    private static final String DB_PROPERTIES = "test_files/stress/config.properties";

    /**
     * This is a private constructor.
     */
    private StressHelper() {
        // empty
    }

    /**
     * Insert record into DB before stress test.
     *
     * @param i
     *            the value of column.
     * @throws Exception
     *             to JUnit.
     */
    static void prepareDB(int i) throws Exception {
        StressHelper.executeSql(
                "INSERT INTO 'informix'.project_payment_type_lu(project_payment_type_id,name,mergeable,pacts_payment_type_id) VALUES ("
                        + i + ", 'Contest Payment', 'f', 1);");
        StressHelper.executeSql(
                "INSERT INTO 'informix'.resource(resource_id, resource_role_id, project_id, project_phase_id ,"
                        + "create_user,create_date,modify_user,modify_date) VALUES ("
                        + i + ", 2, 100001, 111, 'admin', CURRENT, 'admin', CURRENT);");
        StressHelper.executeSql("INSERT INTO 'informix'.submission (submission_id, upload_id, submission_status_id,"
                + " screening_score, initial_score, final_score, placement, submission_type_id, create_user,"
                + " create_date, modify_user, modify_date) VALUES("
                + i + ", 1, 1, 100, 100, 100, 1, 1, 'admin', CURRENT, 'admin', CURRENT);");
    }

    /**
     * <p>
     * Gets the configuration object used for tests.
     * </p>
     *
     * @param namespace
     *            the namespace.
     *
     * @param cofigFile
     *            the path of configuration file.
     *
     * @return the configuration object.
     *
     * @throws Exception
     *             to JUnit.
     */
    static ConfigurationObject getConfigObject(String namespace, String cofigFile) throws Exception {
        XMLFilePersistence persistence = new XMLFilePersistence();

        // Get configuration
        ConfigurationObject obj = persistence.loadFile(namespace, new File(cofigFile));

        return obj.getChild(namespace);
    }

    /**
     * Executes the given sql.
     *
     * @param sql
     *            the sql which will be executed.
     * @throws Exception
     *             to JUnit.
     */
    static void executeSql(String sql) throws Exception {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    /**
     * <p>
     * Creates JDBC database Connection.
     * </p>
     *
     * @return database connection
     *
     * @throws Exception
     *             if any error occurs
     */
    public static Connection getConnection() throws Exception {
        Properties prop = loadPropertiesFromFile(DB_PROPERTIES);

        String url = prop.getProperty("databaseURL");
        String user = prop.getProperty("username");
        String password = prop.getProperty("password");
        String driverClassName = prop.getProperty("driverClassName");

        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            // ignore exception
        }

        return DriverManager.getConnection(url, user, password);
    }

    /**
     * <p>
     * Clears the database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    static void clearDB() throws Exception {
        try (Connection connection = getConnection()) {
            executeSQL(connection, "test_files/stress/clear.sql");
        }
    }

    /**
     * Gets the first index of record in DB.
     *
     * @param sql
     *            the sql
     * @return the first index of record in DB
     * @throws Exception
     *             to JUnit.
     */
    static int getFirstIndex(String sql) throws Exception {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    /**
     * Close the connection.
     *
     * @param connection
     *            the connection to DB.
     */
    private static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // OK
            }
        }
    }

    /**
     * Close the statement.
     *
     * @param statement
     *            the statement
     */
    private static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // OK
            }
        }
    }

    /**
     * <p>
     * Executes the SQL statements in the file. Lines that are empty or starts with '#' will be ignore.
     * </p>
     *
     * @param connection
     *            the connection.
     * @param file
     *            the file.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void executeSQL(Connection connection, String file) throws Exception {
        try (Statement stmt = connection.createStatement()) {

            String[] values = readFile(file).split(";");

            for (String value : values) {
                String sql = value.trim();
                if ((sql.length() != 0) && (!sql.startsWith("#"))) {
                    stmt.executeUpdate(sql);
                }
            }
        }
    }

    /**
     * <p>
     * Reads the content of a given file.
     * </p>
     *
     * @param fileName
     *            the name of the file to read.
     *
     * @return a string represents the content.
     *
     * @throws IOException
     *             if any error occurs during reading.
     */
    private static String readFile(String fileName) throws IOException {

        try (Reader reader = new FileReader(fileName)) {
            // Create a StringBuilder instance
            StringBuilder sb = new StringBuilder();

            // Buffer for reading
            char[] buffer = new char[1024];

            // Number of read chars
            int k = 0;

            // Read characters and append to string builder
            while ((k = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, k);
            }

            // Return read content
            return sb.toString();
        }
        // Ignore
    }

    /**
     * <p>
     * Loads properties from specified file.
     * </p>
     *
     * @param fileName
     *            path to properties file
     *
     * @return loaded from file properties
     *
     * @throws java.io.IOException
     *             i any error occurs
     */
    private static Properties loadPropertiesFromFile(String fileName) throws IOException {

        try (FileInputStream input = new FileInputStream(fileName)) {

            Properties props = new Properties();
            props.load(input);

            return props;
        }
    }
}
