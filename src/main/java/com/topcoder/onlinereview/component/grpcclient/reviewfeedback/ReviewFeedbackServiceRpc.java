package com.topcoder.onlinereview.component.grpcclient.reviewfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.component.reviewfeedback.ReviewFeedback;
import com.topcoder.onlinereview.component.reviewfeedback.ReviewFeedbackDetail;
import com.topcoder.onlinereview.grpc.reviewfeedback.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class ReviewFeedbackServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private ReviewFeedbackServiceGrpc.ReviewFeedbackServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = ReviewFeedbackServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public ReviewFeedback getReviewFeedback(long reviewFeedbackId) {
        ReviewFeedbackIdProto request = ReviewFeedbackIdProto.newBuilder().setReviewFeedbackId(reviewFeedbackId)
                .build();
        ReviewFeedbackProto response = stub.getReviewFeedback(request);
        return loadReviewFeedback(response);
    }

    public List<ReviewFeedback> getReviewFeedbackByProjectId(long projectId) {
        ProjectIdProto request = ProjectIdProto.newBuilder().setProjectId(projectId).build();
        ReviewFeedbacksProto response = stub.getReviewFeedbackByProjectId(request);
        List<ReviewFeedback> reviewFeedbacks = new ArrayList<>();
        for (ReviewFeedbackProto r : response.getReviewFeedbacksList()) {
            reviewFeedbacks.add(loadReviewFeedback(r));
        }
        return reviewFeedbacks;
    }

    public List<ReviewFeedbackDetail> getReviewFeedbackDetails(long reviewFeedbackId) {
        ReviewFeedbackIdProto request = ReviewFeedbackIdProto.newBuilder().setReviewFeedbackId(reviewFeedbackId)
                .build();
        ReviewFeedbackDetailsProto response = stub.getReviewFeedbackDetails(request);
        List<ReviewFeedbackDetail> reviewFeedbackDetails = new ArrayList<>();
        for (ReviewFeedbackDetailProto r : response.getReviewFeedbackDetailsList()) {
            reviewFeedbackDetails.add(loadReviewFeedbackDetail(r));
        }
        return reviewFeedbackDetails;
    }

    public List<ReviewFeedbackDetailProto> getReviewFeedbackDetailsByProjectId(long projectId) {
        ProjectIdProto request = ProjectIdProto.newBuilder().setProjectId(projectId).build();
        ReviewFeedbackDetailsProto response = stub.getReviewFeedbackDetailsByProjectId(request);
        return response.getReviewFeedbackDetailsList();
    }

    private ReviewFeedback loadReviewFeedback(ReviewFeedbackProto r) {
        if (!r.hasReviewFeedbackId()) {
            return null;
        }
        ReviewFeedback newEntity = new ReviewFeedback();
        newEntity.setId(r.getReviewFeedbackId());
        if (r.hasProjectId()) {
            newEntity.setProjectId(r.getProjectId());
        }
        if (r.hasComment()) {
            newEntity.setComment(r.getComment());
        }
        if (r.hasCreateUser()) {
            newEntity.setCreateUser(r.getCreateUser());
        }
        if (r.hasCreateDate()) {
            newEntity.setCreateDate(new Date(r.getCreateDate().getSeconds() * 1000));
        }
        if (r.hasModifyUser()) {
            newEntity.setModifyUser(r.getModifyUser());
        }
        if (r.hasModifyDate()) {
            newEntity.setModifyDate(new Date(r.getModifyDate().getSeconds() * 1000));
        }
        newEntity.setDetails(new ArrayList<>());
        return newEntity;
    }

    private ReviewFeedbackDetail loadReviewFeedbackDetail(ReviewFeedbackDetailProto r) {
        if (!r.hasReviewFeedbackId()) {
            return null;
        }
        ReviewFeedbackDetail detail = new ReviewFeedbackDetail();
        if (r.hasReviewerUserId()) {
            detail.setReviewerUserId(r.getReviewerUserId());
        }
        if (r.hasScore()) {
            detail.setScore(r.getScore());
        }
        if (r.hasFeedbackText()) {
            detail.setFeedbackText(r.getFeedbackText());
        }
        return detail;
    }
}
