package com.topcoder.onlinereview.component.grpcclient.contesteligibility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import com.topcoder.onlinereview.component.contest.ContestEligibility;
import com.topcoder.onlinereview.component.contest.GroupContestEligibility;
import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.grpc.contesteligibility.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class ContestEligibilityServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private ContestEligibilityServiceGrpc.ContestEligibilityServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = ContestEligibilityServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public Long create(ContestEligibility contestEligibility) {
        CreateRequest.Builder cBuilder = CreateRequest.newBuilder();
        if (contestEligibility.getContestId() != null) {
            cBuilder.setContestId(Int64Value.of(contestEligibility.getContestId()));
        }
        if (contestEligibility.getStudio() != null) {
            cBuilder.setStudio(BoolValue.of(contestEligibility.getStudio()));
        }
        CreateResponse response = stub.create(cBuilder.build());

        if (response.hasContestEligibilityId()) {
            return response.getContestEligibilityId().getValue();
        }
        return null;
    }

    public void remove(long id) {
        RemoveRequest removeRequest = RemoveRequest.newBuilder().setContestEligibilityId(Int64Value.of(id))
                .build();
        stub.remove(removeRequest);
    }

    public void bulkRemove(List<Long> ids) {
        BulkRemoveRequest removeRequest = BulkRemoveRequest.newBuilder().addAllContestEligibilityIds(ids)
                .build();
        stub.bulkRemove(removeRequest);
    }

    public void update(ContestEligibility contestEligibility) {
        UpdateRequest.Builder uBuilder = UpdateRequest.newBuilder();
        if (contestEligibility.getId() != null) {
            uBuilder.setContestEligibilityId(Int64Value.of(contestEligibility.getId()));
        }
        if (contestEligibility.getStudio() != null) {
            uBuilder.setStudio(BoolValue.of(contestEligibility.getStudio()));
        }
        stub.update(uBuilder.build());
    }

    public List<ContestEligibility> getContestEligibility(long contestId, boolean isStudio) {
        GetContestEligibilityRequest getContestEligibilityRequest = GetContestEligibilityRequest.newBuilder()
                .setContestId(Int64Value.of(contestId)).setStudio(BoolValue.of(isStudio)).build();
        ContestEligibilitiesResponse contestEligibilitiesResponse = stub
                .getContestEligibility(getContestEligibilityRequest);
        List<GroupContestEligibilityProto> groupContestEligibilitiesList = contestEligibilitiesResponse
                .getGroupContestEligibilitiesList();
        List<ContestEligibility> results = new ArrayList<>();
        for (GroupContestEligibilityProto groupContestEligibility : groupContestEligibilitiesList) {
            GroupContestEligibility ce = new GroupContestEligibility();
            if (groupContestEligibility.hasContestEligibilityId()) {
                ce.setId(groupContestEligibility.getContestEligibilityId().getValue());
            }
            if (groupContestEligibility.hasContestId()) {
                ce.setContestId(groupContestEligibility.getContestId().getValue());
            }
            if (groupContestEligibility.hasStudio()) {
                ce.setStudio(groupContestEligibility.getStudio().getValue());
            }
            ce.setDeleted(false);
            if (groupContestEligibility.hasGroupId()) {
                ce.setGroupId(groupContestEligibility.getGroupId().getValue());
            }
            results.add(ce);
        }
        return results;
    }

    public Set<Long> haveEligibility(Long[] contestIds, boolean isStudio) {
        HaveEligibilityRequest haveEligibilityRequest = HaveEligibilityRequest.newBuilder()
                .addAllContestIds(Arrays.asList(contestIds)).setStudio(BoolValue.of(isStudio)).build();
        HaveEligibilityResponse haveEligibilityResponse = stub.haveEligibility(haveEligibilityRequest);
        return new HashSet<>(haveEligibilityResponse.getContestIdsList());
    }

    public boolean validateUserContestEligibility(long userId, long groupId) {
        ValidateUserContestEligibilityRequest request = ValidateUserContestEligibilityRequest.newBuilder()
                .setUserId(Int64Value.of(userId)).setGroupId(Int64Value.of(groupId)).build();
        ValidateUserContestEligibilityResponse response = stub.validateUserContestEligibility(request);
        return response.getIsValid();
    }
}
