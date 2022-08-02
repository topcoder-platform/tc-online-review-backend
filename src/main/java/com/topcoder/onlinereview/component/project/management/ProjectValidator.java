/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.management;

import org.springframework.stereotype.Component;

import java.util.Map.Entry;

/**
 * This is the default implementation of the ProjectValidator interface to provide project validation functions.
 * <p>
 * It validates the project base on the following rules:
 * </p>
 * <ul>
 * <li>Project type/status/category name: Length must be less than or equal to 64</li>
 * <li>Project type/status/category description: Length must be less than or equal to 256</li>
 * <li>Project property key: Length must be less than or equal to 64</li>
 * <li>Project property value: Length must be less than or equal to 4096</li>
 * </ul>
 * <p>
 * Thread safety: This class is immutable and thread safe.
 * </p>
 *
 * @author tuenm, iamajia
 * @version 1.0
 */
public interface ProjectValidator {

    /**
     * <p>
     * It validates the project base on the following rules:
     * </p>
     * <ul>
     * <li>Project type/status/category name: Length must be less than 64</li>
     * <li>Project type/status/category description: Length must be less than 256</li>
     * <li>Project property key: Length must be less than 64</li>
     * <li>Project property value: Length must be less than 4096</li>
     * </ul>
     * This method will throw ValidationException on the first problem it found. The exception should contains
     * meaningful error message about the validation problem.
     *
     * @param project
     *            The project to validate.
     * @throws ValidationException
     *             if validation fails.
     * @throws IllegalArgumentException
     *             if project is null.
     */
    void validateProject(Project project) throws ValidationException;
}
