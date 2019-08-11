package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.enums.TreatmentType;

public class TreatmentDto {

    private long id;

    private String treatmentName;

    private TreatmentType treatmentType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public TreatmentType getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(TreatmentType treatmentType) {
        this.treatmentType = treatmentType;
    }

    @Override
    public String toString() {
        return "TreatmentDto{" +
                "id=" + id +
                ", treatmentName='" + treatmentName + '\'' +
                ", treatmentType=" + treatmentType +
                '}';
    }
}
