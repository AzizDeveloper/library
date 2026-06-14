package dev.aziz.librarymanagementsystem.dto;

import dev.aziz.librarymanagementsystem.validator.ValidIsbn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record BookRequestDto(

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Author is required")
        String author,

        @NotBlank
        @ValidIsbn(allowedLengths = {10, 13})
        String isbn,

        @NotNull
        LocalDate publicationDate,

        @Positive
        Long totalCopies,

        @Positive
        Long availableCopies

) {
}
