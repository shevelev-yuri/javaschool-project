package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.EventDao;
import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.entity.EventEntity;
import com.tsystems.ecm.mapper.EventDtoToEventEntityMapper;
import com.tsystems.ecm.mapper.EventEntityToEventDtoMapper;
import com.tsystems.ecm.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
    @Transactional
    public List<EventDto> getAll() {
        List<EventEntity> entities = eventDao.getAll();
        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<EventDto> getAllByPatientId(long id) {
        List<EventEntity> entities = eventDao.getAllByPatientId(id);

        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int addEvents(List<EventDto> events) {
        for (EventDto event : events) {
            EventEntity entity = mapperDtoToEntity.map(event);
            eventDao.save(entity);
        }

        return events.size();
    }

}
