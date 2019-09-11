package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.entity.Appointment;
import org.springframework.stereotype.Component;

/**
 * Mapper class
 */
@Component
public class AppointmentEntityToAppointmentDtoMapper {

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
    public AppointmentEntityToAppointmentDtoMapper(PatientEntityToPatientDtoMapper toPatientDtoMapper,
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
    public AppointmentDto map(Appointment src) {
        AppointmentDto dst = new AppointmentDto();

        dst.setId(src.getId());

        dst.setPatient(toPatientDtoMapper.map(src.getPatient()));
        dst.setTreatment(toTreatmentDtoMapper.map(src.getTreatment()));

        dst.setRegimen(src.getRegimen());
        dst.setDuration(src.getDuration());
        dst.setDose(src.getDose());

        return dst;
    }

}
