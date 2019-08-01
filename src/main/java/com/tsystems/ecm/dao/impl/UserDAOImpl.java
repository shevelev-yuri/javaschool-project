package com.tsystems.ecm.dao.impl;

import com.tsystems.ecm.dao.UserDAO;
import com.tsystems.ecm.entity.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger log = LogManager.getLogger(UserDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public UserEntity getByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery("select * from users where login = ?1", UserEntity.class).setParameter(1, login);
        UserEntity user;
        try {
             user = (UserEntity) query.getSingleResult();
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
        log.info("User with login \"{}\" found!", login);
        return user;
    }
}
