package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.PatientDto;

import java.util.List;

public interface PatientService {

    List<PatientDto> getAllPatients();

    PatientDto get(long id);

    void delete(long id);

    long addPatient(PatientDto patient);
}
