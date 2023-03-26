/*
 * Copyright (C) 2011-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.security.groups.services.hibernate;

import com.topcoder.onlinereview.component.grpcclient.GrpcHelper;
import com.topcoder.onlinereview.component.grpcclient.security.SecurityServiceRpc;
import com.topcoder.onlinereview.component.security.groups.model.BillingAccount;
import com.topcoder.onlinereview.component.security.groups.model.GroupPermissionType;
import com.topcoder.onlinereview.component.security.groups.model.ResourceType;
import com.topcoder.onlinereview.component.security.groups.services.AuthorizationService;
import com.topcoder.onlinereview.component.security.groups.services.BillingAccountService;
import com.topcoder.onlinereview.component.security.groups.services.DirectProjectService;
import com.topcoder.onlinereview.component.security.groups.services.SecurityGroupException;
import com.topcoder.onlinereview.component.security.groups.services.dto.ProjectDTO;
import com.topcoder.onlinereview.component.util.LoggingWrapperUtility;
import com.topcoder.onlinereview.grpc.security.proto.BillingAccountProto;
import com.topcoder.onlinereview.grpc.security.proto.GroupDirectProjectProto;
import com.topcoder.onlinereview.grpc.security.proto.GroupMemberProto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This interface defines the contract for the authorization of users to have
 * access to resources, and to check if a user is an administrator of the
 * client.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This implementations is effectively
 * thread-safe.
 * </p>
 * <p>
 * version 1.0 (Topcoder Security Groups Backend Initial Assembly v1.0)
 * </P>
 * <p>
 * Changes in 1.1(Module Assembly - Topcoder Security Groups Backend Group
 * Services):makes it extends BaseGroupService
 * </p>
 * 
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Security Groups Frontend - Invitations Approvals) change notes:
 * <ol>
 *   <li>Added method {@link #checkApprovalPermission(long)} to check if user has approval permission.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous) change notes:
 * <ol>
 *   <li>Updated method {@link #isCustomerAdministrator(long, long)} to check if user is
 *   customer administrator of any client when parameter clientId = 0.</li>
 *   <li>Updated method {@link #checkApprovalPermission(long)} to reuse the updated method
 *   {@link #isCustomerAdministrator(long, long)}.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.4 (TopCoder Security Groups - Direct Permission Mechanism Update) change notes:
 * <ol>
 *   <li>Updated method {@link #checkAuthorization(long, long, ResourceType)} to check allow user access
 *   a project if he has access to the associated billing accounts.</li>
 *   <li>Added field {@link #directProjectService} and its getter and setter.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.5 (TopCoder Security Groups Release 8 - Automatically Grant Permissions) change notes:
 * </p>
 * @author leo_lol, backstretlili, TCSASSEMBLER, notpad, freegod
 * @version 1.5
 * @since 1.0
 */
public class HibernateAuthorizationService extends BaseGroupService implements AuthorizationService {

    private static final Logger logger = LoggerFactory.getLogger(HibernateAuthorizationService.class);

    /**
     * <p>
     * Represent the qualified name of this class.
     * </p>
     */
    private static final String CLASS_NAME = HibernateAuthorizationService.class.getName();

    /**
     * <p>
     * Represents the direct project service instance.
     * </p>
     * @since 1.4
     */
    private DirectProjectService directProjectService;
    
    /**
     * <p>
     * Represents the direct billing account service instance.
     * </p>
     *
     * @since 1.5
     */
    private BillingAccountService billingAccountService;
	
    /**
     * <p>
     * This method checks what kind of permission the given user has to access
     * the given resource, and returns the type of permission associated with
     * the user and resource. If there is no permission, null is returned.
     * </p>
     * 
     * @param resourceId
     *            the ID of the resource
     * @param userId
     *            the ID of the user
     * @param resourceType
     *            the type of the resource
     * @return the permission level for this user for the given resource
     * @throws IllegalArgumentException
     *             If resourceType is null
     */
    public GroupPermissionType checkAuthorization(long userId, long resourceId, ResourceType resourceType)
            throws SecurityGroupException {

        final String signature = CLASS_NAME + "checkAuthorization(long userId, "
                + "long resourceId, ResourceType resourceType)";

        LoggingWrapperUtility.logEntrance(logger, signature, new String[] { "userId", "resourceId", "resourceType" },
                new Object[] { userId, resourceId, resourceType });

        try {
            SecurityServiceRpc securityServiceRpc = GrpcHelper.getSecurityServiceRpc();
            List<GroupMemberProto> groupMemberList = securityServiceRpc.getGroupMembers(userId);

            for (GroupMemberProto groupMember : groupMemberList) {

                boolean resourceAvailable = false;

                if (resourceType == ResourceType.PROJECT) {
                    if (groupMember.hasGroupId()) {
                        List<GroupDirectProjectProto> directProjects = securityServiceRpc.getDirectProjects(groupMember.getGroupId());
                        if (null != directProjects) {
                            for (GroupDirectProjectProto directProject : directProjects) {
                                if (directProject.hasTcDirectProjectId() && directProject.getTcDirectProjectId() == resourceId) {
                                    resourceAvailable = true;
                                    break;
                                }
                            }
                        }
                        
                        // if a user has access to a Billing Account, he also has access to all Projects 
                        // backed by that Billing Account.
                        if (!resourceAvailable) {
                            List<BillingAccountProto> billingAccounts = securityServiceRpc.getBillingAccounts(groupMember.getGroupId());
                            if (null != billingAccounts) {
                                if (directProjectService == null) {
                                    directProjectService = new HibernateDirectProjectService();
                                }
                                List<ProjectDTO> projects = directProjectService.getProjectsByBillingAccounts(billingAccounts);
                                for (ProjectDTO project : projects) {
                                    if (project.getProjectId() == resourceId) {
                                        resourceAvailable = true;
                                        break;
                                    }
                                }
                            }
                        }

                        //if this group is granted permissions automatically, check client's projects.
                        if(!resourceAvailable && groupMember.getGroupAutoGrant()) {
                            if(groupMember.hasGroupClientId()) {
                                if(null == directProjectService) {
                                    directProjectService = new HibernateDirectProjectService();
                                }
                                List<ProjectDTO> projects = directProjectService.getProjectsByClientId(groupMember.getGroupClientId());
                                for (ProjectDTO project : projects) {
                                    if (project.getProjectId() == resourceId) {
                                        resourceAvailable = true;
                                        break;
                                    }
                                }
                                //no need to get billing accounts by client and then get projects by accounts.
                                //the same query 'query_admin_client_billing_accounts_v2.txt' is used.
                                //check service code to see details
                            }
                        }

                    }

                } else if (resourceType == ResourceType.BILLING_ACCOUNT) {
                    if (groupMember.hasGroupId()) {
                        List<BillingAccountProto> billingAccounts = securityServiceRpc.getBillingAccounts(groupMember.getGroupId());
                        if (null != billingAccounts) {
                            for (BillingAccountProto billingAccount : billingAccounts) {
                                if (billingAccount.hasProjectId() && billingAccount.getProjectId() == resourceId) {
                                    resourceAvailable = true;
                                    break;
                                }
                            }
                        }

                        //if the group is granted permissions automatically,
                        // we should check the client's billing accounts.
                        if(!resourceAvailable && groupMember.getGroupAutoGrant()) {
                            if(groupMember.hasGroupClientId()) {
                                if(null == billingAccountService) {
                                    billingAccountService = new HibernateBillingAccountService();
                                }
                                List<BillingAccount> accounts = billingAccountService
                                        .getBillingAccountsForClient(groupMember.getGroupClientId());
                                if (null != accounts) {
                                    for (BillingAccount billingAccount : accounts) {
                                        if (null != billingAccount && billingAccount.getId() == resourceId) {
                                            resourceAvailable = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Determine permission type according previous check.
                if (resourceAvailable ) {
                    GroupPermissionType permissionType = null;

                    if (groupMember.getUseGroupDefault()) {
                        permissionType = GroupPermissionType.valueOf(groupMember.getGroupDefaultPermission());
                    } else {
                        permissionType = GroupPermissionType.valueOf(groupMember.getSpecificPermission());
                    }

                    LoggingWrapperUtility.logExit(logger, signature, new Object[] { permissionType });

                    return permissionType;
                }
            }
            LoggingWrapperUtility.logExit(logger, signature, null);
        } catch (Exception e) {
            wrapAndLogSecurityException(e, logger, signature);
        }

        // There is no such group member or the group member has restricted/no
        // access to the resource.
        return null;
    }

    /**
     * <p>
     * This method checks if the given user is an administrator of the given
     * client.
     * </p>
     * 
     * @param userId
     *            the ID of the user
     * @param clientId
     *            the ID of the client
     * @return true if the users is an administrator of the client, and false
     *         otherwise.
     * @throws SecurityGroupException
     *             If there are any errors during the execution of this method
     */
    public boolean isCustomerAdministrator(long userId, long clientId) throws SecurityGroupException {
        final String signature = CLASS_NAME + "isCustomerAdministrator(long userId, long clientId)";
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] { "userId", "clientId" }, new Object[] {
                userId, clientId });
        boolean result = false;
        try {
            SecurityServiceRpc securityServiceRpc = GrpcHelper.getSecurityServiceRpc();
            result = securityServiceRpc.isCustomerAdministrator(userId, clientId);
            LoggingWrapperUtility.logExit(logger, signature, new Object[] { result });
        } catch (Exception e) {
            wrapAndLogSecurityException(e, logger, signature);
        }

        return result;
    }

    /**
     * <p>
     * Test if user is TC administrator.
     * </p>
     * 
     * @param userId
     * @return true if the corresponding user is TC Staff; false otherwise.
     * @throws SecurityGroupException
     *             If anything wrong happens.
     */
    public boolean isAdministrator(long userId) throws SecurityGroupException {
        final String signature = CLASS_NAME + ".isAdministrator()";
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] { "userId" }, new Object[] { userId });
        boolean result = false;

        /**
         * This code snippet is largely for sake of constructing unit test.
         */
        SecurityServiceRpc securityServiceRpc = GrpcHelper.getSecurityServiceRpc();
        try {
            result = securityServiceRpc.isAdministrator(userId);

            LoggingWrapperUtility.logExit(logger, signature, new Object[] { result });
        } catch (Exception e) {
            wrapAndLogSecurityException(e, logger, signature);
        }
        return result;
    }

    /**
     * <p>
     * This method returns a collection is group id, for which this user has
     * full permission.
     * </p>
     * 
     * @param userId
     * @return an List of Group IDs of whom the specified user has full
     *         permission.
     * @throws SecurityGroupException
     *             If anything wrong happens.
     */
    public List<Long> getGroupIdsOfFullPermissionsUser(long userId) throws SecurityGroupException {
        final String signature = CLASS_NAME + ".getGroupIdsOfFullPermissionsUser()";
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] { "userId" }, new Object[] { userId });

        List<Long> result = new ArrayList<Long>();
        try {
            SecurityServiceRpc securityServiceRpc = GrpcHelper.getSecurityServiceRpc();
            result = securityServiceRpc.getGroupIdsOfFullPermissionsUser(userId);
            if (!result.isEmpty()) {
                LoggingWrapperUtility.logExit(logger, signature, result.toArray());
            } else {
                /*
                 * LoggingWrapperUtility.logExit(logger, signature,
                 * result.toArray());The above line would cause
                 * IndexOutOfBoundsException!
                 */
                LoggingWrapperUtility.logExit(logger, signature, new String[] { "Empty List" });
            }
        } catch (Exception e) {
            wrapAndLogSecurityException(e, logger, signature);
        }

        return result;
    }
        
    /**
     * <p>
     * Test if user has approval permission: either TC administrator, customer administrator,
     * or has full permission.
     * </p>
     * @param userId the user id
     * @return true if the user has approval permission; false otherwise.
     * @since 1.1
     */
    public boolean checkApprovalPermission(long userId) throws SecurityGroupException {
        final String signature = CLASS_NAME + "checkApprovalPermission(long userId)";
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] { "userId" }, new Object[] {
                userId });
        boolean res = isAdministrator(userId) || 
            isCustomerAdministrator(userId, 0) ||
            getGroupIdsOfFullPermissionsUser(userId).size() > 0;
        LoggingWrapperUtility.logExit(logger, signature, new Object[] { res });
        return res;
    }
 
    /**
     * Getter of directProjectService
     * 
     * @return the directProjectService
     * @since 1.4
     */
    public DirectProjectService getDirectProjectService() {
        return directProjectService;
    }
    
    /**
     * Setter of directProjectService
     * 
     * @param directProjectService
     *            the Spring injected directProjectService
     * @since 1.4           
     */
    public void setDirectProjectService(DirectProjectService directProjectService) {
        this.directProjectService = directProjectService;
    }

    /**
     * Gets billing account service.
     *
     * @return the billing account service
     *
     * @since 1.5
     */
    public BillingAccountService getBillingAccountService() {
        return billingAccountService;
    }
    /**
     * Sets billing account service.
     *
     * @param billingAccountService the billing account service
     *
     * @since 1.5
     */
    public void setBillingAccountService(BillingAccountService billingAccountService) {
        this.billingAccountService = billingAccountService;
    }
}
