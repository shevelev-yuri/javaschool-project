create table if not exists appointments
(
    appointment_id       serial primary key,
    patient_id           int         not null unique,
    type                 varchar(20) not null,
    appointment_regimen  varchar(50) not null,
    appointment_duration date        not null,
    dose                 varchar(50),
    constraint patient_id_fkey foreign key (patient_id) references patients (patient_id)
);

create table if not exists treatments
(
    treatment_id   serial primary key,
    treatment_name varchar(100) not null,
    treatment_type varchar(20)  not null
);

create table if not exists events
(
    event_id           serial primary key,
    patient_id         integer     not null,
    scheduled_datetime timestamp   not null,
    status             varchar(30) not null,
    treatment_id       integer     not null,
    constraint patient_id_fkey foreign key (patient_id) references patients (patient_id),
    constraint treatment_id_fkey foreign key (treatment_id) references treatments (treatment_id)
);