create table if not exists auth_sessions
(
    sid         varchar(100) primary key,
    userLogin   varchar(30) not null unique,
    expiredDate timestamp   not null,
    constraint auth_sessions_user_id_fkey foreign key (userLogin) references users (login)
);