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

/**
 * REST controller responsible for events.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api")
public class EventRestController {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(EventRestController.class);

    /**
     * The EventService reference.
     */
    private EventService eventService;

    /**
     * All args constructor.
     *
     * @param eventService the EventService reference
     */
    @Autowired
    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Handles REST requests, gets today events list
     *
     * @return the {@code List} containing all events, scheduled for today
     */
    @GetMapping("/events")
    public List<EventDtoRest> getRestEvents() {
        log.debug("EventRestController called");

        return eventService.getAllToday();
    }
}