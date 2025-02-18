package com.gisma.competition.acm.controller;

import com.gisma.competition.acm.api.dto.LoginRequestDto;
import com.gisma.competition.acm.api.dto.LoginResponseDto;
import com.gisma.competition.acm.api.dto.SignupRequestDto;
import com.gisma.competition.acm.api.exception.UserDuplicateException;
import com.gisma.competition.acm.api.facade.AuthenticationFacade;
import com.gisma.competition.acm.persistence.assembler.UserAssembler;
import com.gisma.competition.acm.persistence.entity.User;
import com.gisma.competition.acm.service.UserService;
import com.gisma.competition.acm.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthenticationFacade.BASE_URL)
@AllArgsConstructor
public class AuthenticationController implements AuthenticationFacade {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserAssembler userAssembler;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> signup(SignupRequestDto signupRequestDto) throws UserDuplicateException {
        User user = userService.addUser(signupRequestDto);
        String jwtToken = jwtUtil.generateToken(user);
        HttpHeaders headers = createAuthorizationHeader(jwtToken);
        return ResponseEntity.status(201).headers(headers).body("{}");
    }

    @Override
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        LoginResponseDto loginResponseDto = userAssembler.toLoginResponseDto(user);
        String jwtToken = jwtUtil.generateToken(user);
        HttpHeaders headers = createAuthorizationHeader(jwtToken);
        return ResponseEntity.ok().headers(headers).body(loginResponseDto);
    }

    private HttpHeaders createAuthorizationHeader(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, JwtUtil.BEARER + jwtToken);
        return headers;
    }
}
