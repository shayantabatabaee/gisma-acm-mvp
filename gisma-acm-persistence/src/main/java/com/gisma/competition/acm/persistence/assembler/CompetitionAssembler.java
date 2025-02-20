package com.gisma.competition.acm.persistence.assembler;

import com.gisma.competition.acm.api.dto.CreateCompetitionRequestDto;
import com.gisma.competition.acm.api.dto.CreateCompetitionResponseDto;
import com.gisma.competition.acm.api.dto.TemplateDto;
import com.gisma.competition.acm.api.dto.TestCaseDto;
import com.gisma.competition.acm.persistence.entity.Competition;
import com.gisma.competition.acm.persistence.entity.Template;
import com.gisma.competition.acm.persistence.entity.TestCase;
import com.gisma.competition.acm.persistence.enumeration.CompetitionLevelModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompetitionAssembler {

    public CreateCompetitionResponseDto toCreateCompetitionResponseDto(Competition competition) {
        CreateCompetitionResponseDto createCompetitionResponseDto = new CreateCompetitionResponseDto();
        createCompetitionResponseDto.setName(competition.getName());
        createCompetitionResponseDto.setStartTime(competition.getStartTime());
        createCompetitionResponseDto.setEndTime(competition.getStartTime() + competition.getDuration());
        return createCompetitionResponseDto;
    }

    public Competition toCompetitionModel(CreateCompetitionRequestDto requestDto) {
        Competition competition = new Competition();
        competition.setName(requestDto.getName());
        competition.setLevel(CompetitionLevelModel.valueOf(requestDto.getLevel().name()));
        competition.setStartTime(requestDto.getStartTime());
        competition.setDuration(requestDto.getDuration());
        competition.setDescription(requestDto.getDescription());
        return competition;
    }

    public Template toTemplateModel(TemplateDto templateDto) {
        Template template = new Template();
        template.setCodeTemplate(templateDto.getCodeTemplate());
        template.setMethodSignature(templateDto.getMethodSignature());
        return template;
    }

    public List<TestCase> toTestCasesModel(List<TestCaseDto> testCaseDtos) {
        List<TestCase> testCases = new ArrayList<>();
        for (TestCaseDto testCaseDto : testCaseDtos) {
            TestCase testCase = new TestCase();
            testCase.setInput(testCaseDto.getInput());
            testCase.setExpectedOutput(testCaseDto.getExpectedOutput());
            testCases.add(testCase);
        }
        return testCases;
    }

}
