/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.phase.handler.or;

import com.topcoder.onlinereview.component.grpcclient.phasehandler.PhaseHandlerServiceRpc;
import com.topcoder.onlinereview.component.project.phase.ManagerHelper;
import com.topcoder.onlinereview.component.project.phase.Phase;
import com.topcoder.onlinereview.component.project.phase.PhaseHandlingException;
import com.topcoder.onlinereview.component.project.phase.handler.ApprovalPhaseHandler;
import com.topcoder.onlinereview.component.project.phase.handler.Constants;
import com.topcoder.onlinereview.component.project.phase.handler.EmailOptions;
import com.topcoder.onlinereview.component.project.phase.handler.EmailScheme;
import com.topcoder.onlinereview.component.project.phase.handler.PhasesHelper;
import com.topcoder.onlinereview.component.review.Comment;
import com.topcoder.onlinereview.component.review.Review;

import java.util.List;

/**
 * This class is the customized phase handler for Approval phase, it will automatically complete the project, if the
 * approval phase is approved.
 *
 * <p>
 * Thread-safety: This class is thread-safe as it does not contain any mutable inner state.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
public class PRApprovalPhaseHandler extends ApprovalPhaseHandler {

    /**
     * Create a new instance of PRApprovalPhaseHandler using the given namespace for loading configuration settings.
     *
     * @throws IllegalArgumentException
     *             if the input is null or empty string.
     */
    public PRApprovalPhaseHandler(ManagerHelper managerHelper,
                                  List<EmailScheme> emailSchemes,
                                  EmailScheme reviewFeedbackEmailScheme,
                                  EmailOptions defaultStartEmailOption,
                                  EmailOptions defaultEndEmailOption,
                                  Long finalFixDuration,
                                  Long studioFinalFixDuration) {
        super(managerHelper, emailSchemes, reviewFeedbackEmailScheme, defaultStartEmailOption, defaultEndEmailOption,
                finalFixDuration, studioFinalFixDuration);
    }

    /**
     * Provides additional logic to execute a phase.
     *
     * @param phase
     *            The input phase to check.
     * @param operator
     *            The operator that execute the phase.
     * @throws PhaseHandlingException
     *             if there is any error occurred while processing the phase.
     * @throws IllegalArgumentException
     *             if the input parameters is null or empty string.
     */
    public void perform(PhaseHandlerServiceRpc phaseHandlerServiceRpc, Phase phase, String operator) throws PhaseHandlingException {
        // invoke the original Approval task.
        super.perform(phaseHandlerServiceRpc, phase, operator);

        boolean toStart = PhasesHelper.checkPhaseStatus(phase.getPhaseStatus());

        if (!toStart) {
            // check final fix is not been rejected
            boolean rejected = checkScorecardsRejected(phase);

            if (!rejected) {
                // update project status to Complete
                PRHelper.completeProject(getManagerHelper(), phase, operator);
            }
        }
    }

    /**
     * Checks whether any the approval scorecard is approved. If any approval scorecard is rejected, true
     * will be returned, otherwise false.
     *
     * @param phase
     *            the input phase.
     * @return true if any committed approval scorecard is rejected, false otherwise.
     * @throws PhaseHandlingException
     *             if any error occurs
     */
    private boolean checkScorecardsRejected(Phase phase) throws PhaseHandlingException {
        Review[] approveReviews = PhasesHelper.searchReviewsForPhase(getManagerHelper(),
                phase.getId(), null);

        // check for approved/rejected comments.
        boolean rejected = false;
        for (Review approveReview : approveReviews) {
            if (!approveReview.isCommitted()) {
                continue;
            }

            Comment[] comments = approveReview.getAllComments();

            for (Comment comment : comments) {
                String value = (String) comment.getExtraInfo();
                if (comment.getCommentType().getName().equals("Approval Review Comment")) {
                    if (Constants.COMMENT_VALUE_APPROVED.equalsIgnoreCase(value)
                            || Constants.COMMENT_VALUE_ACCEPTED.equalsIgnoreCase(value)) {
                        continue;
                    } else if (Constants.COMMENT_VALUE_REJECTED.equalsIgnoreCase(value)) {
                        rejected = true;
                        break;
                    } else {
                        throw new PhaseHandlingException("Comment can either be Approved, Accepted or Rejected.");
                    }
                }
            }
        }
        return rejected;
    }
}
