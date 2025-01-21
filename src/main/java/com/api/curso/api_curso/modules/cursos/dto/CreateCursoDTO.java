package com.api.curso.api_curso.modules.cursos.dto;

import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;

public record CreateCursoDTO(String name, String category) {
    public CursoEntity toEntity() {
        return new CursoEntity(
            name,
            category
        );
    }

}
