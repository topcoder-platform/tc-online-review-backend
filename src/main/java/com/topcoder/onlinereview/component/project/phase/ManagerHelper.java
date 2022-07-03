/*
 * Copyright (C) 2009 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.phase;

import com.topcoder.onlinereview.component.deliverable.UploadManager;
import com.topcoder.onlinereview.component.external.UserRetrieval;
import com.topcoder.onlinereview.component.project.management.ProjectLinkManager;
import com.topcoder.onlinereview.component.project.management.ProjectManager;
import com.topcoder.onlinereview.component.project.payment.ProjectPaymentManager;
import com.topcoder.onlinereview.component.project.payment.calculator.ProjectPaymentCalculator;
import com.topcoder.onlinereview.component.resource.ResourceManager;
import com.topcoder.onlinereview.component.review.ReviewManager;
import com.topcoder.onlinereview.component.reviewfeedback.ReviewFeedbackManager;
import com.topcoder.onlinereview.component.review.scoreaggregator.ReviewScoreAggregator;
import com.topcoder.onlinereview.component.scorecard.ScorecardManager;
import com.topcoder.onlinereview.component.termsofuse.ProjectTermsOfUseDao;
import com.topcoder.onlinereview.component.termsofuse.UserTermsOfUseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * This is the helper class to load manager instances from a default configuration namespace. The namespace is
 * &quot;com.cronos.onlinereview.phases.ManagerHelper&quot;. For configuration properties in this namespace, see
 * the Component Specification, section &quot;3.2 Configuration Parameters&quot;. This class is used by
 * PhaseHandler implementations to retrieve manager instances.
 * </p>
 * <p>
 * Sample configuration file is given below:
 *
 * <pre>
 * &lt;Config name="com.cronos.onlinereview.phases.ManagerHelper"&gt;
 *  &lt;Property name="ProjectManager"&gt;
 *      &lt;Property name="ClassName"&gt;
 *          &lt;Value&gt;com.topcoder.management.project.ProjectManagerImpl&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="Namespace"&gt;
 *          &lt;Value&gt;com.topcoder.management.project.ProjectManagerImpl&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name="ProjectLinkManager"&gt;
 *      &lt;Property name="ClassName"&gt;
 *          &lt;Value&gt;com.topcoder.management.project.persistence.link.ProjectLinkManagerImpl&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="Namespace"&gt;
 *          &lt;Value&gt;com.topcoder.management.project.persistence.link.ProjectLinkManagerImpl&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name="PhaseManager"&gt;
 *      &lt;Property name="ClassName"&gt;
 *          &lt;Value&gt;com.topcoder.management.phase.DefaultPhaseManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="Namespace"&gt;
 *          &lt;Value&gt;com.topcoder.management.phase.DefaultPhaseManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name="ReviewManager"&gt;
 *      &lt;Property name="ClassName"&gt;
 *          &lt;Value&gt;com.topcoder.management.review.DefaultReviewManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="Namespace"&gt;
 *          &lt;Value&gt;com.topcoder.management.review.DefaultReviewManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name="ScorecardManager"&gt;
 *      &lt;Property name="ClassName"&gt;
 *          &lt;Value&gt;com.topcoder.management.scorecard.ScorecardManagerImpl&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="Namespace"&gt;
 *          &lt;Value&gt;com.topcoder.management.scorecard.ScorecardManagerImpl&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name="ConnectionFactoryNS"&gt;
 *      &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name="ConnectionName"&gt;
 *      &lt;Value&gt;informix_connection&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name="SearchBundleManagerNS"&gt;
 *      &lt;Value&gt;com.topcoder.search.builder.Upload_Resource_Search&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name="ResourceManager"&gt;
 *      &lt;Property name="ClassName"&gt;
 *          &lt;Value&gt;com.topcoder.management.resource.persistence.PersistenceResourceManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="ResourceSearchBundleName"&gt;
 *          &lt;Value&gt;ResourceSearchBundle&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="ResourceRoleSearchBundleName"&gt;
 *          &lt;Value&gt;ResourceRoleSearchBundle&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="NotificationSearchBundleName"&gt;
 *          &lt;Value&gt;NotificationSearchBundle&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="NotificationTypeSearchBundleName"&gt;
 *          &lt;Value&gt;NotificationTypeSearchBundle&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="ResourceIdGeneratorName"&gt;
 *          &lt;Value&gt;resource_id_seq&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="NotificationTypeIdGeneratorName"&gt;
 *          &lt;Value&gt;notification_type_id_seq&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="ResourceRoleIdGeneratorName"&gt;
 *          &lt;Value&gt;resource_role_id_seq&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="PersistenceClassName"&gt;
 *          &lt;Value&gt;com.topcoder.management.resource.persistence.sql.SqlResourcePersistence&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name="UploadManager"&gt;
 *      &lt;Property name="ClassName"&gt;
 *          &lt;Value&gt;com.topcoder.management.deliverable.PersistenceUploadManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="UploadSearchBundleName"&gt;
 *          &lt;Value&gt;UploadSearchBundle&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="SubmissionSearchBundleName"&gt;
 *          &lt;Value&gt;SubmissionSearchBundle&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="UploadIdGeneratorName"&gt;
 *          &lt;Value&gt;upload_id_seq&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="UploadTypeIdGeneratorName"&gt;
 *          &lt;Value&gt;upload_type_id_seq&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="UploadStatusIdGeneratorName"&gt;
 *          &lt;Value&gt;upload_status_id_seq&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="SubmissionIdGeneratorName"&gt;
 *          &lt;Value&gt;submission_id_seq&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="SubmissionStatusIdGeneratorName"&gt;
 *          &lt;Value&gt;submission_status_id_seq&lt;/Value&gt;
 *      &lt;/Property&gt;
 *       &lt;Property name="SubmissionStatusIdGeneratorName"&gt;
 *          &lt;Value&gt;submission_type_id_seq&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="PersistenceClassName"&gt;
 *          &lt;Value&gt;com.topcoder.management.deliverable.persistence.sql.SqlUploadPersistence&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name="UserProjectDataStore"&gt;
 *      &lt;Property name="UserRetrievalClassName"&gt;
 *          &lt;Value&gt;com.cronos.onlinereview.external.impl.DBUserRetrieval&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="UserRetrievalNamespace"&gt;
 *          &lt;Value&gt;com.cronos.onlinereview.external&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name="ScorecardAggregator"&gt;
 *      &lt;Property name="Namespace"&gt;
 *          &lt;Value&gt;com.topcoder.management.review.scoreaggregator.ReviewScoreAggregator&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;ProjectDetailsURL&quot;&gt;
 *      &lt;Value&gt;http://www.topcoder.com/tc?projectDetails=?&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;StudioProjectDetailsURL&quot;&gt;
 *      &lt;Value&gt;http://studio.topcoder.com/?projectDetails=?&lt;/Value&gt;
 *  &lt;/Property&gt;
 * &lt;/Config&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Change in version 1.4:
 * <ul>
 * <li>add submission type id generator used to create UploadManager.</li>
 * <li>use java generic type instead of the raw type.</li>
 * </ul>
 * </p>
 * <p>
 * Change in version 1.6:
 * <ul>
 * <li>add link property to studio contest for email reference.</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.6.1 changes note:
 * <ul>
 * <li>Removed screeningManager together with its getter.</li>
 * <li>Removed private initScreeningManager() method.</li>
 * </ul>
 * Version 1.6.2 changes note:
 * <ul>
 * <li>Add copilot specific notifications</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.7.14 (Module Assembly - Enhanced Review Feedback Integration) Change notes:
 *   <ol>
 *   </ol>
 * </p>
 *
 * @author tuenm, bose_java, waits, saarixx, myxgyy, FireIce, microsky, lmmortal, flexme
 * @version 1.8.5
 */
@Component
public class ManagerHelper {

    /**
     * Represents the ProjectManager instance. It is initialized in the constructor and never changed after that.
     * It is
     * never null.
     */
    @Autowired
    private ProjectManager projectManager;

    /**
     * Represents the ProjectLinkManager instance. It is initialized in the constructor and never changed after
     * that. It
     * is never null.
     * @since 1.3
     */
    @Autowired
    private ProjectLinkManager projectLinkManager;

    /**
     * Represents the PhaseManager instance. It is initialized in the constructor and never changed after that. It
     * is
     * never null.
     */
    @Autowired
    private PhaseManager phaseManager;

    /**
     * Represents the ScorecardManager instance. It is initialized in the constructor and never changed after that.
     * It
     * is never null.
     */
    @Autowired
    private ScorecardManager scorecardManager;

    /**
     * Represents the ReviewManager instance. It is initialized in the constructor and never changed after that. It
     * is
     * never null.
     */
    @Autowired
    private ReviewManager reviewManager;

    /**
     * Represents the ResourceManager instance. It is initialized in the constructor and never changed after that.
     * It is
     * never null.
     */
    @Autowired
    private ResourceManager resourceManager;

    /**
     * Represents the UploadManager instance. It is initialized in the constructor and never changed after that. It
     * is
     * never null.
     */
    @Autowired
    private UploadManager uploadManager;

    /**
     * Represents the UserRetrieval instance. It is initialized in the constructor and never changed after that. It
     * is
     * never null.
     */
    @Autowired
    private UserRetrieval userRetrieval;

    /**
     * Represents the ReviewScoreAggregator instance. It is initialized in the constructor and never changed after
     * that.
     * It is never null.
     */
    @Autowired
    private ReviewScoreAggregator scorecardAggregator;

    /**
     * This constant stores Online Review's project details page URL.
     * @since 1.3
     */
    @Value("${managerHelper.ProjectDetailsURL}")
    private String projectDetailsBaseURL;


    /**
     * This constant stores the challenge page URL for the new topcoder site.
     */
    @Value("${managerHelper.ChallengePageURL}")
    private String challengePageBaseURL;

    /**
     * This constant stores studio project details page URL.
     * @since 1.6
     */
    @Value("${managerHelper.StudioProjectDetailsURL}")
    private String studioProjectDetailsBaseURL;

    /**
     * This constant stores URL for contest details page in direct.
     * @since 1.7.6
     */
    @Value("${managerHelper.DirectContestURL}")
    private String directContestBaseURL;

    /**
     * This constant stores URL for copilot posting contest details page in direct.
     * @since 1.6.2
     */
    @Value("${managerHelper.CopilotDirectContestURL}")
    private String copilotDirectContestBaseURL;

    /**
     * <p>A <code>String</code> providing the base URL for the page with final fixes for contest in Direct
     * application.</p>
     * 
     * @since 1.7.15
     */
    @Value("${managerHelper.DirectContestFinalFixURL}")
    private String directContestFinalFixBaseURL;

    /**
     * <p>A <code>String</code> providing the base URL for the page with final fixes for contest in Studio
     * application.</p>
     * @since 1.7.15
     */
    @Value("${managerHelper.StudioProjectFinalFixURL}")
    private String studioProjectFinalFixBaseURL;

    /**
     * This constant stores URL for project page in direct.
     * @since 1.7.6
     */
    @Value("${managerHelper.DirectProjectURL}")
    private String directProjectBaseURL;

    @Value("${managerHelper.V5ChallengeURL:#{null}}")
    private String v5ChallengeURL;

    /**
     * <p>A <code>ReviewFeedbackManager</code> providing the interface to review feedback management system.</p>
     * @since 1.7.6
     */
    @Autowired
    private ReviewFeedbackManager reviewFeedbackManager;


    /**
     * <p>A <code>UserTermsOfUseDao</code> providing the access to user terms of use persistence.</p>
     * 
     * @since 1.6.3
     */
    @Autowired
    private UserTermsOfUseDao userTermsOfUseDao;

    /**
     * <p>A <code>ProjectTermsOfUseDao</code> providing the access to project terms of use persistence.</p>
     * 
     * @since 1.6.3
     */
    @Autowired
    private ProjectTermsOfUseDao projectTermsOfUseDao;

    @Autowired
    private ProjectPaymentManager projectPaymentManager;
    @Autowired
    @Qualifier("defaultProjectPaymentCalculator")
    private ProjectPaymentCalculator defaultProjectPaymentCalculator;
    @Autowired
    @Qualifier("projectPaymentAdjustmentCalculator")
    private ProjectPaymentCalculator projectPaymentAdjustmentCalculator;

    /**
     * Gets the non-null ProjectManager instance.
     * @return The non-null ProjectManager instance.
     */
    public ProjectManager getProjectManager() {
        return projectManager;
    }

    /**
     * Gets the non-null ProjectLinkManager instance.
     * @return The non-null ProjectLinkManager instance.
     * @since 1.3
     */
    public ProjectLinkManager getProjectLinkManager() {
        return projectLinkManager;
    }

    /**
     * Gets the non-null PhaseManager instance.
     * @return The non-null PhaseManager instance.
     */
    public PhaseManager getPhaseManager() {
        return phaseManager;
    }

    /**
     * Gets the non-null ScorecardManager instance.
     * @return The non-null ScorecardManager instance.
     */
    public ScorecardManager getScorecardManager() {
        return scorecardManager;
    }

    /**
     * Gets the non-null ReviewManager instance.
     * @return The non-null ReviewManager instance.
     */
    public ReviewManager getReviewManager() {
        return reviewManager;
    }

    /**
     * Gets the non-null ResourceManager instance.
     * @return The non-null ResourceManager instance.
     */
    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    /**
     * Gets the non-null UploadManager instance.
     * @return The non-null UploadManager instance.
     */
    public UploadManager getUploadManager() {
        return uploadManager;
    }

    /**
     * Gets the non-null UserRetrieval instance.
     * @return The non-null UserRetrieval instance.
     */
    public UserRetrieval getUserRetrieval() {
        return userRetrieval;
    }

    /**
     * Gets the non-null ReviewScoreAggregator instance.
     * @return The non-null ReviewScoreAggregator instance.
     */
    public ReviewScoreAggregator getScorecardAggregator() {
        return scorecardAggregator;
    }

    /**
     * Gets the non-null ReviewFeedbackManager instance.
     * @return The non-null ReviewFeedbackManager instance.
     */
    public ReviewFeedbackManager getReviewFeedbackManager() {
        return reviewFeedbackManager;
    }

    /**
     * <p>
     * Gets the project details base url.
     * </p>
     * @return the project details base url.
     * @since 1.3
     */
    public String getProjectDetailsBaseURL() {
        return projectDetailsBaseURL;
    }

    /**
     * <p>
     * Gets the challenge page base url.
     * </p>
     */
    public String getChallengePageBaseURL() {
        return challengePageBaseURL;
    }

    /**
     * <p>
     * Gets the studio project details base url.
     * </p>
     * @return the studio project details base url.
     * @since 1.6
     */
    public String getStudioProjectDetailsBaseURL() {
        return studioProjectDetailsBaseURL;
    }

    /**
     * <p>
     * Gets the base URL for contest details in direct.
     * </p>
     * @return the base url for contest details in direct.
     * @since 1.7.6
     */
    public String getDirectContestBaseURL() {
        return directContestBaseURL;
    }

    /**
     * <p>
     * Gets the base URL for copilot posting contest details in direct.
     * </p>
     * @return the base URL for copilot posting contest details in direct.
     * @since 1.6.2
     */
    public String getCopilotDirectContestBaseURL() {
        return copilotDirectContestBaseURL;
    }

    /**
     * <p>
     * Gets the base URL for project details in direct.
     * </p>
     * @return the base url for project details in direct.
     * @since 1.7.6
     */
    public String getDirectProjectBaseURL() {
        return directProjectBaseURL;
    }


    /**
     * <p>Gets the access to user terms of use persistence.</p>
     *
     * @return a <code>UserTermsOfUseDao</code> providing the access to user terms of use persistence.
     * @since 1.6.3
     */
    public UserTermsOfUseDao getUserTermsOfUseDao() {
        return this.userTermsOfUseDao;
    }

    /**
     * <p>Gets the access to project terms of use persistence.</p>
     *
     * @return a <code>ProjectTermsOfUseDao</code> providing the access to project terms of use persistence.
     * @since 1.6.3
     */
    public ProjectTermsOfUseDao getProjectTermsOfUseDao() {
        return this.projectTermsOfUseDao;
    }

    /**
     * <p>Gets the base URL for the page with final fixes for contest in Direct application.</p>
     *
     * @return a <code>String</code> providing the base URL for the page with final fixes for contest in Direct
     *         application.
     * @since 1.7.15
     */
    public String getDirectContestFinalFixBaseURL() {
        return this.directContestFinalFixBaseURL;
    }

    /**
     * <p>Sets the base URL for the page with final fixes for contest in Direct application.</p>
     *
     * @param directContestFinalFixBaseURL a <code>String</code> providing the base URL for the page with final fixes
     * for contest in Direct application.
     * @since 1.7.15
     */
    public void setDirectContestFinalFixBaseURL(String directContestFinalFixBaseURL) {
        this.directContestFinalFixBaseURL = directContestFinalFixBaseURL;
    }

    /**
     * <p>Gets the base URL for the page with final fixes for contest in Studio application.</p>
     *
     * @return a <code>String</code> providing the base URL for the page with final fixes for contest in Studio
     *         application.
     * @since 1.7.15
     */
    public String getStudioProjectFinalFixBaseURL() {
        return this.studioProjectFinalFixBaseURL;
    }

    public ProjectPaymentManager getProjectPaymentManager() {
        return projectPaymentManager;
    }

    public ProjectPaymentCalculator getDefaultProjectPaymentCalculator() {
        return defaultProjectPaymentCalculator;
    }

    public ProjectPaymentCalculator getProjectPaymentAdjustmentCalculator() {
        return projectPaymentAdjustmentCalculator;
    }

    public String getV5ChallengeURL() {
        return v5ChallengeURL;
    }
}
