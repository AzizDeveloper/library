package dev.aziz.librarymanagementsystem.dto;

import dev.aziz.librarymanagementsystem.validator.BookCopiesAware;
import dev.aziz.librarymanagementsystem.validator.ValidBookCopies;
import dev.aziz.librarymanagementsystem.validator.ValidIsbn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@ValidBookCopies
public record BookRequestDto(

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Author is required")
        String author,

        @NotBlank
        @ValidIsbn
        String isbn,

        @NotNull
        LocalDate publicationDate,

        @Positive
        @NotNull(message = "Total copies must not be null")
        Long totalCopies,

        @Positive
        @NotNull(message = "Available copies must not be null")
        Long availableCopies

) implements BookCopiesAware {
}
