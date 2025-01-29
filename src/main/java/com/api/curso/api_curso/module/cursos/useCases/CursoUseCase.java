package com.api.curso.api_curso.module.cursos.useCases;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.curso.api_curso.module.cursos.exceptions.CursoNotFoundException;
import com.api.curso.api_curso.module.cursos.exceptions.UnauthorizedActionException;
import com.api.curso.api_curso.module.cursos.model.dto.UpdateCursoDTO;
import com.api.curso.api_curso.module.cursos.model.entity.CursoEntity;
import com.api.curso.api_curso.module.cursos.repository.CursoRepository;
import com.api.curso.api_curso.module.users.model.entity.RoleEnum;


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

    public void delete(UUID id, String email, RoleEnum role) {
        var cursoEntity = cursoRepository.findById(id).orElseThrow(() -> new CursoNotFoundException("Curso não encontrado"));
        if (cursoEntity.getDeletedAt() != null) {
            throw new CursoNotFoundException("Curso já foi excluído.");
        }
        if (role == RoleEnum.ADMIN) {
            cursoEntity.setDeletedAt(LocalDateTime.now());
            cursoRepository.save(cursoEntity);
        } 

        else if (role == RoleEnum.PROFESSOR) {
            if (!cursoEntity.getUser().getEmail().equals(email)) { 
                throw new UnauthorizedActionException("Você não tem permissão para deletar este curso.");
            }
            cursoEntity.setDeletedAt(LocalDateTime.now());
            cursoRepository.save(cursoEntity);
        } 
        
        else {
            throw new UnauthorizedActionException("Você não tem permissão para deletar cursos.");
        }
    }
    

    public Page<CursoEntity> listAllPageable(int page, int size) {
       Pageable pageable = PageRequest.of(page, size);
       return cursoRepository.findByDeletedAtIsNull(pageable);
    }

    

}
