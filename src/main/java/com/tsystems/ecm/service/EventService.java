package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.EventDto;

import java.util.List;

public interface EventService {

    List<EventDto> getAll();

    List<EventDto> getAllByPatientId(long id);

    int addEvents(List<EventDto> events);

    void setCancelledByAppointmentId(long appointmentId);

    void setAccomplishedById(long id);

    void setCancelledById(long id);
}
