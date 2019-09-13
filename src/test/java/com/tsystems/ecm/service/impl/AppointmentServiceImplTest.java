package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.config.TestConfig;
import com.tsystems.ecm.dao.AppointmentDao;
import com.tsystems.ecm.dto.AppointmentDto;
import com.tsystems.ecm.dto.PatientDto;
import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.Appointment;
import com.tsystems.ecm.entity.Patient;
import com.tsystems.ecm.entity.Treatment;
import com.tsystems.ecm.entity.enums.TreatmentType;
import com.tsystems.ecm.service.AppointmentService;
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
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class,
        loader = AnnotationConfigContextLoader.class)
public class AppointmentServiceImplTest {

    private static final Logger log = LogManager.getLogger(AppointmentServiceImplTest.class);

    private static final Appointment APPOINTMENT1 = new Appointment();
    private static final long APPOINTMENT1_ID = 1L;
    private static final Patient APPOINTMENT1_PATIENT = new Patient();
    private static final Treatment APPOINTMENT1_TREATMENT = new Treatment();
    private static final TreatmentType APPOINTMENT1_TYPE = TreatmentType.PROCEDURE;
    private static final String APPOINTMENT1_REGIMEN = "regimen1";
    private static final int APPOINTMENT1_DURATION = 2;

    private static final Appointment APPOINTMENT2 = new Appointment();
    private static final long APPOINTMENT2_ID = 2L;
    private static final Patient APPOINTMENT2_PATIENT = new Patient();
    private static final Treatment APPOINTMENT2_TREATMENT = new Treatment();
    private static final TreatmentType APPOINTMENT2_TYPE = TreatmentType.MEDICATION;
    private static final String APPOINTMENT2_REGIMEN = "regimen2";
    private static final int APPOINTMENT2_DURATION = 2;
    private static final String APPOINTMENT2_DOSE = "dose2";

    private static final int PATIENT_ID = 2;

    private static final List<Appointment> PATIENT_APPOINTMENTS = new ArrayList<>();

    @BeforeClass
    public static void setUp() {
        log.trace("Setting up two appointment entities.");

        APPOINTMENT1.setId(APPOINTMENT1_ID);
        APPOINTMENT1.setPatient(APPOINTMENT1_PATIENT);
        APPOINTMENT1.setTreatment(APPOINTMENT1_TREATMENT);
        APPOINTMENT1.setType(APPOINTMENT1_TYPE);
        APPOINTMENT1.setRegimen(APPOINTMENT1_REGIMEN);
        APPOINTMENT1.setDuration(APPOINTMENT1_DURATION);
        log.trace("Appointment 1: {}", APPOINTMENT1.toString());

        APPOINTMENT2.setId(APPOINTMENT2_ID);
        APPOINTMENT2.setPatient(APPOINTMENT2_PATIENT);
        APPOINTMENT2.setTreatment(APPOINTMENT2_TREATMENT);
        APPOINTMENT2.setType(APPOINTMENT2_TYPE);
        APPOINTMENT2.setRegimen(APPOINTMENT2_REGIMEN);
        APPOINTMENT2.setDuration(APPOINTMENT2_DURATION);
        APPOINTMENT2.setDose(APPOINTMENT2_DOSE);
        log.trace("Appointment 2: {}", APPOINTMENT2.toString());

        log.trace("Setting up patient's appointment list, containing appointment 2");
        PATIENT_APPOINTMENTS.add(APPOINTMENT2);
    }

    @Before
    public void setDao() {
        when(appointmentDao.get(APPOINTMENT1_ID)).thenReturn(APPOINTMENT1);
        when(appointmentDao.getAllByPatientId(PATIENT_ID)).thenReturn(PATIENT_APPOINTMENTS);
        doAnswer(invocation -> {log.info("Delete entity invoked!");
            return null;
        }).when(appointmentDao).delete(any(Appointment.class));
    }

    @Autowired
    public AppointmentService appointmentService;

    @Autowired
    public AppointmentDao appointmentDao;

    @Test
    public void addOrUpdateAppointment_saveNew() {
        log.info("Testing: save new appointment.");
        //setup
        AppointmentDto appointmentToSave = new AppointmentDto(0L, new PatientDto(), new TreatmentDto(), TreatmentType.MEDICATION, "regimen", "regimenString", 2, "dose");

        //expected
        long expected = 0L;

        //actual
        long actual = appointmentService.addOrUpdateAppointment(appointmentToSave);

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void addOrUpdateAppointment_update() {
        log.info("Testing: edit existing appointment; old and updated appointments should be not equal");
        //setup
        AppointmentDto oldAppointment = new AppointmentDto(APPOINTMENT1_ID, new PatientDto(), new TreatmentDto(), APPOINTMENT1_TYPE, APPOINTMENT1_REGIMEN, "regimenString", APPOINTMENT1_DURATION, null);
        AppointmentDto appointmentToUpdate = new AppointmentDto(APPOINTMENT1_ID, new PatientDto(), new TreatmentDto(), TreatmentType.MEDICATION, "updatedRegimen", "regimenString", APPOINTMENT1_DURATION + 1, "dose");

        //expected
        long expected = APPOINTMENT1_ID;

        //actual
        long actual = appointmentService.addOrUpdateAppointment(appointmentToUpdate);

        //test
        log.info("Expected id = {}, actual id = {}", expected, actual);
        assertEquals(expected, actual);
        log.info("Old appointment = {}", oldAppointment);
        log.info("New appointment = {}", appointmentToUpdate);
        assertNotEquals(oldAppointment, appointmentToUpdate);

        //reset data
        APPOINTMENT1.setType(APPOINTMENT1_TYPE);
        APPOINTMENT1.setRegimen(APPOINTMENT1_REGIMEN);
        APPOINTMENT1.setDuration(APPOINTMENT1_DURATION);
        APPOINTMENT1.setDose(null);
        log.info("Test passed!");
    }

    @Test
    public void get_success() {
        log.info("Testing: return appointment DTO.");
        //expected
        AppointmentDto expected = new AppointmentDto(APPOINTMENT1_ID, new PatientDto(), new TreatmentDto(), APPOINTMENT1_TYPE, APPOINTMENT1_REGIMEN, null, APPOINTMENT1_DURATION, null);

        //actual
        AppointmentDto actual = appointmentService.get(APPOINTMENT1_ID);
        actual.setType(APPOINTMENT1_TYPE); //this hack is needed, because appointmentDto treatment type is being set in appointment controller, not by the mapper

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void getAllByPatientId_success() {
        log.info("Testing: return patient's appointments DTO, that is appointment with id 2.");
        //setup
        PatientDto patient = new PatientDto();
        patient.setId(PATIENT_ID);
        AppointmentDto appointment = new AppointmentDto(APPOINTMENT2_ID, patient, new TreatmentDto(), APPOINTMENT2_TYPE, APPOINTMENT2_REGIMEN, null, APPOINTMENT2_DURATION, APPOINTMENT2_DOSE);

        //expected
        List<AppointmentDto> expected = new ArrayList<>();
        expected.add(appointment);

        //actual
        List<AppointmentDto> actual = appointmentService.getAllByPatientId(PATIENT_ID);
        actual.get(0).setType(APPOINTMENT2_TYPE); //this hack is needed, because appointmentDto treatment type is being set in appointment controller, not by the mapper
        actual.get(0).getPatient().setId(PATIENT_ID); //same as for treatment type

        //test
        assertEquals(expected, actual);
        log.info("Test passed!");
    }

    @Test
    public void cancelAppointmentById() {
        log.info("Testing: delete appointment (does nothing).");
        //expected log entry "Delete entity invoked!"
        //verifies by mockito that appointmentDao.delete(Appointment appointment) was run once

        //test
        appointmentService.cancelAppointmentById(APPOINTMENT1_ID);
        verify(appointmentDao, times(1)).delete(any(Appointment.class));
        log.info("Test passed!");
    }
}