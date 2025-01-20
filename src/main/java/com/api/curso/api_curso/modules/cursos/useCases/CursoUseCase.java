package com.api.curso.api_curso.modules.cursos.useCases;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.api.curso.api_curso.modules.cursos.dto.UpdateCursoDTO;
import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;
import com.api.curso.api_curso.modules.cursos.exceptions.CursoNotFoundException;
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
        var cursoEntity = cursoRepository.findById(updateCursoDTO.getId()).orElseThrow(() -> new CursoNotFoundException("Curso não encontrado"));
        
        if (cursoEntity.getName() != null) {
            updateCursoDTO.setName(updateCursoDTO.getName());
        }

        if (cursoEntity.getCategory() != null) {
            updateCursoDTO.setCategory(updateCursoDTO.getCategory());
        }

        cursoEntity.setUpdatedAt(LocalDateTime.now());

        return cursoRepository.save(cursoEntity);
    }

    public void toggleActive(UUID id) {
        var cursoEntity = cursoRepository.findById(id).orElseThrow(() -> new CursoNotFoundException("Curso não encontrado"));
        cursoEntity.setActive(!cursoEntity.isActive());
        cursoRepository.save(cursoEntity);
    }

    public void delete(UUID id) {
        var cursoEntity = cursoRepository.findById(id).orElseThrow(() -> new CursoNotFoundException("Curso não encontrado"));
        cursoEntity.setDeletedAt(LocalDateTime.now());
        cursoRepository.save(cursoEntity);
    }

    public Page<CursoEntity> listAllPageable(int page, int size) {
       Pageable pageable = PageRequest.of(page, size);
       return cursoRepository.findByDeletedAtIsNull(pageable);
    }

}
