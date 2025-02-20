package com.gisma.competition.acm.api.dto;

import com.gisma.competition.acm.api.dto.enumeration.CompetitionLevel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class CreateCompetitionRequestDto {

    @NotBlank(message = "Name cannot be empty.")
    private String name;

    @Getter(AccessLevel.NONE)
    @NotNull(message = "Level must not be null.")
    @Pattern(regexp = "EASY|MEDIUM|HARD", message = "Invalid level, accepted values are:EASY, MEDIUM, HARD")
    private String level;

    @NotNull(message = "Start time must not be null.")
    @Positive(message = "Start time must be positive.")
    private Long startTime;

    @NotNull(message = "Duration must not be null.")
    @Positive(message = "Duration must be positive.")
    private Long duration;

    @NotBlank(message = "Description cannot be empty.")
    private String description;

    @NotNull(message = "Template must not be null.")
    @Valid
    private TemplateDto template;

    @NotNull(message = "Test cases must not be null.")
    @Size(min = 1, message = "At least one test case must be provided.")
    @Valid
    private List<TestCaseDto> testCases;

    public CompetitionLevel getLevel() {
        return CompetitionLevel.valueOf(level);
    }
}
