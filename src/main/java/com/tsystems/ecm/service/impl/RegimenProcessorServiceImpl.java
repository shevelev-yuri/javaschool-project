package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.dto.EventDto;
import com.tsystems.ecm.entity.enums.EventStatus;
import com.tsystems.ecm.service.RegimenProcessorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.tsystems.ecm.utils.StringConstants.*;
import static java.time.temporal.TemporalAdjusters.next;

/**
 * Basic implementation of the <tt>RegimenProcessorService</tt> interface.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Service
public class RegimenProcessorServiceImpl implements RegimenProcessorService {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(RegimenProcessorServiceImpl.class);

    @Override
    public List<EventDto> parseRegimen(AppointmentDto appointmentDto, boolean generateEvents) {
        log.trace("parseRegimen method called");
        String regimen = appointmentDto.getRegimen();
        if (regimen == null || regimen.isEmpty()) {
            log.error(REGIMEN_IS_NOT_SPECIFIED);

            return Collections.emptyList();
        }
        int duration = appointmentDto.getDuration();
        if (duration == 0) {
            log.error(DURATION_IS_NOT_SPECIFIED);

            return Collections.emptyList();
        }

        //Days of week array
        String[] days;
        //Time points array
        String[] times = null;
        //String for days of week representation
        String dayString;
        //String for time points representation
        String timeString;
        //Final representation string
        String regimenString;

        //If time of day is not present
        if (!regimen.contains("_")) {
            days = regimen.split(" ");

            if (days.length == 7) {
                regimenString = EVERY_DAY;
            } else if (days.length == 1) {
                regimenString = ONCE_A_WEEK + ON + days[0];
            } else {
                dayString = transformString(days);
                regimenString = days.length + " " + TIMES_A_WEEK + ON + dayString;
            }
        }
        // If time of day is present
        else {
            days = regimen.substring(0, regimen.indexOf('_')).split(" ");
            times = regimen.substring(regimen.indexOf('_') + 1).split(" ");

            timeString = transformString(times);

            if (days.length == 7) {
                regimenString = EVERY_DAY + " " + timeString;
            } else if (days.length == 1) {
                regimenString = ONCE_A_WEEK + ON + days[0] + " " + timeString;
            } else {
                dayString = transformString(days);
                regimenString = days.length + " " + TIMES_A_WEEK + ON + dayString + " " + timeString;
            }
        }

        //Add duration
        regimenString += FOR + duration + WEEKS;

        //Finally, set appointment regimenString field
        appointmentDto.setRegimenString(regimenString);

        //Generate events part
        if (generateEvents) {

            //List to hold generated events
            List<EventDto> events = new ArrayList<>();
            //LocalDateTime list for generated events
            List<LocalDateTime> nextEventsDateTimes = new ArrayList<>();

            fillLocalDateTimeList(days, times, duration, nextEventsDateTimes);
            addEvents(appointmentDto, events, nextEventsDateTimes);

            return events;
        }

        return Collections.emptyList();
    }

//----------Helper methods that tries to reduce complexity----------

    /*Fills the nextEventsDateTimes list by parsing days[], times[] and duration*/
    private void fillLocalDateTimeList(String[] days, String[] times, int duration, List<LocalDateTime> nextEventsDateTimes) {

        //Parse times points array to List<LocalTime>, if present
        List<LocalTime> localTimes = null;
        if (times != null) {
            localTimes = new ArrayList<>(times.length);
            for (String time : times) {
                if (time.equals("Morning")) {
                    localTimes.add(LocalTime.parse("09:00:00"));
                } else if (time.equals("Afternoon")) {
                    localTimes.add(LocalTime.parse(("14:00:00")));
                } else if (time.equals("Evening")) {
                    localTimes.add(LocalTime.parse(("19:00:00")));
                }
            }
        }

        //Create the right number of LocalDateTime instances for each day (and for each time of day, if present)
        createDateTimes(days, times, duration, nextEventsDateTimes, localTimes);
    }

    /*Creates events DTOs from LocalDateTime instances and puts them into events list*/
    private void addEvents(AppointmentDto appointmentDto, List<EventDto> events, List<LocalDateTime> nextEventsDateTimes) {
        for (LocalDateTime eventDay : nextEventsDateTimes) {
            EventDto eventDto = new EventDto();
            eventDto.setPatient(appointmentDto.getPatient());
            eventDto.setTreatment(appointmentDto.getTreatment());
            eventDto.setEventStatus(EventStatus.SCHEDULED);
            eventDto.setScheduledDatetime(eventDay);

            events.add(eventDto);
        }
    }

    /*Parses arrays and duration and fills up the nextEventsDateTimes list with LocalDateTime instances*/
    private void createDateTimes(String[] days, String[] times, int duration, List<LocalDateTime> nextEventsDateTimes, List<LocalTime> localTimes) {
        LocalDate today = LocalDate.now();
        for (int i = 0; i < duration; i++) {
            for (String day : days) {
                if (times != null) {
                    for (LocalTime localTime : localTimes) {
                        // Adding today events
                        addingTodayEvents(nextEventsDateTimes, i, localTime, day);

                        LocalDateTime toAdd = LocalDateTime.of(today
                                .with(next(DayOfWeek.valueOf(day.toUpperCase()))), localTime);
                        nextEventsDateTimes.add(toAdd);
                    }
                } else {
                    LocalDateTime toAdd = LocalDateTime.from(today
                            .with(next(DayOfWeek.valueOf(day.toUpperCase())))
                            .atTime(0, 0, 0, 0));
                    nextEventsDateTimes.add(toAdd);
                }

            }
            nextEventsDateTimes.sort(LocalDateTime::compareTo);
            today = LocalDate.from(nextEventsDateTimes.get(nextEventsDateTimes.size() - 1));
        }
    }

    /*Logic to include events that should be scheduled after the present time for today
    * eg. if it's 17:00 now and the appointment has morning, afternoon, and evening, then only one event will be generated (for the evening 19:00)*/
    private void addingTodayEvents(List<LocalDateTime> nextEventsDateTimes, int i, LocalTime localTime, String day) {
        if (LocalDateTime.now().with(localTime).isAfter(LocalDateTime.now()) && i == 0 && day.equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("eeee").withLocale(Locale.ENGLISH)))) {
            nextEventsDateTimes.add(LocalDateTime.now().with(localTime));
        }
    }

    /*Builds the string with punctuation*/
    private String transformString(String[] src) {
        StringBuilder dst = new StringBuilder();
        if (src.length == 1) {
            return src[0];
        }

        for (int i = 0; i < src.length - 1; i++) {
            if (i < src.length - 2) {
                dst.append(src[i]).append(", ");
            } else {
                dst.append(src[i]).append(AND).append(src[i + 1]);
            }
        }
        return dst.toString();
    }
}