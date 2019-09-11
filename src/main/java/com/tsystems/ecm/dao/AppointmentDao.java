package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.Appointment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

/**
 * Dao class for appointment entities.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Repository
public class AppointmentDao extends AbstractDao<Appointment> {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(AppointmentDao.class);

    /**
     * SQL query to select appointment by id.
     */
    private static final String SELECT_APPOINTMENTS_BY_PATIENT_ID = "select * from appointments where patient_id = ?";

    /**
     * Gets the appointment by id.
     *
     * @param id the appointment's id
     * @return the appointment entity
     */
    public Appointment get(long id) {
        return getSessionFactory().getCurrentSession().get(Appointment.class, id);
    }

    /**
     * Saves the appointment entity
     *
     * @param appointment the appointment entity
     */
    public void save(Appointment appointment) {
        persist(appointment);
    }

    /**
     * Updates the appointment entity.
     *
     * @param appointment the appointment entity
     */
    public void update(Appointment appointment) {
        getSessionFactory().getCurrentSession().update(appointment);
    }

    /**
     * Gets the list containing all appointments for specified patient.
     *
     * @param id the patient id
     * @return the {@code List} containing all appointments for specified patient
     */
    public List<Appointment> getAllByPatientId(long id) {
        Query query = getSessionFactory().getCurrentSession()
                .createNativeQuery(SELECT_APPOINTMENTS_BY_PATIENT_ID, Appointment.class)
                .setParameter(1, id);

        List<Appointment> appointments;
        try {
            appointments = (List<Appointment>) query.getResultList();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
            return Collections.emptyList();
        } catch (Exception e) {
            log.warn(e.getMessage());
            return Collections.emptyList();
        }

        return appointments;
    }
}
