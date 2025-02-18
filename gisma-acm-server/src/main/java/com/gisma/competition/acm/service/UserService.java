package com.gisma.competition.acm.service;

import com.gisma.competition.acm.api.dto.SignupRequestDto;
import com.gisma.competition.acm.api.exception.UserDuplicateException;
import com.gisma.competition.acm.persistence.entity.User;
import com.gisma.competition.acm.persistence.enumeration.UserRole;
import com.gisma.competition.acm.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void addUser(SignupRequestDto signupRequestDto) throws UserDuplicateException {

        if (userRepository.existsUserByUsername(signupRequestDto.getUsername()) ||
                userRepository.existsUserByEmail(signupRequestDto.getEmail())) {
            throw new UserDuplicateException();
        }

        User user = new User(UserRole.STANDARD, System.currentTimeMillis());
        user.setUsername(signupRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        user.setEmail(signupRequestDto.getEmail());

        userRepository.save(user);
    }

}
