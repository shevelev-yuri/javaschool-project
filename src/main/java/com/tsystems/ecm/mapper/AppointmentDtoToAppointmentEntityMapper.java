package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.entity.Appointment;
import com.tsystems.ecm.entity.enums.TreatmentType;
import org.springframework.stereotype.Component;

/**
 * Mapper class
 */
@Component
public class AppointmentDtoToAppointmentEntityMapper {

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
     * @param toPatientEntityMapper   the PatientDtoToPatientEntityMapper reference
     * @param toTreatmentEntityMapper the TreatmentDtoToTreatmentEntityMapper reference
     */
    public AppointmentDtoToAppointmentEntityMapper(PatientDtoToPatientEntityMapper toPatientEntityMapper,
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
    public Appointment map(AppointmentDto src) {
        Appointment dst = new Appointment();

        dst.setPatient(toPatientEntityMapper.map(src.getPatient()));
        dst.getPatient().setId(src.getPatient().getId());
        dst.setTreatment(toTreatmentEntityMapper.map(src.getTreatment()));
        dst.getTreatment().setId(src.getTreatment().getId());
        dst.setType(src.getType());
        dst.setRegimen(src.getRegimen());
        dst.setDuration(src.getDuration());
        if(src.getType() == TreatmentType.MEDICATION) dst.setDose(src.getDose());

        return dst;
    }
}
