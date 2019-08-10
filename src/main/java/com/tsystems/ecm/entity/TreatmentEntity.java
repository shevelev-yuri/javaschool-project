package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.TreatmentType;

import javax.persistence.*;

@Entity
@Table(name = "treatments")
public class TreatmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treatment_id")
    private long id;

    private String treatmentName;

    @Enumerated(value = EnumType.STRING)
    private TreatmentType treatmentType;

    public long getId() {
        return id;
    }

    public void setId(long treatmentId) {
        this.id = treatmentId;
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
}
