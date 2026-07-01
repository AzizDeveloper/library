package dev.aziz.librarymanagementsystem.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BookCopiesValidator implements ConstraintValidator<ValidBookCopies, BookCopiesAware> {

    @Override
    public boolean isValid(BookCopiesAware dto,
                           ConstraintValidatorContext context) {

        if (dto == null) {
            return true;
        }

        if (dto.totalCopies() == null || dto.availableCopies() == null) {
            // Let @NotNull handle these cases.
            return true;
        }

        if (dto.availableCopies() <= dto.totalCopies()) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate(
                        "Available copies cannot be greater than total copies.")
                .addPropertyNode("availableCopies")
                .addConstraintViolation();

        return false;
    }

}