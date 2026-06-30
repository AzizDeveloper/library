package dev.aziz.librarymanagementsystem.exception;

public class ReaderNotFoundException extends RuntimeException {
    public ReaderNotFoundException(String message) {
        super(message);
    }
}