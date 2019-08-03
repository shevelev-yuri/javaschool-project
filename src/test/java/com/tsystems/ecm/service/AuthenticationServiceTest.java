package com.tsystems.ecm.service;

import com.tsystems.ecm.config.TestConfig;
import com.tsystems.ecm.dao.UserDao;
import com.tsystems.ecm.entity.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class,
        loader = AnnotationConfigContextLoader.class)
public class AuthenticationServiceTest {

    private static final Logger log = LogManager.getLogger(AuthenticationServiceTest.class);

    @Autowired
    public AuthenticationService authenticationService;

    private static final String LOGIN = "user";
    private static final String PASSWORD = "secret";
    private static final String INVALID_PASSWORD = "invalid";
    private static final UserEntity USER = new UserEntity();

    @Autowired
    private UserDao userDao;

    @Before
    public void setup() {
        USER.setLogin(LOGIN);
        USER.setPassword(PASSWORD);
        when(userDao.getByLogin(LOGIN)).thenReturn(USER);
    }

    @Test
    public void authenticateShouldPassIfValidInput() {
        log.trace("Testing: login-password is valid.");
        assertTrue(authenticationService.authenticate(LOGIN, PASSWORD));
        log.trace("Test passed!");

    }

    @Test
    public void authenticateShouldFailIfInvalidInput() {
        log.trace("Testing: login-password is invalid.");
        assertFalse(authenticationService.authenticate(LOGIN, INVALID_PASSWORD));
        log.trace("Test passed!");

    }

    @Test
    public void authenticateShouldFailIfNoUserFound() {
        log.trace("Testing: user not found.");
        when(userDao.getByLogin(LOGIN)).thenReturn(null);
        assertFalse(authenticationService.authenticate(LOGIN, PASSWORD));
        log.trace("Test passed!");

    }
}