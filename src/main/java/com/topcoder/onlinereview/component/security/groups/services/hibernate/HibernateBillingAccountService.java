/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.security.groups.services.hibernate;

import com.topcoder.onlinereview.component.grpcclient.GrpcHelper;
import com.topcoder.onlinereview.component.grpcclient.security.SecurityServiceRpc;
import com.topcoder.onlinereview.component.security.groups.model.BillingAccount;
import com.topcoder.onlinereview.component.security.groups.model.Client;
import com.topcoder.onlinereview.component.security.groups.services.BillingAccountService;
import com.topcoder.onlinereview.component.security.groups.services.EntityNotFoundException;
import com.topcoder.onlinereview.component.security.groups.services.SecurityGroupException;
import com.topcoder.onlinereview.component.util.LoggingWrapperUtility;
import com.topcoder.onlinereview.grpc.security.proto.BillingAccountForClientProto;
import com.topcoder.onlinereview.grpc.security.proto.GetBillingAccountResponse;

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
 * retrieval of billing accounts
 * </p>
 * <p>
 * Thread Safety:Implementations are expected to be effectively thread-safe
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Release 3) change notes:
 * <ol>
 *      <li>Modified method {@link #getBillingAccountsForClient(long)} to use the new query</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Security Groups Release 4) change notes:
 * <ol>
 *      <li>Fix the bug of {@link #getBillingAccountsForClient(long)} of retrieving duplicated billing accounts.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Security Groups Release 5) change notes:
 * <ol>
 *      <li>Updated {@link #getBillingAccountsForClient(long)} to sort the result.</li>
 * </ol>
 * </p>
 *
 * @author backstretlili, TCSASSEMBLER
 * 
 * @version 1.3
 * 
 */
public class HibernateBillingAccountService extends BaseGroupService implements BillingAccountService {
    private static final Logger logger = LoggerFactory.getLogger(HibernateBillingAccountService.class);

    /**
     * <p>
     * Represent the qualified name of this class.
     * </p>
     */
    private static final String CLASS_NAME = HibernateBillingAccountService.class.getName();

    /**
     * This method gets a billing account. If not found, returns null.
     * 
     * @param id
     *            the ID for the billing account to be retrieved
     * @return the requested BillingAccount
     * @throws SecurityGroupException
     *             If there are any errors during the execution of this method
     */
    public BillingAccount get(long id) throws SecurityGroupException {
        final String signature = CLASS_NAME + ".get(long id)";
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] { "id" }, new Object[] { id });

        BillingAccount result = null;
        try {
            SecurityServiceRpc securityServiceRpc = GrpcHelper.getSecurityServiceRpc();
            GetBillingAccountResponse response = securityServiceRpc.getBillingAccount(id);
            if (response.hasProjectId()) {
                result = new BillingAccount();
                result.setId(response.getProjectId());
                if (response.hasActive()) {
                    result.setActive(response.getActive());
                }
                if (response.hasCompanyId()) {
                    result.setCompanyId(response.getCompanyId());
                }
                if (response.hasDescription()) {
                    result.setDescription(response.getDescription());
                }
                if (response.hasIsDeleted()) {
                    result.setDeleted(response.getIsDeleted());
                }
                if (response.hasName()) {
                    result.setName(response.getName());
                }
                if (response.hasClientId()) {
                    Client client = new Client();
                    client.setId(response.getClientId());
                    if (response.hasClientCompanyId()) {
                        client.setCompanyId(response.getClientCompanyId());
                    }
                    if (response.hasClientName()) {
                        client.setName(response.getClientName());
                    }
                    if (response.hasClientIsDeleted()) {
                        client.setDeleted(response.getClientIsDeleted());
                    }
                    result.setClient(client);
                }
            }

        } catch (Exception e) {
            wrapAndLogSecurityException(e, logger, signature);
        }

        LoggingWrapperUtility.logExit(logger, signature, new Object[] { result });

        return result;
    }

    /**
     * This method gets all billing accounts for the given client. If not found,
     * returns an empty list.
     * 
     * @param clientId
     *            the ID for the client
     * @return the billing accounts for the given client
     * @throws EntityNotFoundException
     *             If a client with the given ID was not found
     * @throws SecurityGroupException
     *             If there are any errors during the execution of this method
     */
    public List<BillingAccount> getBillingAccountsForClient(long clientId) throws EntityNotFoundException,
            SecurityGroupException {
        final String signature = CLASS_NAME + ".getBillingAccountsForClient(long clientId)";
        LoggingWrapperUtility.logEntrance(logger, signature, new String[] { "clientId" }, new Object[] { clientId });

        List<BillingAccount> result = new ArrayList<BillingAccount>();
        try {
            SecurityServiceRpc securityServiceRpc = GrpcHelper.getSecurityServiceRpc();
            List<BillingAccountForClientProto> response = securityServiceRpc.getBillingAccountsForClient();
            Set<Long> ids = new HashSet<Long>();
            for (BillingAccountForClientProto row : response) {
                if (row.hasClientId() && row.hasBillingAccountId() && clientId == row.getClientId()
                        && !ids.contains(row.getBillingAccountId())) {
                    BillingAccount dto = new BillingAccount();
                    Client client = new Client();
                    client.setId(row.getClientId());
                    if (row.hasClientName()) {
                        client.setName(row.getClientName());
                    }
                    dto.setClient(client);
                    dto.setId(row.getBillingAccountId());
                    if (row.hasBllingAccountName()) {
                        dto.setName(row.getBllingAccountName());
                    }
                    result.add(dto);
                    ids.add(row.getBillingAccountId());
                }
            }
            Collections.sort(result, new Comparator<BillingAccount>() {
                public int compare(BillingAccount o1, BillingAccount o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
        } catch (Exception e) {
            wrapAndLogSecurityException(e, logger, signature);
        }

        LoggingWrapperUtility.logExit(logger, signature, new Object[] { result });

        return result;
    }
}
