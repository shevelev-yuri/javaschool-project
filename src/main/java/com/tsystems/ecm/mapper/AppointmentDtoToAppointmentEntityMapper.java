package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentDtoToAppointmentEntityMapper {

    private PatientDtoToPatientEntityMapper toPatientEntityMapper;

    private TreatmentDtoToTreatmentEntityMapper toTreatmentEntityMapper;

    public AppointmentDtoToAppointmentEntityMapper(PatientDtoToPatientEntityMapper toPatientEntityMapper,
                                                   TreatmentDtoToTreatmentEntityMapper toTreatmentEntityMapper) {
        this.toPatientEntityMapper = toPatientEntityMapper;
        this.toTreatmentEntityMapper = toTreatmentEntityMapper;
    }

    public Appointment map(AppointmentDto src) {
        Appointment dst = new Appointment();

        dst.setPatient(toPatientEntityMapper.map(src.getPatient()));
        dst.getPatient().setId(src.getPatient().getId());
        dst.setTreatment(toTreatmentEntityMapper.map(src.getTreatment()));
        dst.getTreatment().setId(src.getTreatment().getId());
        dst.setType(src.getType());
        dst.setRegimen(src.getRegimen());
        dst.setDuration(src.getDuration());
        dst.setDose(src.getDose());

        return dst;
    }
}
