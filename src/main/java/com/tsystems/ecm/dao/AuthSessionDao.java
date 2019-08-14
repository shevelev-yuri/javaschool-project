package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.AuthSessionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class AuthSessionDao extends AbstractDao<AuthSessionEntity> {

    private static final String SELECT_AUTH_SESSIONS_BY_USER_LOGIN = "select * from auth_sessions where userLogin = ?";

    public void save(AuthSessionEntity authSession) {
        persist(authSession);
    }

    public AuthSessionEntity get(String sid) {
        return getSessionFactory().getCurrentSession().get(AuthSessionEntity.class, sid);
    }

    public AuthSessionEntity getByLogin(String login) {
        Query query = getSessionFactory().getCurrentSession()
                .createNativeQuery(SELECT_AUTH_SESSIONS_BY_USER_LOGIN, AuthSessionEntity.class)
                .setParameter(1, login);

        AuthSessionEntity authSession;
        try {
            authSession = (AuthSessionEntity) query.getSingleResult();
        } catch (Exception e) {
            //TODO handle
            return null;
        }

        return authSession;
    }

    public void remove(String sid) {
        AuthSessionEntity authSession = getSessionFactory().getCurrentSession().
                byId(AuthSessionEntity.class)
                .load(sid);

        delete(authSession);
    }
}
