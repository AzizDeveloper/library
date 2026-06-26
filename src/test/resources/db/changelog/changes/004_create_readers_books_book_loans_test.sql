-- 1. Insert Readers
INSERT INTO readers (first_name, last_name, username, email, phone_number)
VALUES ('Alice', 'Smith', 'asmith', 'alice@test.com', '111-222-3333');

INSERT INTO readers (first_name, last_name, username, email, phone_number)
VALUES ('Bob', 'Jones', 'bjones', 'bob@test.com', '444-555-6666');

-- 2. Insert Books
INSERT INTO books (title, author, isbn, publication_date, total_copies, available_copies)
VALUES ('The Hobbit', 'J.R.R. Tolkien', '9780547928227', '1937-09-21', 5, 5);

INSERT INTO books (title, author, isbn, publication_date, total_copies, available_copies)
VALUES ('Dune', 'Frank Herbert', '9780441172719', '1965-08-01', 3, 3);

-- 3. Insert Loans
INSERT INTO book_loans (book_id, reader_id, loan_date, due_date, status)
VALUES ((SELECT id FROM books WHERE isbn = '9780547928227'),
        (SELECT id FROM readers WHERE username = 'asmith'),
        '2023-10-01',
        '2023-10-15',
        'ISSUED');

INSERT INTO book_loans (book_id, reader_id, loan_date, due_date, status)
VALUES ((SELECT id FROM books WHERE isbn = '9780441172719'),
        (SELECT id FROM readers WHERE username = 'bjones'),
        '2026-06-15',
        '2026-06-18',
        'OVERDUE');