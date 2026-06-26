package dev.aziz.librarymanagementsystem.service;

import dev.aziz.librarymanagementsystem.dto.BookRequestDto;
import dev.aziz.librarymanagementsystem.dto.BookResponseDto;
import dev.aziz.librarymanagementsystem.dto.BookUpdateDto;
import dev.aziz.librarymanagementsystem.entity.Book;
import dev.aziz.librarymanagementsystem.exception.BusinessConflictException;
import dev.aziz.librarymanagementsystem.exception.ResourceNotFoundException;
import dev.aziz.librarymanagementsystem.mapper.BookMapper;
import dev.aziz.librarymanagementsystem.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookSpecifications bookSpecifications;
    private final BookMapper bookMapper;

    public List<BookResponseDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.toBookResponseDtoList(books);
    }

    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        return bookMapper.toBookResponseDto(book);
    }

    public BookResponseDto saveBook(BookRequestDto bookRequestDto) {
        if (bookRepository.existsByIsbn(bookRequestDto.isbn())) {
            throw new BusinessConflictException("Book with ISBN '%s' already exists.".formatted(bookRequestDto.isbn()));
        }
        Book book = bookMapper.toBook(bookRequestDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toBookResponseDto(savedBook);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public BookResponseDto updateBookById(Long id, BookUpdateDto bookUpdateDto) {
        Book foundBook = bookRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException("Book", "id", id);
        });
        foundBook.setTitle(bookUpdateDto.getTitle());
        foundBook.setAuthor(bookUpdateDto.getAuthor());
        foundBook.setAvailableCopies(bookUpdateDto.getAvailableCopies());
        foundBook.setTotalCopies(bookUpdateDto.getTotalCopies());
        foundBook.setPublicationDate(bookUpdateDto.getPublicationDate());
        foundBook.setIsbn(bookUpdateDto.getIsbn());

        Book updatedBook = bookRepository.save(foundBook);
        return bookMapper.toBookResponseDto(updatedBook);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookResponseDto> getBooksByTitleAndAuthor(String title, String author) {
        List<Specification<Book>> specs = new ArrayList<>();

        if (title != null && !title.isBlank()) {
            specs.add(bookSpecifications.titleContains(title));
        }

        if (author != null && !author.isBlank()) {
            specs.add(bookSpecifications.authorContains(author));
        }
        Specification<Book> spec = specs.stream()
                .reduce(Specification::and)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "At least one search parameter is required"));

        return bookMapper.toBookResponseDtoList(bookRepository.findAll(spec));
    }

}
