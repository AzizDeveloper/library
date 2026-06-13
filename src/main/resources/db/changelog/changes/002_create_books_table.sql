CREATE TABLE books
(
    id               BIGSERIAL PRIMARY KEY,
    title            TEXT        NOT NULL,
    author           TEXT        NOT NULL,
    isbn             VARCHAR(50) NOT NULL,
    publication_year DATE,
    total_copies     BIGINT,
    available_copies BIGINT,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);