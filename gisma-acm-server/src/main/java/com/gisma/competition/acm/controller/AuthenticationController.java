package com.gisma.competition.acm.controller;

import com.gisma.competition.acm.api.dto.SignupRequestDto;
import com.gisma.competition.acm.api.exception.UserDuplicateException;
import com.gisma.competition.acm.api.facade.AuthenticationFacade;
import com.gisma.competition.acm.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthenticationFacade.BASE_URL)
@AllArgsConstructor
public class AuthenticationController implements AuthenticationFacade {

    private final UserService userService;

    @Override
    public ResponseEntity<String> signup(SignupRequestDto signupRequestDto) throws UserDuplicateException {
        userService.addUser(signupRequestDto);
        return ResponseEntity.status(200).body("{}");
    }

}
