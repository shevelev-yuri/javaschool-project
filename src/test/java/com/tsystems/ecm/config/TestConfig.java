package com.tsystems.ecm.config;

import com.tsystems.ecm.dao.*;
import com.tsystems.ecm.mapper.*;
import com.tsystems.ecm.service.impl.*;
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
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl(userDao());
    }

    @Bean
    public AppointmentServiceImpl appointmentService() {
        return new AppointmentServiceImpl(appointmentDao(), appointmentDtoToAppointmentEntityMapper(), appointmentEntityToAppointmentDtoMapper());
    }

    @Bean
    public EventServiceImpl eventService() {
        return new EventServiceImpl(eventDao(), eventDtoToEventEntityMapper(), eventEntityToEventDtoMapper());
    }

    @Bean
    public RegimenProcessorServiceImpl regimenProcessorService() {
        return new RegimenProcessorServiceImpl();
    }

    @Bean
    public TreatmentServiceImpl treatmentService() {
        return new TreatmentServiceImpl(treatmentDao(), treatmentEntityToTreatmentDtoMapper());
    }

    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl(userDao(), userEntityToUserDtoMapper(), passwordEncoder());
    }

    @Bean
    public PatientServiceImpl patientService() {
        return new PatientServiceImpl(patientDao(), patientEntityToPatientDtoMapper(), patientDtoToPatientEntityMapper());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(7);
    }

    //----------DAO----------
    @Bean
    public AppointmentDao appointmentDao() {
        return mock(AppointmentDao.class);
    }

    @Bean
    public EventDao eventDao() {
        return mock(EventDao.class);
    }

    @Bean
    public UserDao userDao() {
        return mock(UserDao.class);
    }

    @Bean
    public PatientDao patientDao() {
        return mock(PatientDao.class);
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

    @Bean
    public TreatmentDtoToTreatmentEntityMapper treatmentDtoToTreatmentEntityMapper() {
        return new TreatmentDtoToTreatmentEntityMapper();
    }

    @Bean
    public EventDtoToEventEntityMapper eventDtoToEventEntityMapper() {
        return new EventDtoToEventEntityMapper(patientDtoToPatientEntityMapper(), treatmentDtoToTreatmentEntityMapper());
    }

    @Bean
    public EventEntityToEventDtoMapper eventEntityToEventDtoMapper() {
        return new EventEntityToEventDtoMapper(patientEntityToPatientDtoMapper(), treatmentEntityToTreatmentDtoMapper());
    }

    @Bean
    public PatientEntityToPatientDtoMapper patientEntityToPatientDtoMapper() {
        return new PatientEntityToPatientDtoMapper();
    }

    @Bean
    public PatientDtoToPatientEntityMapper patientDtoToPatientEntityMapper() {
        return new PatientDtoToPatientEntityMapper();
    }

    @Bean
    public AppointmentDtoToAppointmentEntityMapper appointmentDtoToAppointmentEntityMapper() {
        return new AppointmentDtoToAppointmentEntityMapper(patientDtoToPatientEntityMapper(), treatmentDtoToTreatmentEntityMapper());
    }

    @Bean
    public AppointmentEntityToAppointmentDtoMapper appointmentEntityToAppointmentDtoMapper() {
        return new AppointmentEntityToAppointmentDtoMapper(patientEntityToPatientDtoMapper(), treatmentEntityToTreatmentDtoMapper());
    }
}
