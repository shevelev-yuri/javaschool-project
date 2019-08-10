package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.PatientEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientDao extends AbstractDao<PatientEntity> {

    private static final String SELECT_ALL_PATIENT_ENTITY = "FROM PatientEntity";

    public void save(PatientEntity patient) {
        persist(patient);
    }

    public PatientEntity get(long id) {
        return getSessionFactory().getCurrentSession().get(PatientEntity.class, id);
    }

    public List<PatientEntity> getAll() {
        return getSessionFactory().getCurrentSession().createQuery(SELECT_ALL_PATIENT_ENTITY, PatientEntity.class).getResultList();
    }

    public void remove(long id) {
        Session session = getSessionFactory().getCurrentSession();
        PatientEntity patient = session.get(PatientEntity.class, id);
        if (patient != null) session.delete(patient);
    }
}