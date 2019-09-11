package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Dao class for user entities.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Repository
public class UserDao extends AbstractDao<User> {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(UserDao.class);

    /**
     * SQL query to get user by login.
     */
    private static final String SELECT_USER_BY_LOGIN = "select * from users where login = ?";

    /**
     * HQL query to get all users.
     */
    private static final String SELECT_ALL_USERS = "FROM User";

    /**
     * Gets the user by login.
     *
     * @param login the user's login
     * @return the user entity
     */
    public User getByLogin(String login) {
        Query query = getSessionFactory().getCurrentSession()
                .createNativeQuery(SELECT_USER_BY_LOGIN, User.class)
                .setParameter(1, login);

        User user;
        try {
            user = (User) query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());

            return null;
        } catch (Exception e) {
            log.warn(e.getMessage());

            return null;
        }
        log.debug("User with login \"{}\" found!", login);
        return user;
    }

    /**
     * Gets the list containing all users.
     *
     * @return the {@code List} containing all users
     */
    public List<User> getAll() {
        return getSessionFactory().getCurrentSession()
                .createQuery(SELECT_ALL_USERS, User.class)
                .getResultList();
    }

    /**
     * Saves user entity
     *
     * @param user the user entity
     */
    public void save(User user) {
        persist(user);
    }
}
