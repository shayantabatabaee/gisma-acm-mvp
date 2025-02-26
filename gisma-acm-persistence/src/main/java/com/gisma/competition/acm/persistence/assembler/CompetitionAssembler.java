package com.gisma.competition.acm.persistence.assembler;

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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public TestCaseDto toTestCaseDto(List<TestCase> inputTestCase, TestCase expectedOutputTestCase) {
        TestCaseDto testCaseDto = new TestCaseDto();

        if (inputTestCase != null) {
            List<JsonNode> inputDtos = new ArrayList<>();
            for (TestCase _testCaseDto : inputTestCase) {
                inputDtos.add(_testCaseDto.getJsonValue());
            }
            testCaseDto.setInputs(inputDtos);
        }

        if (expectedOutputTestCase != null) {
            testCaseDto.setExpectedOutput(expectedOutputTestCase.getJsonValue());
        }

        return testCaseDto;
    }

    public List<TestCaseDto> toTestCaseDtoList(List<TestCase> testCases) {
        Map<ArgumentTypeModel, Map<Integer, List<TestCase>>> groupedTestCases =
                testCases.stream()
                        .collect(Collectors.groupingBy(
                                TestCase::getArgumentType,
                                Collectors.groupingBy(TestCase::getTestCaseId)
                        ));
        List<TestCaseDto> testCaseDtoList = new ArrayList<>();

        int iteration = 0;
        if (groupedTestCases.containsKey(ArgumentTypeModel.INPUT))
            iteration = groupedTestCases.get(ArgumentTypeModel.INPUT).size();
        else if (groupedTestCases.containsKey(ArgumentTypeModel.OUTPUT))
            iteration = groupedTestCases.get(ArgumentTypeModel.OUTPUT).size();

        for (int i = 0; i < iteration; i++) {
            List<TestCase> inputTestcases = null;
            TestCase expectedOutputTestCase = null;
            if (groupedTestCases.containsKey(ArgumentTypeModel.INPUT)) {
                inputTestcases = groupedTestCases.get(ArgumentTypeModel.INPUT).get(i);
            }
            if (groupedTestCases.containsKey(ArgumentTypeModel.OUTPUT)) {
                expectedOutputTestCase = groupedTestCases.get(ArgumentTypeModel.OUTPUT).get(i).getFirst();
            }
            TestCaseDto testCaseDto = toTestCaseDto(inputTestcases, expectedOutputTestCase);
            testCaseDtoList.add(testCaseDto);
        }

        return testCaseDtoList;
    }

}
