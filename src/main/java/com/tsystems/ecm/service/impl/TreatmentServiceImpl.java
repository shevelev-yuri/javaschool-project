package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.TreatmentDao;
import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.TreatmentEntity;
import com.tsystems.ecm.mapper.TreatmentEntityToTreatmentDtoMapper;
import com.tsystems.ecm.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private TreatmentDao treatmentDao;
    private TreatmentEntityToTreatmentDtoMapper mapperEntityToDto;

    @Autowired
    public TreatmentServiceImpl(TreatmentDao treatmentDao,
                                TreatmentEntityToTreatmentDtoMapper mapperEntityToDto) {
        this.treatmentDao = treatmentDao;
        this.mapperEntityToDto = mapperEntityToDto;
    }

    @Override
    @Transactional
    public List<TreatmentDto> getAll() {
        List<TreatmentEntity> entities = treatmentDao.getAll();
        return entities.stream().map(mapperEntityToDto::map).collect(Collectors.toList());
    }
}
