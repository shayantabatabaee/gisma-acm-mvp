package com.gisma.competition.acm.errorhandler;

import com.gisma.competition.acm.api.dto.ErrorResponseDto;
import com.gisma.competition.acm.api.exception.BaseException;
import com.gisma.competition.acm.api.exception.BaseRuntimeException;
import com.gisma.competition.acm.api.exception.UserNotAuthorizedException;
import com.gisma.competition.acm.api.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleValidationException(MethodArgumentNotValidException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setErrorCode(ValidationException.class.getSimpleName());
        errorResponseDto.setErrorMessage(ValidationException.MESSAGE);
        Map<String, String> errorDetails = ex.getFieldErrors().stream().
                collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        errorResponseDto.setErrorDetails(errorDetails);
        return errorResponseDto;
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setErrorCode(UserNotAuthorizedException.class.getSimpleName());
        errorResponseDto.setErrorMessage(UserNotAuthorizedException.MESSAGE);
        return new ResponseEntity<>(errorResponseDto, UserNotAuthorizedException.STATUS);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDto> handleBaseException(BaseException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setErrorCode(ex.getClass().getSimpleName());
        errorResponseDto.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, ex.getStatus());
    }

    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleBaseRuntimeException(BaseRuntimeException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setErrorCode(ex.getClass().getSimpleName());
        errorResponseDto.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, ex.getStatus());
    }
}
