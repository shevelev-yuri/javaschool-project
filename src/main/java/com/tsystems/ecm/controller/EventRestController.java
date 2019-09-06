package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.EventDtoRest;
import com.tsystems.ecm.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventRestController {

    private static final Logger log = LogManager.getLogger(EventRestController.class);

    private EventService eventService;

    @Autowired
    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public List<EventDtoRest> getRestEvents() {
        log.debug("EventRestController called");

        return eventService.getAllToday();
    }
}