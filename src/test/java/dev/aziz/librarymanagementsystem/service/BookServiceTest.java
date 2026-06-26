package dev.aziz.librarymanagementsystem.service;

import dev.aziz.librarymanagementsystem.dto.BookResponseDto;
import dev.aziz.librarymanagementsystem.entity.Book;
import dev.aziz.librarymanagementsystem.exception.ResourceNotFoundException;
import dev.aziz.librarymanagementsystem.mapper.BookMapper;
import dev.aziz.librarymanagementsystem.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Spy
    private BookSpecifications bookSpecifications;

    @Spy
    private BookMapper bookMapper;

    @InjectMocks
    private BookService underTest;

    private Book cleanCode;
    private Book springInAction;
    private Book javaFundamentals;
    private List<Book> testBooks;

    @BeforeEach
    void setUp() {
        cleanCode = new Book();
        cleanCode.setId(1L);
        cleanCode.setTitle("Clean Code");
        cleanCode.setAuthor("Robert C. Martin");
        cleanCode.setIsbn("9780132350884");
        cleanCode.setPublicationDate(LocalDate.of(2008, 8, 1));
        cleanCode.setTotalCopies(10L);
        cleanCode.setAvailableCopies(5L);
        cleanCode.setCreatedAt(Instant.now());

        springInAction = new Book();
        springInAction.setId(2L);
        springInAction.setTitle("Spring Boot in Action");
        springInAction.setAuthor("Craig Walls");
        springInAction.setIsbn("9781617292545");
        springInAction.setPublicationDate(LocalDate.of(2016, 5, 1));
        springInAction.setTotalCopies(8L);
        springInAction.setAvailableCopies(8L);
        springInAction.setCreatedAt(Instant.now());

        javaFundamentals = new Book();
        javaFundamentals.setId(3L);
        javaFundamentals.setTitle("Java 21 Fundamentals");
        javaFundamentals.setAuthor("James Gosling");
        javaFundamentals.setIsbn("9780138051864");
        javaFundamentals.setPublicationDate(LocalDate.of(2024, 3, 15));
        javaFundamentals.setTotalCopies(15L);
        javaFundamentals.setAvailableCopies(3L);
        javaFundamentals.setCreatedAt(Instant.now());

        testBooks = List.of(cleanCode, springInAction, javaFundamentals);
    }

    @Test
    void getAllBooks_shouldReturnListOfDtos_whenBooksExist() {
        // given
        when(bookRepository.findAll()).thenReturn(testBooks);

        // when
        List<BookResponseDto> result = underTest.getAllBooks();

        //then
        assertThat(result).isNotNull().hasSize(3);

        BookResponseDto dto = result.get(0);
        assertThat(dto.title()).isEqualTo("Clean Code");
        assertThat(dto.author()).isEqualTo("Robert C. Martin");
        assertThat(dto.isbn()).isEqualTo("9780132350884");

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getAllBooks_shouldReturnEmptyList_whenNoBooksExist() {
        // given
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<BookResponseDto> result = underTest.getAllBooks();

        // then
        assertThat(result).isNotNull().isEmpty();
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_shouldReturnDto_whenBookExists() {
        // given
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.of(cleanCode));

        // when
        BookResponseDto result = underTest.getBookById(id);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.title()).isEqualTo("Clean Code");
        assertThat(result.publicationDate()).isEqualTo(LocalDate.of(2008, 8, 1));

        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void getBookById_shouldThrowResourceNotFoundException_whenBookDoesNotExist() {
        // given
        Long id = 99L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // when
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> underTest.getBookById(id)
        );

        // then
        assertThat(exception.getMessage()).contains("Book").contains("id").contains("99");
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void getBooksByTitleAndAuthor_shouldThrowException_whenBothParamsAreBlank() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> underTest.getBooksByTitleAndAuthor("", null)
        );

        assertThat(exception.getMessage()).isEqualTo("At least one search parameter is required");

        verify(bookRepository, never()).findAll(ArgumentMatchers.any(Specification.class));
    }

    @Test
    void getBooksByTitleAndAuthor_shouldCallRepository_whenAtLeastOneParamIsPresent() {
        // given
        String title = "Dune";
        String author = "";
        Book book = new Book();
        book.setTitle(title);

        when(bookRepository.findAll(ArgumentMatchers.any(Specification.class)))
                .thenReturn(List.of(book));

        // when
        List<BookResponseDto> result = underTest.getBooksByTitleAndAuthor(title, author);

        // then
        assertThat(result).hasSize(1);
        verify(bookRepository, times(1)).findAll(ArgumentMatchers.any(Specification.class));
    }

}
