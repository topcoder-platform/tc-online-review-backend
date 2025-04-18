package com.topcoder.onlinereview.component.grpcclient.actionshelper;

import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.grpc.actionshelper.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class ActionsHelperServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private ActionsHelperServiceGrpc.ActionsHelperServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = ActionsHelperServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public String getUserPreferenceValue(long userId, int preferenceId) {
        GetUserPreferenceValueRequest request = GetUserPreferenceValueRequest.newBuilder().setUserId(userId)
                .setPreferenceId(preferenceId).build();
        GetUserPreferenceValueResponse response = stub.getUserPreferenceValue(request);

        if (response.hasValue()) {
            return response.getValue();
        }
        return null;
    }

    public boolean isProjectResultExists(Long userId, long projectId) {
        IsProjectResultExistsRequest.Builder request = IsProjectResultExistsRequest.newBuilder()
                .setProjectId(projectId);
        if (userId != null) {
            request.setUserId(userId);
        }
        IsProjectResultExistsResponse response = stub.isProjectResultExists(request.build());
        return response.getIsProjectResultExists();
    }

    public boolean isComponentInquiryExists(Long userId, long projectId) {
        IsComponentInquiryExistsRequest.Builder request = IsComponentInquiryExistsRequest.newBuilder()
                .setProjectId(projectId);
        if (userId != null) {
            request.setUserId(userId);
        }
        IsComponentInquiryExistsResponse response = stub.isComponentInquiryExists(request.build());
        return response.getIsComponentInquiryExists();
    }

    public List<RatingProto> getRatings(Long userId, long projectId) {
        GetRatingsRequest.Builder request = GetRatingsRequest.newBuilder()
                .setProjectId(projectId);
        if (userId != null) {
            request.setUserId(userId);
        }
        GetRatingsResponse response = stub.getRatings(request.build());
        return response.getRatingsList();
    }

    public int createProjectResult(Long userId, long projectId, Long ratingInd, Long validSubmissionInd,
            Double oldRating) {
        CreateProjectResultRequest.Builder request = CreateProjectResultRequest.newBuilder()
                .setProjectId(projectId);
        if (userId != null) {
            request.setUserId(userId.toString());
        }
        if (ratingInd != null) {
            request.setRatingInd(ratingInd);
        }
        if (validSubmissionInd != null) {
            request.setValidSubmissionInd(validSubmissionInd);
        }
        if (oldRating != null) {
            request.setOldRating(oldRating);
        }
        CountProto response = stub.createProjectResult(request.build());
        return response.getCount();
    }

    public int createComponentInquiry(Long componentInquiryId, Long componentId, Long userId, Long projectId,
            Long phaseId, Long tcUserId, Long agreedToTerms, Double oldRating, Long version) {
        CreateComponentInquiryRequest.Builder request = CreateComponentInquiryRequest.newBuilder();
        if (componentInquiryId != null) {
            request.setComponentInquiryId(componentInquiryId);
        }
        if (componentId != null) {
            request.setComponentId(componentId);
        }
        if (userId != null) {
            request.setUserId(userId);
        }
        if (projectId != null) {
            request.setProjectId(projectId);
        }
        if (phaseId != null) {
            request.setPhase(phaseId);
        }
        if (tcUserId != null) {
            request.setTcUserId(tcUserId);
        }
        if (agreedToTerms != null) {
            request.setAgreedToTerms(agreedToTerms);
        }
        if (oldRating != null) {
            request.setRating(oldRating);
        }
        if (version != null) {
            request.setVersion(version);
        }
        CountProto response = stub.createComponentInquiry(request.build());
        return response.getCount();
    }

    public long getNextComponentInquiryId() {
        NextIdProto response = stub.getNextComponentInquiryId(null);
        return response.getCurrentValue();
    }

    public int updateNextComponentInquiryId(long newCurrentValue, long oldCurrentValue) {
        UpdateNextIdRequest request = UpdateNextIdRequest.newBuilder().setNewCurrentValue(newCurrentValue)
                .setOldCurrentValue(oldCurrentValue).build();
        CountProto response = stub.updateNextComponentInquiryId(request);
        return response.getCount();
    }

    public String getRootCategoryIdByComponentId(String componentId) {
        GetRootCategoryIdByComponentIdRequest.Builder request = GetRootCategoryIdByComponentIdRequest.newBuilder();
        if (componentId != null) {
            request.setComponentId(componentId);
        }
        GetRootCategoryIdByComponentIdResponse response = stub.getRootCategoryIdByComponentId(request.build());

        if (response.hasRootCategoryId()) {
            return response.getRootCategoryId();
        }
        return null;
    }

    public List<DefaultScorecardProto> getDefaultScorecards() {
        GetDefaultScorecardsResponse response = stub.getDefaultScorecards(null);
        return response.getDefaultScorecardsList();
    }

    public int deleteProjectResult(long projectId, long userId) {
        DeleteProjectResultRequest request = DeleteProjectResultRequest.newBuilder().setProjectId(projectId)
                .setUserId(userId).build();
        CountProto response = stub.deleteProjectResult(request);
        return response.getCount();
    }

    public int deleteComponentInquiry(long projectId, long userId) {
        DeleteComponentInquiryRequest request = DeleteComponentInquiryRequest.newBuilder().setProjectId(projectId)
                .setUserId(userId).build();
        CountProto response = stub.deleteComponentInquiry(request);
        return response.getCount();
    }

    public int updateProjectResultForAdvanceScreening(long projectId, long userId) {
        UpdateProjectResultForAdvanceScreeningRequest request = UpdateProjectResultForAdvanceScreeningRequest
                .newBuilder().setProjectId(projectId)
                .setUserId(userId).build();
        CountProto response = stub.updateProjectResultForAdvanceScreening(request);
        return response.getCount();
    }

    public Integer getVersionUsingComponentVersionId(long componentVersionId) {
        GetVersionUsingComponentVersionIdRequest request = GetVersionUsingComponentVersionIdRequest.newBuilder()
                .setCompVersId(componentVersionId).build();
        GetVersionUsingComponentVersionIdResponse response = stub.getVersionUsingComponentVersionId(request);
        if (response.hasVersion()) {
            return response.getVersion();
        }
        return null;
    }

    public List<DeliverabIdNameProto> getDeliverableIdToNameMap() {
        GetDeliverableIdToNameMapResponse response = stub.getDeliverableIdToNameMap(null);
        return response.getDeliverablesList();
    }

    public int logDownloadAttempt(long uploadId, Long userId, String remoteAddr, boolean successful) {
        LogDownloadAttemptRequest.Builder request = LogDownloadAttemptRequest.newBuilder();
        request.setUploadId(uploadId);
        if (userId != null) {
            request.setUserId(userId);
        }
        if (remoteAddr != null) {
            request.setIpAddress(remoteAddr);
        }
        request.setSuccessful(successful);
        CountProto response = stub.logDownloadAttempt(request.build());
        return response.getCount();
    }
}
