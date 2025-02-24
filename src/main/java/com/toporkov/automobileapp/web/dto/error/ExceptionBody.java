package com.toporkov.automobileapp.web.dto.error;

import java.time.LocalDateTime;
import java.util.Map;

public class ExceptionBody {

    private String message;
    private Map<String, String> errors;
    private LocalDateTime time;

    public ExceptionBody(String message) {
        this.message = message;
    }

    public ExceptionBody(String message, LocalDateTime time) {
        this.message = message;
        this.time = time;
    }

    public ExceptionBody(String message, Map<String, String> errors, LocalDateTime time) {
        this.message = message;
        this.errors = errors;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
