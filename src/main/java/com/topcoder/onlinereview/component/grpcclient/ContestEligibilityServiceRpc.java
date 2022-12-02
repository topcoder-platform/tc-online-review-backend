package com.topcoder.onlinereview.component.grpcclient;

import org.springframework.stereotype.Component;
import com.topcoder.onlinereview.component.contest.ContestEligibility;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Component
public class ContestEligibilityServiceRpc {

    @GrpcClient("ContestEligibilityServiceRpc")
    private ContestEligibilityServiceGrpc.ContestEligibilityServiceBlockingStub stub;

    public Long create(ContestEligibility contestEligibility) {
        CreateContestEligibilityRequest createContestEligibilityRequest = CreateContestEligibilityRequest
                .newBuilder()
                .setContestId(contestEligibility.getContestId()).setStudio(contestEligibility.getStudio()).build();
        stub.createContestEligibility(createContestEligibilityRequest);

        GetContestEligibilityIdByContestIdRequest getContestEligibilityIdByContestIdRequest = GetContestEligibilityIdByContestIdRequest
                .newBuilder().setContestId(contestEligibility.getContestId()).build();
        GetContestEligibilityIdByContestIdResponse getContestEligibilityIdByContestIdResponse = stub
                .getContestEligibilityIdByContestId(getContestEligibilityIdByContestIdRequest);
        return getContestEligibilityIdByContestIdResponse.getContesEligibilitytId();
    }
}
