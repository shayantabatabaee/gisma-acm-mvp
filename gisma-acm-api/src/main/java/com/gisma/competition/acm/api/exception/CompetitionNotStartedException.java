package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class CompetitionNotStartedException extends BaseException {

    private static final String MESSAGE = "Competition not started. Please try again later.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public CompetitionNotStartedException() {
        super(MESSAGE, STATUS);
    }
}
