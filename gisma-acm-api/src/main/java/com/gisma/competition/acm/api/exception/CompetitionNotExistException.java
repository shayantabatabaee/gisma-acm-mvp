package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class CompetitionNotExistException extends BaseException {

    private static final String MESSAGE = "Competition does not exist with id %d.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public CompetitionNotExistException(int competitionId) {
        super(String.format(MESSAGE, competitionId), STATUS);
    }
}
