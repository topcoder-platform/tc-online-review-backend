package com.topcoder.onlinereview.component.grpcclient;

import com.topcoder.onlinereview.component.grpcclient.contesteligibility.ContestEligibilityServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.dataaccess.DataAccessServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.deliverable.DeliverableServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.payment.PaymentServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.phase.PhaseServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.phasehandler.PhaseHandlerServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.project.ProjectServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.resource.ResourceServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.review.ReviewServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.reviewfeedback.ReviewFeedbackServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.reviewupload.ReviewUploadServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.scorecard.ScorecardServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.termsofuse.TermsOfUseServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.upload.UploadServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.userretrieval.UserRetrievalServiceRpc;
import com.topcoder.onlinereview.component.grpcclient.webcommon.WebCommonServiceRpc;
import com.topcoder.onlinereview.component.util.SpringUtils;

public class GrpcHelper {

    public static ContestEligibilityServiceRpc getContestEligibilityServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(ContestEligibilityServiceRpc.class);
    }

    public static DataAccessServiceRpc getDataAccessServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(DataAccessServiceRpc.class);
    }

    public static DeliverableServiceRpc getDeliverableServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(DeliverableServiceRpc.class);
    }

    public static PaymentServiceRpc getPaymentServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(PaymentServiceRpc.class);
    }

    public static PhaseServiceRpc getPhaseServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(PhaseServiceRpc.class);
    }

    public static PhaseHandlerServiceRpc getPhaseHandlerServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(PhaseHandlerServiceRpc.class);
    }

    public static ProjectServiceRpc getProjectServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(ProjectServiceRpc.class);
    }

    public static ResourceServiceRpc getResourceServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(ResourceServiceRpc.class);
    }

    public static ReviewServiceRpc getReviewServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(ReviewServiceRpc.class);
    }

    public static ReviewFeedbackServiceRpc getReviewFeedbackServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(ReviewFeedbackServiceRpc.class);
    }

    public static ReviewUploadServiceRpc getReviewUploadServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(ReviewUploadServiceRpc.class);
    }

    public static ScorecardServiceRpc getScorecardServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(ScorecardServiceRpc.class);
    }

    public static TermsOfUseServiceRpc getTermsOfUseServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(TermsOfUseServiceRpc.class);
    }

    public static UploadServiceRpc getUploadServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(UploadServiceRpc.class);
    }

    public static UserRetrievalServiceRpc getUserRetrievalServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(UserRetrievalServiceRpc.class);
    }

    public static WebCommonServiceRpc getWebCommonServiceRpc() {
        return SpringUtils.getApplicationContext().getBean(WebCommonServiceRpc.class);
    }
}
