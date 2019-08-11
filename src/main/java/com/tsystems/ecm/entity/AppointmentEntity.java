package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.TreatmentType;

import javax.persistence.*;

@Entity
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @OneToOne
    @JoinColumn(name = "treatment_id")
    private TreatmentEntity treatment;

    @Enumerated(value = EnumType.STRING)
    private TreatmentType type;

    private String regimen;

    private int duration;

    private String dose;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public TreatmentEntity getTreatment() {
        return treatment;
    }

    public void setTreatment(TreatmentEntity treatment) {
        this.treatment = treatment;
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

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        return "AppointmentEntity{" +
                "id=" + id +
                ", patient=" + patient.toString() +
                ", treatment=" + treatment.toString() +
                ", type=" + type +
                ", regimen='" + regimen + '\'' +
                ", duration=" + duration +
                ", dose='" + dose + '\'' +
                '}';
    }
}
