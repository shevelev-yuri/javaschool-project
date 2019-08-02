package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.AuthSessionEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class AuthSessionDao extends AbstractDao {

    @Autowired
    private SessionFactory sessionFactory;

    public AuthSessionEntity getByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(
                "select * from auth_sessions where user_login = ?", AuthSessionEntity.class).setParameter(1, login);
        AuthSessionEntity authSession;
        try {
            authSession = (AuthSessionEntity) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return authSession;
    }

    public AuthSessionEntity getBySid(String sid) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(AuthSessionEntity.class, sid);
    }

    public void save(AuthSessionEntity authSession) {
        persist(authSession);
    }

    public void remove(String sid) {
        delete(sid);
    }
}
