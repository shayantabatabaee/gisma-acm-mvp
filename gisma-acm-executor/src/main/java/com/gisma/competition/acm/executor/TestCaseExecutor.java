package com.gisma.competition.acm.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisma.competition.acm.api.dto.SubmitCompetitionResponseDto;
import com.gisma.competition.acm.api.dto.TestCaseDto;
import com.gisma.competition.acm.api.exception.CompilationException;
import com.gisma.competition.acm.executor.assembler.TestCaseAssembler;
import com.gisma.competition.acm.executor.compiler.InMemoryJavaCompiler;
import com.gisma.competition.acm.executor.compiler.InMemoryJavaExecutor;
import com.gisma.competition.acm.executor.util.TestCaseValueConverter;
import com.gisma.competition.acm.persistence.entity.Competition;
import com.gisma.competition.acm.persistence.entity.TestCase;
import com.gisma.competition.acm.persistence.enumeration.ArgumentTypeModel;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestCaseExecutor {

    private static final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Competition competition = objectMapper.readValue(args[0], Competition.class);
            String code = args[1];
            SubmitCompetitionResponseDto competitionResponseDto = execute(competition, code);
            System.out.println(objectMapper.writeValueAsString(competitionResponseDto));
            System.exit(0);
        } catch (CompilationException e) {
            System.out.println(objectMapper.writeValueAsString(e));
            System.exit(1);
        }
    }

    public static SubmitCompetitionResponseDto execute(Competition competition, String code) throws CompilationException {
        SubmitCompetitionResponseDto responseDto = new SubmitCompetitionResponseDto();
        TestCaseAssembler competitionAssembler = new TestCaseAssembler();

        InMemoryJavaCompiler compiler = new InMemoryJavaCompiler();
        Class<?> aClass = compiler.compile(competition.getTemplate().getClassName(), code);

        String methodName = competition.getTemplate().getMethodName();

        Map<ArgumentTypeModel, Map<Integer, List<TestCase>>> groupedTestCases =
                competition.getTestCases().stream()
                        .collect(Collectors.groupingBy(
                                TestCase::getArgumentType,
                                Collectors.groupingBy(TestCase::getTestCaseId)
                        ));

        int iteration = 1;

        if (groupedTestCases.containsKey(ArgumentTypeModel.INPUT))
            iteration = groupedTestCases.get(ArgumentTypeModel.INPUT).size();
        else if (groupedTestCases.containsKey(ArgumentTypeModel.OUTPUT))
            iteration = groupedTestCases.get(ArgumentTypeModel.OUTPUT).size();

        long threadId = Thread.currentThread().threadId();
        long beforeCpuTime = threadMXBean.getThreadCpuTime(threadId);

        List<Object> outputs = new ArrayList<>();
        List<Object> results = new ArrayList<>();

        for (int i = 0; i < iteration; i++) {
            Object[] inputs = null;
            Class<?>[] parameters = null;
            if (groupedTestCases.containsKey(ArgumentTypeModel.INPUT)) {
                inputs = TestCaseValueConverter.convert(groupedTestCases.get(ArgumentTypeModel.INPUT).get(i));
                parameters = TestCaseValueConverter.convertParameterTypes(groupedTestCases.get(ArgumentTypeModel.INPUT).get(i));
            }

            if (groupedTestCases.containsKey(ArgumentTypeModel.OUTPUT)) {
                Object output = TestCaseValueConverter.convert(groupedTestCases.get(ArgumentTypeModel.OUTPUT).get(i))[0];
                outputs.add(output);
            }
            Object result = InMemoryJavaExecutor.execute(aClass, methodName, inputs, parameters);
            results.add(result);
        }
        long afterCpuTime = threadMXBean.getThreadCpuTime(threadId);

        List<TestCaseDto> successTestCases = new ArrayList<>();
        List<TestCaseDto> failureTestCases = new ArrayList<>();

        for (int i = 0; i < iteration; i++) {
            List<TestCase> inputTestCase = null;
            TestCase expectedOutputTestCase = null;

            if (groupedTestCases.containsKey(ArgumentTypeModel.INPUT)) {
                inputTestCase = groupedTestCases.get(ArgumentTypeModel.INPUT).get(i);
            }
            if (groupedTestCases.containsKey(ArgumentTypeModel.OUTPUT)) {
                expectedOutputTestCase = groupedTestCases.get(ArgumentTypeModel.OUTPUT).get(i).getFirst();
            }

            if (expectedOutputTestCase != null) {
                boolean isArray = expectedOutputTestCase.getJsonValue().isArray();
                String stringOutput;
                String stringResult;

                if (isArray) {
                    stringOutput = arrayToString((Object[]) outputs.get(i));
                    stringResult = arrayToString((Object[]) results.get(i));
                } else {
                    stringOutput = outputs.get(i).toString();
                    stringResult = results.get(i).toString();
                }
                if (stringResult.equals(stringOutput)) {
                    successTestCases.add(competitionAssembler.toTestCaseDto(inputTestCase, expectedOutputTestCase));
                } else {
                    failureTestCases.add(competitionAssembler.toTestCaseDto(inputTestCase, expectedOutputTestCase));
                }
            } else {
                successTestCases.add(competitionAssembler.toTestCaseDto(inputTestCase, expectedOutputTestCase));
            }
        }

        boolean success = false;
        if (outputs.isEmpty() || failureTestCases.isEmpty())
            success = true;

        responseDto.setCompetitionId(competition.getCompetitionId());
        responseDto.setCompetitionName(competition.getName());
        responseDto.setCpuTime((afterCpuTime - beforeCpuTime) / 1_000_000_000.0);
        responseDto.setSuccessTestCases(successTestCases);
        responseDto.setFailureTestCases(failureTestCases);
        responseDto.setSuccess(success);

        return responseDto;
    }

    private static String arrayToString(Object[] array) {
        if (array == null) {
            return "null";
        }
        return "[" + Arrays.stream(array)
                .map(String::valueOf)
                .collect(Collectors.joining(", ")) + "]";
    }

}
