package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.EventDto;

import java.util.List;

public interface EventService {

    List<EventDto> getAll();

    List<EventDto> getAllByPatientId(long id, int currentPage, int eventsPageSizeDefault);

    List<EventDto> getAllToday(int pageSize, int pageNumber);

    List<EventDto> getAllByPage(int pageNumber, int pageSize);

    int addEvents(List<EventDto> events);

    void setCancelledByAppointmentId(long appointmentId);

    List<EventDto> getClosest(int pageSize, int pageNumber);

    int getTotalPages(int pageSize, String queryString);

    void setAccomplishedById(long id);

    void setCancelledById(long id);

}
