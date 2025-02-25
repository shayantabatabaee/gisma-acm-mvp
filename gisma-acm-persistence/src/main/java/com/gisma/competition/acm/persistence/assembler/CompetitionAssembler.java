package com.gisma.competition.acm.persistence.assembler;

import com.gisma.competition.acm.api.dto.*;
import com.gisma.competition.acm.persistence.entity.Competition;
import com.gisma.competition.acm.persistence.entity.Template;
import com.gisma.competition.acm.persistence.entity.TestCase;
import com.gisma.competition.acm.persistence.enumeration.ArgumentTypeModel;
import com.gisma.competition.acm.persistence.enumeration.CompetitionLevelModel;
import com.gisma.competition.acm.persistence.enumeration.DataTypeModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompetitionAssembler {

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
        List<TestCase> testCases = new ArrayList<>();
        int j = 0;
        for (TestCaseDto testCaseDto : testCaseDtos) {
            int i = 0;
            if (testCaseDto.getInputs() != null) {
                for (InputDto inputDto : testCaseDto.getInputs()) {
                    TestCase testCase = new TestCase();
                    testCase.setArgumentId(i);
                    testCase.setArray(inputDto.getIsArray());
                    testCase.setDataType(DataTypeModel.valueOf(inputDto.getType()));
                    testCase.setValue(inputDto.getValue());
                    testCase.setArgumentType(ArgumentTypeModel.INPUT);
                    testCase.setTestCaseId(j);
                    testCases.add(testCase);
                    i++;
                }
            }
            if (testCaseDto.getExpectedOutput() != null) {
                TestCase testCase = new TestCase();
                testCase.setArgumentId(i);
                testCase.setArray(testCaseDto.getExpectedOutput().getIsArray());
                testCase.setDataType(DataTypeModel.valueOf(testCaseDto.getExpectedOutput().getType()));
                testCase.setValue(testCaseDto.getExpectedOutput().getValue());
                testCase.setArgumentType(ArgumentTypeModel.OUTPUT);
                testCase.setTestCaseId(j);
                testCases.add(testCase);
            }
            j++;
        }
        return testCases;
    }

    public TestCaseDto toTestCaseDto(List<TestCase> inputTestCase, TestCase expectedOutputTestCase) {
        TestCaseDto testCaseDto = new TestCaseDto();

        if (inputTestCase != null) {
            List<InputDto> inputDtos = new ArrayList<>();
            for (TestCase _testCaseDto : inputTestCase) {
                InputDto inputDto = new InputDto();
                inputDto.setIsArray(_testCaseDto.isArray());
                inputDto.setType(_testCaseDto.getDataType().name());
                inputDto.setValue(_testCaseDto.getValue());
                inputDtos.add(inputDto);
            }
            testCaseDto.setInputs(inputDtos);
        }

        if (expectedOutputTestCase != null) {
            OutputDto expectedOutputDto = new OutputDto();
            expectedOutputDto.setIsArray(expectedOutputTestCase.isArray());
            expectedOutputDto.setType(expectedOutputTestCase.getDataType().name());
            expectedOutputDto.setValue(expectedOutputTestCase.getValue());
            testCaseDto.setExpectedOutput(expectedOutputDto);
        }

        return testCaseDto;
    }

}
