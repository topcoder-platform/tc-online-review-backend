package com.topcoder.onlinereview.component.grpcclient.deliverable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import com.google.protobuf.Timestamp;
import com.topcoder.onlinereview.component.deliverable.Deliverable;
import com.topcoder.onlinereview.component.deliverable.late.LateDeliverable;
import com.topcoder.onlinereview.component.deliverable.late.LateDeliverableType;
import com.topcoder.onlinereview.component.grpcclient.deliverable.protos.*;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Component
public class DeliverableServiceRpc {

    @GrpcClient("DeliverableServiceRpc")
    private DeliverableServiceGrpc.DeliverableServiceBlockingStub stub;

    public Deliverable[] loadDeliverablesWithoutSubmission(Long[] deliverableIds, Long[] resourceIds, Long[] phaseIds) {
        LoadDeliverablesWithoutSubmissionRequest loadDeliverablesWithoutSubmissionRequest = LoadDeliverablesWithoutSubmissionRequest
                .newBuilder().addAllDeliverableIds(Arrays.asList(deliverableIds))
                .addAllResourceIds(Arrays.asList(resourceIds)).addAllPhaseIds(Arrays.asList(phaseIds)).build();
        LoadDeliverablesWithoutSubmissionResponse loadDeliverablesWithoutSubmissionResponse = stub
                .loadDeliverablesWithoutSubmission(loadDeliverablesWithoutSubmissionRequest);
        List<DeliverableWithoutSubmissionProto> deliverableWithoutSubmissionList = loadDeliverablesWithoutSubmissionResponse
                .getDeliverablesWithoutSubmissionList();
        Deliverable[] deliverables = new Deliverable[loadDeliverablesWithoutSubmissionResponse
                .getDeliverablesWithoutSubmissionCount()];
        for (int i = 0; i < loadDeliverablesWithoutSubmissionResponse
                .getDeliverablesWithoutSubmissionCount(); ++i) {
            DeliverableWithoutSubmissionProto dp = deliverableWithoutSubmissionList.get(i);
            Deliverable deliverable = new Deliverable(dp.getProjectId().getValue(), dp.getProjectPhaseId().getValue(),
                    dp.getResourceId().getValue(), null, dp.getRequired().getValue());
            deliverable.setId(dp.getDeliverableId().getValue());
            if (dp.hasCreateUser()) {
                deliverable.setCreationUser(dp.getCreateUser().getValue());
            }
            if (dp.hasCreateDate()) {
                deliverable.setCreationTimestamp(new Date(dp.getCreateDate().getSeconds()));
            }
            if (dp.hasModifyUser()) {
                deliverable.setModificationUser(dp.getModifyUser().getValue());
            }
            if (dp.hasModifyDate()) {
                deliverable.setModificationTimestamp(new Date(dp.getModifyDate().getSeconds()));
            }
            if (dp.hasName()) {
                deliverable.setName(dp.getName().getValue());
            }
            if (dp.hasDescription()) {
                deliverable.setDescription(dp.getDescription().getValue());
            }
            deliverables[i] = deliverable;
        }
        return deliverables;
    }

    public Deliverable[] loadDeliverablesWithSubmission(Long[] deliverableIds, Long[] resourceIds, Long[] phaseIds,
            Long[] submissionIds) {
        LoadDeliverablesWithSubmissionRequest loadDeliverablesWithSubmissionRequest = LoadDeliverablesWithSubmissionRequest
                .newBuilder().addAllDeliverableIds(Arrays.asList(deliverableIds))
                .addAllResourceIds(Arrays.asList(resourceIds)).addAllPhaseIds(Arrays.asList(phaseIds))
                .addAllSubmissionIds(Arrays.asList(submissionIds)).build();
        LoadDeliverablesWithSubmissionResponse loadDeliverablesWithSubmissionResponse = stub
                .loadDeliverablesWithSubmission(loadDeliverablesWithSubmissionRequest);
        List<DeliverableWithSubmissionProto> deliverableWithSubmissionList = loadDeliverablesWithSubmissionResponse
                .getDeliverablesWithSubmissionList();
        Deliverable[] deliverables = new Deliverable[loadDeliverablesWithSubmissionResponse
                .getDeliverablesWithSubmissionCount()];
        for (int i = 0; i < loadDeliverablesWithSubmissionResponse.getDeliverablesWithSubmissionCount(); ++i) {
            DeliverableWithSubmissionProto dp = deliverableWithSubmissionList.get(i);
            Deliverable deliverable = new Deliverable(dp.getProjectId().getValue(), dp.getProjectPhaseId().getValue(),
                    dp.getResourceId().getValue(), dp.getSubmissionId().getValue(), dp.getRequired().getValue());
            deliverable.setId(dp.getDeliverableId().getValue());
            if (dp.hasCreateUser()) {
                deliverable.setCreationUser(dp.getCreateUser().getValue());
            }
            if (dp.hasCreateDate()) {
                deliverable.setCreationTimestamp(new Date(dp.getCreateDate().getSeconds()));
            }
            if (dp.hasModifyUser()) {
                deliverable.setModificationUser(dp.getModifyUser().getValue());
            }
            if (dp.hasModifyDate()) {
                deliverable.setModificationTimestamp(new Date(dp.getModifyDate().getSeconds()));
            }
            if (dp.hasName()) {
                deliverable.setName(dp.getName().getValue());
            }
            if (dp.hasDescription()) {
                deliverable.setDescription(dp.getDescription().getValue());
            }
            deliverables[i] = deliverable;
        }
        return deliverables;
    }

    public int updateLateDeliverable(LateDeliverable lateDeliverable) {
        UpdateLateDeliverableRequest.Builder rBuilder = UpdateLateDeliverableRequest.newBuilder();
        rBuilder.setLateDeliverableId(Int64Value.of(lateDeliverable.getId()));
        rBuilder.setProjectPhaseId(Int64Value.of(lateDeliverable.getProjectPhaseId()));
        rBuilder.setResourceId(Int64Value.of(lateDeliverable.getResourceId()));
        rBuilder.setDeliverableId(Int64Value.of(lateDeliverable.getDeliverableId()));
        if (lateDeliverable.getDeadline() != null) {
            rBuilder.setDeadline(
                    Timestamp.newBuilder().setSeconds(lateDeliverable.getDeadline().toInstant().getEpochSecond()));
        }
        if (lateDeliverable.getCompensatedDeadline() != null) {
            rBuilder.setCompensatedDeadline(Timestamp.newBuilder()
                    .setSeconds(lateDeliverable.getCompensatedDeadline().toInstant().getEpochSecond()));
        }
        if (lateDeliverable.getCreateDate() != null) {
            rBuilder.setCreateDate(
                    Timestamp.newBuilder().setSeconds(lateDeliverable.getCreateDate().toInstant().getEpochSecond()));
        }
        rBuilder.setForgiveInd(BoolValue.of(lateDeliverable.isForgiven()));
        if (lateDeliverable.getLastNotified() != null) {
            rBuilder.setLastNotified(Timestamp.newBuilder()
                    .setSeconds(lateDeliverable.getLastNotified().toInstant().getEpochSecond()));
        }
        if (lateDeliverable.getDelay() != null) {
            rBuilder.setDelay(Int64Value.of(lateDeliverable.getDelay()));
        }
        if (lateDeliverable.getExplanation() != null) {
            rBuilder.setExplanation(StringValue.of(lateDeliverable.getExplanation()));
        }
        if (lateDeliverable.getExplanationDate() != null) {
            rBuilder.setExplanationDate(Timestamp.newBuilder()
                    .setSeconds(lateDeliverable.getExplanationDate().toInstant().getEpochSecond()));
        }
        if (lateDeliverable.getResponse() != null) {
            rBuilder.setResponse(StringValue.of(lateDeliverable.getResponse()));
        }
        if (lateDeliverable.getResponseDate() != null) {
            rBuilder.setResponseDate(Timestamp.newBuilder()
                    .setSeconds(lateDeliverable.getResponseDate().toInstant().getEpochSecond()));
        }
        if (lateDeliverable.getType() != null) {
            rBuilder.setLateDeliverableTypeId(Int64Value.of(lateDeliverable.getType().getId()));
        }
        return stub.updateLateDeliverable(rBuilder.build()).getCount();
    }

    public List<LateDeliverableType> getLateDeliverableTypes() {
        LateDeliverableTypesResponse response = stub.getLateDeliverableTypes(null);
        List<LateDeliverableType> result = new ArrayList<LateDeliverableType>();
        for (LateDeliverableTypeProto ldtp : response.getLateDeliverableTypesList()) {
            LateDeliverableType lateDeliverableType = new LateDeliverableType();
            if (ldtp.hasLateDeliverableTypeId()) {
                lateDeliverableType.setId(ldtp.getLateDeliverableTypeId().getValue());
            }
            if (ldtp.hasName()) {
                lateDeliverableType.setName(ldtp.getName().getValue());
            }
            if (ldtp.hasDescription()) {
                lateDeliverableType.setDescription(ldtp.getDescription().getValue());
            }
            result.add(lateDeliverableType);
        }
        return result;
    }

    public void aggregationDeliverableCheck(Deliverable deliverable) {
        AggregationDeliverableCheckRequest.Builder builder = AggregationDeliverableCheckRequest.newBuilder();
        builder.setResourceId(Int64Value.of(deliverable.getResource()));
        AggregationDeliverableCheckResponse response = stub.aggregationDeliverableCheck(builder.build());
        if (response.getModifyDateCount() > 0 && response.getModifyDate(0).isInitialized()) {
            deliverable
                    .setCompletionDate(new Date(response.getModifyDate(0).getSeconds()));
        }

    }
}
