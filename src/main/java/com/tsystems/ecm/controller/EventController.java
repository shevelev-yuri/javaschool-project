package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.service.EventService;
import com.tsystems.ecm.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private static final Logger log = LogManager.getLogger(EventController.class);

    private EventService eventService;

    private PatientService patientService;

    @Autowired
    public EventController(EventService eventService,
                           PatientService patientService) {
        this.eventService = eventService;
        this.patientService = patientService;
    }

    @GetMapping("/events")
    public ModelAndView events(@RequestParam(value = "patientId", required = false) String patientId,
                               @ModelAttribute("patient") PatientDto patient) {
        ModelAndView mv = new ModelAndView("events/events");

        List<EventDto> events;
        if (patientId != null && !patientId.isEmpty()) {
            patient = patientService.get(Long.parseLong(patientId));
            events = eventService.getAllByPatientId(Long.parseLong(patientId));
        } else {
            events = eventService.getAllByPatientId(patient.getId());
        }
        events.sort(Comparator.comparing(EventDto::getScheduledDatetime));
        mv.addObject("patient", patient);
        mv.addObject("events", events);

        return mv;
    }

    @GetMapping("/eventsall")
    public ModelAndView eventsAll() {
        ModelAndView mv = new ModelAndView("events/eventsall");
        List<EventDto> events;
        events = eventService.getAll();
        events.sort(Comparator.comparing(EventDto::getScheduledDatetime));
        mv.addObject("events", events);

        return mv;
    }

    @PostMapping("/accomplished")
    public ModelAndView eventAccomplished(@RequestParam(value = "patientId", required = false) String patientId,
                                          @RequestParam("eventId") String eventId,
                                          RedirectAttributes redirectAttributes) {
        ModelAndView mv = getMV(patientId, redirectAttributes);

        eventService.setAccomplishedById(Long.parseLong(eventId));

        return mv;
    }

    @PostMapping("/cancelled")
    public ModelAndView eventCancelled(@RequestParam(value = "patientId", required = false) String patientId,
                                       @RequestParam("eventId") String eventId,
                                       RedirectAttributes redirectAttributes) {
        ModelAndView mv = getMV(patientId, redirectAttributes);

        eventService.setCancelledById(Long.parseLong(eventId));

        return mv;
    }

    private ModelAndView getMV(String patientId, RedirectAttributes redirectAttributes) {
        ModelAndView mv;

        if (patientId != null && !patientId.isEmpty()) {
            PatientDto patient = patientService.get(Long.parseLong(patientId));
            redirectAttributes.addFlashAttribute("patient", patient);
            mv = new ModelAndView("redirect:events");
        } else {
            mv = new ModelAndView("redirect:eventsall");
        }
        return mv;
    }
}
