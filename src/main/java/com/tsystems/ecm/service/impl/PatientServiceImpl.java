package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.PatientDAO;
import com.tsystems.ecm.entity.PatientEntity;
import com.tsystems.ecm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientDAO patientDAO;

    @Autowired
    public PatientServiceImpl(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<PatientEntity> getPatients() {
        return patientDAO.getPatients();
    }

    @Override
    @Transactional
    public void savePatient(PatientEntity thePatient) {
        patientDAO.savePatient(thePatient);
    }

    @Override
    @Transactional
    public PatientEntity getPatient(int theId) {
        return patientDAO.getPatient(theId);
    }

    @Override
    @Transactional
    public void deletePatient(int theId) {
        patientDAO.deletePatient(theId);
    }
}
