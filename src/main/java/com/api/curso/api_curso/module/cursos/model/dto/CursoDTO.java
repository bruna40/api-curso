package com.api.curso.api_curso.module.cursos.model.dto;

import java.util.UUID;
import org.springframework.data.domain.Page;

import com.api.curso.api_curso.module.cursos.model.entity.CursoEntity;
import com.api.curso.api_curso.module.cursos.model.enums.CategoryEnum;

public record CursoDTO(UUID id, String name, CategoryEnum category, boolean active, Double price, UUID userId) {
    public static CursoDTO fromEntity(CursoEntity cursoEntity) {
        return new CursoDTO(
            cursoEntity.getId(),
            cursoEntity.getName(),
            cursoEntity.getCategory(),
            cursoEntity.isActive(),
            cursoEntity.getPrice(),
            cursoEntity.getUser().getId()
        );
    }

    public static Page<CursoDTO> fromEntityList(Page<CursoEntity> cursoEntity) {
        return cursoEntity.map(CursoDTO::fromEntity);
    }

    
}
