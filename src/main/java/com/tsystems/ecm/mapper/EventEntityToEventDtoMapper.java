package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventEntityToEventDtoMapper {

    private PatientEntityToPatientDtoMapper toPatientDtoMapper;

    private TreatmentEntityToTreatmentDtoMapper toTreatmentDtoMapper;

    @Autowired
    public EventEntityToEventDtoMapper(PatientEntityToPatientDtoMapper toPatientDtoMapper,
                                       TreatmentEntityToTreatmentDtoMapper toTreatmentDtoMapper) {
        this.toPatientDtoMapper = toPatientDtoMapper;
        this.toTreatmentDtoMapper = toTreatmentDtoMapper;
    }

    public EventDto map(Event src) {
        EventDto dst = new EventDto();

        dst.setId(src.getId());

        dst.setPatient(toPatientDtoMapper.map(src.getPatient()));
        dst.setTreatment(toTreatmentDtoMapper.map(src.getTreatment()));

        dst.setScheduledDatetime(src.getScheduledDatetime());
        dst.setEventStatus(src.getEventStatus());
        dst.setAppointmentIdCreatedBy(src.getAppointmentIdCreatedBy());

        return dst;
    }
}
