package com.gisma.competition.acm.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserSubmissionsResponseDto {

    List<LastUserSubmissionResponseDto> lastSubmissions;
    List<UserSubmissionResponseDto> allSubmissions;

}
