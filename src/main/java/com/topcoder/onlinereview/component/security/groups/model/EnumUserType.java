/*
 * Copyright (C) Copyright belongs to the author. see more in the following 
 * page: http://community.jboss.org/wiki/UserTypeForPersistingAnEnumWithAVARCHARColumn
 */
package com.topcoder.onlinereview.component.security.groups.model;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * <p>
 * Provide an implementation for {@link UserType} interface.
 * </p>
 * 
 * @param <E> Generic Type.
 * @author leo_lol
 * @since 1.0
 * @version 1.0 (Topcoder Security Groups Backend Model and Authorization Assembly v1.0 )
 */
public class EnumUserType<E extends Enum<E>> implements UserType {
    private Class<E> clazz = null;

    protected EnumUserType(Class<E> c) {
        this.clazz = c;
    }

    private static final int[] SQL_TYPES = { Types.VARCHAR };

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @SuppressWarnings("rawtypes")
    public Class returnedClass() {
        return clazz;
    }

    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
            throws HibernateException, SQLException {
        String name = resultSet.getString(names[0]);
        E result = null;
        if (!resultSet.wasNull()) {
            result = Enum.valueOf(clazz, name);
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    public void nullSafeSet(PreparedStatement preparedStatement, Object value,
            int index) throws HibernateException, SQLException {
        if (null == value) {
            preparedStatement.setNull(index, Types.VARCHAR);
        } else {
            preparedStatement.setString(index, ((Enum) value).name());
        }
    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public boolean isMutable() {
        return false;
    }

    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y)
            return true;
        if (null == x || null == y)
            return false;
        return x.equals(y);
    }
}
