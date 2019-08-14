package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private static final Logger log = LogManager.getLogger(EventController.class);

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public ModelAndView events(@RequestParam(value = "patientId", required = false) String patientId ) {
        ModelAndView mv = new ModelAndView("events/events");
        List<EventDto> events;
        if (patientId != null && !patientId.isEmpty()) {
            events = eventService.getAllByPatientId(Long.parseLong(patientId));
        } else {
            events = eventService.getAll();
        }
        mv.addObject("events", events);

        return mv;
    }

}
