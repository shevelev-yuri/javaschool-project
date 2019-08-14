package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.enums.TreatmentType;
import com.tsystems.ecm.service.*;
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
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private static final Logger log = LogManager.getLogger(AppointmentController.class);

    private static final String PATIENT = "patient";

    private TreatmentService treatmentService;

    private PatientService patientService;

    private AppointmentService appointmentService;

    private RegimenProcessorService regimenProcessorService;

    private EventService eventService;

    @Autowired
    public AppointmentController(TreatmentService treatmentService,
                                 PatientService patientService,
                                 AppointmentService appointmentService,
                                 RegimenProcessorService regimenProcessorService,
                                 EventService eventService) {
        this.treatmentService = treatmentService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.regimenProcessorService = regimenProcessorService;
        this.eventService = eventService;
    }

    @GetMapping("/add")
    public ModelAndView appointmentForm(@ModelAttribute("patient") PatientDto patientPrev,
                                        @RequestParam(value = "patientId", required = false) String patientId,
                                        Model model) {
        PatientDto patient;
        if (patientId != null && !patientId.isEmpty()) {
            patient = patientService.get(Long.parseLong(patientId));
        } else {
            patient = patientService.get(patientPrev.getId());
        }

        model.addAttribute(PATIENT, patient);
        ModelAndView modelAndView = new ModelAndView("appointments/add");

        List<TreatmentDto> treatments = treatmentService.getAll();
        model.addAttribute("procedures", treatments
                .stream()
                .filter(treatment -> treatment.getTreatmentType() == TreatmentType.PROCEDURE)
                .collect(Collectors.toList()));

        model.addAttribute("medications", treatments
                .stream()
                .filter(treatment -> treatment.getTreatmentType() == TreatmentType.MEDICATION)
                .collect(Collectors.toList()));

        modelAndView.addObject("appointment", new AppointmentDto());

        List<String> daysOfWeek = new ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            daysOfWeek.add(day.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        }
        modelAndView.addObject("weekdays", daysOfWeek);
        modelAndView.addObject("timesOfDay", new String[]{"Morning", "Afternoon", "Evening"});

        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addAppointment(@ModelAttribute("appointment") AppointmentDto appointment,
                                       @RequestParam(value = "patientId", required = false) String patientId,
                                       @RequestParam(value = "treatmentId", required = false) String treatmentId,
                                       @RequestParam("days[]") String[] weekdays,
                                       @RequestParam(value = "times[]", required = false) String[] times,
                                       RedirectAttributes redirectAttributes) {

        TreatmentDto treatment = treatmentService.get(Long.parseLong(treatmentId));
        appointment.setTreatment(treatment);
        appointment.setType(treatment.getTreatmentType());

        PatientDto patient = patientService.get(Long.parseLong(patientId));
        appointment.setPatient(patient);
        redirectAttributes.addFlashAttribute(PATIENT, patient);

        StringBuilder regimenStringBuilder = new StringBuilder();
        if (weekdays.length > 0 && weekdays.length < 8) {
            regimenStringBuilder.append(String.join(" ", weekdays));
        } else {
            //TODO handle when none of the weekday checkboxes is selected
            log.warn("Something strange happened!");
        }

        if (times != null && times.length > 0) {
            regimenStringBuilder.append("_").append(String.join(" ", times));
        }

        appointment.setRegimen(regimenStringBuilder.toString());
        List<EventDto> events = regimenProcessorService.parseRegimen(appointment, true);
        eventService.addEvents(events);

        appointment.setId(appointmentService.addAppointment(appointment));
        if (log.isDebugEnabled()) log.debug("Created new appointment. {}", appointment.toString());

        return new ModelAndView("redirect:/appointments/appointments");
    }

    @GetMapping("/appointments")
    public String appointments(@ModelAttribute("patient") PatientDto patientPrev,
                               @RequestParam(value = "patientId", required = false) String patientId,
                               Model model) {
        PatientDto patient;
        if (patientId != null && !patientId.isEmpty()) {
            patient = patientService.get(Long.parseLong(patientId));
            model.addAttribute(PATIENT, patient);
        } else {
            patient = patientService.get(patientPrev.getId());
        }

        List<AppointmentDto> appointments = appointmentService.getAllByPatientId(patient.getId());
        appointments.forEach(appointment -> regimenProcessorService.parseRegimen(appointment, false));

        model.addAttribute("appointments", appointments);

        return "appointments/appointments";
    }
}
