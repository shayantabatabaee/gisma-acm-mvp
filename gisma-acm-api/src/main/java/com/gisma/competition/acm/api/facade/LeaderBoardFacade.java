package com.gisma.competition.acm.api.facade;

import com.gisma.competition.acm.api.dto.SubmissionResponseDto;
import com.gisma.competition.acm.api.exception.CompetitionNotExistException;
import com.gisma.competition.acm.api.exception.JwtTokenException;
import com.gisma.competition.acm.api.exception.JwtTokenExpiredException;
import com.gisma.competition.acm.api.exception.ValidationException;
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
}
