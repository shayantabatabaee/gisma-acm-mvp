package com.gisma.competition.acm.assembler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisma.competition.acm.api.dto.*;
import com.gisma.competition.acm.api.exception.ValidationException;
import com.gisma.competition.acm.persistence.entity.Competition;
import com.gisma.competition.acm.persistence.entity.Template;
import com.gisma.competition.acm.persistence.entity.TestCase;
import com.gisma.competition.acm.persistence.enumeration.ArgumentTypeModel;
import com.gisma.competition.acm.persistence.enumeration.CompetitionLevelModel;
import com.gisma.competition.acm.util.TemplateDecorator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CompetitionAssembler {

    public CompetitionInfoResponseDto toCompetitionInfoResponseDto(Competition competition) {
        CompetitionInfoResponseDto competitionInfoResponseDto = new CompetitionInfoResponseDto();
        competitionInfoResponseDto.setCompetitionId(competition.getCompetitionId());
        competitionInfoResponseDto.setIsFinished(System.currentTimeMillis() > (competition.getStartTime() + competition.getDuration()));
        BaseCompetitionDto baseCompetitionDto = new BaseCompetitionDto();
        baseCompetitionDto.setName(competition.getName());
        baseCompetitionDto.setDescription(competition.getDescription());
        baseCompetitionDto.setStartTime(competition.getStartTime());
        baseCompetitionDto.setDuration(competition.getDuration());
        baseCompetitionDto.setLevel(competition.getLevel().name());
        competitionInfoResponseDto.setCompetition(baseCompetitionDto);
        return competitionInfoResponseDto;
    }

    public List<CompetitionInfoDto> toCompetitionInfoDtoList(List<Competition> competitions) {
        List<CompetitionInfoDto> competitionInfoDtoList = new ArrayList<>();
        for (Competition competition : competitions) {
            CompetitionInfoDto competitionInfoDto = new CompetitionInfoDto();
            competitionInfoDto.setCompetitionId(competition.getCompetitionId());
            competitionInfoDto.setIsFinished(System.currentTimeMillis() > (competition.getStartTime() + competition.getDuration()));
            competitionInfoDtoList.add(competitionInfoDto);
        }
        return competitionInfoDtoList;
    }

    public CreateCompetitionResponseDto toCreateCompetitionResponseDto(Competition competition) {
        CreateCompetitionResponseDto createCompetitionResponseDto = new CreateCompetitionResponseDto();
        createCompetitionResponseDto.setCompetitionId(competition.getCompetitionId());
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
        template.setClassName(templateDto.getClassName());
        template.setMethodName(templateDto.getMethodName());
        return template;
    }

    public List<TestCase> toTestCasesModel(List<TestCaseDto> testCaseDtos) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<TestCase> testCases = new ArrayList<>();
            int j = 0;
            for (TestCaseDto testCaseDto : testCaseDtos) {
                if (testCaseDto.getInputs() != null) {
                    for (JsonNode input : testCaseDto.getInputs()) {
                        TestCase testCase = new TestCase();
                        testCase.setValue(objectMapper.writeValueAsString(input));
                        testCase.setArgumentType(ArgumentTypeModel.INPUT);
                        testCase.setTestCaseId(j);
                        testCases.add(testCase);
                    }
                }
                if (testCaseDto.getExpectedOutput() != null) {
                    TestCase testCase = new TestCase();
                    testCase.setValue(objectMapper.writeValueAsString(testCaseDto.getExpectedOutput()));
                    testCase.setArgumentType(ArgumentTypeModel.OUTPUT);
                    testCase.setTestCaseId(j);
                    testCases.add(testCase);
                }
                j++;
            }
            return testCases;
        } catch (JsonProcessingException e) {
            ValidationException validationException = new ValidationException();
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("JsonProcessing", e.getMessage());
            validationException.setDetails(errorDetails);
            throw validationException;
        }
    }

    public CompetitionTemplateResponseDto toCompetitionTemplateResponseDto(Template template, List<TestCase> testCases) {
        CompetitionTemplateResponseDto competitionTemplateResponseDto = new CompetitionTemplateResponseDto();
        competitionTemplateResponseDto.setClassName(template.getClassName());
        competitionTemplateResponseDto.setMethodName(template.getMethodName());
        competitionTemplateResponseDto.setDecoratedTemplate(TemplateDecorator.decorate(template, testCases));
        return competitionTemplateResponseDto;
    }

}
