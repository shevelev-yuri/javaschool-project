package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.enums.TreatmentType;
import lombok.*;

/**
 * Appointment DTO class.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AppointmentDto {

    /**
     * The id.
     */
    private long id;

    /**
     * The patient this appointment belongs to.
     */
    private PatientDto patient;

    /**
     * The treatment of this appointment.
     */
    private TreatmentDto treatment;

    /**
     * The treatment type.
     */
    private TreatmentType type;

    /**
     * The regimen.
     */
    private String regimen;

    /**
     * The user-friendly representation of regimen.
     */
    private String regimenString;

    /**
     * The duration (in weeks).
     */
    private int duration;

    /**
     * The dose.
     */
    private String dose;

}
