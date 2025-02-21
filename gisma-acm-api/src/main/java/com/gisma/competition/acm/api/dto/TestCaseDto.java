package com.gisma.competition.acm.api.dto;

import com.gisma.competition.acm.api.validator.ArgumentValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TestCaseDto {

    @Valid
    @ArgumentValidation
    @Size(min = 1, message = "At least one input must be provided or be null.")
    private List<InputDto> inputs;

    @Valid
    @ArgumentValidation
    private OutputDto expectedOutput;
}
