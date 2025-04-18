package com.topcoder.onlinereview.component.grpcclient.userretrieval;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.grpc.userretrieval.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class UserRetrievalServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private UserRetrievalServiceGrpc.UserRetrievalServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = UserRetrievalServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public List<ExternalUserProto> getUsersByUserIds(List<Object> userIds) {
        GetUsersResponse response = stub.getUsersByUserIds(buildUserIdsRequest(userIds));
        return response.getExternalUsersList();
    }

    public List<ExternalUserProto> getUsersByHandles(List<Object> handles) {
        GetUsersResponse response = stub.getUsersByHandles(buildHandlesRequest(handles));
        return response.getExternalUsersList();
    }

    public List<ExternalUserProto> getUsersByLowerHandles(List<Object> handles) {
        GetUsersResponse response = stub.getUsersByLowerHandles(buildHandlesRequest(handles));
        return response.getExternalUsersList();
    }

    public List<ExternalUserProto> getUsersByName(List<Object> names) {
        GetUsersResponse response = stub.getUsersByName(buildNameRequest(names));
        return response.getExternalUsersList();
    }

    public List<EmailProto> getAlternativeEmailsByUserIds(List<Object> userIds) {
        GetAlternativeEmailsResponse response = stub.getAlternativeEmailsByUserIds(buildUserIdsRequest(userIds));
        return response.getEmailsList();
    }

    public List<EmailProto> getAlternativeEmailsByHandles(List<Object> handles) {
        GetAlternativeEmailsResponse response = stub.getAlternativeEmailsByHandles(buildHandlesRequest(handles));
        return response.getEmailsList();
    }

    public List<EmailProto> getAlternativeEmailsByLowerHandles(List<Object> handles) {
        GetAlternativeEmailsResponse response = stub.getAlternativeEmailsByLowerHandles(buildHandlesRequest(handles));
        return response.getEmailsList();
    }

    public List<EmailProto> getAlternativeEmailsByName(List<Object> names) {
        GetAlternativeEmailsResponse response = stub.getAlternativeEmailsByName(buildNameRequest(names));
        return response.getEmailsList();
    }

    public List<UserRatingProto> getUserRatingsByUserIds(List<Object> userIds) {
        GetUserRatingsResponse response = stub.getUserRatingsByUserIds(buildUserIdsRequest(userIds));
        return response.getUserRatingsList();
    }

    public List<UserRatingProto> getUserRatingsByHandles(List<Object> handles) {
        GetUserRatingsResponse response = stub.getUserRatingsByHandles(buildHandlesRequest(handles));
        return response.getUserRatingsList();
    }

    public List<UserRatingProto> getUserRatingsByLowerHandles(List<Object> handles) {
        GetUserRatingsResponse response = stub.getUserRatingsByLowerHandles(buildHandlesRequest(handles));
        return response.getUserRatingsList();
    }

    public List<UserRatingProto> getUserRatingsByName(List<Object> names) {
        GetUserRatingsResponse response = stub.getUserRatingsByName(buildNameRequest(names));
        return response.getUserRatingsList();
    }

    private UserIdsRequest buildUserIdsRequest(List<Object> userIds) {
        return UserIdsRequest.newBuilder().addAllIds(userIds.stream().map(x -> (Long) x).collect(Collectors.toList()))
                .build();
    }

    private HandlesRequest buildHandlesRequest(List<Object> handles) {
        return HandlesRequest.newBuilder()
                .addAllHandles(handles.stream().map(x -> x.toString()).collect(Collectors.toList())).build();
    }

    private NameRequest buildNameRequest(List<Object> names) {
        NameRequest.Builder request = NameRequest.newBuilder();
        if (names.size() > 0 && names.get(0) != null) {
            request.setFirstName(names.get(0).toString());
        }
        if (names.size() > 1 && names.get(1) != null) {
            request.setLastName(names.get(1).toString());
        }
        return request.build();
    }
}
