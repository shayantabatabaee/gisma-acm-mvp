package com.gisma.competition.acm.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ValidationException extends BaseRuntimeException {

    public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
    public static final String MESSAGE = "Validation exception occurred. " +
            "Please refer to errorDetail section for more information.";
    private final Map<String, Object> details;

    public ValidationException(Map<String, Object> details) {
        super(MESSAGE, STATUS);
        this.details = details;
    }
}
