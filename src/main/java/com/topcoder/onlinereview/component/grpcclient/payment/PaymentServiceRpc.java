package com.topcoder.onlinereview.component.grpcclient.payment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.google.protobuf.ByteString;
import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.component.project.payment.ProjectPayment;
import com.topcoder.onlinereview.component.project.payment.ProjectPaymentAdjustment;
import com.topcoder.onlinereview.component.project.payment.ProjectPaymentType;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.grpc.payment.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class PaymentServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private PaymentServiceGrpc.PaymentServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = PaymentServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public long createPayment(ProjectPayment projectPayment, String operator) {
        CreatePaymentRequest.Builder builder = CreatePaymentRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        if (projectPayment != null) {
            builder.setProjectPayment(buildProjectPayment(projectPayment));
        }
        IdProto response = stub.createPayment(builder.build());
        return response.getId();
    }

    public int updatePayment(ProjectPayment projectPayment, String operator) {
        UpdatePaymentRequest.Builder builder = UpdatePaymentRequest.newBuilder();
        if (operator != null) {
            builder.setOperator(operator);
        }
        if (projectPayment != null) {
            builder.setProjectPayment(buildProjectPayment(projectPayment));
        }
        CountProto response = stub.updatePayment(builder.build());
        return response.getCount();
    }

    public ProjectPayment getPayment(long projectPaymentId) {
        IdProto request = IdProto.newBuilder().setId(projectPaymentId).build();
        ProjectPaymentProto response = stub.getPayment(request);
        if (!response.hasId()) {
            return null;
        }
        ProjectPayment projectPayment = new ProjectPayment();
        projectPayment.setProjectPaymentId(response.getId());
        if (response.hasResourceId()) {
            projectPayment.setResourceId(response.getResourceId());
        }
        if (response.hasSubmissionId()) {
            projectPayment.setSubmissionId(response.getSubmissionId());
        }
        if (response.hasAmount()) {
            BigDecimalProto serialized = response.getAmount();
            projectPayment.setAmount(
                    new BigDecimal(new BigInteger(serialized.getValue().toByteArray()), serialized.getScale(),
                            new MathContext(serialized.getPrecision())));
        }
        if (response.hasPactsPaymentId()) {
            projectPayment.setPactsPaymentId(response.getPactsPaymentId());
        }
        if (response.hasCreateUser()) {
            projectPayment.setCreateUser(response.getCreateUser());
        }
        if (response.hasCreateDate()) {
            projectPayment.setCreateDate(new Date(response.getCreateDate().getSeconds() * 1000));
        }
        if (response.hasModifyUser()) {
            projectPayment.setModifyUser(response.getModifyUser());
        }
        if (response.hasModifyDate()) {
            projectPayment.setModifyDate(new Date(response.getModifyDate().getSeconds() * 1000));
        }
        ProjectPaymentType projectPaymentType = new ProjectPaymentType();
        if (response.getProjectPaymentType().hasId()) {
            projectPaymentType.setProjectPaymentTypeId(response.getProjectPaymentType().getId());
        }
        if (response.getProjectPaymentType().hasName()) {
            projectPaymentType.setName(response.getProjectPaymentType().getName());
        }
        if (response.getProjectPaymentType().hasMergeable()) {
            projectPaymentType.setMergeable(response.getProjectPaymentType().getMergeable());
        }
        projectPayment.setProjectPaymentType(projectPaymentType);
        return projectPayment;
    }

    public int deletePayment(long projectPaymentId) {
        IdProto request = IdProto.newBuilder().setId(projectPaymentId).build();
        CountProto response = stub.deletePayment(request);
        return response.getCount();
    }

    public int createPaymentAdjustment(ProjectPaymentAdjustment projectPaymentAdjustment) {
        CountProto response = stub.createPaymentAdjustment(buildPaymentAdjustmentProto(projectPaymentAdjustment));
        return response.getCount();
    }

    public int updatePaymentAdjustment(ProjectPaymentAdjustment projectPaymentAdjustment) {
        CountProto response = stub.updatePaymentAdjustment(buildPaymentAdjustmentProto(projectPaymentAdjustment));
        return response.getCount();
    }

    public List<ProjectPaymentAdjustment> getPaymentAdjustments(long projectId) {
        IdProto request = IdProto.newBuilder().setId(projectId).build();
        GetPaymentAdjustmentsResponse response = stub.getPaymentAdjustments(request);
        List<ProjectPaymentAdjustment> paymentAdjustments = new ArrayList<>();
        for (ProjectPaymentAdjustmentProto p : response.getPaymentAdjustmentsList()) {
            ProjectPaymentAdjustment paymentAdjustment = new ProjectPaymentAdjustment();
            if (p.hasProjectId()) {
                paymentAdjustment.setProjectId(p.getProjectId());
            }
            if (p.hasResourceRoleId()) {
                paymentAdjustment.setResourceRoleId(p.getResourceRoleId());
            }
            if (p.hasFixedAmount()) {
                BigDecimalProto serialized = p.getFixedAmount();
                paymentAdjustment.setFixedAmount(
                        new BigDecimal(new BigInteger(serialized.getValue().toByteArray()), serialized.getScale(),
                                new MathContext(serialized.getPrecision())));
            }
            if (p.hasMultiplier()) {
                paymentAdjustment.setMultiplier(p.getMultiplier());
            }
            paymentAdjustments.add(paymentAdjustment);
        }
        return paymentAdjustments;
    }

    public List<ProjectPaymentAdjustment> getPaymentAdjustmentsForResource(long projectId, List<Long> resourceRoleIDs) {
        GetPaymentAdjustmentsRequest request = GetPaymentAdjustmentsRequest.newBuilder().setProjectId(projectId)
                .addAllResourceIds(resourceRoleIDs).build();
        GetPaymentAdjustmentsResponse response = stub.getPaymentAdjustmentsForResource(request);
        List<ProjectPaymentAdjustment> paymentAdjustments = new ArrayList<>();
        for (ProjectPaymentAdjustmentProto p : response.getPaymentAdjustmentsList()) {
            ProjectPaymentAdjustment paymentAdjustment = new ProjectPaymentAdjustment();
            if (p.hasResourceRoleId()) {
                paymentAdjustment.setResourceRoleId(p.getResourceRoleId());
            }
            if (p.hasFixedAmount()) {
                BigDecimalProto serialized = p.getFixedAmount();
                paymentAdjustment.setFixedAmount(
                        new BigDecimal(new BigInteger(serialized.getValue().toByteArray()), serialized.getScale(),
                                new MathContext(serialized.getPrecision())));
            }
            if (p.hasMultiplier()) {
                paymentAdjustment.setMultiplier(p.getMultiplier());
            }
            paymentAdjustments.add(paymentAdjustment);
        }
        return paymentAdjustments;
    }

    public boolean isProjectPaymentTypeExists(long projectPaymentId) {
        IdProto request = IdProto.newBuilder().setId(projectPaymentId).build();
        ExistsProto response = stub.isProjectPaymentTypeExists(request);
        return response.getExists();
    }

    public boolean isResourceExists(long resourceId) {
        IdProto request = IdProto.newBuilder().setId(resourceId).build();
        ExistsProto response = stub.isResourceExists(request);
        return response.getExists();
    }

    public boolean isSubmissionExists(long submissionId) {
        IdProto request = IdProto.newBuilder().setId(submissionId).build();
        ExistsProto response = stub.isSubmissionExists(request);
        return response.getExists();
    }

    public List<DefaultPaymentProto> getDefaultPayments(long projectId) {
        GetDefaultPaymentsRequest request = GetDefaultPaymentsRequest.newBuilder().setProjectId(projectId).build();
        GetDefaultPaymentsResponse response = stub.getDefaultPayments(request);
        return response.getDefaultPaymentsList();
    }

    public DefaultPaymentProto getDefaultPayment(long projectCategoryId, long resourceRoleId) {
        GetDefaultPaymentRequest request = GetDefaultPaymentRequest.newBuilder().setProjectCategoryId(projectCategoryId)
                .setResourceRoleId(resourceRoleId).build();
        GetDefaultPaymentResponse response = stub.getDefaultPayment(request);
        if (response.hasDefaultPayment()) {
            return response.getDefaultPayment();
        }
        return null;
    }

    public List<ProjectPayment> searchPayments(Filter filter) {
        FilterProto request = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        SearchPaymentsResponse response = stub.searchPayments(request);
        List<ProjectPayment> projectPayments = new ArrayList<>();
        for (ProjectPaymentProto pp : response.getPaymentsList()) {
            ProjectPayment projectPayment = new ProjectPayment();
            projectPayment.setProjectPaymentId(pp.getId());
            if (pp.hasResourceId()) {
                projectPayment.setResourceId(pp.getResourceId());
            }
            if (pp.hasSubmissionId()) {
                projectPayment.setSubmissionId(pp.getSubmissionId());
            }
            if (pp.hasAmount()) {
                BigDecimalProto serialized = pp.getAmount();
                projectPayment.setAmount(
                        new BigDecimal(new BigInteger(serialized.getValue().toByteArray()), serialized.getScale(),
                                new MathContext(serialized.getPrecision())));
            }
            if (pp.hasPactsPaymentId()) {
                projectPayment.setPactsPaymentId(pp.getPactsPaymentId());
            }
            if (pp.hasCreateUser()) {
                projectPayment.setCreateUser(pp.getCreateUser());
            }
            if (pp.hasCreateDate()) {
                projectPayment.setCreateDate(new Date(pp.getCreateDate().getSeconds() * 1000));
            }
            if (pp.hasModifyUser()) {
                projectPayment.setModifyUser(pp.getModifyUser());
            }
            if (pp.hasModifyDate()) {
                projectPayment.setModifyDate(new Date(pp.getModifyDate().getSeconds() * 1000));
            }
            ProjectPaymentType projectPaymentType = new ProjectPaymentType();
            if (pp.getProjectPaymentType().hasId()) {
                projectPaymentType.setProjectPaymentTypeId(pp.getProjectPaymentType().getId());
            }
            if (pp.getProjectPaymentType().hasName()) {
                projectPaymentType.setName(pp.getProjectPaymentType().getName());
            }
            if (pp.getProjectPaymentType().hasMergeable()) {
                projectPaymentType.setMergeable(pp.getProjectPaymentType().getMergeable());
            }
            if (pp.getProjectPaymentType().hasPactsPaymentTypeId()) {
                projectPaymentType.setPactsPaymentTypeId(pp.getProjectPaymentType().getPactsPaymentTypeId());
            }
            projectPayment.setProjectPaymentType(projectPaymentType);
            projectPayments.add(projectPayment);
        }
        return projectPayments;
    }

    private ProjectPaymentProto buildProjectPayment(ProjectPayment projectPayment) {
        ProjectPaymentProto.Builder pBuilder = ProjectPaymentProto.newBuilder();
        if (projectPayment.getProjectPaymentId() != null) {
            pBuilder.setId(projectPayment.getProjectPaymentId());
        }
        if (projectPayment.getResourceId() != null) {
            pBuilder.setResourceId(projectPayment.getResourceId());
        }
        if (projectPayment.getSubmissionId() != null) {
            pBuilder.setSubmissionId(projectPayment.getSubmissionId());
        }
        if (projectPayment.getPactsPaymentId() != null) {
            pBuilder.setPactsPaymentId(projectPayment.getPactsPaymentId());
        }
        if (projectPayment.getAmount() != null) {
            pBuilder.setAmount(BigDecimalProto.newBuilder()
                    .setScale(projectPayment.getAmount().scale())
                    .setPrecision(projectPayment.getAmount().precision())
                    .setValue(ByteString.copyFrom(projectPayment.getAmount().unscaledValue().toByteArray()))
                    .build());
        }
        if (projectPayment.getProjectPaymentType() != null) {
            ProjectPaymentTypeProto.Builder ptBuilder = ProjectPaymentTypeProto.newBuilder();
            if (projectPayment.getProjectPaymentType().getProjectPaymentTypeId() != null) {
                ptBuilder.setId(projectPayment.getProjectPaymentType().getProjectPaymentTypeId());
            }
            pBuilder.setProjectPaymentType(ptBuilder.build());
        }
        return pBuilder.build();
    }

    private ProjectPaymentAdjustmentProto buildPaymentAdjustmentProto(
            ProjectPaymentAdjustment projectPaymentAdjustment) {
        ProjectPaymentAdjustmentProto.Builder builder = ProjectPaymentAdjustmentProto.newBuilder();
        if (projectPaymentAdjustment.getProjectId() != null) {
            builder.setProjectId(projectPaymentAdjustment.getProjectId());
        }
        if (projectPaymentAdjustment.getResourceRoleId() != null) {
            builder.setResourceRoleId(projectPaymentAdjustment.getResourceRoleId());
        }
        if (projectPaymentAdjustment.getFixedAmount() != null) {
            builder.setFixedAmount(BigDecimalProto.newBuilder()
                    .setScale(projectPaymentAdjustment.getFixedAmount().scale())
                    .setPrecision(projectPaymentAdjustment.getFixedAmount().precision())
                    .setValue(ByteString
                            .copyFrom(projectPaymentAdjustment.getFixedAmount().unscaledValue().toByteArray()))
                    .build());
        }
        if (projectPaymentAdjustment.getMultiplier() != null) {
            builder.setMultiplier(projectPaymentAdjustment.getMultiplier());
        }
        return builder.build();
    }
}