package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.config.TestConfig;
import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.service.RegimenProcessorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class,
        loader = AnnotationConfigContextLoader.class)
public class RegimenProcessorServiceImplTest {

    private static final Logger log = LogManager.getLogger(RegimenProcessorServiceImplTest.class);

    private static final AppointmentDto APPOINTMENT1 = new AppointmentDto();
    private static final String REGIMEN1 = "Monday Tuesday Wednesday Thursday Friday Saturday Sunday_Morning Afternoon Evening";
    private static final int DURATION1 = 2;

    private static final AppointmentDto APPOINTMENT2 = new AppointmentDto();
    private static final String REGIMEN2 = "Monday Tuesday Wednesday Thursday Friday Saturday Sunday";
    private static final int DURATION2 = 3;

    private static final AppointmentDto APPOINTMENT3 = new AppointmentDto();
    private static final String REGIMEN3 = "Monday";
    private static final int DURATION3 = 2;

    private static final AppointmentDto APPOINTMENT4 = new AppointmentDto();
    private static final String REGIMEN4 = "Monday_Morning";
    private static final int DURATION4 = 3;

    private static final AppointmentDto APPOINTMENT5 = new AppointmentDto();
    private static final String REGIMEN5 = "Monday Wednesday_Morning Afternoon";
    private static final int DURATION5 = 2;

    private static final AppointmentDto APPOINTMENT6 = new AppointmentDto();
    private static final String REGIMEN6 = "Monday Wednesday Friday_Morning";
    private static final String REGIMEN6_B = "Monday Wednesday Friday";
    private static final int DURATION6 = 2;

    private static final String MONDAY_WEDNESDAY_AND_FRIDAY = "Monday, Wednesday and Friday";
    private static final String EVERY_DAY = "Every day";

    @Autowired
    private RegimenProcessorService regimenProcessorService;

    @BeforeClass
    public static void setUp() {
        log.trace("Setting up five appointment DTOs.");
        APPOINTMENT1.setRegimen(REGIMEN1);
        APPOINTMENT1.setDuration(DURATION1);
        log.trace("Appointment 1: {}", APPOINTMENT1.toString());

        APPOINTMENT2.setRegimen(REGIMEN2);
        APPOINTMENT2.setDuration(DURATION2);
        log.trace("Appointment 2: {}", APPOINTMENT2.toString());

        APPOINTMENT3.setRegimen(REGIMEN3);
        APPOINTMENT3.setDuration(DURATION3);
        log.trace("Appointment 3: {}", APPOINTMENT3.toString());

        APPOINTMENT4.setRegimen(REGIMEN4);
        APPOINTMENT4.setDuration(DURATION4);
        log.trace("Appointment 4: {}", APPOINTMENT4.toString());

        APPOINTMENT5.setRegimen(REGIMEN5);
        APPOINTMENT5.setDuration(DURATION5);
        log.trace("Appointment 5: {}", APPOINTMENT5.toString());

        APPOINTMENT6.setRegimen(REGIMEN6);
        APPOINTMENT6.setDuration(DURATION6);
        log.trace("Appointment 6: {}", APPOINTMENT6.toString());
    }

    @Test
    public void parseRegimen_regimenIsNull_fail() {
        log.info("Testing: return empty list for null or empty regimen string.");
        //setup
        APPOINTMENT1.setRegimen(null);

        //expected
        List<EventDto> expected = Collections.emptyList();

        //actual
        List<EventDto> actual = regimenProcessorService.parseRegimen(APPOINTMENT1, true);

        //test
        assertEquals(expected, actual);

        //reset data
        APPOINTMENT1.setRegimen(REGIMEN1);

        log.info("Test passed!");
    }

    @Test
    public void parseRegimen_regimenIsEmpty_fail() {
        log.info("Testing: return empty list for null or empty regimen string.");
        //setup
        APPOINTMENT1.setRegimen("");

        //expected
        List<EventDto> expected = Collections.emptyList();

        //actual
        List<EventDto> actual = regimenProcessorService.parseRegimen(APPOINTMENT1, false);

        //test
        assertEquals(expected, actual);

        //reset data
        APPOINTMENT1.setRegimen(REGIMEN1);

        log.info("Test passed!");
    }

    @Test
    public void parseRegimen_durationIsZero_fail() {
        log.info("Testing: return empty list if duration is 0.");
        //setup
        APPOINTMENT1.setDuration(0);

        //expected
        List<EventDto> expected = Collections.emptyList();

        //actual
        List<EventDto> actual = regimenProcessorService.parseRegimen(APPOINTMENT1, true);

        //test
        assertEquals(expected, actual);

        //reset data
        APPOINTMENT1.setDuration(DURATION1);

        log.info("Test passed!");
    }

    @Test
    public void parseRegimen_noTime_success() {
        log.info("Testing: return list with known size, events time should be at midnight.");
        //expected generated list.size() for third appointment
        int expected = 2;
        int expectedMidnightHour = LocalTime.MIDNIGHT.getHour();

        //actual
        List<EventDto> events = regimenProcessorService.parseRegimen(APPOINTMENT3, true);
        int actual = events.size();
        int actualHour = events.get(0).getScheduledDatetime().getHour();

        //test
        assertEquals(expected, actual);
        assertEquals(expectedMidnightHour, actualHour);
        log.info("Test passed!");
    }

    @Test
    public void parseRegimen_withTime_success() {
        log.info("Testing: return list with known size, events time also known.");
        //expected generated list.size() for first appointment
        int expected = 44; //recheck

        //actual
        List<EventDto> events = regimenProcessorService.parseRegimen(APPOINTMENT1, true);
        int actual = events.size();

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void parseRegimen_invalidTime() {
        log.info("Testing: ignore invalid time");
        //setup
        APPOINTMENT5.setRegimen("Monday_invalid");

        //test
        regimenProcessorService.parseRegimen(APPOINTMENT5, true);

        //reset data
        APPOINTMENT5.setRegimen(REGIMEN5);
        log.info("Test passed!");
    }

    @Test
    public void parseRegimen_oneTimeSelected_success() {
        log.info("Testing: return once a week in regimenString.");
        //expected should not contain a comma (',')
        int expected = 3;

        //actual
        List<EventDto> events = regimenProcessorService.parseRegimen(APPOINTMENT4, true);
        int actual = events.size();

        //test
        assertEquals(expected, actual);
        assertFalse(APPOINTMENT4.getRegimenString().contains(","));
        log.info("Test passed!");
    }

    @Test
    public void parseRegimen_allDaysWithoutTime() {
        log.info("Testing: return regimenString with everyday");
        //expected
        String expected = EVERY_DAY;

        //actual
        regimenProcessorService.parseRegimen(APPOINTMENT2, true);
        String actual = APPOINTMENT2.getRegimenString();

        //test
        assertTrue(actual.contains(expected));
        log.info("Test passed!");
    }

    @Test
    public void parseRegimen_notAllDaysWithAndWithoutTime() {
        log.info("Testing: return regimenString with days of week");
        //expected
        String expected = MONDAY_WEDNESDAY_AND_FRIDAY;

        //actual
        regimenProcessorService.parseRegimen(APPOINTMENT6, true);
        APPOINTMENT6.setRegimen(REGIMEN6_B);
        regimenProcessorService.parseRegimen(APPOINTMENT6, true);
        String actual = APPOINTMENT6.getRegimenString();

        //test
        assertTrue(actual.contains(expected));
        log.info("Test passed!");
    }

    @Test
    public void parseRegimen_noEventsGeneration() {
        log.info("Testing: return empty list");
        //expected
        List<EventDto> expected = Collections.emptyList();

        //actual
        List<EventDto> actual = regimenProcessorService.parseRegimen(APPOINTMENT2, false);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

}