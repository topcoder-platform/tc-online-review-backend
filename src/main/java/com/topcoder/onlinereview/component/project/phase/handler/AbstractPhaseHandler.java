/*
 * Copyright (C) 2004-2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.phase.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.topcoder.onlinereview.component.document.DocumentGenerator;
import com.topcoder.onlinereview.component.document.Template;
import com.topcoder.onlinereview.component.document.TemplateDataFormatException;
import com.topcoder.onlinereview.component.document.TemplateFormatException;
import com.topcoder.onlinereview.component.document.fieldconfig.Condition;
import com.topcoder.onlinereview.component.document.fieldconfig.Field;
import com.topcoder.onlinereview.component.document.fieldconfig.Loop;
import com.topcoder.onlinereview.component.document.fieldconfig.Node;
import com.topcoder.onlinereview.component.document.fieldconfig.NodeList;
import com.topcoder.onlinereview.component.document.fieldconfig.TemplateFields;
import com.topcoder.onlinereview.component.document.templatesource.FileTemplateSource;
import com.topcoder.onlinereview.component.document.templatesource.TemplateSourceException;
import com.topcoder.onlinereview.component.email.EmailEngine;
import com.topcoder.onlinereview.component.email.SendingException;
import com.topcoder.onlinereview.component.email.TCSEmailMessage;
import com.topcoder.onlinereview.component.external.ExternalUser;
import com.topcoder.onlinereview.component.external.RetrievalException;
import com.topcoder.onlinereview.component.jwt.JWTTokenGenerator;
import com.topcoder.onlinereview.component.project.management.PersistenceException;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.phase.ManagerHelper;
import com.topcoder.onlinereview.component.project.phase.Phase;
import com.topcoder.onlinereview.component.project.phase.PhaseHandler;
import com.topcoder.onlinereview.component.project.phase.PhaseHandlingException;
import com.topcoder.onlinereview.component.project.phase.PhaseStatus;
import com.topcoder.onlinereview.component.resource.Resource;
import com.topcoder.onlinereview.component.resource.ResourceFilterBuilder;
import com.topcoder.onlinereview.component.resource.ResourceManager;
import com.topcoder.onlinereview.component.resource.ResourcePersistenceException;
import com.topcoder.onlinereview.component.reviewfeedback.ReviewFeedbackManager;
import com.topcoder.onlinereview.component.search.SearchBuilderException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * This abstract class is used as a base class for all phase handlers. This class contains logic in the constructor to
 * load configuration settings for a phase handler. Settings includes database connection, email template and email
 * related information.
 * </p>
 *
 * <p>
 * Sample configuration file (for CheckpointSubmissionPhaseHandler) is given below:
 * <pre>
 *     &lt;Config name="com.cronos.onlinereview.phases.AbstractPhaseHandler"&gt;
 *         &lt;Property name="DefaultStartPhaseEmail"&gt;
 *             &lt;Property name="EmailSubject"&gt;
 *                 &lt;Value&gt;%PHASE_TYPE% Start\\: %PROJECT_NAME%&lt;/Value&gt;
 *             &lt;/Property&gt;
 *             &lt;Property name="EmailFromAddress"&gt;
 *                 &lt;Value&gt;&notificationEmailFromAddress;&lt;/Value&gt;
 *             &lt;/Property&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name="DefaultEndPhaseEmail"&gt;
 *             &lt;Property name="EmailSubject"&gt;
 *                 &lt;Value&gt;%PHASE_TYPE% End\\: %PROJECT_NAME%&lt;/Value&gt;
 *             &lt;/Property&gt;
 *             &lt;Property name="EmailFromAddress"&gt;
 *                 &lt;Value&gt;&notificationEmailFromAddress;&lt;/Value&gt;
 *             &lt;/Property&gt;
 *         &lt;/Property&gt;
 *     &lt;/Config&gt;
 *     &lt;Config name="com.cronos.onlinereview.phases.CheckpointSubmissionPhaseHandler"&gt;
 *         &lt;Property name="ManagerHelperNamespace"&gt;
 *             &lt;Value&gt;com.cronos.onlinereview.phases.ManagerHelper&lt;/Value&gt;
 *         &lt;/Property&gt;
 *         &lt;Property name="Schemes"&gt;
 *             &lt;Property name="DefaultScheme"&gt;
 *                 &lt;Property name="Roles"&gt;
 *                     &lt;Value&gt;*&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="Priority"&gt;
 *                     &lt;Value&gt;0&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="StartPhaseEmail"&gt;
 *                     &lt;Property name="EmailTemplateName"&gt;
 *                         &lt;Value&gt;&phasesEmailTemplate;&lt;/Value&gt;
 *                     &lt;/Property&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="EndPhaseEmail"&gt;
 *                     &lt;Property name="EmailTemplateName"&gt;
 *                         &lt;Value&gt;&phasesEmailTemplate;&lt;/Value&gt;
 *                     &lt;/Property&gt;
 *                 &lt;/Property&gt;
 *             &lt;/Property&gt;
 *             &lt;Property name="ExtendedSWScheme"&gt;
 *                 &lt;Property name="Roles"&gt;
 *                     &lt;Value&gt;Manager&lt;/Value&gt;
 *                     &lt;Value&gt;Observer&lt;/Value&gt;
 *                     &lt;Value&gt;Copilot&lt;/Value&gt;
 *                     &lt;Value&gt;Client Manager&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="ProjectTypes"&gt;
 *                     &lt;Value&gt;1&lt;/Value&gt;
 *                     &lt;Value&gt;2&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="Priority"&gt;
 *                     &lt;Value&gt;2&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="StartPhaseEmail"&gt;
 *                     &lt;Property name="EmailTemplateName"&gt;
 *                         &lt;Value&gt;&managerNotificationEmailTemplatesBase;/checkpoint_submission/start.txt&lt;/Value&gt;
 *                     &lt;/Property&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name="EndPhaseEmail"&gt;
 *                     &lt;Property name="EmailTemplateName"&gt;
 *                         &lt;Value&gt;&managerNotificationEmailTemplatesBase;/checkpoint_submission/end.txt&lt;/Value&gt;
 *                     &lt;/Property&gt;
 *                 &lt;/Property&gt;
 *             &lt;/Property&gt;
 *         &lt;/Property&gt;
 *     &lt;/Config&gt;
 * </pre>
 * </p>
 *
 * <p>
 * Sample email template is given below:
 * <pre>
 * %PHASE_TIMESTAMP{Phase timestamp}%&lt;br/&gt;
 * Handle\: %USER_HANDLE{User handle}%&lt;br/&gt;
 * Contest\: &lt;a href\="%OR_LINK%"&gt;%PROJECT_NAME{Project name}%&lt;/a&gt;&lt;br/&gt;
 * Version\: %PROJECT_VERSION{Project version}%&lt;br/&gt;
 * This is the notification about %PHASE_OPERATION{The phase operation - start/end}% of
 *  the %PHASE_TYPE{Phase type}% phase.&lt;br/&gt;
 * </pre>
 * </p>
 *
 * <p>
 * Thread safety: This class is thread safe because it is immutable.
 * </p>
 *
 * <p>
 * Version 1.1 (Appeals Early Completion Release Assembly 1.0) Change notes:
 * <ol>
 * <li>
 * Changed timeline notification emails subject.
 * </li>
 * <li>
 * Added new fields to timeline notification emails.
 * </li>
 * <li>
 * Property projectDetailsBaseURL added.
 * </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 changes note:
 * <ul>
 * <li>
 * Added capability to support different email template for different role (e.g. Submitter, Reviewer, Manager, etc).
 * </li>
 * <li>
 * Supporting Document Generator 2.1, which has the Loop and Condition.
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6 changes note:
 * <ul>
 * <li>
 * Added link to studio contest for email template.
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.1 changes note:
 * <ul>
 * <li>
 * Email templates now depend on the project category type.
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.2 changes note:
 * <ul>
 * <li>
 * Added copilot posting specific notifications.
 * </li>
 * </ul>
 * </p>
 * @author tuenm, bose_java, pulky, argolite, waits, FireIce, microsky, lmmortal, VolodymyrK
 * @version 1.8.5
 */
public abstract class AbstractPhaseHandler implements PhaseHandler {

    /**
     * Represents the default namespace of this class. It is used in the constructor
     * to load configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.AbstractPhaseHandler";

    /** constant for "Project Name" project info. */
    private static final String PROJECT_NAME = "Project Name";

    /** constant for "Project Version" project info. */
    private static final String PROJECT_VERSION = "Project Version";

    /** constant for "Review Feedback Flag" project info. */
    private static final String REVIEW_FEEDBACK_FLAG = "Review Feedback Flag";

    /** constant for lookup value for notification type id. */
    private static final String NOTIFICATION_TYPE_TIMELINE_NOTIFICATION = "Timeline Notification";

    /** Property name constant for manager helper namespace. */
    private static final String PROP_MANAGER_HELPER_NAMESPACE = "ManagerHelperNamespace";

    /** Format for property name constant for "DefaultStartPhaseEmail" */
    private static final String PROP_DEFAULT_START_PHASE_EMAIL = "DefaultStartPhaseEmail";

    /** Format for property name constant for "DefaultEndPhaseEmail" */
    private static final String PROP_DEFAULT_END_PHASE_EMAIL = "DefaultEndPhaseEmail";

    /** Format for property name constant for "StartPhaseEmail" */
    private static final String PROP_START_PHASE_EMAIL = "StartPhaseEmail";

    /** Format for property name constant for "EndPhaseEmail" */
    private static final String PROP_END_PHASE_EMAIL = "EndPhaseEmail";

    /** Format for property name constant for "EmailTemplateName" */
    private static final String PROP_EMAIL_TEMPLATE_NAME = "EmailTemplateName";

    /** Format for property name constant for "EmailSubject" */
    private static final String PROP_EMAIL_SUBJECT = "EmailSubject";

    /** Format for property name constant for "EmailFromAddress" */
    private static final String PROP_EMAIL_FROM_ADDRESS = "EmailFromAddress";

    /** Format for property name constant for "SendEmail" */
    private static final String PROP_SEND_EMAIL = "SendEmail";

    /** Format for property name constant for "Schemes" */
    private static final String PROP_SCHEMES = "Schemes";

    /** Format for property name constant for "Roles" */
    private static final String PROP_ROLES = "Roles";

    /** Format for property name constant for "ProjectTypes" */
    private static final String PROP_PROJECT_TYPES = "ProjectTypes";

    /** Format for property name constant for "ProjectCategories" */
    private static final String PROP_PROJECT_CATEGORIES = "ProjectCategories";

    /** Format for property name constant for "Priority" */
    private static final String PROP_PRIORITY = "Priority";

    /** Format for property name constant for "ReviewFeedbackScheme" */
    private static final String PROP_REVIEW_FEEDBACK_SCHEME = "ReviewFeedbackScheme";

    /** format for the email timestamp. Will format as "Fri, Jul 28, 2006 01:34 PM EST". */
    private static final String EMAIL_TIMESTAMP_FORMAT = "EEE, MMM d, yyyy hh:mm a z";

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(20);

    /**
     * <p>
     * This field is used to retrieve manager instances to use in phase handlers. It is initialized in the constructor
     * and never change after that. It is never null.
     * </p>
     */
    private final ManagerHelper managerHelper;

    /**
     * <p>
     * The list of the configured email schemes.
     * </p>
     *
     * @since 1.6.1
     */
    private final List<EmailScheme> emailSchemes;

    /**
     * <p>
     * Email scheme for the review feedback emails.
     * </p>
     *
     * @since 1.7.6
     */
    private EmailScheme reviewFeedbackEmailScheme;

    /**
     * <p>
     * Contains default values for start email scheme.
     * </p>
     *
     * @since 1.8.1
     */
    private EmailOptions defaultStartEmailOption;

    /**
     * <p>
     * Contains default values for end email scheme.
     * </p>
     *
     * @since 1.8.1
     */
    private EmailOptions defaultEndEmailOption;

    /**
     * Contains emails to send. Since the perform is called before update phase status.
     * But email should be send after updated success, so collect email message before update
     * and send it after update
     */
    private Map<String, List<TCSEmailMessage>> emailToSend = new ConcurrentHashMap<>();

    /**
     * The log instance used by this handler.
     */
    private static final Logger log = LoggerFactory.getLogger(AbstractPhaseHandler.class.getName());

    /**
     * <p>
     * Creates a new instance of AbstractPhaseHandler using the given namespace for loading configuration settings.
     * </p>
     *
     * <p>
     * It initializes the DB connection factory, connection name, Manager Helper, start and end phase email variables
     * from configuration properties
     * </p>
     *
     * <p>
     * Update in version 1.2: Now each role can have its own email options. And there will be some default setting
     * for 'start'/'end' phases.
     * </p>
     *
     *
     * @throws IllegalArgumentException if the input is null or empty string.
     */
    protected AbstractPhaseHandler(ManagerHelper managerHelper,
                                   List<EmailScheme> emailSchemes,
                                   EmailScheme reviewFeedbackEmailScheme,
                                   EmailOptions defaultStartEmailOption,
                                   EmailOptions defaultEndEmailOption){
        // initialize ManagerHelper from given namespace if provided.
        this.managerHelper = managerHelper;
        this.defaultStartEmailOption = defaultStartEmailOption;
        this.defaultEndEmailOption =  defaultEndEmailOption;
        // load the 'Schemes' property
        this.emailSchemes = getEmailSchemes(emailSchemes, defaultStartEmailOption, defaultEndEmailOption);
        // load the Scheme for review feedback emails
        this.reviewFeedbackEmailScheme = getEmailScheme(reviewFeedbackEmailScheme, defaultStartEmailOption, defaultEndEmailOption);
    }

    private static List<EmailScheme> getEmailSchemes(List<EmailScheme> emailSchemes,
                                                     EmailOptions defaultStartEmailOption,
                                                     EmailOptions defaultEndEmailOption) {
        for(EmailScheme es: emailSchemes) {
            getEmailScheme(es, defaultStartEmailOption, defaultEndEmailOption);
        }
        return emailSchemes;
    }

    private static EmailScheme getEmailScheme(EmailScheme es,
                                              EmailOptions defaultStartEmailOption,
                                              EmailOptions defaultEndEmailOption) {
        if (es == null) {
            return null;
        }
        if (es.getStartEmailOptions() != null) {
            fillEmailOptions(es.getStartEmailOptions(), defaultStartEmailOption);
        }
        if (es.getEndEmailOptions() != null) {
            fillEmailOptions(es.getEndEmailOptions(), defaultEndEmailOption);
        }
        return es;
    }

    private static void fillEmailOptions(EmailOptions emailOptions, EmailOptions defaultEmailOption) {
        if (emailOptions.getTemplateName() == null) {
            emailOptions.setTemplateName(defaultEmailOption.getTemplateName());
        }
        if (emailOptions.getSubject() == null) {
            emailOptions.setSubject(defaultEmailOption.getSubject());
        }
        if (emailOptions.getFromAddress() == null) {
            emailOptions.setFromAddress(defaultEmailOption.getFromAddress());
        }
    }

    /**
     * <p>
     * This method is used by the subclass to get the ManagerHelper instance.
     * </p>
     *
     * @return the ManagerHelper instance.
     */
    protected ManagerHelper getManagerHelper() {
        return managerHelper;
    }

    /**
     * <p>
     * Send email to the external users that are registered to be notified on the phase change.
     * </p>
     *
     * <p>
     * Now each role can have its own email options. If not set for that role, using the default setting.
     * </p>
     *
     * @param phase The current phase. must not be null.
     * @param values The values map. This is a map from String into Object. The key is the template field name and the
     *        value is the template field value.
     *
     * @throws IllegalArgumentException if any argument is null or map contains empty/null key/value.
     * @throws PhaseHandlingException if there was a problem when sending email.
     * @since 1.2
     */
    public void sendPhaseEmails(Phase phase, Map<String, Object> values) throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkValuesMap(values);

        // Do not send any e-mails for phases whose duration is zero
        if (phase.getLength() <= 0) {
            return;
        }

        final Date scheduledStartDate = phase.getScheduledStartDate();
        final Date scheduledEndDate = phase.getScheduledEndDate();

        // Check phase's scheduled start & end dates to be sure
        if ((scheduledStartDate != null) && (scheduledEndDate != null)
                && !scheduledStartDate.before(scheduledEndDate)) {
            return;
        }

        // Determine whether phase is starting or ending...
        PhaseStatus status = phase.getPhaseStatus();

        if (PhasesHelper.isPhaseToStart(status)) {
            sendPhaseEmails(phase, values, true);
        } else if (PhasesHelper.isPhaseToEnd(status)) {
            sendPhaseEmails(phase, values, false);
        }
    }

    /**
     * <p>
     * Sends email at the start/end of a phase. If the phase is in "Scheduled" state, start phase email will be sent.
     * If the phase is in "Open" state, end phase email will be sent. If the phase is in any other state, this method
     * does nothing. Besides, this method will not send any email if it is not configured to do so.
     * </p>
     *
     * <p>
     * This method first retrieves all the ExternalUsers to whom the notification is to be sent as well as the project
     * information. It then instantiates document generator to create the email body content and sends out an email to
     * each user using the Email Engine component.
     * </p>
     *
     * <p>
     * This method will directly invoking the sendPhaseEmails(Phase, Map) method with empty map.
     * </p>
     *
     * <p>
     * Update in version 1.2: Now each role can have its own email options. If not set for that role, using the
     * default setting.
     * </p>
     *
     * @param phase phase instance.
     *
     * @throws IllegalArgumentException if the input is null or empty string.
     * @throws PhaseHandlingException if there was a problem when sending email.
     * @see AbstractPhaseHandler#sendPhaseEmails(Phase, Map)
     */
    protected void sendPhaseEmails(Phase phase) throws PhaseHandlingException {
        sendPhaseEmails(phase, new HashMap<String, Object>());
    }

    /**
     * Sends email at the start/end of a phase. This method first retrieves all the ExternalUsers to whom the
     * notification is to be sent as well as the project information.
     *
     * <p>
     * Update in version 1.2: Now each role can have its own email options. If not set for that role, using the
     * default setting.
     * </p>
     *
     * @param phase phase instance.
     * @param values the values map to look up the fields in template
     * @param bStart true if phase is to start, false if phase is to end.
     *
     * @throws PhaseHandlingException if there was an error retrieving information or sending email.
     */
    private void sendPhaseEmails(Phase phase, Map<String, Object> values, boolean bStart) throws PhaseHandlingException {
        long projectId = phase.getProject().getId();
        Project project;

        // maps user IDs to EmailScheme
        Map<Long, EmailScheme> userEmailSchemes = new HashMap<Long, EmailScheme>();
        try {
            // Lookup notification type id for "Timeline Notification"
            ResourceManager rm = managerHelper.getResourceManager();
            long notificationTypeId = LookupHelper.getNotificationType(rm, NOTIFICATION_TYPE_TIMELINE_NOTIFICATION).getId();

            Resource[] resources = rm.searchResources(ResourceFilterBuilder.createProjectIdFilter(projectId));

            // retrieve project information
            project = managerHelper.getProjectManager().getProject(projectId);

            // retrieve users to be notified
            Long[] externalIds = rm.getNotifications(projectId, notificationTypeId);

            for (Resource resource : resources) {
                long externalId = resource.getUserId();
                if (!contains(externalIds, externalId)) {
                    continue;
                }

                EmailScheme emailScheme = getEmailSchemeForResource(resource, project.getProjectCategory());
                if (emailScheme == null) {
                    continue;
                }

                // since one user could have more than one role in project, we only need to set out
                // one email for all the roles of the same user,
                // we need to find the email scheme with largest priority here
                EmailScheme oldScheme = userEmailSchemes.get(externalId);
                if (oldScheme == null || oldScheme.getPriority() < emailScheme.getPriority()) {
                    userEmailSchemes.put(externalId, emailScheme);
                }
            }
        } catch (ResourcePersistenceException ex) {
            throw new PhaseHandlingException("There was a problem with resource retrieval", ex);
        } catch (PersistenceException ex) {
            throw new PhaseHandlingException("There was a problem with project retrieval", ex);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("There was a problem with project retrieval", e);
        } catch (Exception e) {
            throw new PhaseHandlingException("Problem with retrieving information.", e);
        }

        Map<Long, Map<String, String>> challengeIdCache = new HashMap<>();
        emailToSend.remove(String.join(":", String.valueOf(phase.getId()), phase.getPhaseStatus().getName()));
        for (long userID : userEmailSchemes.keySet()) {
            EmailScheme emailScheme = userEmailSchemes.get(userID);
            EmailOptions emailOptions = bStart ? emailScheme.getStartEmailOptions() : emailScheme.getEndEmailOptions();
            if (emailOptions != null && emailOptions.isSend()) {
                try {
                    sendEmail(userID, emailOptions, project, phase, values, bStart, challengeIdCache);
                } catch(PhaseHandlingException e) {
                    // If any error happens when sending email, log the error and let the logic continue.
                    log.error("Error when sending email.", e);
                }
            }
        }
    }

    /**
     * Sends email notifying the needed resources that they need to leave a feedback about the review performance.
     *
     * @param phase phase instance.
     * @param values the values map to look up the fields in template
     *
     * @throws PhaseHandlingException if there was an error retrieving information or sending email.
     */
    protected void sendReviewFeedbackEmails(Phase phase, Map<String, Object> values)
            throws PhaseHandlingException {
        PhasesHelper.checkNull(phase, "phase");
        PhasesHelper.checkValuesMap(values);
        if (reviewFeedbackEmailScheme == null) {
            return;
        }

        // Determine whether phase is starting or ending
        PhaseStatus status = phase.getPhaseStatus();

        boolean bStart;
        if (PhasesHelper.isPhaseToStart(status)) {
            bStart = true;
        } else if (PhasesHelper.isPhaseToEnd(status)) {
            bStart = false;
        } else {
            return;
        }

        EmailOptions emailOptions = bStart ? reviewFeedbackEmailScheme.getStartEmailOptions() :
                reviewFeedbackEmailScheme.getEndEmailOptions();
        if (emailOptions == null || !emailOptions.isSend()) {
            return;
        }

        try {
            long projectId = phase.getProject().getId();
            Project project = managerHelper.getProjectManager().getProject(projectId);

            boolean containsProjectType = reviewFeedbackEmailScheme.getProjectTypes().contains("*") ||
                    reviewFeedbackEmailScheme.getProjectTypes().contains(""+project.getProjectCategory().getProjectType().getId());
            boolean containsProjectCategory = reviewFeedbackEmailScheme.getProjectCategories().contains("*") ||
                    reviewFeedbackEmailScheme.getProjectCategories().contains(""+project.getProjectCategory().getId());
            if (!containsProjectType || !containsProjectCategory) {
                return;
            }

            // Check that this project is eligible for the review feedback.
            String reviewFeedbackFlag = (String) project.getProperty(REVIEW_FEEDBACK_FLAG);
            if (reviewFeedbackFlag == null || !reviewFeedbackFlag.equalsIgnoreCase("true")) {
                return;
            }

            // Check if there already are some review feedback, in which case no need to send the email.
            ReviewFeedbackManager reviewFeedbackManager = managerHelper.getReviewFeedbackManager();
            if (reviewFeedbackManager.getForProject(projectId).size() > 0) {
                return;
            }

            ResourceManager rm = managerHelper.getResourceManager();
            Resource[] resources = rm.searchResources(ResourceFilterBuilder.createProjectIdFilter(projectId));

            // Collect the IDs of the users who need to receive the review feedback email.
            // A user may have more than one role so we need to make sure not to send multiple emails to them.
            Set<Long> userIDs = new HashSet<Long>();
            for (Resource resource : resources) {
                boolean containsRole = reviewFeedbackEmailScheme.getRoles().contains("*") ||
                        reviewFeedbackEmailScheme.getRoles().contains(resource.getResourceRole().getName());

                if (containsRole) {
                    long externalId = resource.getUserId();

                    // Exclude the "dummy" users such as "Components", "Applications" and "LCSUPPORT"
                    if (externalId != Constants.USER_ID_COMPONENTS &&
                        externalId != Constants.USER_ID_APPLICATIONS &&
                        externalId != Constants.USER_ID_LCSUPPORT) {
                        userIDs.add(externalId);
                    }
                }
            }
            Map<Long, Map<String, String>> challengeIdCache = new HashMap<>();
            for (Long userID : userIDs) {
                try {
                    sendEmail(userID, emailOptions, project, phase, values, bStart, challengeIdCache);
                } catch(PhaseHandlingException e) {
                    // If any error happens when sending email, log the error and let the logic continue.
                    log.error("Error when sending email.", e);
                }
            }
        } catch (ResourcePersistenceException ex) {
            throw new PhaseHandlingException("There was a problem with resource retrieval", ex);
        } catch (PersistenceException ex) {
            throw new PhaseHandlingException("There was a problem with project retrieval", ex);
        } catch (SearchBuilderException e) {
            throw new PhaseHandlingException("There was a problem with project retrieval", e);
        } catch (Exception e) {
            throw new PhaseHandlingException("Problem with retrieving information.", e);
        }
    }

    /**
     * Sends email to the specified user using the specified EmailOptions. It instantiates document generator to
     * create the email body content and sends out an email to the user using the Email Engine component.
     *
     * @param userID user ID.
     * @param emailOptions options to be used for sending the email.
     * @param project project instance.
     * @param phase phase instance.
     * @param values the values map to look up the fields in template
     * @param bStart true if phase is to start, false if phase is to end.
     *
     * @throws PhaseHandlingException if there was an error retrieving information or sending email.
     */
    private void sendEmail(long userID, EmailOptions emailOptions, Project project,
                           Phase phase, Map<String, Object> values, boolean bStart,
                           Map<Long, Map<String, String>> challengeIdCache) throws PhaseHandlingException {
        try {
            // instantiate document generator instance
            DocumentGenerator docGenerator = new DocumentGenerator();
            docGenerator.setDefaultTemplateSource(new FileTemplateSource());

            Template bodyTemplate = docGenerator.getTemplate(emailOptions.getTemplateName());
            Template subjectTemplate = docGenerator.parseTemplate(emailOptions.getSubject());
            ExternalUser user = managerHelper.getUserRetrieval().retrieveUser(userID);

            // set all field values
            TemplateFields bodyRoot = setTemplateFieldValues(docGenerator.getFields(bodyTemplate), user, project,
                    phase, values, bStart, challengeIdCache);
            TemplateFields subjectRoot = setTemplateFieldValues(docGenerator.getFields(subjectTemplate), user, project,
                    phase, values, bStart, challengeIdCache);

            final TCSEmailMessage message = new TCSEmailMessage();
            message.setSubject(docGenerator.applyTemplate(subjectRoot));
            message.setBody(docGenerator.applyTemplate(bodyRoot));
            message.setFromAddress(emailOptions.getFromAddress());
            message.setToAddress(user.getEmail(), TCSEmailMessage.TO);
            message.setContentType("text/html");
            String emailKey = String.join(":", String.valueOf(phase.getId()), phase.getPhaseStatus().getName());
            emailToSend.putIfAbsent(emailKey, new ArrayList<>());
            emailToSend.get(emailKey).add(message);
        } catch (TemplateSourceException e) {
            throw new PhaseHandlingException("Problem with template source", e);
        } catch (TemplateFormatException e) {
            throw new PhaseHandlingException("Problem with template format", e);
        } catch (RetrievalException e) {
            throw new PhaseHandlingException("Fail to retrieve the user info when sending email.", e);
        } catch (TemplateDataFormatException e) {
            throw new PhaseHandlingException("Problem with template data format", e);
        } catch (Throwable e) {
            throw new PhaseHandlingException("Problem with sending email", e);
        }
    }

    /**
    * Finds email scheme for passed resource.
    *
    * @param resource
    *       the resource to get email scheme for
    * @param projectCategory
    *       the project category
    * @return the email scheme for resource. null if not found.
    * @since 1.6.1.
    */
    private EmailScheme getEmailSchemeForResource(Resource resource, ProjectCategory projectCategory) {
        EmailScheme priorityEmailScheme = null;
        for (EmailScheme emailScheme : emailSchemes) {
            boolean containsProjectType = emailScheme.getProjectTypes().contains("*") ||
                emailScheme.getProjectTypes().contains(""+projectCategory.getProjectType().getId());
            boolean containsProjectCategory = emailScheme.getProjectCategories().contains("*") ||
                emailScheme.getProjectCategories().contains(""+projectCategory.getId());
            boolean containsRole = emailScheme.getRoles().contains("*") ||
                emailScheme.getRoles().contains(resource.getResourceRole().getName());

            if (containsProjectType && containsProjectCategory && containsRole) {
                if (priorityEmailScheme == null || priorityEmailScheme.getPriority() < emailScheme.getPriority()) {
                    priorityEmailScheme = emailScheme;
                }
            }
        }

        return priorityEmailScheme;
    }

    /**
     * This method sets the values of the template fields with user, project information and lookup values
     * based on bStart variable which is true if phase is to start, false if phase is to end.
     *
     * @param root template fields.
     * @param user the user to be notified.
     * @param project project instance.
     * @param phase phase instance.
     * @param values the values map to look up
     * @param bStart true if phase is to start, false if phase is to end.
     *
     * @return template fields with data set.
     * @throws PhaseHandlingException if the values element for the loop is invalid
     */
    private TemplateFields setTemplateFieldValues(TemplateFields root, ExternalUser user, Project project, Phase phase,
                                                  Map<String, Object> values, boolean bStart,
                                                  Map<Long, Map<String, String>> challengeIdCache) throws PhaseHandlingException {
        setNodes(root.getNodes(), user, project, phase, values, bStart, challengeIdCache);

        return root;
    }
    /**
     * This method sets the values of the nodes with user, project information and lookup values
     * based on bStart variable which is true if phase is to start, false if phase is to end.
     *
     * @param nodes the nodes in template
     * @param user the user to be notified.
     * @param project project instance.
     * @param phase phase instance.
     * @param values the values map to look up
     * @param bStart true if phase is to start, false if phase is to end.
     *
     * @throws PhaseHandlingException if the values element for the loop is invalid
     */
    private void setNodes(Node[] nodes, ExternalUser user, Project project,
                          Phase phase, Map<String, Object> values, boolean bStart,
                          Map<Long, Map<String, String>> challengeIdCache) throws PhaseHandlingException {
        for (Node node : nodes) {
            if (node instanceof Field) {
                setField((Field) node, user, project, phase, values, bStart, challengeIdCache);
            } else if (node instanceof Loop) {
                setLoopItems((Loop) node, user, project, phase, values, bStart, challengeIdCache);
            } else if (node instanceof Condition) {
                Condition condition = ((Condition) node);

                if (values.containsKey(condition.getName())) {
                    condition.setValue(values.get(condition.getName()).toString());
                }

                setNodes(condition.getSubNodes().getNodes(), user, project, phase, values, bStart, challengeIdCache);
            }
        }
    }
    /**
     * This method sets the values of the Loop with user, project information and lookup values
     * based on bStart variable which is true if phase is to start, false if phase is to end.
     *
     * @param loop the Loop in template
     * @param user the user to be notified.
     * @param project project instance.
     * @param phase phase instance.
     * @param values the values map to look up
     * @param bStart true if phase is to start, false if phase is to end.
     * @throws PhaseHandlingException if the values element for the loop is invalid
     */
    @SuppressWarnings("unchecked")
    private void setLoopItems(Loop loop, ExternalUser user, Project project,
        Phase phase, Map<String, Object> values, boolean bStart,
                              Map<Long, Map<String, String>> challengeIdCache) throws PhaseHandlingException {
        try {
            List<?> loopItems = (List<?>) values.get(loop.getLoopElement());
            if (loopItems == null) {
                throw new PhaseHandlingException("For loop :" + loop.getLoopElement()
                                                  + ", the value in look up maps should not be null.");
            }
            for (int t = 0; t < loopItems.size(); t++) {
                NodeList item = loop.insertLoopItem(t);
                setNodes(item.getNodes(), user, project, phase, (Map<String, Object>) loopItems.get(t), bStart, challengeIdCache);
            }
        } catch (ClassCastException cce) {
            throw new PhaseHandlingException("For loop :" + loop.getLoopElement()
                    + ", the value in look up maps should be of List<Map<String, Object>> type.");
        }
    }

    /**
     * This method sets the values of the Field with user, project information and lookup values
     * based on bStart variable which is true if phase is to start, false if phase is to end.
     *
     * <p>
     * Changes in version 1.6: a new field called STUDIO_LINK is added, which can be referenced in email template.
     * </p>
     *
     * @param field the Field in template
     * @param user the user to be notified.
     * @param project project instance.
     * @param phase phase instance.
     * @param values the values map to look up
     * @param bStart true if phase is to start, false if phase is to end.
     */
    private void setField(Field field, ExternalUser user, Project project,
        Phase phase, Map<String, Object> values, boolean bStart, Map<Long, Map<String, String>> challengeIdCache) {
        if ("PHASE_TIMESTAMP".equals(field.getName())) {
            field.setValue(formatDate(new Date()));
        } else if ("USER_FIRST_NAME".equals(field.getName())) {
            field.setValue(user.getFirstName());
        } else if ("USER_LAST_NAME".equals(field.getName())) {
            field.setValue(user.getLastName());
        } else if ("USER_HANDLE".equals(field.getName())) {
            field.setValue(user.getHandle());
        } else if ("PROJECT_NAME".equals(field.getName())) {
            field.setValue((String) project.getProperty(PROJECT_NAME));
        } else if ("PROJECT_VERSION".equals(field.getName())) {
            field.setValue((String) project.getProperty(PROJECT_VERSION));
        } else if ("PROJECT_CATEGORY".equals(field.getName())) {
            field.setValue(project.getProjectCategory().getName());
        } else if ("PHASE_OPERATION".equals(field.getName())) {
            field.setValue(bStart ? "start" : "end");
        } else if ("PHASE_TYPE".equals(field.getName())) {
            field.setValue(getPhaseName(phase));
        } else if ("OR_LINK".equals(field.getName())) {
            field.setValue(managerHelper.getProjectDetailsBaseURL() + project.getId());
        } else if ("CHALLENGE_LINK".equals(field.getName())) {
            field.setValue(managerHelper.getChallengePageBaseURL() + project.getId());
        } else if ("PEER_REVIEW".equals(field.getName())) {
            Object reviewType = project.getProperty("Review Type");
            field.setValue(String.valueOf(reviewType != null && Constants.REVIEW_TYPE_PEER.equals(reviewType.toString())? 1 : 0));
        } else if ("STUDIO_LINK".equals(field.getName())) {
            field.setValue(managerHelper.getStudioProjectDetailsBaseURL() + project.getId());
        } else if ("STUDIO_CONTEST_FINAL_FIX_LINK".equals(field.getName())) {
            field.setValue(managerHelper.getStudioProjectFinalFixBaseURL() + project.getId());
        } else if ("DIRECT_CONTEST_FINAL_FIX_LINK".equals(field.getName())) {
            field.setValue(managerHelper.getDirectContestFinalFixBaseURL() + project.getId());
        } else if ("DIRECT_CONTEST_LINK".equals(field.getName())) {
            if (project.getProjectCategory().getName().equals(Constants.PROJECT_CATEGORY_COPILOT_POSTING)) {
                field.setValue(managerHelper.getCopilotDirectContestBaseURL() + project.getId());
            } else {
                field.setValue(managerHelper.getDirectContestBaseURL() + project.getId());
            }
        } else if ("DIRECT_PROJECT_LINK".equals(field.getName())) {
            field.setValue(managerHelper.getDirectProjectBaseURL() + project.getTcDirectProjectId());
        } else if ("OR_PROJECT_ID".equals(field.getName())) {
            field.setValue(""+ project.getId());
        } else if ("TC_DIRECT_PROJECT_NAME".equals(field.getName())) {
            field.setValue(""+project.getTcDirectProjectName());
        } else if ("TC_DIRECT_PROJECT_ID".equals(field.getName())) {
            field.setValue(""+project.getTcDirectProjectId());
        } else if ("IS_WINNER".equals(field.getName())) {
            long userId = user.getId();
            String projectWinnerId = (String) project.getProperty("Winner External Reference ID");
            field.setValue(projectWinnerId.equals(String.valueOf(userId)) ? "1" : "0");
        } else if ("CHALLENGE_ID".equals(field.getName())) {
            field.setValue(getV5Id(project.getId(), challengeIdCache, "challengeId"));
        } else if ("PROJECT_ID_V5".equals(field.getName())) {
            field.setValue(getV5Id(project.getId(), challengeIdCache, "projectId"));
        } else if (values.containsKey(field.getName())) {
            if (values.get(field.getName()) != null) {
                field.setValue(values.get(field.getName()).toString());
            }
        }
    }

    /**
     * This method returns phase name, which consists of the phase type name plus phase index if there are multiple
     * phases of the same type.
     *
     * @param phase phase instance.
     * @return Phase name.
     */
    private String getPhaseName(Phase phase) {
        String phaseType = phase.getPhaseType().getName();
        int index = 1;

        Phase allPhases[] = phase.getProject().getAllPhases();
        for (Phase otherPhase : allPhases) {
            if (otherPhase.getPhaseType().getName().equals(phaseType) &&
                otherPhase.getScheduledStartDate().before(phase.getScheduledStartDate())) {
                index++;
            }
        }

        return phaseType + (index>1 ? " #"+index : "");
    }

    /**
     * <p>
     * Creates the Empty EmailOptions with Not Send email.
     * </p>
     * @return EmailOptions instance
     */
    private static EmailOptions createNotSendEmailOptions() {
        EmailOptions options = new EmailOptions();
        options.setSend(Boolean.FALSE);

        return options;
    }

    /**
     * Returns the date formatted as "Fri, Jul 28, 2006 01:34 PM EST" for example.
     *
     * @param dt date to be formatted.
     *
     * @return formatted date string.
     */
    private static String formatDate(Date dt) {
        SimpleDateFormat formatter = new SimpleDateFormat(EMAIL_TIMESTAMP_FORMAT);
        return formatter.format(dt);
    }

    /**
     * Checks if the id exists in the id array.
     *
     * @param ids the id array
     * @param id the id to check
     *
     * @return true if exists
     */
    private static boolean contains(Long[] ids, long id) {
        for (long id1 : ids) {
            if (id1 == id) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get challenge id.
     *
     * @param legacyId
     * @return
     */
    private String getV5Id(Long legacyId, Map<Long, Map<String, String>> challengeIdCache, String idName) {
        if (challengeIdCache.containsKey(legacyId)) {
            return challengeIdCache.get(legacyId).get(idName);
        }
        String challengeUrl = managerHelper.getV5ChallengeURL();
        if (challengeUrl == null) {
            log.error("'managerHelper.V5ChallengeURL' doesn't config");
            return "";
        }
        GetMethod getMethod = new GetMethod(challengeUrl + legacyId);
        getMethod.addRequestHeader("accept", "application/json");
        try {
            setM2AuthToken(getMethod);
            int statusCode = new HttpClient().executeMethod(getMethod);
            if (statusCode == 200) {
                List<Map<String, Object>> res = new ObjectMapper().readValue(getMethod.getResponseBodyAsString(), new TypeReference<List<Map<String, Object>>>(){});
                if (res.size() > 0) {
                    Map<String, String> map = new HashMap<>();
                    map.put("challengeId", res.get(0).get("id").toString());
                    map.put("projectId", res.get(0).get("projectId").toString());
                    challengeIdCache.put(legacyId, map);
                    return map.get(idName);
                }
            } else {
                log.error("Get challenge Id error with statusCode: " + statusCode);
            }
        } catch (Exception e) {
            log.error("parse challenge error: ", e);
        }
        return "";
    }

    /**
     * Set token header
     *
     * @param getMethod request method
     */
    public void setM2AuthToken(GetMethod getMethod) throws Exception {
        if (managerHelper.getAuthClientId() != null) {
            String token = JWTTokenGenerator.getInstance(managerHelper.getAuthClientId(),
                    managerHelper.getAuthClientSecret(),
                    managerHelper.getAuthAudience(),
                    managerHelper.getAuthDomain(),
                    Integer.parseInt(managerHelper.getAuthExpirationTime()),
                    managerHelper.getAuthProxyURL()).getMachineToken();
            getMethod.addRequestHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        }
    }

    /**
     * Send or delete phase related emails.
     *
     * @param phaseId the phase id
     * @param phaseStatus the phase status
     * @param send send or delete
     */
    public void sendEmailAfterUpdatePhase(Long phaseId, String phaseStatus, boolean send) {
        List<TCSEmailMessage> emailMessages = emailToSend.remove(String.join(":", phaseId.toString(), phaseStatus));
        if (send && emailMessages != null) {
            for (TCSEmailMessage message: emailMessages) {
                THREAD_POOL.execute(() -> {
                    try {
                        EmailEngine.send(message);
                    } catch (SendingException e) {
                        log.error("Problem with sending email.", e);
                    }
                });
            }
        }
    }
}
