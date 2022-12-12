package com.topcoder.onlinereview.component.grpcclient.deliverable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import com.google.protobuf.Timestamp;
import com.topcoder.onlinereview.component.deliverable.Deliverable;
import com.topcoder.onlinereview.component.deliverable.late.LateDeliverable;
import com.topcoder.onlinereview.component.deliverable.late.LateDeliverableType;
import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.grpc.deliverable.proto.*;
import com.topcoder.onlinereview.component.search.filter.Filter;

@Service
@DependsOn({ "grpcChannelManager" })
public class DeliverableServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private DeliverableServiceGrpc.DeliverableServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = DeliverableServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public Deliverable[] loadDeliverablesWithoutSubmission(Long[] deliverableIds, Long[] resourceIds, Long[] phaseIds) {
        LoadDeliverablesWithoutSubmissionRequest loadDeliverablesWithoutSubmissionRequest = LoadDeliverablesWithoutSubmissionRequest
                .newBuilder().addAllDeliverableIds(Arrays.asList(deliverableIds))
                .addAllResourceIds(Arrays.asList(resourceIds)).addAllPhaseIds(Arrays.asList(phaseIds)).build();
        LoadDeliverablesWithoutSubmissionResponse loadDeliverablesWithoutSubmissionResponse = stub
                .loadDeliverablesWithoutSubmission(loadDeliverablesWithoutSubmissionRequest);
        List<DeliverableWithoutSubmissionProto> deliverableWithoutSubmissionList = loadDeliverablesWithoutSubmissionResponse
                .getDeliverablesWithoutSubmissionsList();
        Deliverable[] deliverables = new Deliverable[loadDeliverablesWithoutSubmissionResponse
                .getDeliverablesWithoutSubmissionsCount()];
        for (int i = 0; i < loadDeliverablesWithoutSubmissionResponse
                .getDeliverablesWithoutSubmissionsCount(); ++i) {
            DeliverableWithoutSubmissionProto dp = deliverableWithoutSubmissionList.get(i);
            Deliverable deliverable = new Deliverable(dp.getProjectId().getValue(), dp.getProjectPhaseId().getValue(),
                    dp.getResourceId().getValue(), null, dp.getRequired().getValue());
            deliverable.setId(dp.getDeliverableId().getValue());
            if (dp.hasCreateUser()) {
                deliverable.setCreationUser(dp.getCreateUser().getValue());
            }
            if (dp.hasCreateDate()) {
                deliverable.setCreationTimestamp(new Date(dp.getCreateDate().getSeconds() * 1000));
            }
            if (dp.hasModifyUser()) {
                deliverable.setModificationUser(dp.getModifyUser().getValue());
            }
            if (dp.hasModifyDate()) {
                deliverable.setModificationTimestamp(new Date(dp.getModifyDate().getSeconds() * 1000));
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
                .getDeliverablesWithSubmissionsList();
        Deliverable[] deliverables = new Deliverable[loadDeliverablesWithSubmissionResponse
                .getDeliverablesWithSubmissionsCount()];
        for (int i = 0; i < loadDeliverablesWithSubmissionResponse.getDeliverablesWithSubmissionsCount(); ++i) {
            DeliverableWithSubmissionProto dp = deliverableWithSubmissionList.get(i);
            Deliverable deliverable = new Deliverable(dp.getProjectId().getValue(), dp.getProjectPhaseId().getValue(),
                    dp.getResourceId().getValue(), dp.getSubmissionId().getValue(), dp.getRequired().getValue());
            deliverable.setId(dp.getDeliverableId().getValue());
            if (dp.hasCreateUser()) {
                deliverable.setCreationUser(dp.getCreateUser().getValue());
            }
            if (dp.hasCreateDate()) {
                deliverable.setCreationTimestamp(new Date(dp.getCreateDate().getSeconds() * 1000));
            }
            if (dp.hasModifyUser()) {
                deliverable.setModificationUser(dp.getModifyUser().getValue());
            }
            if (dp.hasModifyDate()) {
                deliverable.setModificationTimestamp(new Date(dp.getModifyDate().getSeconds() * 1000));
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
                    Timestamp.newBuilder().setSeconds(lateDeliverable.getDeadline().toInstant().getEpochSecond())
                            .build());
        }
        if (lateDeliverable.getCompensatedDeadline() != null) {
            rBuilder.setCompensatedDeadline(Timestamp.newBuilder()
                    .setSeconds(lateDeliverable.getCompensatedDeadline().toInstant().getEpochSecond()).build());
        }
        if (lateDeliverable.getCreateDate() != null) {
            rBuilder.setCreateDate(
                    Timestamp.newBuilder().setSeconds(lateDeliverable.getCreateDate().toInstant().getEpochSecond())
                            .build());
        }
        rBuilder.setForgiveInd(BoolValue.of(lateDeliverable.isForgiven()));
        if (lateDeliverable.getLastNotified() != null) {
            rBuilder.setLastNotified(Timestamp.newBuilder()
                    .setSeconds(lateDeliverable.getLastNotified().toInstant().getEpochSecond()).build());
        }
        if (lateDeliverable.getDelay() != null) {
            rBuilder.setDelay(Int64Value.of(lateDeliverable.getDelay()));
        }
        if (lateDeliverable.getExplanation() != null) {
            rBuilder.setExplanation(StringValue.of(lateDeliverable.getExplanation()));
        }
        if (lateDeliverable.getExplanationDate() != null) {
            rBuilder.setExplanationDate(Timestamp.newBuilder()
                    .setSeconds(lateDeliverable.getExplanationDate().toInstant().getEpochSecond()).build());
        }
        if (lateDeliverable.getResponse() != null) {
            rBuilder.setResponse(StringValue.of(lateDeliverable.getResponse()));
        }
        if (lateDeliverable.getResponseDate() != null) {
            rBuilder.setResponseDate(Timestamp.newBuilder()
                    .setSeconds(lateDeliverable.getResponseDate().toInstant().getEpochSecond()).build());
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
        if (response.getModifyDatesCount() > 0) {
            deliverable
                    .setCompletionDate(new Date(response.getModifyDates(0).getSeconds() * 1000));
        }

    }

    public void appealResponsesDeliverableCheck(Deliverable deliverable) {
        AppealResponsesDeliverableCheckRequest.Builder builder = AppealResponsesDeliverableCheckRequest.newBuilder();
        builder.setResourceId(Int64Value.of(deliverable.getResource()));
        if (deliverable.getSubmission() != null) {
            builder.setSubmissionId(Int64Value.of(deliverable.getSubmission()));
        }
        AppealResponsesDeliverableCheckResponse response = stub.appealResponsesDeliverableCheck(builder.build());
        if (response.getModifyDatesCount() == 0) {
            deliverable.setCompletionDate(new Date());
        } else {
            response.getModifyDatesList().stream().map(x -> new Date(x.getSeconds() * 1000))
                    .max(Comparator.comparing(x -> x)).ifPresent(d -> deliverable.setCompletionDate(d));
        }
    }

    public void committedReviewDeliverableCheck(Deliverable deliverable) {
        CommittedReviewDeliverableCheckRequest.Builder builder = CommittedReviewDeliverableCheckRequest.newBuilder();
        builder.setIsPerSubmission(BoolValue.of(deliverable.isPerSubmission()));
        builder.setResourceId(Int64Value.of(deliverable.getResource()));
        builder.setProjectPhaseId(Int64Value.of(deliverable.getPhase()));
        if (deliverable.getSubmission() != null) {
            builder.setSubmissionId(Int64Value.of(deliverable.getSubmission()));
        }
        CommittedReviewDeliverableCheckResponse response = stub.committedReviewDeliverableCheck(builder.build());
        if (response.getModifyDatesCount() > 0) {
            deliverable
                    .setCompletionDate(new Date(response.getModifyDates(0).getSeconds() * 1000));
        }
    }

    public void finalFixesDeliverableCheck(Deliverable deliverable) {
        FinalFixesDeliverableCheckRequest.Builder builder = FinalFixesDeliverableCheckRequest.newBuilder();
        builder.setResourceId(Int64Value.of(deliverable.getResource()));
        builder.setProjectPhaseId(Int64Value.of(deliverable.getPhase()));
        FinalFixesDeliverableCheckResponse response = stub.finalFixesDeliverableCheck(builder.build());
        if (response.getModifyDatesCount() > 0) {
            deliverable
                    .setCompletionDate(new Date(response.getModifyDates(0).getSeconds() * 1000));
        }
    }

    public void finalReviewDeliverableCheck(Deliverable deliverable) {
        FinalReviewDeliverableCheckRequest.Builder builder = FinalReviewDeliverableCheckRequest.newBuilder();
        builder.setResourceId(Int64Value.of(deliverable.getResource()));
        FinalReviewDeliverableCheckResponse response = stub.finalReviewDeliverableCheck(builder.build());
        if (response.getResultsCount() > 0) {
            FinalReviewDeliverableCheckResponseMessage result = response.getResults(0);
            if (result.hasModifyDate()) {
                deliverable
                        .setCompletionDate(new Date(result.getModifyDate().getSeconds() * 1000));
            }
            if (result.hasSubmissionId()) {
                deliverable.setSubmission(result.getSubmissionId().getValue());
            }
        }
    }

    public void individualReviewDeliverableCheck(Deliverable deliverable) {
        IndividualReviewDeliverableCheckRequest.Builder builder = IndividualReviewDeliverableCheckRequest.newBuilder();
        builder.setResourceId(Int64Value.of(deliverable.getResource()));
        IndividualReviewDeliverableCheckResponse response = stub.individualReviewDeliverableCheck(builder.build());
        if (response.getResultsCount() > 0) {
            IndividualReviewDeliverableCheckResponseMessage result = response.getResults(0);
            if (result.hasModifyDate()) {
                deliverable
                        .setCompletionDate(new Date(result.getModifyDate().getSeconds() * 1000));
            }
            if (result.hasSubmissionId()) {
                deliverable.setSubmission(result.getSubmissionId().getValue());
            }
        }
    }

    public void specificationSubmissionDeliverableCheck(Deliverable deliverable) {
        SpecificationSubmissionDeliverableCheckRequest.Builder builder = SpecificationSubmissionDeliverableCheckRequest
                .newBuilder();
        builder.setResourceId(Int64Value.of(deliverable.getResource()));
        builder.setProjectPhaseId(Int64Value.of(deliverable.getPhase()));
        SpecificationSubmissionDeliverableCheckResponse response = stub
                .specificationSubmissionDeliverableCheck(builder.build());
        if (response.getModifyDatesCount() > 0) {
            deliverable
                    .setCompletionDate(new Date(response.getModifyDates(0).getSeconds() * 1000));
        }
    }

    public void submissionDeliverableCheck(Deliverable deliverable) {
        SubmissionDeliverableCheckRequest.Builder builder = SubmissionDeliverableCheckRequest.newBuilder();
        builder.setResourceId(Int64Value.of(deliverable.getResource()));
        builder.setProjectPhaseId(Int64Value.of(deliverable.getPhase()));
        SubmissionDeliverableCheckResponse response = stub.submissionDeliverableCheck(builder.build());
        if (response.getModifyDatesCount() > 0) {
            deliverable
                    .setCompletionDate(new Date(response.getModifyDates(0).getSeconds() * 1000));
        }
    }

    public void submitterCommentDeliverableCheck(Deliverable deliverable) {
        SubmitterCommentDeliverableCheckRequest.Builder builder = SubmitterCommentDeliverableCheckRequest.newBuilder();
        builder.setResourceId(Int64Value.of(deliverable.getResource()));
        SubmitterCommentDeliverableCheckResponse response = stub.submitterCommentDeliverableCheck(builder.build());
        if (response.getResultsCount() > 0) {
            SubmitterCommentDeliverableCheckResponseMessage result = response.getResults(0);
            if (result.hasModifyDate()) {
                deliverable
                        .setCompletionDate(new Date(result.getModifyDate().getSeconds() * 1000));
            }
            if (result.hasSubmissionId()) {
                deliverable.setSubmission(result.getSubmissionId().getValue());
            }
        }
    }

    public void testCasesDeliverableCheck(Deliverable deliverable) {
        TestCasesDeliverableCheckRequest.Builder builder = TestCasesDeliverableCheckRequest.newBuilder();
        builder.setResourceId(Int64Value.of(deliverable.getResource()));
        builder.setProjectPhaseId(Int64Value.of(deliverable.getPhase()));
        TestCasesDeliverableCheckResponse response = stub.testCasesDeliverableCheck(builder.build());
        if (response.getModifyDatesCount() > 0) {
            deliverable
                    .setCompletionDate(new Date(response.getModifyDates(0).getSeconds() * 1000));
        }
    }

    public Long[][] searchDeliverables(Filter filter) {
        FilterProto request = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        SearchDeliverablesResponse response = stub.searchDeliverables(request);
        List<SearchDeliverablesProto> deliverableList = response.getDeliverablesList();
        Long[][] res = new Long[3][response.getDeliverablesCount()];
        for (int i = 0; i < response.getDeliverablesCount(); i++) {
            SearchDeliverablesProto dp = deliverableList.get(i);
            if (dp.hasDeliverableId()) {
                res[0][i] = deliverableList.get(i).getDeliverableId().getValue();
            }
            if (dp.hasResourceId()) {
                res[1][i] = deliverableList.get(i).getResourceId().getValue();
            }
            if (dp.hasProjectPhaseId()) {
                res[2][i] = deliverableList.get(i).getProjectPhaseId().getValue();
            }
        }
        return res;
    }

    public Long[][] searchDeliverablesWithSubmission(Filter filter) {
        FilterProto request = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        SearchDeliverablesWithSubmissionResponse response = stub.searchDeliverablesWithSubmission(request);
        List<SearchDeliverablesWithSubmissionProto> deliverableList = response.getDeliverablesList();
        Long[][] res = new Long[3][response.getDeliverablesCount()];
        for (int i = 0; i < response.getDeliverablesCount(); i++) {
            SearchDeliverablesWithSubmissionProto dp = deliverableList.get(i);
            if (dp.hasDeliverableId()) {
                res[0][i] = deliverableList.get(i).getDeliverableId().getValue();
            }
            if (dp.hasResourceId()) {
                res[1][i] = deliverableList.get(i).getResourceId().getValue();
            }
            if (dp.hasProjectPhaseId()) {
                res[2][i] = deliverableList.get(i).getProjectPhaseId().getValue();
            }
            if (dp.hasSubmissionId()) {
                res[2][i] = deliverableList.get(i).getSubmissionId().getValue();
            }
        }
        return res;
    }

    public List<LateDeliverable> searchLateDeliverablesNonRestricted(Filter filter) {
        FilterProto request = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        return loadLateDeliverablesFromSearch(stub.searchLateDeliverablesNonRestricted(request));
    }

    public List<LateDeliverable> searchLateDeliverablesRestricted(Filter filter) {
        FilterProto request = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        return loadLateDeliverablesFromSearch(stub.searchLateDeliverablesRestricted(request));
    }

    private List<LateDeliverable> loadLateDeliverablesFromSearch(SearchLateDeliverablesResponse response) {
        List<LateDeliverable> result = new ArrayList<LateDeliverable>();
        Map<Long, LateDeliverableType> lateDeliverableTypes = new HashMap<Long, LateDeliverableType>();
        for (LateDeliverablesProto ldp : response.getLateDeliverablesList()) {
            LateDeliverable lateDeliverable = new LateDeliverable();
            if (ldp.hasLateDeliverableId()) {
                lateDeliverable.setId(ldp.getLateDeliverableId().getValue());
            }
            if (ldp.hasProjectId()) {
                lateDeliverable.setProjectId(ldp.getProjectId().getValue());
            }
            if (ldp.hasProjectPhaseId()) {
                lateDeliverable.setProjectPhaseId(ldp.getProjectPhaseId().getValue());
            }
            if (ldp.hasResourceId()) {
                lateDeliverable.setResourceId(ldp.getResourceId().getValue());
            }
            if (ldp.hasDeliverableId()) {
                lateDeliverable.setDeliverableId(ldp.getDeliverableId().getValue());
            }
            if (ldp.hasDeadline()) {
                lateDeliverable.setDeadline(new Date(ldp.getDeadline().getSeconds() * 1000));
            }
            if (ldp.hasCompensatedDeadline()) {
                lateDeliverable.setCompensatedDeadline(new Date(ldp.getCompensatedDeadline().getSeconds() * 1000));
            }
            if (ldp.hasCreateDate()) {
                lateDeliverable.setCreateDate(new Date(ldp.getCreateDate().getSeconds() * 1000));
            }
            if (ldp.hasForgiveInd()) {
                lateDeliverable.setForgiven(ldp.getForgiveInd().getValue());
            }
            if (ldp.hasLastNotified()) {
                lateDeliverable.setLastNotified(new Date(ldp.getLastNotified().getSeconds() * 1000));
            }
            if (ldp.hasDelay()) {
                lateDeliverable.setDelay(ldp.getDelay().getValue());
            }
            if (ldp.hasExplanation()) {
                lateDeliverable.setExplanation(ldp.getExplanation().getValue());
            }
            if (ldp.hasExplanationDate()) {
                lateDeliverable.setExplanationDate(new Date(ldp.getExplanationDate().getSeconds() * 1000));
            }
            if (ldp.hasResponse()) {
                lateDeliverable.setResponse(ldp.getResponse().getValue());
            }
            if (ldp.hasResponseUser()) {
                lateDeliverable.setResponseUser(ldp.getResponseUser().getValue());
            }
            if (ldp.hasResponseDate()) {
                lateDeliverable.setResponseDate(new Date(ldp.getResponseDate().getSeconds() * 1000));
            }
            if (ldp.hasLateDeliverableTypeId()) {
                long lateDeliverableTypeId = ldp.getLateDeliverableTypeId().getValue();
                LateDeliverableType lateDeliverableType = lateDeliverableTypes.get(lateDeliverableTypeId);
                if (lateDeliverableType == null) {
                    lateDeliverableType = new LateDeliverableType();
                    lateDeliverableType.setId(lateDeliverableTypeId);
                    if (ldp.hasName()) {
                        lateDeliverableType.setName(ldp.getName().getValue());
                    }
                    if (ldp.hasDescription()) {
                        lateDeliverableType.setDescription(ldp.getDescription().getValue());
                    }
                    lateDeliverableTypes.put(lateDeliverableTypeId, lateDeliverableType);
                }
                lateDeliverable.setType(lateDeliverableType);
            }
            result.add(lateDeliverable);
        }
        return result;
    }
}
