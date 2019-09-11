package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.enums.EventStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Event DTO class.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EventDto {

    /**
     * The id.
     */
    private long id;

    /**
     * The patient this event belongs to.
     */
    private PatientDto patient;

    /**
     * The scheduled date and time.
     */
    private LocalDateTime scheduledDatetime;

    /**
     * The event's status.
     */
    private EventStatus eventStatus;

    /**
     * The treatment for this event.
     */
    private TreatmentDto treatment;

    /**
     * The cancellation reason.
     */
    private String cancelReason;

    /**
     * The appointment id this event created by.
     */
    private long appointmentIdCreatedBy;
}
