package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.service.AppointmentService;
import com.tsystems.ecm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;

public class AppointmentServiceImpl implements AppointmentService {

    private PatientService patientService;

    @Autowired
    public AppointmentServiceImpl(PatientService patientService) {
        this.patientService = patientService;
    }


    @Override
    public void addAppointment(PatientDto patientDto) {

    }
}
