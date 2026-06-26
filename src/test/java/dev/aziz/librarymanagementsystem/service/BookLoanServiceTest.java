package dev.aziz.librarymanagementsystem.service;

import dev.aziz.librarymanagementsystem.dto.BookLoanResponse;
import dev.aziz.librarymanagementsystem.entity.Book;
import dev.aziz.librarymanagementsystem.entity.BookLoan;
import dev.aziz.librarymanagementsystem.entity.Reader;
import dev.aziz.librarymanagementsystem.entity.Status;
import dev.aziz.librarymanagementsystem.exception.BookAlreadyReturnedException;
import dev.aziz.librarymanagementsystem.exception.ResourceNotFoundException;
import dev.aziz.librarymanagementsystem.mapper.BookLoanMapper;
import dev.aziz.librarymanagementsystem.repository.BookLoanRepository;
import dev.aziz.librarymanagementsystem.repository.BookRepository;
import dev.aziz.librarymanagementsystem.repository.ReaderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookLoanServiceTest {

    @Mock
    private BookLoanRepository bookLoanRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ReaderRepository readerRepository;

    @Mock
    private BookLoanMapper bookLoanMapper;

    @InjectMocks
    private BookLoanService underTest;

    @Test
    void shouldReturnOverdueBookLoans() {
        // given
        BookLoan overdueLoan = createBookLoan(Status.OVERDUE);

        BookLoanResponse response = mock(BookLoanResponse.class);

        when(bookLoanRepository.findByStatusOverdueWithFetch(Status.OVERDUE))
                .thenReturn(List.of(overdueLoan));
        when(bookLoanMapper.toBookLoanResponseList(List.of(overdueLoan)))
                .thenReturn(List.of(response));

        // when
        List<BookLoanResponse> result = underTest.getOverdueBookLoans();

        // then
        assertThat(result)
                .hasSize(1)
                .containsExactly(response);

        verify(bookLoanRepository).findByStatusOverdueWithFetch(Status.OVERDUE);
        verify(bookLoanMapper).toBookLoanResponseList(List.of(overdueLoan));
        verifyNoMoreInteractions(bookLoanRepository, bookLoanMapper);
    }

    @Test
    void shouldReturnBookSuccessfully() {
        // given
        Book book = new Book();
        book.setId(1L);
        book.setAvailableCopies(3L);

        BookLoan bookLoan = createBookLoan(Status.ISSUED);
        bookLoan.setBook(book);

        BookLoan savedLoan = createBookLoan(Status.RETURNED);
        savedLoan.setBook(book);
        savedLoan.setReturnDate(LocalDate.now());

        BookLoanResponse response = mock(BookLoanResponse.class);

        when(bookLoanRepository.findById(1L))
                .thenReturn(Optional.of(bookLoan));
        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));
        when(bookLoanRepository.save(bookLoan))
                .thenReturn(savedLoan);
        when(bookLoanMapper.toBookLoanResponse(savedLoan))
                .thenReturn(response);

        // when
        BookLoanResponse result = underTest.returnBook(1L);

        // then
        assertThat(result).isEqualTo(response);

        assertThat(book.getAvailableCopies()).isEqualTo(4L);
        assertThat(bookLoan.getStatus()).isEqualTo(Status.RETURNED);
        assertThat(bookLoan.getReturnDate()).isEqualTo(LocalDate.now());

        verify(bookLoanRepository).findById(1L);
        verify(bookRepository).findById(1L);
        verify(bookLoanRepository).save(bookLoan);
        verify(bookLoanMapper).toBookLoanResponse(savedLoan);
        verifyNoMoreInteractions(bookLoanRepository, bookRepository, bookLoanMapper);
    }

    @Test
    void shouldThrowWhenBookLoanDoesNotExist() {
        // given
        when(bookLoanRepository.findById(1L))
                .thenReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> underTest.returnBook(1L));

        // then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("BookLoan");

        verify(bookLoanRepository).findById(1L);
        verifyNoMoreInteractions(bookLoanRepository);
        verifyNoInteractions(bookRepository, bookLoanMapper);
    }

    @Test
    void shouldThrowWhenBookAlreadyReturned() {
        // given
        BookLoan bookLoan = createBookLoan(Status.RETURNED);

        when(bookLoanRepository.findById(1L))
                .thenReturn(Optional.of(bookLoan));

        // when
        Throwable thrown = catchThrowable(() -> underTest.returnBook(1L));

        // then
        assertThat(thrown)
                .isInstanceOf(BookAlreadyReturnedException.class)
                .hasMessage("This book was already returned.");

        verify(bookLoanRepository).findById(1L);
        verifyNoMoreInteractions(bookLoanRepository);
        verifyNoInteractions(bookRepository, bookLoanMapper);
    }

    @Test
    void shouldThrowWhenBookDoesNotExistDuringReturn() {
        // given
        Book book = new Book();
        book.setId(1L);

        BookLoan bookLoan = createBookLoan(Status.ISSUED);
        bookLoan.setBook(book);

        when(bookLoanRepository.findById(1L))
                .thenReturn(Optional.of(bookLoan));
        when(bookRepository.findById(1L))
                .thenReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> underTest.returnBook(1L));

        // then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book");

        verify(bookLoanRepository).findById(1L);
        verify(bookRepository).findById(1L);
        verifyNoMoreInteractions(bookLoanRepository, bookRepository);
        verifyNoInteractions(bookLoanMapper);
    }

    private BookLoan createBookLoan(Status status) {
        Book book = new Book();
        book.setId(1L);
        book.setAvailableCopies(3L);

        Reader reader = new Reader();
        reader.setId(1L);

        BookLoan bookLoan = new BookLoan();
        bookLoan.setId(1L);
        bookLoan.setBook(book);
        bookLoan.setReader(reader);
        bookLoan.setLoanDate(LocalDate.now().minusDays(7));
        bookLoan.setDueDate(LocalDate.now().plusDays(7));
        bookLoan.setStatus(status);

        return bookLoan;
    }
}
