package com.toporkov.automobileapp.web.dto.error;

import java.time.LocalDateTime;

public class VehicleModelErrorResponse {

    private final String message;
    private final LocalDateTime time;

    public VehicleModelErrorResponse(String message, LocalDateTime time) {
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
