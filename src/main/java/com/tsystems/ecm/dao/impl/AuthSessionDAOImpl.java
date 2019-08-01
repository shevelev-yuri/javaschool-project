package com.tsystems.ecm.dao.impl;

import com.tsystems.ecm.dao.AuthSessionDAO;
import com.tsystems.ecm.entity.AuthSessionEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;

@Component
public class AuthSessionDAOImpl extends AbstractDAO implements AuthSessionDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public AuthSessionEntity getByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(
                "select * from auth_sessions where user_login = ?1", AuthSessionEntity.class).setParameter(1, login);
        AuthSessionEntity authSession;
        try {
            authSession = (AuthSessionEntity) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return authSession;
    }

    @Override
    public AuthSessionEntity getBySid(String sid) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(AuthSessionEntity.class, sid);
    }

    @Override
    public void save(AuthSessionEntity authSession) {
        persist(authSession);
    }

    @Override
    public void remove(String sid) {
        delete(sid);
    }
}
