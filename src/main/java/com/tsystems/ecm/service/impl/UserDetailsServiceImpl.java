package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.UserDao;
import com.tsystems.ecm.entity.User;
import com.tsystems.ecm.entity.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom implementation of the Spring <tt>UserDetailsService</tt> interface.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(UserDetailsServiceImpl.class);

    /**
     * The UserDao reference.
     */
    private UserDao userDao;

    /**
     * All args constructor.
     *
     * @param userDao the UserDao reference
     */
    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) {
        log.trace("loadUserByUsername method called");
        User user = userDao.getByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("No user with login '" + login + "' found!");
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getName(), user.getPassword(), true, true, true,
                true, getAuthority(user.getRole()));

        if (log.isDebugEnabled()) log.debug("Created Spring Security user: {}", userDetails.toString());

        return userDetails;
    }

    /*Helper method that returns List of GrantedAuthorities of the user by user's role field*/
    private List<GrantedAuthority> getAuthority(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }
}