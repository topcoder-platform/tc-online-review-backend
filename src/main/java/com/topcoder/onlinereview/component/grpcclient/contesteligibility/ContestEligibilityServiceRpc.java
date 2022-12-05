package com.topcoder.onlinereview.component.grpcclient.contesteligibility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import com.topcoder.onlinereview.component.contest.ContestEligibility;
import com.topcoder.onlinereview.component.contest.GroupContestEligibility;
import com.topcoder.onlinereview.component.grpcclient.contesteligibility.protos.*;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Component
public class ContestEligibilityServiceRpc {

    @GrpcClient("ContestEligibilityServiceRpc")
    private ContestEligibilityServiceGrpc.ContestEligibilityServiceBlockingStub stub;

    public Long create(ContestEligibility contestEligibility) {
        CreateRequest.Builder cBuilder = CreateRequest.newBuilder();
        if (contestEligibility.getContestId() != null) {
            cBuilder.setContestId(Int64Value.of(contestEligibility.getContestId()));
        }
        if (contestEligibility.getStudio() != null) {
            cBuilder.setStudio(BoolValue.of(contestEligibility.getStudio()));
        }
        stub.create(cBuilder.build());

        GetIdByContestIdRequest getIdByContestIdRequest = GetIdByContestIdRequest
                .newBuilder().setContestId(
                        contestEligibility.getContestId() != null ? Int64Value.of(contestEligibility.getContestId())
                                : null)
                .build();
        GetIdByContestIdResponse getIdByContestIdResponse = stub
                .getIdByContestId(getIdByContestIdRequest);
        if (getIdByContestIdResponse.hasContestEligibilityId()) {
            return getIdByContestIdResponse.getContestEligibilityId().getValue();
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
}
