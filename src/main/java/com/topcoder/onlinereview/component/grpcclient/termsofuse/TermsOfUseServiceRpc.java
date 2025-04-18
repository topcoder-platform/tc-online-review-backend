package com.topcoder.onlinereview.component.grpcclient.termsofuse;

import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.component.termsofuse.TermsOfUse;
import com.topcoder.onlinereview.component.termsofuse.TermsOfUseType;
import com.topcoder.onlinereview.grpc.termsofuse.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class TermsOfUseServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private TermsOfUseServiceGrpc.TermsOfUseServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = TermsOfUseServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public int createProjectRoleTermsOfUse(int projectId, int resourceRoleId, long termsOfUseId, int sortOrder,
            int groupIndex) {
        CreateProjectRoleTermsOfUseRequest request = CreateProjectRoleTermsOfUseRequest.newBuilder()
                .setProjectId(projectId).setResourceRoleId(resourceRoleId).setTermsOfUseId(termsOfUseId)
                .setSortOrder(sortOrder).setGroupInd(groupIndex).build();
        CountProto response = stub.createProjectRoleTermsOfUse(request);
        return response.getCount();
    }

    public int deleteProjectRoleTermsOfUse(int projectId, int resourceRoleId, long termsOfUseId, int sortOrder,
            int groupIndex) {
        DeleteProjectRoleTermsOfUseRequest request = DeleteProjectRoleTermsOfUseRequest.newBuilder()
                .setProjectId(projectId).setResourceRoleId(resourceRoleId).setTermsOfUseId(termsOfUseId)
                .setGroupInd(groupIndex).build();
        CountProto response = stub.deleteProjectRoleTermsOfUse(request);
        return response.getCount();
    }

    public List<ProjectRoleTermsOfUseProto> getProjectRoleTermsOfUse(int projectId, int resourceRoleId) {
        GetProjectRoleTermsOfUseRequest request = GetProjectRoleTermsOfUseRequest.newBuilder()
                .setProjectId(projectId).setResourceRoleId(resourceRoleId).build();
        GetProjectRoleTermsOfUseResponse response = stub.getProjectRoleTermsOfUse(request);
        return response.getProjectRoleTermsOfUsesList();
    }

    public int deleteAllProjectRoleTermsOfUse(int projectId) {
        ProjectIdProto request = ProjectIdProto.newBuilder().setProjectId(projectId).build();
        CountProto response = stub.deleteAllProjectRoleTermsOfUse(request);
        return response.getCount();
    }

    public long createTermsOfUse(TermsOfUse termsOfUse, String termsText) {
        CreateTermsOfUseRequest.Builder request = CreateTermsOfUseRequest.newBuilder();
        if (termsText != null) {
            request.setTermsText(termsText);
        }
        request.setTermsOfUseTypeId(termsOfUse.getTermsOfUseTypeId());
        if (termsOfUse.getTitle() != null) {
            request.setTitle(termsOfUse.getTitle());
        }
        if (termsOfUse.getUrl() != null) {
            request.setUrl(termsOfUse.getUrl());
        }
        if (termsOfUse.getAgreeabilityType() != null) {
            request.setTermsOfUseAgreeabilityTypeId(termsOfUse.getAgreeabilityType().getTermsOfUseAgreeabilityTypeId());
        }
        TermsOfUseIdProto response = stub.createTermsOfUse(request.build());
        return response.getTermsOfUseId();
    }

    public int updateTermsOfUse(TermsOfUse termsOfUse) {
        UpdateTermsOfUseRequest.Builder request = UpdateTermsOfUseRequest.newBuilder();
        request.setTermsOfUseId(termsOfUse.getTermsOfUseId());
        request.setTermsOfUseTypeId(termsOfUse.getTermsOfUseTypeId());
        if (termsOfUse.getTitle() != null) {
            request.setTitle(termsOfUse.getTitle());
        }
        if (termsOfUse.getUrl() != null) {
            request.setUrl(termsOfUse.getUrl());
        }
        if (termsOfUse.getAgreeabilityType() != null) {
            request.setTermsOfUseAgreeabilityTypeId(termsOfUse.getAgreeabilityType().getTermsOfUseAgreeabilityTypeId());
        }
        CountProto response = stub.updateTermsOfUse(request.build());
        return response.getCount();
    }

    public GetTermsOfUseResponse getTermsOfUse(long termsOfUseId) {
        TermsOfUseIdProto request = TermsOfUseIdProto.newBuilder().setTermsOfUseId(termsOfUseId).build();
        GetTermsOfUseResponse response = stub.getTermsOfUse(request);
        if (response.hasTermsOfUseId()) {
            return response;
        }
        return null;
    }

    public int deleteTermsOfUse(long termsOfUseId) {
        TermsOfUseIdProto request = TermsOfUseIdProto.newBuilder().setTermsOfUseId(termsOfUseId).build();
        CountProto response = stub.deleteTermsOfUse(request);
        return response.getCount();
    }

    public List<GetTermsOfUseResponse> getTermsOfUseByTypeId(int termsOfUseTypeId) {
        TermsOfUseTypeIdProto request = TermsOfUseTypeIdProto.newBuilder().setTermsOfUseTypeId(termsOfUseTypeId)
                .build();
        GetTermsOfUseByTypeIdResponse response = stub.getTermsOfUseByTypeId(request);
        return response.getTermsOfUsesList();
    }

    public List<GetTermsOfUseResponse> getAllTermsOfUse() {
        GetAllTermsOfUseResponse response = stub.getAllTermsOfUse(null);
        return response.getTermsOfUsesList();
    }

    public TermsOfUseType getTermsOfUseType(int termsOfUseTypeId) {
        TermsOfUseTypeIdProto request = TermsOfUseTypeIdProto.newBuilder().setTermsOfUseTypeId(termsOfUseTypeId)
                .build();
        TermsOfUseTypeProto response = stub.getTermsOfUseType(request);
        if (!response.hasTermsOfUseTypeId()) {
            return null;
        }
        TermsOfUseType termsType = new TermsOfUseType();
        termsType.setTermsOfUseTypeId(termsOfUseTypeId);
        if (response.hasTermsOfUseTypeDesc()) {
            termsType.setDescription(response.getTermsOfUseTypeDesc());
        }
        return termsType;
    }

    public int updateTermsOfUseType(TermsOfUseType termsType) {
        TermsOfUseTypeProto.Builder request = TermsOfUseTypeProto.newBuilder()
                .setTermsOfUseTypeId(termsType.getTermsOfUseTypeId());
        if (termsType.getDescription() != null) {
            request.setTermsOfUseTypeDesc(termsType.getDescription());
        }
        CountProto response = stub.updateTermsOfUseType(request.build());
        return response.getCount();
    }

    public String getTermsOfUseText(long termsOfUseId) {
        TermsOfUseIdProto request = TermsOfUseIdProto.newBuilder().setTermsOfUseId(termsOfUseId)
                .build();
        GetTermsOfUseTextResponse response = stub.getTermsOfUseText(request);
        if (!response.hasTermsText()) {
            return null;
        }
        return response.getTermsText();
    }

    public int setTermsOfUseText(long termsOfUseId, String text) {
        SetTermsOfUseTextRequest.Builder request = SetTermsOfUseTextRequest.newBuilder().setTermsOfUseId(termsOfUseId);
        if (text != null) {
            request.setTermsText(text);
        }
        CountProto response = stub.setTermsOfUseText(request.build());
        return response.getCount();
    }

    public int createDependencyRelationship(long dependentTermsOfUseId, long dependencyTermsOfUseId) {
        CreateDependencyRelationshipRequest request = CreateDependencyRelationshipRequest.newBuilder()
                .setDependentTermsOfUseId(dependentTermsOfUseId).setDependencyTermsOfUseId(dependencyTermsOfUseId)
                .build();
        CountProto response = stub.createDependencyRelationship(request);
        return response.getCount();
    }

    public List<GetTermsOfUseResponse> getDependencyTermsOfUse(long dependentTermsOfUseId) {
        DependentTermsOfUseIdProto request = DependentTermsOfUseIdProto.newBuilder()
                .setDependentTermsOfUseId(dependentTermsOfUseId).build();
        GetDependencyTermsOfUseResponse response = stub.getDependencyTermsOfUse(request);
        return response.getTermsOfUsesList();
    }

    public List<GetTermsOfUseResponse> getDependentTermsOfUse(long dependencyTermsOfUseId) {
        DependencyTermsOfUseIdProto request = DependencyTermsOfUseIdProto.newBuilder()
                .setDependencyTermsOfUseId(dependencyTermsOfUseId).build();
        GetDependentTermsOfUseResponse response = stub.getDependentTermsOfUse(request);
        return response.getTermsOfUsesList();
    }

    public int deleteDependencyRelationship(long dependentTermsOfUseId, long dependencyTermsOfUseId) {
        DeleteDependencyRelationshipRequest request = DeleteDependencyRelationshipRequest.newBuilder()
                .setDependentTermsOfUseId(dependentTermsOfUseId).setDependencyTermsOfUseId(dependencyTermsOfUseId)
                .build();
        CountProto response = stub.deleteDependencyRelationship(request);
        return response.getCount();
    }

    public int deleteAllDependencyRelationshipsForDependent(long dependentTermsOfUseId) {
        DependentTermsOfUseIdProto request = DependentTermsOfUseIdProto.newBuilder()
                .setDependentTermsOfUseId(dependentTermsOfUseId).build();
        CountProto response = stub.deleteAllDependencyRelationshipsForDependent(request);
        return response.getCount();
    }

    public int deleteAllDependencyRelationshipsForDependency(long dependencyTermsOfUseId) {
        DependencyTermsOfUseIdProto request = DependencyTermsOfUseIdProto.newBuilder()
                .setDependencyTermsOfUseId(dependencyTermsOfUseId).build();
        CountProto response = stub.deleteAllDependencyRelationshipsForDependency(request);
        return response.getCount();
    }

    public List<Long> getDependencyTermsOfUseIds(List<Long> dependentTermsOfUseIds) {
        GetDependencyTermsOfUseIdsRequest request = GetDependencyTermsOfUseIdsRequest.newBuilder()
                .addAllDependentTermsOfUseIds(dependentTermsOfUseIds).build();
        GetDependencyTermsOfUseIdsResponse response = stub.getDependencyTermsOfUseIds(request);
        return response.getDependencyTermsOfUseIdsList();
    }

    public int createUserTermsOfUse(long userId, long termsOfUseId) {
        CreateUserTermsOfUseRequest request = CreateUserTermsOfUseRequest.newBuilder().setUserId(userId)
                .setTermsOfUseId(termsOfUseId).build();
        CountProto response = stub.createUserTermsOfUse(request);
        return response.getCount();
    }

    public int deleteUserTermsOfUse(long userId, long termsOfUseId) {
        DeleteUserTermsOfUseRequest request = DeleteUserTermsOfUseRequest.newBuilder().setUserId(userId)
                .setTermsOfUseId(termsOfUseId).build();
        CountProto response = stub.deleteUserTermsOfUse(request);
        return response.getCount();
    }

    public List<GetTermsOfUseResponse> getTermsOfUseByUserId(long userId) {
        UserIdProto request = UserIdProto.newBuilder().setUserId(userId).build();
        GetTermsOfUseByUserIdResponse response = stub.getTermsOfUseByUserId(request);
        return response.getTermsOfUsesList();
    }

    public List<Long> getUsersByTermsOfUseId(long termsOfUseId) {
        TermsOfUseIdProto request = TermsOfUseIdProto.newBuilder().setTermsOfUseId(termsOfUseId).build();
        GetUsersByTermsOfUseIdResponse response = stub.getUsersByTermsOfUseId(request);
        return response.getUserIdsList();
    }

    public boolean isTermsOfUseExists(long userId, long termsOfUseId) {
        TermsOfUseExistsRequest request = TermsOfUseExistsRequest.newBuilder().setUserId(userId)
                .setTermsOfUseId(termsOfUseId).build();
        ExistsProto response = stub.isTermsOfUseExists(request);
        return response.getExists();
    }

    public boolean isTermsOfUseBanExists(long userId, long termsOfUseId) {
        TermsOfUseExistsRequest request = TermsOfUseExistsRequest.newBuilder().setUserId(userId)
                .setTermsOfUseId(termsOfUseId).build();
        ExistsProto response = stub.isTermsOfUseBanExists(request);
        return response.getExists();
    }
}
