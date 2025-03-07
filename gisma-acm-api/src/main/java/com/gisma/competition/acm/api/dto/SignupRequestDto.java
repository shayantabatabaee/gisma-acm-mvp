package com.gisma.competition.acm.api.dto;

import com.gisma.competition.acm.api.util.Masker;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequestDto {

    @NotBlank(message = "Username cannot be empty.")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters.")
    private String username;

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    @Email(message = "Email address is not valid.")
    private String email;

    @Override
    public String toString() {
        return "SignupRequestDto(" +
                "username='" + username + '\'' +
                ", password='" + Masker.fullMask(password) + '\'' +
                ", email='" + email + '\'' +
                ')';
    }
}
