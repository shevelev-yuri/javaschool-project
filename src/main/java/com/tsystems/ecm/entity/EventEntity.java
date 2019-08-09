package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.EventStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private long eventId;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    private LocalDateTime scheduledDatetime;

    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus;

    @OneToOne
    @JoinColumn(name = "treatment_id")
    private TreatmentEntity treatment;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public LocalDateTime getScheduledDatetime() {
        return scheduledDatetime;
    }

    public void setScheduledDatetime(LocalDateTime scheduledDatetime) {
        this.scheduledDatetime = scheduledDatetime;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus status) {
        this.eventStatus = status;
    }

    public TreatmentEntity getTreatment() {
        return treatment;
    }

    public void setTreatment(TreatmentEntity treatment) {
        this.treatment = treatment;
    }
}
