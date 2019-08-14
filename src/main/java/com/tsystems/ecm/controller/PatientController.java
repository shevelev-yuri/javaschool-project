package com.tsystems.ecm.controller;


import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.service.AppointmentService;
import com.tsystems.ecm.service.EventService;
import com.tsystems.ecm.service.PatientService;
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
import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private static final Logger log = LogManager.getLogger(PatientController.class);

    private PatientService patientService;

    private AppointmentService appointmentService;

    private UserService userService;

    private EventService eventService;

    @Autowired
    public PatientController(PatientService patientService,
                             AppointmentService appointmentService,
                             UserService userService,
                             EventService eventService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("")
    public ModelAndView getPatients() {
        ModelAndView mv = new ModelAndView("patients/patients");
        List<PatientDto> patients = patientService.getAll();
        mv.addObject("patients", patients);

        return mv;
    }

    @GetMapping("/add")
    public ModelAndView addPatientForm(Model model) {
        List<UserDto> doctors = userService.getAllDoctors();
        model.addAttribute("doctors", doctors);

        return new ModelAndView("patients/add", "patient", new PatientDto());
    }

    @PostMapping("/add")
    public ModelAndView addPatientAndGoToAppointment(@Valid @ModelAttribute("patient") PatientDto patient,
                                                     BindingResult result,
                                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return new ModelAndView("add");
        }

        patient.setId(patientService.addPatient(patient));
        redirectAttributes.addFlashAttribute("patient", patient);

        if (log.isDebugEnabled()) log.debug("Created new patient. {}", patient.toString());

        return new ModelAndView("redirect:/appointments/add");
    }

    @GetMapping("/discharge")
    public ModelAndView dischargeConfirmation(@RequestParam("patientId") String patientId) {
        ModelAndView mv = new ModelAndView("discharge");
        long id = Long.parseLong(patientId);
        PatientDto patient = patientService.get(id);
        mv.addObject("patient", patient);

        List<AppointmentDto> appointments = appointmentService.getAllByPatientId(id);
        mv.addObject("appointments", appointments);

        return mv;
    }

    @PostMapping("/discharge")
    public ModelAndView dischargeConfirmed(@RequestParam("patientId") String patientId) {
        ModelAndView mv = new ModelAndView("redirect:patients");

        long id = Long.parseLong(patientId);

        List<AppointmentDto> appointments = appointmentService.getAllByPatientId(id);
        appointments.forEach(appointment -> eventService.setCancelledDueToAppointmentCancelling(appointment.getId()));
        appointments.forEach(appointment -> appointmentService.cancelAppointmentById(appointment.getId()));

        patientService.dischargePatient(id);

        return mv;
    }

}