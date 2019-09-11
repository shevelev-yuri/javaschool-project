package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.enums.TreatmentType;
import com.tsystems.ecm.messaging.JmsPublisher;
import com.tsystems.ecm.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

import static com.tsystems.ecm.utils.StringConstants.*;

/**
 * MVC controller responsible for appointments.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    /**
     * Log4j logger
     */
    private static final Logger log = LogManager.getLogger(AppointmentController.class);

    /**
     * The TreatmentService reference.
     */
    private TreatmentService treatmentService;

    /**
     * The PatientService reference.
     */
    private PatientService patientService;

    /**
     * The AppointmentService reference.
     */
    private AppointmentService appointmentService;

    /**
     * The RegimenProcessorService reference.
     */
    private RegimenProcessorService regimenProcessorService;

    /**
     * The EventService reference.
     */
    private EventService eventService;

    /**
     * The JmsPublisher reference.
     */
    private JmsPublisher publisher;

    /**
     * All args constructor.
     *
     * @param treatmentService        the TreatmentService reference
     * @param patientService          the PatientService reference
     * @param appointmentService      the AppointmentService reference
     * @param regimenProcessorService the RegimenProcessorService reference
     * @param eventService            the EventService reference
     * @param publisher               the JmsPublisher reference
     */
    @Autowired
    public AppointmentController(TreatmentService treatmentService,
                                 PatientService patientService,
                                 AppointmentService appointmentService,
                                 RegimenProcessorService regimenProcessorService,
                                 EventService eventService,
                                 JmsPublisher publisher) {
        this.treatmentService = treatmentService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.regimenProcessorService = regimenProcessorService;
        this.eventService = eventService;
        this.publisher = publisher;
    }

    /**
     * Shows the form for adding new appointment to the patient.
     *
     * @param patientPrev the patient DTO, not null if redirected from patient creation page
     * @param patientId   the patient's id
     * @return the page with form for adding new appointment to the patient
     */
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

    /**
     * Handles addition of new appointment and redirects to appointments page.
     *
     * @param treatmentId        the treatment's id
     * @param patientId          the patient's id
     * @param weekdays           the day of week array
     * @param times              the time points array
     * @param duration           the duration in weeks
     * @param dose               the dose
     * @param redirectAttributes the RedirectAttributes reference
     * @return the appointments page
     */
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

        String message = "Appointment [ID #" + appointment.getId() + "] has been created.";
        publisher.sendMessage(message);

        return new ModelAndView(REDIRECT_APPOINTMENTS);
    }

    /**
     * Shows the appointments for specified patient.
     *
     * @param patientPrev the patient DTO, not null if redirected from patient creation page
     * @param patientId   the patient's id
     * @param model       the Model reference
     * @return the appointments for specified patient
     */
    @GetMapping("/appointments")
    public String appointments(@ModelAttribute(PATIENT) PatientDto patientPrev,
                               @RequestParam(value = "patientId", required = false) String patientId,
                               Model model) {
        PatientDto patient;
        if (patientId != null && !patientId.isEmpty()) {
            patient = parsePatientId(patientId);
            if (patient == null) {
                return REDIRECT_APPOINTMENTS;
            }
        } else {
            if (patientPrev == null || patientPrev.getName() == null) return REDIRECT_APPOINTMENTS;
            patient = patientService.get(patientPrev.getId());
        }

        List<AppointmentDto> appointments = appointmentService.getAllByPatientId(patient.getId());
        appointments.forEach(appointment -> regimenProcessorService.parseRegimen(appointment, false));

        model.addAttribute(PATIENT, patient);
        model.addAttribute("appointments", appointments);

        return "appointments/appointments";
    }

    /**
     * Handles deletion of appointment and redirects to appointments page.
     *
     * @param appointmentId      the appointment's id
     * @param patientId          the patient's id
     * @param redirectAttributes the RedirectAttributes reference
     * @return the appointments page
     */
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

        String message = APPOINTMENT_ID_STRING + appointmentId + "] status has been changed to 'Cancelled'";
        publisher.sendMessage(message);

        return mv;
    }

    /**
     * Shows the form for editing the specified appointment of the patient.
     *
     * @param appointmentId the appointment's id
     * @param patientId     the patient's id
     * @return the page with form for editing specified appointment of the patient
     */
    @GetMapping("/edit")
    public ModelAndView editSelectedForm(@RequestParam("appointmentId") String appointmentId,
                                         @RequestParam("patientId") String patientId) {
        ModelAndView mv = new ModelAndView("appointments/edit");

        PatientDto patient = parsePatientId(patientId);
        if (patient == null) {
            return new ModelAndView(REDIRECT_ERROR400);
        }
        mv.addObject(PATIENT, patient);

        initAppointmentForm(mv);

        AppointmentDto oldAppointment = appointmentService.get(Long.parseLong(appointmentId));
        regimenProcessorService.parseRegimen(oldAppointment, false);
        mv.addObject("oldAppointment", oldAppointment);
        mv.addObject("newAppointment", new AppointmentDto());

        return mv;
    }

    /**
     * Handles edition of appointment and redirects to appointments page.
     *
     * @param patientId          the patient's id
     * @param treatmentId        the treatment's id
     * @param appointmentId      the appointment's id
     * @param weekdays           the day of week array
     * @param times              the time points array
     * @param duration           the duration in weeks
     * @param dose               the dose
     * @param redirectAttributes the RedirectAttributes reference
     * @return the appointments page
     */
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
            return new ModelAndView(REDIRECT_ERROR400);
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

        String message = APPOINTMENT_ID_STRING + appointmentId + "] has been edited, status has been changed to 'Cancelled' and created new appointment.";
        publisher.sendMessage(message);

        return new ModelAndView(REDIRECT_APPOINTMENTS);
    }


    //---------------------------------------------------------------------------------------------------------------
    //Helper methods

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
        return treatmentType != TreatmentType.MEDICATION || (dose != null && !dose.isEmpty());
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
    }
}