package dev.aziz.librarymanagementsystem.dto;

import dev.aziz.librarymanagementsystem.validator.ValidIsbn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequestDto {

    @NotBlank(message = "Book title must not be null or empty")
    private String title;

    @NotBlank(message = "Book author must not be null or empty")
    private String author;

    @NotBlank(message = "Book ISBN must not be null or empty")
    @ValidIsbn(allowedLengths = {10, 13}, message = "ISBN length must be exactly 10 or 13 characters")
    private String isbn;

    @NotNull(message = "Book publication date must not be null")
    private LocalDate publicationDate;

    @Positive
    private Long totalCopies;

    @Positive
    private Long availableCopies;

}
