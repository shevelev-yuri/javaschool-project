create table if not exists users
(
    id       bigint IDENTITY,
    login    varchar(50) not null,
    name     varchar(50) not null,
    password varchar(50) not null,
    role     varchar(50) not null,
    constraint users_pk primary key (id)
);
