CREATE TABLE books
(
    id               BIGSERIAL PRIMARY KEY,
    title            TEXT        NOT NULL,
    author           TEXT        NOT NULL,
    isbn             VARCHAR(13) NOT NULL UNIQUE,
    publication_date DATE        NOT NULL,
    total_copies     BIGINT,
    available_copies BIGINT,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_books_isbn ON books (isbn);
CREATE INDEX idx_books_author ON books (author);
CREATE INDEX idx_books_title_author ON books (title, author);