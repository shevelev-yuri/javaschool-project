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

@Controller
@RequestMapping("/events")
public class EventController {

    private static final Logger log = LogManager.getLogger(EventController.class);

    private static final String TODAY = "today";
    private static final String CLOSEST = "closest";
    private static final String PAGES = "pages";
    private static final String EVENTS = "events";
    private static final String FORMATTER = "formatter";
    private static final String DATETIME_FORMAT = "dd.MM.yyyy HH:mm eee";
    private static final String PATIENTS = "patients";
    private static final String PATIENT = "patient";
    private static final String PATIENT_ID = "patientId";
    private static final String PAGE = "page";
    private static final String FILTER = "filter";
    private static final String FILTER_PARAMS = "filterParams";
    private static final String EVENT_ID = "eventId";
    private static final String CANCELLATION_REASON_NOT_SPECIFIED = "not specified";
    private static final String REASON = "reason";

    private EventService eventService;

    private PatientService patientService;

    private JmsPublisher publisher;

    private static final int NAVIGATION_PAGE_LINKS_COUNT = 8;

    private static final int EVENTS_PAGE_SIZE_DEFAULT = 10;

    @Autowired
    public EventController(EventService eventService,
                           PatientService patientService,
                           JmsPublisher publisher) {
        this.eventService = eventService;
        this.patientService = patientService;
        this.publisher = publisher;
    }

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

        mv.addObject(FORMATTER, DateTimeFormatter.ofPattern(DATETIME_FORMAT).withLocale(Locale.ENGLISH));

        List<PatientDto> patients = patientService.getAll();
        mv.addObject(PATIENTS, patients);
        mv.addObject("filter", filterParams);

        return mv;
    }

    @PostMapping("/accomplished")
    public ModelAndView eventAccomplished(@RequestParam(EVENT_ID) String eventId,
                                          @RequestParam(value = PATIENT_ID, required = false) String patientId,
                                          @RequestParam(value = PAGE, required = false) String page,
                                          @RequestParam(value = FILTER, required = false) String filter,
                                          RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        getFilteredModelAndView(mv, patientId, redirectAttributes, filter, page);

        eventService.setAccomplishedById(Long.parseLong(eventId));

        String message = "Event [ID #" + eventId + "] status has been changed to [Accomplished]";
        publisher.sendMessage(message);

        return mv;
    }

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

        String message = "Event [ID #" + eventId + "] status has been changed to [Cancelled]";
        publisher.sendMessage(message);

        return mv;
    }

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
        log.debug("FilterParams: {}", filterParams.toString());
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

        if (patientId != null && !patientId.isEmpty()) {
            long id;
            try {
                id = Long.parseLong(patientId);
                patient = patientService.get(id);
                if (patient != null) {
                    pagination = getPagination(page, "patientId_" + patientId);
                    events = eventService.getAllByPatientId(Long.parseLong(patientId), pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
                } else {
                    //TODO handle not found patient with id = patientId
                    log.debug("Patient not found, redirect to all events.");
                    pagination = getPagination(page, "all");
                    events = eventService.getAllByPage(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
                }
            } catch (NumberFormatException nfe) {
                //TODO handle incorrect patientId request param
                log.debug(nfe.getMessage() + " Redirect to all events.");
                pagination = getPagination(page, "all");
                events = eventService.getAllByPage(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
            }
            //Filter by today events
        } else if (filter != null && filter.equals(TODAY)) {
            pagination = getPagination(page, TODAY);
            events = eventService.getAllToday(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
            //Filter by
        } else if (filter != null && filter.equals(CLOSEST)) {
            pagination = getPagination(page, CLOSEST);
            events = eventService.getClosest(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
            //If patient model is present
        } else if (patient != null && patient.getId() != 0) {
            pagination = getPagination(page, "patientId_" + patient.getId());
            events = eventService.getAllByPatientId(patient.getId(), pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
        } else {
            pagination = getPagination(page, "all");
            events = eventService.getAllByPage(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
        }

        //Handle filter string
        if (filter != null && (filter.equals(TODAY) || filter.equals(CLOSEST) || filter.equals(PATIENT))) {
            mv.addObject("filter", filter);
        }

        mv.addObject(PATIENT, patient);
        mv.addObject(PAGES, pagination);
        mv.addObject(EVENTS, events);
    }
}