package com.tsystems.ecm;

import com.tsystems.ecm.entity.UserEntity;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;


public class JPAHibernateCRUDTest extends JPAHibernateTest {

    @Test
    public void testGetObjectById_success() {
        UserEntity user = em.find(UserEntity.class, 1);
        assertNotNull(user);
    }

    @Test
    public void testGetAll_success() {
        List<UserEntity> users = em.createQuery("SELECT u FROM UserEntity u", UserEntity.class).getResultList();
        assertEquals(1, users.size());
    }

    @Test
    public void testPersist_success() {
        em.getTransaction().begin();
        UserEntity user = new UserEntity();
        user.setLogin("Login 1");
        user.setPassword("Password 1");
        em.persist(user);
        em.getTransaction().commit();

        List<UserEntity> users = em.createQuery("SELECT u FROM UserEntity u", UserEntity.class).getResultList();

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void testDelete_success(){
        UserEntity user = em.find(UserEntity.class, 1);

        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();

        List<UserEntity> users = em.createQuery("SELECT u FROM UserEntity u", UserEntity.class).getResultList();
        assertEquals(1, users.size());
    }

}