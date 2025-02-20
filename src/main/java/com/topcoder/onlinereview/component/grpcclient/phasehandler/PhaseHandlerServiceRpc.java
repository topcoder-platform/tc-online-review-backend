package com.topcoder.onlinereview.component.grpcclient.phasehandler;

import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.grpc.phasehandler.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class PhaseHandlerServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private PhaseHandlerServiceGrpc.PhaseHandlerServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = PhaseHandlerServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public int updateProjectResultPayment(long projectId, Double totalPayment, Long userId) {
        UpdateProjectResultPaymentRequest.Builder request = UpdateProjectResultPaymentRequest.newBuilder()
                .setProjectId(projectId);
        if (totalPayment != null) {
            request.setPayment(totalPayment);
        }
        if (userId != null) {
            request.setUserId(userId);
        }
        CountProto response = stub.updateProjectResultPayment(request.build());
        return response.getCount();
    }

    public int updateProjectResult(long projectId) {
        UpdateProjectResultRequest request = UpdateProjectResultRequest.newBuilder().setProjectId(projectId).build();
        CountProto response = stub.updateProjectResult(request);
        return response.getCount();
    }

    public int updateFailedPassScreening(long projectId) {
        UpdateFailedPassScreeningRequest request = UpdateFailedPassScreeningRequest.newBuilder().setProjectId(projectId)
                .build();
        CountProto response = stub.updateFailedPassScreening(request);
        return response.getCount();
    }

    public int updatePassScreening(long projectId) {
        UpdatePassScreeningRequest request = UpdatePassScreeningRequest.newBuilder().setProjectId(projectId).build();
        CountProto response = stub.updatePassScreening(request);
        return response.getCount();
    }

    public List<ReviewProto> getReviews(long projectId) {
        GetReviewsRequest request = GetReviewsRequest.newBuilder().setProjectId(projectId).build();
        GetReviewsResponse response = stub.getReviews(request);
        return response.getReviewsList();
    }

    public int updateProjectResultReview(long projectId, Double rawScore, long userId) {
        UpdateProjectResultReviewRequest.Builder request = UpdateProjectResultReviewRequest.newBuilder()
                .setProjectId(projectId).setUserId(userId);
        if (rawScore != null) {
            request.setRawScore(rawScore);
        }
        CountProto response = stub.updateProjectResultReview(request.build());
        return response.getCount();
    }

    public List<AppealResponseProto> getAppealResponses(long projectId) {
        GetAppealResponsesRequest request = GetAppealResponsesRequest.newBuilder().setProjectId(projectId).build();
        GetAppealResponsesResponse response = stub.getAppealResponses(request);
        return response.getAppealResponsesList();
    }

    public int updateProjectResultAppealResponse(long projectId, Double finalScore, Long userId, Integer placed,
            int passedReviewInd) {
        UpdateProjectResultAppealResponseRequest.Builder request = UpdateProjectResultAppealResponseRequest.newBuilder()
                .setProjectId(projectId).setPassedReviewInd(passedReviewInd);
        if (finalScore != null) {
            request.setFinalScore(finalScore);
        }
        if (userId != null) {
            request.setUserId(userId);
        }
        if (placed != null) {
            request.setPlaced(placed);
        }
        CountProto response = stub.updateProjectResultAppealResponse(request.build());
        return response.getCount();
    }
}
