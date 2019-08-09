package com.tsystems.ecm.controller;


import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.entity.enums.PatientStatus;
import com.tsystems.ecm.service.PatientService;
import com.tsystems.ecm.service.TreatmentService;
import com.tsystems.ecm.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private static final Logger log = LogManager.getLogger(PatientController.class);

    private PatientService patientService;
    private UserService userService;
    private TreatmentService treatmentService;

    @Autowired
    public PatientController(PatientService patientService,
                             UserService userService, TreatmentService treatmentService) {
        this.patientService = patientService;
        this.userService = userService;
        this.treatmentService = treatmentService;
    }

    @GetMapping("")
    public String getPatients(ModelMap model) {
        List<PatientDto> patientsList = patientService.getAllPatients();
        model.addAttribute("patients", patientsList);
        return "patients";
    }

    @GetMapping("/add")
    public String addPatientGet(ModelMap model) {
        List<UserDto> doctors = userService.getAllDoctors();
        model.addAttribute("doctors", doctors);

        return "patients/add";
    }

    @PostMapping("/add")
    public String addPatientAndProceedToAppointment(PatientDto patient, RedirectAttributes redirectAttributes) {
        patient.setPatientStatus(PatientStatus.ON_TREATMENT);
        patient.setPatientId(patientService.addPatientAndReturnId(patient));

        log.debug("Creating new patient with parameters (ID: {}, name: {}, diagnosis: {}, insurance № {}, treating doctor: {}, status: {}).",
                patient.getPatientId(), patient.getName(), patient.getDiagnosis(), patient.getInsuranceNumber(), patient.getDoctorName(), patient.getPatientStatus());

        redirectAttributes.addFlashAttribute("patient", patient);

        return "redirect:/patients/appointment";
    }

    @GetMapping("/appointment")
    public String appointmentFormGet(@ModelAttribute("name") String name, @ModelAttribute("patientId") String patientId, Model model) {
        List<TreatmentDto> treatments = treatmentService.getAll();
        model.addAttribute("treatments", treatments);

        PatientDto patientDto = (PatientDto) model.asMap().get("patient");
        if (patientDto != null) {
            model.addAttribute("patientId", patientDto.getPatientId());
            model.addAttribute("patientName", patientDto.getName());
        } else {
            model.addAttribute("patientId", patientId);
            model.addAttribute("name", name);
        }

        return "patients/appointment";
    }

    @PostMapping("/appointment")
    public String addAppointmentAndProceedToAppointments(PatientDto patient, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("patient", patient);

        String[] weekdays = request.getParameterValues("weekday");
        redirectAttributes.addFlashAttribute("weekdays", weekdays);


        return "redirect:/patients/appointments";
    }

    @GetMapping("/appointments")
    public String appointments(Model model) {
        PatientDto patientDto = (PatientDto) model.asMap().get("patient");
        model.addAttribute("patientId", patientDto.getPatientId());
        model.addAttribute("patientName", patientDto.getName());

        String[] weekdays = (String[]) model.asMap().get("weekdays");
        List<String> weekdayList = new ArrayList<>(Arrays.asList(weekdays));
        model.addAttribute("weekdayList", weekdayList);

        return "patients/appointments";
    }

}