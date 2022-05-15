/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.search.filter;

import com.topcoder.onlinereview.component.search.ValidationResult;
import com.topcoder.onlinereview.component.search.builder.EqualsFragmentBuilder;
import com.topcoder.onlinereview.component.search.builder.RangeFragmentBuilder;
import com.topcoder.onlinereview.component.search.builder.SearchFragmentBuilder;

import java.util.Map;


/**
 * This class extends AbstractSimpeFilter class.
 * It is used to construct EqualTo search criterion.
 *
 * <p>
 * The class is thread-safe since it is immutable
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.3
 *
 */
public class EqualToFilter extends AbstractSimpleFilter {
    /**
     * <p>Create a new instance,by setting name and value.</p>
     *
     *
     * @param name the filed name of the search criterion
     * @param value a Comparable object used to set the upperThreshold and lowerThreshold attributes
     * @throws IllegalArgumentException if any parameter is Null
     * @throws IllegalArgumentException if <code>name</code> is empty
     */
    public EqualToFilter(String name, Comparable value) {
        super(name, value);
    }

    /**
     * <p>Test to see if the filter is valid according to the rules presented in the validators. </p>
     *
     *
     * @param validators a map containing <code>ObjectValidator</code> Objects
     * @return the <code>ValidationResult</code> object
     * @param alias a Map containing mapping between real names and alias names
     * @throws IllegalArgumentException if any parameter is Null
     */
    public ValidationResult isValid(Map validators, Map alias) {
        return FilterHelper.isValidSimpleFilter(validators, alias, fieldName, value, this);
    }

    public SearchFragmentBuilder getSearchFragmentBuilder() {
        return new EqualsFragmentBuilder();
    }

    /**
     * <p>return a clone of the object.</p>
     *
     * @return a clone of the object
     */
    public Object clone() {
        return (new EqualToFilter(this.fieldName, this.value));
    }
}
