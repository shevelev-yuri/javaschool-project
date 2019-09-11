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

    private long id;

    @NotBlank(message = "{message.mustBeSpecified}")
    @Size(max = 50, message = "{message.nameSize}")
    private String name;

    @NotBlank(message = "{message.mustBeSpecified}")
    @Size(max = 100, message = "{message.diagnosisSize}")
    private String diagnosis;

    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{3}$", message = "{message.mustBeSpecified}" + " " + "{message.insuranceNumberFormat}")
    private String insuranceNumber;

    @NotBlank(message = "{message.doctorNotSelected}")
    private String doctorName;

    private PatientStatus patientStatus;
}
