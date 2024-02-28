package com.library.demo.Exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> genericExceptionHandler(Exception e) {
        return new ErrorDto(e, HttpStatus.INTERNAL_SERVER_ERROR).toResponseEntity();
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<ErrorDto> resourceNoSuchElementException(Exception e) {
        return new ErrorDto(e, HttpStatus.NOT_FOUND, "No element found").toResponseEntity();
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<ErrorDto> dataIntegrationViolation(Exception e){
        return new ErrorDto(e, HttpStatus.BAD_REQUEST, "Error while Inserting data inside the db").toResponseEntity();
    }

}
