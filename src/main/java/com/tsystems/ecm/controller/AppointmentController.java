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

    private static final Locale LOCALE = Locale.ENGLISH;
    private static final String PATIENT = "patient";
    private static final String MORNING = "Morning";
    private static final String AFTERNOON = "Afternoon";
    private static final String EVENING = "Evening";

    private static final String REDIRECT_APPOINTMENTS = "redirect:/appointments/appointments";
    public static final String REDIRECT_PATIENTS = "redirect:/patients";

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
    public ModelAndView appointmentForm(@ModelAttribute(PATIENT) PatientDto patientPrev,
                                        @RequestParam(value = "patientId", required = false) String patientId) {
        ModelAndView mv = new ModelAndView("appointments/add");
        PatientDto patient;
        if (patientId != null && !patientId.isEmpty()) {
            patient = parsePatientId(patientId);
            if (patient == null) {
                return new ModelAndView(REDIRECT_PATIENTS);
            }
        } else {
            if (patientPrev == null || patientPrev.getName() == null) return new ModelAndView(REDIRECT_PATIENTS);
            patient = patientService.get(patientPrev.getId());
        }
        mv.addObject(PATIENT, patient);

        initAppointmentForm(mv);

        mv.addObject("appointment", new AppointmentDto());

        return mv;
    }

    @PostMapping("/add")
    public ModelAndView addAppointment(@RequestParam("treatmentId") String treatmentId,
                                       @RequestParam("patientId") String patientId,
                                       @RequestParam(value = "days[]", required = false) String[] weekdays,
                                       @RequestParam(value = "times[]", required = false) String[] times,
                                       @RequestParam(value = "duration", required = false) String duration,
                                       @RequestParam(value = "dose", required = false) String dose,
                                       RedirectAttributes redirectAttributes) {
        AppointmentDto appointment;
        appointment = initAppointmentDto(treatmentId, patientId, weekdays, times, duration, dose, redirectAttributes);
        if (appointment == null) {
            return new ModelAndView(REDIRECT_PATIENTS);
        }
        appointment.setId(appointmentService.addOrUpdateAppointment(appointment));

        List<EventDto> events = regimenProcessorService.parseRegimen(appointment, true);
        events.forEach(event -> event.setAppointmentIdCreatedBy(appointment.getId()));
        eventService.addEvents(events);

        if (log.isDebugEnabled()) log.debug("Created new appointment. {}", appointment.toString());

        return new ModelAndView(REDIRECT_APPOINTMENTS);
    }

    @GetMapping("/appointments")
    public String appointments(@ModelAttribute(PATIENT) PatientDto patientPrev,
                               @RequestParam(value = "patientId", required = false) String patientId,
                               Model model) {
        PatientDto patient;
        if (patientId != null && !patientId.isEmpty()) {
            patient = parsePatientId(patientId);
            if (patient == null) {
                return REDIRECT_PATIENTS;
            }
        } else {
            if (patientPrev == null || patientPrev.getName() == null) return REDIRECT_PATIENTS;
            patient = patientService.get(patientPrev.getId());
        }

        List<AppointmentDto> appointments = appointmentService.getAllByPatientId(patient.getId());
        appointments.forEach(appointment -> regimenProcessorService.parseRegimen(appointment, false));

        model.addAttribute(PATIENT, patient);
        model.addAttribute("appointments", appointments);

        return "appointments/appointments";
    }

    @PostMapping("/delete")
    public ModelAndView deleteSelected(@RequestParam("appointmentId") String appointmentId,
                                       @RequestParam("patientId") String patientId,
                                       RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView(REDIRECT_APPOINTMENTS);
        long id = Long.parseLong(appointmentId);

        eventService.setCancelledByAppointmentId(id);
        appointmentService.cancelAppointmentById(id);
        PatientDto patient = patientService.get(Long.parseLong(patientId));

        redirectAttributes.addFlashAttribute(PATIENT, patient);

        return mv;
    }

    @GetMapping("/edit")
    public ModelAndView editSelectedForm(@RequestParam("appointmentId") String appointmentId,
                                         @RequestParam("patientId") String patientId) {
        ModelAndView mv = new ModelAndView("appointments/edit");

        PatientDto patient = parsePatientId(patientId);
        if (patient == null) {
            return new ModelAndView(REDIRECT_PATIENTS);
        }
        mv.addObject(PATIENT, patient);

        initAppointmentForm(mv);

        AppointmentDto oldAppointment = appointmentService.get(Long.parseLong(appointmentId));
        regimenProcessorService.parseRegimen(oldAppointment, false);
        mv.addObject("oldAppointment", oldAppointment);
        mv.addObject("newAppointment", new AppointmentDto());

        return mv;
    }

    @PostMapping("/edit")
    public ModelAndView editAppointment(@RequestParam(value = "patientId", required = false) String patientId,
                                        @RequestParam("treatmentId") String treatmentId,
                                        @RequestParam("oldAppointmentId") String appointmentId,
                                        @RequestParam(value = "days[]", required = false) String[] weekdays,
                                        @RequestParam(value = "times[]", required = false) String[] times,
                                        @RequestParam(value = "duration", required = false) String duration,
                                        @RequestParam(value = "dose", required = false) String dose,
                                        RedirectAttributes redirectAttributes) {
        AppointmentDto newAppointment;
        newAppointment = initAppointmentDto(treatmentId, patientId, weekdays, times, duration, dose, redirectAttributes);
        if (newAppointment == null) {
            return new ModelAndView(REDIRECT_APPOINTMENTS);
        }

        long id = Long.parseLong(appointmentId);
        newAppointment.setId(id);
        eventService.setCancelledByAppointmentId(id);

        log.debug("Old appointment ID: {}, newAppointment.setID()={}", appointmentId, newAppointment.getId());
        long newId = appointmentService.addOrUpdateAppointment(newAppointment);
        log.debug("New ID: {}", newId);

        List<EventDto> events = regimenProcessorService.parseRegimen(newAppointment, true);
        events.forEach(event -> event.setAppointmentIdCreatedBy(newAppointment.getId()));
        eventService.addEvents(events);

        if (log.isDebugEnabled())
            log.debug("Edited appointment with id {}. New appointment: {}", id, newAppointment.toString());

        return new ModelAndView(REDIRECT_APPOINTMENTS);
    }

    //---------------------------------------------------------------------------------------------------------------

    private AppointmentDto initAppointmentDto(String treatmentId, String patientId,
                                              String[] weekdays, String[] times, String duration, String dose,
                                              RedirectAttributes redirectAttributes) {
        AppointmentDto appointmentDto = new AppointmentDto();

        TreatmentDto treatment = treatmentService.get(Long.parseLong(treatmentId));
        if (!validateForm(treatment.getTreatmentType(), weekdays, duration, dose)) {
            return null;
        }

        appointmentDto.setTreatment(treatment);
        appointmentDto.setType(treatment.getTreatmentType());

        PatientDto patient = patientService.get(Long.parseLong(patientId));
        appointmentDto.setPatient(patient);
        redirectAttributes.addFlashAttribute(PATIENT, patient);

        StringBuilder regimenStringBuilder = new StringBuilder();
        regimenStringBuilder.append(String.join(" ", weekdays));

        if (times != null && times.length > 0) {
            regimenStringBuilder.append("_").append(String.join(" ", times));
        }
        appointmentDto.setRegimen(regimenStringBuilder.toString());

        appointmentDto.setDuration(Integer.parseInt(duration));

        if (appointmentDto.getType() == TreatmentType.MEDICATION) {
            appointmentDto.setDose(dose);
        }

        return appointmentDto;
    }
    private boolean validateForm(TreatmentType treatmentType, String[] weekdays, String duration, String dose) {
        if (weekdays == null) return false;

        try {
            int dur = Integer.parseInt(duration);
            if (dur > 10 || dur < 1) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        if (treatmentType == TreatmentType.MEDICATION && (dose == null || dose.isEmpty())) return false;

        return true;
    }

    private PatientDto parsePatientId(String patientId) {
        PatientDto patient;
        long id;
        try {
            id = Long.parseLong(patientId);
            patient = patientService.get(id);
            if (patient == null) {
                return null;
            }
        } catch (NumberFormatException nfe) {
            return null;
        }
        return patient;
    }

    private void initAppointmentForm(ModelAndView mv) {
        List<TreatmentDto> treatments = treatmentService.getAll();
        mv.addObject("procedures", treatments
                .stream()
                .filter(treatment -> treatment.getTreatmentType() == TreatmentType.PROCEDURE)
                .collect(Collectors.toList()));

        mv.addObject("medications", treatments
                .stream()
                .filter(treatment -> treatment.getTreatmentType() == TreatmentType.MEDICATION)
                .collect(Collectors.toList()));

        List<String> daysOfWeek = new ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            daysOfWeek.add(day.getDisplayName(TextStyle.FULL, LOCALE));
        }
        mv.addObject("weekdays", daysOfWeek);
        mv.addObject("timesOfDay", new String[]{MORNING, AFTERNOON, EVENING});
    }
}