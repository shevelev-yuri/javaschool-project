package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.AppointmentDao;
import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.entity.AppointmentEntity;
import com.tsystems.ecm.mapper.AppointmentDtoToAppointmentEntityMapper;
import com.tsystems.ecm.mapper.AppointmentEntityToAppointmentDtoMapper;
import com.tsystems.ecm.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
     * Calls the appropriate DAO to save or update the {@code appointmentDto} and returns its {@code id}.
     *
     * @param appointmentDto Appointment DTO to be saved or updated in database
     * @return the {@code id} of saved or updated appointment entity.
     */
    @Override
    @Transactional
    public long addOrUpdateAppointment(AppointmentDto appointmentDto) {
        AppointmentEntity appointment;
        if (appointmentDto.getId() != 0) {
            appointment = appointmentDao.get(appointmentDto.getId());
            AppointmentEntity src = toAppointmentEntityMapper.map(appointmentDto);
            appointment.setPatient(src.getPatient());
            appointment.setTreatment(src.getTreatment());
            appointment.setRegimen(src.getRegimen());
            appointment.setDuration(src.getDuration());
            appointment.setDose(src.getDose());
        } else {
            appointment = toAppointmentEntityMapper.map(appointmentDto);
        }
        appointmentDao.save(appointment);
        return appointment.getId();
    }

    /**
     * Calls the appropriate DAO to retrieve the appointment by its {@code id}.
     *
     * @param id the id of the appointment
     * @return requested Appointment DTO
     */
    @Override
    @Transactional
    public AppointmentDto get(long id) {
        return toAppointmentDtoMapper.map(appointmentDao.get(id));
    }

    /**
     * Calls the appropriate DAO to retrieve a {@code List} containing all appointments DTOs
     * of the patient with {@code id}.
     *
     * @param id the id of the patient
     * @return a {@code List} that contains all appointments of the specified patient
     */
    @Override
    @Transactional
    public List<AppointmentDto> getAllByPatientId(long id) {
        List<AppointmentEntity> entities = appointmentDao.getAllByPatientId(id);

        return entities.stream().map(toAppointmentDtoMapper::map).collect(Collectors.toList());
    }

    /**
     * Permanently removes appointment from database.
     *
     * @param id the id of the appointment to be removed
     */
    @Override
    @Transactional
    public void cancelAppointmentById(long id) {
        //TODO change logic - implement status change instead of removal
        AppointmentEntity appointmentToDelete = appointmentDao.get(id);
        appointmentDao.delete(appointmentToDelete);
    }

}
