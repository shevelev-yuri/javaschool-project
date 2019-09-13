package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.config.TestConfig;
import com.tsystems.ecm.dao.PatientDao;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.entity.Patient;
import com.tsystems.ecm.entity.enums.PatientStatus;
import com.tsystems.ecm.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
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
public class PatientServiceImplTest {

    private static final Logger log = LogManager.getLogger(PatientServiceImplTest.class);

    private static final Patient PAT_1 = new Patient();
    private static final long PAT_1_ID = 1L;
    private static final String PAT_1_NAME = "name_1";
    private static final String PAT_1_DIAGNOSIS = "diagnosis_1";
    private static final String PAT_1_INSURANCE = "000-0000-001";
    private static final String PAT_1_DOCTOR = "Doctor 1";
    private static final PatientStatus PAT_1_STATUS = PatientStatus.ON_TREATMENT;

    private static final Patient PAT_2 = new Patient();
    private static final long PAT_2_ID = 2L;
    private static final String PAT_2_NAME = "name_2";
    private static final String PAT_2_DIAGNOSIS = "diagnosis_2";
    private static final String PAT_2_INSURANCE = "000-0000-002";
    private static final String PAT_2_DOCTOR = "Doctor 2";
    private static final PatientStatus PAT_2_STATUS = PatientStatus.DISCHARGED;

    private static final long INVALID_ID = 4L;

    private static final PatientDto NEW_PATIENT = new PatientDto();

    private static final List<Patient> ALL_PATIENTS = new ArrayList<>();

    @Autowired
    public PatientService patientService;

    @Autowired
    public PatientDao patientDao;

    @BeforeClass
    public static void setUp() {
        log.trace("Setting up two patients entities.");
        PAT_1.setId(PAT_1_ID);
        PAT_1.setName(PAT_1_NAME);
        PAT_1.setDiagnosis(PAT_1_DIAGNOSIS);
        PAT_1.setInsuranceNumber(PAT_1_INSURANCE);
        PAT_1.setDoctorName(PAT_1_DOCTOR);
        PAT_1.setPatientStatus(PAT_1_STATUS);

        log.trace("Patient 1: {}", PAT_1.toString());
        ALL_PATIENTS.add(PAT_1);

        PAT_2.setId(PAT_2_ID);
        PAT_2.setName(PAT_2_NAME);
        PAT_2.setDiagnosis(PAT_2_DIAGNOSIS);
        PAT_2.setInsuranceNumber(PAT_2_INSURANCE);
        PAT_2.setDoctorName(PAT_2_DOCTOR);
        PAT_2.setPatientStatus(PAT_2_STATUS);

        log.trace("Patient 2: {}", PAT_2.toString());
        ALL_PATIENTS.add(PAT_2);
    }

    @Before
    public void setDao() {
        when(patientDao.getAll()).thenReturn(ALL_PATIENTS);
        when(patientDao.get(PAT_1_ID)).thenReturn(PAT_1);
        when(patientDao.get(INVALID_ID)).thenReturn(null);
    }

    @Test
    public void getAll_success() {
        log.info("Testing: return list containing Patients DTO.");
        //expected
        List<PatientDto> expected = new ArrayList<>();
        PatientDto patient1 = new PatientDto(PAT_1_ID, PAT_1_NAME, PAT_1_DIAGNOSIS, PAT_1_INSURANCE, PAT_1_DOCTOR, PAT_1_STATUS);
        PatientDto patient2 = new PatientDto(PAT_2_ID, PAT_2_NAME, PAT_2_DIAGNOSIS, PAT_2_INSURANCE, PAT_2_DOCTOR, PAT_2_STATUS);
        expected.add(patient1);
        expected.add(patient2);

        //actual
        List<PatientDto> actual = patientService.getAll();

        //test
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        log.info("Test passed!");
    }

    @Test
    public void get_success() {
        log.info("Testing: return patient one DTO.");
        //expected
        PatientDto expected = new PatientDto(PAT_1_ID, PAT_1_NAME, PAT_1_DIAGNOSIS, PAT_1_INSURANCE, PAT_1_DOCTOR, PAT_1_STATUS);

        //actual
        PatientDto actual = patientService.get(PAT_1_ID);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void get_fail() {
        log.info("Testing: return invalid id.");
        //expected null

        //actual
        PatientDto actual = patientService.get(INVALID_ID);

        //test
        assertNull(actual);
        log.info("Test passed!");
    }

    @Test
    public void addPatient() {
        log.info("Testing: add new patient.");

        NEW_PATIENT.setName("name");
        NEW_PATIENT.setDiagnosis("diag");
        NEW_PATIENT.setInsuranceNumber("000-0000-999");
        NEW_PATIENT.setDoctorName("doctor");
        NEW_PATIENT.setPatientStatus(PatientStatus.DISCHARGED);

        //expected
        long expected = 0L;

        //actual
        long actual = patientService.addPatient(NEW_PATIENT);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void dischargePatient() {
        log.info("Testing: discharge patient.");
        //expected Patient.DISCHARGED

        //actual
        patientService.dischargePatient(PAT_1_ID);

        //test
        assertEquals(PatientStatus.DISCHARGED, PAT_1.getPatientStatus());
        log.info("Test passed!");
    }

    @After
    public void reset() {
        PAT_1.setPatientStatus(PatientStatus.ON_TREATMENT);
    }
}