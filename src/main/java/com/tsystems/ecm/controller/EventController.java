package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.service.EventService;
import com.tsystems.ecm.service.PatientService;
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

    private EventService eventService;

    private PatientService patientService;

    @Autowired
    public EventController(EventService eventService,
                           PatientService patientService) {
        this.eventService = eventService;
        this.patientService = patientService;
    }

    @GetMapping("/events")
    public ModelAndView events(@RequestParam(value = "patientId", required = false) String patientId) {
        ModelAndView mv = new ModelAndView("events/events");
        List<EventDto> events;
        if (patientId != null && !patientId.isEmpty()) {
            events = eventService.getAllByPatientId(Long.parseLong(patientId));
        } else {
            events = eventService.getAll();
        }
        events.sort(Comparator.comparing(EventDto::getScheduledDatetime));
        mv.addObject("events", events);

        return mv;
    }

    @PostMapping("/accomplished")
    public ModelAndView eventAccomplished(@RequestParam("patientId") String patientId,
                                          @RequestParam("eventId") String eventId,
                                          RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:events");

        PatientDto patient = patientService.get(Long.parseLong(patientId));

        redirectAttributes.addFlashAttribute("patient", patient);

        eventService.setAccomplishedById(Long.parseLong(eventId));

        return mv;
    }

    @PostMapping("/cancelled")
    public ModelAndView eventCancelled(@RequestParam("patientId") String patientId,
                                       @RequestParam("eventId") String eventId) {
        ModelAndView mv = new ModelAndView("redirect:events");

        eventService.setCancelledById(Long.parseLong(eventId));

        return mv;
    }


}
