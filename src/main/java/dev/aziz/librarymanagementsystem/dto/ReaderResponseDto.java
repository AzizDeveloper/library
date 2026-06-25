package dev.aziz.librarymanagementsystem.dto;

public record ReaderResponseDto(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String phoneNumber
) {
}
