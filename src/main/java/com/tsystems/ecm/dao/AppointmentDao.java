package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.AppointmentEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AppointmentDao extends AbstractDao<AppointmentEntity> {

    private static final Logger log = LogManager.getLogger(AppointmentDao.class);

    private static final String SELECT_APPOINTMENTS_BY_PATIENT_ID = "select * from appointments where patient_id = ?";

    public void save(AppointmentEntity appointment) {
        persist(appointment);
    }

    public List<AppointmentEntity> getAllByPatientId(long id) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createNativeQuery(SELECT_APPOINTMENTS_BY_PATIENT_ID, AppointmentEntity.class).setParameter(1, id);
        List<AppointmentEntity> appointments;
        try {
            appointments = (List<AppointmentEntity>) query.getResultList();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
            return null;
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
        log.info("All appointments for patient with id: {} found!", id);
        for (AppointmentEntity appointmentEntity : appointments) {
            log.info(appointmentEntity.toString());
        }
        return appointments;
    }
}
