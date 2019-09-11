package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.dto.EventDto;

import java.util.List;

/**
 * This service provides internal logic for regimen manipulations.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
public interface RegimenProcessorService {

    /**
     * This method has two uses:
     * <p><ul>
     * <li>if generateEvents = true, then it represents user-friendly string of regimen
     * by parsing {@code String regimen} field of the appointment and generates events corresponding to the appointment's regimen;
     *
     * <li>if generateEvents = false, then it only represents user-friendly string of regimen
     * by parsing {@code String regimen} field of the appointment without generating the events.
     * </ul><p>
     *
     * @param appointmentDto the appointment DTO from which the regimen should be parsed
     * @param generateEvents the flag that checks whether or not to generate the events
     * @return the {@code List} of events DTOs corresponding to this appointment
     */
    List<EventDto> parseRegimen(AppointmentDto appointmentDto, boolean generateEvents);
}