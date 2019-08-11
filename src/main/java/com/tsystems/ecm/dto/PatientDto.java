package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.PatientEntity;
import com.tsystems.ecm.entity.enums.PatientStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class PatientDto {

    private long id;

    @NotBlank(message = "Patient\'s name must be specified!")
    private String name;

    @NotBlank(message = "Diagnosis must be specified!")
    private String diagnosis;

    @NotBlank(message = "Insurance number must be specified!")
    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{3}$", message = "Insurance number format is ###-####-#### where # is any digit (0-9).")
    private String insuranceNumber;

    @NotBlank(message = "Treating doctor not selected!")
    private String doctorName;

    private PatientStatus patientStatus;

    public PatientDto() {
    }

    public PatientDto(PatientEntity patientEntity) {
        this.id = patientEntity.getId();
        this.name = patientEntity.getName();
        this.diagnosis = patientEntity.getDiagnosis();
        this.insuranceNumber = patientEntity.getInsuranceNumber();
        this.doctorName = patientEntity.getDoctorName();
        this.patientStatus = patientEntity.getPatientStatus();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "PatientDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", insuranceNumber='" + insuranceNumber + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", patientStatus=" + patientStatus +
                '}';
    }
}
