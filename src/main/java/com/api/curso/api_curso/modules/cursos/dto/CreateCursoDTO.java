package com.api.curso.api_curso.modules.cursos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCursoDTO {
    @Schema(description = "Nome do curso", example = "Java", requiredMode = RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "Descrição do curso", example = "ensino", requiredMode = RequiredMode.REQUIRED)
    private String category;
}
