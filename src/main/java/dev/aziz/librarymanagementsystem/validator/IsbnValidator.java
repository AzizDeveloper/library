package dev.aziz.librarymanagementsystem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<ValidIsbn, String> {

    private int[] allowedLengths;

    @Override
    public void initialize(ValidIsbn constraintAnnotation) {
        // Grab the configuration from the annotation when the app starts
        this.allowedLengths = constraintAnnotation.allowedLengths();
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        // 1. Null Check:
        // Standard practice is to return 'true' for nulls in custom validators.
        // Let @NotBlank or @NotNull handle null/empty checks to avoid duplicate errors.
        if (isbn == null || isbn.isBlank()) {
            return true;
        }

        // 2. Length Check
        int length = isbn.length();
        for (int allowed : allowedLengths) {
            if (length == allowed) {
                return true;
            }
        }

        return false;
    }
}