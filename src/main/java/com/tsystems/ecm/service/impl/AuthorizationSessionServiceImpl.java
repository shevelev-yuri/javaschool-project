package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.AuthSessionDao;
import com.tsystems.ecm.dao.UserDao;
import com.tsystems.ecm.dto.UserSessionDto;
import com.tsystems.ecm.entity.AuthSessionEntity;
import com.tsystems.ecm.exception.ECMException;
import com.tsystems.ecm.service.AuthorizationSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class AuthorizationSessionServiceImpl implements AuthorizationSessionService {

    private final UserDao userDao;
    private final AuthSessionDao authSessionDao;

    @Autowired
    public AuthorizationSessionServiceImpl(UserDao userDao, AuthSessionDao authSessionDao) {
        this.userDao = userDao;
        this.authSessionDao = authSessionDao;
    }

    @Override
    public UserSessionDto createOrUpdateSession(String login) {
        AuthSessionEntity possibleSession = authSessionDao.getByLogin(login);
        LocalDateTime expiredDate = LocalDateTime.now().plusDays(1);
        String userName = userDao.getByLogin(login).getName();

        if (possibleSession != null) {
            possibleSession.setExpiredDate(expiredDate);
            authSessionDao.save(possibleSession);

            return new UserSessionDto(possibleSession.getSid(), expiredDate, possibleSession.getUserLogin(), userName);
        }

        AuthSessionEntity entity = new AuthSessionEntity();
        entity.setUserLogin(login);
        entity.setExpiredDate(expiredDate);
        authSessionDao.save(entity);

        return new UserSessionDto(entity.getSid(), expiredDate, entity.getUserLogin(), userName);
    }

    @Override
    public boolean isExpired(String sid) {
        AuthSessionEntity session = authSessionDao.get(sid);
        return session == null || session.getExpiredDate().isBefore(LocalDateTime.now());
    }

    @Override
    public String getLoginBySessionId(String sid) {
        AuthSessionEntity sessionEntity = authSessionDao.get(sid);
        if (sessionEntity != null) return sessionEntity.getUserLogin();
        else throw new ECMException();
    }

    @Override
    public void removeSession(String sid) {
        authSessionDao.remove(sid);
    }

}