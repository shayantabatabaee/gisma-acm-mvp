package com.gisma.competition.acm.api.validator;

import com.gisma.competition.acm.api.dto.ArgumentDto;
import com.gisma.competition.acm.api.dto.TestCaseDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class TestCaseValidator implements ConstraintValidator<TestCaseValidation, List<TestCaseDto>> {

    @Override
    public void initialize(TestCaseValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<TestCaseDto> values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) {
            return true;
        }

        boolean hasOutput = values.getFirst().getExpectedOutput() != null;
        int inputCount = values.getFirst().getInputs() == null ? 0 : values.getFirst().getInputs().size();
        int argumentsCount = inputCount + (hasOutput ? 1 : 0);

        List<List<ArgumentDto>> arguments = new ArrayList<>(argumentsCount);

        for (int i = 0; i < argumentsCount; i++) {
            arguments.add(new ArrayList<>());
        }

        for (int i = 0; i < values.size(); i++) {
            int currentInputCount = values.get(i).getInputs() == null ? 0 : values.get(i).getInputs().size();
            boolean currentHasOutput = values.get(i).getExpectedOutput() != null;

            if (currentInputCount != inputCount) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(String.format("testCase[%d] inputs size incorrect.", i))
                        .addConstraintViolation();
                return false;
            }
            if (currentHasOutput != hasOutput) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(String.format("testCase[%d] does not provide output.", i))
                        .addConstraintViolation();
                return false;
            }

            for (int j = 0; j < currentInputCount; j++) {
                arguments.get(j).add(values.get(i).getInputs().get(j));
            }

            if (currentHasOutput) {
                arguments.getLast().add(values.get(i).getExpectedOutput());
            }
        }

        for (int i = 0; i < arguments.size() - 1; i++) {
            if (!isSameToEachOther(arguments.get(i), context, true, i)) {
                return false;
            }
        }


        return isSameToEachOther(arguments.getLast(), context, false, arguments.size() - 1);
    }

    private boolean isSameToEachOther(List<ArgumentDto> arguments,
                                      ConstraintValidatorContext context,
                                      boolean isInput, int index) {
        boolean isArray = arguments.getFirst().getIsArray();
        String type = arguments.getFirst().type;

        String middle = isInput ? String.format("inputs[%d].", index) : "expectedOutput.";

        for (int i = 1; i < arguments.size(); i++) {
            if (!arguments.get(i).type.equals(type)) {
                String message = String.format("testCases[%d].", i) + middle + "type mismatch with others.";
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                        .addConstraintViolation();
                return false;
            }
            if (arguments.get(i).getIsArray() != isArray) {
                String message = String.format("testCases[%d].", i) + middle + "isArray mismatch with others.";
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
