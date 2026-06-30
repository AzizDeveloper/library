package dev.aziz.librarymanagementsystem.repository;

import dev.aziz.librarymanagementsystem.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = """
      SELECT t FROM Token t INNER JOIN Reader r\s
      on t.reader.id = r.id\s
      where r.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByReader(Long id);

    Optional<Token> findByToken(String token);

}
