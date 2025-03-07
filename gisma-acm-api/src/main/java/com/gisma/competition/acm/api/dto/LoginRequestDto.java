package com.gisma.competition.acm.api.dto;

import com.gisma.competition.acm.api.util.Masker;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "Username cannot be empty.")
    private String username;

    @NotBlank(message = "Password cannot be empty.")
    private String password;

    @Override
    public String toString() {
        return "LoginRequestDto(" +
                "username='" + username + '\'' +
                ", password='" + Masker.fullMask(password) + '\'' +
                ')';
    }
}
