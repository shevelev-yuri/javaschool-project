package com.tsystems.ecm.controller;


import com.tsystems.ecm.entity.PatientEntity;
import com.tsystems.ecm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PatientsController {

    private final PatientService patientService;

    @Autowired
    public PatientsController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public String getPatients(ModelMap model) {
        List<PatientEntity> patientsList = patientService.getPatients();
        model.addAttribute("patients", patientsList);
        return "patients";
    }
}
