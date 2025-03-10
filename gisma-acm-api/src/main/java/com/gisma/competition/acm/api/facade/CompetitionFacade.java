package com.gisma.competition.acm.api.facade;

import com.gisma.competition.acm.api.dto.*;
import com.gisma.competition.acm.api.exception.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            CompilationException, CompetitionNotExistException, CompetitionFinishedException, CompetitionNotStartedException, CompetitionPoolBusyException, SubmissionExecutionTimeoutException;


    @GetMapping(value = "/{id}/info",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<CompetitionInfoResponseDto> competitionInfo(@PathVariable("id") int competitionId)
            throws ValidationException, JwtTokenExpiredException, JwtTokenException, CompetitionNotExistException;

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<CompetitionInfoDto>> allCompetitions()
            throws ValidationException, JwtTokenExpiredException, JwtTokenException;

    @GetMapping(value = "/{id}/testcases",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<TestCaseDto>> competitionTestCases(@PathVariable("id") int competitionId)
            throws ValidationException, JwtTokenExpiredException, JwtTokenException, CompetitionNotExistException;

    @GetMapping(value = "/{id}/template",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<CompetitionTemplateResponseDto> competitionTemplate(@PathVariable("id") int competitionId)
            throws ValidationException, JwtTokenExpiredException, JwtTokenException, CompetitionNotExistException;

}
