package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.AppointmentDto;

import javax.transaction.Transactional;
import java.util.List;

public interface AppointmentService {

    long addAppointment(AppointmentDto appointmentDto);

    List<AppointmentDto> getAllByPatientId(long id);

    void cancelAppointmentById(long id);
}
