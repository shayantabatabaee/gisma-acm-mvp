package com.gisma.competition.acm.api.facade;

import com.gisma.competition.acm.api.dto.CreateCompetitionRequestDto;
import com.gisma.competition.acm.api.dto.CreateCompetitionResponseDto;
import com.gisma.competition.acm.api.dto.SubmitCompetitionRequestDto;
import com.gisma.competition.acm.api.dto.SubmitCompetitionResponseDto;
import com.gisma.competition.acm.api.exception.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface CompetitionFacade {
    String BASE_URL = "/api/competition";

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<CreateCompetitionResponseDto> create(@Valid @RequestBody CreateCompetitionRequestDto createCompetitionRequestDto) throws ValidationException,
            JwtTokenExpiredException,
            JwtTokenException,
            UserNotAuthorizedException, CompetitionDuplicateException;

    @PostMapping(value = "/{id}/submit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<SubmitCompetitionResponseDto> submit(@PathVariable("id") int competitionId,
                                                        @Valid @RequestBody SubmitCompetitionRequestDto submitCompetitionRequestDto)
            throws ValidationException, JwtTokenExpiredException, JwtTokenException,
            CompilationException, CompetitionNotExistException, CompetitionFinishedException, CompetitionNotStartedException;

}
