package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {

    long addAppointment(AppointmentDto appointmentDto);

    List<AppointmentDto> getAllByPatientId(long id);

}
