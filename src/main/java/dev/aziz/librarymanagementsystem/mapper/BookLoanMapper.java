package dev.aziz.librarymanagementsystem.mapper;

import dev.aziz.librarymanagementsystem.dto.BookLoanResponse;
import dev.aziz.librarymanagementsystem.entity.BookLoan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookLoanMapper {

    private final BookMapper bookMapper;
    private final ReaderMapper readerMapper;

    public BookLoanResponse toBookLoanResponse(BookLoan bookLoan) {
        return new BookLoanResponse(
                bookLoan.getId(),
                bookLoan.getLoanDate(),
                bookLoan.getDueDate(),
                bookLoan.getReturnDate(),
                bookLoan.getStatus(),
                readerMapper.toReaderResponseDto(bookLoan.getReader()),
                bookMapper.toBookResponseDto(bookLoan.getBook())
        );
    }

    public List<BookLoanResponse> toBookLoanResponseList(List<BookLoan> bookLoans) {
        if (bookLoans == null || bookLoans.isEmpty()) {
            return List.of();
        }
        List<BookLoanResponse> bookResponseDtos = new ArrayList<>(bookLoans.size());

        for (BookLoan bookLoan : bookLoans) {
            bookResponseDtos.add(toBookLoanResponse(bookLoan));
        }
        return bookResponseDtos;
    }

}
