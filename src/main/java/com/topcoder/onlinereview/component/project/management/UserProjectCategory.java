/*
 * Copyright (C) 2006 - 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.management;

import java.io.Serializable;

/**
 * <p>
 * This class represents user project category
 * </p>
 *
 */
public class UserProjectCategory implements Serializable {
    /**
     * Represents the id of this instance.
     */
    private long id = 0;

    /**
     * Represents the name of this instance.
     */
    private String name = null;

    /**
     * Represents the count of this instance.
     */
    private int count = 0;

    /**
     * Create a new UserProjectCategory instance with the given id and name. The two
     * fields
     * are required for a this instance to
     * be persisted.
     *
     * @param id
     *              The project category id.
     * @param name
     *              The project category name.
     * @param count
     */
    public UserProjectCategory(long id, String name, int count) {
        setId(id);
        setName(name);
        setCount(count);
    }

    /**
     * Sets the id for this project category instance. Only positive values are
     * allowed.
     *
     * @param id
     *           The id of this project category instance.
     * @throws IllegalArgumentException
     *                                  If project category id is less than or
     *                                  equals to
     *                                  zero.
     */
    public void setId(long id) {
        Helper.checkNumberPositive(id, "id");
        this.id = id;
    }

    /**
     * Gets the id of this project category instance.
     *
     * @return the id of this project category instance.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the name for this project category instance. Null or empty values are
     * not
     * allowed.
     *
     * @param name
     *             The name of this project category instance.
     * @throws IllegalArgumentException
     *                                  If project category name is null or empty
     *                                  string.
     */
    public void setName(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        this.name = name;
    }

    /**
     * Gets the name of this project category instance.
     *
     * @return the name of this project category instance.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the count for this project category instance. Only positive values are
     * allowed.
     *
     * @param count
     *              The count of this project category instance.
     * @throws IllegalArgumentException
     *                                  If project category count is less than or
     *                                  equals to
     *                                  zero.
     */
    public void setCount(int count) {
        Helper.checkNumberPositive(count, "count");
        this.count = count;
    }

    /**
     * Gets the count of this project category instance.
     *
     * @return the count of this project category instance.
     */
    public int getCount() {
        return count;
    }
}
