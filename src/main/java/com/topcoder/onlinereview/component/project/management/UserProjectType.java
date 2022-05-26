/*
 * Copyright (C) 2006 - 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class represents project type
 * </p>
 *
 */
public class UserProjectType implements Serializable {
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
     * Represents the categories of this instance.
     */
    private List<UserProjectCategory> categories = new ArrayList<>();

    /**
     * Create a new ProjectType instance with the given id and name. The two fields
     * are required for a this instance to
     * be persisted.
     *
     * @param id
     *             The project type id.
     * @param name
     *             The project type name.
     */
    public UserProjectType(long id, String name) {
        setId(id);
        setName(name);
    }

    /**
     * Sets the id for this project type instance. Only positive values are allowed.
     *
     * @param id
     *           The id of this project type instance.
     * @throws IllegalArgumentException
     *                                  If project type id is less than or equals to
     *                                  zero.
     */
    public void setId(long id) {
        Helper.checkNumberPositive(id, "id");
        this.id = id;
    }

    /**
     * Gets the id of this project type instance.
     *
     * @return the id of this project type instance.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the name for this project type instance. Null or empty values are not
     * allowed.
     *
     * @param name
     *             The name of this project type instance.
     * @throws IllegalArgumentException
     *                                  If project type name is null or empty
     *                                  string.
     */
    public void setName(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        this.name = name;
    }

    /**
     * Gets the name of this project type instance.
     *
     * @return the name of this project type instance.
     */
    public String getName() {
        return name;
    }

    /**
     * Add category to categories
     *
     * @param category The UserProjectCategory instance.
     */
    public void addCategory(UserProjectCategory category) {
        categories.add(category);
    }

    /**
     * Get User project category list
     *
     * @return the categories list
     */
    public List<UserProjectCategory> getCategories() {
        return categories;
    }

    /**
     * Sets the count for this project type instance. Only positive values are
     * allowed.
     *
     * @param count
     *              The count of this project type instance.
     * @throws IllegalArgumentException
     *                                  If project type count is less than or
     *                                  equals to
     *                                  zero.
     */
    public void setCount(int count) {
        Helper.checkNumberPositive(count, "count");
        this.count = count;
    }

    /**
     * Gets the count of this project type instance.
     *
     * @return the count of this project type instance.
     */
    public int getCount() {
        return count;
    }
}
