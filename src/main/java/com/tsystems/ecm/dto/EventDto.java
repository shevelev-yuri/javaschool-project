package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.enums.EventStatus;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EventDto {

    private long id;

    private PatientDto patient;

    private LocalDateTime scheduledDatetime;

    private EventStatus eventStatus;

    private TreatmentDto treatment;

    private String cancelReason;

    private long appointmentIdCreatedBy;
}
