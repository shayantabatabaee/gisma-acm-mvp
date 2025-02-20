package com.gisma.competition.acm.api.dto;

import lombok.Data;

@Data
public class CreateCompetitionResponseDto {
    private String name;
    private Long startTime;
    private Long endTime;
}
