package com.tsystems.ecm;

import com.tsystems.ecm.entity.User;
import com.tsystems.ecm.entity.enums.Role;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;


public class JPAHibernateCRUDTest extends JPAHibernateTest {

    @Test
    public void testGetObjectById_success() {
        User user = em.find(User.class, 1L);
        assertNotNull(user);
    }

    @Test
    public void testGetAll_success() {
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        assertEquals(1, users.size());
    }

    @Test
    public void testPersist_success() {
        em.getTransaction().begin();
        User user = new User();
        user.setLogin("Login 1");
        user.setPassword("Password 1");
        user.setName("Name 1");
        user.setRole(Role.ADMIN);
        em.persist(user);
        em.getTransaction().commit();

        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void testDelete_success(){
        User user = em.find(User.class, 1L);

        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();

        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        assertEquals(1, users.size());
    }

}