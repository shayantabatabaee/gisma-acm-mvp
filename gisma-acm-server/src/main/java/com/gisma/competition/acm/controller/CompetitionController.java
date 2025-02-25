package com.gisma.competition.acm.controller;

import com.gisma.competition.acm.api.dto.CreateCompetitionRequestDto;
import com.gisma.competition.acm.api.dto.CreateCompetitionResponseDto;
import com.gisma.competition.acm.api.dto.SubmitCompetitionRequestDto;
import com.gisma.competition.acm.api.dto.SubmitCompetitionResponseDto;
import com.gisma.competition.acm.api.exception.*;
import com.gisma.competition.acm.api.facade.CompetitionFacade;
import com.gisma.competition.acm.persistence.assembler.CompetitionAssembler;
import com.gisma.competition.acm.persistence.entity.Competition;
import com.gisma.competition.acm.service.CompetitionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CompetitionFacade.BASE_URL)
@AllArgsConstructor
public class CompetitionController implements CompetitionFacade {

    private final CompetitionService competitionService;
    private final CompetitionAssembler competitionAssembler;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateCompetitionResponseDto> create(CreateCompetitionRequestDto createCompetitionRequestDto)
            throws CompetitionDuplicateException {
        Competition competition = competitionService.createCompetitionWithTemplateAndTestCases(createCompetitionRequestDto);
        return ResponseEntity.status(201).body(competitionAssembler.toCreateCompetitionResponseDto(competition));
    }

    @Override
    @PreAuthorize("hasRole('STANDARD')")
    public ResponseEntity<SubmitCompetitionResponseDto> submit(int competitionId, SubmitCompetitionRequestDto submitCompetitionRequestDto)
            throws CompilationException, CompetitionNotExistException, CompetitionFinishedException, CompetitionNotStartedException {
        return ResponseEntity.ok(competitionService.submitCompetition(competitionId, submitCompetitionRequestDto));
    }
}
