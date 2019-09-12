package com.tsystems.ecm.config;

import com.tsystems.ecm.dao.TreatmentDao;
import com.tsystems.ecm.dao.UserDao;
import com.tsystems.ecm.mapper.TreatmentEntityToTreatmentDtoMapper;
import com.tsystems.ecm.mapper.UserEntityToUserDtoMapper;
import com.tsystems.ecm.service.impl.TreatmentServiceImpl;
import com.tsystems.ecm.service.impl.UserServiceImpl;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    //----------Service implementations----------
    @Bean
    public TreatmentServiceImpl treatmentService() {
        return new TreatmentServiceImpl(treatmentDao(), treatmentEntityToTreatmentDtoMapper());
    }

    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl(userDao(), userEntityToUserDtoMapper(), passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(7);
    }

    //----------DAO----------
    @Bean
    public UserDao userDao() {
        return mock(UserDao.class);
    }

    @Bean
    public TreatmentDao treatmentDao() {
        return mock(TreatmentDao.class);
    }

    @Bean
    public SessionFactory sessionFactory() {
        return mock(SessionFactory.class);
    }

    //----------Mappers----------
    @Bean
    public UserEntityToUserDtoMapper userEntityToUserDtoMapper() {
        return new UserEntityToUserDtoMapper();
    }

    @Bean
    public TreatmentEntityToTreatmentDtoMapper treatmentEntityToTreatmentDtoMapper() {
        return new TreatmentEntityToTreatmentDtoMapper();
    }
}
