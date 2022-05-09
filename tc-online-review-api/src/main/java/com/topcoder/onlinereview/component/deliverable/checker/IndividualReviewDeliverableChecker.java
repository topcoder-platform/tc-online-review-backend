/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.onlinereview.component.deliverable.checker;

import com.topcoder.onlinereview.component.deliverable.Deliverable;
import com.topcoder.onlinereview.component.deliverable.DeliverableChecker;
import com.topcoder.onlinereview.component.deliverable.DeliverableCheckingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;

/**
 * <p>
 * The IndividualReviewDeliverableChecker class subclasses the SingleQuerySqlDeliverableChecker class. The SQL query
 * that it executes checks whether a given resource (in this case a reviewer) has completed a review for the
 * project and the only submission he/she is associated with. If so, it marks the deliverable as complete,
 * using the date the review was last changed.  In addition, the submission is is filled because such deliverable
 * isn't per submission.
 * </p>
 *
 * <p>
 * This class is immutable.
 * </p>
 *
 * @author aubergineanode
 * @author kr00tki
 * @version 1.0
 */
@Component
public class IndividualReviewDeliverableChecker implements DeliverableChecker {

    @Value("${deliverable.persistence.entity-manager-name}")
    private String entityManagerName;

    @Autowired
    @Qualifier("entityManagerMap")private Map<String, EntityManager> entityManagerMap;
    private EntityManager entityManager;

    @PostConstruct
    public void postRun() {
        entityManager = entityManagerMap.get(entityManagerName);
    }

    public void check(Deliverable deliverable) throws DeliverableCheckingException {
        if (deliverable == null) {
            throw new IllegalArgumentException("deliverable cannot be null.");
        }
        try {
            // execute it
            var rs =
                    executeSqlWithParam(
                            entityManager, getSqlQuery(), newArrayList(deliverable.getResource()));
            if (!rs.isEmpty()) {
                if (rs.get(0).get("modify_date") != null) {
                    deliverable.setCompletionDate(getDate(rs.get(0), "modify_date"));
                    deliverable.setSubmission(getLong(rs.get(0), "submission_id"));
                }
            }
        } catch (Exception ex) {
            throw new DeliverableCheckingException("Error occurs while database check operation.", ex);
        }
    }

    /**
     * <p>
     * Gets the SQL query string to select the last modification date for the review scorecard for the
     * resource(reviewer). Returned query will have 1 placeholder for the resource_id value.
     * </p>
     *
     * @return The SQL query string to execute.
     */
    protected String getSqlQuery() {
        return "SELECT review.modify_date, resource_submission.submission_id FROM resource_submission "
                + "LEFT JOIN review ON review.resource_id = resource_submission.resource_id "
                + "AND review.submission_id = resource_submission.submission_id AND committed = 1 "
                + "WHERE resource_submission.resource_id = ?";
    }
}
