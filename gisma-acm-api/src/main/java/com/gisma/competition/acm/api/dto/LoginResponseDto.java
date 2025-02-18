package com.gisma.competition.acm.api.dto;

import com.gisma.competition.acm.api.dto.enumeration.UserRole;
import lombok.Data;

@Data
public class LoginResponseDto {
    private String username;
    private String email;
    private UserRole userRole;
    private Long registrationDate;
}
