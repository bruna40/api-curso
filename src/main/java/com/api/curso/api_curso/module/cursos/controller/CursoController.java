package com.api.curso.api_curso.module.cursos.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.curso.api_curso.exceptions.UserIdNotFound;
import com.api.curso.api_curso.module.cursos.exceptions.CursoNotFoundException;
import com.api.curso.api_curso.module.cursos.exceptions.UnauthorizedActionException;
import com.api.curso.api_curso.module.cursos.model.dto.CursoDTO;
import com.api.curso.api_curso.module.cursos.model.dto.FiltroCursoDTO;
import com.api.curso.api_curso.module.cursos.model.dto.UpdateCursoDTO;
import com.api.curso.api_curso.module.cursos.model.entity.CursoEntity;
import com.api.curso.api_curso.module.cursos.useCases.CursoUseCase;
import com.api.curso.api_curso.module.cursos.useCases.ListAllCursosByFilterUseCase;
import com.api.curso.api_curso.module.users.model.entity.UserEntity;
import com.api.curso.api_curso.module.users.useCases.UserUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/cursos")
@Tag(name = "Curso", description = "Informacoes dos cursos")
public class CursoController {

    private final CursoUseCase cursoUseCase;
    private final ListAllCursosByFilterUseCase listAllCursosByFilterUseCase;

    @Autowired
    private UserUseCase userUseCase;

    public CursoController(CursoUseCase cursoUseCase, ListAllCursosByFilterUseCase listAllCursosByFilterUseCase) {
        this.cursoUseCase = cursoUseCase;
        this.listAllCursosByFilterUseCase = listAllCursosByFilterUseCase;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('PROFESSOR')")
    @Operation(summary = "Listar de todos os cursos disponiveis", description = "Listar todos os cursos, podendo ser filtrado por nome e/ou categoria")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = CursoDTO.class)))
        }),
        @ApiResponse(responseCode = "400", description = "Erro ao listar cursos"),
        @ApiResponse(responseCode = "401", description = "Sem autorizacao")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Page<CursoDTO>> findCursoByFilter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            FiltroCursoDTO filtroCursoDTO = new FiltroCursoDTO(name, category);
            PageRequest pageable = PageRequest.of(page, size);
            Page<CursoDTO> cursos = listAllCursosByFilterUseCase.execute(filtroCursoDTO, pageable);
            return ResponseEntity.ok(cursos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSOR')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CursoEntity> updateCurso(@PathVariable UUID id, @RequestBody @Valid UpdateCursoDTO updateCursoDTO) {
        updateCursoDTO.setId(id);
        cursoUseCase.updatedCurso(updateCursoDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cursoId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSOR')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CursoEntity> deleteCursoById(@PathVariable UUID cursoId, @AuthenticationPrincipal UserEntity user) {
        try {

            if (cursoId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userEmail == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            var userRole = userUseCase.getByEmail(userEmail);
            cursoUseCase.delete(cursoId, userEmail, userRole.getRole());
            return ResponseEntity.noContent().build();
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (CursoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } 
    }

    @PatchMapping("/{id}/active")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CursoEntity> patchUpdateActive(@PathVariable UUID id) {
        cursoUseCase.toggleActive(id);
        return ResponseEntity.noContent().build();
    }
    
}
