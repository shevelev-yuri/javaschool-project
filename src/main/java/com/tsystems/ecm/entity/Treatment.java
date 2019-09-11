package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.TreatmentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * The treatment entity class.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "treatments")
public class Treatment {

    /**
     * The id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treatment_id")
    private long id;

    /**
     * The treatment name.
     */
    private String treatmentName;

    /**
     * The treatment type.
     */
    @Enumerated(value = EnumType.STRING)
    private TreatmentType treatmentType;

}

