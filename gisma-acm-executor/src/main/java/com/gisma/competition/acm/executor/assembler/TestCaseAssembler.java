package com.gisma.competition.acm.executor.assembler;

import com.fasterxml.jackson.databind.JsonNode;
import com.gisma.competition.acm.api.dto.TestCaseDto;
import com.gisma.competition.acm.persistence.entity.TestCase;
import com.gisma.competition.acm.persistence.enumeration.ArgumentTypeModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TestCaseAssembler {

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
