/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.security.groups.services.hibernate;

import com.topcoder.onlinereview.component.security.groups.model.BillingAccount;
import com.topcoder.onlinereview.component.security.groups.services.DirectProjectService;
import com.topcoder.onlinereview.component.security.groups.services.SecurityGroupException;
import com.topcoder.onlinereview.component.security.groups.services.dto.ProjectDTO;
import com.topcoder.onlinereview.component.shared.dataaccess.DataAccess;
import com.topcoder.onlinereview.component.shared.dataaccess.Request;
import com.topcoder.onlinereview.component.util.LoggingWrapperUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.topcoder.onlinereview.component.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.component.util.CommonUtils.getString;
import static com.topcoder.onlinereview.component.util.SpringUtils.getTcsJdbcTemplate;

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
     * The DataAccess used to retrieve data from corporate_oltp
     */
    private DataAccess dataAccessCorp;

    /**
     * The DataAccess used to retrieve data from tcs_dw
     */
    private DataAccess dataAccessTcs;

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
            if (dataAccessCorp == null)
                dataAccessCorp = new DataAccess(getTcsJdbcTemplate());
            Request request = new Request();
            request.setContentHandle("project_name");
            request.setProperty("tcdirectid", String.valueOf(id));
            List<Map<String, Object>> resultContainer = dataAccessCorp.getData(request).get("project_name");
            if (resultContainer != null) {
                if(resultContainer.size()>0){
                    Map<String, Object> row = resultContainer.get(0);
                    result = new ProjectDTO();
                    result.setProjectId(id);
                    result.setName(getString(row, "project_name"));
                }
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
            if (dataAccessTcs == null)
                dataAccessTcs = new DataAccess(getTcsJdbcTemplate());
            Request request = new Request();
            request.setContentHandle("admin_client_billing_accounts_v2");
            // request.setProperty("client_id",String.valueOf(id));
            List<Map<String, Object>> resultContainer = dataAccessTcs.getData(request).get("admin_client_billing_accounts_v2");
            if (resultContainer != null) {
                Set<Long> ids = new HashSet<Long>();
                for (Map<String, Object> row : resultContainer) {
                    if (id == getLong(row, "client_id")
                        && !ids.contains(getLong(row, "direct_project_id"))) {
                        ProjectDTO dto = new ProjectDTO();
                        dto.setProjectId(getLong(row, "direct_project_id"));
                        dto.setName(getString(row, "direct_project_name"));
                        result.add(dto);
                        ids.add(dto.getProjectId());
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
    
    /**
     * This method gets all projects associated to the given billing accounts. If not found, returns
     * an empty list.
     * 
     * @return the projects associated to the given billing accounts
     * @throws SecurityGroupException
     *             If there are any errors during the execution of this method
     */
    public List<ProjectDTO> getProjectsByBillingAccounts(List<BillingAccount> billingAccounts) throws SecurityGroupException {
        final String signature = CLASS_NAME + ".getProjectsByBillingAccounts(List<BillingAccount> billingAccounts)";

        List<ProjectDTO> result = new ArrayList<ProjectDTO>();
        try {
            if (dataAccessTcs == null)
                dataAccessTcs = new DataAccess(getTcsJdbcTemplate());
            Request request = new Request();
            request.setContentHandle("admin_client_billing_accounts_v2");
            // request.setProperty("client_id",String.valueOf(id));
            List<Map<String, Object>> resultContainer = dataAccessTcs.getData(request).get("admin_client_billing_accounts_v2");
            if (resultContainer != null) {
                Set<Long> ids = new HashSet<Long>();
                for (Map<String, Object> row : resultContainer) {
                    for (BillingAccount billingAccount : billingAccounts) {
                        if (billingAccount.getId() == getLong(row, "billing_account_id")) {
                            if (!ids.contains(getLong(row, "direct_project_id"))) {
                                ProjectDTO dto = new ProjectDTO();
                                dto.setProjectId(getLong(row, "direct_project_id"));
                                dto.setName(getString(row, "direct_project_name"));
                                result.add(dto);
                                ids.add(dto.getProjectId());
                            }
                            break;
                        }
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
    
    /**
     * The setter of dataAccessCorp
     * 
     * @param dataAccessCorp
     *            the dataAccessCorp to set
     */
    public void setDataAccessCorp(DataAccess dataAccessCorp) {
        this.dataAccessCorp = dataAccessCorp;
    }

    /**
     * The setter of dataAccessTcs
     * 
     * @param dataAccessTcs
     *            the dataAccessTcs to set
     */
    public void setDataAccessTcs(DataAccess dataAccessTcs) {
        this.dataAccessTcs = dataAccessTcs;
    }

}
