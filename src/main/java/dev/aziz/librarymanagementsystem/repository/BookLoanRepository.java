package dev.aziz.librarymanagementsystem.repository;

import dev.aziz.librarymanagementsystem.entity.BookLoan;
import dev.aziz.librarymanagementsystem.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {

    List<BookLoan> findBookLoanByReaderId(Long readerId);

    @Query("SELECT bl FROM BookLoan bl " +
            "JOIN FETCH bl.book " +
            "JOIN FETCH bl.reader " +
            "WHERE bl.status = :status")
    List<BookLoan> findByStatusOverdueWithFetch(@Param("status") Status status);

    @Modifying
    @Query("UPDATE BookLoan bl SET bl.status = :newStatus " +
            "WHERE bl.status = :currentStatus AND bl.dueDate < :today AND bl.returnDate IS NULL")
    int markAllOverdueLoans(@Param("newStatus") Status newStatus,
                            @Param("currentStatus") Status currentStatus,
                            @Param("today") LocalDate today);

}
