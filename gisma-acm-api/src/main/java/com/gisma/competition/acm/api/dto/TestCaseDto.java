package com.gisma.competition.acm.api.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TestCaseDto {

    // inputs: null => There is no input for method
    // inputs: [null] => Pass null as test case to method
    @Size(min = 1, message = "At least one input must be provided or be null.")
    private List<JsonNode> inputs;

    // expectedOutput: null => Check null as test case for return of method
    // expectedOutput:      => If absent means the output of model is void
    private JsonNode expectedOutput;
}
