package com.api.curso.api_curso.modules.cursos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class UpdateCursoDTO {
    private UUID id;
    private String name;
    private String category;
    private LocalDateTime updatedAt;
    private boolean active;

    public UpdateCursoDTO(UUID id, String name, String category, LocalDateTime updatedAt, boolean active) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.updatedAt = updatedAt;
        this.active = active;
    }
}
