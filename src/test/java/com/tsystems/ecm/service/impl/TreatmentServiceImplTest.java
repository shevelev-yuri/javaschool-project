package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.config.TestConfig;
import com.tsystems.ecm.dao.TreatmentDao;
import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.Treatment;
import com.tsystems.ecm.entity.enums.TreatmentType;
import com.tsystems.ecm.mapper.TreatmentEntityToTreatmentDtoMapper;
import com.tsystems.ecm.service.TreatmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class,
        loader = AnnotationConfigContextLoader.class)
public class TreatmentServiceImplTest {

    private static final Logger log = LogManager.getLogger(TreatmentServiceImplTest.class);

    private static final Treatment TREATMENT_PROCEDURE = new Treatment();
    private static final Long PROCEDURE_ID = 1L;
    private static final String PROCEDURE_NAME = "Procedure";
    private static final TreatmentType PROCEDURE_TYPE = TreatmentType.PROCEDURE;

    private static final Treatment TREATMENT_MEDICATION = new Treatment();
    private static final Long MEDICATION_ID = 2L;
    private static final String MEDICATION_NAME = "Medication";
    private static final TreatmentType MEDICATION_TYPE = TreatmentType.MEDICATION;

    private static final Long MISSING_RECORD_ID = 3L;

    private static final List<Treatment> ALL_TREATMENTS = new ArrayList<>();

    @Autowired
    public TreatmentService treatmentService;

    @Autowired
    public TreatmentDao treatmentDao;

    @Autowired
    public TreatmentEntityToTreatmentDtoMapper treatmentEntityToTreatmentDtoMapper;

    @BeforeClass
    public static void setUp() {
        log.trace("Setting up two treatment entities.");
        TREATMENT_PROCEDURE.setId(PROCEDURE_ID);
        TREATMENT_PROCEDURE.setTreatmentName(PROCEDURE_NAME);
        TREATMENT_PROCEDURE.setTreatmentType(PROCEDURE_TYPE);
        String procedureString = TREATMENT_PROCEDURE.toString();
        ALL_TREATMENTS.add(TREATMENT_PROCEDURE);
        log.trace("Procedure type treatment set to {}", procedureString);

        TREATMENT_MEDICATION.setId(MEDICATION_ID);
        TREATMENT_MEDICATION.setTreatmentName(MEDICATION_NAME);
        TREATMENT_MEDICATION.setTreatmentType(MEDICATION_TYPE);
        String medicationString = TREATMENT_MEDICATION.toString();
        ALL_TREATMENTS.add(TREATMENT_MEDICATION);
        log.trace("Medication type treatment set to {}", medicationString);
    }
    @Before
    public void setDao() {
        when(treatmentDao.get(PROCEDURE_ID)).thenReturn(TREATMENT_PROCEDURE);
        when(treatmentDao.get(MEDICATION_ID)).thenReturn(TREATMENT_MEDICATION);
        when(treatmentDao.get(3L)).thenReturn(null);
        when(treatmentDao.getAll()).thenReturn(ALL_TREATMENTS);
    }

    @Test
    public void getAll_success() {
        log.info("Testing: return list of DTO's from ALL_TREATMENTS list.");
        //expected
        List<TreatmentDto> expected = new ArrayList<>();
        TreatmentDto procedureDto = new TreatmentDto(PROCEDURE_ID, PROCEDURE_NAME, PROCEDURE_TYPE);
        TreatmentDto medicationDto = new TreatmentDto(MEDICATION_ID, MEDICATION_NAME, MEDICATION_TYPE);
        expected.add(procedureDto);
        expected.add(medicationDto);

        //actual
        List<TreatmentDto> actual = treatmentService.getAll();

        //test
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        log.info("Test passed!");
    }

    @Test
    public void getWithIdOne_success() {
        log.info("Testing: return procedure type TreatmentDto (with id '1')");
        //expected
        TreatmentDto expected = new TreatmentDto(PROCEDURE_ID, PROCEDURE_NAME, PROCEDURE_TYPE);

        //actual
        TreatmentDto actual = treatmentService.get(PROCEDURE_ID);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void getNotFoundId_fail() {
        log.info("Testing: return null for record, that is not in the database (i.e. with id '3')");
        //actual
        TreatmentDto actual = treatmentService.get(MISSING_RECORD_ID);

        //test
        assertNull(actual);
        log.info("Test passed!");
    }
}