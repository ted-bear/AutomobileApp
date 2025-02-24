package com.toporkov.automobileapp.web.dto.error;

import java.time.LocalDateTime;

public record UserNotFoundErrorResponse(String message, LocalDateTime time) {

}
