/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.failuretests;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mock implementation of EntityManager, it's used for failure test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockEntityManager implements EntityManager {
    /**
     * <p>
     * EntityManager proxy.
     * </p>
     */
    private EntityManager manager;

    /**
     * <p>
     * Exception flag for persistence exception.
     * </p>
     */
    private boolean expFlag = false;

    /**
     * <p>
     * Exception flag for transaction exception.
     * </p>
     */
    private boolean transactionExp = false;

    /**
     * <p>
     * Default empty constructor.
     * </p>
     *
     * @param manager the internal manager
     */
    public MockEntityManager(EntityManager manager) {
        this.manager = manager;
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 entity
     */
    public void persist(Object arg0) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        manager.persist(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 entity
     * @param <T> class
     * @return new entity
     */
    public <T> T merge(T arg0) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        return manager.merge(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 entity
     */
    public void remove(Object arg0) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        manager.remove(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 entity
     * @param arg1 primary key
     * @param <T> class
     * @return found entity
     */
    public <T> T find(Class<T> arg0, Object arg1) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        return manager.find(arg0, arg1);
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
        return null;
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 entity
     * @param arg1 primary key
     * @param <T> class
     * @return found entity
     */
    public <T> T getReference(Class<T> arg0, Object arg1) {
        return manager.getReference(arg0, arg1);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     */
    public void flush() {
        manager.flush();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 flush mode type
     */
    public void setFlushMode(FlushModeType arg0) {
        manager.setFlushMode(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @return flush mode type
     */
    public FlushModeType getFlushMode() {
        return manager.getFlushMode();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 arg0
     * @param arg1 arg1
     */
    public void lock(Object arg0, LockModeType arg1) {
        manager.lock(arg0, arg1);
    }

    @Override
    public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {

    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 arg0
     */
    public void refresh(Object arg0) {
        manager.refresh(arg0);
    }

    @Override
    public void refresh(Object o, Map<String, Object> map) {

    }

    @Override
    public void refresh(Object o, LockModeType lockModeType) {

    }

    @Override
    public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {

    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     */
    public void clear() {
        manager.clear();
    }

    @Override
    public void detach(Object o) {

    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 arg0
     * @return result
     */
    public boolean contains(Object arg0) {
        return manager.contains(arg0);
    }

    @Override
    public LockModeType getLockMode(Object o) {
        return null;
    }

    @Override
    public void setProperty(String s, Object o) {

    }

    @Override
    public Map<String, Object> getProperties() {
        return null;
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 arg0
     * @return result
     */
    public Query createQuery(String arg0) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        return manager.createQuery(arg0);
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return null;
    }

    @Override
    public Query createQuery(CriteriaUpdate criteriaUpdate) {
        return null;
    }

    @Override
    public Query createQuery(CriteriaDelete criteriaDelete) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createQuery(String s, Class<T> aClass) {
        return null;
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 arg0
     * @return result
     */
    public Query createNamedQuery(String arg0) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        return manager.createNamedQuery(arg0);
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String s, Class<T> aClass) {
        return null;
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 arg0
     * @return result
     */
    public Query createNativeQuery(String arg0) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        return manager.createNativeQuery(arg0);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 arg0
     * @param arg1 arg1
     * @return result
     */
    @SuppressWarnings("unchecked")
    public Query createNativeQuery(String arg0, Class arg1) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        return manager.createNativeQuery(arg0, arg1);
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 arg0
     * @param arg1 arg1
     * @return result
     */
    public Query createNativeQuery(String arg0, String arg1) {
        if (expFlag) {
            throw new PersistenceException();
        }

        if (transactionExp) {
            throw new TransactionRequiredException();
        }

        return manager.createNativeQuery(arg0, arg1);
    }

    @Override
    public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
        return null;
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     */
    public void joinTransaction() {
        manager.joinTransaction();
    }

    @Override
    public boolean isJoinedToTransaction() {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @return result
     */
    public Object getDelegate() {
        return manager.getDelegate();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     */
    public void close() {
        manager.close();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @return result
     */
    public boolean isOpen() {
        return manager.isOpen();
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @return result
     */
    public EntityTransaction getTransaction() {
        return manager.getTransaction();
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return null;
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return null;
    }

    @Override
    public Metamodel getMetamodel() {
        return null;
    }

    @Override
    public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
        return null;
    }

    @Override
    public EntityGraph<?> createEntityGraph(String s) {
        return null;
    }

    @Override
    public EntityGraph<?> getEntityGraph(String s) {
        return null;
    }

    @Override
    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
        return null;
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param flag the exception flag
     */
    public void enablePersistenceException(boolean flag) {
        this.expFlag = flag;
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param flag the exception flag
     */
    public void enableTransactionException(boolean flag) {
        this.transactionExp = flag;
    }
}
