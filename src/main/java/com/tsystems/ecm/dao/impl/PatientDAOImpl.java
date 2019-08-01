package com.tsystems.ecm.dao.impl;

import com.tsystems.ecm.dao.PatientDAO;
import com.tsystems.ecm.entity.PatientEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PatientDAOImpl implements PatientDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<PatientEntity> getPatients() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PatientEntity> cq = cb.createQuery(PatientEntity.class);
        Root<PatientEntity> root = cq.from(PatientEntity.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void deletePatient(int id) {
        Session session = sessionFactory.getCurrentSession();
        PatientEntity patient = session.byId(PatientEntity.class).load(id);
        session.delete(patient);
    }

    @Override
    public void savePatient(PatientEntity patient) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(patient);
    }

    @Override
    public PatientEntity getPatient(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(PatientEntity.class, id);
    }
}
