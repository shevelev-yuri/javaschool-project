package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.PatientDto;

import java.util.List;

/**
 * This service provides logic for manipulations with patients.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
public interface PatientService {

    /**
     * Gets the list of all patients.
     *
     * @return the {@code List} of all patients
     */
    List<PatientDto> getAll();

    /**
     * Gets the patient by the {@code id}.
     *
     * @param id the id of the patient
     * @return the requested patient DTO
     */
    PatientDto get(long id);

    /**
     * Saves the patient.
     *
     * @param patient the patient DTO to be saved in the database
     * @return the {@code id} of saved patient entity.
     */
    long addPatient(PatientDto patient);

    /**
     * Sets the status of the specified patient to 'Discharged'.
     *
     * @param id the patient's id
     */
    void dischargePatient(long id);
}
