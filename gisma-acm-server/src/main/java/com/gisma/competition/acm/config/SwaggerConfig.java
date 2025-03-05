package com.gisma.competition.acm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gisma ACM MVP")
                        .description("API for managing ACM-style programming competitions")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Seyyed Jalal Tabatabaee")
                                .email("seyyed.tabatabaee@gisma-student.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/license/mit")));
    }
}
