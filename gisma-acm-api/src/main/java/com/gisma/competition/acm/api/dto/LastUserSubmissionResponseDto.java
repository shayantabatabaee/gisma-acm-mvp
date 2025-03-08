package com.gisma.competition.acm.api.dto;

import lombok.Data;

@Data
public class LastUserSubmissionResponseDto {

    private Double cpuTime;
    private Long submissionTime;
    private Integer successTestCasesCount;
    private Integer failedTestCasesCount;
    private Boolean success;
    private Integer competitionId;

}
