package com.api.curso.api_curso.modules.cursos.controller;

import org.springframework.web.bind.annotation.RestController;

import com.api.curso.api_curso.modules.cursos.dto.CreateCursoDTO;
import com.api.curso.api_curso.modules.cursos.dto.CursoDTO;
import com.api.curso.api_curso.modules.cursos.dto.FiltroCursoDTO;
import com.api.curso.api_curso.modules.cursos.dto.UpdateCursoDTO;
import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;
import com.api.curso.api_curso.modules.cursos.useCases.CursoUseCase;
import com.api.curso.api_curso.modules.cursos.useCases.ListAllCursosByFilterUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;


import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/cursos")
@Tag(name = "Curso", description = "Informacoes dos cursos")
public class CursoController {

    private final CursoUseCase cursoUseCase;
    private final ListAllCursosByFilterUseCase listAllCursosByFilterUseCase;

    public CursoController(CursoUseCase cursoUseCase, ListAllCursosByFilterUseCase listAllCursosByFilterUseCase) {
        this.cursoUseCase = cursoUseCase;
        this.listAllCursosByFilterUseCase = listAllCursosByFilterUseCase;
    }

    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar curso", description = "Apenas pessoas com o perfil de administrador podem criar cursos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CreateCursoDTO.class))
        }),
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CursoEntity> create(@Valid @RequestBody CursoEntity cursoEntity) {
        try {
            CursoEntity curso = cursoUseCase.execute(cursoEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(curso);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
       

    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  
    @Operation(summary = "Listar de todos os cursos disponiveis", description = "Listar todos os cursos, podendo ser filtrado por nome e/ou categoria")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = CursoDTO.class)))
        }),
        @ApiResponse(responseCode = "400", description = "Erro ao listar cursos"),
        @ApiResponse(responseCode = "401", description = "Sem autorizacao")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<CursoDTO>> findCursoByFilter(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        try {
            FiltroCursoDTO filtroCursoDTO = new FiltroCursoDTO(name, category);
            List<CursoDTO> cursos = listAllCursosByFilterUseCase.execute(filtroCursoDTO);
            return ResponseEntity.ok(cursos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CursoEntity> updateCurso(@PathVariable UUID id, @RequestBody @Valid UpdateCursoDTO updateCursoDTO) {
        updateCursoDTO.setId(id);
        cursoUseCase.updatedCurso(updateCursoDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CursoEntity> deleteCursoById(@PathVariable UUID id) {
        cursoUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/active")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CursoEntity> patchUpdateActive(@PathVariable UUID id) {
        cursoUseCase.toggleActive(id);
        return ResponseEntity.noContent().build();
    }
    
}
