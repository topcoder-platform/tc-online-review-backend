package com.topcoder.onlinereview.component.grpcclient.upload;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import com.topcoder.onlinereview.component.deliverable.MimeType;
import com.topcoder.onlinereview.component.deliverable.NamedDeliverableStructure;
import com.topcoder.onlinereview.component.deliverable.Submission;
import com.topcoder.onlinereview.component.deliverable.SubmissionImage;
import com.topcoder.onlinereview.component.deliverable.SubmissionStatus;
import com.topcoder.onlinereview.component.deliverable.SubmissionType;
import com.topcoder.onlinereview.component.deliverable.Upload;
import com.topcoder.onlinereview.component.deliverable.UploadStatus;
import com.topcoder.onlinereview.component.deliverable.UploadType;
import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.grpc.upload.proto.*;
import com.topcoder.onlinereview.component.project.management.FileType;
import com.topcoder.onlinereview.component.project.management.Prize;
import com.topcoder.onlinereview.component.project.management.PrizeType;
import com.topcoder.onlinereview.component.search.filter.Filter;

@Service
@DependsOn({ "grpcChannelManager" })
public class UploadServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private UploadServiceGrpc.UploadServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = UploadServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public void addUploadType(UploadType entity) {
        stub.addUploadType(buildEntityProto(entity));
    }

    public void addUploadStatus(UploadStatus entity) {
        stub.addUploadStatus(buildEntityProto(entity));
    }

    public void addSubmissionType(SubmissionType entity) {
        stub.addSubmissionType(buildEntityProto(entity));
    }

    public void addSubmissionStatus(SubmissionStatus entity) {
        stub.addSubmissionStatus(buildEntityProto(entity));
    }

    public void addUpload(Upload upload) {
        stub.addUpload(buildUploadProto(upload));
    }

    public void addSubmission(Submission submission) {
        stub.addSubmission(buildSubmissionProto(submission));
    }

    public void addSubmissionImage(SubmissionImage submissionImage) {
        stub.addSubmissionImage(buildSubmissionImageProto(submissionImage));
    }

    public void removeUploadType(long id) {
        IdProto entitiyRemoveRequest = IdProto.newBuilder()
                .setId(id).build();
        stub.removeUploadType(entitiyRemoveRequest);
    }

    public void removeUploadStatus(long id) {
        IdProto entitiyRemoveRequest = IdProto.newBuilder()
                .setId(id).build();
        stub.removeUploadStatus(entitiyRemoveRequest);
    }

    public void removeSubmissionType(long id) {
        IdProto entitiyRemoveRequest = IdProto.newBuilder()
                .setId(id).build();
        stub.removeSubmissionType(entitiyRemoveRequest);
    }

    public void removeSubmissionStatus(long id) {
        IdProto entitiyRemoveRequest = IdProto.newBuilder()
                .setId(id).build();
        stub.removeSubmissionStatus(entitiyRemoveRequest);
    }

    public void removeUpload(long id) {
        IdProto entitiyRemoveRequest = IdProto.newBuilder()
                .setId(id).build();
        stub.removeUpload(entitiyRemoveRequest);
    }

    public void removeSubmission(long id) {
        IdProto entitiyRemoveRequest = IdProto.newBuilder()
                .setId(id).build();
        stub.removeSubmission(entitiyRemoveRequest);
    }

    public void removeSubmissionImage(SubmissionImage submissionImage) {
        RemoveSubmissionImageRequest request = RemoveSubmissionImageRequest.newBuilder()
                .setSubmissionId(submissionImage.getSubmissionId())
                .setImageId(submissionImage.getImageId()).build();
        stub.removeSubmissionImage(request);
    }

    public void updateUploadType(UploadType entity) {
        stub.updateUploadType(buildEntityProto(entity));
    }

    public void updateUploadStatus(UploadStatus entity) {
        stub.updateUploadStatus(buildEntityProto(entity));
    }

    public void updateSubmissionType(SubmissionType entity) {
        stub.updateSubmissionType(buildEntityProto(entity));
    }

    public void updateSubmissionStatus(SubmissionStatus entity) {
        stub.updateSubmissionStatus(buildEntityProto(entity));
    }

    public void updateUpload(Upload upload) {
        stub.updateUpload(buildUploadProto(upload));
    }

    public void updateSubmission(Submission submission) {
        stub.updateSubmission(buildSubmissionProto(submission));
    }

    public void updateSubmissionImage(SubmissionImage submissionImage) {
        stub.updateSubmissionImage(buildSubmissionImageProto(submissionImage));
    }

    public Long[] getAllUploadTypeIds() {
        EntityIdsProto getAllEntityIdsResponse = stub.getAllUploadTypeIds(null);
        return getAllEntityIdsResponse.getIdsList().toArray(new Long[0]);
    }

    public Long[] getAllUploadStatusIds() {
        EntityIdsProto getAllEntityIdsResponse = stub.getAllUploadStatusIds(null);
        return getAllEntityIdsResponse.getIdsList().toArray(new Long[0]);
    }

    public Long[] getAllSubmissionTypeIds() {
        EntityIdsProto getAllEntityIdsResponse = stub.getAllSubmissionTypeIds(null);
        return getAllEntityIdsResponse.getIdsList().toArray(new Long[0]);
    }

    public Long[] getAllSubmissionStatusIds() {
        EntityIdsProto getAllEntityIdsResponse = stub.getAllSubmissionStatusIds(null);
        return getAllEntityIdsResponse.getIdsList().toArray(new Long[0]);
    }

    public Long[] getAllMimeTypeIds() {
        EntityIdsProto getAllEntityIdsResponse = stub.getAllMimeTypeIds(null);
        return getAllEntityIdsResponse.getIdsList().toArray(new Long[0]);
    }

    public UploadType[] loadUploadTypes(Long[] ids) {
        EntityIdsProto entityIdsProto = EntityIdsProto.newBuilder().addAllIds(Arrays.asList(ids)).build();
        EntityListProto response = stub.loadUploadTypes(entityIdsProto);
        List<EntityProto> entityList = response.getEntitiesList();
        UploadType[] entities = new UploadType[response.getEntitiesCount()];
        for (int i = 0; i < response.getEntitiesCount(); ++i) {
            entities[i] = loadUploadType(entityList.get(i));
        }
        return entities;
    }

    public UploadStatus[] loadUploadStatuses(Long[] ids) {
        EntityIdsProto entityIdsProto = EntityIdsProto.newBuilder().addAllIds(Arrays.asList(ids)).build();
        EntityListProto response = stub.loadUploadStatuses(entityIdsProto);
        List<EntityProto> entityList = response.getEntitiesList();
        UploadStatus[] entities = new UploadStatus[response.getEntitiesCount()];
        for (int i = 0; i < response.getEntitiesCount(); ++i) {
            entities[i] = loadUploadStatus(entityList.get(i));
        }
        return entities;
    }

    public SubmissionType[] loadSubmissionTypes(Long[] ids) {
        EntityIdsProto entityIdsProto = EntityIdsProto.newBuilder().addAllIds(Arrays.asList(ids)).build();
        EntityListProto response = stub.loadSubmissionTypes(entityIdsProto);
        List<EntityProto> entityList = response.getEntitiesList();
        SubmissionType[] entities = new SubmissionType[response.getEntitiesCount()];
        for (int i = 0; i < response.getEntitiesCount(); ++i) {
            entities[i] = loadSubmissionType(entityList.get(i));
        }
        return entities;
    }

    public SubmissionStatus[] loadSubmissionStatuses(Long[] ids) {
        EntityIdsProto entityIdsProto = EntityIdsProto.newBuilder().addAllIds(Arrays.asList(ids)).build();
        EntityListProto response = stub.loadSubmissionStatuses(entityIdsProto);
        List<EntityProto> entityList = response.getEntitiesList();
        SubmissionStatus[] entities = new SubmissionStatus[response.getEntitiesCount()];
        for (int i = 0; i < response.getEntitiesCount(); ++i) {
            entities[i] = loadSubmissionStatus(entityList.get(i));
        }
        return entities;
    }

    public Upload[] loadUploads(Long[] ids) {
        EntityIdsProto entityIdsProto = EntityIdsProto.newBuilder().addAllIds(Arrays.asList(ids)).build();
        UploadCompleteListProto response = stub.loadUploads(entityIdsProto);
        List<UploadCompleteProto> uploadList = response.getUploadsList();
        Upload[] uploads = new Upload[response.getUploadsCount()];
        for (int i = 0; i < response.getUploadsCount(); ++i) {
            Upload upload = loadUpload(uploadList.get(i).getUpload());
            upload.setUploadType(loadUploadType(uploadList.get(i).getUploadType()));
            upload.setUploadStatus(loadUploadStatus(uploadList.get(i).getUploadStatus()));
            uploads[i] = upload;
        }
        return uploads;
    }

    public Submission[] loadSubmissions(Long[] ids) {
        EntityIdsProto entityIdsProto = EntityIdsProto.newBuilder().addAllIds(Arrays.asList(ids)).build();
        SubmissionCompleteListProto response = stub.loadSubmissions(entityIdsProto);
        List<SubmissionCompleteProto> submissionList = response.getSubmissionsList();
        Submission[] submissions = new Submission[response.getSubmissionsCount()];
        for (int i = 0; i < response.getSubmissionsCount(); ++i) {
            Submission submission = loadSubmission(submissionList.get(i).getSubmission());
            submission.setSubmissionStatus(loadSubmissionStatus(submissionList.get(i).getSubmissionStatus()));
            submission.setSubmissionType(loadSubmissionType(submissionList.get(i).getSubmissionType()));
            PrizeProto p = submissionList.get(i).getPrize();
            if (p.hasPrizeId()) {
                Prize prize = loadPrize(p);
                PrizeType prizeType = loadPrizeType(submissionList.get(i).getPrizeType());
                prize.setPrizeType(prizeType);
                submission.setPrize(prize);
            }
            Upload upload = loadUpload(submissionList.get(i).getUpload().getUpload());
            upload.setUploadType(loadUploadType(submissionList.get(i).getUpload().getUploadType()));
            upload.setUploadStatus(loadUploadStatus(submissionList.get(i).getUpload().getUploadStatus()));
            submission.setUpload(upload);
            submissions[i] = submission;
        }
        return submissions;
    }

    public MimeType[] loadMimeTypes(Long[] ids) {
        EntityIdsProto entityIdsProto = EntityIdsProto.newBuilder().addAllIds(Arrays.asList(ids)).build();
        MimeTypeListProto response = stub.loadMimeTypes(entityIdsProto);
        List<MimeTypeProto> mimeTypeList = response.getMimeTypesList();
        MimeType[] mimeTypes = new MimeType[response.getMimeTypesCount()];
        for (int i = 0; i < response.getMimeTypesCount(); ++i) {
            mimeTypes[i] = loadMimeType(mimeTypeList.get(i));
        }
        return mimeTypes;
    }

    public SubmissionImage[] getImagesForSubmission(long submissionId) {
        IdProto request = IdProto.newBuilder().setId(submissionId).build();
        SubmissionImageListProto response = stub.getImagesForSubmission(request);
        List<SubmissionImageProto> submissionImageList = response.getSubmissionImagesList();
        SubmissionImage[] submissionImages = new SubmissionImage[response.getSubmissionImagesCount()];
        for (int i = 0; i < response.getSubmissionImagesCount(); ++i) {
            submissionImages[i] = loadSubmissionImage(submissionImageList.get(i));
        }
        return submissionImages;
    }

    public Upload[] searchUploads(Filter filter) {
        FilterProto filterProto = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        UploadCompleteListProto response = stub.searchUploads(filterProto);
        List<UploadCompleteProto> uploadList = response.getUploadsList();
        Upload[] uploads = new Upload[response.getUploadsCount()];
        for (int i = 0; i < response.getUploadsCount(); ++i) {
            Upload upload = loadUpload(uploadList.get(i).getUpload());
            upload.setUploadType(loadUploadType(uploadList.get(i).getUploadType()));
            upload.setUploadStatus(loadUploadStatus(uploadList.get(i).getUploadStatus()));
            uploads[i] = upload;
        }
        return uploads;
    }

    public Submission[] searchSubmissions(Filter filter) {
        FilterProto filterProto = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        SubmissionCompleteListProto response = stub.searchSubmissions(filterProto);
        List<SubmissionCompleteProto> submissionList = response.getSubmissionsList();
        Submission[] submissions = new Submission[response.getSubmissionsCount()];
        for (int i = 0; i < response.getSubmissionsCount(); ++i) {
            Submission submission = loadSubmission(submissionList.get(i).getSubmission());
            submission.setSubmissionStatus(loadSubmissionStatus(submissionList.get(i).getSubmissionStatus()));
            submission.setSubmissionType(loadSubmissionType(submissionList.get(i).getSubmissionType()));
            PrizeProto p = submissionList.get(i).getPrize();
            if (p.hasPrizeId()) {
                Prize prize = loadPrize(p);
                PrizeType prizeType = loadPrizeType(submissionList.get(i).getPrizeType());
                prize.setPrizeType(prizeType);
                submission.setPrize(prize);
            }
            Upload upload = loadUpload(submissionList.get(i).getUpload().getUpload());
            upload.setUploadType(loadUploadType(submissionList.get(i).getUpload().getUploadType()));
            upload.setUploadStatus(loadUploadStatus(submissionList.get(i).getUpload().getUploadStatus()));
            submission.setUpload(upload);
            submissions[i] = submission;
        }
        return submissions;
    }

    private EntityProto buildEntityProto(NamedDeliverableStructure entity) {
        EntityProto.Builder eBuilder = EntityProto.newBuilder().setId(entity.getId());
        if (entity.getCreationUser() != null) {
            eBuilder.setCreateUser(entity.getCreationUser());
        }
        if (entity.getCreationTimestamp() != null) {
            eBuilder.setCreateDate(Timestamp.newBuilder()
                    .setSeconds(entity.getCreationTimestamp().toInstant().getEpochSecond()).build());
        }
        if (entity.getModificationUser() != null) {
            eBuilder.setModifyUser(entity.getModificationUser());
        }
        if (entity.getModificationTimestamp() != null) {
            eBuilder.setModifyDate(
                    Timestamp.newBuilder().setSeconds(entity.getModificationTimestamp().toInstant().getEpochSecond())
                            .build());
        }
        if (entity.getName() != null) {
            eBuilder.setName(entity.getName());
        }
        if (entity.getDescription() != null) {
            eBuilder.setDescription(entity.getDescription());
        }
        return eBuilder.build();
    }

    private UploadProto buildUploadProto(Upload upload) {
        UploadProto.Builder uploadProtoBuilder = UploadProto.newBuilder()
                .setUploadId(upload.getId());
        if (upload.getCreationUser() != null) {
            uploadProtoBuilder.setCreateUser(upload.getCreationUser());
        }
        if (upload.getCreationTimestamp() != null) {
            uploadProtoBuilder
                    .setCreateDate(Timestamp.newBuilder()
                            .setSeconds(upload.getCreationTimestamp().toInstant().getEpochSecond()).build());
        }
        if (upload.getModificationUser() != null) {
            uploadProtoBuilder.setModifyUser(upload.getModificationUser());
        }
        if (upload.getModificationTimestamp() != null) {
            uploadProtoBuilder.setModifyDate(
                    Timestamp.newBuilder().setSeconds(upload.getModificationTimestamp().toInstant().getEpochSecond())
                            .build());
        }

        uploadProtoBuilder.setProjectId(upload.getProject());
        uploadProtoBuilder.setProjectPhaseId(upload.getProjectPhase());
        uploadProtoBuilder.setResourceId(upload.getOwner());
        if (upload.getUploadType() != null) {
            uploadProtoBuilder.setUploadTypeId(upload.getUploadType().getId());
        }
        if (upload.getUploadStatus() != null) {
            uploadProtoBuilder.setUploadStatusId(upload.getUploadStatus().getId());
        }

        if (upload.getParameter() != null) {
            uploadProtoBuilder.setParameter(upload.getParameter());
        }
        if (upload.getDescription() != null) {
            uploadProtoBuilder.setUploadDesc(upload.getDescription());
        }
        return uploadProtoBuilder.build();
    }

    private SubmissionProto buildSubmissionProto(Submission submission) {
        SubmissionProto.Builder submissionProtoBuilder = SubmissionProto.newBuilder()
                .setSubmissionId(submission.getId());
        if (submission.getCreationUser() != null) {
            submissionProtoBuilder.setCreateUser(submission.getCreationUser());
        }
        if (submission.getCreationTimestamp() != null) {
            submissionProtoBuilder.setCreateDate(
                    Timestamp.newBuilder().setSeconds(submission.getCreationTimestamp().toInstant().getEpochSecond())
                            .build());
        }
        if (submission.getModificationUser() != null) {
            submissionProtoBuilder.setModifyUser(submission.getModificationUser());
        }
        if (submission.getModificationTimestamp() != null) {
            submissionProtoBuilder.setModifyDate(
                    Timestamp.newBuilder()
                            .setSeconds(submission.getModificationTimestamp().toInstant().getEpochSecond()).build());
        }
        if (submission.getSubmissionStatus() != null) {
            submissionProtoBuilder
                    .setSubmissionStatusId(submission.getSubmissionStatus().getId());
        }
        if (submission.getSubmissionType() != null) {
            submissionProtoBuilder
                    .setSubmissionTypeId(submission.getSubmissionType().getId());
        }
        if (submission.getScreeningScore() != null) {
            submissionProtoBuilder.setScreeningScore(submission.getScreeningScore());
        }
        if (submission.getInitialScore() != null) {
            submissionProtoBuilder.setInitialScore(submission.getInitialScore());
        }
        if (submission.getFinalScore() != null) {
            submissionProtoBuilder.setFinalScore(submission.getFinalScore());
        }
        if (submission.getPlacement() != null) {
            submissionProtoBuilder.setPlacement(submission.getPlacement());
        }
        submissionProtoBuilder.setUserRank(submission.getUserRank());
        submissionProtoBuilder.setMarkForPurchase(submission.isExtra());
        if (submission.getPrize() != null) {
            submissionProtoBuilder.setPrizeId(submission.getPrize().getId());
        }
        if (submission.getUpload() != null) {
            submissionProtoBuilder
                    .setUploadId(submission.getUpload().getId());
        }
        if (submission.getThurgoodJobId() != null) {
            submissionProtoBuilder
                    .setThurgoodJobId(submission.getThurgoodJobId());
        }
        return submissionProtoBuilder.build();
    }

    private SubmissionImageProto buildSubmissionImageProto(SubmissionImage submissionImage) {
        SubmissionImageProto.Builder sBuilder = SubmissionImageProto.newBuilder();
        sBuilder.setSubmissionId(submissionImage.getSubmissionId());
        sBuilder.setImageId(submissionImage.getImageId());
        sBuilder.setSortOrder(submissionImage.getSortOrder());
        if (submissionImage.getCreateDate() != null) {
            sBuilder.setCreateDate(
                    Timestamp.newBuilder().setSeconds(submissionImage.getCreateDate().toInstant().getEpochSecond())
                            .build());
        }
        if (submissionImage.getModifyDate() != null) {
            sBuilder.setModifyDate(
                    Timestamp.newBuilder().setSeconds(submissionImage.getModifyDate().toInstant().getEpochSecond())
                            .build());
        }
        return sBuilder.build();
    }

    private UploadType loadUploadType(EntityProto e) {
        UploadType entity = new UploadType();
        loadNamedStructure(entity, e);
        return entity;
    }

    private UploadStatus loadUploadStatus(EntityProto e) {
        UploadStatus entity = new UploadStatus();
        loadNamedStructure(entity, e);
        return entity;
    }

    private SubmissionType loadSubmissionType(EntityProto e) {
        SubmissionType entity = new SubmissionType();
        loadNamedStructure(entity, e);
        return entity;
    }

    private SubmissionStatus loadSubmissionStatus(EntityProto e) {
        SubmissionStatus entity = new SubmissionStatus();
        loadNamedStructure(entity, e);
        return entity;
    }

    private void loadNamedStructure(NamedDeliverableStructure entity, EntityProto e) {
        if (e.hasId()) {
            entity.setId(e.getId());
        }
        if (e.hasName()) {
            entity.setName(e.getName());
        }
        if (e.hasDescription()) {
            entity.setDescription(e.getDescription());
        }
        if (e.hasCreateUser()) {
            entity.setCreationUser(e.getCreateUser());
        }
        if (e.hasCreateDate()) {
            entity.setCreationTimestamp(new Date(e.getCreateDate().getSeconds() * 1000));
        }
        if (e.hasModifyUser()) {
            entity.setModificationUser(e.getModifyUser());
        }
        if (e.hasModifyDate()) {
            entity.setModificationTimestamp(new Date(e.getModifyDate().getSeconds() * 1000));
        }
    }

    private Upload loadUpload(UploadProto u) {
        Upload upload = new Upload();
        if (u.hasUploadId()) {
            upload.setId(u.getUploadId());
        }
        if (u.hasCreateUser()) {
            upload.setCreationUser(u.getCreateUser());
        }
        if (u.hasCreateDate()) {
            upload.setCreationTimestamp(new Date(u.getCreateDate().getSeconds() * 1000));
        }
        if (u.hasModifyUser()) {
            upload.setModificationUser(u.getModifyUser());
        }
        if (u.hasModifyDate()) {
            upload.setModificationTimestamp(new Date(u.getModifyDate().getSeconds() * 1000));
        }
        if (u.hasProjectId()) {
            upload.setProject(u.getProjectId());
        }
        if (u.hasProjectPhaseId()) {
            upload.setProjectPhase(u.getProjectPhaseId());
        }
        if (u.hasResourceId()) {
            upload.setOwner(u.getResourceId());
        }
        if (u.hasParameter()) {
            upload.setParameter(u.getParameter());
        }
        if (u.hasUploadDesc()) {
            upload.setDescription(u.getUploadDesc());
        }
        if (u.hasUrl()) {
            upload.setUrl(u.getUrl());
        }
        return upload;
    }

    private Submission loadSubmission(SubmissionProto s) {
        Submission submission = new Submission();
        if (s.hasSubmissionId()) {
            submission.setId(s.getSubmissionId());
        }
        if (s.hasScreeningScore()) {
            submission.setScreeningScore(s.getScreeningScore());
        }
        if (s.hasInitialScore()) {
            submission.setInitialScore(s.getInitialScore());
        }
        if (s.hasFinalScore()) {
            submission.setFinalScore(s.getFinalScore());
        }
        if (s.hasPlacement()) {
            submission.setPlacement(s.getPlacement());
        }
        if (s.hasMarkForPurchase()) {
            submission.setExtra(s.getMarkForPurchase());
        }
        if (s.hasThurgoodJobId()) {
            submission.setThurgoodJobId(s.getThurgoodJobId());
        }
        if (s.hasUserRank()) {
            submission.setUserRank(s.getUserRank());
        }
        if (s.hasCreateUser()) {
            submission.setCreationUser(s.getCreateUser());
        }
        if (s.hasCreateDate()) {
            submission.setCreationTimestamp(new Date(s.getCreateDate().getSeconds() * 1000));
        }
        if (s.hasModifyUser()) {
            submission.setModificationUser(s.getModifyUser());
        }
        if (s.hasModifyDate()) {
            submission.setModificationTimestamp(new Date(s.getModifyDate().getSeconds() * 1000));
        }
        return submission;
    }

    private Prize loadPrize(PrizeProto p) {
        Prize prize = new Prize();
        if (p.hasPrizeId()) {
            prize.setId(p.getPrizeId());
        }
        if (p.hasPlace()) {
            prize.setPlace(p.getPlace());
        }
        if (p.hasPrizeAmount()) {
            prize.setPrizeAmount(p.getPrizeAmount());
        }
        if (p.hasNumberOfSubmissions()) {
            prize.setNumberOfSubmissions(p.getNumberOfSubmissions());
        }
        if (p.hasCreateUser()) {
            prize.setCreationUser(p.getCreateUser());
        }
        if (p.hasCreateDate()) {
            prize.setCreationTimestamp(new Date(p.getCreateDate().getSeconds() * 1000));
        }
        if (p.hasModifyUser()) {
            prize.setModificationUser(p.getModifyUser());
        }
        if (p.hasModifyDate()) {
            prize.setModificationTimestamp(new Date(p.getModifyDate().getSeconds() * 1000));
        }
        return prize;
    }

    private PrizeType loadPrizeType(PrizeTypeProto p) {
        PrizeType prizeType = new PrizeType();
        if (p.hasPrizeTypeId()) {
            prizeType.setId(p.getPrizeTypeId());
        }
        if (p.hasPrizeTypeDesc()) {
            prizeType.setDescription(p.getPrizeTypeDesc());
        }
        return prizeType;
    }

    private MimeType loadMimeType(MimeTypeProto m) {
        MimeType mimeType = new MimeType();
        if (m.hasMimeTypeId()) {
            mimeType.setId(m.getMimeTypeId());
        }
        if (m.hasMimeTypeDesc()) {
            mimeType.setDescription(m.getMimeTypeDesc());
        }
        FileType fileType = new FileType();
        if (m.hasFileTypeId()) {
            fileType.setId(m.getFileTypeId());
        }
        if (m.hasFileTypeDesc()) {
            fileType.setDescription(m.getFileTypeDesc());
        }
        if (m.hasSort()) {
            fileType.setSort(m.getSort());
        }
        if (m.hasImageFileInd()) {
            fileType.setImageFile(m.getImageFileInd());
        }
        if (m.hasExtension()) {
            fileType.setExtension(m.getExtension());
        }
        if (m.hasBundledFileInd()) {
            fileType.setBundledFile(m.getBundledFileInd());
        }
        mimeType.setFileType(fileType);
        return mimeType;
    }

    private SubmissionImage loadSubmissionImage(SubmissionImageProto sip) {
        SubmissionImage si = new SubmissionImage();
        if (sip.hasSubmissionId()) {
            si.setSubmissionId(sip.getSubmissionId());
        }
        if (sip.hasImageId()) {
            si.setImageId(sip.getImageId());
        }
        if (sip.hasSortOrder()) {
            si.setSortOrder(sip.getSortOrder());
        }
        if (sip.hasCreateDate()) {
            si.setCreateDate(new Date(sip.getCreateDate().getSeconds() * 1000));
        }
        if (sip.hasModifyDate()) {
            si.setModifyDate(new Date(sip.getModifyDate().getSeconds() * 1000));
        }
        return si;
    }
}
