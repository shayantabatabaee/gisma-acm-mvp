package com.gisma.competition.acm.util;

import com.gisma.competition.acm.persistence.entity.Template;
import com.gisma.competition.acm.persistence.entity.TestCase;
import com.gisma.competition.acm.persistence.enumeration.ArgumentTypeModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateDecorator {

    public static String decorate(Template template, List<TestCase> testCases) {
        Map<ArgumentTypeModel, Map<Integer, List<TestCase>>> groupedTestCases =
                testCases.stream()
                        .collect(Collectors.groupingBy(
                                TestCase::getArgumentType,
                                Collectors.groupingBy(TestCase::getTestCaseId)
                        ));

        Class<?>[] inputParam = null;
        Class<?> outputParam = null;
        if (groupedTestCases.containsKey(ArgumentTypeModel.INPUT)) {
            inputParam = TestCaseValueConverter.convertParameterTypes(groupedTestCases.get(ArgumentTypeModel.INPUT).get(0));
        }
        if (groupedTestCases.containsKey(ArgumentTypeModel.OUTPUT)) {
            outputParam = TestCaseValueConverter.convertParameterTypes(groupedTestCases.get(ArgumentTypeModel.OUTPUT).get(0))[0];
        }

        StringBuilder result = new StringBuilder("public class ");
        result.append(template.getClassName());
        result.append(" {\n\t");
        result.append("public ");
        result.append(getOutputParamDecorated(outputParam));
        result.append(" ");
        result.append(template.getMethodName());
        result.append(getInputParamDecorated(inputParam));
        result.append(" {\n");
        result.append("\t\t//Your code goes here\n");
        result.append("\t}\n");
        result.append("}");
        return result.toString();
    }

    private static String getOutputParamDecorated(Class<?> outputParam) {
        if (outputParam == null) {
            return "void";
        } else {
            return outputParam.getSimpleName();
        }
    }

    private static String getInputParamDecorated(Class<?>[] inputParam) {
        if (inputParam == null) {
            return "()";
        } else {
            StringBuilder result = new StringBuilder("(");
            for (int i = 0; i < inputParam.length; i++) {
                result.append(inputParam[i].getSimpleName());
                result.append(" param").append(i);
                if (i < inputParam.length - 1) {
                    result.append(", ");
                }
            }
            result.append(")");
            return result.toString();
        }
    }
}
