/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.security.groups.services.hibernate;

import com.topcoder.onlinereview.component.grpcclient.GrpcHelper;
import com.topcoder.onlinereview.component.grpcclient.security.SecurityServiceRpc;
import com.topcoder.onlinereview.component.security.groups.services.DirectProjectService;
import com.topcoder.onlinereview.component.security.groups.services.SecurityGroupException;
import com.topcoder.onlinereview.component.security.groups.services.dto.ProjectDTO;
import com.topcoder.onlinereview.component.util.LoggingWrapperUtility;
import com.topcoder.onlinereview.grpc.security.proto.BillingAccountProto;
import com.topcoder.onlinereview.grpc.security.proto.ProjectByBillingAccountProto;
import com.topcoder.onlinereview.grpc.security.proto.ProjectByClientIdProto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * This is the implementation of interface defines the contract for the
 * retrieval of projects
 * </p>
 * <p>
 * Thread Safety:Implementations are expected to be effectively thread-safe
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Release 3) change notes:
 * <ol>
 *      <li>Modified method {@link #get(long)} to change the datasource of the query</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Security Groups Release 5) change notes:
 * <ol>
 *      <li>Removed the entrance/exit log of {@link #getProjectsByClientId(long)}.</li>
 *      <li>Updated {@link #getProjectsByClientId(long)} to sort the result and avoid the
 *      duplicates results.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (TopCoder Security Groups - Direct Permission Mechanism Update) change notes:
 * <ol>
 *      <li>Added {@link #getProjectsByBillingAccounts(List)} to get projects from billing accounts.</li>
 * </ol>
 * </p>
 * 
 * @author backstretlili, TCSASSEMBLER, notpad
 * 
 * @version 1.3
 */
public class HibernateDirectProjectService extends BaseGroupService implements DirectProjectService {
    private static final Logger logger = LoggerFactory.getLogger(HibernateBillingAccountService.class);
    /**
     * <p>
     * Represent the qualified name of this class.
     * </p>
     */
    private static final String CLASS_NAME = HibernateDirectProjectService.class.getName();

    /**
     * This method gets a project. If not found, returns null.
     * 
     * @param id
     *            the ID for the project to be retrieved
     * @return the requested ProjectDTO
     * @throws SecurityGroupException
     *             If there are any errors during the execution of this method
     */
    public ProjectDTO get(long id) throws SecurityGroupException {
        final String signature = CLASS_NAME + ".get(long id)";
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] { "id" }, new Object[] { id });

        ProjectDTO result = null;
        try {
            SecurityServiceRpc securityServiceRpc = GrpcHelper.getSecurityServiceRpc();
            String projectName = securityServiceRpc.getProjectName(id);
            if (projectName != null) {
                result = new ProjectDTO();
                result.setProjectId(id);
                result.setName(projectName);
            }
        } catch (Exception e) {
            wrapAndLogSecurityException(e, logger, signature);
        }

        LoggingWrapperUtility.logExit(logger, signature, new Object[] { result });

        return result;
    }

    /**
     * This method gets all projects of the given client. If not found, returns
     * an empty list.
     * 
     * @param id
     *            the ID for the client
     * @return the projects of the given client
     * @throws SecurityGroupException
     *             If there are any errors during the execution of this method
     */
    public List<ProjectDTO> getProjectsByClientId(long id) throws SecurityGroupException {
        final String signature = CLASS_NAME + ".getProjectsByClientId(long id)";

        List<ProjectDTO> result = new ArrayList<ProjectDTO>();
        try {
            SecurityServiceRpc securityServiceRpc = GrpcHelper.getSecurityServiceRpc();
            List<ProjectByClientIdProto> response = securityServiceRpc.getProjectsByClientId();
            Set<Long> ids = new HashSet<Long>();
            for (ProjectByClientIdProto row : response) {
                if (row.hasClientId() && row.hasDirectProjectId() && id == row.getClientId()
                        && !ids.contains(row.getDirectProjectId())) {
                    ProjectDTO dto = new ProjectDTO();
                    dto.setProjectId(row.getDirectProjectId());
                    if (row.hasDirectProjectName()) {
                        dto.setName(row.getDirectProjectName());
                    }
                    result.add(dto);
                    ids.add(dto.getProjectId());
                }
            }
            Collections.sort(result, new Comparator<ProjectDTO>() {
                public int compare(ProjectDTO o1, ProjectDTO o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
        } catch (Exception e) {
            wrapAndLogSecurityException(e, logger, signature);
        }

        return result;
    }
    
    /**
     * This method gets all projects associated to the given billing accounts. If not found, returns
     * an empty list.
     * 
     * @return the projects associated to the given billing accounts
     * @throws SecurityGroupException
     *             If there are any errors during the execution of this method
     */
    public List<ProjectDTO> getProjectsByBillingAccounts(List<BillingAccountProto> billingAccounts) throws SecurityGroupException {
        final String signature = CLASS_NAME + ".getProjectsByBillingAccounts(List<BillingAccount> billingAccounts)";

        List<ProjectDTO> result = new ArrayList<ProjectDTO>();
        try {
            SecurityServiceRpc securityServiceRpc = GrpcHelper.getSecurityServiceRpc();
            List<ProjectByBillingAccountProto> response = securityServiceRpc.getProjectsByBillingAccounts();
            Set<Long> ids = new HashSet<Long>();
            for (ProjectByBillingAccountProto row : response) {
                for (BillingAccountProto billingAccount : billingAccounts) {
                    if (row.hasBillingAccountId() && billingAccount.hasProjectId()
                            && billingAccount.getProjectId() == row.getBillingAccountId()) {
                        if (row.hasDirectProjectId() && !ids.contains(row.getDirectProjectId())) {
                            ProjectDTO dto = new ProjectDTO();
                            dto.setProjectId(row.getDirectProjectId());
                            if (row.hasDirectProjectName()) {
                                dto.setName(row.getDirectProjectName());
                            }
                            result.add(dto);
                            ids.add(dto.getProjectId());
                        }
                        break;
                    }
                }
            }
            Collections.sort(result, new Comparator<ProjectDTO>() {
                public int compare(ProjectDTO o1, ProjectDTO o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
        } catch (Exception e) {
            wrapAndLogSecurityException(e, logger, signature);
        }

        return result;
    }
}
