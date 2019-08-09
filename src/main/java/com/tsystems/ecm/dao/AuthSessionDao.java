package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.AuthSessionEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class AuthSessionDao extends AbstractDao<AuthSessionEntity> {

    private static final String SELECT_FROM_AUTH_SESSIONS_WHERE_USER_LOGIN = "select * from auth_sessions where userLogin = ?";

    public void save(AuthSessionEntity authSession) {
        persist(authSession);
    }

    public AuthSessionEntity get(String sid) {
        return getSessionFactory().getCurrentSession().get(AuthSessionEntity.class, sid);
    }

    public AuthSessionEntity getByLogin(String login) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createNativeQuery(
                SELECT_FROM_AUTH_SESSIONS_WHERE_USER_LOGIN, AuthSessionEntity.class).setParameter(1, login);
        AuthSessionEntity authSession;
        try {
            authSession = (AuthSessionEntity) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return authSession;
    }

    public void remove(String sid) {
        Session session = getSessionFactory().getCurrentSession();
        AuthSessionEntity authSession = session.byId(AuthSessionEntity.class).load(sid);
        delete(authSession);
    }
}
