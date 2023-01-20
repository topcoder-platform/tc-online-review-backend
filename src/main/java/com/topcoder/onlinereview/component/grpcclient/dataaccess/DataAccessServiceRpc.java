package com.topcoder.onlinereview.component.grpcclient.dataaccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.google.protobuf.Timestamp;
import com.topcoder.onlinereview.component.dataaccess.ClientProject;
import com.topcoder.onlinereview.component.dataaccess.CockpitProject;
import com.topcoder.onlinereview.component.dataaccess.Document;
import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.grpc.dataaccess.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class DataAccessServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private DataAccessServiceGrpc.DataAccessServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = DataAccessServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public Long getComponentVersionInfo(long componentId, long versionNumber) {
        GetComponentVersionInfoRequest request = GetComponentVersionInfoRequest.newBuilder().setComponentId(componentId)
                .setVersionNumber(versionNumber).build();
        GetComponentVersionInfoResponse response = stub.getComponentVersionInfo(request);
        if (response.hasVersionId()) {
            return response.getVersionId();
        }
        return null;
    }

    public List<DocumentProto> getDocuments(long componentVersionId) {
        GetDocumentsRequest request = GetDocumentsRequest.newBuilder().setComponentVersionId(componentVersionId)
                .build();
        GetDocumentsResponse response = stub.getDocuments(request);
        return response.getDocumentsList();
    }

    public long addDocument(long componentVersionId, Document document) {
        AddDocumentRequest.Builder request = AddDocumentRequest.newBuilder();
        request.setComponentVersionId(componentVersionId);
        request.setDocumentTypeId(document.getType());
        if (document.getName() != null) {
            request.setDocumentName(document.getName());
        }
        if (document.getURL() != null) {
            request.setUrl(document.getURL());
        }
        AddDocumentResponse response = stub.addDocument(request.build());
        return response.getDocumentId();
    }

    public int updateDocument(long componentVersionId, Document document) {
        UpdateDocumentRequest.Builder request = UpdateDocumentRequest.newBuilder();
        request.setDocumentId(document.getId());
        request.setComponentVersionId(componentVersionId);
        request.setDocumentTypeId(document.getType());
        if (document.getName() != null) {
            request.setDocumentName(document.getName());
        }
        if (document.getURL() != null) {
            request.setUrl(document.getURL());
        }
        UpdateDocumentResponse response = stub.updateDocument(request.build());
        return response.getUpdatedCount();
    }

    public List<DeliverableProto> getDeliverablesList() {
        GetDeliverablesListResponse response = stub.getDeliverablesList(null);
        return response.getDeliverablesList();
    }

    public boolean isCockpitProjectUser(long projectId, long userId) {
        IsCockpitProjectUserRequest request = IsCockpitProjectUserRequest.newBuilder().setProjectId(projectId)
                .setUserId(userId).build();
        IsCockpitProjectUserResponse response = stub.isCockpitProjectUser(request);
        return response.getIsCockpitProjectUser();
    }

    public CockpitProject getCockpitProject(long cockpitProjectId) {
        GetCockpitProjectRequest request = GetCockpitProjectRequest.newBuilder().setCockpitProjectId(cockpitProjectId)
                .build();
        GetCockpitProjectResponse response = stub.getCockpitProject(request);
        if (!response.hasTcDirectProjectId()) {
            return null;
        }
        CockpitProject project = new CockpitProject();
        if (response.hasTcDirectProjectId()) {
            project.setId(response.getTcDirectProjectId());
        }
        if (response.hasTcDirectProjectName()) {
            project.setName(response.getTcDirectProjectName());
        }
        return project;
    }

    public List<CockpitProject> getAllCockpitProjects() {
        GetAllCockpitProjectsResponse response = stub.getAllCockpitProjects(null);
        List<CockpitProject> result = new ArrayList<CockpitProject>();
        for (GetCockpitProjectResponse c : response.getCockpitProjectsList()) {
            CockpitProject project = new CockpitProject();
            if (c.hasTcDirectProjectId()) {
                project.setId(c.getTcDirectProjectId());
            }
            if (c.hasTcDirectProjectName()) {
                project.setName(c.getTcDirectProjectName());
            }
            result.add(project);
        }
        return result;
    }

    public List<CockpitProject> getCockpitProjectsForUser(long userId) {
        GetCockpitProjectsForUserRequest request = GetCockpitProjectsForUserRequest.newBuilder().setUserId(userId)
                .build();
        GetCockpitProjectsForUserResponse response = stub.getCockpitProjectsForUser(request);
        List<CockpitProject> result = new ArrayList<CockpitProject>();
        for (GetCockpitProjectResponse c : response.getCockpitProjectsList()) {
            CockpitProject project = new CockpitProject();
            if (c.hasTcDirectProjectId()) {
                project.setId(c.getTcDirectProjectId());
            }
            if (c.hasTcDirectProjectName()) {
                project.setName(c.getTcDirectProjectName());
            }
            result.add(project);
        }
        return result;
    }

    public ClientProject getClientProject(long clientProjectId) {
        GetClientProjectRequest request = GetClientProjectRequest.newBuilder().setClientProjectId(clientProjectId)
                .build();
        GetClientProjectResponse response = stub.getClientProject(request);
        if (!response.hasProjectId()) {
            return null;
        }
        ClientProject project = new ClientProject();
        if (response.hasProjectId()) {
            project.setId(response.getProjectId());
        }
        if (response.hasProjectName()) {
            project.setName(response.getProjectName());
        }
        return project;
    }

    public List<ClientProject> getAllClientProjects() {
        GetAllClientProjectsResponse response = stub.getAllClientProjects(null);
        List<ClientProject> result = new ArrayList<ClientProject>();
        for (GetClientProjectResponse c : response.getClientProjectsList()) {
            ClientProject project = new ClientProject();
            if (c.hasProjectId()) {
                project.setId(c.getProjectId());
            }
            if (c.hasProjectName()) {
                project.setName(c.getProjectName());
            }
            result.add(project);
        }
        return result;
    }

    public List<ClientProject> getClientProjectsForUser(long userId) {
        GetClientProjectsForUserRequest request = GetClientProjectsForUserRequest.newBuilder().setUserId(userId)
                .build();
        GetClientProjectsForUserResponse response = stub.getClientProjectsForUser(request);
        List<ClientProject> result = new ArrayList<ClientProject>();
        for (GetClientProjectResponse c : response.getClientProjectsList()) {
            ClientProject project = new ClientProject();
            if (c.hasProjectId()) {
                project.setId(c.getProjectId());
            }
            if (c.hasProjectName()) {
                project.setName(c.getProjectName());
            }
            result.add(project);
        }
        return result;
    }
}
