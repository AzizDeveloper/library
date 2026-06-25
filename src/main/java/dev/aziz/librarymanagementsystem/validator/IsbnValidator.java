package dev.aziz.librarymanagementsystem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<ValidIsbn, String> {

    private int[] allowedLengths;

    @Override
    public void initialize(ValidIsbn constraintAnnotation) {
        this.allowedLengths = constraintAnnotation.allowedLengths();
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null || isbn.isBlank()) {
            return true;
        }

        int length = isbn.length();
        for (int allowed : allowedLengths) {
            if (length == allowed) {
                return true;
            }
        }

        return false;
    }
}