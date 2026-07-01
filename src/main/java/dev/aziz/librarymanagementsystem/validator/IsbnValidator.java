package dev.aziz.librarymanagementsystem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<ValidIsbn, String> {

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null || isbn.isBlank()) {
            return true;
        }

        String cleanIsbn = isbn.replaceAll("[\\s-]", "").toUpperCase();

        if (cleanIsbn.length() == 10) {
            return isValidISBN10(cleanIsbn);
        } else if (cleanIsbn.length() == 13) {
            return isValidISBN13(cleanIsbn);
        }

        return false;
    }

    private boolean isValidISBN10(String isbn) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            char c = isbn.charAt(i);
            int digit;

            if (i == 9 && c == 'X') {
                digit = 10;
            } else if (Character.isDigit(c)) {
                digit = c - '0';
            } else {
                return false;
            }

            sum += (10 - i) * digit;
        }
        return sum % 11 == 0;
    }

    private boolean isValidISBN13(String isbn) {
        if (!isbn.matches("\\d+")) return false;

        int sum = 0;
        for (int i = 0; i < 13; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0 ? 1 : 3) * digit;
        }
        return sum % 10 == 0;
    }

}