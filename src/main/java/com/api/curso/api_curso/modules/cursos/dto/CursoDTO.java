package com.api.curso.api_curso.modules.cursos.dto;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;

public record CursoDTO(UUID id, String name, String category, boolean active, UUID userId) {
    public static CursoDTO fromEntity(CursoEntity cursoEntity) {
        return new CursoDTO(
            cursoEntity.getId(),
            cursoEntity.getName(),
            cursoEntity.getCategory(),
            cursoEntity.isActive(),
            cursoEntity.getUser().getId()
        );
    }

    public static Page<CursoDTO> fromEntityList(Page<CursoEntity> cursoEntity) {
        return cursoEntity.map(CursoDTO::fromEntity);
    }

    
}
