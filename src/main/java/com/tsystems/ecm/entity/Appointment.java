package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.TreatmentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @Enumerated(value = EnumType.STRING)
    private TreatmentType type;

    private String regimen;

    private int duration;

    private String dose;

    @Override
    public String toString() {
        return "Appointment{" +
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
