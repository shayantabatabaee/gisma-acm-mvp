package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class CompetitionPoolBusyException extends BaseException {

    private static final String MESSAGE = "Competition pool is busy, try again later.";
    private static final HttpStatus STATUS = HttpStatus.SERVICE_UNAVAILABLE;

    public CompetitionPoolBusyException() {
        super(MESSAGE, STATUS);
    }
}
