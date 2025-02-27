package com.gisma.competition.acm.controller;

import com.gisma.competition.acm.api.dto.*;
import com.gisma.competition.acm.api.exception.*;
import com.gisma.competition.acm.api.facade.CompetitionFacade;
import com.gisma.competition.acm.service.CompetitionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(CompetitionFacade.BASE_URL)
@AllArgsConstructor
public class CompetitionController implements CompetitionFacade {

    private final CompetitionService competitionService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateCompetitionResponseDto> create(CreateCompetitionRequestDto createCompetitionRequestDto)
            throws CompetitionDuplicateException {
        return ResponseEntity.status(201).body(competitionService.createCompetitionWithTemplateAndTestCases(createCompetitionRequestDto));
    }

    @Override
    @PreAuthorize("hasRole('STANDARD')")
    public ResponseEntity<SubmitCompetitionResponseDto> submit(int competitionId, SubmitCompetitionRequestDto submitCompetitionRequestDto)
            throws CompilationException, CompetitionNotExistException, CompetitionFinishedException, CompetitionNotStartedException {
        return ResponseEntity.ok(competitionService.submitCompetition(competitionId, submitCompetitionRequestDto));
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
