package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class JwtTokenExpiredException extends BaseRuntimeException {

    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    public JwtTokenExpiredException(String message, Throwable cause) {
        super(message, cause, STATUS);
    }
}
