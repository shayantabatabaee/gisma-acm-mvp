package com.gisma.competition.acm.api.facade;

import com.gisma.competition.acm.api.dto.SubmissionResponseDto;
import com.gisma.competition.acm.api.dto.UserSubmissionsResponseDto;
import com.gisma.competition.acm.api.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface LeaderBoardFacade {
    String BASE_URL = "/api/leaderboard";

    @GetMapping(value = "/winners/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<SubmissionResponseDto>> winners(@PathVariable("id") int competitionId) throws ValidationException,
            JwtTokenExpiredException,
            JwtTokenException, CompetitionNotExistException;


    @GetMapping(value = "/user-submissions/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<UserSubmissionsResponseDto> getUserSubmissions(@PathVariable("id") int userId) throws ValidationException,
            JwtTokenExpiredException,
            JwtTokenException, UserNotAuthorizedException, UserIdNotExistsException;
}
