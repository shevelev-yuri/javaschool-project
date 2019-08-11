package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.TreatmentDto;

import java.util.List;

public interface TreatmentService {

    List<TreatmentDto> getAll();

    TreatmentDto get(long treatmentId);
}
