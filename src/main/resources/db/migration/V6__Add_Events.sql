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