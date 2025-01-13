package com.api.curso.api_curso.modules.cursos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltroCursoDTO {
    private String name;
    private String category;
}
