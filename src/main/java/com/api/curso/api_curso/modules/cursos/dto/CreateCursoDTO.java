package com.api.curso.api_curso.modules.cursos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCursoDTO {
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome pode ter no máximo 100 caracteres")
    private String name;

    @NotBlank(message = "A categoria é obrigatória")
    @Size(max = 50, message = "A categoria pode ter no máximo 50 caracteres")
    private String category;
}
