package com.topcoder.onlinereview.component.grpcclient.reviewupload;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.grpc.reviewupload.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class ReviewUploadServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private ReviewUploadServiceGrpc.ReviewUploadServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = ReviewUploadServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public boolean isProjectResultExists(long projectId, String userId) {
        ProjectUserRequest.Builder request = ProjectUserRequest.newBuilder();
        request.setProjectId(projectId);
        if (userId != null) {
            request.setUserId(userId);
        }
        ExistsProto response = stub.isProjectResultExists(request.build());
        return response.getExists();
    }

    public boolean isComponentInquiryExists(long projectId, String userId) {
        ProjectUserRequest.Builder request = ProjectUserRequest.newBuilder();
        request.setProjectId(projectId);
        if (userId != null) {
            request.setUserId(userId);
        }
        ExistsProto response = stub.isComponentInquiryExists(request.build());
        return response.getExists();
    }

    public Long getUserRating(long projectId, String userId) {
        ProjectUserRequest.Builder request = ProjectUserRequest.newBuilder();
        request.setProjectId(projectId);
        if (userId != null) {
            request.setUserId(userId);
        }
        RatingProto response = stub.getUserRating(request.build());
        if (response.hasRating()) {
            return response.getRating();
        }
        return null;
    }

    public int createProjectResult(long projectId, String userId, long ratingInd, long validSubmissionInd,
            Double oldRating) {
        ProjectResultProto.Builder request = ProjectResultProto.newBuilder();
        request.setProjectId(projectId);
        if (userId != null) {
            request.setUserId(userId);
        }
        request.setRatingInd(ratingInd);
        request.setValidSubmissionInd(validSubmissionInd);
        if (oldRating != null) {
            request.setOldRating(oldRating);
        }
        CountProto response = stub.createProjectResult(request.build());
        return response.getCount();
    }

    public int createComponentInquiry(long componentInquiryId, long componentId, String userId, long projectId,
            Long phaseId, String tcUserId, long agreedToTerms, double rating, long version) {
        ComponentInquiryProto.Builder request = ComponentInquiryProto.newBuilder();
        request.setComponentInquiryId(componentInquiryId);
        request.setComponentId(componentId);
        if (userId != null) {
            request.setUserId(userId);
        }
        request.setProjectId(projectId);
        if (phaseId != null) {
            request.setPhase(phaseId);
        }
        if (tcUserId != null) {
            request.setTcUserId(tcUserId);
        }
        request.setAgreedToTerms(agreedToTerms);
        request.setRating(rating);
        request.setVersion(version);
        CountProto response = stub.createComponentInquiry(request.build());
        return response.getCount();
    }

    public long getNextId() {
        NextIdProto response = stub.getNextId(null);
        return response.getCurrentValue();
    }

    public int updateNextId(long newCurrentValue, long oldCurrentValue) {
        UpdateNextIdRequest request = UpdateNextIdRequest.newBuilder().setNewCurrentValue(newCurrentValue)
                .setOldCurrentValue(oldCurrentValue).build();
        CountProto response = stub.updateNextId(request);
        return response.getCount();
    }
}
