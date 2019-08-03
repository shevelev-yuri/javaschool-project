create table if not exists users
(
    id       int IDENTITY,
    login    varchar(50) not null,
    password varchar(50) not null,
    constraint users_pk primary key (id)
);
