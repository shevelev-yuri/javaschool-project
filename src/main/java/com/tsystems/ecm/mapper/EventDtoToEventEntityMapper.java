package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper class
 */
@Component
public class EventDtoToEventEntityMapper {

    /**
     * The PatientDtoToPatientEntityMapper reference to handle patient mapping.
     */
    private PatientDtoToPatientEntityMapper toPatientEntityMapper;

    /**
     * The TreatmentDtoToTreatmentEntityMapper reference to handle treatment mapping.
     */
    private TreatmentDtoToTreatmentEntityMapper toTreatmentEntityMapper;

    /**
     * All args constructor.
     *
     * @param toPatientEntityMapper   the PatientDtoToPatientEntityMapper
     * @param toTreatmentEntityMapper the TreatmentDtoToTreatmentEntityMapper reference
     */
    @Autowired
    public EventDtoToEventEntityMapper(PatientDtoToPatientEntityMapper toPatientEntityMapper,
                                       TreatmentDtoToTreatmentEntityMapper toTreatmentEntityMapper) {
        this.toPatientEntityMapper = toPatientEntityMapper;
        this.toTreatmentEntityMapper = toTreatmentEntityMapper;
    }

    /**
     * Maps DTO to entity.
     *
     * @param src the source DTO to map from
     * @return the entity to map to
     */
    public Event map(EventDto src) {
        Event dst = new Event();

        dst.setPatient(toPatientEntityMapper.map(src.getPatient()));
        dst.getPatient().setId(src.getPatient().getId());
        dst.setTreatment(toTreatmentEntityMapper.map(src.getTreatment()));
        dst.getTreatment().setId(src.getTreatment().getId());
        dst.setEventStatus(src.getEventStatus());
        dst.setScheduledDatetime(src.getScheduledDatetime());
        if (src.getCancelReason() != null && !src.getCancelReason().isEmpty()) {
            dst.setCancelReason(src.getCancelReason());
        }
        dst.setAppointmentIdCreatedBy(src.getAppointmentIdCreatedBy());

        return dst;
    }
}
