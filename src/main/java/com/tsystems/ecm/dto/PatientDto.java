package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.enums.PatientStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PatientDto {

    private static final String MUST_BE_SPECIFIED = "must be specified!";
    private static final String NAME_SIZE = "must be at least 1 and no more than 50 characters!";
    private static final String DIAGNOSIS_SIZE = "must be no longer than 100 characters!";
    private static final String INSURANCE_NUMBER_FORMAT = "format is \"###-####-####\" # - [0...9]!";
    private static final String TREATING_DOCTOR_NOT_SELECTED = "Treating doctor not selected!";

    private long id;

    @NotBlank(message = MUST_BE_SPECIFIED)
    @Size(max = 50, message = NAME_SIZE)
    private String name;

    @NotBlank(message = MUST_BE_SPECIFIED)
    @Size(max = 100, message = DIAGNOSIS_SIZE)
    private String diagnosis;

    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{3}$", message = MUST_BE_SPECIFIED + ", " + INSURANCE_NUMBER_FORMAT)
    private String insuranceNumber;

    @NotBlank(message = TREATING_DOCTOR_NOT_SELECTED)
    private String doctorName;

    private PatientStatus patientStatus;
}
