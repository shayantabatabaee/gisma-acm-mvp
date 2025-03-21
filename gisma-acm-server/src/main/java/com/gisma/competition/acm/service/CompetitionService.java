package com.gisma.competition.acm.service;

import com.gisma.competition.acm.api.dto.*;
import com.gisma.competition.acm.api.exception.*;
import com.gisma.competition.acm.assembler.CompetitionAssembler;
import com.gisma.competition.acm.executor.assembler.TestCaseAssembler;
import com.gisma.competition.acm.persistence.entity.Competition;
import com.gisma.competition.acm.persistence.entity.Template;
import com.gisma.competition.acm.persistence.entity.TestCase;
import com.gisma.competition.acm.persistence.repository.CompetitionRepository;
import com.gisma.competition.acm.persistence.repository.TemplateRepository;
import com.gisma.competition.acm.persistence.repository.TestCaseRepository;
import com.gisma.competition.acm.util.TestCaseExecutorProcessManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompetitionService {

    private final CompetitionAssembler competitionAssembler;
    private final TestCaseAssembler testCaseAssembler;
    private final CompetitionRepository competitionRepository;
    private final TemplateRepository templateRepository;
    private final TestCaseRepository testCaseRepository;
    private final TestCaseExecutorProcessManager testCaseExecutor;

    @Transactional
    public CreateCompetitionResponseDto createCompetitionWithTemplateAndTestCases(CreateCompetitionRequestDto createCompetitionRequestDto) throws CompetitionDuplicateException {
        if (competitionRepository.getCompetitionByName(createCompetitionRequestDto.getName()).isPresent()) {
            throw new CompetitionDuplicateException();
        }
        Competition competition = competitionAssembler.toCompetitionModel(createCompetitionRequestDto);
        competitionRepository.save(competition);

        Template template = competitionAssembler.toTemplateModel(createCompetitionRequestDto.getTemplate());
        template.setCompetition(competition);
        templateRepository.save(template);

        List<TestCase> testCases = competitionAssembler.toTestCasesModel(createCompetitionRequestDto.getTestCases());
        testCases.forEach(testCase -> testCase.setCompetition(competition));
        testCaseRepository.saveAll(testCases);
        return competitionAssembler.toCreateCompetitionResponseDto(competition);
    }

    public SubmitCompetitionResponseDto submitCompetition(int competitionId, SubmitCompetitionRequestDto submitCompetitionRequestDto)
            throws CompilationException, CompetitionNotExistException, CompetitionNotStartedException, CompetitionFinishedException, CompetitionPoolBusyException, SubmissionExecutionTimeoutException {
        Optional<Competition> competitionOptional = competitionRepository.getCompetitionByCompetitionId(competitionId);
        if (competitionOptional.isEmpty()) {
            throw new CompetitionNotExistException(competitionId);
        }

        Competition competition = competitionOptional.get();

        long currentTime = System.currentTimeMillis();
        long competitionStartTime = competition.getStartTime();
        long competitionEndTime = competitionStartTime + competition.getDuration();

        if (currentTime < competitionStartTime) {
            throw new CompetitionNotStartedException();
        }
        if (currentTime > competitionEndTime) {
            throw new CompetitionFinishedException();
        }
        return testCaseExecutor.execute(competition, submitCompetitionRequestDto.getCode());
    }

    public CompetitionInfoResponseDto getCompetitionInfo(int competitionId) throws CompetitionNotExistException {
        Optional<Competition> competitionOptional = competitionRepository.getCompetitionByCompetitionId(competitionId);
        if (competitionOptional.isEmpty()) {
            throw new CompetitionNotExistException(competitionId);
        }
        Competition competition = competitionOptional.get();
        return competitionAssembler.toCompetitionInfoResponseDto(competition);
    }

    public List<CompetitionInfoDto> getAllCompetitionInfo() {
        List<Competition> competitionOptional = competitionRepository.findAll();
        return competitionAssembler.toCompetitionInfoDtoList(competitionOptional);
    }

    public List<TestCaseDto> getAllTestCases(int competitionId) throws CompetitionNotExistException {
        List<TestCase> testCases = testCaseRepository.findByCompetition_CompetitionId(competitionId);
        if (testCases.isEmpty()) {
            throw new CompetitionNotExistException(competitionId);
        }
        return testCaseAssembler.toTestCaseDtoList(testCases);
    }

    public CompetitionTemplateResponseDto getCompetitionTemplate(int competitionId) throws CompetitionNotExistException {
        Template template = templateRepository.findByCompetition_CompetitionId(competitionId);
        if (template == null) {
            throw new CompetitionNotExistException(competitionId);
        }
        List<TestCase> testCases = testCaseRepository.findByCompetition_CompetitionId(competitionId);
        return competitionAssembler.toCompetitionTemplateResponseDto(template, testCases);
    }
}
