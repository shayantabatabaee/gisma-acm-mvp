package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class JwtTokenException extends BaseRuntimeException {

    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public JwtTokenException(String message, Throwable cause) {
        super(message, cause, STATUS);
    }
}
