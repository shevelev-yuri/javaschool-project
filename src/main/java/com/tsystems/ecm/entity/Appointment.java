package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.TreatmentType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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

}
