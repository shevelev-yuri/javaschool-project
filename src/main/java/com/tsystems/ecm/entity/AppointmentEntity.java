package com.tsystems.ecm.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "appointment_id")
    private int appointmentId;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
    private String type;
    @Column(name = "appointment_regimen")
    private String appointmentRegimen;
    @Column(name = "appointment_duration")
    private LocalDate appointmentDuration;
    private String dose;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppointmentRegimen() {
        return appointmentRegimen;
    }

    public void setAppointmentRegimen(String appointmentRegimen) {
        this.appointmentRegimen = appointmentRegimen;
    }

    public LocalDate getAppointmentDuration() {
        return appointmentDuration;
    }

    public void setAppointmentDuration(LocalDate appointmentDuration) {
        this.appointmentDuration = appointmentDuration;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
