package com.topcoder.onlinereview.component.grpcclient.reviewfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.google.protobuf.Timestamp;
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

    public List<Long> getReviewerIdsByFeedbackId(long reviewFeedbackId) {
        ReviewFeedbackIdProto request = ReviewFeedbackIdProto.newBuilder().setReviewFeedbackId(reviewFeedbackId)
                .build();
        ReviewerIdsProto response = stub.getReviewerIdsByFeedbackId(request);
        return response.getReviewerUserIdsList();
    }

    public long createReviewFeedback(ReviewFeedback reviewFeedback, String operator, Date date) {
        ReviewFeedbackProto.Builder request = ReviewFeedbackProto.newBuilder();
        request.setProjectId(reviewFeedback.getProjectId());
        if (reviewFeedback.getComment() != null) {
            request.setComment(reviewFeedback.getComment());
        }
        if (operator != null) {
            request.setCreateUser(operator);
            request.setModifyUser(operator);
        }
        if (date != null) {
            request.setCreateDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()).build());
            request.setModifyDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()).build());
        }
        ReviewFeedbackIdProto response = stub.createReviewFeedback(request.build());
        return response.getReviewFeedbackId();
    }

    public int createReviewFeedbackDetail(ReviewFeedbackDetail reviewFeedbackDetail, long feedbackId) {
        ReviewFeedbackDetailProto.Builder request = ReviewFeedbackDetailProto.newBuilder();
        request.setReviewFeedbackId(feedbackId);
        request.setReviewerUserId(reviewFeedbackDetail.getReviewerUserId());
        if (reviewFeedbackDetail.getScore() != null) {
            request.setScore(reviewFeedbackDetail.getScore());
        }
        if (reviewFeedbackDetail.getFeedbackText() != null) {
            request.setFeedbackText(reviewFeedbackDetail.getFeedbackText());
        }
        CountProto response = stub.createReviewFeedbackDetail(request.build());
        return response.getCount();
    }

    public int auditReviewFeedback(ReviewFeedback reviewFeedback, long auditActionTypeId, String operator,
            Date actionDate) {
        ReviewFeedbackAuditProto.Builder request = ReviewFeedbackAuditProto.newBuilder();
        request.setReviewFeedbackId(reviewFeedback.getId());
        request.setProjectId(reviewFeedback.getProjectId());
        if (reviewFeedback.getComment() != null) {
            request.setComment(reviewFeedback.getComment());
        }
        request.setAuditActionTypeId(auditActionTypeId);
        if (operator != null) {
            request.setActionUser(operator);
        }
        if (actionDate != null) {
            request.setActionDate(Timestamp.newBuilder().setSeconds(actionDate.toInstant().getEpochSecond()).build());
        }
        CountProto response = stub.auditReviewFeedback(request.build());
        return response.getCount();
    }

    public int auditReviewFeedbackDetail(ReviewFeedbackDetail detail, long reviewFeedbackId, long auditActionTypeId,
            String operator, Date actionDate) {
        ReviewFeedbackDetailAuditProto.Builder request = ReviewFeedbackDetailAuditProto.newBuilder();
        request.setReviewFeedbackId(reviewFeedbackId);
        request.setReviewerUserId(detail.getReviewerUserId());
        if (detail.getScore() != null) {
            request.setScore(detail.getScore());
        }
        if (detail.getFeedbackText() != null) {
            request.setFeedbackText(detail.getFeedbackText());
        }
        request.setAuditActionTypeId(auditActionTypeId);
        if (operator != null) {
            request.setActionUser(operator);
        }
        if (actionDate != null) {
            request.setActionDate(Timestamp.newBuilder().setSeconds(actionDate.toInstant().getEpochSecond()).build());
        }
        CountProto response = stub.auditReviewFeedbackDetail(request.build());
        return response.getCount();
    }

    public int updateReviewFeedback(ReviewFeedback reviewFeedback, String operator, Date date) {
        ReviewFeedbackProto.Builder request = ReviewFeedbackProto.newBuilder();
        request.setReviewFeedbackId(reviewFeedback.getId());
        request.setProjectId(reviewFeedback.getProjectId());
        if (reviewFeedback.getComment() != null) {
            request.setComment(reviewFeedback.getComment());
        }
        if (operator != null) {
            request.setModifyUser(operator);
        }
        if (date != null) {
            request.setModifyDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()).build());
        }
        CountProto response = stub.updateReviewFeedback(request.build());
        return response.getCount();
    }

    public int updateReviewFeedbackDetail(ReviewFeedbackDetail reviewFeedbackDetail, long feedbackId) {
        ReviewFeedbackDetailProto.Builder request = ReviewFeedbackDetailProto.newBuilder();
        request.setReviewFeedbackId(feedbackId);
        request.setReviewerUserId(reviewFeedbackDetail.getReviewerUserId());
        if (reviewFeedbackDetail.getScore() != null) {
            request.setScore(reviewFeedbackDetail.getScore());
        }
        if (reviewFeedbackDetail.getFeedbackText() != null) {
            request.setFeedbackText(reviewFeedbackDetail.getFeedbackText());
        }
        CountProto response = stub.updateReviewFeedbackDetail(request.build());
        return response.getCount();
    }

    public int deleteReviewFeedback(long feedbackId) {
        ReviewFeedbackIdProto request = ReviewFeedbackIdProto.newBuilder().setReviewFeedbackId(feedbackId)
                .build();
        CountProto response = stub.deleteReviewFeedback(request);
        return response.getCount();
    }

    public int deleteReviewFeedbackDetail(long feedbackId, List<Long> reviewerUserIds) {
        DeleteReviewFeedbackDetailRequest request = DeleteReviewFeedbackDetailRequest.newBuilder()
                .setReviewFeedbackId(feedbackId).addAllReviewerUserIds(reviewerUserIds).build();
        CountProto response = stub.deleteReviewFeedbackDetail(request);
        return response.getCount();
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
