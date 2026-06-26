package dev.aziz.librarymanagementsystem.service;

import dev.aziz.librarymanagementsystem.dto.BookLoanRequest;
import dev.aziz.librarymanagementsystem.dto.BookLoanResponse;
import dev.aziz.librarymanagementsystem.entity.Book;
import dev.aziz.librarymanagementsystem.entity.BookLoan;
import dev.aziz.librarymanagementsystem.entity.Reader;
import dev.aziz.librarymanagementsystem.entity.Status;
import dev.aziz.librarymanagementsystem.exception.BookAlreadyReturnedException;
import dev.aziz.librarymanagementsystem.exception.BookNotAvailableException;
import dev.aziz.librarymanagementsystem.exception.ResourceNotFoundException;
import dev.aziz.librarymanagementsystem.mapper.BookLoanMapper;
import dev.aziz.librarymanagementsystem.repository.BookLoanRepository;
import dev.aziz.librarymanagementsystem.repository.BookRepository;
import dev.aziz.librarymanagementsystem.repository.ReaderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final BookLoanMapper bookLoanMapper;

    @Transactional
    public BookLoanResponse createBookLoan(BookLoanRequest bookLoanRequest) {
        BookLoan bookLoan = new BookLoan();

        Book book = bookRepository.findById((bookLoanRequest.bookId()))
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookLoanRequest.bookId()));
        if (book.getAvailableCopies() <= 0) {
            throw new BookNotAvailableException("Book with id: " + bookLoanRequest.bookId() +   " has no available copies.");
        }
        Reader reader = readerRepository.findById(bookLoanRequest.readerId())
                .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", bookLoanRequest.readerId()));

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookLoan.setBook(book);
        bookLoan.setReader(reader);
        bookLoan.setDueDate(bookLoanRequest.dueDate());
        bookLoan.setStatus(Status.ISSUED);
        bookLoan.setLoanDate(LocalDate.now());
        return bookLoanMapper.toBookLoanResponse(bookLoanRepository.save(bookLoan));
    }

    public List<BookLoanResponse> getBookLoansByReaderId(Long readerId) {
        return bookLoanMapper.toBookLoanResponseList(
                bookLoanRepository.findBookLoanByReaderId(readerId));
    }

    @Transactional
    public BookLoanResponse returnBook(Long id) {
        BookLoan bookLoan = bookLoanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookLoan", "id", id));

        if (bookLoan.getStatus() == Status.RETURNED) {
            throw new BookAlreadyReturnedException("This book was already returned.");
        }

        Book book = bookRepository.findById(bookLoan.getBook().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookLoan.getBook().getId()));
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        bookLoan.setReturnDate(LocalDate.now());
        bookLoan.setStatus(Status.RETURNED);

        return bookLoanMapper.toBookLoanResponse(bookLoanRepository.save(bookLoan));
    }

    public List<BookLoanResponse> getOverdueBookLoans() {
        return bookLoanMapper.toBookLoanResponseList(
                bookLoanRepository.findByStatusOverdueWithFetch(Status.OVERDUE)
        );
    }

}
