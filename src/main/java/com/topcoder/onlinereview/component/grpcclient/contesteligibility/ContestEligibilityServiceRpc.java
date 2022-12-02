package com.topcoder.onlinereview.component.grpcclient.contesteligibility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import com.topcoder.onlinereview.component.contest.ContestEligibility;
import com.topcoder.onlinereview.component.contest.GroupContestEligibility;
import com.topcoder.onlinereview.component.grpcclient.contesteligibility.protos.*;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Component
public class ContestEligibilityServiceRpc {

    @GrpcClient("ContestEligibilityServiceRpc")
    private ContestEligibilityServiceGrpc.ContestEligibilityServiceBlockingStub stub;

    public Long create(ContestEligibility contestEligibility) {
        CreateRequest createRequest = CreateRequest.newBuilder().setContestId(contestEligibility.getContestId())
                .setStudio(contestEligibility.getStudio()).build();
        stub.create(createRequest);

        GetIdByContestIdRequest getIdByContestIdRequest = GetIdByContestIdRequest
                .newBuilder().setContestId(contestEligibility.getContestId()).build();
        GetIdByContestIdResponse getIdByContestIdResponse = stub
                .getIdByContestId(getIdByContestIdRequest);
        return getIdByContestIdResponse.getContestEligibilityId();
    }

    public void remove(long id) {
        RemoveRequest removeRequest = RemoveRequest.newBuilder().setContestEligibilityId(id)
                .build();
        stub.remove(removeRequest);
    }

    public void bulkRemove(List<Long> ids) {
        BulkRemoveRequest removeRequest = BulkRemoveRequest.newBuilder().addAllContestEligibilityIds(ids)
                .build();
        stub.bulkRemove(removeRequest);
    }

    public void update(ContestEligibility contestEligibility) {
        UpdateRequest updateRequest = UpdateRequest.newBuilder().setContestEligibilityId(contestEligibility.getId())
                .setContestId(contestEligibility.getContestId()).setStudio(contestEligibility.getStudio()).build();
        stub.update(updateRequest);
    }

    public List<ContestEligibility> getContestEligibility(long contestId, boolean isStudio) {
        GetContestEligibilityRequest getContestEligibilityRequest = GetContestEligibilityRequest.newBuilder()
                .setContestId(contestId).setStudio(isStudio).build();
        ContestEligibilitiesResponse contestEligibilitiesResponse = stub
                .getContestEligibility(getContestEligibilityRequest);
        List<GroupContestEligibilityProto> x = contestEligibilitiesResponse.getGroupContestEligibilitiesList();
        List<ContestEligibility> results = new ArrayList<>();
        for (GroupContestEligibilityProto groupContestEligibility : x) {
            GroupContestEligibility ce = new GroupContestEligibility();
            ce.setId(groupContestEligibility.getContestEligibilityId());
            ce.setContestId(groupContestEligibility.getContestId());
            ce.setStudio(groupContestEligibility.getStudio());
            ce.setDeleted(false);
            ce.setGroupId(groupContestEligibility.getGroupId());
            results.add(ce);
        }
        return results;
    }

    public Set<Long> haveEligibility(Long[] contestIds, boolean isStudio) {
        HaveEligibilityRequest haveEligibilityRequest = HaveEligibilityRequest.newBuilder()
                .addAllContestIds(Arrays.asList(contestIds)).setStudio(isStudio).build();
        HaveEligibilityResponse haveEligibilityResponse = stub.haveEligibility(haveEligibilityRequest);
        return new HashSet<>(haveEligibilityResponse.getContestIdsList());
    }
}
