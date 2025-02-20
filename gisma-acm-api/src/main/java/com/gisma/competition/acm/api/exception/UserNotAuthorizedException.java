package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class UserNotAuthorizedException extends BaseRuntimeException {

    public static final HttpStatus STATUS = HttpStatus.FORBIDDEN;
    public static String MESSAGE = "User with this role is not authorized";

    public UserNotAuthorizedException() {
        super(MESSAGE, STATUS);
    }
}
