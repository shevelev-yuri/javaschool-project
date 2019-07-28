package com.tsystems.ecm.service;

import com.tsystems.ecm.entity.PatientEntity;

import java.util.List;

public interface PatientService {

    List<PatientEntity> getPatients();

    void savePatient(PatientEntity thePatient);

    PatientEntity getPatient(int theId);

    void deletePatient(int theId);
}
