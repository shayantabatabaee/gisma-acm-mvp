package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class CompetitionFinishedException extends BaseException {

    private static final String MESSAGE = "Competition finished. Submission is not allowed.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public CompetitionFinishedException() {
        super(MESSAGE, STATUS);
    }
}
