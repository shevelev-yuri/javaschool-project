package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.enums.TreatmentType;

public class AppointmentDto {

    private long id;

    private PatientDto patient;

    private TreatmentDto treatment;

    private TreatmentType type;

    private String regimen;

    private String regimenString;

    private int duration;

    private String dose;

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

    public TreatmentDto getTreatment() {
        return treatment;
    }

    public void setTreatment(TreatmentDto treatment) {
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

    public String getRegimenString() {
        return regimenString;
    }

    public void setRegimenString(String regimenString) {
        this.regimenString = regimenString;
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
        return "AppointmentDto{" +
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
