package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {

    List<AppointmentDto> getAllByPatientId(long id);

    void cancelAppointmentById(long id);

    long addOrUpdateAppointment(AppointmentDto appointmentDto);

    AppointmentDto get(long id);
}
