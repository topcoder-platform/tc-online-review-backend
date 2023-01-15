package com.topcoder.onlinereview.component.grpcclient.scorecard;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.component.scorecard.Group;
import com.topcoder.onlinereview.grpc.scorecard.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class ScorecardServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private ScorecardServiceGrpc.ScorecardServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = ScorecardServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public long createGroup(Group group, int order, String operator, long parentId) {
        CreateGroupRequest request = buildCreateGroupRequest(group, order, operator, parentId);
        GroupIdProto response = stub.createGroup(request);
        return response.getScorecardGroupId();
    }

    public List<Long> createGroups(Group[] groups, String operator, long parentId) {
        CreateGroupsRequest.Builder request = CreateGroupsRequest.newBuilder();
        for (int i = 0; i < groups.length; i++) {
            CreateGroupRequest req = buildCreateGroupRequest(groups[i], i, operator, parentId);
            request.addGroups(req);
        }
        GroupIdsProto response = stub.createGroups(request.build());
        return response.getScorecardGroupIdsList();
    }

    public int updateGroup(Group group, String operator, long parentId, int order) {
        UpdateGroupRequest.Builder request = UpdateGroupRequest.newBuilder();
        request.setScorecardGroupId(group.getId());
        request.setScorecardId(parentId);
        if (group.getName() != null) {
            request.setName(group.getName());
        }
        request.setWeight(group.getWeight());
        request.setSort(order);
        if (operator != null) {
            request.setOperator(operator);
        }
        CountProto response = stub.updateGroup(request.build());
        return response.getCount();
    }

    public List<Long> getScorecardSectionIds(Long[] ids) {
        GroupIdsProto request = GroupIdsProto.newBuilder().addAllScorecardGroupIds(Arrays.asList(ids)).build();
        SectionIdsProto response = stub.getScorecardSectionIds(request);
        return response.getScorecardSectionIdsList();
    }

    public int deleteGroups(Long[] ids) {
        GroupIdsProto request = GroupIdsProto.newBuilder().addAllScorecardGroupIds(Arrays.asList(ids)).build();
        CountProto response = stub.deleteGroups(request);
        return response.getCount();
    }

    public GetGroupResponse getGroup(long id) {
        GroupIdProto request = GroupIdProto.newBuilder().setScorecardGroupId(id).build();
        GetGroupResponse response = stub.getGroup(request);
        if (!response.hasScorecardGroupId()) {
            return null;
        }
        return response;
    }

    public List<GetGroupResponse> getGroups(Long parentId) {
        ScorecardIdProto.Builder request = ScorecardIdProto.newBuilder();
        if (parentId != null) {
            request.setScorecardId(parentId);
        }
        GetGroupsResponse response = stub.getGroups(request.build());
        return response.getGroupsList();
    }

    private CreateGroupRequest buildCreateGroupRequest(Group group, int order, String operator, long parentId) {
        CreateGroupRequest.Builder request = CreateGroupRequest.newBuilder();
        request.setScorecardId(parentId);
        if (group.getName() != null) {
            request.setName(group.getName());
        }
        request.setWeight(group.getWeight());
        request.setSort(order);
        if (operator != null) {
            request.setOperator(operator);
        }
        return request.build();
    }
}
