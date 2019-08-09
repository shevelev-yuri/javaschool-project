package com.tsystems.ecm.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserEntityTest {


    private static final long ID = 1;
    private static final String LOGIN = "login";
    private static final String PSWD = "pswd";
    private static final UserEntity user = new UserEntity();

    @Before
    public void setUp() {
        user.setId(ID);
        user.setLogin(LOGIN);
        user.setPassword(PSWD);
    }

    @Test
    public void getId_success() {
        assertEquals(ID, user.getId());
    }

    @Test
    public void setId_success() {
        user.setId(2);
        assertNotEquals(ID,user.getId());
    }

    @Test
    public void getLogin_success() {
        assertEquals(LOGIN, user.getLogin());
    }

    @Test
    public void setLogin_success() {
        user.setLogin("newlogin");
        assertNotEquals(LOGIN, user.getLogin());
    }

    @Test
    public void getPassword_success() {
        assertEquals(PSWD, user.getPassword());
    }

    @Test
    public void setPassword_success() {
        user.setPassword("newpswd");
        assertNotEquals(PSWD, user.getPassword());
    }
}