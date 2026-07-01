package dev.aziz.librarymanagementsystem.dto;

import dev.aziz.librarymanagementsystem.validator.BookCopiesAware;
import dev.aziz.librarymanagementsystem.validator.ValidBookCopies;
import dev.aziz.librarymanagementsystem.validator.ValidIsbn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@ValidBookCopies
public record BookUpdateDto(

        @NotBlank(message = "Book title must not be null or empty")
        String title,

        @NotBlank(message = "Book author must not be null or empty")
        String author,

        @NotBlank(message = "Book ISBN must not be null or empty")
        @ValidIsbn
        String isbn,

        @NotNull(message = "Book publication date must not be null")
        LocalDate publicationDate,

        @Positive
        @NotNull(message = "Total copies must not be null")
        Long totalCopies,

        @Positive
        @NotNull(message = "Available copies must not be null")
        Long availableCopies

) implements BookCopiesAware {
}
