CREATE TABLE readers
(
    id           BIGSERIAL PRIMARY KEY,
    first_name   TEXT NOT NULL,
    last_name    TEXT NOT NULL,
    username     TEXT NOT NULL,
    email        TEXT NOT NULL UNIQUE,
    phone_number TEXT NOT NULL UNIQUE,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);