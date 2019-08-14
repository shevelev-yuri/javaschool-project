create table if not exists patients
(
    patient_id      serial primary key,
    name            varchar(50)  not null,
    diagnosis       varchar(100) not null,
    insuranceNumber char(12)     not null unique,
    doctorName      varchar(50)  not null,
    patientStatus   varchar(15)  not null
);