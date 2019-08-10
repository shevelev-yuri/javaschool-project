package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.entity.AppointmentEntity;
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

    public AppointmentEntity map(AppointmentDto src) {
        AppointmentEntity dst = new AppointmentEntity();

        dst.setPatient(toPatientEntityMapper.map(src.getPatient()));
        dst.setTreatment(toTreatmentEntityMapper.map(src.getTreatment()));

        dst.setType(src.getType());
        dst.setRegimen(src.getRegimen());
        dst.setDuration(src.getDuration());
        dst.setDose(src.getDose());

        return dst;
    }
}
