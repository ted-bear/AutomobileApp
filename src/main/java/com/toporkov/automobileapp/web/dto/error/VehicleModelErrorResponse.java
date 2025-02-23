package com.toporkov.automobileapp.web.dto.error;

import java.time.LocalDateTime;

public record VehicleModelErrorResponse(String message, LocalDateTime time) {

}
