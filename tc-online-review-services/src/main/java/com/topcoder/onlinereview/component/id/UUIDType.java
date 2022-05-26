/**
 * Copyright (C) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.onlinereview.component.id;

/**
 * <p>
 * this is an enum for the valid UUID types this component deals with. While java
 * doesn't support the enum type, we can get a close resemblance by using the type safe enum component. This
 * enum simply defines the types for the given generators in this component. The enum provides
 * implementations of the hashCode() and equals() functions to allow it to be the key of the generators map
 * </p>
 * <p>
 * <strong>Thread Safety: </strong>this class is thread safe because it's immutable
 * </p>
 *  
 * @author TCSDEVELOPER
 * @author TCSDESIGNER
 * @version 1.0
 */
public enum UUIDType {
    TYPE1(1), TYPE4(4), TYPEINT32(32);

    /**
     * <p>
     * represents the version number of this type.
     * </p>
     * <p>
     * <strong>Valid Values: </strong>a valid integer
     * </p>
     *
     */
    private int version;

     /**
     * <p>
     * this private constructor simply saves the passed argument.
     * </p>
     *
     * @param version the version of UUID to use
     */
    UUIDType(int version) {
        this.version = version;
    }
}

