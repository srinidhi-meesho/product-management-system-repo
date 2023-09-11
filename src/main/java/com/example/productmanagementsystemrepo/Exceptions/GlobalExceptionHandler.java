package com.example.productmanagementsystemrepo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final String invalidRequest= "INVALID_REQUEST";
    private final String notFound = "NOT_FOUND";
    private final String internalServerError = "INTERNAL_SERVER_ERROR";
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllEx(Exception exception, WebRequest request) {
        ErrResponse errResponse = new ErrResponse();
        errResponse.setMessage(exception.getMessage());
        if(exception instanceof MethodArgumentNotValidException) {
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append(",");
            }
            for(ObjectError objectError : ((MethodArgumentNotValidException) exception).getBindingResult().getGlobalErrors()) {
                stringBuilder.append((objectError.getObjectName()+":" + objectError.getDefaultMessage()));
                stringBuilder.append(",");
            }
            errResponse.setMessage(stringBuilder.toString());
            errResponse.setCode(invalidRequest);
            return new ResponseEntity<>(errResponse, HttpStatus.BAD_REQUEST);
        } else if (exception instanceof NoSuchElementException) {
            errResponse.setCode(notFound);
            return new ResponseEntity<>(errResponse, HttpStatus.NOT_FOUND);
        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            errResponse.setCode(invalidRequest);
            return new ResponseEntity<>(errResponse, HttpStatus.BAD_REQUEST);
        } else if (exception instanceof HttpMessageNotReadableException) {
            errResponse.setCode(invalidRequest);
            return new ResponseEntity<>(errResponse, HttpStatus.BAD_REQUEST);
        } else if (exception instanceof IllegalArgumentException) {
            errResponse.setCode(invalidRequest);
            return new ResponseEntity<>(errResponse, HttpStatus.BAD_REQUEST);
        } else {
            errResponse.setMessage("we are having trouble at the moment, try after sometime");
            errResponse.setCode(internalServerError);
            return new ResponseEntity<>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
