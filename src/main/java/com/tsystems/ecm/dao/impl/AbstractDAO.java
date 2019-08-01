package com.tsystems.ecm.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractDAO<T> {

    @Autowired
    private SessionFactory sessionFactory;

    public void persist(T entity) {
        sessionFactory.getCurrentSession().persist(entity);
    }

    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

}
