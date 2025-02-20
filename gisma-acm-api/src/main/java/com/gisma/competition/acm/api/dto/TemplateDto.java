package com.gisma.competition.acm.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TemplateDto {

    @NotBlank(message = "Template of code cannot be empty.")
    private String codeTemplate;

    @NotBlank(message = "Signature of method cannot be empty.")
    private String methodSignature;
}
