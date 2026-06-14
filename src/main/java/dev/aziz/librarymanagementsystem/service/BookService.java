package dev.aziz.librarymanagementsystem.service;

import dev.aziz.librarymanagementsystem.dto.BookRequestDto;
import dev.aziz.librarymanagementsystem.dto.BookResponseDto;
import dev.aziz.librarymanagementsystem.dto.BookUpdateDto;
import dev.aziz.librarymanagementsystem.entity.Book;
import dev.aziz.librarymanagementsystem.exception.ResourceNotFoundException;
import dev.aziz.librarymanagementsystem.mapper.BookMapper;
import dev.aziz.librarymanagementsystem.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookResponseDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.toBookResponseDtoList(books);
    }

    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException("Book", "id", id);
        });
        return bookMapper.toBookResponseDto(book);
    }

    public BookResponseDto saveBook(BookRequestDto bookRequestDto) {
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

    public List<Book> getBooksByTitleAndAuthor(String title, String author) {
        return bookRepository.findBooksByTitleAndAuthor(title, author);
    }
}
