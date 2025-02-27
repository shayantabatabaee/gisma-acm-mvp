package com.gisma.competition.acm.api.dto;

import lombok.Data;

@Data
public class SubmissionResponseDto {

    private Integer userId;
    private Double cpuTime;
    private long submissionTime;
    private Integer successTestCasesCount;
    private Integer failedTestCasesCount;
    private Boolean success;

}
