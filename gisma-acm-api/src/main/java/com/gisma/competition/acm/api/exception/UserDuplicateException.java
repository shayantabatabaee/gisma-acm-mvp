package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class UserDuplicateException extends BaseException {

    private static final String MESSAGE = "User already exists, try different information.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public UserDuplicateException() {
        super(MESSAGE, STATUS);
    }
}
