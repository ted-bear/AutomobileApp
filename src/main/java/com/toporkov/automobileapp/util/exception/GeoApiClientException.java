package com.toporkov.automobileapp.util.exception;

public class GeoApiClientException extends RuntimeException {
    public GeoApiClientException(String message) {
        super(message);
    }

    public GeoApiClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
