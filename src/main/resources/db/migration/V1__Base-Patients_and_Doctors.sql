create table if not exists doctors
(
    doctor_id serial primary key,
    name      varchar(32) not null
);

create table if not exists patients
(
    patient_id      serial primary key,
    name            varchar(32)  not null,
    diagnosis       varchar(100) not null,
    insuranceNumber char(12)     not null,
    doctor_id       integer      not null,
    status          varchar(20)  not null,
    constraint doctor_id_fkey foreign key (doctor_id) references doctors (doctor_id)
);