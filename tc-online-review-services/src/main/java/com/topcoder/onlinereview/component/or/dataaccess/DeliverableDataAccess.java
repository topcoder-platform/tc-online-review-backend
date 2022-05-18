/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.or.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.util.CommonUtils.getLong;

/**
 * A simple DAO for deliverables backed up by Query Tool.
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
@Component
public class DeliverableDataAccess {
  @Autowired private BaseDataAccess baseDataAccess;

  /**
   * Gets the configuration for deliverables as set up in <code>deliverable_lu</code> database
   * table.
   *
   * @return a <code>Map</code> mapping the resource role IDs to maps mapping the project phase type
   *     IDs to IDs of deliverables.
   */
  public Map<Long, Map<Long, Long>> getDeliverablesList() {
    Map<String, List<Map<String, Object>>> results =
        baseDataAccess.runQuery("tcs_deliverables", (String) null, null);

    Map<Long, Map<Long, Long>> deliverables = new HashMap<Long, Map<Long, Long>>();

    List<Map<String, Object>> resourcesData = results.get("tcs_deliverables");
    for (Map<String, Object> row : resourcesData) {
      long roleId = getLong(row, "resource_role_id");
      long phaseTypeId = getLong(row, "phase_type_id");
      long deliverableId = getLong(row, "deliverable_id");
      if (!deliverables.containsKey(roleId)) {
        deliverables.put(roleId, new HashMap<Long, Long>());
      }
      deliverables.get(roleId).put(phaseTypeId, deliverableId);
    }

    return deliverables;
  }
}
