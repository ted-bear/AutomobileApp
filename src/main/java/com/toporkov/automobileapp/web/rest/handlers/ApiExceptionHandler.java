package com.toporkov.automobileapp.web.rest.handlers;

import com.toporkov.automobileapp.util.exception.GeoApiClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(GeoApiClientException.class)
    public ResponseEntity<String> handleGeoCodingException(GeoApiClientException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(ex.getMessage());
    }
}
