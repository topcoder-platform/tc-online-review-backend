package com.topcoder.onlinereview.component.grpcclient.sync;

import java.util.ArrayList;
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

    public void saveProjectSync(Long projectId, boolean statusHasChanged, boolean categoryHasChanged,
            boolean externalRefIdHasChanged, boolean directProjectIdHasChanged, boolean phaseUpdated,
            boolean resourceUpdated, boolean prizeUpdated, boolean submissionUpdated) {
        SyncInput.Builder request = getSyncInput(projectId);
        if (statusHasChanged || categoryHasChanged || directProjectIdHasChanged) {
            List<String> changedColumnNames = new ArrayList<>();
            if (statusHasChanged) {
                changedColumnNames.add("project_status_id");
            }
            if (categoryHasChanged) {
                changedColumnNames.add("project_category_id");
            }
            if (directProjectIdHasChanged) {
                changedColumnNames.add("tc_direct_project_id");
            }
            request.addUpdatedTables(getProjectTable(changedColumnNames));
        }
        if (externalRefIdHasChanged) {
            request.addUpdatedTables(getProjectInfoTable(Arrays.asList(1L)));
        }
        if (phaseUpdated) {
            request.addUpdatedTables(getProjectPhaseTable(null));
            request.addUpdatedTables(getPhaseCriteriaTable(null));
        }
        if (resourceUpdated) {
            request.addUpdatedTables(getResourceTable(null));
            request.addUpdatedTables(getProjectPaymentTable(null));
        }
        if (prizeUpdated) {
            request.addUpdatedTables(getPrizeTable(null));
        }
        if (submissionUpdated) {
            request.addUpdatedTables(getSubmissionTable(null));
        }
        stub.syncLegacy(request.build());
    }

    public void advanceFailedScreeningSubmissionSync(Long projectId, Long submissionId, boolean postMortemDeleted) {
        if (postMortemDeleted) {
            SyncInput.Builder request = getSyncInput(projectId);
            request.addUpdatedTables(getProjectPhaseTable(null));
            stub.syncLegacy(request.build());
        }
    }

    public void advanceFailedCheckpointScreeningSubmissionSync(Long projectId, Long submissionId) {

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

    }

    public void SaveProjectPaymentsSync(Long projectId, List<Long> resourceIds) {
        SyncInput.Builder request = getSyncInput(projectId);
        request.addUpdatedTables(getProjectPaymentTable(null));
        stub.syncLegacy(request.build());
    }

    public void SaveReviewSync(Long projectId) {
        SyncInput.Builder request = getSyncInput(projectId);
        request.addUpdatedTables(getSubmissionTable(null));
        stub.syncLegacy(request.build());
    }

    private SyncInput.Builder getSyncInput(Long projectId) {
        return SyncInput.newBuilder().setProjectId(projectId.intValue());
    }

    private Table getProjectTable(List<String> columnNames) {
        Table.Builder table = Table.newBuilder().setTable("project").setPrimaryKey("COLUMNS");
        addProperties(table, columnNames);
        return table.build();
    }

    private Table getProjectInfoTable(List<Long> projectInfoTypeIds) {
        Table.Builder table = Table.newBuilder().setTable("project_info").setPrimaryKey("project_info_type_id");
        addValues(table, projectInfoTypeIds);
        return table.build();
    }

    private Table getPrizeTable(List<Long> prizeIds) {
        Table.Builder table = Table.newBuilder().setTable("prize").setPrimaryKey("prizeId");
        addValues(table, prizeIds);
        return table.build();
    }

    private Table getSubmissionTable(List<Long> submissionIds) {
        Table.Builder table = Table.newBuilder().setTable("submission").setPrimaryKey("submission_id");
        addValues(table, submissionIds);
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

    private Table getProjectPaymentTable(List<Long> projectPaymentIds) {
        Table.Builder table = Table.newBuilder().setTable("project_payment").setPrimaryKey("project_payment_id");
        addValues(table, projectPaymentIds);
        return table.build();
    }

    private void addValues(Table.Builder builder, List<Long> values) {
        if (values != null) {
            for (Long value : values)
                builder.addValue(value.toString());
        }
    }

    private void addProperties(Table.Builder builder, List<String> values) {
        if (values != null) {
            for (String value : values)
                builder.addValue(value.toString());
        }
    }
}
