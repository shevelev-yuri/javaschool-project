package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.messaging.JmsPublisher;
import com.tsystems.ecm.service.EventService;
import com.tsystems.ecm.service.PatientService;
import com.tsystems.ecm.utils.FilterParams;
import com.tsystems.ecm.utils.Pagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static com.tsystems.ecm.utils.StringConstants.*;

/**
 * MVC controller responsible for events.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Controller
@RequestMapping("/events")
public class EventController {

    /**
     * Log4j logger
     */
    private static final Logger log = LogManager.getLogger(EventController.class);

    /**
     * The EventService reference.
     */
    private EventService eventService;

    /**
     * The PatientService reference.
     */
    private PatientService patientService;

    /**
     * The JmsPublisher reference.
     */
    private JmsPublisher publisher;

    /**
     * Number of page navigation panel buttons
     */
    private static final int NAVIGATION_PAGE_LINKS_COUNT = 4;

    /**
     * The records quantity to be shown on the page
     */
    private static final int EVENTS_PAGE_SIZE_DEFAULT = 10;

    /**
     * All args constructor.
     *
     * @param eventService   the EventService reference
     * @param patientService the PatientService reference
     * @param publisher      the JmsPublisher reference
     */
    @Autowired
    public EventController(EventService eventService,
                           PatientService patientService,
                           JmsPublisher publisher) {
        this.eventService = eventService;
        this.patientService = patientService;
        this.publisher = publisher;
    }

    /**
     * Shows the all events page.
     *
     * @param patientId    the patient's id
     * @param filter       the filter
     * @param page         the page
     * @param patient      the patient DTO
     * @param filterParams the FilterParams reference
     * @return the all events page
     */
    @GetMapping("/events")
    public ModelAndView eventsParam(@RequestParam(value = PATIENT_ID, required = false) String patientId,
                                    @RequestParam(value = FILTER, required = false) String filter,
                                    @RequestParam(value = PAGE, required = false) String page,
                                    @ModelAttribute(PATIENT) PatientDto patient,
                                    @ModelAttribute(FILTER_PARAMS) FilterParams filterParams) {
        ModelAndView mv = new ModelAndView("events/events");

        if (filterParams.getFilter() != null) filter = filterParams.getFilter();
        if (filterParams.getPage() != null) page = filterParams.getPage();

        handleFilterAndPage(mv, patientId, page, filter, patient);
        //Check for bad request
        if (mv == null) return new ModelAndView(REDIRECT_ERROR400);

        mv.addObject(FORMATTER, DateTimeFormatter.ofPattern(DATETIME_FORMAT).withLocale(Locale.ENGLISH));

        List<PatientDto> patients = patientService.getAll();
        mv.addObject(PATIENTS, patients);
        mv.addObject(FILTER, filterParams);

        return mv;
    }

    /**
     * Handles event's status changing to "Accomplished".
     *
     * @param eventId            the event's id
     * @param patientId          the patient's id
     * @param filter             the filter
     * @param page               the page
     * @param redirectAttributes the RedirectAttributes reference
     * @return the all events page
     */
    @PostMapping("/accomplished")
    public ModelAndView eventAccomplished(@RequestParam(EVENT_ID) String eventId,
                                          @RequestParam(value = PATIENT_ID, required = false) String patientId,
                                          @RequestParam(value = PAGE, required = false) String page,
                                          @RequestParam(value = FILTER, required = false) String filter,
                                          RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        getFilteredModelAndView(mv, patientId, redirectAttributes, filter, page);

        eventService.setAccomplishedById(Long.parseLong(eventId));

        String message = "Event [ID #" + eventId + "] status has been changed to 'Accomplished'";
        publisher.sendMessage(message);

        return mv;
    }

    /**
     * Handles event's status changing to "Cancelled".
     *
     * @param eventId            the event's id
     * @param patientId          the patient's id
     * @param filter             the filter
     * @param page               the page
     * @param reason             the cancellation reason
     * @param redirectAttributes the RedirectAttributes reference
     * @return the all events page
     */
    @PostMapping("/cancelled")
    public ModelAndView eventCancelled(@RequestParam(value = PATIENT_ID, required = false) String patientId,
                                       @RequestParam(EVENT_ID) String eventId,
                                       @RequestParam(value = PAGE, required = false) String page,
                                       @RequestParam(value = FILTER, required = false) String filter,
                                       @RequestParam(value = REASON, required = false) String reason,
                                       RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        getFilteredModelAndView(mv, patientId, redirectAttributes, filter, page);
        if (reason != null && !reason.isEmpty()) {
            eventService.setCancelledById(Long.parseLong(eventId), reason);
        } else {
            reason = CANCELLATION_REASON_NOT_SPECIFIED;
            eventService.setCancelledById(Long.parseLong(eventId), reason);
        }

        String message = "Event [ID #" + eventId + "] status has been changed to 'Cancelled'";
        publisher.sendMessage(message);

        return mv;
    }


    //---------------------------------------------------------------------------------------------------------------
    //Helper methods

    private void getFilteredModelAndView(ModelAndView mv,
                                         String patientId,
                                         RedirectAttributes redirectAttributes,
                                         String filter,
                                         String page) {
        mv.setViewName("redirect:events");
        FilterParams filterParams = new FilterParams();
        PatientDto patient;

        if (patientId != null && !patientId.equals("0") && !patientId.isEmpty()) {
            filterParams.setFilter(PATIENT);
            patient = patientService.get(Long.parseLong(patientId));
            redirectAttributes.addFlashAttribute(PATIENT, patient);
        } else if (filter != null && filter.equals(TODAY)) {
            filterParams.setFilter(TODAY);
        } else if (filter != null && filter.equals(CLOSEST)) {
            filterParams.setFilter(CLOSEST);
        }

        //Set page if applicable
        if (page != null && !page.isEmpty()) {
            filterParams.setPage(page);
        }
        redirectAttributes.addFlashAttribute(FILTER_PARAMS, filterParams);
    }

    private Pagination getPagination(String page, String queryString) {
        Pagination pagination = new Pagination();

        int pageNumber;
        try {
            pageNumber = Integer.parseInt(page);
        } catch (NumberFormatException nfe) {
            log.debug("Default, null or invalid page id, setting current page to #1");
            pageNumber = 1;
        }
        if (pageNumber < 1) pageNumber = 1;

        int totalPages = eventService.getTotalPages(EVENTS_PAGE_SIZE_DEFAULT, queryString);
        log.debug("Total pages is {}", totalPages);
        pagination.calcNavPages(pageNumber, totalPages, NAVIGATION_PAGE_LINKS_COUNT);

        return pagination;
    }

    private void handleFilterAndPage(ModelAndView mv, String patientId, String page, String filter, PatientDto patient) {
        //Filter by patient selected
        List<EventDto> events;
        Pagination pagination;

        //Filter: request param "patientId"
        if (patientId != null && !patientId.isEmpty()) {
            long id;
            try {
                id = Long.parseLong(patientId);
                patient = patientService.get(id);
                if (patient != null) {
                    pagination = getPagination(page, "patientId_" + patientId);
                    events = eventService.getAllByPatientId(Long.parseLong(patientId), pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
                } else {
                    //Patient with this ID not found, set modelAndView to null to redirect to error page
                    mv = null;

                    return;
                }
            } catch (NumberFormatException nfe) {
                //Invalid request parameters, set modelAndView to null to redirect to error page
                mv = null;

                return;
            }
            //Filter: today events
        } else if (filter != null && filter.equals(TODAY)) {
            pagination = getPagination(page, TODAY);
            events = eventService.getAllToday(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
            //Filter: events for next hour
        } else if (filter != null && filter.equals(CLOSEST)) {
            pagination = getPagination(page, CLOSEST);
            events = eventService.getClosest(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
            //If patient model is present
        } else if (patient != null && patient.getId() != 0) {
            pagination = getPagination(page, "patientId_" + patient.getId());
            events = eventService.getAllByPatientId(patient.getId(), pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
        } else {
            pagination = getPagination(page, ALL);
            events = eventService.getAllByPage(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
        }

        //Handle filter string
        handleFilterString(mv, filter);

        mv.addObject(PATIENT, patient);
        mv.addObject(PAGES, pagination);
        mv.addObject(EVENTS, events);
    }

    private void handleFilterString(ModelAndView mv, String filter) {
        if (filter != null && (filter.equals(TODAY) || filter.equals(CLOSEST) || filter.equals(PATIENT))) {
            mv.addObject(FILTER, filter);
        }
    }
}