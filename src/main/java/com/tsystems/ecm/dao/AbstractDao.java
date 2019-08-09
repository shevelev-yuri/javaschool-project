package com.tsystems.ecm.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    public void persist(T entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
