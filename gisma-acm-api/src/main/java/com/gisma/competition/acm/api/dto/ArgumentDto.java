package com.gisma.competition.acm.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ArgumentDto {

    @NotNull(message = "Is array or not must not be null.")
    private Boolean isArray;

    @NotNull(message = "Type must not be null.")
    @Pattern(regexp = "BYTE|SHORT|INT|LONG|FLOAT|DOUBLE|BOOLEAN|CHAR|STRING",
            message = "Invalid type, accepted values are: BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BOOLEAN, CHAR, STRING")
    public String type;

    @NotBlank(message = "Value can not be empty.")
    private String value;
}
