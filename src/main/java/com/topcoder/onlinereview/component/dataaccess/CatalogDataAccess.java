/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.dataaccess;

import com.topcoder.onlinereview.component.idgenerator.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeUpdateSql;
import static com.topcoder.onlinereview.component.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.component.util.CommonUtils.getString;

/**
 * <p>A simple DAO for component catalog backed up by Query Tool.</p>
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
@Component
public class CatalogDataAccess extends BaseDataAccess {

    /**
     * <p>A <code>Strng</code> providing the name of the table keeping the details for ID generation.</p>
     */
    @Value("${catalogDataAccess.idGeneratorTableName:sequence_object}")
    private String idGeneratorTableName;

    /**
     * <p>A <code>String</code> providing the name for column in ID generation table providing the names of ID
     * generation sequences.</p>
     */
    @Value("${catalogDataAccess.idGeneratorUserDefColumnName:name}")
    private String idGeneratorUserDefColumnName;

    /**
     * <p>A <code>String</code> providing the name for column in ID generation table providing the current highest
     * generated ID.</p>
     */
    @Value("${catalogDataAccess.idGeneratorHighValueColumnName:current_value}")
    private String idGeneratorHighValueColumnName;

    /**
     * <p>A <code>long</code> providing the maximum value for IDs to be generated by ID generator.</p>
     */
    @Value("${catalogDataAccess.idGeneratorMaxHighValue:9999999999}")
    private long idGeneratorMaxHighValue;

    /**
     * <p>A <code>int</code> providing the maximum value for lowest value for IDs to be generated by ID generator.</p>
     */
    @Value("${catalogDataAccess.idGeneratorMaxLowValue:1}")
    private int idGeneratorMaxLowValue;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether ID generator is to be auto-initialized or
     * not.</p>
     */
    @Value("${catalogDataAccess.idGeneratorAutoInit:false}")
    private boolean idGeneratorAutoInit;

    /**
     * <p>A <code>String</code> providing the data source for ID generator.</p>
     */
    @Autowired
    @Qualifier("tcsJdbcTemplate")
    private JdbcTemplate idGeneratorJdbcTemplate;

    /**
     * <p>Sets the name of the table keeping the details for ID generation.</p>
     *
     * @param idGeneratorTableName a <code>String</code> providing the name of the table keeping the details for ID
     *        generation.
     */
    public void setIdGeneratorTableName(String idGeneratorTableName) {
        this.idGeneratorTableName = idGeneratorTableName;
    }

    /**
     * <p>Sets the name for column in ID generation table providing the current highest generated ID.</p>
     *
     * @param idGeneratorHighValueColumnName a <code>String</code> providing the name for column in ID generation table
     *        providing the current highest generated ID.
     */
    public void setIdGeneratorHighValueColumnName(String idGeneratorHighValueColumnName) {
        this.idGeneratorHighValueColumnName = idGeneratorHighValueColumnName;
    }

    /**
     * <p>Sets the name for table in ID generation table providing the names of ID generation sequences.</p>
     *
     * @param idGeneratorUserDefColumnName a <code>String</code> providing the name for table in ID generation table
     *        providing the names of ID generation sequences.
     */
    public void setIdGeneratorUserDefColumnName(String idGeneratorUserDefColumnName) {
        this.idGeneratorUserDefColumnName = idGeneratorUserDefColumnName;
    }

    /**
     * <p>Sets the maximum value for lowest value for IDs to be generated by ID generator.</p>
     *
     * @param idGeneratorMaxLowValue a <code>int</code> providing the maximum value for lowest value for IDs to be
     *        generated by ID generator.
     */
    public void setIdGeneratorMaxLowValue(int idGeneratorMaxLowValue) {
        this.idGeneratorMaxLowValue = idGeneratorMaxLowValue;
    }

    /**
     * <p>Sets the maximum value for IDs to be generated by ID generator.</p>
     *
     * @param idGeneratorMaxHighValue a <code>long</code> providing the maximum value for IDs to be generated by ID
     *        generator.
     */
    public void setIdGeneratorMaxHighValue(long idGeneratorMaxHighValue) {
        this.idGeneratorMaxHighValue = idGeneratorMaxHighValue;
    }

    /**
     * <p>Sets the flag indicating whether ID generator is to be auto-initialized or not.</p>
     *
     * @param idGeneratorAutoInit a <code>boolean</code> providing the flag indicating whether ID generator is to be
     *                            auto-initialized or not.
     */
    public void setIdGeneratorAutoInit(boolean idGeneratorAutoInit) {
        this.idGeneratorAutoInit = idGeneratorAutoInit;
    }

    /**
     * <p>Gets the details for component version for specified component ID and version number.</p>
     *
     * <p>As of current version the returned {@link ComponentVersionInfo} object has only <code>versionId</code>
     * property set. All other properties are not initialized.</p>
     *
     * @param componentId a <code>long</code> providing the component ID.
     * @param versionNumber a <code>long</code> providing the component version number.
     * @return a <code>ComponentVersionInfo</code> providing the details for component version.
     */
    public ComponentVersionInfo getComponentVersionInfo(long componentId, long versionNumber) {
        final String queryName = "comp_version";
        String[] queryArgNames = new String[] {"cd", "vid"};
        String[] queryArgs = new String[] {String.valueOf(componentId), String.valueOf(versionNumber)};

        Map<String, List<Map<String, Object>>> results = runQuery(queryName, queryArgNames, queryArgs);
        List<Map<String, Object>> versionData = results.get(queryName);

        if (versionData.isEmpty()) {
            return null;
        } else {
            long versionId = getLong(versionData.get(0), "version_id");
            return new ComponentVersionInfo(versionId, 0, null, null, 0, new Date(), 0, false);
        }
    }

    /**
     * <p>Gets the documents for specified component version.</p>
     *
     * @param componentVersionId a <code>long</code> providing the component version ID.
     * @return a <code>Collection</code> listing the documents for specified component version. If there are no such
     *         documents then empty collection is returned.
     */
    public Collection<Document> getDocuments(long componentVersionId) {
        final String queryName = "comp_version_documents";
        Map<String, List<Map<String, Object>>> results = runQuery(queryName, "cv", String.valueOf(componentVersionId));
        List<Map<String, Object>> data = results.get(queryName);
        Collection<Document> documents = new ArrayList<Document>();
        for (int i = 0 ; i < data.size(); i++) {
            Document document = new Document(getLong(data.get(i), "document_id"),
                                             getString(data.get(i), "document_name"),
                                             getString(data.get(i), "url"),
                                             getLong(data.get(i), "document_type_id"));
            documents.add(document);
        }
        return documents;
    }

    /**
     * <p>Adds new document for specified component version.</p>
     *
     * @param componentVersionId a <code>long</code> providing the component version ID.
     * @param document a <code>Document</code> providing the details for new document.
     * @return a new <code>Document</code> instance providing the details for added document.
     * @throws DataAccessException if an unexpected error occurs.
     */
    public Document addDocument(long componentVersionId, Document document) {
        long documentId = generateNextCatalogScopedId();
        int rowsInserted = executeUpdateSql(tcsJdbcTemplate, "INSERT INTO comp_documentation (document_id, comp_vers_id, document_type_id, document_name, url) "
                + "VALUES (?, ?, ?, ?, ?)", newArrayList(documentId, componentVersionId, document.getType(), document.getName(), document.getURL()));
        if (rowsInserted != 1) {
            throw new DataAccessException("Failed to insert record for new document. Number of records inserted: "
                                          + rowsInserted);
        }
        return new Document(documentId, document.getName(), document.getURL(), document.getType());
    }

    /**
     * <p>Updates existing document for specified component version.</p>
     *
     * @param componentVersionId a <code>long</code> providing the component version ID.
     * @param document a <code>Document</code> providing the details for document.
     * @throws DataAccessException if an unexpected error occurs.
     */
    public void updateDocument(long componentVersionId, Document document) {
        int rowsUpdated = executeUpdateSql(tcsJdbcTemplate, "UPDATE comp_documentation SET document_type_id = ?, document_name = ?, url = ? "
                + " WHERE document_id = ? AND comp_vers_id = ?", newArrayList(document.getType(), document.getName(), document.getURL(), document.getId(), componentVersionId));
        if (rowsUpdated != 1) {
            throw new DataAccessException(
                "Failed to update record for existing document. Number of records updated: " + rowsUpdated);
        }
    }

    /**
     * <p>Generates new ID using ID Generator used by the <code>Component Catalog</code> system.</p>
     *
     * @return a <code>long</code> providing next generated unique ID.
     */
    protected long generateNextCatalogScopedId() {
        try {
            if (!IdGenerator.isInitialized()) {
                IdGenerator.init(idGeneratorJdbcTemplate, this.idGeneratorTableName,
                                 this.idGeneratorUserDefColumnName, this.idGeneratorHighValueColumnName,
                                 this.idGeneratorMaxHighValue, this.idGeneratorMaxLowValue, this.idGeneratorAutoInit);
            }
            return IdGenerator.nextId();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to generate next catalog scoped ID", e);
        }
    }
}
