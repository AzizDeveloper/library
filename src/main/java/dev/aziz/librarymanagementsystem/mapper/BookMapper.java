package dev.aziz.librarymanagementsystem.mapper;

import dev.aziz.librarymanagementsystem.dto.BookRequestDto;
import dev.aziz.librarymanagementsystem.dto.BookResponseDto;
import dev.aziz.librarymanagementsystem.entity.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {

    public BookResponseDto toBookResponseDto(Book book) {
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(book.getId());
        bookResponseDto.setTitle(book.getTitle());
        bookResponseDto.setAuthor(book.getAuthor());
        bookResponseDto.setIsbn(book.getIsbn());
        bookResponseDto.setPublicationDate(book.getPublicationDate());
        bookResponseDto.setTotalCopies(book.getTotalCopies());
        bookResponseDto.setAvailableCopies(book.getAvailableCopies());
        bookResponseDto.setCreatedAt(book.getCreatedAt());
        return bookResponseDto;
    }

    public Book toBook(BookRequestDto bookRequestDto) {
        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPublicationDate(bookRequestDto.getPublicationDate());
        book.setTotalCopies(bookRequestDto.getTotalCopies());
        book.setAvailableCopies(bookRequestDto.getAvailableCopies());
        return book;
    }

    public List<BookResponseDto> toBookResponseDtoList(List<Book> books) {
        if (books == null || books.isEmpty()) {
            return List.of();
        }

        // Pre-allocate the exact size needed to prevent internal array resizing
        List<BookResponseDto> dtos = new ArrayList<>(books.size());

        for (Book book : books) {
            dtos.add(toBookResponseDto(book));
        }

        return dtos;
    }

}
