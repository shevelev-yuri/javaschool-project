package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.TreatmentType;

import javax.persistence.*;

@Entity
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private long appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @OneToOne()
    @JoinColumn(name = "treatment_id")
    private TreatmentEntity treatment;

    @Enumerated(value = EnumType.STRING)
    private TreatmentType type;

    private String regimen;

    private int duration;

    private String dose;

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public TreatmentType getType() {
        return type;
    }

    public void setType(TreatmentType type) {
        this.type = type;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String appointmentRegimen) {
        this.regimen = appointmentRegimen;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int appointmentDuration) {
        this.duration = appointmentDuration;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
