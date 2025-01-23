package com.api.curso.api_curso.modules.cursos.dto;

import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;

public record CreateCursoDTO(String name, String category, Double price) {
    public CursoEntity toEntity() {
        return new CursoEntity(
            name,
            category,
            price
        );
    }

}
