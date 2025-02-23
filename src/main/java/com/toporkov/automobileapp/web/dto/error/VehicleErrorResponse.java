package com.toporkov.automobileapp.web.dto.error;

import java.time.LocalDateTime;

public record VehicleErrorResponse(String message, LocalDateTime time) {

}
