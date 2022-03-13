/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;


/**
 * This is a test class for AuditedDeliverableStructure. It extends AuditedDeliverableStructure and no method is
 * overrided.
 *
 * @author singlewood
 * @version 1.1
 */
public class AuditedDeliverableStructureExtends extends AuditedDeliverableStructure {
    /**
     * Creates a new AuditedDeliverableStructureExtends.
     */
    public AuditedDeliverableStructureExtends() {
        super();
    }

    /**
     * Creates a new AuditedDeliverableStructureExtends.
     *
     * @param id The id of the AuditedDeliverableStructureExtends
     *
     * @throws IllegalArgumentException If id is <= 0
     */
    public AuditedDeliverableStructureExtends(long id) {
        super(id);
    }
}
