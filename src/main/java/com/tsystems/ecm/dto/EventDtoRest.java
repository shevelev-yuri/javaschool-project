package com.tsystems.ecm.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EventDtoRest {

    private String patient;

    private String scheduledDatetime;

    private String eventStatus;

    private String treatment;

    private String cancelReason;

}
