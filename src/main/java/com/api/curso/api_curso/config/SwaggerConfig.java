package com.api.curso.api_curso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info().title("API Curso").description("API para gerenciamento de cursos")
            .version("1.0"))
            .schemaRequirement("jwt_auth", createSecurityScheme());
    }

   
    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
            .name("jwt_auth")
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");
    }

}
