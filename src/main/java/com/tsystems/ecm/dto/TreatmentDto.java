package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.enums.TreatmentType;
import lombok.*;

/**
 * The treatment DTO class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TreatmentDto {

    /**
     * The id.
     */
    private long id;

    /**
     * The treatment name.
     */
    private String treatmentName;

    /**
     * The treatment type.
     */
    private TreatmentType treatmentType;

}
