package dev.aziz.librarymanagementsystem.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BookCopiesValidator.class)
@Documented
public @interface ValidBookCopies {

    String message() default "Available copies cannot be greater than total copies.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
