package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.config.TestConfig;
import com.tsystems.ecm.dao.UserDao;
import com.tsystems.ecm.entity.User;
import com.tsystems.ecm.entity.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class,
        loader = AnnotationConfigContextLoader.class)
public class UserDetailsServiceImplTest {

    private static final Logger log = LogManager.getLogger(UserDetailsServiceImplTest.class);

    private static final String LOGIN_INVALID = "no such user";

    private static final User USER = new User();
    private static final String USER_LOGIN = "login";
    private static final String USER_NAME = "name";
    private static final String USER_PWSD = "pass";
    private static final Role USER_ROLE = Role.DOCTOR;

    @Autowired
    public UserDao userDao;

    @Autowired
    public UserDetailsService userDetailsService;

    @BeforeClass
    public static void setUp() {
        log.trace("Setting up user entity.");
        USER.setLogin(USER_LOGIN);
        USER.setPassword(USER_PWSD);
        USER.setName(USER_NAME);
        USER.setRole(USER_ROLE);
        log.trace("User: {}", USER.toString());
    }

    @Before
    public void setDao() {
        when(userDao.getByLogin(LOGIN_INVALID)).thenReturn(null);
        when(userDao.getByLogin(USER_LOGIN)).thenReturn(USER);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_fail() {
        log.info("Testing: if no such user exists, return exception.");
        //test
        userDetailsService.loadUserByUsername(LOGIN_INVALID);
        log.info("Test passed!");
    }

    @Test
    public void loadUserByUsername_success() {
        log.info("Testing: create Spring Security user");
        //expected
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.name()));
        UserDetails expected = new org.springframework.security.core.userdetails.User(USER_NAME, USER_PWSD, true, true, true, true, authorities);

        //actual
        UserDetails actual = userDetailsService.loadUserByUsername(USER_LOGIN);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }
}