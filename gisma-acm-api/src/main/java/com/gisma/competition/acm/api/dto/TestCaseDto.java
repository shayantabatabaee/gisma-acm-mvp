package com.gisma.competition.acm.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TestCaseDto {

    @NotBlank(message = "Input of test case cannot be empty.")
    private String input;

    @NotBlank(message = "Expected output of test case cannot be empty.")
    private String expectedOutput;
}
