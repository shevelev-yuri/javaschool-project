package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDao extends AbstractDao<User> {

    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static final String SELECT_USER_BY_LOGIN = "select * from users where login = ?";
    private static final String SELECT_ALL_USERS = "FROM User";

    public User getByLogin(String login) {
        Query query = getSessionFactory().getCurrentSession()
                .createNativeQuery(SELECT_USER_BY_LOGIN, User.class)
                .setParameter(1, login);

        User user;
        try {
            user = (User) query.getSingleResult();
        } catch (NoResultException nre) {
            //TODO handle
            log.debug(nre.getMessage());

            return null;
        } catch (Exception e) {
            //TODO handle
            log.warn(e.getMessage());

            return null;
        }
        log.info("User with login \"{}\" found!", login);
        return user;
    }

    public List<User> getAll() {
        return getSessionFactory().getCurrentSession()
                .createQuery(SELECT_ALL_USERS, User.class)
                .getResultList();
    }

    public void save(User user) {
        persist(user);
    }
}
