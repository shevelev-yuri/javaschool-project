package com.tsystems.ecm.utils;

/**
 * Utility class containing {@code String} constants ({@code static final String } fields).
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
public class StringConstants {

    /**
     * Private constructor to prevent instantiation.
     */
    private StringConstants() {
    }

    //Event controller strings
    public static final String TODAY = "today";
    public static final String CLOSEST = "closest";
    public static final String PAGES = "pages";
    public static final String EVENTS = "events";
    public static final String FORMATTER = "formatter";
    public static final String DATETIME_FORMAT = "dd.MM.yyyy HH:mm eee";
    public static final String PATIENTS = "patients";
    public static final String PATIENT_ID = "patientId";
    public static final String PAGE = "page";
    public static final String FILTER = "filter";
    public static final String FILTER_PARAMS = "filterParams";
    public static final String EVENT_ID = "eventId";
    public static final String CANCELLATION_REASON_NOT_SPECIFIED = "not specified";
    public static final String REASON = "reason";
    public static final String ALL = "all";

    //Appointment controller strings
    public static final String APPOINTMENT_ID_STRING = "Appointment [ID #";
    public static final String REDIRECT_APPOINTMENTS = "redirect:/appointments/appointments";

    //Patient controller strings
    public static final String PATIENTS_DISCHARGE = "patients/discharge";
    public static final String PATIENTS_ADD = "patients/add";
    public static final String REDIRECT_APPOINTMENTS_ADD = "redirect:/appointments/add";
    public static final String PATIENTS_PATIENTS = "patients/patients";

    //EventService strings
    public static final String APPOINTMENT_CANCELLATION_EVENTS_CANCEL_REASON = "Сancelled due to cancellation of appointment";

    //Regimen processor service strings
    public static final String REGIMEN_IS_NOT_SPECIFIED = "Regimen is not specified.";
    public static final String DURATION_IS_NOT_SPECIFIED = "Duration is not specified";
    public static final String EVERY_DAY = "Every day";
    public static final String TIMES_A_WEEK = "times a week";
    public static final String AND = " and ";
    public static final String FOR = " for ";
    public static final String WEEKS = " week(s)";
    public static final String ONCE_A_WEEK = "Once a week";
    public static final String ON = " on ";

    //Common strings
    public static final String PATIENT = "patient";
    public static final String REDIRECT_ERROR400 = "redirect:/error/error400";
    public static final String REDIRECT_PATIENTS = "redirect:/patients";
}
