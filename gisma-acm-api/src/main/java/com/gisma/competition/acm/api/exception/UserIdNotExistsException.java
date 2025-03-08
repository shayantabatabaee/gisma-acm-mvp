package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class UserIdNotExistsException extends BaseException{

    private static final String MESSAGE = "UserId does not exist.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public UserIdNotExistsException() {
        super(MESSAGE, STATUS);
    }
}
