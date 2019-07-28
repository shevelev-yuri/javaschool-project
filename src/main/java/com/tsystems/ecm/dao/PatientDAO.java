package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.PatientEntity;

import java.util.List;

public interface PatientDAO {

    PatientEntity getPatient(int theId);

    List getPatients();

    void savePatient(PatientEntity thePatient);

    void deletePatient(int theId);

}
