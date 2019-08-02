package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.AuthSessionDao;
import com.tsystems.ecm.dao.UserDao;
import com.tsystems.ecm.dto.UserSession;
import com.tsystems.ecm.entity.AuthSessionEntity;
import com.tsystems.ecm.entity.UserEntity;
import com.tsystems.ecm.exception.ECMException;
import com.tsystems.ecm.service.AuthorizationSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class AuthorizationSessionServiceImpl implements AuthorizationSessionService {

    private ThreadLocal<String> authenticatedUser = new ThreadLocal<>();

    private final UserDao userDao;
    private final AuthSessionDao authSessionDao;

    @Autowired
    public AuthorizationSessionServiceImpl(UserDao userDao, AuthSessionDao authSessionDao) {
        this.userDao = userDao;
        this.authSessionDao = authSessionDao;
    }

    @Override
    public UserSession createOrUpdateSession(String login) {
        UserEntity user = userDao.getByLogin(login);
        if (user == null) {
            throw new ECMException();
        }

        AuthSessionEntity possibleSession = authSessionDao.getByLogin(login);
        LocalDateTime expiredDate = LocalDateTime.now().plusDays(1);

        if (possibleSession != null) {
            possibleSession.setExpiredDate(expiredDate);
            authSessionDao.save(possibleSession);

            return new UserSession(possibleSession.getSid(), expiredDate, possibleSession.getUserLogin());
        }

        AuthSessionEntity entity = new AuthSessionEntity();
        entity.setUserLogin(login);
        entity.setExpiredDate(expiredDate);
        authSessionDao.save(entity);

        return new UserSession(entity.getSid(), expiredDate, entity.getUserLogin());
    }

    @Override
    public boolean isExpired(String sid) {
        AuthSessionEntity session = authSessionDao.getBySid(sid);
        return session == null || session.getExpiredDate().isBefore(LocalDateTime.now());
    }

    @Override
    public String getLoginBySessionId(String sid) {
        AuthSessionEntity entity = authSessionDao.getBySid(sid);
        if (entity != null) return entity.getUserLogin();
        else throw new ECMException();
    }

    @Override
    public void removeSession(String sid) {
        authSessionDao.remove(sid);
    }

    @Override
    public String getAuthenticatedUser() {
        return authenticatedUser.get();
    }

    @Override
    public void setAuthenticatedUser(String login) {
        authenticatedUser.set(login);
    }

}