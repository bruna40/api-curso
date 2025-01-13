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

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CursoUseCase cursoUseCase;
    @Autowired
    private ListAllCursosByFilterUseCase listAllCursosByFIlterUseCase;

    @PostMapping
    public ResponseEntity<CursoEntity> create(@Valid @RequestBody CursoEntity cursoEntity) {
       var curso = this.cursoUseCase.execute(cursoEntity);

       return ResponseEntity.status(201).body(curso);
    }

    @GetMapping
    public ResponseEntity<List<CursoDTO>> findCursoByFilter(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        FiltroCursoDTO filtroCursoDTO = new FiltroCursoDTO();
        filtroCursoDTO.setName(name);
        filtroCursoDTO.setCategory(category);
        try {
            List<CursoDTO> cursos= listAllCursosByFIlterUseCase.execute(filtroCursoDTO);
            return ResponseEntity.ok().body(cursos);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(List.of(new CursoDTO(UUID.randomUUID(), "Nenhum curso encontrado", "Sem categoria", false, LocalDateTime.now(), LocalDateTime.now())));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoEntity> findCursoById(@PathVariable UUID id, @RequestBody @Valid UpdateCursoDTO updateCursoDTO) {
       

        updateCursoDTO.setId(id);
        
        CursoEntity cursoUpdated = cursoUseCase.updatedCurso(updateCursoDTO);
        return ResponseEntity.ok().body(cursoUpdated);
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CursoEntity> deleteCursoById(@PathVariable UUID id) {
        var curso = cursoUseCase.getById(id).orElseThrow(() -> new CursoNotFoundException("Curso não encontrado"));
        curso.setActive(false);
        curso.setDeletedAt(LocalDateTime.now());
        cursoUseCase.execute(curso);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<CursoEntity> patchUpdateActive(@PathVariable UUID id, @RequestBody UpdateCursoDTO updateCursoDTO) {
        var curso = cursoUseCase.getById(id).orElseThrow(() -> new CursoNotFoundException("Curso não encontrado"));
        curso.setActive(!curso.isActive());
        curso.setUpdatedAt(LocalDateTime.now());
        cursoUseCase.execute(curso);
        return ResponseEntity.ok().build();
    }
    
}
