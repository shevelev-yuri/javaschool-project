create table if not exists treatments
(
    treatment_id  serial primary key,
    treatmentName varchar(100) not null,
    treatmentType varchar(20)  not null
);

create table if not exists appointments
(
    appointment_id serial primary key,
    patient_id     integer     not null unique,
    treatment_id   integer     not null unique,
    type           varchar(20) not null,
    regimen        varchar(50) not null,
    duration       integer     not null,
    dose           varchar(50),
    constraint patient_id_fkey foreign key (patient_id) references patients (patient_id),
    constraint treatment_id_fkey foreign key (treatment_id) references treatments (treatment_id)
);

create table if not exists events
(
    event_id          serial primary key,
    patient_id        integer     not null,
    scheduledDatetime timestamp   not null,
    eventStatus       varchar(20) not null,
    treatment_id      integer     not null,
    constraint patient_id_fkey foreign key (patient_id) references patients (patient_id),
    constraint treatment_id_fkey foreign key (treatment_id) references treatments (treatment_id)
);