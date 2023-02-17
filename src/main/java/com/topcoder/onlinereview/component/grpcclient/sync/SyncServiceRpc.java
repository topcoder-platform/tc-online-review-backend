package com.topcoder.onlinereview.component.grpcclient.sync;

import java.util.Arrays;
import java.util.List;

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

    public void advanceFailedScreeningSubmissionSync(Long projectId, Long submissionId, boolean postMortemDeleted) {
        SyncInput.Builder request = getSyncInput(projectId);
        request.addUpdatedTables(getSubmissionTable(Arrays.asList(submissionId)));
        if (postMortemDeleted) {
            request.addUpdatedTables(getResourceInfoTable(null));
            request.addUpdatedTables(getResourceSubmissionTable(null));
            request.addUpdatedTables(getResourceTable(null));
            request.addUpdatedTables(getProjectPhaseTable(null));
            request.addUpdatedTables(getPhaseCriteriaTable(null));
        }
        stub.syncLegacy(request.build());
    }

    public void advanceFailedCheckpointScreeningSubmissionSync(Long projectId, Long submissionId) {
        SyncInput.Builder request = getSyncInput(projectId);
        request.addUpdatedTables(getSubmissionTable(Arrays.asList(submissionId)));
        stub.syncLegacy(request.build());
    }

    public void manageProjectSync(Long projectId, boolean isPhasesUpdated, List<Long> resourceIds) {
        SyncInput.Builder request = getSyncInput(projectId);
        if (isPhasesUpdated) {
            request.addUpdatedTables(getProjectPhaseTable(null));
        }
        if (resourceIds != null && !resourceIds.isEmpty()) {
            request.addUpdatedTables(getResourceTable(resourceIds));
        }
        stub.syncLegacy(request.build());
    }

    public void saveReviewPaymentsSync(Long projectId) {
        SyncInput.Builder request = getSyncInput(projectId);
        request.addUpdatedTables(getProjectPaymentTable(null));
        request.addUpdatedTables(getProjectPaymentAdjustmentTable(projectId));
        stub.syncLegacy(request.build());
    }

    public void SaveProjectPaymentsSync(Long projectId, List<Long> resourceIds) {
        SyncInput.Builder request = getSyncInput(projectId);
        request.addUpdatedTables(getProjectPaymentTable(null));
        request.addUpdatedTables(getResourceInfoTable(resourceIds));
        request.addUpdatedTables(getProjectResultTable(projectId));
        stub.syncLegacy(request.build());
    }

    public void SaveReviewSync(Long projectId) {
        SyncInput.Builder request = getSyncInput(projectId);
        request.addUpdatedTables(getSubmissionTable(null));
        request.addUpdatedTables(getProjectPaymentTable(null));
        request.addUpdatedTables(getProjectResultTable(projectId));
        stub.syncLegacy(request.build());
    }

    private SyncInput.Builder getSyncInput(Long projectId) {
        return SyncInput.newBuilder().setProjectId(projectId.intValue());
    }

    private Table getSubmissionTable(List<Long> submissionIds) {
        Table.Builder table = Table.newBuilder().setTable("submission").setPrimaryKey("submission_id");
        addValues(table, submissionIds);
        return table.build();
    }

    private Table getResourceInfoTable(List<Long> resourceIds) {
        Table.Builder table = Table.newBuilder().setTable("resource_info").setPrimaryKey("resource_id");
        addValues(table, resourceIds);
        return table.build();
    }

    private Table getResourceSubmissionTable(List<Long> resourceIds) {
        Table.Builder table = Table.newBuilder().setTable("resource_submission").setPrimaryKey("resource_id");
        addValues(table, resourceIds);
        return table.build();
    }

    private Table getResourceTable(List<Long> resourceIds) {
        Table.Builder table = Table.newBuilder().setTable("resource").setPrimaryKey("resource_id");
        addValues(table, resourceIds);
        return table.build();
    }

    private Table getProjectPhaseTable(List<Long> phaseIds) {
        Table.Builder table = Table.newBuilder().setTable("project_phase").setPrimaryKey("project_phase_id");
        addValues(table, phaseIds);
        return table.build();
    }

    private Table getPhaseCriteriaTable(List<Long> phaseIds) {
        Table.Builder table = Table.newBuilder().setTable("phase_criteria").setPrimaryKey("project_phase_id");
        addValues(table, phaseIds);
        return table.build();
    }

    private Table getProjectPaymentAdjustmentTable(Long projectId) {
        Table.Builder table = Table.newBuilder().setTable("project_payment_adjustment").setPrimaryKey("project_id");
        if (projectId != null) {
            table.addValue(projectId.toString());
        }
        return table.build();
    }

    private Table getProjectPaymentTable(List<Long> projectPaymentIds) {
        Table.Builder table = Table.newBuilder().setTable("project_payment").setPrimaryKey("project_payment_id");
        addValues(table, projectPaymentIds);
        return table.build();
    }

    private Table getProjectResultTable(Long projectId) {
        Table.Builder table = Table.newBuilder().setTable("project_result").setPrimaryKey("project_id");
        if (projectId != null) {
            table.addValue(projectId.toString());
        }
        return table.build();
    }

    private void addValues(Table.Builder builder, List<Long> values) {
        if (values != null) {
            for (Long value : values)
                builder.addValue(value.toString());
        }
    }
}
