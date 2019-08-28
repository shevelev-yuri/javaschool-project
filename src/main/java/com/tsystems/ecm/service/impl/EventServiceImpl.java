package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.EventDao;
import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.entity.Event;
import com.tsystems.ecm.entity.enums.EventStatus;
import com.tsystems.ecm.mapper.EventDtoToEventEntityMapper;
import com.tsystems.ecm.mapper.EventEntityToEventDtoMapper;
import com.tsystems.ecm.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private EventDao eventDao;
    private EventDtoToEventEntityMapper mapperDtoToEntity;
    private EventEntityToEventDtoMapper mapperEntityToDto;

    @Autowired
    public EventServiceImpl(EventDao eventDao,
                            EventDtoToEventEntityMapper mapperDtoToEntity,
                            EventEntityToEventDtoMapper toEventDtoMapper) {
        this.eventDao = eventDao;
        this.mapperDtoToEntity = mapperDtoToEntity;
        this.mapperEntityToDto = toEventDtoMapper;
    }

    @Override
    public List<EventDto> getAll() {
        List<Event> entities = eventDao.getAll();
        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getAllByPatientId(long id, int currentPage, int pageSize) {
        List<Event> entities = eventDao.getAllByPatientId(id, currentPage, pageSize);

        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getAllByPage(int pageNumber, int pageSize) {
        List<Event> entities = eventDao.getAllByPage(pageNumber, pageSize);

        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getAllToday(int pageSize, int pageNumber) {
        List<Event> entities = eventDao.getAllToday(pageSize, pageNumber);

        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getClosest(int pageSize, int pageNumber) {
        List<Event> entities = eventDao.getClosest(pageSize, pageNumber);

        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    public int getTotalPages(int pageSize, String queryString) {

        return eventDao.getTotalPages(pageSize, queryString);
    }

    @Override
    public void setAccomplishedById(long id) {
        eventDao.get(id).setEventStatus(EventStatus.ACCOMPLISHED);
    }

    @Override
    public void setCancelledById(long id) {
        eventDao.get(id).setEventStatus(EventStatus.CANCELLED);
    }

    @Override
    public void setCancelledByAppointmentId(long appointmentId) {
        List<Event> events = getAllByAppointmentId(appointmentId);
        events.stream()
                .filter(event -> event.getEventStatus().equals(EventStatus.SCHEDULED))
                .forEach(event -> event.setEventStatus(EventStatus.CANCELLED));
    }

    private List<Event> getAllByAppointmentId(long id) {

        return eventDao.getAllByAppointmentId(id);
    }

    @Override
    public int addEvents(List<EventDto> events) {
        for (EventDto event : events) {
            Event entity = mapperDtoToEntity.map(event);
            eventDao.save(entity);
        }

        return events.size();
    }
}
