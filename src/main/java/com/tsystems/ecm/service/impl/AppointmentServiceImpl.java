package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.AppointmentDao;
import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.entity.AppointmentEntity;
import com.tsystems.ecm.mapper.AppointmentDtoToAppointmentEntityMapper;
import com.tsystems.ecm.mapper.AppointmentEntityToAppointmentDtoMapper;
import com.tsystems.ecm.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentDao appointmentDao;

    private AppointmentDtoToAppointmentEntityMapper toAppointmentEntityMapper;

    private AppointmentEntityToAppointmentDtoMapper toAppointmentDtoMapper;

    @Autowired
    public AppointmentServiceImpl(AppointmentDao appointmentDao,
                                  AppointmentDtoToAppointmentEntityMapper toAppointmentEntityMapper,
                                  AppointmentEntityToAppointmentDtoMapper toAppointmentDtoMapper) {
        this.appointmentDao = appointmentDao;
        this.toAppointmentEntityMapper = toAppointmentEntityMapper;
        this.toAppointmentDtoMapper = toAppointmentDtoMapper;
    }

    /**
     * Calls the appropriate DAO to save the {@code appointmentDto} and returns its {@code id}.
     *
     * @param appointmentDto Appointment DTO to be saved in database
     * @return the {@code id} of saved appointment entity
     */
    @Override
    public long addAppointment(AppointmentDto appointmentDto) {
        AppointmentEntity appointmentEntity = toAppointmentEntityMapper.map(appointmentDto);
        appointmentDao.save(appointmentEntity);
        return appointmentEntity.getId();
    }

    /**
     * Calls the appropriate DAO to retrieve a {@code List} containing all appointments DTOs
     * of the patient with {@code id}.
     *
     * @param id the id of the patient
     * @return a {@code List} that contains all appointments of the specified patient
     */
    @Override
    public List<AppointmentDto> getAllByPatientId(long id) {
        List<AppointmentEntity> entities = appointmentDao.getAllByPatientId(id);

        return entities.stream().map(toAppointmentDtoMapper::map).collect(Collectors.toList());
    }
}
