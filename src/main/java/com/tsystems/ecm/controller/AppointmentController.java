package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.service.AppointmentService;
import com.tsystems.ecm.service.TreatmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/appointments")
@SessionAttributes(names = {"patient", "appointment"})
public class AppointmentController {

    private static final Logger log = LogManager.getLogger(AppointmentController.class);

    private TreatmentService treatmentService;

    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(TreatmentService treatmentService, AppointmentService appointmentService) {
        this.treatmentService = treatmentService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/add")
    public ModelAndView appointmentForm(@ModelAttribute("patient") PatientDto patient, Model model) {
        List<TreatmentDto> treatments = treatmentService.getAll();
        model.addAttribute("treatments", treatments);
        model.addAttribute("patient", patient);

        AppointmentDto appointment = new AppointmentDto();
        appointment.setPatient(patient);
        model.addAttribute("appointment", appointment);

        List<String> daysOfWeek = new ArrayList<>();
        for(DayOfWeek day : DayOfWeek.values()) {
            daysOfWeek.add(day.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        }
        model.addAttribute("weekdays", daysOfWeek);

        return new ModelAndView("appointments/add");
    }

    @PostMapping("/add")
    public ModelAndView addAppointment(@ModelAttribute("appointment") AppointmentDto appointment,
                                       @ModelAttribute("treatment") TreatmentDto treatment,
                                       RedirectAttributes redirectAttributes) {
        appointment.setTreatment(treatment);
        appointmentService.addAppointment(appointment);

        log.debug("Created new appointment. {}", appointment.toString());

        redirectAttributes.addFlashAttribute("appointment", appointment);

        return new ModelAndView("redirect:/appointments/appointments");
    }

    @GetMapping("/appointments")
    public String appointments(@ModelAttribute("patient") PatientDto patient, Model model) {
        List<AppointmentDto> appointments = appointmentService.getAllByPatientId(patient.getId());

        model.addAttribute("appointments", appointments);

        return "appointments/appointments";
    }
}
