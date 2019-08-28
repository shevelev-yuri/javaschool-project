package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.enums.TreatmentType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AppointmentDto {

    private long id;

    private PatientDto patient;

    private TreatmentDto treatment;

    private TreatmentType type;

    private String regimen;

    private String regimenString;

    private int duration;

    private String dose;

}
