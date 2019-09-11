package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.TreatmentType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * The appointment entity class.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "appointments")
public class Appointment {

    /**
     * The id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private long id;

    /**
     * The patient this appointment belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    /**
     * The treatment of this appointment.
     */
    @OneToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    /**
     * The treatment type.
     */
    @Enumerated(value = EnumType.STRING)
    private TreatmentType type;

    /**
     * The regimen.
     */
    private String regimen;

    /**
     * The duration (in weeks).
     */
    private int duration;

    /**
     * The dose.
     */
    private String dose;

}
