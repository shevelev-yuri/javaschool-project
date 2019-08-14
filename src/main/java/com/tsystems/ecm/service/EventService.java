package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.entity.EventEntity;

import java.util.List;

public interface EventService {

    List<EventDto> getAll();

    List<EventDto> getAllByPatientId(long id);

    int addEvents(List<EventDto> events);

    List<EventEntity> getAllByAppointmentId(long id);

    void setCancelledDueToAppointmentCancelling(long id);

    void setAccomplishedById(long id);

    void setCancelledById(long id);
}
