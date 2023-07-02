package com.example.postwebapp.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIExeption extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public BlogAPIExeption(String message, String message1, HttpStatus httpStatus) {
        super(message);
        this.message = message1;
        this.httpStatus = httpStatus;
    }

    public BlogAPIExeption(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
