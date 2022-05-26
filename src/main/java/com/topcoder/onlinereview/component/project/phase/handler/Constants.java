package com.topcoder.onlinereview.component.project.phase.handler;

/**
 * <p>
 * A helper class used for storing constant values.
 * </p>
 * @author VolodymyrK, Vovka
 * @version 1.8.6
 */
public final class Constants {

    // ------------------------ Project Status Names -----
    public static final String PROJECT_STATUS_COMPLETED = "Completed";

    // ------------------------ Project Type Names -----
    public static final String PROJECT_TYPE_STUDIO = "Studio";

    // ------------------------ Project Category Names -----
    public static final String PROJECT_CATEGORY_COPILOT_POSTING = "Copilot Posting";

    // ------------------------ Phase Type Names -----
    public static final String PHASE_SPECIFICATION_SUBMISSION = "Specification Submission";
    public static final String PHASE_SPECIFICATION_REVIEW = "Specification Review";
    public static final String PHASE_REGISTRATION = "Registration";
    public static final String PHASE_SUBMISSION = "Submission";
    public static final String PHASE_CHECKPOINT_SUBMISSION = "Checkpoint Submission";
    public static final String PHASE_SCREENING = "Screening";
    public static final String PHASE_CHECKPOINT_SCREENING = "Checkpoint Screening";
    public static final String PHASE_REVIEW = "Review";
    public static final String PHASE_CHECKPOINT_REVIEW = "Checkpoint Review";
    public static final String PHASE_APPEALS = "Appeals";
    public static final String PHASE_APPEALS_RESPONSE = "Appeals Response";
    public static final String PHASE_AGGREGATION = "Aggregation";
    public static final String PHASE_FINAL_FIX = "Final Fix";
    public static final String PHASE_FINAL_REVIEW = "Final Review";
    public static final String PHASE_APPROVAL = "Approval";
    public static final String PHASE_POST_MORTEM = "Post-Mortem";
    public static final String PHASE_ITERATIVE_REVIEW = "Iterative Review";

    // ------------------------ Phase Status Names -----
    public static final String PHASE_STATUS_SCHEDULED = "Scheduled";
    public static final String PHASE_STATUS_OPEN = "Open";
    public static final String PHASE_STATUS_CLOSED = "Closed";

    // ------------------------ Resource Role Names -----
    public static final String ROLE_SUBMITTER = "Submitter";
    public static final String ROLE_PRIMARY_SCREENER = "Primary Screener";
    public static final String ROLE_REVIEWER = "Reviewer";
    public static final String ROLE_ACCURACY_REVIEWER = "Accuracy Reviewer";
    public static final String ROLE_FAILURE_REVIEWER = "Failure Reviewer";
    public static final String ROLE_STRESS_REVIEWER = "Stress Reviewer";
    public static final String ROLE_AGGREGATOR = "Aggregator";
    public static final String ROLE_FINAL_REVIEWER = "Final Reviewer";
    public static final String ROLE_APPROVER = "Approver";
    public static final String ROLE_DESIGNER = "Designer";
    public static final String ROLE_OBSERVER = "Observer";
    public static final String ROLE_MANAGER = "Manager";
    public static final String ROLE_COPILOT = "Copilot";
    public static final String ROLE_CLIENT_MANAGER = "Client Manager";
    public static final String ROLE_POST_MORTEM_REVIEWER = "Post-Mortem Reviewer";
    public static final String ROLE_SPECIFICATION_SUBMITTER = "Specification Submitter";
    public static final String ROLE_SPECIFICATION_REVIEWER = "Specification Reviewer";
    public static final String ROLE_CHECKPOINT_SCREENER = "Checkpoint Screener";
    public static final String ROLE_CHECKPOINT_REVIEWER = "Checkpoint Reviewer";
    public static final String ROLE_ITERATIVE_REVIEWER = "Iterative Reviewer";

    // ------------------------ Submission Type Names -----
    public static final String SUBMISSION_TYPE_CONTEST_SUBMISSION = "Contest Submission";
    public static final String SUBMISSION_TYPE_SPECIFICATION_SUBMISSION = "Specification Submission";
    public static final String SUBMISSION_TYPE_CHECKPOINT_SUBMISSION = "Checkpoint Submission";

    // ------------------------ Submission Status Names -----
    public static final String SUBMISSION_STATUS_ACTIVE = "Active";
    public static final String SUBMISSION_STATUS_FAILED_SCREENING = "Failed Screening";
    public static final String SUBMISSION_STATUS_FAILED_REVIEW = "Failed Review";
    public static final String SUBMISSION_STATUS_COMPLETED_WITHOUT_WIN = "Completed Without Win";
    public static final String SUBMISSION_STATUS_DELETED = "Deleted";
    public static final String SUBMISSION_STATUS_FAILED_CHECKPOINT_SCREENING = "Failed Checkpoint Screening";
    public static final String SUBMISSION_STATUS_FAILED_CHECKPOINT_REVIEW = "Failed Checkpoint Review";

    // ------------------------ Upload Status Names -----
    public static final String UPLOAD_STATUS_ACTIVE = "Active";
    public static final String UPLOAD_STATUS_DELETED = "Deleted";

    // ------------------------ Upload Type Names -----
    public static final String UPLOAD_TYPE_SUBMISSION = "Submission";
    public static final String UPLOAD_TYPE_TEST_CASE = "Test Case";
    public static final String UPLOAD_TYPE_FINAL_FIX = "Final Fix";
    public static final String UPLOAD_TYPE_REVIEW_DOCUMENT = "Review Document";

    // ------------------------ Review Comment Type Names -----
    public static final String COMMENT_TYPE_COMMENT = "Comment";
    public static final String COMMENT_TYPE_RECOMMENDED = "Recommended";
    public static final String COMMENT_TYPE_REQUIRED = "Required";
    public static final String COMMENT_TYPE_APPEAL = "Appeal";
    public static final String COMMENT_TYPE_APPEAL_RESPONSE = "Appeal Response";
    public static final String COMMENT_TYPE_AGGREGATION_COMMENT = "Aggregation Comment";
    public static final String COMMENT_TYPE_SUBMITTER_COMMENT = "Submitter Comment";
    public static final String COMMENT_TYPE_FINAL_FIX_COMMENT = "Final Fix Comment";
    public static final String COMMENT_TYPE_FINAL_REVIEW_COMMENT = "Final Review Comment";
    public static final String COMMENT_TYPE_MANAGER_COMMENT = "Manager Comment";
    public static final String COMMENT_TYPE_APPROVAL_REVIEW_COMMENT = "Approval Review Comment";
    public static final String COMMENT_TYPE_APPROVAL_REVIEW_COMMENT_OTHER_FIXES = "Approval Review Comment - Other Fixes";
    public static final String COMMENT_TYPE_SPECIFICATION_REVIEW_COMMENT = "Specification Review Comment";

    // ------------------------ Review Comment Values -----
    public static final String COMMENT_VALUE_APPROVED = "Approved";
    public static final String COMMENT_VALUE_ACCEPTED = "Accepted";
    public static final String COMMENT_VALUE_REJECTED = "Rejected";
    public static final String COMMENT_VALUE_REQUIRED = "Required";

    // ------------------------ Phase Criteria Type Names -----
    public static final String PHASE_CRITERIA_SCORECARD_ID = "Scorecard ID";
    public static final String PHASE_CRITERIA_REGISTRATION_NUMBER = "Registration Number";
    public static final String PHASE_CRITERIA_REVIEWER_NUMBER = "Reviewer Number";

    // ------------------------ Review Type Names -----
    public static final String REVIEW_TYPE_PEER = "PEER";

    // ------------------------ Dummy User IDs -----
    public static final long USER_ID_COMPONENTS = 22719217;
    public static final long USER_ID_APPLICATIONS = 22770213;
    public static final long USER_ID_LCSUPPORT = 22873364;

    // ------------------------ Other -----
    public static final long HOUR = 3600 * 1000;
}
