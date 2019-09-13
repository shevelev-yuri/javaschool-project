package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.config.TestConfig;
import com.tsystems.ecm.dao.EventDao;
import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.dto.EventDtoRest;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.Event;
import com.tsystems.ecm.entity.Patient;
import com.tsystems.ecm.entity.Treatment;
import com.tsystems.ecm.entity.enums.EventStatus;
import com.tsystems.ecm.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.tsystems.ecm.utils.StringConstants.APPOINTMENT_CANCELLATION_EVENTS_CANCEL_REASON;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class,
        loader = AnnotationConfigContextLoader.class)
public class EventServiceImplTest {

    private static final Logger log = LogManager.getLogger(PatientServiceImplTest.class);

    private static final Event EVENT1 = new Event();
    private static final long EVENT1_ID = 1L;
    private static final Patient EVENT1_PATIENT = new Patient();
    private static final LocalDateTime EVENT1_DATETIME = LocalDateTime.now().plusDays(2);
    private static final EventStatus EVENT1_STATUS = EventStatus.SCHEDULED;
    private static final Treatment EVENT1_TREATMENT = new Treatment();
    private static final String EVENT1_CANCEL_REASON = "Reason 1";
    private static final long EVENT1_APPOINTMENT_ID = 1L;

    private static final Event EVENT2 = new Event();
    private static final long EVENT2_ID = 2L;
    private static final Patient EVENT2_PATIENT = new Patient();
    private static final LocalDateTime EVENT2_DATETIME = LocalDateTime.now().plusDays(3);
    private static final EventStatus EVENT2_STATUS = EventStatus.ACCOMPLISHED;
    private static final Treatment EVENT2_TREATMENT = new Treatment();
    private static final String EVENT2_CANCEL_REASON = "Reason 2";
    private static final long EVENT2_APPOINTMENT_ID = 2L;

    private static final Event EVENT3 = new Event();
    private static final long EVENT3_ID = 3L;
    private static final Patient EVENT3_PATIENT = new Patient();
    private static final LocalDateTime EVENT3_DATETIME = LocalDateTime.now().plusHours(4); //today event
    private static final EventStatus EVENT3_STATUS = EventStatus.SCHEDULED;
    private static final Treatment EVENT3_TREATMENT = new Treatment();
    private static final String EVENT3_CANCEL_REASON = "Reason 3";
    private static final long EVENT3_APPOINTMENT_ID = 3L;

    private static final Event EVENT4 = new Event();
    private static final long EVENT4_ID = 4L;
    private static final Patient EVENT4_PATIENT = new Patient();
    private static final LocalDateTime EVENT4_DATETIME = LocalDateTime.now().plusMinutes(45); //next hour event
    private static final EventStatus EVENT4_STATUS = EventStatus.SCHEDULED;
    private static final Treatment EVENT4_TREATMENT = new Treatment();
    private static final String EVENT4_CANCEL_REASON = "Reason 4";
    private static final long EVENT4_APPOINTMENT_ID = EVENT1_APPOINTMENT_ID; //same as event 1

    private static final int ANY_INT = 0; //for irrelevant args

    private final static long PATIENT1_ID = 1L;

    private static final List<Event> ALL_EVENTS = new ArrayList<>();
    private static final List<Event> PATIENT1_EVENTS = new ArrayList<>();

    private static final int SECOND_PAGE_NUMBER = 2;
    private static final List<Event> EVENTS_ON_SECOND_PAGE = new ArrayList<>();

    private static final List<Event> EVENTS_FOR_TODAY = new ArrayList<>();
    private static final List<Event> EVENTS_FOR_NEXT_HOUR = new ArrayList<>();
    private static final List<Event> EVENTS_WITH_SAME_APPOINTMENT_ID = new ArrayList<>();

    private static final int PAGE_COUNT = 4;

    @Autowired
    public EventService eventService;

    @Autowired
    public EventDao eventDao;

    @BeforeClass
    public static void setUp() {
        log.trace("Setting up four event entities.");
        EVENT1.setId(EVENT1_ID);
        EVENT1.setPatient(EVENT1_PATIENT);
        EVENT1.setScheduledDatetime(EVENT1_DATETIME);
        EVENT1.setEventStatus(EVENT1_STATUS);
        EVENT1.setTreatment(EVENT1_TREATMENT);
        EVENT1.setCancelReason(EVENT1_CANCEL_REASON);
        EVENT1.setAppointmentIdCreatedBy(EVENT1_APPOINTMENT_ID);

        log.trace("Event 1: {}", EVENT1.toString());
        ALL_EVENTS.add(EVENT1);

        EVENT2.setId(EVENT2_ID);
        EVENT2.setPatient(EVENT2_PATIENT);
        EVENT2.setScheduledDatetime(EVENT2_DATETIME);
        EVENT2.setEventStatus(EVENT2_STATUS);
        EVENT2.setTreatment(EVENT2_TREATMENT);
        EVENT2.setCancelReason(EVENT2_CANCEL_REASON);
        EVENT2.setAppointmentIdCreatedBy(EVENT2_APPOINTMENT_ID);

        log.trace("Event 2: {}", EVENT2.toString());
        ALL_EVENTS.add(EVENT2);

        EVENT3.setId(EVENT3_ID);
        EVENT3.setPatient(EVENT3_PATIENT);
        EVENT3.setScheduledDatetime(EVENT3_DATETIME);
        EVENT3.setEventStatus(EVENT3_STATUS);
        EVENT3.setTreatment(EVENT3_TREATMENT);
        EVENT3.setCancelReason(EVENT3_CANCEL_REASON);
        EVENT3.setAppointmentIdCreatedBy(EVENT3_APPOINTMENT_ID);

        log.trace("Event 3: {}", EVENT3.toString());
        ALL_EVENTS.add(EVENT3);

        EVENT4.setId(EVENT4_ID);
        EVENT4.setPatient(EVENT4_PATIENT);
        EVENT4.setScheduledDatetime(EVENT4_DATETIME);
        EVENT4.setEventStatus(EVENT4_STATUS);
        EVENT4.setTreatment(EVENT4_TREATMENT);
        EVENT4.setCancelReason(EVENT4_CANCEL_REASON);
        EVENT4.setAppointmentIdCreatedBy(EVENT4_APPOINTMENT_ID);

        log.trace("Event 4: {}", EVENT4.toString());
        ALL_EVENTS.add(EVENT4);

        log.trace("Setting up event list for patient 1, containing event 1.");
        PATIENT1_EVENTS.add(EVENT1);

        log.trace("Setting up event list for page 2, containing event 2.");
        EVENTS_ON_SECOND_PAGE.add(EVENT2);

        log.trace("Setting up event list for today, containing event 3.");
        EVENTS_FOR_TODAY.add(EVENT3);

        log.trace("Setting up event list for next hour, containing event 4.");
        EVENTS_FOR_NEXT_HOUR.add(EVENT4);

        log.trace("Setting up event list created by same appointment, containing event 1 and 4.");
        EVENTS_WITH_SAME_APPOINTMENT_ID.add(EVENT1);
        EVENTS_WITH_SAME_APPOINTMENT_ID.add(EVENT4);
    }

    @Before
    public void setDao() {
        //should be in @BeforeClass
        when(eventDao.getAll()).thenReturn(ALL_EVENTS);
        when(eventDao.get(EVENT1_ID)).thenReturn(EVENT1);
        when(eventDao.get(EVENT2_ID)).thenReturn(EVENT2);
        when(eventDao.getAllByPatientId(eq(PATIENT1_ID), anyInt(), anyInt())).thenReturn(PATIENT1_EVENTS);
        when(eventDao.getAllByPage(eq(SECOND_PAGE_NUMBER), anyInt())).thenReturn(EVENTS_ON_SECOND_PAGE);
        when(eventDao.getAllToday(anyInt(), anyInt())).thenReturn(EVENTS_FOR_TODAY);
        when(eventDao.getAllToday()).thenReturn(EVENTS_FOR_TODAY);
        when(eventDao.getClosest(anyInt(), anyInt())).thenReturn(EVENTS_FOR_NEXT_HOUR);
        when(eventDao.getTotalPages(anyInt(), anyString())).thenReturn(PAGE_COUNT);
        when(eventDao.getAllByAppointmentId(EVENT1_APPOINTMENT_ID)).thenReturn(EVENTS_WITH_SAME_APPOINTMENT_ID);
    }

    @Test
    public void getAll_success() {
        log.info("Testing: return list containing both events DTO.");
        //setup
        PatientDto patient = new PatientDto();
        TreatmentDto treatment = new TreatmentDto();
        EventDto event1 = new EventDto(EVENT1_ID, patient, EVENT1_DATETIME, EVENT1_STATUS, treatment, EVENT1_CANCEL_REASON, EVENT1_APPOINTMENT_ID);
        EventDto event2 = new EventDto(EVENT2_ID, patient, EVENT2_DATETIME, EVENT2_STATUS, treatment, EVENT2_CANCEL_REASON, EVENT2_APPOINTMENT_ID);
        EventDto event3 = new EventDto(EVENT3_ID, patient, EVENT3_DATETIME, EVENT3_STATUS, treatment, EVENT3_CANCEL_REASON, EVENT3_APPOINTMENT_ID);
        EventDto event4 = new EventDto(EVENT4_ID, patient, EVENT4_DATETIME, EVENT4_STATUS, treatment, EVENT4_CANCEL_REASON, EVENT4_APPOINTMENT_ID);

        //expected
        List<EventDto> expected = new ArrayList<>();
        expected.add(event1);
        expected.add(event2);
        expected.add(event3);
        expected.add(event4);

        //actual
        List<EventDto> actual = eventService.getAll();

        //test
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        log.info("Test passed!");
    }

    @Test
    public void getAllByPatientId_success() {
        log.info("Testing: return list containing events DTO for patient with id 1.");
        //setup
        PatientDto patient = new PatientDto();
        TreatmentDto treatment = new TreatmentDto();

        //expected
        List<EventDto> expected = new ArrayList<>();
        EventDto event1 = new EventDto(EVENT1_ID, patient, EVENT1_DATETIME, EVENT1_STATUS, treatment, EVENT1_CANCEL_REASON, EVENT1_APPOINTMENT_ID);
        expected.add(event1);

        //actual
        List<EventDto> actual = eventService.getAllByPatientId(PATIENT1_ID, ANY_INT, ANY_INT);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void getAllByPage_success() {
        log.info("Testing: return list of events DTO on page 2 containing event 2.");
        //expected
        List<EventDto> expected = new ArrayList<>();
        EventDto eventOnSecondPage = new EventDto(EVENT2_ID, new PatientDto(), EVENT2_DATETIME, EVENT2_STATUS, new TreatmentDto(), EVENT2_CANCEL_REASON, EVENT2_APPOINTMENT_ID);
        expected.add(eventOnSecondPage);

        //actual
        List<EventDto> actual = eventService.getAllByPage(SECOND_PAGE_NUMBER, ANY_INT);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void getAllToday_success() {
        log.info("Testing: return list with events DTO scheduled for today, containing event 3.");
        //expected
        List<EventDto> expected = new ArrayList<>();
        EventDto eventForToday = new EventDto(EVENT3_ID, new PatientDto(), EVENT3_DATETIME, EVENT3_STATUS, new TreatmentDto(), EVENT3_CANCEL_REASON, EVENT3_APPOINTMENT_ID);
        expected.add(eventForToday);

        //actual
        List<EventDto> actual = eventService.getAllToday(ANY_INT, ANY_INT);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void getAllTodayRest_success() {
        log.info("Testing: return list with events DTO scheduled for today, containing event 3.");
        //expected

        List<EventDtoRest> expected = new ArrayList<>();
        EventDtoRest eventForToday = new EventDtoRest(
                null,
                EVENT3_DATETIME.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm eeee").withLocale(Locale.ENGLISH)),
                StringUtils.capitalize(EVENT3_STATUS.name().toLowerCase()),
                null,
                EVENT3_CANCEL_REASON);
        expected.add(eventForToday);

        //actual
        List<EventDtoRest> actual = eventService.getAllToday();

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void getClosest() {
        log.info("Testing: return list with events DTO scheduled for next hour, containing event 3.");
        //expected
        List<EventDto> expected = new ArrayList<>();
        EventDto eventForNextHour = new EventDto(EVENT4_ID, new PatientDto(), EVENT4_DATETIME, EVENT4_STATUS, new TreatmentDto(), EVENT4_CANCEL_REASON, EVENT4_APPOINTMENT_ID);
        expected.add(eventForNextHour);

        //actual
        List<EventDto> actual = eventService.getClosest(ANY_INT, ANY_INT);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void getTotalPages() {
        log.info("Testing: return page count");
        //expected
        int expected = PAGE_COUNT;

        //actual
        int actual = eventService.getTotalPages(ANY_INT, "any string");

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void setAccomplishedById() {
        log.info("Testing: change status for event 1 to 'Accomplished'");
        //expected
        EventStatus expected = EventStatus.ACCOMPLISHED;

        //test
        eventService.setAccomplishedById(EVENT1_ID);
        assertEquals(expected, EVENT1.getEventStatus());

        //reset data
        EVENT1.setEventStatus(EVENT1_STATUS);
        log.info("Test passed!");
    }

    @Test
    public void setCancelledById() {
        log.info("Testing: change status for event 1 to 'Cancelled'");
        //expected
        EventStatus expected = EventStatus.CANCELLED;
        String expectedReason = "Some reason";

        //test
        eventService.setCancelledById(EVENT1_ID, "Some reason");
        assertEquals(expected, EVENT1.getEventStatus());
        assertEquals(expectedReason, EVENT1.getCancelReason());

        //reset data
        EVENT1.setEventStatus(EVENT1_STATUS);
        EVENT1.setCancelReason(EVENT1_CANCEL_REASON);
        log.info("Test passed!");
    }

    @Test
    public void setCancelledByAppointmentId() {
        log.info("Testing: change status to 'Cancelled' for events created by same appointment - events 1 and 4");
        //expected
        EventStatus expected = EventStatus.CANCELLED;
        String expectedReason = APPOINTMENT_CANCELLATION_EVENTS_CANCEL_REASON;

        //test
        eventService.setCancelledByAppointmentId(EVENT1_ID);
        assertEquals(expected, EVENT1.getEventStatus());
        assertEquals(expectedReason, EVENT1.getCancelReason());

        assertEquals(expected, EVENT4.getEventStatus());
        assertEquals(expectedReason, EVENT4.getCancelReason());

        //reset data
        EVENT1.setEventStatus(EVENT1_STATUS);
        EVENT1.setCancelReason(EVENT1_CANCEL_REASON);
        EVENT4.setEventStatus(EVENT4_STATUS);
        EVENT4.setCancelReason(EVENT4_CANCEL_REASON);
        log.info("Test passed!");
    }

    @Test
    public void addEvents() {
        log.info("Testing: add new events list, return list size.");
        //setup
        EventDto event1 = new EventDto(EVENT1_ID, new PatientDto(), EVENT1_DATETIME, EVENT1_STATUS, new TreatmentDto(), EVENT1_CANCEL_REASON, EVENT1_APPOINTMENT_ID);
        EventDto event2 = new EventDto(EVENT3_ID, new PatientDto(), EVENT3_DATETIME, EVENT3_STATUS, new TreatmentDto(), EVENT3_CANCEL_REASON, EVENT3_APPOINTMENT_ID);
        List<EventDto> list = new ArrayList<>();
        list.add(event1);
        list.add(event2);

        //expected
        int expected = 2;

        //actual
        int actual = eventService.addEvents(list);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }
}