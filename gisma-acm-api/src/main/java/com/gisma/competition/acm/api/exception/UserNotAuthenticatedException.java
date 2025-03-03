package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class UserNotAuthenticatedException extends BaseRuntimeException {

    public static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;
    public static String MESSAGE = "User is not authenticated, please login first.";

    public UserNotAuthenticatedException() {
        super(MESSAGE, STATUS);
    }
}
