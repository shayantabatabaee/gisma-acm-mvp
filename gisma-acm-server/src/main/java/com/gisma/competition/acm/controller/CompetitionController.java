package com.gisma.competition.acm.controller;

import com.gisma.competition.acm.api.dto.*;
import com.gisma.competition.acm.api.exception.*;
import com.gisma.competition.acm.api.facade.CompetitionFacade;
import com.gisma.competition.acm.persistence.entity.User;
import com.gisma.competition.acm.service.CompetitionService;
import com.gisma.competition.acm.service.LeaderBoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(CompetitionFacade.BASE_URL)
@AllArgsConstructor
public class CompetitionController implements CompetitionFacade {

    private final CompetitionService competitionService;
    private final LeaderBoardService leaderBoardService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateCompetitionResponseDto> create(CreateCompetitionRequestDto createCompetitionRequestDto)
            throws CompetitionDuplicateException {
        return ResponseEntity.status(201).body(competitionService.createCompetitionWithTemplateAndTestCases(createCompetitionRequestDto));
    }

    @Override
    @PreAuthorize("hasRole('STANDARD')")
    public ResponseEntity<SubmitCompetitionResponseDto> submit(int competitionId, SubmitCompetitionRequestDto submitCompetitionRequestDto)
            throws CompilationException, CompetitionNotExistException, CompetitionFinishedException, CompetitionNotStartedException, CompetitionPoolBusyException, SubmissionExecutionTimeoutException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = ((User) authentication.getPrincipal()).getUserId();
        SubmitCompetitionResponseDto responseDto = competitionService.submitCompetition(competitionId, submitCompetitionRequestDto);
        leaderBoardService.saveOrUpdateLeaderBoard(responseDto, userId);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    @PreAuthorize("hasRole('STANDARD')")
    public ResponseEntity<CompetitionInfoResponseDto> competitionInfo(int competitionId) throws ValidationException, JwtTokenExpiredException, JwtTokenException, CompetitionNotExistException {
        return ResponseEntity.ok(competitionService.getCompetitionInfo(competitionId));
    }

    @Override
    @PreAuthorize("hasRole('STANDARD')")
    public ResponseEntity<List<CompetitionInfoDto>> allCompetitions() throws ValidationException, JwtTokenExpiredException, JwtTokenException {
        return ResponseEntity.ok(competitionService.getAllCompetitionInfo());
    }

    @Override
    @PreAuthorize("hasRole('STANDARD')")
    public ResponseEntity<List<TestCaseDto>> competitionTestCases(int competitionId) throws ValidationException, JwtTokenExpiredException, JwtTokenException, CompetitionNotExistException {
        return ResponseEntity.ok(competitionService.getAllTestCases(competitionId));
    }

    @Override
    @PreAuthorize("hasRole('STANDARD')")
    public ResponseEntity<CompetitionTemplateResponseDto> competitionTemplate(int competitionId) throws ValidationException, JwtTokenExpiredException, JwtTokenException, CompetitionNotExistException {
        return ResponseEntity.ok(competitionService.getCompetitionTemplate(competitionId));
    }
}
