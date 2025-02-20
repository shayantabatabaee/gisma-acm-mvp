package com.gisma.competition.acm.service;

import com.gisma.competition.acm.api.dto.CreateCompetitionRequestDto;
import com.gisma.competition.acm.api.exception.CompetitionDuplicateException;
import com.gisma.competition.acm.persistence.assembler.CompetitionAssembler;
import com.gisma.competition.acm.persistence.entity.Competition;
import com.gisma.competition.acm.persistence.entity.Template;
import com.gisma.competition.acm.persistence.entity.TestCase;
import com.gisma.competition.acm.persistence.repository.CompetitionRepository;
import com.gisma.competition.acm.persistence.repository.TemplateRepository;
import com.gisma.competition.acm.persistence.repository.TestCaseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompetitionService {

    private final CompetitionAssembler competitionAssembler;
    private final CompetitionRepository competitionRepository;
    private final TemplateRepository templateRepository;
    private final TestCaseRepository testCaseRepository;

    @Transactional
    public Competition createCompetitionWithTemplateAndTestCases(CreateCompetitionRequestDto createCompetitionRequestDto) throws CompetitionDuplicateException {
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
        return competition;
    }
}
