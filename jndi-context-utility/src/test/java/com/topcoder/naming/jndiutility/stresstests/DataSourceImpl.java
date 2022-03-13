/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.naming.jndiutility.stresstests;

import java.io.PrintWriter;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import javax.sql.DataSource;


/**
 * A datasource object. NOTE: This is a mock object an provides no DataSource funtionality.
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class DataSourceImpl implements Referenceable, DataSource {
    /** The name of the DataSource */
    private String name;

    /**
     * Constructor
     *
     * @param name a name
     */
    public DataSourceImpl(String name) {
        this.name = name;
    }

    /**
     * Return a String describing this DataSource
     *
     * @return a String describing this DataSource
     */
    public String toString() {
        return name;
    }

    /**
     * Return a Reference for this object
     *
     * @return a Reference for this object
     *
     * @throws NamingException if a NamingException occurs
     */
    public Reference getReference() throws NamingException {
        return new Reference(DataSource.class.getName(), new StringRefAddr("datasource", name),
            TestObjectFactory.class.getName(), null);
    }

    // Start of MOCK methods
    public Connection getConnection() {
        return new Connection() {
            @Override
            public <T> T unwrap(Class<T> iface) throws SQLException {
                return null;
            }

            @Override
            public boolean isWrapperFor(Class<?> iface) throws SQLException {
                return false;
            }

            public void clearWarnings() {
                }

                public void close() {
                }

                public void commit() {
                }

                public Statement createStatement() {
                    return null;
                }

                public Statement createStatement(int arg1, int arg2) {
                    return null;
                }

                public Statement createStatement(int arg1, int arg2, int arg3) {
                    return null;
                }

                public boolean getAutoCommit() {
                    return false;
                }

                public String getCatalog() {
                    return "Catalog";
                }

                public int getHoldability() {
                    return 0;
                }

                public DatabaseMetaData getMetaData() {
                    return null;
                }

                public int getTransactionIsolation() {
                    return 1;
                }

                public Map getTypeMap() {
                    return null;
                }

                public SQLWarning getWarnings() {
                    return null;
                }

                public boolean isClosed() {
                    return false;
                }

                public boolean isReadOnly() {
                    return false;
                }

                public String nativeSQL(String arg) {
                    return null;
                }

                public CallableStatement prepareCall(String SQl) {
                    return null;
                }

                public CallableStatement prepareCall(String SQl, int arg1, int arg2) {
                    return null;
                }

                public CallableStatement prepareCall(String SQl, int arg1, int arg2, int arg3) {
                    return null;
                }

                public PreparedStatement prepareStatement(String sql) {
                    return null;
                }

                public PreparedStatement prepareStatement(String sql, int arg1) {
                    return null;
                }

                public PreparedStatement prepareStatement(String sql, int[] arg1) {
                    return null;
                }

                public PreparedStatement prepareStatement(String sql, int arg1, int arg2) {
                    return null;
                }

                public PreparedStatement prepareStatement(String sql, int arg1, int arg2, int arg3) {
                    return null;
                }

                public PreparedStatement prepareStatement(String sql, String[] arg1) {
                    return null;
                }

            @Override
            public Clob createClob() throws SQLException {
                return null;
            }

            @Override
            public Blob createBlob() throws SQLException {
                return null;
            }

            @Override
            public NClob createNClob() throws SQLException {
                return null;
            }

            @Override
            public SQLXML createSQLXML() throws SQLException {
                return null;
            }

            @Override
            public boolean isValid(int timeout) throws SQLException {
                return false;
            }

            @Override
            public void setClientInfo(String name, String value) throws SQLClientInfoException {

            }

            @Override
            public void setClientInfo(Properties properties) throws SQLClientInfoException {

            }

            @Override
            public String getClientInfo(String name) throws SQLException {
                return null;
            }

            @Override
            public Properties getClientInfo() throws SQLException {
                return null;
            }

            @Override
            public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
                return null;
            }

            @Override
            public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
                return null;
            }

            @Override
            public void setSchema(String schema) throws SQLException {

            }

            @Override
            public String getSchema() throws SQLException {
                return null;
            }

            @Override
            public void abort(Executor executor) throws SQLException {

            }

            @Override
            public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

            }

            @Override
            public int getNetworkTimeout() throws SQLException {
                return 0;
            }

            public void releaseSavepoint(Savepoint savepoint) {
                }

                public void rollback() {
                }

                public void rollback(Savepoint s) {
                }

                public void setAutoCommit(boolean arg) {
                }

                public void setCatalog(String arg) {
                }

                public void setHoldability(int arg) {
                }

                public void setReadOnly(boolean arg) {
                }

                public void setTransactionIsolation(int level) {
                }

                public void setTypeMap(Map map) {
                }

                public Savepoint setSavepoint() {
                    return null;
                }

                public Savepoint setSavepoint(String arg) {
                    return null;
                }
            };
    }

    /**
     * Get a Connection
     *
     * @param name a name
     * @param password a password
     *
     * @return a Connection
     */
    public Connection getConnection(String name, String password) {
        return null;
    }

    /**
     * Get the login timeout
     *
     * @return the login timeout
     */
    public int getLoginTimeout() {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    /**
     * Get the log writer
     *
     * @return a PrintWriter
     */
    public PrintWriter getLogWriter() {
        return null;
    }

    /**
     * Set th login timeout
     *
     * @param seconds the timeout
     */
    public void setLoginTimeout(int seconds) {
    }

    /**
     * Set the log writer
     *
     * @param p a PrintWriter
     */
    public void setLogWriter(PrintWriter p) {
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    // End of MOCK methods
}
