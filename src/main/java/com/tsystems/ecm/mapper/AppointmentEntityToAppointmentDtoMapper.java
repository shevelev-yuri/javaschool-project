package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentEntityToAppointmentDtoMapper {

    private PatientEntityToPatientDtoMapper toPatientDtoMapper;

    private TreatmentEntityToTreatmentDtoMapper toTreatmentDtoMapper;

    public AppointmentEntityToAppointmentDtoMapper(PatientEntityToPatientDtoMapper toPatientDtoMapper,
                                                   TreatmentEntityToTreatmentDtoMapper toTreatmentDtoMapper) {
        this.toPatientDtoMapper = toPatientDtoMapper;
        this.toTreatmentDtoMapper = toTreatmentDtoMapper;
    }

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
