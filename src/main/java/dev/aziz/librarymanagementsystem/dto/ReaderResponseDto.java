package dev.aziz.librarymanagementsystem.dto;

import java.time.Instant;

public record ReaderResponseDto(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String phoneNumber,
        Instant createdAt
) {
}
