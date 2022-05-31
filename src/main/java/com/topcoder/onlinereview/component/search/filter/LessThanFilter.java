/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.search.filter;

import com.topcoder.onlinereview.component.search.ValidationResult;
import com.topcoder.onlinereview.component.search.builder.RangeFragmentBuilder;
import com.topcoder.onlinereview.component.search.builder.SearchFragmentBuilder;

import java.util.Map;

/**
 * This class extends AbstractSimpeFilter class. It is used to construct lessThan?
 * search criterion. This class also provides concrete isValid(), and getFilterType() methods.
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.3
 *
 */
public class LessThanFilter extends AbstractSimpleFilter {
    /**
     * <p>Create a new instance,by setting the name and value.</p>
     *
     * <p>
     * The class is thread-safe since it is immutable.
     * </p>
     *
     *
     * @param name the filed name of the search criterion
     * @param value a Comparable object used to set the upperThreshold and lowerThreshold attributes
     * @throws IllegalArgumentException if any parameter is Null
     * @throws IllegalArgumentException if <code>name</code> parameter is empty
     */
    public LessThanFilter(String name, Comparable value) {
        super(name, value);
        upperThreshold = value;
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
        return new RangeFragmentBuilder();
    }

    /**
     * <p>return a clone of the object.</p>
     *
     * @return a clone of the object
     */
    public Object clone() {
        return new LessThanFilter(this.fieldName, this.value);
    }
}
