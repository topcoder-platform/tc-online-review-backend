/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.datavalidator;

import java.util.ArrayList;
import java.util.Collection;


/**
 * <p>
 * This is a simple <code>ObjectValidator</code> which specifically deals with validating the input type of a value. It
 * disregards all primitive based class types (such as Double for example) and considered any input that is primitive
 * based wrapper to be invalid. user can create this validator with a specific class type and then it (or its
 * descendants) would be considered as the valid type to be used by this validator.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b>This class is thread-safe as it is immutable.
 * </p>
 *
 * @author WishingBone, zimmy, AleaActaEst, telly12
 * @version 1.1
 */
public class TypeValidator extends AbstractObjectValidator {
    /**
     * <p>
     * This array represents invalid types.
     * </p>
     */
    private static Collection invalidTypes = new ArrayList();

    /**
     * <p>
     * The underlying validator to use. This is initialized through the constructor and is immutable after that. It is
     * expected to be non-null.
     * </p>
     */
    private final ObjectValidator validator;

    /**
     * <p>
     * Inputs must be instances of this type. This is initialized in the constructor and is immutable after that.
     * Cannot be null.
     * </p>
     */
    private final Class type;

    {
        invalidTypes.add(byte.class);
        invalidTypes.add(short.class);
        invalidTypes.add(int.class);
        invalidTypes.add(long.class);
        invalidTypes.add(float.class);
        invalidTypes.add(double.class);
        invalidTypes.add(char.class);
        invalidTypes.add(boolean.class);
    }

    /**
     * <p>
     * Creates a new <code>TypeValidator</code> that ensures inputs are instances of <code>type</code>.
     * </p>
     *
     * @param type ensure inputs are instances of this class.
     *
     * @throws IllegalArgumentException if <code>type</code> is <code>null</code>.
     */
    public TypeValidator(Class type) {
        Helper.checkNull(type, "type");

        if (invalidTypes.contains(type)) {
            throw new IllegalArgumentException("type cannot be a primitive " + "data type (" + type.getName() + ")");
        }

        this.validator = null;
        this.type = type;
    }

    /**
     * <p>
     * Creates a new <code>TypeValidator</code> that ensures inputs are instances of <code>type</code> and is
     * initialized with a resource bundle information.
     * </p>
     *
     * @param type ensure inputs are instances of this class.
     * @param bundleInfo resource bundle information
     *
     * @throws IllegalArgumentException if <code>type</code> is <code>null</code>.
     */
    public TypeValidator(Class type, BundleInfo bundleInfo) {
        super(bundleInfo);
        Helper.checkNull(type, "type");

        if (invalidTypes.contains(type)) {
            throw new IllegalArgumentException("type cannot be a primitive " + "data type (" + type.getName() + ")");
        }

        this.validator = null;
        this.type = type;
    }

    /**
     * <p>
     * Creates a new <code>TypeValidator</code> that ensures inputs are instances of <code>type</code>, and uses
     * <code>validator</code> as the underlying <code>ObjectValidator</code>.
     * </p>
     *
     * @param validator the underlying <code>ObjectValidator</code> to use.
     * @param type ensure inputs are instances of this class.
     *
     * @throws IllegalArgumentException if <code>validator</code> or <code>type</code> is <code>null</code>.
     */
    public TypeValidator(ObjectValidator validator, Class type) {
        Helper.checkNull(validator, "validator");
        Helper.checkNull(type, "type");

        if (invalidTypes.contains(type)) {
            throw new IllegalArgumentException("type cannot be a primitive " + "data type (" + type.getName() + ")");
        }

        this.validator = validator;
        this.type = type;
    }

    /**
     * <p>
     * Creates a new <code>TypeValidator</code> that ensures inputs are instances of <code>type</code>, and uses
     * <code>validator</code> as the underlying <code>ObjectValidator</code>.
     * </p>
     *
     * @param validator the underlying <code>ObjectValidator</code> to use.
     * @param type ensure inputs are instances of this class.
     * @param bundleInfo resource bundle information
     *
     * @throws IllegalArgumentException if <code>validator</code> or <code>type</code> is <code>null</code>.
     */
    public TypeValidator(ObjectValidator validator, Class type, BundleInfo bundleInfo) {
        super(bundleInfo);

        Helper.checkNull(validator, "validator");
        Helper.checkNull(type, "type");

        if (invalidTypes.contains(type)) {
            throw new IllegalArgumentException("type cannot be a primitive " + "data type (" + type.getName() + ")");
        }

        this.validator = validator;
        this.type = type;
    }

    /**
     * <p>
     * Validates the given <code>Object</code>.
     * </p>
     *
     * @param obj <code>Object</code> to be validated.
     *
     * @return <code>true</code> if <code>obj</code> is valid; <code>false</code> otherwise.
     */
    public boolean valid(Object obj) {
        if (obj == null) {
            return false;
        }

        // obj is not of valid type...
        if (!type.isAssignableFrom(obj.getClass())) {
            return false;

            // obj type ok, no more validations...
        } else if (validator == null) {
            return true;

            // obj type ok, use underlying validator...
        } else {
            return validator.valid(obj);
        }
    }

    /**
     * <p>
     * If the given <code>Object</code> is valid, this returns <code>null</code>. Otherwise, it returns an error
     * message.
     * </p>
     *
     * @param obj <code>Object</code> to be validated.
     *
     * @return <code>null</code> if <code>obj</code> is valid. Otherwise an error message is returned.
     */
    public String getMessage(Object obj) {
        if (obj == null) {
            return "object is null";
        }

        // obj is not of valid type...
        if (!type.isAssignableFrom(obj.getClass())) {
            return "not instance of " + type.getName();

            // obj type ok, no more validations...
        } else if (validator == null) {
            return null;

            // obj type ok, use underlying validator...
        } else {
            return validator.getMessage(obj);
        }
    }
}
