/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.dataaccess;

import com.topcoder.onlinereview.component.grpcclient.dataaccess.DataAccessServiceRpc;
import com.topcoder.onlinereview.grpc.dataaccess.proto.DocumentProto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>A simple DAO for component catalog backed up by Query Tool.</p>
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
@Component
public class CatalogDataAccess {
    @Autowired
    DataAccessServiceRpc dataAccessServiceRpc;

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
        Long versionId = dataAccessServiceRpc.getComponentVersionInfo(componentId, versionNumber);

        if (versionId == null) {
            return null;
        } else {
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
        List<DocumentProto> docs = dataAccessServiceRpc.getDocuments(componentVersionId);
        Collection<Document> documents = new ArrayList<Document>();
        for (int i = 0 ; i < docs.size(); i++) {
            DocumentProto doc = docs.get(i);
            Document document = new Document(doc.getDocumentId(),
                                             doc.hasDocumentName() ? doc.getDocumentName() : null,
                                             doc.hasUrl() ? doc.getUrl() : null,
                                             doc.getDocumentTypeId());
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
        long documentId = dataAccessServiceRpc.addDocument(componentVersionId, document);
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
        int rowsUpdated = dataAccessServiceRpc.updateDocument(componentVersionId, document);
        if (rowsUpdated != 1) {
            throw new DataAccessException(
                "Failed to update record for existing document. Number of records updated: " + rowsUpdated);
        }
    }
}
