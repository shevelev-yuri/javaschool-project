package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientDtoToPatientEntityMapper {

    public Patient map(PatientDto src) {
        Patient dst = new Patient();

        dst.setName(src.getName());
        dst.setDiagnosis(src.getDiagnosis());
        dst.setInsuranceNumber(src.getInsuranceNumber());
        dst.setDoctorName(src.getDoctorName());
        dst.setPatientStatus(src.getPatientStatus());

        return dst;
    }
}
