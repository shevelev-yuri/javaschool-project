package com.tsystems.ecm.entity;

import javax.persistence.*;

@Entity
@Table(name = "treatments")
public class TreatmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "treatment_id")
    private String treatmentId;
    @Column(name = "treatment_name")
    private String treatmentName;
    @Column(name = "treatment_type")
    private String treatmentType;

    public String getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(String treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }
}
