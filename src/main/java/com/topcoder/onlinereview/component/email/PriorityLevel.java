/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 *
 * @(#)PriorityLevel.java
 */
package com.topcoder.onlinereview.component.email;

/**
 * <p>Type Safe Enumeration of the values that priority can be. All valid priority values if provided by the static
 * instances.</p>
 *
 * <p>This class is thread-safe, since it is immutable.</p>
 *
 * @see TCSEmailMessage#setPriority(PriorityLevel)
 *
 * @since   3.0
 *
 * @author  BEHiker57W
 * @author  smell
 * @version 3.0
 */
public enum PriorityLevel {
    NONE("none"), HIGHEST("highest"), HIGH("high"), NORMAL("normal"), LOW("low"), LOWEST("lowest");

    /**
     * <p>String name of the priority. Immutable once created.</p>
     *
     * <p>The purpose of this property is to allow lookups in the configuration file that will show the custom headers
     * associated with the name.</p>
     */
    private final String name;

    /**
     * <p>Private Constructor to prevent outside instantiation. It is called to instantiate the static instances.</p>
     *
     * @param name  name for this instance, this parameter is not check since the constructor is used inside only
     */
    PriorityLevel(String name) {
        // No argument check, since this method is invoked internally only.
        this.name = name;
    }

    /**
     * <p>Returns name the name of this priority, it corresponds to custom config value for priority.</p>
     *
     * @return the name value for this instance of <code>PriorityLevel</code>
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Returns a string representation of the object. This overrides the default toString to return the name as the
     * string representation. It is equivalent to <code>getName()</code>.</p>
     *
     * @return The name of the <code>PriorityLevel</code>
     */
    public String toString() {
        return name;
    }

}
