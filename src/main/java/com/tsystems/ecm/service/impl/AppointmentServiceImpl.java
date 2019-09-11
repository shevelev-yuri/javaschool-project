package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.AppointmentDao;
import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.entity.Appointment;
import com.tsystems.ecm.mapper.AppointmentDtoToAppointmentEntityMapper;
import com.tsystems.ecm.mapper.AppointmentEntityToAppointmentDtoMapper;
import com.tsystems.ecm.service.AppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic implementation of the <tt>AppointmentService</tt> interface.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(AppointmentServiceImpl.class);

    /**
     * The AppointmentDao reference.
     */
    private AppointmentDao appointmentDao;

    /**
     * The AppointmentDtoToAppointmentEntityMapper reference.
     */
    private AppointmentDtoToAppointmentEntityMapper toAppointmentEntityMapper;

    /**
     * The AppointmentEntityToAppointmentDtoMapper reference.
     */
    private AppointmentEntityToAppointmentDtoMapper toAppointmentDtoMapper;

    /**
     * All args constructor.
     *
     * @param appointmentDao            the AppointmentDao reference
     * @param toAppointmentEntityMapper the AppointmentDtoToAppointmentEntityMapper reference
     * @param toAppointmentDtoMapper    the AppointmentEntityToAppointmentDtoMapper reference
     */
    @Autowired
    public AppointmentServiceImpl(AppointmentDao appointmentDao,
                                  AppointmentDtoToAppointmentEntityMapper toAppointmentEntityMapper,
                                  AppointmentEntityToAppointmentDtoMapper toAppointmentDtoMapper) {
        this.appointmentDao = appointmentDao;
        this.toAppointmentEntityMapper = toAppointmentEntityMapper;
        this.toAppointmentDtoMapper = toAppointmentDtoMapper;
    }

    @Override
    @Transactional
    public long addOrUpdateAppointment(AppointmentDto appointmentDto) {
        log.trace("addOrUpdateAppointment method called");
        Appointment appointment;
        if (appointmentDto.getId() != 0) {
            appointment = appointmentDao.get(appointmentDto.getId());
            Appointment src = toAppointmentEntityMapper.map(appointmentDto);
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

    @Override
    @Transactional
    public AppointmentDto get(long id) {
        log.trace("get method called");

        return toAppointmentDtoMapper.map(appointmentDao.get(id));
    }

    @Override
    @Transactional
    public List<AppointmentDto> getAllByPatientId(long id) {
        log.trace("getAllByPatientId method called");
        List<Appointment> entities = appointmentDao.getAllByPatientId(id);

        return entities.stream().map(toAppointmentDtoMapper::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelAppointmentById(long id) {
        log.trace("cancelAppointmentById method called");

        //TODO change logic - implement status change instead of removal
        Appointment appointmentToDelete = appointmentDao.get(id);
        appointmentDao.delete(appointmentToDelete);
    }
}
