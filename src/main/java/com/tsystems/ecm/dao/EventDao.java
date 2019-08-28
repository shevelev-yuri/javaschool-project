package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Repository
public class EventDao extends AbstractDao<Event> {

    private static final Logger log = LogManager.getLogger(EventDao.class);

    private static final String SELECT_ALL_EVENTS = "select * from events";
    private static final String ADD_PAGINATION = " order by scheduleddatetime limit :lmt offset :ofst";
    private static final String SELECT_ALL_EVENTS_BY_PATIENT_ID = "select * from events where patient_id = :id";
    private static final String SELECT_ALL_EVENTS_BY_APPOINTMENT_ID = "select * from events where appointmentIdCreatedBy = ?";
    private static final String SELECT_TODAY_EVENTS = "select * from events where scheduledDatetime between CAST (now() AS date) and CAST (now() AS date) + INTERVAL '24h'";
    private static final String SELECT_CLOSEST_EVENTS = "select * from events where scheduledDatetime between now() and now() + INTERVAL '1h'";
    public static final String SELECT_ALL_EVENTS_COUNT = "select count(*) from events";
    public static final String SELECT_ALL_EVENTS_BY_PATIENT_ID_COUNT = "select count(*) from events where patient_id = :id";
    public static final String SELECT_TODAY_EVENTS_COUNT = "select count(*) from events where scheduledDatetime between CAST (now() AS date) and CAST (now() AS date) + INTERVAL '24h'";
    public static final String SELECT_CLOSEST_EVENTS_COUNT = "select count(*) from events where scheduledDatetime between now() and now() + INTERVAL '1h'";

    public void save(Event event) {
        persist(event);
    }

    public Event get(long id) {
        return getSessionFactory().getCurrentSession().get(Event.class, id);
    }

    public List<Event> getAll() {
        return getSessionFactory().getCurrentSession().createNativeQuery(SELECT_ALL_EVENTS, Event.class).getResultList();
    }

    public List<Event> getAllByPatientId(long patientId, int pageNumber, int pageSize) {
        Query countSQL = getSessionFactory().getCurrentSession().createNativeQuery(SELECT_ALL_EVENTS_BY_PATIENT_ID_COUNT)
                .setParameter("id", patientId);

        int totalRows = ((BigInteger) countSQL.getSingleResult()).intValue();
        int totalPages = calcTotalPages(pageSize, totalRows);

        int offset = getOffset(pageNumber, totalPages, pageSize);

        Query query = getSessionFactory().getCurrentSession()
                .createNativeQuery(SELECT_ALL_EVENTS_BY_PATIENT_ID + ADD_PAGINATION, Event.class)
                .setParameter("id", patientId)
                .setParameter("lmt", pageSize)
                .setParameter("ofst", offset);

        List<Event> events = getEventsByQuery(query);
        log.debug("Found {} events for patient with id '{}'!", events.size(), patientId);

        return events;
    }

    public List<Event> getAllByPage(int pageNumber, int pageSize) {
        Query countSQL = getSessionFactory().getCurrentSession().createNativeQuery(SELECT_ALL_EVENTS_COUNT);

        return getEvents(pageNumber, pageSize, countSQL, SELECT_ALL_EVENTS);
    }

    public List<Event> getAllToday(int pageNumber, int pageSize) {
        Query countSQL = getSessionFactory().getCurrentSession().createNativeQuery(SELECT_TODAY_EVENTS_COUNT);

        return getEvents(pageNumber, pageSize, countSQL, SELECT_TODAY_EVENTS);
    }

    public List<Event> getClosest(int pageNumber, int pageSize) {
        Query countSQL = getSessionFactory().getCurrentSession().createNativeQuery(SELECT_CLOSEST_EVENTS_COUNT);

        return getEvents(pageNumber, pageSize, countSQL, SELECT_CLOSEST_EVENTS);
    }

    public List<Event> getAllByAppointmentId(long appointmentId) {
        Query query = getSessionFactory().getCurrentSession()
                .createNativeQuery(SELECT_ALL_EVENTS_BY_APPOINTMENT_ID, Event.class)
                .setParameter(1, appointmentId);

        List<Event> events = getEventsByQuery(query);
        log.info("All events, created by appointment with id {} found!", appointmentId);

        return events;
    }

    private List<Event> getEvents(int pageNumber, int pageSize, Query countQuery, String queryString) {
        int totalRows = ((BigInteger) countQuery.getSingleResult()).intValue();
        int totalPages = calcTotalPages(pageSize, totalRows);

        int offset = getOffset(pageNumber, totalPages, pageSize);

        Query query = getSessionFactory().getCurrentSession()
                .createNativeQuery(queryString + ADD_PAGINATION, Event.class)
                .setParameter("lmt", pageSize)
                .setParameter("ofst", offset);

        return getEventsByQuery(query);
    }

    private int calcTotalPages(int pageSize, int totalRows) {
        int totalPages;
        if (totalRows % pageSize == 0) {
            totalPages = (totalRows / pageSize);
        } else {
            totalPages = (totalRows / pageSize) + 1;
        }
        return totalPages;
    }

    @SuppressWarnings("unchecked")
    private List<Event> getEventsByQuery(Query query) {
        List<Event> events;
        try {
            events = (List<Event>) query.getResultList();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
            return Collections.emptyList();
        } catch (Exception e) {
            log.warn(e.getMessage());
            return Collections.emptyList();
        }

        return events;
    }

    private int getOffset(int pageNumber, int totalPages, int pageSize) {
        int offset;

        if (pageNumber == 1) {
            offset = 0;
        } else if (pageNumber > 1 && pageNumber <= totalPages) {
            offset = ((pageNumber - 1) * pageSize);
        } else {
            //TODO handle invalid page number
            offset = totalPages;
        }
        return offset;
    }

    public int getTotalPages(int pageSize, String queryString) {
        int totalRows;
        Query countQuery;

        if (queryString.contains("patientId")) {
            int id = Integer.parseInt(queryString.split("_")[1]);
            countQuery = getSessionFactory().getCurrentSession().createNativeQuery(SELECT_ALL_EVENTS_BY_PATIENT_ID_COUNT)
                    .setParameter("id", id);
        } else if (queryString.equals("today")) {
            countQuery = getSessionFactory().getCurrentSession().createNativeQuery(SELECT_TODAY_EVENTS_COUNT);
        } else if (queryString.equals("closest")) {
            countQuery = getSessionFactory().getCurrentSession().createNativeQuery(SELECT_CLOSEST_EVENTS_COUNT);
        } else {
            countQuery = getSessionFactory().getCurrentSession().createNativeQuery(SELECT_ALL_EVENTS_COUNT);
        }
        totalRows = ((BigInteger) countQuery.getSingleResult()).intValue();

        return calcTotalPages(pageSize, totalRows);
    }
}