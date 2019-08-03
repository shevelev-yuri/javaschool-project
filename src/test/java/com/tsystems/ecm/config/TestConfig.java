package com.tsystems.ecm.config;

import com.tsystems.ecm.dao.UserDao;
import com.tsystems.ecm.service.impl.AuthenticationServiceImpl;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public AuthenticationServiceImpl authenticationService() {
        return new AuthenticationServiceImpl();
    }

    @Bean
    public UserDao userDao() {
        return mock(UserDao.class);
    }

    @Bean
    public SessionFactory sessionFactory() {
        return mock(SessionFactory.class);
    }

}
