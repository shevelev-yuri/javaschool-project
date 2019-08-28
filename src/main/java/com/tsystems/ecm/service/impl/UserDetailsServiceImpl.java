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

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LogManager.getLogger(UserDetailsServiceImpl.class);

    private UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) {
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

    private List<GrantedAuthority> getAuthority(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }
}