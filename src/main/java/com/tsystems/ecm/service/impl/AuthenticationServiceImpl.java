package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.UserDAO;
import com.tsystems.ecm.entity.UserEntity;
import com.tsystems.ecm.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public boolean authenticate(String login, String password) {
        UserEntity entity = userDAO.getByLogin(login);
        return entity != null && entity.getPassword().equals(password);
    }
}
