package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.TreatmentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "treatments")
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treatment_id")
    private long id;

    private String treatmentName;

    @Enumerated(value = EnumType.STRING)
    private TreatmentType treatmentType;

}

