package dev.aziz.librarymanagementsystem.controller;

import dev.aziz.librarymanagementsystem.dto.BookLoanRequest;
import dev.aziz.librarymanagementsystem.dto.BookLoanResponse;
import dev.aziz.librarymanagementsystem.service.BookLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BookLoanResponse> createBookLoan(@RequestBody @Valid BookLoanRequest bookLoanRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookLoanService.createBookLoan(bookLoanRequest));
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<BookLoanResponse> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookLoanService.returnBook(id));
    }

    @GetMapping("/reader/{readerId}")
    public ResponseEntity<List<BookLoanResponse>> getBookLoansByReader(@PathVariable Long readerId) {
        return ResponseEntity.ok(bookLoanService.getBookLoansByReaderId(readerId));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<BookLoanResponse>> overdueBookLoan() {
        return ResponseEntity.ok(bookLoanService.getOverdueBookLoans());
    }

}
