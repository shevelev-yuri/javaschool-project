package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.UserEntity;

public interface UserDAO {
    UserEntity getByLogin(String login);
}
