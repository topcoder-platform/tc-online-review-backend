package com.topcoder.onlinereview.component.grpcclient.phase;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.google.protobuf.Timestamp;
import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.component.project.phase.Dependency;
import com.topcoder.onlinereview.component.project.phase.Phase;
import com.topcoder.onlinereview.component.project.phase.PhaseStatus;
import com.topcoder.onlinereview.component.project.phase.PhaseType;
import com.topcoder.onlinereview.grpc.phase.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class PhaseServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private PhaseServiceGrpc.PhaseServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = PhaseServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public List<Long> getProjectIds(Long[] projectIds) {
        ProjectIdsProto request = ProjectIdsProto.newBuilder().addAllProjectIds(Arrays.asList(projectIds)).build();
        ProjectIdsProto response = stub.getProjectIds(request);
        return response.getProjectIdsList();
    }

    public List<PhaseProto> getPhasesByProjectIds(Long[] projectIds) {
        ProjectIdsProto request = ProjectIdsProto.newBuilder().addAllProjectIds(Arrays.asList(projectIds)).build();
        GetPhasesResponse response = stub.getPhasesByProjectIds(request);
        return response.getPhasesList();
    }

    public List<PhaseIdProjectIdProto> getProjectIdsByPhaseIds(Long[] phaseIds) {
        PhaseIdsProto request = PhaseIdsProto.newBuilder().addAllPhaseIds(Arrays.asList(phaseIds)).build();
        GetProjectIdsByPhaseIdsResponse response = stub.getProjectIdsByPhaseIds(request);
        return response.getPhaseIdsList();
    }

    public List<PhaseCriteriaProto> getPhaseCriteriasByProjectIds(Long[] projectIds) {
        ProjectIdsProto request = ProjectIdsProto.newBuilder().addAllProjectIds(Arrays.asList(projectIds)).build();
        GetPhaseCriteriasResponse response = stub.getPhaseCriteriasByProjectIds(request);
        return response.getPhaseCriteriaList();
    }

    public List<PhaseCriteriaProto> getPhaseCriteriasByPhaseId(long phaseId) {
        PhaseIdProto request = PhaseIdProto.newBuilder().setPhaseId(phaseId).build();
        GetPhaseCriteriasResponse response = stub.getPhaseCriteriasByPhaseId(request);
        return response.getPhaseCriteriaList();
    }

    public List<PhaseDependencyProto> getPhaseDependenciesByProjectId(Long[] projectIds) {
        ProjectIdsProto request = ProjectIdsProto.newBuilder().addAllProjectIds(Arrays.asList(projectIds)).build();
        GetPhaseDependenciesResponse response = stub.getPhaseDependenciesByProjectId(request);
        return response.getPhaseDependenciesList();
    }

    public List<PhaseDependencyProto> getPhaseDependenciesByDependentPhaseId(long phaseId) {
        PhaseIdProto request = PhaseIdProto.newBuilder().setPhaseId(phaseId).build();
        GetPhaseDependenciesResponse response = stub.getPhaseDependenciesByDependentPhaseId(request);
        return response.getPhaseDependenciesList();
    }

    public List<PhaseType> getAllPhaseTypes() {
        GetPhaseTypesResponse response = stub.getAllPhaseTypes(null);
        return response.getPhaseTypesList().stream().map(p -> new PhaseType(p.getPhaseTypeId(), p.getPhaseTypeName()))
                .collect(Collectors.toList());
    }

    public List<PhaseStatus> getAllPhaseStatuses() {
        GetPhaseStatusesResponse response = stub.getAllPhaseStatuses(null);
        return response.getPhaseStatusesList().stream()
                .map(p -> new PhaseStatus(p.getPhaseStatusId(), p.getPhaseStatusName()))
                .collect(Collectors.toList());
    }

    public List<PhaseCriteriaTypeProto> getAllPhaseCriteriaTypes() {
        GetPhaseCriteriaTypesResponse response = stub.getAllPhaseCriteriaTypes(null);
        return response.getPhaseCriteriaTypesList();
    }

    public void createPhase(Phase phase, String operator) {
        CreatePhaseRequest.Builder builder = CreatePhaseRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        if (phase != null) {
            builder.setPhase(buildPhase(phase));
        }
        PhaseProto response = stub.createPhase(builder.build());
        if (phase != null) {
            phase.setId(response.getProjectPhaseId());
            phase.setModifyDate(new Date(response.getModifyDate().getSeconds() * 1000));
        }
    }

    public int createPhaseCriteria(long phaseId, long typeId, String value, String operator) {
        CreatePhaseCriteriaRequest.Builder builder = CreatePhaseCriteriaRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }

        PhaseCriteriaProto.Builder pBuilder = PhaseCriteriaProto.newBuilder().setProjectPhaseId(phaseId)
                .setPhaseCriteriaTypeId(typeId);
        if (value != null) {
            pBuilder.setParameter(value);
        }
        builder.setPhaseCriteria(pBuilder.build());

        CountProto response = stub.createPhaseCriteria(builder.build());
        return response.getCount();
    }

    public int createPhaseDependency(Dependency dep, String operator) {
        CreatePhaseDependencyRequest.Builder builder = CreatePhaseDependencyRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        PhaseDependencyProto d = PhaseDependencyProto.newBuilder().setDependencyPhaseId(dep.getDependency().getId())
                .setDependentPhaseId(dep.getDependent().getId()).setDependencyStart(dep.isDependencyStart())
                .setDependentStart(dep.isDependentStart()).setLagTime(dep.getLagTime()).build();
        builder.setPhaseDependency(d);
        CountProto response = stub.createPhaseDependency(builder.build());
        return response.getCount();
    }

    public void updatePhase(List<Phase> phases, String operator) {
        UpdatePhaseRequest.Builder builder = UpdatePhaseRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        for (Phase phase : phases) {
            if (phase != null) {
                builder.addPhases(buildPhase(phase));
            }
        }
        UpdatePhaseResponse response = stub.updatePhase(builder.build());
        for (Phase phase : phases) {
            phase.setModifyDate(new Date(response.getModifyDate().getSeconds() * 1000));
        }
    }

    public int updatePhaseCriteria(long phaseId, long typeId, String value, String operator) {
        UpdatePhaseCriteriaRequest.Builder builder = UpdatePhaseCriteriaRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }

        PhaseCriteriaProto.Builder pBuilder = PhaseCriteriaProto.newBuilder().setProjectPhaseId(phaseId)
                .setPhaseCriteriaTypeId(typeId);
        if (value != null) {
            pBuilder.setParameter(value);
        }
        builder.setPhaseCriteria(pBuilder.build());

        CountProto response = stub.updatePhaseCriteria(builder.build());
        return response.getCount();
    }

    public int updatePhaseDependency(Dependency dep, String operator) {
        UpdatePhaseDependencyRequest.Builder builder = UpdatePhaseDependencyRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        PhaseDependencyProto d = PhaseDependencyProto.newBuilder().setDependencyPhaseId(dep.getDependency().getId())
                .setDependentPhaseId(dep.getDependent().getId()).setDependencyStart(dep.isDependencyStart())
                .setDependentStart(dep.isDependentStart()).setLagTime(dep.getLagTime()).build();
        builder.setPhaseDependency(d);
        CountProto response = stub.updatePhaseDependency(builder.build());
        return response.getCount();
    }

    public int deletePhases(List<Long> phaseIds) {
        PhaseIdsProto request = PhaseIdsProto.newBuilder().addAllPhaseIds(phaseIds).build();
        CountProto response = stub.deletePhases(request);
        return response.getCount();
    }

    public int deletePhaseDependencies(long dependantId, List<Long> dependencyIds) {
        DeletePhaseDependencyRequest request = DeletePhaseDependencyRequest.newBuilder()
                .setDependentPhaseId(dependantId).addAllDependencyPhaseIds(dependencyIds).build();
        CountProto response = stub.deletePhaseDependencies(request);
        return response.getCount();
    }

    public int deletePhaseCriterias(long phaseId, List<Long> criteriaIds) {
        DeletePhaseCriteriasRequest request = DeletePhaseCriteriasRequest.newBuilder()
                .setProjectPhaseId(phaseId).addAllPhaseCriteriaTypeIds(criteriaIds).build();
        CountProto response = stub.deletePhaseCriterias(request);
        return response.getCount();
    }

    public boolean checkIfDependencyExists(long dependency, long dependent) {
        DependencyExistenceRequest request = DependencyExistenceRequest.newBuilder().setDependencyPhaseId(dependency)
                .setDependentPhaseId(dependent).build();
        DependencyExistenceResponse response = stub.checkIfDependencyExists(request);
        return response.getExists();
    }

    private PhaseProto buildPhase(Phase phase) {
        PhaseProto.Builder pBuilder = PhaseProto.newBuilder().setProjectPhaseId(phase.getId())
                .setProjectId(phase.getProject().getId())
                .setPhaseTypeId(phase.getPhaseType().getId()).setPhaseStatusId(phase.getPhaseStatus().getId())
                .setDuration(phase.getLength());
        if (phase.getFixedStartDate() != null) {
            pBuilder.setFixedStartTime(
                    Timestamp.newBuilder().setSeconds(phase.getFixedStartDate().toInstant().getEpochSecond()));
        }
        if (phase.getScheduledStartDate() != null) {
            pBuilder.setScheduledStartTime(
                    Timestamp.newBuilder().setSeconds(phase.getScheduledStartDate().toInstant().getEpochSecond()));
        }
        if (phase.getScheduledEndDate() != null) {
            pBuilder.setScheduledEndTime(
                    Timestamp.newBuilder().setSeconds(phase.getScheduledEndDate().toInstant().getEpochSecond()));
        }
        if (phase.getActualStartDate() != null) {
            pBuilder.setActualStartTime(
                    Timestamp.newBuilder().setSeconds(phase.getActualStartDate().toInstant().getEpochSecond()));
        }
        if (phase.getActualEndDate() != null) {
            pBuilder.setActualEndTime(
                    Timestamp.newBuilder().setSeconds(phase.getActualEndDate().toInstant().getEpochSecond()));
        }
        return pBuilder.build();
    }
}
