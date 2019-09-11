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

/**
 * Dao class for event entities.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Repository
public class EventDao extends AbstractDao<Event> {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(EventDao.class);

    /**
     * SQL query suffix for ordering.
     */
    private static final String ORDERING = " order by scheduleddatetime, patient_id, treatment_id";

    /**
     * SQL query suffix for ordering with pagination.
     */
    private static final String WITH_PAGINATION = " order by scheduleddatetime, patient_id, treatment_id limit :lmt offset :ofst";

    /**
     * SQL query to select all events.
     */
    private static final String SELECT_ALL_EVENTS = "select * from events";

    /**
     * SQL query to select all events for specified patient.
     */
    private static final String SELECT_ALL_EVENTS_BY_PATIENT_ID = "select * from events where patient_id = :id";

    /**
     * SQL query to select all events, created by specified appointment.
     */
    private static final String SELECT_ALL_EVENTS_BY_APPOINTMENT_ID = "select * from events where appointmentIdCreatedBy = ?";

    /**
     * SQL query to select all events, scheduled for today.
     */
    private static final String SELECT_TODAY_EVENTS = "select * from events where scheduledDatetime between CAST (now() AS date) and CAST (now() AS date) + INTERVAL '24h'";

    /**
     * SQL query to select all events, scheduled for next hour.
     */
    private static final String SELECT_EVENTS_FOR_NEXT_HOUR = "select * from events where scheduledDatetime between now() and now() + INTERVAL '1h'";

    /**
     * SQL query to get all events count.
     */
    private static final String SELECT_ALL_EVENTS_COUNT = "select count(*) from events";

    /**
     * SQL query to get specified patient's events count.
     */
    private static final String SELECT_ALL_EVENTS_BY_PATIENT_ID_COUNT = "select count(*) from events where patient_id = :id";

    /**
     * SQL query to get count of all events, scheduled for today.
     */
    private static final String SELECT_TODAY_EVENTS_COUNT = "select count(*) from events where scheduledDatetime between CAST (now() AS date) and CAST (now() AS date) + INTERVAL '24h'";

    /**
     * SQL query to get count of all events, scheduled for next hor.
     */
    private static final String SELECT_CLOSEST_EVENTS_COUNT = "select count(*) from events where scheduledDatetime between now() and now() + INTERVAL '1h'";

    /**
     * Saves event entity.
     *
     * @param event the event entity
     */
    public void save(Event event) {
        persist(event);
    }

    /**
     * Gets event entity by id
     *
     * @param id the entity's id
     * @return the event entity
     */
    public Event get(long id) {
        return getSessionFactory().getCurrentSession().get(Event.class, id);
    }

    /**
     * Gets the list containing all events.
     *
     * @return the {@code List} containing all events
     */
    public List<Event> getAll() {
        return getSessionFactory().getCurrentSession().createNativeQuery(SELECT_ALL_EVENTS, Event.class).getResultList();
    }

    /**
     * Gets the list containing all events for specified patient.
     *
     * @param patientId  the patient's id
     * @param pageNumber the page currently selected
     * @param pageSize   the records quantity to be shown on the page
     * @return the {@code List} containing all events for specified patient
     */
    public List<Event> getAllByPatientId(long patientId, int pageNumber, int pageSize) {
        Query countSQL = getSessionFactory().getCurrentSession().createNativeQuery(SELECT_ALL_EVENTS_BY_PATIENT_ID_COUNT)
                .setParameter("id", patientId);

        int totalRows = ((BigInteger) countSQL.getSingleResult()).intValue();
        int totalPages = calcTotalPages(pageSize, totalRows);

        int offset = getOffset(pageNumber, totalPages, pageSize);

        Query query = getSessionFactory().getCurrentSession()
                .createNativeQuery(SELECT_ALL_EVENTS_BY_PATIENT_ID + WITH_PAGINATION, Event.class)
                .setParameter("id", patientId)
                .setParameter("lmt", pageSize)
                .setParameter("ofst", offset);

        List<Event> events = getEventsByQuery(query);
        log.debug("Found {} events for patient with id '{}'!", events.size(), patientId);

        return events;
    }

    /**
     * Gets the list containing all events with pagination.
     *
     * @param pageNumber the page currently selected
     * @param pageSize   the records quantity to be shown on the page
     * @return the {@code List} containing all events for current page
     */
    public List<Event> getAllByPage(int pageNumber, int pageSize) {
        Query countSQL = getSessionFactory().getCurrentSession().createNativeQuery(SELECT_ALL_EVENTS_COUNT);

        return getEvents(pageNumber, pageSize, countSQL, SELECT_ALL_EVENTS);
    }

    /**
     * Gets the list containing all events, scheduled for today.
     *
     * @param pageNumber the page currently selected
     * @param pageSize   the records quantity to be shown on the page
     * @return the {@code List} containing all events, scheduled for today
     */
    public List<Event> getAllToday(int pageNumber, int pageSize) {
        Query countSQL = getSessionFactory().getCurrentSession().createNativeQuery(SELECT_TODAY_EVENTS_COUNT);

        return getEvents(pageNumber, pageSize, countSQL, SELECT_TODAY_EVENTS);
    }

    /**
     * <p>Overloaded method for REST requests.</p>
     *
     * Gets the list containing all events, scheduled for today.
     *
     * @return the {@code List} containing all events, scheduled for today
     */
    public List<Event> getAllToday() {
        Query query = getSessionFactory().getCurrentSession()
                .createNativeQuery(SELECT_TODAY_EVENTS + ORDERING, Event.class);

        return getEventsByQuery(query);
    }

    /**
     * Gets the list containing all events, scheduled for next hour.
     *
     * @param pageNumber the page currently selected
     * @param pageSize   the records quantity to be shown on the page
     * @return the {@code List} containing all events, scheduled for next hour
     */
    public List<Event> getClosest(int pageNumber, int pageSize) {
        Query countSQL = getSessionFactory().getCurrentSession().createNativeQuery(SELECT_CLOSEST_EVENTS_COUNT);

        return getEvents(pageNumber, pageSize, countSQL, SELECT_EVENTS_FOR_NEXT_HOUR);
    }

    /**
     * Gets the list containing all events, created by specified appointment.
     *
     * @param appointmentId the appointment's id
     * @return the {@code List} containing all events, created by specified appointment
     */
    public List<Event> getAllByAppointmentId(long appointmentId) {
        Query query = getSessionFactory().getCurrentSession()
                .createNativeQuery(SELECT_ALL_EVENTS_BY_APPOINTMENT_ID, Event.class)
                .setParameter(1, appointmentId);

        List<Event> events = getEventsByQuery(query);
        log.debug("All events, created by appointment with id {} found!", appointmentId);

        return events;
    }

    /**
     * Gets number of total pages of records found by specific query.
     *
     * @param pageSize    the records quantity to be shown on the page
     * @param queryString the query type, can be 'patientId_<i>id</i>', 'today' or 'closest'
     * @return the total pages number
     */
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


    /*Helper method that executes the queries, with pagination*/
    private List<Event> getEvents(int pageNumber, int pageSize, Query countQuery, String queryString) {
        int totalRows = ((BigInteger) countQuery.getSingleResult()).intValue();
        int totalPages = calcTotalPages(pageSize, totalRows);

        int offset = getOffset(pageNumber, totalPages, pageSize);

        Query query = getSessionFactory().getCurrentSession()
                .createNativeQuery(queryString + WITH_PAGINATION, Event.class)
                .setParameter("lmt", pageSize)
                .setParameter("ofst", offset);

        return getEventsByQuery(query);
    }

    /*Helper method that executes the queries, without pagination*/
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

    /*Helper method that calculates total number of pages*/
    private int calcTotalPages(int pageSize, int totalRows) {
        int totalPages;
        if (totalRows % pageSize == 0) {
            totalPages = (totalRows / pageSize);
        } else {
            totalPages = (totalRows / pageSize) + 1;
        }
        return totalPages;
    }

    /*Helper method that calculates offset for queries with pagination*/
    private int getOffset(int pageNumber, int totalPages, int pageSize) {
        int offset;

        if (pageNumber == 1) {
            offset = 0;
        } else if (pageNumber > 1 && pageNumber <= totalPages) {
            offset = ((pageNumber - 1) * pageSize);
        } else {
            offset = totalPages;
        }
        return offset;
    }
}