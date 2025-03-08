package com.gisma.competition.acm.api.dto;

import lombok.Data;

@Data
public class UserSubmissionResponseDto {

    private Double cpuTime;
    private Long submissionTime;
    private Integer competitionId;

}
