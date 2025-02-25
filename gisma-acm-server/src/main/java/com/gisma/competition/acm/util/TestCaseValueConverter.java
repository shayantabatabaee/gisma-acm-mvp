package com.gisma.competition.acm.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.gisma.competition.acm.persistence.entity.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TestCaseValueConverter {

    public static Class<?>[] convertParameterTypes(List<TestCase> testCases) {
        Class<?>[] result = new Class<?>[testCases.size()];
        for (int i = 0; i < testCases.size(); i++) {
            result[i] = getDataTypeClass(testCases.get(i).getJsonValue());
        }
        return result;
    }

    public static Object[] convert(List<TestCase> testCases) {
        Object[] result = new Object[testCases.size()];
        for (int i = 0; i < testCases.size(); i++) {
            result[i] = TestCaseValueConverter.convertValue(testCases.get(i).getJsonValue());
        }
        return result;
    }

    private static Object convertValue(JsonNode value) {
        if (value.isArray()) {
            JsonNode[] valueArray = new JsonNode[value.size()];
            int i = 0;
            for (JsonNode element : value) {
                valueArray[i] = element;
                i++;
            }
            Stream<Object> stream = Arrays.stream(valueArray)
                    .map(TestCaseValueConverter::convertValueBasedOnType);

            JsonNode firstValue = value.get(0);

            if (firstValue.isNumber()) {
                return switch (firstValue.numberType()) {
                    case INT -> stream.toArray(Integer[]::new);
                    case LONG -> stream.toArray(Long[]::new);
                    case FLOAT -> stream.toArray(Float[]::new);
                    case DOUBLE -> stream.toArray(Double[]::new);
                    default -> throw new IllegalArgumentException("Unsupported target type: " + value.numberType());
                };
            } else if (value.isTextual()) {
                return stream.toArray(String[]::new);
            } else if (value.isBoolean()) {
                return stream.toArray(Boolean[]::new);
            } else if (value.isNull()) {
                return null;
            } else
                throw new IllegalArgumentException("Unsupported target type: " + value.numberType());
        } else return convertValueBasedOnType(value);
    }

    private static Object convertValueBasedOnType(JsonNode value) {
        if (value.isNumber()) {
            return switch (value.numberType()) {
                case INT -> value.intValue();
                case LONG -> value.longValue();
                case FLOAT -> value.floatValue();
                case DOUBLE -> value.doubleValue();
                default -> throw new IllegalArgumentException("Unsupported target type: " + value.numberType());
            };
        } else if (value.isTextual()) {
            return value.textValue();
        } else if (value.isBoolean()) {
            return value.booleanValue();
        } else if (value.isNull()) {
            return null;
        } else
            throw new IllegalArgumentException("Unsupported target type: " + value.numberType());
    }

    private static Class<?> getDataTypeClass(JsonNode value) {
        JsonNode conditionValue = value;
        boolean isArray = conditionValue.isArray();
        if (isArray) {
            conditionValue = value.get(0);
        }
        if (conditionValue.isNumber()) {
            return switch (conditionValue.numberType()) {
                case INT -> isArray ? Integer[].class : Integer.class;
                case LONG -> isArray ? Long[].class : Long.class;
                case FLOAT -> isArray ? Float[].class : Float.class;
                case DOUBLE -> isArray ? Double[].class : Double.class;
                default -> throw new IllegalArgumentException("Unsupported target type: " + value.numberType());
            };
        } else if (conditionValue.isTextual()) {
            return isArray ? String[].class : String.class;
        } else if (conditionValue.isBoolean()) {
            return isArray ? Boolean[].class : Boolean.class;
        } else
            throw new IllegalArgumentException("Unsupported target type: " + value.numberType());
    }
}
