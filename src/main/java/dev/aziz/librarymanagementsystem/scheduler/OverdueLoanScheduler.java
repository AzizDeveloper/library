package dev.aziz.librarymanagementsystem.scheduler;

import dev.aziz.librarymanagementsystem.entity.Status;
import dev.aziz.librarymanagementsystem.repository.BookLoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class OverdueLoanScheduler {

    private final BookLoanRepository bookLoanRepository;

    @Scheduled(cron = "0 26 15 * * *")
    @Transactional
    public void markOverdueLoans() {
        log.info("Checking for overdue loans...");

        int updatedCount = bookLoanRepository.markAllOverdueLoans(
                Status.OVERDUE, Status.ISSUED, LocalDate.now());

        log.info("Marked {} book loans as overdue", updatedCount);
    }
}