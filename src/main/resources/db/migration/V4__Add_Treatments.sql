create table if not exists treatments
(
    treatment_id  serial primary key,
    treatmentName varchar(100) not null unique,
    treatmentType varchar(20)  not null
);