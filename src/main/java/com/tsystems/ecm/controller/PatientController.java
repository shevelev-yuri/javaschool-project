package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.service.*;
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

import static com.tsystems.ecm.utils.StringConstants.*;

/**
 * MVC controller responsible for patients.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Controller
@RequestMapping("/patients")
public class PatientController {

    /**
     * Log4j logger
     */
    private static final Logger log = LogManager.getLogger(PatientController.class);

    /**
     * The PatientService reference.
     */
    private PatientService patientService;

    /**
     * The AppointmentService reference.
     */
    private AppointmentService appointmentService;

    /**
     * The UserService reference.
     */
    private UserService userService;

    /**
     * The EventService reference.
     */
    private EventService eventService;

    /**
     * The RegimenProcessorService reference.
     */
    private RegimenProcessorService regimenProcessorService;

    /**
     * All args constructor.
     *
     * @param patientService          the PatientService reference
     * @param appointmentService      the AppointmentService reference
     * @param regimenProcessorService the RegimenProcessorService reference
     * @param userService             the UserService reference
     * @param eventService            the EventService reference
     */
    @Autowired
    public PatientController(PatientService patientService,
                             AppointmentService appointmentService,
                             UserService userService,
                             EventService eventService,
                             RegimenProcessorService regimenProcessorService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.userService = userService;
        this.eventService = eventService;
        this.regimenProcessorService = regimenProcessorService;
    }

    /**
     * Shows all patients page.
     *
     * @return the all patients page
     */
    @GetMapping("")
    public ModelAndView getPatients() {
        ModelAndView mv = new ModelAndView(PATIENTS_PATIENTS);
        List<PatientDto> patients = patientService.getAll();
        mv.addObject("patients", patients);

        return mv;
    }

    /**
     * Shows the form for adding new patient.
     *
     * @param model the Model reference
     * @return the page with form for adding new patient
     */
    @GetMapping("/add")
    public ModelAndView addPatientForm(Model model) {
        List<UserDto> doctors = userService.getAllDoctors();
        model.addAttribute("doctors", doctors);

        return new ModelAndView(PATIENTS_ADD, PATIENT, new PatientDto());
    }

    /**
     * Handles addition of new patient and redirects to all patients page.
     *
     * @param patient            the patient DTO
     * @param result             the BindingResult reference
     * @param redirectAttributes the RedirectAttributes reference
     * @return the all patients page
     */
    @PostMapping("/add")
    public ModelAndView addPatientAndGoToAppointment(@Valid @ModelAttribute(PATIENT) PatientDto patient,
                                                     BindingResult result,
                                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<UserDto> doctors = userService.getAllDoctors();
            return new ModelAndView(PATIENTS_ADD, "doctors", doctors);
        }

        try {
            patient.setId(patientService.addPatient(patient));
        } catch (Exception e) {
            return new ModelAndView(REDIRECT_ERROR400, "err", "insuranceAlreadyExists");
        }

        redirectAttributes.addFlashAttribute(PATIENT, patient);

        if (log.isDebugEnabled()) log.debug("Created new patient. {}", patient.toString());

        return new ModelAndView(REDIRECT_APPOINTMENTS_ADD);
    }

    /**
     * Shows the patient discharge confirmation page.
     *
     * @param patientId the patient's id
     * @return the patient discharge confirmation page
     */
    @GetMapping("/discharge")
    public ModelAndView dischargeConfirmation(@RequestParam("patientId") String patientId) {
        ModelAndView mv = new ModelAndView(PATIENTS_DISCHARGE);
        PatientDto patient;

        long id;
        try {
            id = Long.parseLong(patientId);
            patient = patientService.get(id);
            if (patient == null) {
                //Patient with this ID not found, redirect to error page
                return new ModelAndView(REDIRECT_ERROR400);
            }
        } catch (NumberFormatException nfe) {
            //Invalid request parameters, redirect to error page
            return new ModelAndView(REDIRECT_ERROR400);
        }

        mv.addObject(PATIENT, patient);
        List<AppointmentDto> appointments = appointmentService.getAllByPatientId(id);
        appointments.forEach(appointment -> regimenProcessorService.parseRegimen(appointment, false));
        mv.addObject("appointments", appointments);

        return mv;
    }

    /**
     * Handles patient discharge and redirects to all patients page.
     *
     * @param patientId the patient's id
     * @return the all patients page
     */
    @PostMapping("/discharge")
    public ModelAndView dischargeConfirmed(@RequestParam("patientId") String patientId) {
        ModelAndView mv = new ModelAndView(REDIRECT_PATIENTS);

        long id = Long.parseLong(patientId);

        List<AppointmentDto> appointments = appointmentService.getAllByPatientId(id);
        appointments.forEach(appointment -> eventService.setCancelledByAppointmentId(appointment.getId()));
        appointments.forEach(appointment -> appointmentService.cancelAppointmentById(appointment.getId()));

        patientService.dischargePatient(id);

        return mv;
    }
}