package com.api.curso.api_curso.modules.cursos.controller;

import org.springframework.web.bind.annotation.RestController;

import com.api.curso.api_curso.modules.cursos.dto.CursoDTO;
import com.api.curso.api_curso.modules.cursos.dto.FiltroCursoDTO;
import com.api.curso.api_curso.modules.cursos.dto.UpdateCursoDTO;
import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;
import com.api.curso.api_curso.modules.cursos.exceptions.CursoNotFoundException;
import com.api.curso.api_curso.modules.cursos.useCases.CursoUseCase;
import com.api.curso.api_curso.modules.cursos.useCases.ListAllCursosByFilterUseCase;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class CursoController {

    private final CursoUseCase cursoUseCase;
    private final ListAllCursosByFilterUseCase listAllCursosByFilterUseCase;

    public CursoController(CursoUseCase cursoUseCase, ListAllCursosByFilterUseCase listAllCursosByFilterUseCase) {
        this.cursoUseCase = cursoUseCase;
        this.listAllCursosByFilterUseCase = listAllCursosByFilterUseCase;
    }

    @PostMapping
    public ResponseEntity<CursoEntity> create(@Valid @RequestBody CursoEntity cursoEntity) {
       
        var curso = this.cursoUseCase.execute(cursoEntity);
       return ResponseEntity.status(HttpStatus.CREATED).body(curso);

    }

    @GetMapping
    public ResponseEntity<List<CursoDTO>> findCursoByFilter(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        FiltroCursoDTO filtroCursoDTO = new FiltroCursoDTO(name, category);
        var cursos = listAllCursosByFilterUseCase.execute(filtroCursoDTO);
        return ResponseEntity.ok().body(cursos);
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoEntity> updateCurso(@PathVariable UUID id, @RequestBody @Valid UpdateCursoDTO updateCursoDTO) {
        updateCursoDTO.setId(id);
        cursoUseCase.updatedCurso(updateCursoDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CursoEntity> deleteCursoById(@PathVariable UUID id) {
        cursoUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<CursoEntity> patchUpdateActive(@PathVariable UUID id) {
        cursoUseCase.toggleActive(id);
        return ResponseEntity.noContent().build();
    }
    
}
