package com.gisma.competition.acm.api.facade;

import com.gisma.competition.acm.api.dto.LoginRequestDto;
import com.gisma.competition.acm.api.dto.LoginResponseDto;
import com.gisma.competition.acm.api.dto.SignupRequestDto;
import com.gisma.competition.acm.api.exception.UserDuplicateException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface AuthenticationFacade {
    String BASE_URL = "/api/auth";

    @PostMapping(value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) throws UserDuplicateException;

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto);
}
