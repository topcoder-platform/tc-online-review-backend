/*
 * Copyright (C) 2012, 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.reviewfeedback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.topcoder.onlinereview.component.grpcclient.reviewfeedback.ReviewFeedbackServiceRpc;
import com.topcoder.onlinereview.grpc.reviewfeedback.proto.ReviewFeedbackDetailProto;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * This is a JDBC based implementation of ReviewFeedbackManager. It provides CRUD operations for managing the
 * ReviewFeedback entities in persistence and performs them using SQL queries executed with aid of JDBC. This class uses
 * TC configuration components for configuration and owns a DBConnectionFactory used for obtaining DB connections. Also
 * it performs logging via TC Logging Wrapper component as per CS 1.3.1.
 * </p>
 *
 * <p>
 * <strong>Sample Config:</strong>
 *
 * <pre>
 * &lt;?xml version="1.0"?&gt;
 * &lt;CMConfig&gt;
 *   &lt;Config name="com.topcoder.management.reviewfeedback"&gt;
 *     &lt;Property name="logName"&gt;
 *       &lt;value&gt;com.topcoder.management.reviewfeedback&lt;/value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name="dbConnectionName"&gt;
 *       &lt;value&gt;TCSCatalog&lt;/value&gt;
 *     &lt;/Property&gt;
 *
 *     &lt;Property name="dbConnectionFactoryConfiguration"&gt;
 *       &lt;!-- Configuration for DBConnectionFactoryImpl should be placed here.
 *               It must have "TCSCatalog" connection configured. --&gt;
 *         &lt;Property name="com.topcoder.db.connectionfactory.DBConnectionFactoryImpl"&gt;
 *             &lt;Property name="connections"&gt;
 *                 &lt;Property name="default"&gt;
 *                     &lt;Value&gt;TCSCatalog&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="TCSCatalog"&gt;
 *                     &lt;Property name="producer"&gt;
 *                         &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *                     &lt;/Property&gt;
 *                     &lt;Property name="parameters"&gt;
 *                         &lt;Property name="jdbc_driver"&gt;
 *                             &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                         &lt;/Property&gt;
 *                         &lt;Property name="jdbc_url"&gt;
 *                             &lt;Value&gt;
 *                                 jdbc:informix-sqli://localhost:1526/test:informixserver=ol_topcoder&lt;/Value&gt;
 *                         &lt;/Property&gt;
 *                         &lt;Property name="user"&gt;
 *                             &lt;Value&gt;informix&lt;/Value&gt;
 *                         &lt;/Property&gt;
 *                         &lt;Property name="password"&gt;
 *                             &lt;Value&gt;123456&lt;/Value&gt;
 *                         &lt;/Property&gt;
 *                     &lt;/Property&gt;
 *                 &lt;/Property&gt;
 *             &lt;/Property&gt;
 *         &lt;/Property&gt;
 *     &lt;/Property&gt;
 *
 *   &lt;/Config&gt;
 *
 * &lt;/CMConfig&gt;
 *
 * </pre>
 *
 * </p>
 *
 * <p>
 *
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * // Create sample input.
 * ReviewFeedback entity = new ReviewFeedback();
 * entity.setProjectId(1);
 * entity.setComment(&quot;comment text&quot;);
 * ReviewFeedbackDetail detail = new ReviewFeedbackDetail();
 * detail.setReviewerUserId(126);
 * detail.setScore(2);
 * detail.setFeedbackText(&quot;feedback text&quot;);
 * List&lt;ReviewFeedbackDetail&gt; details = new ArrayList&lt;ReviewFeedbackDetail&gt;();
 * details.add(detail);
 * entity.setDetails(details);
 *
 * // Create DAO.
 * ReviewFeedbackManager dao = new JDBCReviewFeedbackManager();
 *
 * // Perform CRUD operations
 * // Create.
 * entity = dao.create(entity, &quot;operator&quot;);
 * System.out.println(&quot;entity.getId() = &quot; + entity.getId());
 * System.out.println(&quot;entity.getCreateUser() = &quot; + entity.getCreateUser());
 * // Update.
 * entity.getDetails().get(0).setScore(1);
 * dao.update(entity, &quot;anotherOperator&quot;);
 * // Get.
 * long id = entity.getId();
 * entity = null;
 * entity = dao.get(id);
 * System.out.println(&quot;entity.getScore() = &quot; + entity.getDetails().get(0).getScore());
 * System.out.println(&quot;entity.getModifyUser() = &quot; + entity.getModifyUser());
 *
 * // Retrieves entities with given project ID from persistence.
 * List&lt;ReviewFeedback&gt; list = dao.getForProject(1);
 * System.out.println(list.size() + &quot; ReviewFeedback entities return.&quot;);
 *
 * // Delete.
 * dao.delete(entity.getId());
 * entity = dao.get(entity.getId());
 * System.out.println(&quot;'entity == null' = &quot; + (entity == null));
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Changes in 2.0:</em>
 * <ol>
 * <li>Documentation is updated to reflect the data model changes.</li>
 * <li>New "operator:String" argument is added to create() and update() methods in order to support auditing, and return
 * value is added to update() method.</li>
 * </ol>
 * </p>
 *
 * <p>
 * <strong>Thread safety: </strong> This class is immutable and thread-safe with assumption that caller uses method
 * arguments thread safely.
 * </p>
 *
 * @author gevak, amazingpig, hesibo, sparemax
 * @version 2.0.1
 */
@Slf4j
@Component
public class ReviewFeedbackManager {

    @Autowired
    ReviewFeedbackServiceRpc reviewFeedbackServiceRpc;
    /**
     * It is a constant for default configuration namespace. It is used in configuration file based constructors when no
     * configuration namespace is specified by user. It is not null and not empty.
     */
    public static final String DEFAULT_CONFIGURATION_NAMESPACE = "com.topcoder.management.reviewfeedback";
    /**
     * The default log date format.
     */
    private static final String DEFAULT_LOGDATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * The maximum length of the user id.
     */
    private static final int USER_ID_MAX_LEN = 10;

    /**
     * <p>
     * Represents the entrance message.
     * </p>
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>the date time.</li>
     * <li>the method signature.</li>
     * <li>the input log.</li>
     * </ol>
     * </p>
     */
    private static final String MESSAGE_ENTRANCE = "%1$s INFO The method [%2$s] began. [%3$s]";

    /**
     * <p>
     * Represents the exit message.
     * </p>
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>the date time.</li>
     * <li>the method signature.</li>
     * <li>the output log.</li>
     * </ol>
     * </p>
     */
    private static final String MESSAGE_EXIT = "%1$s INFO The method [%2$s] ended. [%3$s]";

    /**
     * <p>
     * Represents the error message.
     * </p>
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>the date time.</li>
     * <li>the method signature.</li>
     * <li>the error message.</li>
     * </ol>
     * </p>
     */
    private static final String MESSAGE_ERROR = "%1$s ERROR Error in method [%2$s], details: %3$s";

    @Value("${createAuditActionTypeId:1}")
    private long createAuditActionTypeId;
    /**
     * Audit action type ID for "update" action, used for auditing (in create and update operations). It is initialized
     * from constructor and never changed afterwards. Can be any value. Default value is 3 (will be set in constructor
     * if no configuration value provided).
     *
     * @since 2.0
     */
    @Value("${updateAuditActionTypeId:3}")
    private long updateAuditActionTypeId;
    /**
     * Audit action type ID for "delete" action, used for auditing (in create and update operations). It is initialized
     * from constructor and never changed afterwards. Can be any value. Default value is 2 (will be set in constructor
     * if no configuration value provided).
     *
     * @since 2.0
     */
    @Value("${deleteAuditActionTypeId:2}")
    private long deleteAuditActionTypeId;

    /**
     * <p>
     * Creates given entity (along with its details records) in persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Implementation and documentation are updated according to the new data model and new audit requirements.</li>
     * <li>New "operator:String" argument is added in order to support auditing.</li>
     * <li>Transaction handling is added.</li>
     * </ol>
     * </p>
     *
     * @param entity
     *            Entity to be created in persistence. Its original Id property will be ignored and after successful
     *            execution, it will be populated with newly generated identity. Its original audit-related properties
     *            (CreateUser, CreateDate, ModifyUser, ModifyDate) will be ignored, they all will be populated by this
     *            method. It must be not null. Its Comment property but be not empty (but may be null). Its Details
     *            property must be not null (but may be empty) and each of its elements (if any) must conform to all of
     *            the following validation rules:
     *            <ol>
     *            <li>Must be not null.</li>
     *            <li>Its ReviewerUserId property must contain not more than 10 significant decimal digits and must be
     *            unique across all entity.Details elements.</li>
     *            <li>Its FeedbackText property must be not null and not empty.</li>
     *            </ol>
     * @param operator
     *            Specifies user who is performing this operation. Must be not null and not empty.
     *
     * @return Entity created in persistence (some of its properties will be populated as per method argument
     *         documentation). Not null.
     *
     * @throws IllegalArgumentException
     *             if any argument is invalid (as per argument description above).
     * @throws ReviewFeedbackManagementPersistenceException
     *             if any issue occurs with persistence.
     */
    @Transactional
    public ReviewFeedback create(ReviewFeedback entity, String operator)
        throws ReviewFeedbackManagementPersistenceException {
        final String signature = "JDBCReviewFeedbackManager.create";
        logEntrance(signature, new String[] {"entity", "operator"}, new Object[] {entity, operator});
        try {
            checkEntity(entity);
            checkNullIAE(operator, "operator");
            checkEmptyIAE(operator, "operator");

            Date createDate = new Date();

            // Create feedback record
            long entityId = reviewFeedbackServiceRpc.createReviewFeedback(entity, operator, createDate);
            entity.setId(entityId);

            // Create feedback detail records (if any)
            List<ReviewFeedbackDetail> details = entity.getDetails();
            createDetails(entityId, details);

            // Create audit records:
            auditReviewFeedback(entity, createAuditActionTypeId, operator, createDate);
            auditReviewFeedbackDetails(entityId, details, createAuditActionTypeId, operator, createDate);
            // Populate entity properties and return the populated entity:
            entity.setCreateUser(operator);
            entity.setCreateDate(createDate);
            entity.setModifyUser(operator);
            entity.setModifyDate(createDate);

            logExit(signature, new Object[] {entity});
            return entity;
        } catch (IllegalArgumentException e) {
            throw logException(signature, e);
        }
    }

    /**
     * <p>
     * Updates given entity (along with associations to details records) in persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Implementation and documentation are updated according to the new data model and new audit requirements.</li>
     * <li>New "operator:String" argument is added in order to support auditing.</li>
     * <li>Return value is added.</li>
     * <li>Transaction handling is added.</li>
     * </ol>
     * </p>
     *
     * @param entity
     *            Entity to be updated in persistence. Its Id property will be used to identify (find) entity in
     *            persistence. Its original audit-related properties (CreateUser, CreateDate, ModifyUser, ModifyDate)
     *            will be ignored, ModifyUser and ModifyDate will be populated by this method. It must be not null. Its
     *            Comment property but be not empty (but may be null). Its Details property must be not null (but may be
     *            empty) and each of its elements (if any) must conform to all of the following validation rules:
     *            <ol>
     *            <li>Must be not null.</li>
     *            <li>Its ReviewerUserId property must contain not more than 10 significant decimal digits and must be
     *            unique across all entity.Details elements.</li>
     *            <li>Its FeedbackText property must be not null and not empty.</li>
     *            </ol>
     * @param operator
     *            Specifies user who is performing this operation. Must be not null and not empty.
     *
     * @return Updated entity. Not null.
     *
     * @throws IllegalArgumentException
     *             if any argument is invalid (as per argument description above).
     * @throws ReviewFeedbackManagementEntityNotFoundException
     *             if review feedback entity with specified identity is not found in persistence.
     * @throws ReviewFeedbackManagementPersistenceException
     *             if any issue occurs with persistence.
     */
    @Transactional
    public ReviewFeedback update(ReviewFeedback entity, String operator)
        throws ReviewFeedbackManagementEntityNotFoundException, ReviewFeedbackManagementPersistenceException {
        final String signature = "JDBCReviewFeedbackManager.update";
        logEntrance(signature, new String[] {"entity", "operator"}, new Object[] {entity, operator});
        try {
            checkEntity(entity);
            checkNullIAE(operator, "operator");
            checkEmptyIAE(operator, "operator");
            long feedbackId = entity.getId();

            // Populate query parameters.
            Date modifyDate = new Date();

            // Update feedback record
            if (reviewFeedbackServiceRpc.updateReviewFeedback(entity, operator, modifyDate) == 0) {
                throw logException(signature, new ReviewFeedbackManagementEntityNotFoundException(
                    "The review feedback entity with specified identity is not found in persistence."));
            }

            List<ReviewFeedbackDetail> createdDetails = new ArrayList<ReviewFeedbackDetail>();
            List<ReviewFeedbackDetail> updatedDetails = new ArrayList<ReviewFeedbackDetail>();
            List<ReviewFeedbackDetail> deletedDetails = new ArrayList<ReviewFeedbackDetail>();

            updateDetails(feedbackId, entity.getDetails(), createdDetails, updatedDetails,
                deletedDetails);

            // Create audit records:
            auditReviewFeedback(entity, updateAuditActionTypeId, operator, modifyDate);
            auditReviewFeedbackDetails(feedbackId, createdDetails, createAuditActionTypeId, operator,
                    modifyDate);
            auditReviewFeedbackDetails(feedbackId, updatedDetails, updateAuditActionTypeId, operator,
                    modifyDate);
            auditReviewFeedbackDetails(feedbackId, deletedDetails, deleteAuditActionTypeId, operator,
                    modifyDate);
            // Update entity properties and return the updated entity:
            entity.setModifyUser(operator);
            entity.setModifyDate(modifyDate);

            logExit(signature, new Object[] {entity});
            return entity;
        } catch (IllegalArgumentException e) {
            throw logException(signature, e);
        }
    }

    /**
     * <p>
     * Retrieves entity with given ID from persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Implementation is updated according to the new data model.</li>
     * </ol>
     * </p>
     *
     * @param id
     *            ID of entity to retrieve.
     * @return Retrieved entity. Null if entity with specified ID is not found in persistence.
     * @throws ReviewFeedbackManagementPersistenceException
     *             If any issue occurs with persistence.
     */
    public ReviewFeedback get(long id) throws ReviewFeedbackManagementPersistenceException {
        final String signature = "JDBCReviewFeedbackManager.get";
        logEntrance(signature, new String[] {"id"}, new Object[] {id});
        ReviewFeedback reviewFeedback = reviewFeedbackServiceRpc.getReviewFeedback(id);
        if (reviewFeedback == null) {
            return reviewFeedback;
        }
        List<ReviewFeedbackDetail> details = reviewFeedbackServiceRpc.getReviewFeedbackDetails(id);
        reviewFeedback.setDetails(details);
        logExit(signature, new Object[] {reviewFeedback});
        return reviewFeedback;
    }

    /**
     * <p>
     * Deletes entity with given ID from persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Implementation is updated according to the new data model.</li>
     * <li>Transaction handling is added.</li>
     * </ol>
     * </p>
     *
     * @param id
     *            ID of entity to delete.
     * @return true if entity was found and deleted, false if entity was not found.
     * @throws ReviewFeedbackManagementPersistenceException
     *             If any issue occurs with persistence.
     */
    @Transactional
    public boolean delete(long id) throws ReviewFeedbackManagementPersistenceException {
        final String signature = "JDBCReviewFeedbackManager.delete";
        logEntrance(signature, new String[] {"id"}, new Object[] {id});

        // Delete feedback record
        boolean ret = (reviewFeedbackServiceRpc.deleteReviewFeedback(id) > 0);

        logExit(signature, new Object[] {ret});
        return ret;
    }

    /**
     * <p>
     * Retrieves entities with given project ID from persistence.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Implementation is updated according to the new data model.</li>
     * </ol>
     * </p>
     *
     * @param projectId
     *            the project ID
     *
     * @return a list of retrieved entities
     *
     * @throws ReviewFeedbackManagementPersistenceException
     *             If any issue occurs with persistence.
     */
    public List<ReviewFeedback> getForProject(long projectId) throws ReviewFeedbackManagementPersistenceException {
        final String signature = "JDBCReviewFeedbackManager.getForProject";
        logEntrance(signature, new String[] { "projectId" }, new Object[] { projectId });
        List<ReviewFeedback> reviewFeedbacks = reviewFeedbackServiceRpc.getReviewFeedbackByProjectId(projectId);

        // This map will store the retrieved review feedback entities;
        // key is ReviewFeedback.Id, value is corresponding ReviewFeedback entity
        Map<Long, ReviewFeedback> reviewFeedbacksMap = reviewFeedbacks.stream()
                .collect(Collectors.toMap(ReviewFeedback::getId, x -> x));
        List<ReviewFeedbackDetailProto> detailsP = reviewFeedbackServiceRpc
                .getReviewFeedbackDetailsByProjectId(projectId);

        for (ReviewFeedbackDetailProto detailP : detailsP) {
            ReviewFeedback entity = reviewFeedbacksMap.get(detailP.getReviewFeedbackId());
            if (entity != null) {
                ReviewFeedbackDetail detail = new ReviewFeedbackDetail();
                if (detailP.hasReviewerUserId()) {
                    detail.setReviewerUserId(detailP.getReviewerUserId());
                }
                if (detailP.hasScore()) {
                    detail.setScore(detailP.getScore());
                }
                if (detailP.hasFeedbackText()) {
                    detail.setFeedbackText(detailP.getFeedbackText());
                }
                entity.getDetails().add(detail);
            }
        }

        List<ReviewFeedback> result = new ArrayList<ReviewFeedback>(reviewFeedbacksMap.values());
        // Return constructed entity:
        logExit(signature, new Object[] {result});

        return result;
    }

    /**
     * <p>
     * Checks the validity of the entity.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Implementation and documentation are updated according to the new data model.</li>
     * </ol>
     * </p>
     *
     * @param entity
     *            the entity. It must be not null. Its Comment property but be not empty (but may be null). Its Details
     *            property must be not null (but may be empty) and each of its elements (if any) must conform to all of
     *            the following validation rules:
     *            <ol>
     *            <li>Must be not null.</li>
     *            <li>Its ReviewerUserId property must contain not more than 10 significant decimal digits and must be
     *            unique across all entity.Details elements.</li>
     *            <li>Its FeedbackText property must be not null and not empty.</li>
     *            </ol>
     *
     * @throws IllegalArgumentException
     *             if any argument is invalid (as per argument description above).
     */
    private static void checkEntity(ReviewFeedback entity) {
        checkNullIAE(entity, "entity");
        checkEmptyIAE(entity.getComment(), "entity.getComment");

        List<ReviewFeedbackDetail> details = entity.getDetails();
        checkNullIAE(details, "entity.getDetails()");

        Set<Long> reviewerUserIdSet = new HashSet<Long>();
        for (ReviewFeedbackDetail detail : details) {
            checkNullIAE(detail, "entity.getDetails()[i]");

            String feedbackText = detail.getFeedbackText();
            checkNullIAE(feedbackText, "entity.getDetails()[i].getFeedbackText()");
            checkEmptyIAE(feedbackText, "entity.getDetails()[i].getFeedbackText()");

            long reviewerUserId = detail.getReviewerUserId();
            if (String.valueOf(reviewerUserId).length() > USER_ID_MAX_LEN) {
                throw new IllegalArgumentException(
                    "entity.getDetails()[i].getReviewerUserId() property must contain not more than " + USER_ID_MAX_LEN
                        + " significant decimal digits");
            }
            if (reviewerUserIdSet.contains(reviewerUserId)) {
                throw new IllegalArgumentException("entity.getDetails()[i].getReviewerUserId() property"
                    + " must be unique across all entity.Details elements.");
            }
            reviewerUserIdSet.add(reviewerUserId);
        }
    }

    /**
     * Creates the review feedback detail.
     *
     * @param feedbackId
     *            the feedback id
     * @param details
     *            the details
     *
     * @since 2.0
     *
     */
    private void createDetails(long feedbackId, List<ReviewFeedbackDetail> details) {
        for (ReviewFeedbackDetail detail : details) {
            reviewFeedbackServiceRpc.createReviewFeedbackDetail(detail, feedbackId);
        }
    }

    /**
     * Updates the details.
     *
     * @param feedbackId
     *            the feedback ID
     * @param details
     *            the feedback details
     * @param createdDetails
     *            the created feedback details
     * @param updatedDetails
     *            the updated feedback details
     * @param deletedDetails
     *            the deleted feedback details
     *
     * @throws SQLException
     *             if any error occurs
     *
     * @since 2.0
     */
    private void updateDetails(long feedbackId,
        List<ReviewFeedbackDetail> details, List<ReviewFeedbackDetail> createdDetails,
        List<ReviewFeedbackDetail> updatedDetails, List<ReviewFeedbackDetail> deletedDetails) {

        // Retrieve reviewer user IDs
        Set<Long> oldReviewerUserIds = getReviewerIds(feedbackId);

        // Determine created and updated items.
        for (ReviewFeedbackDetail detail : details) {
            if (oldReviewerUserIds.remove(detail.getReviewerUserId())) {
                updatedDetails.add(detail);
            } else {
                createdDetails.add(detail);
            }
        }

        // Delete feedback detail
        if (!oldReviewerUserIds.isEmpty()) {
            List<Long> revieweIds = new ArrayList<Long>();
            revieweIds.addAll(oldReviewerUserIds);
            for (long reviewerUserId : oldReviewerUserIds) {
                ReviewFeedbackDetail detail = new ReviewFeedbackDetail();
                detail.setReviewerUserId(reviewerUserId);
                deletedDetails.add(detail);
            }
            reviewFeedbackServiceRpc.deleteReviewFeedbackDetail(feedbackId, revieweIds);
        }

        // Insert new items.
        createDetails(feedbackId, createdDetails);

        // Update items.
        for (ReviewFeedbackDetail detail : updatedDetails) {
            reviewFeedbackServiceRpc.updateReviewFeedbackDetail(detail, feedbackId);
        }
    }

    /**
     * Gets the reviewer user IDs.
     *
     * @param feedbackId
     *            the feedback ID
     *
     * @return the reviewer user IDs.
     *
     * @since 2.0
     */
    private Set<Long> getReviewerIds(long feedbackId) {
        List<Long> ids = reviewFeedbackServiceRpc.getReviewerIdsByFeedbackId(feedbackId);
        return new HashSet<Long>(ids);
    }

    /**
     * Creates/updates record in review_feedback_audit table.
     *
     * @param entity
     *            Created/updated ReviewFeedback entity. Must be not null.
     * @param auditActionTypeId
     *            Audit action type ID.
     * @param operator
     *            Specifies user who is performing this operation. Must be not null and not empty.
     * @param actionDate
     *            Date and time when this operation is performed. Must be not null.
     *
     * @since 2.0
     */
    private void auditReviewFeedback(ReviewFeedback entity,
        long auditActionTypeId, String operator, Date actionDate) {
        // Create new record:
        reviewFeedbackServiceRpc.auditReviewFeedback(entity, auditActionTypeId, operator, actionDate);
    }

    /**
     * Creates/updates records in review_feedback_detail_audit table.
     *
     * @param reviewFeedbackId
     *            ID of ReviewFeedback entity to which the given ReviewFeedbackDetail entities are associated.
     * @param details
     *            Created/updated/deleted ReviewFeedbackDetail entities. Must be not null and not contain null elements.
     *            Please note that in case of delete operation, the ReviewFeedbackDetail-s will have only the
     *            ReviewerUserId property populated.
     * @param auditActionTypeId
     *            Audit action type ID.
     * @param operator
     *            Specifies user who is performing this operation. Must be not null and not empty.
     * @param actionDate
     *            Date and time when this operation is performed. Must be not null.
     *
     * @throws SQLException
     *             if any issue occurs with persistence.
     *
     * @since 2.0
     */
    private void auditReviewFeedbackDetails(long reviewFeedbackId,
        List<ReviewFeedbackDetail> details, long auditActionTypeId, String operator, Date actionDate) {
        if (details.isEmpty()) {
            return;
        }
        for (ReviewFeedbackDetail detail : details) {
            reviewFeedbackServiceRpc.auditReviewFeedbackDetail(detail, reviewFeedbackId, auditActionTypeId, operator,
                    actionDate);
        }
    }

    /**
     * <p>
     * Checks whether the given String is empty.
     * </p>
     *
     * @param arg
     *            the String to check (can be null)
     * @param name
     *            the name of the String argument to check
     *
     * @throws IllegalArgumentException
     *             if the given string is empty
     */
    private static void checkEmptyIAE(String arg, String name) {
        if (arg != null && arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

    /**
     * <p>
     * Checks whether the given object is null.
     * </p>
     *
     * @param arg
     *            the object to check
     * @param name
     *            the name of the object argument to check
     *
     * @throws IllegalArgumentException
     *             if the given object is null
     */
    private static void checkNullIAE(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * <p>
     * Logs for entrance into public method and parameters at <code>DEBUG</code> level.
     * </p>
     *
     * <p>
     * <em>NOTE:</em> Logging is NOT performed if log is <code>null</code>.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Updated to use toString(Object) method.</li>
     * </ol>
     * </p>
     *
     * @param signature
     *            the signature of the method to log.
     * @param paramNames
     *            the names of parameters to log.
     * @param paramValues
     *            the values of parameters to log.
     */
    private void logEntrance(String signature, String[] paramNames, Object[] paramValues) {
        if (log != null) {
            DateFormat format = new SimpleDateFormat(DEFAULT_LOGDATE_FORMAT);

            StringBuilder sb = new StringBuilder();
            // Log parameters
            sb.append("Input {");
            for (int i = 0; i < paramNames.length; i++) {
                sb.append(paramNames[i]).append(":").append(toString(paramValues[i]));
            }
            sb.append("}");

            log.debug(String.format(MESSAGE_ENTRANCE, format.format(new Date()), signature, sb.toString()));
        }
    }

    /**
     * <p>
     * Logs for exit from public method and return value at <code>DEBUG</code> level.
     * </p>
     *
     * <p>
     * <em>NOTE:</em> Logging is NOT performed if log is <code>null</code>.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Updated to use toString(Object) method.</li>
     * </ol>
     * </p>
     *
     * @param signature
     *            the signature of the method to log.
     * @param value
     *            the return value to log.
     */
    private void logExit(String signature, Object[] value) {
        if (log != null) {
            DateFormat format = new SimpleDateFormat(DEFAULT_LOGDATE_FORMAT);

            // Log return value
            String output = "Output: " + toString(value[0]);

            log.debug(String.format(MESSAGE_EXIT, format.format(new Date()), signature, output));
        }
    }

    /**
     * <p>
     * Logs the given exception and message at <code>ERROR</code> level.
     * </p>
     *
     * <p>
     * <em>NOTE:</em> Logging is NOT performed if log is <code>null</code>.
     * </p>
     *
     * @param <T>
     *            the exception type.
     * @param signature
     *            the signature of the method to log.
     * @param e
     *            the exception to log.
     *
     * @return the passed in exception.
     */
    private <T extends Throwable> T logException(String signature, T e) {
        if (log != null) {
            // Log exception at ERROR level
            DateFormat format = new SimpleDateFormat(DEFAULT_LOGDATE_FORMAT);
            log.error(String.format(MESSAGE_ERROR, format.format(new Date()), signature, getStackTrace(e)));
        }

        return e;
    }

    /**
     * <p>
     * Returns the exception stack trace string.
     * </p>
     *
     * @param cause
     *            the exception to be recorded.
     *
     * @return the exception stack trace string.
     */
    private static String getStackTrace(Throwable cause) {
        OutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);

        // Print a new line
        ps.println();
        cause.printStackTrace(ps);

        return out.toString();
    }

    /**
     * <p>
     * Converts an object to a string.
     * </p>
     *
     * @param obj
     *            the object
     *
     * @return the converted string.
     *
     * @since 2.0
     */
    private static String toString(Object obj) {
        if (obj instanceof ReviewFeedbackDetail) {
            return toString((ReviewFeedbackDetail) obj);
        } else if (obj instanceof ReviewFeedback) {
            return toString((ReviewFeedback) obj);
        } else if (obj instanceof List<?>) {
            return toString((List<?>) obj);
        }

        return String.valueOf(obj);
    }

    /**
     * <p>
     * Converts a list of objects to a string.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Changed List&lt;ReviewFeedback&gt; to List&lt;?&gt;.</li>
     * </ol>
     * </p>
     *
     * @param list
     *            a list of objects.
     *
     * @return the converted string.
     */
    private static String toString(List<?> list) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Object element : list) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(toString(element));
        }
        sb.append("]");

        return sb.toString();
    }

    /**
     * <p>
     * Converts the ReviewFeedback object to a string.
     * </p>
     *
     * <p>
     * <em>Changes in 2.0:</em>
     * <ol>
     * <li>Updated according to the new data model.</li>
     * </ol>
     * </p>
     *
     * @param obj
     *            the ReviewFeedback object
     *
     * @return the converted string.
     */
    private static String toString(ReviewFeedback obj) {
        StringBuilder sb = new StringBuilder();

        sb.append("ReviewFeedback{id:").append(obj.getId()).append(", createUser:").append(obj.getCreateUser())
            .append(", createDate:").append(obj.getCreateDate()).append(", modifyUser:").append(obj.getModifyUser())
            .append(", modifyDate:").append(obj.getModifyDate()).append(", projectid:").append(obj.getProjectId())
            .append(", comment:").append(obj.getComment()).append(", details:")
            .append(toString((Object) obj.getDetails())).append("}");

        return sb.toString();
    }

    /**
     * <p>
     * Converts the ReviewFeedback object to a string.
     * </p>
     *
     * @param obj
     *            the ReviewFeedbackDetail object
     *
     * @return the converted string.
     *
     * @since 2.0
     */
    private static String toString(ReviewFeedbackDetail obj) {
        StringBuilder sb = new StringBuilder();

        sb.append("ReviewFeedbackDetail{reviewerUserId:").append(obj.getReviewerUserId()).append(", score:")
            .append(obj.getScore()).append(", feedbackText:").append(obj.getFeedbackText()).append("}");

        return sb.toString();
    }
}
