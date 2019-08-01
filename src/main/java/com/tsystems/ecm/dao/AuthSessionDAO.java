package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.AuthSessionEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthSessionDAO {

    AuthSessionEntity getByLogin(String login);

    AuthSessionEntity getBySid(String sid);

    void save(AuthSessionEntity user);

    void remove(String sid);
}
