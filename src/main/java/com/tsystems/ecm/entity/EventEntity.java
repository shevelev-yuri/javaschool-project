package com.tsystems.ecm.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    private String eventId;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
    @Column(name = "scheduled_datetime")
    private LocalDateTime scheduledDatetime;
    private String status;
    @OneToOne
    @JoinColumn(name = "treatment_id")
    private TreatmentEntity treatment;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TreatmentEntity getTreatment() {
        return treatment;
    }

    public void setTreatment(TreatmentEntity treatment) {
        this.treatment = treatment;
    }
}
