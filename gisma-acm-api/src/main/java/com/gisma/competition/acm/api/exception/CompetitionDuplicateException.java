package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class CompetitionDuplicateException extends BaseException {

    private static final String MESSAGE = "Competition already exists, try different name.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public CompetitionDuplicateException() {
        super(MESSAGE, STATUS);
    }
}
