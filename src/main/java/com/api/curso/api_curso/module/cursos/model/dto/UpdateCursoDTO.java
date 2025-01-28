package com.api.curso.api_curso.module.cursos.model.dto;

import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateCursoDTO {

    private UUID id;
    @Schema(description = "Nome do curso", example = "Java")
    private String name;
    @Schema(description = "Descrição do curso", example = "ensino")
    private String category;

    public UpdateCursoDTO(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
