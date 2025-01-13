package com.api.curso.api_curso.modules.cursos.useCases;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.curso.api_curso.modules.cursos.dto.UpdateCursoDTO;
import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;
import com.api.curso.api_curso.modules.cursos.repository.CursoRepository;


@Service
public class CursoUseCase {

    @Autowired
    private CursoRepository cursoRepository;
    

    public CursoEntity execute(CursoEntity cursoEntity) {
        return cursoRepository.save(cursoEntity);
    }

    public Optional<CursoEntity> getById(UUID id) {
       return cursoRepository.findById(id);
    }

    public CursoEntity updatedCurso(UpdateCursoDTO updateCursoDTO) {
        var cursoEntity = cursoRepository.findById(updateCursoDTO.getId()).orElseThrow();
        if (cursoEntity.getName() != null) {
            updateCursoDTO.setName(updateCursoDTO.getName());
        }
        if (cursoEntity.getCategory() != null) {
            updateCursoDTO.setCategory(updateCursoDTO.getCategory());
        }

        cursoEntity.setUpdatedAt(LocalDateTime.now());

        return cursoRepository.save(cursoEntity);
    }

}
