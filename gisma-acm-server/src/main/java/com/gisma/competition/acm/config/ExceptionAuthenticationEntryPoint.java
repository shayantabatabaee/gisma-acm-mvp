package com.gisma.competition.acm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisma.competition.acm.api.dto.ErrorResponseDto;
import com.gisma.competition.acm.api.exception.UserNotAuthenticatedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class ExceptionAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(UserNotAuthenticatedException.STATUS.value());

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setErrorCode(UserNotAuthenticatedException.class.getSimpleName());
        errorResponseDto.setErrorMessage(UserNotAuthenticatedException.MESSAGE);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponseDto));
    }
}
