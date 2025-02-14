package com.toporkov.automobileapp.web.dto.error;

import java.time.LocalDateTime;

public class VehicleErrorResponse {

    private final String message;
    private final LocalDateTime time;

    public VehicleErrorResponse(String message, LocalDateTime time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
