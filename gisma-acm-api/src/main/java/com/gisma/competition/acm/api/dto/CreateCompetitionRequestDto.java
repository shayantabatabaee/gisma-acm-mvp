package com.gisma.competition.acm.api.dto;

import com.gisma.competition.acm.api.validator.TestCaseValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateCompetitionRequestDto extends BaseCompetitionDto {

    @NotNull(message = "Template must not be null.")
    @Valid
    private TemplateDto template;

    @NotNull(message = "Test cases must not be null.")
    @Size(min = 1, message = "At least one test case must be provided.")
    @Valid
    @TestCaseValidation
    private List<TestCaseDto> testCases;

}
