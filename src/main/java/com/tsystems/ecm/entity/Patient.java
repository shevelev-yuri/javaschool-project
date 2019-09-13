package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.PatientStatus;
import lombok.*;

import javax.persistence.*;

/**
 * The patient entity class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "patients")
public class Patient {

    /**
     * The id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private long id;

    /**
     * The patient's name.
     */
    private String name;

    /**
     * The patient's diagnosis.
     */
    private String diagnosis;

    /**
     * The patient's insurance number.
     */
    private String insuranceNumber;

    /**
     * The patient's treating doctor.
     */
    private String doctorName;

    /**
     * The patient's status.
     */
    @Enumerated(value = EnumType.STRING)
    private PatientStatus patientStatus;

}