package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.PatientDao;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.entity.Patient;
import com.tsystems.ecm.entity.enums.PatientStatus;
import com.tsystems.ecm.mapper.PatientDtoToPatientEntityMapper;
import com.tsystems.ecm.mapper.PatientEntityToPatientDtoMapper;
import com.tsystems.ecm.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic implementation of the <tt>PatientService</tt> interface.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Service
public class PatientServiceImpl implements PatientService {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(PatientServiceImpl.class);

    /**
     * The PatientDao reference.
     */
    private PatientDao patientDao;

    /**
     * The PatientEntityToPatientDtoMapper reference.
     */
    private PatientEntityToPatientDtoMapper mapperEntityToDto;

    /**
     * The PatientDtoToPatientEntityMapper reference.
     */
    private PatientDtoToPatientEntityMapper mapperDtoToEntity;

    /**
     * All args constructor
     *
     * @param patientDao        the PatientDao reference
     * @param mapperEntityToDto the PatientEntityToPatientDtoMapper reference
     * @param mapperDtoToEntity the PatientDtoToPatientEntityMapper reference
     */
    @Autowired
    public PatientServiceImpl(PatientDao patientDao, PatientEntityToPatientDtoMapper mapperEntityToDto, PatientDtoToPatientEntityMapper mapperDtoToEntity) {
        this.patientDao = patientDao;
        this.mapperEntityToDto = mapperEntityToDto;
        this.mapperDtoToEntity = mapperDtoToEntity;
    }

    @Override
    @Transactional
    public List<PatientDto> getAll() {
        log.trace("getAll method called");
        List<Patient> entities = patientDao.getAll();

        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PatientDto get(long id) {
        log.trace("get method called");

        Patient patient = patientDao.get(id);
        if (patient == null) {
            return null;
        }

        return mapperEntityToDto.map(patient);
    }

    @Override
    @Transactional
    public long addPatient(PatientDto patient) {
        log.trace("addPatient method called");
        Patient patientEntity = mapperDtoToEntity.map(patient);
        patientDao.save(patientEntity);

        return patientEntity.getId();
    }

    @Override
    @Transactional
    public void dischargePatient(long id) {
        log.trace("dischargePatient method called");
        patientDao.get(id).setPatientStatus(PatientStatus.DISCHARGED);
    }
}
