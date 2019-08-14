package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.dto.EventDto;

import java.util.List;

public interface RegimenProcessorService {

    List<EventDto> parseRegimen(AppointmentDto appointmentDto, boolean generateEvents);
}
