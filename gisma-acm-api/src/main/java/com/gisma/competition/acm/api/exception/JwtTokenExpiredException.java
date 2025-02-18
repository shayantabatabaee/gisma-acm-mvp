package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class JwtTokenExpiredException extends BaseException {

    private static final String MESSAGE = "JWT Token has expired.";
    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    public JwtTokenExpiredException() {
        super(MESSAGE, STATUS);
    }
}
