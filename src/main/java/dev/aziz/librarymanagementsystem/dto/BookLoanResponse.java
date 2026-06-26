package dev.aziz.librarymanagementsystem.dto;

import dev.aziz.librarymanagementsystem.entity.Status;

import java.time.LocalDate;

public record BookLoanResponse(
        Long id,
        LocalDate loanDate,
        LocalDate dueDate,
        LocalDate returnDate,
        Status status,
        ReaderResponseDto readerResponseDto,
        BookResponseDto bookResponseDto
) {
}
