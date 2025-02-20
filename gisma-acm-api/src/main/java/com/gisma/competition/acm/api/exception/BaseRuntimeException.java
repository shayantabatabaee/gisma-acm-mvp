package com.gisma.competition.acm.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseRuntimeException extends RuntimeException {

    private final HttpStatus status;

    public BaseRuntimeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BaseRuntimeException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
}
