package com.api.curso.api_curso.modules.cursos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class FiltroCursoDTO {
    private String name;
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
