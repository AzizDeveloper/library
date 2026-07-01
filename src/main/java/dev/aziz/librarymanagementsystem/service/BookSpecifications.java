package dev.aziz.librarymanagementsystem.service;

import dev.aziz.librarymanagementsystem.entity.Book;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class BookSpecifications {

    public Specification<Book> titleContains(String title) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }

    public Specification<Book> authorContains(String author) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("author")),
                        "%" + author.toLowerCase() + "%"
                );
    }
}
