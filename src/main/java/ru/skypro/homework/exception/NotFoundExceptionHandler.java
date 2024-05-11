package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(AdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAdNotFoundException(AdNotFoundException e) {
        return e.getMessage();
    }

}
