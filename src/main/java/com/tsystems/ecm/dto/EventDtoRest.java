package com.tsystems.ecm.dto;

import lombok.*;

/**
 * Event DTO class for REST requests.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EventDtoRest {

    /**
     * The patient this event belongs to.
     */
    private String patient;

    /**
     * The scheduled date and time.
     */
    private String scheduledDatetime;

    /**
     * The event's status.
     */
    private String eventStatus;

    /**
     * The treatment name for this event.
     */
    private String treatment;

    /**
     * The cancellation reason.
     */
    private String cancelReason;

}
