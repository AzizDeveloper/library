package dev.aziz.librarymanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "title", "author", "isbn", "publicationDate", "totalCopies", "availableCopies", "createdAt"})
public class BookResponseDto {

    private Long id;

    private String title;

    private String author;

    private String isbn;

    private LocalDate publicationDate;

    private Long totalCopies;

    private Long availableCopies;

    private Instant createdAt;

}
