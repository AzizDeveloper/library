package dev.aziz.librarymanagementsystem.repository;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Testcontainers
public class ReaderRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:17");

    @Autowired
    private ReaderRepository underTest;

    @ParameterizedTest
    @CsvSource({
            "bob@test.com, true",
            "alice@test.com, true",
            "amigo@test.com, false",
            "martin@test.com, false"
    })
    void existsByEmailTest(String email, boolean expected) {
        // when
        boolean actual = underTest.existsByEmail(email);
        // then
        assertThat(actual).isEqualTo(expected);
    }

}
