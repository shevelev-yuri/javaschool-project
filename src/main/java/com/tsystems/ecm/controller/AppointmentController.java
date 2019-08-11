package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.enums.TreatmentType;
import com.tsystems.ecm.service.AppointmentService;
import com.tsystems.ecm.service.PatientService;
import com.tsystems.ecm.service.TreatmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private static final Logger log = LogManager.getLogger(AppointmentController.class);

    private TreatmentService treatmentService;

    private PatientService patientService;

    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(TreatmentService treatmentService,
                                 PatientService patientService,
                                 AppointmentService appointmentService) {
        this.treatmentService = treatmentService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

/*    @GetMapping("/add")
    public ModelAndView appointmentForm(Model model) {
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
    }*/

    @GetMapping("/add{patientId}")
    public ModelAndView appointmentFormFromButton(@PathVariable(value = "patientId", required = false) String patientId,
                                                  Model model) {
        if (patientId != null && !patientId.isEmpty()) {
            PatientDto patient = patientService.get(Long.parseLong(patientId));
            model.addAttribute("patient", patient);
        }

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
                                       RedirectAttributes redirectAttributes, HttpServletRequest request) {

        TreatmentDto treatment = treatmentService.get(Long.parseLong(request.getParameter("treatmentId")));
        appointment.setTreatment(treatment);
        appointment.setType(treatment.getTreatmentType());

        PatientDto patient = patientService.get(Long.parseLong(request.getParameter("patientId")));
        appointment.setPatient(patient);

        redirectAttributes.addFlashAttribute("patient", patient);

        StringBuilder regimenStringBuilder = new StringBuilder();

        String[] weekdays = request.getParameterValues("days[]");
        if (weekdays != null && weekdays.length > 0) {
            Arrays.stream(weekdays).forEach(day -> regimenStringBuilder.append(day).append(" "));
        }
        String[] times = request.getParameterValues("times[]");
        if (times != null && times.length > 0) {
            Arrays.stream(times).forEach(time -> regimenStringBuilder.append(time).append(" "));
        }
        appointment.setRegimen(regimenStringBuilder.toString().trim());

        appointment.setId(appointmentService.addAppointment(appointment));
        if (log.isDebugEnabled()) log.debug("Created new appointment. {}", appointment.toString());

        return new ModelAndView("redirect:/appointments/appointments");
    }

    @GetMapping("/appointments{patientId}")
    public String appointments(@ModelAttribute("patient") PatientDto patient,
                               @PathVariable(value = "patientId", required = false) String patientId,
                               Model model) {
        if (patientId != null && !patientId.isEmpty()) {
            PatientDto patient = patientService.get(Long.parseLong(patientId));
        } else {
            PatientDto patient = //TODO
        }

        List<AppointmentDto> appointments = appointmentService.getAllByPatientId(patient.getId());
        model.addAttribute("appointments", appointments);

        return "appointments/appointments";
    }
}
