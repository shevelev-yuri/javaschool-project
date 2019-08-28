package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.service.EventService;
import com.tsystems.ecm.service.PatientService;
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
    public static final String REDIRECT_EVENTSALL = "redirect:/events/eventsall";
    public static final String TODAY = "today";
    public static final String CLOSEST = "closest";
    public static final String PAGES = "pages";
    public static final String EVENTS = "events";
    public static final String FORMATTER = "formatter";
    public static final String DATETIME_FORMAT = "dd.MM.yyyy HH:mm eee";
    public static final String PATIENTS = "patients";

    private EventService eventService;

    private PatientService patientService;

    private static final int NAVIGATION_PAGE_LINKS_COUNT = 8;

    private static final int EVENTS_PAGE_SIZE_DEFAULT = 10;

    @Autowired
    public EventController(EventService eventService,
                           PatientService patientService) {
        this.eventService = eventService;
        this.patientService = patientService;
    }

    @GetMapping("/events")
    public ModelAndView eventsParam(@RequestParam(value = "patientId", required = false) String patientId,
                                    @RequestParam(value = "date", required = false) String dateFilter,
                                    @RequestParam(value = "page", required = false) String page,
                                    @ModelAttribute("patient") PatientDto patient) {
        ModelAndView mv = new ModelAndView("events/events");

        List<EventDto> events;
        Pagination pagination;
        if (patientId != null && !patientId.isEmpty()) {
            long id;
            try {
                id = Long.parseLong(patientId);
            } catch (NumberFormatException nfe) {
                //TODO handle incorrect patientId request param
                log.debug(nfe.getMessage() + " Redirect to all events.");
                return new ModelAndView(REDIRECT_EVENTSALL);
            }
            patient = patientService.get(id);
            if (patient == null) {
                //TODO handle not found patient with id = patientId
                return new ModelAndView(REDIRECT_EVENTSALL);
            }
            pagination = getPagination(page, "patientId_" + patientId);
            events = eventService.getAllByPatientId(Long.parseLong(patientId), pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
        } else if (dateFilter != null && dateFilter.equals(TODAY)) {
            pagination = getPagination(page, TODAY);
            events = eventService.getAllToday(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
        } else if (dateFilter != null && dateFilter.equals(CLOSEST)) {
            pagination = getPagination(page, CLOSEST);
            events = eventService.getClosest(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
        } else if (patient.getName() != null) {
            pagination = getPagination(page, "patientId_" + patient.getId());
            events = eventService.getAllByPatientId(patient.getId(), pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
        } else {
            return new ModelAndView(REDIRECT_EVENTSALL);
        }
        mv.addObject(PAGES, pagination);
        mv.addObject(EVENTS, events);
        mv.addObject("patient", patient);
        mv.addObject(FORMATTER, DateTimeFormatter.ofPattern(DATETIME_FORMAT).withLocale(Locale.ENGLISH));

        List<PatientDto> patients = patientService.getAll();
        mv.addObject(PATIENTS, patients);

        return mv;
    }

    @GetMapping("/eventstoday")
    public ModelAndView eventsToday(@RequestParam("date") String dateFilter,
                                    @RequestParam(value = "page", required = false) String page) {
        ModelAndView mv = new ModelAndView("events/eventstoday");

        return getFilteredMV(dateFilter, page, mv);
    }

    @GetMapping("/eventsclosest")
    public ModelAndView eventsClosest(@RequestParam("date") String dateFilter,
                                      @RequestParam(value = "page", required = false) String page) {
        ModelAndView mv = new ModelAndView("events/eventsclosest");

        return getFilteredMV(dateFilter, page, mv);
    }

    @GetMapping("/eventsall")
    public ModelAndView eventsAll(@RequestParam(value = "page", required = false) String page) {
        ModelAndView mv = new ModelAndView("events/eventsall");

        Pagination pagination = getPagination(page, "all");
        mv.addObject(PAGES, pagination);

        List<EventDto> events = eventService.getAllByPage(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
        mv.addObject(EVENTS, events);

        //TODO Locale and date/datetime diversification
        mv.addObject(FORMATTER, DateTimeFormatter.ofPattern(DATETIME_FORMAT).withLocale(Locale.ENGLISH));

        List<PatientDto> patients = patientService.getAll();
        mv.addObject(PATIENTS, patients);

        return mv;
    }

    @PostMapping("/accomplished")
    public ModelAndView eventAccomplished(@RequestParam(value = "patientId", required = false) String patientId,
                                          @RequestParam("eventId") String eventId,
                                          @RequestParam(value = "date", required = false) String dateFilter,
                                          RedirectAttributes redirectAttributes) {
        log.debug("Date is {}", dateFilter);
        ModelAndView mv = getMV(patientId, redirectAttributes, dateFilter);

        eventService.setAccomplishedById(Long.parseLong(eventId));

        return mv;
    }

    @PostMapping("/cancelled")
    public ModelAndView eventCancelled(@RequestParam(value = "patientId", required = false) String patientId,
                                       @RequestParam("eventId") String eventId,
                                       @RequestParam(value = "date", required = false) String dateFilter,
                                       RedirectAttributes redirectAttributes) {
        log.debug("Date is {}", dateFilter);
        ModelAndView mv = getMV(patientId, redirectAttributes, dateFilter);

        eventService.setCancelledById(Long.parseLong(eventId));

        return mv;
    }

    private ModelAndView getMV(String patientId, RedirectAttributes redirectAttributes, String dateFilter) {
        ModelAndView mv;
        log.debug("From getMV: dateFilter = {}", dateFilter);

        if (patientId != null && !patientId.isEmpty()) {
            PatientDto patient = patientService.get(Long.parseLong(patientId));
            redirectAttributes.addFlashAttribute("patient", patient);
            mv = new ModelAndView("redirect:events");
        } else if (dateFilter != null && dateFilter.equals(TODAY)) {
            mv = new ModelAndView("redirect:eventstoday");
        } else if (dateFilter != null && dateFilter.equals(CLOSEST)) {
            mv = new ModelAndView("redirect:eventsclosest");
        } else {
            mv = new ModelAndView("redirect:eventsall");
        }
        return mv;
    }

    private ModelAndView getFilteredMV(String dateFilter, String page, ModelAndView mv) {
        List<EventDto> events;
        Pagination pagination;
        String filter;
        if (dateFilter != null && dateFilter.equals(TODAY)) {
            pagination = getPagination(page, TODAY);
            filter = TODAY;
            events = eventService.getAllToday(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
        } else if (dateFilter != null && dateFilter.equals(CLOSEST)) {
            pagination = getPagination(page, CLOSEST);
            filter = CLOSEST;
            events = eventService.getClosest(pagination.getCurrentPage(), EVENTS_PAGE_SIZE_DEFAULT);
        } else {
            return new ModelAndView(REDIRECT_EVENTSALL);
        }
        mv.addObject(PAGES, pagination);
        mv.addObject(EVENTS, events);
        mv.addObject(FORMATTER, DateTimeFormatter.ofPattern(DATETIME_FORMAT).withLocale(Locale.ENGLISH));
        mv.addObject("filter", filter);

        List<PatientDto> patients = patientService.getAll();
        mv.addObject(PATIENTS, patients);

        return mv;
    }

    private Pagination getPagination(String page, String queryString) {
        Pagination pagination = new Pagination();

        int pageNumber;
        try {
            pageNumber = Integer.parseInt(page);
        } catch (NumberFormatException nfe) {
            log.debug("Null or invalid page id, setting current page to #1");
            pageNumber = 1;
        }
        if (pageNumber < 1) pageNumber = 1;

        int totalPages = eventService.getTotalPages(EVENTS_PAGE_SIZE_DEFAULT, queryString);
        log.debug("Total page quantity is {}", totalPages);
        pagination.calcNavPages(pageNumber, totalPages, NAVIGATION_PAGE_LINKS_COUNT);

        return pagination;
    }
}