package com.gisma.competition.acm.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubmitCompetitionResponseDto {

    private Integer competitionId;
    private String competitionName;
    private Boolean success;
    private List<TestCaseDto> successTestCases;
    private List<TestCaseDto> failureTestCases;
    private Double cpuTime;
}
