package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class BadCredentialException extends BaseException {

    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;
    public static final String MESSAGE = "Username or Password is incorrect.";

    public BadCredentialException(Throwable cause) {
        super(MESSAGE, cause, STATUS);
    }
}
