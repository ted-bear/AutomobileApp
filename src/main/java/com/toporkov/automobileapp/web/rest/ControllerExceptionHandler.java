package com.toporkov.automobileapp.web.rest;

import com.toporkov.automobileapp.util.exception.VehicleModelNotFoundException;
import com.toporkov.automobileapp.util.exception.VehicleNotCreatedException;
import com.toporkov.automobileapp.util.exception.VehicleNotFoundException;
import com.toporkov.automobileapp.web.dto.error.VehicleErrorResponse;
import com.toporkov.automobileapp.web.dto.error.VehicleModelErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(VehicleNotCreatedException.class)
    public ResponseEntity<VehicleModelErrorResponse> handleVehicleNotCreatedException(VehicleNotCreatedException e) {
        return new ResponseEntity<>(
                new VehicleModelErrorResponse(e.getMessage(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(VehicleModelNotFoundException.class)
    public ResponseEntity<VehicleModelErrorResponse> handleVehicleNotFoundException(VehicleModelNotFoundException e) {
        return new ResponseEntity<>(
                new VehicleModelErrorResponse("Vehicle Model not found", LocalDateTime.now()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<VehicleErrorResponse> handleVehicleNotFoundException(VehicleNotFoundException e) {
        return new ResponseEntity<>(
                new VehicleErrorResponse("Vehicle not found", LocalDateTime.now()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherException(Exception e) {
        return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}
