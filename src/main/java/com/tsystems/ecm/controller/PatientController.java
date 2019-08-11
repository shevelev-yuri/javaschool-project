package com.tsystems.ecm.controller;


import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.service.PatientService;
import com.tsystems.ecm.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/patients")
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
        List<PatientDto> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);

        return "patients/patients";
    }

    @GetMapping("/add")
    public ModelAndView addPatientForm(Model model) {
        List<UserDto> doctors = userService.getAllDoctors();
        model.addAttribute("doctors", doctors);

        return new ModelAndView("patients/add", "patient", new PatientDto());
    }

    @PostMapping("/add")
    public ModelAndView addPatientAndGoToAppointment(@Valid @ModelAttribute("patient") PatientDto patient,
                                                     BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return new ModelAndView("add");
        }

        patient.setId(patientService.addPatient(patient));
        redirectAttributes.addFlashAttribute("patient", patient);

        if (log.isDebugEnabled()) log.debug("Created new patient. {}", patient.toString());

        return new ModelAndView("redirect:/appointments/add");
    }
}