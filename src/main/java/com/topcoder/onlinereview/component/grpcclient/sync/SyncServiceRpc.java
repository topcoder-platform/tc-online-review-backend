package com.topcoder.onlinereview.component.grpcclient.sync;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.grpc.sync.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class SyncServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private LegacySyncGrpc.LegacySyncBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = LegacySyncGrpc.newBlockingStub(grpcChannelManager.getChannelSync());
    }

    public void saveProjectSync(Long projectId) {
        SyncInput.Builder request = SyncInput.newBuilder();
        request.setProjectId(projectId.intValue());
        stub.syncLegacy(request.build());
    }

    public void advanceFailedScreeningSubmissionSync(Long projectId, long submissionId, boolean postMortemDeleted) {
        SyncInput.Builder request = SyncInput.newBuilder();
        request.setProjectId(projectId.intValue());
        request.addUpdatedTables(getSubmissionTable(submissionId));
        if (postMortemDeleted) {
            request.addUpdatedTables(getResourceInfoTable(null));
            request.addUpdatedTables(getResourceSubmissionTable(null));
            request.addUpdatedTables(getResourceTable(null));
            request.addUpdatedTables(getProjectPhaseTable(null));
            request.addUpdatedTables(getPhaseCriteriaTable(null));
        }
        stub.syncLegacy(request.build());
    }

    private Table getSubmissionTable(Long submissionId) {
        Table.Builder table = Table.newBuilder().setTable("submission").setPrimaryKey("submission_id");
        if (submissionId != null) {
            table.setValue(String.valueOf(submissionId));
        }
        return table.build();
    }

    private Table getResourceInfoTable(Long resourceId) {
        Table.Builder table = Table.newBuilder().setTable("resource_info").setPrimaryKey("resource_id");
        if (resourceId != null) {
            table.setValue(String.valueOf(resourceId));
        }
        return table.build();
    }

    private Table getResourceSubmissionTable(Long resourceId) {
        Table.Builder table = Table.newBuilder().setTable("resource_submission").setPrimaryKey("resource_id");
        if (resourceId != null) {
            table.setValue(String.valueOf(resourceId));
        }
        return table.build();
    }

    private Table getResourceTable(Long resourceId) {
        Table.Builder table = Table.newBuilder().setTable("resource").setPrimaryKey("resource_id");
        if (resourceId != null) {
            table.setValue(String.valueOf(resourceId));
        }
        return table.build();
    }

    private Table getProjectPhaseTable(Long phaseId) {
        Table.Builder table = Table.newBuilder().setTable("project_phase").setPrimaryKey("project_phase_id");
        if (phaseId != null) {
            table.setValue(String.valueOf(phaseId));
        }
        return table.build();
    }

    private Table getPhaseCriteriaTable(Long phaseId) {
        Table.Builder table = Table.newBuilder().setTable("phase_criteria").setPrimaryKey("project_phase_id");
        if (phaseId != null) {
            table.setValue(String.valueOf(phaseId));
        }
        return table.build();
    }
}
