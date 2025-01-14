package com.api.curso.api_curso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "API Curso",
		version = "1.0",
		description = "API para gerenciamento de cursos"
	)
)
public class ApiCursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCursoApplication.class, args);
	}

}
