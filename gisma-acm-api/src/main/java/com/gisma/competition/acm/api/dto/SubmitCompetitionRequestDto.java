package com.gisma.competition.acm.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubmitCompetitionRequestDto {

    @NotBlank(message = "Code cannot be empty.")
    private String code;
}
