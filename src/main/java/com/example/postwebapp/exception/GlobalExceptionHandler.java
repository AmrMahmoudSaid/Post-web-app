package com.example.postwebapp.exception;

import com.example.postwebapp.dtos.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handelResponseNotFoundException(ResourceNotFoundException exception , WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date() , exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlogAPIExeption.class)
    public ResponseEntity<ErrorDetails> handelBlogAPIExeption(BlogAPIExeption exception , WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date() , exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //global handle
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handelExeption(Exception exception , WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date() , exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
