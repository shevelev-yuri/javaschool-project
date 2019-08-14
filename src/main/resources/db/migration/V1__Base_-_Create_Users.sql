create table if not exists users
(
    id       serial primary key,
    login    varchar(30)  not null unique,
    password varchar(100) not null,
    name     varchar(50)  not null,
    role     varchar(20)  not null
);