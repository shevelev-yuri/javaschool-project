package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.PatientDao;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.entity.PatientEntity;
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
    public List<PatientDto> getAllPatients() {
        List<PatientEntity> entities = patientDao.getAll();
        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PatientDto getPatientById(int id) {
        PatientEntity patientEntity = patientDao.get(id);
        return new PatientDto(patientEntity);
    }

    @Override
    @Transactional
    public void deletePatient(int id) {
        patientDao.remove(id);
    }

    @Override
    @Transactional
    public long addPatientAndReturnId(PatientDto patient) {
        PatientEntity patientEntity = mapperDtoToEntity.map(patient);
        patientDao.save(patientEntity);
        return patientEntity.getPatientId();
    }
}
