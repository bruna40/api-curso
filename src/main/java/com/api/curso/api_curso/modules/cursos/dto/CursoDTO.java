package com.api.curso.api_curso.modules.cursos.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CursoDTO {
    
    private UUID id;
    private String name;
    private String category;

    public CursoDTO(UUID id, String category, String name) {
        this.id = id;
        this.category = category;
        this.name = name;
    }
}
