package com.topcoder.onlinereview.component.grpcclient.project;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.google.protobuf.Timestamp;
import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.component.project.management.FileType;
import com.topcoder.onlinereview.component.project.management.Prize;
import com.topcoder.onlinereview.component.project.management.PrizeType;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import com.topcoder.onlinereview.component.project.management.ProjectStudioSpecification;
import com.topcoder.onlinereview.component.project.management.ProjectType;
import com.topcoder.onlinereview.grpc.project.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class ProjectServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private ProjectServiceGrpc.ProjectServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = ProjectServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public Project[] getProjects(Long[] ids) {
        GetProjectsRequest request = GetProjectsRequest.newBuilder().addAllProjectIds(Arrays.asList(ids)).build();
        GetProjectsResponse response = stub.getProjects(request);
        Project[] projects = new Project[response.getProjectsCount()];
        for (int i = 0; i < response.getProjectsCount(); i++) {
            ProjectProto p = response.getProjects(i);
            ProjectStatus status = new ProjectStatus(p.getProjectStatusId(), p.getStatusName());
            ProjectType type = new ProjectType(p.getProjectTypeId(), p.getTypeName());
            ProjectCategory category = new ProjectCategory(p.getProjectCategoryId(), p.getCategoryName(), type);
            if (p.hasDescription()) {
                category.setDescription(p.getDescription());
            }
            Project project = new Project(p.getProjectId(), category, status);
            if (p.hasCreateUser()) {
                project.setCreationUser(p.getCreateUser());
            }
            if (p.hasCreateDate()) {
                project.setCreationTimestamp(new Date(p.getCreateDate().getSeconds() * 1000));
            }
            if (p.hasModifyUser()) {
                project.setModificationUser(p.getModifyUser());
            }
            if (p.hasModifyDate()) {
                project.setModificationTimestamp(new Date(p.getModifyDate().getSeconds() * 1000));
            }
            project.setTcDirectProjectId(p.getDirectProjectId());
            if (p.hasTcDirectProjectName()) {
                project.setTcDirectProjectName(p.getTcDirectProjectName());
            }
            projects[i] = project;
        }
        return projects;
    }

    public List<Long> getProjectIdsByDirectId(long directId) {
        IdProto request = IdProto.newBuilder().setId(directId).build();
        GetProjectIdsByDirectIdResponse response = stub.getProjectIdsByDirectId(request);
        return response.getProjectIdsList();
    }

    public int createProject(Long projectId, Project project, String operator) {
        CreateProjectRequest.Builder builder = CreateProjectRequest.newBuilder();
        if (projectId != null) {
            builder.setProjectId(projectId);
        }
        builder.setProjectStatusId(project.getProjectStatus().getId());
        builder.setProjectCategoryId(project.getProjectCategory().getId());
        if (operator != null) {
            builder.setCreateUser(operator);
            builder.setModifyUser(operator);
        }
        if (project.getTcDirectProjectId() != null) {
            builder.setDirectProjectId(project.getTcDirectProjectId());
        }
        CountProto response = stub.createProject(builder.build());
        return response.getCount();
    }

    public int updateProject(Project project, String operator, Date modifyDate) {
        UpdateProjectRequest.Builder builder = UpdateProjectRequest.newBuilder();
        builder.setProjectId(project.getId());
        builder.setProjectStatusId(project.getProjectStatus().getId());
        builder.setProjectCategoryId(project.getProjectCategory().getId());
        if (operator != null) {
            builder.setModifyUser(operator);
        }
        if (modifyDate != null) {
            builder.setModifyDate(Timestamp.newBuilder().setSeconds(modifyDate.toInstant().getEpochSecond()));
        }
        if (project.getTcDirectProjectId() != null && project.getTcDirectProjectId() != 0) {
            builder.setDirectProjectId(project.getTcDirectProjectId());
        }
        CountProto response = stub.updateProject(builder.build());
        return response.getCount();
    }

    public Date getProjectCreateDate(Long projectId) {
        ProjectIdProto.Builder builder = ProjectIdProto.newBuilder();
        if (projectId != null) {
            builder.setProjectId(projectId);
        }
        CreateDateProto response = stub.getProjectCreateDate(builder.build());
        return new Date(response.getCreateDate().getSeconds() * 1000);
    }

    public Map<Long, String> getProjectPropertyIdValue(long projectId) {
        IdProto request = IdProto.newBuilder().setId(projectId).build();
        GetProjectPropertyIdValueResponse response = stub.getProjectPropertyIdValue(request);
        return response.getProjectPropertiesList().stream()
                .collect(Collectors.toMap(ProjectPropertyIdValue::getProjectInfoTypeId,
                        ProjectPropertyIdValue::getValue));
    }

    public List<ProjectPropertyProto> getProjectsProperties(Long[] ids) {
        GetProjectsPropertiesRequest request = GetProjectsPropertiesRequest.newBuilder()
                .addAllProjectIds(Arrays.asList(ids)).build();
        GetProjectsPropertiesResponse response = stub.getProjectsProperties(request);
        return response.getPropertiesList();
    }

    public int createProjectProperty(Long projectId, Long key, String value, String operator) {
        CreateProjectPropertyRequest.Builder builder = CreateProjectPropertyRequest.newBuilder();
        if (projectId != null) {
            builder.setProjectId(projectId);
        }
        if (key != null) {
            builder.setProjectInfoTypeId(key);
        }
        if (value != null) {
            builder.setValue(value);
        }
        if (operator != null) {
            builder.setCreateUser(operator);
            builder.setModifyUser(operator);
        }
        CountProto response = stub.createProjectProperty(builder.build());
        return response.getCount();
    }

    public int updateProjectProperty(Long projectId, Long key, String value, String operator) {
        UpdateProjectPropertyRequest.Builder builder = UpdateProjectPropertyRequest.newBuilder();
        if (projectId != null) {
            builder.setProjectId(projectId);
        }
        if (key != null) {
            builder.setProjectInfoTypeId(key);
        }
        if (value != null) {
            builder.setValue(value);
        }
        if (operator != null) {
            builder.setModifyUser(operator);
        }
        CountProto response = stub.updateProjectProperty(builder.build());
        return response.getCount();
    }

    public int deleteProjectProperty(Long projectId, Set<Long> typeIds) {
        DeleteProjectPropertyRequest.Builder builder = DeleteProjectPropertyRequest.newBuilder();
        if (projectId != null) {
            builder.setProjectId(projectId);
        }
        if (typeIds != null && typeIds.size() > 0) {
            builder.addAllProjectInfoTypeIds(typeIds);
        }
        CountProto response = stub.deleteProjectProperty(builder.build());
        return response.getCount();
    }

    public int auditProjectInfo(Long projectId, int auditType, long projectInfoTypeId, String value, String operator) {
        AuditProjectInfoRequest.Builder builder = AuditProjectInfoRequest.newBuilder();
        if (projectId != null) {
            builder.setProjectId(projectId);
        }
        builder.setAuditType(auditType);
        builder.setProjectInfoTypeId(projectInfoTypeId);
        if (value != null) {
            builder.setValue(value);
        }
        if (operator != null) {
            builder.setActionUserId(operator);
        }
        CountProto response = stub.auditProjectInfo(builder.build());
        return response.getCount();
    }

    public int auditProject(Long auditId, Long projectId, String reason, String operator) {
        AuditProjectRequest.Builder builder = AuditProjectRequest.newBuilder();
        if (auditId != null) {
            builder.setProjectAuditId(auditId);
        }
        if (projectId != null) {
            builder.setProjectId(projectId);
        }
        if (reason != null) {
            builder.setUpdateReason(reason);
        }
        if (operator != null) {
            builder.setCreateUser(operator);
            builder.setModifyUser(operator);
        }
        CountProto response = stub.auditProject(builder.build());
        return response.getCount();
    }

    public FileType[] getProjectFileTypes(long projectId) {
        ProjectIdProto request = ProjectIdProto.newBuilder().setProjectId(projectId).build();
        GetProjectFileTypesResponse response = stub.getProjectFileTypes(request);
        FileType[] fileTypes = new FileType[response.getFileTypesCount()];
        for (int i = 0; i < response.getFileTypesCount(); i++) {
            FileTypeProto f = response.getFileTypes(i);
            FileType fileType = new FileType();
            fileType.setId(f.getFileTypeId());
            if (f.hasDescription()) {
                fileType.setDescription(f.getDescription());
            }
            fileType.setSort(f.getSort());
            fileType.setImageFile(f.getImageFile());
            if (f.hasExtension()) {
                fileType.setExtension(f.getExtension());
            }
            fileType.setBundledFile(f.getBundledFile());
            fileTypes[i] = fileType;
        }
        return fileTypes;
    }

    public int createProjectFileType(long projectId, long fileTypeId) {
        CreateProjectFileTypeRequest request = CreateProjectFileTypeRequest.newBuilder().setProjectId(projectId)
                .setFileTypeId(fileTypeId).build();
        CountProto response = stub.createProjectFileType(request);
        return response.getCount();
    }

    public int deleteProjectFileType(long projectId) {
        DeleteProjectFileTypeRequest request = DeleteProjectFileTypeRequest.newBuilder().setProjectId(projectId)
                .build();
        CountProto response = stub.deleteProjectFileType(request);
        return response.getCount();
    }

    public int createFileType(FileType fileType) {
        CreateFileTypeRequest.Builder builder = CreateFileTypeRequest.newBuilder();
        builder.setFileTypeId(fileType.getId());
        if (fileType.getDescription() != null) {
            builder.setDescription(fileType.getDescription());
        }
        builder.setSort(fileType.getSort());
        builder.setImageFile(fileType.isImageFile());
        if (fileType.getExtension() != null) {
            builder.setExtension(fileType.getExtension());
        }
        builder.setBundledFile(fileType.isBundledFile());
        if (fileType.getCreationUser() != null) {
            builder.setCreateUser(fileType.getCreationUser());
        }
        if (fileType.getModificationUser() != null) {
            builder.setModifyUser(fileType.getModificationUser());
        }
        if (fileType.getCreationTimestamp() != null) {
            builder.setCreateDate(
                    Timestamp.newBuilder().setSeconds(fileType.getCreationTimestamp().toInstant().getEpochSecond()));
        }
        if (fileType.getModificationTimestamp() != null) {
            builder.setModifyDate(
                    Timestamp.newBuilder()
                            .setSeconds(fileType.getModificationTimestamp().toInstant().getEpochSecond()));
        }
        CountProto response = stub.createFileType(builder.build());
        return response.getCount();
    }

    public int updateFileType(FileType fileType) {
        UpdateFileTypeRequest.Builder builder = UpdateFileTypeRequest.newBuilder();
        builder.setFileTypeId(fileType.getId());
        if (fileType.getDescription() != null) {
            builder.setDescription(fileType.getDescription());
        }
        builder.setSort(fileType.getSort());
        builder.setImageFile(fileType.isImageFile());
        if (fileType.getExtension() != null) {
            builder.setExtension(fileType.getExtension());
        }
        builder.setBundledFile(fileType.isBundledFile());
        if (fileType.getModificationUser() != null) {
            builder.setModifyUser(fileType.getModificationUser());
        }
        if (fileType.getModificationTimestamp() != null) {
            builder.setModifyDate(
                    Timestamp.newBuilder()
                            .setSeconds(fileType.getModificationTimestamp().toInstant().getEpochSecond()));
        }
        CountProto response = stub.updateFileType(builder.build());
        return response.getCount();
    }

    public boolean isProjectExists(long id) {
        IdProto request = IdProto.newBuilder().setId(id).build();
        ExistsProto response = stub.isProjectExists(request);
        return response.getExists();
    }

    public boolean isFileTypeExists(long id) {
        IdProto request = IdProto.newBuilder().setId(id).build();
        ExistsProto response = stub.isFileTypeExists(request);
        return response.getExists();
    }

    public boolean isPrizeExists(long id) {
        IdProto request = IdProto.newBuilder().setId(id).build();
        ExistsProto response = stub.isPrizeExists(request);
        return response.getExists();
    }

    public boolean isStudioSpecExists(long id) {
        IdProto request = IdProto.newBuilder().setId(id).build();
        ExistsProto response = stub.isStudioSpecExists(request);
        return response.getExists();
    }

    public Prize[] getProjectPrizes(long projectId) {
        ProjectIdProto request = ProjectIdProto.newBuilder().setProjectId(projectId).build();
        GetProjectPrizesResponse response = stub.getProjectPrizes(request);
        Prize[] prizes = new Prize[response.getPrizesCount()];
        for (int i = 0; i < response.getPrizesCount(); i++) {
            ProjectPrizeProto p = response.getPrizes(i);
            Prize prize = new Prize();
            prize.setId(p.getPrizeId());
            prize.setProjectId(p.getProjectId());
            prize.setPlace(p.getPlace());
            prize.setPrizeAmount(p.getPrizeAmount());
            prize.setNumberOfSubmissions(p.getNumberOfSubmissions());
            PrizeType prizeType = new PrizeType();
            prizeType.setId(p.getPrizeTypeId());
            if (p.hasPrizeTypeDesc()) {
                prizeType.setDescription(p.getPrizeTypeDesc());
            }
            prize.setPrizeType(prizeType);
            prizes[i] = prize;
        }
        return prizes;
    }

    public int createPrize(Prize prize) {
        CreatePrizeRequest.Builder builder = CreatePrizeRequest.newBuilder();
        builder.setPrizeId(prize.getId());
        builder.setProjectId(prize.getProjectId());
        builder.setPlace(prize.getPlace());
        builder.setPrizeAmount(prize.getPrizeAmount());
        builder.setPrizeTypeId(prize.getPrizeType().getId());
        builder.setNumberOfSubmissions(prize.getNumberOfSubmissions());
        if (prize.getCreationUser() != null) {
            builder.setCreateUser(prize.getCreationUser());
        }
        if (prize.getCreationTimestamp() != null) {
            builder.setCreateDate(
                    Timestamp.newBuilder().setSeconds(prize.getCreationTimestamp().toInstant().getEpochSecond()));
        }
        if (prize.getModificationUser() != null) {
            builder.setModifyUser(prize.getModificationUser());
        }
        if (prize.getModificationTimestamp() != null) {
            builder.setModifyDate(
                    Timestamp.newBuilder().setSeconds(prize.getModificationTimestamp().toInstant().getEpochSecond()));
        }
        CountProto response = stub.createPrize(builder.build());
        return response.getCount();
    }

    public int updatePrize(Prize prize) {
        UpdatePrizeRequest.Builder builder = UpdatePrizeRequest.newBuilder();
        builder.setPrizeId(prize.getId());
        builder.setProjectId(prize.getProjectId());
        builder.setPlace(prize.getPlace());
        builder.setPrizeAmount(prize.getPrizeAmount());
        builder.setPrizeTypeId(prize.getPrizeType().getId());
        builder.setNumberOfSubmissions(prize.getNumberOfSubmissions());
        if (prize.getModificationUser() != null) {
            builder.setModifyUser(prize.getModificationUser());
        }
        if (prize.getModificationTimestamp() != null) {
            builder.setModifyDate(
                    Timestamp.newBuilder().setSeconds(prize.getModificationTimestamp().toInstant().getEpochSecond()));
        }
        CountProto response = stub.updatePrize(builder.build());
        return response.getCount();
    }

    public int deletePrize(long prizeId) {
        DeletePrizeRequest request = DeletePrizeRequest.newBuilder().setPrizeId(prizeId).build();
        CountProto response = stub.deletePrize(request);
        return response.getCount();
    }

    public List<Long> getPrizeProjectIds(long prizeId) {
        GetPrizeProjectIdsRequest request = GetPrizeProjectIdsRequest.newBuilder().setPrizeId(prizeId).build();
        GetPrizeProjectIdsResponse response = stub.getPrizeProjectIds(request);
        return response.getIdsList();
    }

    public ProjectStudioSpecification getProjectStudioSpec(long projectId) {
        ProjectIdProto request = ProjectIdProto.newBuilder().setProjectId(projectId).build();
        GetProjectStudioSpecResponse response = stub.getProjectStudioSpec(request);
        if (!response.hasProjectStudioSpecId()) {
            return null;
        }
        ProjectStudioSpecification studioSpec = new ProjectStudioSpecification();
        studioSpec.setId(response.getProjectStudioSpecId());
        if (response.hasGoals()) {
            studioSpec.setGoals(response.getGoals());
        }
        if (response.hasTargetAudience()) {
            studioSpec.setTargetAudience(response.getTargetAudience());
        }
        if (response.hasBrandingGuidelines()) {
            studioSpec.setBrandingGuidelines(response.getBrandingGuidelines());
        }
        if (response.hasDislikedDesignWebsites()) {
            studioSpec.setDislikedDesignWebSites(response.getDislikedDesignWebsites());
        }
        if (response.hasOtherInstructions()) {
            studioSpec.setOtherInstructions(response.getOtherInstructions());
        }
        if (response.hasWinningCriteria()) {
            studioSpec.setWinningCriteria(response.getWinningCriteria());
        }
        if (response.hasSubmittersLockedBetweenRounds()) {
            studioSpec.setSubmittersLockedBetweenRounds(response.getSubmittersLockedBetweenRounds());
        }
        if (response.hasRoundOneIntroduction()) {
            studioSpec.setRoundOneIntroduction(response.getRoundOneIntroduction());
        }
        if (response.hasRoundTwoIntroduction()) {
            studioSpec.setRoundTwoIntroduction(response.getRoundTwoIntroduction());
        }
        if (response.hasColors()) {
            studioSpec.setColors(response.getColors());
        }
        if (response.hasFonts()) {
            studioSpec.setFonts(response.getFonts());
        }
        if (response.hasLayoutAndSize()) {
            studioSpec.setLayoutAndSize(response.getLayoutAndSize());
        }
        return studioSpec;
    }

    public int createStudioSpec(Long id, ProjectStudioSpecification spec, String operator, Date date) {
        CreateStudioSpecRequest.Builder builder = CreateStudioSpecRequest.newBuilder();
        if (id != null) {
            builder.setProjectStudioSpecId(id);
        }
        if (spec.getGoals() != null) {
            builder.setGoals(spec.getGoals());
        }
        if (spec.getTargetAudience() != null) {
            builder.setTargetAudience(spec.getTargetAudience());
        }
        if (spec.getBrandingGuidelines() != null) {
            builder.setBrandingGuidelines(spec.getBrandingGuidelines());
        }
        if (spec.getDislikedDesignWebSites() != null) {
            builder.setDislikedDesignWebsites(spec.getDislikedDesignWebSites());
        }
        if (spec.getOtherInstructions() != null) {
            builder.setOtherInstructions(spec.getOtherInstructions());
        }
        if (spec.getWinningCriteria() != null) {
            builder.setWinningCriteria(spec.getWinningCriteria());
        }
        builder.setSubmittersLockedBetweenRounds(spec.isSubmittersLockedBetweenRounds());
        if (spec.getRoundOneIntroduction() != null) {
            builder.setRoundOneIntroduction(spec.getRoundOneIntroduction());
        }
        if (spec.getRoundTwoIntroduction() != null) {
            builder.setRoundTwoIntroduction(spec.getRoundTwoIntroduction());
        }
        if (spec.getColors() != null) {
            builder.setColors(spec.getColors());
        }
        if (spec.getFonts() != null) {
            builder.setFonts(spec.getFonts());
        }
        if (spec.getLayoutAndSize() != null) {
            builder.setLayoutAndSize(spec.getLayoutAndSize());
        }
        if (operator != null) {
            builder.setCreateUser(operator);
            builder.setModifyUser(operator);
        }
        if (date != null) {
            builder.setCreateDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()));
            builder.setModifyDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()));
        }
        CountProto response = stub.createStudioSpec(builder.build());
        return response.getCount();
    }

    public int updateStudioSpec(ProjectStudioSpecification spec, String operator, Date date) {
        UpdateStudioSpecRequest.Builder builder = UpdateStudioSpecRequest.newBuilder();
        builder.setProjectStudioSpecId(spec.getId());
        if (spec.getGoals() != null) {
            builder.setGoals(spec.getGoals());
        }
        if (spec.getTargetAudience() != null) {
            builder.setTargetAudience(spec.getTargetAudience());
        }
        if (spec.getBrandingGuidelines() != null) {
            builder.setBrandingGuidelines(spec.getBrandingGuidelines());
        }
        if (spec.getDislikedDesignWebSites() != null) {
            builder.setDislikedDesignWebsites(spec.getDislikedDesignWebSites());
        }
        if (spec.getOtherInstructions() != null) {
            builder.setOtherInstructions(spec.getOtherInstructions());
        }
        if (spec.getWinningCriteria() != null) {
            builder.setWinningCriteria(spec.getWinningCriteria());
        }
        builder.setSubmittersLockedBetweenRounds(spec.isSubmittersLockedBetweenRounds());
        if (spec.getRoundOneIntroduction() != null) {
            builder.setRoundOneIntroduction(spec.getRoundOneIntroduction());
        }
        if (spec.getRoundTwoIntroduction() != null) {
            builder.setRoundTwoIntroduction(spec.getRoundTwoIntroduction());
        }
        if (spec.getColors() != null) {
            builder.setColors(spec.getColors());
        }
        if (spec.getFonts() != null) {
            builder.setFonts(spec.getFonts());
        }
        if (spec.getLayoutAndSize() != null) {
            builder.setLayoutAndSize(spec.getLayoutAndSize());
        }
        if (operator != null) {
            builder.setModifyUser(operator);
        }
        if (date != null) {
            builder.setModifyDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()));
        }
        CountProto response = stub.updateStudioSpec(builder.build());
        return response.getCount();
    }

    public int deleteStudioSpec(long id) {
        DeleteStudioSpecRequest request = DeleteStudioSpecRequest.newBuilder().setProjectStudioSpecId(id).build();
        CountProto response = stub.deleteStudioSpec(request);
        return response.getCount();
    }

    public List<Long> getStudioSpecProjectIds(long specId) {
        GetStudioSpecProjectIdsRequest request = GetStudioSpecProjectIdsRequest.newBuilder()
                .setProjectStudioSpecId(specId).build();
        GetStudioSpecProjectIdsResponse response = stub.getStudioSpecProjectIds(request);
        return response.getProjectIdsList();
    }

    public int removeStudioSpecFromProjects(long id) {
        RemoveStudioSpecFromProjectsRequest request = RemoveStudioSpecFromProjectsRequest.newBuilder()
                .setProjectStudioSpecId(id).build();
        CountProto response = stub.removeStudioSpecFromProjects(request);
        return response.getCount();
    }
}
