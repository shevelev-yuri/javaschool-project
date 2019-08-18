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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.next;

@Service
public class RegimenProcessorServiceImpl implements RegimenProcessorService {

    private static final Logger log = LogManager.getLogger(RegimenProcessorServiceImpl.class);

    private static final String REGIMEN_IS_NOT_SPECIFIED = "Regimen is not specified.";
    private static final String DURATION_IS_NOT_SPECIFIED = "Duration is not specified";

    private static final String EVERY_DAY = "Every day";
    private static final String TIMES_A_WEEK = "times a week";
    private static final String AND = " and ";
    private static final String FOR = " for ";
    private static final String WEEKS = " week(s)";
    private static final String ONCE_A_WEEK = "Once a week";
    private static final String ON = " on ";

    public List<EventDto> parseRegimen(AppointmentDto appointmentDto, boolean generateEvents) {
        String regimen = appointmentDto.getRegimen();
        if (regimen == null || regimen.isEmpty()) {
            //TODO Handle
            log.error(REGIMEN_IS_NOT_SPECIFIED);

            return Collections.emptyList();
        }
        int duration = appointmentDto.getDuration();
        if (duration == 0) {
            //TODO Handle
            log.error(DURATION_IS_NOT_SPECIFIED);

            return Collections.emptyList();
        }

        //TODO add a map to check if this regimen has been parsed before and take regimenString from there,
        // if not - parse it and add it to map

        String[] days;
        String[] times = null;
        String timeString;
        String dayString;
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

        regimenString += FOR + duration + WEEKS;
        appointmentDto.setRegimenString(regimenString);


        if (generateEvents) {

            List<EventDto> events = new ArrayList<>();
            List<LocalDateTime> nextEventsDateTimes = new ArrayList<>();

            createEvents(days, times, duration, nextEventsDateTimes);

            for (LocalDateTime eventDay : nextEventsDateTimes) {
                EventDto eventDto = new EventDto();
                eventDto.setPatient(appointmentDto.getPatient());
                eventDto.setTreatment(appointmentDto.getTreatment());
                eventDto.setEventStatus(EventStatus.SCHEDULED);
                eventDto.setScheduledDatetime(eventDay);

                events.add(eventDto);
            }

            return events;
        }

        return Collections.emptyList();
    }

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

    private void createEvents(String[] days, String[] times, int duration, List<LocalDateTime> nextEventsDateTimes) {
        //Parse times[] to List<LocalTime>, if present
        List<LocalTime> localTimes = null;
        if (times != null) {
            localTimes = new ArrayList<>(times.length);
            for (String time : times) {
                if ("Morning".equals(time)) {
                    localTimes.add(LocalTime.parse("09:00:00"));
                } else if ("Afternoon".equals(time)) {
                    localTimes.add(LocalTime.parse(("14:00:00")));
                } else if ("Evening".equals(time)) {
                    localTimes.add(LocalTime.parse(("19:00:00")));
                }
            }
        }

        //Create the right number of LocalDateTime instances for each day (and for each time of day, if present)
        LocalDate today = LocalDate.now();
        for (int i = 0; i < duration; i++) {
            for (String day : days) {
                if (times != null) {
                    for (LocalTime localTime : localTimes) {
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
}