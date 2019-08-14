create table if not exists appointments
(
    appointment_id serial primary key,
    patient_id     integer      not null,
    treatment_id   integer      not null,
    type           varchar(20)  not null,
    regimen        varchar(200) not null,
    duration       integer      not null,
    dose           varchar(50),
    constraint patient_id_fkey foreign key (patient_id) references patients (patient_id),
    constraint treatment_id_fkey foreign key (treatment_id) references treatments (treatment_id)
);