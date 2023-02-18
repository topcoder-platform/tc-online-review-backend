package com.topcoder.onlinereview.component.grpcclient.webcommon;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.grpc.webcommon.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class WebCommonServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private WebCommonServiceGrpc.WebCommonServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = WebCommonServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public GetUserPasswordResponse getUserPassword(long userId) {
        GetUserPasswordRequest request = GetUserPasswordRequest.newBuilder().setUserId(userId).build();
        return stub.getUserPassword(request);
    }

    public GetUserTimezoneResponse getUserTimezone(long userId) {
        GetUserTimezoneRequest request = GetUserTimezoneRequest.newBuilder().setUserId(userId).build();
        return stub.getUserTimezone(request);
    }

    public GetMemberCountResponse getMemberCount() {
        return stub.getMemberCount(null);
    }

    public GetMemberImageResponse getMemberImage(long userId) {
        GetMemberImageRequest request = GetMemberImageRequest.newBuilder().setUserId(userId).build();
        return stub.getMemberImage(request);
    }

    public GetCoderAllRatingsResponse getCoderAllRatings(long coderId) {
        GetCoderAllRatingsRequest request = GetCoderAllRatingsRequest.newBuilder().setCoderId(coderId).build();
        return stub.getCoderAllRatings(request);
    }

    public DoStartTagResponse doStartTag(String command, Hashtable params) {
        DoStartTagRequest.Builder request = DoStartTagRequest.newBuilder();
        if (command != null) {
            request.setCommand(command);
        }
        if (params != null) {
            Enumeration e = params.keys();
            while (e.hasMoreElements()) {
                Object key = e.nextElement();
                ParameterProto.Builder parameter = ParameterProto.newBuilder();
                if (key != null) {
                    parameter.setKey(key.toString());
                }
                if (params.get(key) != null) {
                    parameter.setValue(params.get(key).toString());
                }
                request.addParameters(parameter.build());
            }
        }
        return stub.doStartTag(request.build());
    }

    public List<DataTypeMappingProto> getDataTypeMappings() {
        GetDataTypeMappingsResponse response = stub.getDataTypeMappings(null);
        return response.getDataTypeMappingsList();
    }

    public List<DataTypeProto> getDataTypes() {
        GetDataTypesResponse response = stub.getDataTypes(null);
        return response.getDataTypesList();
    }
}
