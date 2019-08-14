package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.EventEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

@Repository
public class EventDao extends AbstractDao<EventEntity> {

    private static final Logger log = LogManager.getLogger(EventDao.class);

    private static final String SELECT_ALL_EVENTS_BY_PATIENT_ID = "select * from events where patient_id = ?";
    private static final String SELECT_ALL_EVENTS_BY_APPOINTMENT_ID = "select * from events where appointmentIdCreatedBy = ?";
    private static final String SELECT_ALL_EVENTS = "FROM EventEntity";

    public void save(EventEntity event) {
        persist(event);
    }

    public EventEntity get(long id) {
        return getSessionFactory().getCurrentSession().get(EventEntity.class, id);
    }

    public List<EventEntity> getAll() {
        return getSessionFactory().getCurrentSession().createQuery(SELECT_ALL_EVENTS, EventEntity.class).getResultList();
    }

    public List<EventEntity> getAllByPatientId(long patientId) {
        List<EventEntity> events = getByQuery(SELECT_ALL_EVENTS_BY_PATIENT_ID, patientId);
        log.info("All events for patient with id: {} found!", patientId);

        return events;
    }

    public List<EventEntity> getAllByAppointmentId(long appointmentId) {
        List<EventEntity> events = getByQuery(SELECT_ALL_EVENTS_BY_APPOINTMENT_ID, appointmentId);
        log.info("All events, created by appointment with id {} found!", appointmentId);

        return events;
    }


    private List<EventEntity> getByQuery(String queryString, long id) {
        Query query = getSessionFactory().getCurrentSession()
                .createNativeQuery(queryString, EventEntity.class)
                .setParameter(1, id);

        List<EventEntity> events;
        try {
            events = (List<EventEntity>) query.getResultList();
        } catch (NoResultException nre) {
            //TODO handle
            log.debug(nre.getMessage());

            return Collections.emptyList();
        } catch (Exception e) {
            //TODO handle
            log.warn(e.getMessage());

            return Collections.emptyList();
        }

        return events;
    }
}

