package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.TreatmentDto;

import java.util.List;

/**
 * This service provides logic for manipulations with treatments.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
public interface TreatmentService {

    /**
     * Gets the list of all treatments.
     *
     * @return the {@code List} of all treatments
     */
    List<TreatmentDto> getAll();

    /**
     * Gets the patient by its {@code id}.
     *
     * @param treatmentId the id of the treatment
     * @return the requested treatment DTO
     */
    TreatmentDto get(long treatmentId);
}
