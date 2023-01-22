/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topcoder.onlinereview.component.grpcclient.dataaccess.DataAccessServiceRpc;
import com.topcoder.onlinereview.grpc.dataaccess.proto.DeliverableProto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A simple DAO for deliverables backed up by Query Tool.</p>
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
@Component
public class DeliverableDataAccess {
    @Autowired
    DataAccessServiceRpc dataAccessServiceRpc;

    /**
     * <p>Gets the configuration for deliverables as set up in <code>deliverable_lu</code> database table.</p>
     *
     * @return a <code>Map</code> mapping the resource role IDs to maps mapping the project phase type IDs to IDs of
     *         deliverables.
     */
    public Map<Long, Map<Long, Long>> getDeliverablesList() {
        List<DeliverableProto> result = dataAccessServiceRpc.getDeliverablesList();

        Map<Long, Map<Long, Long>> deliverables = new HashMap<Long, Map<Long, Long>>();
        int recordNum = result.size();
        for (int i = 0; i < recordNum; i++) {
            long roleId = result.get(i).getResourceRoleId();
            long phaseTypeId = result.get(i).getPhaseTypeId();
            long deliverableId = result.get(i).getDeliverableId();

            if (!deliverables.containsKey(roleId)) {
                deliverables.put(roleId, new HashMap<>());
            }
            deliverables.get(roleId).put(phaseTypeId, deliverableId);
        }

        return deliverables;
    }
}
