package dev.aziz.librarymanagementsystem.controller;

import dev.aziz.librarymanagementsystem.dto.BookLoanRequest;
import dev.aziz.librarymanagementsystem.dto.BookLoanResponse;
import dev.aziz.librarymanagementsystem.service.BookLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/loans")
@RestController
@RequiredArgsConstructor
public class BookLoanController {

    private final BookLoanService bookLoanService;

    @PostMapping
    public BookLoanResponse createBookLoan(@RequestBody @Valid BookLoanRequest bookLoanRequest) {
        return bookLoanService.createBookLoan(bookLoanRequest);
    }

    @PutMapping("/{id}/return")
    public BookLoanResponse returnBook(@PathVariable Long id) {
        return bookLoanService.returnBook(id);
    }

    @GetMapping("/reader/{readerId}")
    public List<BookLoanResponse> readBookLoan(@PathVariable Long readerId) {
        return bookLoanService.getBookLoansByReaderId(readerId);
    }

    @GetMapping("/overdue")
    public List<BookLoanResponse> overdueBookLoan() {
        return bookLoanService.getOverdueBookLoans();
    }

}
