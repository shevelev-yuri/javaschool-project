package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.AppointmentDto;

import java.util.List;

/**
 * This service provides logic for manipulations with appointments.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
public interface AppointmentService {

    /**
     * Gets the {@code List} containing all appointments DTOs of the patient with {@code id}.
     *
     * @param id the id of the patient
     * @return the {@code List}containing all appointments of the specified patient
     */
    List<AppointmentDto> getAllByPatientId(long id);

    /**
     * Permanently removes appointment from database.
     *
     * @param id the id of the appointment to be removed
     */
    void cancelAppointmentById(long id);

    /**
     * Saves or updates the {@code appointmentDto} and returns its {@code id}.
     *
     * @param appointmentDto the appointment DTO to be saved or updated in the database
     * @return the {@code id} of saved or updated appointment entity.
     */
    long addOrUpdateAppointment(AppointmentDto appointmentDto);

    /**
     * Gets the appointment by its {@code id}.
     *
     * @param id the id of the appointment
     * @return the requested appointment DTO
     */
    AppointmentDto get(long id);
}
