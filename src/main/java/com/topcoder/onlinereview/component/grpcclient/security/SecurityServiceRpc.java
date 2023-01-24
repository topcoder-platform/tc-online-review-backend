package com.topcoder.onlinereview.component.grpcclient.security;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.grpc.security.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class SecurityServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private SecurityServiceGrpc.SecurityServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = SecurityServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public List<GroupMemberProto> getGroupMembers(long userId) {
        GetGroupMembersRequest request = GetGroupMembersRequest.newBuilder().setUserId(userId).build();
        GetGroupMembersResponse response = stub.getGroupMembers(request);
        return response.getGroupMembersList();
    }

    public List<GroupDirectProjectProto> getDirectProjects(long groupId) {
        GetDirectProjectsRequest request = GetDirectProjectsRequest.newBuilder().setGroupId(groupId).build();
        GetDirectProjectsResponse response = stub.getDirectProjects(request);
        return response.getDirectProjectsList();
    }

    public List<BillingAccountProto> getBillingAccounts(long groupId) {
        GetBillingAccountsRequest request = GetBillingAccountsRequest.newBuilder().setGroupId(groupId).build();
        GetBillingAccountsResponse response = stub.getBillingAccounts(request);
        return response.getBillingAccountsList();
    }

    public boolean isCustomerAdministrator(long userId, long clientId) {
        IsCustomerAdministratorRequest.Builder request = IsCustomerAdministratorRequest.newBuilder().setUserId(userId);
        if (clientId > 0) {
            request.setClientId(clientId);
        }
        IsCustomerAdministratorResponse response = stub.isCustomerAdministrator(request.build());
        return response.getIsCustomerAdministrator();
    }

    public boolean isAdministrator(long userId) {
        IsAdministratorRequest request = IsAdministratorRequest.newBuilder().setUserId(userId).build();
        IsAdministratorResponse response = stub.isAdministrator(request);
        return response.getIsAdministrator();
    }

    public List<Long> getGroupIdsOfFullPermissionsUser(long userId) {
        GetGroupIdsOfFullPermissionsUserRequest request = GetGroupIdsOfFullPermissionsUserRequest.newBuilder()
                .setUserId(userId).build();
        GetGroupIdsOfFullPermissionsUserResponse response = stub.getGroupIdsOfFullPermissionsUser(request);
        return response.getGroupIdsList();
    }

    public GetBillingAccountResponse getBillingAccount(long id) {
        GetBillingAccountRequest request = GetBillingAccountRequest.newBuilder().setId(id).build();
        GetBillingAccountResponse response = stub.getBillingAccount(request);
        if (response.hasProjectId()) {
            return response;
        }
        return null;
    }

    public List<BillingAccountForClientProto> getBillingAccountsForClient() {
        GetBillingAccountsForClientResponse response = stub.getBillingAccountsForClient(null);
        return response.getBillingAccountsList();
    }

    public String getProjectName(long projectId) {
        GetProjectNameRequest request = GetProjectNameRequest.newBuilder().setProjectId(projectId).build();
        GetProjectNameResponse response = stub.getProjectName(request);
        if (response.hasProjectName()) {
            return response.getProjectName();
        }
        return null;
    }

    public List<ProjectByClientIdProto> getProjectsByClientId() {
        GetProjectsByClientIdResponse response = stub.getProjectsByClientId(null);
        return response.getProjectsList();
    }

    public List<ProjectByBillingAccountProto> getProjectsByBillingAccounts() {
        GetProjectsByBillingAccountsResponse response = stub.getProjectsByBillingAccounts(null);
        return response.getProjectsList();
    }

    public List<UserRoleProto> getUserRoles(long userId) {
        GetUserRolesRequest request = GetUserRolesRequest.newBuilder().setUserId(userId).build();
        GetUserRolesResponse response = stub.getUserRoles(request);
        return response.getUserRolesList();
    }

    public boolean isCloudSpokesUser(String handle) {
        IsCloudSpokesUserRequest.Builder request = IsCloudSpokesUserRequest.newBuilder();
        if (handle != null) {
            request.setHandle(handle);
        }
        IsCloudSpokesUserResponse response = stub.isCloudSpokesUser(request.build());
        return response.getIsCloudSpokesUser();
    }
}
