package com.gisma.competition.acm.api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TestCaseValidator.class)
public @interface TestCaseValidation {
    String message() default "Test cases are invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
