package com.gisma.competition.acm.api.dto;

import com.gisma.competition.acm.api.dto.enumeration.CompetitionLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class BaseCompetitionDto {

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

    public CompetitionLevel getLevel() {
        return CompetitionLevel.valueOf(level);
    }

}
