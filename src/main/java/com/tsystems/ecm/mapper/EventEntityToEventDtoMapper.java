package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper class
 */
@Component
public class EventEntityToEventDtoMapper {

    /**
     * The PatientEntityToPatientDtoMapper reference to handle patient mapping.
     */
    private PatientEntityToPatientDtoMapper toPatientDtoMapper;

    /**
     * The TreatmentEntityToTreatmentDtoMapper reference to handle treatment mapping.
     */
    private TreatmentEntityToTreatmentDtoMapper toTreatmentDtoMapper;

    /**
     * All args constructor.
     *
     * @param toPatientDtoMapper   the PatientEntityToPatientDtoMapper reference
     * @param toTreatmentDtoMapper the TreatmentEntityToTreatmentDtoMapper reference
     */
    @Autowired
    public EventEntityToEventDtoMapper(PatientEntityToPatientDtoMapper toPatientDtoMapper,
                                       TreatmentEntityToTreatmentDtoMapper toTreatmentDtoMapper) {
        this.toPatientDtoMapper = toPatientDtoMapper;
        this.toTreatmentDtoMapper = toTreatmentDtoMapper;
    }

    /**
     * Maps entity to DTO.
     *
     * @param src the source entity to map from
     * @return the DTO to map to
     */
    public EventDto map(Event src) {
        EventDto dst = new EventDto();

        dst.setId(src.getId());

        dst.setPatient(toPatientDtoMapper.map(src.getPatient()));
        dst.setTreatment(toTreatmentDtoMapper.map(src.getTreatment()));

        dst.setScheduledDatetime(src.getScheduledDatetime());
        dst.setEventStatus(src.getEventStatus());
        if (src.getCancelReason() != null && !src.getCancelReason().isEmpty()) {
            dst.setCancelReason(src.getCancelReason());
        }
        dst.setAppointmentIdCreatedBy(src.getAppointmentIdCreatedBy());

        return dst;
    }
}
