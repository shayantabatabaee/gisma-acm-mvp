package com.gisma.competition.acm.api.validator;

import com.gisma.competition.acm.api.dto.ArgumentDto;
import com.gisma.competition.acm.api.dto.OutputDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ArgumentValidator implements ConstraintValidator<ArgumentValidation, Object> {

    @Override
    public void initialize(ArgumentValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value instanceof List<?>) {
            for (Object o : (List<?>) value) {
                if (o instanceof ArgumentDto) {
                    if (!isArgumentValid((ArgumentDto) o)) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }

        if (value instanceof OutputDto) {
            if (!isArgumentValid((OutputDto) value)) {
                return false;
            }
            return true;
        }

        return false;
    }

    private boolean isArgumentValid(ArgumentDto argumentDto) {
        boolean isArray = argumentDto.getIsArray();
        String type = argumentDto.getType();
        String value = argumentDto.getValue();

        if (isArray) {
            if (!value.startsWith("[") || !value.endsWith("]")) {
                return false;
            }
            value = value.substring(1, value.length() - 1);
            String[] split = value.split(",");
            for (String s : split) {
                if (!isTypeValid(type, s.trim())) {
                    return false;
                }
            }
            return true;
        } else {
            return isTypeValid(type, value);
        }
    }

    private boolean isTypeValid(String type, String value) {
        try {
            switch (type.toUpperCase()) {
                case "BYTE":
                    Byte.parseByte(value);
                    return true;
                case "SHORT":
                    Short.parseShort(value);
                    return true;
                case "INT":
                    Integer.parseInt(value);
                    return true;
                case "LONG":
                    Long.parseLong(value);
                    return true;
                case "FLOAT":
                    Float.parseFloat(value);
                    return true;
                case "DOUBLE":
                    Double.parseDouble(value);
                    return true;
                case "BOOLEAN":
                    return value.equalsIgnoreCase("true") ||
                            value.equalsIgnoreCase("false");
                case "CHAR":
                    return value.length() == 1;
                case "STRING":
                    return true;
                default:
                    return false;

            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
