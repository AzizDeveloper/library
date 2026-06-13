CREATE TABLE book_loans
(
    id          BIGSERIAL PRIMARY KEY,
    book_id     BIGINT      NOT NULL,
    reader_id   BIGINT      NOT NULL,
    loan_date   DATE        NOT NULL,
    due_date    DATE        NOT NULL,
    return_date DATE,
    status      VARCHAR(20) NOT NULL,

    CONSTRAINT fk_book_loans_book
        FOREIGN KEY (book_id)
            REFERENCES books (id),

    CONSTRAINT fk_book_loans_reader
        FOREIGN KEY (reader_id)
            REFERENCES readers (id)
);