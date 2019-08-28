package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.Patient;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientDao extends AbstractDao<Patient> {

    private static final String SELECT_ALL_PATIENTS = "FROM Patient";

    public void save(Patient patient) {
        persist(patient);
    }

    public Patient get(long id) {
        return getSessionFactory().getCurrentSession().get(Patient.class, id);
    }

    public List<Patient> getAll() {
        return getSessionFactory().getCurrentSession().createQuery(SELECT_ALL_PATIENTS, Patient.class).getResultList();
    }

    public void remove(long id) {
        Session session = getSessionFactory().getCurrentSession();
        Patient patient = session.get(Patient.class, id);
        if (patient != null) session.delete(patient);
    }
}