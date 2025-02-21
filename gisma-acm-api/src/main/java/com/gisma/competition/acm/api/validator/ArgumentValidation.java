package com.gisma.competition.acm.api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ArgumentValidator.class)
public @interface ArgumentValidation {
    String message() default "Argument are invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
