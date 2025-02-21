package com.gisma.competition.acm.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TemplateDto {

    @NotBlank(message = "Name of class cannot be empty.")
    private String className;

    @NotBlank(message = "Name of method cannot be empty.")
    private String methodName;
}
