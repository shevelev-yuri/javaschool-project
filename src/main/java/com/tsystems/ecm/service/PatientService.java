package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.PatientDto;

import java.util.List;

public interface PatientService {

    List<PatientDto> getAllPatients();

    PatientDto getPatientById(int id);

    void deletePatient(int id);

    long addPatientAndReturnId(PatientDto patient);
}
