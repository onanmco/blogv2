create table if not exists "user" (
    id uuid not null primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    credentials_updated_at timestamptz not null default now(),
    constraint user_un_1 unique (email)
)

