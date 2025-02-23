package com.gisma.competition.acm.api.exception;

import org.springframework.http.HttpStatus;

public class CompilationException extends BaseException {

    private static final String MESSAGE = "Compilation of submission failed. " +
            "Please refer to errorDetail section for more information.";
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public CompilationException() {
        super(MESSAGE, STATUS);
    }

    public CompilationException(Throwable cause) {
        super(MESSAGE, cause, STATUS);
    }
}
