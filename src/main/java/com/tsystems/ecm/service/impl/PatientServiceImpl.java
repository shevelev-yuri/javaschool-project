package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.PatientDao;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.entity.Patient;
import com.tsystems.ecm.entity.enums.PatientStatus;
import com.tsystems.ecm.mapper.PatientDtoToPatientEntityMapper;
import com.tsystems.ecm.mapper.PatientEntityToPatientDtoMapper;
import com.tsystems.ecm.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientDao patientDao;

    private PatientEntityToPatientDtoMapper mapperEntityToDto;

    private PatientDtoToPatientEntityMapper mapperDtoToEntity;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao, PatientEntityToPatientDtoMapper mapperEntityToDto, PatientDtoToPatientEntityMapper mapperDtoToEntity) {
        this.patientDao = patientDao;
        this.mapperEntityToDto = mapperEntityToDto;
        this.mapperDtoToEntity = mapperDtoToEntity;
    }

    @Override
    @Transactional
    public List<PatientDto> getAll() {
        List<Patient> entities = patientDao.getAll();
        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PatientDto get(long id) {
        Patient patient = patientDao.get(id);
        if (patient == null) {
            return null;
        }
        return mapperEntityToDto.map(patient);
    }

    @Override
    @Transactional
    public void delete(long id) {
        patientDao.remove(id);
    }

    @Override
    @Transactional
    public long addPatient(PatientDto patient) {
        Patient patientEntity = mapperDtoToEntity.map(patient);
        patientDao.save(patientEntity);
        return patientEntity.getId();
    }

    @Override
    @Transactional
    public void dischargePatient(long id) {
        patientDao.get(id).setPatientStatus(PatientStatus.DISCHARGED);
    }
}
