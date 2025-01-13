package com.api.curso.api_curso.modules.cursos.dto;


import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCursoDTO {

    private UUID id;
    private String name;
    private String category;
}
