package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class EvmErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(final RuntimeException e) {
        log.error("500 {}", e.getMessage(), e);
        LocalDateTime timestamp = LocalDateTime.now();
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), timestamp);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.error("400 {}", e.getMessage(), e);
        LocalDateTime timestamp = LocalDateTime.now();
        return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), timestamp);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.error("404 {}", e.getMessage(), e);
        LocalDateTime timestamp = LocalDateTime.now();
        return new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), timestamp);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(final ConflictException e) {
        log.error("409 {}", e.getMessage(), e);
        LocalDateTime timestamp = LocalDateTime.now();
        return new ErrorResponse(HttpStatus.CONFLICT, e.getMessage(), timestamp);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingPathVariableException(final MissingServletRequestParameterException e) {
        log.error("400 {}", e.getMessage(), e);
        LocalDateTime timestamp = LocalDateTime.now();
        return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), timestamp);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handSQLException(final DataIntegrityViolationException e) {
        log.error("409 {}", e.getMessage(), e);
        LocalDateTime timestamp = LocalDateTime.now();
        return new ErrorResponse(HttpStatus.CONFLICT, e.getMessage(), timestamp);
    }
}
