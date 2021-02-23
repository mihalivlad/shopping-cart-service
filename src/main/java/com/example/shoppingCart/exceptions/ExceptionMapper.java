package com.example.shoppingCart.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionMapper extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundError(UserNotFoundException ex, WebRequest request) {

        String errorMessageDescription = ex.getLocalizedMessage();

        if (errorMessageDescription == null) errorMessageDescription = ex.toString();

        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription, "The user was not found!");

        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    //    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
//    public ResponseEntity<Object> handleInvalidTransactionID(EmptyResultDataAccessException ex, WebRequest request) {
//
//        String errorMessageDescription = ex.getLocalizedMessage();
//
//        if(errorMessageDescription == null) errorMessageDescription = ex.toString();
//
//        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription, "The requested transaction was not found!");
//
//        return new ResponseEntity<>(
//                ex, new HttpHeaders(), HttpStatus.NOT_FOUND);
//    }
    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Invalid transaction ID")
    public void handleInvalidID() {
    }
}
