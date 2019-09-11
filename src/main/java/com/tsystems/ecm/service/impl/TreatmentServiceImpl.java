package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.TreatmentDao;
import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.Treatment;
import com.tsystems.ecm.mapper.TreatmentEntityToTreatmentDtoMapper;
import com.tsystems.ecm.service.TreatmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic implementation of the <tt>TreatmentService</tt> interface.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Service
public class TreatmentServiceImpl implements TreatmentService {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(TreatmentServiceImpl.class);

    /**
     * The TreatmentDao reference.
     */
    private TreatmentDao treatmentDao;

    /**
     * The TreatmentEntityToTreatmentDtoMapper reference.
     */
    private TreatmentEntityToTreatmentDtoMapper toTreatmentDtoMapper;

    /**
     * All args constructor.
     *
     * @param treatmentDao         the TreatmentDao reference
     * @param toTreatmentDtoMapper the TreatmentEntityToTreatmentDtoMapper reference
     */
    @Autowired
    public TreatmentServiceImpl(TreatmentDao treatmentDao,
                                TreatmentEntityToTreatmentDtoMapper toTreatmentDtoMapper) {
        this.treatmentDao = treatmentDao;
        this.toTreatmentDtoMapper = toTreatmentDtoMapper;
    }

    @Override
    @Transactional
    public List<TreatmentDto> getAll() {
        log.trace("getAll method called");

        return treatmentDao.getAll().stream().map(toTreatmentDtoMapper::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TreatmentDto get(long treatmentId) {
        log.trace("get method called");
        Treatment treatment = treatmentDao.get(treatmentId);
        if (treatment == null) return null;

        return toTreatmentDtoMapper.map(treatment);
    }
}
