create table if not exists doctors
(
    doctorId serial primary key,
    name     varchar(32) not null
);

create table if not exists patients
(
    patientId       serial primary key,
    name            varchar(32)  not null,
    diagnosis       varchar(100) not null,
    insuranceNumber char(12)     not null,
    doctorId        integer      not null,
    status          varchar(20)  not null,
    constraint doctorId_fkey foreign key (doctorId) references doctors (doctorId)
);