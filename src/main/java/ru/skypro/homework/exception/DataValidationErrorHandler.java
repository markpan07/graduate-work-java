package ru.skypro.homework.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RestControllerAdvice
public class DataValidationErrorHandler extends RuntimeException {


    //TODO: написать обработку исключения с некорреткным паролем PasswordIsNotCorrectException

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ValidationErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        final List<Violation> violationsList = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage())
                )
                .collect(Collectors.toList());

        return new ValidationErrorResponse(violationsList);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<Violation> violationsList = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violationsList);
    }
}
