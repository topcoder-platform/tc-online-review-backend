package com.topcoder.onlinereview.component.grpcclient.resource;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.component.resource.NotificationType;
import com.topcoder.onlinereview.component.resource.Resource;
import com.topcoder.onlinereview.component.resource.ResourceRole;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.grpc.resource.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class ResourceServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private ResourceServiceGrpc.ResourceServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = ResourceServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public ResourceProto getResource(long resourceId) {
        ResourceIdProto request = ResourceIdProto.newBuilder().setResourceId(resourceId).build();
        ResourceProto resource = stub.getResource(request);
        if (resource.hasResourceId()) {
            return resource;
        }
        return null;
    }

    public List<ResourceProto> getAllResources(Long[] resourceIds) {
        ResourceIdsProto request = ResourceIdsProto.newBuilder().addAllResourceIds(Arrays.asList(resourceIds)).build();
        GetResourcesResponse response = stub.getAllResources(request);
        return response.getResourcesList();
    }

    public List<ResourceProto> getResourcesByProjects(Long[] projectIds, long userId) {
        GetResourcesByProjectsRequest request = GetResourcesByProjectsRequest.newBuilder()
                .addAllProjectIds(Arrays.asList(projectIds)).setUserId(userId).build();
        GetResourcesResponse response = stub.getResourcesByProjects(request);
        return response.getResourcesList();
    }

    public List<ResourceSubmissionProto> getResourceSubmissions(Long[] resourceIds) {
        ResourceIdsProto request = ResourceIdsProto.newBuilder().addAllResourceIds(Arrays.asList(resourceIds)).build();
        GetSubmissionsResponse response = stub.getResourceSubmissions(request);
        return response.getSubmissionsList();
    }

    public List<ResourceInfoProto> getResourceInfo(long resourceId) {
        ResourceIdProto request = ResourceIdProto.newBuilder().setResourceId(resourceId).build();
        GetResourceInfoResponse response = stub.getResourceInfo(request);
        return response.getResourceInfoList();
    }

    public List<ResourceInfoProto> getAllResourceInfo(Long[] resourceIds) {
        ResourceIdsProto request = ResourceIdsProto.newBuilder().addAllResourceIds(Arrays.asList(resourceIds)).build();
        GetResourceInfoResponse response = stub.getAllResourceInfo(request);
        return response.getResourceInfoList();
    }

    public Integer getResourceInfoTypeId(String name) {
        GetResourceInfoTypeIdRequest request = GetResourceInfoTypeIdRequest.newBuilder().setName(name).build();
        GetResourceInfoTypeIdResponse response = stub.getResourceInfoTypeId(request);
        if (response.hasResourceInfoTypeId()) {
            return response.getResourceInfoTypeId();
        }
        return null;
    }

    public List<ResourceRoleProto> getResourceRoles() {
        GetResourceRolesReponse response = stub.getResourceRoles(null);
        return response.getResourceRolesList();
    }

    public NotificationProto getNotification(long user, long project, long notificationType) {
        NotificationProto request = NotificationProto.newBuilder().setExternalRefId(user).setProjectId(project)
                .setNotificationTypeId(notificationType).build();
        NotificationProto response = stub.getNotification(request);
        if (response.hasProjectId()) {
            return response;
        }
        return null;
    }

    public List<NotificationProto> getAllNotifications(Long[] userIds, Long[] projectIds, Long[] notificationTypes) {
        GetAllNotificationsRequest.Builder request = GetAllNotificationsRequest.newBuilder();
        for (int i = 0; i < userIds.length; i++) {
            request.addNotifications(NotificationProto.newBuilder().setExternalRefId(userIds[i])
                    .setProjectId(projectIds[i]).setNotificationTypeId(notificationTypes[i]).build());
        }
        GetAllNotificationsResponse response = stub.getAllNotifications(request.build());
        return response.getNotificationsList();
    }

    public NotificationTypeProto getNotificationType(long notificationTypeId) {
        NotificationTypeIdProto request = NotificationTypeIdProto.newBuilder().setNotificationTypeId(notificationTypeId)
                .build();
        NotificationTypeProto response = stub.getNotificationType(request);
        if (response.hasNotificationTypeId()) {
            return response;
        }
        return null;
    }

    public List<NotificationTypeProto> getAllNotificationType(Long[] notificationTypeIds) {
        NotificationTypeIdsProto request = NotificationTypeIdsProto.newBuilder()
                .addAllNotificationTypeIds(Arrays.asList(notificationTypeIds)).build();
        GetNotificationTypesResponse response = stub.getAllNotificationType(request);
        return response.getNotificationTypesList();
    }

    public long createResource(Resource resource) {
        ResourceIdProto response = stub.createResource(buildResource(resource));
        return response.getResourceId();
    }

    public int createResourceSubmission(Resource resource, Long submission) {
        CountProto response = stub.createResourceSubmission(buildResourceSubmission(resource, submission, false));
        return response.getCount();
    }

    public int createResourceInfo(Resource resource, int resourceInfoTypeId, String value) {
        CountProto response = stub.createResourceInfo(buildResourceInfo(resource, resourceInfoTypeId, value));
        return response.getCount();
    }

    public long createResourceRole(ResourceRole resourceRole) {
        ResourceRoleIdProto response = stub.createResourceRole(buildResourceRole(resourceRole));
        return response.getResourceRoleId();
    }

    public int createNotification(Long user, Long project, Long notificationType, String operator, Date date) {
        CountProto response = stub
                .createNotification(buildNotification(user, project, notificationType, operator, date, false));
        return response.getCount();
    }

    public long createNotificationType(NotificationType notificationType) {
        NotificationTypeIdProto response = stub.createNotificationType(buildNotificationType(notificationType));
        return response.getNotificationTypeId();
    }

    public int updateResource(Resource resource) {
        CountProto response = stub.updateResource(buildResource(resource));
        return response.getCount();
    }

    public int updateResourceSubmission(Resource resource, Long submission) {
        CountProto response = stub.updateResourceSubmission(buildResourceSubmission(resource, submission, false));
        return response.getCount();
    }

    public int updateResourceInfo(long resourceId, int resourceInfoTypeId, String value) {
        ResourceInfoProto.Builder request = ResourceInfoProto.newBuilder().setResourceId(resourceId)
                .setResourceInfoTypeId(resourceInfoTypeId);
        if (value != null) {
            request.setValue(value);
        }
        CountProto response = stub.updateResourceInfo(request.build());
        return response.getCount();
    }

    public int updateResourceRole(ResourceRole resourceRole) {
        CountProto response = stub.updateResourceRole(buildResourceRole(resourceRole));
        return response.getCount();
    }

    public int updateNotificationType(NotificationType notificationType) {
        CountProto response = stub.updateNotificationType(buildNotificationType(notificationType));
        return response.getCount();
    }

    public int deleteResource(long resourceId) {
        ResourceIdProto request = ResourceIdProto.newBuilder().setResourceId(resourceId).build();
        CountProto response = stub.deleteResource(request);
        return response.getCount();
    }

    public int deleteResourceSubmission(Resource resource, Long submission) {
        ResourceSubmissionProto request = buildResourceSubmission(resource, submission, true);
        CountProto response = stub.deleteResourceSubmission(request);
        return response.getCount();
    }

    public int deleteResourceInfo(long resourceId, int resourceInfoTypeId) {
        ResourceInfoProto request = ResourceInfoProto.newBuilder().setResourceId(resourceId)
                .setResourceInfoTypeId(resourceInfoTypeId).build();
        CountProto response = stub.deleteResourceInfo(request);
        return response.getCount();
    }

    public int deleteResourceRole(long resourceRoleId) {
        ResourceRoleIdProto request = ResourceRoleIdProto.newBuilder().setResourceRoleId(resourceRoleId).build();
        CountProto response = stub.deleteResourceRole(request);
        return response.getCount();
    }

    public int deleteNotification(long user, long project, long notificationType) {
        CountProto response = stub
                .deleteNotification(buildNotification(user, project, notificationType, null, null, true));
        return response.getCount();
    }

    public int deleteNotificationType(long notificationTypeId) {
        NotificationTypeIdProto request = NotificationTypeIdProto.newBuilder().setNotificationTypeId(notificationTypeId)
                .build();
        CountProto response = stub.deleteNotificationType(request);
        return response.getCount();
    }

    public int auditProjectUser(Long projectId, long resourceUserId, long resourceRoleId, int auditType,
            Date actionDate, long actionUserId) {
        AuditProjectUserRequest.Builder request = AuditProjectUserRequest.newBuilder().setResourceUserId(resourceUserId)
                .setResourceRoleId(resourceRoleId).setAuditActionTypeId(auditType)
                .setActionDate(Timestamp.newBuilder().setSeconds(actionDate.toInstant().getEpochSecond()))
                .setActionUserId(actionUserId);
        if (projectId != null) {
            request.setProjectId(projectId);
        }
        CountProto response = stub.auditProjectUser(request.build());
        return response.getCount();
    }

    public List<ResourceProto> searchResources(Filter filter) {
        FilterProto filterProto = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        GetResourcesResponse response = stub.searchResources(filterProto);
        return response.getResourcesList();
    }

    public List<ResourceRoleProto> searchResourceRoles(Filter filter) {
        FilterProto filterProto = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        GetResourceRolesReponse response = stub.searchResourceRoles(filterProto);
        return response.getResourceRolesList();
    }

    public List<NotificationProto> searchNotifications(Filter filter) {
        FilterProto filterProto = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        GetAllNotificationsResponse response = stub.searchNotifications(filterProto);
        return response.getNotificationsList();
    }

    public List<NotificationTypeProto> searchNotificationTypes(Filter filter) {
        FilterProto filterProto = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        GetNotificationTypesResponse response = stub.searchNotificationTypes(filterProto);
        return response.getNotificationTypesList();
    }

    private ResourceProto buildResource(Resource resource) {
        ResourceProto.Builder request = ResourceProto.newBuilder().setResourceId(resource.getId());
        if (resource.getResourceRole() != null) {
            request.setResourceRoleId(resource.getResourceRole().getId());
        }
        if (resource.getProject() != null) {
            request.setProjectId(resource.getProject());
        }
        if (resource.getPhase() != null) {
            request.setProjectPhaseId(resource.getPhase());
        }
        if (resource.getUserId() != null) {
            request.setUserId(resource.getUserId());
        }
        if (resource.getCreationUser() != null) {
            request.setCreateUser(resource.getCreationUser());
        }
        if (resource.getCreationTimestamp() != null) {
            request.setCreateDate(
                    Timestamp.newBuilder().setSeconds(resource.getCreationTimestamp().toInstant().getEpochSecond()));
        }
        if (resource.getModificationUser() != null) {
            request.setModifyUser(resource.getModificationUser());
        }
        if (resource.getModificationTimestamp() != null) {
            request.setModifyDate(Timestamp.newBuilder()
                    .setSeconds(resource.getModificationTimestamp().toInstant().getEpochSecond()));
        }
        return request.build();
    }

    private ResourceSubmissionProto buildResourceSubmission(Resource resource, Long submission, boolean isDelete) {
        ResourceSubmissionProto.Builder request = ResourceSubmissionProto.newBuilder().setResourceId(resource.getId());
        if (submission != null) {
            request.setSubmissionId(submission);
        }
        if (!isDelete) {
            if (resource.getCreationUser() != null) {
                request.setCreateUser(resource.getCreationUser());
            }
            if (resource.getCreationTimestamp() != null) {
                request.setCreateDate(
                        Timestamp.newBuilder()
                                .setSeconds(resource.getCreationTimestamp().toInstant().getEpochSecond()));
            }
            if (resource.getModificationUser() != null) {
                request.setModifyUser(resource.getModificationUser());
            }
            if (resource.getModificationTimestamp() != null) {
                request.setModifyDate(Timestamp.newBuilder()
                        .setSeconds(resource.getModificationTimestamp().toInstant().getEpochSecond()));
            }
        }
        return request.build();
    }

    private ResourceInfoProto buildResourceInfo(Resource resource, int resourceInfoTypeId, String value) {
        ResourceInfoProto.Builder request = ResourceInfoProto.newBuilder().setResourceId(resource.getId())
                .setResourceInfoTypeId(resourceInfoTypeId);
        if (value != null) {
            request.setValue(value);
        }
        if (resource.getCreationUser() != null) {
            request.setCreateUser(resource.getCreationUser());
        }
        if (resource.getCreationTimestamp() != null) {
            request.setCreateDate(
                    Timestamp.newBuilder()
                            .setSeconds(resource.getCreationTimestamp().toInstant().getEpochSecond()));
        }
        if (resource.getModificationUser() != null) {
            request.setModifyUser(resource.getModificationUser());
        }
        if (resource.getModificationTimestamp() != null) {
            request.setModifyDate(Timestamp.newBuilder()
                    .setSeconds(resource.getModificationTimestamp().toInstant().getEpochSecond()));
        }
        return request.build();
    }

    private ResourceRoleProto buildResourceRole(ResourceRole resourceRole) {
        ResourceRoleProto.Builder request = ResourceRoleProto.newBuilder().setResourceRoleId(resourceRole.getId());
        if (resourceRole.getName() != null) {
            request.setName(resourceRole.getName());
        }
        if (resourceRole.getDescription() != null) {
            request.setDescription(resourceRole.getDescription());
        }
        if (resourceRole.getPhaseType() != null) {
            request.setPhaseTypeId(resourceRole.getPhaseType());
        }
        if (resourceRole.getCreationUser() != null) {
            request.setCreateUser(resourceRole.getCreationUser());
        }
        if (resourceRole.getCreationTimestamp() != null) {
            request.setCreateDate(
                    Timestamp.newBuilder()
                            .setSeconds(resourceRole.getCreationTimestamp().toInstant().getEpochSecond()));
        }
        if (resourceRole.getModificationUser() != null) {
            request.setModifyUser(resourceRole.getModificationUser());
        }
        if (resourceRole.getModificationTimestamp() != null) {
            request.setModifyDate(Timestamp.newBuilder()
                    .setSeconds(resourceRole.getModificationTimestamp().toInstant().getEpochSecond()));
        }
        return request.build();
    }

    private NotificationProto buildNotification(Long user, Long project, Long notificationType, String operator,
            Date date, boolean isDelete) {
        NotificationProto.Builder request = NotificationProto.newBuilder();
        if (user != null) {
            request.setExternalRefId(user);
        }
        if (project != null) {
            request.setProjectId(project);
        }
        if (notificationType != null) {
            request.setNotificationTypeId(notificationType);
        }
        if (!isDelete) {
            if (operator != null) {
                request.setCreateUser(operator);
                request.setModifyUser(operator);
            }
            if (date != null) {
                request.setCreateDate(
                        Timestamp.newBuilder()
                                .setSeconds(date.toInstant().getEpochSecond()));
                request.setModifyDate(Timestamp.newBuilder()
                        .setSeconds(date.toInstant().getEpochSecond()));
            }
        }
        return request.build();
    }

    private NotificationTypeProto buildNotificationType(NotificationType notificationType) {
        NotificationTypeProto.Builder request = NotificationTypeProto.newBuilder()
                .setNotificationTypeId(notificationType.getId());
        if (notificationType.getName() != null) {
            request.setName(notificationType.getName());
        }
        if (notificationType.getDescription() != null) {
            request.setDescription(notificationType.getDescription());
        }
        if (notificationType.getCreationUser() != null) {
            request.setCreateUser(notificationType.getCreationUser());
        }
        if (notificationType.getCreationTimestamp() != null) {
            request.setCreateDate(
                    Timestamp.newBuilder()
                            .setSeconds(notificationType.getCreationTimestamp().toInstant().getEpochSecond()));
        }
        if (notificationType.getModificationUser() != null) {
            request.setModifyUser(notificationType.getModificationUser());
        }
        if (notificationType.getModificationTimestamp() != null) {
            request.setModifyDate(Timestamp.newBuilder()
                    .setSeconds(notificationType.getModificationTimestamp().toInstant().getEpochSecond()));
        }
        return request.build();
    }
}
