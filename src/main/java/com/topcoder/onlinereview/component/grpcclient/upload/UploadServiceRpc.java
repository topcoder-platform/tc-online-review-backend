package com.topcoder.onlinereview.component.grpcclient.upload;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Component;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
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
import com.topcoder.onlinereview.grpc.upload.proto.*;
import com.topcoder.onlinereview.component.project.management.FileType;
import com.topcoder.onlinereview.component.project.management.Prize;
import com.topcoder.onlinereview.component.project.management.PrizeType;
import com.topcoder.onlinereview.component.search.filter.Filter;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Component
public class UploadServiceRpc {

    @GrpcClient("UploadServiceRpc")
    private UploadServiceGrpc.UploadServiceBlockingStub stub;

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
                .setId(Int64Value.of(id)).build();
        stub.removeUploadType(entitiyRemoveRequest);
    }

    public void removeUploadStatus(long id) {
        IdProto entitiyRemoveRequest = IdProto.newBuilder()
                .setId(Int64Value.of(id)).build();
        stub.removeUploadStatus(entitiyRemoveRequest);
    }

    public void removeSubmissionType(long id) {
        IdProto entitiyRemoveRequest = IdProto.newBuilder()
                .setId(Int64Value.of(id)).build();
        stub.removeSubmissionType(entitiyRemoveRequest);
    }

    public void removeSubmissionStatus(long id) {
        IdProto entitiyRemoveRequest = IdProto.newBuilder()
                .setId(Int64Value.of(id)).build();
        stub.removeSubmissionStatus(entitiyRemoveRequest);
    }

    public void removeUpload(long id) {
        IdProto entitiyRemoveRequest = IdProto.newBuilder()
                .setId(Int64Value.of(id)).build();
        stub.removeUpload(entitiyRemoveRequest);
    }

    public void removeSubmission(long id) {
        IdProto entitiyRemoveRequest = IdProto.newBuilder()
                .setId(Int64Value.of(id)).build();
        stub.removeSubmission(entitiyRemoveRequest);
    }

    public void removeSubmissionImage(SubmissionImage submissionImage) {
        RemoveSubmissionImageRequest request = RemoveSubmissionImageRequest.newBuilder()
                .setSubmissionId(Int64Value.of(submissionImage.getSubmissionId()))
                .setImageId(Int32Value.of(submissionImage.getImageId())).build();
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
        IdProto request = IdProto.newBuilder().setId(Int64Value.of(submissionId)).build();
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
        EntityProto.Builder eBuilder = EntityProto.newBuilder().setId(Int64Value.of(entity.getId()));
        if (entity.getCreationUser() != null) {
            eBuilder.setCreateUser(StringValue.of(entity.getCreationUser()));
        }
        if (entity.getCreationTimestamp() != null) {
            eBuilder.setCreateDate(Timestamp.newBuilder()
                    .setSeconds(entity.getCreationTimestamp().toInstant().getEpochSecond()).build());
        }
        if (entity.getModificationUser() != null) {
            eBuilder.setModifyUser(StringValue.of(entity.getModificationUser()));
        }
        if (entity.getModificationTimestamp() != null) {
            eBuilder.setModifyDate(
                    Timestamp.newBuilder().setSeconds(entity.getModificationTimestamp().toInstant().getEpochSecond())
                            .build());
        }
        if (entity.getName() != null) {
            eBuilder.setName(StringValue.of(entity.getName()));
        }
        if (entity.getDescription() != null) {
            eBuilder.setDescription(StringValue.of(entity.getDescription()));
        }
        return eBuilder.build();
    }

    private UploadProto buildUploadProto(Upload upload) {
        UploadProto.Builder uploadProtoBuilder = UploadProto.newBuilder()
                .setUploadId(Int64Value.of(upload.getId()));
        if (upload.getCreationUser() != null) {
            uploadProtoBuilder.setCreateUser(StringValue.of(upload.getCreationUser()));
        }
        if (upload.getCreationTimestamp() != null) {
            uploadProtoBuilder
                    .setCreateDate(Timestamp.newBuilder()
                            .setSeconds(upload.getCreationTimestamp().toInstant().getEpochSecond()).build());
        }
        if (upload.getModificationUser() != null) {
            uploadProtoBuilder.setModifyUser(StringValue.of(upload.getModificationUser()));
        }
        if (upload.getModificationTimestamp() != null) {
            uploadProtoBuilder.setModifyDate(
                    Timestamp.newBuilder().setSeconds(upload.getModificationTimestamp().toInstant().getEpochSecond())
                            .build());
        }

        uploadProtoBuilder.setProjectId(Int64Value.of(upload.getProject()));
        uploadProtoBuilder.setProjectPhaseId(Int64Value.of(upload.getProjectPhase()));
        uploadProtoBuilder.setResourceId(Int64Value.of(upload.getOwner()));
        if (upload.getUploadType() != null) {
            uploadProtoBuilder.setUploadTypeId(Int64Value.of(upload.getUploadType().getId()));
        }
        if (upload.getUploadStatus() != null) {
            uploadProtoBuilder.setUploadStatusId(Int64Value.of(upload.getUploadStatus().getId()));
        }

        if (upload.getParameter() != null) {
            uploadProtoBuilder.setParameter(StringValue.of(upload.getParameter()));
        }
        if (upload.getDescription() != null) {
            uploadProtoBuilder.setUploadDesc(StringValue.of(upload.getDescription()));
        }
        return uploadProtoBuilder.build();
    }

    private SubmissionProto buildSubmissionProto(Submission submission) {
        SubmissionProto.Builder submissionProtoBuilder = SubmissionProto.newBuilder()
                .setSubmissionId(Int64Value.of(submission.getId()));
        if (submission.getCreationUser() != null) {
            submissionProtoBuilder.setCreateUser(StringValue.of(submission.getCreationUser()));
        }
        if (submission.getCreationTimestamp() != null) {
            submissionProtoBuilder.setCreateDate(
                    Timestamp.newBuilder().setSeconds(submission.getCreationTimestamp().toInstant().getEpochSecond())
                            .build());
        }
        if (submission.getModificationUser() != null) {
            submissionProtoBuilder.setModifyUser(StringValue.of(submission.getModificationUser()));
        }
        if (submission.getModificationTimestamp() != null) {
            submissionProtoBuilder.setModifyDate(
                    Timestamp.newBuilder()
                            .setSeconds(submission.getModificationTimestamp().toInstant().getEpochSecond()).build());
        }
        if (submission.getSubmissionStatus() != null) {
            submissionProtoBuilder
                    .setSubmissionStatusId(Int64Value.of(submission.getSubmissionStatus().getId()));
        }
        if (submission.getSubmissionType() != null) {
            submissionProtoBuilder
                    .setSubmissionTypeId(Int64Value.of(submission.getSubmissionType().getId()));
        }
        if (submission.getScreeningScore() != null) {
            submissionProtoBuilder.setScreeningScore(DoubleValue.of(submission.getScreeningScore()));
        }
        if (submission.getInitialScore() != null) {
            submissionProtoBuilder.setInitialScore(DoubleValue.of(submission.getInitialScore()));
        }
        if (submission.getFinalScore() != null) {
            submissionProtoBuilder.setFinalScore(DoubleValue.of(submission.getFinalScore()));
        }
        if (submission.getPlacement() != null) {
            submissionProtoBuilder.setPlacement(Int64Value.of(submission.getPlacement()));
        }
        submissionProtoBuilder.setUserRank(Int32Value.of(submission.getUserRank()));
        submissionProtoBuilder.setMarkForPurchase(BoolValue.of(submission.isExtra()));
        if (submission.getPrize() != null) {
            submissionProtoBuilder.setPrizeId(Int64Value.of(submission.getPrize().getId()));
        }
        if (submission.getUpload() != null) {
            submissionProtoBuilder
                    .setUploadId(Int64Value.of(submission.getUpload().getId()));
        }
        if (submission.getThurgoodJobId() != null) {
            submissionProtoBuilder
                    .setThurgoodJobId(StringValue.of(submission.getThurgoodJobId()));
        }
        return submissionProtoBuilder.build();
    }

    private SubmissionImageProto buildSubmissionImageProto(SubmissionImage submissionImage) {
        SubmissionImageProto.Builder sBuilder = SubmissionImageProto.newBuilder();
        sBuilder.setSubmissionId(Int64Value.of(submissionImage.getSubmissionId()));
        sBuilder.setImageId(Int32Value.of(submissionImage.getImageId()));
        sBuilder.setSortOrder(Int32Value.of(submissionImage.getSortOrder()));
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
            entity.setId(e.getId().getValue());
        }
        if (e.hasName()) {
            entity.setName(e.getName().getValue());
        }
        if (e.hasDescription()) {
            entity.setDescription(e.getDescription().getValue());
        }
        if (e.hasCreateUser()) {
            entity.setCreationUser(e.getCreateUser().getValue());
        }
        if (e.hasCreateDate()) {
            entity.setCreationTimestamp(new Date(e.getCreateDate().getSeconds() * 1000));
        }
        if (e.hasModifyUser()) {
            entity.setModificationUser(e.getModifyUser().getValue());
        }
        if (e.hasModifyDate()) {
            entity.setModificationTimestamp(new Date(e.getModifyDate().getSeconds() * 1000));
        }
    }

    private Upload loadUpload(UploadProto u) {
        Upload upload = new Upload();
        if (u.hasUploadId()) {
            upload.setId(u.getUploadId().getValue());
        }
        if (u.hasCreateUser()) {
            upload.setCreationUser(u.getCreateUser().getValue());
        }
        if (u.hasCreateDate()) {
            upload.setCreationTimestamp(new Date(u.getCreateDate().getSeconds() * 1000));
        }
        if (u.hasModifyUser()) {
            upload.setModificationUser(u.getModifyUser().getValue());
        }
        if (u.hasModifyDate()) {
            upload.setModificationTimestamp(new Date(u.getModifyDate().getSeconds() * 1000));
        }
        if (u.hasProjectId()) {
            upload.setProject(u.getProjectId().getValue());
        }
        if (u.hasProjectPhaseId()) {
            upload.setProjectPhase(u.getProjectPhaseId().getValue());
        }
        if (u.hasResourceId()) {
            upload.setOwner(u.getResourceId().getValue());
        }
        if (u.hasParameter()) {
            upload.setParameter(u.getParameter().getValue());
        }
        if (u.hasUploadDesc()) {
            upload.setDescription(u.getUploadDesc().getValue());
        }
        if (u.hasUrl()) {
            upload.setUrl(u.getUrl().getValue());
        }
        return upload;
    }

    private Submission loadSubmission(SubmissionProto s) {
        Submission submission = new Submission();
        if (s.hasSubmissionId()) {
            submission.setId(s.getSubmissionId().getValue());
        }
        if (s.hasScreeningScore()) {
            submission.setScreeningScore(s.getScreeningScore().getValue());
        }
        if (s.hasInitialScore()) {
            submission.setInitialScore(s.getInitialScore().getValue());
        }
        if (s.hasFinalScore()) {
            submission.setFinalScore(s.getFinalScore().getValue());
        }
        if (s.hasPlacement()) {
            submission.setPlacement(s.getPlacement().getValue());
        }
        if (s.hasMarkForPurchase()) {
            submission.setExtra(s.getMarkForPurchase().getValue());
        }
        if (s.hasThurgoodJobId()) {
            submission.setThurgoodJobId(s.getThurgoodJobId().getValue());
        }
        if (s.hasUserRank()) {
            submission.setUserRank(s.getUserRank().getValue());
        }
        if (s.hasCreateUser()) {
            submission.setCreationUser(s.getCreateUser().getValue());
        }
        if (s.hasCreateDate()) {
            submission.setCreationTimestamp(new Date(s.getCreateDate().getSeconds() * 1000));
        }
        if (s.hasModifyUser()) {
            submission.setModificationUser(s.getModifyUser().getValue());
        }
        if (s.hasModifyDate()) {
            submission.setModificationTimestamp(new Date(s.getModifyDate().getSeconds() * 1000));
        }
        return submission;
    }

    private Prize loadPrize(PrizeProto p) {
        Prize prize = new Prize();
        if (p.hasPrizeId()) {
            prize.setId(p.getPrizeId().getValue());
        }
        if (p.hasPlace()) {
            prize.setPlace(p.getPlace().getValue());
        }
        if (p.hasPrizeAmount()) {
            prize.setPrizeAmount(p.getPrizeAmount().getValue());
        }
        if (p.hasNumberOfSubmissions()) {
            prize.setNumberOfSubmissions(p.getNumberOfSubmissions().getValue());
        }
        if (p.hasCreateUser()) {
            prize.setCreationUser(p.getCreateUser().getValue());
        }
        if (p.hasCreateDate()) {
            prize.setCreationTimestamp(new Date(p.getCreateDate().getSeconds() * 1000));
        }
        if (p.hasModifyUser()) {
            prize.setModificationUser(p.getModifyUser().getValue());
        }
        if (p.hasModifyDate()) {
            prize.setModificationTimestamp(new Date(p.getModifyDate().getSeconds() * 1000));
        }
        return prize;
    }

    private PrizeType loadPrizeType(PrizeTypeProto p) {
        PrizeType prizeType = new PrizeType();
        if (p.hasPrizeTypeId()) {
            prizeType.setId(p.getPrizeTypeId().getValue());
        }
        if (p.hasPrizeTypeDesc()) {
            prizeType.setDescription(p.getPrizeTypeDesc().getValue());
        }
        return prizeType;
    }

    private MimeType loadMimeType(MimeTypeProto m) {
        MimeType mimeType = new MimeType();
        if (m.hasMimeTypeId()) {
            mimeType.setId(m.getMimeTypeId().getValue());
        }
        if (m.hasMimeTypeDesc()) {
            mimeType.setDescription(m.getMimeTypeDesc().getValue());
        }
        FileType fileType = new FileType();
        if (m.hasFileTypeId()) {
            fileType.setId(m.getFileTypeId().getValue());
        }
        if (m.hasFileTypeDesc()) {
            fileType.setDescription(m.getFileTypeDesc().getValue());
        }
        if (m.hasSort()) {
            fileType.setSort(m.getSort().getValue());
        }
        if (m.hasImageFileInd()) {
            fileType.setImageFile(m.getImageFileInd().getValue());
        }
        if (m.hasExtension()) {
            fileType.setExtension(m.getExtension().getValue());
        }
        if (m.hasBundledFileInd()) {
            fileType.setBundledFile(m.getBundledFileInd().getValue());
        }
        mimeType.setFileType(fileType);
        return mimeType;
    }

    private SubmissionImage loadSubmissionImage(SubmissionImageProto sip) {
        SubmissionImage si = new SubmissionImage();
        if (sip.hasSubmissionId()) {
            si.setSubmissionId(sip.getSubmissionId().getValue());
        }
        if (sip.hasImageId()) {
            si.setImageId(sip.getImageId().getValue());
        }
        if (sip.hasSortOrder()) {
            si.setSortOrder(sip.getSortOrder().getValue());
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
