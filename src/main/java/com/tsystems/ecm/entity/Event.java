package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.EventStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime scheduledDatetime;

    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus;

    @OneToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    private String cancelReason;

    private long appointmentIdCreatedBy;

}
