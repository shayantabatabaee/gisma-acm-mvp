package com.gisma.competition.acm.initializer;

import com.gisma.competition.acm.persistence.entity.User;
import com.gisma.competition.acm.persistence.enumeration.UserRoleModel;
import com.gisma.competition.acm.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements ApplicationRunner {

    private final static Logger logger = LoggerFactory.getLogger(AdminUserInitializer.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${ADMIN_USERNAME}")
    private String adminUsername;
    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;
    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User admin = new User(UserRoleModel.ADMIN, System.currentTimeMillis());
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setEmail(adminEmail);
            userRepository.save(admin);
            logger.info("Admin user created");
        } else {
            logger.info("Admin user already exists");
        }
    }
}
