package com.tsystems.ecm.controller;


import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.service.PatientService;
import com.tsystems.ecm.service.TreatmentService;
import com.tsystems.ecm.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/patients")
@SessionAttributes("patient")
public class PatientController {

    private static final Logger log = LogManager.getLogger(PatientController.class);

    private PatientService patientService;

    private UserService userService;

    @Autowired
    public PatientController(PatientService patientService, UserService userService) {
        this.patientService = patientService;
        this.userService = userService;
    }

    @GetMapping("")
    public String getPatients(Model model) {
        List<PatientDto> patientsList = patientService.getAllPatients();
        model.addAttribute("patients", patientsList);

        return "patients/patients";
    }

    @GetMapping("/add")
    public ModelAndView addPatientForm(Model model) {
        List<UserDto> doctors = userService.getAllDoctors();
        model.addAttribute("doctors", doctors);

        return new ModelAndView("patients/add", "patient", new PatientDto());
    }

    @PostMapping("/add")
    public ModelAndView addPatientAndGoToAppointment(@Valid @ModelAttribute("patient") PatientDto patient, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return new ModelAndView("add");
        }

        patient.setId(patientService.addPatient(patient));
        log.debug("Created new patient. " + patient.toString());
        redirectAttributes.addFlashAttribute("patient", patient);

        return new ModelAndView("redirect:/appointments/add");
    }
}