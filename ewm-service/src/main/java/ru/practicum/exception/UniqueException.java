package ru.practicum.exception;

public class UniqueException extends RuntimeException {
    public UniqueException(final String message) {
        super(message);
    }

    public UniqueException() {
        super();
    }
}
