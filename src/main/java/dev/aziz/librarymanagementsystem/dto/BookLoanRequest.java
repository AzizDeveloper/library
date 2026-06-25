package dev.aziz.librarymanagementsystem.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookLoanRequest(
        @NotNull(message = "Book id must not be null or empty")
        Long bookId,

        @NotNull(message = "Reader id must not be null or empty")
        Long readerId,

        @Future(message = "Due date must not be past date")
        @NotNull(message = "Due date must not be null or empty")
        LocalDate dueDate
) {
}
