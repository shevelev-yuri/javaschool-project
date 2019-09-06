package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.dto.EventDtoRest;

import java.util.List;

public interface EventService {

    List<EventDto> getAll();

    List<EventDto> getAllByPatientId(long id, int currentPage, int eventsPageSizeDefault);

    List<EventDto> getAllToday(int pageSize, int pageNumber);

    List<EventDtoRest> getAllToday();

    List<EventDto> getAllByPage(int pageNumber, int pageSize);

    int addEvents(List<EventDto> events);

    void setCancelledById(long id, String reason);

    void setCancelledByAppointmentId(long appointmentId);

    List<EventDto> getClosest(int pageSize, int pageNumber);

    int getTotalPages(int pageSize, String queryString);

    void setAccomplishedById(long id);
}
