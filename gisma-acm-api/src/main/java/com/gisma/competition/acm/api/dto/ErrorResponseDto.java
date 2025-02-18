package com.gisma.competition.acm.api.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorResponseDto {

    private String errorCode;
    private String errorMessage;
    private Map<String, String> errorDetails;

}
