package dev.aziz.librarymanagementsystem.repository;

import dev.aziz.librarymanagementsystem.entity.BookLoan;
import dev.aziz.librarymanagementsystem.entity.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Testcontainers
public class BookLoanRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:17");

    @Autowired
    private BookLoanRepository underTest;

    @Test
    void findByStatusOverdueWithFetchTest() {
        // given
        Status status = Status.OVERDUE;
        // when
        List<BookLoan> actual = underTest.findByStatusOverdueWithFetch(status);
        // then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void markAllOverdueLoansTest() {
        // given
        Status newStatus = Status.OVERDUE;
        Status currentStatus = Status.ISSUED;
        LocalDate now = LocalDate.now();
        // when
        int actual = underTest.markAllOverdueLoans(newStatus, currentStatus, now);
        // then
        assertThat(actual).isEqualTo(1);
    }

}
