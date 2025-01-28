package com.api.curso.api_curso.module.cursos.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class FiltroCursoDTO {

    @Schema(description = "Nome do curso", example = "Java")
    private String name;
    @Schema(description = "Categoria do curso", example = "ensino")
    private String category;

    public FiltroCursoDTO(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
