package com.bnp.kingmainandhiskingdom.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ApiError> handlePersonNotFoundException(PersonNotFoundException ex) {
        ApiError personNotFound = new ApiError("Person not found", HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(personNotFound);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiError apiError = new ApiError("Invalid arguments", status.value(), ex.getMessage());
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<ValidationError> validationErrors = apiError.getValidationErrors();
        for (FieldError fieldError : fieldErrors) {
            ValidationError validationError = new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
            validationErrors.add(validationError);
        }
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
