package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.config.TestConfig;
import com.tsystems.ecm.dao.UserDao;
import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.entity.User;
import com.tsystems.ecm.entity.enums.Role;
import com.tsystems.ecm.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImplTest {

    private static final Logger log = LogManager.getLogger(UserServiceImplTest.class);

    private static final User DOC = new User();
    private static final long DOC_ID = 1L;
    private static final String DOC_LOGIN = "login_1";
    private static final String DOC_PSWD = "pass_1";
    private static final String DOC_NAME = "Doctor";
    private static final Role DOC_ROLE = Role.DOCTOR;

    private static final User NURSE = new User();
    private static final long NURSE_ID = 2L;
    private static final String NURSE_LOGIN = "login_2";
    private static final String NURSE_PSWD = "pass_2";
    private static final String NURSE_NAME = "Nurse";
    private static final Role NURSE_ROLE = Role.NURSE;

    private static final UserDto NEW_DOCTOR = new UserDto();

    private static final List<User> ALL_USERS = new ArrayList<>();

    @Autowired
    public UserService userService;

    @Autowired
    private UserDao userDao;

    @BeforeClass
    public static void setUp() {
        log.trace("Setting up two user entities.");
        DOC.setId(DOC_ID);
        DOC.setLogin(DOC_LOGIN);
        DOC.setPassword(DOC_PSWD);
        DOC.setName(DOC_NAME);
        DOC.setRole(DOC_ROLE);
        log.trace("Doctor user: {}", DOC.toString());
        ALL_USERS.add(DOC);

        NURSE.setId(NURSE_ID);
        NURSE.setLogin(NURSE_LOGIN);
        NURSE.setPassword(NURSE_PSWD);
        NURSE.setName(NURSE_NAME);
        NURSE.setRole(NURSE_ROLE);
        log.trace("Nurse user: {}", NURSE.toString());
        ALL_USERS.add(NURSE);
    }

    @Before
    public void setDao() {
        when(userDao.getAll()).thenReturn(ALL_USERS);
        when(userDao.getByLogin(NURSE_LOGIN)).thenReturn(NURSE);
    }

    @Test
    public void getAllDoctors_success() {
        log.info("Testing: return list containing Doctor DTO.");
        //expected
        List<UserDto> expected = new ArrayList<>();
        UserDto doctor = new UserDto(DOC_ID, DOC_LOGIN, DOC_PSWD, DOC_NAME, DOC_ROLE);
        expected.add(doctor);

        //actual
        List<UserDto> actual = userService.getAllDoctors();
        //pswd hack
        actual.get(0).setPassword(DOC_PSWD);

        //test
        assertEquals(expected.get(0), actual.get(0));
        log.info("Test passed!");
    }

    @Test
    public void getUserByLogin_success() {
        log.info("Testing: return Nurse DTO.");
        //expected
        UserDto expected = new UserDto(NURSE_ID, NURSE_LOGIN, NURSE_PSWD, NURSE_NAME, NURSE_ROLE);

        //actual
        UserDto actual = userService.getUserByLogin(NURSE_LOGIN);
        //pswd hack
        actual.setPassword(NURSE_PSWD);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void registerNewUser_success() {
        log.info("Testing: save new user.");

        NEW_DOCTOR.setLogin("somelogin");
        NEW_DOCTOR.setPassword("pswd");
        NEW_DOCTOR.setName("name");
        NEW_DOCTOR.setRole(Role.DOCTOR);

        //expected
        long expected = 0L;

        //actual
        long actual = userService.registerNewUser(NEW_DOCTOR);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void registerNewUser_fail() {
        log.info("Testing: attempt to save user with login that already exists.");
        //expected
        long expected = -1L;
        UserDto user = new UserDto(NURSE_ID, NURSE_LOGIN, NURSE_PSWD, NURSE_NAME, NURSE_ROLE);

        //actual
        long actual = userService.registerNewUser(user);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }
}