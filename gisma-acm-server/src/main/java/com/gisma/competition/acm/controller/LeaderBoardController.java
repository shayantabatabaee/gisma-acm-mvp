package com.gisma.competition.acm.controller;

import com.gisma.competition.acm.api.dto.SubmissionResponseDto;
import com.gisma.competition.acm.api.dto.UserSubmissionsResponseDto;
import com.gisma.competition.acm.api.exception.*;
import com.gisma.competition.acm.api.facade.LeaderBoardFacade;
import com.gisma.competition.acm.service.LeaderBoardService;
import com.gisma.competition.acm.service.UserSubmissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(LeaderBoardFacade.BASE_URL)
@AllArgsConstructor
public class LeaderBoardController implements LeaderBoardFacade {

    private final LeaderBoardService leaderBoardService;
    private final UserSubmissionService userSubmissionService;

    @Override
    @PreAuthorize("hasRole('STANDARD')")
    public ResponseEntity<List<SubmissionResponseDto>> winners(int competitionId) throws ValidationException,
            JwtTokenExpiredException, JwtTokenException, CompetitionNotExistException {
        return ResponseEntity.ok(leaderBoardService.getSuccessSubmissionSortByCpuTime(competitionId));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserSubmissionsResponseDto> getUserSubmissions(int userId) throws ValidationException, JwtTokenExpiredException, JwtTokenException, UserNotAuthorizedException, UserIdNotExistsException {
        return ResponseEntity.ok(userSubmissionService.getUserSubmissions(userId));
    }

}
