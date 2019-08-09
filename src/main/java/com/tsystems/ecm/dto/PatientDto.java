package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.PatientEntity;
import com.tsystems.ecm.entity.enums.PatientStatus;

public class PatientDto {

    private long patientId;
    private String name;
    private String diagnosis;
    private String insuranceNumber;
    private String doctorName;
    private PatientStatus patientStatus;

    public PatientDto() {
    }

    public PatientDto(PatientEntity patientEntity) {
        this.patientId = patientEntity.getPatientId();
        this.name = patientEntity.getName();
        this.diagnosis = patientEntity.getDiagnosis();
        this.insuranceNumber = patientEntity.getInsuranceNumber();
        this.doctorName = patientEntity.getDoctorName();
        this.patientStatus = patientEntity.getPatientStatus();
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
