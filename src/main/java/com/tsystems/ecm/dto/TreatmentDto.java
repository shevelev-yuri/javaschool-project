package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.enums.TreatmentType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TreatmentDto {

    private long id;

    private String treatmentName;

    private TreatmentType treatmentType;

}
