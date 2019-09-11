package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.EventDao;
import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.dto.EventDtoRest;
import com.tsystems.ecm.entity.Event;
import com.tsystems.ecm.entity.enums.EventStatus;
import com.tsystems.ecm.mapper.EventDtoToEventEntityMapper;
import com.tsystems.ecm.mapper.EventEntityToEventDtoMapper;
import com.tsystems.ecm.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.tsystems.ecm.utils.StringConstants.APPOINTMENT_CANCELLATION_EVENTS_CANCEL_REASON;

/**
 * Basic implementation of the <tt>EventService</tt> interface.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(EventServiceImpl.class);

    /**
     * The EventDao reference.
     */
    private EventDao eventDao;

    /**
     * The EventDtoToEventEntityMapper reference.
     */
    private EventDtoToEventEntityMapper mapperDtoToEntity;

    /**
     * The EventEntityToEventDtoMapper reference.
     */
    private EventEntityToEventDtoMapper mapperEntityToDto;

    /**
     * All args constructor.
     *
     * @param eventDao          the EventDao reference
     * @param mapperDtoToEntity the EventDtoToEventEntityMapper reference
     * @param toEventDtoMapper  the EventEntityToEventDtoMapper reference
     */
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
        log.trace("getAll method called");
        List<Event> entities = eventDao.getAll();

        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getAllByPatientId(long id, int pageNumber, int pageSize) {
        log.trace("getAllByPatientId method called");
        List<Event> entities = eventDao.getAllByPatientId(id, pageNumber, pageSize);

        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getAllByPage(int pageNumber, int pageSize) {
        log.trace("getAllByPage method called");
        List<Event> entities = eventDao.getAllByPage(pageNumber, pageSize);

        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getAllToday(int pageSize, int pageNumber) {
        log.trace("getAllToday(int pageSize, int pageNumber) method called");
        List<Event> entities = eventDao.getAllToday(pageSize, pageNumber);

        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    public List<EventDtoRest> getAllToday() {
        log.trace("getAllToday method called");
        List<Event> entities = eventDao.getAllToday();
        List<EventDtoRest> dtos = new ArrayList<>();
        for (Event event : entities) {
            EventDtoRest eventDto = new EventDtoRest(
                    event.getPatient().getName(),
                    event.getScheduledDatetime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm eeee").withLocale(Locale.ENGLISH)),
                    event.getEventStatus().name(),
                    event.getTreatment().getTreatmentName(),
                    event.getCancelReason());
            dtos.add(eventDto);
        }

        return dtos;
    }

    @Override
    public List<EventDto> getClosest(int pageSize, int pageNumber) {
        log.trace("getClosest method called");
        List<Event> entities = eventDao.getClosest(pageSize, pageNumber);

        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    public int getTotalPages(int pageSize, String queryString) {
        log.trace("getTotalPages method called");

        return eventDao.getTotalPages(pageSize, queryString);
    }

    @Override
    public void setAccomplishedById(long id) {
        log.trace("setAccomplishedById method called");
        eventDao.get(id).setEventStatus(EventStatus.ACCOMPLISHED);
    }

    @Override
    public void setCancelledById(long id, String reason) {
        log.trace("setCancelledById method called");
        Event event = eventDao.get(id);
        event.setEventStatus(EventStatus.CANCELLED);
        event.setCancelReason(reason);
    }

    @Override
    public void setCancelledByAppointmentId(long appointmentId) {
        log.trace("setCancelledByAppointmentId method called");
        List<Event> events = getAllByAppointmentId(appointmentId);
        events.stream()
                .filter(event -> event.getEventStatus().equals(EventStatus.SCHEDULED))
                .forEach(event -> {
                    event.setEventStatus(EventStatus.CANCELLED);
                    event.setCancelReason(APPOINTMENT_CANCELLATION_EVENTS_CANCEL_REASON);
                });
    }

    /*Helper method to get the list of all events by specified appointment*/
    private List<Event> getAllByAppointmentId(long id) {

        return eventDao.getAllByAppointmentId(id);
    }

    @Override
    public int addEvents(List<EventDto> events) {
        log.trace("addEvents method called");
        for (EventDto event : events) {
            Event entity = mapperDtoToEntity.map(event);
            eventDao.save(entity);
        }

        return events.size();
    }
}
