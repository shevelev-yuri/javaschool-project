package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.Patient;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dao class for patient entities.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Repository
public class PatientDao extends AbstractDao<Patient> {

    /**
     * HQL query to select all patients.
     */
    private static final String SELECT_ALL_PATIENTS = "FROM Patient";

    /**
     * Saves the patient entity.
     *
     * @param patient the patient entity
     */
    public void save(Patient patient) {
        persist(patient);
    }

    /**
     * Gets the patient entity by id
     *
     * @param id the patient's id
     * @return the patient entity
     */
    public Patient get(long id) {
        return getSessionFactory().getCurrentSession().get(Patient.class, id);
    }

    /**
     * Gets the list containing all patients.
     *
     * @return the {@code List} containing all patients
     */
    public List<Patient> getAll() {
        return getSessionFactory().getCurrentSession().createQuery(SELECT_ALL_PATIENTS, Patient.class).getResultList();
    }

    /**
     * Deletes the patient entity.
     *
     * @param id the patient's id
     */
    public void remove(long id) {
        Session session = getSessionFactory().getCurrentSession();
        Patient patient = session.get(Patient.class, id);
        if (patient != null) session.delete(patient);
    }
}