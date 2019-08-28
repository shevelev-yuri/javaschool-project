package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.Appointment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

@Repository
public class AppointmentDao extends AbstractDao<Appointment> {

    private static final Logger log = LogManager.getLogger(AppointmentDao.class);

    private static final String SELECT_APPOINTMENTS_BY_PATIENT_ID = "select * from appointments where patient_id = ?";

    public Appointment get(long id) {
        return getSessionFactory().getCurrentSession().get(Appointment.class, id);
    }

    public void save(Appointment appointment) {
        persist(appointment);
    }

    public void update(Appointment appointment) {
        getSessionFactory().getCurrentSession().update(appointment);
    }

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
