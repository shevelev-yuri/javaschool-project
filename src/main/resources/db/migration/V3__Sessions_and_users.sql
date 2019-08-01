create table if not exists users
(
    id       serial primary key,
    login    varchar(30)  not null unique,
    password varchar(100) not null
);

create table if not exists auth_sessions
(
    sid          varchar(100) primary key,
    user_login   varchar(30) not null unique,
    expired_date timestamp   not null,
    constraint auth_sessions_user_id_fkey foreign key (user_login) references users (login)
);