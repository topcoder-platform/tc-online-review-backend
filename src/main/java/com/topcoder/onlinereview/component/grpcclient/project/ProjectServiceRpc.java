package com.topcoder.onlinereview.component.grpcclient.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.google.protobuf.ByteString;
import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.component.project.management.FileType;
import com.topcoder.onlinereview.component.project.management.Prize;
import com.topcoder.onlinereview.component.project.management.PrizeType;
import com.topcoder.onlinereview.component.project.management.Project;
import com.topcoder.onlinereview.component.project.management.ProjectCategory;
import com.topcoder.onlinereview.component.project.management.ProjectLinkType;
import com.topcoder.onlinereview.component.project.management.ProjectPropertyType;
import com.topcoder.onlinereview.component.project.management.ProjectStatus;
import com.topcoder.onlinereview.component.project.management.ProjectStudioSpecification;
import com.topcoder.onlinereview.component.project.management.ProjectType;
import com.topcoder.onlinereview.component.project.management.UserProjectCategory;
import com.topcoder.onlinereview.component.project.management.UserProjectType;
import com.topcoder.onlinereview.component.search.filter.Filter;
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
            Project project = loadProject(p);
            project.setProjectFileTypes(loadProjectFileTypes(p.getFileTypesList()));
            project.setPrizes(loadProjectPrizes(p.getPrizesList()));
            if (p.hasProjectStudioSpec()) {
                project.setProjectStudioSpecification(loadProjectStudioSpecification(p.getProjectStudioSpec()));
            }
            setProjectProperties(project, p.getPropertiesList());
            projects[i] = project;
        }
        return projects;
    }

    public List<UserProjectType> countUserProjects(Long userId, long statusId, boolean my, boolean hasManagerRole) {
        CountUserProjectsRequest.Builder builder = CountUserProjectsRequest.newBuilder().setProjectStatusId(statusId)
                .setIsMyProjects(my).setHasManagerRole(hasManagerRole);
        if (userId != null) {
            builder.setUserId(userId);
        }
        CountUserProjectsResponse response = stub.countUserProjects(builder.build());
        List<UserProjectType> userProjectTypes = new ArrayList<>();
        for (UserProjectTypeProto pt : response.getUserProjectTypesList()) {
            UserProjectType userProjectType = new UserProjectType(pt.getId(), pt.getName());
            userProjectType.setCount(pt.getCount());
            for (UserProjectCategoryProto pc : pt.getCategoriesList()) {
                userProjectType.addCategory(new UserProjectCategory(pc.getId(), pc.getName(), pc.getCount()));
            }
            userProjectTypes.add(userProjectType);
        }
        return userProjectTypes;
    }

    public Project[] getAllProjects(Long userId, ProjectStatus status, int page, int perPage, long categoryId,
            boolean my, boolean hasManagerRole) {
        GetAllProjectsRequest.Builder builder = GetAllProjectsRequest.newBuilder().setProjectStatusId(status.getId())
                .setPage(page).setPerPage(perPage).setCategoryId(categoryId).setIsMyProjects(my)
                .setHasManagerRole(hasManagerRole);
        if (userId != null) {
            builder.setUserId(userId);
        }
        GetAllProjectsResponse response = stub.getAllProjects(builder.build());
        Project[] projects = new Project[response.getProjectsCount()];
        for (int i = 0; i < response.getProjectsCount(); i++) {
            ProjectProto p = response.getProjects(i);
            Project project = new Project(p.getId(), status);
            setProjectProperties(project, p.getPropertiesList());
            projects[i] = project;
        }
        return projects;
    }

    public long[] searchProjectsForAutopilot() {
        IdListProto response = stub.searchProjectsForAutopilot(null);
        return response.getIdsList().stream().mapToLong(Long::longValue).toArray();
    }

    public Project[] searchProjects(Filter filter) {
        FilterProto filterProto = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        SearchProjectsResponse response = stub.searchProjects(filterProto);
        Project[] projects = new Project[response.getProjectsCount()];
        for (int i = 0; i < response.getProjectsCount(); i++) {
            ProjectProto p = response.getProjects(i);
            Project project = loadProject(p);
            setProjectProperties(project, p.getPropertiesList());
            projects[i] = project;
        }
        return projects;
    }

    public void createProject(Project project, String operator) {
        CreateProjectRequest.Builder builder = CreateProjectRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        builder.setProject(buildProject(project).build());
        ProjectProto response = stub.createProject(builder.build());
        project.setId(response.getId());
        project.setCreationUser(response.getCreateUser());
        project.setCreationTimestamp(new Date(response.getCreateDate().getSeconds() * 1000));
        project.setModificationUser(response.getModifyUser());
        project.setModificationTimestamp(new Date(response.getModifyDate().getSeconds() * 1000));
        project.setProjectFileTypes(loadProjectFileTypes(response.getFileTypesList()));
        project.setPrizes(loadProjectPrizes(response.getPrizesList()));
        if (project.getProjectStudioSpecification() != null) {
            project.setProjectStudioSpecification(loadProjectStudioSpecification(response.getProjectStudioSpec()));
        }
    }

    public void updateProject(Project project, String operator, String reason) {
        UpdateProjectRequest.Builder builder = UpdateProjectRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        if (reason != null) {
            builder.setReason(reason);
        }
        builder.setProject(buildProject(project).setId(project.getId()).build());
        ProjectProto response = stub.updateProject(builder.build());
        project.setModificationUser(response.getModifyUser());
        project.setModificationTimestamp(new Date(response.getModifyDate().getSeconds() * 1000));
        project.setProjectFileTypes(loadProjectFileTypes(response.getFileTypesList()));
        project.setPrizes(loadProjectPrizes(response.getPrizesList()));
        if (project.getProjectStudioSpecification() != null) {
            project.setProjectStudioSpecification(loadProjectStudioSpecification(response.getProjectStudioSpec()));
        }
    }

    public List<Long> getProjectIdsByDirectId(long directId) {
        IdProto request = IdProto.newBuilder().setId(directId).build();
        GetProjectIdsByDirectIdResponse response = stub.getProjectIdsByDirectId(request);
        return response.getProjectIdsList();
    }

    public FileType[] getProjectFileTypes(long projectId) {
        ProjectIdProto request = ProjectIdProto.newBuilder().setProjectId(projectId).build();
        GetProjectFileTypesResponse response = stub.getProjectFileTypes(request);
        return loadProjectFileTypes(response.getFileTypesList()).toArray(new FileType[0]);
    }

    public void updateProjectFileTypes(long projectId, List<FileType> fileTypes, String operator) {
        UpdateProjectFileTypesRequest.Builder builder = UpdateProjectFileTypesRequest.newBuilder();
        builder.setProjectId(projectId);
        if (operator != null) {
            builder.setOperator(operator);
        }
        for (FileType filetype : fileTypes) {
            builder.addFileTypes(buildFileType(filetype));
        }
        UpdateProjectFileTypesResponse response = stub.updateProjectFileTypes(builder.build());
        fileTypes.clear();
        fileTypes.addAll(loadProjectFileTypes(response.getFileTypesList()));
    }

    public Prize[] getProjectPrizes(long projectId) {
        ProjectIdProto request = ProjectIdProto.newBuilder().setProjectId(projectId).build();
        GetProjectPrizesResponse response = stub.getProjectPrizes(request);
        return loadProjectPrizes(response.getPrizesList()).toArray(new Prize[0]);
    }

    public void updateProjectPrizes(long projectId, List<Prize> prizes, String operator) {
        UpdateProjectPrizesRequest.Builder builder = UpdateProjectPrizesRequest.newBuilder();
        builder.setProjectId(projectId);
        if (operator != null) {
            builder.setOperator(operator);
        }
        for (Prize prize : prizes) {
            builder.addPrizes(buildPrize(prize));
        }
        UpdateProjectPrizesResponse response = stub.updateProjectPrizes(builder.build());
        prizes.clear();
        prizes.addAll(loadProjectPrizes(response.getPrizesList()));
    }

    public ProjectStudioSpecification getProjectStudioSpec(long projectId) {
        ProjectIdProto request = ProjectIdProto.newBuilder().setProjectId(projectId).build();
        GetProjectStudioSpecResponse response = stub.getProjectStudioSpec(request);
        if (!response.hasProjectStudioSpec()) {
            return null;
        }
        return loadProjectStudioSpecification(response.getProjectStudioSpec());
    }

    public void updateProjectStudioSpec(long projectId, ProjectStudioSpecification spec, String operator) {
        UpdateProjectStudioSpecRequest.Builder builder = UpdateProjectStudioSpecRequest.newBuilder();
        builder.setProjectId(projectId);
        if (operator != null) {
            builder.setOperator(operator);
        }
        builder.setProjectStudioSpec(buildStudioSpec(spec));
        ProjectStudioSpecProto response = stub.updateProjectStudioSpec(builder.build());
        spec.setModificationUser(response.getModifyUser());
        spec.setModificationTimestamp(new Date(response.getModifyDate().getSeconds() * 1000));
    }

    public FileType createFileType(FileType fileType, String operator) {
        CreateFileTypeRequest.Builder builder = CreateFileTypeRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        builder.setFileType(buildFileType(fileType));
        FileTypeProto response = stub.createFileType(builder.build());
        fileType.setId(response.getId());
        fileType.setCreationUser(response.getCreateUser());
        fileType.setCreationTimestamp(new Date(response.getCreateDate().getSeconds() * 1000));
        fileType.setModificationUser(response.getModifyUser());
        fileType.setModificationTimestamp(new Date(response.getModifyDate().getSeconds() * 1000));
        return fileType;
    }

    public FileType updateFileType(FileType fileType, String operator) {
        UpdateFileTypeRequest.Builder builder = UpdateFileTypeRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        builder.setFileType(buildFileType(fileType));
        FileTypeProto response = stub.updateFileType(builder.build());
        fileType.setModificationUser(response.getModifyUser());
        fileType.setModificationTimestamp(new Date(response.getModifyDate().getSeconds() * 1000));
        return fileType;
    }

    public int deleteFileType(long fileTypeId, String operator) {
        DeleteFileTypeRequest.Builder builder = DeleteFileTypeRequest.newBuilder();
        builder.setFileTypeId(fileTypeId);
        if (operator != null) {
            builder.setOperator(operator);
        }
        CountProto response = stub.deleteFileType(builder.build());
        return response.getCount();
    }

    public Prize createPrize(Prize prize, String operator) {
        CreatePrizeRequest.Builder builder = CreatePrizeRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        builder.setPrize(buildPrize(prize));
        PrizeProto response = stub.createPrize(builder.build());
        prize.setId(response.getId());
        prize.setCreationUser(response.getCreateUser());
        prize.setCreationTimestamp(new Date(response.getCreateDate().getSeconds() * 1000));
        prize.setModificationUser(response.getModifyUser());
        prize.setModificationTimestamp(new Date(response.getModifyDate().getSeconds() * 1000));
        return prize;
    }

    public Prize updatePrize(Prize prize, String operator) {
        UpdatePrizeRequest.Builder builder = UpdatePrizeRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        builder.setPrize(buildPrize(prize));
        PrizeProto response = stub.updatePrize(builder.build());
        prize.setModificationUser(response.getModifyUser());
        prize.setModificationTimestamp(new Date(response.getModifyDate().getSeconds() * 1000));
        return prize;
    }

    public int deletePrize(long prizeId, String operator) {
        DeletePrizeRequest.Builder builder = DeletePrizeRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        builder.setPrizeId(prizeId);
        CountProto response = stub.deletePrize(builder.build());
        return response.getCount();
    }

    public ProjectStudioSpecification createStudioSpec(ProjectStudioSpecification spec, String operator) {
        CreateStudioSpecRequest.Builder builder = CreateStudioSpecRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        builder.setProjectStudioSpec(buildStudioSpec(spec));
        ProjectStudioSpecProto response = stub.createStudioSpec(builder.build());
        spec.setId(response.getId());
        spec.setCreationUser(response.getCreateUser());
        spec.setCreationTimestamp(new Date(response.getCreateDate().getSeconds() * 1000));
        spec.setModificationUser(response.getModifyUser());
        spec.setModificationTimestamp(new Date(response.getModifyDate().getSeconds() * 1000));
        return spec;
    }

    public ProjectStudioSpecification updateStudioSpec(ProjectStudioSpecification spec, String operator) {
        UpdateStudioSpecRequest.Builder builder = UpdateStudioSpecRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        builder.setProjectStudioSpec(buildStudioSpec(spec));
        ProjectStudioSpecProto response = stub.updateStudioSpec(builder.build());
        spec.setModificationUser(response.getModifyUser());
        spec.setModificationTimestamp(new Date(response.getModifyDate().getSeconds() * 1000));
        return spec;
    }

    public int deleteStudioSpec(long specId, String operator) {
        DeleteStudioSpecRequest.Builder builder = DeleteStudioSpecRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        builder.setProjectStudioSpecId(specId);
        CountProto response = stub.deleteStudioSpec(builder.build());
        return response.getCount();
    }

    public ProjectPropertyType[] getAllProjectPropertyTypes() {
        GetAllProjectPropertyTypesResponse response = stub.getAllProjectPropertyTypes(null);
        ProjectPropertyType[] propertyTypes = new ProjectPropertyType[response.getPropertyTypesCount()];
        for (int i = 0; i < response.getPropertyTypesCount(); ++i) {
            ProjectPropertyTypeProto p = response.getPropertyTypes(i);
            ProjectPropertyType propertyType = new ProjectPropertyType(p.getId(), p.getName());
            if (p.hasDescription()) {
                propertyType.setDescription(p.getDescription());
            }
            propertyTypes[i] = propertyType;
        }
        return propertyTypes;
    }

    public ProjectType[] getAllProjectTypes() {
        GetAllProjectTypesResponse response = stub.getAllProjectTypes(null);
        ProjectType[] projectTypes = new ProjectType[response.getProjectTypesCount()];
        for (int i = 0; i < response.getProjectTypesCount(); ++i) {
            ProjectTypeProto p = response.getProjectTypes(i);
            ProjectType projectType = new ProjectType();
            if (p.hasId()) {
                projectType.setId(p.getId());
            }
            if (p.hasName()) {
                projectType.setName(p.getName());
            }
            if (p.hasDescription()) {
                projectType.setDescription(p.getDescription());
            }
            if (p.hasGeneric()) {
                projectType.setGeneric(p.getGeneric());
            }
            projectTypes[i] = projectType;
        }
        return projectTypes;
    }

    public ProjectCategory[] getAllProjectCategories() {
        GetAllProjectCategoriesResponse response = stub.getAllProjectCategories(null);
        ProjectCategory[] projectCategories = new ProjectCategory[response.getProjectCategoriesCount()];
        for (int i = 0; i < response.getProjectCategoriesCount(); ++i) {
            ProjectCategoryProto p = response.getProjectCategories(i);
            ProjectCategory projectCategory = new ProjectCategory();
            if (p.hasId()) {
                projectCategory.setId(p.getId());
            }
            if (p.hasName()) {
                projectCategory.setName(p.getName());
            }
            if (p.hasDescription()) {
                projectCategory.setDescription(p.getDescription());
            }
            if (p.hasProjectType()) {
                ProjectTypeProto pt = p.getProjectType();
                ProjectType projectType = new ProjectType();
                if (pt.hasId()) {
                    projectType.setId(pt.getId());
                }
                if (pt.hasName()) {
                    projectType.setName(pt.getName());
                }
                if (pt.hasDescription()) {
                    projectType.setDescription(pt.getDescription());
                }
                if (pt.hasGeneric()) {
                    projectType.setGeneric(pt.getGeneric());
                }
                projectCategory.setProjectType(projectType);
            }
            projectCategories[i] = projectCategory;
        }
        return projectCategories;
    }

    public ProjectStatus[] getAllProjectStatuses() {
        GetAllProjectStatusesResponse response = stub.getAllProjectStatuses(null);
        ProjectStatus[] projectStatuses = new ProjectStatus[response.getProjectStatusesCount()];
        for (int i = 0; i < response.getProjectStatusesCount(); ++i) {
            ProjectStatusProto p = response.getProjectStatuses(i);
            ProjectStatus status = new ProjectStatus(p.getId(), p.getName());
            if (p.hasDescription()) {
                status.setDescription(p.getDescription());
            }
            projectStatuses[i] = status;
        }
        return projectStatuses;
    }

    public FileType[] getAllFileTypes() {
        GetAllFileTypesResponse response = stub.getAllFileTypes(null);
        return loadProjectFileTypes(response.getFileTypesList()).toArray(new FileType[0]);
    }

    public PrizeType[] getAllPrizeTypes() {
        GetAllPrizeTypesResponse response = stub.getAllPrizeTypes(null);
        PrizeType[] prizeTypes = new PrizeType[response.getPrizeTypesCount()];
        for (int i = 0; i < response.getPrizeTypesCount(); ++i) {
            PrizeTypeProto p = response.getPrizeTypes(i);
            PrizeType prizeType = new PrizeType();
            if (p.hasId()) {
                prizeType.setId(p.getId());
            }
            if (p.hasDescription()) {
                prizeType.setDescription(p.getDescription());
            }
            prizeTypes[i] = prizeType;
        }
        return prizeTypes;
    }

    public ProjectLinkType[] getAllProjectLinkTypes() {
        GetAllProjectLinkTypesResponse response = stub.getAllProjectLinkTypes(null);
        ProjectLinkType[] projectLinkTypes = new ProjectLinkType[response.getProjectLinkTypesCount()];
        for (int i = 0; i < response.getProjectLinkTypesCount(); ++i) {
            ProjectLinkTypeProto p = response.getProjectLinkTypes(i);
            ProjectLinkType projectLinkType = new ProjectLinkType();
            if (p.hasLinkTypeId()) {
                projectLinkType.setId(p.getLinkTypeId());
            }
            if (p.hasLinkTypeName()) {
                projectLinkType.setName(p.getLinkTypeName());
            }
            projectLinkTypes[i] = projectLinkType;
        }
        return projectLinkTypes;
    }

    public List<ProjectLinkProto> getSourceProjectLinks(long sourceProjectId) {
        GetSourceProjectLinksRequest request = GetSourceProjectLinksRequest.newBuilder()
                .setDestProjectId(sourceProjectId).build();
        GetSourceProjectLinksResponse response = stub.getSourceProjectLinks(request);
        return response.getProjectLinksList();
    }

    public List<ProjectLinkProto> getDestProjectLinks(long destProjectId) {
        GetDestProjectLinksRequest request = GetDestProjectLinksRequest.newBuilder().setSourceProjectId(destProjectId)
                .build();
        GetDestProjectLinksResponse response = stub.getDestProjectLinks(request);
        return response.getProjectLinksList();
    }

    public int deleteProjectLinksBySourceId(Long sourceProjectId) {
        DeleteProjectLinksBySourceIdRequest.Builder request = DeleteProjectLinksBySourceIdRequest.newBuilder();
        if (sourceProjectId != null) {
            request.setSourceProjectId(sourceProjectId);
        }
        CountProto response = stub.deleteProjectLinksBySourceId(request.build());
        return response.getCount();
    }

    public int createProjectLink(Long sourceProjectId, Long destProjectId, Long linkTypeId) {
        ProjectLinkProto.Builder request = ProjectLinkProto.newBuilder();
        if (sourceProjectId != null) {
            request.setSourceProjectId(sourceProjectId);
        }
        if (destProjectId != null) {
            request.setDestProjectId(destProjectId);
        }
        if (linkTypeId != null) {
            request.setLinkTypeId(linkTypeId);
        }
        CountProto response = stub.createProjectLink(request.build());
        return response.getCount();
    }

    public int updateProjectStatus(Long projectId, long statusId) {
        UpdateProjectStatusRequest.Builder request = UpdateProjectStatusRequest.newBuilder();
        if (projectId != null) {
            request.setProjectId(projectId);
        }
        request.setStatusId(statusId);
        CountProto response = stub.updateProjectStatus(request.build());
        return response.getCount();
    }

    private Project loadProject(ProjectProto p) {
        ProjectStatus status = new ProjectStatus(p.getProjectStatus().getId(), p.getProjectStatus().getName());
        ProjectType type = new ProjectType(p.getProjectCategory().getProjectType().getId(),
                p.getProjectCategory().getProjectType().getName());
        ProjectCategory category = new ProjectCategory(p.getProjectCategory().getId(),
                p.getProjectCategory().getName(), type);
        if (p.getProjectCategory().hasDescription()) {
            category.setDescription(p.getProjectCategory().getDescription());
        }
        Project project = new Project(p.getId(), category, status);
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
        return project;
    }

    private List<FileType> loadProjectFileTypes(List<FileTypeProto> fileTypeProtos) {
        List<FileType> fileTypes = new ArrayList<>();
        for (FileTypeProto f : fileTypeProtos) {
            FileType fileType = new FileType();
            fileType.setId(f.getId());
            if (f.hasDescription()) {
                fileType.setDescription(f.getDescription());
            }
            fileType.setSort(f.getSort());
            fileType.setImageFile(f.getImageFile());
            if (f.hasExtension()) {
                fileType.setExtension(f.getExtension());
            }
            fileType.setBundledFile(f.getBundledFile());
            fileTypes.add(fileType);
        }
        return fileTypes;
    }

    private List<Prize> loadProjectPrizes(List<PrizeProto> prizeProtos) {
        List<Prize> prizes = new ArrayList<>();
        for (PrizeProto pp : prizeProtos) {
            Prize prize = new Prize();
            prize.setId(pp.getId());
            prize.setProjectId(pp.getProjectId());
            prize.setPlace(pp.getPlace());
            prize.setPrizeAmount(pp.getPrizeAmount());
            prize.setNumberOfSubmissions(pp.getNumberOfSubmissions());
            if (pp.hasPrizeType()) {
                PrizeType prizeType = new PrizeType();
                prizeType.setId(pp.getPrizeType().getId());
                if (pp.getPrizeType().hasDescription()) {
                    prizeType.setDescription(pp.getPrizeType().getDescription());
                }
                prize.setPrizeType(prizeType);
            }
            prizes.add(prize);
        }
        return prizes;
    }

    private ProjectStudioSpecification loadProjectStudioSpecification(ProjectStudioSpecProto ps) {
        ProjectStudioSpecification studioSpec = new ProjectStudioSpecification();
        studioSpec.setId(ps.getId());
        if (ps.hasGoals()) {
            studioSpec.setGoals(ps.getGoals());
        }
        if (ps.hasTargetAudience()) {
            studioSpec.setTargetAudience(ps.getTargetAudience());
        }
        if (ps.hasBrandingGuidelines()) {
            studioSpec.setBrandingGuidelines(ps.getBrandingGuidelines());
        }
        if (ps.hasDislikedDesignWebsites()) {
            studioSpec.setDislikedDesignWebSites(ps.getDislikedDesignWebsites());
        }
        if (ps.hasOtherInstructions()) {
            studioSpec.setOtherInstructions(ps.getOtherInstructions());
        }
        if (ps.hasWinningCriteria()) {
            studioSpec.setWinningCriteria(ps.getWinningCriteria());
        }
        if (ps.hasSubmittersLockedBetweenRounds()) {
            studioSpec.setSubmittersLockedBetweenRounds(ps.getSubmittersLockedBetweenRounds());
        }
        if (ps.hasRoundOneIntroduction()) {
            studioSpec.setRoundOneIntroduction(ps.getRoundOneIntroduction());
        }
        if (ps.hasRoundTwoIntroduction()) {
            studioSpec.setRoundTwoIntroduction(ps.getRoundTwoIntroduction());
        }
        if (ps.hasColors()) {
            studioSpec.setColors(ps.getColors());
        }
        if (ps.hasFonts()) {
            studioSpec.setFonts(ps.getFonts());
        }
        if (ps.hasLayoutAndSize()) {
            studioSpec.setLayoutAndSize(ps.getLayoutAndSize());
        }
        return studioSpec;
    }

    private void setProjectProperties(Project project, List<ProjectPropertyProto> properties) {
        for (ProjectPropertyProto pp : properties) {
            project.setProperty(pp.getName(), pp.hasValue() ? pp.getValue() : null);
        }
    }

    private ProjectProto.Builder buildProject(Project project) {
        ProjectProto.Builder p = ProjectProto.newBuilder();
        if (project.getTcDirectProjectId() != null) {
            p.setDirectProjectId(project.getTcDirectProjectId());
        }
        if (project.getProjectCategory() != null) {
            p.setProjectCategory(ProjectCategoryProto.newBuilder().setId(project.getProjectCategory().getId()));
        }
        if (project.getProjectStatus() != null) {
            p.setProjectStatus(ProjectStatusProto.newBuilder().setId(project.getProjectStatus().getId()));
        }
        for (Object item : project.getAllProperties().entrySet()) {
            Entry entry = (Entry) item;
            p.addProperties(ProjectPropertyProto.newBuilder().setName(entry.getKey().toString())
                    .setValue(entry.getValue().toString()));
        }
        for (Prize prize : project.getPrizes()) {
            p.addPrizes(buildPrize(prize));
        }
        if (project.getProjectStudioSpecification() != null) {
            p.setProjectStudioSpec(buildStudioSpec(project.getProjectStudioSpecification()));
        }
        for (FileType filetype : project.getProjectFileTypes()) {
            p.addFileTypes(buildFileType(filetype));
        }
        return p;
    }

    private FileTypeProto buildFileType(FileType filetype) {
        FileTypeProto.Builder fBuilder = FileTypeProto.newBuilder().setId(filetype.getId())
                .setSort(filetype.getSort()).setBundledFile(filetype.isBundledFile())
                .setImageFile(filetype.isImageFile());
        if (filetype.getDescription() != null) {
            fBuilder.setDescription(filetype.getDescription());
        }
        if (filetype.getExtension() != null) {
            fBuilder.setExtension(filetype.getExtension());
        }
        return fBuilder.build();
    }

    private PrizeProto buildPrize(Prize prize) {
        PrizeProto.Builder prizeBuilder = PrizeProto.newBuilder().setId(prize.getId())
                .setProjectId(prize.getProjectId()).setPlace(prize.getPlace()).setPrizeAmount(prize.getPrizeAmount())
                .setNumberOfSubmissions(prize.getNumberOfSubmissions());
        if (prize.getPrizeType() != null) {
            prizeBuilder.setPrizeType(PrizeTypeProto.newBuilder().setId(prize.getPrizeType().getId()));
        }
        return prizeBuilder.build();
    }

    private ProjectStudioSpecProto buildStudioSpec(ProjectStudioSpecification spec) {
        ProjectStudioSpecProto.Builder specProto = ProjectStudioSpecProto.newBuilder();
        specProto.setId(spec.getId());
        if (spec.getGoals() != null) {
            specProto.setGoals(spec.getGoals());
        }
        if (spec.getTargetAudience() != null) {
            specProto.setTargetAudience(spec.getTargetAudience());
        }
        if (spec.getBrandingGuidelines() != null) {
            specProto.setBrandingGuidelines(spec.getBrandingGuidelines());
        }
        if (spec.getDislikedDesignWebSites() != null) {
            specProto.setDislikedDesignWebsites(spec.getDislikedDesignWebSites());
        }
        if (spec.getOtherInstructions() != null) {
            specProto.setOtherInstructions(spec.getOtherInstructions());
        }
        if (spec.getWinningCriteria() != null) {
            specProto.setWinningCriteria(spec.getWinningCriteria());
        }
        specProto.setSubmittersLockedBetweenRounds(spec.isSubmittersLockedBetweenRounds());
        if (spec.getRoundOneIntroduction() != null) {
            specProto.setRoundOneIntroduction(spec.getRoundOneIntroduction());
        }
        if (spec.getRoundTwoIntroduction() != null) {
            specProto.setRoundTwoIntroduction(spec.getRoundTwoIntroduction());
        }
        if (spec.getColors() != null) {
            specProto.setColors(spec.getColors());
        }
        if (spec.getFonts() != null) {
            specProto.setFonts(spec.getFonts());
        }
        if (spec.getLayoutAndSize() != null) {
            specProto.setLayoutAndSize(spec.getLayoutAndSize());
        }
        return specProto.build();
    }
}
