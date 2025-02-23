package com.gisma.competition.acm.api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public class BaseRuntimeException extends RuntimeException {

    private final HttpStatus status;
    private Map<String, String> details;

    public BaseRuntimeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BaseRuntimeException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
}
