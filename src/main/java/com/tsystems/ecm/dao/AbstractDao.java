package com.tsystems.ecm.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract class of the Dao
 *
 * @param <T> the entity class
 * @author Yurii Shevelev
 * @version 1.0.0
 */
public abstract class AbstractDao<T> {

    /**
     * Hibernate sessionFactory.
     *
     * @see org.hibernate.SessionFactory SessionFactory
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Persists the entity.
     *
     * @param entity the entity to be persisted
     */
    public void persist(T entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    /**
     * Deletes the entity permanently.
     *
     * @param entity the entity to be deleted
     */
    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    /**
     * Gets SessionFactory reference.
     *
     * @return the SessionFactory reference
     */
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
