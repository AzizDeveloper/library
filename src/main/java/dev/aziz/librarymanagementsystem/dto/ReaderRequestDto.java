package dev.aziz.librarymanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ReaderRequestDto (

        @NotBlank(message = "Readers first name must not be null or empty")
        String firstName,

        @NotBlank(message = "Readers last name must not be null or empty")
        String lastName,

        @NotBlank(message = "Readers username must not be null or empty")
        String username,

        @NotBlank(message = "Readers email must not be null or empty")
        @Email(message = "Readers email must be in correct format")
        String email

) {
}
