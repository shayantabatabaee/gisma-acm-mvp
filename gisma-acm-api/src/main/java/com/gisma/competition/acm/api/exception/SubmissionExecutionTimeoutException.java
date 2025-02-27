package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class SubmissionExecutionTimeoutException extends BaseException {

    private static final String MESSAGE = "Submission takes more than %d seconds.";
    private static final HttpStatus STATUS = HttpStatus.GATEWAY_TIMEOUT;

    public SubmissionExecutionTimeoutException(int timeout) {
        super(String.format(MESSAGE, timeout), STATUS);
    }

}
