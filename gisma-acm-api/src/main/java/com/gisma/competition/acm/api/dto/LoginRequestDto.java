package com.gisma.competition.acm.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "Username cannot be empty.")
    private String username;

    @NotBlank(message = "Password cannot be empty.")
    private String password;
}
