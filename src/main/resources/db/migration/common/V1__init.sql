CREATE TABLE IF NOT EXISTS users (
    id varchar(36) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
    constraint users_pk primary key(id)
);