package com.tsystems.ecm.config;

import com.tsystems.ecm.dao.TreatmentDao;
import com.tsystems.ecm.mapper.TreatmentEntityToTreatmentDtoMapper;
import com.tsystems.ecm.service.impl.TreatmentServiceImpl;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {


    @Bean
    public TreatmentServiceImpl treatmentService() {
        return new TreatmentServiceImpl(treatmentDao(), treatmentEntityToTreatmentDtoMapper());
    }

    @Bean
    public TreatmentDao treatmentDao() {
        return mock(TreatmentDao.class);
    }

    @Bean
    public SessionFactory sessionFactory() {
        return mock(SessionFactory.class);
    }

    @Bean
    public TreatmentEntityToTreatmentDtoMapper treatmentEntityToTreatmentDtoMapper() {
        return new TreatmentEntityToTreatmentDtoMapper();
    }
}
