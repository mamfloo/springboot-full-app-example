package com.library.demo.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ErrorDto {

    private HttpStatus status;
    private String message;
    private long timeStamp;

    public ErrorDto(Exception exception, HttpStatus status){
        this(exception, status, null);
    }
    public ErrorDto(Exception exception, HttpStatus status, String errorMessage){
        this.status = status;
        this.message = errorMessage == null ? exception.getMessage() : errorMessage;
        this.timeStamp = System.currentTimeMillis();
    }

    public ResponseEntity<ErrorDto> toResponseEntity(){
        return new ResponseEntity<>(this, status);
    }
}
