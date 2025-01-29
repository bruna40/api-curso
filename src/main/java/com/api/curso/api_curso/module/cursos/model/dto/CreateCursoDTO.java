package com.api.curso.api_curso.module.cursos.model.dto;

import com.api.curso.api_curso.module.cursos.model.entity.CursoEntity;
import com.api.curso.api_curso.module.cursos.model.enums.CategoryEnum;

public record CreateCursoDTO(String name, CategoryEnum category, Double price) {
    public CursoEntity toEntity() {
        return new CursoEntity(
            name,
            category,
            price
        );
    }

}
