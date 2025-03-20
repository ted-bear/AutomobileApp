package com.toporkov.automobileapp.web.rest.handlers;

import com.toporkov.automobileapp.util.exception.ManagerDoNotHaveAccessException;
import com.toporkov.automobileapp.util.exception.UserNotFoundException;
import com.toporkov.automobileapp.util.exception.VehicleModelNotFoundException;
import com.toporkov.automobileapp.util.exception.VehicleNotDeletedException;
import com.toporkov.automobileapp.util.exception.VehicleNotFoundException;
import com.toporkov.automobileapp.util.exception.VehicleNotSavedException;
import com.toporkov.automobileapp.web.dto.error.ExceptionBody;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(VehicleNotSavedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleVehicleNotCreatedException(VehicleNotSavedException e) {
        return new ExceptionBody(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(VehicleNotDeletedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleVehicleNotDeleterException(VehicleNotDeletedException e) {
        return new ExceptionBody(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpStatus> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(VehicleModelNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleVehicleNotFoundException(VehicleModelNotFoundException e) {
        return new ExceptionBody("Vehicle Model not found", LocalDateTime.now());
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleVehicleNotFoundException(VehicleNotFoundException e) {
        return new ExceptionBody("Vehicle not found", LocalDateTime.now());
    }

    @ExceptionHandler(ManagerDoNotHaveAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleManagerDoNotHaveAccessException(ManagerDoNotHaveAccessException e) {
        return new ExceptionBody("Manager do not have access", LocalDateTime.now());
    }

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handlePropertyReferenceException(PropertyReferenceException e) {
        return new ExceptionBody(e.getMessage(), LocalDateTime.now());
    }
}
