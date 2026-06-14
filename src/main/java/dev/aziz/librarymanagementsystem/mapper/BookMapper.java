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
        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublicationDate(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.getCreatedAt()
        );
    }

    public Book toBook(BookRequestDto bookRequestDto) {
        Book book = new Book();
        book.setTitle(bookRequestDto.title());
        book.setAuthor(bookRequestDto.author());
        book.setIsbn(bookRequestDto.isbn());
        book.setPublicationDate(bookRequestDto.publicationDate());
        book.setTotalCopies(bookRequestDto.totalCopies());
        book.setAvailableCopies(bookRequestDto.availableCopies());
        return book;
    }

    public List<BookResponseDto> toBookResponseDtoList(List<Book> books) {
        if (books == null || books.isEmpty()) {
            return List.of();
        }
        List<BookResponseDto> dtos = new ArrayList<>(books.size());
        for (Book book : books) {
            dtos.add(toBookResponseDto(book));
        }
        return dtos;
    }

}
