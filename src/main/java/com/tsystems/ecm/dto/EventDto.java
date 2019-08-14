package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.enums.EventStatus;

import java.time.LocalDateTime;

public class EventDto {

    private long id;

    private PatientDto patient;

    private LocalDateTime scheduledDatetime;

    private EventStatus eventStatus;

    private TreatmentDto treatment;

    private long appointmentIdCreatedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
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

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public TreatmentDto getTreatment() {
        return treatment;
    }

    public void setTreatment(TreatmentDto treatment) {
        this.treatment = treatment;
    }

    public long getAppointmentIdCreatedBy() {
        return appointmentIdCreatedBy;
    }

    public void setAppointmentIdCreatedBy(long appointmentIdCreatedBy) {
        this.appointmentIdCreatedBy = appointmentIdCreatedBy;
    }

    @Override
    public String toString() {
        return "EventDto{" +
                "id=" + id +
                ", patient=" + patient.toString() +
                ", scheduledDatetime=" + scheduledDatetime +
                ", eventStatus=" + eventStatus +
                ", treatment=" + treatment.toString() +
                ", appointmentIdCreatedBy=" + appointmentIdCreatedBy +
                '}';
    }
}
