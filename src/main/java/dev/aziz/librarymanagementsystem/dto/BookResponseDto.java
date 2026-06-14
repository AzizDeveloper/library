package dev.aziz.librarymanagementsystem.dto;

import java.time.Instant;
import java.time.LocalDate;

public record BookResponseDto(
        Long id,
        String title,
        String author,
        String isbn,
        LocalDate publicationDate,
        Long totalCopies,
        Long availableCopies,
        Instant createdAt
) {
}
