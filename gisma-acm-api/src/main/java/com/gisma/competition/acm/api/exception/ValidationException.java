package com.gisma.competition.acm.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationException extends BaseRuntimeException {

    public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
    public static final String MESSAGE = "Validation exception occurred. " +
            "Please refer to errorDetail section for more information.";

    public ValidationException() {
        super(MESSAGE, STATUS);
    }
}
