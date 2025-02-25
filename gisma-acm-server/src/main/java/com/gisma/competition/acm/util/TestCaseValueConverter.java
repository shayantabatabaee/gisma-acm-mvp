package com.gisma.competition.acm.util;

import com.gisma.competition.acm.persistence.entity.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TestCaseValueConverter {

    public static Class<?>[] convertParameterTypes(List<TestCase> testCases) {
        Class<?>[] result = new Class<?>[testCases.size()];
        for (int i = 0; i < testCases.size(); i++) {
            boolean isArray = testCases.get(i).isArray();
            result[i] = testCases.get(i).getDataType().getDataTypeClass(isArray);
        }
        return result;
    }

    public static Object[] convert(List<TestCase> testCases) {
        Object[] result = new Object[testCases.size()];

        for (int i = 0; i < testCases.size(); i++) {
            boolean isArray = testCases.get(i).isArray();
            result[i] = TestCaseValueConverter.convertValue(testCases.get(i).getValue(), testCases.get(i).getDataType().getDataTypeClass(isArray));
        }
        return result;
    }

    private static Object convertValue(String value, Class<?> aClass) {
        if (aClass.isArray()) {
            Stream<Object> stream = Arrays.stream(value.replace("[", "").replace("]", "").split(","))
                    .map(v -> convertValueBasedOnType(v.trim(), aClass.getComponentType()));
            Class<?> targetType = aClass.getComponentType();
            if (targetType == Byte.class || targetType == byte.class) {
                return stream.toArray(Byte[]::new);
            } else if (targetType == Short.class || targetType == short.class) {
                return stream.toArray(Short[]::new);
            } else if (targetType == Integer.class || targetType == int.class) {
                return stream.toArray(Integer[]::new);
            } else if (targetType == Long.class || targetType == long.class) {
                return stream.toArray(Long[]::new);
            } else if (targetType == Float.class || targetType == float.class) {
                return stream.toArray(Float[]::new);
            } else if (targetType == Double.class || targetType == double.class) {
                return stream.toArray(Double[]::new);
            } else if (targetType == Boolean.class || targetType == boolean.class) {
                return stream.toArray(Boolean[]::new);
            } else if (targetType == Character.class || targetType == char.class) {
                return stream.toArray(Character[]::new);
            } else if (targetType == String.class) {
                return stream.toArray(String[]::new);
            } else {
                throw new IllegalArgumentException("Unsupported target type: " + targetType);
            }
        } else return convertValueBasedOnType(value, aClass);
    }

    private static Object convertValueBasedOnType(String value, Class<?> targetType) {
        if (targetType == Byte.class || targetType == byte.class) {
            return Byte.valueOf(value);
        } else if (targetType == Short.class || targetType == short.class) {
            return Short.valueOf(value);
        } else if (targetType == Integer.class || targetType == int.class) {
            return Integer.parseInt(value);
        } else if (targetType == Long.class || targetType == long.class) {
            return Long.valueOf(value);
        } else if (targetType == Float.class || targetType == float.class) {
            return Float.valueOf(value);
        } else if (targetType == Double.class || targetType == double.class) {
            return Double.valueOf(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.valueOf(value);
        } else if (targetType == Character.class || targetType == char.class) {
            return value.charAt(0);
        } else if (targetType == String.class) {
            return value;
        } else {
            throw new IllegalArgumentException("Unsupported target type: " + targetType);
        }
    }
}
