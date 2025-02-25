package com.gisma.competition.acm.api.dto;

import lombok.Data;

@Data
public class CreateCompetitionResponseDto {
    private Integer competitionId;
    private String name;
    private Long startTime;
    private Long endTime;
}
