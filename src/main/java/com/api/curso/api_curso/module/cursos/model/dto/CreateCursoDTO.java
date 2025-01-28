package com.api.curso.api_curso.module.cursos.model.dto;

import com.api.curso.api_curso.module.cursos.model.entity.CursoEntity;

public record CreateCursoDTO(String name, String category, Double price) {
    public CursoEntity toEntity() {
        return new CursoEntity(
            name,
            category,
            price
        );
    }

}
