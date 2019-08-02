package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class UserDao extends AbstractDao<UserEntity> {

    private static final Logger log = LogManager.getLogger(UserDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    public UserEntity getByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery("select * from users where login = ?", UserEntity.class).setParameter(1, login);
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
