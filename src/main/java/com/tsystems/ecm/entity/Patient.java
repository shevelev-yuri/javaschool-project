package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.PatientStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private long id;

    private String name;

    private String diagnosis;

    private String insuranceNumber;

    private String doctorName;

    @Enumerated(value = EnumType.STRING)
    private PatientStatus patientStatus;

}