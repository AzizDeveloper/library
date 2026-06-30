package dev.aziz.librarymanagementsystem.repository;

import dev.aziz.librarymanagementsystem.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    boolean existsByEmail(String email);

    Optional<Reader> findByEmailEqualsIgnoreCase(String email);

}
