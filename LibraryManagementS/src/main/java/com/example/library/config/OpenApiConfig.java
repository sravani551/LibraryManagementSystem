package com.example.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration to customize the generated API documentation.
 * Springdoc will auto-configure /swagger-ui.html and /v3/api-docs endpoints.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Library Management System API")
                        .description("REST API for managing members, books and borrow records")
                        .version("1.0.0")
                        .contact(new Contact().name("Library API Support").email("support@example.com"))
                );
    }
}

