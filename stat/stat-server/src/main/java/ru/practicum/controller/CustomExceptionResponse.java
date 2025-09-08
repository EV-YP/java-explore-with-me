package ru.practicum.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CustomExceptionResponse {
    private HttpStatus status;
    private String message;
    private String stackTrace;
}
