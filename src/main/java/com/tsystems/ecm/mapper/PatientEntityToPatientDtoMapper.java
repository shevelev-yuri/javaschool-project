package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.entity.Patient;
import org.springframework.stereotype.Component;

/**
 * Mapper class
 */
@Component
public class PatientEntityToPatientDtoMapper {

    /**
     * Maps entity to DTO.
     *
     * @param src the source entity to map from
     * @return the DTO to map to
     */
    public PatientDto map(Patient src) {
        PatientDto dst = new PatientDto();

        dst.setId(src.getId());
        dst.setName(src.getName());
        dst.setDiagnosis(src.getDiagnosis());
        dst.setInsuranceNumber(src.getInsuranceNumber());
        dst.setDoctorName(src.getDoctorName());
        dst.setPatientStatus(src.getPatientStatus());

        return dst;
    }
}
