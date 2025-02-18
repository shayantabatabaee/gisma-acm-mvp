package com.gisma.competition.acm.persistence.assembler;

import com.gisma.competition.acm.api.dto.LoginResponseDto;
import com.gisma.competition.acm.api.dto.enumeration.UserRole;
import com.gisma.competition.acm.persistence.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    public LoginResponseDto toLoginResponseDto(User user) {
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setUsername(user.getUsername());
        responseDto.setEmail(user.getEmail());
        responseDto.setRegistrationDate(user.getRegistrationDate());
        responseDto.setUserRole(UserRole.valueOf(user.getUserRole().name()));
        return responseDto;
    }
}
