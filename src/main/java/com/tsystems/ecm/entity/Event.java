package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.EventStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The event entity class.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "events")
public class Event {

    /**
     * The id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private long id;

    /**
     * The patient this event belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    /**
     * The scheduled date and time.
     */
    private LocalDateTime scheduledDatetime;

    /**
     * The event's status.
     */
    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus;

    /**
     * The treatment for this event.
     */
    @OneToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    /**
     * The cancellation reason.
     */
    private String cancelReason;

    /**
     * The appointment id this event created by.
     */
    private long appointmentIdCreatedBy;

}
