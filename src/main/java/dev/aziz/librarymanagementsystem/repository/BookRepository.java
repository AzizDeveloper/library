package dev.aziz.librarymanagementsystem.repository;

import dev.aziz.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // TODO: Maybe write sql script manually.
    List<Book> findBooksByTitleAndAuthor(String title, String author);

}
